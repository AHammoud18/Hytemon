package META-INF.versions.9.org.bouncycastle.pqc.crypto.slhdsa;

import org.bouncycastle.pqc.crypto.slhdsa.PK;
import org.bouncycastle.pqc.crypto.slhdsa.SK;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAKeyParameters;
import org.bouncycastle.pqc.crypto.slhdsa.SLHDSAParameters;
import org.bouncycastle.util.Arrays;

public class SLHDSAPrivateKeyParameters extends SLHDSAKeyParameters {
  final SK sk;
  
  final PK pk;
  
  public SLHDSAPrivateKeyParameters(SLHDSAParameters paramSLHDSAParameters, byte[] paramArrayOfbyte) {
    super(true, paramSLHDSAParameters);
    int i = paramSLHDSAParameters.getN();
    if (paramArrayOfbyte.length != 4 * i)
      throw new IllegalArgumentException("private key encoding does not match parameters"); 
    this.sk = new SK(Arrays.copyOfRange(paramArrayOfbyte, 0, i), Arrays.copyOfRange(paramArrayOfbyte, i, 2 * i));
    this.pk = new PK(Arrays.copyOfRange(paramArrayOfbyte, 2 * i, 3 * i), Arrays.copyOfRange(paramArrayOfbyte, 3 * i, 4 * i));
  }
  
  public SLHDSAPrivateKeyParameters(SLHDSAParameters paramSLHDSAParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) {
    super(true, paramSLHDSAParameters);
    this.sk = new SK(paramArrayOfbyte1, paramArrayOfbyte2);
    this.pk = new PK(paramArrayOfbyte3, paramArrayOfbyte4);
  }
  
  SLHDSAPrivateKeyParameters(SLHDSAParameters paramSLHDSAParameters, SK paramSK, PK paramPK) {
    super(true, paramSLHDSAParameters);
    this.sk = paramSK;
    this.pk = paramPK;
  }
  
  public byte[] getSeed() {
    return Arrays.clone(this.sk.seed);
  }
  
  public byte[] getPrf() {
    return Arrays.clone(this.sk.prf);
  }
  
  public byte[] getPublicSeed() {
    return Arrays.clone(this.pk.seed);
  }
  
  public byte[] getRoot() {
    return Arrays.clone(this.pk.root);
  }
  
  public byte[] getPublicKey() {
    return Arrays.concatenate(this.pk.seed, this.pk.root);
  }
  
  public byte[] getEncoded() {
    return Arrays.concatenate(new byte[][] { this.sk.seed, this.sk.prf, this.pk.seed, this.pk.root });
  }
  
  public byte[] getEncodedPublicKey() {
    return Arrays.concatenate(this.pk.seed, this.pk.root);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAPrivateKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */