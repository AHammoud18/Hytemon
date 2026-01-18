package org.bouncycastle.pqc.crypto.ntru;

import java.security.SecureRandom;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.util.SecretWithEncapsulationImpl;
import org.bouncycastle.pqc.math.ntru.Polynomial;
import org.bouncycastle.pqc.math.ntru.parameters.NTRUParameterSet;
import org.bouncycastle.util.Arrays;

public class NTRUKEMGenerator implements EncapsulatedSecretGenerator {
  private final SecureRandom random;
  
  public NTRUKEMGenerator(SecureRandom paramSecureRandom) {
    if (paramSecureRandom == null)
      throw new NullPointerException("'random' cannot be null"); 
    this.random = paramSecureRandom;
  }
  
  public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter paramAsymmetricKeyParameter) {
    if (paramAsymmetricKeyParameter == null)
      throw new NullPointerException("'recipientKey' cannot be null"); 
    NTRUPublicKeyParameters nTRUPublicKeyParameters = (NTRUPublicKeyParameters)paramAsymmetricKeyParameter;
    NTRUParameterSet nTRUParameterSet = nTRUPublicKeyParameters.getParameters().getParameterSet();
    NTRUSampling nTRUSampling = new NTRUSampling(nTRUParameterSet);
    NTRUOWCPA nTRUOWCPA = new NTRUOWCPA(nTRUParameterSet);
    byte[] arrayOfByte1 = new byte[nTRUParameterSet.owcpaMsgBytes()];
    byte[] arrayOfByte2 = new byte[nTRUParameterSet.sampleRmBytes()];
    this.random.nextBytes(arrayOfByte2);
    PolynomialPair polynomialPair = nTRUSampling.sampleRm(arrayOfByte2);
    Polynomial polynomial1 = polynomialPair.r();
    Polynomial polynomial2 = polynomialPair.m();
    polynomial1.s3ToBytes(arrayOfByte1, 0);
    polynomial2.s3ToBytes(arrayOfByte1, nTRUParameterSet.packTrinaryBytes());
    SHA3Digest sHA3Digest = new SHA3Digest(256);
    byte[] arrayOfByte3 = new byte[sHA3Digest.getDigestSize()];
    sHA3Digest.update(arrayOfByte1, 0, arrayOfByte1.length);
    sHA3Digest.doFinal(arrayOfByte3, 0);
    polynomial1.z3ToZq();
    byte[] arrayOfByte4 = nTRUOWCPA.encrypt(polynomial1, polynomial2, nTRUPublicKeyParameters.publicKey);
    byte[] arrayOfByte5 = Arrays.copyOfRange(arrayOfByte3, 0, nTRUParameterSet.sharedKeyBytes());
    Arrays.clear(arrayOfByte3);
    return (SecretWithEncapsulation)new SecretWithEncapsulationImpl(arrayOfByte5, arrayOfByte4);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntru\NTRUKEMGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */