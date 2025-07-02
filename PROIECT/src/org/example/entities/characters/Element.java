package org.example.entities.characters;

public interface Element <T extends Entity> {
    void accept(Visitor<T> visitor);
}
