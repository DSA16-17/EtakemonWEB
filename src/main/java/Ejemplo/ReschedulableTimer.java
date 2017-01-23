package Ejemplo;

import java.util.Timer;
import java.util.TimerTask;

public class ReschedulableTimer extends Timer
{
    private Runnable  task;
    private TimerTask timerTask;
    public void schedule(Runnable runnable, long delay,long period)
    {
        task = runnable;
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                task.run();
            }
        };
        this.schedule(timerTask, delay,period);
    }

}