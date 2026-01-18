/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonNull;
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
/*    */ class JsonNullConverter
/*    */   implements Converter<BsonNull>
/*    */ {
/*    */   public void convert(BsonNull value, StrictJsonWriter writer) {
/* 24 */     writer.writeNull();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonNullConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */