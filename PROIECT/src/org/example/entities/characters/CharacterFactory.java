package org.example.entities.characters;

// clasa folosita in implementarea pentru Factory Design Pattern
public class CharacterFactory {
    public static Characters factory(String proffesion, String cname, int experience, int lvl){
        switch (proffesion) {
            case "Mage":
                return new Mage(cname, experience, lvl);
            case "Rogue":
                return new Rogue(cname, experience, lvl);
            case "Warrior":
                return new Warrior(cname, experience, lvl);
            default:
                return null;
        }
    }
}
