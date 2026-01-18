package org.bouncycastle.crypto.agreement.ecjpake;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.math.ec.ECCurve;

public class ECJPAKECurves {
  public static final ECJPAKECurve NIST_P256 = getCurve("P-256");
  
  public static final ECJPAKECurve NIST_P384 = getCurve("P-384");
  
  public static final ECJPAKECurve NIST_P521 = getCurve("P-521");
  
  private static ECJPAKECurve getCurve(String paramString) {
    X9ECParameters x9ECParameters = CustomNamedCurves.getByName(paramString);
    return new ECJPAKECurve((ECCurve.AbstractFp)x9ECParameters.getCurve(), x9ECParameters.getG());
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\agreement\ecjpake\ECJPAKECurves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */