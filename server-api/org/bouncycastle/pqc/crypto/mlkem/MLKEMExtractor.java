package org.bouncycastle.pqc.crypto.mlkem;

import org.bouncycastle.crypto.EncapsulatedSecretExtractor;

public class MLKEMExtractor implements EncapsulatedSecretExtractor {
  private final MLKEMPrivateKeyParameters privateKey;
  
  private final MLKEMEngine engine;
  
  public MLKEMExtractor(MLKEMPrivateKeyParameters paramMLKEMPrivateKeyParameters) {
    if (paramMLKEMPrivateKeyParameters == null)
      throw new NullPointerException("'privateKey' cannot be null"); 
    this.privateKey = paramMLKEMPrivateKeyParameters;
    this.engine = paramMLKEMPrivateKeyParameters.getParameters().getEngine();
  }
  
  public byte[] extractSecret(byte[] paramArrayOfbyte) {
    return this.engine.kemDecrypt(this.privateKey, paramArrayOfbyte);
  }
  
  public int getEncapsulationLength() {
    return this.engine.getCryptoCipherTextBytes();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mlkem\MLKEMExtractor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */