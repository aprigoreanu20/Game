package org.example.entities.characters;

public interface Visitor <T extends Entity> {
    void visit(T entity);
}