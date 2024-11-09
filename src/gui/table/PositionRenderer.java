/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Curious
 */
public class PositionRenderer extends JPanel implements TableCellRenderer {
    JLabel pos = new JLabel(), con = new JLabel();
    int secure, total, contributed;
    GridBagConstraints gbc = new GridBagConstraints();
    
    
    public PositionRenderer(){
        this.setLayout(new GridBagLayout());
        setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(isSelected) setBackground(table.getSelectionBackground());
        else setBackground(Color.white);
        getContributed(table, Integer.parseInt(value.toString()), row, column);
        pos.setText(String.valueOf(value));
        con.setText(String.valueOf(secure));
        
        if(secure > 0){con.setText("+"+con.getText());  con.setForeground(Color.red);}
        else {con.setText("-"+con.getText()); con.setForeground(new Color(25,186,68)); }
        con.setFont(new Font("Arial", Font.BOLD, 9));
        
        setPanel();
        return this;
    }
    
    void getContributed(JTable table, int value, int row, int col){//if P5 then take in account Total - P1,P2,P3,P4
        total = Integer.parseInt(table.getValueAt(row, 2).toString());
        if(col > 3){
            contributed = 0;
            for(int i=3; i < col; i++)
                contributed += Integer.parseInt(table.getValueAt(row, i).toString());
            secure = total - contributed - 2*value;
        }
        else secure = total - Integer.parseInt(table.getValueAt(row, col).toString())*2;
//        System.out.printf("[%d,%d][%d] %d = %d - %d \n ",row, col, value, secure, total, contributed);
    }
    
    void setPanel(){
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        this.add(pos, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        this.add(con, gbc);
    }
    
}
