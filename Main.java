package minesweeper;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? ");
        int mineCount = scanner.nextInt();
        Minefield minefield = new Minefield(mineCount);
        minefield.setCells();
        minefield.draw();
        System.out.print("Set/unset mines marks or claim a cell as free: ");
        startGame(minefield);
    }

    public static void startGame(Minefield minefield) {
        int freeCounter = 0;
        while (!minefield.isSwept()) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            int xCoordinate = scanner.nextInt();
            int yCoordinate = scanner.nextInt();
            String command = scanner.next();
            if ("mine".equals(command)) {
                minefield.addFlag(xCoordinate - 1, yCoordinate - 1);
            } else if ("free".equals(command)) {
                Cell cell = minefield.openCell(xCoordinate - 1, yCoordinate - 1);
                if (cell.isMine()) {
                    if (freeCounter == 0) {
                        minefield.resetCell(cell);
                        minefield.openCell(xCoordinate - 1, yCoordinate - 1);
                    } else {
                        minefield.drawGameOver();
                        gameOver();
                    }
                }
                freeCounter++;
            }
            minefield.draw();
        }
        win();
    }

    private static void gameOver() {
        System.out.println("You stepped on a mine and failed!");
        System.exit(0);
    }

    private static void win() {
        System.out.println("Congratulations! You found all the mines!");
    }
}
