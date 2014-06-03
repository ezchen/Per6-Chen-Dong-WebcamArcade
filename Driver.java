import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.Stack;

import javax.swing.JFrame;

public class Driver extends JFrame implements Runnable, WebcamPanel.Painter {

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			screens.peek().keyPressed(e);
		}
	}

	private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

	private Stack<Screen> screens;

	private Webcam webcam;
	private WebcamPanel.Painter painter;

	public Driver() throws IOException {
		super();

		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open(true);

		addKeyListener(new MyKeyAdapter());

		WebcamPanel panel = new WebcamPanel(webcam, false);
		panel.setPreferredSize(WebcamResolution.VGA.getSize());
		panel.setPainter(this);
		panel.setFPSLimited(true);
		panel.setFPSDisplayed(true);
		panel.setFPSLimit(20);
		panel.setPainter(this);
		panel.start();

		painter = panel.getDefaultPainter();
		screens = new Stack<Screen>();
		Screen setupScreen = new SetupScreen(webcam, painter);
		screens.push(setupScreen);

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

			// update the screen (handle all the logic)
			if (!screens.empty()) {
				screens.peek().update();
			}
		}
	}

	@Override
	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
		if (!screens.empty()) {
			screens.peek().paintPanel(panel, g2);
		}
	}

	@Override
	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {

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
