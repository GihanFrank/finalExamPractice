import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Shapes extends JPanel {
    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 270;
    private boolean[] cell;
    private int size;
    private int rule;
    private boolean[] ttable;
   
    public Shapes(int size, int rule) {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.size = size;
        this.rule = rule;
        ttable = new boolean[8];
        cell = new boolean[size];
        cell[size / 2] = true;
        int x = this.rule;
        for (int i = 0; i < 8; i++) {
            if (x % 2 == 0) {
                this.ttable[i] = false;
                x = (x / 2);
            } else {
                this.ttable[i] = true;
                x = ((x - 1) / 2);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        
        for (int n = 0; n < 1; n++) {
            int count = 0;
            boolean[] temp = new boolean[this.size];

            for (int b = 0; b < this.cell.length; b++) {
                temp[b] = this.cell[b];

            }
            for (int b = 0; b < this.cell.length; b++) {
                g2d.setColor(Color.WHITE);
                if (this.cell[b] && g2d.getColor().equals(Color.WHITE)) {
                    
                    for (int c = 0; c < WINDOW_HEIGHT; c += 10) {
                        for (int j = 0; j < WINDOW_WIDTH; j += 10) {
                            
                                g2d.setColor(Color.BLACK);
                                System.out.println("y");
                                Rectangle2D r = new Rectangle2D.Double(j, c, 10, 10);
                                g2d.fill(r);
                            } }}
                            
                                // Rectangle2D r = new Rectangle2D.Double(j, c, 10, 10);
                                // g2d.fill(r);
                            
                        
                    
                else {g2d.setColor(Color.WHITE);
                    System.out.println("x");}
                    
                    
                    this.cell[b] = false;
                
                // System.out.print(" ");

            }
            System.out.println(" ");
            for (int i = 1; i < (this.size - 1); i++) {
                for (int k = 0; k < 8; k++) {

                    if (this.ttable[0] && !temp[i - 1] && !temp[i] && !temp[i + 1]) {
                        this.cell[i] = false;
                    } else if (this.ttable[1] && !temp[i - 1] && !temp[i] && temp[i + 1]) {
                        this.cell[i] = true;
                    } else if (this.ttable[2] && !temp[i - 1] && temp[i] && !temp[i + 1]) {
                        this.cell[i] = true;
                    } else if (this.ttable[3] && !temp[i - 1] && temp[i] && temp[i + 1]) {
                        this.cell[i] = true;
                    } else if (this.ttable[4] && temp[i - 1] && !temp[i] && !temp[i + 1]) {
                        this.cell[i] = true;
                    } else if (this.ttable[5] && temp[i - 1] && !temp[i] && temp[i + 1]) {
                        this.cell[i] = true;
                    } else if (this.ttable[6] && temp[i - 1] && temp[i] && !temp[i + 1]) {
                        this.cell[i] = true;
                    } else if (this.ttable[7] && temp[i - 1] && temp[i] && temp[i + 1]) {
                        this.cell[i] = true;
                    }
                }
            }
        }

    }

    public static void runApplication(JPanel app) {
        JFrame frame = new JFrame();
        frame.setSize(app.getPreferredSize());
        frame.setTitle(app.getClass().getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(app);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Shapes s = new Shapes(50, 30);
        runApplication(s);
    }
}