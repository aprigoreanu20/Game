package org.spells;

import org.example.entities.characters.Enemy;
import org.example.entities.characters.Entity;
import org.example.entities.characters.Mage;

public class Ice extends Spell {
    public Ice() {
        // folosim constructorul de la clasa Spell prin care sunt generate valori random pentru costul manei si pentru
        // damage-ul provocat prin folosirea acestei abilitati
        super();
    }

    @Override
    public String getImagePath() {
        return "src/resources/Ice.png";
    }

    @Override
    public void visit(Entity entity) {
        // aplicarea damage-ului fata de entitate in cazul in care entitatea nu e imuna fata de Ice
        if (!entity.hasIceImmunity()) {
            entity.receiveDamage(super.getDamageInflicted());
        }
    }
}
