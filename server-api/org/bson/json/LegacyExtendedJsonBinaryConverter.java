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
/*    */ class LegacyExtendedJsonBinaryConverter
/*    */   implements Converter<BsonBinary>
/*    */ {
/*    */   public void convert(BsonBinary value, StrictJsonWriter writer) {
/* 26 */     writer.writeStartObject();
/* 27 */     writer.writeString("$binary", Base64.encode(value.getData()));
/* 28 */     writer.writeString("$type", String.format("%02X", new Object[] { Byte.valueOf(value.getType()) }));
/* 29 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\LegacyExtendedJsonBinaryConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */