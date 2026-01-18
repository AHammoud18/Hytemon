package org.bouncycastle.pqc.crypto.hqc;

import java.security.SecureRandom;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.util.SecretWithEncapsulationImpl;
import org.bouncycastle.util.Arrays;

public class HQCKEMGenerator implements EncapsulatedSecretGenerator {
  private final SecureRandom sr;
  
  public HQCKEMGenerator(SecureRandom paramSecureRandom) {
    this.sr = paramSecureRandom;
  }
  
  public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    HQCPublicKeyParameters hQCPublicKeyParameters = (HQCPublicKeyParameters)paramAsymmetricKeyParameter;
    HQCEngine hQCEngine = hQCPublicKeyParameters.getParameters().getEngine();
    byte[] arrayOfByte1 = new byte[hQCPublicKeyParameters.getParameters().getSHA512_BYTES()];
    byte[] arrayOfByte2 = new byte[hQCPublicKeyParameters.getParameters().getN_BYTES()];
    byte[] arrayOfByte3 = new byte[hQCPublicKeyParameters.getParameters().getN1N2_BYTES()];
    byte[] arrayOfByte4 = new byte[hQCPublicKeyParameters.getParameters().getSALT_SIZE_BYTES()];
    byte[] arrayOfByte5 = hQCPublicKeyParameters.getPublicKey();
    hQCEngine.encaps(arrayOfByte2, arrayOfByte3, arrayOfByte1, arrayOfByte5, arrayOfByte4, this.sr);
    byte[] arrayOfByte6 = Arrays.concatenate(arrayOfByte2, arrayOfByte3, arrayOfByte4);
    return (SecretWithEncapsulation)new SecretWithEncapsulationImpl(Arrays.copyOfRange(arrayOfByte1, 0, 32), arrayOfByte6);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\hqc\HQCKEMGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */