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
/*     */ public enum SlhDsaSignatureType
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   SLH_DSA_SIGNATURE_TYPE_UNSPECIFIED(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   FAST_SIGNING(1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   SMALL_SIGNATURE(2),
/*  26 */   UNRECOGNIZED(-1);
/*     */   public static final int SLH_DSA_SIGNATURE_TYPE_UNSPECIFIED_VALUE = 0;
/*     */   
/*     */   static {
/*  30 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", SlhDsaSignatureType.class
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
/*  88 */     internalValueMap = new Internal.EnumLiteMap<SlhDsaSignatureType>()
/*     */       {
/*     */         public SlhDsaSignatureType findValueByNumber(int number) {
/*  91 */           return SlhDsaSignatureType.forNumber(number);
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
/*     */   public static final int FAST_SIGNING_VALUE = 1;
/*     */   public static final int SMALL_SIGNATURE_VALUE = 2;
/*     */   private static final Internal.EnumLiteMap<SlhDsaSignatureType> internalValueMap;
/*     */   private static final SlhDsaSignatureType[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED) {
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
/*     */     }
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   SlhDsaSignatureType(int value) {
/* 129 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static SlhDsaSignatureType forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return SLH_DSA_SIGNATURE_TYPE_UNSPECIFIED;
/*     */       case 1:
/*     */         return FAST_SIGNING;
/*     */       case 2:
/*     */         return SMALL_SIGNATURE;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<SlhDsaSignatureType> internalGetValueMap() {
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
/*     */     return SlhDsa.getDescriptor().getEnumTypes().get(1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\SlhDsaSignatureType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */