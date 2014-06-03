import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.geom.Ellipse2D;

import com.github.sarxos.webcam.WebcamPanel;

public class SetupScreen implements Screen {

	private WebcamPanel.Painter painter;
	private Ellipse2D.Double ellipse;

	public SetupScreen() {
		ellipse = new Ellipse2D.Double();
		ellipse.width = 25;
		ellipse.height = 25;
		ellipse.x = 0;
		ellipse.y = 0;
	}

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		paintCircle(panel, image, g2);
	}

	// Should move to another screen once we capture the
	// color the user wants to use to track the data
	public void update() {
	}

	public void paintCircle(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
		g2.draw(ellipse);
	}
}
