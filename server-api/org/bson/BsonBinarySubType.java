/*    */ package org.bson;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum BsonBinarySubType
/*    */ {
/* 28 */   BINARY((byte)0),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   FUNCTION((byte)1),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   OLD_BINARY((byte)2),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   UUID_LEGACY((byte)3),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   UUID_STANDARD((byte)4),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   MD5((byte)5),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   USER_DEFINED(-128);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final byte value;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isUuid(byte value) {
/* 70 */     return (value == UUID_LEGACY.getValue() || value == UUID_STANDARD.getValue());
/*    */   }
/*    */   
/*    */   BsonBinarySubType(byte value) {
/* 74 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getValue() {
/* 83 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonBinarySubType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */