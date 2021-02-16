import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Main.
 */
public class Main {
    /**
     * The constant WIDTH.
     */
    public static int WIDTH = 600;
    /**
     * The constant HEIGHT.
     */
    public static int HEIGHT = 600;
    /**
     * The constant TranslateToCenter.
     */
    public static int TranslateToCenter = (WIDTH / 2) + 1;
    /**
     * The constant FPS.
     */
    public static int FPS=30;

    /**
     * The constant MAX_R.
     */
    public static int MAX_R;
    /**
     * The constant MIN_R.
     */
    public static int MIN_R;
    /**
     * The constant MAX_POINTS.
     */
    public static int MAX_POINTS;
    /**
     * The constant MIN_POINTS.
     */
    public static int MIN_POINTS;
    /**
     * The constant MAX_FACTOR.
     */
    public static double MAX_FACTOR;
    /**
     * The constant MIN_FACTOR.
     */
    public static double MIN_FACTOR;

    /**
     * Sets setting.
     *
     * @param file the file
     */
    public static void setSetting(File file) {
        if (file.exists()) {
            ArrayList<String> lines = (ArrayList<String>) readFileIntoLines(file);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains("MAX_R")) {
                    MAX_R = Integer.parseInt(lines.get(i).substring(8));
                }
                if (lines.get(i).contains("MAX_POINTS")) {
                    MAX_POINTS = Integer.parseInt(lines.get(i).substring(13));
                }
                if (lines.get(i).contains("MIN_POINTS")) {
                    MIN_POINTS = Integer.parseInt(lines.get(i).substring(13));
                }
                if (lines.get(i).contains("MAX_FACTOR")) {
                    MAX_FACTOR = Double.parseDouble(lines.get(i).substring(13));
                }
                if (lines.get(i).contains("MIN_FACTOR")) {
                    MIN_FACTOR = Double.parseDouble(lines.get(i).substring(13));
                }
            }
        } else {
            MAX_R = (WIDTH / 2) - 2;
            MIN_R = 50;
            MAX_POINTS = 1000;
            MIN_POINTS = 200;
            MAX_FACTOR = 10;
            MIN_FACTOR = 0.5;
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        File file = new File("setting.txt");
        setSetting(file);
        Display display = new Display(WIDTH, HEIGHT,"Times Table");
        display.run();
    }

    /**
     * Read file into lines list.
     *
     * @param file the file
     *
     * @return the list
     */
    public static List<String> readFileIntoLines(File file) {
        InputStream inputStream = null;
        List<String> lines = new ArrayList<>();
        try {
            inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream);
            String line = "";

            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            while ((line = lineNumberReader.readLine()) != null) {
                if (line.isEmpty() || line.charAt(0) == '#') {
                    continue;
                }
                lines.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
