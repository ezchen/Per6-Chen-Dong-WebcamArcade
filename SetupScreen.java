import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

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
		int pWidth = panel.getSize().width;
		int pHeight = panel.getSize().height;
		int iWidth = savedImage.getWidth(null);
		int iHeight = savedImage.getHeight(null);

		g2.drawImage(savedImage, (pWidth - iWidth) / 2, (pHeight - iHeight) / 2, null);
		paintCircle(panel, savedImage, g2);

		System.out.println("press any button to confirm that this is the correct image");
	}

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		// paints the Image to the background
		if (painter != null) {

			int pWidth = panel.getSize().width;
			int pHeight = panel.getSize().height;
			int iWidth = -image.getWidth(null); // Flips the image along y axis
			int iHeight = image.getHeight(null);

			g2.drawImage(image, (pWidth - iWidth) / 2, (pHeight - iHeight) / 2, iWidth, iHeight , null);

			image.flush();

			g2.drawRect(ROI.getLeft(), ROI.getTop(), ROI.getSize(), ROI.getSize());
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
	// of the object being tracked and allows the user to switch to the next screen
	public void keyPressed(KeyEvent e) {
		if (!buttonPressed) {
			buttonPressed = true;
			savedImage = webcam.getImage();
			int[] colors = ROI.getAverageRGB(savedImage);
			tracker = new ObjectTracker(ROI, colors, 12, panel);
			System.out.println("SetupScreen: Key pressed; Image saved");
			System.out.println("ROI: " + colors[0] + "," + colors[1] + "," + colors[2]);
		} else {
			driver.getScreens().pop();
			driver.getScreens().push(new SetupScreen(webcam, panel, painter, driver));

			int keyCode = e.getKeyCode();
			switch(keyCode) {
				// If D is pressed
				case KeyEvent.VK_D:
					System.out.println("SetupScreen: 'D' pressed; Entering DrawScreen");
					driver.getScreens().push(new DrawScreen(webcam, panel, painter, driver, tracker));
					break;
				// If P is pressed
				case KeyEvent.VK_P:
					System.out.println("SetupScreen: 'P' pressed; Entering PongScreen");
					// driver.getScreens().push(new DrawScreen(webcam, panel, painter, driver, tracker));
					break;
			}
		}
	}

	public void paintCircle(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		g2.draw(ellipse);
	}
}
