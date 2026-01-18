/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonRegularExpression;
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
/*    */ class LegacyExtendedJsonRegularExpressionConverter
/*    */   implements Converter<BsonRegularExpression>
/*    */ {
/*    */   public void convert(BsonRegularExpression value, StrictJsonWriter writer) {
/* 24 */     writer.writeStartObject();
/* 25 */     writer.writeString("$regex", value.getPattern());
/* 26 */     writer.writeString("$options", value.getOptions());
/* 27 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\LegacyExtendedJsonRegularExpressionConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */