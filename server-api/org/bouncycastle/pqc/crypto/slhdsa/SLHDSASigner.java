package org.bouncycastle.pqc.crypto.slhdsa;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithContext;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.util.Arrays;

public class SLHDSASigner implements MessageSigner {
  private static final byte[] DEFAULT_PREFIX = new byte[] { 0, 0 };
  
  private byte[] msgPrefix;
  
  private SLHDSAPublicKeyParameters pubKey;
  
  private SLHDSAPrivateKeyParameters privKey;
  
  private SecureRandom random;
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    SLHDSAParameters sLHDSAParameters;
    if (paramCipherParameters instanceof ParametersWithContext) {
      ParametersWithContext parametersWithContext = (ParametersWithContext)paramCipherParameters;
      paramCipherParameters = parametersWithContext.getParameters();
      int i = parametersWithContext.getContextLength();
      if (i > 255)
        throw new IllegalArgumentException("context too long"); 
      this.msgPrefix = new byte[2 + i];
      this.msgPrefix[0] = 0;
      this.msgPrefix[1] = (byte)i;
      parametersWithContext.copyContextTo(this.msgPrefix, 2, i);
    } else {
      this.msgPrefix = DEFAULT_PREFIX;
    } 
    if (paramBoolean) {
      this.pubKey = null;
      if (paramCipherParameters instanceof ParametersWithRandom) {
        ParametersWithRandom parametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.privKey = (SLHDSAPrivateKeyParameters)parametersWithRandom.getParameters();
        this.random = parametersWithRandom.getRandom();
      } else {
        this.privKey = (SLHDSAPrivateKeyParameters)paramCipherParameters;
        this.random = null;
      } 
      sLHDSAParameters = this.privKey.getParameters();
    } else {
      this.pubKey = (SLHDSAPublicKeyParameters)paramCipherParameters;
      this.privKey = null;
      this.random = null;
      sLHDSAParameters = this.pubKey.getParameters();
    } 
    if (sLHDSAParameters.isPreHash())
      throw new IllegalArgumentException("\"pure\" slh-dsa must use non pre-hash parameters"); 
  }
  
  public byte[] generateSignature(byte[] paramArrayOfbyte) {
    SLHDSAEngine sLHDSAEngine = this.privKey.getParameters().getEngine();
    sLHDSAEngine.init(this.privKey.pk.seed);
    byte[] arrayOfByte = new byte[sLHDSAEngine.N];
    if (this.random != null) {
      this.random.nextBytes(arrayOfByte);
    } else {
      System.arraycopy(this.privKey.pk.seed, 0, arrayOfByte, 0, arrayOfByte.length);
    } 
    return internalGenerateSignature(this.privKey, this.msgPrefix, paramArrayOfbyte, arrayOfByte);
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    return internalVerifySignature(this.pubKey, this.msgPrefix, paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  protected boolean internalVerifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    return internalVerifySignature(this.pubKey, null, paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  private static boolean internalVerifySignature(SLHDSAPublicKeyParameters paramSLHDSAPublicKeyParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
    SLHDSAEngine sLHDSAEngine = paramSLHDSAPublicKeyParameters.getParameters().getEngine();
    sLHDSAEngine.init(paramSLHDSAPublicKeyParameters.getSeed());
    ADRS aDRS = new ADRS();
    if ((1 + sLHDSAEngine.K * (1 + sLHDSAEngine.A) + sLHDSAEngine.H + sLHDSAEngine.D * sLHDSAEngine.WOTS_LEN) * sLHDSAEngine.N != paramArrayOfbyte3.length)
      return false; 
    SIG sIG = new SIG(sLHDSAEngine.N, sLHDSAEngine.K, sLHDSAEngine.A, sLHDSAEngine.D, sLHDSAEngine.H_PRIME, sLHDSAEngine.WOTS_LEN, paramArrayOfbyte3);
    byte[] arrayOfByte1 = sIG.getR();
    SIG_FORS[] arrayOfSIG_FORS = sIG.getSIG_FORS();
    SIG_XMSS[] arrayOfSIG_XMSS = sIG.getSIG_HT();
    IndexedDigest indexedDigest = sLHDSAEngine.H_msg(arrayOfByte1, paramSLHDSAPublicKeyParameters.getSeed(), paramSLHDSAPublicKeyParameters.getRoot(), paramArrayOfbyte1, paramArrayOfbyte2);
    byte[] arrayOfByte2 = indexedDigest.digest;
    long l = indexedDigest.idx_tree;
    int i = indexedDigest.idx_leaf;
    aDRS.setTypeAndClear(3);
    aDRS.setLayerAddress(0);
    aDRS.setTreeAddress(l);
    aDRS.setKeyPairAddress(i);
    byte[] arrayOfByte3 = (new Fors(sLHDSAEngine)).pkFromSig(arrayOfSIG_FORS, arrayOfByte2, paramSLHDSAPublicKeyParameters.getSeed(), aDRS);
    aDRS.setTypeAndClear(2);
    aDRS.setLayerAddress(0);
    aDRS.setTreeAddress(l);
    aDRS.setKeyPairAddress(i);
    HT hT = new HT(sLHDSAEngine, null, paramSLHDSAPublicKeyParameters.getSeed());
    return hT.verify(arrayOfByte3, arrayOfSIG_XMSS, paramSLHDSAPublicKeyParameters.getSeed(), l, i, paramSLHDSAPublicKeyParameters.getRoot());
  }
  
  protected byte[] internalGenerateSignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    return internalGenerateSignature(this.privKey, null, paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  private static byte[] internalGenerateSignature(SLHDSAPrivateKeyParameters paramSLHDSAPrivateKeyParameters, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
    SLHDSAEngine sLHDSAEngine = paramSLHDSAPrivateKeyParameters.getParameters().getEngine();
    sLHDSAEngine.init(paramSLHDSAPrivateKeyParameters.pk.seed);
    Fors fors = new Fors(sLHDSAEngine);
    byte[] arrayOfByte1 = sLHDSAEngine.PRF_msg(paramSLHDSAPrivateKeyParameters.sk.prf, paramArrayOfbyte3, paramArrayOfbyte1, paramArrayOfbyte2);
    IndexedDigest indexedDigest = sLHDSAEngine.H_msg(arrayOfByte1, paramSLHDSAPrivateKeyParameters.pk.seed, paramSLHDSAPrivateKeyParameters.pk.root, paramArrayOfbyte1, paramArrayOfbyte2);
    byte[] arrayOfByte2 = indexedDigest.digest;
    long l = indexedDigest.idx_tree;
    int i = indexedDigest.idx_leaf;
    ADRS aDRS1 = new ADRS();
    aDRS1.setTypeAndClear(3);
    aDRS1.setTreeAddress(l);
    aDRS1.setKeyPairAddress(i);
    SIG_FORS[] arrayOfSIG_FORS = fors.sign(arrayOfByte2, paramSLHDSAPrivateKeyParameters.sk.seed, paramSLHDSAPrivateKeyParameters.pk.seed, aDRS1);
    aDRS1 = new ADRS();
    aDRS1.setTypeAndClear(3);
    aDRS1.setTreeAddress(l);
    aDRS1.setKeyPairAddress(i);
    byte[] arrayOfByte3 = fors.pkFromSig(arrayOfSIG_FORS, arrayOfByte2, paramSLHDSAPrivateKeyParameters.pk.seed, aDRS1);
    ADRS aDRS2 = new ADRS();
    aDRS2.setTypeAndClear(2);
    HT hT = new HT(sLHDSAEngine, paramSLHDSAPrivateKeyParameters.getSeed(), paramSLHDSAPrivateKeyParameters.getPublicSeed());
    byte[] arrayOfByte4 = hT.sign(arrayOfByte3, l, i);
    byte[][] arrayOfByte = new byte[arrayOfSIG_FORS.length + 2][];
    arrayOfByte[0] = arrayOfByte1;
    for (byte b = 0; b != arrayOfSIG_FORS.length; b++)
      arrayOfByte[1 + b] = Arrays.concatenate((arrayOfSIG_FORS[b]).sk, Arrays.concatenate((arrayOfSIG_FORS[b]).authPath)); 
    arrayOfByte[arrayOfByte.length - 1] = arrayOfByte4;
    return Arrays.concatenate(arrayOfByte);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\SLHDSASigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */