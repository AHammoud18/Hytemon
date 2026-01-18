package META-INF.versions.9.org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.pqc.crypto.hqc.HQCKeyParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCParameters;
import org.bouncycastle.util.Arrays;

public class HQCPrivateKeyParameters extends HQCKeyParameters {
  private final byte[] sk;
  
  public HQCPrivateKeyParameters(HQCParameters paramHQCParameters, byte[] paramArrayOfbyte) {
    super(true, paramHQCParameters);
    this.sk = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getPrivateKey() {
    return Arrays.clone(this.sk);
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.sk);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\hqc\HQCPrivateKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */