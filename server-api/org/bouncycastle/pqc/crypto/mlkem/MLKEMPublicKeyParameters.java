package org.bouncycastle.pqc.crypto.mlkem;

import org.bouncycastle.util.Arrays;

public class MLKEMPublicKeyParameters extends MLKEMKeyParameters {
  final byte[] t;
  
  final byte[] rho;
  
  static byte[] getEncoded(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    return Arrays.concatenate(paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  public MLKEMPublicKeyParameters(MLKEMParameters paramMLKEMParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    super(false, paramMLKEMParameters);
    MLKEMEngine mLKEMEngine = paramMLKEMParameters.getEngine();
    if (paramArrayOfbyte1.length != mLKEMEngine.getKyberPolyVecBytes())
      throw new IllegalArgumentException("'t' has invalid length"); 
    if (paramArrayOfbyte2.length != 32)
      throw new IllegalArgumentException("'rho' has invalid length"); 
    this.t = Arrays.clone(paramArrayOfbyte1);
    this.rho = Arrays.clone(paramArrayOfbyte2);
    if (!mLKEMEngine.checkModulus(this.t))
      throw new IllegalArgumentException("Modulus check failed for ML-KEM public key"); 
  }
  
  public MLKEMPublicKeyParameters(MLKEMParameters paramMLKEMParameters, byte[] paramArrayOfbyte) {
    super(false, paramMLKEMParameters);
    MLKEMEngine mLKEMEngine = paramMLKEMParameters.getEngine();
    if (paramArrayOfbyte.length != mLKEMEngine.getKyberIndCpaPublicKeyBytes())
      throw new IllegalArgumentException("'encoding' has invalid length"); 
    this.t = Arrays.copyOfRange(paramArrayOfbyte, 0, paramArrayOfbyte.length - 32);
    this.rho = Arrays.copyOfRange(paramArrayOfbyte, paramArrayOfbyte.length - 32, paramArrayOfbyte.length);
    if (!mLKEMEngine.checkModulus(this.t))
      throw new IllegalArgumentException("Modulus check failed for ML-KEM public key"); 
  }
  
  public byte[] getEncoded() {
    return getEncoded(this.t, this.rho);
  }
  
  public byte[] getRho() {
    return Arrays.clone(this.rho);
  }
  
  public byte[] getT() {
    return Arrays.clone(this.t);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mlkem\MLKEMPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */