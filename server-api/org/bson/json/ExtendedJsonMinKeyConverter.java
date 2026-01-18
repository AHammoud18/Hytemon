/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonMinKey;
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
/*    */ class ExtendedJsonMinKeyConverter
/*    */   implements Converter<BsonMinKey>
/*    */ {
/*    */   public void convert(BsonMinKey value, StrictJsonWriter writer) {
/* 24 */     writer.writeStartObject();
/* 25 */     writer.writeNumber("$minKey", "1");
/* 26 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonMinKeyConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */