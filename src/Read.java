import java.util.Scanner;

public class Read {

    private static final Scanner scanner = new Scanner(System.in);

    private Read(){}

    //Reading and validating the Russian letter of a column
    public static int column(String prompt){
        while (true){
            System.out.println(prompt);
            String input = scanner.next();
            if (!input.isEmpty()){
                int col = Field.letterToColumn(input.charAt(0));
                if (col != -1){
                    return col;
                }
            }
            System.out.println("Введите одну из букв: А, Б, В, Г, Д, Е, Ж, З, И, К.");
        }
    }

    //Read Line (0-9)
    public static int row(String prompt){
        while (true){
            System.out.println(prompt);
            if (scanner.hasNextInt()){
                int val = scanner.nextInt();
                if (val >= 0 && val < Field.SIZE){
                    return val;
                }
            } else {
                scanner.next();
            }
            System.out.println("Введите число от 0 до " + (Field.SIZE - 1) + ".");
        }
    }

    //Reading Direction (0 or 1)
    public static int direction(){
        while (true){
            System.out.println("Положение (0 - вертикально, 1 - горизонтально): ");
            if (scanner.hasNextInt()){
                int val = scanner.nextInt();
                if (val == 0 || val == 1){
                    return val;
                }
            } else {
                scanner.next();
            }
            System.out.println("Введите 0 для вертикальной или 1 для горизонтальной установки.");
        }
    }
}
