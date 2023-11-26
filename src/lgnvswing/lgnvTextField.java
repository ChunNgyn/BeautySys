package lgnvswing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.TransferHandler;
import javax.swing.border.AbstractBorder;

public class lgnvTextField extends JPasswordField {

    // validation
    public enum textTypeEnum {
        NORMAL,
        NUMBER_FORMAT,
        NUMBER,
        ID,
        PASSWORD
    }

    textTypeEnum LGNV_textType;
    private int LGNV_maxCharLen;

    // background
    private Color LGNV_BgColor;

    // border
    private boolean LGNV_allow_drawborder;
    private int LGNV_radius;
    private Color LGNV_borderColor;

    // icon
    private Icon LGNV_iconLeft;
    private Icon LGNV_iconRight;

    private int LGNV_iconLeftPadding;
    private int LGNV_iconRightPadding;

    // placeholderText
    private String LGNV_placeholderText;
    private Font LGNV_placeholderFont;
    private Color LGNV_placeholderForeGround;

    // validation
    private String LGNV_regexReplaceNumber = "[^\\d]";
    private String LGNV_regexReplaceID = "[^a-zA-Z0-9]+";

    // error
    private boolean LGNV_error;
    private Icon LGNV_errorIcon;
    private Color LGNV_errorBackground;
    private Color LGNV_errorBorder;

    // password char
    private char LGNV_passwordChar;
    
    //box-shadow
    
    private boolean LGNV_ShaDownAllow = false;
    private int LGNV_ShadownOpactity = 40;
    private int LGNV_ShadownPixel = 15;
    private int LGNV_ShadownColor = 0;

    
    

   

    

    // private
    private int LGNV_textPaddingL;
    private int LGNV_textPaddingR;
    
    

    public textTypeEnum gets_textType() {
        return LGNV_textType;
    }

    public void sets_textType(textTypeEnum textType) {
        if (textType == textTypeEnum.PASSWORD) {
            setEchoChar(LGNV_passwordChar);
        } else {
            setEchoChar((char) 0);
        }

        this.LGNV_textType = textType;
    }

    public textTypeEnum getLGNV_textType() {
        return LGNV_textType;
    }

    public void setLGNV_textType(textTypeEnum LGNV_textType) {
        this.LGNV_textType = LGNV_textType;
    }

    public int getLGNV_maxCharLen() {
        return LGNV_maxCharLen;
    }

    public void setLGNV_maxCharLen(int LGNV_maxCharLen) {
        this.LGNV_maxCharLen = LGNV_maxCharLen;
    }

    public Color getLGNV_BgColor() {
        return LGNV_BgColor;
    }

    public void setLGNV_BgColor(Color LGNV_BgColor) {
        this.LGNV_BgColor = LGNV_BgColor;
    }

    public boolean isLGNV_allow_drawborder() {
        return LGNV_allow_drawborder;
    }

    public void setLGNV_allow_drawborder(boolean LGNV_allow_drawborder) {
        this.LGNV_allow_drawborder = LGNV_allow_drawborder;
    }

    public int getLGNV_radius() {
        return LGNV_radius;
    }

    public void setLGNV_radius(int LGNV_radius) {
        this.LGNV_radius = LGNV_radius;
    }

    public Color getLGNV_borderColor() {
        return LGNV_borderColor;
    }

    public void setLGNV_borderColor(Color LGNV_borderColor) {
        this.LGNV_borderColor = LGNV_borderColor;
    }

    public Icon getLGNV_iconLeft() {
        return LGNV_iconLeft;
    }

    public void setLGNV_iconLeft(Icon LGNV_iconLeft) {
        this.LGNV_iconLeft = LGNV_iconLeft;
    }

    public Icon getLGNV_iconRight() {
        return LGNV_iconRight;
    }

    public void setLGNV_iconRight(Icon LGNV_iconRight) {
        this.LGNV_iconRight = LGNV_iconRight;
    }

    public int getLGNV_iconLeftPadding() {
        return LGNV_iconLeftPadding;
    }

    public void setLGNV_iconLeftPadding(int LGNV_iconLeftPadding) {
        this.LGNV_iconLeftPadding = LGNV_iconLeftPadding;
    }

    public int getLGNV_iconRightPadding() {
        return LGNV_iconRightPadding;
    }

    public void setLGNV_iconRightPadding(int LGNV_iconRightPadding) {
        this.LGNV_iconRightPadding = LGNV_iconRightPadding;
    }

    public String getLGNV_placeholderText() {
        return LGNV_placeholderText;
    }

    public void setLGNV_placeholderText(String LGNV_placeholderText) {
        this.LGNV_placeholderText = LGNV_placeholderText;
    }

    public Font getLGNV_placeholderFont() {
        return LGNV_placeholderFont;
    }

    public void setLGNV_placeholderFont(Font LGNV_placeholderFont) {
        this.LGNV_placeholderFont = LGNV_placeholderFont;
    }

    public Color getLGNV_placeholderForeGround() {
        return LGNV_placeholderForeGround;
    }

    public void setLGNV_placeholderForeGround(Color LGNV_placeholderForeGround) {
        this.LGNV_placeholderForeGround = LGNV_placeholderForeGround;
    }

    

    public boolean isLGNV_error() {
        return LGNV_error;
    }

    public void setLGNV_error(boolean LGNV_error) {
        this.LGNV_error = LGNV_error;
    }

    public Icon getLGNV_errorIcon() {
        return LGNV_errorIcon;
    }

    public void setLGNV_errorIcon(Icon LGNV_errorIcon) {
        this.LGNV_errorIcon = LGNV_errorIcon;
    }

    public Color getLGNV_errorBackground() {
        return LGNV_errorBackground;
    }

    public void setLGNV_errorBackground(Color LGNV_errorBackground) {
        this.LGNV_errorBackground = LGNV_errorBackground;
    }

    public Color getLGNV_errorBorder() {
        return LGNV_errorBorder;
    }

    public void setLGNV_errorBorder(Color LGNV_errorBorder) {
        this.LGNV_errorBorder = LGNV_errorBorder;
    }

    

    public int getLGNV_textPaddingL() {
        return LGNV_textPaddingL;
    }

    public void setLGNV_textPaddingL(int LGNV_textPaddingL) {
        this.LGNV_textPaddingL = LGNV_textPaddingL;
    }

    public int getLGNV_textPaddingR() {
        return LGNV_textPaddingR;
    }

    public void setLGNV_textPaddingR(int LGNV_textPaddingR) {
        this.LGNV_textPaddingR = LGNV_textPaddingR;
    }

    public boolean isLGNV_ShaDownAllow() {
        return LGNV_ShaDownAllow;
    }

