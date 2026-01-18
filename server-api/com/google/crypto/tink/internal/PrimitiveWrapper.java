package com.google.crypto.tink.internal;

import java.security.GeneralSecurityException;

public interface PrimitiveWrapper<B, P> {
  P wrap(KeysetHandleInterface paramKeysetHandleInterface, MonitoringAnnotations paramMonitoringAnnotations, PrimitiveFactory<B> paramPrimitiveFactory) throws GeneralSecurityException;
  
  Class<P> getPrimitiveClass();
  
  Class<B> getInputPrimitiveClass();
  
  public static interface PrimitiveFactory<B> {
    B create(KeysetHandleInterface.Entry param1Entry) throws GeneralSecurityException;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\PrimitiveWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */