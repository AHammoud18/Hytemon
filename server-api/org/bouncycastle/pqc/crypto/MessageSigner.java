package org.bouncycastle.pqc.crypto;

import org.bouncycastle.crypto.CipherParameters;

public interface MessageSigner {
  void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  byte[] generateSignature(byte[] paramArrayOfbyte);
  
  boolean verifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\MessageSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */