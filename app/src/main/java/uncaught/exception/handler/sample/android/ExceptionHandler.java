package uncaught.exception.handler.sample.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;

class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context activity;

    ExceptionHandler(Context context) {
        this.activity = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        String LINE_SEPARATOR = "\n";
        String errorReport = "***********CAUSE OF ERROR***********\n\n" +
                stackTrace.toString() +
                "\n***********DEVICE INFORMATION***********\n\n" +
                "Brand:" +
                Build.BRAND +
                LINE_SEPARATOR +
                "DEVICE:" +
                Build.DEVICE +
                LINE_SEPARATOR +
                "Model:" +
                Build.MODEL +
                LINE_SEPARATOR +
                "Id:" +
                Build.ID +
                LINE_SEPARATOR +
                "Product:" +
                Build.PRODUCT +
                LINE_SEPARATOR +
                "\n***********FIRMWARE***********\n\n" +
                "SDK:" +
                Build.VERSION.SDK +
                LINE_SEPARATOR +
                "Release:" +
                Build.VERSION.RELEASE +
                LINE_SEPARATOR +
                "INCREMENTAL" +
                Build.VERSION.INCREMENTAL +
                LINE_SEPARATOR;

        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("error", errorReport);
        activity.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}