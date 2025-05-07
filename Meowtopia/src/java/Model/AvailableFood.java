/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.*;

import java.util.Base64;

public class AvailableFood {

    private String foodid;
    private String foodname;
    private int energy;
    private byte[] image;
    private int quantity;

    public AvailableFood(String foodid, String foodname, int energy, byte[] image, int quantity) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.energy = energy;
        this.image = image;
        this.quantity = quantity;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
    
    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getPicBase64() {
        if (image != null && image.length > 0) {
            return Base64.getEncoder().encodeToString(image);
        } else {
            return "";
        }
    }

}
