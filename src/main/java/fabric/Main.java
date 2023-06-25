package fabric;

import fabric.enums.SortingType;
import fabric.sorting.SortingInsertObject;
import fabric.sorting.SortingMergeObject;
import fabric.sorting.SortingObject;
import fabric.sorting.SortingSelectObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    private static final IoC<SortingObject> ioc = new IoC<>();
    private static String in;
    private static String out;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        printString("Enter sorting method:");
        printString("Available sorting methods - " + Arrays.toString(SortingType.values()));
        String type = br.readLine();

        printString("Enter source path:");
        in = br.readLine();

        printString("Enter target path:");
        out = br.readLine();

        if (in.equalsIgnoreCase(out)) {
            printString("IN and OUT must be not equals");
            return;
        }

        initFiles();
        initIoC();

        SortingObject sortingObject = ioc.resolve(type);
        if (sortingObject == null) {
            printString("Sorting method not found");
            return;
        }
        sortingObject.sort(in, out);

        printString("Exit");
    }

    private static void printString(String s) {
        System.out.print(s);
        System.out.println(System.lineSeparator());
    }

    private static void initIoC() {
        ioc.resolve(SortingType.INSERT.name(), args -> new SortingInsertObject());
        ioc.resolve(SortingType.MERGE.name(), args -> new SortingMergeObject());
        ioc.resolve(SortingType.SELECT.name(), args -> new SortingSelectObject());
    }

    private static void initFiles() {
        FileUtils.createFileIfNotExist(Paths.get(out));
    }
}
