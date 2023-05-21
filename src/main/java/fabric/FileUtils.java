package fabric;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private FileUtils() {
    }

    public static void createFileIfNotExist(Path path) {
        if (path.toFile().exists()) {
            return;
        }
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getLines(int count, Path source) {
        String readString;
        List<String> result = new ArrayList<>();

        try (BufferedReader bf = Files.newBufferedReader(source)) {
            while (result.size() < count && (readString = bf.readLine()) != null) {
                result.add(readString);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static void writeFile(String s, Path path) {
        try {
            Files.writeString(path, s, StandardOpenOption.APPEND);
            Files.writeString(path, System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeRandomStrings(Path path) throws IOException {
        for (int i = 0; i < 100; i++) {
            Files.writeString(path, randomString(), StandardOpenOption.APPEND);
            Files.writeString(path, System.lineSeparator(), StandardOpenOption.APPEND);
        }
    }

    private static String randomString() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static void writeMatrix(Path path, int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            for (int j = 0; j < count; j++) {
                stringBuilder.append(i + j);
                if (j < count-1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(System.lineSeparator());
        }
        writeFile(stringBuilder.toString(), path);
    }

    public static void writeMatrix(Path path, int[][] matrix) {
        for (int[] ints : matrix) {
            writeFile(Arrays.toString(ints).replace("[", "").replace("]", ""), path);
        }
    }
}
