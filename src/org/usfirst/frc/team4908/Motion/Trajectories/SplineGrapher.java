package org.usfirst.frc.team4908.Motion.Trajectories;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SplineGrapher extends JPanel
{
    // Some global constants for reference
    public static final double PPI = 1.60; // "Pixels Per Inch" - needs to be scaled once field is overlaid on background
    public static final double TRACK_WIDTH = 15.0; // 1/2 of the track width of the robot in inches
    public static final int K_WIDTH = 647, K_HEIGHT = 634; // Height and width of JPanel, will change with field image

    // class objects
    public ArrayList<DraggablePoint> editPoints = new ArrayList<>();
    public ArrayList<ReferencePoint> refPoints = new ArrayList<>();

    public Trajectory traj;

    public DecimalFormat df = new DecimalFormat("0.##");

    final public BufferedImage background = ImageIO.read(getClass().getResource("/res/STEAMWORKSFIELD.png"));

    public SplineGrapher() throws IOException
    {
        // Generate some points for example
        editPoints.add(new DraggablePoint());
        editPoints.add(new DraggablePoint());
        editPoints.add(new DraggablePoint());
        editPoints.add(new DraggablePoint());

        refPoints.add(new ReferencePoint(50,50));
        refPoints.add(new ReferencePoint(80,400));
        refPoints.add(new ReferencePoint(200,300));
        refPoints.add(new ReferencePoint(400,250));

        // create trajectory
        traj = new Trajectory(5.0, refPoints);

        Dimension size = new Dimension(K_WIDTH, K_HEIGHT);
        setSize(size);
        setPreferredSize(size);
        setLayout(null);

        // initialize and add points to the panel
        for(int i = 0; i < editPoints.size(); i++)
        {
            initRefPoint(editPoints.get(i), i);
        }

    }

    /**
     * method from parent JPanel class, called whenever the window is updated
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g)
    {
        traj = getUpdatedTrajectory();

        Graphics2D g2d = (Graphics2D) g;

        g2d.clearRect(0,0, K_WIDTH,K_HEIGHT);
        g2d.drawImage(background, 0, 0, null);


        g2d.setStroke(new BasicStroke(2));

        g2d.draw(generateRefPath(traj));

        g2d.setColor(Color.RED);
        g2d.draw(generateSplinePath(traj));

        g2d.setColor(Color.BLUE);
        g2d.draw(generateLeftPath(traj));
        g2d.draw(generateRightPath(traj));

        for(int i = 0; i < editPoints.size(); i++)
        {
            g2d.drawString("(" + df.format(editPoints.get(i).getXPositionFeet() - editPoints.get(0).getXPositionFeet()) +
                            ", " + df.format(editPoints.get(i).getYPositionFeet() - editPoints.get(0).getYPositionFeet()) + ")",
                            editPoints.get(i).getX() + 10, editPoints.get(i).getY() + 10);
        }

        for(DraggablePoint p : editPoints)
        {
            if(p.isChanged)
            {
                this.repaint();
                p.setUpdated();
            }
        }
    }

    /**
     *  Various settings set for the button to make it work
     *
     * @param r     Point to be initialized
     * @param num   index of the point
     */
    public void initRefPoint(DraggablePoint r, int num)
    {
        r.setOpaque(false);
        r.setContentAreaFilled(false);
        r.setBorderPainted(false);
        r.setFocusPainted(false);

        r.setLocation((int)traj.getReferencePoints().get(num).getX(), K_HEIGHT - (int)traj.getReferencePoints().get(num).getY());
        r.setSize(10, 10);
        add(r);
    }

    /**
     * Regenerates the trajectory with the locations of the draggable points
     *
     * @return
     */
    public Trajectory getUpdatedTrajectory()
    {
        for(int i = 0; i < editPoints.size(); i++)
        {
            if(refPoints.get(i) == null)
            {
                refPoints.add(new ReferencePoint(editPoints.get(i).getX() - 5,  K_HEIGHT - editPoints.get(i).getY() + 5));
            }
            else
            {
                refPoints.get(i).setX(editPoints.get(i).getX() - 5);
                refPoints.get(i).setY(K_HEIGHT - editPoints.get(i).getY() + 5);
            }
        }

        return new Trajectory(5, refPoints);
    }

    public Path2D generateRefPath(Trajectory t)
    {
        Path2D p = new Path2D.Double();
        double ref_x, ref_y;

        for (int i = 0; i < t.getReferencePoints().size(); i++)
        {
            ref_x = 10 + t.getReferencePoints().get(i).getX();
            ref_y = 10 + K_HEIGHT - t.getReferencePoints().get(i).getY();

            if (i == 0)
                p.moveTo(ref_x, ref_y);
            else
                p.lineTo(ref_x, ref_y);
        }

        return(p);
    }

    public Path2D generateSplinePath(Trajectory t)
    {
        Path2D p = new Path2D.Double();
        double set_x, set_y;

        for(int i = 0; i < t.getSetpoints().size(); i++)
        {
            set_x = (10 + t.getSetpoints().get(i).getX());
            set_y = (10 + K_HEIGHT - t.getSetpoints().get(i).getY());

            if(i == 0)
                p.moveTo(set_x, set_y);
            else
                p.lineTo(set_x, set_y);
        }

        return p;
    }


    public Path2D generateLeftPath(Trajectory t)
    {
        Path2D p = new Path2D.Double();
        double set_x, set_y;

        for (int i = 0; i < traj.getSetpoints().size(); i++)
        {
            //if(i % 3 == 0)
            {
                set_x = (10 + t.getSetpoints().get(i).getX()) - (Math.sin(t.getSetpoints().get(i).getH()) * (PPI * TRACK_WIDTH));
                set_y = (10 + K_HEIGHT - t.getSetpoints().get(i).getY()) - (Math.cos(t.getSetpoints().get(i).getH()) * (PPI * TRACK_WIDTH));

                if(i == 0)
                    p.moveTo(set_x, set_y);
                else
                    p.lineTo(set_x, set_y);
            }
        }

        return(p);
    }

    public Path2D generateRightPath(Trajectory t)
    {
        Path2D p = new Path2D.Double();
        double set_x, set_y;

        for (int i = 0; i < traj.getSetpoints().size(); i++)
        {
            set_x = (10 + t.getSetpoints().get(i).getX()) + (Math.sin(t.getSetpoints().get(i).getH()) * (PPI * TRACK_WIDTH));
            set_y = (10 + K_HEIGHT - t.getSetpoints().get(i).getY()) + (Math.cos(t.getSetpoints().get(i).getH()) * (PPI * TRACK_WIDTH));

            if(i == 0)
                p.moveTo(set_x, set_y);
            else
                p.lineTo(set_x, set_y);
        }

        return(p);
    }
}
