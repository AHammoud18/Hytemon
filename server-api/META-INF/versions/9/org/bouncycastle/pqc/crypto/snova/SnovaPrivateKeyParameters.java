package META-INF.versions.9.org.bouncycastle.pqc.crypto.snova;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.snova.SnovaParameters;
import org.bouncycastle.util.Arrays;

public class SnovaPrivateKeyParameters extends AsymmetricKeyParameter {
  private final byte[] privateKey;
  
  private final SnovaParameters parameters;
  
  public SnovaPrivateKeyParameters(SnovaParameters paramSnovaParameters, byte[] paramArrayOfbyte) {
    super(true);
    this.privateKey = Arrays.clone(paramArrayOfbyte);
    this.parameters = paramSnovaParameters;
  }
  
  public byte[] getPrivateKey() {
    return Arrays.clone(this.privateKey);
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.privateKey);
  }
  
  public SnovaParameters getParameters() {
    return this.parameters;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\snova\SnovaPrivateKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */