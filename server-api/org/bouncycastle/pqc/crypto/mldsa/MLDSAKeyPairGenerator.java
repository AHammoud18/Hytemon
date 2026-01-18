package org.bouncycastle.pqc.crypto.mldsa;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class MLDSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private MLDSAParameters parameters;
  
  private SecureRandom random;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    this.parameters = ((MLDSAKeyGenerationParameters)paramKeyGenerationParameters).getParameters();
    this.random = paramKeyGenerationParameters.getRandom();
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    MLDSAEngine mLDSAEngine = this.parameters.getEngine(this.random);
    byte[][] arrayOfByte = mLDSAEngine.generateKeyPair();
    MLDSAPublicKeyParameters mLDSAPublicKeyParameters = new MLDSAPublicKeyParameters(this.parameters, arrayOfByte[0], arrayOfByte[6]);
    MLDSAPrivateKeyParameters mLDSAPrivateKeyParameters = new MLDSAPrivateKeyParameters(this.parameters, arrayOfByte[0], arrayOfByte[1], arrayOfByte[2], arrayOfByte[3], arrayOfByte[4], arrayOfByte[5], arrayOfByte[6], arrayOfByte[7]);
    return new AsymmetricCipherKeyPair(mLDSAPublicKeyParameters, mLDSAPrivateKeyParameters);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\MLDSAKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */