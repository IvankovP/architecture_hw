package adapter.process;

import fabric.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessMatrixProgram implements IProcessMatrix {
    @Override
    public int[][] read(String in, int count, int countSkip) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(in));
        int[][] result = new int[count][count];
        AtomicInteger skipped = new AtomicInteger();

        AtomicInteger j = new AtomicInteger();
        allLines.forEach(s -> {
            if (j.get() == count) {
                return;
            }
            if (!s.isBlank() && skipped.get() >= countSkip) {
                String[] strings = s.split(", ");
                for (int i = 0; i < strings.length; i++) {
                    result[j.get()][i] = Integer.parseInt(strings[i]);
                }
                j.getAndIncrement();
            }
            skipped.getAndIncrement();
        });

        return result;
    }

    @Override
    public int[][] sum(int[][] matrix1, int[][] matrix2) {
        int[][] result =  new int[matrix1.length][matrix1.length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }

    @Override
    public void write(Path path, int[][] matrix) {
        FileUtils.writeMatrix(path, matrix);
    }
}
