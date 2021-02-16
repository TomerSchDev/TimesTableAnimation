
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The type Display.
 */
public class Display extends Canvas implements Runnable {
    /**
     * The Thread.
     */
    private Thread thread;
    /**
     * The Frame.
     */
    private JFrame frame;
    /**
     * The constant running.
     */
    private static boolean running = false;

    /**
     * Instantiates a new Display.
     *
     * @param width  the width
     * @param height the height
     * @param title  the title
     */
    public Display(int width, int height, String title) {
        this.frame = new JFrame();
        Dimension size = new Dimension(width, height);
        this.setPreferredSize(size);
        this.frame.setTitle(title);
        this.frame.add(this);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        running = true;
    }

    /**
     * Start.
     */
    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "render.Display");
        this.thread.start();
    }

    /**
     * Stop.
     */
    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run() Thread#run()
     */
    @Override
    public void run() {

        long MS_PF = 1000 / Main.FPS;
        Table table = new Table(Main.MIN_R, Main.MIN_POINTS, Main.MIN_FACTOR);
        while (running) {
            long startTime = System.currentTimeMillis();
            render(table);
            update(table);
            long end = System.currentTimeMillis();
            long work = end - startTime;
            if (MS_PF > work) {
                try {
                    Thread.sleep(MS_PF - work);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        stop();
    }

    /**
     * Update.
     *
     * @param table the table
     */
    private void update(Table table) {
        table.updateTable();
    }


    /**
     * Render.
     *
     * @param table the table
     */
    private void render(Table table) {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.WIDTH * 2, Main.HEIGHT * 2);
        table.render(g);
        g.dispose();
        bs.show();
    }


}
