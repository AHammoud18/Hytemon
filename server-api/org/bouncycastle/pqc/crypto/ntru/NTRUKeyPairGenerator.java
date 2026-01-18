package org.bouncycastle.pqc.crypto.ntru;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.math.ntru.parameters.NTRUParameterSet;
import org.bouncycastle.util.Arrays;

public class NTRUKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
  private NTRUKeyGenerationParameters params;
  
  private SecureRandom random;
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters) {
    this.params = (NTRUKeyGenerationParameters)paramKeyGenerationParameters;
    this.random = paramKeyGenerationParameters.getRandom();
  }
  
  public AsymmetricCipherKeyPair generateKeyPair() {
    NTRUParameters nTRUParameters = this.params.getParameters();
    NTRUParameterSet nTRUParameterSet = nTRUParameters.getParameterSet();
    byte[] arrayOfByte1 = new byte[nTRUParameterSet.sampleFgBytes()];
    this.random.nextBytes(arrayOfByte1);
    NTRUOWCPA nTRUOWCPA = new NTRUOWCPA(nTRUParameterSet);
    OWCPAKeyPair oWCPAKeyPair = nTRUOWCPA.keypair(arrayOfByte1);
    byte[] arrayOfByte2 = oWCPAKeyPair.publicKey;
    byte[] arrayOfByte3 = new byte[nTRUParameterSet.prfKeyBytes()];
    this.random.nextBytes(arrayOfByte3);
    byte[] arrayOfByte4 = Arrays.concatenate(oWCPAKeyPair.privateKey, arrayOfByte3);
    return new AsymmetricCipherKeyPair(new NTRUPublicKeyParameters(nTRUParameters, arrayOfByte2), new NTRUPrivateKeyParameters(nTRUParameters, arrayOfByte4));
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntru\NTRUKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */