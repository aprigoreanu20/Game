import org.example.entities.CellEntityType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.Random;

public class GamePage extends JFrame implements ActionListener {
    JPanel mapPanel = new JPanel();
    ArrayList<ArrayList<JLabel>> cellLabels = new ArrayList<>();

    // butoane pentru deplasarea personajului pe tabla de joc
    JButton eastButton = new JButton("East");
    JButton northButton = new JButton("North");
    JButton southButton = new JButton("South");
    JButton westButton = new JButton("West");
    JButton quitButton = new JButton("Quit");

    // label pentru afisarea atributelor personajului
    JLabel statsLabel = new JLabel("");
    // label pentru afisarea unui mesaj despre celula pe care se afla personajul
    JLabel messageLabel = new JLabel();

    // icons pentru afisarea celulelor
    ImageIcon questionIcon;
    ImageIcon sanctuaryIcon;
    ImageIcon enemyIcon;

    public GamePage() {
        super("Game");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // obtinerea instantei unice a clasei Game
        Game.game = Game.getGameInstance();

        Random rand = new Random();
        // generarea hartii de joc
        Game.game.map = Grid.generateGrid(rand.nextInt(3, 10), rand.nextInt(3, 10));

        mapPanel.setLayout(new GridLayout(Game.game.map.getWidth(), Game.game.map.getLength()));

        // setarea iconitelor de pe celule
        int labelWidth = 30;
        int labelHeight = 30;

        // obtinerea unor obiecte de tip ImageIcon folosind imaginile din folderul resources
        ImageIcon icon = new ImageIcon("src/resources/question.png");
        Image image = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        questionIcon = new ImageIcon(image);

        icon = new ImageIcon("src/resources/heart.jpg");
        image = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        sanctuaryIcon = new ImageIcon(image);

        icon = new ImageIcon("src/resources/enemy.jpeg");
        image = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        enemyIcon = new ImageIcon(image);

        // afisarea hartii la inceputul jocului
        for (int i = 0; i < Game.game.map.getWidth(); i++) {
            cellLabels.add(new ArrayList<>());
            for (int j = 0; j < Game.game.map.getLength(); j++) {
                JLabel label = new JLabel("");
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setBackground(Color.GRAY);
                label.setOpaque(true);

                // initial, toate celulele sunt marcate cu "?"
                label.setIcon(questionIcon);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);

                cellLabels.get(i).add(label);
                mapPanel.add(label);
            }
        }
        // marcarea celulei pe care se afla player-ul
        showCurrentCell();

        add(mapPanel, BorderLayout.CENTER);

        // panel pentru afisarea butoanelor de deplasare si a butonului "QUIT"
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 1, 5,5));
        buttonsPanel.add(northButton);
        buttonsPanel.add(eastButton);
        buttonsPanel.add(southButton);
        buttonsPanel.add(westButton);
        buttonsPanel.add(quitButton);

        // activarea butoanelor
        eastButton.addActionListener(this);
        westButton.addActionListener(this);
        southButton.addActionListener(this);
        northButton.addActionListener(this);
        quitButton.addActionListener(this);

        add(buttonsPanel, BorderLayout.EAST);

        // panel pentru afisarea atributelor pentru player
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

        statsLabel.setText("<html>" + Game.userCharacter.getName() + "(" + Game.userCharacter.getType() +
                "):<br>Level: " + Game.userCharacter.getLevel() + "<br>Experience: " + Game.userCharacter.getExperience()
                + "<br>Life: " + Game.userCharacter.getCurrentLife() + "<br>Mana: " + Game.userCharacter.getCurrentMana()
                +"</html");
        southPanel.add(statsLabel);
        southPanel.add(messageLabel);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
        show();
    }

    // metoda pentru afisarea datelor despre player
    public void setStatsLabel() {
        if (Game.userCharacter != null) {
            statsLabel.setText("<html>" + Game.userCharacter.getName() + "(" + Game.userCharacter.getType() +
                    "):<br>Level: " + Game.userCharacter.getLevel() + "<br>Experience: " + Game.userCharacter.getExperience()
                    + "<br>Life: " + Game.userCharacter.getCurrentLife() + "<br>Mana: " + Game.userCharacter.getCurrentMana()
                    + "</html");
            statsLabel.setVisible(true);
        }
    }

    // metoda pentru initierea unei actiuni pe baza tipului de celula pe care se afla player-ul
    public void processCell() {
        Cell currentCell = Game.game.map.getCurrentCell();

        if (currentCell.getEntityType() == CellEntityType.SANCTUARY) {
            // parametrul life primeste o valoare random, mai mare decat cea curenta
            Game.userCharacter.regenerateLife();
            // similar, regenerarea manei cu o valoare random
            Game.userCharacter.regenerateMana();

            // afisarea unui mesaj
            messageLabel.setText("<html>You have reached a sanctuary! <br>Life: " + Game.userCharacter.getCurrentLife()
                    + "<br>Mana: " + Game.userCharacter.getCurrentMana() + "</html>");
            messageLabel.setForeground(Color.BLUE);
            messageLabel.setVisible(true);
        }

        if (currentCell.getEntityType() == CellEntityType.ENEMY) {
            // initiere unei lupte intre player si un inamic
            FightPage fightPage = new FightPage(this);
        }

        if (currentCell.getEntityType() == CellEntityType.PORTAL) {
            // updatarea statisticilor pentru user odata cu trecerea la un nivel superior
            Game.userCharacter.nextLevel();
            Game.account.setNrGames(Game.account.getNrGames() + 1);

            // initierea unui nou joc
            CompletedLevel completedLevel = new CompletedLevel();
            setVisible(false);
        }
    }

    // marcarea celulei curente prin colorarea cu albastru
    public void showCurrentCell() {
        int currentCellX = Game.game.map.getCurrentCell().getX();
        int currentCellY = Game.game.map.getCurrentCell().getY();
        cellLabels.get(currentCellX).get(currentCellY).setBackground(Color.BLUE);
        cellLabels.get(currentCellX).get(currentCellY).setIcon(null);
    }

    // marcarea celulelor vizitate prin alb
    public void markVisitedCell() {
        int currentCellX = Game.game.map.getCurrentCell().getX();
        int currentCellY = Game.game.map.getCurrentCell().getY();
        cellLabels.get(currentCellX).get(currentCellY).setBackground(Color.WHITE);
        cellLabels.get(currentCellX).get(currentCellY).setIcon(null);
    }

    // marcarea unei celule vizitate cu iconite pentru a evidentia tipul inital al celulei
    public void markCell() {
        CellEntityType cellType = Game.game.map.getCurrentCell().getEntityType();
        CellEntityType initialType = Game.game.map.getCurrentCell().getInitialEntityType();

        int currentCellX = Game.game.map.getCurrentCell().getX();
        int currentCellY = Game.game.map.getCurrentCell().getY();

        JLabel cellLabel = cellLabels.get(currentCellX).get(currentCellY);
        ImageIcon icon;
        int labelWidth = 40;
        int labelHeight = 40;

        // marcarea celulelor prin setarea background-ului alb
        if (cellType == CellEntityType.VOID) {
            markVisitedCell();
        }

        // afisarea imaginii pentru enemy pe celula vizitata
        if (cellType == CellEntityType.ENEMY || initialType == CellEntityType.ENEMY) {
            cellLabel.setIcon(enemyIcon);
            cellLabel.setBackground(Color.WHITE);
        }

        // afisarea imaginii pentru sanctuar pe celula vizitata
        if (cellType == CellEntityType.SANCTUARY || initialType == CellEntityType.SANCTUARY) {
            cellLabel.setIcon(sanctuaryIcon);
            cellLabel.setBackground(Color.WHITE);
        }

        if (cellType == CellEntityType.PLAYER) {
            markVisitedCell();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // iesirea din joc la apasarea butonului "QUIT"
        if (e.getSource() == quitButton) {
            System.exit(0);
        }

        // marcarea celulei pe care s-a aflat player-ul
        markCell();
        messageLabel.setText("");

        // preluarea urmatoarei comenzi prin identificarea butonului apasat
        String command = "";
        if (e.getSource() == eastButton) {
            command = "EAST";
        }
        if (e.getSource() == northButton) {
            command = "NORTH";
        }
        if (e.getSource() == southButton) {
            command = "SOUTH";
        }
        if (e.getSource() == westButton) {
            command = "WEST";
        }

        // deplasarea jucatorului
        try {
            Game.game.getNextCommand(command);
        }
        catch (ImpossibleMove | InvalidCommandException ex) {
            // afisarea unui mesaj de eroare daca se incearca deplasarea in afara tablei de joc
            messageLabel.setText(ex.getMessage());
            messageLabel.setForeground(Color.RED);
            messageLabel.setVisible(true);
        }

        // marcarea celulei curente, realizarea unei actiuni in functie de tipul acesteia si afisarea datelor despre player
        showCurrentCell();
        processCell();
        setStatsLabel();

        // setarea celululei vizitate ca VOID
        Game.game.map.getCurrentCell().setVisited();
        Game.game.map.getCurrentCell().setEntityType(CellEntityType.VOID);
    }
}
