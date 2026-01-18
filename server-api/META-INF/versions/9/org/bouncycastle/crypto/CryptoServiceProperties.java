package META-INF.versions.9.org.bouncycastle.crypto;

import org.bouncycastle.crypto.CryptoServicePurpose;

public interface CryptoServiceProperties {
  int bitsOfSecurity();
  
  String getServiceName();
  
  CryptoServicePurpose getPurpose();
  
  Object getParams();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\crypto\CryptoServiceProperties.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */