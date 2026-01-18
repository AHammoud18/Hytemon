/*     */ package com.google.crypto.tink.proto;
/*     */ 
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.Internal;
/*     */ import com.google.protobuf.ProtocolMessageEnum;
/*     */ import com.google.protobuf.RuntimeVersion;
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
/*     */ public enum MlDsaInstance
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  21 */   ML_DSA_UNKNOWN_INSTANCE(0),
/*     */ 
/*     */ 
/*     */   
/*  25 */   ML_DSA_65(1),
/*     */ 
/*     */ 
/*     */   
/*  29 */   ML_DSA_87(2),
/*  30 */   UNRECOGNIZED(-1);
/*     */   public static final int ML_DSA_UNKNOWN_INSTANCE_VALUE = 0;
/*     */   
/*     */   static {
/*  34 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", MlDsaInstance.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  40 */         .getName());
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
/*  92 */     internalValueMap = new Internal.EnumLiteMap<MlDsaInstance>()
/*     */       {
/*     */         public MlDsaInstance findValueByNumber(int number) {
/*  95 */           return MlDsaInstance.forNumber(number);
/*     */         }
/*     */       };
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
/* 116 */     VALUES = values();
/*     */   }
/*     */   
/*     */   public static final int ML_DSA_65_VALUE = 1;
/*     */   public static final int ML_DSA_87_VALUE = 2;
/*     */   private static final Internal.EnumLiteMap<MlDsaInstance> internalValueMap;
/*     */   private static final MlDsaInstance[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED) {
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
/*     */     }
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   MlDsaInstance(int value) {
/* 133 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static MlDsaInstance forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return ML_DSA_UNKNOWN_INSTANCE;
/*     */       case 1:
/*     */         return ML_DSA_65;
/*     */       case 2:
/*     */         return ML_DSA_87;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<MlDsaInstance> internalGetValueMap() {
/*     */     return internalValueMap;
/*     */   }
/*     */   
/*     */   public final Descriptors.EnumValueDescriptor getValueDescriptor() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value."); 
/*     */     return getDescriptor().getValues().get(ordinal());
/*     */   }
/*     */   
/*     */   public final Descriptors.EnumDescriptor getDescriptorForType() {
/*     */     return getDescriptor();
/*     */   }
/*     */   
/*     */   public static Descriptors.EnumDescriptor getDescriptor() {
/*     */     return MlDsa.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsaInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */