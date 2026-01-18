package org.bouncycastle.pqc.crypto.ntru;

import org.bouncycastle.util.Arrays;

public class NTRUPrivateKeyParameters extends NTRUKeyParameters {
  final byte[] privateKey;
  
  public NTRUPrivateKeyParameters(NTRUParameters paramNTRUParameters, byte[] paramArrayOfbyte) {
    super(true, paramNTRUParameters);
    this.privateKey = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getPrivateKey() {
    return Arrays.clone(this.privateKey);
  }
  
  public byte[] getEncoded() {
    return getPrivateKey();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntru\NTRUPrivateKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */