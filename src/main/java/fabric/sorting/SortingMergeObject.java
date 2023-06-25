package fabric.sorting;

import fabric.FileUtils;
import fabric.enums.SortingType;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SortingMergeObject implements SortingObject {
    @Override
    public void sort(String fileNameIn, String fileNameOut) throws IOException {
        List<String> lines = FileUtils.getLines(50, Paths.get(fileNameIn));

        mergeSort(lines, lines.size());

        FileUtils.writeFile(SortingType.MERGE.name(), Paths.get(fileNameOut));
        lines.forEach(s -> FileUtils.writeFile(s, Paths.get(fileNameOut)));
    }

    public static void mergeSort(List<String> list, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        List<String> l = new ArrayList<>(mid);
        List<String> r = new ArrayList<>(n - mid);

        for (int i = 0; i < mid; i++) {
            l.add(list.get(i));
        }
        for (int i = mid; i < n; i++) {
            r.add(list.get(i));
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(list, l, r, mid, n - mid);
    }

    public static void merge(List<String> list, List<String> l, List<String> r, int left, int right) {

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left && j < right) {
            if (l.get(i).compareTo(r.get(j)) <= 0) {
                list.set(k++, l.get(i++));
            } else {
                list.set(k++, r.get(j++));
            }
        }
        while (i < left) {
            list.set(k++, l.get(i++));
        }
        while (j < right) {
            list.set(k++, r.get(j++));
        }
    }
}
