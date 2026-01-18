package org.bouncycastle.jcajce.provider.asymmetric.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithContext;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.spec.ContextParameterSpec;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.SpecUtil;
import org.bouncycastle.util.Exceptions;

public abstract class BaseDeterministicOrRandomSignature extends SignatureSpi {
  private final JcaJceHelper helper = (JcaJceHelper)new BCJcaJceHelper();
  
  private final AlgorithmParameterSpec originalSpec = (AlgorithmParameterSpec)ContextParameterSpec.EMPTY_CONTEXT_SPEC;
  
  protected AlgorithmParameters engineParams;
  
  protected ContextParameterSpec paramSpec;
  
  protected AsymmetricKeyParameter keyParams;
  
  protected boolean isInitState = true;
  
  protected BaseDeterministicOrRandomSignature(String paramString) {}
  
  protected final void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException {
    verifyInit(paramPublicKey);
    this.paramSpec = ContextParameterSpec.EMPTY_CONTEXT_SPEC;
    this.isInitState = true;
    reInit();
  }
  
  protected abstract void verifyInit(PublicKey paramPublicKey) throws InvalidKeyException;
  
  protected final void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException {
    signInit(paramPrivateKey, null);
    this.paramSpec = ContextParameterSpec.EMPTY_CONTEXT_SPEC;
    this.isInitState = true;
    reInit();
  }
  
  protected final void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
    signInit(paramPrivateKey, paramSecureRandom);
    this.paramSpec = ContextParameterSpec.EMPTY_CONTEXT_SPEC;
    this.isInitState = true;
    reInit();
  }
  
  protected abstract void signInit(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException;
  
  protected final void engineUpdate(byte paramByte) throws SignatureException {
    this.isInitState = false;
    updateEngine(paramByte);
  }
  
  protected abstract void updateEngine(byte paramByte) throws SignatureException;
  
  protected final void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
    this.isInitState = false;
    updateEngine(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  protected abstract void updateEngine(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException;
  
  protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramAlgorithmParameterSpec == null)
      if (this.originalSpec != null) {
        paramAlgorithmParameterSpec = this.originalSpec;
      } else {
        return;
      }  
    if (!this.isInitState)
      throw new ProviderException("cannot call setParameter in the middle of update"); 
    if (paramAlgorithmParameterSpec instanceof ContextParameterSpec) {
      this.paramSpec = (ContextParameterSpec)paramAlgorithmParameterSpec;
      reInit();
    } else {
      byte[] arrayOfByte = SpecUtil.getContextFrom(paramAlgorithmParameterSpec);
      if (arrayOfByte != null) {
        this.paramSpec = new ContextParameterSpec(arrayOfByte);
        reInit();
      } else {
        throw new InvalidAlgorithmParameterException("unknown AlgorithmParameterSpec in signature");
      } 
    } 
  }
  
  private void reInit() {
    ParametersWithContext parametersWithContext;
    AsymmetricKeyParameter asymmetricKeyParameter = this.keyParams;
    if (this.keyParams.isPrivate()) {
      ParametersWithRandom parametersWithRandom;
      if (this.appRandom != null)
        parametersWithRandom = new ParametersWithRandom((CipherParameters)asymmetricKeyParameter, this.appRandom); 
      if (this.paramSpec != null)
        parametersWithContext = new ParametersWithContext((CipherParameters)parametersWithRandom, this.paramSpec.getContext()); 
      reInitialize(true, (CipherParameters)parametersWithContext);
    } else {
      if (this.paramSpec != null)
        parametersWithContext = new ParametersWithContext((CipherParameters)parametersWithContext, this.paramSpec.getContext()); 
      reInitialize(false, (CipherParameters)parametersWithContext);
    } 
  }
  
  protected abstract void reInitialize(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  protected final AlgorithmParameters engineGetParameters() {
    if (this.engineParams == null && this.paramSpec != null && this.paramSpec != ContextParameterSpec.EMPTY_CONTEXT_SPEC)
      try {
        this.engineParams = this.helper.createAlgorithmParameters("CONTEXT");
        this.engineParams.init((AlgorithmParameterSpec)this.paramSpec);
      } catch (Exception exception) {
        throw Exceptions.illegalStateException(exception.toString(), exception);
      }  
    return this.engineParams;
  }
  
  protected final void engineSetParameter(String paramString, Object paramObject) {
    throw new UnsupportedOperationException("SetParameter unsupported");
  }
  
  protected final Object engineGetParameter(String paramString) {
    throw new UnsupportedOperationException("GetParameter unsupported");
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\jcajce\provider\asymmetri\\util\BaseDeterministicOrRandomSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */