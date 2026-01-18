/*     */ package org.bson;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bson.io.BasicOutputBuffer;
/*     */ import org.bson.io.BsonOutput;
/*     */ import org.bson.io.OutputBuffer;
/*     */ import org.bson.types.BSONTimestamp;
/*     */ import org.bson.types.Binary;
/*     */ import org.bson.types.Code;
/*     */ import org.bson.types.CodeWScope;
/*     */ import org.bson.types.Decimal128;
/*     */ import org.bson.types.ObjectId;
/*     */ import org.bson.types.Symbol;
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
/*     */ public class BasicBSONEncoder
/*     */   implements BSONEncoder
/*     */ {
/*     */   private BsonBinaryWriter bsonWriter;
/*     */   private OutputBuffer outputBuffer;
/*     */   
/*     */   public byte[] encode(BSONObject document) {
/*  50 */     BasicOutputBuffer basicOutputBuffer = new BasicOutputBuffer();
/*  51 */     set((OutputBuffer)basicOutputBuffer);
/*  52 */     putObject(document);
/*  53 */     done();
/*  54 */     return basicOutputBuffer.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  59 */     this.bsonWriter.close();
/*  60 */     this.bsonWriter = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(OutputBuffer buffer) {
/*  65 */     if (this.bsonWriter != null) {
/*  66 */       throw new IllegalStateException("Performing another operation at this moment");
/*     */     }
/*  68 */     this.outputBuffer = buffer;
/*  69 */     this.bsonWriter = new BsonBinaryWriter((BsonOutput)buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OutputBuffer getOutputBuffer() {
/*  78 */     return this.outputBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BsonBinaryWriter getBsonWriter() {
/*  87 */     return this.bsonWriter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int putObject(BSONObject document) {
/*  98 */     int startPosition = getOutputBuffer().getPosition();
/*  99 */     this.bsonWriter.writeStartDocument();
/*     */     
/* 101 */     if (isTopLevelDocument() && document.containsField("_id")) {
/* 102 */       _putObjectField("_id", document.get("_id"));
/*     */     }
/*     */     
/* 105 */     for (String key : document.keySet()) {
/* 106 */       if (isTopLevelDocument() && key.equals("_id")) {
/*     */         continue;
/*     */       }
/* 109 */       _putObjectField(key, document.get(key));
/*     */     } 
/* 111 */     this.bsonWriter.writeEndDocument();
/* 112 */     return getOutputBuffer().getPosition() - startPosition;
/*     */   }
/*     */   
/*     */   private boolean isTopLevelDocument() {
/* 116 */     return (this.bsonWriter.getContext().getParentContext() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putName(String name) {
/* 125 */     if (this.bsonWriter.getState() == AbstractBsonWriter.State.NAME) {
/* 126 */       this.bsonWriter.writeName(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _putObjectField(String name, Object value) {
/* 137 */     if ("_transientFields".equals(name)) {
/*     */       return;
/*     */     }
/* 140 */     if (name.contains("\000")) {
/* 141 */       throw new IllegalArgumentException("Document field names can't have a NULL character. (Bad Key: '" + name + "')");
/*     */     }
/*     */     
/* 144 */     if ("$where".equals(name) && value instanceof String) {
/* 145 */       putCode(name, new Code((String)value));
/*     */     }
/*     */     
/* 148 */     if (value == null) {
/* 149 */       putNull(name);
/* 150 */     } else if (value instanceof Date) {
/* 151 */       putDate(name, (Date)value);
/* 152 */     } else if (value instanceof Decimal128) {
/* 153 */       putDecimal128(name, (Decimal128)value);
/* 154 */     } else if (value instanceof Number) {
/* 155 */       putNumber(name, (Number)value);
/* 156 */     } else if (value instanceof Character) {
/* 157 */       putString(name, value.toString());
/* 158 */     } else if (value instanceof String) {
/* 159 */       putString(name, value.toString());
/* 160 */     } else if (value instanceof ObjectId) {
/* 161 */       putObjectId(name, (ObjectId)value);
/* 162 */     } else if (value instanceof Boolean) {
/* 163 */       putBoolean(name, (Boolean)value);
/* 164 */     } else if (value instanceof Pattern) {
/* 165 */       putPattern(name, (Pattern)value);
/* 166 */     } else if (value instanceof Iterable) {
/* 167 */       putIterable(name, (Iterable)value);
/* 168 */     } else if (value instanceof BSONObject) {
/* 169 */       putObject(name, (BSONObject)value);
/* 170 */     } else if (value instanceof Map) {
/* 171 */       putMap(name, (Map)value);
/* 172 */     } else if (value instanceof byte[]) {
/* 173 */       putBinary(name, (byte[])value);
/* 174 */     } else if (value instanceof Binary) {
/* 175 */       putBinary(name, (Binary)value);
/* 176 */     } else if (value instanceof UUID) {
/* 177 */       putUUID(name, (UUID)value);
/* 178 */     } else if (value.getClass().isArray()) {
/* 179 */       putArray(name, value);
/* 180 */     } else if (value instanceof Symbol) {
/* 181 */       putSymbol(name, (Symbol)value);
/* 182 */     } else if (value instanceof BSONTimestamp) {
/* 183 */       putTimestamp(name, (BSONTimestamp)value);
/* 184 */     } else if (value instanceof CodeWScope) {
/* 185 */       putCodeWScope(name, (CodeWScope)value);
/* 186 */     } else if (value instanceof Code) {
/* 187 */       putCode(name, (Code)value);
/* 188 */     } else if (value instanceof org.bson.types.MinKey) {
/* 189 */       putMinKey(name);
/* 190 */     } else if (value instanceof org.bson.types.MaxKey) {
/* 191 */       putMaxKey(name);
/* 192 */     } else if (!putSpecial(name, value)) {
/*     */ 
/*     */       
/* 195 */       throw new IllegalArgumentException("Can't serialize " + value.getClass());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putNull(String name) {
/* 207 */     putName(name);
/* 208 */     this.bsonWriter.writeNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putUndefined(String name) {
/* 218 */     putName(name);
/* 219 */     this.bsonWriter.writeUndefined();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putTimestamp(String name, BSONTimestamp timestamp) {
/* 230 */     putName(name);
/* 231 */     this.bsonWriter.writeTimestamp(new BsonTimestamp(timestamp.getTime(), timestamp.getInc()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putCode(String name, Code code) {
/* 241 */     putName(name);
/* 242 */     this.bsonWriter.writeJavaScript(code.getCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putCodeWScope(String name, CodeWScope codeWScope) {
/* 252 */     putName(name);
/* 253 */     this.bsonWriter.writeJavaScriptWithScope(codeWScope.getCode());
/* 254 */     putObject(codeWScope.getScope());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putBoolean(String name, Boolean value) {
/* 264 */     putName(name);
/* 265 */     this.bsonWriter.writeBoolean(value.booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putDate(String name, Date date) {
/* 276 */     putName(name);
/* 277 */     this.bsonWriter.writeDateTime(date.getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putNumber(String name, Number number) {
/* 287 */     putName(name);
/* 288 */     if (number instanceof Integer || number instanceof Short || number instanceof Byte || number instanceof java.util.concurrent.atomic.AtomicInteger) {
/* 289 */       this.bsonWriter.writeInt32(number.intValue());
/* 290 */     } else if (number instanceof Long || number instanceof java.util.concurrent.atomic.AtomicLong) {
/* 291 */       this.bsonWriter.writeInt64(number.longValue());
/* 292 */     } else if (number instanceof Float || number instanceof Double) {
/* 293 */       this.bsonWriter.writeDouble(number.doubleValue());
/*     */     } else {
/* 295 */       throw new IllegalArgumentException("Can't serialize " + number.getClass());
/*     */     } 
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
/*     */   protected void putDecimal128(String name, Decimal128 value) {
/* 308 */     putName(name);
/* 309 */     this.bsonWriter.writeDecimal128(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putBinary(String name, byte[] bytes) {
/* 320 */     putName(name);
/* 321 */     this.bsonWriter.writeBinaryData(new BsonBinary(bytes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putBinary(String name, Binary binary) {
/* 332 */     putName(name);
/* 333 */     this.bsonWriter.writeBinaryData(new BsonBinary(binary.getType(), binary.getData()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putUUID(String name, UUID uuid) {
/* 344 */     putName(name);
/* 345 */     byte[] bytes = new byte[16];
/* 346 */     writeLongToArrayLittleEndian(bytes, 0, uuid.getMostSignificantBits());
/* 347 */     writeLongToArrayLittleEndian(bytes, 8, uuid.getLeastSignificantBits());
/* 348 */     this.bsonWriter.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_LEGACY, bytes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putSymbol(String name, Symbol symbol) {
/* 359 */     putName(name);
/* 360 */     this.bsonWriter.writeSymbol(symbol.getSymbol());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putString(String name, String value) {
/* 371 */     putName(name);
/* 372 */     this.bsonWriter.writeString(value);
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
/*     */   protected void putPattern(String name, Pattern value) {
/* 384 */     putName(name);
/* 385 */     this.bsonWriter.writeRegularExpression(new BsonRegularExpression(value.pattern(), BSON.regexFlags(value.flags())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putObjectId(String name, ObjectId objectId) {
/* 395 */     putName(name);
/* 396 */     this.bsonWriter.writeObjectId(objectId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putArray(String name, Object object) {
/* 406 */     putName(name);
/* 407 */     this.bsonWriter.writeStartArray();
/* 408 */     if (object instanceof int[]) {
/* 409 */       for (int i : (int[])object) {
/* 410 */         this.bsonWriter.writeInt32(i);
/*     */       }
/* 412 */     } else if (object instanceof long[]) {
/* 413 */       for (long i : (long[])object) {
/* 414 */         this.bsonWriter.writeInt64(i);
/*     */       }
/* 416 */     } else if (object instanceof float[]) {
/* 417 */       for (float i : (float[])object) {
/* 418 */         this.bsonWriter.writeDouble(i);
/*     */       }
/* 420 */     } else if (object instanceof short[]) {
/* 421 */       for (short i : (short[])object) {
/* 422 */         this.bsonWriter.writeInt32(i);
/*     */       }
/* 424 */     } else if (object instanceof byte[]) {
/* 425 */       for (byte i : (byte[])object) {
/* 426 */         this.bsonWriter.writeInt32(i);
/*     */       }
/* 428 */     } else if (object instanceof double[]) {
/* 429 */       for (double i : (double[])object) {
/* 430 */         this.bsonWriter.writeDouble(i);
/*     */       }
/* 432 */     } else if (object instanceof boolean[]) {
/* 433 */       for (boolean i : (boolean[])object) {
/* 434 */         this.bsonWriter.writeBoolean(i);
/*     */       }
/* 436 */     } else if (object instanceof String[]) {
/* 437 */       for (String i : (String[])object) {
/* 438 */         this.bsonWriter.writeString(i);
/*     */       }
/*     */     } else {
/* 441 */       int length = Array.getLength(object);
/* 442 */       for (int i = 0; i < length; i++) {
/* 443 */         _putObjectField(String.valueOf(i), Array.get(object, i));
/*     */       }
/*     */     } 
/* 446 */     this.bsonWriter.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putIterable(String name, Iterable iterable) {
/* 457 */     putName(name);
/* 458 */     this.bsonWriter.writeStartArray();
/* 459 */     int i = 0;
/* 460 */     for (Object o : iterable) {
/* 461 */       _putObjectField(String.valueOf(i), o);
/*     */     }
/* 463 */     this.bsonWriter.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putMap(String name, Map map) {
/* 474 */     putName(name);
/* 475 */     this.bsonWriter.writeStartDocument();
/* 476 */     for (Map.Entry entry : map.entrySet()) {
/* 477 */       _putObjectField((String)entry.getKey(), entry.getValue());
/*     */     }
/* 479 */     this.bsonWriter.writeEndDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int putObject(String name, BSONObject document) {
/* 490 */     putName(name);
/* 491 */     return putObject(document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean putSpecial(String name, Object special) {
/* 502 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putMinKey(String name) {
/* 511 */     putName(name);
/* 512 */     this.bsonWriter.writeMinKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putMaxKey(String name) {
/* 521 */     putName(name);
/* 522 */     this.bsonWriter.writeMaxKey();
/*     */   }
/*     */   
/*     */   private static void writeLongToArrayLittleEndian(byte[] bytes, int offset, long x) {
/* 526 */     bytes[offset] = (byte)(int)(0xFFL & x);
/* 527 */     bytes[offset + 1] = (byte)(int)(0xFFL & x >> 8L);
/* 528 */     bytes[offset + 2] = (byte)(int)(0xFFL & x >> 16L);
/* 529 */     bytes[offset + 3] = (byte)(int)(0xFFL & x >> 24L);
/* 530 */     bytes[offset + 4] = (byte)(int)(0xFFL & x >> 32L);
/* 531 */     bytes[offset + 5] = (byte)(int)(0xFFL & x >> 40L);
/* 532 */     bytes[offset + 6] = (byte)(int)(0xFFL & x >> 48L);
/* 533 */     bytes[offset + 7] = (byte)(int)(0xFFL & x >> 56L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BasicBSONEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */