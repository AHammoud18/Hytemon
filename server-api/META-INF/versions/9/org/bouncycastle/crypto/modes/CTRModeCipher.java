package META-INF.versions.9.org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.MultiBlockCipher;
import org.bouncycastle.crypto.SkippingStreamCipher;

public interface CTRModeCipher extends MultiBlockCipher, SkippingStreamCipher {
  BlockCipher getUnderlyingCipher();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\crypto\modes\CTRModeCipher.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */