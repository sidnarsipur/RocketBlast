import java.awt.*;

public class PlayerControlledObject {

    public int x,y;

    public Image image;

    public float speed;
    public float speedIncrease;
    public float gravity;
    public float initial_velocity;
    public float terminal_velocity;

    public Rectangle solidArea;
    public boolean CollisionOn = false;
}
