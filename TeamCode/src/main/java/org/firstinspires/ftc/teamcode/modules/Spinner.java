package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Spinner implements Component {

    private HardwareMap hwMap;
    private Gamepad gamepad1;
    private DcMotor spinner;

    public Spinner(HardwareMap hwMap, Gamepad gamepad1) {
        this.hwMap = hwMap;
        this.gamepad1 = gamepad1;
        this.spinner = hwMap.get(DcMotor.class, "spinner");
    }

    // Update the duck spinner power based on the trigger power
    public void update() {
        double spinnerPower = -gamepad1.right_trigger;
        spinner.setPower(spinnerPower);
    }

}
