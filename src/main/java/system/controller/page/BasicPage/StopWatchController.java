package system.controller.page.BasicPage;

/**
 * Created by vladimir on 15.04.2018.
 *
 * Класс описывает работу секундомера и подсчет суммарного времени очереди
 */

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import system.controller.Queue;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static system.util.TicketUtil.*;
import static system.controller.page.helper.FontAwesomeIconHelper.*;

public class StopWatchController implements Closeable {

    private BasicPageController controller;

    private Timer timer = new Timer(60 * TICKET_DURATION);

    public StopWatchController(BasicPageController controller) {
        this.controller = controller;
    }

    /********      Init block      ********/

    public void init() {
        controller.play.getStyleClass().add("play-button");
        initPlay();
        initStop();
        controller.play.setOnAction(event -> {
            if (!timer.isAlive() || !timer.isActive())
                clickPlay();
            else
                clickPause();
        });

        controller.stop.setOnAction(event -> clickStop());
    }

    private void initStop() {
        controller.stop.getStyleClass().add("play-button");
        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.STOP_CIRCLE);
        icon.getStyleClass().add("stop-time");
        controller.stop.setGraphic(icon);
    }

    private void initPlay() {
        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.PLAY_CIRCLE);
        icon.getStyleClass().add("play-time");
        controller.play.setGraphic(getPane(icon));
    }

    private void initPause() {
        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.PAUSE_CIRCLE);
        icon.getStyleClass().add("pause-time");
        controller.play.setGraphic(getPane(icon));
    }

    /********      End block      ********/

    private void clickPlay() {
        if (!Queue.getInstance().checkActive()) return;

        initPause();
        if (!timer.isAlive())       timer.start();
        else if (!timer.isActive()) timer.setActive(true);

        Queue.getInstance().startReedemTicket();
    }

    private void clickPause() {
        initPlay();
        if (timer.isActive())
            timer.setActive(false);
    }

    private void clickStop() {
        initPlay();
        if (timer.onPause())
            endtime();
        if (timer.isAlive())
            timer.reset();
    }

    private void endtime() {
        controller.play
                .getStyleClass()
                .remove("play-disactive");
        controller.play
                .getStyleClass()
                .add("play-active");
        Queue.getInstance()
                .endReedeemTicket();//сообщить очереди, что необходимо списать билет
    }

    private void insertTime(long time) {
        LocalTime localTime = LocalTime.ofSecondOfDay(time);
        controller.timer.setText(localTime.format(DateTimeFormatter.ofPattern("mm:ss")));
        Queue.getInstance().updateTotalTime(time);
    }

    private class Timer extends Thread {
        private final long max;
        private long value = 0;

        private volatile boolean active = true;

        Timer(long max) {
            this.max = max;
        }

        void setActive(boolean active) {
            this.active = active;
        }

        boolean isActive() {
            return active;
        }

        boolean onPause() { return value!=0; }

        @Override
        public void run() {
            while (true) {
                while (active) {
                    try {
                        value+=1;
                        if (max + 1 > value) {
                            insertTime(value);
                        } else {
                            reset();
                            endtime();
                        }
                        Thread.currentThread().sleep(999);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        void reset() {
            value = 0;
            active = false;
            insertTime(value);
        }
    }

    @Override
    public void close() throws IOException {
        if (timer.isAlive())
            timer.interrupt();
    }
}
