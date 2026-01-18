/*    */ package org.bson.json;
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
/*    */ enum JsonTokenType
/*    */ {
/* 23 */   INVALID,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   BEGIN_ARRAY,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   BEGIN_OBJECT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   END_ARRAY,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   LEFT_PAREN,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   RIGHT_PAREN,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   END_OBJECT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   COLON,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   COMMA,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   DOUBLE,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 73 */   INT32,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 78 */   INT64,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 83 */   REGULAR_EXPRESSION,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 88 */   STRING,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 93 */   UNQUOTED_STRING,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 98 */   END_OF_FILE;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonTokenType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */