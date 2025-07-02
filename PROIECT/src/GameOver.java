import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// pagina afisata cand jocul s-a terminat
public class GameOver extends JFrame implements ActionListener {
    JPanel panel = new JPanel();

    // afisarea unei imagini pentru jucator
    JLabel imageLabel = new JLabel();
    // afisarea informatiilor despre jucator
    JLabel infoLabel = new JLabel();
    String info;

    // butoane pentru inceperea unui joc nou / iesirea din joc
    JButton continueButton, quitButton;

    public GameOver() {
        super("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout(new FlowLayout());

        // setarea unui panel pentru organizarea elementelor paginii
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // setarea imaginii pentru personaj
        ImageIcon icon = new ImageIcon(Game.userCharacter.getImagePath());
        Image image = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setIcon(new ImageIcon(image));

        // afisarea informatiilor despre personaj
        infoLabel.setPreferredSize(new Dimension(150, 150));
        info = "Name: " + Game.userCharacter.getName() + "<br>Role: " +
                Game.userCharacter.getType() + "<br>Level: " + Game.userCharacter.getLevel() + "<br>Experience: " +
                Game.userCharacter.getExperience() + "<br>Killed enemies: " + Game.userCharacter.getKilledEnemies() +
                "<br></html>";
        infoLabel.setText("<html><h3>Game Over! You lost :(</h3>" + info);

        // adaugarea de butoane (quit pentru iesirea din joc, continue pentru inceperea unui nou joc)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        continueButton = new JButton("New Game");
        continueButton.addActionListener(this);
        buttonPanel.add(continueButton);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        panel.add(imageLabel);
        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(buttonPanel);

        add(panel);
        show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Game game = Game.getGameInstance();
        if (e.getSource() == continueButton) {
            // pornirea unui joc nou
            setVisible(false);

            // userul isi poate alege un alt personaj
            ChooseCharacter chooseCharacter = new ChooseCharacter();
        }
        if (e.getSource() == quitButton) {
            // iesirea din joc
            System.exit(0);
        }
    }
}
