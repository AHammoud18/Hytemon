package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;
import org.bouncycastle.pqc.jcajce.interfaces.KyberKey;
import org.bouncycastle.pqc.jcajce.interfaces.KyberPublicKey;

public interface KyberPrivateKey extends PrivateKey, KyberKey {
  KyberPublicKey getPublicKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\KyberPrivateKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */