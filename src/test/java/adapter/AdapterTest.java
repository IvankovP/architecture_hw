package adapter;

import adapter.generate.AdapterMatrix;
import adapter.generate.GenerateMatrixProgram;
import adapter.generate.IGenerateMatrix;
import adapter.process.IProcessMatrix;
import adapter.process.ProcessMatrixProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static fabric.FileUtils.createFileIfNotExist;
import static fabric.FileUtils.writeMatrix;
import static java.nio.file.Files.deleteIfExists;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AdapterTest {

    private final String in = Paths.get(System.getProperty("java.io.tmpdir")).toAbsolutePath() + "/in.txt";
    private final String out = Paths.get(System.getProperty("java.io.tmpdir")).toAbsolutePath() + "/out.txt";
    private final String out2 = Paths.get(System.getProperty("java.io.tmpdir")).toAbsolutePath() + "/out2.txt";

    @BeforeEach
    void before() {
        try {
            deleteIfExists(Paths.get(in));
            deleteIfExists(Paths.get(out));
            deleteIfExists(Paths.get(out2));
            initFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initFiles() throws IOException {
        createFileIfNotExist(Paths.get(in));
        createFileIfNotExist(Paths.get(out));
        createFileIfNotExist(Paths.get(out2));
        writeMatrix(Paths.get(in), 3);
        writeMatrix(Paths.get(in), 3);
    }

    @Test
    void writeMatrixToFileTest() throws IOException {
        IProcessMatrix processMatrix = new ProcessMatrixProgram();
        int[][] matrix1 = processMatrix.read(in, 3, 0);
        int[][] matrix2 = processMatrix.read(in, 3, 3);
        int[][] matrixResult = processMatrix.sum(matrix1, matrix2);
        processMatrix.write(Paths.get(out), matrixResult);

        List<String> result = Files.readAllLines(Paths.get(out));

        assertEquals(3, result.size());
    }

    @Test
    void generateMatrixAndWriteToFileTest() throws IOException {
        IGenerateMatrix generator = new GenerateMatrixProgram();
        int[][] matrix = generator.generateMatrix(3);
        generator.writeMatrix(Paths.get(out2), matrix);

        List<String> result = Files.readAllLines(Paths.get(out2));

        assertEquals(3, result.size());
    }

    @Test
    void adapterTest() throws IOException {
        IGenerateMatrix adapter = new AdapterMatrix(new ProcessMatrixProgram(), new GenerateMatrixProgram());
        int[][] matrix = adapter.generateMatrix(3);
        int[][] matrix2 = adapter.generateMatrix(3);
        int[][] matrixResult = adapter.sumMatrix(matrix, matrix2);

        adapter.writeMatrix(Paths.get(out2), matrixResult);

        List<String> result = Files.readAllLines(Paths.get(out2));

        assertEquals(3, result.size());
    }
}
