package org.bouncycastle.pqc.crypto.snova;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.util.Arrays;

public class SnovaKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private SnovaEngine engine;
  
  private static final int seedLength = 48;
  
  static final int publicSeedLength = 16;
  
  static final int privateSeedLength = 32;
  
  private SnovaParameters params;
  
  private SecureRandom random;
  
  private boolean initialized;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    SnovaKeyGenerationParameters snovaKeyGenerationParameters = (SnovaKeyGenerationParameters)paramKeyGenerationParameters;
    this.params = snovaKeyGenerationParameters.getParameters();
    this.random = snovaKeyGenerationParameters.getRandom();
    this.initialized = true;
    this.engine = new SnovaEngine(this.params);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    if (!this.initialized)
      throw new IllegalStateException("SNOVA key pair generator not initialized"); 
    byte[] arrayOfByte1 = new byte[48];
    this.random.nextBytes(arrayOfByte1);
    byte[] arrayOfByte2 = new byte[this.params.getPublicKeyLength()];
    byte[] arrayOfByte3 = new byte[this.params.getPrivateKeyLength()];
    byte[] arrayOfByte4 = Arrays.copyOfRange(arrayOfByte1, 0, 16);
    byte[] arrayOfByte5 = Arrays.copyOfRange(arrayOfByte1, 16, arrayOfByte1.length);
    SnovaKeyElements snovaKeyElements = new SnovaKeyElements(this.params);
    System.arraycopy(arrayOfByte4, 0, arrayOfByte2, 0, arrayOfByte4.length);
    this.engine.genMap1T12Map2(snovaKeyElements, arrayOfByte4, arrayOfByte5);
    this.engine.genP22(arrayOfByte2, arrayOfByte4.length, snovaKeyElements.T12, snovaKeyElements.map1.p21, snovaKeyElements.map2.f12);
    System.arraycopy(arrayOfByte4, 0, arrayOfByte2, 0, arrayOfByte4.length);
    if (this.params.isSkIsSeed()) {
      arrayOfByte3 = arrayOfByte1;
    } else {
      int i = this.params.getO();
      int j = this.params.getLsq();
      int k = this.params.getV();
      int m = i * this.params.getAlpha() * j * 4 + k * i * j + (i * k * k + i * k * i + i * i * k) * j;
      byte[] arrayOfByte = new byte[m];
      int n = 0;
      n = SnovaKeyElements.copy3d(snovaKeyElements.map1.aAlpha, arrayOfByte, n);
      n = SnovaKeyElements.copy3d(snovaKeyElements.map1.bAlpha, arrayOfByte, n);
      n = SnovaKeyElements.copy3d(snovaKeyElements.map1.qAlpha1, arrayOfByte, n);
      n = SnovaKeyElements.copy3d(snovaKeyElements.map1.qAlpha2, arrayOfByte, n);
      n = SnovaKeyElements.copy3d(snovaKeyElements.T12, arrayOfByte, n);
      n = SnovaKeyElements.copy4d(snovaKeyElements.map2.f11, arrayOfByte, n);
      n = SnovaKeyElements.copy4d(snovaKeyElements.map2.f12, arrayOfByte, n);
      SnovaKeyElements.copy4d(snovaKeyElements.map2.f21, arrayOfByte, n);
      GF16Utils.encodeMergeInHalf(arrayOfByte, m, arrayOfByte3);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte3, arrayOfByte3.length - 48, 48);
    } 
    return new AsymmetricCipherKeyPair(new SnovaPublicKeyParameters(this.params, arrayOfByte2), new SnovaPrivateKeyParameters(this.params, arrayOfByte3));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\snova\SnovaKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */