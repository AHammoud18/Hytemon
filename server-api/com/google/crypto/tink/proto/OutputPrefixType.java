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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum OutputPrefixType
/*     */   implements ProtocolMessageEnum
/*     */ {
/*     */   public static final int UNKNOWN_PREFIX_VALUE = 0;
/*  39 */   UNKNOWN_PREFIX(0),
/*     */ 
/*     */ 
/*     */   
/*  43 */   TINK(1),
/*     */ 
/*     */ 
/*     */   
/*  47 */   LEGACY(2),
/*     */ 
/*     */ 
/*     */   
/*  51 */   RAW(3),
/*     */ 
/*     */ 
/*     */   
/*  55 */   CRUNCHY(4),
/*     */ 
/*     */ 
/*     */   
/*  59 */   WITH_ID_REQUIREMENT(5),
/*  60 */   UNRECOGNIZED(-1);
/*     */   public static final int TINK_VALUE = 1;
/*     */   
/*     */   static {
/*  64 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", OutputPrefixType.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  70 */         .getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     internalValueMap = new Internal.EnumLiteMap<OutputPrefixType>()
/*     */       {
/*     */         public OutputPrefixType findValueByNumber(int number) {
/* 140 */           return OutputPrefixType.forNumber(number);
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
/* 161 */     VALUES = values();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int LEGACY_VALUE = 2;
/*     */ 
/*     */   
/*     */   public static final int RAW_VALUE = 3;
/*     */ 
/*     */   
/*     */   public static final int CRUNCHY_VALUE = 4;
/*     */   
/*     */   public static final int WITH_ID_REQUIREMENT_VALUE = 5;
/*     */ 
/*     */   
/*     */   OutputPrefixType(int value) {
/* 178 */     this.value = value;
/*     */   }
/*     */   
/*     */   private static final Internal.EnumLiteMap<OutputPrefixType> internalValueMap;
/*     */   private static final OutputPrefixType[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   public static OutputPrefixType forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return UNKNOWN_PREFIX;
/*     */       case 1:
/*     */         return TINK;
/*     */       case 2:
/*     */         return LEGACY;
/*     */       case 3:
/*     */         return RAW;
/*     */       case 4:
/*     */         return CRUNCHY;
/*     */       case 5:
/*     */         return WITH_ID_REQUIREMENT;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<OutputPrefixType> internalGetValueMap() {
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
/*     */     return Tink.getDescriptor().getEnumTypes().get(1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\OutputPrefixType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */