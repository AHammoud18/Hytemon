package org.bouncycastle.math.ec;

public abstract class AbstractECLookupTable implements ECLookupTable {
  public ECPoint lookupVar(int paramInt) {
    return lookup(paramInt);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\math\ec\AbstractECLookupTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */