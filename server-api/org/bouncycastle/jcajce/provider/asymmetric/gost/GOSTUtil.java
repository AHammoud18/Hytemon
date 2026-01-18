package org.bouncycastle.jcajce.provider.asymmetric.gost;

import java.math.BigInteger;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;

class GOSTUtil {
  static String privateKeyToString(String paramString, BigInteger paramBigInteger, GOST3410Parameters paramGOST3410Parameters) {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    BigInteger bigInteger = paramGOST3410Parameters.getA().modPow(paramBigInteger, paramGOST3410Parameters.getP());
    stringBuilder.append(paramString);
    stringBuilder.append(" Private Key [").append(generateKeyFingerprint(bigInteger, paramGOST3410Parameters)).append("]").append(str);
    stringBuilder.append("                  Y: ").append(bigInteger.toString(16)).append(str);
    return stringBuilder.toString();
  }
  
  static String publicKeyToString(String paramString, BigInteger paramBigInteger, GOST3410Parameters paramGOST3410Parameters) {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    stringBuilder.append(paramString);
    stringBuilder.append(" Public Key [").append(generateKeyFingerprint(paramBigInteger, paramGOST3410Parameters)).append("]").append(str);
    stringBuilder.append("                 Y: ").append(paramBigInteger.toString(16)).append(str);
    return stringBuilder.toString();
  }
  
  private static String generateKeyFingerprint(BigInteger paramBigInteger, GOST3410Parameters paramGOST3410Parameters) {
    return (new Fingerprint(Arrays.concatenate(paramBigInteger.toByteArray(), paramGOST3410Parameters.getP().toByteArray(), paramGOST3410Parameters.getA().toByteArray()))).toString();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\gost\GOSTUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */