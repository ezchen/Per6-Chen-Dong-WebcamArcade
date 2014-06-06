import java.awt.Graphics2D
import java.awt.image.BufferedImage;

import java.awt.geom.Rectangle2D;

import java.awt.event.KeyEvent;

import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.Webcam;

public class DrawScreen implements Screen {
	
	private WebcamPanel.Painter painter;
	private RegionOfInterest ROI;
	
	private Driver driver;

	private Webcam webcam;
	private WebcamPanel panel;

	private ObjectTracker tracker;

	private boolean buttonPressed;

	private ObjectTracker tracker;

	private Stack<Rectangle> paint;

	public DrawScreen(Webcam webcam, WebcamPanel panel, WebcamPanel.Painter painter, Driver driver, RegionOfInterest ROI) {
		this.webcam = webcam;
		this.panel = panel;
		this.driver = driver;

		buttonPressed = false;
		this.ROI = ROI;
	}

	public DrawScreen(Webcam webcam, WebcamPanel panel, WebcamPanel.Painter painter, Driver driver, ObjectTracker tracker) {
		this.webcam = webcam;
		this.panel = panel;
		this.driver = driver;

		buttonPressed = false;
		this.tracker = tracker;
		this.ROI = tracker.getROI();
	}

	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
	}

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		if (painter != null) {
			int w1 = panel.getSize().width;
			int h1 = panel.getSize().height;
			int w2 = image.getWidth(null);
			int h2 = image.getHeight(null);

			g2.drawImage(image, (w1 - w2) / 2, (h1 - h2) / 2, null);

			image.flush();

			g2.drawRect(ROI.getLeft(), ROI.getTop(), ROI.getSize(), ROI.getSize());

		}
	}

	public void update() {
		tracker.trackObject(image);
	}

	public void drawRectangles(Graphics2D g2) {
		// Iterate through all of the rectangles and draw them
		for (Rectangle rect : paint) {
			g2.draw(rect);
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// if (key == undo) {
		// 		undo(10);
		// 	} 
		
		// find the position of the rectangle to be drawn
		int y = ROI.getTop();
		int x = ROI.getLeft();
		int size = ROI.getSize();

		addRectangle(size, size, x, y);
	}

	public void addRectangle(int height, int width, int x, int y) {
		Rectangle rectangle = new Rectangle(height, width, x, y);
		paint.push(rectangle);
	}

	public void undo(int iterations) {
		if (!paint.empty()) {
			for stack(int i = iterations; i > 0; i++) {
				paint.pop();
			}
		}
	}
}
