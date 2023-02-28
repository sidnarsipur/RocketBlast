import java.awt.*;

public class Object {

    public int x,y;
    public Rectangle solidArea;
    public int speed;
    public int direction;

    public Image image;
    public boolean collision = false;

    public Object(int x, int y, int speed, int direction){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
    }

    public Object(){
    }
}
