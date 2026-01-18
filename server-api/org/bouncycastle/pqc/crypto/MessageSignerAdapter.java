package org.bouncycastle.pqc.crypto;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.util.Arrays;

public final class MessageSignerAdapter implements Signer {
  private final Buffer buffer = new Buffer();
  
  private final MessageSigner messageSigner;
  
  public MessageSignerAdapter(MessageSigner paramMessageSigner) {
    if (paramMessageSigner == null)
      throw new NullPointerException("'messageSigner' cannot be null"); 
    this.messageSigner = paramMessageSigner;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters) {
    this.messageSigner.init(paramBoolean, paramCipherParameters);
  }
  
  public void update(byte paramByte) {
    this.buffer.write(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.buffer.write(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public byte[] generateSignature() {
    return this.messageSigner.generateSignature(getMessage());
  }
  
  public boolean verifySignature(byte[] paramArrayOfbyte) {
    return this.messageSigner.verifySignature(getMessage(), paramArrayOfbyte);
  }
  
  public void reset() {
    this.buffer.reset();
  }
  
  private byte[] getMessage() {
    try {
      return this.buffer.toByteArray();
    } finally {
      reset();
    } 
  }
  
  private static final class Buffer extends ByteArrayOutputStream {
    private Buffer() {}
    
    public synchronized void reset() {
      Arrays.fill(this.buf, 0, this.count, (byte)0);
      this.count = 0;
    }
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\MessageSignerAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */