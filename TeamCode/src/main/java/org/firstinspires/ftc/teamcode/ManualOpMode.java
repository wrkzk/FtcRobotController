package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;

@TeleOp(name = "Driver-Controlled OpMode", group = "")
public class ManualOpMode extends LinearOpMode {

    private HardwareMap hwMap;

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Arm arm = new Arm(hwMap, gamepad1);
        Drivetrain drive = new Drivetrain(hwMap, gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            arm.update();
            drive.update();
        }
    }
}