/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.Transformer;
/*     */ import org.bson.UuidRepresentation;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.configuration.CodecProvider;
/*     */ import org.bson.codecs.configuration.CodecRegistries;
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
/*     */ public class MapCodec
/*     */   implements Codec<Map<String, Object>>, OverridableUuidRepresentationCodec<Map<String, Object>>
/*     */ {
/*  42 */   private static final CodecRegistry DEFAULT_REGISTRY = CodecRegistries.fromProviders(Arrays.asList(new CodecProvider[] { new ValueCodecProvider(), new BsonValueCodecProvider(), new DocumentCodecProvider(), new IterableCodecProvider(), new MapCodecProvider() }));
/*     */   
/*  44 */   private static final BsonTypeClassMap DEFAULT_BSON_TYPE_CLASS_MAP = new BsonTypeClassMap();
/*     */   
/*     */   private final BsonTypeCodecMap bsonTypeCodecMap;
/*     */   
/*     */   private final CodecRegistry registry;
/*     */   
/*     */   private final Transformer valueTransformer;
/*     */   private final UuidRepresentation uuidRepresentation;
/*     */   
/*     */   public MapCodec() {
/*  54 */     this(DEFAULT_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapCodec(CodecRegistry registry) {
/*  63 */     this(registry, DEFAULT_BSON_TYPE_CLASS_MAP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapCodec(CodecRegistry registry, BsonTypeClassMap bsonTypeClassMap) {
/*  73 */     this(registry, bsonTypeClassMap, null);
/*     */   }
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
/*     */   public MapCodec(CodecRegistry registry, BsonTypeClassMap bsonTypeClassMap, Transformer valueTransformer) {
/*  86 */     this(registry, new BsonTypeCodecMap((BsonTypeClassMap)Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap), registry), valueTransformer, UuidRepresentation.UNSPECIFIED);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MapCodec(CodecRegistry registry, BsonTypeCodecMap bsonTypeCodecMap, Transformer valueTransformer, UuidRepresentation uuidRepresentation) {
/*  92 */     this.registry = (CodecRegistry)Assertions.notNull("registry", registry);
/*  93 */     this.bsonTypeCodecMap = bsonTypeCodecMap;
/*  94 */     this.valueTransformer = (valueTransformer != null) ? valueTransformer : new Transformer()
/*     */       {
/*     */         public Object transform(Object value) {
/*  97 */           return value;
/*     */         }
/*     */       };
/* 100 */     this.uuidRepresentation = uuidRepresentation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Codec<Map<String, Object>> withUuidRepresentation(UuidRepresentation uuidRepresentation) {
/* 105 */     return new MapCodec(this.registry, this.bsonTypeCodecMap, this.valueTransformer, uuidRepresentation);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, Map<String, Object> map, EncoderContext encoderContext) {
/* 110 */     writer.writeStartDocument();
/* 111 */     for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 112 */       writer.writeName(entry.getKey());
/* 113 */       writeValue(writer, encoderContext, entry.getValue());
/*     */     } 
/* 115 */     writer.writeEndDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> decode(BsonReader reader, DecoderContext decoderContext) {
/* 120 */     Map<String, Object> map = new HashMap<>();
/*     */     
/* 122 */     reader.readStartDocument();
/* 123 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 124 */       String fieldName = reader.readName();
/* 125 */       map.put(fieldName, readValue(reader, decoderContext));
/*     */     } 
/*     */     
/* 128 */     reader.readEndDocument();
/* 129 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<Map<String, Object>> getEncoderClass() {
/* 135 */     return (Class)Map.class;
/*     */   }
/*     */   
/*     */   private Object readValue(BsonReader reader, DecoderContext decoderContext) {
/* 139 */     BsonType bsonType = reader.getCurrentBsonType();
/* 140 */     if (bsonType == BsonType.NULL) {
/* 141 */       reader.readNull();
/* 142 */       return null;
/* 143 */     }  if (bsonType == BsonType.ARRAY)
/* 144 */       return decoderContext.decodeWithChildContext(this.registry.get(List.class), reader); 
/* 145 */     if (bsonType == BsonType.BINARY && reader.peekBinarySize() == 16) {
/* 146 */       Codec<?> codec = this.bsonTypeCodecMap.get(bsonType);
/* 147 */       switch (reader.peekBinarySubType()) {
/*     */         case 3:
/* 149 */           if (this.uuidRepresentation == UuidRepresentation.JAVA_LEGACY || this.uuidRepresentation == UuidRepresentation.C_SHARP_LEGACY || this.uuidRepresentation == UuidRepresentation.PYTHON_LEGACY)
/*     */           {
/*     */             
/* 152 */             codec = this.registry.get(UUID.class);
/*     */           }
/*     */           break;
/*     */         case 4:
/* 156 */           if (this.uuidRepresentation == UuidRepresentation.STANDARD) {
/* 157 */             codec = this.registry.get(UUID.class);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 164 */       return decoderContext.decodeWithChildContext(codec, reader);
/*     */     } 
/* 166 */     return this.valueTransformer.transform(this.bsonTypeCodecMap.get(bsonType).decode(reader, decoderContext));
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeValue(BsonWriter writer, EncoderContext encoderContext, Object value) {
/* 171 */     if (value == null) {
/* 172 */       writer.writeNull();
/*     */     } else {
/* 174 */       Codec codec = this.registry.get(value.getClass());
/* 175 */       encoderContext.encodeWithChildContext(codec, writer, value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\MapCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */