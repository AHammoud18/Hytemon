/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.Transformer;
/*     */ import org.bson.UuidRepresentation;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IterableCodec
/*     */   implements Codec<Iterable>, OverridableUuidRepresentationCodec<Iterable>
/*     */ {
/*     */   private final CodecRegistry registry;
/*     */   private final BsonTypeCodecMap bsonTypeCodecMap;
/*     */   private final Transformer valueTransformer;
/*     */   private final UuidRepresentation uuidRepresentation;
/*     */   
/*     */   public IterableCodec(CodecRegistry registry, BsonTypeClassMap bsonTypeClassMap) {
/*  52 */     this(registry, bsonTypeClassMap, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterableCodec(CodecRegistry registry, BsonTypeClassMap bsonTypeClassMap, Transformer valueTransformer) {
/*  63 */     this(registry, new BsonTypeCodecMap((BsonTypeClassMap)Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap), registry), valueTransformer, UuidRepresentation.UNSPECIFIED);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IterableCodec(CodecRegistry registry, BsonTypeCodecMap bsonTypeCodecMap, Transformer valueTransformer, UuidRepresentation uuidRepresentation) {
/*  69 */     this.registry = (CodecRegistry)Assertions.notNull("registry", registry);
/*  70 */     this.bsonTypeCodecMap = bsonTypeCodecMap;
/*  71 */     this.valueTransformer = (valueTransformer != null) ? valueTransformer : new Transformer()
/*     */       {
/*     */         public Object transform(Object objectToTransform) {
/*  74 */           return objectToTransform;
/*     */         }
/*     */       };
/*  77 */     this.uuidRepresentation = uuidRepresentation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Codec<Iterable> withUuidRepresentation(UuidRepresentation uuidRepresentation) {
/*  83 */     return new IterableCodec(this.registry, this.bsonTypeCodecMap, this.valueTransformer, uuidRepresentation);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable decode(BsonReader reader, DecoderContext decoderContext) {
/*  88 */     reader.readStartArray();
/*     */     
/*  90 */     List<Object> list = new ArrayList();
/*  91 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  92 */       list.add(readValue(reader, decoderContext));
/*     */     }
/*     */     
/*  95 */     reader.readEndArray();
/*     */     
/*  97 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, Iterable value, EncoderContext encoderContext) {
/* 102 */     writer.writeStartArray();
/* 103 */     for (Object cur : value) {
/* 104 */       writeValue(writer, encoderContext, cur);
/*     */     }
/* 106 */     writer.writeEndArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Iterable> getEncoderClass() {
/* 111 */     return Iterable.class;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeValue(BsonWriter writer, EncoderContext encoderContext, Object value) {
/* 116 */     if (value == null) {
/* 117 */       writer.writeNull();
/*     */     } else {
/* 119 */       Codec codec = this.registry.get(value.getClass());
/* 120 */       encoderContext.encodeWithChildContext(codec, writer, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object readValue(BsonReader reader, DecoderContext decoderContext) {
/* 125 */     BsonType bsonType = reader.getCurrentBsonType();
/* 126 */     if (bsonType == BsonType.NULL) {
/* 127 */       reader.readNull();
/* 128 */       return null;
/*     */     } 
/* 130 */     Codec<?> codec = this.bsonTypeCodecMap.get(bsonType);
/* 131 */     if (bsonType == BsonType.BINARY && reader.peekBinarySize() == 16) {
/* 132 */       switch (reader.peekBinarySubType()) {
/*     */         case 3:
/* 134 */           if (this.uuidRepresentation == UuidRepresentation.JAVA_LEGACY || this.uuidRepresentation == UuidRepresentation.C_SHARP_LEGACY || this.uuidRepresentation == UuidRepresentation.PYTHON_LEGACY)
/*     */           {
/*     */             
/* 137 */             codec = this.registry.get(UUID.class);
/*     */           }
/*     */           break;
/*     */         case 4:
/* 141 */           if (this.uuidRepresentation == UuidRepresentation.STANDARD) {
/* 142 */             codec = this.registry.get(UUID.class);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 149 */     return this.valueTransformer.transform(codec.decode(reader, decoderContext));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\IterableCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */