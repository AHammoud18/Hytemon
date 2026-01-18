package org.bouncycastle.crypto;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class AsymmetricCipherKeyPair {
  private AsymmetricKeyParameter publicParam;
  
  private AsymmetricKeyParameter privateParam;
  
  public AsymmetricCipherKeyPair(AsymmetricKeyParameter paramAsymmetricKeyParameter1, AsymmetricKeyParameter paramAsymmetricKeyParameter2) {
    this.publicParam = paramAsymmetricKeyParameter1;
    this.privateParam = paramAsymmetricKeyParameter2;
  }
  
  @Deprecated
  public AsymmetricCipherKeyPair(CipherParameters paramCipherParameters1, CipherParameters paramCipherParameters2) {
    this.publicParam = (AsymmetricKeyParameter)paramCipherParameters1;
    this.privateParam = (AsymmetricKeyParameter)paramCipherParameters2;
  }
  
  public AsymmetricKeyParameter getPublic() {
    return this.publicParam;
  }
  
  public AsymmetricKeyParameter getPrivate() {
    return this.privateParam;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\AsymmetricCipherKeyPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */