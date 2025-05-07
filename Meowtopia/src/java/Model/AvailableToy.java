/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.*;

import java.util.Base64;

/**
 *
 * @author User
 */
public class AvailableToy {

    private String toyid;
    private String toyname;
    private int happyvalue;
    private int energyused;
    private byte[] image;

    public AvailableToy(String toyid, String toyname, int happyvalue, int energyused, byte[] image) {
        this.toyid = toyid;
        this.toyname = toyname;
        this.happyvalue = happyvalue;
        this.energyused = energyused;
        this.image = image;
    }

    public String getToyid() {
        return toyid;
    }

    public void setToyid(String toyid) {
        this.toyid = toyid;
    }

    public String getToyname() {
        return toyname;
    }

    public void setToyname(String toyname) {
        this.toyname = toyname;
    }

    public int getHappyvalue() {
        return happyvalue;
    }

    public void setHappyvalue(int happyvalue) {
        this.happyvalue = happyvalue;
    }

    public int getEnergyused() {
        return energyused;
    }

    public void setEnergyused(int energyused) {
        this.energyused = energyused;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPicBase64() {
        if (image != null && image.length > 0) {
            return Base64.getEncoder().encodeToString(image);
        } else {
            return "";
        }
    }
}
