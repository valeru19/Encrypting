import java.io.*;
import java.util.*;

public class CryptoApp {

    // Основной метод программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) { // Бесконечный цикл для возврата в главное меню
            // Запрос у пользователя выбора режима работы
            System.out.println("\nВыберите режим: 1 - Цезарь, 2 - Виженер, 0 - Выход");
            int mode = scanner.nextInt();
            scanner.nextLine(); // Поглощение оставшегося символа новой строки

            if (mode == 0) {
                // Выход из программы
                System.out.println("Программа завершена.");
                break;
            }

            // Запрос у пользователя имени файла
            System.out.println("Введите имя файла:");
            String fileName = scanner.nextLine();

            // Обработка выбора режима
            if (mode == 1) {
                // Режим шифра Цезаря
                System.out.println("Введите ключ для шифра Цезаря:");
                int key = scanner.nextInt();
                scanner.nextLine(); // Поглощение оставшегося символа новой строки
                caesarCipher(fileName, key);
            } else if (mode == 2) {
                // Режим шифра Виженера
                System.out.println("Введите ключ для шифра Виженера:");
                String vigenereKey = scanner.nextLine();
                vigenereCipher(fileName, vigenereKey);
            } else {
                // Обработка неверного выбора режима
                System.out.println("Неверный режим. Пожалуйста, выберите 1, 2 или 0 для выхода.");
            }
        }
        scanner.close(); // Закрытие сканера
    }

    // Метод для обработки шифра Цезаря
    private static void caesarCipher(String fileName, int key) {
        try {
            // Чтение текста из файла
            String text = readFile(fileName);
            // Шифрование текста
            String encrypted = caesarEncrypt(text, key);
            // Сохранение зашифрованного текста в файл
            writeFile("encC_" + fileName, encrypted);
            // Дешифрование текста
            String decrypted = caesarDecrypt(encrypted, key);
            // Сохранение расшифрованного текста в файл
            writeFile("decC_" + fileName, decrypted);

            // Вывод первых 100 символов исходного, зашифрованного и расшифрованного текста
            System.out.println("Исходный текст: " + text.substring(0, Math.min(100, text.length())));
            System.out.println("Зашифрованный текст: " + encrypted.substring(0, Math.min(100, encrypted.length())));
            System.out.println("Расшифрованный текст: " + decrypted.substring(0, Math.min(100, decrypted.length())));
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
        }
    }

    // Метод для шифрования текста методом Цезаря
    private static String caesarEncrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                // Определение базового символа для сдвига (кириллица или латиница)
                char base = Character.isLowerCase(c) ? (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 'а' : 'a')
                        : (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 'А' : 'A');
                int alphabetSize = Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 32 : 26; // Размер алфавита (32 для кириллицы, 26 для латиницы)
                result.append((char) ((c - base + key + alphabetSize) % alphabetSize + base));
            } else {
                // Если символ не буква, оставляем его без изменений
                result.append(c);
            }
        }
        return result.toString();
    }

    // Метод для дешифрования текста методом Цезаря
    private static String caesarDecrypt(String text, int key) {
        // Дешифрование через шифрование с обратным ключом
        return caesarEncrypt(text, -key);
    }

    // Метод для обработки шифра Виженера
    private static void vigenereCipher(String fileName, String key) {
        try {
            // Чтение текста из файла
            String text = readFile(fileName);
            // Шифрование текста
            String encrypted = vigenereEncrypt(text, key);
            // Сохранение зашифрованного текста в файл
            writeFile("encV_" + fileName, encrypted);
            // Дешифрование текста
            String decrypted = vigenereDecrypt(encrypted, key);
            // Сохранение расшифрованного текста в файл
            writeFile("decV_" + fileName, decrypted);

            // Вывод первых 100 символов исходного, зашифрованного и расшифрованного текста
            System.out.println("Исходный текст: " + text.substring(0, Math.min(100, text.length())));
            System.out.println("Зашифрованный текст: " + encrypted.substring(0, Math.min(100, encrypted.length())));
            System.out.println("Расшифрованный текст: " + decrypted.substring(0, Math.min(100, decrypted.length())));
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
        }
    }

    // Метод для шифрования текста методом Виженера
    private static String vigenereEncrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase(); // Приведение ключа к верхнему регистру
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                // Определение базового символа для сдвига (кириллица или латиница)
                char base = Character.isLowerCase(c) ? (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 'а' : 'a')
                        : (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 'А' : 'A');
                int alphabetSize = Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 32 : 26; // Размер алфавита (32 для кириллицы, 26 для латиницы)
                int keyChar = key.charAt(keyIndex % key.length()) - 'A'; // Получаем сдвиг из ключа
                result.append((char) ((c - base + keyChar + alphabetSize) % alphabetSize + base));
                keyIndex++;
            } else {
                // Если символ не буква, оставляем его без изменений
                result.append(c);
            }
        }
        return result.toString();
    }

    // Метод для дешифрования текста методом Виженера
    private static String vigenereDecrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase(); // Приведение ключа к верхнему регистру
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                // Определение базового символа для сдвига (кириллица или латиница)
                char base = Character.isLowerCase(c) ? (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 'а' : 'a')
                        : (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 'А' : 'A');
                int alphabetSize = Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ? 32 : 26; // Размер алфавита (32 для кириллицы, 26 для латиницы)
                int keyChar = key.charAt(keyIndex % key.length()) - 'A'; // Получаем сдвиг из ключа
                result.append((char) ((c - base - keyChar + alphabetSize) % alphabetSize + base));
                keyIndex++;
            } else {
                // Если символ не буква, оставляем его без изменений
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
            // Чтение файла построчно
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
            }
        }
        return text.toString();
    }

    // Метод для записи текста в файл
    private static void writeFile(String fileName, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Запись текста в файл
            bw.write(content);
        }
    }
}