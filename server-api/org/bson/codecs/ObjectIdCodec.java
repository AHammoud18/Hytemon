/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectIdCodec
/*    */   implements Codec<ObjectId>
/*    */ {
/*    */   public void encode(BsonWriter writer, ObjectId value, EncoderContext encoderContext) {
/* 31 */     writer.writeObjectId(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectId decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     return reader.readObjectId();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<ObjectId> getEncoderClass() {
/* 41 */     return ObjectId.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\ObjectIdCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */