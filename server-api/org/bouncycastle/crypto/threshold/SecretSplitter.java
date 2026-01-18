package org.bouncycastle.crypto.threshold;

import java.io.IOException;

public interface SecretSplitter {
  SplitSecret split(int paramInt1, int paramInt2);
  
  SplitSecret splitAround(SecretShare paramSecretShare, int paramInt1, int paramInt2) throws IOException;
  
  SplitSecret resplit(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\threshold\SecretSplitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */