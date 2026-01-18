package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.Key;
import org.bouncycastle.pqc.jcajce.spec.CMCEParameterSpec;

public interface CMCEKey extends Key {
  CMCEParameterSpec getParameterSpec();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\CMCEKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */