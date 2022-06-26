package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

class DBManager {
    private static Connection connection;

    //Establish a connection to the database
    public DBManager() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=Satisfactory;" +
                            "IntegratedSecurity=true;TrustServerCertificate=true");
            System.out.println("Connection established.");
        } catch (SQLException e) {e.printStackTrace();}
    }

    /*
    Method:     getPartList
    Input:      nothing!
    Output:     Vector  - of all parts in the database

    This is the default no-arg method for makePartList, which is used to populate a list of all parts in a database
     */
    public ArrayList<Part> getPartList() {
        ArrayList<Part> partList = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    "SELECT p.Part, pt.Type FROM Part p\n" +
                    "INNER JOIN PartType pt ON p.Type = pt.TypeID;");
            while (rs.next()) {
                //1 - PartName, 2 - PartType
                partList.add(new Part(rs.getString(0), rs.getString(1)));
            }
        } catch (Exception e) {e.printStackTrace();}
        return partList;
    }

    /*
    Method:     getPartList(String, int)
    Input:      String  - the recipe to be searched
                int     - the type of list that should be returned
    Output:     Map     - the map of parts (ingredients) and the amounts used in the given recipe

    This method searches for a part list that belongs to a recipe. The type defines whether this method returns a list
    of input parts or output parts.

    Type:       1 - input parts
                2 - output parts
     */
    public Map<Part, Double> getPartList(String recipeName, int type) {
        Map<Part, Double> partList = new TreeMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    "SELECT p.Part, pt.Type, rp.Amount\n" +
                            "FROM Recipe_Part rp INNER JOIN Recipe r\n" +
                            "ON rp.Recipe = r.RecipeID INNER JOIN Recipe_Part_Type rpt\n" +
                            "ON rp.Type = rpt.TypeID INNER JOIN Part p\n" +
                            "ON rp.Part = p.PartID INNER JOIN PartType pt\n" +
                            "ON p.Type = pt.TypeID\n" +
                            "WHERE r.Recipe = '" + recipeName +
                            "' AND rpt.Type = '" + ((type == 1) ? "Input" : "Output") + "';");
            while (rs.next()) {
                //1 - PartName, 2 - PartType, 3 - Amount
                partList.put(new Part(rs.getString(1), rs.getString(2)),
                        rs.getDouble(3));
            }
        } catch (Exception e) {e.printStackTrace();}
        return partList;
    }

    public ArrayList<Recipe> getRecipeList() {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    "SELECT r.Recipe FROM Recipe r;");
            while (rs.next()) {
                //1 - RecipeName
                recipeList.add(new Recipe(rs.getString(1), this));
            }
        } catch (Exception e) {e.printStackTrace();}
        return recipeList;
    }

    /*
    Method:     getRecipeType(String)
    Input:      String  - the recipe to be searched
    Output:     int     - the numeric representation of the type of recipe
    
    Type:       1 - Standard
                2 - Alternate
     */
    public int getRecipeType(String recipeName) {
        int type = 0;
        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    "SELECT r.RecipeType\n" +
                            "FROM Recipe r\n" +
                            "WHERE r.Recipe = '" + recipeName + "';");
            while (rs.next()) {
                type = rs.getInt(1);
            }
        } catch (Exception e) {e.printStackTrace();}
        return type;
    }

    /*
    Method:     getBuilding(String)
    Input:      String  - the recipe to be searched
    Output:     String  - the name of the building this recipe is produced in
     */
    public String getBuilding(String recipeName) {
        String buildingName = null;
        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    "SELECT b.Building\n" +
                            "FROM Building b INNER JOIN Recipe r\n" +
                            "ON r.Building = b.BuildingID\n" +
                            "WHERE r.Recipe = '" + recipeName + "';");
            while (rs.next()) {
                buildingName = rs.getString(1);
            }
        } catch (Exception e) {e.printStackTrace();}
        return buildingName;
    }
}
