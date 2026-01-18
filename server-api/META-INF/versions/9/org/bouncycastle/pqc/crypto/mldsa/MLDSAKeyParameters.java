package META-INF.versions.9.org.bouncycastle.pqc.crypto.mldsa;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.mldsa.MLDSAParameters;

public class MLDSAKeyParameters extends AsymmetricKeyParameter {
  private final MLDSAParameters params;
  
  public MLDSAKeyParameters(boolean paramBoolean, MLDSAParameters paramMLDSAParameters) {
    super(paramBoolean);
    this.params = paramMLDSAParameters;
  }
  
  public MLDSAParameters getParameters() {
    return this.params;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\mldsa\MLDSAKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */