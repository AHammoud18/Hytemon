/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
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
/*     */ final class CollectionPropertyCodecProvider
/*     */   implements PropertyCodecProvider
/*     */ {
/*     */   public <T> Codec<T> get(TypeWithTypeParameters<T> type, PropertyCodecRegistry registry) {
/*  36 */     if (Collection.class.isAssignableFrom(type.getType()) && type.getTypeParameters().size() == 1) {
/*  37 */       return new CollectionCodec((Class)type.getType(), registry.get(type.getTypeParameters().get(0)));
/*     */     }
/*  39 */     return null;
/*     */   }
/*     */   
/*     */   private static class CollectionCodec<T>
/*     */     implements Codec<Collection<T>> {
/*     */     private final Class<Collection<T>> encoderClass;
/*     */     private final Codec<T> codec;
/*     */     
/*     */     CollectionCodec(Class<Collection<T>> encoderClass, Codec<T> codec) {
/*  48 */       this.encoderClass = encoderClass;
/*  49 */       this.codec = codec;
/*     */     }
/*     */ 
/*     */     
/*     */     public void encode(BsonWriter writer, Collection<T> collection, EncoderContext encoderContext) {
/*  54 */       writer.writeStartArray();
/*  55 */       for (T value : collection) {
/*  56 */         if (value == null) {
/*  57 */           writer.writeNull(); continue;
/*     */         } 
/*  59 */         this.codec.encode(writer, value, encoderContext);
/*     */       } 
/*     */       
/*  62 */       writer.writeEndArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<T> decode(BsonReader reader, DecoderContext context) {
/*  67 */       Collection<T> collection = getInstance();
/*  68 */       reader.readStartArray();
/*  69 */       while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  70 */         if (reader.getCurrentBsonType() == BsonType.NULL) {
/*  71 */           collection.add(null);
/*  72 */           reader.readNull(); continue;
/*     */         } 
/*  74 */         collection.add((T)this.codec.decode(reader, context));
/*     */       } 
/*     */       
/*  77 */       reader.readEndArray();
/*  78 */       return collection;
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<Collection<T>> getEncoderClass() {
/*  83 */       return this.encoderClass;
/*     */     }
/*     */     
/*     */     private Collection<T> getInstance() {
/*  87 */       if (this.encoderClass.isInterface()) {
/*  88 */         if (this.encoderClass.isAssignableFrom(ArrayList.class))
/*  89 */           return new ArrayList<>(); 
/*  90 */         if (this.encoderClass.isAssignableFrom(HashSet.class)) {
/*  91 */           return new HashSet<>();
/*     */         }
/*  93 */         throw new CodecConfigurationException(String.format("Unsupported Collection interface of %s!", new Object[] { this.encoderClass.getName() }));
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/*  98 */         return this.encoderClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/*  99 */       } catch (Exception e) {
/* 100 */         throw new CodecConfigurationException(e.getMessage(), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\CollectionPropertyCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */