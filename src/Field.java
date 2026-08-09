import java.util.Random;

public class Field {
    public static final int SIZE = 10;

    // Russian letters for columns (without Ё to get exactly 10 letters)
    public static final char[] LETTERS = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'К'};

    // Cell states
    public static final int EMPTY = 0;
    public static final int SHIP = 1;
    public static final int HIT = 2;
    public static final int MISS = 3;

    private final int[][] grid = new int[SIZE][SIZE];
    private int totalShipCells = 0;

    public int getCell(int x, int y) {
        return grid[x][y];
    }

    // Random placement of ships for the computer
    public void generateRandomBoard() {
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        Random random = new Random();

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int r = random.nextInt(SIZE);
                int c = random.nextInt(SIZE);
                boolean horizontal = random.nextBoolean();

                if (canPlaceShip(r, c, size, horizontal)) {
                    placeShip(r, c, size, horizontal);
                    placed = true;
                }
            }
        }
    }

    // Checking the possibility of installing a ship (taking into account the halo around)
    public boolean canPlaceShip(int h1, int h2, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int currH1 = h1 + (horizontal ? 0 : i);
            int currH2 = h2 + (horizontal ? i : 0);

            if (currH1 < 0 || currH1 >= SIZE || currH2 < 0 || currH2 >= SIZE) {
                return false;
            }

            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = currH1 + dr;
                    int nc = currH2 + dc;
                    if (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE) {
                        if (grid[nr][nc] == SHIP) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void placeShip(int h1, int h2, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int currH1 = h1 + (horizontal ? 0 : i);
            int currH2 = h2 + (horizontal ? i : 0);
            grid[currH1][currH2] = SHIP;
        }
        totalShipCells += size;
    }

    // Drawing a field with column letters
    public void printField(boolean hideShips) {
        System.out.print("   ");
        for (char letter : LETTERS) {
            System.out.print(letter + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            // Formatting the Line Output (0-9)
            System.out.printf("%2d ", i);
            for (int x = 0; x < SIZE; x++) {
                char symbol = '~';
                int cell = grid[i][x];

                if (cell == SHIP) {
                    symbol = hideShips ? '~' : 'S';
                } else if (cell == HIT) {
                    symbol = 'X';
                } else if (cell == MISS) {
                    symbol = '*';
                }
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }

    public boolean makeShot(int i, int x) {
        if (grid[i][x] == SHIP) {
            grid[i][x] = HIT;
            totalShipCells--;
            return true;
        } else if (grid[i][x] == EMPTY) {
            grid[i][x] = MISS;
            return false;
        }
        return false;
    }

    public boolean isAllSunk() {
        return totalShipCells <= 0;
    }

    // Auxiliary method: convert a Russian letter to a column index (0-9)
    public static int letterToColumn(char ch) {
        ch = Character.toUpperCase(ch);
        for (int i = 0; i < LETTERS.length; i++) {
            if (LETTERS[i] == ch) {
                return i;
            }
        }
        return -1; // Invalid letter
    }
}
