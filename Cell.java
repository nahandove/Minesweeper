package minesweeper;

public class Cell {
    private int i;
    private int j;
    private boolean isMine;
    private boolean isOpen;
    private boolean isFlagged;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
        this.isMine = false;
        this.isOpen = false;
        this.isFlagged = false;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
