package org.usfirst.frc.team4908.Motion.Trajectories;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

/**
 * @author Siggy
 *         $
 */
public class VisualRunner
{
    SplineGrapher s;

    Random r = new Random();

    public static void main(String[] args)
    {
        new VisualRunner().run();
    }

    public void run()
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("Splines");
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

            try
            {
                s = new SplineGrapher();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            JPanel controls = new JPanel();

            JButton addPointButton = new JButton();
            addPointButton.setText("Add Point");
            addPointButton.addActionListener(e ->
            {
                DraggablePoint d = new DraggablePoint();
                d.setLocation(r.nextInt(640), r.nextInt(480));
                s.editPoints.add(d);
                s.refPoints.add(new ReferencePoint(d.getX()-5, s.K_HEIGHT - d.getY() + 5));
                s.traj = s.getUpdatedTrajectory();
                s.initRefPoint(s.editPoints.get(s.editPoints.size()-1), (s.editPoints.size()-1));
                s.repaint();
            });

            JButton removePointButton = new JButton();
            removePointButton.setText("Remove Point");
            removePointButton.addActionListener(e ->
            {
                s.editPoints.get(s.editPoints.size()-1).setVisible(false);
                s.editPoints.remove(s.editPoints.size()-1);
                s.refPoints.remove(s.refPoints.size()-1);
                s.traj = s.getUpdatedTrajectory();
                s.repaint();
            });

            JButton save = new JButton();
            save.setText("Raw Data");
            save.addActionListener(e->
            {
                SwingUtilities.invokeLater(() ->
                {
                    JFrame newWindow = new JFrame();
                    newWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    newWindow.setLocation(400, 200);
                    JTextArea text = new JTextArea();
                    String raw = "";

                    for(int i = 0; i < s.editPoints.size(); i++)
                    {
                        raw += (s.df.format(s.editPoints.get(i).getXPositionFeet() - s.editPoints.get(0).getXPositionFeet()) + "\t" +
                                s.df.format(s.editPoints.get(i).getYPositionFeet() - s.editPoints.get(0).getYPositionFeet()) + "\n");
                    }

                    text.setText(raw);
                    text.setEditable(false);
                    JScrollPane pane = new JScrollPane(text);

                    newWindow.add(pane);
                    newWindow.pack();
                    newWindow.setVisible(true);

                });
            });


            JButton code = new JButton();
            code.setText("Get Code");
            code.addActionListener(e->
            {
                SwingUtilities.invokeLater(() ->
                {
                    JFrame newWindow = new JFrame();
                    newWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    newWindow.setLocation(400, 200);
                    JTextArea text = new JTextArea();
                    String raw = "";
                    String x, y;

                    raw += "ArrayList<ReferencePoint> refPoints = new ArrayList<>();\n";

                    for(int i = 0; i < s.editPoints.size(); i++)
                    {
                        x = s.df.format(s.editPoints.get(i).getXPositionFeet() - s.editPoints.get(0).getXPositionFeet());
                        y = s.df.format(s.editPoints.get(i).getYPositionFeet() - s.editPoints.get(0).getYPositionFeet());

                        raw += "refPoints.add(new ReferencePoint(" + x + ", " + y + "));\n";
                    }
                    raw += "Trajectory traj = new Trajectory(/*target time*/, refPoints);";

                    text.setText(raw);
                    text.setEditable(false);
                    JScrollPane pane = new JScrollPane(text);

                    newWindow.add(pane);
                    newWindow.pack();
                    newWindow.setVisible(true);

                });
            });

            JComboBox fieldSelector = new JComboBox(new String[]{"Steamworks", "Stronghold"});

            fieldSelector.setSelectedIndex(0);
            fieldSelector.addActionListener(e ->
            {
                switch (fieldSelector.getSelectedIndex())
                {

                }
            });

            controls.add(addPointButton);
            controls.add(removePointButton);
            controls.add(save);
            controls.add(code);

            frame.getContentPane().add(s);
            frame.getContentPane().add(controls);

            frame.pack();
            frame.setVisible(true);
        });
    }

}
