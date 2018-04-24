package org.firstinspires.ftc;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by andrew on 4/24/18.
 */

public class GyroThread implements Runnable {

    public static class DontIntegrate implements BNO055IMU.AccelerationIntegrator {

        @Override
        public void initialize(@NonNull BNO055IMU.Parameters parameters, @Nullable Position initialPosition, @Nullable Velocity initialVelocity) {
        }

        @Override
        public Position getPosition() {
            return null;
        }

        @Override
        public Velocity getVelocity() {
            return null;
        }

        @Override
        public Acceleration getAcceleration() {
            return null;
        }

        @Override
        public void update(Acceleration linearAcceleration) {
        }
    }

    private BNO055IMU gyro;
    private Thread t;
    private long prevTime;
    public double pitch, yaw;

    public static GyroThread instance;
    private static String IMU_NAME = "imu2";

    public static void create(HardwareMap map) {
        // gyro shenanigans
        BNO055IMU.Parameters gyroParams = new BNO055IMU.Parameters();
        gyroParams.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        gyroParams.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        gyroParams.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        gyroParams.loggingEnabled      = true;
        gyroParams.loggingTag          = "IMU";
        gyroParams.accelerationIntegrationAlgorithm = new DontIntegrate();

        BNO055IMU imu = map.get(BNO055IMU.class, IMU_NAME);
        imu.initialize(gyroParams);

        instance = new GyroThread(imu);
    }

    private GyroThread(BNO055IMU gyro) {
        this.t = new Thread(this);
        this.gyro = gyro;
    }

    @Override
    public void run() {
        while (true) {
            TelemetryThread.instance.addTelemetry("gyro DT", System.nanoTime() - prevTime);
            prevTime = System.nanoTime();

            Orientation o = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);

            pitch = -o.firstAngle;
            yaw = o.secondAngle;
        }
    }

    public void start() {
        this.prevTime = System.nanoTime();
        this.t.start();
    }

    public void stop() {
        this.t.interrupt();
    }
}
