package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Spinner;

@TeleOp(name = "Driver-Controlled OpMode", group = "")
public class ManualOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Arm arm = new Arm(hardwareMap, gamepad1);
        Drivetrain drive = new Drivetrain(hardwareMap, gamepad1);
        Intake intake = new Intake(hardwareMap, gamepad1);
        Spinner spinner = new Spinner(hardwareMap, gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            arm.update();
            drive.update();
            intake.update();
            spinner.update();
        }
    }
}