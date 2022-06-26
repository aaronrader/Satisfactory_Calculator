package main;

public record PowerBuilding(String name, int power) implements Building {
    public PowerBuilding(String name, int power) {
        this.name = name.trim();
        this.power = power;
    }
}
