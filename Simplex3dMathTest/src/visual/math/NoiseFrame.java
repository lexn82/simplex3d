/*
 * Simplex3d, MathTest package
 * Copyright (C) 2009-2010 Simplex3d Team
 *
 * This file is part of Simplex3dMathTest.
 *
 * Simplex3dMathTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dMathTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package visual.math;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * @author Aleksey Nikiforov (lex)
 */
public class NoiseFrame extends JFrame {

    public static interface Painter {
        public int[] paint(int width, int height);
    }

    protected Painter painter;
    private Timer timer;
    private FpsTimer fpsTimer;

    private BufferedImage img;
    private int width;
    private int height;

    private class DrawingPanel extends JPanel implements ActionListener {
        @Override public void paint(Graphics g) {
            fpsTimer.update();

            if (width != getWidth() || height != getHeight()) {
                width = getWidth();
                height = getHeight();
                img = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_ARGB_PRE
                );
            }
            
            int[] buffer = painter.paint(width, height);
            img.setRGB(0, 0, width, height, buffer, 0, width);
            g.drawImage(img, 0, 0, null);

            String fps = String.valueOf(fpsTimer.fps());
            Font bold = new Font("Monospaced", Font.BOLD, 16);
            g.setFont(bold);
            g.setColor(Color.BLACK);
            g.drawString(fps, 10, 20);
        }
        
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    /** Creates new form NoiseFrame */
    public NoiseFrame() {
        initComponents();
        timer = new Timer(1000/60, (ActionListener) drawingPanel);
        fpsTimer = new FpsTimer();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        drawingPanel = new DrawingPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Noise Test");

        drawingPanel.setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 635, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void run(final Painter painter) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NoiseFrame frame = new NoiseFrame();
                frame.painter = painter;
                frame.timer.start();
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel drawingPanel;
    // End of variables declaration//GEN-END:variables

}
