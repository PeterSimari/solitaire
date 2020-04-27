/**
 * Creates the timer for the Klondike and the GUI class.
 */
public class StopWatch {
  private boolean running;
  private boolean paused;
  private long start;
  private long pausedStart;
  private long end;

  /**
   * Initializes the stopwatch. Sets the starting time to 0.
   */
  public StopWatch() {
    this.pausedStart = 0;
    this.start = 0;
    this.end = 0;
  }

  /**
   * Whether or not the timer is currently running.
   * @return True or False for if it is running. Reads off the field.
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Whether or not the timer is currently paused.
   * @return True or False for if it is paused. Reads off the field.
   */
  public boolean isPaused() {
    return paused;
  }

  /**
   * Starts the timer by reading the System time.
   */
  public void start() {
    start = System.nanoTime();
    running = true;
    paused = false;
    pausedStart = -1;
  }

  /**
   * Stops the timer.
   * @return the time that has elapsed.
   */
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

  /**
   * Pauses the timer.
   * @return The time that the timer was paused at.
   * Saves the time as well so it can resume at a later time but not count the entire time since it's been paused.
   */
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

  /**
   * Resumes the timer from when it was paused.
   */
  public void resume() {
    if (isPaused() && isRunning()) {
      start = System.nanoTime() - (pausedStart - start);
      paused = false;
    }
  }

  /**
   * How much time has elapsed since the timer was started.
   * @return The Long number since the timer started.
   */
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

  /**
   * Turns it into a whole number so it is easier to read.
   */
  public String toString() {
    long enlapsed = elapsed();
    double beans = Math.round(((double) enlapsed / 1000000000.0));
    return beans + " Seconds";
  }

}