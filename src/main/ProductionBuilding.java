package main;

public record ProductionBuilding(String name, int power) implements Building {
    public ProductionBuilding(String name, int power) {
        this.name = name.trim();
        this.power = power;
    }
}
