package org.bouncycastle.pqc.crypto.mldsa;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.params.ParametersWithContext;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class MLDSASigner implements Signer {
  private static final byte[] EMPTY_CONTEXT = new byte[0];
  
  private MLDSAPublicKeyParameters pubKey;
  
  private MLDSAPrivateKeyParameters privKey;
  
  private SecureRandom random;
  
  private MLDSAEngine engine;
  
  private SHAKEDigest msgDigest;
  
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
      this.engine.initSign(this.privKey.tr, false, arrayOfByte);
    } else {
      this.pubKey = (MLDSAPublicKeyParameters)paramCipherParameters;
      this.privKey = null;
      this.random = null;
      mLDSAParameters = this.pubKey.getParameters();
      this.engine = mLDSAParameters.getEngine(null);
      this.engine.initVerify(this.pubKey.rho, this.pubKey.t1, false, arrayOfByte);
    } 
    if (mLDSAParameters.isPreHash())
      throw new IllegalArgumentException("\"pure\" ml-dsa must use non pre-hash parameters"); 
    reset();
  }
  
  public void update(byte paramByte) {
    this.msgDigest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.msgDigest.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public byte[] generateMu() throws CryptoException, DataLengthException {
    byte[] arrayOfByte = this.engine.generateMu(this.msgDigest);
    reset();
    return arrayOfByte;
  }
  
  public byte[] generateMuSignature(byte[] paramArrayOfbyte) throws CryptoException, DataLengthException {
    if (paramArrayOfbyte.length != 64)
      throw new DataLengthException("mu value must be 64 bytes"); 
    byte[] arrayOfByte1 = new byte[32];
    if (this.random != null)
      this.random.nextBytes(arrayOfByte1); 
    this.msgDigest.reset();
    byte[] arrayOfByte2 = this.engine.generateSignature(paramArrayOfbyte, this.msgDigest, this.privKey.rho, this.privKey.k, this.privKey.t0, this.privKey.s1, this.privKey.s2, arrayOfByte1);
    reset();
    return arrayOfByte2;
  }
  
  public byte[] generateSignature() throws CryptoException, DataLengthException {
    byte[] arrayOfByte1 = new byte[32];
    if (this.random != null)
      this.random.nextBytes(arrayOfByte1); 
    byte[] arrayOfByte2 = this.engine.generateMu(this.msgDigest);
    byte[] arrayOfByte3 = this.engine.generateSignature(arrayOfByte2, this.msgDigest, this.privKey.rho, this.privKey.k, this.privKey.t0, this.privKey.s1, this.privKey.s2, arrayOfByte1);
    reset();
    return arrayOfByte3;
  }
  
  public boolean verifyMu(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte.length != 64)
      throw new DataLengthException("mu value must be 64 bytes"); 
    boolean bool = this.engine.verifyInternalMu(paramArrayOfbyte);
    reset();
    return bool;
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte) {
    boolean bool = this.engine.verifyInternal(paramArrayOfbyte, paramArrayOfbyte.length, this.msgDigest, this.pubKey.rho, this.pubKey.t1);
    reset();
    return bool;
  }
  
  public boolean verifyMuSignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    if (paramArrayOfbyte1.length != 64)
      throw new DataLengthException("mu value must be 64 bytes"); 
    this.msgDigest.reset();
    boolean bool = this.engine.verifyInternalMuSignature(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte2.length, this.msgDigest, this.pubKey.rho, this.pubKey.t1);
    reset();
    return bool;
  }
  
  public void reset() {
    this.msgDigest = this.engine.getShake256Digest();
  }
  
  protected byte[] internalGenerateSignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    MLDSAEngine mLDSAEngine = this.privKey.getParameters().getEngine(this.random);
    mLDSAEngine.initSign(this.privKey.tr, false, null);
    return mLDSAEngine.signInternal(paramArrayOfbyte1, paramArrayOfbyte1.length, this.privKey.rho, this.privKey.k, this.privKey.t0, this.privKey.s1, this.privKey.s2, paramArrayOfbyte2);
  }
  
  protected boolean internalVerifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    MLDSAEngine mLDSAEngine = this.pubKey.getParameters().getEngine(this.random);
    mLDSAEngine.initVerify(this.pubKey.rho, this.pubKey.t1, false, null);
    SHAKEDigest sHAKEDigest = mLDSAEngine.getShake256Digest();
    sHAKEDigest.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
    return mLDSAEngine.verifyInternal(paramArrayOfbyte2, paramArrayOfbyte2.length, sHAKEDigest, this.pubKey.rho, this.pubKey.t1);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\mldsa\MLDSASigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */