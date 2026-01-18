/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.DecoderContext;
/*    */ import org.bson.codecs.EncoderContext;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*    */ class LazyMissingCodec<S>
/*    */   implements Codec<S>
/*    */ {
/*    */   private final Class<S> clazz;
/*    */   private final CodecConfigurationException exception;
/*    */   
/*    */   LazyMissingCodec(Class<S> clazz, CodecConfigurationException exception) {
/* 32 */     this.clazz = clazz;
/* 33 */     this.exception = exception;
/*    */   }
/*    */ 
/*    */   
/*    */   public S decode(BsonReader reader, DecoderContext decoderContext) {
/* 38 */     throw this.exception;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, S value, EncoderContext encoderContext) {
/* 43 */     throw this.exception;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<S> getEncoderClass() {
/* 48 */     return this.clazz;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\LazyMissingCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */