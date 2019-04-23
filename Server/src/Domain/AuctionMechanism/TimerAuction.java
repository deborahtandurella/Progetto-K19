package Domain.AuctionMechanism;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAuction {
    private static int interval;
    private static Timer timer;
    private static int delay, period;

    public TimerAuction() {
        this.timer = new Timer();
        this.delay = 1000;
        this.period = 1000;
        interval =  Integer.parseInt("5");
    }
    public static final void setTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                decrementInterval();
            }
        }, delay, period);
    }
    private static final int decrementInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }
    public static int getInterval() {
        return interval;
    }
    public static void setInterval(int interval) {
        TimerAuction.interval = interval;
    }

}
