/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonValue;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistries;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
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
/*    */ public class BsonValueCodec
/*    */   implements Codec<BsonValue>
/*    */ {
/*    */   private final CodecRegistry codecRegistry;
/*    */   
/*    */   public BsonValueCodec() {
/* 41 */     this(CodecRegistries.fromProviders(new CodecProvider[] { new BsonValueCodecProvider() }));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonValueCodec(CodecRegistry codecRegistry) {
/* 50 */     this.codecRegistry = codecRegistry;
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonValue decode(BsonReader reader, DecoderContext decoderContext) {
/* 55 */     return this.codecRegistry.get(BsonValueCodecProvider.getClassForBsonType(reader.getCurrentBsonType())).decode(reader, decoderContext);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonValue value, EncoderContext encoderContext) {
/* 61 */     Codec<BsonValue> codec = this.codecRegistry.get(value.getClass());
/* 62 */     encoderContext.encodeWithChildContext(codec, writer, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonValue> getEncoderClass() {
/* 67 */     return BsonValue.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonValueCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */