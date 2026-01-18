package org.bouncycastle.pqc.crypto.mldsa;

import java.io.IOException;
import java.security.SecureRandom;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.params.ParametersWithContext;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.DigestUtils;

public class HashMLDSASigner implements Signer {
  private static final byte[] EMPTY_CONTEXT = new byte[0];
  
  private MLDSAPublicKeyParameters pubKey;
  
  private MLDSAPrivateKeyParameters privKey;
  
  private SecureRandom random;
  
  private MLDSAEngine engine;
  
  private Digest digest;
  
  private byte[] digestOIDEncoding;
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    MLDSAParameters mLDSAParameters;
    byte[] arrayOfByte = EMPTY_CONTEXT;
    if (paramCipherParameters instanceof ParametersWithContext) {
      ParametersWithContext parametersWithContext = (ParametersWithContext)paramCipherParameters;
      arrayOfByte = parametersWithContext.getContext();
      paramCipherParameters = parametersWithContext.getParameters();
      if (arrayOfByte.length > 255)
        throw new IllegalArgumentException("context too long"); 
    } 
    if (paramBoolean) {
      this.pubKey = null;
      if (paramCipherParameters instanceof ParametersWithRandom) {
        ParametersWithRandom parametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.privKey = (MLDSAPrivateKeyParameters)parametersWithRandom.getParameters();
        this.random = parametersWithRandom.getRandom();
      } else {
        this.privKey = (MLDSAPrivateKeyParameters)paramCipherParameters;
        this.random = null;
      } 
      mLDSAParameters = this.privKey.getParameters();
      this.engine = mLDSAParameters.getEngine(this.random);
      this.engine.initSign(this.privKey.tr, true, arrayOfByte);
    } else {
      this.pubKey = (MLDSAPublicKeyParameters)paramCipherParameters;
      this.privKey = null;
      this.random = null;
      mLDSAParameters = this.pubKey.getParameters();
      this.engine = mLDSAParameters.getEngine(null);
      this.engine.initVerify(this.pubKey.rho, this.pubKey.t1, true, arrayOfByte);
    } 
    initDigest(mLDSAParameters);
  }
  
  private void initDigest(MLDSAParameters paramMLDSAParameters) {
    this.digest = createDigest(paramMLDSAParameters);
    ASN1ObjectIdentifier aSN1ObjectIdentifier = DigestUtils.getDigestOid(this.digest.getAlgorithmName());
    try {
      this.digestOIDEncoding = aSN1ObjectIdentifier.getEncoded("DER");
    } catch (IOException iOException) {
      throw new IllegalStateException("oid encoding failed: " + iOException.getMessage());
    } 
  }
  
  public void update(byte paramByte) {
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.digest.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public byte[] generateSignature() throws CryptoException, DataLengthException {
    SHAKEDigest sHAKEDigest = finishPreHash();
    byte[] arrayOfByte1 = new byte[32];
    if (this.random != null)
      this.random.nextBytes(arrayOfByte1); 
    byte[] arrayOfByte2 = this.engine.generateMu(sHAKEDigest);
    return this.engine.generateSignature(arrayOfByte2, sHAKEDigest, this.privKey.rho, this.privKey.k, this.privKey.t0, this.privKey.s1, this.privKey.s2, arrayOfByte1);
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte) {
    SHAKEDigest sHAKEDigest = finishPreHash();
    return this.engine.verifyInternal(paramArrayOfbyte, paramArrayOfbyte.length, sHAKEDigest, this.pubKey.rho, this.pubKey.t1);
  }
  
  public void reset() {
    this.digest.reset();
  }
  
  private SHAKEDigest finishPreHash() {
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    SHAKEDigest sHAKEDigest = this.engine.getShake256Digest();
    sHAKEDigest.update(this.digestOIDEncoding, 0, this.digestOIDEncoding.length);
    sHAKEDigest.update(arrayOfByte, 0, arrayOfByte.length);
    return sHAKEDigest;
  }
  
  private static Digest createDigest(MLDSAParameters paramMLDSAParameters) {
    switch (paramMLDSAParameters.getType()) {
      case 0:
      case 1:
        return (Digest)new SHA512Digest();
    } 
    throw new IllegalArgumentException("unknown parameters type");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\HashMLDSASigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */