package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.util.Arrays;

public class FalconPublicKeyParameters extends FalconKeyParameters {
  private final byte[] H;
  
  public FalconPublicKeyParameters(FalconParameters paramFalconParameters, byte[] paramArrayOfbyte) {
    super(false, paramFalconParameters);
    this.H = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getH() {
    return Arrays.clone(this.H);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */