package META-INF.versions.9.org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;
import org.bouncycastle.pqc.jcajce.interfaces.XMSSMTKey;

public interface XMSSMTPrivateKey extends XMSSMTKey, PrivateKey {
  long getIndex();
  
  long getUsagesRemaining();
  
  org.bouncycastle.pqc.jcajce.interfaces.XMSSMTPrivateKey extractKeyShard(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\jcajce\interfaces\XMSSMTPrivateKey.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */