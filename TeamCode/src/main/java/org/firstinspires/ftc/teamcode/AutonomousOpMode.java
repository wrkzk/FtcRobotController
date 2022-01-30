package org.firstinspires.ftc.teamcode;

import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;

@TeleOp(name = "Tensorflow Detection Test")
public class TensorflowTestOpMode extends LinearOpMode {

    // Random
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = { "Ball", "Cube", "Duck", "Marker" };

    // Vuforia stuff
    private static final String VUFORIA_KEY = "AdKwFHr/////AAABmUrKa6jPRkCNg8oDY6tARNNXDktMphXL0xSR/B1J7j5Cfpi+yERJZsfKfoecmT+s4/cTg4A5d1X721HLFbZ0plZB1xJC3CUlh1mw2igecNnoSDKmfK5fOUFV8HlJt3325yDvjX5+PMRaQjEVdv6HbiUAL0tlH8/ffMP9rbY3UYrkiEnxK05/EKCRKnGQPn7DCDcbIaD/gS110uFMRn74vqksry9HkN0+WAZPznmyKMhqoIgfDjm8uP7E8uKviitRc6xpXuUIsUNndwdrXJ7NgM1BPraB/ILOgzXGqhAzS1JRKXkJVMyKQGrshOOFQxJYznbEhsCxe+RF/VC4Pj8jzfHUr0qUwCU3UbCdAnHAbgix";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // Initialize the Vuforia localizer (for video frames) and tensorflow
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();

            // Set the tensorflow zoom and aspect ratio
            tfod.setZoom(2.5, 16.0/9.0);
        }

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {

                    // Get the updates list of recognitions from tensorflow (will return null if no new recognitions)
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {

                        // Iterate through the list and display the bounding information
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f", recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f", recognition.getRight(), recognition.getBottom());
                            i++;
                        }

                        // Update the telemetry
                        telemetry.update();
                    }
                }
            }
        }
    }

    // Initialize Vuforia
    private void initVuforia() {
        // Create a Parameters object to initialize Vuforia
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        // Pass the Vuforia key and the webcam name to the parameters object
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    // Initialize Tensorflow
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        // Create a Parameters object for tensorflow
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        // Pass in the parameters to the tensorflow parameters object, and create the tensorflow instance
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Load the trained tensorflow model
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
