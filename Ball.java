import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double{
    
    private int speed;
    
    public Ball(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    
    public boolean setSpeed(int speed){
        if(speed<0 || speed>10)
            return false;
        this.speed=speed;
        return true;
    }
    
    public void moveX(boolean right){
        if(right)
            x+=speed;
        else x-=speed;
    }
    
    public void moveY(boolean down){
        if(down)
            y+=speed;
        else y-=speed;
    }
    
    public boolean setX(int x){
        if(x<width)
            this.x=x;
        return x<width;
    }
    
    public boolean setY(int y){
        if(y<height)
            this.y=y;
        return y<height;
    }
}