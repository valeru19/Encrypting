import java.io.*;
import java.util.*;

public class CryptoApp {

    // Определяем общий алфавит, включая пробелы, цифры и специальные символы
    private static final String ALPHABET = " абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{};':\",./<>?`~";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Выводим меню выбора режима
            System.out.println("\nВыберите режим: 1 - Цезарь, 2 - Виженер, 0 - Выход");
            int mode = scanner.nextInt();
            scanner.nextLine(); // Поглощаем оставшийся символ новой строки

            if (mode == 0) {
                System.out.println("Программа завершена.");
                break; // Выход из программы
            }

            // Запрашиваем имя файла
            System.out.println("Введите имя файла:");
            String fileName = scanner.nextLine();

            if (mode == 1) {
                // Режим шифра Цезаря
                System.out.println("Введите ключ для шифра Цезаря:");
                int key = scanner.nextInt();
                scanner.nextLine(); // Поглощаем оставшийся символ новой строки
                caesarCipher(fileName, key); // Вызываем метод для шифра Цезаря
            } else if (mode == 2) {
                // Режим шифра Виженера
                System.out.println("Введите ключ для шифра Виженера:");
                String vigenereKey = scanner.nextLine();
                vigenereCipher(fileName, vigenereKey); // Вызываем метод для шифра Виженера
            } else {
                System.out.println("Неверный режим. Пожалуйста, выберите 1, 2 или 0 для выхода.");
            }
        }
        scanner.close(); // Закрываем Scanner
    }

    // Метод для обработки шифра Цезаря
    private static void caesarCipher(String fileName, int key) {
        try {
            // Читаем текст из файла
            String text = readFile(fileName);
            // Шифруем текст
            String encrypted = caesarEncrypt(text, key);
            // Сохраняем зашифрованный текст в файл
            writeFile("encC_" + fileName, encrypted);
            // Дешифруем текст
            String decrypted = caesarDecrypt(encrypted, key);
            // Сохраняем расшифрованный текст в файл
            writeFile("decC_" + fileName, decrypted);

            // Выводим первые 100 символов исходного, зашифрованного и расшифрованного текста
            System.out.println("Исходный текст: " + text.substring(0, Math.min(100, text.length())));
            System.out.println("Зашифрованный текст: " + encrypted.substring(0, Math.min(100, encrypted.length())));
            System.out.println("Расшифрованный текст: " + decrypted.substring(0, Math.min(100, decrypted.length())));
        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок ввода-вывода
        }
    }

    // Метод для шифрования текста методом Цезаря
    private static String caesarEncrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = ALPHABET.indexOf(c);
            if (index != -1) { // Если символ найден в алфавите
                // Выполняем сдвиг символа
                int shiftedIndex = (index + key + ALPHABET.length()) % ALPHABET.length();
                result.append(ALPHABET.charAt(shiftedIndex));
            } else {
                // Если символ не найден в алфавите, оставляем его без изменений
                result.append(c);
            }
        }
        return result.toString();
    }

    // Метод для дешифрования текста методом Цезаря
    private static String caesarDecrypt(String text, int key) {
        return caesarEncrypt(text, -key); // Дешифрование через шифрование с обратным ключом
    }

    // Метод для обработки шифра Виженера
    private static void vigenereCipher(String fileName, String key) {
        try {
            // Читаем текст из файла
            String text = readFile(fileName);
            // Шифруем текст
            String encrypted = vigenereEncrypt(text, key);
            // Сохраняем зашифрованный текст в файл
            writeFile("encV_" + fileName, encrypted);
            // Дешифруем текст
            String decrypted = vigenereDecrypt(encrypted, key);
            // Сохраняем расшифрованный текст в файл
            writeFile("decV_" + fileName, decrypted);

            // Выводим первые 100 символов исходного, зашифрованного и расшифрованного текста
            System.out.println("Исходный текст: " + text.substring(0, Math.min(100, text.length())));
            System.out.println("Зашифрованный текст: " + encrypted.substring(0, Math.min(100, encrypted.length())));
            System.out.println("Расшифрованный текст: " + decrypted.substring(0, Math.min(100, decrypted.length())));
        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок ввода-вывода
        }
    }

    // Метод для шифрования текста методом Виженера
    private static String vigenereEncrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            int index = ALPHABET.indexOf(c);
            if (index != -1) { // Если символ найден в алфавите
                // Получаем сдвиг из ключа
                int keyChar = ALPHABET.indexOf(key.charAt(keyIndex % key.length()));
                // Выполняем сдвиг символа
                int shiftedIndex = (index + keyChar + ALPHABET.length()) % ALPHABET.length();
                result.append(ALPHABET.charAt(shiftedIndex));
                keyIndex++;
            } else {
                // Если символ не найден в алфавите, оставляем его без изменений
                result.append(c);
            }
        }
        return result.toString();
    }

    // Метод для дешифрования текста методом Виженера
    private static String vigenereDecrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            int index = ALPHABET.indexOf(c);
            if (index != -1) { // Если символ найден в алфавите
                // Получаем сдвиг из ключа
                int keyChar = ALPHABET.indexOf(key.charAt(keyIndex % key.length()));
                // Выполняем обратный сдвиг символа
                int shiftedIndex = (index - keyChar + ALPHABET.length()) % ALPHABET.length();
                result.append(ALPHABET.charAt(shiftedIndex));
                keyIndex++;
            } else {
                // Если символ не найден в алфавите, оставляем его без изменений
                result.append(c);
            }
        }
        return result.toString();
    }

    // Метод для чтения текста из файла
    private static String readFile(String fileName) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
            }
        }
        return text.toString();
    }

    // Метод для записи текста в файл
    private static void writeFile(String fileName, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Записываем текст в файл
            bw.write(content);
        }
    }
}
