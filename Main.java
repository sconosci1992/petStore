// Chris Thibodeaux
// C00468013
// CMPS 260
// Programming Assignment : #7
// Due Date : 4/24/20
// Program Description: Making a simple POS using a cart feature.
// Certificate of Authenticity:
// I certify that, other than the code provided by the instructor, the code in this project is entirely
// my own work.

package com.company;

import java.util.*;
import java.io.*;

public class Main {

    static ArrayList<Item> itemList = new ArrayList<>();
    static ArrayList<Item> shoppingCart = new ArrayList<>();
    static File inventoryFile = new File("C:\\Users\\Chris\\pa7_c00468013\\src\\com\\company\\inventory.txt");
    static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        try (Scanner fileInput = new Scanner(inventoryFile)) {
            while (fileInput.hasNext()) {
                String tempName = fileInput.next();
                double tempPrice = fileInput.nextDouble();
                int tempQuantity = fileInput.nextInt();
                itemList.add((new Item(tempName, tempPrice, tempQuantity)));
            }
        }

        System.out.print("Welcome to my shop. ");
        displayItemNames();
        //displays menu options
        navigateMenu();
        //writes revised inventory to text file
        writeInventory();
    }

    public static void displayMenu() {
        System.out.println("Here are options you can choose an action from: ");
        makeDots();
        System.out.println("1. Select an item to add to your cart.");
        System.out.println("2. Return an item from your cart");
        System.out.println("3. Finish shopping and check out. If no items in cart, just leave");
        makeDots();
    }

    public static void navigateMenu() {
        displayMenu();
        System.out.print("Select a number: ");
        int userChoice = userInput.nextInt();
        makeDots();
        if (userChoice == 1) {
            displayItemNames();
            System.out.println("0) Return to main menu");
            System.out.println("Select a number: ");
            userChoice = userInput.nextInt();
            if (userChoice == 0) {
                navigateMenu();
            } else {
                addAnimal(userChoice);
                navigateMenu();
            }
        } else if (userChoice == 2) {
            displayCartNames();
            System.out.println("0) Return to main menu");
            System.out.println("Select a number: ");
            userChoice = userInput.nextInt();
            if (userChoice == 0) {
                navigateMenu();
            }else {
                removeAnimal(userChoice);
                navigateMenu();
            }
        } else if (userChoice == 3) {
            printReceipt();
            shoppingCart.clear();
            System.out.println("Welcome new shopper, enter 1 to shop or enter 2 to exit shop");
            userChoice = userInput.nextInt();
            if(userChoice == 1){
                displayItemNames();
                navigateMenu();
            }
        } else if (userChoice < 1 || userChoice > 3) {
            System.out.println("Incorrect input. System failure.");
            return;
        }
    }

    public static void displayItemNames() {
        System.out.println("This is what we offer:");
        makeDots();
        for (int i = 0; i < itemList.size(); i++) {
            System.out.print((i + 1) + ") " + itemList.get(i).getName() + "   ");
            System.out.println();
        }
        makeDots();
    }

    public static void displayCartNames() {
        System.out.println("This is what you have already selected:");
        makeDots();
        if (shoppingCart.size() <= 0) {
            System.out.println("You have nothing currently in your cart");
            navigateMenu();
        }
        for (int i = 0; i < shoppingCart.size(); i++) {
            System.out.print((i + 1) + ") " + shoppingCart.get(i).getName() + "   ");
            System.out.println();
        }
        makeDots();
    }

    //move animal to cart, updating quanties
    public static void addAnimal(int index) {
        String animalName = itemList.get(index - 1).getName();
        Item itemListAnimal = new Item(itemList.get(index - 1));

        int i = 0;
        do {
            //if the cart has the selected animal already
            if (shoppingCart.size() > 0 && shoppingCart.get(i).getName() == animalName) {
                if (itemList.get(index - 1).getQuantity() > 0) {
                    shoppingCart.get(i).addQuantity();
                    itemList.get(index - 1).subtractQuantity();
                    if (itemList.get(index - 1).getQuantity() == 0) {
                        itemList.remove(index - 1);
                    }
                } else {
                    System.out.println("Quantities on hand cannot be less than zero;");
                    return;
                }
                //else if the cart does not have the selected animal already
            } else if (i == shoppingCart.size() - 1 && shoppingCart.get(i).getName() != animalName) {
                shoppingCart.add(itemListAnimal);
                itemList.get(index - 1).subtractQuantity();
                shoppingCart.get(i).setQuantity();
                if (itemList.get(index - 1).getQuantity() == 0) {
                    itemList.remove(index - 1);
                }
            } else if (i == shoppingCart.size() && shoppingCart.size() == 0) {
                shoppingCart.add(itemListAnimal);
                itemList.get(index - 1).subtractQuantity();
                shoppingCart.get(i).setQuantity();
                if (itemList.get(index - 1).getQuantity() == 0) {
                    itemList.remove(index - 1);
                }
            }
            i++;
        } while (i < shoppingCart.size());
    }


    //move animal to itemList, updating quantities
    public static void removeAnimal(int index) {
        String animalName = shoppingCart.get(index - 1).getName();
        Item itemListAnimal = new Item(shoppingCart.get(index - 1));

        int i = 0;
        do {
            //if the cart has the selected animal already
            if (itemList.size() > 0 && itemList.get(i).getName() == animalName) {
                if (shoppingCart.get(index - 1).getQuantity() > 0) {
                    itemList.get(i).addQuantity();
                    shoppingCart.get(index - 1).subtractCartQuantity();
                    if (shoppingCart.get(index - 1).getQuantity() == 0) {
                        shoppingCart.remove(index - 1);
                    }
                } else {
                    System.out.println("Quantities on hand cannot be less than zero;");
                    return;
                }
                //else if the cart does not have the selected animal already
            } else if (i == itemList.size() - 1 && itemList.get(i).getName() != animalName) {
                itemList.add(itemListAnimal);
                shoppingCart.get(index - 1).subtractCartQuantity();
                itemList.get(i).setQuantity();
                if (shoppingCart.get(index - 1).getQuantity() == 0) {
                    shoppingCart.remove(index - 1);
                }
            } else if (i == itemList.size() && itemList.size() == 0) {
                itemList.add(itemListAnimal);
                shoppingCart.get(index - 1).subtractCartQuantity();
                itemList.get(i).setQuantity();
                if (shoppingCart.get(index - 1).getQuantity() == 0) {
                    shoppingCart.remove(index - 1);
                }
            }
            i++;
        } while (i < itemList.size());
    }

    public static void printReceipt(){
        double totalPrice = 0;
        if(shoppingCart.size() > 0) {
            for (int i = 0; i < shoppingCart.size(); i++) {
                System.out.print(shoppingCart.get(i).getName() + "  ");
                System.out.printf("(%d)  %.2f %n", shoppingCart.get(i).getQuantity(), (shoppingCart.get(i).getPrice() * shoppingCart.get(i).getQuantity()));
                totalPrice += shoppingCart.get(i).getPrice();
                makeDots();
            }
            System.out.println("Total price = " + totalPrice);
            makeDots();
        }else{
            System.out.println("There is currently nothing in your cart. Goodbye");
            navigateMenu();
        }
    }

    public static void writeInventory() throws Exception {
        try (PrintWriter output = new PrintWriter("inventory.txt")) {
            for (Item item : itemList) {
                output.println(item.getName());
                output.println(item.getPrice());
                output.println(item.getQuantity());
            }
        }
    }

    public static void makeDots() {
        System.out.println("--------------------------");
    }
}
