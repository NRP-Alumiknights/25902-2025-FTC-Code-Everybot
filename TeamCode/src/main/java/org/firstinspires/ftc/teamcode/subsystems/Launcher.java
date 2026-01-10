package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * Launcher Subsystem
 * Usage: Launcher.launch(power);
 * OR
 * Launcher.stop()
 */
public class Launcher {

    protected final Robot robot;
    protected final Telemetry telemetry;

    public Launcher(Robot robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;


    }

    // =======================================================
    //                PUBLIC API ACTION METHODS
    // =======================================================

    public void launch(double power) {
         robot.launcher2.setPower(-power);
         robot.launcher1.setPower(power);
    }

    /** Stop everything related to this subsystem */
    public void stop() {
         robot.launcher2.setPower(0);
         robot.launcher1.setPower(0);

    }


    }

