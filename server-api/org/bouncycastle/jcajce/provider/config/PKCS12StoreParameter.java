package org.bouncycastle.jcajce.provider.config;

import java.io.OutputStream;
import java.security.KeyStore;
import org.bouncycastle.jcajce.PKCS12StoreParameter;

public class PKCS12StoreParameter extends PKCS12StoreParameter {
  public PKCS12StoreParameter(OutputStream paramOutputStream, char[] paramArrayOfchar) {
    super(paramOutputStream, paramArrayOfchar, false);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, KeyStore.ProtectionParameter paramProtectionParameter) {
    super(paramOutputStream, paramProtectionParameter, false);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, char[] paramArrayOfchar, boolean paramBoolean) {
    super(paramOutputStream, new KeyStore.PasswordProtection(paramArrayOfchar), paramBoolean);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, char[] paramArrayOfchar, boolean paramBoolean1, boolean paramBoolean2) {
    super(paramOutputStream, new KeyStore.PasswordProtection(paramArrayOfchar), paramBoolean1, paramBoolean2);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, KeyStore.ProtectionParameter paramProtectionParameter, boolean paramBoolean) {
    super(paramOutputStream, paramProtectionParameter, paramBoolean);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\config\PKCS12StoreParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */