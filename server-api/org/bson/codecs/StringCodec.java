/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonInvalidOperationException;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonType;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*    */ 
/*    */ 
/*    */ public class StringCodec
/*    */   implements Codec<String>, RepresentationConfigurable<String>
/*    */ {
/*    */   private BsonType representation;
/*    */   
/*    */   public StringCodec() {
/* 38 */     this.representation = BsonType.STRING;
/*    */   }
/*    */   
/*    */   private StringCodec(BsonType representation) {
/* 42 */     this.representation = representation;
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonType getRepresentation() {
/* 47 */     return this.representation;
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<String> withRepresentation(BsonType representation) {
/* 52 */     if (representation != BsonType.OBJECT_ID && representation != BsonType.STRING) {
/* 53 */       throw new CodecConfigurationException(representation + " is not a supported representation for StringCodec");
/*    */     }
/* 55 */     return new StringCodec(representation);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, String value, EncoderContext encoderContext) {
/* 61 */     switch (this.representation) {
/*    */       case STRING:
/* 63 */         writer.writeString(value);
/*    */         return;
/*    */       case OBJECT_ID:
/* 66 */         writer.writeObjectId(new ObjectId(value));
/*    */         return;
/*    */     } 
/* 69 */     throw new BsonInvalidOperationException("Cannot encode a String to a " + this.representation);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String decode(BsonReader reader, DecoderContext decoderContext) {
/* 75 */     switch (this.representation) {
/*    */       case STRING:
/* 77 */         if (reader.getCurrentBsonType() == BsonType.SYMBOL) {
/* 78 */           return reader.readSymbol();
/*    */         }
/* 80 */         return reader.readString();
/*    */       
/*    */       case OBJECT_ID:
/* 83 */         return reader.readObjectId().toHexString();
/*    */     } 
/* 85 */     throw new CodecConfigurationException("Cannot decode " + this.representation + " to a String");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<String> getEncoderClass() {
/* 91 */     return String.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\StringCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */