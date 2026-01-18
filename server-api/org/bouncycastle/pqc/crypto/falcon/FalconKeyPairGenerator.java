package org.bouncycastle.pqc.crypto.falcon;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class FalconKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private FalconKeyGenerationParameters params;
  
  private FalconNIST nist;
  
  private int pk_size;
  
  private int sk_size;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    this.params = (FalconKeyGenerationParameters)paramKeyGenerationParameters;
    SecureRandom secureRandom = paramKeyGenerationParameters.getRandom();
    int i = ((FalconKeyGenerationParameters)paramKeyGenerationParameters).getParameters().getLogN();
    int j = ((FalconKeyGenerationParameters)paramKeyGenerationParameters).getParameters().getNonceLength();
    this.nist = new FalconNIST(i, j, secureRandom);
    int k = 1 << i;
    byte b = 8;
    if (k == 1024) {
      b = 5;
    } else if (k == 256 || k == 512) {
      b = 6;
    } else if (k == 64 || k == 128) {
      b = 7;
    } 
    this.pk_size = 1 + 14 * k / 8;
    this.sk_size = 1 + 2 * b * k / 8 + k;
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    byte[] arrayOfByte1 = new byte[this.pk_size];
    byte[] arrayOfByte2 = new byte[this.sk_size];
    byte[][] arrayOfByte = this.nist.crypto_sign_keypair(arrayOfByte1, arrayOfByte2);
    FalconParameters falconParameters = this.params.getParameters();
    FalconPrivateKeyParameters falconPrivateKeyParameters = new FalconPrivateKeyParameters(falconParameters, arrayOfByte[1], arrayOfByte[2], arrayOfByte[3], arrayOfByte[0]);
    FalconPublicKeyParameters falconPublicKeyParameters = new FalconPublicKeyParameters(falconParameters, arrayOfByte[0]);
    return new AsymmetricCipherKeyPair(falconPublicKeyParameters, falconPrivateKeyParameters);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */