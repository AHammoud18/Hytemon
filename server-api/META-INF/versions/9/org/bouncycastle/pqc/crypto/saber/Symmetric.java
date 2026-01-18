package META-INF.versions.9.org.bouncycastle.pqc.crypto.saber;

abstract class Symmetric {
  abstract void hash_h(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt);
  
  abstract void hash_g(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2);
  
  abstract void prf(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\9\org\bouncycastle\pqc\crypto\saber\Symmetric.class
 * Java compiler version: 9 (53.0)
 * JD-Core Version:       1.1.3
 */