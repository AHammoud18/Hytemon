package META-INF.versions.9.org.bouncycastle.pqc.crypto.crystals.dilithium;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.crystals.dilithium.DilithiumParameters;

public class DilithiumKeyParameters extends AsymmetricKeyParameter {
  private final DilithiumParameters params;
  
  public DilithiumKeyParameters(boolean paramBoolean, DilithiumParameters paramDilithiumParameters) {
    super(paramBoolean);
    this.params = paramDilithiumParameters;
  }
  
  public DilithiumParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\crystals\dilithium\DilithiumKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */