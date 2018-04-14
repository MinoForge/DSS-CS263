package refmeister.XML;

/**
 * Manages saving on a seperate thread.
 */
public enum SaveSystem{
    FILE_SYSTEM;

    private boolean alive;
    private Thread fileThread;

    private SaveSystem(){
        alive = false;
        fileThread = new Thread(this::runLoop);
    }

    private void runLoop(){
        while(alive){
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                // We just got told to save
            }
        }
        //save
    }

    public void start(){
        this.alive = true;
        this.fileThread.start();
    }

    public void forceSave(){
        fileThread.interrupt();
    }

    public void stop(){
        this.alive = false;
    }
}
