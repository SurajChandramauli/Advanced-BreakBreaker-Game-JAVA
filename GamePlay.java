package BB;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;

	private int score = 0;
	private int totalBricks;

	private Timer timer;

	private int LevelDelay = 0;
	private int playerX = 310;
	private int ballposX = 340;
	private int ballposY = 530;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private int rowNo;
	private int colNo;
	private int batSize;

	private MapGenerator map;

	GamePlay(int rowNo, int colNo, int batSize, int ballSpeed) {
		// row and column
		this.rowNo = rowNo;
		this.colNo = colNo;
		this.batSize = batSize;
		LevelDelay = ballSpeed;
		map = new MapGenerator(rowNo, colNo);
		this.totalBricks = rowNo * colNo;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(LevelDelay, this);
		timer.start();

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(1, 1, 1092, 992);

		map.draw((Graphics2D) g);

		// border of the screen
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		g.setColor(Color.blue);
		g.fillRect(playerX, 550, batSize, 11);

		g.setColor(Color.green);
		g.fillOval(ballposX, ballposY, 20, 20);

		g.setColor(Color.black);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);

		// game won by user
		if (totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won, Score: " + score, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart.", 230, 350);

		}

		// game loss by user
		if (ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart.", 230, 350);
		}

		// garbage collection to make program more efficient
		g.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
			// Ball - Pedal interaction
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;

						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;

						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;

							if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
								ballXdir = -ballXdir;
							else {
								ballYdir = -ballYdir;
							}
						}

					}

				}
			}

			ballposX += ballXdir;
			ballposY += ballYdir;
			if (ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if (ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if (ballposX > 670) {
				ballXdir = -ballXdir;

			}

		}

		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();

			}
		}

		// enter key after game over
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				playerX = 310;
				ballposX = 340;
				ballposY = 530;
				ballXdir = -1;
				ballYdir = -2;
				score = 0;

				map = new MapGenerator(rowNo, colNo);
				totalBricks = rowNo * colNo;

				repaint();
			}
		}

	}

	private void moveLeft() {

		play = true;
		playerX -= 20;

	}

	private void moveRight() {

		play = true;
		playerX += 20;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
