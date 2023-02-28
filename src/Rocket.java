import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class Rocket extends PlayerControlledObject {

    GamePanel gp;
    KeyHandler keyH;
    LevelManager lm;

    boolean left_booster;
    boolean right_booster;
    boolean isAccelerating;
    boolean hitBoundary;

    String currentBooster;

    public int rotationAngle;
    int crashSpeed;
    int newX;
    int newY;

    int count = 0;

    int xIncrease = 5;
    int yIncrease = 10;
    int width = 39;
    int height = 50;

    float fastSpeedLimit = 4;

    public Rocket(GamePanel gp, KeyHandler keyH, LevelManager lm){
        this.gp = gp;
        this.keyH = keyH;
        this.lm = lm;

        checkCol();

        solidArea = new Rectangle(x,y,image.getWidth(null), image.getHeight(null));

        setDefault();
    }

    public void setDefault(){

        image = new ImageIcon("Images/Rocket.png").getImage();

        x = lm.gameLevels[lm.currentLevel].startPosition.x;
        y = lm.gameLevels[lm.currentLevel].startPosition.y;

        speedIncrease = 0.005f;
        gravity = 0.01f;
        initial_velocity = 2;
        speed = initial_velocity;
        terminal_velocity = 3;
        crashSpeed = 3;
        fastSpeedLimit = (terminal_velocity + initial_velocity)/2;

        left_booster = false;
        right_booster = false;
        isAccelerating = false;

        currentBooster = "none";

        rotationAngle = gp.levelManager.gameLevels[gp.levelManager.currentLevel].rotationAngle;
    }

    public void update() {

        if(gp.levelManager.gameLevels[gp.levelManager.currentLevel].running) {
            if (gp.timePassed % 60 == 0) {
                if (isAccelerating || left_booster || right_booster) {
                    lm.gameLevels[lm.currentLevel].fuel -= 1;
                    if(lm.gameLevels[lm.currentLevel].fuel <= 0){
                        gp.levelManager.gameLevels[gp.levelManager.currentLevel].running = false;
                        lm.message = "You ran out of fuel!";
                        gp.restartLevel();
                    }
                }
            }


            CollisionOn = false;

            if (keyH.leftPressed) {
                rotationAngle -= 15;
                lm.gameLevels[lm.currentLevel].fuel -= 0.001;
                if (rotationAngle <= -360) {
                    rotationAngle = 0;
                }
                currentBooster = "left";
                left_booster = true;
                keyH.leftPressed = false;
            }
            if (keyH.rightPressed) {
                rotationAngle += 15;
                if (rotationAngle >= 360) {
                    rotationAngle = 0;
                }
                currentBooster = "right";
                right_booster = true;
                keyH.rightPressed = false;
            }
            if (keyH.upPressed) {
                speed += speedIncrease;
                if (!isAccelerating) {
                    speed = initial_velocity;
                }
                isAccelerating = true;
                if (speed >= terminal_velocity) {
                    speed = terminal_velocity;
                }

               if(rotationAngle == 345 || rotationAngle == 195 || rotationAngle == -15 || rotationAngle == -165){
                   newX = (int) (x + (speed * Math.sin(Math.toRadians(rotationAngle))));
               }
               else {
                   newX = (int) Math.ceil(x + (speed * Math.sin(Math.toRadians(rotationAngle))));
               }

               if(rotationAngle == 75 || rotationAngle == 285 || rotationAngle == -75 || rotationAngle == -285) {
                   newY = (int) (y - speed * Math.cos(Math.toRadians(rotationAngle)));
               }
               else{
                   newY = (int) Math.ceil(y - speed * Math.cos(Math.toRadians(rotationAngle)));
               }

                crashCheck();

            }
            else if (keyH.downPressed) {
                speed += speedIncrease;
                if (!isAccelerating) {
                    speed = initial_velocity;
                }
                isAccelerating = true;
                if (speed >= terminal_velocity) {
                    speed = terminal_velocity;
                }

                if(rotationAngle == 345 || rotationAngle == 195 || rotationAngle == -15 || rotationAngle == -165){
                    newX = (int) (x - (speed * Math.sin(Math.toRadians(rotationAngle))));
                }
                else {
                    newX = (int) Math.ceil(x - (speed * Math.sin(Math.toRadians(rotationAngle))));
                }

                if(rotationAngle == 75 || rotationAngle == 285 || rotationAngle == -75 || rotationAngle == -285) {
                    newY = (int) (y + speed * Math.cos(Math.toRadians(rotationAngle)));
                }
                else{
                    newY = (int) Math.ceil(y + speed * Math.cos(Math.toRadians(rotationAngle)));
                }

                crashCheck();

            }
            else {
                if (y <= gp.screenHeight - image.getHeight(null)) {
                    speed += gravity;
//                    if (speed >= terminal_velocity) {
//                        speed = terminal_velocity;
//                    }
                    y = (int) (y + speed);
                }
            }
            if (keyH.leftReleased) {
                left_booster = false;
                keyH.leftReleased = false;
            }
            if (keyH.rightReleased) {
                right_booster = false;
                keyH.rightReleased = false;
            }
            if (keyH.upReleased) {
                keyH.upPressed = false;
                isAccelerating = false;
                keyH.upReleased = false;
            }
            if (keyH.downReleased) {
                keyH.downPressed = false;
                isAccelerating = false;
                keyH.downReleased = false;
            }

            checkCol();
            getSolidArea();

            if(Objects.equals(CheckCollision.hasLanded(this, gp), "Landed")){
                lm.gameState = "levelComplete";
                gp.levelManager.gameLevels[gp.levelManager.currentLevel].running = false;
                gp.nextLevel();
            }
            else{
                if(Objects.equals(CheckCollision.hasLanded(this, gp), "Crashed")){
                    lm.message = "You crashed!";
                    image = new ImageIcon("Images/Crash.png").getImage();
                    gp.levelManager.gameLevels[gp.levelManager.currentLevel].running = false;
                    if(count ==0) {
                        Timer timer = new Timer(1000, arg0 -> {
                            gp.levelManager.message = "You ran out of lives!";
                            gp.restartLevel();
                            count++;
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
                }
            }

        }
        }

    public void draw(Graphics2D g2D){

            hitBoundary = false;

            g2D.setColor(Color.red);

            g2D.setColor(Color.white);

            AffineTransform rt = AffineTransform.getTranslateInstance(x, y);

            if (rotationAngle == 0) {
                g2D.drawImage(image, x, y, null);
                rt.rotate(Math.toRadians(rotationAngle), (double) image.getWidth(null) / 2, (double) image.getHeight(null) / 2);
                g2D.drawImage(image, rt, null);
            }

            else if (currentBooster.equals("left")) {
                rt.rotate(Math.toRadians(rotationAngle - 360), (double) image.getWidth(null) / 2, (double) image.getHeight(null) / 2);
                g2D.drawImage(image, rt, null);
            }
            else if (currentBooster.equals("right")) {
                rt.rotate(Math.toRadians(rotationAngle), (double) image.getWidth(null) / 2, (double) image.getHeight(null) / 2);
                g2D.drawImage(image, rt, null);
            }
    }

    public void getSolidArea(){
        if(Math.abs(rotationAngle)>=45){
            solidArea = new Rectangle(x+xIncrease, y+yIncrease, width-15, height-10);
        }
        else {
            solidArea = new Rectangle(x + xIncrease, y + yIncrease, width - 12, height - 10);
        }

        AffineTransform xt = new AffineTransform();
        xt.rotate(Math.toRadians(rotationAngle), x+19, y+25);
        solidArea = xt.createTransformedShape(solidArea).getBounds();
    }

    public void checkCol(){
        try{
            if(CheckCollision.checkStarCollision(this,gp)){
                lm.gameLevels[lm.currentLevel].score+=10;
                }
            if(Objects.equals(CheckCollision.checkRocketCollision(this, gp), "Collide")) {
            image = new ImageIcon("Images/Crash.png").getImage();
            gp.levelManager.gameLevels[gp.levelManager.currentLevel].running = false;
            if(count ==0) {
                Timer timer = new Timer(1000, arg0 -> {
                    gp.levelManager.message = "You ran out of lives!";
                    gp.restartLevel();
                    count++;
                });
                timer.setRepeats(false);
                timer.start();
            }

        }
            else{
            if (left_booster) {
                if (speed >= fastSpeedLimit) {
                    image = new ImageIcon("Images/Rocket-fire2-left.png").getImage();
                } else {
                    image = new ImageIcon("Images/Rocket-fire1-left.png").getImage();
                }
            } else if (right_booster) {
                if (speed >= fastSpeedLimit) {
                    image = new ImageIcon("Images/Rocket-fire2-right.png").getImage();
                } else {
                    image = new ImageIcon("Images/Rocket-fire1-right.png").getImage();
                }
            } else if (isAccelerating) {
                if (speed >= fastSpeedLimit) {
                    image = new ImageIcon("Images/Rocket-fire2.png").getImage();
                } else {
                    image = new ImageIcon("Images/Rocket-fire.png").getImage();
                }
            } else {
                image = new ImageIcon("Images/Rocket.png").getImage();
            }
        }
        }
        catch (Exception e){
            image = new ImageIcon("Images/Rocket.png").getImage();
        }
    }

    public void crashCheck(){

        if(x>=0 && x <= gp.screenWidth - image.getHeight(null)){
            x = newX;
        }
        else if((newX < x && x>=image.getHeight(null)) || (newX > x && x<=image.getHeight(null))){
            x = newX;
        }
        if(y>=0 && y<=gp.screenHeight - image.getHeight(null)){
            y = newY;
        }
        else if((newY < y && y>=image.getHeight(null)) || (newY > y && y<=image.getHeight(null))){
            y = newY;
        }
    }
}
