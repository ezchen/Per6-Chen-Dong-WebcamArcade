import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;

import com.github.sarxos.webcam.WebcamPanel;

public interface Screen {

	public void paintPanel(WebcamPanel panel, Graphics2D g2);

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2);

	public void update();

	public void keyPressed(KeyEvent e);
}
