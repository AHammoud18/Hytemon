package META-INF.versions.9.org.bouncycastle.crypto;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.KeyGenerationParameters;

public interface AsymmetricCipherKeyPairGenerator {
  void init(KeyGenerationParameters paramKeyGenerationParameters);
  
  AsymmetricCipherKeyPair generateKeyPair();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\crypto\AsymmetricCipherKeyPairGenerator.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */