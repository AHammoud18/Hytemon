package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.ParametersWithSBox;

public class GCFBBlockCipher extends StreamBlockCipher {
  private static final byte[] C = new byte[] { 
      105, 0, 114, 34, 100, -55, 4, 35, -115, 58, 
      -37, -106, 70, -23, 42, -60, 24, -2, -84, -108, 
      0, -19, 7, 18, -64, -122, -36, -62, -17, 76, 
      -87, 43 };
  
  private final CFBBlockCipher cfbEngine;
  
  private ParametersWithIV initParams;
  
  private KeyParameter key;
  
  private long counter = 0L;
  
  private boolean forEncryption;
  
  public GCFBBlockCipher(BlockCipher paramBlockCipher) {
    super(paramBlockCipher);
    this.cfbEngine = new CFBBlockCipher(paramBlockCipher, paramBlockCipher.getBlockSize() * 8);
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) throws IllegalArgumentException {
    this.counter = 0L;
    this.cfbEngine.init(paramBoolean, paramCipherParameters);
    byte[] arrayOfByte = null;
    this.forEncryption = paramBoolean;
    if (paramCipherParameters instanceof ParametersWithIV) {
      ParametersWithIV parametersWithIV = (ParametersWithIV)paramCipherParameters;
      paramCipherParameters = parametersWithIV.getParameters();
      arrayOfByte = parametersWithIV.getIV();
    } 
    if (paramCipherParameters instanceof ParametersWithRandom)
      paramCipherParameters = ((ParametersWithRandom)paramCipherParameters).getParameters(); 
    if (paramCipherParameters instanceof ParametersWithSBox)
      paramCipherParameters = ((ParametersWithSBox)paramCipherParameters).getParameters(); 
    this.key = (KeyParameter)paramCipherParameters;
    if (this.key == null && this.initParams != null)
      this.key = (KeyParameter)this.initParams.getParameters(); 
    if (arrayOfByte == null && this.initParams != null) {
      arrayOfByte = this.initParams.getIV();
    } else {
      arrayOfByte = this.cfbEngine.getCurrentIV();
    } 
    this.initParams = new ParametersWithIV((CipherParameters)this.key, arrayOfByte);
  }
  
  public String getAlgorithmName() {
    String str = this.cfbEngine.getAlgorithmName();
    return str.substring(0, str.indexOf('/')) + "/G" + str.substring(str.indexOf('/') + 1);
  }
  
  public int getBlockSize() {
    return this.cfbEngine.getBlockSize();
  }
  
  public int processBlock(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws DataLengthException, IllegalStateException {
    processBytes(paramArrayOfbyte1, paramInt1, this.cfbEngine.getBlockSize(), paramArrayOfbyte2, paramInt2);
    return this.cfbEngine.getBlockSize();
  }
  
  protected byte calculateByte(byte paramByte) {
    if (this.counter > 0L && (this.counter & 0x3FFL) == 0L) {
      BlockCipher blockCipher = this.cfbEngine.getUnderlyingCipher();
      blockCipher.init(false, (CipherParameters)this.key);
      byte[] arrayOfByte1 = new byte[32];
      int i = blockCipher.getBlockSize();
      int j;
      for (j = 0; j < arrayOfByte1.length; j += i)
        blockCipher.processBlock(C, j, arrayOfByte1, j); 
      this.key = new KeyParameter(arrayOfByte1);
      blockCipher.init(true, (CipherParameters)this.key);
      byte[] arrayOfByte2 = this.cfbEngine.getCurrentIV();
      blockCipher.processBlock(arrayOfByte2, 0, arrayOfByte2, 0);
      this.cfbEngine.init(this.forEncryption, (CipherParameters)new ParametersWithIV((CipherParameters)this.key, arrayOfByte2));
    } 
    this.counter++;
    return this.cfbEngine.calculateByte(paramByte);
  }
  
  public void reset() {
    this.counter = 0L;
    if (this.initParams != null) {
      this.key = (KeyParameter)this.initParams.getParameters();
      this.cfbEngine.init(this.forEncryption, (CipherParameters)this.initParams);
    } else {
      this.cfbEngine.reset();
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\modes\GCFBBlockCipher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */