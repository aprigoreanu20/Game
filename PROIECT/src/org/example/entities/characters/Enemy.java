package org.example.entities.characters;

import java.util.Random;

public class Enemy extends Entity {
    public Enemy(int maxLife, int maxMana) {
        super(maxLife, maxMana);

        Random rand = new Random();
        // setarea unor valori aleatorii pentru viata si mana
        int randLife = rand.nextInt(1, maxLife);
        setCurrentLife(randLife);
        int randMana = rand.nextInt(1, maxMana);
        setCurrentMana(randMana);

        // setarea valorilor pentru imunitati
        int randBool;
        randBool = rand.nextInt(2);
        if (randBool == 0)
            setFireImmunity(false);
        else
            setFireImmunity(true);
        randBool = rand.nextInt(2);
        if (randBool == 0)
            setIceImmunity(false);
        else
            setIceImmunity(true);
        randBool = rand.nextInt(2);
        if (randBool == 0)
            setEarthImmunity(false);
        else
            setFireImmunity(true);

        // constructorul superclasei Entity genereaza si lista de abilitati
    }

    @Override
    public void receiveDamage(int damage) {
        System.out.println("Enemy receives damage!");
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            // damage-ul primit se injumatateste in functie de rezultatul obtinut prin Random.nextInt
            damage /= 2;
        }
        super.receiveDamage(damage);
    }

    @Override
    public int getDamage() {
        int damage = super.getDamage();
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            // damage-ul oferit se dubleaza in functie de rezultatul obtinut prin Random.nextInt
            damage *= 2;
        }
        return damage;
    }

    @Override
    public String getImagePath() {
        return "src/resources/enemy.jpeg";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
