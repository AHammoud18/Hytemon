/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonBinary;
/*    */ import org.bson.internal.Base64;
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
/*    */ class ExtendedJsonBinaryConverter
/*    */   implements Converter<BsonBinary>
/*    */ {
/*    */   public void convert(BsonBinary value, StrictJsonWriter writer) {
/* 26 */     writer.writeStartObject();
/* 27 */     writer.writeStartObject("$binary");
/* 28 */     writer.writeString("base64", Base64.encode(value.getData()));
/* 29 */     writer.writeString("subType", String.format("%02X", new Object[] { Byte.valueOf(value.getType()) }));
/* 30 */     writer.writeEndObject();
/* 31 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonBinaryConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */