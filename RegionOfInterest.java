import java.awt.image.BufferedImage;

// ROI is in the shape of a square
public class RegionOfInterest {
	private int x;
	private int y;
	private int sideLength;
	private int[][] pixels;
	private int[] colors;

	public RegionOfInterest(int sideLength, int x, int y) {
		this.sideLength = sideLength;
		this.x = x;
		this.y = y;
		int[][] pixels = new int[sideLength][sideLength];
		colors = new int[3];
	}

	public int[] getAverageRGB(BufferedImage image) {
		int rgb;
		int red = 0;
		int green = 0;
		int blue = 0;
		for (int r = getTop(); r < sideLength + getTop(); r++) {
			for (int c = getLeft(); c < sideLength + getLeft(); c++) {
				rgb = image.getRGB(r, c);
				red += (rgb >> 16) & 0xFF;
				green += (rgb >> 8) & 0xFF;
				blue += (rgb & 0xFF);
			}
		}
		colors[0] = red/(sideLength*sideLength);
		colors[1] = green/(sideLength*sideLength);
		colors[2] = blue/(sideLength*sideLength);
		return colors;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return sideLength;
	}

	public int[][] getPixels() {
		return pixels;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// y coordinate of the top of the box
	public int getTop() {
		return (y - sideLength/2);
	}

	// x coordinate of the top of the box
	public int getLeft() {
		return (x - sideLength/2);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
