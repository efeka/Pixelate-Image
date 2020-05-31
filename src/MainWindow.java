import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class MainWindow {

	JFrame frame;
	private int width = 505, height = 443;

	private JLabel lblSelectDetail, lblHow2Save, lblSaveAs;
	private JTextField txtImagePath, txtFileName;
	private JButton btnGenerateImage, btnSaveImage, btnChooseFile, btnCheckName;
	private JSeparator topSeparator1, topSeparator2, topSeparator3, topSeparator4, topSeparator5;
	private Choice extention;
	private FileChooser fileChooser;
	public static JProgressBar progressBar;

	private Image image;
	
	private boolean correctNameInput;
	
	private GenerateImage generate;

	private final int sliderMin = 0, sliderMax = 30;
	JSlider detailSlider = new JSlider(JSlider.HORIZONTAL, sliderMin, sliderMax, (sliderMin + sliderMax) / 2);

	//actual bounds 5, width - 22 (328)
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.setTitle("Pixel Art Generator");
		frame.setResizable(false);
		frame.getContentPane().setBackground(SystemColor.controlHighlight);
		frame.setBounds(100, 100, width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//choose file
		txtImagePath = new JTextField();
		txtImagePath.setVisible(true);
		txtImagePath.setEditable(false);
		txtImagePath.setBackground(Color.WHITE);
		txtImagePath.setHorizontalAlignment(JTextField.CENTER);
		txtImagePath.setText(" Path of the selected image should appear here");
		txtImagePath.setFont(new Font("Myriad Web Pro", Font.PLAIN, 15));
		txtImagePath.setBounds(width / 2 - 160, 55, 320, 20);
		frame.add(txtImagePath);
		
		btnChooseFile = new JButton("Choose your image file");
		btnChooseFile.setBounds(width / 2 - 100, 17, 200, 26);
		btnChooseFile.setFont(new Font("Myriad Web Pro", Font.BOLD, 14));
		btnChooseFile.setVisible(true);
		btnChooseFile.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				fileChooser = new FileChooser(0);
				image = fileChooser.getImage();
				txtImagePath.setHorizontalAlignment(JTextField.LEFT);
				txtImagePath.setText(fileChooser.getImagePath());
				txtImagePath.setToolTipText(txtImagePath.getText());
			}
		});
		frame.add(btnChooseFile);
		
		//slider and label
		lblSelectDetail = new JLabel("Choose individual pixel size");
		lblSelectDetail.setVisible(true);
		lblSelectDetail.setFont(new Font("Myriad Web Pro", Font.PLAIN, 18));
		lblSelectDetail.setBounds(width / 2 - 115, 73, 230, 50);
		frame.add(lblSelectDetail);

		detailSlider.setMajorTickSpacing(5);
		detailSlider.setMinorTickSpacing(1);
		detailSlider.setPaintTicks(true);
		detailSlider.setPaintLabels(true);
		detailSlider.setFont(new Font("Myriad Web Pro", Font.PLAIN, 15));
		detailSlider.setBounds(width / 2 - 185, 118, 370, 50);
		detailSlider.setVisible(true);
		frame.add(detailSlider);

		//generate pixel art button
		btnGenerateImage = new JButton("Generate pixel art");
		btnGenerateImage.setBounds(width / 2 - 100, 190, 200, 30);
		btnGenerateImage.setFont(new Font("Myriad Web Pro", Font.BOLD, 14));
		btnGenerateImage.setVisible(true);
		btnGenerateImage.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				progressBar.setValue(0);
				if (detailSlider.getValue() == 0)
					generate = new GenerateImage(image, 1);
				else
					generate = new GenerateImage(image, detailSlider.getValue());
			}
		});
		frame.add(btnGenerateImage);

		//separators
		topSeparator1 = new JSeparator();
		topSeparator1.setVisible(true);
		topSeparator1.setBounds(5, 5, width - 25, 2);
		frame.add(topSeparator1);
		topSeparator2 = new JSeparator();
		topSeparator2.setVisible(true);
		topSeparator2.setBounds(5, 245, width - 25, 2);
		frame.add(topSeparator2);
		topSeparator3 = new JSeparator(JSeparator.VERTICAL);
		topSeparator3.setVisible(true);
		topSeparator3.setBounds(4, 5, 2, 396);
		frame.add(topSeparator3);
		topSeparator4 = new JSeparator(JSeparator.VERTICAL);
		topSeparator4.setVisible(true);
		topSeparator4.setBounds(485, 5, 2, 395);
		frame.add(topSeparator4);
		topSeparator5 = new JSeparator();
		topSeparator5.setVisible(true);
		topSeparator5.setBounds(4, 400, width - 23, 2);
		frame.add(topSeparator5);
		
		//saving guide
		lblHow2Save = new JLabel("How is my new image saved?");
		lblHow2Save.setVisible(true);
		lblHow2Save.setForeground(new Color(0, 0, 160));
		lblHow2Save.setFont(new Font("Myriad Web Pro", Font.PLAIN, 15));
		lblHow2Save.setBounds(width / 2 - 100, 260, 200, 20);
		Font font = lblHow2Save.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblHow2Save.setFont(font.deriveFont(attributes));
		lblHow2Save.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				lblHow2Save.setForeground(Color.blue);
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				lblHow2Save.setForeground(new Color(0, 0, 160));
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Give a name to the file that you want to save your new image into,\nby typing it into the text area below."
						+ "\nNote that the file name should comply to Windows file naming constraints.\nVisit \"https://docs.microsoft.com/en-us/windows/win32/fileio/naming-a-file#naming-conventions\"\nfor more information.\nMake sure to test this by clicking on the \"Check name\" button.\n"
						+ "\nOnce you click on \"Save new image\", a file will be created\nwith the name you gave it, inside the same directory as the program is located in.\n"
						+ "Lastly, wait until the progress bar at the bottom of the window reaches 100%.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		frame.add(lblHow2Save);
		
		//everything else for saving
		txtFileName = new JTextField();
		txtFileName.setVisible(true);
		txtFileName.setEditable(true);
		txtFileName.setBackground(Color.WHITE);
		txtFileName.setFont(new Font("Myriad Web Pro", Font.PLAIN, 17));
		txtFileName.setBounds(88, 300, 200, 20);
		frame.add(txtFileName);
		
		extention = new Choice();
		extention.setFont(new Font("Myriad Web Pro", Font.PLAIN, 13));
		extention.setBounds(295, 299, 55, 18);
		extention.add(".png");
		extention.add(".jpg");
		extention.add(".jfif");
		frame.add(extention);
		
		lblSaveAs = new JLabel("Save as:");
		lblSaveAs.setVisible(true);
		lblSaveAs.setFont(new Font("Myriad Web Pro", Font.PLAIN, 18));
		lblSaveAs.setBounds(20, 299, 100, 20);
		frame.add(lblSaveAs);

		btnCheckName = new JButton("Check name");
		btnCheckName.setBounds(360, 299, 100, 20);
		btnCheckName.setFont(new Font("Myriad Web Pro", Font.BOLD, 12));
		btnCheckName.setVisible(true);
		btnCheckName.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(MouseEvent e) {
				progressBar.setValue(0);
				switch (checkName(txtFileName.getText())) {
				case 0:
					correctNameInput = true;
					JOptionPane.showMessageDialog(null, "File name \"" + txtFileName.getText() + "\" is approved", "Information", JOptionPane.INFORMATION_MESSAGE);
					break;
				case 1:
					correctNameInput = false;
					JOptionPane.showMessageDialog(null, "File name cannot contain the following characters:\n"
							+ "< > : \\ / | ? *", "Incorrect File Name", JOptionPane.ERROR_MESSAGE);
					break;
				case 2:
					correctNameInput = false;
					JOptionPane.showMessageDialog(null, "File name cannot be left empty.", "Incorrect File Name", JOptionPane.ERROR_MESSAGE);
					break;
				case 3:
					correctNameInput = false;
					JOptionPane.showMessageDialog(null, "File name cannot end with a whitespace or a period.", "Incorrect File Name", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		});
		frame.add(btnCheckName);
		
		btnSaveImage = new JButton("Save new image");
		btnSaveImage.setBounds(width / 2 - 100, 335, 200, 25);
		btnSaveImage.setFont(new Font("Myriad Web Pro", Font.BOLD, 14));
		btnSaveImage.setVisible(true);
		btnSaveImage.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(MouseEvent e) {
				System.out.println("save");
				if (!correctNameInput)
					JOptionPane.showMessageDialog(null, "Faulty file name.", "Incorrect File Name", JOptionPane.ERROR_MESSAGE);
				else {
					JOptionPane.showMessageDialog(null, "Everything is ready, saving your file now.", "Information", JOptionPane.INFORMATION_MESSAGE);
					generate.saveResult(txtFileName.getText(), extention.getSelectedItem().substring(1));
				}	
			}
		});
		frame.add(btnSaveImage);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(width / 2 - 150, 370, 300, 20);
		progressBar.setVisible(true);
		frame.add(progressBar);
	}
	
	//windows file name constraints
	private int checkName(String name) {
		if (name.contains("<") || name.contains(">") ||
				name.contains(":") || name.contains("\"") ||
				name.contains("\\") || name.contains("/") ||
				name.contains("|") || name.contains("?") ||
				name.contains("*"))
			return 1;
		if (name == null || name.equals(""))
			return 2;
		if (name.substring(name.length() - 1).equals(" ") || name.substring(name.length() - 1).equals("."))
			return 3;
		return 0;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}
}
