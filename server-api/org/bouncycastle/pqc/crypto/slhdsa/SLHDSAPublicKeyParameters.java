package org.bouncycastle.pqc.crypto.slhdsa;

import org.bouncycastle.util.Arrays;

public class SLHDSAPublicKeyParameters extends SLHDSAKeyParameters {
  private final PK pk;
  
  public SLHDSAPublicKeyParameters(SLHDSAParameters paramSLHDSAParameters, byte[] paramArrayOfbyte) {
    super(false, paramSLHDSAParameters);
    int i = paramSLHDSAParameters.getN();
    if (paramArrayOfbyte.length != 2 * i)
      throw new IllegalArgumentException("public key encoding does not match parameters"); 
    this.pk = new PK(Arrays.copyOfRange(paramArrayOfbyte, 0, i), Arrays.copyOfRange(paramArrayOfbyte, i, 2 * i));
  }
  
  SLHDSAPublicKeyParameters(SLHDSAParameters paramSLHDSAParameters, PK paramPK) {
    super(false, paramSLHDSAParameters);
    this.pk = paramPK;
  }
  
  public byte[] getSeed() {
    return Arrays.clone(this.pk.seed);
  }
  
  public byte[] getRoot() {
    return Arrays.clone(this.pk.root);
  }
  
  public byte[] getEncoded() {
    return Arrays.concatenate(this.pk.seed, this.pk.root);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */