package alma.atarigo.ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steg
 */
public class GKit{

    public static BufferedImage loadImage(String imageUrl) throws IOException{
            if(imageUrl==null){
                throw new FileNotFoundException("No such resource: "+imageUrl);
            }
            InputStream imageStream = ClassLoader.getSystemClassLoader().getResourceAsStream(imageUrl);
            if(imageStream==null){
                throw new FileNotFoundException("No such resource: "+imageUrl);
            }
            return ImageIO.read(imageStream);
    }

    public static ImageIcon loadIcon(String iconUrl) {
        try {
            return new ImageIcon(loadImage(iconUrl));
        } catch (IOException ex) {
            Logger.getLogger(GKit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ImageIcon loadIcon(String iconUrl,int width,int height){
        try {
            return new ImageIcon(loadImage(iconUrl).getScaledInstance(width, height,BufferedImage.SCALE_REPLICATE));
        } catch (Exception ex) {
            Logger.getLogger(GKit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static JButton doButton(String url,String alt,String toolTipText,ActionListener action)
    {
        JButton button = new JButton();
        button.setToolTipText(toolTipText);

        //chargement de l'icon
        Icon icon = url==null?null:loadIcon(url,32,32);
        if(icon==null) {
            button.setText(alt);
        }else{
            button.setIcon(icon);
        }

        if(action!=null) {
            button.addActionListener(action);
        }

        return button;
    }

    public static JButton doButton(String iconUrl ,String iconText,String tooltipText,String actionCommand){
        return doButton(iconUrl,iconText,tooltipText,ActionHandler.newActionListener(actionCommand));
    }

    public static JMenuItem doItem(String alt,ActionListener action,KeyStroke stroke)
    {
        JMenuItem button = new JMenuItem(alt);

        if(stroke!=null) {
            button.setAccelerator(stroke);
        }

        if(action!=null) {
            button.addActionListener(action);
        }

        return button;
    }

    public static JMenuItem doItem(String alt,String actionCommand,KeyStroke stroke){
        JMenuItem button = new JMenuItem(alt);

        if(stroke!=null) {
            button.setAccelerator(stroke);
        }

        ActionListener action = ActionHandler.newActionListener(actionCommand);
        if(action!=null) {
            button.addActionListener(action);
        }

        return button;
    }

    public static JMenuItem doItem(String alt,ActionListener action)
    {
        return doItem(alt,action,null);
    }

    public static JMenuItem doItem(String alt,String actionCommand){
        return doItem(alt,actionCommand,null);
    }
    
    public static void error(Component parent,String message) {
        JOptionPane.showMessageDialog(parent,message,"Erreur",JOptionPane.ERROR_MESSAGE);
    }

    public static void error(String message){
        error(MainWindow.getWindow(),message);
    }
	

    public static void warning(Component parent,String message) {
        JOptionPane.showMessageDialog(parent,message,"Attention",JOptionPane.WARNING_MESSAGE);
    }

    public static void warning(String message){
        warning(MainWindow.getWindow(),message);
    }

    public static void info(Component parent,String message) {
        JOptionPane.showMessageDialog(parent,message,"Information",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void info(String message){
        info(MainWindow.getWindow(),message);
    }

    public static void popup(JDialog dialog){
        popup(MainWindow.getWindow(),dialog);
    }

    public static void popup(Component parent,final JDialog dialog){
        if(dialog!=null){
            dialog.setLocationRelativeTo((parent instanceof JDialog)?parent:MainWindow.getWindow());
            dialog.addComponentListener(new ComponentListener() {
                public void componentResized(ComponentEvent e) {}
                public void componentMoved(ComponentEvent e) {}
                public void componentShown(ComponentEvent e) {dialog.toFront();}
                public void componentHidden(ComponentEvent e) {}
            });
            dialog.setVisible(true);
        }
    }

    public static void drawLine(Graphics2D g2,int x1,int y1,int x2,int y2,Stroke stroke,Object color){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
        Stroke savedStroke = g2.getStroke();
        g2.setStroke(stroke);
        g2.drawLine(x1, y1, x2, y2);      
        g2.setStroke(savedStroke);
        
        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }
    }
    
    public static void drawRect(Graphics2D g2,int x,int y,int w,int h,Stroke stroke,Object color){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
        Stroke savedStroke = g2.getStroke();
        if(stroke!=null){
        	g2.setStroke(stroke);
        }
        g2.drawRect(x, y, w, h);
        g2.setStroke(savedStroke);

        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }        
    }


    public static void drawOval(Graphics2D g2,int x,int y,int w,int h,Stroke stroke,Object color){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
        Stroke savedStroke = g2.getStroke();
        if(stroke!=null){
        	g2.setStroke(stroke);
        }
        g2.drawOval(x, y, w, h);
        g2.setStroke(savedStroke);

        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }
    }
    
    public static void fillRect(Graphics2D g2,int x,int y,int w,int h,Stroke stroke,Object color){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
        Stroke savedStroke = g2.getStroke();
        if(stroke!=null){
        	g2.setStroke(stroke);
        }
        g2.fillRect(x, y, w, h);
        g2.setStroke(savedStroke);

        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }
    }

    public static void fillOval(Graphics2D g2,int x,int y,int w,int h,Stroke stroke,Object color){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
        Stroke savedStroke = g2.getStroke();
        if(stroke!=null){
        	g2.setStroke(stroke);
        }
        g2.fillOval(x, y, w, h);
        g2.setStroke(savedStroke);

        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }
    }

    public static void fillRect(Graphics2D g2,int x,int y,int w,int h,Object color){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
        g2.fillRect(x, y, w, h);

        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }
        
    }

    public static void fillOval(Graphics2D g2,int x,int y,int w,int h,Object color){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
        g2.fillOval(x, y, w, h);

        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }
    }

    public static void fillTriangle(Graphics2D g2,int x,int y,int w,int h,Object color,Stroke stroke){
    	Object save = null;    	
    	if(color!=null){
    		if(color instanceof Color){
    			save = g2.getColor();
    			g2.setColor((Color)color);
    		}
    		else if(color instanceof Paint){
    			save = g2.getPaint();
    			g2.setPaint((Paint)color);
    		}
    	}
    	
    	Stroke saveStroke = g2.getStroke();
    	
    	if(stroke!=null){
    		g2.setStroke(stroke);
    	}

    	int[] xP = { x,x+w/2,x+w };
    	int[] yP = {y+h,y,y+h };
    	
    	g2.fillPolygon(xP, yP, 3);    	
    	g2.setStroke(saveStroke);
    	
        if(color!=null){
    		if(color instanceof Color){
    			g2.setColor((Color)save);
    		}
    		else if(color instanceof Paint){
    			g2.setPaint((Paint)color);
    		}
        }
    }
}
