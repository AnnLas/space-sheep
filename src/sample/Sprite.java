package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents graphical objects on the scene
 */
public class Sprite {
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;

    /**
     * Initialize sprite
     *
     * @param positionX sprite x position
     * @param positionY sprite y position
     * @param velocityX sprite x velocity
     * @param velocityY sprite y velocity
     */
    public Sprite(double positionX, double positionY, double velocityX, double velocityY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    /**
     * Sets sprite image
     *
     * @param image image of the Sprite
     */
    public void setImage(Image image) {
        this.image = image;

    }

    /**
     * Returns sprite y coordinate
     *
     * @return y coordinate
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Returns image which represents sprite
     *
     * @return image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets sprite y coordinate
     *
     * @param positionY y coordinate of the sprite
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * Sets velocity of the sprite
     *
     * @param xV x direction velocity
     * @param yV y direction velocity
     */
    public void setVelocity(double xV, double yV) {
        setVelocityX(xV);
        setVelocityY(yV);
    }

    /**
     * Sets velocity in the x direction
     *
     * @param velocityX velocity in the x direction
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * Sets velocity in the y direction
     *
     * @param velocityY velocity in the y direction
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * Updates sprite position
     *
     * @param time time of the displacement
     */
    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    /**
     * Renders image on the screen
     *
     * @param graphicsContext context which is responsible for graphic rendering
     */
    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, positionX, positionY);
    }


}
