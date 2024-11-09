/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import objects.GB;

/**
 *
 * @author Curious
 */
public class GB_Renderer extends DefaultListCellRenderer{

    GB gb;
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        gb = (GB) value;
        switch(gb.getAge()){// set backgroundColor for elements in gbList(GUI)
            case "bronze": this.setBackground(new Color(180,139,19));break;
            case "iron": this.setBackground(new Color(154,71,33));break;
            case "ema": this.setBackground(new Color(80,131,68));break;
            case "hma": this.setBackground(new Color(48,142,142));break;
            case "lma": this.setBackground(new Color(128,67,137));break;
            case "colonial": this.setBackground(new Color(205,100,4));break;
            case "industrial": this.setBackground(new Color(166,42,38));break;
            case "progressive": this.setBackground(new Color(182,154,97));break;
            case "modern": this.setBackground(new Color(94,162,232));break;
            case "post_modern": this.setBackground(new Color(166,177,166));break;
            case "contemporary": this.setBackground(new Color(254,142,106));break;
            case "tomorrow": this.setBackground(new Color(64,67,72));break;
            case "future": this.setBackground(new Color(148,193,103));break;
            case "arctic_future": this.setBackground(new Color(255,255,255));break;
            case "oceanic_future": this.setBackground(new Color(45,145,118));break;
            case "virtual_future": this.setBackground(new Color(85,58,139));break;
            case "sa_mars": this.setBackground(new Color(187,101,61));break;
            case "sa_asteroid_belt": this.setBackground(new Color(102,0,102));break;
            case "sa_jupiter_moon": this.setBackground(new Color(46,103,107));break;
            case "sa_venus": this.setBackground(new Color(221,197,0));break;
            case "sa_titan": this.setBackground(new Color(37,63,53));break;
            case "sa_space_hub": this.setBackground(new Color(128,0,0));break;
            default: this.setBackground(new Color(136,110,66)); break;
        }
        if(gb.getAge().equals("arctic_future")) setForeground(Color.black);
        else setForeground(Color.white);
        
        setText(agePrefix(gb.getAge()) +gb.getName().replaceAll("_", " "));
        return this;
    }
    
    String agePrefix(String age){ //some string extras to more easily navigate in gbList
        switch(age){
            case "bronze": return "[BA] ";
            case "iron": return "[IA] ";
            case "ema": return "[EMA] ";
            case "hma": return "[HMA] ";
            case "lma": return "[LMA] ";
            case "colonial": return "[CA] ";
            case "industrial": return "[INA] ";
            case "progressive": return "[PE] ";
            case "modern": return "[ME] ";
            case "post_modern": return "[PME] ";
            case "contemporary": return "[CE] ";
            case "tomorrow": return "[TE] ";
            case "future": return "[FE] ";
            case "arctic_future": return "[AF] ";
            case "oceanic_future": return "[OF] ";
            case "virtual_future": return "[VF] ";
            case "sa_mars": return "[SAM] ";
            case "sa_asteroid_belt": return "[SAAB] ";
            case "sa_jupiter_moon": return "[SAJM] ";
            case "sa_venus": return "[SAV] ";
            case "sa_titan": return "[SAT] ";
            case "sa_space_hub": return "[SASH] ";
            default: return "[NoAge] ";
        }
    }
    
    
}
