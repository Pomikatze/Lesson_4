package ru.geekbrains.Lesson_4;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        play(scanner, random);
    }

    static void play(Scanner scanner, Random random) {
        System.out.println("Введите длину поля (больше 2):");
        int size = scanner.nextInt();
        char[][] field = getField(size); //ЗАДАНИЕ №3
        drawField(field);

        do {
            doPlayerMove(scanner, field, size);
            if (isFinal(field, 'X', size)) {
                break;
            }

            doAIMove(random, field);
            if (isFinal(field, 'O', size)) {
                break;
            }
            drawField(field);
        } while (true);

        System.out.println("Результат:");
        drawField(field);
    }

    static boolean isFinal(char[][] field, char sign, int size) {
        if (isWin(field, sign, size)) {
            String name = sign == 'X' ? "Игрок" : "Компьютер";
            System.out.println(String.format("%s победил", name));
            return true;
        }
        if (isDraw(field)) {
            System.out.println("There is draw detected. Thanks god no one won!");
            return true;
        }
        return false;
    }

    static boolean isWin(char[][] field, char sign, int size) {
        int count = 0;
        /**
         * Проверка на горизонталь
         */
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++){
                if (field[i][j] == sign){
                    count++;
                    if (count >= 3) {
                        return true;
                    }
                } else count = 0;
            }
            count = 0;
        }

        /**
         * Проверка на вертикаль
         */
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++){
                if (field[j][i] == sign){
                    count++;
                    if (count >= 3) {
                        return true;
                    }
                } else count = 0;
            }
            count = 0;
        }

        /**
         * Проверка на диагональ. ЗАДАНИЕ №2
         */
        for (int i = 0; i < field.length; i++){
            for (int j =0; j < field.length; j++){
                if (i == j && field[i][j] == sign){
                       count++;
                        if(count >= 3){
                            return true;
                        }
                } else count = 0;
            }
        }
        count = 0;

        for (int i = 0; i < field.length; i++) {
                if (field[i][size - 1 - i] == sign) {
                    count++;
                    if(count >= 3) {
                        return true;
                    }
                } else count = 0;
        }
        count = 0;

        return false;
    }

    static boolean isDraw(char[][] field) {
        int count = field.length * field.length;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] != '-') {
                    count--;
                }
            }
        }
        return count == 0;
    }

    static void doAIMove(Random random, char[][] field) {
        int x, y;
        for (int i = 0; i < field.length - 2; i++){
            for (int j = 0; j < field.length - 2; j++){
                if (field[i][j] == 'X' && field[i][j+1] == 'X' && field[i][j+2] == '-'){ //ЗАДАНИЕ №4
                    field[i][j+2] = 'O';
                } else if (field[i][j] == 'X' && field[i+1][j] == 'X' && field[i+2][j] == '-'){
                    field[i+2][j] = 'O';
                } else if (field[i][i] == 'X' && field[i+1][i+1] == 'X' && field[i+2][i+2] == '-'){
                    field[i+2][i+2] = 'O';
                } else if (field[i][field.length - 1 - i] == 'X' && field[i+1][field.length - 2 - i] == 'X' && field[i+2][field.length - 3 - i] == '-'){
                    field[i+2][field.length - 3 - i] = 'O';
                } else{
                    do {
                        x = random.nextInt(3);
                        y = random.nextInt(3);
                    } while (field[x][y] != '-');
                    field[x][y] = 'O';
                }
            }
        }
    }


    static void doPlayerMove(Scanner scanner, char[][] field, int size) {
        int x, y;
        do {
            x = getCoordinate(scanner, 'X', size);
            y = getCoordinate(scanner, 'Y', size);
        } while (field[x][y] != '-');
        field[x][y] = 'X';
    }

    static int getCoordinate(Scanner scanner, char coordName, int size) {
        int coord;
        do {
            System.out.println(String.format("Пожалуйста, укажите %s-координату в диапазоне [1, %s] ...", coordName, size));
            /**
             * Получение значения от пользователя в даипазоне [1, 3] и
             * последующая конвертация в индексы массива
             */
            coord = scanner.nextInt() - 1;
        } while (coord < 0 || coord > size);
        return coord;
    }

    static char[][] getField(int size) {
            char field[][] = new char[size][size]; //ЗАДАНИЕ №3
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field.length; j++) {
                    field[i][j] = '-';
                }
            }
            return field;
        }



    static void drawField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}
