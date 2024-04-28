import javax.swing.JFrame;

public class FinestraGioco extends JFrame {

    private static final long serialVersionUID = 1L;

    FinestraGioco() {
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        PannelloGioco panel = new PannelloGioco();
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new FinestraGioco();
    }
}

