package com.google.common.flogger.backend;

import java.util.logging.Level;

public abstract class LoggerBackend {
  public abstract String getLoggerName();
  
  public abstract boolean isLoggable(Level paramLevel);
  
  public abstract void log(LogData paramLogData);
  
  public abstract void handleError(RuntimeException paramRuntimeException, LogData paramLogData);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\LoggerBackend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */