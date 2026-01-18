package org.bouncycastle.math.ec.endo;

import java.math.BigInteger;

public interface GLVEndomorphism extends ECEndomorphism {
  BigInteger[] decomposeScalar(BigInteger paramBigInteger);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\math\ec\endo\GLVEndomorphism.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */