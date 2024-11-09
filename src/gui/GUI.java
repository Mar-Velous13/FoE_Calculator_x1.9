/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import gui.table.TableButtonRenderer;
import db.DBManager;
import gui.table.PositionRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import objects.GB;
import user.UserData;

/**
 *
 * @author Curious
 */
public final class GUI {
    
    JFrame f;
    JPanel p, sidePan, subSidePan1, subSidePan2, bottom, sett_main, subBottom;
    JPanel []  subSidePan2_2;
    String temp;
    JScrollPane scrollPane;
    JTable table;
    DefaultTableModel dtm;
    String [] header = new String[]{"Button", "LvL", "Total","P1", "P2", "P3", "P4", "P5"}, 
            textData = new String[]{"","","P5()","P4()","P3()","P2()","P1()"};
    JCheckBox [] checks = new JCheckBox[6];
    DBManager db;
    BufferedImage bimg;
    JLabel img_gb, img_logo, author;
    JComboBox gbList;
    Image newImage;
    GridBagConstraints gbc = new GridBagConstraints(), gbc2 = new GridBagConstraints(), gbc3 = new GridBagConstraints();
    GB gb;
    GB [] gbs;
    JTextField prefix, outcome;
    UserData ud;
    char [] chars;
    JButton info, quest;
    Guide guide;
    
    public GUI(){
        db = new DBManager();
        ud = db.getUserData();
        gbs = db.readGBS();
        initSwingComponents();
    }
    
    void initSwingComponents(){
        setupFrame();
        p = new JPanel(new BorderLayout());
        bottom = new JPanel(new BorderLayout());
        subBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        //Sine Panels and components
        sidePan = new JPanel(new GridBagLayout());
        subSidePan1 = new JPanel(new GridBagLayout());
        subSidePan2 = new JPanel();
        subSidePan2_2 = new JPanel[2];
        
        img_gb = new JLabel("");
        img_logo = new JLabel("");
        info = new JButton();
        quest = new JButton();
        guide = new Guide(f);
        author = new JLabel("Made by Mar");
        
        gbList = new JComboBox();
        setupTable();
        
        for(int i=0; i < checks.length; i++){
            checks[i] = new JCheckBox();
            checks[i].setName(i+"");
        }
            
        subSidePan2_2[0] = new JPanel(new FlowLayout());
        subSidePan2_2[1] = new JPanel(new GridBagLayout());
        prefix = new JTextField(); prefix.setPreferredSize(new Dimension(200,25));
        outcome = new JTextField(); outcome.setPreferredSize(new Dimension(200,25));
        
        setupPanels();
        f.add(p);
        f.setResizable(true);
        f.setVisible(true);
    }
    
    /*  ===============================  Basic Swing Structure ==============================================
        JFrame
            1) JPanel p
                1.1)JScrollPane
                    1.1.1) JTable
                1.2) SidePanel
                    1.2.1) SubSidePanel1 - GB list and GB icon
                    1.2.2) SubSidePanel2 - checkboxes and textFields
                        1.2.2.x) Extra Panels for checkboxes to use LayoutManagers on
                    1.2.3) Forge of Empires LOGO
                1.3) Bottom JPanel
                    1.3.1) SubBottom with INFO and QUESTION buttons (also used for LayoutManager)
    
    
    
    */
    
