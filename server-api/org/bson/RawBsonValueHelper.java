/*    */ package org.bson;
/*    */ 
/*    */ import org.bson.codecs.BsonValueCodecProvider;
/*    */ import org.bson.codecs.DecoderContext;
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistries;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
/*    */ import org.bson.io.BsonInputMark;
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
/*    */ final class RawBsonValueHelper
/*    */ {
/* 28 */   private static final CodecRegistry REGISTRY = CodecRegistries.fromProviders(new CodecProvider[] { (CodecProvider)new BsonValueCodecProvider() });
/*    */   
/*    */   static BsonValue decode(byte[] bytes, BsonBinaryReader bsonReader) {
/* 31 */     if (bsonReader.getCurrentBsonType() == BsonType.DOCUMENT || bsonReader.getCurrentBsonType() == BsonType.ARRAY) {
/* 32 */       int position = bsonReader.getBsonInput().getPosition();
/* 33 */       BsonInputMark mark = bsonReader.getBsonInput().getMark(4);
/* 34 */       int size = bsonReader.getBsonInput().readInt32();
/* 35 */       mark.reset();
/* 36 */       bsonReader.skipValue();
/* 37 */       if (bsonReader.getCurrentBsonType() == BsonType.DOCUMENT) {
/* 38 */         return new RawBsonDocument(bytes, position, size);
/*    */       }
/* 40 */       return new RawBsonArray(bytes, position, size);
/*    */     } 
/*    */     
/* 43 */     return (BsonValue)REGISTRY.get(BsonValueCodecProvider.getClassForBsonType(bsonReader.getCurrentBsonType())).decode(bsonReader, DecoderContext.builder().build());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\RawBsonValueHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */