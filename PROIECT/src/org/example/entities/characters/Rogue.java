package org.example.entities.characters;

import java.util.Random;

public class Rogue extends Characters {
    public Rogue(String name, int experience, int level) {
        super(name, experience, level);
        // rogue are imunitate fata de Earth
        setEarthImmunity(true);
        // atributul principal pentru rogue este DEXTERITY
        setMainAttribute(Attributes.DEXTERITY);

        // valoarea initiala a celor 3 atribute este calculata in functie de experienta
        // atributul principal are valoarea cea mai mare
        setDexterity(experience);
        setCharisma(experience / 2);
        setStrength(experience / 2);
    }

    // metoda pentru efectuarea actualizarilor necesare odata cu terminarea unui joc
    @Override
    public void nextLevel() {
        super.nextLevel();

        // atunci cand se depaseste urmatorul prag setat pentru experienta, personajul isi imbunatateste atributele
        if (getExperience() >= getNextExperienceTarget()) {
            // atributul principal este crescut cu 40 de puncte, iar celelalte cu 20 de puncte
            setDexterity(getDexterity() + 40);
            setStrength(getStrength() + 20);
            setCharisma(getCharisma() + 20);

            // una dintre cele doua abilitati secundare primeste extra 20 de puncte
            // alegerea este random (flip a coin)
            Random rand = new Random();
            int randomAttribute = rand.nextInt(2);
            if (randomAttribute == 0) {
                setStrength(getStrength() + 20);
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
        return "src/resources/Rogue.png";
    }

    @Override
    public void receiveDamage(int damage) {
        // damage-ul primit se injumatateste daca atributul charisma este mai mare sau egal cu atributul strength
        if (getCharisma() >= getStrength()) {
            damage /= 2;
        }
        super.receiveDamage(damage);
    }

    @Override
    public int getDamage() {
        int damage = super.getDamage();
        if (getDexterity() >= damage) {
            // damage-ul dat prin atac normal se dubleaza in functie de valoarea atributului principal (dexterity)
            damage *= 2;
        }
        return damage;
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}