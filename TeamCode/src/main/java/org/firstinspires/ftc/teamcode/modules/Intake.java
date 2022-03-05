package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake implements Component {

    private HardwareMap hwMap;
    private Gamepad gamepad1;

    private DcMotor leftIntake;
    private DcMotor rightIntake;

    private boolean intakeOn = false;
    private boolean ejectOn = false;


    public Intake(HardwareMap hwMap, Gamepad gamepad1) {
        this.hwMap = hwMap;
        this.gamepad1 = gamepad1;
        this.leftIntake = hwMap.get(DcMotor.class, "leftIntake");
        this.rightIntake = hwMap.get(DcMotor.class, "rightIntake");
    }

    // Update the motor power for intake based on the x and y gamepad buttons
    public void update() {
        if (gamepad1.x && !intakeOn) {
            intakeOn = true;
        } else if (gamepad1.y && intakeOn) {
            intakeOn = false;
        }// else if (!gamepad1.x && gamepad1.y && !intakeOn && !ejectOn) {
        //    ejectOn = true;
        //} else if (!gamepad1.x && gamepad1.y && !intakeOn && ejectOn) {
        //    ejectOn = false;
        //}

        if (intakeOn) {// && !ejectOn) {
            rightIntake.setPower(1);
            leftIntake.setPower(1);
        } else if (!intakeOn) {
            rightIntake.setPower(0);
            leftIntake.setPower(0);
        }// else if (!intakeOn && ejectOn) {
        //    rightIntake.setPower(-1);
        //    leftIntake.setPower(-1);
        //}

        if (!intakeOn && gamepad1.b) {
            rightIntake.setPower(-1);
            leftIntake.setPower(-1);
        }

        rightIntake.setPower(0);
        leftIntake.setPower(0);
    }
}
