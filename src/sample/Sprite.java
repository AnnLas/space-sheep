package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Sprite {
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;


    public Sprite(double positionX, double positionY, double velocityX, double velocityY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void setImage(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setImage(String imagePath) {
        Image image = new Image(imagePath);
        setImage(image);

    }

    public Image getImage() {
        return image;
    }

    public void setPosition(double x, double y){
        setPositionX(x);
        setPositionY(y);
    }
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setVelocity(double xV, double yV){
        setVelocityX(xV);
        setVelocityY(yV);
    }


    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void update(double time){
        positionX +=velocityX*time;
        positionY +=velocityY*time;
    }
    public void render(GraphicsContext graphicsContext){
        graphicsContext.drawImage(image,positionX,positionY);
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }



}
