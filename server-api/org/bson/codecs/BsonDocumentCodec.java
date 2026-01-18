/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonElement;
/*     */ import org.bson.BsonObjectId;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonValue;
/*     */ import org.bson.BsonWriter;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.configuration.CodecProvider;
/*     */ import org.bson.codecs.configuration.CodecRegistries;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ import org.bson.types.ObjectId;
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
/*     */ public class BsonDocumentCodec
/*     */   implements CollectibleCodec<BsonDocument>
/*     */ {
/*     */   private static final String ID_FIELD_NAME = "_id";
/*  44 */   private static final CodecRegistry DEFAULT_REGISTRY = CodecRegistries.fromProviders(new CodecProvider[] { new BsonValueCodecProvider() });
/*  45 */   private static final BsonTypeCodecMap DEFAULT_BSON_TYPE_CODEC_MAP = new BsonTypeCodecMap(BsonValueCodecProvider.getBsonTypeClassMap(), DEFAULT_REGISTRY);
/*     */ 
/*     */   
/*     */   private final CodecRegistry codecRegistry;
/*     */   
/*     */   private final BsonTypeCodecMap bsonTypeCodecMap;
/*     */ 
/*     */   
/*     */   public BsonDocumentCodec() {
/*  54 */     this(DEFAULT_REGISTRY, DEFAULT_BSON_TYPE_CODEC_MAP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocumentCodec(CodecRegistry codecRegistry) {
/*  63 */     this(codecRegistry, new BsonTypeCodecMap(BsonValueCodecProvider.getBsonTypeClassMap(), codecRegistry));
/*     */   }
/*     */   
/*     */   private BsonDocumentCodec(CodecRegistry codecRegistry, BsonTypeCodecMap bsonTypeCodecMap) {
/*  67 */     this.codecRegistry = (CodecRegistry)Assertions.notNull("Codec registry", codecRegistry);
/*  68 */     this.bsonTypeCodecMap = (BsonTypeCodecMap)Assertions.notNull("bsonTypeCodecMap", bsonTypeCodecMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodecRegistry getCodecRegistry() {
/*  77 */     return this.codecRegistry;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDocument decode(BsonReader reader, DecoderContext decoderContext) {
/*  82 */     List<BsonElement> keyValuePairs = new ArrayList<>();
/*     */     
/*  84 */     reader.readStartDocument();
/*  85 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  86 */       String fieldName = reader.readName();
/*  87 */       keyValuePairs.add(new BsonElement(fieldName, readValue(reader, decoderContext)));
/*     */     } 
/*     */     
/*  90 */     reader.readEndDocument();
/*     */     
/*  92 */     return new BsonDocument(keyValuePairs);
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
/*     */   protected BsonValue readValue(BsonReader reader, DecoderContext decoderContext) {
/* 104 */     return (BsonValue)this.bsonTypeCodecMap.get(reader.getCurrentBsonType()).decode(reader, decoderContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, BsonDocument value, EncoderContext encoderContext) {
/* 109 */     writer.writeStartDocument();
/*     */     
/* 111 */     beforeFields(writer, encoderContext, value);
/* 112 */     for (Map.Entry<String, BsonValue> entry : (Iterable<Map.Entry<String, BsonValue>>)value.entrySet()) {
/* 113 */       if (skipField(encoderContext, entry.getKey())) {
/*     */         continue;
/*     */       }
/*     */       
/* 117 */       writer.writeName(entry.getKey());
/* 118 */       writeValue(writer, encoderContext, entry.getValue());
/*     */     } 
/*     */     
/* 121 */     writer.writeEndDocument();
/*     */   }
/*     */   
/*     */   private void beforeFields(BsonWriter bsonWriter, EncoderContext encoderContext, BsonDocument value) {
/* 125 */     if (encoderContext.isEncodingCollectibleDocument() && value.containsKey("_id")) {
/* 126 */       bsonWriter.writeName("_id");
/* 127 */       writeValue(bsonWriter, encoderContext, value.get("_id"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean skipField(EncoderContext encoderContext, String key) {
/* 132 */     return (encoderContext.isEncodingCollectibleDocument() && key.equals("_id"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeValue(BsonWriter writer, EncoderContext encoderContext, BsonValue value) {
/* 138 */     Codec<BsonValue> codec = this.codecRegistry.get(value.getClass());
/* 139 */     encoderContext.encodeWithChildContext(codec, writer, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<BsonDocument> getEncoderClass() {
/* 144 */     return BsonDocument.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDocument generateIdIfAbsentFromDocument(BsonDocument document) {
/* 149 */     if (!documentHasId(document)) {
/* 150 */       document.put("_id", (BsonValue)new BsonObjectId(new ObjectId()));
/*     */     }
/* 152 */     return document;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean documentHasId(BsonDocument document) {
/* 157 */     return document.containsKey("_id");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue getDocumentId(BsonDocument document) {
/* 162 */     return document.get("_id");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonDocumentCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */