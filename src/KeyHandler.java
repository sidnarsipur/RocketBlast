import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class KeyHandler implements KeyListener, MouseListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean upReleased, downReleased, leftReleased, rightReleased;
    public int keyCode;

    public GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.levelManager.gameLevels[gp.levelManager.currentLevel].running) {
                leftPressed = true;
                setKeyCode(e);
            }
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if(gp.levelManager.gameLevels[gp.levelManager.currentLevel].running) {
                rightPressed = true;
                setKeyCode(e);
            }
        }
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = true;
            setKeyCode(e);
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = true;
            setKeyCode(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftReleased = true;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightReleased = true;
        }
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upReleased = true;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downReleased = true;
        }
    }

    public void setKeyCode(KeyEvent e){
        int keyCode = e.getKeyCode();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       checkMouse(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        checkMouse(e);
    }

    public void checkMouse(MouseEvent e){

        if(e.getX() >= 500 && e.getX() <= 710 && e.getY() >= 340 && e.getY() <= 400 && Objects.equals(gp.levelManager.gameState, "Start")){
            gp.levelManager.gameState = "Game";
        }
        if(e.getX() >= 480 && e.getX() <= 727 && e.getY() >= 443 && e.getY() <= 493 && Objects.equals(gp.levelManager.gameState, "GameOver")){
            gp.levelManager.gameState = "Game";
            gp.levelManager.gameLevels[gp.levelManager.currentLevel].running = true;
        }
        if(e.getX() >= 499 && e.getX() <= 700 && e.getY() >= 434 && e.getY() <= 489 && Objects.equals(gp.levelManager.gameState, "levelComplete")){
            gp.levelManager.gameState = "Game";
            gp.levelManager.gameLevels[gp.levelManager.currentLevel].running = true;
        }
        if(Objects.equals(gp.levelManager.gameState, "Win")) {
            if (e.getX() >= 572 && e.getX() <= 789 && e.getY() >= 435 && e.getY() <= 491) {
                gp.levelManager.currentLevel = 0;
                gp.levelManager.setDefault(gp.levelManager.currentLevel);
                gp.levelManager.restartDefault(gp.levelManager.currentLevel);
                gp.restartLevel();
                gp.levelManager.gameLevels[gp.levelManager.currentLevel].running = true;
                gp.levelManager.gameState = "Game";
            }
            if (e.getX() >= 505 && e.getX() <= 693 && e.getY() >= 538 && e.getY() <= 588) {
                System.exit(0);
            }
        }

        if(Objects.equals(gp.levelManager.gameState, "Start")) {
            if (e.getX() >= 466 && e.getX() <= 759 && e.getY() >= 430 && e.getY() <= 491) {
                gp.levelManager.gameState = "Rules";
            }
        }
        if(Objects.equals(gp.levelManager.gameState, "RulesDrawn")) {
                gp.levelManager.gameState = "Start";
        }
        }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
