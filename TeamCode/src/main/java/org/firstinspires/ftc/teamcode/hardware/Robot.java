package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class Robot {
    public DcMotor rightFront, leftFront, rightBack, leftBack, launcher1, launcher2, intake, lift;

    public IMU imu;

    public HardwareMap hw;

    /** FIXED: Robot constructor now stores the hardware map AND initializes. */
    public Robot(HardwareMap hardwareMap) {
        init(hardwareMap);
    }

    /** Proper init for all motors */
    public void init(HardwareMap hardwareMap) {
        this.hw = hardwareMap;

        // Motors (MAKE SURE names match your config!)
        rightFront = initMotor("RightFront", true);
        leftFront  = initMotor("LeftFront", false);
        rightBack  = initMotor("RightBack", true);
        leftBack   = initMotor("LeftBack", false);
        launcher1 = initMotor("Launcher1", true);
        launcher2 = initMotor("Launcher2", true);
        intake = initMotor("intake", true);
        lift = initMotor("lift", true);




        // IMU
        imu = hw.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        ));

        // Brake mode default
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private DcMotor initMotor(String name, boolean forward) {
        DcMotor m = hw.get(DcMotor.class, name);
        if (!forward) m.setDirection(DcMotorSimple.Direction.REVERSE);
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        return m;
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        if (leftFront != null) leftFront.setZeroPowerBehavior(behavior);
        if (rightFront!= null) rightFront.setZeroPowerBehavior(behavior);
        if (leftBack  != null) leftBack.setZeroPowerBehavior(behavior);
        if (rightBack != null) rightBack.setZeroPowerBehavior(behavior);
        if (launcher1 != null) launcher1.setZeroPowerBehavior(behavior);
        if (launcher2 != null) launcher2.setZeroPowerBehavior(behavior);
    }

    public void resetEncoders() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Assume flipper is at load position
        launcher1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        launcher1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
