package org.bouncycastle.pqc.crypto.mldsa;

import org.bouncycastle.util.Arrays;

public class MLDSAPublicKeyParameters extends MLDSAKeyParameters {
  final byte[] rho;
  
  final byte[] t1;
  
  static byte[] getEncoded(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    return Arrays.concatenate(paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  public MLDSAPublicKeyParameters(MLDSAParameters paramMLDSAParameters, byte[] paramArrayOfbyte) {
    super(false, paramMLDSAParameters);
    this.rho = Arrays.copyOfRange(paramArrayOfbyte, 0, 32);
    this.t1 = Arrays.copyOfRange(paramArrayOfbyte, 32, paramArrayOfbyte.length);
    if (this.t1.length == 0)
      throw new IllegalArgumentException("encoding too short"); 
  }
  
  public MLDSAPublicKeyParameters(MLDSAParameters paramMLDSAParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    super(false, paramMLDSAParameters);
    if (paramArrayOfbyte1 == null)
      throw new NullPointerException("rho cannot be null"); 
    if (paramArrayOfbyte2 == null)
      throw new NullPointerException("t1 cannot be null"); 
    this.rho = Arrays.clone(paramArrayOfbyte1);
    this.t1 = Arrays.clone(paramArrayOfbyte2);
  }
  
  public byte[] getEncoded() {
    return getEncoded(this.rho, this.t1);
  }
  
  public byte[] getRho() {
    return Arrays.clone(this.rho);
  }
  
  public byte[] getT1() {
    return Arrays.clone(this.t1);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\MLDSAPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */