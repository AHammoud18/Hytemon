/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.crypto.tink.HybridDecrypt;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.KeyManager;
/*    */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*    */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*    */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*    */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*    */ import com.google.crypto.tink.internal.Util;
/*    */ import com.google.crypto.tink.proto.OutputPrefixType;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Arrays;
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
/*    */ public final class LegacyFullHybridDecrypt
/*    */   implements HybridDecrypt
/*    */ {
/*    */   private final HybridDecrypt rawHybridDecrypt;
/*    */   private final byte[] outputPrefix;
/*    */   
/*    */   public static HybridDecrypt create(LegacyProtoKey key) throws GeneralSecurityException {
/*    */     byte[] outputPrefix;
/* 53 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*    */ 
/*    */     
/* 56 */     KeyManager<HybridDecrypt> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), HybridDecrypt.class);
/*    */     
/* 58 */     HybridDecrypt rawPrimitive = (HybridDecrypt)manager.getPrimitive(protoKeySerialization.getValue());
/*    */     
/* 60 */     OutputPrefixType outputPrefixType = protoKeySerialization.getOutputPrefixType();
/*    */     
/* 62 */     switch (outputPrefixType) {
/*    */       case RAW:
/* 64 */         outputPrefix = OutputPrefixUtil.EMPTY_PREFIX.toByteArray();
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
/* 78 */         return new LegacyFullHybridDecrypt(rawPrimitive, outputPrefix);case LEGACY: case CRUNCHY: outputPrefix = OutputPrefixUtil.getLegacyOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullHybridDecrypt(rawPrimitive, outputPrefix);case TINK: outputPrefix = OutputPrefixUtil.getTinkOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullHybridDecrypt(rawPrimitive, outputPrefix);
/*    */     } 
/*    */     throw new GeneralSecurityException("unknown output prefix type " + outputPrefixType);
/*    */   } private LegacyFullHybridDecrypt(HybridDecrypt rawHybridDecrypt, byte[] outputPrefix) {
/* 82 */     this.rawHybridDecrypt = rawHybridDecrypt;
/* 83 */     this.outputPrefix = outputPrefix;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] decrypt(byte[] ciphertext, byte[] contextInfo) throws GeneralSecurityException {
/* 89 */     if (this.outputPrefix.length == 0) {
/* 90 */       return this.rawHybridDecrypt.decrypt(ciphertext, contextInfo);
/*    */     }
/* 92 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 93 */       throw new GeneralSecurityException("Invalid ciphertext (output prefix mismatch)");
/*    */     }
/*    */     
/* 96 */     byte[] ciphertextNoPrefix = Arrays.copyOfRange(ciphertext, this.outputPrefix.length, ciphertext.length);
/* 97 */     return this.rawHybridDecrypt.decrypt(ciphertextNoPrefix, contextInfo);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\LegacyFullHybridDecrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */