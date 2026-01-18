/*    */ package com.google.crypto.tink.streamingaead.internal;
/*    */ 
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.KeyManager;
/*    */ import com.google.crypto.tink.StreamingAead;
/*    */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*    */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*    */ import com.google.crypto.tink.internal.ProtoKeySerialization;
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
/*    */ public class LegacyFullStreamingAead
/*    */   implements StreamingAead
/*    */ {
/*    */   private final StreamingAead rawStreamingAead;
/*    */   
/*    */   public static StreamingAead create(LegacyProtoKey key) throws GeneralSecurityException {
/* 49 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*    */ 
/*    */     
/* 52 */     KeyManager<StreamingAead> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), StreamingAead.class);
/*    */     
/* 54 */     return (StreamingAead)manager.getPrimitive(protoKeySerialization.getValue());
/*    */   }
/*    */   
/*    */   private LegacyFullStreamingAead(StreamingAead rawStreamingAead) {
/* 58 */     this.rawStreamingAead = rawStreamingAead;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WritableByteChannel newEncryptingChannel(WritableByteChannel ciphertextDestination, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 65 */     return this.rawStreamingAead.newEncryptingChannel(ciphertextDestination, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SeekableByteChannel newSeekableDecryptingChannel(SeekableByteChannel ciphertextSource, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 72 */     return this.rawStreamingAead.newSeekableDecryptingChannel(ciphertextSource, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReadableByteChannel newDecryptingChannel(ReadableByteChannel ciphertextSource, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 79 */     return this.rawStreamingAead.newDecryptingChannel(ciphertextSource, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream newEncryptingStream(OutputStream ciphertextDestination, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 85 */     return this.rawStreamingAead.newEncryptingStream(ciphertextDestination, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream newDecryptingStream(InputStream ciphertextSource, byte[] associatedData) throws GeneralSecurityException, IOException {
/* 91 */     return this.rawStreamingAead.newDecryptingStream(ciphertextSource, associatedData);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\internal\LegacyFullStreamingAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */