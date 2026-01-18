/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.codecs.Codec;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MapPropertyCodecProvider
/*     */   implements PropertyCodecProvider
/*     */ {
/*     */   public <T> Codec<T> get(TypeWithTypeParameters<T> type, PropertyCodecRegistry registry) {
/*  37 */     if (Map.class.isAssignableFrom(type.getType()) && type.getTypeParameters().size() == 2) {
/*  38 */       Class<?> keyType = ((TypeWithTypeParameters)type.getTypeParameters().get(0)).getType();
/*  39 */       if (!keyType.equals(String.class)) {
/*  40 */         throw new CodecConfigurationException(String.format("Invalid Map type. Maps MUST have string keys, found %s instead.", new Object[] { keyType }));
/*     */       }
/*     */       
/*     */       try {
/*  44 */         return new MapCodec((Class)type.getType(), registry.get(type.getTypeParameters().get(1)));
/*  45 */       } catch (CodecConfigurationException e) {
/*  46 */         if (((TypeWithTypeParameters<Object>)type.getTypeParameters().get(1)).getType() == Object.class) {
/*     */           try {
/*  48 */             return registry.get(TypeData.<T>builder((Class)Map.class).build());
/*  49 */           } catch (CodecConfigurationException codecConfigurationException) {}
/*     */         }
/*     */ 
/*     */         
/*  53 */         throw e;
/*     */       } 
/*     */     } 
/*  56 */     return null;
/*     */   }
/*     */   
/*     */   private static class MapCodec<T>
/*     */     implements Codec<Map<String, T>> {
/*     */     private final Class<Map<String, T>> encoderClass;
/*     */     private final Codec<T> codec;
/*     */     
/*     */     MapCodec(Class<Map<String, T>> encoderClass, Codec<T> codec) {
/*  65 */       this.encoderClass = encoderClass;
/*  66 */       this.codec = codec;
/*     */     }
/*     */ 
/*     */     
/*     */     public void encode(BsonWriter writer, Map<String, T> map, EncoderContext encoderContext) {
/*  71 */       writer.writeStartDocument();
/*  72 */       for (Map.Entry<String, T> entry : map.entrySet()) {
/*  73 */         writer.writeName(entry.getKey());
/*  74 */         if (entry.getValue() == null) {
/*  75 */           writer.writeNull(); continue;
/*     */         } 
/*  77 */         this.codec.encode(writer, entry.getValue(), encoderContext);
/*     */       } 
/*     */       
/*  80 */       writer.writeEndDocument();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, T> decode(BsonReader reader, DecoderContext context) {
/*  85 */       reader.readStartDocument();
/*  86 */       Map<String, T> map = getInstance();
/*  87 */       while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  88 */         if (reader.getCurrentBsonType() == BsonType.NULL) {
/*  89 */           map.put(reader.readName(), null);
/*  90 */           reader.readNull(); continue;
/*     */         } 
/*  92 */         map.put(reader.readName(), (T)this.codec.decode(reader, context));
/*     */       } 
/*     */       
/*  95 */       reader.readEndDocument();
/*  96 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<Map<String, T>> getEncoderClass() {
/* 101 */       return this.encoderClass;
/*     */     }
/*     */     
/*     */     private Map<String, T> getInstance() {
/* 105 */       if (this.encoderClass.isInterface()) {
/* 106 */         return new HashMap<>();
/*     */       }
/*     */       try {
/* 109 */         return this.encoderClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 110 */       } catch (Exception e) {
/* 111 */         throw new CodecConfigurationException(e.getMessage(), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\MapPropertyCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */