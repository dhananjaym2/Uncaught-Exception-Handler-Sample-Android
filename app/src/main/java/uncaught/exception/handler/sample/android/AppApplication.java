package uncaught.exception.handler.sample.android;

import android.app.Application;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }
}
