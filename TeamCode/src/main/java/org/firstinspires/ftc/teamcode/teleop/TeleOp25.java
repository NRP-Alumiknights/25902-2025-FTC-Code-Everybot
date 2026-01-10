package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Load;

@TeleOp(name = "TeleOp25", group = "ITD")
public class TeleOp25 extends LinearOpMode {

    Robot robot;
    Drive drive;
    Launcher launcher;
    Load loader;

    Lift lift;

    @Override
    public void runOpMode() {

        // Initialize robot hardware
        robot = new Robot(hardwareMap);
        drive = new Drive(robot, telemetry);
        launcher = new Launcher(robot, telemetry);
        loader = new Load(robot, telemetry);
        lift = new Lift(robot, telemetry);




        waitForStart();

        while (opModeIsActive()) {

            // 1. Driving
            drive.setMecanum(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    1.0
            );



           //Launch
            if (gamepad2.x)
            {
                launcher.launch(1);
            }
            else if (gamepad2.a)
            {
                launcher.launch(-0.1);
            }
            else {
                launcher.stop();
            }
            //4. Intake systems
            if (gamepad2.left_trigger > 0.1)
            {
                loader.load(1);
            }
            else {
                loader.stop();
            }
            //5. Lifter
            if (gamepad1.dpad_up)
            {
               lift.move(1);
            }
            if (gamepad1.dpad_down)
            {
                lift.move(-1);
            }

            telemetry.update();

        }
    }
}
