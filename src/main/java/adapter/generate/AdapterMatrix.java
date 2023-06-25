package adapter.generate;

import adapter.process.IProcessMatrix;

import java.nio.file.Path;

public class AdapterMatrix implements IGenerateMatrix {

    private final IProcessMatrix processMatrix;
    private final IGenerateMatrix generateMatrix;

    public AdapterMatrix(IProcessMatrix processMatrix, IGenerateMatrix generateMatrix) {
        this.processMatrix = processMatrix;
        this.generateMatrix = generateMatrix;
    }

    @Override
    public int[][] generateMatrix(int i) {
        return generateMatrix.generateMatrix(i);
    }

    @Override
    public void writeMatrix(Path path, int[][] matrix) {
        generateMatrix.writeMatrix(path, matrix);
    }

    @Override
    public int[][] sumMatrix(int[][] matrix1, int[][] matrix2) {
        return processMatrix.sum(matrix1, matrix2);
    }
}
