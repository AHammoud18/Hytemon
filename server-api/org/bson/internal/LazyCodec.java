/*    */ package org.bson.internal;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.DecoderContext;
/*    */ import org.bson.codecs.EncoderContext;
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
/*    */ class LazyCodec<T>
/*    */   implements Codec<T>
/*    */ {
/*    */   private final CodecRegistry registry;
/*    */   private final Class<T> clazz;
/*    */   private volatile Codec<T> wrapped;
/*    */   
/*    */   LazyCodec(CodecRegistry registry, Class<T> clazz) {
/* 32 */     this.registry = registry;
/* 33 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
/* 38 */     getWrapped().encode(writer, value, encoderContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<T> getEncoderClass() {
/* 43 */     return this.clazz;
/*    */   }
/*    */ 
/*    */   
/*    */   public T decode(BsonReader reader, DecoderContext decoderContext) {
/* 48 */     return (T)getWrapped().decode(reader, decoderContext);
/*    */   }
/*    */   
/*    */   private Codec<T> getWrapped() {
/* 52 */     if (this.wrapped == null) {
/* 53 */       this.wrapped = this.registry.get(this.clazz);
/*    */     }
/*    */     
/* 56 */     return this.wrapped;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\LazyCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */