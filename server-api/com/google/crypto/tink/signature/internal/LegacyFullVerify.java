/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
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
/*     */ @Immutable
/*     */ public final class LegacyFullVerify
/*     */   implements PublicKeyVerify
/*     */ {
/*     */   private final PublicKeyVerify rawVerifier;
/*     */   private final byte[] outputPrefix;
/*     */   private final byte[] messageSuffix;
/*     */   
/*     */   public static PublicKeyVerify create(LegacyProtoKey key) throws GeneralSecurityException {
/*  43 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*     */ 
/*     */     
/*  46 */     KeyManager<PublicKeyVerify> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), PublicKeyVerify.class);
/*     */     
/*  48 */     PublicKeyVerify rawVerifier = (PublicKeyVerify)manager.getPrimitive(protoKeySerialization.getValue());
/*  49 */     return new LegacyFullVerify(rawVerifier, 
/*     */         
/*  51 */         getOutputPrefix(protoKeySerialization), 
/*  52 */         getMessageSuffix(protoKeySerialization));
/*     */   }
/*     */   
/*     */   static byte[] getOutputPrefix(ProtoKeySerialization key) throws GeneralSecurityException {
/*  56 */     switch (key.getOutputPrefixType()) {
/*     */       case LEGACY:
/*     */       case CRUNCHY:
/*  59 */         return OutputPrefixUtil.getLegacyOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray();
/*     */       case TINK:
/*  61 */         return OutputPrefixUtil.getTinkOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray();
/*     */       case RAW:
/*  63 */         return OutputPrefixUtil.EMPTY_PREFIX.toByteArray();
/*     */     } 
/*  65 */     throw new GeneralSecurityException("unknown output prefix type");
/*     */   }
/*     */ 
/*     */   
/*     */   static byte[] getMessageSuffix(ProtoKeySerialization key) {
/*  70 */     if (key.getOutputPrefixType().equals(OutputPrefixType.LEGACY)) {
/*  71 */       return new byte[] { 0 };
/*     */     }
/*  73 */     return new byte[0];
/*     */   }
/*     */   
/*     */   private LegacyFullVerify(PublicKeyVerify rawVerifier, byte[] outputPrefix, byte[] messageSuffix) {
/*  77 */     this.rawVerifier = rawVerifier;
/*  78 */     this.outputPrefix = outputPrefix;
/*  79 */     this.messageSuffix = messageSuffix;
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
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/*  93 */     if (this.outputPrefix.length == 0 && this.messageSuffix.length == 0) {
/*  94 */       this.rawVerifier.verify(signature, data);
/*     */       return;
/*     */     } 
/*  97 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/*  98 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 100 */     byte[] dataCopy = data;
/* 101 */     if (this.messageSuffix.length != 0) {
/* 102 */       dataCopy = Bytes.concat(new byte[][] { data, this.messageSuffix });
/*     */     }
/* 104 */     byte[] signatureNoPrefix = Arrays.copyOfRange(signature, this.outputPrefix.length, signature.length);
/* 105 */     this.rawVerifier.verify(signatureNoPrefix, dataCopy);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\LegacyFullVerify.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */