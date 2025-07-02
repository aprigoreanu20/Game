package org.spells;

import org.example.entities.characters.Entity;

public class Earth extends Spell {
    public Earth() {
        // folosim constructorul de la clasa Spell prin care sunt generate valori random pentru costul manei si pentru
        // damage-ul provocat prin folosirea acestei abilitati
        super();
    }

    @Override
    public String getImagePath() {
        return "src/resources/Earth.jpg";
    }

    @Override
    public void visit(Entity entity) {
        // aplicarea damage-ului fata de entitate in cazul in care entitatea nu e imuna fata de Ice
        if (!entity.hasEarthImmunity()) {
            entity.receiveDamage(super.getDamageInflicted());
        }
    }
}
