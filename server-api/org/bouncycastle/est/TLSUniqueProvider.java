package org.bouncycastle.est;

public interface TLSUniqueProvider {
  boolean isTLSUniqueAvailable();
  
  byte[] getTLSUnique();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\est\TLSUniqueProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */