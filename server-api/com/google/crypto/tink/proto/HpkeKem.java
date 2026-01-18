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
/*     */ public enum HpkeKem
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   KEM_UNKNOWN(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   DHKEM_X25519_HKDF_SHA256(1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   DHKEM_P256_HKDF_SHA256(2),
/*     */ 
/*     */ 
/*     */   
/*  29 */   DHKEM_P384_HKDF_SHA384(3),
/*     */ 
/*     */ 
/*     */   
/*  33 */   DHKEM_P521_HKDF_SHA512(4),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   X_WING(5),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   ML_KEM768(6),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   ML_KEM1024(7),
/*  61 */   UNRECOGNIZED(-1); public static final int KEM_UNKNOWN_VALUE = 0;
/*     */   public static final int DHKEM_X25519_HKDF_SHA256_VALUE = 1;
/*     */   
/*     */   static {
/*  65 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HpkeKem.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         .getName());
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
/* 163 */     internalValueMap = new Internal.EnumLiteMap<HpkeKem>()
/*     */       {
/*     */         public HpkeKem findValueByNumber(int number) {
/* 166 */           return HpkeKem.forNumber(number);
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
/* 187 */     VALUES = values();
/*     */   }
/*     */   public static final int DHKEM_P256_HKDF_SHA256_VALUE = 2; public static final int DHKEM_P384_HKDF_SHA384_VALUE = 3; public static final int DHKEM_P521_HKDF_SHA512_VALUE = 4;
/*     */   public static final int X_WING_VALUE = 5;
/*     */   public static final int ML_KEM768_VALUE = 6;
/*     */   public static final int ML_KEM1024_VALUE = 7;
/*     */   private static final Internal.EnumLiteMap<HpkeKem> internalValueMap;
/*     */   private static final HpkeKem[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   HpkeKem(int value) {
/* 204 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static HpkeKem forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return KEM_UNKNOWN;
/*     */       case 1:
/*     */         return DHKEM_X25519_HKDF_SHA256;
/*     */       case 2:
/*     */         return DHKEM_P256_HKDF_SHA256;
/*     */       case 3:
/*     */         return DHKEM_P384_HKDF_SHA384;
/*     */       case 4:
/*     */         return DHKEM_P521_HKDF_SHA512;
/*     */       case 5:
/*     */         return X_WING;
/*     */       case 6:
/*     */         return ML_KEM768;
/*     */       case 7:
/*     */         return ML_KEM1024;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<HpkeKem> internalGetValueMap() {
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
/*     */     return Hpke.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkeKem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */