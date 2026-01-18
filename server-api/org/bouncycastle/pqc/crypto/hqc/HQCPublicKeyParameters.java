package org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.util.Arrays;

public class HQCPublicKeyParameters extends HQCKeyParameters {
  private final byte[] pk;
  
  public HQCPublicKeyParameters(HQCParameters paramHQCParameters, byte[] paramArrayOfbyte) {
    super(true, paramHQCParameters);
    this.pk = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getPublicKey() {
    return Arrays.clone(this.pk);
  }
  
  public byte[] getEncoded() {
    return getPublicKey();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\hqc\HQCPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */