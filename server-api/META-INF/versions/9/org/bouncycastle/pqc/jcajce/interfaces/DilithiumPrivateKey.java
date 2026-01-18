package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;
import org.bouncycastle.pqc.jcajce.interfaces.DilithiumKey;
import org.bouncycastle.pqc.jcajce.interfaces.DilithiumPublicKey;

public interface DilithiumPrivateKey extends PrivateKey, DilithiumKey {
  DilithiumPublicKey getPublicKey();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\DilithiumPrivateKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */