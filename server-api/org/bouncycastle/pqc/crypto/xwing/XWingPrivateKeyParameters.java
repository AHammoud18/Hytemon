package org.bouncycastle.pqc.crypto.xwing;

import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.util.Arrays;

public class XWingPrivateKeyParameters extends XWingKeyParameters {
  private final transient byte[] seed;
  
  private final transient MLKEMPrivateKeyParameters kyberPrivateKey;
  
  private final transient X25519PrivateKeyParameters xdhPrivateKey;
  
  private final transient MLKEMPublicKeyParameters kyberPublicKey;
  
  private final transient X25519PublicKeyParameters xdhPublicKey;
  
  public XWingPrivateKeyParameters(byte[] paramArrayOfbyte, MLKEMPrivateKeyParameters paramMLKEMPrivateKeyParameters, X25519PrivateKeyParameters paramX25519PrivateKeyParameters, MLKEMPublicKeyParameters paramMLKEMPublicKeyParameters, X25519PublicKeyParameters paramX25519PublicKeyParameters) {
    super(true);
    this.seed = Arrays.clone(paramArrayOfbyte);
    this.kyberPrivateKey = paramMLKEMPrivateKeyParameters;
    this.xdhPrivateKey = paramX25519PrivateKeyParameters;
    this.kyberPublicKey = paramMLKEMPublicKeyParameters;
    this.xdhPublicKey = paramX25519PublicKeyParameters;
  }
  
  public XWingPrivateKeyParameters(byte[] paramArrayOfbyte) {
    super(true);
    XWingPrivateKeyParameters xWingPrivateKeyParameters = (XWingPrivateKeyParameters)XWingKeyPairGenerator.genKeyPair(paramArrayOfbyte).getPrivate();
    this.seed = xWingPrivateKeyParameters.seed;
    this.kyberPrivateKey = xWingPrivateKeyParameters.kyberPrivateKey;
    this.xdhPrivateKey = xWingPrivateKeyParameters.xdhPrivateKey;
    this.kyberPublicKey = xWingPrivateKeyParameters.kyberPublicKey;
    this.xdhPublicKey = xWingPrivateKeyParameters.xdhPublicKey;
  }
  
  public byte[] getSeed() {
    return Arrays.clone(this.seed);
  }
  
  MLKEMPrivateKeyParameters getKyberPrivateKey() {
    return this.kyberPrivateKey;
  }
  
  MLKEMPublicKeyParameters getKyberPublicKey() {
    return this.kyberPublicKey;
  }
  
  X25519PrivateKeyParameters getXDHPrivateKey() {
    return this.xdhPrivateKey;
  }
  
  X25519PublicKeyParameters getXDHPublicKey() {
    return this.xdhPublicKey;
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.seed);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\xwing\XWingPrivateKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */