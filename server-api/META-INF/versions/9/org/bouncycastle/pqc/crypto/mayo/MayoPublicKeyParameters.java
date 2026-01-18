package META-INF.versions.9.org.bouncycastle.pqc.crypto.mayo;

import org.bouncycastle.pqc.crypto.mayo.MayoKeyParameters;
import org.bouncycastle.pqc.crypto.mayo.MayoParameters;
import org.bouncycastle.util.Arrays;

public class MayoPublicKeyParameters extends MayoKeyParameters {
  private final byte[] p;
  
  public MayoPublicKeyParameters(MayoParameters paramMayoParameters, byte[] paramArrayOfbyte) {
    super(false, paramMayoParameters);
    this.p = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getP() {
    return Arrays.clone(this.p);
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.p);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mayo\MayoPublicKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */