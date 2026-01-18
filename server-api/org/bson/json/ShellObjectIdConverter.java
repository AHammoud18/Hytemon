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
/*    */ 
/*    */ class ShellObjectIdConverter
/*    */   implements Converter<ObjectId>
/*    */ {
/*    */   public void convert(ObjectId value, StrictJsonWriter writer) {
/* 26 */     writer.writeRaw(String.format("ObjectId(\"%s\")", new Object[] { value.toHexString() }));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ShellObjectIdConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */