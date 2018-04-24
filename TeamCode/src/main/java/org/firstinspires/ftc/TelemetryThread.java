package org.firstinspires.ftc;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by andrew on 4/24/18.
 */

public class TelemetryThread implements Runnable {

    private Telemetry telemetry;
    private Thread t;

    public static TelemetryThread instance;

    public static void create(Telemetry telem) {
        instance = new TelemetryThread(telem);
    }

    private TelemetryThread(Telemetry telem) {
        this.t = new Thread(this);
        this.telemetry = telem;
    }

    @Override
    public void run() {
        while (true) {
            telemetry.update();
        }
    }

    public synchronized void addTelemetry(String tag, Object msg) {
        telemetry.addData(tag, String.valueOf(msg));
    }

    public void start() {
        this.t.start();
    }

    public void stop() {
        this.t.interrupt();
    }

}
