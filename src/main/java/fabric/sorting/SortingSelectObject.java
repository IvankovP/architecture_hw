package fabric.sorting;

import fabric.FileUtils;
import fabric.enums.SortingType;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class SortingSelectObject implements SortingObject {

    @Override
    public void sort(String fileNameIn, String fileNameOut) throws IOException {
        List<String> lines = FileUtils.getLines(50, Paths.get(fileNameIn));

        selectionSort(lines);

        FileUtils.writeFile(SortingType.SELECT.name(), Paths.get(fileNameOut));
        lines.forEach(s -> FileUtils.writeFile(s, Paths.get(fileNameOut)));
    }

    public static void selectionSort(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            int pos = i;
            String min = list.get(i);

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(min) < 0) {
                    pos = j;
                    min = list.get(j);
                }
            }
            list.set(pos, list.get(i));
            list.set(i, min);
        }
    }
}

