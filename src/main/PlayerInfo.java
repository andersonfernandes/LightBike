package main;

import java.util.ArrayList;

/**
 * Created by anderson on 02/04/17.
 */
public class PlayerInfo implements Observer {

    private Player player;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Line> lines;

    private int playerIndex;
    private Direction nextDirection;
    private int linesCount;

    public PlayerInfo() {
        rectangles = new ArrayList<>();
        lines = new ArrayList<>();
        nextDirection = null;
        linesCount = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(ArrayList<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }

    @Override
    public void update() {
        if (nextDirection != player.getDirection() && player.getAlive()) {
            if (nextDirection == Direction.UP) {
                rectangles
                        .add(new Rectangle(player.getLocation().getx() - PlayView.getOneSide(),
                                player.getLocation().gety() - PlayView.getOneSide(),
                                player.getLocation().getx() + PlayView.getOneSide(),
                                player.getLocation().gety() - PlayView.getOneSide() + linesCount));
            }
            else if (nextDirection == Direction.RIGHT) {
                rectangles
                        .add(new Rectangle(player.getLocation().getx() + PlayView.getOneSide() - linesCount,
                                player.getLocation().gety() - PlayView.getOneSide(),
                                player.getLocation().getx() + PlayView.getOneSide(),
                                player.getLocation().gety() + PlayView.getOneSide()));
            }
            else if (nextDirection == Direction.DOWN) {
                rectangles
                        .add(new Rectangle(player.getLocation().getx() - PlayView.getOneSide(),
                                player.getLocation().gety() + PlayView.getOneSide() - linesCount,
                                player.getLocation().getx() + PlayView.getOneSide(),
                                player.getLocation().gety() + PlayView.getOneSide()));
            }
            else if (nextDirection == Direction.LEFT) {
                rectangles
                        .add(new Rectangle(player.getLocation().getx() - PlayView.getOneSide(),
                                player.getLocation().gety() - PlayView.getOneSide(),
                                player.getLocation().getx() - PlayView.getOneSide() + linesCount,
                                player.getLocation().gety() + PlayView.getOneSide()));
            }

            PlayView.setPlayerPosition(player, playerIndex);

//            playerLines.get(p).clear();
            linesCount = 0;
            nextDirection = null;
        }
    }
}
