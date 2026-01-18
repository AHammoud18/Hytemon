/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.proto.OutputPrefixType;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.util.Objects;
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
/*    */ public final class LegacyProtoParameters
/*    */   extends Parameters
/*    */ {
/*    */   private final ProtoParametersSerialization serialization;
/*    */   
/*    */   public LegacyProtoParameters(ProtoParametersSerialization serialization) {
/* 31 */     this.serialization = serialization;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasIdRequirement() {
/* 36 */     return (this.serialization.getKeyTemplate().getOutputPrefixType() != OutputPrefixType.RAW);
/*    */   }
/*    */ 
/*    */   
/*    */   public ProtoParametersSerialization getSerialization() {
/* 41 */     return this.serialization;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 46 */     if (!(o instanceof LegacyProtoParameters)) {
/* 47 */       return false;
/*    */     }
/* 49 */     ProtoParametersSerialization other = ((LegacyProtoParameters)o).serialization;
/* 50 */     return (this.serialization
/* 51 */       .getKeyTemplate()
/* 52 */       .getOutputPrefixType()
/* 53 */       .equals(other.getKeyTemplate().getOutputPrefixType()) && this.serialization
/* 54 */       .getKeyTemplate().getTypeUrl().equals(other.getKeyTemplate().getTypeUrl()) && this.serialization
/* 55 */       .getKeyTemplate().getValue().equals(other.getKeyTemplate().getValue()));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 60 */     return Objects.hash(new Object[] { this.serialization.getKeyTemplate(), this.serialization.getObjectIdentifier() });
/*    */   }
/*    */ 
/*    */   
/*    */   private static String outputPrefixToString(OutputPrefixType outputPrefixType) {
/* 65 */     switch (outputPrefixType) {
/*    */       case TINK:
/* 67 */         return "TINK";
/*    */       case LEGACY:
/* 69 */         return "LEGACY";
/*    */       case RAW:
/* 71 */         return "RAW";
/*    */       case CRUNCHY:
/* 73 */         return "CRUNCHY";
/*    */     } 
/* 75 */     return "UNKNOWN";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return String.format("(typeUrl=%s, outputPrefixType=%s)", new Object[] { this.serialization
/*    */           
/* 83 */           .getKeyTemplate().getTypeUrl(), 
/* 84 */           outputPrefixToString(this.serialization.getKeyTemplate().getOutputPrefixType()) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\LegacyProtoParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */