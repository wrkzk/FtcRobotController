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

@Autonomous

public class BlueAutonomousDuck extends LinearOpMode{

    private double motorPower = .3;

    public void runOpMode() throws InterruptedException {

        waitForStart();

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        Drivetrain drive = new Drivetrain(hardwareMap, gamepad1);
        Arm arm = new Arm(hardwareMap, gamepad1);
        Spinner spinner = new Spinner(hardwareMap, gamepad2);

        arm.armUpStageOne();
        //drive.driveStraight(75, motorPower);
        drive.driveStraight(200, motorPower);
        //drive.resetMotors();
        Thread.sleep(300);
        //drive.turnRight(250, motorPower);
        drive.turnRight(240, motorPower);
        //drive.resetMotors();
        Thread.sleep(300);
        //drive.driveStraight(400, motorPower);
        drive.driveStraight(440, motorPower);
        //drive.resetMotors();
        Thread.sleep(300);
        drive.turnLeft(320, motorPower);
        //drive.resetMotors();
        Thread.sleep(300);
        //drive.driveReverse(110, motorPower);
        drive.driveReverse(200, motorPower);
        //drive.resetMotors();

        drive.resetMotors();
    }
}
