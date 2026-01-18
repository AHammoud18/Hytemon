/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.security.GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface KeyManager<P>
/*     */ {
/*     */   P getPrimitive(ByteString paramByteString) throws GeneralSecurityException;
/*     */   
/*     */   @Deprecated
/*     */   default P getPrimitive(MessageLite key) throws GeneralSecurityException {
/*  68 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default MessageLite newKey(ByteString serializedKeyFormat) throws GeneralSecurityException {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default MessageLite newKey(MessageLite keyFormat) throws GeneralSecurityException {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean doesSupport(String typeUrl) {
/* 110 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getKeyType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int getVersion() {
/* 124 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   Class<P> getPrimitiveClass();
/*     */   
/*     */   KeyData newKeyData(ByteString paramByteString) throws GeneralSecurityException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */