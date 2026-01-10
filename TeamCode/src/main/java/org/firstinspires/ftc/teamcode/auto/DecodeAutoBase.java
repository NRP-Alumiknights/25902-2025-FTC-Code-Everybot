package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Load;

@Autonomous(name = "Base Auto", group = "ITD")
public class DecodeAutoBase extends LinearOpMode {

    Robot robot;
    Drive drive;
    Launcher launcher;
    Load loader;

    Lift lift;
    private final ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // -------------------------------
        // Initialize all robot systems
        // -------------------------------
        robot = new Robot(hardwareMap);
        drive = new Drive(robot, telemetry);
        launcher = new Launcher(robot, telemetry);
        loader = new Load(robot, telemetry);
        lift = new Lift(robot, telemetry);

        telemetry.addLine("Auto Initialized");
        telemetry.update();

        waitForStart();

        if (!opModeIsActive()) return;

        // -------------------------------
        // MAIN AUTO SEQUENCE
        // -------------------------------

        // Example: Spin up & shoot once
        launch();

        // Example: Drive forward
        driveForward(0.5, 1000);

        // Example: Strafe
        strafeRight(0.5, 600);



        // Add additional steps here…
        // pick up pixels, score, park, etc.

        stopAll();
    }

    // ==========================================================
    //                      HELPER METHODS
    // ==========================================================

    /** Drive forward/backward using mecanum */
    private void driveForward(double power, long ms) {
        timer.reset();
        while (opModeIsActive() && timer.milliseconds() < ms) {
            drive.setMecanum(power, 0, 0, 1.0);
        }
        drive.stop();
    }

    private void driveBackward(double power, long ms) {
        driveForward(-power, ms);
    }

    /** Strafe left/right */
    private void strafeRight(double power, long ms) {
        timer.reset();
        while (opModeIsActive() && timer.milliseconds() < ms) {
            drive.setMecanum(0, power, 0, 1.0);
        }
        drive.stop();
    }

    private void strafeLeft(double power, long ms) {
        strafeRight(-power, ms);
    }

    /** Turn using mecanum rotation */
    private void turnRight(double power, long ms) {
        timer.reset();
        while (opModeIsActive() && timer.milliseconds() < ms) {
            drive.setMecanum(0, 0, power, 1.0);
        }
        drive.stop();
    }

    private void turnLeft(double power, long ms) {
        turnRight(-power, ms);
    }

    private void launch() {
        launcher.launch(1);


        launcher.stop();
    }

    // ==========================================================
    //                    STOP EVERYTHING
    // ==========================================================

    private void stopAll() {
        drive.stop();
        launcher.stop();
        loader.stop();


    }
}
