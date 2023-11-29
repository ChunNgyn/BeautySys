/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lgnvswing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author LGNV
 */
public class lgnvPanel extends JPanel {

    private int LGNV_borderRadius =0;
    private boolean LGNV_GradientBackGround = false;
    private Color LGNV_GradientStartColor = Color.RED;
    private Color LGNV_GradientEndColor = Color.PINK;
    
    private boolean LGNV_lineAllow = true;
    private Color LGNV_lineColor = Color.BLACK;

    private boolean LGNV_ShaDownAllow = false;
    private int LGNV_ShadownOpactity = 40;
    private int LGNV_ShadownPixel = 15;
    private int LGNV_ShadownColor = 0;

    
    

    public lgnvPanel() {
        super();
        setOpaque(false);

        
    }
    

    @Override
    public void paintComponent(Graphics g) {

        
        
        super.paintComponent(g);
       
        
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint Gradient;
        Shape shape2;

        if (LGNV_ShaDownAllow) {
            for (int i = 0; i < LGNV_ShadownPixel; i++) {
                g2d.setColor(new Color(LGNV_ShadownColor, LGNV_ShadownColor, LGNV_ShadownColor, ((LGNV_ShadownOpactity / 20) * i)));
                shape2 = new RoundRectangle2D.Double(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), LGNV_borderRadius - 2, LGNV_borderRadius + 2);
                g2d.draw(shape2);
            }// phần tạo shawdown
        }
        
        

        if (LGNV_GradientBackGround) {
            setBackground(new Color(0, 0, 0, 0));
            Gradient = new GradientPaint(0, 0, LGNV_GradientStartColor, getHeight(), getHeight()*3/2, LGNV_GradientEndColor);
            // phần tạo Gradient
        } else {
            Gradient = new GradientPaint(0, 0, getColor(), getWidth(), getHeight(), getColor());
            // phần tạo màu thường
        }
        
        
        g2d.setPaint(Gradient); 
        Shape shape;

        if (LGNV_ShaDownAllow) {
            shape = new RoundRectangle2D.Double(LGNV_ShadownPixel / 2, LGNV_ShadownPixel / 2, getWidth() - LGNV_ShadownPixel, getHeight() - LGNV_ShadownPixel, LGNV_borderRadius - 2, LGNV_borderRadius + 2);
        } else {
            shape = new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, LGNV_borderRadius - 2, LGNV_borderRadius + 2);
            // tạo shape nếu không có boxshadow
        }
        g2d.fill(shape); // đổ màu lên qua shape
        
        
        if (LGNV_lineAllow && !LGNV_ShaDownAllow) {
            g2d.setColor(LGNV_lineColor);
            g2d.draw(shape);
        }

    }
    
    
    

    private Color getColor() {
        if (!isEnabled()) {
            return new Color(204, 204, 204);
        }
        Color temp = getBackground();
        return temp;
    }

    //

    

    //

    public int getLGNV_borderRadius() {
        return LGNV_borderRadius;
    }

    public void setLGNV_borderRadius(int LGNV_borderRadius) {
        this.LGNV_borderRadius = LGNV_borderRadius;
    }

    public boolean isLGNV_GradientBackGround() {
        return LGNV_GradientBackGround;
    }

    public void setLGNV_GradientBackGround(boolean LGNV_GradientBackGround) {
        this.LGNV_GradientBackGround = LGNV_GradientBackGround;
    }

    public Color getLGNV_GradientStartColor() {
        return LGNV_GradientStartColor;
    }

    public void setLGNV_GradientStartColor(Color LGNV_GradientStartColor) {
        this.LGNV_GradientStartColor = LGNV_GradientStartColor;
    }

    public Color getLGNV_GradientEndColor() {
        return LGNV_GradientEndColor;
    }

    public void setLGNV_GradientEndColor(Color LGNV_GradientEndColor) {
        this.LGNV_GradientEndColor = LGNV_GradientEndColor;
    }

    public boolean isLGNV_lineAllow() {
        return LGNV_lineAllow;
    }

    public void setLGNV_lineAllow(boolean LGNV_lineAllow) {
        this.LGNV_lineAllow = LGNV_lineAllow;
    }

    public Color getLGNV_lineColor() {
        return LGNV_lineColor;
    }

    public void setLGNV_lineColor(Color LGNV_lineColor) {
        this.LGNV_lineColor = LGNV_lineColor;
    }

    public boolean isLGNV_ShaDownAllow() {
        return LGNV_ShaDownAllow;
    }

    public void setLGNV_ShaDownAllow(boolean LGNV_ShaDownAllow) {
        this.LGNV_ShaDownAllow = LGNV_ShaDownAllow;
    }

    public int getLGNV_ShadownOpactity() {
        return LGNV_ShadownOpactity;
    }

    public void setLGNV_ShadownOpactity(int LGNV_ShadownOpactity) {
        this.LGNV_ShadownOpactity = LGNV_ShadownOpactity;
    }

    public int getLGNV_ShadownPixel() {
        return LGNV_ShadownPixel;
    }

    public void setLGNV_ShadownPixel(int LGNV_ShadownPixel) {
        this.LGNV_ShadownPixel = LGNV_ShadownPixel;
    }

    public int getLGNV_ShadownColor() {
        return LGNV_ShadownColor;
    }

    public void setLGNV_ShadownColor(int LGNV_ShadownColor) {
        this.LGNV_ShadownColor = LGNV_ShadownColor;
    }
}
