/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonBinary;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.types.Binary;
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
/*    */ public class BinaryCodec
/*    */   implements Codec<Binary>
/*    */ {
/*    */   public void encode(BsonWriter writer, Binary value, EncoderContext encoderContext) {
/* 32 */     writer.writeBinaryData(new BsonBinary(value.getType(), value.getData()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Binary decode(BsonReader reader, DecoderContext decoderContext) {
/* 37 */     BsonBinary bsonBinary = reader.readBinaryData();
/* 38 */     return new Binary(bsonBinary.getType(), bsonBinary.getData());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Binary> getEncoderClass() {
/* 43 */     return Binary.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BinaryCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */