package com.google.common.flogger.backend;

import com.google.common.flogger.LogSite;
import java.util.logging.Level;

public interface LogData {
  Level getLevel();
  
  @Deprecated
  long getTimestampMicros();
  
  long getTimestampNanos();
  
  String getLoggerName();
  
  LogSite getLogSite();
  
  Metadata getMetadata();
  
  boolean wasForced();
  
  TemplateContext getTemplateContext();
  
  Object[] getArguments();
  
  Object getLiteralArgument();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\LogData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */