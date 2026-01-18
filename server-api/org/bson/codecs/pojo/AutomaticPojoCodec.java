/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
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
/*    */ 
/*    */ final class AutomaticPojoCodec<T>
/*    */   extends PojoCodec<T>
/*    */ {
/*    */   private final PojoCodec<T> pojoCodec;
/*    */   
/*    */   AutomaticPojoCodec(PojoCodec<T> pojoCodec) {
/* 31 */     this.pojoCodec = pojoCodec;
/*    */   }
/*    */ 
/*    */   
/*    */   public T decode(BsonReader reader, DecoderContext decoderContext) {
/*    */     try {
/* 37 */       return (T)this.pojoCodec.decode(reader, decoderContext);
/* 38 */     } catch (CodecConfigurationException e) {
/* 39 */       throw new CodecConfigurationException(
/* 40 */           String.format("An exception occurred when decoding using the AutomaticPojoCodec.%nDecoding into a '%s' failed with the following exception:%n%n%s%n%nA custom Codec or PojoCodec may need to be explicitly configured and registered to handle this type.", new Object[] {
/*    */ 
/*    */               
/* 43 */               this.pojoCodec.getEncoderClass().getSimpleName(), e.getMessage()
/*    */             }), e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
/*    */     try {
/* 50 */       this.pojoCodec.encode(writer, value, encoderContext);
/* 51 */     } catch (CodecConfigurationException e) {
/* 52 */       throw new CodecConfigurationException(
/* 53 */           String.format("An exception occurred when encoding using the AutomaticPojoCodec.%nEncoding a %s: '%s' failed with the following exception:%n%n%s%n%nA custom Codec or PojoCodec may need to be explicitly configured and registered to handle this type.", new Object[] {
/*    */ 
/*    */               
/* 56 */               getEncoderClass().getSimpleName(), value, e.getMessage()
/*    */             }), e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Class<T> getEncoderClass() {
/* 62 */     return this.pojoCodec.getEncoderClass();
/*    */   }
/*    */ 
/*    */   
/*    */   ClassModel<T> getClassModel() {
/* 67 */     return this.pojoCodec.getClassModel();
/*    */   }
/*    */ 
/*    */   
/*    */   DiscriminatorLookup getDiscriminatorLookup() {
/* 72 */     return this.pojoCodec.getDiscriminatorLookup();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\AutomaticPojoCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */