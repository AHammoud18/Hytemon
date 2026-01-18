package org.bouncycastle.crypto.threshold;

import java.io.IOException;
import org.bouncycastle.util.Arrays;

public class ShamirSplitSecretShare implements SecretShare {
  private final byte[] secretShare;
  
  final int r;
  
  public ShamirSplitSecretShare(byte[] paramArrayOfbyte, int paramInt) {
    this.secretShare = Arrays.clone(paramArrayOfbyte);
    this.r = paramInt;
  }
  
  public ShamirSplitSecretShare(byte[] paramArrayOfbyte) {
    this.secretShare = Arrays.clone(paramArrayOfbyte);
    this.r = 1;
  }
  
  public byte[] getEncoded() throws IOException {
    return Arrays.clone(this.secretShare);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\threshold\ShamirSplitSecretShare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */