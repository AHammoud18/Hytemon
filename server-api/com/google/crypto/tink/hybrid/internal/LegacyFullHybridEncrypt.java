/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.crypto.tink.HybridEncrypt;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.KeyManager;
/*    */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*    */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*    */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*    */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*    */ import com.google.crypto.tink.proto.OutputPrefixType;
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
/*    */ public final class LegacyFullHybridEncrypt
/*    */   implements HybridEncrypt
/*    */ {
/*    */   private final HybridEncrypt rawHybridEncrypt;
/*    */   private final byte[] outputPrefix;
/*    */   
/*    */   public static HybridEncrypt create(LegacyProtoKey key) throws GeneralSecurityException {
/*    */     byte[] outputPrefix;
/* 52 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*    */ 
/*    */     
/* 55 */     KeyManager<HybridEncrypt> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), HybridEncrypt.class);
/*    */     
/* 57 */     HybridEncrypt rawPrimitive = (HybridEncrypt)manager.getPrimitive(protoKeySerialization.getValue());
/*    */     
/* 59 */     OutputPrefixType outputPrefixType = protoKeySerialization.getOutputPrefixType();
/*    */     
/* 61 */     switch (outputPrefixType) {
/*    */       case RAW:
/* 63 */         outputPrefix = OutputPrefixUtil.EMPTY_PREFIX.toByteArray();
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
/* 77 */         return new LegacyFullHybridEncrypt(rawPrimitive, outputPrefix);case LEGACY: case CRUNCHY: outputPrefix = OutputPrefixUtil.getLegacyOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullHybridEncrypt(rawPrimitive, outputPrefix);case TINK: outputPrefix = OutputPrefixUtil.getTinkOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullHybridEncrypt(rawPrimitive, outputPrefix);
/*    */     } 
/*    */     throw new GeneralSecurityException("unknown output prefix type " + outputPrefixType);
/*    */   } private LegacyFullHybridEncrypt(HybridEncrypt rawHybridEncrypt, byte[] outputPrefix) {
/* 81 */     this.rawHybridEncrypt = rawHybridEncrypt;
/* 82 */     this.outputPrefix = outputPrefix;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] encrypt(byte[] plaintext, byte[] contextInfo) throws GeneralSecurityException {
/* 88 */     if (this.outputPrefix.length == 0) {
/* 89 */       return this.rawHybridEncrypt.encrypt(plaintext, contextInfo);
/*    */     }
/* 91 */     return Bytes.concat(new byte[][] { this.outputPrefix, this.rawHybridEncrypt.encrypt(plaintext, contextInfo) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\LegacyFullHybridEncrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */