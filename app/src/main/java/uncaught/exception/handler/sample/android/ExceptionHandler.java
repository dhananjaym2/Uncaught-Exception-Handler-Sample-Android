package uncaught.exception.handler.sample.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;

class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context context;
    private String LINE_SEPARATOR = "\n";

    ExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
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
                LINE_SEPARATOR +
                "\n***********Application Info***********\n\n"
                + fetchCurrentApplicationInfo();

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("error", errorReport);
        context.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    private String fetchCurrentApplicationInfo() {
        long longVersionCode = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= 28) {
                longVersionCode = packageInfo.getLongVersionCode();
            } else {
                longVersionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "longVersionCode:" + longVersionCode + LINE_SEPARATOR;
    }
}