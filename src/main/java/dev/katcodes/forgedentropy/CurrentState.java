package dev.katcodes.forgedentropy;

public class CurrentState {
    public static CurrentState currentState;



    private CurrentState() {

    }
    public static CurrentState Get() {
        if(currentState==null)
            currentState=new CurrentState();
        return currentState;

    }
    public boolean forceForward = false;

    /**
     * Do not show events to screen.
     */
    public boolean doNotShowEvents = false;

    /**
     * Percentage time multiplier
     */
    public float timeMultiplier= 1.0f;

    /**
     * Black and White screen
     */
    public boolean blackAndWhite=false;

    /**
     * Blurs the screen
     */
    public boolean blur=false;


    /**
     * Is the players field of view forced to a paticular view?
     */
    public boolean forcedFov=false;


    /**
     * Field of View to force
     */
    public int fov=0;

    /**
     * Ignores variable field of views
     */
    public boolean ignoreVariableFov=false;

    /**
     * Use Monitor shader
     */
    public boolean monitor;



}
