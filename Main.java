package BB;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {

		JFrame obj = new JFrame();

		String rowS = JOptionPane.showInputDialog(null, "Enter no of Rows for bricks");
		String colS = JOptionPane.showInputDialog(null, "Enter no of Cols for bricks");

		String bastSizeS = JOptionPane.showInputDialog(null,
				"Enter your Bat size : 1. For Small 2. For Medium 3. For Large");

		String ballSpeedS = JOptionPane.showInputDialog(null,
				"Enter your Ball speed : 1. For slow 2. For Medium 3. For fast 4");
		int row = Integer.parseInt(rowS);
		int col = Integer.parseInt(colS);
		int batSize = Integer.parseInt(bastSizeS);
		int ballSpeed = Integer.parseInt(ballSpeedS);

		if (batSize == 1)
			batSize = 8 * 5;
		else if (batSize == 2)
			batSize = 16 * 5;
		else if (batSize == 1)
			batSize = 24 * 5;
		else
			batSize = 100;

		if (ballSpeed == 1)
			ballSpeed = 12;
		else if (ballSpeed == 2)
			ballSpeed = 4;
		else if (ballSpeed == 3)
			ballSpeed = -6;
		else if (ballSpeed == 4)
			ballSpeed = -25;
		else
			ballSpeed = 25 - ballSpeed;

		GamePlay gamePlay = new GamePlay(row, col, batSize, ballSpeed);

		obj.setBounds(10, 10, 700, 600);

		obj.setTitle("Brick Breaker");

		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		obj.add(gamePlay);

	}

}
