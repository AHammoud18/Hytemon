package org.bouncycastle.pqc.crypto.saber;

import org.bouncycastle.util.Arrays;

public class SABERPrivateKeyParameters extends SABERKeyParameters {
  private byte[] privateKey;
  
  public SABERPrivateKeyParameters(SABERParameters paramSABERParameters, byte[] paramArrayOfbyte) {
    super(true, paramSABERParameters);
    this.privateKey = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getPrivateKey() {
    return Arrays.clone(this.privateKey);
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.privateKey);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\saber\SABERPrivateKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */