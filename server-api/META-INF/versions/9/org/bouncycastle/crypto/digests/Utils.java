package META-INF.versions.9.org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.CryptoServiceProperties;
import org.bouncycastle.crypto.CryptoServicePurpose;
import org.bouncycastle.crypto.Digest;

class Utils {
  static CryptoServiceProperties getDefaultProperties(Digest paramDigest, CryptoServicePurpose paramCryptoServicePurpose) {
    return (CryptoServiceProperties)new DefaultProperties(paramDigest.getDigestSize() * 4, paramDigest.getAlgorithmName(), paramCryptoServicePurpose);
  }
  
  static CryptoServiceProperties getDefaultProperties(Digest paramDigest, int paramInt, CryptoServicePurpose paramCryptoServicePurpose) {
    return (CryptoServiceProperties)new DefaultPropertiesWithPRF(paramDigest.getDigestSize() * 4, paramInt, paramDigest.getAlgorithmName(), paramCryptoServicePurpose);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\crypto\digests\Utils.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */