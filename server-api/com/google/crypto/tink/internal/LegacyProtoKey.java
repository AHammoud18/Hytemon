/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Objects;
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
/*     */ @Immutable
/*     */ public final class LegacyProtoKey
/*     */   extends Key
/*     */ {
/*     */   private final ProtoKeySerialization serialization;
/*     */   
/*     */   @Immutable
/*     */   private static class LegacyProtoParametersNotForCreation
/*     */     extends Parameters
/*     */   {
/*     */     private final String typeUrl;
/*     */     private final OutputPrefixType outputPrefixType;
/*     */     
/*     */     public boolean hasIdRequirement() {
/*  45 */       return (this.outputPrefixType != OutputPrefixType.RAW);
/*     */     }
/*     */ 
/*     */     
/*     */     private static String outputPrefixToString(OutputPrefixType outputPrefixType) {
/*  50 */       switch (outputPrefixType) {
/*     */         case SYMMETRIC:
/*  52 */           return "TINK";
/*     */         case ASYMMETRIC_PRIVATE:
/*  54 */           return "LEGACY";
/*     */         case null:
/*  56 */           return "RAW";
/*     */         case null:
/*  58 */           return "CRUNCHY";
/*     */       } 
/*  60 */       return "UNKNOWN";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  69 */       return String.format("(typeUrl=%s, outputPrefixType=%s)", new Object[] { this.typeUrl, 
/*  70 */             outputPrefixToString(this.outputPrefixType) });
/*     */     }
/*     */     
/*     */     private LegacyProtoParametersNotForCreation(String typeUrl, OutputPrefixType outputPrefixType) {
/*  74 */       this.typeUrl = typeUrl;
/*  75 */       this.outputPrefixType = outputPrefixType;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwIfMissingAccess(ProtoKeySerialization protoKeySerialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/*  84 */     switch (protoKeySerialization.getKeyMaterialType()) {
/*     */       case SYMMETRIC:
/*     */       case ASYMMETRIC_PRIVATE:
/*  87 */         SecretKeyAccess.requireAccess(access);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Bytes computeOutputPrefix(ProtoKeySerialization serialization) throws GeneralSecurityException {
/*  95 */     if (serialization.getOutputPrefixType().equals(OutputPrefixType.RAW)) {
/*  96 */       return Bytes.copyFrom(new byte[0]);
/*     */     }
/*  98 */     if (serialization.getOutputPrefixType().equals(OutputPrefixType.TINK)) {
/*  99 */       return OutputPrefixUtil.getTinkOutputPrefix(serialization.getIdRequirementOrNull().intValue());
/*     */     }
/* 101 */     if (serialization.getOutputPrefixType().equals(OutputPrefixType.LEGACY) || serialization
/* 102 */       .getOutputPrefixType().equals(OutputPrefixType.CRUNCHY)) {
/* 103 */       return OutputPrefixUtil.getLegacyOutputPrefix(serialization.getIdRequirementOrNull().intValue());
/*     */     }
/* 105 */     throw new GeneralSecurityException("Unknown output prefix type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegacyProtoKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 115 */     throwIfMissingAccess(serialization, access);
/* 116 */     this.serialization = serialization;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key key) {
/* 133 */     if (!(key instanceof LegacyProtoKey)) {
/* 134 */       return false;
/*     */     }
/* 136 */     ProtoKeySerialization other = ((LegacyProtoKey)key).serialization;
/*     */     
/* 138 */     if (!other.getOutputPrefixType().equals(this.serialization.getOutputPrefixType())) {
/* 139 */       return false;
/*     */     }
/* 141 */     if (!other.getKeyMaterialType().equals(this.serialization.getKeyMaterialType())) {
/* 142 */       return false;
/*     */     }
/* 144 */     if (!other.getTypeUrl().equals(this.serialization.getTypeUrl())) {
/* 145 */       return false;
/*     */     }
/* 147 */     if (!Objects.equals(other.getIdRequirementOrNull(), this.serialization.getIdRequirementOrNull())) {
/* 148 */       return false;
/*     */     }
/* 150 */     return Bytes.equal(this.serialization
/* 151 */         .getValue().toByteArray(), other.getValue().toByteArray());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 157 */     return this.serialization.getIdRequirementOrNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtoKeySerialization getSerialization(@Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 167 */     throwIfMissingAccess(this.serialization, access);
/* 168 */     return this.serialization;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Parameters getParameters() {
/* 179 */     return new LegacyProtoParametersNotForCreation(this.serialization
/* 180 */         .getTypeUrl(), this.serialization.getOutputPrefixType());
/*     */   }
/*     */   
/*     */   public Bytes getOutputPrefix() throws GeneralSecurityException {
/* 184 */     return computeOutputPrefix(this.serialization);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\LegacyProtoKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */