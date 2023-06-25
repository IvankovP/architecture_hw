package adapter.process;

import java.io.IOException;
import java.nio.file.Path;

public interface IProcessMatrix {
    int[][] read(String in, int count, int countSkip) throws IOException;

    int[][] sum(int[][] matrix1, int[][] matrix2);

    void write(Path path, int[][] matrix);
}
