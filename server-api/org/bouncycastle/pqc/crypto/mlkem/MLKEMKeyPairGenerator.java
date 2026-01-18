package org.bouncycastle.pqc.crypto.mlkem;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class MLKEMKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private MLKEMParameters mlkemParams;
  
  private SecureRandom random;
  
  private void initialize(KeyGenerationParameters paramKeyGenerationParameters) {
    this.mlkemParams = ((MLKEMKeyGenerationParameters)paramKeyGenerationParameters).getParameters();
    this.random = paramKeyGenerationParameters.getRandom();
  }
  
  private AsymmetricCipherKeyPair genKeyPair() {
    MLKEMEngine mLKEMEngine = this.mlkemParams.getEngine();
    mLKEMEngine.init(this.random);
    byte[][] arrayOfByte = mLKEMEngine.generateKemKeyPair();
    MLKEMPublicKeyParameters mLKEMPublicKeyParameters = new MLKEMPublicKeyParameters(this.mlkemParams, arrayOfByte[0], arrayOfByte[1]);
    MLKEMPrivateKeyParameters mLKEMPrivateKeyParameters = new MLKEMPrivateKeyParameters(this.mlkemParams, arrayOfByte[2], arrayOfByte[3], arrayOfByte[4], arrayOfByte[0], arrayOfByte[1], arrayOfByte[5]);
    return new AsymmetricCipherKeyPair(mLKEMPublicKeyParameters, mLKEMPrivateKeyParameters);
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    initialize(paramKeyGenerationParameters);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    return genKeyPair();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mlkem\MLKEMKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */