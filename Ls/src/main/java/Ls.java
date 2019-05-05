import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Ls {
    static File item;
    private static boolean l;
    private static boolean h;
    private static boolean r;
    private static String outputPath = "";
    static TreeMap<String, String> answer = new TreeMap<>();
    public Ls(boolean l, boolean h, boolean o,String outputPath){
        this.l = l;
        this.h = h;
        this.r = o;
        this.outputPath = outputPath;

    }

    public static void switchHumanReadable(StringBuilder str, long lengthItemInBytes) {
        int base = 1024;
        String[] type = {" B"," KB"," MB"," GB"};
        DecimalFormat lengthItem = new DecimalFormat("###.##");
        int i;
        double k = lengthItemInBytes;
        for (i = 0; i<2; i++) {
            if (k >= base) {
                k /= base;
                i++;
            }
            if (k >= base) {
                k /= base;
                i++;
            }
        }
        str.append(lengthItem.format(k)).append(type[i - 1]);
    }
    public static void reverseTreeMap(TreeMap<String, String> treeMap){
        for(String key : treeMap.descendingKeySet()){
            System.out.println(treeMap.get(key) + " " + key);
        }
    }
    public static void getRWX(boolean mode) {
        File[] array = new File[]{item};
        if (item.isDirectory()) {
            array = item.listFiles();
        }
        for (File testFile : array) {
            StringBuilder str = new StringBuilder();
            String lastChange = new SimpleDateFormat("hh:mm").format(new Date(testFile.lastModified()));
            if (testFile.canRead())
                    str.append("r");
            else
                str.append("-");
            if (testFile.canWrite())
                    str.append("w");
                else
                        str.append("-");
            if (testFile.canExecute())
                        str.append("x");
                else str.append("-");
            str.append("  ").append(lastChange).append("  ");
            long lengthItemInBytes = 0;
            if (testFile.isFile())
                lengthItemInBytes = testFile.length();
            if (mode)
                switchHumanReadable(str, lengthItemInBytes);
            else
                str.append(lengthItemInBytes);

            answer.replace(testFile.getName(), str.toString());
        }
    }


    public static class MyException extends RuntimeException {
        MyException(String message) {
            super(message);
        }
    }
}