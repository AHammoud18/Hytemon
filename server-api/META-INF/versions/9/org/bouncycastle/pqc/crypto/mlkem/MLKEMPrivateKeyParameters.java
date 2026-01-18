package META-INF.versions.9.org.bouncycastle.pqc.crypto.mlkem;

import org.bouncycastle.pqc.crypto.mlkem.MLKEMEngine;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.util.Arrays;

public class MLKEMPrivateKeyParameters extends MLKEMKeyParameters {
  public static final int BOTH = 0;
  
  public static final int SEED_ONLY = 1;
  
  public static final int EXPANDED_KEY = 2;
  
  final byte[] s;
  
  final byte[] hpk;
  
  final byte[] nonce;
  
  final byte[] t;
  
  final byte[] rho;
  
  final byte[] seed;
  
  private final int prefFormat;
  
  public MLKEMPrivateKeyParameters(MLKEMParameters paramMLKEMParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5) {
    this(paramMLKEMParameters, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfbyte4, paramArrayOfbyte5, null);
  }
  
  public MLKEMPrivateKeyParameters(MLKEMParameters paramMLKEMParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, byte[] paramArrayOfbyte6) {
    super(true, paramMLKEMParameters);
    this.s = Arrays.clone(paramArrayOfbyte1);
    this.hpk = Arrays.clone(paramArrayOfbyte2);
    this.nonce = Arrays.clone(paramArrayOfbyte3);
    this.t = Arrays.clone(paramArrayOfbyte4);
    this.rho = Arrays.clone(paramArrayOfbyte5);
    this.seed = Arrays.clone(paramArrayOfbyte6);
    this.prefFormat = 0;
  }
  
  public MLKEMPrivateKeyParameters(MLKEMParameters paramMLKEMParameters, byte[] paramArrayOfbyte) {
    this(paramMLKEMParameters, paramArrayOfbyte, null);
  }
  
  public MLKEMPrivateKeyParameters(MLKEMParameters paramMLKEMParameters, byte[] paramArrayOfbyte, MLKEMPublicKeyParameters paramMLKEMPublicKeyParameters) {
    super(true, paramMLKEMParameters);
    MLKEMEngine mLKEMEngine = paramMLKEMParameters.getEngine();
    if (paramArrayOfbyte.length == 64) {
      byte[][] arrayOfByte = mLKEMEngine.generateKemKeyPairInternal(Arrays.copyOfRange(paramArrayOfbyte, 0, 32), Arrays.copyOfRange(paramArrayOfbyte, 32, paramArrayOfbyte.length));
      this.s = arrayOfByte[2];
      this.hpk = arrayOfByte[3];
      this.nonce = arrayOfByte[4];
      this.t = arrayOfByte[0];
      this.rho = arrayOfByte[1];
      this.seed = arrayOfByte[5];
    } else {
      int i = 0;
      this.s = Arrays.copyOfRange(paramArrayOfbyte, 0, mLKEMEngine.getKyberIndCpaSecretKeyBytes());
      i += mLKEMEngine.getKyberIndCpaSecretKeyBytes();
      this.t = Arrays.copyOfRange(paramArrayOfbyte, i, i + mLKEMEngine.getKyberIndCpaPublicKeyBytes() - 32);
      i += mLKEMEngine.getKyberIndCpaPublicKeyBytes() - 32;
      this.rho = Arrays.copyOfRange(paramArrayOfbyte, i, i + 32);
      i += 32;
      this.hpk = Arrays.copyOfRange(paramArrayOfbyte, i, i + 32);
      i += 32;
      this.nonce = Arrays.copyOfRange(paramArrayOfbyte, i, i + 32);
      this.seed = null;
    } 
    if (paramMLKEMPublicKeyParameters != null && (!Arrays.constantTimeAreEqual(this.t, paramMLKEMPublicKeyParameters.t) || !Arrays.constantTimeAreEqual(this.rho, paramMLKEMPublicKeyParameters.rho)))
      throw new IllegalArgumentException("passed in public key does not match private values"); 
    this.prefFormat = (this.seed == null) ? 2 : 0;
  }
  
  private MLKEMPrivateKeyParameters(org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters paramMLKEMPrivateKeyParameters, int paramInt) {
    super(true, paramMLKEMPrivateKeyParameters.getParameters());
    this.s = paramMLKEMPrivateKeyParameters.s;
    this.t = paramMLKEMPrivateKeyParameters.t;
    this.rho = paramMLKEMPrivateKeyParameters.rho;
    this.hpk = paramMLKEMPrivateKeyParameters.hpk;
    this.nonce = paramMLKEMPrivateKeyParameters.nonce;
    this.seed = paramMLKEMPrivateKeyParameters.seed;
    this.prefFormat = paramInt;
  }
  
  public org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters getParametersWithFormat(int paramInt) {
    if (this.prefFormat == paramInt)
      return this; 
    switch (paramInt) {
      case 0:
      case 1:
        if (this.seed == null)
          throw new IllegalStateException("no seed available"); 
      case 2:
        return new org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters(this, paramInt);
    } 
    throw new IllegalArgumentException("unknown format");
  }
  
  public int getPreferredFormat() {
    return this.prefFormat;
  }
  
  public byte[] getEncoded() {
    return Arrays.concatenate(new byte[][] { this.s, this.t, this.rho, this.hpk, this.nonce });
  }
  
  public byte[] getHPK() {
    return Arrays.clone(this.hpk);
  }
  
  public byte[] getNonce() {
    return Arrays.clone(this.nonce);
  }
  
  public byte[] getPublicKey() {
    return MLKEMPublicKeyParameters.getEncoded(this.t, this.rho);
  }
  
  public MLKEMPublicKeyParameters getPublicKeyParameters() {
    return new MLKEMPublicKeyParameters(getParameters(), this.t, this.rho);
  }
  
  public byte[] getRho() {
    return Arrays.clone(this.rho);
  }
  
  public byte[] getS() {
    return Arrays.clone(this.s);
  }
  
  public byte[] getT() {
    return Arrays.clone(this.t);
  }
  
  public byte[] getSeed() {
    return Arrays.clone(this.seed);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mlkem\MLKEMPrivateKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */