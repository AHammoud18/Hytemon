/*    */ package com.google.crypto.tink.streamingaead;
/*    */ 
/*    */ import com.google.crypto.tink.StreamingAead;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import java.nio.channels.SeekableByteChannel;
/*    */ import java.nio.channels.WritableByteChannel;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.List;
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
/*    */ final class StreamingAeadHelper
/*    */   implements StreamingAead
/*    */ {
/*    */   private final List<StreamingAead> allPrimitives;
/*    */   private final StreamingAead primary;
/*    */   
/*    */   public StreamingAeadHelper(List<StreamingAead> allPrimitives, StreamingAead primary) throws GeneralSecurityException {
/* 44 */     this.allPrimitives = allPrimitives;
/* 45 */     this.primary = primary;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WritableByteChannel newEncryptingChannel(WritableByteChannel ciphertextDestination, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 52 */     return this.primary.newEncryptingChannel(ciphertextDestination, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReadableByteChannel newDecryptingChannel(ReadableByteChannel ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 59 */     return new ReadableByteChannelDecrypter(this.allPrimitives, ciphertextChannel, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SeekableByteChannel newSeekableDecryptingChannel(SeekableByteChannel ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 66 */     return new SeekableByteChannelDecrypter(this.allPrimitives, ciphertextChannel, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream newDecryptingStream(InputStream ciphertextStream, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 74 */     return new InputStreamDecrypter(this.allPrimitives, ciphertextStream, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream newEncryptingStream(OutputStream ciphertext, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 81 */     return this.primary.newEncryptingStream(ciphertext, associatedData);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\StreamingAeadHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */