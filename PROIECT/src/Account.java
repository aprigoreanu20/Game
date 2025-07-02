import org.example.entities.characters.Characters;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Account {
    private Information info;
    private ArrayList<org.example.entities.characters.Characters> characters;
    private int nrGames;

    public static class Information {
        private Credentials credentials;
        private SortedSet<String> favoriteGames;
        private String playerName, country;

        // constructor pentru Information, pe baza unui obiect Builder
        private Information(Builder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.playerName = builder.playerName;
            this.country = builder.country;
        }

        // clasa interna pentru Builder Design Pattern
        public static class Builder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String playerName, country;

            // metode pentru setarea atributelor
            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder setFavouriteGames(SortedSet<String> favoriteGames) {
                if (favoriteGames != null) {
                    this.favoriteGames = new TreeSet<>(favoriteGames);
                }
                else {
                    this.favoriteGames = new TreeSet<>();
                }
                return this;
            }

            public Builder setPlayerName(String playerName) {
                this.playerName = playerName;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            // metoda build din Builder Design Pattern - folosita pentru instantierea unui
            // obiect Information pe baza unui obiect Builder
            public Information build() {
                return new Information(this);
            }
        }

        // getters
        public Credentials getCredentials() {
            return credentials;
        }

        public SortedSet<String> getFavoriteGames() {
            return favoriteGames;
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getCountry() {
            return country;
        }

        public void printFavoriteGames() {
            for (String game : favoriteGames) {
                System.out.println(game);
            }
            System.out.print("\n");
        }
    }

    // constructor
    public Account(ArrayList<org.example.entities.characters.Characters> characters, int nrGames, Information info) {
        this.characters = characters;
        this.nrGames = nrGames;
        this.info = info;
    }

    // getters
    public Information getInfo() {
        return info;
    }

    public ArrayList<Characters> getCharacters() {
        return characters;
    }

    public int getNrGames() {
        return nrGames;
    }

    // setters
    public void setInfo(Information info) {
        this.info = info;
    }

    public void setCharacters(ArrayList<org.example.entities.characters.Characters> characters) {
        this.characters = characters;
    }

    public void setNrGames(int nrGames) {
        this.nrGames = nrGames;
    }

    // metoda pentru afisarea personajelor unui user
    public void printCharacterList() {
        System.out.println("These are your characters:");
        int index = 1;
        for (Characters character : getCharacters()) {
            System.out.println(index + ". " + character);
            index++;
        }
    }

    // metoda pentru gasirea unui personaj dupa nume
    public Characters findCharacterByName(String name) {
        for (Characters character : getCharacters()) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }
}
