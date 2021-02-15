import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int WIDTH = 600;
    public static int HEIGHT = 600;
    public static int TranslateToCenter = (WIDTH / 2) + 1;

    public static int MAX_R;
    public static int MIN_R;
    public static int MAX_POINTS;
    public static int MIN_POINTS;
    public static double MAX_FACTOR;
    public static double MIN_FACTOR;

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
            MAX_FACTOR = 15;
            MIN_FACTOR = 1;
        }
    }

    public static void main(String[] args) {
        File file = new File("setting.txt");
        setSetting(file);

        Sleeper sleeper = new Sleeper();
        GUI gui = new GUI("Times Table", WIDTH, HEIGHT);
        Table table = new Table(MIN_R, MIN_POINTS, MIN_FACTOR);
        int FPS = 10;
        long MS_PF = 1000 / FPS;
        while (true) {
            long start = System.currentTimeMillis();
            DrawSurface drawSurface = gui.getDrawSurface();
            drawSurface.setColor(Color.BLACK);
            drawSurface.fillRectangle(0, 0, WIDTH, HEIGHT);
            table.updateTable();
            table.drawTable(drawSurface);
            gui.show(drawSurface);
            long end = System.currentTimeMillis();
            long work = end - start;
            if (work < MS_PF) {
                sleeper.sleepFor(MS_PF - work);
            }
        }
    }

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
