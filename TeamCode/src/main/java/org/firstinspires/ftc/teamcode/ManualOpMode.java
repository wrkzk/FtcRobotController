package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.modules.Arm;

@TeleOp(name = "Intake/Arm Test Mode", group = "")
public class ManualOpMode extends LinearOpMode {

    HardwareMap hwMap;1

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Arm arm = new Arm(hwMap);

        waitForStart();

        while (opModeIsActive()) {
            robot.update();
        }
    }
}