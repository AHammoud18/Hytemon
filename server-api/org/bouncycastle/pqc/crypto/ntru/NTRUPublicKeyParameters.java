package org.bouncycastle.pqc.crypto.ntru;

import org.bouncycastle.util.Arrays;

public class NTRUPublicKeyParameters extends NTRUKeyParameters {
  final byte[] publicKey;
  
  public NTRUPublicKeyParameters(NTRUParameters paramNTRUParameters, byte[] paramArrayOfbyte) {
    super(false, paramNTRUParameters);
    this.publicKey = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getPublicKey() {
    return Arrays.clone(this.publicKey);
  }
  
  public byte[] getEncoded() {
    return getPublicKey();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntru\NTRUPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */