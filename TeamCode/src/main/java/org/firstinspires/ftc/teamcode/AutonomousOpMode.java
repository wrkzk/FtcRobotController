package org.firstinspires.ftc.teamcode;

import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;

@TeleOp(name = "Autonomous OpMode")
public class AutonomousOpMode extends LinearOpMode {

    // ML model constants
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = { "Ball", "Cube", "Duck", "Marker" };

    // Webcam constants
    private static final String VUFORIA_KEY = "AdKwFHr/////AAABmUrKa6jPRkCNg8oDY6tARNNXDktMphXL0xSR/B1J7j5Cfpi+yERJZsfKfoecmT+s4/cTg4A5d1X721HLFbZ0plZB1xJC3CUlh1mw2igecNnoSDKmfK5fOUFV8HlJt3325yDvjX5+PMRaQjEVdv6HbiUAL0tlH8/ffMP9rbY3UYrkiEnxK05/EKCRKnGQPn7DCDcbIaD/gS110uFMRn74vqksry9HkN0+WAZPznmyKMhqoIgfDjm8uP7E8uKviitRc6xpXuUIsUNndwdrXJ7NgM1BPraB/ILOgzXGqhAzS1JRKXkJVMyKQGrshOOFQxJYznbEhsCxe+RF/VC4Pj8jzfHUr0qUwCU3UbCdAnHAbgix";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private boolean isDuckFound = false;
    private int targetLevel;

    @Override
    public void runOpMode() {
        // Initialize the Vuforia localizer (for video frames) and tensorflow
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2.5, 16.0/9.0);
        }

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                // Determine which level the duck has to go on
                if (!isDuckFound && tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    while (updatedRecognitions != null) {
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals("Block") || recognition.getLabel().equals("Duck")) {
                                Recognition duckRecognition = recognition;
                                float totalWidth = duckRecognition.getLeft() + duckRecognition.getRight();
                                if (duckRecognition.getLeft() < totalWidth / 3) {
                                    targetLevel = 1;
                                } else if (duckRecognition.getLeft() > totalWidth / 3 && duckRecognition.getLeft() < totalWidth * ((float)2 / 3)) {
                                    targetLevel = 2;
                                } else if (duckRecognition.getLeft() > totalWidth * ((float)2 / 3)) {
                                    targetLevel = 3;
                                }
                                isDuckFound = true;
                            }
                        }
                        updatedRecognitions = tfod.getUpdatedRecognitions();
                    }
                }

                // Drive forward a small amount
                // Turn right 90 degrees
                // Drive forward until on same level as the shipping hub
                // Turn right 90 degrees
                // Back into the shipping hub (distance might depend on what the targetLevel is)
                // Drive forward a small amount
                // Turn left 90 degrees
                // Drive forward until robot is completely inside of the
            }
        }
    }

    // Initialize Vuforia
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    // Initialize Tensorflow
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
