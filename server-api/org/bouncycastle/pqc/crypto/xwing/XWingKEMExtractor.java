package org.bouncycastle.pqc.crypto.xwing;

import org.bouncycastle.crypto.EncapsulatedSecretExtractor;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMExtractor;
import org.bouncycastle.util.Arrays;

public class XWingKEMExtractor implements EncapsulatedSecretExtractor {
  private static final int MLKEM_CIPHERTEXT_SIZE = 1088;
  
  private final XWingPrivateKeyParameters key;
  
  private final MLKEMExtractor mlkemExtractor;
  
  public XWingKEMExtractor(XWingPrivateKeyParameters paramXWingPrivateKeyParameters) {
    this.key = paramXWingPrivateKeyParameters;
    this.mlkemExtractor = new MLKEMExtractor(this.key.getKyberPrivateKey());
  }
  
  public byte[] extractSecret(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte1 = Arrays.copyOfRange(paramArrayOfbyte, 0, 1088);
    byte[] arrayOfByte2 = Arrays.copyOfRange(paramArrayOfbyte, 1088, paramArrayOfbyte.length);
    byte[] arrayOfByte3 = XWingKEMGenerator.computeSSX(new X25519PublicKeyParameters(arrayOfByte2, 0), this.key.getXDHPrivateKey());
    byte[] arrayOfByte4 = XWingKEMGenerator.computeSharedSecret(this.key.getXDHPublicKey().getEncoded(), this.mlkemExtractor.extractSecret(arrayOfByte1), arrayOfByte2, arrayOfByte3);
    Arrays.clear(arrayOfByte3);
    return arrayOfByte4;
  }
  
  public int getEncapsulationLength() {
    return this.mlkemExtractor.getEncapsulationLength() + 32;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\xwing\XWingKEMExtractor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */