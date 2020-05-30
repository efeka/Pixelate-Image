import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class Window {

	public static JProgressBar progress;
	
	public Window(int w, int h, String title, GenerateImage main) {
		main.setPreferredSize(new Dimension(w, h));
		main.setMaximumSize(new Dimension(w, h));
		main.setMinimumSize(new Dimension(w, h));
		
		JFrame frame=new JFrame(title);

		frame.add(main);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}
