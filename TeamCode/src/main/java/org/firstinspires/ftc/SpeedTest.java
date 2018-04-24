package org.firstinspires.ftc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Sped")
public class SpeedTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        int wantedThreads = Runtime.getRuntime().availableProcessors()*2;

        GyroThread.create(hardwareMap);
        TelemetryThread.create(telemetry);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        GyroThread.instance.start();
        TelemetryThread.instance.start();

        while (opModeIsActive()) {
            double a = 123.2 * 1.4532 + 12.3*0.123123 + Math.random()*1111;
        }

        GyroThread.instance.stop();
        TelemetryThread.instance.stop();
    }


    // 30030726
}
