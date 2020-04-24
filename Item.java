package com.company;

public class Item{
    private String name;
    private double price;
    private int quantity;

    public Item(){
    }

    public Item(Item e){
        this.name = e.getName();
        this.price = e.getPrice();
        this.quantity = e.getQuantity();
    }

    public Item(String name, double price, int quantity){
        if(name == "" || name == null || price < 0 || quantity < 0) {
            System.out.println("One or more of your inputs were formatted incorrectly, try again.");
            return;
        }else {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public int getQuantity(){
        return quantity;
    }
    public void subtractQuantity(){
        this.quantity --;
        if(this.quantity <= 0){
            this.quantity = 0;
            System.out.println("Quantities on hand can not be less than zero, please select an in-stock item");
            return;
        }
    }
    public void subtractCartQuantity(){
        this.quantity --;
        if(this.quantity <= 0){
            this.quantity = 0;
        }
    }
    public void addQuantity(){
        this.quantity++;
    }

    public void setQuantity(){
        this.quantity = 1;
    }


    public String toString(){
        return this.name + " " + this.price + " " + this.quantity;
    }
}
