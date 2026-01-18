package org.bouncycastle.jce.provider;

import java.io.OutputStream;
import java.security.KeyStore;

public class JDKPKCS12StoreParameter implements KeyStore.LoadStoreParameter {
  private OutputStream outputStream;
  
  private KeyStore.ProtectionParameter protectionParameter;
  
  private boolean useDEREncoding;
  
  private boolean overwriteFriendlyName;
  
  public OutputStream getOutputStream() {
    return this.outputStream;
  }
  
  public KeyStore.ProtectionParameter getProtectionParameter() {
    return this.protectionParameter;
  }
  
  public boolean isUseDEREncoding() {
    return this.useDEREncoding;
  }
  
  public boolean isOverwriteFriendlyName() {
    return this.overwriteFriendlyName;
  }
  
  public void setOutputStream(OutputStream paramOutputStream) {
    this.outputStream = paramOutputStream;
  }
  
  public void setPassword(char[] paramArrayOfchar) {
    this.protectionParameter = new KeyStore.PasswordProtection(paramArrayOfchar);
  }
  
  public void setProtectionParameter(KeyStore.ProtectionParameter paramProtectionParameter) {
    this.protectionParameter = paramProtectionParameter;
  }
  
  public void setUseDEREncoding(boolean paramBoolean) {
    this.useDEREncoding = paramBoolean;
  }
  
  public void setOverwriteFriendlyName(boolean paramBoolean) {
    this.overwriteFriendlyName = paramBoolean;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jce\provider\JDKPKCS12StoreParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */