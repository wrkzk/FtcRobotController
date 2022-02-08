package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drivetrain implements Component {

    private HardwareMap hwMap;
    private Gamepad gamepad1;

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    // Current power that the robot moves at
    private final double powerScale = 0.5;

    public Drivetrain(HardwareMap hwMap, Gamepad gamepad1) {
        this.hwMap = hwMap;
        this.gamepad1 = gamepad1;

        this.frontLeft = hwMap.get(DcMotor.class, "fl");
        this.frontRight = hwMap.get(DcMotor.class, "fr");
        this.backLeft = hwMap.get(DcMotor.class, "bl");
        this.backRight = hwMap.get(DcMotor.class, "br");
    }

    // Move each motor at a certain power for a certain number of ticks
    private void driveForTicks(double power, int flTicks, int frTicks, int blTicks, int brTicks) {
        DcMotor[] motors = new DcMotor[] {frontLeft, frontRight, backLeft, backRight};
        int[] motorTicks = new int[] {flTicks, frTicks, blTicks, brTicks};

        for (int i = 0; i < motors.length; i++) {
            motors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motors[i].setTargetPosition(motorTicks[i]);
            motors[i].setPower(power);
        }

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {}
    }

    // Turn right for a certain amount of ticks at a specified power
    public void turnRight(int ticks, double power) {
        driveForTicks(power, -ticks, -ticks, -ticks, -ticks);
    }

    // Turn left for a certain amount of ticks at a specified power
    public void turnLeft(int ticks, double power) {
        driveForTicks(power, ticks, ticks, ticks, ticks);
    }

    // Drive forward for a certain amount of ticks at a specified power
    public void driveStraight(int ticks, double power) {
        driveForTicks(power, -ticks, ticks, -ticks, ticks);
    }

    // Drive backward for a certain amount of ticks at a specified power
    public void driveReverse(int ticks, double power) {
        driveForTicks(power, ticks, -ticks, ticks, -ticks);
    }

    // Set the motor power of every motor back to zero
    public void resetMotors() {
        DcMotor[] motors = new DcMotor[] {frontLeft, frontRight, backLeft, backRight};
        for (DcMotor motor : motors) {
            motor.setPower(0);
        }
    }

    // Update the motor power based on the left and right joysticks, and apply a square root curve to scale the power
    public void update() {
        double wheelPower = 0;
        if (gamepad1.left_stick_y > 0) {
            wheelPower = Math.sqrt(gamepad1.left_stick_y) * powerScale;
        } else if (gamepad1.left_stick_y < 0) {
            wheelPower = -1 * Math.sqrt(-1 * gamepad1.left_stick_y) * powerScale;
        }
        double turnPower = gamepad1.right_stick_x * powerScale;

        frontLeft.setPower(wheelPower - turnPower);
        frontRight.setPower(-wheelPower - turnPower);
        backLeft.setPower(wheelPower - turnPower);
        backRight.setPower(-wheelPower - turnPower);
    }
}