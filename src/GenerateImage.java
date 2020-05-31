import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class GenerateImage extends Canvas {

	private BufferedImage img;
	private Pixel[][] pixels;
	private int w, h;
	private int blockSize;

	public GenerateImage(Image image, int blockSize) {
		this.blockSize = blockSize;

		try {
			img = (BufferedImage) image;
			w = img.getWidth();
			h = img.getHeight();
			if (w > 1200 || h > 1200) {
				int newHeight = h;
				System.out.println(w + " " + h);
				while (newHeight > 1000 || w > 1000) {
					newHeight -= h / 100;
					w -= w / 100;
				}
				img = resizeImg(img, w, newHeight);
				w = img.getWidth();
				h = img.getHeight();
			}

		} catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "ERROR! File not found","Information", JOptionPane.ERROR_MESSAGE);
			return;
		}
		pixels = new Pixel[w][h];
		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < w; yy++) {
				int pixel = img.getRGB(yy, xx);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				pixels[yy][xx] = new Pixel(red, green, blue, 255);
			}
		}
		
		//convert image
		pixelise();
		new Window(img.getWidth(), img.getHeight(), "Pixelated Image", this);

	}
	
	//an attempt at preventing the loss of some pixels
	//https://stackoverflow.com/questions/15558202/how-to-resize-image-in-java
	private BufferedImage resizeImg(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;      
	}
	
	//splits the image into bigger pixels with a size of blockSize
	//in each big pixel, finds the sum of each pixel's rgb and paints the top left pixel with the average rgb
	private void pixelise() {
		for (int x = 0; x < w; x += blockSize) {
			for (int y = 0; y < h; y += blockSize) {
				Sums sums = new Sums();
				for (int xx = 0; xx < blockSize; ++xx) {
					for (int yy = 0; yy < blockSize; ++yy) {
						if (x + xx >= w || y + yy >= h)
							continue;
						Pixel color = pixels[x + xx][y + yy];
						sums.setA(sums.getA() + color.getA());
						sums.setR(sums.getR() + color.getR());
						sums.setG(sums.getG() + color.getG());
						sums.setB(sums.getB() + color.getB());
						sums.setT(sums.getT() + 1);
					}
				}
				Pixel newPixel = new Pixel(sums.getR() / sums.getT(),
						sums.getG() / sums.getT(), 
						sums.getB() / sums.getT(), 
						sums.getA() / sums.getT());
				pixels[x][y] = newPixel;

			}
		}
	}
	
	//looks at the top left corner of each big pixel, paints a rectangle with a size of blockSize with that pixels color
	public void paint(Graphics g) {

		for (int i = 0; i < h; i += blockSize) {
			for (int j = 0; j < w; j += blockSize) {
				try {
					g.setColor(new Color(pixels[i][j].getR(), pixels[i][j].getG(), pixels[i][j].getB(), pixels[i][j].getA()));
					g.fillRect(i, j, blockSize, blockSize);
				} catch(ArrayIndexOutOfBoundsException e) {
					g.setColor(Color.blue);
					g.fillRect(i, j, blockSize, blockSize);
				}
			}
		}

	}
	
	//saves the resulting image inside the same directory as the program runs in
	//user decides on the name and extension of this file
	//still saves the image even if some of it is missing after giving a pop up warning
	public void saveResult(String fileName, String extention) {
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		int warningCount = 0;
		for (int i = 0; i < h; i += blockSize) {
			for (int j = 0; j < w; j += blockSize) {
				double progressVal = h / 100 * i;
				MainWindow.progressBar.setValue((int) progressVal);
				try {
					int R = pixels[i][j].getR();
					int G = pixels[i][j].getG();
					int B = pixels[i][j].getB();
					int A = pixels[i][j].getA();
					Color color = new Color(R, G, B, A);
					for (int x = 0; x < blockSize; ++x) {
						for (int y = 0; y < blockSize; ++y) {
							if (i + x >= w || j + y >= h)
								continue;
							result.setRGB(i + x, j + y, color.getRGB());
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					if (warningCount++ == 0) {
						JOptionPane.showMessageDialog(null, "Warning! Image resolution is too high,\n"
								+ "part of the new image may be missing.","Warning", JOptionPane.WARNING_MESSAGE);

					}
					continue;
				}
			}
		}
		try {
			ImageIO.write(result, extention, new File(fileName + "." + extention));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//class used for calculating average rgbs of blocks
	private class Sums {
		private int A, R, G, B, T;

		public int getA() { return A; }
		public void setA(int a) { A = a; }
		public int getR() { return R; }
		public void setR(int r) { R = r; }
		public int getG() { return G; }
		public void setG(int g) { G = g; }
		public int getB() { return B; }
		public void setB(int b) { B = b; }
		public int getT() { return T; }
		public void setT(int t) { T = t; }

	}

}
