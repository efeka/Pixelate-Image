import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	
	JFrame frame;
	
	private int width = 500;
	
	private File file;
	private Image image;
	private String path;
	
	private JFileChooser fileChooser;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter(".png & .jpg & .jfif files", "jpg", "png", "jfif");
	
	public FileChooser(int type) {
		if (type == 0) {
			fileChooser = new JFileChooser();
			fileChooser.setFileFilter(filter);
			fileChooser.setDialogTitle("Choose image file");
			int returnVal = fileChooser.showOpenDialog(fileChooser.getParent());
			fileChooser.setBounds(width / 2 - 200, 5, 400, 350);
			fileChooser.setVisible(true);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				try {
					image = ImageIO.read(file);
					path = file.getAbsolutePath();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "ERROR! Could not find file","Information", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (type == 1) {
			fileChooser = new JFileChooser();
			fileChooser.setFileFilter(filter);
			int returnVal = fileChooser.showOpenDialog(fileChooser.getParent());
			fileChooser.setBounds(width / 2 - 200, 5, 400, 350);
			fileChooser.setVisible(true);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
			}
		}
	}
	
	public File getFile() { return file; }
	public String getImagePath() { return path; }
	public Image getImage() { return image; }
	
}	
