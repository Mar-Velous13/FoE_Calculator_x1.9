/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Curious
 */
public class Guide extends JDialog {
    
    JButton b;
    JLabel img;
    JPanel p;
    
    public Guide(JFrame f){
        super(f, "Application's Guide", true);
        setLayout(new BorderLayout());
        setSize(630,665);
        setVisible(false);
        
        try{img = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/gb_images/guide2.png"))));}
        catch(IOException ex){ex.printStackTrace();}
        
        p = new JPanel(new BorderLayout());
        b = new JButton("Ok");
        p.add(b, BorderLayout.CENTER);
        b.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                setVisible(false);
            }
        });
        
        add(img, BorderLayout.CENTER);
        add(p, BorderLayout.SOUTH);
    }
    
    public void showGuide(){setVisible(true);}
    
}
