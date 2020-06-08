import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
public class FractalExplorer
{
    private int m_DisplaySize;
    private JImageDisplay m_Display;
    private JButton m_But;
    private JFrame m_Frame;
    private FractalGenerator m_Gener;
    private Rectangle2D.Double m_Rover;
    private class actionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            m_Gener.getInitialRange(m_Rover);
            drawFractal();
        }
    }
    private class MouseListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            double xCoord = m_Gener.getCoord(m_Rover.x, m_Rover.x + m_Rover.width, m_DisplaySize,x);
            double yCoord = m_Gener.getCoord(m_Rover.y, m_Rover.y + m_Rover.height, m_DisplaySize,y);
            m_Gener.recenterAndZoomRange(m_Rover, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    public FractalExplorer(int ScreenSize)
    {
        m_DisplaySize = ScreenSize;
        m_Rover = new Rectangle2D.Double();
        m_Gener = new Mandelbrot();
        m_Gener.getInitialRange(m_Rover);
    }
    public void createAndShowGUI()
    {
        m_Frame  = new JFrame();
        m_Display = new JImageDisplay(m_DisplaySize, m_DisplaySize);
        m_But = new JButton("Tap me");
        m_Frame.getContentPane().add(m_Display, BorderLayout.CENTER);
        m_Frame.getContentPane().add(m_But, BorderLayout.SOUTH);
        m_Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        m_Display.addMouseListener(new MouseListener());
        m_But.addActionListener(new actionListener());
        m_Frame.pack();
        m_Frame.setVisible(true);
        m_Frame.setResizable(true);
    }
    private void drawFractal()
    {
        for (int x = 0; x < m_DisplaySize; x++)
        {
            for (int y = 0; y < m_DisplaySize; y++)
            {
                double xCoord = FractalGenerator.getCoord
                        (m_Rover.x, m_Rover.x + m_Rover.width, m_DisplaySize, x);
                double yCoord = FractalGenerator.getCoord
                        (m_Rover.y, m_Rover.y + m_Rover.height, m_DisplaySize, y);
                int IterNum = m_Gener.numIterations(xCoord, yCoord);
                if (IterNum == -1) m_Display.drawPixel(x, y, 0);
                else
                    {
                    float hue = 0.7f + (float) IterNum / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    m_Display.drawPixel(x, y, rgbColor);
                }
            }
        }
        m_Display.repaint();
    }
    public static void main(String args[])
    {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }

}
