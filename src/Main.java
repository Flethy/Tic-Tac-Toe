import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        char[][] field = inputField();
        printField(field);
        // enter and check the coordinates
        char simbol = 'X';
        while (checkState(field).equals("Game not finished")) {
            makeMove(field, simbol);
            simbol = (simbol == 'X') ? 'O' : 'X'; // change the symbol
        }
    }

    // input data to field
    private static char[][] inputField() {
        char[][] field = new char[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(field[i], ' ');
        }
        return field;
    }

    // printing the field
    private static void printField(char[][] field) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.print(" |");
            System.out.println();
        }
        System.out.println("---------");
    }

    private static void makeMove(char[][] field, char simbol) {
        Scanner scanner = new Scanner(System.in);
        int x = 1, y = 1;
        boolean isAgain = true;
        while (isAgain) {
            System.out.print("Enter the coordinates: ");
            if (scanner.hasNextInt()) {
                x = scanner.nextInt();
                if (scanner.hasNextInt()) {
                    y = scanner.nextInt();
                    if ((x <= 3 && x >=0) && (y <= 3 && y >=0)) {
                        if (field[3-y][x-1] == ' ') {
                            field[3-y][x-1] = simbol;
                            printField(field);
                            isAgain = false;
                        } else { System.out.println("This cell is occupied! Choose another one!"); }
                    } else { System.out.println("Coordinates should be from 1 to 3!"); }
                } else {scanner.next();  System.out.println("You should enter numbers!"); }
            } else { scanner.next();  System.out.println("You should enter numbers!"); }
        }
    }

    //Possible states:
    //"Game not finished" - when no side has a three in a row but the field has empty cells;
    //"Draw" - when no side has a three in a row and the field has no empty cells;
    //"X wins" - when the field has three X in a row;
    //"O wins" - when the field has three O in a row;
    //"Impossible" - when the field has three X in a row as well as three O in a row. Or the field has a lot more X's than O's or vice versa (if the difference is 2 or more, should be 1 or 0).
    public static String checkState(char[][] field) {
        String result = "Impossible";
        int countOfO = 0;
        int countOfX = 0;
        int countOfEmpty = 0;
        int countOfWinX = 0;
        int countOfWinO = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                countOfX += field[i][j] == 'X' ? 1 : 0;
                countOfO += field[i][j] == 'O' ? 1 : 0;
                countOfEmpty += field[i][j] == ' ' ? 1 : 0;
            }
            if (field[i][0] == 'X' && field[i][1] == 'X' && field[i][2] == 'X') countOfWinX++;
            if (field[0][i] == 'X' && field[1][i] == 'X' && field[2][i] == 'X') countOfWinX++;
            if (field[i][0] == 'O' && field[i][1] == 'O' && field[i][2] == 'O') countOfWinO++;
            if (field[0][i] == 'O' && field[1][i] == 'O' && field[2][i] == 'O') countOfWinO++;
        }
        if (field[0][0] == 'X' && field[1][1] == 'X' && field[2][2] == 'X') countOfWinX++;
        if (field[0][2] == 'X' && field[1][1] == 'X' && field[2][0] == 'X') countOfWinX++;
        if (field[0][0] == 'O' && field[1][1] == 'O' && field[2][2] == 'O') countOfWinO++;
        if (field[0][2] == 'O' && field[1][1] == 'O' && field[2][0] == 'O') countOfWinO++;

        if (countOfWinO > 0 && countOfWinX > 0) result = "Impossible";
        else if (Math.abs(countOfO - countOfX) > 1) result = "Impossible";
        else if (countOfWinX == 0 && countOfWinO == 1) { result = "O wins"; System.out.println(result); }
        else if (countOfWinX == 1 && countOfWinO == 0) { result = "X wins";  System.out.println(result); }
        else if (countOfWinX == 0 && countOfWinO == 0 && countOfEmpty == 0) { result = "Draw";  System.out.println(result); }
        else if (countOfWinX == 0 && countOfWinO == 0 && countOfEmpty > 0) result = "Game not finished";

        return result;
    }
}