package org.bouncycastle.crypto.hpke;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Bytes;
import org.bouncycastle.util.Pack;

public class AEAD {
  private final short aeadId;
  
  private final byte[] key;
  
  private final byte[] baseNonce;
  
  private long seq = 0L;
  
  private AEADCipher cipher;
  
  public AEAD(short paramShort, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    this.key = paramArrayOfbyte1;
    this.baseNonce = paramArrayOfbyte2;
    this.aeadId = paramShort;
    this.seq = 0L;
    switch (paramShort) {
      case 1:
      case 2:
        this.cipher = (AEADCipher)GCMBlockCipher.newInstance((BlockCipher)AESEngine.newInstance());
        break;
      case 3:
        this.cipher = (AEADCipher)new ChaCha20Poly1305();
        break;
    } 
  }
  
  public byte[] seal(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws InvalidCipherTextException {
    return process(true, paramArrayOfbyte1, paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
  }
  
  public byte[] seal(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) throws InvalidCipherTextException {
    Arrays.validateSegment(paramArrayOfbyte2, paramInt1, paramInt2);
    return process(true, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2);
  }
  
  public byte[] open(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws InvalidCipherTextException {
    return process(false, paramArrayOfbyte1, paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
  }
  
  public byte[] open(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) throws InvalidCipherTextException {
    Arrays.validateSegment(paramArrayOfbyte2, paramInt1, paramInt2);
    return process(false, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2);
  }
  
  private byte[] computeNonce() {
    byte[] arrayOfByte1 = Pack.longToBigEndian(this.seq++);
    byte[] arrayOfByte2 = Arrays.clone(this.baseNonce);
    Bytes.xorTo(8, arrayOfByte1, 0, arrayOfByte2, arrayOfByte2.length - 8);
    return arrayOfByte2;
  }
  
  private byte[] process(boolean paramBoolean, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) throws InvalidCipherTextException {
    ParametersWithIV parametersWithIV;
    switch (this.aeadId) {
      case 1:
      case 2:
      case 3:
        parametersWithIV = new ParametersWithIV((CipherParameters)new KeyParameter(this.key), computeNonce());
        break;
      default:
        throw new IllegalStateException("Export only mode, cannot be used to seal/open");
    } 
    this.cipher.init(paramBoolean, (CipherParameters)parametersWithIV);
    this.cipher.processAADBytes(paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
    byte[] arrayOfByte = new byte[this.cipher.getOutputSize(paramInt2)];
    int i = this.cipher.processBytes(paramArrayOfbyte2, paramInt1, paramInt2, arrayOfByte, 0);
    i += this.cipher.doFinal(arrayOfByte, i);
    if (i != arrayOfByte.length)
      throw new IllegalStateException(); 
    return arrayOfByte;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\crypto\hpke\AEAD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */