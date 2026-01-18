/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonDocumentWrapper;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
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
/*    */ public class BsonDocumentWrapperCodec
/*    */   implements Codec<BsonDocumentWrapper>
/*    */ {
/*    */   private final Codec<BsonDocument> bsonDocumentCodec;
/*    */   
/*    */   public BsonDocumentWrapperCodec(Codec<BsonDocument> bsonDocumentCodec) {
/* 40 */     this.bsonDocumentCodec = bsonDocumentCodec;
/*    */   }
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
/*    */   public BsonDocumentWrapper decode(BsonReader reader, DecoderContext decoderContext) {
/* 53 */     throw new UnsupportedOperationException("Decoding into a BsonDocumentWrapper is not allowed");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonDocumentWrapper value, EncoderContext encoderContext) {
/* 59 */     if (value.isUnwrapped()) {
/* 60 */       this.bsonDocumentCodec.encode(writer, value, encoderContext);
/*    */     } else {
/* 62 */       Encoder<Object> encoder = value.getEncoder();
/* 63 */       encoder.encode(writer, value.getWrappedDocument(), encoderContext);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonDocumentWrapper> getEncoderClass() {
/* 69 */     return BsonDocumentWrapper.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonDocumentWrapperCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */