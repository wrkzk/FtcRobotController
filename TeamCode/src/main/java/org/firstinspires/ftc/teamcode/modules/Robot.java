package org.firstinspires.ftc.teamcode.modules;

public class Robot {

    private Arm arm;

    public Robot() {
        arm = new Arm();
    }

    public void update() {
        arm.update();
    }

}
