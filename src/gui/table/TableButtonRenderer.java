/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.table;

import db.DBManager;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import gui.Toast;
import java.awt.Dimension;
import user.UserData;

/**
 *
 * @author Curious
 */
public class TableButtonRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, MouseListener {
    String toCopy = "";
    int row, col;
    private JTable table;
    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    DBManager db;
    UserData ud;
    char [] chars;
    Dimension screenSize;

    /* Constructor needs to have db and ud for table button's functionality*/
    public TableButtonRenderer(JTable table, int column, DBManager db, UserData ud){
        this.table = table;
        this.db = db;
        this.ud = ud;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted( false );
        editButton.addMouseListener(this);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
        table.addMouseListener( this );
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,int row, int col){
        editButton.setBackground(new Color(222,225,0));
        
        this.table = table;
        this.row = row;
        this.col = col;
        
        editButton.setText("Copy");
        return editButton;
    }
    
    @Override
    public Object getCellEditorValue(){ return editorValue;}
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderButton.setBackground(new Color(222,205,118));
        
        this.table = table;
        this.row = row;
        this.col = column;
        
        renderButton.setText("Copy");
        return renderButton;
    }
    
    
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
    public void mouseClicked(MouseEvent e) {// toCopy - generateString
//        System.out.println("Button pressed! "+row);
        toCopy = ud.getPre()+" ";
        toCopy += ud.getShort() == 1 ? ud.getGB() : db.getLast_gb(ud.getGB());
        chars = String.valueOf(ud.getPos()).toCharArray();
        for(int i=7, j = 0; i>=3 ; i--, j++){
            if (chars[j] == '1')
                toCopy += " P"+(i-2)+"("+Integer.parseInt(table.getValueAt(row, i).toString())+")";
        }
        /* Copies the string straight to clipboard so user can instantly use Ctrl+V to paste it */
        StringSelection stringSelection = new StringSelection(toCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        
        /* Update db and inform user on status */
        db.update_gb_lvl(ud.getGB(), row+1);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Toast toast = new Toast("String has been generated for level "+(row+1)+"!", screenSize.width/2, (screenSize.height *3)/4);
        toast.showtoast();
    }
        
    

    
}
