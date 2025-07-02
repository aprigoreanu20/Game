import java.awt.*;
import java.awt.event.ActionEvent;

// pagina afisata cand personajul trece la un nivel superior
public class CompletedLevel extends GameOver {
    public CompletedLevel() {
        // aceasta clasa extinde GameOver pentru ca cele doua pagini au un aspect foarte asemanator
        super();
        setName("Completed Level");

        // afisarea unui mesaj si a datelor despre personaj
        infoLabel.setPreferredSize(new Dimension(150, 150));
        infoLabel.setText("<html><h3>Congrats! You completed this level!</h3>" + info);
        infoLabel.setVisible(true);

        continueButton.setText("Continue");
        show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Game game = Game.getGameInstance();
        if (e.getSource() == continueButton) {
            // continuarea jocului prin generarea unei joc nou
            setVisible(false);
            GamePage gamePage = new GamePage();
        }
        if (e.getSource() == quitButton) {
            // iesirea din joc
            System.exit(0);
        }
    }
}
