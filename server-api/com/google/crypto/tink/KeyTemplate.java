/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.internal.LegacyProtoParameters;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
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
/*     */ 
/*     */ @Immutable
/*     */ public final class KeyTemplate
/*     */ {
/*     */   @Nullable
/*     */   private final KeyTemplate kt;
/*     */   @Nullable
/*     */   private final Parameters parameters;
/*     */   
/*     */   public enum OutputPrefixType
/*     */   {
/*  52 */     TINK,
/*  53 */     LEGACY,
/*  54 */     RAW,
/*  55 */     CRUNCHY;
/*     */   }
/*     */ 
/*     */   
/*     */   static OutputPrefixType fromProto(OutputPrefixType outputPrefixType) {
/*  60 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  62 */         return OutputPrefixType.TINK;
/*     */       case LEGACY:
/*  64 */         return OutputPrefixType.LEGACY;
/*     */       case RAW:
/*  66 */         return OutputPrefixType.RAW;
/*     */       case CRUNCHY:
/*  68 */         return OutputPrefixType.CRUNCHY;
/*     */     } 
/*  70 */     throw new IllegalArgumentException("Unknown output prefix type");
/*     */   }
/*     */ 
/*     */   
/*     */   static OutputPrefixType toProto(OutputPrefixType outputPrefixType) {
/*  75 */     switch (outputPrefixType.ordinal()) {
/*     */       case 0:
/*  77 */         return OutputPrefixType.TINK;
/*     */       case 1:
/*  79 */         return OutputPrefixType.LEGACY;
/*     */       case 2:
/*  81 */         return OutputPrefixType.RAW;
/*     */       case 3:
/*  83 */         return OutputPrefixType.CRUNCHY;
/*     */     } 
/*  85 */     throw new IllegalArgumentException("Unknown output prefix type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static KeyTemplate create(String typeUrl, byte[] value, OutputPrefixType outputPrefixType) {
/*  94 */     return new KeyTemplate(
/*  95 */         KeyTemplate.newBuilder()
/*  96 */         .setTypeUrl(typeUrl)
/*  97 */         .setValue(ByteString.copyFrom(value))
/*  98 */         .setOutputPrefixType(toProto(outputPrefixType))
/*  99 */         .build());
/*     */   }
/*     */   
/*     */   public static KeyTemplate createFrom(Parameters p) throws GeneralSecurityException {
/* 103 */     return new KeyTemplate(p);
/*     */   }
/*     */   
/*     */   private KeyTemplate(KeyTemplate kt) {
/* 107 */     this.kt = kt;
/* 108 */     this.parameters = null;
/*     */   }
/*     */   
/*     */   private KeyTemplate(Parameters parameters) {
/* 112 */     this.kt = null;
/* 113 */     this.parameters = parameters;
/*     */   }
/*     */   
/*     */   KeyTemplate getProto() {
/*     */     try {
/* 118 */       return getProtoMaybeThrow();
/* 119 */     } catch (GeneralSecurityException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       throw new TinkBugException("Parsing parameters failed in getProto(). You probably want to call some Tink register function for " + this.parameters, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KeyTemplate getProtoMaybeThrow() throws GeneralSecurityException {
/* 134 */     if (this.kt != null) {
/* 135 */       return this.kt;
/*     */     }
/* 137 */     if (this.parameters instanceof LegacyProtoParameters) {
/* 138 */       return ((LegacyProtoParameters)this.parameters).getSerialization().getKeyTemplate();
/*     */     }
/*     */ 
/*     */     
/* 142 */     ProtoParametersSerialization s = (ProtoParametersSerialization)MutableSerializationRegistry.globalInstance().serializeParameters(this.parameters, ProtoParametersSerialization.class);
/* 143 */     return s.getKeyTemplate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getTypeUrl() {
/* 154 */     return getProto().getTypeUrl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte[] getValue() {
/* 165 */     return getProto().getValue().toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public OutputPrefixType getOutputPrefixType() {
/* 176 */     return fromProto(getProto().getOutputPrefixType());
/*     */   }
/*     */   
/*     */   public Parameters toParameters() throws GeneralSecurityException {
/* 180 */     if (this.parameters != null) {
/* 181 */       return this.parameters;
/*     */     }
/* 183 */     return TinkProtoParametersFormat.parse(getProto().toByteArray());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KeyTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */