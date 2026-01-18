/*    */ package com.google.crypto.tink.aead;
/*    */ 
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
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
/*    */ public final class LegacyKmsAeadKey
/*    */   extends AeadKey
/*    */ {
/*    */   private final LegacyKmsAeadParameters parameters;
/*    */   private final Bytes outputPrefix;
/*    */   @Nullable
/*    */   private final Integer idRequirement;
/*    */   
/*    */   private LegacyKmsAeadKey(LegacyKmsAeadParameters parameters, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 42 */     this.parameters = parameters;
/* 43 */     this.outputPrefix = outputPrefix;
/* 44 */     this.idRequirement = idRequirement;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static LegacyKmsAeadKey create(LegacyKmsAeadParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*    */     Bytes outputPrefix;
/* 51 */     if (parameters.variant() == LegacyKmsAeadParameters.Variant.TINK) {
/* 52 */       if (idRequirement == null) {
/* 53 */         throw new GeneralSecurityException("For given Variant TINK the value of idRequirement must be non-null");
/*    */       }
/*    */ 
/*    */       
/* 57 */       outputPrefix = Bytes.copyFrom(ByteBuffer.allocate(5).put((byte)1).putInt(idRequirement.intValue()).array());
/* 58 */     } else if (parameters.variant() == LegacyKmsAeadParameters.Variant.NO_PREFIX) {
/* 59 */       if (idRequirement != null) {
/* 60 */         throw new GeneralSecurityException("For given Variant NO_PREFIX the value of idRequirement must be null");
/*    */       }
/*    */       
/* 63 */       outputPrefix = Bytes.copyFrom(new byte[0]);
/*    */     } else {
/* 65 */       throw new GeneralSecurityException("Unknown Variant: " + parameters.variant());
/*    */     } 
/* 67 */     return new LegacyKmsAeadKey(parameters, outputPrefix, idRequirement);
/*    */   }
/*    */ 
/*    */   
/*    */   public static LegacyKmsAeadKey create(LegacyKmsAeadParameters parameters) throws GeneralSecurityException {
/* 72 */     return create(parameters, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public Bytes getOutputPrefix() {
/* 77 */     return this.outputPrefix;
/*    */   }
/*    */ 
/*    */   
/*    */   public LegacyKmsAeadParameters getParameters() {
/* 82 */     return this.parameters;
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer getIdRequirementOrNull() {
/* 87 */     return this.idRequirement;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 92 */     if (!(o instanceof LegacyKmsAeadKey)) {
/* 93 */       return false;
/*    */     }
/* 95 */     LegacyKmsAeadKey that = (LegacyKmsAeadKey)o;
/* 96 */     return (that.parameters.equals(this.parameters) && Objects.equals(that.idRequirement, this.idRequirement));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\LegacyKmsAeadKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */