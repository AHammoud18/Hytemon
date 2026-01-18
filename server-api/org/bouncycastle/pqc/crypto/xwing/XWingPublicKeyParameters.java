package org.bouncycastle.pqc.crypto.xwing;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters;
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters;
import org.bouncycastle.util.Arrays;

public class XWingPublicKeyParameters extends XWingKeyParameters {
  private final MLKEMPublicKeyParameters kybPub;
  
  private final X25519PublicKeyParameters xdhPub;
  
  XWingPublicKeyParameters(AsymmetricKeyParameter paramAsymmetricKeyParameter1, AsymmetricKeyParameter paramAsymmetricKeyParameter2) {
    super(false);
    this.kybPub = (MLKEMPublicKeyParameters)paramAsymmetricKeyParameter1;
    this.xdhPub = (X25519PublicKeyParameters)paramAsymmetricKeyParameter2;
  }
  
  public XWingPublicKeyParameters(byte[] paramArrayOfbyte) {
    super(false);
    this.kybPub = new MLKEMPublicKeyParameters(MLKEMParameters.ml_kem_768, Arrays.copyOfRange(paramArrayOfbyte, 0, paramArrayOfbyte.length - 32));
    this.xdhPub = new X25519PublicKeyParameters(paramArrayOfbyte, paramArrayOfbyte.length - 32);
  }
  
  MLKEMPublicKeyParameters getKyberPublicKey() {
    return this.kybPub;
  }
  
  X25519PublicKeyParameters getXDHPublicKey() {
    return this.xdhPub;
  }
  
  public byte[] getEncoded() {
    return Arrays.concatenate(this.kybPub.getEncoded(), this.xdhPub.getEncoded());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\xwing\XWingPublicKeyParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */