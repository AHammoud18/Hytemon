package org.bouncycastle.pqc.crypto.xwing;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.agreement.X25519Agreement;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMGenerator;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.SecretWithEncapsulationImpl;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class XWingKEMGenerator implements EncapsulatedSecretGenerator {
  private final SecureRandom random;
  
  private static final byte[] XWING_LABEL = Strings.toByteArray("\\.//^\\");
  
  public XWingKEMGenerator(SecureRandom paramSecureRandom) {
    this.random = paramSecureRandom;
  }
  
  public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    XWingPublicKeyParameters xWingPublicKeyParameters = (XWingPublicKeyParameters)paramAsymmetricKeyParameter;
    MLKEMPublicKeyParameters mLKEMPublicKeyParameters = xWingPublicKeyParameters.getKyberPublicKey();
    X25519PublicKeyParameters x25519PublicKeyParameters = xWingPublicKeyParameters.getXDHPublicKey();
    byte[] arrayOfByte1 = x25519PublicKeyParameters.getEncoded();
    MLKEMGenerator mLKEMGenerator = new MLKEMGenerator(this.random);
    SecretWithEncapsulation secretWithEncapsulation = mLKEMGenerator.generateEncapsulated((AsymmetricKeyParameter)mLKEMPublicKeyParameters);
    byte[] arrayOfByte2 = secretWithEncapsulation.getEncapsulation();
    X25519KeyPairGenerator x25519KeyPairGenerator = new X25519KeyPairGenerator();
    x25519KeyPairGenerator.init((KeyGenerationParameters)new X25519KeyGenerationParameters(this.random));
    AsymmetricCipherKeyPair asymmetricCipherKeyPair = x25519KeyPairGenerator.generateKeyPair();
    byte[] arrayOfByte3 = ((X25519PublicKeyParameters)asymmetricCipherKeyPair.getPublic()).getEncoded();
    byte[] arrayOfByte4 = computeSSX(x25519PublicKeyParameters, (X25519PrivateKeyParameters)asymmetricCipherKeyPair.getPrivate());
    byte[] arrayOfByte5 = computeSharedSecret(arrayOfByte1, secretWithEncapsulation.getSecret(), arrayOfByte3, arrayOfByte4);
    Arrays.clear(arrayOfByte4);
    return (SecretWithEncapsulation)new SecretWithEncapsulationImpl(arrayOfByte5, Arrays.concatenate(arrayOfByte2, arrayOfByte3));
  }
  
  static byte[] computeSSX(X25519PublicKeyParameters paramX25519PublicKeyParameters, X25519PrivateKeyParameters paramX25519PrivateKeyParameters) {
    X25519Agreement x25519Agreement = new X25519Agreement();
    x25519Agreement.init((CipherParameters)paramX25519PrivateKeyParameters);
    byte[] arrayOfByte = new byte[x25519Agreement.getAgreementSize()];
    x25519Agreement.calculateAgreement((CipherParameters)paramX25519PublicKeyParameters, arrayOfByte, 0);
    return arrayOfByte;
  }
  
  static byte[] computeSharedSecret(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) {
    SHA3Digest sHA3Digest = new SHA3Digest(256);
    sHA3Digest.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
    sHA3Digest.update(paramArrayOfbyte4, 0, paramArrayOfbyte4.length);
    sHA3Digest.update(paramArrayOfbyte3, 0, paramArrayOfbyte3.length);
    sHA3Digest.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
    sHA3Digest.update(XWING_LABEL, 0, XWING_LABEL.length);
    byte[] arrayOfByte = new byte[32];
    sHA3Digest.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\xwing\XWingKEMGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */