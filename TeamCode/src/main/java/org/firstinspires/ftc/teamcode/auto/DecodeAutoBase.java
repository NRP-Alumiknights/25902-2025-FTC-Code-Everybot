package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Load;

@Autonomous(name = "Base Auto", group = "ITD")
public class DecodeAutoBase extends LinearOpMode {

    Robot robot;
    Drive drive;
    Launcher launcher;
    Load loader;
    Turret turret;

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
        turret = new Turret(robot, telemetry);

        telemetry.addLine("Auto Initialized");
        telemetry.update();

        waitForStart();

        if (!opModeIsActive()) return;

        // -------------------------------
        // MAIN AUTO SEQUENCE
        // -------------------------------

        // Example: Spin up & shoot once
        shootAtRPM(3000);

        // Example: Drive forward
        driveForward(0.5, 1000);

        // Example: Strafe
        strafeRight(0.5, 600);

        // Example: Turn turret to neutral
        turret.rotateTurret(0.3);
        sleep(300);
        turret.stop();

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

    /** Fire once at a specific RPM */
    private void shootAtRPM(int rpm) {
        launcher.setRPM(rpm);

        // spin up for 1.5 seconds
        timer.reset();
        while (opModeIsActive() && timer.seconds() < 1.5) {
            launcher.update();
            telemetry.addData("Spinning Up", launcher.readyToFire());
            telemetry.update();
        }

        // Fire loader if needed
        // loader.LoadTurret(1);
        // sleep(300);
        // loader.stopLoader();

        launcher.stop();
    }

    // ==========================================================
    //                    STOP EVERYTHING
    // ==========================================================

    private void stopAll() {
        drive.stop();
        launcher.stop();
        loader.stopLoader();
        loader.stopIntake();
        turret.stop();
    }
}
