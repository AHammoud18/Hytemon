package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.Key;
import org.bouncycastle.pqc.jcajce.spec.SnovaParameterSpec;

public interface SnovaKey extends Key {
  SnovaParameterSpec getParameterSpec();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\interfaces\SnovaKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */