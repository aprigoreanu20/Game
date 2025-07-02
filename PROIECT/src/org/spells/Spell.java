package org.spells;

import org.example.entities.characters.Enemy;
import org.example.entities.characters.Entity;
import org.example.entities.characters.Visitor;

import java.util.Random;

public abstract class Spell implements Visitor<Entity> {
    private int damageInflicted, requiredMana;

    public Spell() {
        // generarea random a valorilor pentru atributele damageInflicted si requiredMana
        Random rand = new Random();
        // valoarea pentru damageInflicted este maxim 50 pentru ca, adunata cu damage-ul dat prin lupta normala, sa nu
        // depaseasca valoarea de 100 (maximul posibil pentru viata)
        damageInflicted = rand.nextInt(1, 50);
        // requiredMana este un numar aleator intre 1 si 30 pentru ca valoarea manei este maxim 30
        // valoarea 30 este selectata astfel incat jucatorul sa poata folosi de mai multe ori atac cu abilitate
        // intr-o runda de joc, dar sa existe totodata si posibilitatea sa isi consume mana
        requiredMana = rand.nextInt(1, 30);
    }

    // verificare pentru tipul abilitatii
    public String getType() {
        if (this instanceof Ice)
            return "Ice";
        if (this instanceof Fire)
            return "Fire";
        if (this instanceof Earth)
            return "Earth";
        return "Spell";
    }

    // getter + setter pt damageInflicted
    public int getDamageInflicted() {
        return damageInflicted;
    }
    public void setDamageInflicted(int damageInflicted) {
        this.damageInflicted = damageInflicted;
    }

    // getter + setter pt requiredMana
    public int getRequiredMana() {
        return requiredMana;
    }
    public void setRequiredMana(int requiredMana) {
        this.requiredMana = requiredMana;
    }

    // verifica daca o entitate are imunitate fata de abilitate
    // metoda returneaza false daca entitatea este imuna fata de abilitate
    public boolean canBeUsed(Entity e) {
        if (this instanceof Ice && e.hasIceImmunity())
            return false;
        if (this instanceof Fire && e.hasFireImmunity())
            return false;
        if (this instanceof Earth && e.hasEarthImmunity())
            return false;
        return true;
    }

    public abstract String getImagePath();

    // metoda toString pentru afisarea abilitatii folosite
    @Override
    public String toString() {
        return getType() + " (Damage = " + damageInflicted +", Required Mana = " + requiredMana + ")";
    }
}
