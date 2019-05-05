import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;

public class Parser {
    @Option(name = "-l",forbids = "-h", usage = "Long format")
    private static boolean l = false;

    @Option(name = "-h",forbids = "-l",usage = "Human-readable")
    private static boolean h = false;

    @Option(name = "-r", usage = "Reverse")
    private static boolean r = false;

    @Option(metaVar = "outputPath", usage = "Input file name", name = "-o")
    private String[] outputPath;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Ls [-l] [-h] [-r] [-o output.file] directory_or_file");
            return;
        }
        Ls.item = new File(args[args.length - 1]);
        if (Ls.item.isDirectory()) {
            String[] names = Ls.item.list();
            if (names == null) {
                throw new Ls.MyException("Не введены аргументы");
            }
            if (names.length == 0) {
                Ls.answer.put("Пусто", "");
                return;
            }

            for (String str : names)
                Ls.answer.put(str, "");
        }
        else
            Ls.answer.put(Ls.item.getName(), "");


        new Parser().launch(args);
        if (args[0].contains("-l")) {
            l = true;
            Ls.getRWX(false);


        }
        if (args[1].contains("-h")) {
            h = true;
            Ls.getRWX(true);


        }
        System.out.println(args[0]);

    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {

        }

    }
}