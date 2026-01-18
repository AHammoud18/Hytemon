/*     */ package com.google.protobuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Syntax
/*     */   implements ProtocolMessageEnum
/*     */ {
/*  17 */   SYNTAX_PROTO2(0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   SYNTAX_PROTO3(1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   SYNTAX_EDITIONS(2),
/*  26 */   UNRECOGNIZED(-1);
/*     */   public static final int SYNTAX_PROTO2_VALUE = 0;
/*     */   
/*     */   static {
/*  30 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Syntax");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     internalValueMap = new Internal.EnumLiteMap<Syntax>()
/*     */       {
/*     */         public Syntax findValueByNumber(int number) {
/*  91 */           return Syntax.forNumber(number);
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
/*     */   public static final int SYNTAX_PROTO3_VALUE = 1;
/*     */   public static final int SYNTAX_EDITIONS_VALUE = 2;
/*     */   private static final Internal.EnumLiteMap<Syntax> internalValueMap;
/*     */   private static final Syntax[] VALUES;
/*     */   private final int value;
/*     */   
/*     */   public final int getNumber() {
/*     */     if (this == UNRECOGNIZED) {
/*     */       throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
/*     */     }
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   Syntax(int value) {
/* 129 */     this.value = value;
/*     */   }
/*     */   
/*     */   public static Syntax forNumber(int value) {
/*     */     switch (value) {
/*     */       case 0:
/*     */         return SYNTAX_PROTO2;
/*     */       case 1:
/*     */         return SYNTAX_PROTO3;
/*     */       case 2:
/*     */         return SYNTAX_EDITIONS;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Internal.EnumLiteMap<Syntax> internalGetValueMap() {
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
/*     */     return TypeProto.getDescriptor().getEnumTypes().get(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Syntax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */