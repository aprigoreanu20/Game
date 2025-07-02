import org.example.entities.characters.Enemy;
import org.spells.Spell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

// pagina pentru lupta dintre personaj si inamic
public class FightPage extends JDialog implements ActionListener {
    // inamicul personajului
    Enemy enemy;
    // atribut pentru retinerea paginii din care s-a creat aceasta pagina
    JFrame frame;

    // panou pentru afisarea personajului - in stanga ecranului
    JPanel leftPanel = new JPanel();
    JLabel playerImage = new JLabel();
    JLabel playerStats = new JLabel();

    // panou pentru afisarea butoanelor pentru atac / folosire abilitate
    JPanel centerPanel = new JPanel();
    JButton attackButton = new JButton("    Attack!     ");
    JButton abilitiesButton = new JButton("Use abilities");

    // panou pentru afisarea inamicului - in dreapta ecranului
    JPanel rightPanel = new JPanel();
    JLabel enemyImage = new JLabel();
    JLabel enemyStats = new JLabel();

    // componente pentru afisarea mesajelor legate de atacurile din lupta
    JLabel messageLabel1 = new JLabel();
    JLabel messageLabel2 = new JLabel();
    JPanel messagePanel = new JPanel();

    // panou pentru afisarea abilitatilor
    JPanel abilitiesPanel = new JPanel();
    ArrayList<JButton> selectButtons = new ArrayList<>();

