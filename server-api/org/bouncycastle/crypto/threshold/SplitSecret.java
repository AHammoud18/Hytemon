package org.bouncycastle.crypto.threshold;

import java.io.IOException;

public interface SplitSecret {
  SecretShare[] getSecretShares();
  
  byte[] getSecret() throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\threshold\SplitSecret.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */