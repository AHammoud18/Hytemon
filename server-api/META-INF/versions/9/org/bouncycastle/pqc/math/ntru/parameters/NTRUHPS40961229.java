package META-INF.versions.9.org.bouncycastle.pqc.math.ntru.parameters;

import org.bouncycastle.pqc.math.ntru.HPS4096Polynomial;
import org.bouncycastle.pqc.math.ntru.Polynomial;
import org.bouncycastle.pqc.math.ntru.parameters.NTRUHPSParameterSet;

public class NTRUHPS40961229 extends NTRUHPSParameterSet {
  public NTRUHPS40961229() {
    super(1229, 12, 32, 32, 32);
  }
  
  public Polynomial createPolynomial() {
    return (Polynomial)new HPS4096Polynomial(this);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\math\ntru\parameters\NTRUHPS40961229.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */