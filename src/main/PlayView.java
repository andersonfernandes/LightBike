package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PlayView extends JPanel implements ActionListener, Observable {
	private static final int WINDOW_WIDTH = Application.WINDOW_WIDTH;
	private static final int WINDOW_HEIGHT = Application.WINDOW_HEIGHT;

	private int delay;

	private int playerWidth = 0;

	private static final int SMALL_WIDTH = 7; //px
	private static final int MEDIUM_WIDTH = 15; //px
	private static final int LARGE_WIDTH = 23; //px

	private static int oneSide;

	private Location tempLocation;

	// TODO: Initialize to 0s for readability
	private static int[][] gameBoard = new int[WINDOW_WIDTH][WINDOW_HEIGHT];

	private ArrayList<Observer> playersInfoObservers = new ArrayList<>();

	private ArrayList<Integer> playersDead = new ArrayList<Integer>();

	private Application application;

	private Timer gameTime;

	private int numPlayers = 2;

	private Player p1, p2, p3, p4;
	private ArrayList<Player> players = new ArrayList<Player>();

	public PlayView() {
	}

	@Override
	public void attachObserver(Observer observer) {
		playersInfoObservers.add(observer);
	}

	@Override
	public void detachObserver(Observer observer) {
		playersInfoObservers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : playersInfoObservers) {
			observer.update();
		}
	}

	public static int getOneSide() {
		return oneSide;
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			processKeyPressed(e);
		}
	}

	public void start() {
		initializeGame();
		requestFocus();
	}

	public void resume() {
		requestFocus();

		gameTime = new Timer(delay, this);
		gameTime.start();
	}

	public void stop() {
		gameTime.stop();
	}

	private void initializeGame() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
		setBackground(Palette.BLACK);
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		numPlayers = application.players;
		
		switch (application.speed) {
			case "Slow": delay = 8;
				break;
			case "Medium": delay = 5;
				break;
			case "Fast": delay = 3;
				break;
			case "Impossible": delay = 1;
				break;
			default: delay = 5;
				break;
		}

		if (application.size.equals("Small")) {
			playerWidth = SMALL_WIDTH;
		}
		else if (application.size.equals("Medium")) {
			playerWidth = MEDIUM_WIDTH;
		}
		else if (application.size.equals("Large")) {
			playerWidth = LARGE_WIDTH;
		}
		oneSide = ((playerWidth - 1) / 2);

		initializePlayers();

		for (int p = 0; p < numPlayers; p++) {
			PlayerInfo playerInfo = new PlayerInfo();

			Player player = players.get(p);
			setPlayerPosition(player, p);

			Rectangle rectangle = new Rectangle(player.getLocation().getx() - oneSide,
					player.getLocation().gety() - oneSide, player.getLocation().getx() + oneSide,
					player.getLocation().gety() + oneSide);
			playerInfo.getRectangles().add(rectangle);

			playerInfo.setPlayer(player);
			attachObserver(playerInfo);
		}

		gameTime = new Timer(delay, this);
		gameTime.start();
	}

	private void initializePlayers() {
		p1 = new Player(new Location(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2 - 4 * playerWidth), Direction.UP, Palette.GREEN,
				true, playerWidth);
		players.add(p1);

		p2 = new Player(new Location(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2 + 4 * playerWidth), Direction.DOWN,
				Palette.MAGENTA, true, playerWidth);
		players.add(p2);

		p3 = new Player(new Location(WINDOW_WIDTH / 2 + 4 * playerWidth, WINDOW_HEIGHT / 2), Direction.RIGHT, Palette.RED,
				true, playerWidth);
		players.add(p3);

		p4 = new Player(new Location(WINDOW_WIDTH / 2 - 4 * playerWidth, WINDOW_HEIGHT / 2), Direction.LEFT, Palette.BLUE,
				true, playerWidth);
		players.add(p4);
	}

	public static void setPlayerPosition(Player player, int playerIndex) {
		for (int i = -oneSide; i <= oneSide; i++) {
			for (int j = -oneSide; j <= oneSide; j++) {
				gameBoard[player.getLocation().getx() + i][player.getLocation().gety() + j] = playerIndex + 1;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		updateGame(g);

		Toolkit.getDefaultToolkit().sync();
	}

	public void processKeyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		ArrayList<Direction> currentDirections = new ArrayList<Direction>();

		for (int p = 0; p < numPlayers; p++) {
			currentDirections.add(players.get(p).getDirection());
		}

		if (numPlayers >= 1) {
			PlayerInfo info = (PlayerInfo) playersInfoObservers.get(0);

			if (p1.getDirection() != Direction.DOWN && p1.getDirection() != Direction.UP) {
				if (key == KeyEvent.VK_UP) {
					info.setNextDirection(Direction.UP);
					p1.setDirection(Direction.UP);
				}
				else if (key == KeyEvent.VK_DOWN) {
					info.setNextDirection(Direction.DOWN);
					p1.setDirection(Direction.DOWN);
				}
			}

			else if (p1.getDirection() != Direction.LEFT && p1.getDirection() != Direction.RIGHT) {
				if (key == KeyEvent.VK_RIGHT) {
					info.setNextDirection(Direction.RIGHT);
					p1.setDirection(Direction.RIGHT);
				}
				else if (key == KeyEvent.VK_LEFT) {
					info.setNextDirection(Direction.LEFT);
					p1.setDirection(Direction.LEFT);
				}
			}
		}
		if (numPlayers >= 2) {
			PlayerInfo info = (PlayerInfo) playersInfoObservers.get(1);

			if (p2.getDirection() != Direction.DOWN && p2.getDirection() != Direction.UP) {
				if (key == KeyEvent.VK_W) {
					info.setNextDirection(Direction.UP);
					p2.setDirection(Direction.UP);
				}
				else if (key == KeyEvent.VK_S) {
					info.setNextDirection(Direction.DOWN);
					p2.setDirection(Direction.DOWN);
				}
			}
			else if (p2.getDirection() != Direction.LEFT && p2.getDirection() != Direction.RIGHT) {
				if (key == KeyEvent.VK_D) {
					info.setNextDirection(Direction.RIGHT);
					p2.setDirection(Direction.RIGHT);
				}
				else if (key == KeyEvent.VK_A) {
					info.setNextDirection(Direction.LEFT);
					p2.setDirection(Direction.LEFT);
				}
			}
		}
		if (numPlayers >= 3) {
			PlayerInfo info = (PlayerInfo) playersInfoObservers.get(2);

			if (p3.getDirection() != Direction.DOWN && p3.getDirection() != Direction.UP) {
				if (key == KeyEvent.VK_I) {
					info.setNextDirection(Direction.UP);
					p3.setDirection(Direction.UP);
				}
				else if (key == KeyEvent.VK_K) {
					info.setNextDirection(Direction.DOWN);
					p3.setDirection(Direction.DOWN);
				}
			}
			else if (p3.getDirection() != Direction.LEFT && p3.getDirection() != Direction.RIGHT) {
				if (key == KeyEvent.VK_L) {
					info.setNextDirection(Direction.RIGHT);
					p3.setDirection(Direction.RIGHT);
				}
				else if (key == KeyEvent.VK_J) {
					info.setNextDirection(Direction.LEFT);
					p3.setDirection(Direction.LEFT);
				}
			}
		}
		if (numPlayers >= 4) {
			PlayerInfo info = (PlayerInfo) playersInfoObservers.get(3);

			if (p4.getDirection() != Direction.DOWN && p4.getDirection() != Direction.UP) {
				if (key == KeyEvent.VK_T) {
					info.setNextDirection(Direction.UP);
					p4.setDirection(Direction.UP);
				}
				else if (key == KeyEvent.VK_G) {
					info.setNextDirection(Direction.DOWN);
					p4.setDirection(Direction.DOWN);
				}
			}
			else if (p4.getDirection() != Direction.LEFT && p4.getDirection() != Direction.RIGHT) {
				if (key == KeyEvent.VK_H) {
					info.setNextDirection(Direction.RIGHT);
					p4.setDirection(Direction.RIGHT);
				}
				else if (key == KeyEvent.VK_F) {
					info.setNextDirection(Direction.LEFT);
					p4.setDirection(Direction.LEFT);
				}
			}
		}

		// executed when player changes direction
		// convert individual lines to a big rectangle, change the player position, and update the corner indices
		notifyObservers();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: check collision with itself or other players
		move();
		repaint();
	}

	public boolean checkCollision(Location playerLoc, Direction dir) {
		if (dir == Direction.UP) {
			for (int i = -oneSide; i <= oneSide; i++) {
				if (gameBoard[playerLoc.getx() + i][tempLocation.gety() - oneSide] != 0) {
					if (Application.DEBUG)
						System.out.println("Collision dir up!");
					return true;
				}
			}
		}
		else if (dir == Direction.RIGHT) {
			for (int i = -oneSide; i <= oneSide; i++) {
				if (gameBoard[playerLoc.getx() + oneSide][playerLoc.gety() + i] != 0) {
					if (Application.DEBUG)
						System.out.println("Collision dir right!");
					return true;
				}
			}
		}
		else if (dir == Direction.DOWN) {
			for (int i = -oneSide; i <= oneSide; i++) {
				if (gameBoard[playerLoc.getx() + i][tempLocation.gety() + oneSide] != 0) {
					if (Application.DEBUG)
						System.out.println("Collision dir down!");
					return true;
				}
			}
		}
		else if (dir == Direction.LEFT) {
			for (int i = -oneSide; i <= oneSide; i++) {
				if (gameBoard[playerLoc.getx() - oneSide][playerLoc.gety() + i] != 0) {
					if (Application.DEBUG)
						System.out.println("Collision dir left!");
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkOutOfBounds(Location loc) {
		if (loc.getx() - oneSide < 1 || loc.getx() + oneSide >= WINDOW_WIDTH - 1 || loc.gety() - oneSide < 1
				|| loc.gety() + oneSide >= WINDOW_HEIGHT - 1) {
			return true;
		}
		return false;
	}

	public void move() {
		boolean endGameCollision = true;

		for (Observer observer : playersInfoObservers) {
			PlayerInfo info = (PlayerInfo) observer;

			if (info.getPlayer().getAlive()) { endGameCollision = false; }
		}
		
		if (endGameCollision) {
			application.swapGameOver(playersDead);
			stop();
		}

		int index = 0;
		for (Observer observer : playersInfoObservers) {
			PlayerInfo info = (PlayerInfo) observer;

			Player player = info.getPlayer();
			if (player.getAlive()) {
				tempLocation = player.getLocation();

				if (player.getDirection() == Direction.UP) {
					tempLocation.sety(tempLocation.gety() - 1);
				}
				else if (player.getDirection() == Direction.RIGHT) {
					tempLocation.setx(tempLocation.getx() + 1);
				}
				else if (player.getDirection() == Direction.DOWN) {
					tempLocation.sety(tempLocation.gety() + 1);
				}
				else if (player.getDirection() == Direction.LEFT) {
					tempLocation.setx(tempLocation.getx() - 1);
				}
				player.setLocation(tempLocation); // location is moved by one pixel in some direction

				if (checkCollision(player.getLocation(), player.getDirection())
						|| checkOutOfBounds(player.getLocation())) {
					player.setAlive(false);
					playersDead.add(index + 1);
				}
				else {
					if (player.getDirection() == Direction.UP) {
						for (int i = -oneSide; i <= oneSide; i++) {
							gameBoard[tempLocation.getx() + i][tempLocation.gety() - oneSide] = 1;
						}
						info.getLines()
								.add(new Line(player.getLocation().getx() - oneSide,
										player.getLocation().gety() - oneSide,
										player.getLocation().getx() + oneSide,
										player.getLocation().gety() - oneSide));
					}
					else if (player.getDirection() == Direction.DOWN) {
						for (int i = -oneSide; i <= oneSide; i++) {
							gameBoard[tempLocation.getx() + i][tempLocation.gety() + oneSide] = 1;
						}
						info.getLines()
								.add(new Line(player.getLocation().getx() - oneSide,
										player.getLocation().gety() + oneSide,
										player.getLocation().getx() + oneSide,
										player.getLocation().gety() + oneSide));
					}
					else if (player.getDirection() == Direction.LEFT) {
						for (int i = -oneSide; i <= oneSide; i++) {
							gameBoard[tempLocation.getx() - oneSide][tempLocation.gety() + i] = 1;
						}
						info.getLines()
								.add(new Line(player.getLocation().getx() - oneSide,
										player.getLocation().gety() - oneSide,
										player.getLocation().getx() - oneSide,
										player.getLocation().gety() + oneSide));
					}
					else if (player.getDirection() == Direction.RIGHT) {
						for (int i = -oneSide; i <= oneSide; i++) {
							gameBoard[tempLocation.getx() + oneSide][tempLocation.gety() + i] = 1;
						}
						info.getLines()
								.add(new Line(player.getLocation().getx() + oneSide,
										player.getLocation().gety() - oneSide,
										player.getLocation().getx() + oneSide,
										player.getLocation().gety() + oneSide));
					}
					info.setLinesCount(info.getLinesCount() +1);
				}
			}
		}
	}

	public void updateGame(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);


		for (Observer observer : playersInfoObservers) {
			PlayerInfo info = (PlayerInfo) observer;

			g.setColor(info.getPlayer().getColor());

			// draw all the lines
			for (Line line : info.getLines()) {
				g.drawLine(line.getx1(), line.gety1(), line.getx2(), line.gety2());
			}

			// draw all the rectangles
			for (Rectangle rectangle : info.getRectangles()) {
				g.fillRect(rectangle.getx1(), rectangle.gety1(),
						rectangle.getx2() + 1 - rectangle.getx1(),
						rectangle.gety2() + 1 - rectangle.gety1());
			}
		}

		if (Application.DEBUG) {
			g.setColor(Palette.RED);
			g.drawLine(p1.getLocation().getx(), p1.getLocation().gety(), p1.getLocation().getx(),
					p1.getLocation().gety());
		}
	}

	// to be called from Application.java
	public void setApp(Application app) {
		this.application = app;
	}
}
