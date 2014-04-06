/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaintern;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claudiu
 */
public class Clock extends Thread {

    private AgendaIntern view = null;
    private SimpleDateFormat sdf = null;
    public static final String CLOCK_FORMAT_NOW = "HH:mm:ss";
    boolean stopThread;
    Thread thread = null;

    public Clock(AgendaIntern view) {
        this.view = view;
        sdf = new SimpleDateFormat(CLOCK_FORMAT_NOW);
    }

    @Override
    public void run() {
        while (stopThread == false) {
            sdf.setTimeZone(view.t);
            view.getjLabel2().setText("Current Time: " + sdf.format(Calendar.getInstance().getTime()));
            view.getjLabel2().repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
            stopThread = false;
        }
    }
}
