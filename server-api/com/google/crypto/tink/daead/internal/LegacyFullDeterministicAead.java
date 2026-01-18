/*     */ package com.google.crypto.tink.daead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.DeterministicAead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.subtle.Bytes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegacyFullDeterministicAead
/*     */   implements DeterministicAead
/*     */ {
/*     */   private final DeterministicAead rawDaead;
/*     */   private final OutputPrefixType outputPrefixType;
/*     */   private final byte[] identifier;
/*     */   
/*     */   public static DeterministicAead create(LegacyProtoKey key) throws GeneralSecurityException {
/*     */     byte[] identifier;
/*  50 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*     */ 
/*     */     
/*  53 */     KeyManager<DeterministicAead> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), DeterministicAead.class);
/*     */     
/*  55 */     DeterministicAead rawPrimitive = (DeterministicAead)manager.getPrimitive(protoKeySerialization.getValue());
/*     */     
/*  57 */     OutputPrefixType outputPrefixType = protoKeySerialization.getOutputPrefixType();
/*     */     
/*  59 */     switch (outputPrefixType) {
/*     */       case RAW:
/*  61 */         identifier = OutputPrefixUtil.EMPTY_PREFIX.toByteArray();
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
/*  77 */         return new LegacyFullDeterministicAead(rawPrimitive, outputPrefixType, identifier);case LEGACY: case CRUNCHY: identifier = OutputPrefixUtil.getLegacyOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullDeterministicAead(rawPrimitive, outputPrefixType, identifier);case TINK: identifier = OutputPrefixUtil.getTinkOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullDeterministicAead(rawPrimitive, outputPrefixType, identifier);
/*     */     } 
/*     */     throw new GeneralSecurityException("unknown output prefix type " + outputPrefixType.getNumber());
/*     */   }
/*     */   private LegacyFullDeterministicAead(DeterministicAead rawDaead, OutputPrefixType outputPrefixType, byte[] identifier) {
/*  82 */     this.rawDaead = rawDaead;
/*  83 */     this.outputPrefixType = outputPrefixType;
/*  84 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encryptDeterministically(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  90 */     if (this.outputPrefixType == OutputPrefixType.RAW) {
/*  91 */       return this.rawDaead.encryptDeterministically(plaintext, associatedData);
/*     */     }
/*  93 */     return Bytes.concat(new byte[][] { this.identifier, this.rawDaead.encryptDeterministically(plaintext, associatedData) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decryptDeterministically(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*  99 */     if (this.outputPrefixType == OutputPrefixType.RAW) {
/* 100 */       return this.rawDaead.decryptDeterministically(ciphertext, associatedData);
/*     */     }
/*     */     
/* 103 */     if (!Util.isPrefix(this.identifier, ciphertext)) {
/* 104 */       throw new GeneralSecurityException("wrong prefix");
/*     */     }
/* 106 */     return this.rawDaead.decryptDeterministically(
/* 107 */         Arrays.copyOfRange(ciphertext, 5, ciphertext.length), associatedData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\internal\LegacyFullDeterministicAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */