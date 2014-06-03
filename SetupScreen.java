import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;

import java.awt.geom.Ellipse2D;

import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.Webcam;

public class SetupScreen implements Screen {

	private WebcamPanel.Painter painter;
	private Ellipse2D.Double ellipse;
	private BufferedImage savedImage;

	private Webcam webcam;

	private boolean buttonPressed;

	public SetupScreen(Webcam webcam, WebcamPanel.Painter painter) {
		this.webcam = webcam;
		this.painter = painter;
		buttonPressed = false;
		ellipse = new Ellipse2D.Double();
		ellipse.width = 25;
		ellipse.height = 25;
		ellipse.x = 0;
		ellipse.y = 0;
	}

	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
		if (painter != null && savedImage == null) {
			painter.paintPanel(panel, g2);
		}
	}

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		// paints the Image to the background
		// Note -- image is inverted because of the camera
		if (painter != null && savedImage == null) {
			painter.paintImage(panel, image, g2);
			paintCircle(panel, image, g2);
			g2.drawImage(savedImage, null, 0, 0);
		} else {
			g2.drawImage(savedImage, null, 0, 0);
		}

	}

	// Should move to another screen once we capture the
	// color the user wants to use to track the data
	public void update() {
		if (savedImage == null && buttonPressed) {
			System.out.println("image saved");
			savedImage = webcam.getImage();
		}
	}

	// Create the image which we will use to find the color
	// of the finger
	public void keyPressed(KeyEvent e) {
		buttonPressed = true;
		System.out.println("SetupScreen: Key pressed");
	}

	public void paintCircle(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		g2.draw(ellipse);
	}
}
