
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Menu {

    public String title = "Rocket Blast";

    public boolean blink = true;

    public Font gameFont;
    public Font menuFont;

    Timer timer;

    int count = 0;

    public Image rulesImage = new ImageIcon("Images/Rules.png").getImage();

    public GamePanel gp;

    public Menu(GamePanel gp){
        this.gp = gp;

        try {
            InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("Fonts/Space.ttf")));
            gameFont = Font.createFont(Font.TRUETYPE_FONT, is);
            InputStream ls = new BufferedInputStream(Files.newInputStream(Paths.get("Fonts/Menu.ttf")));
            menuFont = Font.createFont(Font.TRUETYPE_FONT, ls);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
         timer = new Timer(500, arg0 -> blink = !blink);

        timer.start();
    }

    public void draw(Graphics2D g2){

        if(Objects.equals(gp.levelManager.gameState, "Start")) {
            g2.setColor(Color.white);

            g2.setFont(gameFont.deriveFont(75f));
            g2.drawString(title, gp.screenWidth / 2 - 250, gp.screenHeight / 2 - 150);

            g2.setFont(menuFont.deriveFont(50f));
            if (blink) {
                g2.drawString("Start Game", gp.screenWidth / 2 - 192 / 2, gp.screenHeight / 2);
            }
            g2.drawString("Rules & Controls", (gp.screenWidth / 2) - 136, (gp.screenHeight / 2) + 100);
        }

        if(Objects.equals(gp.levelManager.gameState, "GameOver")) {

            g2.setColor(Color.white);

            g2.setFont(gameFont.deriveFont(75f));
            g2.drawString("Game Over!", gp.screenWidth / 2 - 215, gp.screenHeight / 2 - 150);

            g2.setFont(menuFont.deriveFont(50f));

            if(Objects.equals(gp.levelManager.message, "You ran out of lives!")){
                g2.drawString("You ran out of lives!", gp.screenWidth / 2 - 172, gp.screenHeight / 2);
            }
            else{
                g2.drawString(gp.levelManager.message, (gp.screenWidth / 2) - 167, 391 - 32);
            }

            if (blink) {
                g2.drawString("Restart Game", (gp.screenWidth / 2) - 118, (gp.screenHeight / 2) + 100);
            }
        }

        if(Objects.equals(gp.levelManager.gameState, "levelComplete")) {
            g2.setColor(Color.white);

            g2.setFont(gameFont.deriveFont(75f));
            g2.drawString("Level Complete!", gp.screenWidth / 2 - 305, gp.screenHeight / 2 - 150);

            g2.setFont(menuFont.deriveFont(50f));
            if(gp.levelManager.gameLevels[gp.levelManager.currentLevel].score >=100){
                g2.drawString(gp.levelManager.message, (gp.screenWidth / 2) - 89, 391-32);
            }
            else{
                g2.drawString(gp.levelManager.message, (gp.screenWidth / 2) - 88, 391-32);
            }
            g2.drawString("Next Level", (gp.screenWidth / 2) - 88, (gp.screenHeight / 2) + 100);

        }

        if(Objects.equals(gp.levelManager.gameState, "Win")) {
            g2.setColor(Color.white);

            g2.setFont(gameFont.deriveFont(75f));
            g2.drawString("Game Completed!", gp.screenWidth / 2 - 305, gp.screenHeight / 2 - 150);

            g2.setFont(menuFont.deriveFont(50f));
            g2.drawString(gp.levelManager.message, (gp.screenWidth / 2) - 79, 391-32);

            g2.drawString("Go Back To The Start", (gp.screenWidth / 2) - 176, (gp.screenHeight / 2) + 100);

            g2.drawString("Exit Game", (gp.screenWidth / 2) - 85, (gp.screenHeight / 2) + 200);
        }

        if(Objects.equals(gp.levelManager.gameState, "Rules") || Objects.equals(gp.levelManager.gameState, "RulesDrawn")) {
            count++;
            if(count>=120){
                gp.levelManager.gameState = "RulesDrawn";
                count = 0;
            }
            g2.setFont(gameFont.deriveFont(75f));
            g2.drawImage(rulesImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        }
    }

}
