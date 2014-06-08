import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.WebcamPanel;

/*
 * Class used to track objects
 *
 * A huge amount of credit goes to this website
 * cmsoft.com
 *
 * After toying around with weighted means and trying to figure out a nice way to describe the weighted mean
 * of a pixel I used his equation to find the weight of a pixel: e^(-k*Distance(colorTracked, colorPixel)^2),
 */
public class ObjectTracker {
	private RegionOfInterest ROI;
	private int[] colors;
	private WebcamPanel panel;
	// k is the threshold of error between the given pixel and the tracked pixel
	private double k;

	public ObjectTracker(RegionOfInterest ROI, int[] rgb, double k, WebcamPanel panel) {
		this.ROI = ROI;
		this.panel = panel;
		this.k = k;
		colors = rgb;
	}

	public void trackObject(BufferedImage image) {
		// take the average position of all the pixels that
		// are close enough to the colors array in this class
		double x = 0;
		double y = 0;
		double weight = 0;
		// compute the weighted mean of the all the points in the region of interest
		for (int r = ROI.getTop(); r < ROI.getSize() + ROI.getTop(); r++) {
			for (int c = ROI.getLeft(); c < ROI.getSize() + ROI.getLeft(); c++) {
				double pWeight = pixelWeight(image.getRGB(c, r));
				y += (r * pWeight);
				x += (c * pWeight);
				weight += pWeight;
			}
		}

		if (x/weight - ROI.getSize() > 0 && x/weight + ROI.getSize() < panel.getWidth()) {
			ROI.setX((int)(x/weight));
		}
		if (y/weight - ROI.getSize() > 0 && y/weight + ROI.getSize() < panel.getHeight()) {
			ROI.setY((int)(y/weight));
		}

		// For testing purposes: System.out.println(ROI);
	}

	public int pixelDistance(int rgb) {
		int red = Math.abs(((rgb >> 16) & 0xFF) - colors[0]);
		int green = Math.abs(((rgb >> 8) & 0xFF) - colors[1]);
		int blue = Math.abs(((rgb & 0xFF) & 0xFF) - colors[2]);

		return Math.max(Math.max(red, green), blue);
	}

	public double pixelWeight(int rgb) {
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = (rgb & 0xFF);

		return Math.exp(-(k*Math.pow(pixelDistance(rgb),2)));
	}

	public RegionOfInterest getROI() {
		return ROI;
	}
}
