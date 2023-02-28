import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager {

    GamePanel gp;
    LevelManager lm;

    public Object[] element;
    public int[][] mapTileNum;
    public ArrayList<Rectangle> objectSolidAreaArray;

    Random random = new Random();

    public Rectangle landingPad;

    public ArrayList<Point> bombTileArray;
    public ArrayList<Object> starTileArray;

    public boolean drawnOnce = false;

    public ObjectManager(GamePanel gp, LevelManager lm){
        this.gp = gp;
        this.lm = lm;

        element = new Object[5];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        objectSolidAreaArray = new ArrayList<>();

        bombTileArray = new ArrayList<>();
        starTileArray = new ArrayList<>();

        landingPad = lm.gameLevels[lm.currentLevel].landingArea;

        getElementImage();
        loadMap(lm.gameLevels[lm.currentLevel].map);
    }

    public void getElementImage(){
        try{
            element[0] = new Object();
            element[0].image = new ImageIcon("Images/Stone.png").getImage();
            element[0].collision = true;

            element[1] = new Object();
            element[1].image = new ImageIcon("Images/Bomb.png").getImage();
            element[1].collision = true;

            element[2] = new Object();
            element[2].image = new ImageIcon("Images/Star.png").getImage();
            element[2].collision = false;
            element[2].speed = 0;

        } catch (Exception ignored) {
        }
    }

    public void generateBombTiles(){
            for (int i = 0; i < lm.gameLevels[lm.currentLevel].bombNo;) {
                int randomX = random.nextInt(gp.screenWidth-gp.tileSize + 1 - gp.tileSize) + gp.tileSize;
                int randomY = random.nextInt(gp.screenHeight-gp.tileSize + 1 - gp.tileSize) + gp.tileSize;
                if (!CheckCollision.checkObjectCollision(new Point(randomX, randomY), objectSolidAreaArray, gp)) {
                    bombTileArray.add(new Point(randomX, randomY));
                    objectSolidAreaArray.add(new Rectangle(randomX, randomY, gp.tileSize, gp.tileSize));
                    i++;
                }
            }
    }

    public void generateStars(){

        int direction = random.nextInt(2 + 1 - 1) +1;
        int randomX;
        int randomY;

        switch (direction) {
            case 1 -> {

                randomX = 0;
                randomY = random.nextInt(gp.screenHeight-gp.tileSize + 1 - gp.tileSize) + gp.tileSize;
                starTileArray.add(new Object(randomX, randomY, 1, direction));
            }
            case 2 -> {
                randomX = gp.screenWidth-gp.tileSize;
                randomY = random.nextInt(gp.screenHeight-gp.tileSize + 1 - gp.tileSize) + gp.tileSize;
                starTileArray.add(new Object(randomX, randomY, 1, direction));
            }
        }
        }


    public void changeStarSpeed() {
        try{
        for(Object object : starTileArray) {
            if (object.direction == 1) {
                object.x += 2;
                object.solidArea = new Rectangle(object.x, object.y, gp.tileSize, gp.tileSize);
            }
            if (object.direction == 2) {
                object.x -= 2;
                object.solidArea = new Rectangle(object.x, object.y, gp.tileSize, gp.tileSize);
            }
            if(object.x >= gp.screenWidth || object.x <= 0){
                starTileArray.remove(object);
            }
        }}catch (Exception ignored){}
    }

    public void loadMap(String filepath){
        try {
            File file = new File(filepath);

            BufferedReader br = new BufferedReader(new FileReader(file));

            int col = 0;
            int row = 0;
            int x = 0;
            int y = 0;

            while (col < gp.maxScreenCol - 1 && row < gp.maxScreenRow) {
                String line = br.readLine();
                while (col < gp.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    if(!drawnOnce && num ==1) {
                        objectSolidAreaArray.add(new Rectangle(x, y, gp.tileSize, gp.tileSize));
                    }
                    col++;
                    x+= gp.tileSize;
                }

                if (col == gp.maxScreenCol) {
                    col = 0;
                    x = 0;
                    y += gp.tileSize;
                    row++;
                }
            }
            generateBombTiles();
            br.close();
        }
        catch(Exception i){
            System.out.println("Hello" + i);
        }
        drawnOnce = true;
    }

    public void draw(Graphics2D g2){

        g2.setColor(Color.red);
        g2.fillRect(landingPad.x, landingPad.y, landingPad.width, landingPad.height);

        for (Object object : starTileArray) {
            g2.drawImage(element[2].image, object.x, object.y, gp.tileSize, gp.tileSize, null);
        }

        g2.drawImage(element[2].image, 1200, 73, gp.tileSize, gp.tileSize, null);

        changeStarSpeed();

        if( gp.timePassed % 60 ==0 && gp.seconds % 5 == 1) {
            generateStars();
        }

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int eNum = mapTileNum[col][row];
            if(eNum == 1) {
                g2.drawImage(element[0].image, x, y, gp.tileSize, gp.tileSize, null);
                g2.setColor(Color.white);
            }
            col++;
            x += gp.tileSize;

            if(col==gp.maxScreenCol){
                col = 0;
                x = 0;
                row++ ;
                y+= gp.tileSize;
            }
        }

        for (Point point : bombTileArray) {
            g2.drawImage(element[1].image, point.x, point.y, gp.tileSize, gp.tileSize, null);
            g2.setColor(Color.white);
        }
    }

}

