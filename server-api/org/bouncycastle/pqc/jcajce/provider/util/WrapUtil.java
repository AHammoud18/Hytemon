package org.bouncycastle.pqc.jcajce.provider.util;

import java.security.InvalidKeyException;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.ARIAEngine;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.RFC3394WrapEngine;
import org.bouncycastle.crypto.engines.RFC5649WrapEngine;
import org.bouncycastle.crypto.engines.SEEDEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.bouncycastle.util.Arrays;

public class WrapUtil {
  public static Wrapper getKeyWrapper(KTSParameterSpec paramKTSParameterSpec, byte[] paramArrayOfbyte) throws InvalidKeyException {
    Wrapper wrapper = getWrapper(paramKTSParameterSpec.getKeyAlgorithmName());
    AlgorithmIdentifier algorithmIdentifier = paramKTSParameterSpec.getKdfAlgorithm();
    if (algorithmIdentifier == null) {
      wrapper.init(true, (CipherParameters)new KeyParameter(Arrays.copyOfRange(paramArrayOfbyte, 0, (paramKTSParameterSpec.getKeySize() + 7) / 8)));
    } else {
      wrapper.init(true, (CipherParameters)new KeyParameter(makeKeyBytes(paramKTSParameterSpec, paramArrayOfbyte)));
    } 
    return wrapper;
  }
  
  public static Wrapper getKeyUnwrapper(KTSParameterSpec paramKTSParameterSpec, byte[] paramArrayOfbyte) throws InvalidKeyException {
    Wrapper wrapper = getWrapper(paramKTSParameterSpec.getKeyAlgorithmName());
    AlgorithmIdentifier algorithmIdentifier = paramKTSParameterSpec.getKdfAlgorithm();
    if (algorithmIdentifier == null) {
      wrapper.init(false, (CipherParameters)new KeyParameter(paramArrayOfbyte, 0, (paramKTSParameterSpec.getKeySize() + 7) / 8));
    } else {
      wrapper.init(false, (CipherParameters)new KeyParameter(makeKeyBytes(paramKTSParameterSpec, paramArrayOfbyte)));
    } 
    return wrapper;
  }
  
  public static Wrapper getWrapper(String paramString) {
    RFC5649WrapEngine rFC5649WrapEngine;
    if (paramString.equalsIgnoreCase("AESWRAP") || paramString.equalsIgnoreCase("AES")) {
      RFC3394WrapEngine rFC3394WrapEngine = new RFC3394WrapEngine((BlockCipher)AESEngine.newInstance());
    } else if (paramString.equalsIgnoreCase("ARIA")) {
      RFC3394WrapEngine rFC3394WrapEngine = new RFC3394WrapEngine((BlockCipher)new ARIAEngine());
    } else if (paramString.equalsIgnoreCase("Camellia")) {
      RFC3394WrapEngine rFC3394WrapEngine = new RFC3394WrapEngine((BlockCipher)new CamelliaEngine());
    } else if (paramString.equalsIgnoreCase("SEED")) {
      RFC3394WrapEngine rFC3394WrapEngine = new RFC3394WrapEngine((BlockCipher)new SEEDEngine());
    } else if (paramString.equalsIgnoreCase("AES-KWP")) {
      rFC5649WrapEngine = new RFC5649WrapEngine((BlockCipher)new AESEngine());
    } else if (paramString.equalsIgnoreCase("Camellia-KWP")) {
      rFC5649WrapEngine = new RFC5649WrapEngine((BlockCipher)new CamelliaEngine());
    } else if (paramString.equalsIgnoreCase("ARIA-KWP")) {
      rFC5649WrapEngine = new RFC5649WrapEngine((BlockCipher)new ARIAEngine());
    } else {
      throw new UnsupportedOperationException("unknown key algorithm: " + paramString);
    } 
    return (Wrapper)rFC5649WrapEngine;
  }
  
  public static byte[] trimSecret(String paramString, byte[] paramArrayOfbyte) {
    return paramString.equals("SEED") ? Arrays.copyOfRange(paramArrayOfbyte, 0, 16) : paramArrayOfbyte;
  }
  
  private static byte[] makeKeyBytes(KTSParameterSpec paramKTSParameterSpec, byte[] paramArrayOfbyte) throws InvalidKeyException {
    try {
      return KdfUtil.makeKeyBytes(paramKTSParameterSpec.getKdfAlgorithm(), paramArrayOfbyte, paramKTSParameterSpec.getOtherInfo(), paramKTSParameterSpec.getKeySize());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new InvalidKeyException(illegalArgumentException.getMessage());
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\jcajce\provide\\util\WrapUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */