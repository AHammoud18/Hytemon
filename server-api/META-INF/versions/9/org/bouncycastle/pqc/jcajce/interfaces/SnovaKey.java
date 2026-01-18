package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.Key;
import org.bouncycastle.pqc.jcajce.spec.SnovaParameterSpec;

public interface SnovaKey extends Key {
  SnovaParameterSpec getParameterSpec();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\SnovaKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */