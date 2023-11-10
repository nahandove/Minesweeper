package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minefield {
    private static final Cell[][] gameField;
    private final int mineCount;

    static {
        gameField = new Cell[9][9];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                gameField[i][j] = new Cell(i, j);
            }
        }
    }

    public Minefield(int mineCount) {
        this.mineCount = mineCount;
    }

    public void draw() {
        System.out.println("\n |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < gameField.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j].isFlagged() && !hasEmptyNeighbors(gameField[i][j])) {
                    System.out.print('*');
                } else if (!gameField[i][j].isOpen()) {
                    System.out.print('.');
                } else {
                    if (countMineNeighbors(gameField[i][j]) > 0) {
                        System.out.print(countMineNeighbors(gameField[i][j]));
                    } else {
                        System.out.print('/');
                    }
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    public void drawGameOver() {
        System.out.println("\n |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < gameField.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j].isMine()) {
                    System.out.print('X');
                } else if (!gameField[i][j].isOpen()) {
                    System.out.print('.');
                } else {
                    if (countMineNeighbors(gameField[i][j]) > 0) {
                        System.out.print(countMineNeighbors(gameField[i][j]));
                    } else {
                        System.out.print('/');
                    }
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    public void setCells() {
        int count = 0;
        Random random = new Random();
        while (count < mineCount) {
            int i = random.nextInt(9);
            int j = random.nextInt(9);
            if (!gameField[i][j].isMine()) {
                gameField[i][j].setMine(true);
                count++;
            }
        }
    }

    public void resetCell(Cell cell) {
        int newMine = 1;
        cell.setMine(false);
        cell.setOpen(true);
        Random random = new Random();
        while (newMine > 0) {
            int i = random.nextInt(9);
            int j = random.nextInt(9);
            if (!gameField[i][j].equals(cell) && !gameField[i][j].isMine()) {
                gameField[i][j].setMine(true);
                newMine--;
            }
        }
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        if (cell.isMine()) {
            return neighbors;
        }
        if (cell.getJ() > 0) {
            neighbors.add(gameField[cell.getI()][cell.getJ() - 1]);
        }
        if (cell.getJ() < gameField[0].length - 1) {
            neighbors.add(gameField[cell.getI()][cell.getJ() + 1]);
        }
        if (cell.getI() > 0) {
            neighbors.add(gameField[cell.getI() - 1][cell.getJ()]);
        }
        if (cell.getI() < gameField.length - 1) {
            neighbors.add(gameField[cell.getI() + 1][cell.getJ()]);
        }
        if (cell.getI() < gameField.length - 1 && cell.getJ() < gameField[0].length - 1) {
            neighbors.add(gameField[cell.getI() + 1][cell.getJ() + 1]);
        }
        if (cell.getI() > 0 && cell.getJ() > 0) {
            neighbors.add(gameField[cell.getI() - 1][cell.getJ() - 1]);
        }
        if (cell.getI() < gameField.length - 1 && cell.getJ() > 0) {
            neighbors.add(gameField[cell.getI() + 1][cell.getJ() - 1]);
        }
        if (cell.getI() > 0 && cell.getJ() < gameField[0].length - 1) {
            neighbors.add(gameField[cell.getI() - 1][cell.getJ() + 1]);
        }
        return neighbors;
    }

    public Cell openCell(int x, int y) {
        Cell cell = gameField[y][x];
        if (cell.isMine()) {

        } else {
            if (!cell.isOpen()) {
                cell.setOpen(true);
            }
            if (countMineNeighbors(cell) == 0) {
                List<Cell> neighbors = getNeighbors(cell);
                for (Cell neighbor : neighbors) {
                    if (!neighbor.isOpen()) {
                        openCell(neighbor.getJ(), neighbor.getI());
                    }
                }
            }
        }
        return cell;
    }

    private int countMineNeighbors(Cell cell) {
        int count = 0;
        for (Cell neighborCell : getNeighbors(cell)) {
            if (neighborCell.isMine()) {
                count++;
            }
        }
        return count;
    }

    private boolean hasEmptyNeighbors(Cell cell) {
        List<Cell> neighbors = getNeighbors(cell);
        for (Cell neighbor: neighbors) {
            if (neighbor.isOpen() && countMineNeighbors(neighbor) == 0) {
                return true;
            }
        }
        return false;
    }

    public void addFlag(int x, int y) {
        if (gameField[y][x].isFlagged()) {
            gameField[y][x].setFlagged(false);
        } else {
            gameField[y][x].setFlagged(true);
        }
    }

    public boolean isSwept() {
        int trueCount = mineCount;
        int openCellCount = 0;
        int falseCount = 0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (!gameField[i][j].isMine() && gameField[i][j].isFlagged()) {
                    falseCount++;
                }
                if (gameField[i][j].isMine() && gameField[i][j].isFlagged()) {
                    trueCount--;
                }
                if (trueCount == 0 && falseCount == 0) {
                    return true;
                }
            }
        }
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (!gameField[i][j].isMine() && gameField[i][j].isOpen()) {
                    openCellCount++;
                }
                if (openCellCount + mineCount == 81) {
                    return true;
                }
            }
        }
        return false;
    }
}
