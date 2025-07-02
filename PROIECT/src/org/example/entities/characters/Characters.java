package org.example.entities.characters;

public abstract class Characters extends Entity {
    private String name;
    private int experience, level;
    private int strength, dexterity, charisma;
    // camp pentru setarea unui atribut principal dintre cele 3 (strength / dexterity / charisma)
    private Attributes mainAttribute;
    // nextExperienceTarget reprezinta urmatorul prag pentru experience (cand experience depaseste
    // acest prag, sunt actualizate valorile pentru cele 3 atribute
    private int nextExperienceTarget;
    // atribut pentru numarul de inamici ucisi
    private int killedEnemies;

    // atributele pe care le poate avea un personaj
    public enum Attributes {
        STRENGTH, DEXTERITY, CHARISMA;
    }

    public Characters(String name, int experience, int level) {
        super();
        this.name = name;
        this.experience = experience;
        this.level = level;
        nextExperienceTarget = 2 * experience;
        killedEnemies = 0;
    }

    public Characters(String name, int currentLife, int maxLife, int currentMana, int maxMana, boolean fireImmunity,
                     boolean iceImmunity, boolean earthImmunity, int experience, int level, int strength,
                     int dexterity, int charisma, Attributes mainAttribute) {
        super(currentLife, maxLife, currentMana, maxMana, fireImmunity, iceImmunity, earthImmunity);
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.strength = strength;
        this.dexterity = dexterity;
        this.charisma = charisma;
        this.mainAttribute = mainAttribute;
        nextExperienceTarget = 2 * experience;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public int getNextExperienceTarget() { return nextExperienceTarget; }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getKilledEnemies() { return killedEnemies; }

    // setters
    public Attributes getMainAttribute() {
        return mainAttribute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setNextExperienceTarget(int nextExperienceTarget) {
        this.nextExperienceTarget = nextExperienceTarget;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setMainAttribute(Attributes mainAttribute) {
        this.mainAttribute = mainAttribute;
    }

    public void incKilledEnemies() { killedEnemies++; }

    // metoda pentru actualizarea valorilor pentru experienta si nivel atunci cand se termina un joc
    public void nextLevel() {
        setExperience(getExperience() + 5 * getLevel());
        setLevel(getLevel() + 1);
    }

    public String getType() {
        if (this instanceof Warrior)
            return "Warrior";
        if (this instanceof Mage)
            return"Mage";
        if (this instanceof Rogue)
            return  "Rogue";
        return "";
    }

    @Override
    public String toString() {
        String proffesion = "";
        if (this instanceof Warrior)
            proffesion = "Warrior";
        if (this instanceof Mage)
            proffesion = "Mage";
        if (this instanceof Rogue)
            proffesion = "Rogue";
        return name + "(" + proffesion + ") : experience = " + experience + ", level = " + level + "\n" + "Strength = "
                + strength + ", dexterity = " + dexterity + ", charisma = " + charisma;
    }
}