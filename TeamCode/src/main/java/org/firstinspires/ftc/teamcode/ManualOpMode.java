package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// Import the modules for the hardware
import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Spinner;
import org.firstinspires.ftc.teamcode.modules.Component;

@TeleOp(name = "Driver-Controlled OpMode", group = "")
public class ManualOpMode extends LinearOpMode {

    // General thread to control groups of components
    class ComponentThread extends Thread {
        Component[] components;

        public ComponentThread(Component[] components) {
            this.components = components;
        }

        public void run() {
            while (opModeIsActive()) {
                try {
                    for (Component component : components) {
                        component.update();
                    }
                } catch (Exception e) {
                    telemetry.addData("Status", "Error: " + e);
                    telemetry.update();
                }
            }
        }
    }

    @Override
    public void runOpMode() {

        // Initialize each piece of hardware
        Arm arm = new Arm(hardwareMap, gamepad2);
        Drivetrain drive = new Drivetrain(hardwareMap, gamepad1);
        Intake intake = new Intake(hardwareMap, gamepad2);
        Spinner spinner = new Spinner(hardwareMap, gamepad2);

        // Initialize the drivetrain thread and the manipulators thread
        ComponentThread drivetrainThread = new ComponentThread(new Component[] {drive});
        ComponentThread manipulatorThread = new ComponentThread(new Component[] {arm, intake, spinner});

        // Alert the user that the program has initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // Once the user starts the program, start the threads for each subsystem
        if (opModeIsActive()) {
            drivetrainThread.start();
            manipulatorThread.start();
        }
    }
}