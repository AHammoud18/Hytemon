package com.google.crypto.tink.internal;

import com.google.crypto.tink.annotations.Alpha;

@Alpha
public interface MonitoringClient {
  Logger createLogger(KeysetHandleInterface paramKeysetHandleInterface, MonitoringAnnotations paramMonitoringAnnotations, String paramString1, String paramString2);
  
  public static interface Logger {
    default void log(int keyId, long numBytesAsInput) {}
    
    default void logFailure() {}
    
    default void logKeyExport(int keyId) {}
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MonitoringClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */