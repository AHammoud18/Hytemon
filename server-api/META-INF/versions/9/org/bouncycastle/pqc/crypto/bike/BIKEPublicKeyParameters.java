package META-INF.versions.9.org.bouncycastle.pqc.crypto.bike;

import org.bouncycastle.pqc.crypto.bike.BIKEKeyParameters;
import org.bouncycastle.pqc.crypto.bike.BIKEParameters;
import org.bouncycastle.util.Arrays;

public class BIKEPublicKeyParameters extends BIKEKeyParameters {
  byte[] publicKey;
  
  public BIKEPublicKeyParameters(BIKEParameters paramBIKEParameters, byte[] paramArrayOfbyte) {
    super(false, paramBIKEParameters);
    this.publicKey = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.publicKey);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\bike\BIKEPublicKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */