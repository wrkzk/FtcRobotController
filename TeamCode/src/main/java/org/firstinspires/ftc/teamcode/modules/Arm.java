package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.InterruptedException;

public class Arm implements Component {

    private HardwareMap hwMap;
    private Gamepad gamepad1;

    private Servo scoop;
    private DcMotor arm;

    // Variable to keep track of the current arm position
    private int currentArmState = 1;

    // Power and motor tick variables for each level
    private final double scoopOpenPos = 0.085;
    private final double scoopClosedPos = 0.3;
    private final double scoopDropPos = 0.95;
    private final int stageOneTicks = 80;
    //private final int stageTwoTicks = 695;
    private final int stageTwoTicks = 690;
    private final double stageOnePower = 0.5;
    private final double stageTwoPower = 0.8;
    private final double armDownPower = 0.75;

    public Arm(HardwareMap hwMap, Gamepad gamepad1) {
        this.hwMap = hwMap;
        this.gamepad1 = gamepad1;
        this.scoop = hwMap.get(Servo.class, "scoop");
        this.arm = hwMap.get(DcMotor.class, "arm");
        scoop.setPosition(scoopOpenPos);
    }

    // Move the arm from the collecting position to the storing position
    public void armUpStageOne() {
        if (currentArmState == 1) {
            scoop.setPosition(scoopClosedPos);
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(-stageOneTicks - 5);
            arm.setPower(stageOnePower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (arm.isBusy()) {}
            scoop.setPosition(scoopClosedPos);
            currentArmState = 2;
        }
    }

    // Move the arm from the storing position to the dropping position
    public void armUpStageTwo() {
        if (currentArmState == 2) {
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(-stageTwoTicks);
            arm.setPower(stageTwoPower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (arm.isBusy()) {}
            scoop.setPosition(scoopDropPos);
            currentArmState = 3;
        }
    }

    // Move the arm from the storing position to the first level of the shipping hub, and back down
    public void armUpLevelOne() throws InterruptedException {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(-450);
        arm.setPower(stageTwoPower);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (arm.isBusy()) {}
        scoop.setPosition(scoopDropPos);
        Thread.sleep(1500);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(450);
        arm.setPower(armDownPower);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        scoop.setPosition(scoopClosedPos);
        while (arm.isBusy()) {}
    }

    // Move the arm from the storing position to the second level of the shipping hub, and back down
    public void armUpLevelTwo() throws InterruptedException {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(-565);
        arm.setPower(stageTwoPower);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (arm.isBusy()) {}
        scoop.setPosition(scoopDropPos);
        Thread.sleep(1500);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(565);
        arm.setPower(armDownPower);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        scoop.setPosition(scoopClosedPos);
        while (arm.isBusy()) {}
    }

    // Move the arm from the storing position to the third level of the shipping hub, and back down
    public void armUpLevelThree() throws InterruptedException {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(-695);
        arm.setPower(stageTwoPower);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (arm.isBusy()) {
        }
        scoop.setPosition(scoopDropPos);
        Thread.sleep(1500);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(695);
        arm.setPower(armDownPower);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        scoop.setPosition(scoopClosedPos);
        while (arm.isBusy()) {
        }
    }

    // Move the arm from the dropping position to the storing position
    public void armDownStageOne() {
        if (currentArmState == 3) {
            scoop.setPosition(scoopClosedPos);
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(stageTwoTicks - 65);
            arm.setPower(armDownPower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (arm.isBusy()) {}
            currentArmState = 2;
        }
    }

    // Move the arm from the storing position to the collecting position
    public void armDownStageTwo() {
        if (currentArmState == 2) {
            scoop.setPosition(scoopOpenPos);
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(stageOneTicks);
            arm.setPower(armDownPower);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (arm.isBusy()) {}
            currentArmState = 1;
        }
    }

    // Keep track of the current arm state, and move the arm according to the LB and RB presses
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

        if (gamepad1.dpad_up) {
            arm.setPower(-0.4);
        } else if (gamepad1.dpad_down) {
            arm.setPower(0.2);
        }

        arm.setPower(0);
    }
}