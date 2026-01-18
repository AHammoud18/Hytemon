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
/*     */ public enum EllipticCurveType
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   UNKNOWN_CURVE(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   NIST_P256(2),
/*     */ 
/*     */ 
/*     */   
/*  25 */   NIST_P384(3),
/*     */ 
/*     */ 
/*     */   
/*  29 */   NIST_P521(4),
/*     */ 
/*     */ 
/*     */   
/*  33 */   CURVE25519(5),
/*  34 */   UNRECOGNIZED(-1);
/*     */   public static final int UNKNOWN_CURVE_VALUE = 0;
/*     */   
/*     */   static {
/*  38 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EllipticCurveType.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  44 */         .getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     internalValueMap = new Internal.EnumLiteMap<EllipticCurveType>()
/*     */       {
/*     */         public EllipticCurveType findValueByNumber(int number) {
/* 109 */           return EllipticCurveType.forNumber(number);
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
/* 130 */     VALUES = values();
/*     */   }
/*     */   public static final int NIST_P256_VALUE = 2;
/*     */   public static final int NIST_P384_VALUE = 3;
/*     */   public static final int NIST_P521_VALUE = 4;
/*     */   public static final int CURVE25519_VALUE = 5;
/*     */   private static final Internal.EnumLiteMap<EllipticCurveType> internalValueMap;
/*     */   private static final EllipticCurveType[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   EllipticCurveType(int value) {
/* 147 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static EllipticCurveType forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return UNKNOWN_CURVE;
/*     */       case 2:
/*     */         return NIST_P256;
/*     */       case 3:
/*     */         return NIST_P384;
/*     */       case 4:
/*     */         return NIST_P521;
/*     */       case 5:
/*     */         return CURVE25519;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<EllipticCurveType> internalGetValueMap() {
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
/*     */     return Common.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EllipticCurveType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */