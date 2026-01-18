package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;
import org.bouncycastle.pqc.jcajce.interfaces.SPHINCSPlusKey;
import org.bouncycastle.pqc.jcajce.interfaces.SPHINCSPlusPublicKey;

public interface SPHINCSPlusPrivateKey extends PrivateKey, SPHINCSPlusKey {
  SPHINCSPlusPublicKey getPublicKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\SPHINCSPlusPrivateKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */