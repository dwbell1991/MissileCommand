package pkg.missile.vector;

import java.awt.Graphics;
import pkg.missile.framework.KeyboardInput;
import pkg.missile.framework.Matrix3x3f;
import pkg.missile.framework.RelativeMouseInput;
import pkg.missile.framework.Vector2f;

public abstract class VectorObject {

    protected Vector2f[] polygon;                         //Objects defined points
    protected Matrix3x3f world;                            //Objects transformation matrix
    protected Matrix3x3f viewport;                     //Viewport scaling matrix
    protected int maxSpawn;                                //Accounting for size of asteriods
    protected int minSpawn;                                //Accounting for size of asteroids
    protected int spawnX;                                       //X value for starting spawn location
    protected int spawnY;                                        //Y value for starting spawn location
    protected int width;                                           //Screen width
    protected int height;                                         //Screen height
    protected Vector2f velocity;                           //Vector for velocity
    protected float tx;                                               //Translation position x
    protected float ty;                                               //Translation position y
    protected RelativeMouseInput mouse;     //Mouse input
    protected KeyboardInput keyboard;         //Keyboard input
    protected float rot;                                            //Rotation
    protected float rotStep;                                  //Rotation Steps

    public VectorObject(int spawnX, int spawnY, Matrix3x3f viewport, RelativeMouseInput mouse, KeyboardInput keyboard) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.viewport = viewport;
        this.mouse = mouse;
        this.keyboard = keyboard;
        this.minSpawn = 0;
        this.maxSpawn = 0;
    }

    public abstract void processInput(Vector2f m);

    public abstract void updateObjects(float delta, Matrix3x3f viewport, float width, float height);

    public abstract void render(Graphics g);

}
