package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.Key;
import org.bouncycastle.pqc.jcajce.spec.FalconParameterSpec;

public interface FalconKey extends Key {
  FalconParameterSpec getParameterSpec();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\interfaces\FalconKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */