package org.bouncycastle.pqc.crypto.xwing;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.crypto.prng.FixedSecureRandom;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMKeyPairGenerator;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.util.Arrays;

public class XWingKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private SecureRandom random;
  
  private void initialize(KeyGenerationParameters paramKeyGenerationParameters) {
    this.random = paramKeyGenerationParameters.getRandom();
  }
  
  static AsymmetricCipherKeyPair genKeyPair(byte[] paramArrayOfbyte) {
    SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
    sHAKEDigest.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    byte[] arrayOfByte1 = new byte[96];
    sHAKEDigest.doOutput(arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = Arrays.copyOfRange(arrayOfByte1, 0, 64);
    byte[] arrayOfByte3 = Arrays.copyOfRange(arrayOfByte1, 64, 96);
    FixedSecureRandom fixedSecureRandom1 = new FixedSecureRandom(arrayOfByte2);
    MLKEMKeyPairGenerator mLKEMKeyPairGenerator = new MLKEMKeyPairGenerator();
    mLKEMKeyPairGenerator.init((KeyGenerationParameters)new MLKEMKeyGenerationParameters((SecureRandom)fixedSecureRandom1, MLKEMParameters.ml_kem_768));
    AsymmetricCipherKeyPair asymmetricCipherKeyPair1 = mLKEMKeyPairGenerator.generateKeyPair();
    MLKEMPublicKeyParameters mLKEMPublicKeyParameters = (MLKEMPublicKeyParameters)asymmetricCipherKeyPair1.getPublic();
    MLKEMPrivateKeyParameters mLKEMPrivateKeyParameters = (MLKEMPrivateKeyParameters)asymmetricCipherKeyPair1.getPrivate();
    FixedSecureRandom fixedSecureRandom2 = new FixedSecureRandom(arrayOfByte3);
    X25519KeyPairGenerator x25519KeyPairGenerator = new X25519KeyPairGenerator();
    x25519KeyPairGenerator.init((KeyGenerationParameters)new X25519KeyGenerationParameters((SecureRandom)fixedSecureRandom2));
    AsymmetricCipherKeyPair asymmetricCipherKeyPair2 = x25519KeyPairGenerator.generateKeyPair();
    X25519PublicKeyParameters x25519PublicKeyParameters = (X25519PublicKeyParameters)asymmetricCipherKeyPair2.getPublic();
    X25519PrivateKeyParameters x25519PrivateKeyParameters = (X25519PrivateKeyParameters)asymmetricCipherKeyPair2.getPrivate();
    return new AsymmetricCipherKeyPair(new XWingPublicKeyParameters((AsymmetricKeyParameter)mLKEMPublicKeyParameters, (AsymmetricKeyParameter)x25519PublicKeyParameters), new XWingPrivateKeyParameters(paramArrayOfbyte, mLKEMPrivateKeyParameters, x25519PrivateKeyParameters, mLKEMPublicKeyParameters, x25519PublicKeyParameters));
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    initialize(paramKeyGenerationParameters);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    byte[] arrayOfByte = new byte[32];
    this.random.nextBytes(arrayOfByte);
    return genKeyPair(arrayOfByte);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\xwing\XWingKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */