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
/*     */ public enum SlhDsaHashType
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   SLH_DSA_HASH_TYPE_UNSPECIFIED(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   SHA2(1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   SHAKE(2),
/*  26 */   UNRECOGNIZED(-1);
/*     */   public static final int SLH_DSA_HASH_TYPE_UNSPECIFIED_VALUE = 0;
/*     */   
/*     */   static {
/*  30 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", SlhDsaHashType.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  36 */         .getName());
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
/*  88 */     internalValueMap = new Internal.EnumLiteMap<SlhDsaHashType>()
/*     */       {
/*     */         public SlhDsaHashType findValueByNumber(int number) {
/*  91 */           return SlhDsaHashType.forNumber(number);
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
/* 112 */     VALUES = values();
/*     */   }
/*     */   
/*     */   public static final int SHA2_VALUE = 1;
/*     */   public static final int SHAKE_VALUE = 2;
/*     */   private static final Internal.EnumLiteMap<SlhDsaHashType> internalValueMap;
/*     */   private static final SlhDsaHashType[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED) {
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
/*     */     }
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   SlhDsaHashType(int value) {
/* 129 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static SlhDsaHashType forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return SLH_DSA_HASH_TYPE_UNSPECIFIED;
/*     */       case 1:
/*     */         return SHA2;
/*     */       case 2:
/*     */         return SHAKE;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<SlhDsaHashType> internalGetValueMap() {
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
/*     */     return SlhDsa.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\SlhDsaHashType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */