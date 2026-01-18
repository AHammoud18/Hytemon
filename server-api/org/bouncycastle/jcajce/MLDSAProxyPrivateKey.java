package org.bouncycastle.jcajce;

import java.security.PublicKey;
import org.bouncycastle.jcajce.interfaces.MLDSAPrivateKey;
import org.bouncycastle.jcajce.interfaces.MLDSAPublicKey;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;

public class MLDSAProxyPrivateKey implements MLDSAPrivateKey {
  private final MLDSAPublicKey publicKey;
  
  public MLDSAProxyPrivateKey(PublicKey paramPublicKey) {
    if (!(paramPublicKey instanceof MLDSAPublicKey))
      throw new IllegalArgumentException("public key must be an ML-DSA public key"); 
    this.publicKey = (MLDSAPublicKey)paramPublicKey;
  }
  
  public MLDSAPublicKey getPublicKey() {
    return this.publicKey;
  }
  
  public String getAlgorithm() {
    return this.publicKey.getAlgorithm();
  }
  
  public String getFormat() {
    return null;
  }
  
  public byte[] getEncoded() {
    return new byte[0];
  }
  
  public MLDSAParameterSpec getParameterSpec() {
    return this.publicKey.getParameterSpec();
  }
  
  public byte[] getPrivateData() {
    return new byte[0];
  }
  
  public byte[] getSeed() {
    return new byte[0];
  }
  
  public MLDSAPrivateKey getPrivateKey(boolean paramBoolean) {
    return null;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\MLDSAProxyPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */