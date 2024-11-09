/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author Curious
 */
public class GB {
    String img, shortForm, age, name;
    int lastLevel;
//    BufferedImage bimg;
    
    public GB(String era, String name, String shortForm, int last_lvl, String imgSrc){
        this.age = era;
        this.name = name;
        this.shortForm = shortForm;
        this.lastLevel = last_lvl;
        this.img = imgSrc;
    }
    
    public String getImage(){ return this.img; }
    public int getLevel(){ return this.lastLevel; }
    public String getName(){ return this.name; }
    public String getShortName(){ return this.shortForm; }
    public String getAge() { return this.age; }
    
    public void setLevel(int lvl){ this.lastLevel = lvl; }
}
