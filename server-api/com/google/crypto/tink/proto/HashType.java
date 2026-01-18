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
/*     */ public enum HashType
/*     */   implements ProtocolMessageEnum
/*     */ {
/*     */   public static final int UNKNOWN_HASH_VALUE = 0;
/*  17 */   UNKNOWN_HASH(0),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   SHA1(1),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   SHA384(2),
/*     */ 
/*     */ 
/*     */   
/*  37 */   SHA256(3),
/*     */ 
/*     */ 
/*     */   
/*  41 */   SHA512(4),
/*     */ 
/*     */ 
/*     */   
/*  45 */   SHA224(5),
/*  46 */   UNRECOGNIZED(-1);
/*     */   public static final int SHA1_VALUE = 1;
/*     */   
/*     */   static {
/*  50 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HashType.class
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     internalValueMap = new Internal.EnumLiteMap<HashType>()
/*     */       {
/*     */         public HashType findValueByNumber(int number) {
/* 134 */           return HashType.forNumber(number);
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
/* 155 */     VALUES = values();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SHA384_VALUE = 2;
/*     */ 
/*     */   
/*     */   public static final int SHA256_VALUE = 3;
/*     */ 
/*     */   
/*     */   public static final int SHA512_VALUE = 4;
/*     */   
/*     */   public static final int SHA224_VALUE = 5;
/*     */ 
/*     */   
/*     */   HashType(int value) {
/* 172 */     this.value = value;
/*     */   }
/*     */   
/*     */   private static final Internal.EnumLiteMap<HashType> internalValueMap;
/*     */   private static final HashType[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   public static HashType forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return UNKNOWN_HASH;
/*     */       case 1:
/*     */         return SHA1;
/*     */       case 2:
/*     */         return SHA384;
/*     */       case 3:
/*     */         return SHA256;
/*     */       case 4:
/*     */         return SHA512;
/*     */       case 5:
/*     */         return SHA224;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<HashType> internalGetValueMap() {
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
/*     */     return Common.getDescriptor().getEnumTypes().get(2);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HashType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */