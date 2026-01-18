package META-INF.versions.9.org.bouncycastle.pqc.crypto.saber;

import org.bouncycastle.pqc.crypto.saber.SABERKeyParameters;
import org.bouncycastle.pqc.crypto.saber.SABERParameters;
import org.bouncycastle.util.Arrays;

public class SABERPrivateKeyParameters extends SABERKeyParameters {
  private byte[] privateKey;
  
  public SABERPrivateKeyParameters(SABERParameters paramSABERParameters, byte[] paramArrayOfbyte) {
    super(true, paramSABERParameters);
    this.privateKey = Arrays.clone(paramArrayOfbyte);
  }
  
  public byte[] getPrivateKey() {
    return Arrays.clone(this.privateKey);
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.privateKey);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\saber\SABERPrivateKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */