import org.example.entities.CellEntityType;

public class Cell {
    private int x, y;
    private CellEntityType entityType;
    private CellEntityType initialEntityType;
    private int visited;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        visited = 0;
    }

    public Cell(int x, int y, CellEntityType entityType) {
        this(x, y);
        this.entityType = entityType;
        this.initialEntityType = entityType;
    }

    // metode getter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public CellEntityType getEntityType() {
        return entityType;
    }
    public int getVisited() {
        return visited;
    }
    public CellEntityType getInitialEntityType() {
        return initialEntityType;
    }

    // metode setter
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setEntityType(CellEntityType entityType) {
        this.entityType = entityType;
    }
    public void setVisited() {
        this.visited = 1;
    }
    public void setInitialEntityType(CellEntityType initialEntityType) {
        this.initialEntityType = initialEntityType;
    }

    public String printCell() {
        if (entityType == CellEntityType.PLAYER)
            return "P";
        if (visited == 0)
            return "N";
        return "V";
    }

    @Override
    public String toString() {
        if (visited == 1 && entityType != CellEntityType.PLAYER)
            return "V";
        switch (entityType) {
            case CellEntityType.PLAYER:
                return "P";
            case CellEntityType.SANCTUARY:
                return "S";
            case CellEntityType.PORTAL:
                return "F";
            case CellEntityType.ENEMY:
                return "E";
            case CellEntityType.VOID:
                return "N";
        }
        return "V";
    }
}
