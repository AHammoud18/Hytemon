/*    */ package com.google.crypto.tink.signature.internal;
/*    */ 
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.KeyManager;
/*    */ import com.google.crypto.tink.PublicKeySign;
/*    */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*    */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*    */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*    */ import com.google.crypto.tink.subtle.Bytes;
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ @Immutable
/*    */ public final class LegacyFullSign
/*    */   implements PublicKeySign
/*    */ {
/*    */   private final PublicKeySign rawSigner;
/*    */   private final byte[] outputPrefix;
/*    */   private final byte[] messageSuffix;
/*    */   
/*    */   public static PublicKeySign create(LegacyProtoKey key) throws GeneralSecurityException {
/* 38 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*    */ 
/*    */     
/* 41 */     KeyManager<PublicKeySign> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), PublicKeySign.class);
/*    */     
/* 43 */     PublicKeySign rawSigner = (PublicKeySign)manager.getPrimitive(protoKeySerialization.getValue());
/*    */     
/* 45 */     return new LegacyFullSign(rawSigner, 
/*    */         
/* 47 */         LegacyFullVerify.getOutputPrefix(protoKeySerialization), 
/* 48 */         LegacyFullVerify.getMessageSuffix(protoKeySerialization));
/*    */   }
/*    */   
/*    */   private LegacyFullSign(PublicKeySign rawSigner, byte[] outputPrefix, byte[] messageSuffix) {
/* 52 */     this.rawSigner = rawSigner;
/* 53 */     this.outputPrefix = outputPrefix;
/* 54 */     this.messageSuffix = messageSuffix;
/*    */   }
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
/*    */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/*    */     byte[] signature;
/* 69 */     if (this.messageSuffix.length == 0) {
/* 70 */       signature = this.rawSigner.sign(data);
/*    */     } else {
/* 72 */       signature = this.rawSigner.sign(Bytes.concat(new byte[][] { data, this.messageSuffix }));
/*    */     } 
/* 74 */     if (this.outputPrefix.length == 0) {
/* 75 */       return signature;
/*    */     }
/* 77 */     return Bytes.concat(new byte[][] { this.outputPrefix, signature });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\LegacyFullSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */