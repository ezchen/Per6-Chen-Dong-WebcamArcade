import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;

import java.awt.geom.Ellipse2D;

import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.Webcam;

public class SetupScreen implements Screen {

	private WebcamPanel.Painter painter;
	private Ellipse2D.Double ellipse;
	private RegionOfInterest ROI;
	private BufferedImage savedImage;
	private Driver driver;

	private Webcam webcam;
	private WebcamPanel panel;

	private boolean buttonPressed;

	private ObjectTracker tracker;

	public SetupScreen(Webcam webcam, WebcamPanel panel, WebcamPanel.Painter painter, Driver driver) {
		this.webcam = webcam;
		this.painter = painter;
		this.panel = panel;
		this.driver = driver;
		buttonPressed = false;

		ROI = new RegionOfInterest(40, 250, 250); 
		ellipse = new Ellipse2D.Double();
		ellipse.width = 25;
		ellipse.height = 25;
		ellipse.x = 0;
		ellipse.y = 0;
	}

	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
		int w1 = panel.getSize().width;
		int h1 = panel.getSize().height;
		int w2 = savedImage.getWidth(null);
		int h2 = savedImage.getHeight(null);
		g2.drawImage(savedImage, (w1 - w2) / 2, (h1 - h2) / 2, null);
		paintCircle(panel, savedImage, g2);

		System.out.println("press any button to confirm that this is the correct image");
	}

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		// paints the Image to the background
		// Note -- image is inverted because of the camera
		if (painter != null) {

			int w1 = panel.getSize().width;
			int h1 = panel.getSize().height;
			int w2 = image.getWidth(null);
			int h2 = image.getHeight(null);

			g2.drawImage(image, (w1 - w2) / 2, (h1 - h2) / 2, null);

			image.flush();

			g2.drawRect(ROI.getLeft(), ROI.getTop(), ROI.getSize(), ROI.getSize());

			test(tracker, panel, image, g2);
		}

	}

	// Should move to another screen once we capture the
	// color the user wants to use to track the data
	public void update() {
		if (savedImage == null && buttonPressed) {
			System.out.println("image saved");
		}
	}

	// Create the image which we will use to find the color
	// of the finger
	public void keyPressed(KeyEvent e) {
		if (!buttonPressed) {
			buttonPressed = true;
			savedImage = webcam.getImage();
			int[] colors = ROI.getAverageRGB(savedImage);
			tracker = new ObjectTracker(ROI, colors, 12, panel);
			// driver.getScreens().pop();
			// driver.getScreens().push(new MainScreen(webcam, painter, driver);
			System.out.println(colors[0]);
			System.out.println(colors[1]);
			System.out.println(colors[2]);
			System.out.println("SetupScreen: Key pressed");
		}
	}

	public void test(ObjectTracker tracker, WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		if (tracker == null)
			return;
		
		tracker.trackObject(image);
	}	

	public void paintCircle(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		g2.draw(ellipse);
	}
}
