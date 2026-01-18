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
/*    */ 
/*    */ class ShellBinaryConverter
/*    */   implements Converter<BsonBinary>
/*    */ {
/*    */   public void convert(BsonBinary value, StrictJsonWriter writer) {
/* 27 */     writer.writeRaw(String.format("new BinData(%s, \"%s\")", new Object[] { Integer.toString(value.getType() & 0xFF), 
/* 28 */             Base64.encode(value.getData()) }));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ShellBinaryConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */