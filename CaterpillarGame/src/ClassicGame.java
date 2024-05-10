import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class ClassicGame extends JPanel implements ActionListener {

 
	private static final int SCREEN_HEIGHT = 300;
	private static final int SCREEN = 300;
	private static final int SCREEN_WIDTH = 300;
	private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;
    private int score;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
     

    private int dots;
    private int leaf_x;
    private int leaf_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ivy;
    private Image ball;
    private Image leaf;
    private Image head;
    private Image headLeft;
    private Image headRight;
    private Image headUp;
    private Image headDown;
    private Image bodyHorizontal;
    private Image bodyVertical;

    private Font scoreFont;

    private boolean gameOver;


    public ClassicGame() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        scoreFont = new Font("Helvetica", Font.BOLD, 14);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon dotIcon = new ImageIcon("src/resources/dot.png");
        ball = dotIcon.getImage();

        ImageIcon leafIcon = new ImageIcon("src/resources/leaf.png");
        leaf = leafIcon.getImage();

        ImageIcon headLeftIcon = new ImageIcon("src/resources/headLeft.png");
        headLeft = headLeftIcon.getImage();

        ImageIcon headRightIcon = new ImageIcon("src/resources/headRight.png");
        headRight = headRightIcon.getImage();

        ImageIcon headUpIcon = new ImageIcon("src/resources/headUp.png");
        headUp = headUpIcon.getImage();

        ImageIcon headDownIcon = new ImageIcon("src/resources/headDown.png");
        headDown = headDownIcon.getImage();
        
        ImageIcon ivyIcon = new ImageIcon("src/resources/ivy.png");
        ivy = ivyIcon.getImage();

        ImageIcon bodyHorizontalIcon = new ImageIcon("src/resources/bodyHorizontal.png");
        bodyHorizontal = bodyHorizontalIcon.getImage();

        ImageIcon bodyVerticalIcon = new ImageIcon("src/resources/bodyVertical.png");
        bodyVertical = bodyVerticalIcon.getImage();
    }


    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateLeaf();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
          
                g.drawImage(leaf, leaf_x, leaf_y, this);               
                g.setColor(Color.white);
                g.setFont(new Font("Havatica", Font.BOLD,14));
                g.drawString("Score: "+ score,120,SCREEN_HEIGHT-20);

                
            for (int z = 0; z < dots; z++) {
                if (z == 0) {                  
                    if (leftDirection) {
                        g.drawImage(headLeft, x[z], y[z], this);
                    } 
                    else if (rightDirection) {
                        g.drawImage(headRight, x[z], y[z], this);
                    } 
                    else if (upDirection) {
                        g.drawImage(headUp, x[z], y[z], this);
                    } 
                    else if (downDirection) {
                        g.drawImage(headDown, x[z], y[z], this);
                    } 
                    } 
                
                else {                   
                    if (leftDirection || rightDirection) {
                        g.drawImage(bodyHorizontal, x[z], y[z], this);
                    } 
                    else if (upDirection || downDirection) {
                        g.drawImage(bodyVertical, x[z], y[z], this);
                    }
                    }
            }
            
         
        
         int componentWidth = getWidth();
         int componentHeight = getHeight();

         int gap = 1;

         int availableWidth = componentWidth - gap * 9;
         int availableHeight = componentHeight - gap * 9;


         int imageWidth = availableWidth / 20;
         int imageHeight = availableHeight / 20;

         for (int i = 0; i < 20; i++) {
             int x = gap * i + imageWidth * i;
             g.drawImage(ivy, x, 0, imageWidth, imageHeight, this);
         }
        
         for (int i = 0; i < 20; i++) {
             int x = gap * i + imageWidth * i;
             int y = componentHeight - imageHeight;
             g.drawImage(ivy, x, y, imageWidth, imageHeight, this);
         }

         for (int i = 0; i < 20; i++) {
             int y = gap * i + imageHeight * i;
             g.drawImage(ivy, 0, y, imageWidth, imageHeight, this);
         }

         for (int i = 0; i < 20; i++) {
             int x = componentWidth - imageWidth;
             int y = gap * i + imageHeight * i;
             g.drawImage(ivy, x, y, imageWidth, imageHeight, this);
         }
         
         Toolkit.getDefaultToolkit().sync();
        } 
        else {
            gameOver(g);
        }
    }


    private void gameOver(Graphics g) {
        inGame = false;
        timer.stop();

        SwingUtilities.invokeLater(() -> {
            EndingPanel endscene = new EndingPanel();
            endscene.startAnimation();

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.getContentPane().add(endscene);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    private void endGame() {
        timer.stop();

    }
    private void showLunaMoth() {
        JFrame boardFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        boardFrame.dispose();

        JFrame arkaPlanFrame = new JFrame();
        arkaPlanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        arkaPlanFrame.setSize(B_WIDTH, B_HEIGHT);
        arkaPlanFrame.setResizable(false);
        arkaPlanFrame.add(new LunaMoth());
        arkaPlanFrame.setLocationRelativeTo(null); 
        arkaPlanFrame.setVisible(true);
    }

  

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
       
      
        if (y[0] >= B_HEIGHT - 15 || y[0] < 15 || x[0] >= B_WIDTH - 15 || x[0] < 15) {
            inGame = false;
        }


        if (!inGame) {
            timer.stop();
    
        }    
        
    }
    private boolean isCollision(int ivyX, int ivyY) {
       
        if (x[0] == ivyX && y[0] == ivyY) {
            return true;
        }
        return false;
    }

   


    

    int panelWidth = 280;
    int panelHeight = 280;
    int maxPosX = panelWidth / DOT_SIZE;
    int maxPosY = panelHeight / DOT_SIZE;


    private void locateLeaf() {
        int r = (int) (Math.random() * maxPosX);
        leaf_x = r * DOT_SIZE;

        r = (int) (Math.random() * maxPosY);
        leaf_y = r * DOT_SIZE;

       
        boolean leafUnderIvy = false;
        for (int z = 0; z < dots; z++) {
            if (x[z] == leaf_x && y[z] == leaf_y) {
            	leafUnderIvy = true;
                break;
            }
        }
        
        boolean leafAtEdges = leaf_x == 0 || leaf_y == 0 || leaf_x == B_WIDTH - DOT_SIZE || leaf_y == B_HEIGHT - DOT_SIZE;

      
        while (leafUnderIvy || leafAtEdges) {
            r = (int) (Math.random() * maxPosX);
            leaf_x = r * DOT_SIZE;

            r = (int) (Math.random() * maxPosY);
            leaf_y = r * DOT_SIZE;

            leafUnderIvy = false;
            for (int z = 0; z < dots; z++) {
                if (x[z] == leaf_x && y[z] == leaf_y) {
                	leafUnderIvy = true;
                    break;
                }
            }

            leafAtEdges = leaf_x == 0 || leaf_y == 0 || leaf_x == B_WIDTH - DOT_SIZE || leaf_y == B_HEIGHT - DOT_SIZE;
        }
    }
    
    private void checkLeaf() {
        if ((x[0] == leaf_x) && (y[0] == leaf_y) ) {
        	
        		dots++;
                score++;
                locateLeaf();
        	}
        }
   


    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkLeaf();
            checkCollision();
            move();
            repaint();
        }
        if (gameOver) {
            showLunaMoth();
        }


    }
    

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT)) {
                leftDirection = true;
                rightDirection= false;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT)) {
            	leftDirection= false;
            	rightDirection = true;                
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) ) {
            	leftDirection= false;
            	rightDirection = false;                
                upDirection = true;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) ) {
            	leftDirection= false;
            	rightDirection = false;                
                upDirection = false;
                downDirection = true;
            }
        }
    }
}