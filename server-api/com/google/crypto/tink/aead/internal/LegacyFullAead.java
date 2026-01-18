/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public class LegacyFullAead
/*     */   implements Aead
/*     */ {
/*     */   private final Aead rawAead;
/*     */   private final byte[] identifier;
/*     */   
/*     */   public static Aead create(LegacyProtoKey key) throws GeneralSecurityException {
/*     */     byte[] identifier;
/*  47 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*     */ 
/*     */     
/*  50 */     KeyManager<Aead> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), Aead.class);
/*     */     
/*  52 */     Aead rawPrimitive = (Aead)manager.getPrimitive(protoKeySerialization.getValue());
/*     */     
/*  54 */     OutputPrefixType outputPrefixType = protoKeySerialization.getOutputPrefixType();
/*     */     
/*  56 */     switch (outputPrefixType) {
/*     */       case RAW:
/*  58 */         identifier = OutputPrefixUtil.EMPTY_PREFIX.toByteArray();
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
/*  73 */         return new LegacyFullAead(rawPrimitive, identifier);case LEGACY: case CRUNCHY: identifier = OutputPrefixUtil.getLegacyOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullAead(rawPrimitive, identifier);case TINK: identifier = OutputPrefixUtil.getTinkOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullAead(rawPrimitive, identifier);
/*     */     } 
/*     */     throw new GeneralSecurityException("unknown output prefix type " + outputPrefixType);
/*     */   } public static Aead create(Aead rawAead, Bytes outputPrefix) {
/*  77 */     return new LegacyFullAead(rawAead, outputPrefix.toByteArray());
/*     */   }
/*     */   
/*     */   private LegacyFullAead(Aead rawAead, byte[] identifier) {
/*  81 */     this.rawAead = rawAead;
/*  82 */     if (identifier.length != 0 && identifier.length != 5) {
/*  83 */       throw new IllegalArgumentException("identifier has an invalid length");
/*     */     }
/*  85 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  90 */     if (this.identifier.length == 0) {
/*  91 */       return this.rawAead.encrypt(plaintext, associatedData);
/*     */     }
/*  93 */     return Bytes.concat(new byte[][] { this.identifier, this.rawAead.encrypt(plaintext, associatedData) });
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*  98 */     if (this.identifier.length == 0) {
/*  99 */       return this.rawAead.decrypt(ciphertext, associatedData);
/*     */     }
/*     */     
/* 102 */     if (!Util.isPrefix(this.identifier, ciphertext)) {
/* 103 */       throw new GeneralSecurityException("wrong prefix");
/*     */     }
/*     */     
/* 106 */     return this.rawAead.decrypt(
/* 107 */         Arrays.copyOfRange(ciphertext, 5, ciphertext.length), associatedData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\LegacyFullAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */