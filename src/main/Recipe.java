package main;

import java.util.Map;

public class Recipe implements Comparable<Recipe>{
    private final String name;
    private final String building;
    private final int type;
    private final Map<Part, Double> inputParts;
    private final Map<Part, Double> outputParts;

    public Recipe(String name, DBManager dbm) {
        this.name = name.trim();

        building = dbm.getBuilding(this.name);
        type = dbm.getRecipeType(this.name);
        inputParts = dbm.getPartList(this.name, 1);
        outputParts = dbm.getPartList(this.name, 2);
    }

    public Recipe(String name, String building, String type, Map<Part, Double> inParts, Map<Part, Double> outParts) throws Exception {
        this.name = name;
        this.building = building;
        switch (type.toLowerCase()) {
            case "input" -> this.type = 1;
            case "output" -> this.type = 2;
            default -> throw new Exception("\"" + type + "\" is not a valid main.Recipe ingredient type.");
        }
        this.inputParts = inParts;
        this.outputParts = outParts;
    }

    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }

    public int getType() {
        return type;
    }

    public Map<Part, Double> getInputParts() {
        return inputParts;
    }

    public Map<Part, Double> getOutputParts() {
        return outputParts;
    }

    @Override
    public int compareTo(Recipe o) {
        return this.name.compareTo(o.getName());
    }
}
