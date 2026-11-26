import javax.swing.*;
import java.awt.*;

public class Dessin extends JPanel {
	int x = 100;
	int y = 40;

	public void paint(Graphics g) {

		super.paint(g);

		// g.setColor(Color.BLACK);
		// g.fillRect(x, y, 60, 30);

		try {
			Thread.sleep(50);
		} catch (Exception e) {

		}
		System.out.println("bonjou");
		x++;
		y++;
		repaint();

	}
}