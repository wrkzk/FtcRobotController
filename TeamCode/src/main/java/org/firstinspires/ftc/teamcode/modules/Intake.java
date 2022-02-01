package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private HardwareMap hwMap;
    private Gamepad gamepad1;

    private DcMotor leftIntake;
    private DcMotor rightIntake;

    public Intake(HardwareMap hwMap, Gamepad gamepad1) {
        this.hwMap = hwMap;
        this.gamepad1 = gamepad1;
        this.leftIntake = hwMap.get(DcMotor.class, "leftIntake");
        this.rightIntake = hwMap.get(DcMotor.class, "rightIntake");
    }

    public void update() {
        if (gamepad1.x && !gamepad1.y) {
            rightIntake.setPower(1);
            leftIntake.setPower(1);
        } else if (gamepad1.y && !gamepad1.x) {
            rightIntake.setPower(-1);
            leftIntake.setPower(-1);
        }
        rightIntake.setPower(0);
        leftIntake.setPower(0);
    }
}
