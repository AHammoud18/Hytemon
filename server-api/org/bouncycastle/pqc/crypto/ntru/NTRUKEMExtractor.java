package org.bouncycastle.pqc.crypto.ntru;

import org.bouncycastle.crypto.EncapsulatedSecretExtractor;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.pqc.math.ntru.parameters.NTRUParameterSet;
import org.bouncycastle.util.Arrays;

public class NTRUKEMExtractor implements EncapsulatedSecretExtractor {
  private final NTRUPrivateKeyParameters ntruPrivateKey;
  
  public NTRUKEMExtractor(NTRUPrivateKeyParameters paramNTRUPrivateKeyParameters) {
    if (paramNTRUPrivateKeyParameters == null)
      throw new NullPointerException("'ntruPrivateKey' cannot be null"); 
    this.ntruPrivateKey = paramNTRUPrivateKeyParameters;
  }
  
  public byte[] extractSecret(byte[] paramArrayOfbyte) {
    NTRUParameterSet nTRUParameterSet = this.ntruPrivateKey.getParameters().getParameterSet();
    if (paramArrayOfbyte == null)
      throw new NullPointerException("'encapsulation' cannot be null"); 
    if (paramArrayOfbyte.length != nTRUParameterSet.ntruCiphertextBytes())
      throw new IllegalArgumentException("encapsulation"); 
    byte[] arrayOfByte1 = this.ntruPrivateKey.privateKey;
    NTRUOWCPA nTRUOWCPA = new NTRUOWCPA(nTRUParameterSet);
    OWCPADecryptResult oWCPADecryptResult = nTRUOWCPA.decrypt(paramArrayOfbyte, arrayOfByte1);
    byte[] arrayOfByte2 = oWCPADecryptResult.rm;
    int i = oWCPADecryptResult.fail;
    SHA3Digest sHA3Digest = new SHA3Digest(256);
    byte[] arrayOfByte3 = new byte[sHA3Digest.getDigestSize()];
    sHA3Digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    sHA3Digest.doFinal(arrayOfByte3, 0);
    sHA3Digest.update(arrayOfByte1, nTRUParameterSet.owcpaSecretKeyBytes(), nTRUParameterSet.prfKeyBytes());
    sHA3Digest.update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    sHA3Digest.doFinal(arrayOfByte2, 0);
    cmov(arrayOfByte3, arrayOfByte2, (byte)i);
    byte[] arrayOfByte4 = Arrays.copyOfRange(arrayOfByte3, 0, nTRUParameterSet.sharedKeyBytes());
    Arrays.clear(arrayOfByte3);
    return arrayOfByte4;
  }
  
  private void cmov(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte paramByte) {
    paramByte = (byte)((paramByte ^ 0xFFFFFFFF) + 1);
    for (byte b = 0; b < paramArrayOfbyte1.length; b++)
      paramArrayOfbyte1[b] = (byte)(paramArrayOfbyte1[b] ^ paramByte & (paramArrayOfbyte2[b] ^ paramArrayOfbyte1[b])); 
  }
  
  public int getEncapsulationLength() {
    return this.ntruPrivateKey.getParameters().getParameterSet().ntruCiphertextBytes();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\ntru\NTRUKEMExtractor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */