package org.example.entities.characters;

import org.spells.*;

import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle, Element<Entity> {
    private ArrayList<Spell> abilities = new ArrayList<Spell>();

    private int currentLife, maxLife;
    private int currentMana, maxMana;
    private boolean fireImmunity, iceImmunity, earthImmunity;

    public Entity() {
        // am ales 100 ca valoare maxima atat pentru viata, cat si pentru mana
        maxLife = 100;
        maxMana = 100;

        Random rand = new Random();
        // valorile atribuite pentru viata si mana sunt generate aleator
        currentLife = rand.nextInt(1, maxLife);
        currentMana = rand.nextInt(1, maxMana);
        fireImmunity = false;
        iceImmunity = false;
        earthImmunity = false;

        generateAbilities();
    }

    public Entity(int maxLife, int maxMana) {
        this.maxLife = maxLife;
        this.maxMana = maxMana;

        Random rand = new Random();
        currentLife = rand.nextInt(1, maxLife);
        currentMana = rand.nextInt(1, maxMana);

        generateAbilities();
    }

    public Entity(int currentLife, int maxLife, int currentMana, int maxMana, boolean fireImmunity, boolean iceImmunity,
                  boolean earthImmunity) {
        this(maxLife, maxMana);
        this.currentLife = currentLife;
        this.currentMana = currentMana;
        this.fireImmunity = fireImmunity;
        this.iceImmunity = iceImmunity;
        this.earthImmunity = earthImmunity;

        generateAbilities();
    }

    // metode getter
    public ArrayList<Spell> getAbilities() {
        return abilities;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public boolean hasFireImmunity() {
        return fireImmunity;
    }

    public boolean hasIceImmunity() {
        return iceImmunity;
    }

    public boolean hasEarthImmunity() {
        return earthImmunity;
    }

    // metode setter
    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setFireImmunity(boolean fireImmunity) {
        this.fireImmunity = fireImmunity;
    }

    public void setIceImmunity(boolean iceImmunity) {
        this.iceImmunity = iceImmunity;
    }

    public void setEarthImmunity(boolean earthImmunity) {
        this.earthImmunity = earthImmunity;
    }

    // metode pentru lucrul cu lista de abilitati
    // adaugarea unei abilitati in lista
    public void addAbility(Spell spell) {
        abilities.add(spell);
    }

    // scoaterea unei abilitati din lista
    public void removeAbility(Spell spell) {
        abilities.remove(spell);
    }

    // metoda pentru generarea listei de abilitati
    public void generateAbilities() {
        // se adauga cate o abilitate ice, fire si earth in lista de abilitati
        abilities.clear();
        Spell fireAbility = new Fire();
        addAbility(fireAbility);
        Spell iceAbility = new Ice();
        addAbility(iceAbility);
        Spell earthAbility = new Earth();
        addAbility(earthAbility);

        // se adauga atribute random (cel mult 3 atribute generate aleator)
        Random rand = new Random();
        int nrAbilities = rand.nextInt(3);
        for (int i = 0; i < nrAbilities; i++) {
            // se genereaza un nr random intre 0, 1 sau 2
            // aceste valori corespund cate unui tip de abilitate (ice / fire / earth)
            int randomAbility = rand.nextInt(3);
            switch (randomAbility) {
                case 0: {
                    Spell newSpell = new Ice();
                    addAbility(newSpell);
                    break;
                }
                case 1: {
                    Spell newSpell = new Fire();
                    addAbility(newSpell);
                    break;
                }
                case 2: {
                    Spell newSpell = new Earth();
                    addAbility(newSpell);
                    break;
                }
            }
        }
    }

    // metoda pentru afisarea listei de abilitati
    public void printAbilities() {
        int index = 1;
        for (Spell ability : abilities) {
            System.out.println(index + ". " + ability);
            index++;
        }
    }

    // metoda pentru regenerarea valorii pentru viata
    public void regenerateLife() {
        Random rand = new Random();
        if (currentLife + 1 >= maxLife)
            currentLife = maxLife;
        else {
            currentLife = rand.nextInt(currentLife + 1, maxLife);
        }
    }

    // metoda pentru regenerarea valorii pentru mana
    public void regenerateMana() {
        Random rand = new Random();
        if (currentMana + 1 >= maxMana) {
            currentMana = maxMana;
        } else {
            currentMana = rand.nextInt(currentMana + 1, maxMana);
        }
    }

    // metoda pentru folosirea unei abilitati - intoarce true daca abilitatea dorita se poate folosi
    public boolean useAbility(Spell ability, Entity enemy) {
        // verificare daca abilitatea se afla in lista de abilitati
        if (!abilities.contains(ability)) {
            System.out.println("Ability was not found in your character's list of abilities");
            return false;
        }

        // verificare daca acest entity are suficienta mana pentru a folosi abilitatea
        if (ability.getRequiredMana() > currentMana) {
            if (!(this instanceof Enemy))
                System.out.println("You can't use this ability because you don't have enough mana");
            return false;
        }

        // verificare daca inamicul are imunitate pentru abilitatea folosita
        if (!ability.canBeUsed(enemy)) {
            if (!(this instanceof Enemy))
                System.out.println("The enemy is immune against the spell you tried to use!");
            return false;
        }
//        removeAbility(ability);
//        setCurrentMana(currentMana - ability.getRequiredMana());
        return true;
    }

    // metodele din interfata Battle
    public void receiveDamage(int damage) {
        if (currentLife < damage) {
            currentLife = 0;
        } else {
            currentLife -= damage;
        }
    }

    public int getDamage() {
        int damage = 0;
        // daca parametrele de joc sunt cel putin egale cu jumatate din valoarea maxima, primeste 10 puncte
        if (currentLife >= maxLife / 2) {
            damage += 10;
        }
        if (currentMana >= maxMana / 2) {
            damage += 10;
        }

        // primeste cate 10 puncte de damage pentru fiecare imunitate pe care o are
        if (fireImmunity) {
            damage += 10;
        }
        if (iceImmunity) {
            damage += 10;
        }
        if (earthImmunity) {
            damage += 10;
        }

        // consideram ca valoarea damage-ului este cel putin 10
        if (damage < 10) {
            damage = 10;
        }
        return damage;
    }

    // metoda pentru gasirea imaginilor asociate entitatilor in folderul resources
    public abstract String getImagePath();
}
