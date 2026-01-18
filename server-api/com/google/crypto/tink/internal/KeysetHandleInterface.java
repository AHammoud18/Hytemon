package com.google.crypto.tink.internal;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.KeyStatus;

public interface KeysetHandleInterface {
  Entry getPrimary();
  
  int size();
  
  Entry getAt(int paramInt);
  
  public static interface Entry {
    Key getKey();
    
    KeyStatus getStatus();
    
    int getId();
    
    boolean isPrimary();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\KeysetHandleInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */