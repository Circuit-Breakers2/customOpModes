package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------


public class AutonomousMode

{
    //--------------------------------------------------------------------------
    //
    // v_state
    //
    //--------
    // This class member remembers which state is currently active.  When the
    // start method is called, the state will be initialized (0).  When the loop
    // starts, the state will change from initialize to state_1.  When state_1
    // actions are complete, the state will change to state_2.  This implements
    // a state machine for the loop method.
    //--------
    int v_state = 0;

    //--------------------------------------------------------------------------
    //
    // PushBotAuto
    //
    /**
     * Constructs the class.
     *
     * The system calls this member when the class is instantiated.
     */
    public PushBotAuto ()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotAuto::PushBotAuto

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Performs any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start ()

    {
        //
        // Call the PushBotHardware (super/base class) start method.
        //
        super.start ();

        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_drive_encoders ();

    } // PushBotAuto::start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Implement a state machine that controls the robot during auto-operation.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */

    static int[] l_times = new int [6];
    @Override public void loop ()

    {
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
            //
            // Synchronoize the state machine and hardware.
            //
            case 0:
                //
                // Reset the encoders to ensure they are at a known good value.
                //
                reset_drive_encoders ();

                //
                // Transition to the next state when this method is called again.
                //
                //Created by: Sulaiman Ahmed
                //Updated on: 11/27/15
                l_times[0] = 0;
                l_times[1] = 0;
                l_times[2] = 0;
                l_times[3] = 0;
                l_times[4] = 0;
                l_times[5] = 0;
                v_state++;

                break;
            //
            // Move forward 8 rotations
            //
            case 1:
                run_using_encoders ();
                set_drive_power (1.0f, 1.0f);


                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 2:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[0]++;
                }
                break;
            //
            // Turn left 45 degrees
            //
            case 3:
                run_using_encoders ();
                set_drive_power (-1.0f, 1.0f);
                if (have_drive_encoders_reached (1440, 1440))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 4:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[1]++;
                }
                break;
            //
            // Move forward 8 rotations

            //
            case 5:
                run_using_encoders ();
                set_drive_power (1.0f, 1.0f);
                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 6:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[2]++;
                }
                break;
            /*
             * Created by: Sulaiman Ahmed
             * Updated on: 11/27/2015
            */
            //
            //Move backwards
            //
            case 7:
                run_using_encoders ();
                set_drive_power (-1.0f, -1.0f);
                if(have_drive_encoders_reached(2880, 2880))
                {
                    reset_drive_encoders();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            //Wait...
            //
            case 8:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[3]++;
                }
                break;
            //
            //Turn left 45 degrees. You should be facing mountain
            //
            case 9:
                run_using_encoders ();
                set_drive_power (-1.0f, 1.0f);
                if (have_drive_encoders_reached (1440, 1440))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 10:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[4]++;
                }
                break;
            //
            //Climb up the mountain
            //
            case 11:
                run_using_encoders ();
                set_drive_power (1.0f, 1.0f);
                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 12:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                else
                {
                    l_times[5]++;
                }
                break;
            //
            // The autonomous actions have been accomplished (i.e. the state has
            // transitioned into its final state.
            //
            default:
                break;
        }

        update_telemetry ();
        telemetry.addData("11", "State: " + v_state);
        telemetry.addData ("12", "Times: " + l_times[0]);
        telemetry.addData ("13", "Times: " + l_times[1]);
        telemetry.addData ("14", "Times: " + l_times[2]);
        telemetry.addData ("15", "Times: " + l_times[3]);
        telemetry.addData ("16", "Times: " + l_times[4]);
        telemetry.addData ("17", "Times: " + l_times[5]);
    }

}
