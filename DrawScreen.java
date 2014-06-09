import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.Rectangle;
import java.awt.Color;

import java.awt.event.KeyEvent;

import java.util.LinkedList;
import java.util.Stack;

import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.Webcam;

public class DrawScreen implements Screen {
	
	private WebcamPanel.Painter painter;
	private RegionOfInterest ROI;
	
	private Driver driver;

	private Webcam webcam;
	private WebcamPanel panel;

	private boolean buttonPressed;

	private ObjectTracker tracker;

	private LinkedList<Rectangle> paint;
	private Stack<Rectangle> redo;

	public DrawScreen(Webcam webcam, WebcamPanel panel, WebcamPanel.Painter painter, Driver driver, RegionOfInterest ROI) {
		this.webcam = webcam;
		this.panel = panel;
		this.driver = driver;
		this.painter = painter;

		buttonPressed = false;
		this.ROI = ROI;
	}

	public DrawScreen(Webcam webcam, WebcamPanel panel, WebcamPanel.Painter painter, Driver driver, ObjectTracker tracker) {
		this.webcam = webcam;
		this.panel = panel;
		this.painter = painter;
		this.driver = driver;

		buttonPressed = false;
		this.tracker = tracker;
		this.ROI = tracker.getROI();
		paint = new LinkedList<Rectangle>();
		redo = new Stack<Rectangle>();
	}

	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
	}

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
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_Z) {
			undo(5);
		} else if (key == KeyEvent.VK_R) {
			redo(5);
		} else {
			// find the position of the rectangle to be drawn
			int y = ROI.getTop();
			int x = ROI.getLeft();
			int size = ROI.getSize();

			addRectangle(size, size, x, y);
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
