package org.bouncycastle.jcajce.interfaces;

import java.security.Key;
import org.bouncycastle.jcajce.spec.MLDSAParameterSpec;

public interface MLDSAKey extends Key {
  MLDSAParameterSpec getParameterSpec();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\interfaces\MLDSAKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */