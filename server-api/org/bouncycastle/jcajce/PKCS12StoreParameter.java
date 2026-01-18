package org.bouncycastle.jcajce;

import java.io.OutputStream;
import java.security.KeyStore;

public class PKCS12StoreParameter implements KeyStore.LoadStoreParameter {
  private final OutputStream out;
  
  private final KeyStore.ProtectionParameter protectionParameter;
  
  private final boolean forDEREncoding;
  
  private final boolean overwriteFriendlyName;
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, char[] paramArrayOfchar) {
    this(paramOutputStream, paramArrayOfchar, false);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, KeyStore.ProtectionParameter paramProtectionParameter) {
    this(paramOutputStream, paramProtectionParameter, false, true);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, char[] paramArrayOfchar, boolean paramBoolean) {
    this(paramOutputStream, new KeyStore.PasswordProtection(paramArrayOfchar), paramBoolean, true);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, KeyStore.ProtectionParameter paramProtectionParameter, boolean paramBoolean) {
    this(paramOutputStream, paramProtectionParameter, paramBoolean, true);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, char[] paramArrayOfchar, boolean paramBoolean1, boolean paramBoolean2) {
    this(paramOutputStream, new KeyStore.PasswordProtection(paramArrayOfchar), paramBoolean1, paramBoolean2);
  }
  
  public PKCS12StoreParameter(OutputStream paramOutputStream, KeyStore.ProtectionParameter paramProtectionParameter, boolean paramBoolean1, boolean paramBoolean2) {
    this.out = paramOutputStream;
    this.protectionParameter = paramProtectionParameter;
    this.forDEREncoding = paramBoolean1;
    this.overwriteFriendlyName = paramBoolean2;
  }
  
  public OutputStream getOutputStream() {
    return this.out;
  }
  
  public KeyStore.ProtectionParameter getProtectionParameter() {
    return this.protectionParameter;
  }
  
  public boolean isForDEREncoding() {
    return this.forDEREncoding;
  }
  
  public boolean isOverwriteFriendlyName() {
    return this.overwriteFriendlyName;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\PKCS12StoreParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */