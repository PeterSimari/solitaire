
import java.util.concurrent.TimeUnit;

public class StopWatch {
  private boolean running;
  private boolean paused;
  private long start;
  private long pausedStart;
  private long end;

  public StopWatch() {
    this.pausedStart = 0;
    this.start = 0;
    this.end = 0;
  }

  public boolean isRunning() {
    return running;
  }

  public boolean isPaused() {
    return paused;
  }

  public void start() {
    start = System.nanoTime();
    running = true;
    paused = false;
    pausedStart = -1;
  }

  public long stop() {
    if (!isRunning()) {
      running = false;
      paused = false;
      return pausedStart - start;
    } else {
      end = System.nanoTime();
      running = false;
      return end - start;
    }
  }

  public long pause() {
    if (!isRunning()) {
      return -1;
    } else if (isPaused()) {
      return (pausedStart - start);
    } else {
      pausedStart = System.nanoTime();
      paused = true;
      return (pausedStart - start);
    }
  }

  public void resume() {
    if (isPaused() && isRunning()) {
      start = System.nanoTime() - (pausedStart - start);
      paused = false;
    }
  }

  public long elapsed() {
    if (isRunning()) {
      if (isPaused()) {
        return (pausedStart - start);
      }
      return (System.nanoTime() - start);
    } else {
      return (end - start);
    }
  }

  public String toString() {
    long enlapsed = elapsed();
    double beans = Math.round(((double) enlapsed / 1000000000.0));
    return beans + " Seconds";
  }

  public static void main(String[] args) {
    StopWatch jawn = new StopWatch();
    System.out.println(jawn.toString());
    jawn.start();
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch(Exception e) {

    }
    

    System.out.println(jawn.toString());
  }
}