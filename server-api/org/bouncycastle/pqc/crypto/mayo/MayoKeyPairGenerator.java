package org.bouncycastle.pqc.crypto.mayo;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.GF16;
import org.bouncycastle.util.Longs;

public class MayoKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private MayoParameters p;
  
  private SecureRandom random;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    this.p = ((MayoKeyGenerationParameters)paramKeyGenerationParameters).getParameters();
    this.random = paramKeyGenerationParameters.getRandom();
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    int i = this.p.getMVecLimbs();
    int j = this.p.getM();
    int k = this.p.getV();
    int m = this.p.getO();
    int n = this.p.getOBytes();
    int i1 = this.p.getP1Limbs();
    int i2 = this.p.getP3Limbs();
    int i3 = this.p.getPkSeedBytes();
    int i4 = this.p.getSkSeedBytes();
    byte[] arrayOfByte1 = new byte[this.p.getCpkBytes()];
    byte[] arrayOfByte2 = new byte[this.p.getCskBytes()];
    byte[] arrayOfByte3 = new byte[i3 + n];
    long[] arrayOfLong1 = new long[i1 + this.p.getP2Limbs()];
    long[] arrayOfLong2 = new long[m * m * i];
    byte[] arrayOfByte4 = new byte[k * m];
    this.random.nextBytes(arrayOfByte2);
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    sHAKEDigest.update(arrayOfByte2, 0, i4);
    sHAKEDigest.doFinal(arrayOfByte3, 0, i3 + n);
    GF16.decode(arrayOfByte3, i3, arrayOfByte4, 0, arrayOfByte4.length);
    Utils.expandP1P2(this.p, arrayOfLong1, arrayOfByte3);
    GF16Utils.mulAddMUpperTriangularMatXMat(i, arrayOfLong1, arrayOfByte4, arrayOfLong1, i1, k, m);
    GF16Utils.mulAddMatTransXMMat(i, arrayOfByte4, arrayOfLong1, i1, arrayOfLong2, k, m);
    System.arraycopy(arrayOfByte3, 0, arrayOfByte1, 0, i3);
    long[] arrayOfLong3 = new long[i2];
    int i5 = 0;
    int i6 = m * i;
    byte b = 0;
    int i7 = 0;
    int i8 = 0;
    while (b < m) {
      byte b1 = b;
      int i9 = i7;
      int i10;
      for (i10 = i8; b1 < m; i10 += i6) {
        System.arraycopy(arrayOfLong2, i8 + i9, arrayOfLong3, i5, i);
        if (b != b1)
          Longs.xorTo(i, arrayOfLong2, i10 + i7, arrayOfLong3, i5); 
        i5 += i;
        b1++;
        i9 += i;
      } 
      b++;
      i8 += i6;
      i7 += i;
    } 
    Utils.packMVecs(arrayOfLong3, arrayOfByte1, i3, i2 / i, j);
    Arrays.clear(arrayOfByte4);
    Arrays.clear(arrayOfLong2);
    return new AsymmetricCipherKeyPair(new MayoPublicKeyParameters(this.p, arrayOfByte1), new MayoPrivateKeyParameters(this.p, arrayOfByte2));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mayo\MayoKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */