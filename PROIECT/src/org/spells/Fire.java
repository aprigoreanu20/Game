package org.spells;

import org.example.entities.characters.Entity;

public class Fire extends Spell {
    public Fire() {
        // folosim constructorul de la clasa Spell prin care sunt generate valori random pentru costul manei si pentru
        // damage-ul provocat prin folosirea acestei abilitati
        super();
    }

    @Override
    public String getImagePath() {
        return "src/resources/Fire.jpg";
    }

    @Override
    public void visit(Entity entity) {
        // aplicarea damage-ului fata de entitate in cazul in care entitatea nu e imuna fata de Ice
        if (!entity.hasFireImmunity()) {
            entity.receiveDamage(super.getDamageInflicted());
        }
    }
}
