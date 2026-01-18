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
/*     */ public enum HpkeAead
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   AEAD_UNKNOWN(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   AES_128_GCM(1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   AES_256_GCM(2),
/*     */ 
/*     */ 
/*     */   
/*  29 */   CHACHA20_POLY1305(3),
/*  30 */   UNRECOGNIZED(-1);
/*     */   public static final int AEAD_UNKNOWN_VALUE = 0;
/*     */   
/*     */   static {
/*  34 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HpkeAead.class
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
/*  97 */     internalValueMap = new Internal.EnumLiteMap<HpkeAead>()
/*     */       {
/*     */         public HpkeAead findValueByNumber(int number) {
/* 100 */           return HpkeAead.forNumber(number);
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
/*     */   public static final int AES_128_GCM_VALUE = 1;
/*     */   public static final int AES_256_GCM_VALUE = 2;
/*     */   public static final int CHACHA20_POLY1305_VALUE = 3;
/*     */   private static final Internal.EnumLiteMap<HpkeAead> internalValueMap;
/*     */   private static final HpkeAead[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   HpkeAead(int value) {
/* 138 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static HpkeAead forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return AEAD_UNKNOWN;
/*     */       case 1:
/*     */         return AES_128_GCM;
/*     */       case 2:
/*     */         return AES_256_GCM;
/*     */       case 3:
/*     */         return CHACHA20_POLY1305;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<HpkeAead> internalGetValueMap() {
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
/*     */     return Hpke.getDescriptor().getEnumTypes().get(2);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkeAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */