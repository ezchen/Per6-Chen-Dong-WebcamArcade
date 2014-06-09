import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;

import java.util.Stack;

import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.Webcam;

public class PongScreen implements Screen {
	
	private WebcamPanel.Painter painter;
	
	private Driver driver;

	private Webcam webcam;
	private WebcamPanel panel;

	private boolean buttonPressed;

	private ObjectTracker tracker;

	private int movement;
	private Ball ball;
	private Paddle paddle;
	private PaddleAI ai;

	public PongScreen(Webcam webcam, WebcamPanel panel, WebcamPanel.Painter painter, Driver driver, ObjectTracker tracker) {
		this.webcam = webcam;
		this.panel = panel;
		this.painter = painter;
		this.driver = driver;

		buttonPressed = false;
		this.tracker = tracker;

		ball = new Ball(100,40,20,20);
		ball.setSpeed(6);

		player = new Paddle(0,0,60,10);
        player.setSpeed(7);
        ai=new AIPaddle(width,0,5.5,width,height);
	}

	public void paintPanel(WebcamPanel panel, Graphics2D g2){}

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		if (painter != null) {
			int pWidth = panel.getSize().width;
			int pHeight = panel.getSize().height;
			int iWidth = -image.getWidth(null); // Flips the image along y axis
			int iHeight = image.getHeight(null);

			g2.drawImage(image, (pWidth - iWidth) / 2, (pHeight - iHeight) / 2, iWidth, iHeight , null);

			image.flush();

			// draw the object tracker's bounds
			g2.setPaint(Color.red);
			g2.drawRect(ROI.getLeft(), ROI.getTop(), ROI.getSize(), ROI.getSize());

			tracker.trackObject(image);

			// draw the rectangles
			drawRectangles(g2);
		}
	}

	public void update() {
	}

	public void drawRectangles(Graphics2D g2) {
		// Iterate through all of the rectangles and draw them
		g2.setPaint(Color.gray);
		for (Rectangle rect : paint) {
			g2.fill(rect);
		}
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch(keyCode) {
			case KeyEvent.VK_Z:
				undo(5);
				break;
			case KeyEvent.VK_R:
				redo(5);
				break;
			case KeyEvent.VK_P:
				System.out.println("DrawScreen: 'P' pressed; Entering PongScreen");
				driver.getScreens().pop();
				// driver.getScreens().push(new DrawScreen(webcam, panel, painter, driver, tracker));
				break;
			case KeyEvent.VK_S:
				System.out.println("DrawScreen: 'S' pressed. Entering SetupScreen");
				driver.getScreens().pop();
			default:
				int y = ROI.getTop();
				int x = ROI.getLeft();
				int size = ROI.getSize();

				addRectangle(size,size,x,y);
				break;
		}
	}

	public void addRectangle(int height, int width, int x, int y) {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		paint.push(rectangle);
		System.out.println(rectangle);
		System.out.println("Rectangle Added");
	}

	public void undo(int iterations) {
		if (paint.size() > 0) {
			redo.push(paint.pop());
		}
	}

	public void redo(int iterations) {
		if (!redo.empty()) {
			paint.push(redo.pop());
		}
	}
}
