package META-INF.versions.9.org.bouncycastle.pqc.crypto.snova;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.snova.SnovaParameters;
import org.bouncycastle.util.Arrays;

public class SnovaPublicKeyParameters extends AsymmetricKeyParameter {
  private final byte[] publicKey;
  
  private final SnovaParameters parameters;
  
  public SnovaPublicKeyParameters(SnovaParameters paramSnovaParameters, byte[] paramArrayOfbyte) {
    super(false);
    this.publicKey = Arrays.clone(paramArrayOfbyte);
    this.parameters = paramSnovaParameters;
  }
  
  public byte[] getPublicKey() {
    return Arrays.clone(this.publicKey);
  }
  
  public byte[] getEncoded() {
    return Arrays.clone(this.publicKey);
  }
  
  public SnovaParameters getParameters() {
    return this.parameters;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\snova\SnovaPublicKeyParameters.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */