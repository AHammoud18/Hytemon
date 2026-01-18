/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
/*    */ import org.bson.conversions.Bson;
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
/*    */ public class BsonCodec
/*    */   implements Codec<Bson>
/*    */ {
/* 34 */   private static final Codec<BsonDocument> BSON_DOCUMENT_CODEC = new BsonDocumentCodec();
/*    */ 
/*    */ 
/*    */   
/*    */   private final CodecRegistry registry;
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonCodec(CodecRegistry registry) {
/* 43 */     this.registry = registry;
/*    */   }
/*    */ 
/*    */   
/*    */   public Bson decode(BsonReader reader, DecoderContext decoderContext) {
/* 48 */     throw new UnsupportedOperationException("The BsonCodec can only encode to Bson");
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, Bson value, EncoderContext encoderContext) {
/*    */     try {
/* 54 */       BsonDocument bsonDocument = value.toBsonDocument(BsonDocument.class, this.registry);
/* 55 */       BSON_DOCUMENT_CODEC.encode(writer, bsonDocument, encoderContext);
/* 56 */     } catch (Exception e) {
/* 57 */       throw new CodecConfigurationException(String.format("Unable to encode a Bson implementation: %s", new Object[] { value }), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Bson> getEncoderClass() {
/* 63 */     return Bson.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */