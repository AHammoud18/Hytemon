/*     */ package org.bson.json;
/*     */ 
/*     */ import java.io.Writer;
/*     */ import org.bson.AbstractBsonWriter;
/*     */ import org.bson.BsonBinary;
/*     */ import org.bson.BsonContextType;
/*     */ import org.bson.BsonDbPointer;
/*     */ import org.bson.BsonRegularExpression;
/*     */ import org.bson.BsonTimestamp;
/*     */ import org.bson.types.Decimal128;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonWriter
/*     */   extends AbstractBsonWriter
/*     */ {
/*     */   private final JsonWriterSettings settings;
/*     */   private final StrictCharacterStreamJsonWriter strictJsonWriter;
/*     */   
/*     */   public JsonWriter(Writer writer) {
/*  45 */     this(writer, JsonWriterSettings.builder().build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriter(Writer writer, JsonWriterSettings settings) {
/*  55 */     super(settings);
/*  56 */     this.settings = settings;
/*  57 */     setContext(new Context(null, BsonContextType.TOP_LEVEL));
/*  58 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       .strictJsonWriter = new StrictCharacterStreamJsonWriter(writer, StrictCharacterStreamJsonWriterSettings.builder().indent(settings.isIndent()).newLineCharacters(settings.getNewLineCharacters()).indentCharacters(settings.getIndentCharacters()).maxLength(settings.getMaxLength()).build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Writer getWriter() {
/*  72 */     return this.strictJsonWriter.getWriter();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Context getContext() {
/*  77 */     return (Context)super.getContext();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteName(String name) {
/*  82 */     this.strictJsonWriter.writeName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteStartDocument() {
/*  87 */     this.strictJsonWriter.writeStartObject();
/*     */     
/*  89 */     BsonContextType contextType = (getState() == AbstractBsonWriter.State.SCOPE_DOCUMENT) ? BsonContextType.SCOPE_DOCUMENT : BsonContextType.DOCUMENT;
/*  90 */     setContext(new Context(getContext(), contextType));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteEndDocument() {
/*  95 */     this.strictJsonWriter.writeEndObject();
/*  96 */     if (getContext().getContextType() == BsonContextType.SCOPE_DOCUMENT) {
/*  97 */       setContext(getContext().getParentContext());
/*  98 */       writeEndDocument();
/*     */     } else {
/* 100 */       setContext(getContext().getParentContext());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteStartArray() {
/* 106 */     this.strictJsonWriter.writeStartArray();
/* 107 */     setContext(new Context(getContext(), BsonContextType.ARRAY));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteEndArray() {
/* 112 */     this.strictJsonWriter.writeEndArray();
/* 113 */     setContext(getContext().getParentContext());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doWriteBinaryData(BsonBinary binary) {
/* 119 */     this.settings.getBinaryConverter().convert(binary, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteBoolean(boolean value) {
/* 124 */     this.settings.getBooleanConverter().convert(Boolean.valueOf(value), this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDateTime(long value) {
/* 129 */     this.settings.getDateTimeConverter().convert(Long.valueOf(value), this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDBPointer(BsonDbPointer value) {
/* 134 */     if (this.settings.getOutputMode() == JsonMode.EXTENDED) {
/* 135 */       (new Converter<BsonDbPointer>()
/*     */         {
/*     */           public void convert(BsonDbPointer value1, StrictJsonWriter writer) {
/* 138 */             writer.writeStartObject();
/* 139 */             writer.writeStartObject("$dbPointer");
/* 140 */             writer.writeString("$ref", value1.getNamespace());
/* 141 */             writer.writeName("$id");
/* 142 */             JsonWriter.this.doWriteObjectId(value1.getId());
/* 143 */             writer.writeEndObject();
/* 144 */             writer.writeEndObject();
/*     */           }
/* 146 */         }).convert(value, this.strictJsonWriter);
/*     */     } else {
/* 148 */       (new Converter<BsonDbPointer>()
/*     */         {
/*     */           public void convert(BsonDbPointer value1, StrictJsonWriter writer) {
/* 151 */             writer.writeStartObject();
/* 152 */             writer.writeString("$ref", value1.getNamespace());
/* 153 */             writer.writeName("$id");
/* 154 */             JsonWriter.this.doWriteObjectId(value1.getId());
/* 155 */             writer.writeEndObject();
/*     */           }
/* 157 */         }).convert(value, this.strictJsonWriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDouble(double value) {
/* 163 */     this.settings.getDoubleConverter().convert(Double.valueOf(value), this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt32(int value) {
/* 168 */     this.settings.getInt32Converter().convert(Integer.valueOf(value), this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt64(long value) {
/* 173 */     this.settings.getInt64Converter().convert(Long.valueOf(value), this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDecimal128(Decimal128 value) {
/* 178 */     this.settings.getDecimal128Converter().convert(value, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScript(String code) {
/* 183 */     this.settings.getJavaScriptConverter().convert(code, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScriptWithScope(String code) {
/* 188 */     writeStartDocument();
/* 189 */     writeString("$code", code);
/* 190 */     writeName("$scope");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMaxKey() {
/* 195 */     this.settings.getMaxKeyConverter().convert(null, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMinKey() {
/* 200 */     this.settings.getMinKeyConverter().convert(null, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteNull() {
/* 205 */     this.settings.getNullConverter().convert(null, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteObjectId(ObjectId objectId) {
/* 210 */     this.settings.getObjectIdConverter().convert(objectId, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteRegularExpression(BsonRegularExpression regularExpression) {
/* 215 */     this.settings.getRegularExpressionConverter().convert(regularExpression, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteString(String value) {
/* 220 */     this.settings.getStringConverter().convert(value, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteSymbol(String value) {
/* 225 */     this.settings.getSymbolConverter().convert(value, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteTimestamp(BsonTimestamp value) {
/* 230 */     this.settings.getTimestampConverter().convert(value, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteUndefined() {
/* 235 */     this.settings.getUndefinedConverter().convert(null, this.strictJsonWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 240 */     this.strictJsonWriter.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTruncated() {
/* 251 */     return this.strictJsonWriter.isTruncated();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean abortPipe() {
/* 256 */     return this.strictJsonWriter.isTruncated();
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
/*     */   public class Context
/*     */     extends AbstractBsonWriter.Context
/*     */   {
/*     */     public Context(Context parentContext, BsonContextType contextType) {
/* 272 */       super(JsonWriter.this, parentContext, contextType);
/*     */     }
/*     */ 
/*     */     
/*     */     public Context getParentContext() {
/* 277 */       return (Context)super.getParentContext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */