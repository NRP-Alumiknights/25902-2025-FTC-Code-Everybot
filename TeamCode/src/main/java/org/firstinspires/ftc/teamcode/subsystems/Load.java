package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * Generic subsystem template that only uses hardware inside Robot.java.
 * Copy → rename → customize.
 */
public class Load {

    protected final Robot robot;
    protected final Telemetry telemetry;

    public Load(Robot robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;


    }

    // =======================================================
    //                PUBLIC API ACTION METHODS
    // =======================================================

    /** Example—replace with real behavior */
    public void load(double power) {
        // Example: using a motor
         robot.intake.setPower(power);

        // Or a CRServo
        // robot.intakeL.setPower(power);
        // robot.intakeR.setPower(power);
    }

    /** Stop everything related to this subsystem */
    public void stop() {
         robot.intake.setPower(0);
        // robot.intakeL.setPower(0);
        // robot.intakeR.setPower(0);
    }

    // =======================================================
    //                HELPER FUNCTIONS
    // =======================================================

    /** Example mode switcher */
    public void runWithoutEncoder() {
        // robot.launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Optional telemetry */
    public void log() {
        // telemetry.addData("LauncherRPM", currentRPM);
        // telemetry.addData("IntakePower", robot.intakeL.getPower());
    }
}


