import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import java.util.concurrent.Executors;
import java.util.concurrent.Executor;

import javax.swing.JFrame;

public class Driver extends JFrame implements Runnable, WebcamPanel.Painter {

	private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

	private Webcam webcam;
	private WebcamPanel.Painter painter = null;

	public Driver() throws IOException {
		super();

		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open(true);

		WebcamPanel panel = new WebcamPanel(webcam, false);
		panel.setPreferredSize(WebcamResolution.VGA.getSize());
		panel.setPainter(this);
		panel.setFPSLimited(true);
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
	public void PaintPanel(WebcamPanel panel, Graphics2D g2) {
		if (painter != null) {
			painter.painPanel(panel, g2);
		}
	}

	@Override
	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {
	}
}
