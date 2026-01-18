/*     */ package com.google.crypto.tink.mac.internal;
/*     */ 
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.Mac;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
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
/*     */ public final class LegacyFullMac
/*     */   implements Mac
/*     */ {
/*  40 */   private static final byte[] formatVersion = new byte[] { 0 };
/*     */   
/*     */   static final int MIN_TAG_SIZE_IN_BYTES = 10;
/*     */   
/*     */   private final Mac rawMac;
/*     */   private final OutputPrefixType outputPrefixType;
/*     */   private final byte[] identifier;
/*     */   
/*     */   public static Mac create(LegacyProtoKey key) throws GeneralSecurityException {
/*     */     byte[] outputPrefix;
/*  50 */     ProtoKeySerialization protoKeySerialization = key.getSerialization(InsecureSecretKeyAccess.get());
/*     */ 
/*     */     
/*  53 */     KeyManager<Mac> manager = KeyManagerRegistry.globalInstance().getKeyManager(protoKeySerialization.getTypeUrl(), Mac.class);
/*     */     
/*  55 */     Mac rawPrimitive = (Mac)manager.getPrimitive(protoKeySerialization.getValue());
/*     */     
/*  57 */     OutputPrefixType outputPrefixType = protoKeySerialization.getOutputPrefixType();
/*     */     
/*  59 */     switch (outputPrefixType) {
/*     */       case RAW:
/*  61 */         outputPrefix = OutputPrefixUtil.EMPTY_PREFIX.toByteArray();
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
/*  75 */         return new LegacyFullMac(rawPrimitive, outputPrefixType, outputPrefix);case LEGACY: case CRUNCHY: outputPrefix = OutputPrefixUtil.getLegacyOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullMac(rawPrimitive, outputPrefixType, outputPrefix);case TINK: outputPrefix = OutputPrefixUtil.getTinkOutputPrefix(key.getIdRequirementOrNull().intValue()).toByteArray(); return new LegacyFullMac(rawPrimitive, outputPrefixType, outputPrefix);
/*     */     } 
/*     */     throw new GeneralSecurityException("unknown output prefix type");
/*     */   } private LegacyFullMac(Mac rawMac, OutputPrefixType outputPrefixType, byte[] identifier) {
/*  79 */     this.rawMac = rawMac;
/*  80 */     this.outputPrefixType = outputPrefixType;
/*  81 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] computeMac(byte[] data) throws GeneralSecurityException {
/*  86 */     byte[] data2 = data;
/*  87 */     if (this.outputPrefixType.equals(OutputPrefixType.LEGACY)) {
/*  88 */       data2 = Bytes.concat(new byte[][] { data, formatVersion });
/*     */     }
/*  90 */     return Bytes.concat(new byte[][] { this.identifier, this.rawMac.computeMac(data2) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void verifyMac(byte[] mac, byte[] data) throws GeneralSecurityException {
/*  95 */     if (mac.length < 10) {
/*  96 */       throw new GeneralSecurityException("tag too short");
/*     */     }
/*     */     
/*  99 */     byte[] data2 = data;
/* 100 */     if (this.outputPrefixType.equals(OutputPrefixType.LEGACY)) {
/* 101 */       data2 = Bytes.concat(new byte[][] { data, formatVersion });
/*     */     }
/*     */     
/* 104 */     byte[] prefix = new byte[0];
/* 105 */     byte[] macNoPrefix = mac;
/* 106 */     if (!this.outputPrefixType.equals(OutputPrefixType.RAW)) {
/* 107 */       prefix = Arrays.copyOf(mac, 5);
/* 108 */       macNoPrefix = Arrays.copyOfRange(mac, 5, mac.length);
/*     */     } 
/*     */     
/* 111 */     if (!Arrays.equals(this.identifier, prefix)) {
/* 112 */       throw new GeneralSecurityException("wrong prefix");
/*     */     }
/*     */     
/* 115 */     this.rawMac.verifyMac(macNoPrefix, data2);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\LegacyFullMac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */