package META-INF.versions.9.org.bouncycastle.pqc.crypto.ntru;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.ntru.NTRUParameters;

public abstract class NTRUKeyParameters extends AsymmetricKeyParameter {
  private final NTRUParameters params;
  
  NTRUKeyParameters(boolean paramBoolean, NTRUParameters paramNTRUParameters) {
    super(paramBoolean);
    this.params = paramNTRUParameters;
  }
  
  public NTRUParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\ntru\NTRUKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */