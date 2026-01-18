/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonBinaryReader;
/*    */ import org.bson.BsonBinaryWriter;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.RawBsonDocument;
/*    */ import org.bson.io.BasicOutputBuffer;
/*    */ import org.bson.io.BsonInput;
/*    */ import org.bson.io.BsonOutput;
/*    */ import org.bson.io.ByteBufferBsonInput;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RawBsonDocumentCodec
/*    */   implements Codec<RawBsonDocument>
/*    */ {
/*    */   public void encode(BsonWriter writer, RawBsonDocument value, EncoderContext encoderContext) {
/* 43 */     BsonBinaryReader reader = new BsonBinaryReader((BsonInput)new ByteBufferBsonInput(value.getByteBuffer()));
/*    */     try {
/* 45 */       writer.pipe((BsonReader)reader);
/*    */     } finally {
/* 47 */       reader.close();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public RawBsonDocument decode(BsonReader reader, DecoderContext decoderContext) {
/* 53 */     BasicOutputBuffer buffer = new BasicOutputBuffer(0);
/* 54 */     BsonBinaryWriter writer = new BsonBinaryWriter((BsonOutput)buffer);
/*    */     try {
/* 56 */       writer.pipe(reader);
/* 57 */       return new RawBsonDocument(buffer.getInternalBuffer(), 0, buffer.getPosition());
/*    */     } finally {
/* 59 */       writer.close();
/* 60 */       buffer.close();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<RawBsonDocument> getEncoderClass() {
/* 66 */     return RawBsonDocument.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\RawBsonDocumentCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */