/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonReader;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonValue;
/*     */ import org.bson.BsonWriter;
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
/*     */ public class BsonArrayCodec
/*     */   implements Codec<BsonArray>
/*     */ {
/*  39 */   private static final CodecRegistry DEFAULT_REGISTRY = CodecRegistries.fromProviders(new CodecProvider[] { new BsonValueCodecProvider() });
/*     */ 
/*     */ 
/*     */   
/*     */   private final CodecRegistry codecRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonArrayCodec() {
/*  49 */     this(DEFAULT_REGISTRY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonArrayCodec(CodecRegistry codecRegistry) {
/*  58 */     this.codecRegistry = (CodecRegistry)Assertions.notNull("codecRegistry", codecRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonArray decode(BsonReader reader, DecoderContext decoderContext) {
/*  63 */     reader.readStartArray();
/*     */     
/*  65 */     List<BsonValue> list = new ArrayList<>();
/*  66 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  67 */       list.add(readValue(reader, decoderContext));
/*     */     }
/*     */     
/*  70 */     reader.readEndArray();
/*     */     
/*  72 */     return new BsonArray(list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(BsonWriter writer, BsonArray array, EncoderContext encoderContext) {
/*  78 */     writer.writeStartArray();
/*     */     
/*  80 */     for (BsonValue value : array) {
/*  81 */       Codec<BsonValue> codec = this.codecRegistry.get(value.getClass());
/*  82 */       encoderContext.encodeWithChildContext(codec, writer, value);
/*     */     } 
/*     */     
/*  85 */     writer.writeEndArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<BsonArray> getEncoderClass() {
/*  90 */     return BsonArray.class;
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
/* 102 */     return this.codecRegistry.get(BsonValueCodecProvider.getClassForBsonType(reader.getCurrentBsonType())).decode(reader, decoderContext);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonArrayCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */