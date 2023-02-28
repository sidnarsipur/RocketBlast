import java.awt.*;

public class LevelManager {

    public int screenWidth = 1200;
    public int screenHeight = 768;

    public String gameState = "Start";
    public String message;

    public int currentLevel = 0;
    public Level[] gameLevels = new Level[5];

    public LevelManager(){

        gameLevels[0] = new Level();
        gameLevels[0].timer = 0;
        gameLevels[0].levelNumber = 1;
        gameLevels[0].score = 0;
        gameLevels[0].landed = false;
        gameLevels[0].fuelCapacity = 100;
        gameLevels[0].fuel = gameLevels[0].fuelCapacity;
        gameLevels[0].rotationAngle = 0;
        gameLevels[0].bombNo = 5;
        gameLevels[0].running = true;
        gameLevels[0].lives = 3;
        gameLevels[0].map = "Maps/map1.txt";
        gameLevels[0].startPosition = new Point( screenWidth/5,screenHeight - 50);
        gameLevels[0].landingArea = new Rectangle(screenWidth-150, screenHeight-25,150,25);

        gameLevels[1] = new Level();
        gameLevels[1].timer = 100;
        gameLevels[1].levelNumber = 2;
        gameLevels[1].score = 0;
        gameLevels[1].landed = false;
        gameLevels[1].fuelCapacity = 100;
        gameLevels[1].fuel = 100;
        gameLevels[1].rotationAngle = 0;
        gameLevels[1].bombNo = 5;
        gameLevels[1].running = true;
        gameLevels[1].lives = 3;
        gameLevels[1].map = "Maps/map2.txt";
        gameLevels[1].startPosition = new Point(1200-45,768 - 50);
        gameLevels[1].landingArea = new Rectangle(150,0,150,25);

        gameLevels[2] = new Level();
        gameLevels[2].timer = 100;
        gameLevels[2].levelNumber = 3;
        gameLevels[2].score = 0;
        gameLevels[2].landed = false;
        gameLevels[2].fuelCapacity = 100;
        gameLevels[2].fuel = 100;
        gameLevels[2].rotationAngle = 0;
        gameLevels[2].bombNo = 5;
        gameLevels[2].running = true;
        gameLevels[2].lives = 3;
        gameLevels[2].map = "Maps/map3.txt";
        gameLevels[2].startPosition = new Point( screenWidth - 1000,screenHeight - 50);
        gameLevels[2].landingArea = new Rectangle(screenWidth-700, screenHeight-25,150,25);

        gameLevels[3] = new Level();
        gameLevels[3].timer = 100;
        gameLevels[3].levelNumber = 4;
        gameLevels[3].score = 0;
        gameLevels[3].landed = false;
        gameLevels[3].fuelCapacity = 100;
        gameLevels[3].fuel = 100;
        gameLevels[3].rotationAngle = 0;
        gameLevels[3].bombNo = 5;
        gameLevels[3].running = true;
        gameLevels[3].lives = 3;
        gameLevels[3].map = "Maps/map4.txt";
        gameLevels[3].startPosition = new Point( screenWidth - 60,screenHeight - 50);
        gameLevels[3].landingArea = new Rectangle(150,0,150,25);

        gameLevels[4] = new Level();
        gameLevels[4].timer = 100;
        gameLevels[4].levelNumber = 4;
        gameLevels[4].score = 0;
        gameLevels[4].landed = false;
        gameLevels[4].fuelCapacity = 100;
        gameLevels[4].fuel = 100;
        gameLevels[4].rotationAngle = 0;
        gameLevels[4].bombNo = 5;
        gameLevels[4].running = true;
        gameLevels[4].lives = 3;
        gameLevels[4].map = "Maps/map5.txt";
        gameLevels[4].startPosition = new Point( 45,screenHeight - 50);
        gameLevels[4].landingArea = new Rectangle(screenWidth-150, screenHeight-25,150,25);

    }

    public void setDefault(int level){
        gameLevels[level].landed = false;
        gameLevels[level].timer = 0;
        gameLevels[level].running = true;

    }

    public void restartDefault(int level){
        gameLevels[level].score = 0;
        gameLevels[level].lives = 3;
        gameLevels[level].landed = false;
        gameLevels[level].fuel = gameLevels[level].fuelCapacity;
    }
}
