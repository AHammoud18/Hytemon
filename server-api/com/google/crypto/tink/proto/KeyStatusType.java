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
/*     */ public enum KeyStatusType
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   UNKNOWN_STATUS(0),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   ENABLED(1),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   DISABLED(2),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   DESTROYED(3),
/*  42 */   UNRECOGNIZED(-1);
/*     */   public static final int UNKNOWN_STATUS_VALUE = 0;
/*     */   
/*     */   static {
/*  46 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KeyStatusType.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  52 */         .getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     internalValueMap = new Internal.EnumLiteMap<KeyStatusType>()
/*     */       {
/*     */         public KeyStatusType findValueByNumber(int number) {
/* 124 */           return KeyStatusType.forNumber(number);
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
/* 145 */     VALUES = values();
/*     */   }
/*     */   
/*     */   public static final int ENABLED_VALUE = 1;
/*     */   public static final int DISABLED_VALUE = 2;
/*     */   public static final int DESTROYED_VALUE = 3;
/*     */   private static final Internal.EnumLiteMap<KeyStatusType> internalValueMap;
/*     */   private static final KeyStatusType[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED)
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value."); 
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   KeyStatusType(int value) {
/* 162 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static KeyStatusType forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return UNKNOWN_STATUS;
/*     */       case 1:
/*     */         return ENABLED;
/*     */       case 2:
/*     */         return DISABLED;
/*     */       case 3:
/*     */         return DESTROYED;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<KeyStatusType> internalGetValueMap() {
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
/*     */     return Tink.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeyStatusType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */