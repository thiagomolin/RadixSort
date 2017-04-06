import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	private static final long serialVersionUID = 1L;

	public Window(int h, int w, String title, Game g){
		
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(h,w));
		frame.setMaximumSize(new Dimension(h,w));
		frame.setMinimumSize(new Dimension(h,w));
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(g);
		g.start();
		
		
	}
	
}
