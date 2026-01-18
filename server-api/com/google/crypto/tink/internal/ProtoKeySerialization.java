/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.protobuf.ByteString;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.annotation.Nullable;
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
/*     */ @Immutable
/*     */ public final class ProtoKeySerialization
/*     */   implements Serialization
/*     */ {
/*     */   private final String typeUrl;
/*     */   private final Bytes objectIdentifier;
/*     */   private final ByteString value;
/*     */   private final KeyData.KeyMaterialType keyMaterialType;
/*     */   private final OutputPrefixType outputPrefixType;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private ProtoKeySerialization(String typeUrl, Bytes objectIdentifier, ByteString value, KeyData.KeyMaterialType keyMaterialType, OutputPrefixType outputPrefixType, @Nullable Integer idRequirement) {
/*  51 */     this.typeUrl = typeUrl;
/*  52 */     this.objectIdentifier = objectIdentifier;
/*  53 */     this.value = value;
/*  54 */     this.keyMaterialType = keyMaterialType;
/*  55 */     this.outputPrefixType = outputPrefixType;
/*  56 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProtoKeySerialization create(String typeUrl, ByteString value, KeyData.KeyMaterialType keyMaterialType, OutputPrefixType outputPrefixType, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  66 */     if (outputPrefixType == OutputPrefixType.RAW) {
/*  67 */       if (idRequirement != null) {
/*  68 */         throw new GeneralSecurityException("Keys with output prefix type raw should not have an id requirement.");
/*     */       
/*     */       }
/*     */     }
/*  72 */     else if (idRequirement == null) {
/*  73 */       throw new GeneralSecurityException("Keys with output prefix type different from raw should have an id requirement.");
/*     */     } 
/*     */ 
/*     */     
/*  77 */     Bytes objectIdentifier = Util.checkedToBytesFromPrintableAscii(typeUrl);
/*  78 */     return new ProtoKeySerialization(typeUrl, objectIdentifier, value, keyMaterialType, outputPrefixType, idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteString getValue() {
/*  84 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyData.KeyMaterialType getKeyMaterialType() {
/*  92 */     return this.keyMaterialType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputPrefixType getOutputPrefixType() {
/* 100 */     return this.outputPrefixType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 109 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bytes getObjectIdentifier() {
/* 119 */     return this.objectIdentifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTypeUrl() {
/* 124 */     return this.typeUrl;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\ProtoKeySerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */