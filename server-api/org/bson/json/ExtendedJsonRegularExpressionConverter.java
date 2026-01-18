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
/*    */ class ExtendedJsonRegularExpressionConverter
/*    */   implements Converter<BsonRegularExpression>
/*    */ {
/*    */   public void convert(BsonRegularExpression value, StrictJsonWriter writer) {
/* 24 */     writer.writeStartObject();
/* 25 */     writer.writeStartObject("$regularExpression");
/* 26 */     writer.writeString("pattern", value.getPattern());
/* 27 */     writer.writeString("options", value.getOptions());
/* 28 */     writer.writeEndObject();
/* 29 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonRegularExpressionConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */