
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.jupiter.api.Test;
import static junit.framework.Assert.assertEquals;
import java.io.*;
import java.util.*;
public class Tests {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    TreeMap<String, String> rightAnswer = new TreeMap<>();

//    @Before
//    public void setup(){
//
//        System.setOut(new PrintStream(outContent));
//    }

    @Test
    public void testLong() throws IOException {
        String[] args = {"[-l]", "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3855332" );
        rightAnswer.put("1.png", "rwx  07:09  367048");
        assertEquals(rightAnswer, Ls.answer);
    }
    @Test
    public void testAll() throws IOException {
        String[] args = {"[-l]", "[-h]", "[-r]", "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3,68 MB");
        rightAnswer.put("1.png", "rwx  07:09  358,45 KB");
        assertEquals(rightAnswer, Ls.answer);

    }

    @Test
    public void testEmpty() throws IOException {
        String[] args = {"[-l]", "[-h]", "[-r]", "/C:/Users/User/HightSkill"};
        Parser.main(args);
        rightAnswer.put("Пусто","");
        assertEquals(rightAnswer, Ls.answer);

    }
    @Test
    public void testR() throws IOException {
        String[] args = { "[-r]", "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3855332" );
        rightAnswer.put("1.png", "rwx  07:09  367048");
        assertEquals(rightAnswer, Ls.answer);

    }

    @Test
    public void testLongHumRead() throws IOException {
        String[] args = {"[-l]", "[-h]",  "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3,68 MB");
        rightAnswer.put("1.png", "rwx  07:09  358,45 KB");
        assertEquals(rightAnswer, Ls.answer);
    }

    @Test
    public void testOutput() throws IOException {
        String[] args = {"[-l]" ,"[-o]", "example.txt]", "/C:/Users/User/LowSkill/1.png"};
        Parser.main(args);
        String rightAnswer = "rwx  07:09  367048   1.png";
        String fromFile = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("example.txt"));
            fromFile = reader.readLine();
        } catch (Exception e) {
            System.out.println("runtime error");
        }
        assertEquals(rightAnswer, fromFile);
    }
}
