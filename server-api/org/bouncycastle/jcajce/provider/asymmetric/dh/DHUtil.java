package org.bouncycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;

class DHUtil {
  static String privateKeyToString(String paramString, BigInteger paramBigInteger, DHParameters paramDHParameters) {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    BigInteger bigInteger = paramDHParameters.getG().modPow(paramBigInteger, paramDHParameters.getP());
    stringBuilder.append(paramString);
    stringBuilder.append(" Private Key [").append(generateKeyFingerprint(bigInteger, paramDHParameters)).append("]").append(str);
    stringBuilder.append("              Y: ").append(bigInteger.toString(16)).append(str);
    return stringBuilder.toString();
  }
  
  static String publicKeyToString(String paramString, BigInteger paramBigInteger, DHParameters paramDHParameters) {
    StringBuilder stringBuilder = new StringBuilder();
    String str = Strings.lineSeparator();
    stringBuilder.append(paramString);
    stringBuilder.append(" Public Key [").append(generateKeyFingerprint(paramBigInteger, paramDHParameters)).append("]").append(str);
    stringBuilder.append("             Y: ").append(paramBigInteger.toString(16)).append(str);
    return stringBuilder.toString();
  }
  
  private static String generateKeyFingerprint(BigInteger paramBigInteger, DHParameters paramDHParameters) {
    return (new Fingerprint(Arrays.concatenate(paramBigInteger.toByteArray(), paramDHParameters.getP().toByteArray(), paramDHParameters.getG().toByteArray()))).toString();
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetric\dh\DHUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */