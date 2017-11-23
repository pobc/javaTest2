package Thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

/**
 * Created by jiang on 2017/9/15.
 */
public class AlgorithmAnimation {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new AnimationFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class AnimationFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;
    public AnimationFrame() {
        ArrayComponent comp = new ArrayComponent();
        add(comp, BorderLayout.CENTER);

        final Sorter sorter = new Sorter(comp);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setRun();
            }
        });
        JButton stepButton = new JButton("step");
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setStep();
            }
        });
        JPanel buttons = new JPanel();
        buttons.add(runButton);
        buttons.add(stepButton);
        add(buttons,BorderLayout.SOUTH);
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        Thread t = new Thread(sorter);
        t.start();
    }
}

class Sorter implements Runnable {
    private Double[] values;
    private ArrayComponent component;
    private Semaphore gate;
    private static final int DELAY = 100;
    private volatile boolean run;
    private static final int VALUES_LENGTH = 30;

    public Sorter(ArrayComponent comp) {
        values = new Double[VALUES_LENGTH];
        for (int i = 0; i < values.length; i++) {
            values[i] = new Double(Math.random());
        }
        this.component = comp;
        this.gate = new Semaphore(1);
        this.run = false;
    }

    public void setRun() {
        run = true;
        gate.release();
    }
    public void setStep(){
        run = false;
        gate.release();
    }


    @Override
    public void run() {
        Comparator<Double> comp = new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                component.setValues(o1,o2,values);
                try {
                    if (run)Thread.sleep(DELAY);
                    else gate.acquire();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                return o1.compareTo(o2);
            }
        };
        Arrays.sort(values,comp);
        component.setValues(null,null,values);
    }
}

class ArrayComponent extends JComponent {
    private Double market1;
    private Double market2;
    private Double[] values;

    public synchronized void setValues(Double market1, Double market2, Double[] values) {
        this.market1 = market1;
        this.market2 = market2;
        this.values = values;
        repaint();
    }

    public synchronized void paintComponent(Graphics g) {
        if (values == null) return;
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        for (int i = 0; i < values.length; i++) {
            double height = values[i] * getHeight();
            Rectangle2D bar = new Rectangle2D.Double(width * i, 0, width, height);
            if (values[i] == market1 || values[i] == market2) g2.fill(bar);
            else g2.draw(bar);
        }
    }

}