package pkg.missile.vector.vectorobjects;

import pkg.missile.vector.VectorObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import pkg.missile.framework.KeyboardInput;
import pkg.missile.framework.Matrix3x3f;
import pkg.missile.framework.RelativeMouseInput;
import pkg.missile.framework.Vector2f;

public class Asteroid extends VectorObject {

    private float falling;                                       //Holds  -9.8m/s^2 to terminal velocity values
    private boolean isAlive;                                //Is it alive?
    private boolean shotDown;                       //Was it shot down?
    private int distFallen;                                   //Distance fallen
    private final int TERMINAL_V = 11000;    //Terminal velocity 

    public Asteroid(int spawnX, int spawnY, Matrix3x3f viewport, RelativeMouseInput mouse, KeyboardInput keyboard) {
        super(spawnX, spawnY, viewport, mouse, keyboard);
        this.falling = 0;
        this.isAlive = true;
        this.shotDown = false;
        this.distFallen = 0;

        initialize();
    }

    /*
    Name; initialize
    Param: N/A
    Desc: Sets up the asteroid shape, starting spawn position, and 
    initializes world and velocity matrix.
    */
    private void initialize() {
        //Asteroid Shape (8 sided)
        polygon = new Vector2f[]{new Vector2f(400, -800), new Vector2f(-400, -800), new Vector2f(-800, -400),
            new Vector2f(-800, 400), new Vector2f(-400, 800), new Vector2f(400, 800), new Vector2f(800, 400), new Vector2f(800, -400)};
        //Translation variables
        this.tx = spawnX;
        this.ty = spawnY;    //Start above screen to gain momentum
        this.velocity = new Vector2f();

        //World initialize
        this.world = new Matrix3x3f();

    }

    /*
    Name; processInput
    Param: Vector2f m - world mouse coordinates
    Desc: Does a simple collision detection with asteroid against
    the mouse. If within bounds set the asteroid as shot down. 
    */
    @Override
    public void processInput(Vector2f m) {

        //Check if mouse within bounds, if so, kill it
        if (mouse.buttonDown(MouseEvent.BUTTON1)) {
            if (m.x < this.tx + 800 && m.x > this.tx - 800 && m.y < this.ty + 800 && m.y > this.ty - 800) {
                this.shotDown = true;
            }
        }

    }

    /*
    Name; updateObject
    Param: float delta: time, Matrix3x3f viewport: viewport matrix,
    float width: appWorldWidth, float height: app WorldHeight
    Desc: Updates asteroid by checking distance fallen versus
    terminal velocity, if it exceeds that, then set the translation
    to its cap. If it falls below screen remove it. Apply the 
    vector transforms to the asteroid. 
    */
    @Override
    public void updateObjects(float delta, Matrix3x3f viewport, float width, float height) {

        //Checking terminal velocity
        if (distFallen < TERMINAL_V) {
            //Acceleration of gravity
            ty -= (falling += Math.pow(9.80665f, 2) * delta);
            distFallen++;
        } else {
            //Terminal was reached, so stop increasing fall speed.
            ty -= falling;
        }

        //If below screen
        if (ty < (-1 * (height / 2 + 200))) {
            this.isAlive = false;
        }

        Matrix3x3f mat = Matrix3x3f.translate(tx, ty);
        velocity = mat.mul(new Vector2f());

        //Translations
        world = Matrix3x3f.translate(velocity);
        //Multiply by viewport scalar
        world = world.mul(viewport);
    }

    /*
    Name; render
    Param: Graphics g
    Desc: Render the single asteroid
    */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        Vector2f S = world.mul(polygon[polygon.length - 1]);
        Vector2f P = null;
        for (int i = 0; i < polygon.length; ++i) {
            P = world.mul(polygon[i]);
            g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            S = P;
        }
    }

    /*
    Name; setWind
    Param: float wind
    Desc: Setter, to appy wind transistions from main
    */
    public void setWind(float wind) {
        this.tx += wind;
    }
    
    /*
    Name; isShotDown
    Param: N/A
    Desc: Getter method to see if an asteroid was shot down
    */
    public boolean isShotDown(){
        return this.shotDown;
    }

    /*
    Name; isAlive
    Param: N/A
    Desc: Getter method to check if an asteroid is
    currently alive. Set to now, to check if it exceeds
    bottom y bounds. 
    */
    public boolean isAlive() {
        return this.isAlive;
    }

    /*
    Name; getX
    Param: N/A
    Desc: Returns the asteroids x position
    */
    public int getX() {
        return (int) this.tx;
    }

    /*
    Name; getY
    Param: N/A
    Desc: Returns the asteroid y position
    */
    public int getY() {
        return (int) this.ty;
    }
}
