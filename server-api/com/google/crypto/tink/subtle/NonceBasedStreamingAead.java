/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.StreamingAead;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import java.nio.channels.SeekableByteChannel;
/*    */ import java.nio.channels.WritableByteChannel;
/*    */ import java.security.GeneralSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class NonceBasedStreamingAead
/*    */   implements StreamingAead
/*    */ {
/*    */   public abstract StreamSegmentEncrypter newStreamSegmentEncrypter(byte[] paramArrayOfbyte) throws GeneralSecurityException;
/*    */   
/*    */   public abstract StreamSegmentDecrypter newStreamSegmentDecrypter() throws GeneralSecurityException;
/*    */   
/*    */   public abstract int getPlaintextSegmentSize();
/*    */   
/*    */   public abstract int getCiphertextSegmentSize();
/*    */   
/*    */   public abstract int getCiphertextOffset();
/*    */   
/*    */   public abstract int getCiphertextOverhead();
/*    */   
/*    */   public abstract int getHeaderLength();
/*    */   
/*    */   public WritableByteChannel newEncryptingChannel(WritableByteChannel ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 56 */     return new StreamingAeadEncryptingChannel(this, ciphertextChannel, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReadableByteChannel newDecryptingChannel(ReadableByteChannel ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 63 */     return new StreamingAeadDecryptingChannel(this, ciphertextChannel, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SeekableByteChannel newSeekableDecryptingChannel(SeekableByteChannel ciphertextSource, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 70 */     return new StreamingAeadSeekableDecryptingChannel(this, ciphertextSource, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream newEncryptingStream(OutputStream ciphertext, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 76 */     return new StreamingAeadEncryptingStream(this, ciphertext, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream newDecryptingStream(InputStream ciphertextStream, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 82 */     return new StreamingAeadDecryptingStream(this, ciphertextStream, associatedData);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\NonceBasedStreamingAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */