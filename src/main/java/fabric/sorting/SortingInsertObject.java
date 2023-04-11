package fabric.sorting;

import fabric.FileUtils;
import fabric.enums.SortingType;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class SortingInsertObject implements SortingObject {
    @Override
    public void sort(String fileNameIn, String fileNameOut) throws IOException {
        List<String> lines = FileUtils.getLines(50, Paths.get(fileNameIn));

        insertionSort(lines);

        FileUtils.writeFile(SortingType.INSERT.name(), Paths.get(fileNameOut));
        lines.forEach(s -> FileUtils.writeFile(s, Paths.get(fileNameOut)));
    }

    public static void insertionSort(List<String> list) {
        int j;

        for (int i = 1; i < list.size(); i++) {
            String swap = list.get(i);

            for (j = i; j > 0 && list.get(j - 1).compareTo(swap) > 0; j--) {
                list.set(j, list.get(j - 1));
            }

            list.set(j, swap);
        }
    }
}