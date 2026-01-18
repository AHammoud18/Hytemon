/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum JavaType
/*    */ {
/* 13 */   VOID(Void.class, Void.class, null),
/* 14 */   INT(int.class, Integer.class, Integer.valueOf(0)),
/* 15 */   LONG(long.class, Long.class, Long.valueOf(0L)),
/* 16 */   FLOAT(float.class, Float.class, Float.valueOf(0.0F)),
/* 17 */   DOUBLE(double.class, Double.class, Double.valueOf(0.0D)),
/* 18 */   BOOLEAN(boolean.class, Boolean.class, Boolean.valueOf(false)),
/* 19 */   STRING(String.class, String.class, ""),
/* 20 */   BYTE_STRING(ByteString.class, ByteString.class, ByteString.EMPTY),
/* 21 */   ENUM(int.class, Integer.class, null),
/* 22 */   MESSAGE(Object.class, Object.class, null);
/*    */   
/*    */   private final Class<?> type;
/*    */   private final Class<?> boxedType;
/*    */   private final Object defaultDefault;
/*    */   
/*    */   JavaType(Class<?> type, Class<?> boxedType, Object defaultDefault) {
/* 29 */     this.type = type;
/* 30 */     this.boxedType = boxedType;
/* 31 */     this.defaultDefault = defaultDefault;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getDefaultDefault() {
/* 36 */     return this.defaultDefault;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> getType() {
/* 41 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> getBoxedType() {
/* 46 */     return this.boxedType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidType(Class<?> t) {
/* 51 */     return this.type.isAssignableFrom(t);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\JavaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */