package org.bouncycastle.pqc.crypto.mlkem;

import java.security.SecureRandom;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.util.SecretWithEncapsulationImpl;

public class MLKEMGenerator implements EncapsulatedSecretGenerator {
  private final SecureRandom sr;
  
  public MLKEMGenerator(SecureRandom paramSecureRandom) {
    this.sr = paramSecureRandom;
  }
  
  public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    byte[] arrayOfByte = new byte[32];
    this.sr.nextBytes(arrayOfByte);
    return internalGenerateEncapsulated(paramAsymmetricKeyParameter, arrayOfByte);
  }
  
  public SecretWithEncapsulation internalGenerateEncapsulated(AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfbyte) {
    MLKEMPublicKeyParameters mLKEMPublicKeyParameters = (MLKEMPublicKeyParameters)paramAsymmetricKeyParameter;
    MLKEMEngine mLKEMEngine = mLKEMPublicKeyParameters.getParameters().getEngine();
    mLKEMEngine.init(this.sr);
    byte[][] arrayOfByte = mLKEMEngine.kemEncrypt(mLKEMPublicKeyParameters, paramArrayOfbyte);
    return (SecretWithEncapsulation)new SecretWithEncapsulationImpl(arrayOfByte[0], arrayOfByte[1]);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mlkem\MLKEMGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */