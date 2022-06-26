package main;

public record Part(String name, String type) implements Comparable<Part> {
    public Part(String name, String type) {
        this.name = name.trim();
        this.type = type.trim();
    }

    @Override
    public int compareTo(Part o) {
        return this.name.compareTo(o.name());
    }
}
