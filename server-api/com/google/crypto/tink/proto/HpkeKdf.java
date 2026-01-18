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
/*     */ public enum HpkeKdf
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   KDF_UNKNOWN(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   HKDF_SHA256(1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   HKDF_SHA384(2),
/*     */ 
/*     */ 
/*     */   
/*  29 */   HKDF_SHA512(3),
/*  30 */   UNRECOGNIZED(-1);
/*     */   public static final int KDF_UNKNOWN_VALUE = 0;
/*     */   
/*     */   static {
/*  34 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HpkeKdf.class
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     internalValueMap = new Internal.EnumLiteMap<HpkeKdf>()
/*     */       {
/*     */         public HpkeKdf findValueByNumber(int number) {
/* 100 */           return HpkeKdf.forNumber(number);
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
/* 121 */     VALUES = values();
/*     */   }
/*     */   
/*     */   public static final int HKDF_SHA256_VALUE = 1;
/*     */   public static final int HKDF_SHA384_VALUE = 2;
/*     */   public static final int HKDF_SHA512_VALUE = 3;
/*     */   private static final Internal.EnumLiteMap<HpkeKdf> internalValueMap;
/*     */   private static final HpkeKdf[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   HpkeKdf(int value) {
/* 138 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static HpkeKdf forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return KDF_UNKNOWN;
/*     */       case 1:
/*     */         return HKDF_SHA256;
/*     */       case 2:
/*     */         return HKDF_SHA384;
/*     */       case 3:
/*     */         return HKDF_SHA512;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<HpkeKdf> internalGetValueMap() {
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
/*     */     return Hpke.getDescriptor().getEnumTypes().get(1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkeKdf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */