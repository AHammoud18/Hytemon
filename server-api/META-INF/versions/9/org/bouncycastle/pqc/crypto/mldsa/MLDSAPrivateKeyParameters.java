package META-INF.versions.9.org.bouncycastle.pqc.crypto.mldsa;

import org.bouncycastle.pqc.crypto.mldsa.MLDSAEngine;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAKeyParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAPublicKeyParameters;
import org.bouncycastle.util.Arrays;

public class MLDSAPrivateKeyParameters extends MLDSAKeyParameters {
  public static final int BOTH = 0;
  
  public static final int SEED_ONLY = 1;
  
  public static final int EXPANDED_KEY = 2;
  
  final byte[] rho;
  
  final byte[] k;
  
  final byte[] tr;
  
  final byte[] s1;
  
  final byte[] s2;
  
  final byte[] t0;
  
  private final byte[] t1;
  
  private final byte[] seed;
  
  private final int prefFormat;
  
  public MLDSAPrivateKeyParameters(MLDSAParameters paramMLDSAParameters, byte[] paramArrayOfbyte) {
    this(paramMLDSAParameters, paramArrayOfbyte, null);
  }
  
  public MLDSAPrivateKeyParameters(MLDSAParameters paramMLDSAParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, byte[] paramArrayOfbyte6, byte[] paramArrayOfbyte7) {
    this(paramMLDSAParameters, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfbyte4, paramArrayOfbyte5, paramArrayOfbyte6, paramArrayOfbyte7, null);
  }
  
  public MLDSAPrivateKeyParameters(MLDSAParameters paramMLDSAParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, byte[] paramArrayOfbyte5, byte[] paramArrayOfbyte6, byte[] paramArrayOfbyte7, byte[] paramArrayOfbyte8) {
    super(true, paramMLDSAParameters);
    this.rho = Arrays.clone(paramArrayOfbyte1);
    this.k = Arrays.clone(paramArrayOfbyte2);
    this.tr = Arrays.clone(paramArrayOfbyte3);
    this.s1 = Arrays.clone(paramArrayOfbyte4);
    this.s2 = Arrays.clone(paramArrayOfbyte5);
    this.t0 = Arrays.clone(paramArrayOfbyte6);
    this.t1 = Arrays.clone(paramArrayOfbyte7);
    this.seed = Arrays.clone(paramArrayOfbyte8);
    this.prefFormat = (paramArrayOfbyte8 != null) ? 0 : 2;
  }
  
  public MLDSAPrivateKeyParameters(MLDSAParameters paramMLDSAParameters, byte[] paramArrayOfbyte, MLDSAPublicKeyParameters paramMLDSAPublicKeyParameters) {
    super(true, paramMLDSAParameters);
    MLDSAEngine mLDSAEngine = paramMLDSAParameters.getEngine(null);
    if (paramArrayOfbyte.length == 32) {
      byte[][] arrayOfByte = mLDSAEngine.generateKeyPairInternal(paramArrayOfbyte);
      this.rho = arrayOfByte[0];
      this.k = arrayOfByte[1];
      this.tr = arrayOfByte[2];
      this.s1 = arrayOfByte[3];
      this.s2 = arrayOfByte[4];
      this.t0 = arrayOfByte[5];
      this.t1 = arrayOfByte[6];
      this.seed = arrayOfByte[7];
    } else {
      int i = 0;
      this.rho = Arrays.copyOfRange(paramArrayOfbyte, 0, 32);
      i += true;
      this.k = Arrays.copyOfRange(paramArrayOfbyte, i, i + 32);
      i += 32;
      this.tr = Arrays.copyOfRange(paramArrayOfbyte, i, i + 64);
      i += 64;
      int j = mLDSAEngine.getDilithiumL() * mLDSAEngine.getDilithiumPolyEtaPackedBytes();
      this.s1 = Arrays.copyOfRange(paramArrayOfbyte, i, i + j);
      i += j;
      j = mLDSAEngine.getDilithiumK() * mLDSAEngine.getDilithiumPolyEtaPackedBytes();
      this.s2 = Arrays.copyOfRange(paramArrayOfbyte, i, i + j);
      i += j;
      j = mLDSAEngine.getDilithiumK() * 416;
      this.t0 = Arrays.copyOfRange(paramArrayOfbyte, i, i + j);
      i += j;
      this.t1 = mLDSAEngine.deriveT1(this.rho, this.k, this.tr, this.s1, this.s2, this.t0);
      this.seed = null;
    } 
    if (paramMLDSAPublicKeyParameters != null && !Arrays.constantTimeAreEqual(this.t1, paramMLDSAPublicKeyParameters.getT1()))
      throw new IllegalArgumentException("passed in public key does not match private values"); 
    this.prefFormat = (this.seed != null) ? 0 : 2;
  }
  
  private MLDSAPrivateKeyParameters(org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters paramMLDSAPrivateKeyParameters, int paramInt) {
    super(true, paramMLDSAPrivateKeyParameters.getParameters());
    this.rho = paramMLDSAPrivateKeyParameters.rho;
    this.k = paramMLDSAPrivateKeyParameters.k;
    this.tr = paramMLDSAPrivateKeyParameters.tr;
    this.s1 = paramMLDSAPrivateKeyParameters.s1;
    this.s2 = paramMLDSAPrivateKeyParameters.s2;
    this.t0 = paramMLDSAPrivateKeyParameters.t0;
    this.t1 = paramMLDSAPrivateKeyParameters.t1;
    this.seed = paramMLDSAPrivateKeyParameters.seed;
    this.prefFormat = paramInt;
  }
  
  public org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters getParametersWithFormat(int paramInt) {
    if (this.prefFormat == paramInt)
      return this; 
    switch (paramInt) {
      case 0:
      case 1:
        if (this.seed == null)
          throw new IllegalStateException("no seed available"); 
      case 2:
        return new org.bouncycastle.pqc.crypto.mldsa.MLDSAPrivateKeyParameters(this, paramInt);
    } 
    throw new IllegalArgumentException("unknown format");
  }
  
  public int getPreferredFormat() {
    return this.prefFormat;
  }
  
  public byte[] getEncoded() {
    return Arrays.concatenate(new byte[][] { this.rho, this.k, this.tr, this.s1, this.s2, this.t0 });
  }
  
  public byte[] getK() {
    return Arrays.clone(this.k);
  }
  
  @Deprecated
  public byte[] getPrivateKey() {
    return getEncoded();
  }
  
  public byte[] getPublicKey() {
    return MLDSAPublicKeyParameters.getEncoded(this.rho, this.t1);
  }
  
  public byte[] getSeed() {
    return Arrays.clone(this.seed);
  }
  
  public MLDSAPublicKeyParameters getPublicKeyParameters() {
    return (this.t1 == null) ? null : new MLDSAPublicKeyParameters(getParameters(), this.rho, this.t1);
  }
  
  public byte[] getRho() {
    return Arrays.clone(this.rho);
  }
  
  public byte[] getS1() {
    return Arrays.clone(this.s1);
  }
  
  public byte[] getS2() {
    return Arrays.clone(this.s2);
  }
  
  public byte[] getT0() {
    return Arrays.clone(this.t0);
  }
  
  public byte[] getT1() {
    return Arrays.clone(this.t1);
  }
  
  public byte[] getTr() {
    return Arrays.clone(this.tr);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mldsa\MLDSAPrivateKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */