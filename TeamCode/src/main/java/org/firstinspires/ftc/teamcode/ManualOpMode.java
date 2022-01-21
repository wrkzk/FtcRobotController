package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.modules.Robot;

@TeleOp(name = "Intake/Arm Test Mode", group = "")
public class ManualOpMode extends LinearOpMode {
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Robot robot = new Robot();

        waitForStart();

        while (opModeIsActive()) {
            robot.update();
        }
    }
}