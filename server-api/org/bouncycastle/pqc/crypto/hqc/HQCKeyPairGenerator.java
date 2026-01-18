package org.bouncycastle.pqc.crypto.hqc;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class HQCKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private HQCKeyGenerationParameters hqcKeyGenerationParameters;
  
  private SecureRandom random;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    this.hqcKeyGenerationParameters = (HQCKeyGenerationParameters)paramKeyGenerationParameters;
    this.random = paramKeyGenerationParameters.getRandom();
  }
  
  private AsymmetricCipherKeyPair genKeyPair() {
    HQCEngine hQCEngine = this.hqcKeyGenerationParameters.getParameters().getEngine();
    byte[] arrayOfByte1 = new byte[this.hqcKeyGenerationParameters.getParameters().getPublicKeyBytes()];
    byte[] arrayOfByte2 = new byte[this.hqcKeyGenerationParameters.getParameters().getSecretKeyBytes()];
    hQCEngine.genKeyPair(arrayOfByte1, arrayOfByte2, this.random);
    HQCPublicKeyParameters hQCPublicKeyParameters = new HQCPublicKeyParameters(this.hqcKeyGenerationParameters.getParameters(), arrayOfByte1);
    HQCPrivateKeyParameters hQCPrivateKeyParameters = new HQCPrivateKeyParameters(this.hqcKeyGenerationParameters.getParameters(), arrayOfByte2);
    return new AsymmetricCipherKeyPair(hQCPublicKeyParameters, hQCPrivateKeyParameters);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    return genKeyPair();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\hqc\HQCKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */