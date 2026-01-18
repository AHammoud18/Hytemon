package org.bouncycastle.cms;

import org.bouncycastle.util.Selector;

public abstract class RecipientId implements Selector {
  public static final int keyTrans = 0;
  
  public static final int kek = 1;
  
  public static final int keyAgree = 2;
  
  public static final int password = 3;
  
  public static final int kem = 4;
  
  private final int type;
  
  protected RecipientId(int paramInt) {
    this.type = paramInt;
  }
  
  public int getType() {
    return this.type;
  }
  
  public abstract Object clone();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\cms\RecipientId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */