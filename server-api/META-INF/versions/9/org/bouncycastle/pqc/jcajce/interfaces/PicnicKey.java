package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.Key;
import org.bouncycastle.pqc.jcajce.spec.PicnicParameterSpec;

public interface PicnicKey extends Key {
  PicnicParameterSpec getParameterSpec();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\PicnicKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */