import java.awt.*;
import java.util.ArrayList;

/**
 * The class GameObject presents a game object
 * @author Gökay
 */
public class GameObject {
    public int type;
    public double x;
    public double y;
    public double w;
    public double h;

    /**
     * GameObject constructor with all parameters
     * @param type type of game object
     * @param x x coordinate of game object
     * @param y y coordinate of game object
     * @param w width of game object
     * @param h height of game object
     */
    public GameObject(int type, double x, double y, double w, double h) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     *
     * @param input_ballx x coordinate of the ball
     * @param input_bally y coordinate of the ball
     * @return checks hit or miss and returns true if it is a hit
     */
    public boolean isInside(double input_ballx, double input_bally) {
        double halfWidth = this.w / 2.0;
        double halfHeight = this.h / 2.0;
        double x1 = this.x - halfWidth;
        double x2 = this.x + halfWidth;
        if (input_ballx > x1 && input_ballx < x2 && input_bally < this.h) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for drawing game objects to screen
     */
    public void draw() {
        double halfW = this.w / 2.0;
        double halfH = this.h / 2.0;
        if (this.type == 1) {
            StdDraw.setPenColor( Color.BLUE );
        } else {
            StdDraw.setPenColor( Color.RED );
        }
        StdDraw.filledRectangle( this.x, this.y, halfW, this.h );
    }
}
