package fabric;

import fabric.enums.SortingType;
import fabric.sorting.SortingInsertObject;
import fabric.sorting.SortingMergeObject;
import fabric.sorting.SortingObject;
import fabric.sorting.SortingSelectObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FabricTest {

    private final IoC<SortingObject> ioc = new IoC<>();
    private final String in = Paths.get(System.getProperty("java.io.tmpdir")).toAbsolutePath() + "/in.txt";
    private final String out = Paths.get(System.getProperty("java.io.tmpdir")).toAbsolutePath() + "/out.txt";

    @BeforeEach
    void before() {
        try {
            Files.deleteIfExists(Paths.get(in));
            Files.deleteIfExists(Paths.get(out));
            initIoC();
            initFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initIoC() {
        ioc.resolve(SortingType.INSERT.name(), args -> new SortingInsertObject());
        ioc.resolve(SortingType.MERGE.name(), args -> new SortingMergeObject());
        ioc.resolve(SortingType.SELECT.name(), args -> new SortingSelectObject());
    }

    void initFiles() throws IOException {
        FileUtils.createFileIfNotExist(Paths.get(in));
        FileUtils.createFileIfNotExist(Paths.get(out));
        FileUtils.writeRandomStrings(Paths.get(in));
    }

    @Test
    void copyFileInsertMethodIncorrectTypeTest() {
        assertNull(ioc.resolve("test"));
    }

    @Test
    void copyFileInsertMethodTest() throws IOException {
        SortingObject sortingObject = ioc.resolve(SortingType.INSERT.name());
        sortingObject.sort(in, out);
        List<String> result = Files.readAllLines(Paths.get(out));

        assertEquals(51, result.size());
        assertEquals(SortingType.INSERT.name(), result.get(0));
    }

    @Test
    void copyFileMergeMethodTest() throws IOException {
        SortingObject sortingObject = ioc.resolve(SortingType.MERGE.name());
        sortingObject.sort(in, out);
        List<String> result = Files.readAllLines(Paths.get(out));

        assertEquals(51, result.size());
        assertEquals(SortingType.MERGE.name(), result.get(0));
    }

    @Test
    void copyFileSelectMethodTest() throws IOException {
        SortingObject sortingObject = ioc.resolve(SortingType.SELECT.name());
        sortingObject.sort(in, out);
        List<String> result = Files.readAllLines(Paths.get(out));

        assertEquals(51, result.size());
        assertEquals(SortingType.SELECT.name(), result.get(0));
    }
}
