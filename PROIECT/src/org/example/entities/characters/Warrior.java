package org.example.entities.characters;

import java.util.Random;

public class Warrior extends Characters {
    public Warrior(String name, int experience, int level) {
        super(name, experience, level);
        // warrior are imunitate fata de Fire
        setFireImmunity(true);
        // atributul principal pentru warrior este STRENGTH
        setMainAttribute(Attributes.STRENGTH);

        // valoarea initiala a celor 3 atribute este calculata in functie de experienta
        // atributul principal are valoarea cea mai mare
        setStrength(experience);
        setCharisma(experience / 2);
        setDexterity(experience / 2);
    }

    // metoda pentru efectuarea actualizarilor necesare odata cu terminarea unui joc
    @Override
    public void nextLevel() {
        super.nextLevel();

        // atunci cand se depaseste urmatorul prag setat pentru experienta, personajul isi imbunatateste atributele
        if (getExperience() >= getNextExperienceTarget()) {
            // atributul principal este crescut cu 40 de puncte, iar celelalte cu 20 de puncte
            setStrength(getStrength() + 40);
            setCharisma(getCharisma() + 20);
            setDexterity(getDexterity() + 20);

            // una dintre cele doua abilitati secundare primeste extra 20 de puncte
            // alegerea este random
            Random rand = new Random();
            int randomAttribute = rand.nextInt(2);
            if (randomAttribute == 0) {
                setDexterity(getDexterity() + 20);
            }
            else {
                setCharisma(getCharisma() + 20);
            }

            // se updateaza si valoarea urmatorului target pentru experience
            setNextExperienceTarget(2 * getNextExperienceTarget());
        }
    }

    @Override
    public String getImagePath() {
        return "src/resources/Warrior.jpg";
    }

    @Override
    public void receiveDamage(int damage) {
        // damage-ul primit se injumatateste daca atributul dexterity este mai mare sau egal cu atributul charisma
        if (getDexterity() >= getCharisma()) {
            damage /= 2;
        }
        super.receiveDamage(damage);
    }

    @Override
    public int getDamage() {
        int damage = super.getDamage();
        if (getStrength() % 50 >= damage) {
            damage *= 2;
        }
        return damage;
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}