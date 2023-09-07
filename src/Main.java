import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int WIN_COUNT = 4; // Выигрышная комбинация
    private static final char DOT_HUMAN = 'X'; // Фишка человека
    private static final char DOT_AI = 'O'; // Фишка компа
    private static final char DOT_AMPTY = '-'; // Пустое поле

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static char[][] field; // Переменная для отображения игрового поля
    private static int fieldSizeX; // Размер поля по X
    private static int fieldSizeY; // Размер поля по Y

    public static void main(String[] args) {
//                field = new char[5][5];
//        while (true){
//            initialize();
//            printField();
//            while (true) {
//                humanTurn();
//                printField();
//                if (verdikt()){
//                    System.out.println("Победа");
//                    break;}
//            }
//            System.out.println("Сиграть еще раз?");
//            if (!scanner.nextLine().equalsIgnoreCase("Y"))
//                break;
//        }


        field = new char[5][5];
        while (true){
            initialize();
            printField();
            while (true) {
                humanTurn();
                for (int item: turn) {
                    System.out.println(item);
                }
                printField();
                if (verdikt()){System.out.println("Победа");
                    break;}
                aiTurn();
                printField();
                if (verdikt()){System.out.println("Поражение");
                    break;}
            }
            System.out.println("Сиграть еще раз?");
            if (!scanner.nextLine().equalsIgnoreCase("Y"))
            break;
        }

    }
    /**
     * Переменная координат хода
     */
    private static int [] turn = new int[2];
    /**
     * Метод копирования массива
     */
    private static void copyMassiv(int[] copy, int[] massiv){
        for (int i = 0;i < copy.length; i++){
                copy[i] = massiv[i];
        }
    }
    /**
     * Метод поиска соседней координаты по вертикали/горизонтали
     */
    private static int verticalHorizont(char sims, int index){
        int score = 0;
        char c = field[turn[0]][turn[1]];
        int[] left = new int[2];
        copyMassiv(left, turn);
        while (field[left[0]][left[1]] == c){
            switch (sims){
                case '+' -> left[index] = left[index] + 1;
                case '-' -> left[index] = left[index] - 1;
            }
            try {
                if (field[left[0]][left[1]] == c)score++;
            }catch (ArrayIndexOutOfBoundsException o){return score;}
        }
        return score;
    }
    /**
     * По диагонали diagonal
     */

    private static int diagonal1(char sims){
        int score = 0;
        char c = field[turn[0]][turn[1]];
        int[] left = new int[2];
        copyMassiv(left, turn);
        while (field[left[0]][left[1]] == c){
            if(sims == '+'){
                left[1] = left[1] + 1;
                left[0] = left[0] + 1;
            }
            if (sims == '-'){
                left[1] = left[1] - 1;
                left[0] = left[0] - 1;
            }
            try {
                if (field[left[0]][left[1]] == c)score++;
            }catch (ArrayIndexOutOfBoundsException o){return score;}
        }
        return score;
    }
    private static int diagonal2(char sims){
        int score = 0;
        char c = field[turn[0]][turn[1]];
        int[] left = new int[2];
        copyMassiv(left, turn);
        while (field[left[0]][left[1]] == c){
            if(sims == '+'){
                left[1] = left[1] + 1;
                left[0] = left[0] - 1;
            }
            if (sims == '-'){
                left[1] = left[1] - 1;
                left[0] = left[0] + 1;
            }
            try {
                if (field[left[0]][left[1]] == c)score++;
            }catch (ArrayIndexOutOfBoundsException o){return score;}
        }
        return score;
    }
    /**
     * Мой" метод для проверки условия победы
     */
    private static boolean verdikt(){
        int vertical = verticalHorizont('-',1) + verticalHorizont('+',1);
        int horizont = verticalHorizont('-',0) + verticalHorizont('+',0);
        int diagonal1 = diagonal1('-') + diagonal1('+');
        int diagonal2 = diagonal2('-') + diagonal2('+');
        if (vertical > 1)return true;
        if (horizont > 1)return true;
        if (diagonal1 > 1)return true;
        if (diagonal2 > 1)return true;
        return false;
    }

    /**
     * Инициализация объектов игры
     */
    private static void initialize(){
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                field[x][y] = DOT_AMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля
     */
    private static void printField(){
        System.out.print("+");
        for (int x = 0;x < fieldSizeX * 2 + 1; x++){
            System.out.print((x%2 == 0) ? "-" : x/2 + 1);
        }
        System.out.println();
        for (int x = 0; x < fieldSizeX; x++){
            System.out.print(x+1+"|");
            for (int y = 0; y < fieldSizeY; y++){
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }
        for (int x = 0;x < fieldSizeX * 2 + 2; x++){
            System.out.print("-");
        }
        System.out.println();

    }
    /**
     * Ход игрока
     */
    private static void humanTurn(){
        int x, y;
        do{
            System.out.println("Введите координаты хода x и y (от 1 до 3)\nчерез пробел >>>>");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
        turn[0] = x;
        turn[1] = y;
    }
    /**
     * Ход машины
     */
    private static void aiTurn(){
        int x, y;
        do{
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
        turn = new int[]{x, y};
    }
    /**
     * Проверка на пустоту
     * @param x
     * @param y
     * @return
     */
    private static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_AMPTY;
    }

    /**
     * Проверка корректности ввода
     * @param x
     * @param y
     * @return
     */
    private static boolean isCellValid(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Метод проверки победы
     * @param c
     * @return
     */
    private static boolean chackWin(char c){
        // Проверка по трем горизонталям
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;
        // Проверка по трем вертикалям
        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;
        // Проверка по дагоналям
        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;
        return false;
    }

    /**
     * Проверка на ничью
     * @return
     */
    private static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                if (!isCellEmpty(x, y))
                    return false;
            }
            System.out.println();
        }
        return true;
    }

    /**
     * Проверка состояния игры
     * @param c Игрок
     * @param s
     * @return
     */
    public static boolean checkGemeState(char c, String s){
        if (chackWin(c)){
            System.out.println(s);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья");
            return true;
        }
        return false;
    }
}








