    public FightPage(JFrame frame) {
        super(frame, "Fight Page", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(500, 800);
        setLayout(new BorderLayout());

        // clasa extinde JDialog pentru a crea o dependenta intre pagina de joc si cea de lupta
        this.frame = frame;

        // fereastra de lupta nu poate fi inchisa inainte de terminarea unei lupte
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (Game.userCharacter.getCurrentLife() <= 0 || enemy.getCurrentLife() <= 0) {
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Please finish the fight", "Denied", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // instantierea unui inamic
        enemy = new Enemy(Game.userCharacter.getMaxLife(), Game.userCharacter.getMaxMana());
        // generarea unor abilitati pentru personaj
        Game.userCharacter.generateAbilities();

        // afisarea jucatorului
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
//        playerImage.setSize(150, 500);
        playerImage.setPreferredSize(new Dimension(150, 150));

        // afisarea unei imagini pentru player
        ImageIcon icon = new ImageIcon(Game.userCharacter.getImagePath());
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        playerImage.setIcon(new ImageIcon(image));
        leftPanel.add(playerImage);

        // afisarea unor statistici despre player
        playerStats.setSize(150, 150);
        playerStats.setText("<html><h3>" + Game.userCharacter.getName() + "</h3>Life: " +
                Game.userCharacter.getCurrentLife() + "<br>Mana: " + Game.userCharacter.getCurrentMana() + "</html");
        playerStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(playerStats);

        // butoane pentru atac / folosire abilitate
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        attackButton.addActionListener(this);
        abilitiesButton.addActionListener(this);
        centerPanel.add(Box.createVerticalStrut(50));

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(attackButton);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(abilitiesButton);

        // afisarea unei imagini pentru inamic
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        enemyImage.setPreferredSize(new Dimension(150, 150));
        icon = new ImageIcon(enemy.getImagePath());
        image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        enemyImage.setIcon(new ImageIcon(image));
        enemyImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(enemyImage);

        // afisarea unor statistici despre inamic
        enemyStats.setSize(150, 150);
        enemyStats.setText("<html><h3>Enemy </h3>Life: " +
                enemy.getCurrentLife() + "<br>Mana: " + enemy.getCurrentMana() + "</html");
        enemyStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(enemyStats);

        // crearea panoului pentru afisarea abilitatilor, vizibil doar dupa apasarea butonului "Use ability"
        abilitiesPanel.setLayout(new GridLayout(2, 3));
        displayAbilities();
        abilitiesPanel.setVisible(false);

        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.add(messageLabel1);
        messagePanel.add(messageLabel2);

        add(messagePanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(abilitiesPanel, BorderLayout.SOUTH);
        show();
    }

    // metoda pentru afisarea abilitatilor
    public void displayAbilities() {
        ArrayList<Spell> abilities = Game.userCharacter.getAbilities();
        abilitiesPanel.removeAll();
        selectButtons = new ArrayList<>();
        JLabel infoAbilities = new JLabel();

        // afisarea unui mesaj in cazul in care jucatorul nu are nicio abilitate
        if (abilities.isEmpty()) {
            infoAbilities.setText("<html><div style='margin-top: 100px;'>You have no abilities! Please use normal attack</div></html>");
            infoAbilities.setVisible(true);
            abilitiesPanel.add(infoAbilities);
        }

        // afisarea abilitatilor
        for (Spell s : abilities) {
            JPanel spellPanel = new JPanel();
            spellPanel.setLayout(new BoxLayout(spellPanel, BoxLayout.Y_AXIS));
            spellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            spellPanel.setPreferredSize(new Dimension(150, 200));

            // afisarea unor imagini in functie de tipul abilitatii
            JLabel spellImage = new JLabel();
            ImageIcon icon = new ImageIcon(s.getImagePath());
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            spellImage.setIcon(new ImageIcon(image));
            spellPanel.add(spellImage);

            // afisarea unor date despre abilitati
            JLabel spellStats = new JLabel();
            spellStats.setText("<html>Damage: " + s.getDamageInflicted() + "<br>Mana: " + s.getRequiredMana() + "</html>");
            spellStats.setAlignmentX(Component.CENTER_ALIGNMENT);
            spellPanel.add(spellStats);

            // crearea unui buton de selectare pentru fiecare abilitate (butoanele sunt adaugate unei liste pentru
            // identificarea abilitatii pe care jucatorul doreste sa o foloseasca)
            JButton selectButton = new JButton("SELECT");
            selectButton.addActionListener(this);
            selectButtons.add(selectButton);
            spellPanel.add(selectButton);

            abilitiesPanel.add(spellPanel);
        }
        abilitiesPanel.setVisible(true);
    }

    // afisarea datelor despre inamic
    public void displayEnemyStats() {
        enemyStats.setText("<html><h3>Enemy </h3>Life: " +
                enemy.getCurrentLife() + "<br>Mana: " + enemy.getCurrentMana() + "</html");
        enemyStats.setVisible(true);
    }

    // afisarea datelor despre personaj
    public void displayPlayerStats() {
        playerStats.setText("<html><h3>" + Game.userCharacter.getName() + "</h3>Life: " +
                Game.userCharacter.getCurrentLife() + "<br>Mana: " + Game.userCharacter.getCurrentMana() + "</html");
    }

    // aplicarea unui atac normal asupra inamicului
    public void normalAttack() {
        int damage = Game.userCharacter.getDamage();
        enemy.receiveDamage(damage);
    }

    // atacul din partea inamicului asupra personajului
    public void enemyAttack() {
        Random rand = new Random();

        // generarea unei abilitati random
        int randomAbilityIndex = rand.nextInt(enemy.getAbilities().size());
        Spell enemyAbility = enemy.getAbilities().get(randomAbilityIndex);

        if (enemy.useAbility(enemyAbility, Game.userCharacter)) {
            // scaderea manei inamicului si stergerea abilitatii folosite din lista de abilitati a inamicului
            enemy.removeAbility(enemyAbility);
            enemy.setCurrentMana(enemy.getCurrentMana() - enemyAbility.getRequiredMana());

            // afisarea unui mesaj despre abilitatea folosita de inamic
            messageLabel2.setText("Enemy used this spell - " + enemyAbility);
            messageLabel2.setForeground(Color.RED);
            messageLabel2.setVisible(true);

            // patternul visitor
            Game.userCharacter.accept(enemyAbility);
        } else {
            // afisarea unui mesaj despre atacul inamicului
            messageLabel2.setText("<html>Enemy tried to use this spell - " + enemyAbility + "," +
                    "<br> but the spell was blocked!</html>");
            messageLabel2.setForeground(Color.BLUE);
            messageLabel2.setVisible(true);
        }

        // aplicarea damage-ului normal dat de inamic asupra personajului
        int damage = enemy.getDamage();
        Game.userCharacter.receiveDamage(damage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // daca jucatorul doreste sa foloseasca o abilitate, se afiseaza abilitatile
        if (e.getSource() == abilitiesButton) {
            messagePanel.setVisible(false);
            displayAbilities();
            abilitiesPanel.setVisible(true);
        }

        // cand jucatorul alege o abilitate
        JButton button = (JButton) e.getSource();
        if (selectButtons.contains(button)) {
            // identificarea abilitati dorite
            int index = selectButtons.indexOf(button);
            selectButtons.remove(button);
            Spell spell = Game.userCharacter.getAbilities().get(index);

            if (Game.userCharacter.useAbility(spell, enemy)) {
                // aplicarea abilitatii asupra inamicului prin patternul VISITOR
                enemy.accept(spell);
                Game.userCharacter.getAbilities().remove(index);

                // scaderea costului de mana pentru jucator
                int currentMana = Game.userCharacter.getCurrentMana();
                Game.userCharacter.setCurrentMana(currentMana - spell.getRequiredMana());

                // afisarea unui mesaj legat de abilitatea folosita de player
                messageLabel1.setText("Player used ability: " + spell);
                messageLabel1.setForeground(Color.GREEN);
                messageLabel1.setVisible(true);
                messagePanel.setVisible(true);
            } else {
                // afisarea unui mesaj daca abilitatea nu a putut fi folosita
                messageLabel1.setText("Your spell was blocked");
                messageLabel1.setForeground(Color.RED);
                messageLabel1.setVisible(true);
            }

            // atac normal din partea playerului asupra inamicului
            normalAttack();

            // atac din partea inamicului
            if (enemy.getCurrentLife() > 0)
                enemyAttack();

            messagePanel.setVisible(true);
            abilitiesPanel.setVisible(false);
        }

        // cand jucatorul alege atac normal
        if (button == attackButton) {
            normalAttack();
            abilitiesPanel.setVisible(false);
            messageLabel1.setText("You attacked!");
            messageLabel1.setForeground(Color.BLACK);
            messageLabel1.setVisible(true);
            if (enemy.getCurrentLife() > 0)
                enemyAttack();
        }

        // afisarea informatiilor despre inamic si player
        displayPlayerStats();
        displayEnemyStats();

        // verificare daca lupta s-a terminat
        if (Game.userCharacter.getCurrentLife() <= 0) {
            // daca personajul a pierdut, se afiseaza o pagina de final
            setVisible(false);
            frame.setVisible(false);

            // atribuirea unei noi valori pentru viata
            Game.userCharacter.regenerateLife();

            GameOver gameOver = new GameOver();
        }

        if (enemy.getCurrentLife() <= 0) {
            // daca inamicul a pierdut, poate continua jocul
            Game.userCharacter.incKilledEnemies();
            setVisible(false);
        }
    }
}
