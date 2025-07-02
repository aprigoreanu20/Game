import org.example.entities.characters.Characters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// pagina pentru introducerea credentialelor
public class LoginPage extends JFrame  implements ActionListener {
    JButton submitButton = new JButton("Submit");
    JLabel emailLabel = new JLabel("Email   ", JLabel.CENTER);
    JLabel passwordLabel = new JLabel("Password", JLabel.CENTER);
    JTextField emailText = new JTextField(30);
    JPasswordField passwordText = new JPasswordField(30);
    ArrayList<Credentials> credentialsList;
    JLabel messageLabel = new JLabel("Please enter your credentials", JLabel.CENTER);
    Game game;

    public LoginPage() {
        super("Login Page");
        setSize(500,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // obtinerea listei cu credentialele din fisierul json pentru validarea datelor introduse de user
        game = Game.getGameInstance();

        // crearea unui panel pentru afisarea componentelor paginii
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // panel pentru email (label & text box)
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.X_AXIS));
        emailPanel.setPreferredSize(new Dimension(300, 20));
        emailLabel.setPreferredSize(new Dimension(300, 20));
        emailText.setPreferredSize(new Dimension(300, 20));
        emailPanel.add(emailLabel);
        emailPanel.add(emailText);
        panel.add(emailPanel);

        // panel pentru parola (label si text box)
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setPreferredSize(new Dimension(300, 20));
        passwordLabel.setPreferredSize(new Dimension(300, 20));
        passwordText.setPreferredSize(new Dimension(300, 20));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordText);
        panel.add(passwordPanel);

        // adaugarea butonului de SUBMIT
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue());
        panel.add(submitButton);
        panel.add(Box.createVerticalGlue());

        // messageLabel afiseaza un mesaj despre logarea in cont
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        add(panel);
        submitButton.addActionListener(this);

        show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // prelucrarea datelor introduse de user
            String email = emailText.getText();
            String password = passwordText.getText();

            Credentials credentials = new Credentials(email, password);
            if (game.chooseAccount(credentials)) {
                // daca datele introduse sunt corecte, userul trebuie sa aleaga personajul
                ChooseCharacter chooseCharacterPage = new ChooseCharacter();
                setVisible(false);
            } else {
                // afisarea unui mesaj de eroare daca datele introduse nu sunt corect
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Wrong email / password");
            }
        }
    }
}
