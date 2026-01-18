package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.digests.ISAPDigest;
import org.bouncycastle.util.Longs;

public class AsconPermutationFriend {
  public static AsconPermutation getAsconPermutation(ISAPDigest.Friend paramFriend) {
    if (null == paramFriend)
      throw new NullPointerException("This method is only for use by ISAPDigest or Ascon Digest"); 
    return new AsconPermutation();
  }
  
  public static class AsconPermutation {
    public long x0;
    
    public long x1;
    
    public long x2;
    
    public long x3;
    
    public long x4;
    
    public void round(long param1Long) {
      this.x2 ^= param1Long;
      long l1 = this.x0 ^ this.x4;
      long l2 = this.x1 ^ this.x2;
      long l3 = this.x1 | this.x2;
      long l4 = this.x3 ^ l3 ^ this.x0 ^ this.x1 & l1;
      long l5 = l1 ^ (l3 | this.x3) ^ this.x1 & this.x2 & this.x3;
      long l6 = l2 ^ this.x4 & (this.x3 ^ 0xFFFFFFFFFFFFFFFFL);
      long l7 = (this.x0 | this.x3 ^ this.x4) ^ l2;
      long l8 = this.x3 ^ (this.x1 | this.x4) ^ this.x0 & this.x1;
      this.x0 = l4 ^ Longs.rotateRight(l4, 19) ^ Longs.rotateRight(l4, 28);
      this.x1 = l5 ^ Longs.rotateRight(l5, 39) ^ Longs.rotateRight(l5, 61);
      this.x2 = l6 ^ Longs.rotateRight(l6, 1) ^ Longs.rotateRight(l6, 6) ^ 0xFFFFFFFFFFFFFFFFL;
      this.x3 = l7 ^ Longs.rotateRight(l7, 10) ^ Longs.rotateRight(l7, 17);
      this.x4 = l8 ^ Longs.rotateRight(l8, 7) ^ Longs.rotateRight(l8, 41);
    }
    
    public void p(int param1Int) {
      if (param1Int == 12) {
        round(240L);
        round(225L);
        round(210L);
        round(195L);
      } 
      if (param1Int >= 8) {
        round(180L);
        round(165L);
      } 
      round(150L);
      round(135L);
      round(120L);
      round(105L);
      round(90L);
      round(75L);
    }
    
    public void set(long param1Long1, long param1Long2, long param1Long3, long param1Long4, long param1Long5) {
      this.x0 = param1Long1;
      this.x1 = param1Long2;
      this.x2 = param1Long3;
      this.x3 = param1Long4;
      this.x4 = param1Long5;
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\engines\AsconPermutationFriend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */