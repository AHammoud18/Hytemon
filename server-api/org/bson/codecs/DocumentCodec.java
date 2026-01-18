/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonDocumentWriter;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonValue;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.Document;
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
/*     */ 
/*     */ 
/*     */ public class DocumentCodec
/*     */   implements CollectibleCodec<Document>, OverridableUuidRepresentationCodec<Document>
/*     */ {
/*     */   private static final String ID_FIELD_NAME = "_id";
/*  49 */   private static final CodecRegistry DEFAULT_REGISTRY = CodecRegistries.fromProviders(Arrays.asList(new CodecProvider[] { new ValueCodecProvider(), new BsonValueCodecProvider(), new DocumentCodecProvider() }));
/*     */ 
/*     */   
/*  52 */   private static final BsonTypeCodecMap DEFAULT_BSON_TYPE_CODEC_MAP = new BsonTypeCodecMap(BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP, DEFAULT_REGISTRY);
/*  53 */   private static final IdGenerator DEFAULT_ID_GENERATOR = new ObjectIdGenerator();
/*     */   
/*     */   private final BsonTypeCodecMap bsonTypeCodecMap;
/*     */   
/*     */   private final CodecRegistry registry;
/*     */   
/*     */   private final IdGenerator idGenerator;
/*     */   
/*     */   private final Transformer valueTransformer;
/*     */   private final UuidRepresentation uuidRepresentation;
/*     */   
/*     */   public DocumentCodec() {
/*  65 */     this(DEFAULT_REGISTRY, DEFAULT_BSON_TYPE_CODEC_MAP, (Transformer)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentCodec(CodecRegistry registry) {
/*  75 */     this(registry, BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentCodec(CodecRegistry registry, BsonTypeClassMap bsonTypeClassMap) {
/*  85 */     this(registry, bsonTypeClassMap, (Transformer)null);
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
/*     */   public DocumentCodec(CodecRegistry registry, BsonTypeClassMap bsonTypeClassMap, Transformer valueTransformer) {
/*  98 */     this(registry, new BsonTypeCodecMap((BsonTypeClassMap)Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap), registry), valueTransformer);
/*     */   }
/*     */   
/*     */   private DocumentCodec(CodecRegistry registry, BsonTypeCodecMap bsonTypeCodecMap, Transformer valueTransformer) {
/* 102 */     this(registry, bsonTypeCodecMap, DEFAULT_ID_GENERATOR, valueTransformer, UuidRepresentation.UNSPECIFIED);
/*     */   }
/*     */ 
/*     */   
/*     */   private DocumentCodec(CodecRegistry registry, BsonTypeCodecMap bsonTypeCodecMap, IdGenerator idGenerator, Transformer valueTransformer, UuidRepresentation uuidRepresentation) {
/* 107 */     this.registry = (CodecRegistry)Assertions.notNull("registry", registry);
/* 108 */     this.bsonTypeCodecMap = bsonTypeCodecMap;
/* 109 */     this.idGenerator = idGenerator;
/* 110 */     this.valueTransformer = (valueTransformer != null) ? valueTransformer : new Transformer()
/*     */       {
/*     */         public Object transform(Object value) {
/* 113 */           return value;
/*     */         }
/*     */       };
/* 116 */     this.uuidRepresentation = uuidRepresentation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Codec<Document> withUuidRepresentation(UuidRepresentation uuidRepresentation) {
/* 121 */     return new DocumentCodec(this.registry, this.bsonTypeCodecMap, this.idGenerator, this.valueTransformer, uuidRepresentation);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean documentHasId(Document document) {
/* 126 */     return document.containsKey("_id");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue getDocumentId(Document document) {
/* 131 */     if (!documentHasId(document)) {
/* 132 */       throw new IllegalStateException("The document does not contain an _id");
/*     */     }
/*     */     
/* 135 */     Object id = document.get("_id");
/* 136 */     if (id instanceof BsonValue) {
/* 137 */       return (BsonValue)id;
/*     */     }
/*     */     
/* 140 */     BsonDocument idHoldingDocument = new BsonDocument();
/* 141 */     BsonDocumentWriter bsonDocumentWriter = new BsonDocumentWriter(idHoldingDocument);
/* 142 */     bsonDocumentWriter.writeStartDocument();
/* 143 */     bsonDocumentWriter.writeName("_id");
/* 144 */     writeValue((BsonWriter)bsonDocumentWriter, EncoderContext.builder().build(), id);
/* 145 */     bsonDocumentWriter.writeEndDocument();
/* 146 */     return idHoldingDocument.get("_id");
/*     */   }
/*     */ 
/*     */   
/*     */   public Document generateIdIfAbsentFromDocument(Document document) {
/* 151 */     if (!documentHasId(document)) {
/* 152 */       document.put("_id", this.idGenerator.generate());
/*     */     }
/* 154 */     return document;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, Document document, EncoderContext encoderContext) {
/* 159 */     writeMap(writer, (Map<String, Object>)document, encoderContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public Document decode(BsonReader reader, DecoderContext decoderContext) {
/* 164 */     Document document = new Document();
/*     */     
/* 166 */     reader.readStartDocument();
/* 167 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 168 */       String fieldName = reader.readName();
/* 169 */       document.put(fieldName, readValue(reader, decoderContext));
/*     */     } 
/*     */     
/* 172 */     reader.readEndDocument();
/*     */     
/* 174 */     return document;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Document> getEncoderClass() {
/* 179 */     return Document.class;
/*     */   }
/*     */   
/*     */   private void beforeFields(BsonWriter bsonWriter, EncoderContext encoderContext, Map<String, Object> document) {
/* 183 */     if (encoderContext.isEncodingCollectibleDocument() && document.containsKey("_id")) {
/* 184 */       bsonWriter.writeName("_id");
/* 185 */       writeValue(bsonWriter, encoderContext, document.get("_id"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean skipField(EncoderContext encoderContext, String key) {
/* 190 */     return (encoderContext.isEncodingCollectibleDocument() && key.equals("_id"));
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeValue(BsonWriter writer, EncoderContext encoderContext, Object value) {
/* 195 */     if (value == null) {
/* 196 */       writer.writeNull();
/* 197 */     } else if (value instanceof Iterable) {
/* 198 */       writeIterable(writer, (Iterable<Object>)value, encoderContext.getChildContext());
/* 199 */     } else if (value instanceof Map) {
/* 200 */       writeMap(writer, (Map<String, Object>)value, encoderContext.getChildContext());
/*     */     } else {
/* 202 */       Codec codec = this.registry.get(value.getClass());
/* 203 */       encoderContext.encodeWithChildContext(codec, writer, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeMap(BsonWriter writer, Map<String, Object> map, EncoderContext encoderContext) {
/* 208 */     writer.writeStartDocument();
/*     */     
/* 210 */     beforeFields(writer, encoderContext, map);
/*     */     
/* 212 */     for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 213 */       if (skipField(encoderContext, entry.getKey())) {
/*     */         continue;
/*     */       }
/* 216 */       writer.writeName(entry.getKey());
/* 217 */       writeValue(writer, encoderContext, entry.getValue());
/*     */     } 
/* 219 */     writer.writeEndDocument();
/*     */   }
/*     */   
/*     */   private void writeIterable(BsonWriter writer, Iterable<Object> list, EncoderContext encoderContext) {
/* 223 */     writer.writeStartArray();
/* 224 */     for (Object value : list) {
/* 225 */       writeValue(writer, encoderContext, value);
/*     */     }
/* 227 */     writer.writeEndArray();
/*     */   }
/*     */   
/*     */   private Object readValue(BsonReader reader, DecoderContext decoderContext) {
/* 231 */     BsonType bsonType = reader.getCurrentBsonType();
/* 232 */     if (bsonType == BsonType.NULL) {
/* 233 */       reader.readNull();
/* 234 */       return null;
/* 235 */     }  if (bsonType == BsonType.ARRAY) {
/* 236 */       return readList(reader, decoderContext);
/*     */     }
/* 238 */     Codec<?> codec = this.bsonTypeCodecMap.get(bsonType);
/*     */     
/* 240 */     if (bsonType == BsonType.BINARY && reader.peekBinarySize() == 16) {
/* 241 */       switch (reader.peekBinarySubType()) {
/*     */         case 3:
/* 243 */           if (this.uuidRepresentation == UuidRepresentation.JAVA_LEGACY || this.uuidRepresentation == UuidRepresentation.C_SHARP_LEGACY || this.uuidRepresentation == UuidRepresentation.PYTHON_LEGACY)
/*     */           {
/*     */             
/* 246 */             codec = this.registry.get(UUID.class);
/*     */           }
/*     */           break;
/*     */         case 4:
/* 250 */           if (this.uuidRepresentation == UuidRepresentation.STANDARD) {
/* 251 */             codec = this.registry.get(UUID.class);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 258 */     return this.valueTransformer.transform(codec.decode(reader, decoderContext));
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Object> readList(BsonReader reader, DecoderContext decoderContext) {
/* 263 */     reader.readStartArray();
/* 264 */     List<Object> list = new ArrayList();
/* 265 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 266 */       list.add(readValue(reader, decoderContext));
/*     */     }
/* 268 */     reader.readEndArray();
/* 269 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\DocumentCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */