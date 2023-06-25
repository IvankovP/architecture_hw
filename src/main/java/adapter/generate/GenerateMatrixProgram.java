package adapter.generate;

import fabric.FileUtils;

import java.nio.file.Path;

public class GenerateMatrixProgram implements IGenerateMatrix {
    @Override
    public int[][] generateMatrix(int count) {
        int[][] result =  new int[count][count];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                result[i][j] = i + j;
            }
        }

        return result;
    }

    @Override
    public void writeMatrix(Path path, int[][] matrix) {
        FileUtils.writeMatrix(path, matrix);
    }

    @Override
    public int[][] sumMatrix(int[][] matrix1, int[][] matrix2) {
        throw new UnsupportedOperationException();
    }
}
