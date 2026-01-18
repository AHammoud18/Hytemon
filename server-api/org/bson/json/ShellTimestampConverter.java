/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonTimestamp;
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
/*    */ class ShellTimestampConverter
/*    */   implements Converter<BsonTimestamp>
/*    */ {
/*    */   public void convert(BsonTimestamp value, StrictJsonWriter writer) {
/* 26 */     writer.writeRaw(String.format("Timestamp(%d, %d)", new Object[] { Integer.valueOf(value.getTime()), Integer.valueOf(value.getInc()) }));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ShellTimestampConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */