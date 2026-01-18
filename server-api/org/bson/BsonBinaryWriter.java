/*     */ package org.bson;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.io.BsonInput;
/*     */ import org.bson.io.BsonOutput;
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
/*     */ public class BsonBinaryWriter
/*     */   extends AbstractBsonWriter
/*     */ {
/*     */   private final BsonBinaryWriterSettings binaryWriterSettings;
/*     */   private final BsonOutput bsonOutput;
/*  39 */   private final Stack<Integer> maxDocumentSizeStack = new Stack<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private Mark mark;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinaryWriter(BsonOutput bsonOutput, FieldNameValidator validator) {
/*  49 */     this(new BsonWriterSettings(), new BsonBinaryWriterSettings(), bsonOutput, validator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinaryWriter(BsonOutput bsonOutput) {
/*  58 */     this(new BsonWriterSettings(), new BsonBinaryWriterSettings(), bsonOutput);
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
/*     */   public BsonBinaryWriter(BsonWriterSettings settings, BsonBinaryWriterSettings binaryWriterSettings, BsonOutput bsonOutput) {
/*  70 */     this(settings, binaryWriterSettings, bsonOutput, new NoOpFieldNameValidator());
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
/*     */   public BsonBinaryWriter(BsonWriterSettings settings, BsonBinaryWriterSettings binaryWriterSettings, BsonOutput bsonOutput, FieldNameValidator validator) {
/*  83 */     super(settings, validator);
/*  84 */     this.binaryWriterSettings = binaryWriterSettings;
/*  85 */     this.bsonOutput = bsonOutput;
/*  86 */     this.maxDocumentSizeStack.push(Integer.valueOf(binaryWriterSettings.getMaxDocumentSize()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  91 */     super.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonOutput getBsonOutput() {
/* 100 */     return this.bsonOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinaryWriterSettings getBinaryWriterSettings() {
/* 108 */     return this.binaryWriterSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */   
/*     */   protected Context getContext() {
/* 117 */     return (Context)super.getContext();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteStartDocument() {
/* 122 */     if (getState() == AbstractBsonWriter.State.VALUE) {
/* 123 */       this.bsonOutput.writeByte(BsonType.DOCUMENT.getValue());
/* 124 */       writeCurrentName();
/*     */     } 
/* 126 */     setContext(new Context(getContext(), BsonContextType.DOCUMENT, this.bsonOutput.getPosition()));
/* 127 */     this.bsonOutput.writeInt32(0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteEndDocument() {
/* 132 */     this.bsonOutput.writeByte(0);
/* 133 */     backpatchSize();
/*     */     
/* 135 */     setContext(getContext().getParentContext());
/* 136 */     if (getContext() != null && getContext().getContextType() == BsonContextType.JAVASCRIPT_WITH_SCOPE) {
/* 137 */       backpatchSize();
/* 138 */       setContext(getContext().getParentContext());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteStartArray() {
/* 144 */     this.bsonOutput.writeByte(BsonType.ARRAY.getValue());
/* 145 */     writeCurrentName();
/* 146 */     setContext(new Context(getContext(), BsonContextType.ARRAY, this.bsonOutput.getPosition()));
/* 147 */     this.bsonOutput.writeInt32(0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteEndArray() {
/* 152 */     this.bsonOutput.writeByte(0);
/* 153 */     backpatchSize();
/* 154 */     setContext(getContext().getParentContext());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteBinaryData(BsonBinary value) {
/* 159 */     this.bsonOutput.writeByte(BsonType.BINARY.getValue());
/* 160 */     writeCurrentName();
/*     */     
/* 162 */     int totalLen = (value.getData()).length;
/*     */     
/* 164 */     if (value.getType() == BsonBinarySubType.OLD_BINARY.getValue()) {
/* 165 */       totalLen += 4;
/*     */     }
/*     */     
/* 168 */     this.bsonOutput.writeInt32(totalLen);
/* 169 */     this.bsonOutput.writeByte(value.getType());
/* 170 */     if (value.getType() == BsonBinarySubType.OLD_BINARY.getValue()) {
/* 171 */       this.bsonOutput.writeInt32(totalLen - 4);
/*     */     }
/* 173 */     this.bsonOutput.writeBytes(value.getData());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteBoolean(boolean value) {
/* 178 */     this.bsonOutput.writeByte(BsonType.BOOLEAN.getValue());
/* 179 */     writeCurrentName();
/* 180 */     this.bsonOutput.writeByte(value ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDateTime(long value) {
/* 185 */     this.bsonOutput.writeByte(BsonType.DATE_TIME.getValue());
/* 186 */     writeCurrentName();
/* 187 */     this.bsonOutput.writeInt64(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDBPointer(BsonDbPointer value) {
/* 192 */     this.bsonOutput.writeByte(BsonType.DB_POINTER.getValue());
/* 193 */     writeCurrentName();
/*     */     
/* 195 */     this.bsonOutput.writeString(value.getNamespace());
/* 196 */     this.bsonOutput.writeBytes(value.getId().toByteArray());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDouble(double value) {
/* 201 */     this.bsonOutput.writeByte(BsonType.DOUBLE.getValue());
/* 202 */     writeCurrentName();
/* 203 */     this.bsonOutput.writeDouble(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt32(int value) {
/* 208 */     this.bsonOutput.writeByte(BsonType.INT32.getValue());
/* 209 */     writeCurrentName();
/* 210 */     this.bsonOutput.writeInt32(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt64(long value) {
/* 215 */     this.bsonOutput.writeByte(BsonType.INT64.getValue());
/* 216 */     writeCurrentName();
/* 217 */     this.bsonOutput.writeInt64(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDecimal128(Decimal128 value) {
/* 222 */     this.bsonOutput.writeByte(BsonType.DECIMAL128.getValue());
/* 223 */     writeCurrentName();
/* 224 */     this.bsonOutput.writeInt64(value.getLow());
/* 225 */     this.bsonOutput.writeInt64(value.getHigh());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScript(String value) {
/* 230 */     this.bsonOutput.writeByte(BsonType.JAVASCRIPT.getValue());
/* 231 */     writeCurrentName();
/* 232 */     this.bsonOutput.writeString(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScriptWithScope(String value) {
/* 237 */     this.bsonOutput.writeByte(BsonType.JAVASCRIPT_WITH_SCOPE.getValue());
/* 238 */     writeCurrentName();
/* 239 */     setContext(new Context(getContext(), BsonContextType.JAVASCRIPT_WITH_SCOPE, this.bsonOutput.getPosition()));
/* 240 */     this.bsonOutput.writeInt32(0);
/* 241 */     this.bsonOutput.writeString(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMaxKey() {
/* 246 */     this.bsonOutput.writeByte(BsonType.MAX_KEY.getValue());
/* 247 */     writeCurrentName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMinKey() {
/* 252 */     this.bsonOutput.writeByte(BsonType.MIN_KEY.getValue());
/* 253 */     writeCurrentName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteNull() {
/* 258 */     this.bsonOutput.writeByte(BsonType.NULL.getValue());
/* 259 */     writeCurrentName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteObjectId(ObjectId value) {
/* 264 */     this.bsonOutput.writeByte(BsonType.OBJECT_ID.getValue());
/* 265 */     writeCurrentName();
/* 266 */     this.bsonOutput.writeBytes(value.toByteArray());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteRegularExpression(BsonRegularExpression value) {
/* 271 */     this.bsonOutput.writeByte(BsonType.REGULAR_EXPRESSION.getValue());
/* 272 */     writeCurrentName();
/* 273 */     this.bsonOutput.writeCString(value.getPattern());
/* 274 */     this.bsonOutput.writeCString(value.getOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteString(String value) {
/* 279 */     this.bsonOutput.writeByte(BsonType.STRING.getValue());
/* 280 */     writeCurrentName();
/* 281 */     this.bsonOutput.writeString(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteSymbol(String value) {
/* 286 */     this.bsonOutput.writeByte(BsonType.SYMBOL.getValue());
/* 287 */     writeCurrentName();
/* 288 */     this.bsonOutput.writeString(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteTimestamp(BsonTimestamp value) {
/* 293 */     this.bsonOutput.writeByte(BsonType.TIMESTAMP.getValue());
/* 294 */     writeCurrentName();
/* 295 */     this.bsonOutput.writeInt64(value.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteUndefined() {
/* 300 */     this.bsonOutput.writeByte(BsonType.UNDEFINED.getValue());
/* 301 */     writeCurrentName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void pipe(BsonReader reader) {
/* 306 */     Assertions.notNull("reader", reader);
/* 307 */     pipeDocument(reader, (List<BsonElement>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pipe(BsonReader reader, List<BsonElement> extraElements) {
/* 312 */     Assertions.notNull("reader", reader);
/* 313 */     Assertions.notNull("extraElements", extraElements);
/* 314 */     pipeDocument(reader, extraElements);
/*     */   }
/*     */   
/*     */   private void pipeDocument(BsonReader reader, List<BsonElement> extraElements) {
/* 318 */     if (reader instanceof BsonBinaryReader) {
/* 319 */       BsonBinaryReader binaryReader = (BsonBinaryReader)reader;
/* 320 */       if (getState() == AbstractBsonWriter.State.VALUE) {
/* 321 */         this.bsonOutput.writeByte(BsonType.DOCUMENT.getValue());
/* 322 */         writeCurrentName();
/*     */       } 
/* 324 */       BsonInput bsonInput = binaryReader.getBsonInput();
/* 325 */       int size = bsonInput.readInt32();
/* 326 */       if (size < 5) {
/* 327 */         throw new BsonSerializationException("Document size must be at least 5");
/*     */       }
/* 329 */       int pipedDocumentStartPosition = this.bsonOutput.getPosition();
/* 330 */       this.bsonOutput.writeInt32(size);
/* 331 */       byte[] bytes = new byte[size - 4];
/* 332 */       bsonInput.readBytes(bytes);
/* 333 */       this.bsonOutput.writeBytes(bytes);
/*     */       
/* 335 */       binaryReader.setState(AbstractBsonReader.State.TYPE);
/*     */       
/* 337 */       if (extraElements != null) {
/* 338 */         this.bsonOutput.truncateToPosition(this.bsonOutput.getPosition() - 1);
/* 339 */         setContext(new Context(getContext(), BsonContextType.DOCUMENT, pipedDocumentStartPosition));
/* 340 */         setState(AbstractBsonWriter.State.NAME);
/* 341 */         pipeExtraElements(extraElements);
/* 342 */         this.bsonOutput.writeByte(0);
/* 343 */         this.bsonOutput.writeInt32(pipedDocumentStartPosition, this.bsonOutput.getPosition() - pipedDocumentStartPosition);
/* 344 */         setContext(getContext().getParentContext());
/*     */       } 
/*     */       
/* 347 */       if (getContext() == null) {
/* 348 */         setState(AbstractBsonWriter.State.DONE);
/*     */       } else {
/* 350 */         if (getContext().getContextType() == BsonContextType.JAVASCRIPT_WITH_SCOPE) {
/* 351 */           backpatchSize();
/* 352 */           setContext(getContext().getParentContext());
/*     */         } 
/* 354 */         setState(getNextState());
/*     */       } 
/*     */       
/* 357 */       validateSize(this.bsonOutput.getPosition() - pipedDocumentStartPosition);
/* 358 */     } else if (extraElements != null) {
/* 359 */       super.pipe(reader, extraElements);
/*     */     } else {
/* 361 */       super.pipe(reader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushMaxDocumentSize(int maxDocumentSize) {
/* 371 */     this.maxDocumentSizeStack.push(Integer.valueOf(maxDocumentSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void popMaxDocumentSize() {
/* 378 */     this.maxDocumentSizeStack.pop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark() {
/* 385 */     this.mark = new Mark();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 394 */     if (this.mark == null) {
/* 395 */       throw new IllegalStateException("Can not reset without first marking");
/*     */     }
/*     */     
/* 398 */     this.mark.reset();
/* 399 */     this.mark = null;
/*     */   }
/*     */   
/*     */   private void writeCurrentName() {
/* 403 */     if (getContext().getContextType() == BsonContextType.ARRAY) {
/* 404 */       this.bsonOutput.writeCString(Integer.toString((getContext()).index++));
/*     */     } else {
/* 406 */       this.bsonOutput.writeCString(getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void backpatchSize() {
/* 411 */     int size = this.bsonOutput.getPosition() - (getContext()).startPosition;
/* 412 */     validateSize(size);
/* 413 */     this.bsonOutput.writeInt32(this.bsonOutput.getPosition() - size, size);
/*     */   }
/*     */   
/*     */   private void validateSize(int size) {
/* 417 */     if (size > ((Integer)this.maxDocumentSizeStack.peek()).intValue()) {
/* 418 */       throw new BsonMaximumSizeExceededException(String.format("Document size of %d is larger than maximum of %d.", new Object[] { Integer.valueOf(size), this.maxDocumentSizeStack
/* 419 */               .peek() }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected class Context
/*     */     extends AbstractBsonWriter.Context
/*     */   {
/*     */     private final int startPosition;
/*     */ 
/*     */     
/*     */     private int index;
/*     */ 
/*     */     
/*     */     public Context(Context parentContext, BsonContextType contextType, int startPosition) {
/* 435 */       super(parentContext, contextType);
/* 436 */       this.startPosition = startPosition;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Context(Context from) {
/* 445 */       super(from);
/* 446 */       this.startPosition = from.startPosition;
/* 447 */       this.index = from.index;
/*     */     }
/*     */ 
/*     */     
/*     */     public Context getParentContext() {
/* 452 */       return (Context)super.getParentContext();
/*     */     }
/*     */ 
/*     */     
/*     */     public Context copy() {
/* 457 */       return new Context(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class Mark
/*     */     extends AbstractBsonWriter.Mark
/*     */   {
/*     */     private final int position;
/*     */     
/*     */     protected Mark() {
/* 468 */       this.position = BsonBinaryWriter.this.bsonOutput.getPosition();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void reset() {
/* 473 */       super.reset();
/* 474 */       BsonBinaryWriter.this.bsonOutput.truncateToPosition(BsonBinaryWriter.this.mark.position);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonBinaryWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */