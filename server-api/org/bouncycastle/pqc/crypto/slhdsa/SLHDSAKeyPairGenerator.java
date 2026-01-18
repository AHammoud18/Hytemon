package org.bouncycastle.pqc.crypto.slhdsa;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class SLHDSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private SecureRandom random;
  
  private SLHDSAParameters parameters;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    this.random = paramKeyGenerationParameters.getRandom();
    this.parameters = ((SLHDSAKeyGenerationParameters)paramKeyGenerationParameters).getParameters();
  }
  
  public AsymmetricCipherKeyPair internalGenerateKeyPair(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
    return implGenerateKeyPair(this.parameters.getEngine(), paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    SLHDSAEngine sLHDSAEngine = this.parameters.getEngine();
    byte[] arrayOfByte1 = sec_rand(sLHDSAEngine.N);
    byte[] arrayOfByte2 = sec_rand(sLHDSAEngine.N);
    byte[] arrayOfByte3 = sec_rand(sLHDSAEngine.N);
    return implGenerateKeyPair(sLHDSAEngine, arrayOfByte1, arrayOfByte2, arrayOfByte3);
  }
  
  private AsymmetricCipherKeyPair implGenerateKeyPair(SLHDSAEngine paramSLHDSAEngine, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
    SK sK = new SK(paramArrayOfbyte1, paramArrayOfbyte2);
    paramSLHDSAEngine.init(paramArrayOfbyte3);
    PK pK = new PK(paramArrayOfbyte3, (new HT(paramSLHDSAEngine, sK.seed, paramArrayOfbyte3)).htPubKey);
    return new AsymmetricCipherKeyPair(new SLHDSAPublicKeyParameters(this.parameters, pK), new SLHDSAPrivateKeyParameters(this.parameters, sK, pK));
  }
  
  private byte[] sec_rand(int paramInt) {
    byte[] arrayOfByte = new byte[paramInt];
    this.random.nextBytes(arrayOfByte);
    return arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SLHDSAKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */