package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Spinner;
import java.lang.InterruptedException;
import org.firstinspires.ftc.teamcode.modules.Component;

@Autonomous

public class RedAutonomousDuck extends LinearOpMode{

    class SpinnerThread extends Thread {
        Spinner spinner;
        double spinnerSpeed;

        public SpinnerThread(Spinner spinner) {
            this.spinner = spinner;
            this.spinnerSpeed = 0;
        }

        public void setSpeed(double speed) {
            this.spinnerSpeed = speed;
        }

        public void run() {
            while (opModeIsActive()) {
                try {
                    spinner.setSpeed(spinnerSpeed);
                } catch (Exception e) {
                    telemetry.addData("Status", "Error: " + e);
                    telemetry.update();
                }
            }
        }
    }Update everything

    private double motorPower = .4;

    public void runOpMode() throws InterruptedException {

        waitForStart();

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        Drivetrain drive = new Drivetrain(hardwareMap, gamepad1);
        Spinner spinner = new Spinner(hardwareMap, gamepad2);
        Arm arm = new Arm(hardwareMap, gamepad2);

        SpinnerThread spinnerThread = new SpinnerThread(spinner);

        if (opModeIsActive()) {
            spinnerThread.start();
        }

        arm.armUpStageOne();
        //drive.driveStraight(75, motorPower);
        drive.driveStraight(100, motorPower);
        //drive.resetMotors();
        Thread.sleep(500);
        //drive.turnRight(250, motorPower);
        drive.turnLeft(225, motorPower);
        //drive.resetMotors();
        Thread.sleep(500);
        //drive.driveStraight(400, motorPower);
        drive.driveStraight(465, motorPower);
        //drive.resetMotors();
        Thread.sleep(500);
        drive.turnRight(320, motorPower);
        //drive.resetMotors();
        Thread.sleep(500);
        //drive.driveReverse(110, motorPower);
        motorPower = .3;
        drive.driveReverse(175, motorPower);
        //drive.resetMotors();

        drive.resetMotors();

        Thread.sleep(500);

        drive.driveReverse(25, 0.3);

        spinnerThread.setSpeed(-0.4);
        Thread.sleep(2500);
        spinnerThread.setSpeed(-1);
        Thread.sleep(2500);
        spinnerThread.setSpeed(0);

        //drive.driveReverse(50, .1);
        //spinner.variableSpin(2500, .6);

        drive.resetMotors();

        drive.turnLeft(70, motorPower);
        drive.driveStraight(100, motorPower);

        Thread.sleep(300);
        drive.driveStraight(150, motorPower);


    }
}