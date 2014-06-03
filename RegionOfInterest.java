import java.awt.image.BufferedImage;
public class RegionOfInterest {
	private int x;
	private int y;
	private int size;
	private int[][] pixels;
	private int[] colors;

	public RegionOfInterest(int size, int x, int y) {
		this.size = size;
		this.x = x;
		this.y = y;
		int[][] pixels = new int[size][size];
		colors = new int[3];
	}

	public int[] getAverageRGB(BufferedImage image) {
		int rgb;
		int red = 0;
		int green = 0;
		int blue = 0;
		for (int r = getTop(); r < size + getTop(); r++) {
			for (int c = getLeft(); c < size + getLeft(); c++) {
				rgb = image.getRGB(c, r);
				red += (rgb >> 16) & 0xFF;
				green += (rgb >> 8) & 0xFF;
				blue += (rgb & 0xFF);
			}
		}
		colors[0] = red/(size*size);
		colors[1] = green/(size*size);
		colors[2] = blue/(size*size);
		return colors;
	}

	public int getSize() {
		return size;
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
		return (size/2 + y);
	}

	// x coordinate of the top of the box
	public int getLeft() {
		return (x - size/2);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
