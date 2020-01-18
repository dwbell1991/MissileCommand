package pkg.missile.vector.vectorobjects;

import pkg.missile.vector.VectorObject;
import java.awt.Color;
import java.awt.Graphics;
import pkg.missile.framework.KeyboardInput;
import pkg.missile.framework.Matrix3x3f;
import pkg.missile.framework.RelativeMouseInput;
import pkg.missile.framework.Vector2f;

public class MouseCursor extends VectorObject {

    public MouseCursor(int spawnX, int spawnY, Matrix3x3f viewport, RelativeMouseInput mouse, KeyboardInput keyboard) {
        super(spawnX, spawnY, viewport, mouse, keyboard);
        initialize();
    }

    /*
    Name; initialize
    Param: N/A
    Desc: Sets up the crosshair cursor, its starting position and
    the velocity and world vectors. 
     */
    private void initialize() {

        //Cross Hair Cursor
        polygon = new Vector2f[]{new Vector2f(-500, 0), new Vector2f(0, 0), new Vector2f(0, -500), new Vector2f(0, 0), new Vector2f(500, 0), new Vector2f(0, 0), new Vector2f(0, 500), new Vector2f(0, 0)};
        //Translation variables
        this.tx = spawnX;   //Set to center initially
        this.ty = spawnY;   //Set to center initially
        this.velocity = new Vector2f();

        //World initialize
        this.world = new Matrix3x3f();

    }

    /*
    Name; processInput
    Param: Vector2f m
    Desc: This takes the mouses world position and applies it to the transform X
    and transform Y variables. 
     */
    @Override
    public void processInput(Vector2f m) {
        this.tx = m.x;
        this.ty = m.y;

    }

    /*
    Name; updateObjects
    Param: float delta: time, Matrix3x3f viewport: viewport scaler,
    float width: appWorldWidth, float height appWorldHeight
    Desc: Applies the transforms to the mouse cursor crosshair. 
     */
    @Override
    public void updateObjects(float delta, Matrix3x3f viewport, float width, float height) {

        //Translations
        world = Matrix3x3f.translate(tx, ty);
        //Multiply by viewport scalar
        world = world.mul(viewport);
    }

    /*
    Name; render
    Param: Graphics g
    Desc: Renders the mouse crosshair.
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
}
