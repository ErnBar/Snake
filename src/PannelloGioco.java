import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class PannelloGioco extends JPanel implements ActionListener {


    // Costanti per le dimensioni del pannello di gioco e delle unità
    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    // Array per le coordinate x e y del serpente
    final int x[] = new int[NUMBER_OF_UNITS];
    final int y[] = new int[NUMBER_OF_UNITS];

    // Lunghezza iniziale del serpente, cibo mangiato, coordinate del cibo
    int length = 2;
    int foodEaten;
    int foodX;
    int foodY;
    // Direzione del serpente
    char direction = 'D';
    // Variabile per indicare se il gioco è in esecuzione
    boolean running = false;
    // Oggetto per generare numeri casuali
    Random random;
    // Timer per controllare il movimento del serpente
    Timer timer;

    // Costruttore del pannello di gioco
    PannelloGioco() {
        random = new Random();
        // Imposta le dimensioni e il colore di sfondo del pannello
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        // Il pannello può ottenere il focus per poter gestire gli eventi da tastiera
        this.setFocusable(true);
        // Aggiunge un KeyListener per ascoltare gli eventi da tastiera
        this.addKeyListener(new MyKeyAdapter());
        // Avvia il gioco
        play();
    }

    // Metodo per avviare il gioco
    public void play() {
        // Aggiunge il cibo e imposta il gioco in esecuzione
        addFood();
        running = true;

        // Crea un timer per controllare il movimento del serpente
        timer = new Timer(100, this);
        timer.start();
    }

    // Metodo per disegnare il pannello di gioco
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    // Metodo per gestire il movimento del serpente e gli eventi di gioco
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (running) {
            move();
            checkFood();
            checkHit();
        }
        repaint();
    }

    // Metodo per muovere il serpente
    public void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i-1];// Sposta la coordinata x del segmento i-1 alla posizione del segmento i
            y[i] = y[i-1]; // Sposta la coordinata y del segmento i-1 alla posizione del segmento i
        }

        if (direction == 'L') {
            x[0] = x[0] - UNIT_SIZE; // Muove la testa del serpente verso sinistra
        } else if (direction == 'R') {
            x[0] = x[0] + UNIT_SIZE; // Muove la testa del serpente verso destra
        } else if (direction == 'U') {
            y[0] = y[0] - UNIT_SIZE; // Muove la testa del serpente verso l'alto
        } else {
            y[0] = y[0] + UNIT_SIZE; // Muove la testa del serpente verso il basso
        }
    }

    // Metodo per verificare se il serpente ha mangiato il cibo
    public void checkFood() {
        if(x[0] == foodX && y[0] == foodY) {
            length++;
            foodEaten++;
            addFood();
        }
    }

    // Metodo per disegnare il pannello di gioco
    public void draw(Graphics graphics) {
        if (running) {
            graphics.setColor(new Color(236, 64, 18));
            graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            graphics.setColor(Color.white);
            graphics.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

            for (int i = 1; i < length; i++) {
                graphics.setColor(new Color(56, 200, 40));
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            graphics.setColor(Color.white);
            graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());

        } else {
            gameOver(graphics);
        }
    }

    // Metodo per aggiungere il cibo in posizioni casuali
    public void addFood() {
        foodX = random.nextInt((int)(WIDTH / UNIT_SIZE))*UNIT_SIZE;
        foodY = random.nextInt((int)(HEIGHT / UNIT_SIZE))*UNIT_SIZE;
    }

    // Metodo per verificare se il serpente ha colpito qualcosa
    public void checkHit() {
        // Controlla se il serpente ha colpito il proprio corpo
        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        // Controlla se il serpente ha colpito i bordi del pannello
        if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
            running = false;
        }

        // Ferma il timer se il gioco è finito
        if(!running) {
            timer.stop();
        }
    }

    // Metodo per visualizzare il messaggio di Game Over
    public void gameOver(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());
    }

    // Classe interna per gestire gli eventi da tastiera
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
