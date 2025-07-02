import org.example.entities.CellEntityType;
import org.example.entities.characters.*;
import org.spells.Spell;
import java.util.*;

public class Game {
    ArrayList<Account> accountsList = new ArrayList<Account>();
    Grid map;
    // instanta unica a clasei Game
    public static Game game;
    // account reprezinta contul cu care s-a logat user-ul
    static Account account;
    // userCharacter reprezinta personajul ales de user
    static Characters userCharacter;

    // constructor privat pentru clasa Game - conform Singleton design pattern
    private Game() {
        accountsList = JsonInput.deserializeAccounts();
    }

    // metoda statica pentru a obtine instanta unica a clasei Game - conform Singleton design pattern
    public static Game getGameInstance() {
        // lazy initialization
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public ArrayList<Credentials> getCredentialsList() {
        ArrayList<Credentials> credentialsList = new ArrayList<>();
        for (Account account : accountsList) {
            Account.Information accountInfo = account.getInfo();
            credentialsList.add(accountInfo.getCredentials());
        }
        return credentialsList;
    }

    public void setAccount(Account account) {
        Game.account = account;
    }

    // metoda pentru logarea intr-un cont
    public void chooseAccount() {
//        accountsList = JsonInput.deserializeAccounts();
        ArrayList<Credentials> credentialsList = getCredentialsList();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please log in:");
            System.out.println("email> ");
            String email = scanner.nextLine();
            System.out.println("password> ");
            String password = scanner.nextLine();
            Credentials credentials = new Credentials(email, password);
            if (credentialsList.contains(credentials)) {
                account = accountsList.get(credentialsList.indexOf(credentials));
                break;
            } else {
                System.out.println("Wrong username / password. Please try again :(");
            }
        }
        System.out.println("Hello " + account.getInfo().getPlayerName() + "!");
        System.out.println("Your location: " + account.getInfo().getCountry());
        System.out.println("These are your favourite games:");
        account.getInfo().printFavoriteGames();
    }

    // metoda pentru validarea credentialelor introduse de user
    public boolean chooseAccount(Credentials credentials) {
        ArrayList<Credentials> credentialsList = getCredentialsList();
        if (credentialsList.contains(credentials)) {
            account = accountsList.get(credentialsList.indexOf(credentials));
            return true;
        }
        return false;
    }

    // metoda pentru alegerea personajului
    public void chooseCharacter() {
        account.printCharacterList();

        Scanner scanner = new Scanner(System.in);
        ArrayList<Characters> userCharacters = account.getCharacters();

        while (true) {
            System.out.println("Please choose a character (type the character index or character name): ");
            String chosenCharacter = scanner.nextLine();

            // personajul poate fi ales prin indexul din lista de personaje / prin tastarea numelui
            try {
                // tratarea cazului in care user-ul introduce un numar
                int characterIndex = Integer.parseInt(chosenCharacter) - 1;
                if (characterIndex >= 0 && characterIndex < userCharacters.size()) {
                    Characters character = userCharacters.get(characterIndex);
                    System.out.println("You chose: " + character.getName());
                    System.out.println("Character life: " + character.getCurrentLife());
                    System.out.println("Character mana: " + character.getCurrentMana());
                    userCharacter = character;
                    break;
                } else {
                    System.out.println("Wrong character index. Please try again :(");
                }
            } catch (NumberFormatException e) {
                // altfel, se incearca gasirea unui personaj cu numele tastat de user
                Characters character = account.findCharacterByName(chosenCharacter);
                if (character != null) {
                    System.out.println("You chose: " + character.getName());
                    System.out.println("Character life: " + character.getCurrentLife());
                    System.out.println("Character mana: " + character.getCurrentMana());
                    userCharacter = character;
                    break;
                } else {
                    System.out.println("Character name does not exist in your list. Please try again :(");
                }
            }
        }
    }

