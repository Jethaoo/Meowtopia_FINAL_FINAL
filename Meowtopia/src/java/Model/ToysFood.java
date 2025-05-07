/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author User
 */
public class ToysFood {

    private String id;
    private String name;
    private int price;
    private Integer happyValue;    // Only for toys
    private Integer energyUsed;    // Toys: ENERGYUSED, Food: ENERGY
    private String base64Image;
    private String category;

    public ToysFood(String id, String name, int price, Integer happyValue, Integer energyUsed, byte[] imageBytes, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.happyValue = happyValue;
        this.energyUsed = energyUsed;
        this.base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
        this.category = category;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Integer getHappyValue() {
        return happyValue;
    }

    public Integer getEnergyUsed() {
        return energyUsed;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public String getCategory() {
        return category;
    }
}
