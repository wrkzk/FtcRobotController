package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Spinner;

@TeleOp(name = "Driver-Controlled OpMode", group = "")
public class ManualOpMode extends LinearOpMode {

    private HardwareMap hwMap;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Arm arm = new Arm(hwMap, gamepad1);
        Drivetrain drive = new Drivetrain(hwMap, gamepad1);
        Intake intake = new Intake(hwMap, gamepad1);
        Spinner spinner = new Spinner(hwMap, gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            arm.update();
            drive.update();
            intake.update();
            spinner.update();
        }
    }
}