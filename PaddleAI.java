import java.awt.geom.Rectangle2D;

public class AIPaddle extends Rectangle2D.Double{

    private final int gameHeight, gameWidth;
    private final int WIDTH, HEIGHT
    private final double SPEED;

    public AIPaddle(int x, int y, double speed, int gameWidth, int gameHeight){
        SPEED = speed;
        WIDTH = 60;
        HEIGHT = 10;
        this.x = x;
        this.y = y;
        this.height = HEIGHT;
        this.width = WIDTH;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
    }
    public void moveAi(double ballX, double ballY){
        if(ballY >= gameWidth/2){    
            if(ballX < x && x>=2) // avoids the bug where x and/or ballX = 0
                x-= SPEED;
            if(ballX > x && x <= gameHeight)
                x+= SPEED; 
        }
    }
}