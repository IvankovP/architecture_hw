package adapter.generate;

import java.nio.file.Path;

public interface IGenerateMatrix {
    int[][] generateMatrix(int i);

    void writeMatrix(Path path, int[][] matrix);

    int[][] sumMatrix(int[][] matrix1, int[][] matrix2);
}
