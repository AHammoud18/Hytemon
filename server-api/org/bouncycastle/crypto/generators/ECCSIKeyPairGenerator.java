package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.CryptoServiceProperties;
import org.bouncycastle.crypto.CryptoServicePurpose;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECCSIKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECCSIPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECCSIPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.BigIntegers;

public class ECCSIKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private BigInteger q;
  
  private ECPoint G;
  
  private Digest digest;
  
  private ECCSIKeyGenerationParameters parameters;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    this.parameters = (ECCSIKeyGenerationParameters)paramKeyGenerationParameters;
    this.q = this.parameters.getQ();
    this.G = this.parameters.getG();
    this.digest = this.parameters.getDigest();
    CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties("ECCSI", this.parameters.getN(), null, CryptoServicePurpose.KEYGEN));
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    SecureRandom secureRandom = this.parameters.getRandom();
    this.digest.reset();
    byte[] arrayOfByte1 = this.parameters.getId();
    ECPoint eCPoint1 = this.parameters.getKPAK();
    BigInteger bigInteger1 = BigIntegers.createRandomBigInteger(256, secureRandom).mod(this.q);
    ECPoint eCPoint2 = this.G.multiply(bigInteger1).normalize();
    byte[] arrayOfByte2 = this.G.getEncoded(false);
    this.digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    arrayOfByte2 = eCPoint1.getEncoded(false);
    this.digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    this.digest.update(arrayOfByte1, 0, arrayOfByte1.length);
    arrayOfByte2 = eCPoint2.getEncoded(false);
    this.digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    arrayOfByte2 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte2, 0);
    BigInteger bigInteger2 = (new BigInteger(1, arrayOfByte2)).mod(this.q);
    BigInteger bigInteger3 = this.parameters.computeSSK(bigInteger2.multiply(bigInteger1));
    ECCSIPublicKeyParameters eCCSIPublicKeyParameters = new ECCSIPublicKeyParameters(eCPoint2);
    return new AsymmetricCipherKeyPair((AsymmetricKeyParameter)new ECCSIPublicKeyParameters(eCPoint2), (AsymmetricKeyParameter)new ECCSIPrivateKeyParameters(bigInteger3, eCCSIPublicKeyParameters));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\generators\ECCSIKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */