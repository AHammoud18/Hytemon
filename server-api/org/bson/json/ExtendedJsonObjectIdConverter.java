/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.types.ObjectId;
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
/*    */ class ExtendedJsonObjectIdConverter
/*    */   implements Converter<ObjectId>
/*    */ {
/*    */   public void convert(ObjectId value, StrictJsonWriter writer) {
/* 25 */     writer.writeStartObject();
/* 26 */     writer.writeString("$oid", value.toHexString());
/* 27 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonObjectIdConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */