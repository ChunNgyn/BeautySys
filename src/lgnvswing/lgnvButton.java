/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lgnvswing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

/**
 *
 * @author LGNV
 */
public class lgnvButton extends JButton implements MouseListener {

    private boolean LGNV_backgroundAllow;
    
    private Color LGNV_gradientBackgroundColor;
    
    private boolean LGNV_lineAllow;
    private Color LGNV_lineColor = Color.BLACK;
    private Color LGNV_lineGradientColor;

    
    private int LGNV_borderRadius  = 0;

    private boolean LGNV_ShadowAllow;
    private int LGNV_ShadownOpactity = 40;
    private int LGNV_ShadownPixel = 15;
    private int LGNV_ShadownColor = 0;
    
    private boolean LGNV_hoverAllow;
    private Color LGNV_enteredColor;
    private Color LGNV_pressedColor;
    private boolean entered;
    private boolean pressed;
    
    

    public lgnvButton() {
        super();
        LGNV_backgroundAllow = true;
        LGNV_lineAllow = true;
        entered = false;
        pressed = false;
        LGNV_enteredColor = getBackground().brighter();
        LGNV_pressedColor = getBackground().darker();
        LGNV_hoverAllow = true;
        this.setFocusable(false);
        this.setBorder(null);
        
        setCursor(new Cursor(Cursor.HAND_CURSOR) {
        });

        setContentAreaFilled(false);
        setFocusPainted(false);
        addMouseListener(this);

    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg); //To change body of generated methods, choose Tools | Templates.
        LGNV_enteredColor = bg.brighter();
        LGNV_pressedColor = bg.darker();

    }

    @Override
    protected void paintComponent(Graphics g) {
//       

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape s = (LGNV_borderRadius>0) ? new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, LGNV_borderRadius - 2, LGNV_borderRadius + 2)
                : new Rectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2);
        
        if(LGNV_ShadowAllow){
            s = (LGNV_borderRadius>0) ? new RoundRectangle2D.Float(LGNV_ShadownPixel/2, LGNV_ShadownPixel/2, getWidth() - LGNV_ShadownPixel, getHeight() - LGNV_ShadownPixel, LGNV_borderRadius - 2, LGNV_borderRadius + 2)
                : new Rectangle2D.Float(LGNV_ShadownPixel/2, LGNV_ShadownPixel/2, getWidth() - LGNV_ShadownPixel, getHeight() - LGNV_ShadownPixel);
        }
        
        
        if (LGNV_ShadowAllow) {
            for (int i = 0; i < LGNV_ShadownPixel; i++) {
                g2.setColor(new Color(LGNV_ShadownColor, LGNV_ShadownColor, LGNV_ShadownColor, ((LGNV_ShadownOpactity / 20) * i)));
                s = new RoundRectangle2D.Double(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), LGNV_borderRadius - 2, LGNV_borderRadius + 2);
                g2.draw(s);
            }// phần tạo shawdown
        }

        
        
        
        if (LGNV_gradientBackgroundColor == null) {
            g2.setColor(color());
        }
        if (LGNV_gradientBackgroundColor != null) {
            GradientPaint paint = new GradientPaint(1, 1, getBackground(), getWidth() - 2, getHeight() - 2, LGNV_gradientBackgroundColor);
            g2.setPaint(paint);
        }
        

        if (LGNV_backgroundAllow) {
            g2.fill(s);
        }

        if (LGNV_lineGradientColor == null) {
            g2.setColor(isEnabled() ? LGNV_lineColor : new Color(204, 204, 204));
        } else {
            GradientPaint paint = new GradientPaint(1, 1, LGNV_lineColor, getWidth() - 2, getHeight() - 2, LGNV_lineGradientColor);
            g2.setPaint(paint);
        }

        if(LGNV_lineAllow){
            g2.draw(s);
        }

        super.paintComponent(g);
    }

   

    private Color color() {

        if (!isEnabled()) {
            return new Color(204, 204, 204);
        }

        Color temp = getBackground();
        if (pressed) {
            return LGNV_pressedColor;
        }
        if (entered) {
            return LGNV_enteredColor;
        }

        return temp;

    }

    public boolean isLGNV_backgroundAllow() {
        return LGNV_backgroundAllow;
    }

    public void setLGNV_backgroundAllow(boolean LGNV_backgroundAllow) {
        this.LGNV_backgroundAllow = LGNV_backgroundAllow;
    }

    public Color getLGNV_gradientBackgroundColor() {
        return LGNV_gradientBackgroundColor;
    }

    public void setLGNV_gradientBackgroundColor(Color LGNV_gradientBackgroundColor) {
        this.LGNV_gradientBackgroundColor = LGNV_gradientBackgroundColor;
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

    public Color getLGNV_lineGradientColor() {
        return LGNV_lineGradientColor;
    }

    public void setLGNV_lineGradientColor(Color LGNV_lineGradientColor) {
        this.LGNV_lineGradientColor = LGNV_lineGradientColor;
    }

    public int getLGNV_borderRadius() {
        return LGNV_borderRadius;
    }

    public void setLGNV_borderRadius(int LGNV_borderRadius) {
        this.LGNV_borderRadius = LGNV_borderRadius;
    }

    public boolean isLGNV_ShadowAllow() {
        return LGNV_ShadowAllow;
    }

    public void setLGNV_ShadowAllow(boolean LGNV_ShadowAllow) {
        this.LGNV_ShadowAllow = LGNV_ShadowAllow;
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

    public boolean isLGNV_hoverAllow() {
        return LGNV_hoverAllow;
    }

    public void setLGNV_hoverAllow(boolean LGNV_hoverAllow) {
        this.LGNV_hoverAllow = LGNV_hoverAllow;
    }

    public Color getLGNV_enteredColor() {
        return LGNV_enteredColor;
    }

    public void setLGNV_enteredColor(Color LGNV_enteredColor) {
        this.LGNV_enteredColor = LGNV_enteredColor;
    }

    public Color getLGNV_pressedColor() {
        return LGNV_pressedColor;
    }

    public void setLGNV_pressedColor(Color LGNV_pressedColor) {
        this.LGNV_pressedColor = LGNV_pressedColor;
    }

    
    
    
    
    
    
    
    
    
    
    

    

    ////////////////////////////
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (LGNV_hoverAllow) {
            entered = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (LGNV_hoverAllow) {
            entered = false;
        }
    }

}
