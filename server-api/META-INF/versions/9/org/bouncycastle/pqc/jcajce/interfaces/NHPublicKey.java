package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PublicKey;
import org.bouncycastle.pqc.jcajce.interfaces.NHKey;

public interface NHPublicKey extends NHKey, PublicKey {
  byte[] getPublicData();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\NHPublicKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */