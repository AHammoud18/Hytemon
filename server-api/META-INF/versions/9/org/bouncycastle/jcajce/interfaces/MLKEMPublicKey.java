package META-INF.versions.9.org.bouncycastle.jcajce.interfaces;

import java.security.PublicKey;
import org.bouncycastle.jcajce.interfaces.MLKEMKey;

public interface MLKEMPublicKey extends PublicKey, MLKEMKey {
  byte[] getPublicData();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\jcajce\interfaces\MLKEMPublicKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */