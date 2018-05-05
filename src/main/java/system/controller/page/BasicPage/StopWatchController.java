package system.controller.page.BasicPage;

/**
 * Created by vladimir on 15.04.2018.
 *
 * Класс описывает работу секундомера и подсчет суммарного времени очереди
 */

import system.controller.Queue;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static system.util.TicketUtil.*;

public class StopWatchController implements Closeable {

    private BasicPageController controller;

    private Timer timer = new Timer(60 * TICKET_DURATION);

    public StopWatchController(BasicPageController controller) {
        this.controller = controller;
    }

    public void init() {
        initPlay();
        controller.play.setOnAction(event -> {
            if (controller.play.getStyleClass().contains("play-active"))
                clickPlay();
            else clickPause();
        });

        controller.stop.setOnAction(event -> clickStop());
    }

    private void clickPlay() {
        controller.play.getStyleClass().remove("play-active");
        controller.play.getStyleClass().add("play-disactive");
        if (!timer.isAlive())
            timer.start();
        else if (!timer.isActive())
        {
            timer.setActive(true);
        }
    }

    private void clickPause() {
        initPlay();
        if (timer.isActive())
            timer.setActive(false);
    }

    private void initPlay() {
        controller.play.getStyleClass().clear();
        controller.play.getStyleClass().addAll("play-button", "play-active");
    }

    private void clickStop() {
        initPlay();
        if (timer.isAlive()) timer.reset();

    }

    private void endtime() {
        controller.play
                .getStyleClass()
                .remove("play-disactive");
        controller.play
                .getStyleClass()
                .add("play-active");
        Queue.getInstance().reedeemTicket();//сообщить очереди, что необходимо списать билет
    }

    private void insertTime(long time) {
        LocalTime localTime = LocalTime.ofSecondOfDay(time);
        controller.timer.setText(localTime.format(DateTimeFormatter.ofPattern("mm:ss")));
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

        @Override
        public void run() {
            while (true) {
                while (active) {
                    try {
                        value+=1;
                        if (max > value) {
                            insertTime(value);
                        } else {
                            reset();
                            endtime();
                        }
                        Thread.currentThread().sleep(1000);
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
