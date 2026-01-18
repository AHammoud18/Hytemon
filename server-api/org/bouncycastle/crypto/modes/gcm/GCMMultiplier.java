package org.bouncycastle.crypto.modes.gcm;

public interface GCMMultiplier {
  void init(byte[] paramArrayOfbyte);
  
  void multiplyH(byte[] paramArrayOfbyte);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\modes\gcm\GCMMultiplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */