package org.bouncycastle.pqc.crypto.slhdsa;

import java.io.IOException;
import java.security.SecureRandom;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.params.ParametersWithContext;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.DigestUtils;
import org.bouncycastle.util.Arrays;

public class HashSLHDSASigner implements Signer {
  private byte[] msgPrefix;
  
  private SLHDSAPublicKeyParameters pubKey;
  
  private SLHDSAPrivateKeyParameters privKey;
  
  private SecureRandom random;
  
  private Digest digest;
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    SLHDSAParameters sLHDSAParameters;
    ParametersWithContext parametersWithContext = null;
    if (paramCipherParameters instanceof ParametersWithContext) {
      parametersWithContext = (ParametersWithContext)paramCipherParameters;
      paramCipherParameters = ((ParametersWithContext)paramCipherParameters).getParameters();
      if (parametersWithContext.getContextLength() > 255)
        throw new IllegalArgumentException("context too long"); 
    } 
    if (paramBoolean) {
      this.pubKey = null;
      if (paramCipherParameters instanceof ParametersWithRandom) {
        this.privKey = (SLHDSAPrivateKeyParameters)((ParametersWithRandom)paramCipherParameters).getParameters();
        this.random = ((ParametersWithRandom)paramCipherParameters).getRandom();
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
    initDigest(sLHDSAParameters, parametersWithContext);
  }
  
  private void initDigest(SLHDSAParameters paramSLHDSAParameters, ParametersWithContext paramParametersWithContext) {
    byte[] arrayOfByte;
    this.digest = createDigest(paramSLHDSAParameters);
    ASN1ObjectIdentifier aSN1ObjectIdentifier = DigestUtils.getDigestOid(this.digest.getAlgorithmName());
    try {
      arrayOfByte = aSN1ObjectIdentifier.getEncoded("DER");
    } catch (IOException iOException) {
      throw new IllegalStateException("oid encoding failed: " + iOException.getMessage());
    } 
    byte b = (paramParametersWithContext == null) ? 0 : paramParametersWithContext.getContextLength();
    this.msgPrefix = new byte[2 + b + arrayOfByte.length];
    this.msgPrefix[0] = 1;
    this.msgPrefix[1] = (byte)b;
    if (paramParametersWithContext != null)
      paramParametersWithContext.copyContextTo(this.msgPrefix, 2, b); 
    System.arraycopy(arrayOfByte, 0, this.msgPrefix, 2 + b, arrayOfByte.length);
  }
  
  public void update(byte paramByte) {
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.digest.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public byte[] generateSignature() throws CryptoException, DataLengthException {
    SLHDSAEngine sLHDSAEngine = this.privKey.getParameters().getEngine();
    sLHDSAEngine.init(this.privKey.pk.seed);
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte1, 0);
    byte[] arrayOfByte2 = new byte[sLHDSAEngine.N];
    if (this.random != null) {
      this.random.nextBytes(arrayOfByte2);
    } else {
      System.arraycopy(this.privKey.pk.seed, 0, arrayOfByte2, 0, arrayOfByte2.length);
    } 
    return internalGenerateSignature(this.privKey, this.msgPrefix, arrayOfByte1, arrayOfByte2);
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    return internalVerifySignature(this.pubKey, this.msgPrefix, arrayOfByte, paramArrayOfbyte);
  }
  
  public void reset() {
    this.digest.reset();
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
  
  private static Digest createDigest(SLHDSAParameters paramSLHDSAParameters) {
    String str;
    switch (paramSLHDSAParameters.getType()) {
      case 0:
        str = paramSLHDSAParameters.getName();
        return (Digest)(str.startsWith("sha2") ? ((SLHDSAParameters.sha2_128f == paramSLHDSAParameters || SLHDSAParameters.sha2_128s == paramSLHDSAParameters) ? SHA256Digest.newInstance() : new SHA512Digest()) : ((SLHDSAParameters.shake_128f == paramSLHDSAParameters || SLHDSAParameters.shake_128s == paramSLHDSAParameters) ? new SHAKEDigest(128) : new SHAKEDigest(256)));
      case 1:
        return (Digest)SHA256Digest.newInstance();
      case 2:
        return (Digest)new SHA512Digest();
      case 3:
        return (Digest)new SHAKEDigest(128);
      case 4:
        return (Digest)new SHAKEDigest(256);
    } 
    throw new IllegalArgumentException("unknown parameters type");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\slhdsa\HashSLHDSASigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */