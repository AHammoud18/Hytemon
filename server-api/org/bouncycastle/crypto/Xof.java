package org.bouncycastle.crypto;

public interface Xof extends ExtendedDigest {
  int doFinal(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  int doOutput(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\Xof.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */