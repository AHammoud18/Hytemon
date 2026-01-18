package META-INF.versions.9.org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CryptoServicePurpose;

class Utils {
  static CryptoServicePurpose getPurpose(boolean paramBoolean) {
    return paramBoolean ? CryptoServicePurpose.ENCRYPTION : CryptoServicePurpose.DECRYPTION;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\crypto\engines\Utils.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */