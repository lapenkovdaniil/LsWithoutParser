import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ls {
    static File item;
    private boolean lFlag;
    private boolean hFlag;
    private boolean rFlag;
    private String outputPath = "";
    static TreeMap<String, String> answer = new TreeMap<>();
    public Ls(boolean lFlag, boolean hFlag, boolean rFlag,String outputPath){
        this.lFlag = lFlag;
        this.hFlag = hFlag;
        this.rFlag = rFlag;
        this.outputPath = outputPath;
    }
    static Ls ls = new Ls(false, false, false,"");

    public static void switchHumanReadable(StringBuilder str, long lengthItemInBytes) {
        int base = 1024;
        String[] type = {" B"," KB"," MB"," GB"};
        DecimalFormat lengthItem = new DecimalFormat("###.##");
        int i;
        double k = lengthItemInBytes;
        for (i = 0; i < 2; i++) {
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
            str.append(testFile.canRead() ? "r" : "-");
            str.append(testFile.canWrite() ? "w" : "-");
            str.append(testFile.canExecute() ? "x" : "-");
            str.append("  ").append(lastChange).append("  ");
            long lengthItemInBytes = 0;
            if (testFile.isFile()) {
                lengthItemInBytes = testFile.length();
            }
            if (mode) {
                switchHumanReadable(str, lengthItemInBytes);
            } else {
                str.append(lengthItemInBytes);
            }

            answer.replace(testFile.getName(), str.toString());
        }
    }
    private static void printAnswer() {
        try
        {
            FileWriter outputFile = new FileWriter(ls.outputPath);

            for (Map.Entry<String, String> map : answer.entrySet())
                if (ls.lFlag)
                    outputFile.write(map.getValue() + "   " + map.getKey() + "\n");
                else
                    outputFile.write(map.getKey() + "\n");

            outputFile.close();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ls [-l] [-h] [-r] [-o output.file] directory_or_file");
            return;
        }
        Ls.item = new File(args[args.length - 1]);
        if (Ls.item.isDirectory()) {

            String[] names = Ls.item.list();
            if (names.length == 0) {
                Ls.answer.put("Пусто", "");
                return;
            }

            for (String str : names) {
                Ls.answer.put(str, "");
            }
        } else {
            Ls.answer.put(Ls.item.getName(), "");
        }

        final int last = args.length - 1;
        int i = 0;
        while(i < last) {
            if (args[i].contains("-l")) {
                ls.lFlag = true;
                Ls.getRWX(false);
                i++;
                continue;

            }
            if (args[i].contains("-h")) {
                ls.hFlag = true;
                Ls.getRWX(true);
                i++;
                continue;

            }
            if (args[i].contains("-r")) {
                ls.rFlag = true;
                Ls.reverseTreeMap(Ls.answer);

            }
            if (args[i].equals("-o")) {
                ls.outputPath = args[i++].substring(0, args[i].length() - 1);
            }
            return;
        }
        Ls.printAnswer();
    }
}