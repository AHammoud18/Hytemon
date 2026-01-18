/*     */ package org.bson;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.io.BsonInput;
/*     */ import org.bson.io.BsonInputMark;
/*     */ import org.bson.io.ByteBufferBsonInput;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BsonBinaryReader
/*     */   extends AbstractBsonReader
/*     */ {
/*     */   private final BsonInput bsonInput;
/*     */   
/*     */   public BsonBinaryReader(ByteBuffer byteBuffer) {
/*  45 */     this((BsonInput)new ByteBufferBsonInput(new ByteBufNIO((ByteBuffer)Assertions.notNull("byteBuffer", byteBuffer))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinaryReader(BsonInput bsonInput) {
/*  54 */     if (bsonInput == null) {
/*  55 */       throw new IllegalArgumentException("bsonInput is null");
/*     */     }
/*  57 */     this.bsonInput = bsonInput;
/*  58 */     setContext(new Context(null, BsonContextType.TOP_LEVEL, 0, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  63 */     super.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonInput getBsonInput() {
/*  72 */     return this.bsonInput;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType readBsonType() {
/*  77 */     if (isClosed()) {
/*  78 */       throw new IllegalStateException("BSONBinaryWriter");
/*     */     }
/*     */     
/*  81 */     if (getState() == AbstractBsonReader.State.INITIAL || getState() == AbstractBsonReader.State.DONE || getState() == AbstractBsonReader.State.SCOPE_DOCUMENT) {
/*     */       
/*  83 */       setCurrentBsonType(BsonType.DOCUMENT);
/*  84 */       setState(AbstractBsonReader.State.VALUE);
/*  85 */       return getCurrentBsonType();
/*     */     } 
/*  87 */     if (getState() != AbstractBsonReader.State.TYPE) {
/*  88 */       throwInvalidState("ReadBSONType", new AbstractBsonReader.State[] { AbstractBsonReader.State.TYPE });
/*     */     }
/*     */     
/*  91 */     byte bsonTypeByte = this.bsonInput.readByte();
/*  92 */     BsonType bsonType = BsonType.findByValue(bsonTypeByte);
/*  93 */     if (bsonType == null) {
/*  94 */       String name = this.bsonInput.readCString();
/*  95 */       throw new BsonSerializationException(String.format("Detected unknown BSON type \"\\x%x\" for fieldname \"%s\". Are you using the latest driver version?", new Object[] {
/*     */               
/*  97 */               Byte.valueOf(bsonTypeByte), name }));
/*     */     } 
/*  99 */     setCurrentBsonType(bsonType);
/*     */     
/* 101 */     if (getCurrentBsonType() == BsonType.END_OF_DOCUMENT) {
/* 102 */       switch (getContext().getContextType()) {
/*     */         case ARRAY:
/* 104 */           setState(AbstractBsonReader.State.END_OF_ARRAY);
/* 105 */           return BsonType.END_OF_DOCUMENT;
/*     */         case BINARY:
/*     */         case BOOLEAN:
/* 108 */           setState(AbstractBsonReader.State.END_OF_DOCUMENT);
/* 109 */           return BsonType.END_OF_DOCUMENT;
/*     */       } 
/* 111 */       throw new BsonSerializationException(String.format("BSONType EndOfDocument is not valid when ContextType is %s.", new Object[] {
/* 112 */               getContext().getContextType()
/*     */             }));
/*     */     } 
/* 115 */     switch (getContext().getContextType()) {
/*     */       case ARRAY:
/* 117 */         this.bsonInput.skipCString();
/* 118 */         setState(AbstractBsonReader.State.VALUE);
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
/* 129 */         return getCurrentBsonType();case BINARY: case BOOLEAN: setCurrentName(this.bsonInput.readCString()); setState(AbstractBsonReader.State.NAME); return getCurrentBsonType();
/*     */     } 
/*     */     throw new BSONException("Unexpected ContextType.");
/*     */   }
/*     */   
/*     */   protected BsonBinary doReadBinaryData() {
/* 135 */     int numBytes = readSize();
/* 136 */     byte type = this.bsonInput.readByte();
/*     */     
/* 138 */     if (type == BsonBinarySubType.OLD_BINARY.getValue()) {
/* 139 */       int repeatedNumBytes = this.bsonInput.readInt32();
/* 140 */       if (repeatedNumBytes != numBytes - 4) {
/* 141 */         throw new BsonSerializationException("Binary sub type OldBinary has inconsistent sizes");
/*     */       }
/* 143 */       numBytes -= 4;
/*     */     } 
/* 145 */     byte[] bytes = new byte[numBytes];
/* 146 */     this.bsonInput.readBytes(bytes);
/* 147 */     return new BsonBinary(type, bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte doPeekBinarySubType() {
/* 152 */     Mark mark = new Mark();
/* 153 */     readSize();
/* 154 */     byte type = this.bsonInput.readByte();
/* 155 */     mark.reset();
/* 156 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doPeekBinarySize() {
/* 161 */     Mark mark = new Mark();
/* 162 */     int size = readSize();
/* 163 */     mark.reset();
/* 164 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doReadBoolean() {
/* 169 */     byte booleanByte = this.bsonInput.readByte();
/* 170 */     if (booleanByte != 0 && booleanByte != 1) {
/* 171 */       throw new BsonSerializationException(String.format("Expected a boolean value but found %d", new Object[] { Byte.valueOf(booleanByte) }));
/*     */     }
/* 173 */     return (booleanByte == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doReadDateTime() {
/* 178 */     return this.bsonInput.readInt64();
/*     */   }
/*     */ 
/*     */   
/*     */   protected double doReadDouble() {
/* 183 */     return this.bsonInput.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadInt32() {
/* 188 */     return this.bsonInput.readInt32();
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doReadInt64() {
/* 193 */     return this.bsonInput.readInt64();
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 doReadDecimal128() {
/* 198 */     long low = this.bsonInput.readInt64();
/* 199 */     long high = this.bsonInput.readInt64();
/* 200 */     return Decimal128.fromIEEE754BIDEncoding(high, low);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadJavaScript() {
/* 205 */     return this.bsonInput.readString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadJavaScriptWithScope() {
/* 210 */     int startPosition = this.bsonInput.getPosition();
/* 211 */     int size = readSize();
/* 212 */     setContext(new Context(getContext(), BsonContextType.JAVASCRIPT_WITH_SCOPE, startPosition, size));
/* 213 */     return this.bsonInput.readString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doReadMaxKey() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doReadMinKey() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doReadNull() {}
/*     */ 
/*     */   
/*     */   protected ObjectId doReadObjectId() {
/* 230 */     return this.bsonInput.readObjectId();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BsonRegularExpression doReadRegularExpression() {
/* 235 */     return new BsonRegularExpression(this.bsonInput.readCString(), this.bsonInput.readCString());
/*     */   }
/*     */ 
/*     */   
/*     */   protected BsonDbPointer doReadDBPointer() {
/* 240 */     return new BsonDbPointer(this.bsonInput.readString(), this.bsonInput.readObjectId());
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadString() {
/* 245 */     return this.bsonInput.readString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadSymbol() {
/* 250 */     return this.bsonInput.readString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BsonTimestamp doReadTimestamp() {
/* 255 */     return new BsonTimestamp(this.bsonInput.readInt64());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doReadUndefined() {}
/*     */ 
/*     */   
/*     */   public void doReadStartArray() {
/* 264 */     int startPosition = this.bsonInput.getPosition();
/* 265 */     int size = readSize();
/* 266 */     setContext(new Context(getContext(), BsonContextType.ARRAY, startPosition, size));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doReadStartDocument() {
/* 272 */     BsonContextType contextType = (getState() == AbstractBsonReader.State.SCOPE_DOCUMENT) ? BsonContextType.SCOPE_DOCUMENT : BsonContextType.DOCUMENT;
/* 273 */     int startPosition = this.bsonInput.getPosition();
/* 274 */     int size = readSize();
/* 275 */     setContext(new Context(getContext(), contextType, startPosition, size));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doReadEndArray() {
/* 280 */     setContext(getContext().popContext(this.bsonInput.getPosition()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doReadEndDocument() {
/* 285 */     setContext(getContext().popContext(this.bsonInput.getPosition()));
/* 286 */     if (getContext().getContextType() == BsonContextType.JAVASCRIPT_WITH_SCOPE) {
/* 287 */       setContext(getContext().popContext(this.bsonInput.getPosition()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSkipName() {}
/*     */ 
/*     */   
/*     */   protected void doSkipValue() {
/*     */     int skip;
/* 297 */     if (isClosed()) {
/* 298 */       throw new IllegalStateException("BSONBinaryWriter");
/*     */     }
/* 300 */     if (getState() != AbstractBsonReader.State.VALUE) {
/* 301 */       throwInvalidState("skipValue", new AbstractBsonReader.State[] { AbstractBsonReader.State.VALUE });
/*     */     }
/*     */ 
/*     */     
/* 305 */     switch (getCurrentBsonType()) {
/*     */       case ARRAY:
/* 307 */         skip = readSize() - 4;
/*     */         break;
/*     */       case BINARY:
/* 310 */         skip = readSize() + 1;
/*     */         break;
/*     */       case BOOLEAN:
/* 313 */         skip = 1;
/*     */         break;
/*     */       case DATE_TIME:
/* 316 */         skip = 8;
/*     */         break;
/*     */       case DOCUMENT:
/* 319 */         skip = readSize() - 4;
/*     */         break;
/*     */       case DOUBLE:
/* 322 */         skip = 8;
/*     */         break;
/*     */       case INT32:
/* 325 */         skip = 4;
/*     */         break;
/*     */       case INT64:
/* 328 */         skip = 8;
/*     */         break;
/*     */       case DECIMAL128:
/* 331 */         skip = 16;
/*     */         break;
/*     */       case JAVASCRIPT:
/* 334 */         skip = readSize();
/*     */         break;
/*     */       case JAVASCRIPT_WITH_SCOPE:
/* 337 */         skip = readSize() - 4;
/*     */         break;
/*     */       case MAX_KEY:
/* 340 */         skip = 0;
/*     */         break;
/*     */       case MIN_KEY:
/* 343 */         skip = 0;
/*     */         break;
/*     */       case NULL:
/* 346 */         skip = 0;
/*     */         break;
/*     */       case OBJECT_ID:
/* 349 */         skip = 12;
/*     */         break;
/*     */       case REGULAR_EXPRESSION:
/* 352 */         this.bsonInput.skipCString();
/* 353 */         this.bsonInput.skipCString();
/* 354 */         skip = 0;
/*     */         break;
/*     */       case STRING:
/* 357 */         skip = readSize();
/*     */         break;
/*     */       case SYMBOL:
/* 360 */         skip = readSize();
/*     */         break;
/*     */       case TIMESTAMP:
/* 363 */         skip = 8;
/*     */         break;
/*     */       case UNDEFINED:
/* 366 */         skip = 0;
/*     */         break;
/*     */       case DB_POINTER:
/* 369 */         skip = readSize() + 12;
/*     */         break;
/*     */       default:
/* 372 */         throw new BSONException("Unexpected BSON type: " + getCurrentBsonType());
/*     */     } 
/* 374 */     this.bsonInput.skip(skip);
/*     */     
/* 376 */     setState(AbstractBsonReader.State.TYPE);
/*     */   }
/*     */   
/*     */   private int readSize() {
/* 380 */     int size = this.bsonInput.readInt32();
/* 381 */     if (size < 0) {
/* 382 */       String message = String.format("Size %s is not valid because it is negative.", new Object[] { Integer.valueOf(size) });
/* 383 */       throw new BsonSerializationException(message);
/*     */     } 
/* 385 */     return size;
/*     */   }
/*     */   
/*     */   protected Context getContext() {
/* 389 */     return (Context)super.getContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonReaderMark getMark() {
/* 394 */     return new Mark();
/*     */   }
/*     */   
/*     */   protected class Mark
/*     */     extends AbstractBsonReader.Mark {
/*     */     private final int startPosition;
/*     */     private final int size;
/*     */     private final BsonInputMark bsonInputMark;
/*     */     
/*     */     protected Mark() {
/* 404 */       this.startPosition = (BsonBinaryReader.this.getContext()).startPosition;
/* 405 */       this.size = (BsonBinaryReader.this.getContext()).size;
/* 406 */       this.bsonInputMark = BsonBinaryReader.this.bsonInput.getMark(2147483647);
/*     */     }
/*     */     
/*     */     public void reset() {
/* 410 */       super.reset();
/* 411 */       this.bsonInputMark.reset();
/* 412 */       BsonBinaryReader.this.setContext(new BsonBinaryReader.Context((BsonBinaryReader.Context)getParentContext(), getContextType(), this.startPosition, this.size));
/*     */     } }
/*     */   
/*     */   protected class Context extends AbstractBsonReader.Context {
/*     */     private final int startPosition;
/*     */     private final int size;
/*     */     
/*     */     Context(Context parentContext, BsonContextType contextType, int startPosition, int size) {
/* 420 */       super(parentContext, contextType);
/* 421 */       this.startPosition = startPosition;
/* 422 */       this.size = size;
/*     */     }
/*     */     
/*     */     Context popContext(int position) {
/* 426 */       int actualSize = position - this.startPosition;
/* 427 */       if (actualSize != this.size) {
/* 428 */         throw new BsonSerializationException(String.format("Expected size to be %d, not %d.", new Object[] { Integer.valueOf(this.size), Integer.valueOf(actualSize) }));
/*     */       }
/* 430 */       return getParentContext();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Context getParentContext() {
/* 435 */       return (Context)super.getParentContext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonBinaryReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */