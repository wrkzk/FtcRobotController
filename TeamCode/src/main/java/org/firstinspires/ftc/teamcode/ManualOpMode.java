package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Spinner;

class DrivetrainThread extends Thread {

    Drivetrain drive;

    public DrivetrainThread(Drivetrain drive) {
        this.drive = drive;
    }

    public void run() {
        while (true) {
            try {
                drive.update();
            } catch (Exception e) {}
        }
    }
}

class ManipulatorThread extends Thread {

    Arm arm;
    Intake intake;
    Spinner spinner;

    public ManipulatorThread(Arm arm, Intake intake, Spinner spinner) {
        this.arm = arm;
        this.intake = intake;
        this.spinner = spinner;
    }

    public void run() {
        while (true) {
            try {
                arm.update();
                intake.update();
                spinner.update();
            } catch (Exception e) {}
        }
    }
}

@TeleOp(name = "Driver-Controlled OpMode", group = "")
public class ManualOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Arm arm = new Arm(hardwareMap, gamepad2);
        Drivetrain drive = new Drivetrain(hardwareMap, gamepad1);
        Intake intake = new Intake(hardwareMap, gamepad2);
        Spinner spinner = new Spinner(hardwareMap, gamepad2);

        DrivetrainThread drivetrain = new DrivetrainThread(drive);
        ManipulatorThread manipulators = new ManipulatorThread(arm, intake, spinner);

        waitForStart();

        if (opModeIsActive()) {
            drivetrain.start();
            manipulators.start();
        }

        while (opModeIsActive());

        drivetrain.stop();
        manipulators.stop();
    }
}