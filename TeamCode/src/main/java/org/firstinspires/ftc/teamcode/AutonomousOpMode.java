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
import java.lang.InterruptedException;


@Autonomous(name = "Autonomous OpMode")
public class AutonomousOpMode extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {"Ball", "Cube", "Duck", "Marker"};

    private static final String VUFORIA_KEY = "AdKwFHr/////AAABmUrKa6jPRkCNg8oDY6tARNNXDktMphXL0xSR/B1J7j5Cfpi+yERJZsfKfoecmT+s4/cTg4A5d1X721HLFbZ0plZB1xJC3CUlh1mw2igecNnoSDKmfK5fOUFV8HlJt3325yDvjX5+PMRaQjEVdv6HbiUAL0tlH8/ffMP9rbY3UYrkiEnxK05/EKCRKnGQPn7DCDcbIaD/gS110uFMRn74vqksry9HkN0+WAZPznmyKMhqoIgfDjm8uP7E8uKviitRc6xpXuUIsUNndwdrXJ7NgM1BPraB/ILOgzXGqhAzS1JRKXkJVMyKQGrshOOFQxJYznbEhsCxe+RF/VC4Pj8jzfHUr0qUwCU3UbCdAnHAbgix";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private int targetLevel;
    private double motorPower = 0.3;

    @Override
    public void runOpMode() throws InterruptedException {

        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1, 16.0/9.0);
        }

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        boolean duckFound = false;
        int numOfPasses = 0;

        Arm arm = new Arm(hardwareMap, gamepad1);
        Drivetrain drive = new Drivetrain(hardwareMap, gamepad1);
        arm.armUpStageOne();

        waitForStart();

        if (opModeIsActive()) {

            while (!duckFound) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                    if (numOfPasses > 1000) {
                        if (updatedRecognitions != null) {
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals("Duck")) {
                                    if (recognition.getLeft() < 200) {
                                        targetLevel = 2;
                                        if (numOfPasses > 1500) {
                                            duckFound = true;
                                        }
                                    } else if (recognition.getLeft() > 200) {
                                        targetLevel = 3;
                                        if (numOfPasses > 1500) {
                                            duckFound = true;
                                        }
                                    }
                                }
                            }
                            if (duckFound == false) {
                                targetLevel = 1;
                                if (numOfPasses > 1500) {
                                    duckFound = true;
                                }
                            }
                        }
                    }
                    numOfPasses++;

                }

                telemetry.addData("Target Level", targetLevel);
                telemetry.update();
            }

            drive.driveStraight(75, motorPower);
            //drive.resetMotors();
            Thread.sleep(200);
            drive.turnRight(250, motorPower);
            //drive.resetMotors();
            Thread.sleep(200);
            drive.driveStraight(400, motorPower);
            //drive.resetMotors();
            Thread.sleep(200);
            drive.turnRight(300, motorPower);
            //drive.resetMotors();
            Thread.sleep(200);
            drive.driveReverse(110, motorPower);
            //drive.resetMotors();

            drive.driveReverse(1, 0.01);

            if (targetLevel == 1) {
                arm.armUpLevelOne();
            } else if (targetLevel == 2) {
                arm.armUpLevelTwo();
            } else if (targetLevel == 3) {
                arm.armUpLevelThree();
            }

            Thread.sleep(200);
            drive.turnLeft(280, motorPower);
            Thread.sleep(200);
            drive.driveStraight(1500, 0.5);
            arm.armDownStageTwo();
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}