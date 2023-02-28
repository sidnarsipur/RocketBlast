import javax.swing.*;
import java.awt.*;

public class GameScreen {

    LevelManager levelManager;
    GamePanel gp;

    JLabel scoreLabel;
    Font font;

    public GameScreen(GamePanel gp, LevelManager levelManager){
        this.levelManager = levelManager;
        this.gp = gp;

        font = new Font("SF Pro", Font.BOLD, 20);

        scoreLabel = new JLabel();
    }

    public void update(){
    }

    public void draw(Graphics2D g2D){

        g2D.setFont(font);

        g2D.drawString("Score: " + levelManager.gameLevels[levelManager.currentLevel].score, 10, 20);
        g2D.drawString("Lives: " + levelManager.gameLevels[levelManager.currentLevel].lives, 10, 40);
        g2D.drawString("Fuel: " + levelManager.gameLevels[levelManager.currentLevel].fuel, 10, 60);
        g2D.drawString("Level: " + ((levelManager.currentLevel)+1), 10, 100);


    }
}
