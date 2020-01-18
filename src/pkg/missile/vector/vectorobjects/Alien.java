package pkg.missile.vector.vectorobjects;

import pkg.missile.vector.VectorObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import pkg.missile.framework.KeyboardInput;
import pkg.missile.framework.Matrix3x3f;
import pkg.missile.framework.RelativeMouseInput;
import pkg.missile.framework.Vector2f;

public class Alien extends VectorObject {

    private boolean shotDown;                       //Was it shot down?

    public Alien(int spawnX, int spawnY, Matrix3x3f viewport, RelativeMouseInput mouse, KeyboardInput keyboard) {
        super(spawnX, spawnY, viewport, mouse, keyboard);
        this.shotDown = false;

        initialize();
    }

    /*
    Name: initialize
    Param: N/A
    Desc: Initializes the spaceship, starting position, and the world matrix.
     */
    private void initialize() {

        //Alien spaceship polygon
        polygon = new Vector2f[]{new Vector2f(-2000, 0), new Vector2f(-1300, 0), new Vector2f(-1000, 1000), new Vector2f(1000, 1000), new Vector2f(1300, 0), new Vector2f(2000, 0)};
        //Translation variables
        this.tx = spawnX;
        this.ty = spawnY;
        this.velocity = new Vector2f();

        //World initialize
        this.world = new Matrix3x3f();

    }

    /*
    Name: processInput
    Param: Vector2f m
    Desc: Checks for mouse collision detection within the alien spaceship bounds. (roughly)
     */
    @Override
    public void processInput(Vector2f m) {

        //Check if mouse within bounds, if so, kill it
        if (mouse.buttonDown(MouseEvent.BUTTON1)) {
            if (m.x < this.tx + 1300 && m.x > this.tx - 1300 && m.y < this.ty + 1000 && m.y > this.ty - 1000) {
                this.shotDown = true;
            }
        }

    }

    /*
    Name: updateObjects
    Param: float delta, Matrix3x3f viewport, float width, float height
    Desc: Updates object towards the right until it passes the right screen bounds, and then it stalls.
    The transformations also take place in this area. 
     */
    @Override
    public void updateObjects(float delta, Matrix3x3f viewport, float width, float height) {

        //Move until its out of right side of screen
        if (this.tx < width / 2 + 2200) {
            this.tx += 20000 * delta;
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
    Desc: Render the single alien ship
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
    Name; isShotDown
    Param: N/A
    Desc: Getter method to see if an asteroid was shot down
     */
    public boolean isShotDown() {
        return this.shotDown;
    }

    /*
    Name: setShotDown
    Param: boolean shotDown
    Desc: Sets shotdown back to false
     */
    public void setShotDown(boolean shotDown) {
        this.shotDown = false;
    }


    /*
    Name: setX
    Param: float x
    Desc: Setter for x position
     */
    public void setX(float x) {
        this.tx = x;
    }

    /*
    Name: setY
    Param: float y
    Desc: Setter for y position
     */
    public void setY(float y) {
        this.ty = y;
    }

}
