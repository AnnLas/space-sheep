package sample;

public class BackgroundSprite extends Sprite {

    public BackgroundSprite(double positionX, double positionY, double velocityX, double velocityY) {
        super(positionX, positionY, velocityX, velocityY);
    }

    @Override
    public void update(double time) {
        super.update(time);
        if (getPositionY()<-getImage().getHeight()){
           setPositionY(getImage().getHeight());
        }
        else if (getPositionY()>getImage().getHeight())
            setPositionY(-getImage().getHeight());
    }
}
