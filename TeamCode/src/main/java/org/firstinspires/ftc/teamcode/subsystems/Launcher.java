package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * Launcher Subsystem
 * Usage: launcher.launch(power);
 *        launcher.stop();
 *
 * NON-BLOCKING / FTC-SAFE
 */
public class Launcher {

    protected final Robot robot;
    protected final Telemetry telemetry;

    private final ElapsedTime timer = new ElapsedTime();

    private enum State {
        IDLE,
        LAUNCHING,
        SETTLING,
        RETURNING
    }

    private State state = State.IDLE;
    private double launchPower = 0;

    // Tuning
    private static final int LAUNCH_TICKS = 360;
    private static final int RETURN_TICKS = 10;
    private static final double RETURN_POWER_SCALE = 0.6;
    private static final long SETTLE_MS = 40;

    public Launcher(Robot robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;

        robot.launcher1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.launcher2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Mirror motor once instead of flipping power everywhere
        robot.launcher2.setDirection(DcMotor.Direction.REVERSE);

        robot.launcher1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.launcher2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // =======================================================
    //                PUBLIC API ACTION METHODS
    // =======================================================

    /** Starts a launch cycle (non-blocking) */
    public void launch(double power) {
        if (state != State.IDLE) return;

        launchPower = power;

        robot.launcher1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.launcher2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.launcher1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.launcher2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.launcher1.setPower(launchPower);
        robot.launcher2.setPower(launchPower);

        state = State.LAUNCHING;
    }

    /** MUST be called every loop() */
    public void update() {
        switch (state) {

            case LAUNCHING:
                if (Math.abs(robot.launcher1.getCurrentPosition()) >= LAUNCH_TICKS) {
                    stop();
                    timer.reset();
                    state = State.SETTLING;
                }
                break;

            case SETTLING:
                if (timer.milliseconds() >= SETTLE_MS) {
                    robot.launcher1.setPower(-launchPower * RETURN_POWER_SCALE);
                    robot.launcher2.setPower(-launchPower * RETURN_POWER_SCALE);
                    state = State.RETURNING;
                }
                break;

            case RETURNING:
                if (Math.abs(robot.launcher1.getCurrentPosition()) <= RETURN_TICKS) {
                    stop();
                    state = State.IDLE;
                }
                break;

            case IDLE:
            default:
                // Do nothing
                break;
        }

        telemetry.addData("Launcher State", state);
        telemetry.addData("Launcher Encoder", robot.launcher1.getCurrentPosition());
    }

    /** Emergency or manual stop */
    public void stop() {
        robot.launcher1.setPower(0);
        robot.launcher2.setPower(0);
        state = State.IDLE;
    }

    /** Optional helper */
    public boolean isBusy() {
        return state != State.IDLE;
    }
}
