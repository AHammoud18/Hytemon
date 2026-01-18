package META-INF.versions.9.org.bouncycastle.crypto;

public interface Digest {
  String getAlgorithmName();
  
  int getDigestSize();
  
  void update(byte paramByte);
  
  void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  int doFinal(byte[] paramArrayOfbyte, int paramInt);
  
  void reset();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\crypto\Digest.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */