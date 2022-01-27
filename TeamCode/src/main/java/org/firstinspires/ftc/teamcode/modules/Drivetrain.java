package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drivetrain {

    private HardwareMap hwMap;
    private Gamepad gamepad1;

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private final double powerScale = 0.5;

    public Drivetrain(HardwareMap hwMap, Gamepad gamepad1) {
        this.hwMap = hwMap;
        this.gamepad1 = gamepad1;

        this.frontLeft = hwMap.get(DcMotor.class, "fl");
        this.frontRight = hwMap.get(DcMotor.class, "fr");
        this.backLeft = hwMap.get(DcMotor.class, "bl");
        this.backRight = hwMap.get(DcMotor.class, "br");
    }

    public void update() {
        double wheelPower = gamepad1.right_stick_x * powerScale;
        double turnPower = -gamepad1.left_stick_y * powerScale;

        frontLeft.setPower(wheelPower - turnPower);
        frontRight.setPower(wheelPower + turnPower);
        backLeft.setPower(wheelPower - turnPower);
        backRight.setPower(wheelPower + turnPower);
    }

}