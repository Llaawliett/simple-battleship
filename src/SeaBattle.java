import java.util.Random;

public class SeaBattle {
    private static final Random random = new Random();

    public void play() {
        System.out.println("=== ДОБРО ПОЖАЛОВАТЬ В 'МОРСКОЙ БОЙ' ===");

        Field playerField = new Field();
        Field computerField = new Field();

        computerField.generateRandomBoard();
        System.out.println("Компьютер расставил свои корабли.");

        setupPlayerShips(playerField);

        boolean playerTurn = true;

        while (!playerField.isAllSunk() && !computerField.isAllSunk()) {
            if (playerTurn) {
                System.out.println("\n--- ВАШ ХОД ---");
                System.out.println("ПОЛЕ СОПЕРНИКА:");
                computerField.printField(true);

                int col = Read.column("Введите букву столбца (А-К): ");
                int r = Read.row("Введите номер строки (0-9): ");

                int targetState = computerField.getCell(r, col);
                if (targetState == Field.HIT || targetState == Field.MISS) {
                    System.out.println("Вы уже стреляли в эту клетку! Попробуйте снова.");
                    continue; //Repeat Turn Without Changing Players
                }

                boolean hit = computerField.makeShot(r, col);
                if (hit) {
                    System.out.println("ПОПАДАНИЕ! Вы ходите снова.");
                    if (computerField.isAllSunk()) break;
                } else {
                    System.out.println("ПРОМАХ! Ход переходит к компьютеру.");
                    playerTurn = false;
                }
            } else {
                System.out.println("\n--- ХОД КОМПЬЮТЕРА ---");
                int r, c;
                do {
                    r = random.nextInt(Field.SIZE);
                    c = random.nextInt(Field.SIZE);
                } while (playerField.getCell(r, c) == Field.HIT || playerField.getCell(r, c) == Field.MISS);

                System.out.printf("Компьютер стреляет в [%c, %d]...\n", Field.LETTERS[c], r);
                boolean hit = playerField.makeShot(r, c);

                if (hit) {
                    System.out.println("Компьютер попал в ваш корабль! Он ходит снова.");
                    if (playerField.isAllSunk()) break;
                } else {
                    System.out.println("Компьютер промахнулся!");
                    playerTurn = true;
                }
            }
        }

        System.out.println("\n=== ИГРА ОКОНЧЕНА ===");
        if (computerField.isAllSunk()) {
            System.out.println("ПОЗДРАВЛЯЕМ! Вы уничтожили все корабли компьютера!");
        } else {
            System.out.println("Вы проиграли. Компьютер уничтожил все ваши корабли.");
        }
    }

    private static void setupPlayerShips(Field field) {
        System.out.println("\nРасставьте свои корабли:");
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        for (int i = 0; i < shipSizes.length; i++) {
            int size = shipSizes[i];
            boolean placed = false;

            while (!placed) {
                System.out.println("\nВаше текущее поле:");
                field.printField(false);
                System.out.printf("Установка %d-палубного корабля (%d из %d):\n", size, i + 1, shipSizes.length);

                int c = Read.column("Введите начальную букву столбца (А-К): ");
                int r = Read.row("Введите начальный номер строки (0-9): ");

                boolean horizontal = true;
                if (size > 1) {
                    int dir = Read.direction();
                    horizontal = (dir == 1);
                }

                if (field.canPlaceShip(r, c, size, horizontal)) {
                    field.placeShip(r, c, size, horizontal);
                    placed = true;
                } else {
                    System.out.println("❌ Ошибка! Корабль нельзя здесь разместить.");
                }
            }
        }
        System.out.println("\nВсе ваши корабли успешно расставлены!");
    }
}

