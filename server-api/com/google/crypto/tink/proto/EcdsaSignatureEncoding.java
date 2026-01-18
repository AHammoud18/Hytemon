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
/*     */ public enum EcdsaSignatureEncoding
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   UNKNOWN_ENCODING(0),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   IEEE_P1363(1),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   DER(2),
/*  41 */   UNRECOGNIZED(-1);
/*     */   public static final int UNKNOWN_ENCODING_VALUE = 0;
/*     */   
/*     */   static {
/*  45 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EcdsaSignatureEncoding.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  51 */         .getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     internalValueMap = new Internal.EnumLiteMap<EcdsaSignatureEncoding>()
/*     */       {
/*     */         public EcdsaSignatureEncoding findValueByNumber(int number) {
/* 121 */           return EcdsaSignatureEncoding.forNumber(number);
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
/* 142 */     VALUES = values();
/*     */   }
/*     */   
/*     */   public static final int IEEE_P1363_VALUE = 1;
/*     */   public static final int DER_VALUE = 2;
/*     */   private static final Internal.EnumLiteMap<EcdsaSignatureEncoding> internalValueMap;
/*     */   private static final EcdsaSignatureEncoding[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED) {
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
/*     */     }
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   EcdsaSignatureEncoding(int value) {
/* 159 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static EcdsaSignatureEncoding forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return UNKNOWN_ENCODING;
/*     */       case 1:
/*     */         return IEEE_P1363;
/*     */       case 2:
/*     */         return DER;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<EcdsaSignatureEncoding> internalGetValueMap() {
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
/*     */     return Ecdsa.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EcdsaSignatureEncoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */