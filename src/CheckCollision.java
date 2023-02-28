import java.awt.*;
import java.util.ArrayList;


public class CheckCollision {
    public static String  checkRocketCollision(PlayerControlledObject object1, GamePanel gp){
        for(int i = 0; i < gp.gameObjects.objectSolidAreaArray.size(); i++){
            if(object1.solidArea.intersects( gp.gameObjects.objectSolidAreaArray.get(i)) ){
                return "Collide";
            }
        }
        return "Not Collide";
    }
    public static boolean checkObjectCollision(Point p, ArrayList<Rectangle> objectSolidAreaArray, GamePanel gp){
        Rectangle objectRect = new Rectangle(p.x, p.y, gp.tileSize, gp.tileSize);

        for (Rectangle rectangle : objectSolidAreaArray) {
            if (objectRect.intersects(rectangle)) {
                return true;
            }
        }
        return objectRect.intersects(gp.rocket.solidArea);
    }

    public static String hasLanded(Rocket object1, GamePanel gp) {
        if (object1.solidArea.intersects(gp.gameObjects.landingPad)) {
            if (object1.speed >= object1.terminal_velocity + 2) {
                return "Crashed";
            } else if (object1.rotationAngle != 0 && object1.rotationAngle != -180 && object1.rotationAngle != 180) {
                return "Crashed";
            } else if ((object1.rotationAngle == 0 && gp.gameObjects.landingPad.y != gp.screenHeight - 25) || ( (object1.rotationAngle == -180 || object1.rotationAngle == 180) && gp.gameObjects.landingPad.y != 0)) {
                return "Crashed";
            }
            return "Landed";
        }
        return null;
    }

    public static boolean checkStarCollision(PlayerControlledObject object1, GamePanel gp){
        boolean flag = false;
        for(int i = 0; i < gp.gameObjects.starTileArray.size(); i++){
            if(object1.solidArea.intersects( gp.gameObjects.starTileArray.get(i).solidArea) ){
                gp.gameObjects.starTileArray.remove(i);
                i--;
                flag = true;
            }
        }
        return flag;
    }

}

