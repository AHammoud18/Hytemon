package META-INF.versions.9.org.bouncycastle.pqc.crypto.ntruprime;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.ntruprime.SNTRUPrimeParameters;

public class SNTRUPrimeKeyParameters extends AsymmetricKeyParameter {
  private final SNTRUPrimeParameters params;
  
  public SNTRUPrimeKeyParameters(boolean paramBoolean, SNTRUPrimeParameters paramSNTRUPrimeParameters) {
    super(paramBoolean);
    this.params = paramSNTRUPrimeParameters;
  }
  
  public SNTRUPrimeParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\ntruprime\SNTRUPrimeKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */