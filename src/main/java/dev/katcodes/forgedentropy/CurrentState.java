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


    /**
     * Will pickaxe explode blocks?
     */
    public boolean explodingPickaxe;


    /**
     * Turn fire overlay off
     */
    public boolean fire;

    /**
     * Visually flip all entities upside down
     */
    public boolean flipEntities;

    /**
     * Force a third person view
     *
     */
    public boolean thirdPersonView;


    /**
     * Make third person view front view
     */
    public boolean frontView;


    /**
     * Force the player to be riding ... something
     */
    public boolean forceRiding;

    /**
     * Force the player to keep jumping
     */
    public boolean forceJump;


    /**
     * Force the player to keep sneaking
     */
    public boolean forceSneak;


    /**
     * Custom fog for the player
     */
    public boolean customFog;


    /**
     * Force the player's sound to be a specific pitch
     */
    public boolean forcePitch;

    /**
     * Pitch to force to
     */

    public float forcedPitch;

    /**
     * Invert colours shader
     */
    public boolean invertedShader;

    /**
     * Invert Controls
     */
    public boolean invertedControls;




}
