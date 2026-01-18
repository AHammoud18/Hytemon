/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonMaxKey;
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
/*    */ class ExtendedJsonMaxKeyConverter
/*    */   implements Converter<BsonMaxKey>
/*    */ {
/*    */   public void convert(BsonMaxKey value, StrictJsonWriter writer) {
/* 24 */     writer.writeStartObject();
/* 25 */     writer.writeNumber("$maxKey", "1");
/* 26 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonMaxKeyConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */