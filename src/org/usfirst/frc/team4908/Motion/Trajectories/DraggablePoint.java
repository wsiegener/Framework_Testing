package org.usfirst.frc.team4908.Motion.Trajectories;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DraggablePoint extends JButton
{
    BufferedImage icon;

    private int draggedX, draggedY;

    boolean isChanged = false;

    public DraggablePoint()
    {
        super();

        try
        {
            icon = ImageIO.read(getClass().getResource("/res/circle.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                draggedX = e.getX();
                draggedY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                setLocation( e.getX() - draggedX + getLocation().x,e.getY() - draggedY + getLocation().y);
                isChanged = true;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.drawImage(icon, 0, 0, null);
        g.dispose();
    }

    public void setUpdated()
    {
        isChanged = false;
    }

    public double getXPositionFeet()
    {
        return ((getX() / SplineGrapher.PPI) / 12.0);
    }

    public double getYPositionFeet()
    {
        return ((SplineGrapher.K_HEIGHT - getY() / SplineGrapher.PPI) / 12.0);
    }
}
