package system.controller.page.BasicPage;

/**
 * Created by vladimir on 15.04.2018.
 *
 * Класс описывает работу секундомера и подсчет суммарного времени очереди
 */
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import system.controller.Queue;
import system.controller.page.listener.StopWatchListener;
import system.view.FactoryPage;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static system.util.TicketUtil.*;
import static system.controller.page.helper.FontAwesomeIconHelper.*;

public class StopWatchController implements Closeable {

    private BasicPageController controller;

    private static final String TIMER_THREAD_NAME = "Thread=Timer";

    private List<StopWatchListener> listeners = new ArrayList<>();

    private Timer timer;

    StopWatchController(BasicPageController controller) {
        this.controller = controller;
        this.timer = (Timer) FactoryPage.getInstance().getThreadByName(TIMER_THREAD_NAME);
        if(Objects.isNull(timer))
            this.timer = new Timer(60 * TICKET_DURATION);
        this.timer.setController(this);
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
        if (timer.isAlive()) initPause();

        controller.stop.setOnAction(event -> clickStop());
    }

    private void initStop() {
        controller.stop.getStyleClass().add("play-button");
        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.STOP_CIRCLE, "3em");
        icon.getStyleClass().add("stop-time");
        controller.stop.setGraphic(icon);
    }

    private void initPlay() {
        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.PLAY_CIRCLE, "3em");
        icon.getStyleClass().add("play-time");
        controller.play.setGraphic(getPane(icon));
    }

    private void initPause() {
        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.PAUSE_CIRCLE, "3em");
        icon.getStyleClass().add("pause-time");
        controller.play.setGraphic(getPane(icon));
    }

    public void setListeners(StopWatchListener listener) {
        this.listeners.add(listener);
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
        //initPlay();
        if (timer.onPause())
            endtime();
        if (timer.isAlive())
            timer.reset();
    }

    private void endtime() {
        //System.out.println("End time");
        initPlay();
        Queue.getInstance()
                .endReedeemTicket();//сообщить очереди, что необходимо списать билет
    }

    //Рассылаем время через слушателей, объявленных в Queue
    private void insertTime(long time) {
        Queue.getInstance().updateTime(time);
    }

    private class Timer extends Thread {
        private final long max;
        private long value = 0;
        private StopWatchController controller;

        private volatile boolean active = true;

        Timer(long max) {
            super(FactoryPage.getInstance().getThreadGroup(), "Thread=Timer");
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
                            //http://qaru.site/questions/95989/platformrunlater-and-task-in-javafx
                            Platform.runLater(()->this.controller.insertTime(value));
                        } else {
                            Platform.runLater(()->this.controller.clickStop());
                        }
                        Thread.currentThread().sleep(999);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }

        void reset() {
            value = 0;
            active = false;
            Platform.runLater(()->this.controller.insertTime(value));
        }

        public void setController(StopWatchController controller) {
            this.controller = controller;
        }
    }

    @Override
    public void close() {
        if (timer.isAlive())
            timer.interrupt();
    }
}
