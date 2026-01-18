package org.bouncycastle.crypto.hpke;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public abstract class KEM {
  abstract AsymmetricCipherKeyPair GeneratePrivateKey();
  
  abstract AsymmetricCipherKeyPair DeriveKeyPair(byte[] paramArrayOfbyte);
  
  abstract byte[][] Encap(AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  abstract byte[][] Encap(AsymmetricKeyParameter paramAsymmetricKeyParameter, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair);
  
  abstract byte[][] AuthEncap(AsymmetricKeyParameter paramAsymmetricKeyParameter, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair);
  
  abstract byte[] Decap(byte[] paramArrayOfbyte, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair);
  
  abstract byte[] AuthDecap(byte[] paramArrayOfbyte, AsymmetricCipherKeyPair paramAsymmetricCipherKeyPair, AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  abstract byte[] SerializePublicKey(AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  abstract byte[] SerializePrivateKey(AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  abstract AsymmetricKeyParameter DeserializePublicKey(byte[] paramArrayOfbyte);
  
  abstract AsymmetricCipherKeyPair DeserializePrivateKey(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2);
  
  abstract int getEncryptionSize();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\hpke\KEM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */