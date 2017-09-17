package com.example.synerzip.quiz;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
   private final String LINE_SEPARATOR = "\n";
   public static final String LOG_TAG = ExceptionHandler.class.getSimpleName();

  @SuppressWarnings("deprecation")
  public void uncaughtException(Thread thread, Throwable exception) {
    StringWriter stackTrace = new StringWriter();
    exception.printStackTrace(new PrintWriter(stackTrace));

      Log.e("Exception", stackTrace.toString());

    android.os.Process.killProcess(android.os.Process.myPid());
    System.exit(10);
   }
}