    void setupFrame(){
        f = new JFrame(" Forge of Empires x1.9 GB Calculator ");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(900,650));
        f.setSize(900, 650);
    }
    
    void setupSidePanels(){
        sidePan.setBorder(new LineBorder(Color.black,1));
        setup_side_panel_one();
        setup_side_panel_two();
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        
        sidePan.add(subSidePan1, gbc); gbc.gridy = 1;
        sidePan.add(subSidePan2, gbc); gbc.gridy = 2;
        sidePan.add(img_logo, gbc); gbc.gridy = 2;
    }
    
    void setupPanels(){
        setupSidePanels();
        info.setIcon(new ImageIcon(adjustImage("icon_info.png", 15,15)));
        quest.setIcon(new ImageIcon(adjustImage("icon_quest.png", 15,15)));
        info.setBorder(null);
        quest.setBorder(null);
        info.setBackground(subBottom.getBackground());
        quest.setBackground(subBottom.getBackground());
        info.addActionListener(bottom_panel_info_function());
        quest.addActionListener(bottom_panel_quest_function());
        
        
        subBottom.add(author);
        subBottom.add(info);
        subBottom.add(quest);
        bottom.add(subBottom, BorderLayout.EAST);
        p.add(sidePan, BorderLayout.WEST);
        p.add(scrollPane, BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);
        
    }
    
    /**  Used for loading and adjusting image size to the application
     @param x - image target width
     @param y - image target height
     @param src - imgSource
     */
    Image adjustImage(String src, int x, int y){
        try{
            bimg = ImageIO.read(getClass().getClassLoader().getResource("gb_images/"+src));
            newImage = bimg.getScaledInstance(x , y, Image.SCALE_DEFAULT);}
        catch(IOException ex){}
        return newImage;
    }

    
    void setup_side_panel_one(){//GB icons and GB list
        img_gb = new JLabel(new ImageIcon(adjustImage(db.getLast_gb_image(ud.getGB()), 200, 200))); img_gb.setBorder(new LineBorder(Color.black,1));
        img_logo = new JLabel(new ImageIcon(adjustImage("logo3_2.png", 300, 200)));

        gbList.setModel(new DefaultComboBoxModel<>(gbs));
        gbList.setSelectedItem(getLastGB());
        gbList.setRenderer(new GB_Renderer());
        gbList.addItemListener(gb_list_function());

        gbc2.fill = GridBagConstraints.HORIZONTAL; gbc2.gridheight = 3;
        gbc2.gridx = 0; gbc2.gridy = 0; 
        subSidePan1.add(img_gb, gbc2);
        
        gbc2.gridy = 4; gbc2.gridheight = 1;
        subSidePan1.add(gbList, gbc2);


    }
    
    void setupTable(){//seting up table and loading data
        dtm = new DefaultTableModel(0,0);
        dtm.setColumnIdentifiers(header);
        db.read(getLastGB().getAge(),dtm);//populate table
        table = new JTable(dtm){//make cells uneditable
            @Override
            public boolean isCellEditable(int row, int column) { return false; };
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumn("P1").setCellRenderer(new PositionRenderer());
        table.getColumn("P2").setCellRenderer(new PositionRenderer());
        table.getColumn("P3").setCellRenderer(new PositionRenderer());
        table.getColumn("P4").setCellRenderer(new PositionRenderer());
        table.getColumn("P5").setCellRenderer(new PositionRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.setRowHeight(25);
        
        TableButtonRenderer tbr = new TableButtonRenderer(table, 0, db, ud);//first column as buttons
        
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollTableToRow();
        
    }
    
    void updateTable(String age){//change GB data
        dtm.setRowCount(0);
        db.read(age,dtm);
    }
    
    void scrollTableToRow(){//navigate user to the last used LVL for GB in question
        table.getSelectionModel().setSelectionInterval(db.get_gb_lvl(ud.getGB())-1,db.get_gb_lvl(ud.getGB())-1);
        table.scrollRectToVisible(new Rectangle(table.getCellRect(db.get_gb_lvl(ud.getGB())-1, 0, false)));
    }
    
    void setup_side_panel_two(){// checkboxes and textfields
        loadUserDataValues();
        checks[0].setText("Use GB short string");
        checks[0].setSelected(ud.getShort() == 1);
        checks[0].addActionListener(check_box_function(0));
        gbc3.gridx = 0; gbc3.gridy = 0; gbc3.fill = GridBagConstraints.HORIZONTAL;gbc3.gridheight = 1; gbc3.gridwidth = 2;
        subSidePan2_2[1].add(checks[0], gbc3);

        for(int i=1, j=5; i < checks.length; i++, j--){
            checks[i].setText("P"+j);
            checks[i].setSelected(ud.getPosChar(i-1) == '1');
            checks[i].addActionListener(check_box_function(i));
            textData[i+1] = ud.getPosChar(i-1) == '1' ? "P"+j+"()" : "";
            subSidePan2_2[0].add(checks[i]);
        }
        
        textData[0] = ud.getPre();
        prefix.setText(ud.getPre());
        prefix.getDocument().addDocumentListener(prefix_function());

        gbc3.gridx = 0; gbc3.gridy = 1;
        subSidePan2_2[1].add(subSidePan2_2[0], gbc3);
        
        gbc3.gridwidth = 1;
        gbc3.gridx = 0; gbc3.gridy = 2;
        subSidePan2_2[1].add(new JLabel("Prefix"), gbc3);
        gbc3.gridx = 1; gbc3.gridy = 2;
        subSidePan2_2[1].add(prefix, gbc3);
        gbc3.gridx = 0; gbc3.gridy = 3;
        subSidePan2_2[1].add(new JLabel("Output: "), gbc3);
        gbc3.gridx = 1; gbc3.gridy = 3;
        updateOutcomeString();
        outcome.setEditable(false);
        subSidePan2_2[1].add(outcome, gbc3);
        
        subSidePan2.add(subSidePan2_2[1]);
    }
    
    //  ===========================  Helper Functions  ===================================
    
    void updateOutcomeString(){//update "outcome" string
        temp = textData[0]+" "+textData[1];
        for(int i=2; i < textData.length; i++)
            temp += " "+textData[i];
        outcome.setText(temp);
    }
    
    int updatePositions(int pos, int targetPos){//used for changing 11111 to 11112 e.g., digits are flags to display P1-P5
        chars = String.valueOf(pos).toCharArray();
        chars[targetPos] = chars[targetPos] == '1' ? '2' : '1';
        return Integer.parseInt(String.valueOf(chars));
    }
    
    void loadUserDataValues(){
        for(int i=0, j = 7; i<textData.length; i++, j--){
            if( i == 0 ) textData[i] = ud.getPre();
            else if (i == 1) {
                gb = (GB)gbList.getSelectedItem();
                textData[i] = ud.getShort() == 1 ? gb.getShortName() : gb.getName().replaceAll("_", "");
            }
            else textData[i] = ud.getPosChar(i-2) == '1' ? "P"+j+"()" : "";
        }
    }
    
    GB getLastGB(){//used for gbList on application start
        for (GB gb1 : gbs) {
            if (gb1.getShortName().equals(ud.getGB())) gb = gb1;
        }
        return gb;
    }


     //======================= All functionalities for Swing Components =============================

    ItemListener gb_list_function(){
        ItemListener il = new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                gb = (GB) e.getItem();
                JComboBox box = (JComboBox)e.getSource();

                if(e.getStateChange() == ItemEvent.SELECTED){
                    img_gb.setIcon(new ImageIcon(adjustImage(gb.getImage(), 200, 200)));
                    
                    ud.setGB( gb.getShortName() );
                    db.writeUserData(3, ud);
                    textData[1] = checks[1].isSelected() ? gb.getShortName() : gb.getName().replaceAll("_", "");
                    updateOutcomeString();
                    updateTable(gb.getAge());//change table contents
                    System.out.println("[GBLIST] "+db.get_gb_lvl(gb.getShortName()));
                    scrollTableToRow();
                }
            }
        };
        return il;
    }
    
    ActionListener check_box_function(int index){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JCheckBox box = (JCheckBox) e.getSource();
                gb = (GB) gbList.getSelectedItem();
                if(box.isSelected()){
                    if( index == 0 ) textData[1] = gb.getShortName();
                    else textData[index + 1] = "P"+(6-index)+"()";}
                else{
                    if (index == 0) textData[1] = gb.getName().replaceAll("_", "");
                    else textData[index + 1] = "";}
                updateOutcomeString();
                if(index == 0) { ud.toggleShort(); db.writeUserData(1, ud); }
                else { ud.setPos(updatePositions(ud.getPos(), index - 1)); db.writeUserData(4, ud);}

            }
        };
        return al;
    }
    
    ActionListener bottom_panel_info_function(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                temp = "<html><body><div align='center'>This application features media protected by the Fair Use guidelines of Section 107 of the Copyright Act.<br>All rights reserved to the copyright owners.</div><br><br>"
                + "<div align='center'>This application was made by Mar (vict100@mail.com).<br>Contact me if you have questions or suggestions! Enjoy!</div></body></html>";
                JOptionPane.showMessageDialog(null,temp,"Information",JOptionPane.INFORMATION_MESSAGE);

            }
        };
        return al;
    }
    
        ActionListener bottom_panel_quest_function(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                guide.showGuide();
            }
        };
        return al;
    }
    
    DocumentListener prefix_function(){
        DocumentListener dl = new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e) {}

            @Override
            public void insertUpdate(DocumentEvent e) {
                textData[0] = prefix.getText();
                ud.setPre(textData[0]);
                db.writeUserData(0, ud);
                updateOutcomeString();
            }

            @Override
            public void removeUpdate(DocumentEvent e)  {
                textData[0] = prefix.getText();
                ud.setPre(textData[0]);
                db.writeUserData(0, ud);
                updateOutcomeString();
            }
        };
        return dl;
    }
    
}
