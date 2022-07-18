package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {

    private static DBManager db;
    private static ArrayList<Part> partList;
    private static ArrayList<Recipe> recipeList;

    private static void displayPartList() {
        partList = db.getPartList();
        System.out.printf("%-30s%s\n", "Part Name", "Part Type");
        System.out.println("------------------------------------------------------------");
        for (Part part : partList) {
            System.out.printf("%-30s%s\n", part.name(), part.type());
        }
    }

    private static void displayRecipeList() {
        recipeList = db.getRecipeList();
        System.out.printf("%-30s%-20s%s\n", "Recipe Name", "Recipe Type", "Building");
        System.out.println("------------------------------------------------------------");
        for (Recipe recipe : recipeList) {
            System.out.printf("%-30s%-20s%s\n", recipe.getName(), recipe.getType() == 1 ? "Standard" : "Alternate", recipe.getBuilding());
        }
    }

    public static void main(String[] args) {
        db = new DBManager();
        Scanner scnr = new Scanner(System.in);

        //Print out instructions
        System.out.println("Aaron's Satisfactory Calculator");
        System.out.printf(" %-4s%s\n", "1", "Part List");
        System.out.printf(" %-4s%s\n", "2", "Recipe List");
        System.out.printf(" %-4s%s\n", "3", "Add a Part");
        System.out.printf(" %-4s%s\n", "4", "Add a Recipe");

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Option:\t");
            String input = scnr.nextLine();
            switch (input.charAt(0)) {
                case '1' -> {
                    displayPartList();
                    validInput = true;
                }
                case '2' -> {
                    displayRecipeList();
                    validInput = true;
                }
                default -> System.out.println("Unknown Option");
            }
        }
    }
}
