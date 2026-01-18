package META-INF.versions.9.org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.hqc.HQCParameters;

public class HQCKeyParameters extends AsymmetricKeyParameter {
  private final HQCParameters params;
  
  public HQCKeyParameters(boolean paramBoolean, HQCParameters paramHQCParameters) {
    super(paramBoolean);
    this.params = paramHQCParameters;
  }
  
  public HQCParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\hqc\HQCKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */