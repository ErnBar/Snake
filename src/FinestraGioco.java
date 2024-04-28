import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraGioco extends JFrame {

    private PannelloGioco panel;

    FinestraGioco() {
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Creazione del pannello di gioco
        panel = new PannelloGioco();
        this.add(panel);

        // Creazione e aggiunta del pulsante di riavvio
        JButton restartButton = new JButton("Riavvia");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        this.add(restartButton, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // Metodo per riavviare il gioco
    private void restartGame() {
        // Rimuovi il pannello esistente
        this.remove(panel);
        // Crea un nuovo pannello di gioco
        panel = new PannelloGioco();
        // Aggiungi il nuovo pannello al frame
        this.add(panel);
        // Ottieni il focus per il nuovo pannello di gioco
        panel.requestFocusInWindow();
        // Ridisegna il frame
        this.revalidate();
        // Centra il frame
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new FinestraGioco();
    }
}

