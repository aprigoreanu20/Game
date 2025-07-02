import org.example.entities.characters.Characters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChooseCharacter extends JFrame implements ActionListener {
    JLabel messageLabel = new JLabel();

    // grup care contine butoanele de selectare a personajelor
    JButton chooseCharacterButton = new JButton("Choose This Character");
    ButtonGroup group = new ButtonGroup();
    // lista pentru a retine butoanele de tip radio pentru selectarea personaje
    ArrayList<JRadioButton> radioButtons = new ArrayList<>();

    // label pentru afisarea datelor pentru fiecare personaj
    JLabel characterStats = new JLabel();
    Characters selectedCharacter;

    public ChooseCharacter() {
        super("Choose Character");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // unica instanta a clasei Game
        Game.game = Game.getGameInstance();

        // crearea unui panel pentru afisarea componentelor paginii
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // afiseaza mesaj pentru user
        messageLabel.setLayout(new BoxLayout(messageLabel, BoxLayout.X_AXIS));
        messageLabel.setText("<html><h1>Welcome " + Game.account.getInfo().getPlayerName() + "!" +"</h1>Please choose your characters:<br></html>");
        messageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        messageLabel.setForeground(Color.BLUE);
        panel.add(messageLabel);

        // se afiseaza cate un buton pentru fiecare personaj
        for (Characters character : Game.account.getCharacters()) {
            JRadioButton radioButton = new JRadioButton(character.getName());
            radioButtons.add(radioButton);
            group.add(radioButton);
            radioButton.addActionListener(this);
            panel.add(radioButton);
        }
        characterStats.setVisible(false);
        panel.add(characterStats);

        chooseCharacterButton.addActionListener(this);
        chooseCharacterButton.setVisible(false);
        panel.add(chooseCharacterButton);
        add(panel);

        show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chooseCharacterButton) {
            // userul a selectat un personaj prin apasarea butonului chooseCharacterButton
            if (selectedCharacter != null) {
                Game.userCharacter = selectedCharacter;
            }
            setVisible(false);

            // pornirea paginii pentru jocul propriu zis
            GamePage gamePage = new GamePage();
            return;
        }

        if (radioButtons.contains((e.getSource()))) {
            // cand userul selecteaza unul dintre butoanele de tip radio, sunt afisate
            // informatii despre personajul asociat butonului
            JRadioButton radioButton = (JRadioButton) e.getSource();

            String characterName = radioButton.getText();
            selectedCharacter = Game.account.findCharacterByName(characterName);

            characterStats.setText("<html>" + selectedCharacter.getName() + "(" + selectedCharacter.getType() + "):<br>" +
                    "Experience: " + selectedCharacter.getExperience() + "<br>Level: " + selectedCharacter.getLevel() + "<br><br></html>");
            characterStats.setVisible(true);
            chooseCharacterButton.setVisible(true);
        }
    }
}
