import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{

    public final int FPS = 60;

    public final int tileSize = 48;

    public final int maxScreenCol = 25;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public int timePassed = 0;
    public int seconds = 0;

    public Thread gameThread;

    public LevelManager levelManager = new LevelManager();

    Rocket rocket;
    ObjectManager gameObjects;
    GameScreen ui;
    Menu titleScreen;

    KeyHandler keyH = new KeyHandler(this);

    Image backgroundImage = new ImageIcon("Images/Space.png").getImage();

    public GamePanel() {

        rocket = new Rocket(this,keyH,levelManager);
        ui = new GameScreen(this, levelManager);
        gameObjects = new ObjectManager(this, levelManager);
        titleScreen = new Menu(this);

        gameObjects.drawnOnce = false;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = (double) 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread!=null){
            currentTime = System.nanoTime();

            delta+= (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >=1) {
                update();
                repaint();
                timePassed++;
                if(timePassed%60==0){
                    seconds++;
                    if(Objects.equals(levelManager.gameState, "Game")) {
                    levelManager.gameLevels[levelManager.currentLevel].timer++;
                    }
                }
                delta--;
            }
        }
    }

    public void nextLevel(){

        if(levelManager.gameLevels[levelManager.currentLevel].timer <= 100){
            levelManager.gameLevels[levelManager.currentLevel].score += 100 - levelManager.gameLevels[levelManager.currentLevel].timer + levelManager.gameLevels[levelManager.currentLevel].fuel;
        }

        levelManager.message = "Score: " + levelManager.gameLevels[levelManager.currentLevel].score;

        if(levelManager.currentLevel >= levelManager.gameLevels.length-1){
           levelManager.gameState = "Win";
        }
        else {
            levelManager.currentLevel++;
            levelManager.setDefault(levelManager.currentLevel);
            levelManager.restartDefault(levelManager.currentLevel);
        }
        rocket = new Rocket(this, keyH, levelManager);
        ui = new GameScreen(this, levelManager);
        gameObjects = new ObjectManager(this, levelManager);
        gameObjects.drawnOnce = false;
    }

    public void restartLevel(){

        if(!Objects.equals(levelManager.gameState, "Win")) {
            levelManager.gameLevels[levelManager.currentLevel].lives--;
        }

        if(levelManager.gameLevels[levelManager.currentLevel].lives == 0 || levelManager.gameLevels[levelManager.currentLevel].fuel <= 0){
            levelManager.restartDefault(levelManager.currentLevel);
            levelManager.gameState = "GameOver";
        }


        levelManager.setDefault(levelManager.currentLevel);
        rocket = new Rocket(this,keyH,levelManager);
        ui = new GameScreen(this, levelManager);
        gameObjects = new ObjectManager(this, levelManager);

        gameObjects.drawnOnce = false;
    }

    public void update(){
        rocket.update();
        ui.update();
    }


    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        if(Objects.equals(levelManager.gameState, "Game")) {
            g2.drawImage(backgroundImage,0,0,null);
            gameObjects.draw(g2);
            rocket.draw(g2);
            ui.draw(g2);
        }

        titleScreen.draw(g2);
        g2.dispose();
    }
}