    // metoda pentru preluarea urmatoarei comenzi pentru deplasarea jucatorului
    public void getNextCommand(String command) throws ImpossibleMove, InvalidCommandException {
        Scanner scanner = new Scanner(System.in);

        // afisarea directiilor posibile de deplasare
        if (map.checkNorthMovePossible()) {
            System.out.println("To move up, type \"NORTH\" or press N");
        }
        if (map.checkSouthMovePossible()) {
            System.out.println("To move down, type \"SOUTH\" or press S");
        }
        if (map.checkWestMovePossible()) {
            System.out.println("To move left, type \"WEST\" or press W");
        }
        if (map.checkEastMovePossible()) {
            System.out.println("To move right, type \"EAST\" or press E");
        }
        System.out.println("Enter \"QUIT\" or \"Q\" to escape");

        if (command.equals("")) {
            command = scanner.nextLine();
        }
        boolean validDirection = false;

        // mutarea jucatorului in functie de alegerea userului
        try {
            if (command.equalsIgnoreCase("NORTH") || command.equalsIgnoreCase("N")) {
                map.goNorth();
                validDirection = true;
            }
            if (command.equalsIgnoreCase("SOUTH") || command.equalsIgnoreCase("S")) {
                map.goSouth();
                validDirection = true;
            }
            if (command.equalsIgnoreCase("WEST") || command.equalsIgnoreCase("W")) {
                map.goWest();
                validDirection = true;
            }
            if (command.equalsIgnoreCase("EAST") || command.equalsIgnoreCase("E")) {
                map.goEast();
                validDirection = true;
            }
            if (command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q")) {
                System.out.println("Game over!");
                validDirection = true;
                System.exit(0);
            }
            if (!validDirection) {
                // aruncarea unei exceptii InvalidCommandException daca input-ul oferit de user nu corespunde
                // niciunei directii de deplasare / QUIT pentru iesirea din joc
                throw new InvalidCommandException();
            }
        } catch (ImpossibleMove e) {
            // catch pentru exceptia ImpossibleMove (daca user-ul incearca sa se deplaseze in afara tablei de joc)
            System.out.println(e.getMessage());
            throw e;
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    // metoda pentru afisarea starii curente a personajului & a hartii de joc
    public void printStats(Characters character) {
        System.out.println(character);
        System.out.println(map);
    }

    // implementare logica pentru lupta dintre personaj si inamic
    public void fightMode(Characters character, Enemy enemy) {
        // generarea unei noi liste de abilitati pentru personaj
        character.generateAbilities();

        Scanner scanner = new Scanner(System.in);
        int damage, abilityIndex;
        Spell ability;

        while (true) {
            System.out.println(character.getName() + "'s life = " + character.getCurrentLife() + " and mana = " + character.getCurrentMana());

            System.out.println("Enter 1 to use ability");
            System.out.println("Enter 2 to use normal attack");

            damage = character.getDamage();

            // preluarea inputului pentru folosirea unei abilitati
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2")) {
                if (input.equals("1")) {
                    // afisarea listei de abilitati
                    if (!character.getAbilities().isEmpty()) {
                        System.out.println("Your character's abilities:");
                        character.printAbilities();
                        System.out.println();
                    } else {
                        System.out.println("You don't have any abilities!");
                    }

                    while (true) {
                        try {
                            System.out.println("Enter the index of the ability you want to use / enter 0 to use normal attack");
                            try {
                                abilityIndex = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException | InputMismatchException e) {
                                throw new InvalidCommandException();
                            }
                            if (abilityIndex < 0 || abilityIndex > character.getAbilities().size()) {
                                System.out.println("That is not a valid index. Please try again :(");
                            } else {
                                break;
                            }
                        } catch (InvalidCommandException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    if (abilityIndex != 0) {
                        ability = character.getAbilities().get(abilityIndex - 1);
                        if (character.useAbility(ability, enemy)) {
                            // scaderea manei personajului ca urmare a folosirii abilitatii
                            // si stergerea abilitatii din lista de abilitati a personajului
                            System.out.println("You used this spell - " + ability);
                            character.removeAbility(ability);
                            character.setCurrentMana(character.getCurrentMana() - ability.getRequiredMana());
                            System.out.println("The ability you used gave " + ability.getDamageInflicted() + " extra damage points");
//                            damage += ability.getDamageInflicted();
                            enemy.accept(ability);
                        }
                    }
                }
                // aplicarea damage-ului dat de personaj asupra inamicului
                enemy.receiveDamage(damage);
                System.out.println("After attack, enemy life is " + enemy.getCurrentLife());

                if (enemy.getCurrentLife() <= 0 || character.getCurrentLife() <= 0) {
                    break;
                }

                // atac din partea inamicului
                System.out.println("The enemy attacks you!");
                // inamicul incearca sa foloseasca o abilitate random din lista sa de abilitati
                Random rand = new Random();
                int randomAbilityIndex = rand.nextInt(enemy.getAbilities().size());
                Spell enemyAbility = enemy.getAbilities().get(randomAbilityIndex);
                if (enemy.useAbility(enemyAbility, character)) {
                    // scaderea manei inamicului si stergerea abilitatii folosite din lista de abilitati a inamicului
                    enemy.removeAbility(enemyAbility);
                    enemy.setCurrentMana(enemy.getCurrentMana() - enemyAbility.getRequiredMana());
                    System.out.println("Enemy used this spell - " + enemyAbility);
                    character.accept(enemyAbility);
                } else {
                    System.out.println("Enemy tried to use this spell - " + enemyAbility + ", but the spell was blocked!");
                }
                // aplicarea damage-ului dat de inamic asupra personajului
                character.receiveDamage(damage);

                if (enemy.getCurrentLife() <= 0 || character.getCurrentLife() <= 0) {
                    break;
                }
            } else {
                System.out.println("Please enter either 1 or 2!");
            }
        }
    }

    // metoda pentru logare si alegerea personajului
    public void run() {
        chooseAccount();
        chooseCharacter();
    }

    // metoda pentru implementarea efectiva a jocului
    public void gameLogic() {
        // user-ul alege un personaj (daca nu a ales unul deja)
        if (userCharacter == null) {
            chooseCharacter();
        }
        Random rand = new Random();

        map = Grid.generateGrid(rand.nextInt(3, 10), rand.nextInt(3, 10));

        // loop infinit pentru flow-ul jocului
        while (true) {
            printStats(userCharacter);

            // efectuarea modificarilor legate de currentCell (celula pe care se afla player-ul) inainte de procesarea
            // noii comenzi primite de la jucator
            map.getCurrentCell().setVisited();
            map.getCurrentCell().setEntityType(CellEntityType.VOID);

            // procesarea urmatoarei comenzi
            try {
                getNextCommand("");
            } catch (ImpossibleMove | InvalidCommandException e) {
                System.out.println(e.getMessage());
            }

            // efectuarea unor actiuni in functie de tipul celulei pe care a ajuns player-ul
            if (map.getCurrentCell().getEntityType() == CellEntityType.SANCTUARY) {
                // daca a ajuns pe o celula de tip SANCTUARY:
                System.out.println("\nYou have reached a sanctuary!");
                // regenerarea vietii cu o valoare random mai mare decat cea curenta, care nu depaseste valoarea maxima
                userCharacter.regenerateLife();
                System.out.println("Life: " + userCharacter.getCurrentLife());

                // similar, regenerarea manei cu o valoare random
                userCharacter.regenerateMana();
                System.out.println("Mana: " + userCharacter.getCurrentMana() + "\n");
            }

            if (map.getCurrentCell().getEntityType() == CellEntityType.PORTAL) {
                // daca ajunge pe o celula de tip PORTAL:
                // updatarea nivelului, a punctelor de experienta si a numarului de jocuri ale user-ului
                userCharacter.nextLevel();
                account.setNrGames(account.getNrGames() + 1);
                System.out.print("Congrats! You've finished this level! ");
                System.out.println("So far, you've played " + account.getNrGames() + " games!" + "\n");
                System.out.println("After completing this level, your character's stats look like this: ");
                System.out.println(userCharacter + "\n");
                // resetarea hartii
                userCharacter = null;
                gameLogic();
            }

            if (map.getCurrentCell().getEntityType() == CellEntityType.ENEMY) {
                // daca ajunge pe o celula de tip ENEMY:
                Enemy enemy = new Enemy(userCharacter.getMaxLife(), userCharacter.getMaxMana());
                System.out.println("AN ENEMY CHALLENGES YOU TO FIGHT!");
                System.out.println("Enemy life: " + enemy.getCurrentLife());
                System.out.println("Enemy mana: " + enemy.getCurrentMana() + "\n");

                fightMode(userCharacter, enemy);

                if (userCharacter.getCurrentLife() <= 0) {
                    // daca jucatorul a pierdut, se declanseaza un nou joc
                    System.out.println("Game over!! You lost! :(\n");
                    // atribuirea unor noi valori pentru life & mana ale personajului curent
                    userCharacter.regenerateLife();
                    userCharacter.regenerateMana();
                    // se porneste un joc nou
                    userCharacter = null;
                    gameLogic();
                }
            }

            // marcarea celulei pe care se afla jucatorul acum
            map.getCurrentCell().setEntityType(CellEntityType.PLAYER);
        }
    }
}
