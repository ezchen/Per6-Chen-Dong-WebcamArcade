import java.awt.geom.Rectangle2D;

public class Paddle extends Rectangle2D.Double{

    private int speed;
    
    public Paddle(int x, int y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    
    public boolean setSpeed(int speed){
        if(speed<0 || speed>16){
            return false;
        }
        this.speed=speed;
        return true;
    }
    
    public boolean setX(int x){
        if(x<height){
            this.x=x;
        }
        return x<width;
    }
    
    public void move(boolean dir){
        if(dir)
            x+=speed;
        else
            x-=speed;
    }
    
    
}