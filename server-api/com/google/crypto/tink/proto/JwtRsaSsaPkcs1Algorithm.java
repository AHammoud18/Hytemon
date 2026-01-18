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
/*     */ public enum JwtRsaSsaPkcs1Algorithm
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  21 */   RS_UNKNOWN(0),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   RS256(1),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   RS384(2),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   RS512(3),
/*  46 */   UNRECOGNIZED(-1);
/*     */   public static final int RS_UNKNOWN_VALUE = 0;
/*     */   
/*     */   static {
/*  50 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtRsaSsaPkcs1Algorithm.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  56 */         .getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     internalValueMap = new Internal.EnumLiteMap<JwtRsaSsaPkcs1Algorithm>()
/*     */       {
/*     */         public JwtRsaSsaPkcs1Algorithm findValueByNumber(int number) {
/* 128 */           return JwtRsaSsaPkcs1Algorithm.forNumber(number);
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
/* 149 */     VALUES = values();
/*     */   }
/*     */   
/*     */   public static final int RS256_VALUE = 1;
/*     */   public static final int RS384_VALUE = 2;
/*     */   public static final int RS512_VALUE = 3;
/*     */   private static final Internal.EnumLiteMap<JwtRsaSsaPkcs1Algorithm> internalValueMap;
/*     */   private static final JwtRsaSsaPkcs1Algorithm[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   JwtRsaSsaPkcs1Algorithm(int value) {
/* 166 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static JwtRsaSsaPkcs1Algorithm forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return RS_UNKNOWN;
/*     */       case 1:
/*     */         return RS256;
/*     */       case 2:
/*     */         return RS384;
/*     */       case 3:
/*     */         return RS512;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<JwtRsaSsaPkcs1Algorithm> internalGetValueMap() {
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
/*     */     return JwtRsaSsaPkcs1.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPkcs1Algorithm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */