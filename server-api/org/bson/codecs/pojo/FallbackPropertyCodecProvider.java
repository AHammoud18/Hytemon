/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import org.bson.codecs.Codec;
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
/*    */ final class FallbackPropertyCodecProvider
/*    */   implements PropertyCodecProvider
/*    */ {
/*    */   private final CodecRegistry codecRegistry;
/*    */   private final PojoCodec<?> pojoCodec;
/*    */   
/*    */   FallbackPropertyCodecProvider(PojoCodec<?> pojoCodec, CodecRegistry codecRegistry) {
/* 27 */     this.pojoCodec = pojoCodec;
/* 28 */     this.codecRegistry = codecRegistry;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <S> Codec<S> get(TypeWithTypeParameters<S> type, PropertyCodecRegistry propertyCodecRegistry) {
/* 34 */     Class<S> clazz = type.getType();
/* 35 */     if (clazz == this.pojoCodec.getEncoderClass()) {
/* 36 */       return (Codec)this.pojoCodec;
/*    */     }
/* 38 */     return this.codecRegistry.get(type.getType());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\FallbackPropertyCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */