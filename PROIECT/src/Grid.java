import org.example.entities.CellEntityType;
import org.example.entities.characters.Characters;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private int length, width;
    private Characters c;
    private Cell currentCell;

    // constructor cu access modifier private
    private Grid(int length, int width) {
        super(width);
        // constuirea grid-ului propriu zis prin adaugarea unor obiecte Cell
        for (int i = 0; i < width; i++) {
            add(new ArrayList<>());
            ArrayList<Cell> row = get(i);
            for (int j = 0; j < length; j++) {
                row.add(new Cell(i, j));
            }
        }
        this.length = length;
        this.width = width;
        currentCell = new Cell(0, 0);
    }

    // metode getter
    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Characters getCharacter() {
        return c;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    // metode setter
    static void setCurrentCell(Grid g, int cellNumber, int length) {
        int cellRow = cellNumber / length;
        int cellCol = cellNumber % length;

        ArrayList<Cell> row = g.get(cellRow);
        g.currentCell = row.get(cellCol);
    }

    static void setCurrentCell(Grid g, int cellRow, int cellCol, int length) {
        ArrayList<Cell> row = g.get(cellRow);
        g.currentCell = row.get(cellCol);
    }

    // metoda pentru setarea unui CellEntityType pentru o celula
    static void initCell(Grid g, int length, int cellNumber, CellEntityType entityType) {
        int cellRow = cellNumber / length;
        int cellCol = cellNumber % length;

        ArrayList<Cell> row = g.get(cellRow);
        Cell cell = row.get(cellCol);

        cell.setEntityType(entityType);
        cell.setInitialEntityType(entityType);
    }

    // metoda statica pentru generarea unei harti
    static Grid generateGrid(int length, int width) {
        Grid grid = new Grid(length, width);
        ArrayList<Integer> cellNumbers = new ArrayList<>();
        Random rand = new Random();
        int randIndex;

        for (int i = 0; i < length * width; i++) {
            cellNumbers.add(i);
        }

        // genereaza un Cell pentru jucator
        randIndex = rand.nextInt(cellNumbers.size());
        int randPlayer = cellNumbers.get(randIndex);
        initCell(grid, length, randPlayer, CellEntityType.PLAYER);
        // setarea celulei cu player ca fiind currentCell
        setCurrentCell(grid, randPlayer, length);
        // eliminarea celulei generate pentru a evita situatia in care aceeasi celula
        // este generata de 2 ori prin random
        cellNumbers.remove(randIndex);

        // genereaza un cell pentru portal
        randIndex = rand.nextInt(cellNumbers.size());
        int randPortal = cellNumbers.get(randIndex);
        initCell(grid, length, randPortal, CellEntityType.PORTAL);
        cellNumbers.remove(randIndex);

        // genereaza 2 sanctuare
        for (int i = 0; i < 2; i++) {
            randIndex = rand.nextInt(cellNumbers.size());
            int randSanctuary = cellNumbers.get(randIndex);
            initCell(grid, length, randSanctuary, CellEntityType.SANCTUARY);
            cellNumbers.remove(randIndex);
        }

        // genereaza 4 cells pentru inamici
        for (int i = 0; i < 4; i++) {
            randIndex = rand.nextInt(cellNumbers.size());
            int randEnemy = cellNumbers.get(randIndex);
            initCell(grid, length, randEnemy, CellEntityType.ENEMY);
            cellNumbers.remove(randIndex);
        }

        // celelalte celule sunt marcate ca VOID
        for (int i = 0; i < cellNumbers.size(); i++) {
            initCell(grid, length, cellNumbers.get(i), CellEntityType.VOID);
        }

        return grid;
    }

    // metode pentru verificarea daca o mutare este posibila, returneaza true
    // daca jucatorul se poate muta in directia dorita
    public boolean checkNorthMovePossible() {
        int currentX = currentCell.getX();
        return currentX != 0;
    }

    public boolean checkSouthMovePossible() {
        int currentX = currentCell.getX();
        return currentX != getWidth() - 1;
    }

    public boolean checkWestMovePossible() {
        int currentY = currentCell.getY();
        return currentY != 0;
    }

    public boolean checkEastMovePossible() {
        int currentY = currentCell.getY();
        return currentY != getLength() - 1;
    }

    // metode pentru efectuarea propriu-zisa a mutarii in cele 4 directii posibile
    public void goNorth() throws ImpossibleMove {
        if (checkNorthMovePossible()) {
            setCurrentCell(this,currentCell.getX() - 1, currentCell.getY(), length);
        }
        else{
            throw new ImpossibleMove();
        }
    }

    public void goSouth() throws ImpossibleMove {
        if (checkSouthMovePossible()) {
            setCurrentCell(this, currentCell.getX() + 1, currentCell.getY(), length);
        }
        else {
            throw new ImpossibleMove();
        }
    }

    public void goWest() throws ImpossibleMove {
        if (checkWestMovePossible()) {
            setCurrentCell(this, currentCell.getX(),currentCell.getY() - 1, length);
        }
        else {
            throw new ImpossibleMove();
        }
    }

    public void goEast() throws ImpossibleMove {
        if (checkEastMovePossible()) {
            setCurrentCell(this, currentCell.getX(),currentCell.getY() + 1, length);
        }
        else {
            throw new ImpossibleMove();
        }
    }

    public String showGrid() {
        StringBuilder str = new StringBuilder();
        ArrayList<Cell> row;
        for (int i = 0; i < width; i++) {
            row = get(i);
            for (int j = 0; j < length; j++) {
                str.append(" ").append(row.get(j).toString());
            }
            str.append("\n");
        }
        return str.toString();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        ArrayList<Cell> row;
        for (int i = 0; i < width; i++) {
            row = get(i);
            for (int j = 0; j < length; j++) {
                str.append(" ").append(row.get(j).printCell());
            }
            str.append("\n");
        }
        return str.toString();
    }
}
