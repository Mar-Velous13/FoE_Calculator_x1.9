/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

/**
 *
 * @author Curious
 */
public class UserData {
    
    int positions, shortForm;
    String lastGB, prefix;
    char [] posValues;
    
    public UserData(String pre, int shortF, int pos, String gb){
        this.positions = pos;
        this.shortForm = shortF;
        this.prefix = pre;
        this.lastGB = gb;
        this.posValues = String.valueOf(pos).toCharArray();
    }
    
    public int getPos(){return this.positions;}
    public int getShort(){return this.shortForm;}
    public String getGB(){return this.lastGB;}
    public String getPre(){return this.prefix;}
    public int getPosChar(int i){ return this.posValues[i]; }
    
    public void setPos(int pos){this.positions = pos; this.posValues = String.valueOf(pos).toCharArray();}
    public void toggleShort(){this.shortForm = this.shortForm == 0 ? 1 : 0;}
    public void setPre(String pre){this.prefix = pre;}
    public void setGB(String gb){this.lastGB = gb;}
}
