/*     */ package org.bson;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum BsonType
/*     */ {
/*  28 */   END_OF_DOCUMENT(0),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   DOUBLE(1),
/*     */ 
/*     */ 
/*     */   
/*  37 */   STRING(2),
/*     */ 
/*     */ 
/*     */   
/*  41 */   DOCUMENT(3),
/*     */ 
/*     */ 
/*     */   
/*  45 */   ARRAY(4),
/*     */ 
/*     */ 
/*     */   
/*  49 */   BINARY(5),
/*     */ 
/*     */ 
/*     */   
/*  53 */   UNDEFINED(6),
/*     */ 
/*     */ 
/*     */   
/*  57 */   OBJECT_ID(7),
/*     */ 
/*     */ 
/*     */   
/*  61 */   BOOLEAN(8),
/*     */ 
/*     */ 
/*     */   
/*  65 */   DATE_TIME(9),
/*     */ 
/*     */ 
/*     */   
/*  69 */   NULL(10),
/*     */ 
/*     */ 
/*     */   
/*  73 */   REGULAR_EXPRESSION(11),
/*     */ 
/*     */ 
/*     */   
/*  77 */   DB_POINTER(12),
/*     */ 
/*     */ 
/*     */   
/*  81 */   JAVASCRIPT(13),
/*     */ 
/*     */ 
/*     */   
/*  85 */   SYMBOL(14),
/*     */ 
/*     */ 
/*     */   
/*  89 */   JAVASCRIPT_WITH_SCOPE(15),
/*     */ 
/*     */ 
/*     */   
/*  93 */   INT32(16),
/*     */ 
/*     */ 
/*     */   
/*  97 */   TIMESTAMP(17),
/*     */ 
/*     */ 
/*     */   
/* 101 */   INT64(18),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   DECIMAL128(19),
/*     */ 
/*     */ 
/*     */   
/* 111 */   MIN_KEY(255),
/*     */ 
/*     */ 
/*     */   
/* 115 */   MAX_KEY(127); private static final BsonType[] LOOKUP_TABLE;
/*     */   static {
/* 117 */     LOOKUP_TABLE = new BsonType[MIN_KEY.getValue() + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     for (BsonType cur : values())
/* 123 */       LOOKUP_TABLE[cur.getValue()] = cur; 
/*     */   }
/*     */   private final int value;
/*     */   
/*     */   BsonType(int value) {
/* 128 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 137 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BsonType findByValue(int value) {
/* 147 */     return LOOKUP_TABLE[value & 0xFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContainer() {
/* 156 */     return (this == DOCUMENT || this == ARRAY);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */