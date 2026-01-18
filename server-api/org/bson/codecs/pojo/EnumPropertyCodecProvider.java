/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.DecoderContext;
/*    */ import org.bson.codecs.EncoderContext;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*    */ final class EnumPropertyCodecProvider
/*    */   implements PropertyCodecProvider
/*    */ {
/*    */   private final CodecRegistry codecRegistry;
/*    */   
/*    */   EnumPropertyCodecProvider(CodecRegistry codecRegistry) {
/* 32 */     this.codecRegistry = codecRegistry;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(TypeWithTypeParameters<T> type, PropertyCodecRegistry propertyCodecRegistry) {
/* 38 */     Class<T> clazz = type.getType();
/* 39 */     if (Enum.class.isAssignableFrom(clazz)) {
/*    */       try {
/* 41 */         return this.codecRegistry.get(clazz);
/* 42 */       } catch (CodecConfigurationException e) {
/* 43 */         return (Codec)new EnumCodec<>((Class)clazz);
/*    */       } 
/*    */     }
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   private static class EnumCodec<T extends Enum<T>> implements Codec<T> {
/*    */     private final Class<T> clazz;
/*    */     
/*    */     EnumCodec(Class<T> clazz) {
/* 53 */       this.clazz = clazz;
/*    */     }
/*    */ 
/*    */     
/*    */     public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
/* 58 */       writer.writeString(value.name());
/*    */     }
/*    */ 
/*    */     
/*    */     public Class<T> getEncoderClass() {
/* 63 */       return this.clazz;
/*    */     }
/*    */ 
/*    */     
/*    */     public T decode(BsonReader reader, DecoderContext decoderContext) {
/* 68 */       return Enum.valueOf(this.clazz, reader.readString());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\EnumPropertyCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */