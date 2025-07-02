package org.example.entities.characters;

import java.util.Random;

public class Mage extends Characters {
    public Mage(String name, int experience, int level) {
        super(name, experience, level);
        // mage are imunitate fata de Ice
        setIceImmunity(true);
        // atributul principal pentru mage este CHARISMA
        setMainAttribute(Attributes.CHARISMA);

        // valoarea initiala a celor 3 atribute este calculata in functie de experienta
        // atributul principal are valoarea cea mai mare
        setCharisma(experience);
        setStrength(experience / 2);
        setDexterity(experience / 2);
    }

    // metoda pentru efectuarea actualizarilor necesare odata cu terminarea unui joc
    @Override
    public void nextLevel() {
        super.nextLevel();

        // atunci cand se depaseste urmatorul prag setat pentru experienta, personajul isi imbunatateste atributele
        if (getExperience() >= getNextExperienceTarget()) {
            // atributul principal este crescut cu 40 de puncte, iar celelalte cu 20 de puncte
            setCharisma(getCharisma() + 40);
            setDexterity(getDexterity() + 20);
            setStrength(getStrength() + 20);

            // una dintre cele doua abilitati secundare primeste extra 20 de puncte
            // alegerea este random
            Random rand = new Random();
            int randomAttribute = rand.nextInt(2);
            if (randomAttribute == 0) {
                setDexterity(getDexterity() + 20);
            }
            else {
                setStrength(getStrength() + 20);
            }

            // se updateaza si valoarea urmatorului target pentru experience
            setNextExperienceTarget(2 * getNextExperienceTarget());
        }
    }

    @Override
    public void receiveDamage(int damage) {
        // damage-ul primit se injumatateste daca atributul strength este mai mare sau egal cu atributul dexterity
        if (getStrength() >= getDexterity()) {
            damage /= 2;
        }
        super.receiveDamage(damage);
    }

    @Override
    public int getDamage() {
        // damage-ul dat prin atac normal se dubleaza in functie de valoarea atributului principal (charisma)
        int damage = super.getDamage();
        if (getCharisma() % 50 >= damage) {
            damage *= 2;
        }
        return damage;
    }

    @Override
    public String getImagePath() {
        return "src/resources/Mage.jpg";
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}