import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// pagina pentru alegerea tipului de joc (harta hardcodata / generata aleatoriu)
public class StartPage extends JFrame implements ActionListener {
    JLabel label = new JLabel("Welcome! Please login", JLabel.CENTER);
    JButton gameButton = new JButton("Login");
    JButton exitButton = new JButton("Exit");
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    public StartPage() {
        super("Start");
        setLayout(new GridLayout(2, 1));
        setSize(500,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameButton.setSize(200, 100);
        gameButton.addActionListener(this);

        exitButton.setSize(200,100);
        exitButton.addActionListener(this);

        label.setFont(label.getFont().deriveFont(Font.BOLD, 20));
        panel1.add(label);

        panel2.add(gameButton);
        panel2.add(exitButton);

        add(panel1);
        add(panel2);

        show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameButton) {
            LoginPage loginPage = new LoginPage();
            setVisible(false);
        }
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
