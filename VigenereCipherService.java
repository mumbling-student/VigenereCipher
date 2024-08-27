import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VigenereCipherService {
    public static void decode(String filePath, String keyString) {
        try {
            char[] initialMessageCharArr = getCharArrOfInitialMessage(filePath);
            char[] keySequenceCharArr = getCharArrOfKeySequence(keyString, initialMessageCharArr);
            decodeMessage(initialMessageCharArr, keySequenceCharArr);
            createFile(filePath, initialMessageCharArr, "-decoded.txt");
            createFile(filePath, keySequenceCharArr, "-key_sequence.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void encode(String filePath, String keyString) {
        try {
            char[] initialMessageCharArr = getCharArrOfInitialMessage(filePath);
            char[] keySequenceCharArr = getCharArrOfKeySequence(keyString, initialMessageCharArr);
            encodeMessage(initialMessageCharArr, keySequenceCharArr);
            createFile(filePath, initialMessageCharArr, "-encoded.txt");
            createFile(filePath, keySequenceCharArr, "-key_sequence.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createFile(String filePath, char[] chars, String suffix) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath.split("\\.")[0] + suffix);
        fileWriter.write(chars);
        fileWriter.flush();
        fileWriter.close();
    }

    private static void decodeMessage(char[] initialMessageCharArr, char[] keySequenceCharArr) {
        for (int i = 0; i < keySequenceCharArr.length; i++) {
            if (initialMessageCharArr[i] >= 65 && initialMessageCharArr[i] <= 90) {
                initialMessageCharArr[i] = (char) (initialMessageCharArr[i] - (keySequenceCharArr[i] - 65));
                if (initialMessageCharArr[i] < 65) {
                    initialMessageCharArr[i] += 26;
                }
            }
        }
    }

    private static void encodeMessage(char[] initialMessageCharArr, char[] keySequenceCharArr) {
        for (int i = 0; i < keySequenceCharArr.length; i++) {
            if (initialMessageCharArr[i] >= 65 && initialMessageCharArr[i] <= 90) {
                initialMessageCharArr[i] = (char) (initialMessageCharArr[i] + (keySequenceCharArr[i] - 65));
                if (initialMessageCharArr[i] > 90) {
                    initialMessageCharArr[i] -= 26;
                }
            }
        }
    }

    private static char[] getCharArrOfKeySequence(String keyString, char[] initialMessageCharArr) {
        char[] keySequenceCharArr = new char[initialMessageCharArr.length];
        final char[] KEY_CHAR_ARR = keyString.toUpperCase().toCharArray();
        for (int i = 0, j = 0; i < keySequenceCharArr.length; i++) {
            if (initialMessageCharArr[i] >= 65 && initialMessageCharArr[i] <= 90) {
                keySequenceCharArr[i] = KEY_CHAR_ARR[j];
                j++;
                if (j > KEY_CHAR_ARR.length - 1) {
                    j = 0;
                }
            } else {
                keySequenceCharArr[i] = initialMessageCharArr[i];
            }
        }
        return keySequenceCharArr;
    }

    private static char[] getCharArrOfInitialMessage(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
            line = bufferedReader.readLine();
        }
        return stringBuilder.toString().toUpperCase().toCharArray();
    }
}
