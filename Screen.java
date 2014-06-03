import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.WebcamPanel;

public interface Screen {
	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2);

	public void update();
}
