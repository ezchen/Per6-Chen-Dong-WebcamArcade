import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.Stack;

import javax.swing.JFrame;

public class Driver extends JFrame implements Runnable, WebcamPanel.Painter {

	private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

	private Stack<Screen> screens;

	private Webcam webcam;
	private WebcamPanel.Painter painter = null;

	public Driver() throws IOException {
		super();

		screens = new Stack<Screen>();
		Screen setupScreen = new SetupScreen();
		screens.push(setupScreen);

		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open(true);

		WebcamPanel panel = new WebcamPanel(webcam, false);
		panel.setPreferredSize(WebcamResolution.VGA.getSize());
		panel.setPainter(this);
		panel.setFPSLimited(true);
		panel.setFPSDisplayed(true);
		panel.setFPSLimit(20);
		panel.setPainter(this);
		panel.start();

		painter = panel.getDefaultPainter();

		add(panel);

		setTitle("Webcam-Arcade");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		EXECUTOR.execute(this);
	}
	
	@Override
	public void run() {
		boolean running = true;
		while (running) {
			if (!webcam.isOpen()) {
				return;
			}
		}
	}

	@Override
	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
		if (painter != null) {
			painter.paintPanel(panel, g2);
		}
	}

	@Override
	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {

		// paints the Image to the background
		// Note -- image is inverted because of the camera
		if (painter != null) {
			painter.paintImage(panel, image, g2);
		}

		// paint other stuff here with the different screens. The different screens 
		// should also handle update methods (such as finger tracking)
		if (!screens.empty()) {
			screens.peek().paintImage(panel, image, g2);
		}
	}

	public static void main(String[] args) throws IOException {
		new Driver();
	}

}