    public void setLGNV_ShaDownAllow(boolean LGNV_ShaDownAllow) {
        this.LGNV_ShaDownAllow = LGNV_ShaDownAllow;
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

    
    
    
    
    
    

    

    public String getTextValue() {
        String value = String.valueOf(getPassword());

        if (LGNV_textType == textTypeEnum.NUMBER || LGNV_textType == textTypeEnum.NUMBER_FORMAT) {
            value = value.replaceAll(LGNV_regexReplaceNumber, ""); 
        }

        return value;
    }

    public lgnvTextField() {
        super();

        setDefaultValue();

        addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                if (!checkKeyControls(e)) {
                    textChangeEvent();
                }
            }

            public void keyTyped(KeyEvent e) {
                if (!checkKeyControls(e)) {
                    textChangeEvent();
                }
            }

            public void keyReleased(KeyEvent e) {
                if (!checkKeyControls(e)) {
                    textChangeEvent();
                }
            }
        });

    }

    private void setDefaultValue() {

        // background
        LGNV_BgColor = Color.white;

        // border
        LGNV_allow_drawborder = true;
        LGNV_radius = 30;
        LGNV_borderColor = new Color(102, 102, 102);

        // icon
        LGNV_iconLeftPadding = 5;
        LGNV_iconRightPadding = 5;

        // placeholderText
        LGNV_placeholderFont = new Font("Tahoma", 2, 12);
        LGNV_placeholderForeGround = new Color(102, 102, 102);

        // validation
        LGNV_textType = textTypeEnum.NORMAL;
        LGNV_maxCharLen = 4000;

        // password
        LGNV_passwordChar = '•';
        setEchoChar((char) 0);

        // error
        LGNV_error = false;
        LGNV_errorBackground = new Color(25, 25, 25);
        LGNV_errorBorder = new Color(25, 25, 25);

        // default size
        setPreferredSize(new Dimension(200, 30));

        
        

       
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(new java.awt.Color(255, 255, 255, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {

            //padding text
            LGNV_textPaddingL = LGNV_iconLeft != null ? LGNV_iconLeft.getIconWidth() + LGNV_iconLeftPadding + 5 : 10;
            LGNV_textPaddingR = LGNV_iconRight != null ? LGNV_iconRight.getIconWidth() + LGNV_iconRightPadding + 5 : 5;
            setBorder(new RoundedCornerBorderTextBox(LGNV_textPaddingL, LGNV_textPaddingR));

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape border = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, LGNV_radius, LGNV_radius);
            
            Area corner = new Area(new Rectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1));
            corner.intersect(new Area(border));
            
            //boxshadow
            
            if (LGNV_ShaDownAllow) {
                for (int i = 0; i < LGNV_ShadownPixel; i++) {
                    g2.setColor(new Color(LGNV_ShadownColor, LGNV_ShadownColor, LGNV_ShadownColor, ((LGNV_ShadownOpactity / 20) * i)));
                    border = new RoundRectangle2D.Double(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), LGNV_radius - 2, LGNV_radius + 2);
                    g2.draw(border);
                }// phần tạo shawdown
                
                g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                border = new RoundRectangle2D.Double(LGNV_ShadownPixel / 2, LGNV_ShadownPixel / 2, getWidth() - LGNV_ShadownPixel, getHeight() - LGNV_ShadownPixel, LGNV_radius - 2, LGNV_radius + 2);
                corner = new Area(new Rectangle2D.Double(LGNV_ShadownPixel / 2, LGNV_ShadownPixel / 2, getWidth() - LGNV_ShadownPixel, getHeight() - LGNV_ShadownPixel));
                
                
                
                corner.intersect(new Area(border));
            }
            
            
            
            
            
            
            
            
            
            // background
            if (LGNV_error) {
                g2.setPaint(LGNV_errorBackground);
            } else {
                g2.setPaint(LGNV_BgColor);
            }

            
            g2.fill(corner);

            // Icon
            if (LGNV_iconLeft != null) {
                ImageIcon imgIconLeft = (ImageIcon) LGNV_iconLeft;
                final Image img = imgIconLeft.getImage();

                int yPosition = getHeight() / 2 - img.getHeight(null) / 2;

                g2.drawImage(img, LGNV_iconLeftPadding, yPosition, imgIconLeft.getIconWidth(), imgIconLeft.getIconHeight(), null);

            }

            // icon
            ImageIcon imgIcon = null;

            if (LGNV_error) {
                if (LGNV_errorIcon != null) {
                    imgIcon = (ImageIcon) LGNV_errorIcon;
                }
            } else {
                if (LGNV_iconRight != null) {
                    imgIcon = (ImageIcon) LGNV_iconRight;
                }
            }

            if (imgIcon != null) {
                final Image img = imgIcon.getImage();

                int yPosition = getHeight() / 2 - img.getHeight(null) / 2;
                int xPosition = getWidth() - img.getWidth(null) - LGNV_iconRightPadding;

                g2.drawImage(img, xPosition, yPosition, imgIcon.getIconWidth(), imgIcon.getIconHeight(), null);
            }

            if (checkPlaceholder()) {

                g2.setFont(LGNV_placeholderFont);
                g2.setPaint(LGNV_placeholderForeGround);
                FontMetrics fm = g.getFontMetrics();
                int msgAscent = fm.getAscent();

                int msgY = getHeight() / 2 + msgAscent / 2;
                g2.drawString(LGNV_placeholderText, LGNV_textPaddingL, msgY);
            }

            // border
            if (LGNV_allow_drawborder) {
                if (LGNV_error) {
                    g2.setPaint(LGNV_errorBorder);
                } else {
                    g2.setPaint(LGNV_borderColor);
                }
                g2.draw(border);
            }

            g2.dispose();
        }
        super.paintComponent(g);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
        setBorder(new RoundedCornerBorderTextBox());
    }

    int a = 0;

    // validation
    private void checkTextLength() {
        String text = getTextValue();
        if (text.length() > LGNV_maxCharLen) {
            text = text.substring(0, LGNV_maxCharLen);
        }
        setText(text);
    }

    private void textChangeEvent() {

        if (LGNV_textType != textTypeEnum.NUMBER_FORMAT) {
            checkTextLength();
        }

        switch (LGNV_textType) {
            case NUMBER_FORMAT: {
                textNumberFormat();
                break;
            }

            case NUMBER: {
                textNumber();
                break;
            }
            case ID: {
                textID();
                break;
            }
        }
    }
    
    

    private boolean checkPlaceholder() {
        return LGNV_placeholderText != "" && LGNV_placeholderText != null && (getTextValue().isBlank());
    }

    private void textNumberFormat() {

        DecimalFormat formatter = new DecimalFormat("#,###.##");

        String text = getTextValue();
        text = regexNumberText(text);

        // check text length
        if (text.length() > LGNV_maxCharLen) {
            text = text.substring(0, LGNV_maxCharLen);
        }

        if (text == null || text == "" || text.isEmpty()) {
            return;
        }

        long number = Long.parseLong(text);
        text = formatter.format(number);

        setText(text);

    }

    private void textNumber() {

        String text = getTextValue();
        setText(regexNumberText(text));
    }

    private String regexNumberText(String text) {
        return text.replaceAll(LGNV_regexReplaceNumber, "");
    }

    

    

    private void textID() {
        String text = getTextValue();
        text = text.replaceAll(LGNV_regexReplaceID, "");
        setText(text);
    }

    private boolean checkKeyControls(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_BACK_SPACE;
    }
    
    class RoundedCornerBorderTextBox extends AbstractBorder {

    private int paddingLeft = 10;
    private int paddingRight = 5;

    public RoundedCornerBorderTextBox() {

    }

    public RoundedCornerBorderTextBox(int paddingLeft, int paddingRight) {
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
    }
    
    
    
    
    

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, paddingLeft, 4, paddingRight);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, paddingLeft, 4, paddingRight);
        return insets;
    }
    
    
    
}
    
}


