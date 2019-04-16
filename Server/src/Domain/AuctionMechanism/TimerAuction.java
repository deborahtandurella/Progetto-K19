package Domain.AuctionMechanism;

import java.util.Timer;
import java.util.TimerTask;


// la classe TimerAuction si occupa di cronometra il tempo rimanente di un asta attiva
public class TimerAuction {
    private static int interval;
    private static Timer timer;
    private static int delay, period;
    private static String winner;

    public TimerAuction() {
        this.timer = new Timer();
        this.delay = 1000;
        this.period = 1000;
        interval =  Integer.parseInt("5");
        setInterval();
        setTimer();
    }

    private static final void setTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setInterval();
                System.out.println(getInterval());
                /*GIORDANO
                if(interval==0){
                    System.out.println("AUCTION OVER: "+winner);
                    System.exit(0);
                }*/
            }
        }, delay, period);
    }

    private static final int setInterval() {
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
/* GIORDANO
    public static final void setWinner(String s) {
        winner = s;
    }*/
}


