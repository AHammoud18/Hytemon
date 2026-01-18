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
/*     */ public enum EcPointFormat
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   UNKNOWN_FORMAT(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   UNCOMPRESSED(1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   COMPRESSED(2),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   DO_NOT_USE_CRUNCHY_UNCOMPRESSED(3),
/*  35 */   UNRECOGNIZED(-1);
/*     */   public static final int UNKNOWN_FORMAT_VALUE = 0;
/*     */   
/*     */   static {
/*  39 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EcPointFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  45 */         .getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     internalValueMap = new Internal.EnumLiteMap<EcPointFormat>()
/*     */       {
/*     */         public EcPointFormat findValueByNumber(int number) {
/* 110 */           return EcPointFormat.forNumber(number);
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
/* 131 */     VALUES = values();
/*     */   }
/*     */   
/*     */   public static final int UNCOMPRESSED_VALUE = 1;
/*     */   public static final int COMPRESSED_VALUE = 2;
/*     */   public static final int DO_NOT_USE_CRUNCHY_UNCOMPRESSED_VALUE = 3;
/*     */   private static final Internal.EnumLiteMap<EcPointFormat> internalValueMap;
/*     */   private static final EcPointFormat[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   EcPointFormat(int value) {
/* 148 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static EcPointFormat forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return UNKNOWN_FORMAT;
/*     */       case 1:
/*     */         return UNCOMPRESSED;
/*     */       case 2:
/*     */         return COMPRESSED;
/*     */       case 3:
/*     */         return DO_NOT_USE_CRUNCHY_UNCOMPRESSED;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<EcPointFormat> internalGetValueMap() {
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
/*     */     return Common.getDescriptor().getEnumTypes().get(1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EcPointFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */