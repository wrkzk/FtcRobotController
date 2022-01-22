package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    private HardwareMap hwMap;
    private Gamepad gamepad1;

    private Servo scoop;
    private DcMotor arm;
    private int currentArmState = 1;

    private final double scoopOpenPos = 0.085;
    private final double scoopClosedPos = 0.3;
    private final double scoopDropPos = 0.95;

    private final int stageOneTicks = 80;
    private final int stageTwoTicks = 695;

    private final double stageOnePower = 0.5;
    private final double stageTwoPower = 0.7;
    private final double armDownPower = 0.5;

    public Arm(HardwareMap hwMap, Gamepad gamepad1) {
        scoop.setPosition(scoopOpenPos);
        this.hwMap = hwMap;
        this.gamepad1 = gamepad1;
        this.scoop = hwMap.get(Servo.class, "scoop");
        this.arm = hwMap.get(DcMotor.class, "arm");
    }

    private void armUpStageOne() {
        if (currentArmState == 1) {
            scoop.setPosition(scoopClosedPos);
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(-stageOneTicks);
            arm.setPower(stageOnePower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            currentArmState = 2;
        }
    }

    private void armUpStageTwo() {
        if (currentArmState == 2) {
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(-stageTwoTicks);
            arm.setPower(stageTwoPower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (!arm.isBusy()) {
                scoop.setPosition(scoopDropPos);
                currentArmState = 3;
            }
        }
    }

    private void armDownStageOne() {
        if (currentArmState == 3) {
            scoop.setPosition(scoopClosedPos);
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(stageTwoTicks);
            arm.setPower(armDownPower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            currentArmState = 2;
        }
    }

    private void armDownStageTwo() {
        if (currentArmState == 2) {
            scoop.setPosition(scoopOpenPos);
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(stageOneTicks);
            arm.setPower(armDownPower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            currentArmState = 1;
        }
    }

    public void update() {
        if (gamepad1.right_bumper && currentArmState == 1) {
            armUpStageOne();
        } else if (gamepad1.right_bumper && currentArmState == 2) {
            armUpStageTwo();
        } else if (gamepad1.left_bumper && currentArmState == 2) {
            armDownStageTwo();
        } else if (gamepad1.left_bumper && currentArmState == 3) {
            armDownStageOne();
        }
    }
}