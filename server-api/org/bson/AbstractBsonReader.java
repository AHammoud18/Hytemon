/*     */ package org.bson;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractBsonReader
/*     */   implements BsonReader
/*     */ {
/*  41 */   private State state = State.INITIAL; private Context context; private BsonType currentBsonType;
/*     */   private String currentName;
/*     */   private boolean closed;
/*     */   
/*     */   public BsonType getCurrentBsonType() {
/*  46 */     return this.currentBsonType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCurrentName() {
/*  51 */     if (this.state != State.VALUE) {
/*  52 */       throwInvalidState("getCurrentName", new State[] { State.VALUE });
/*     */     }
/*  54 */     return this.currentName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCurrentBsonType(BsonType newType) {
/*  63 */     this.currentBsonType = newType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState() {
/*  70 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setState(State newState) {
/*  79 */     this.state = newState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCurrentName(String newName) {
/*  88 */     this.currentName = newName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  95 */     this.closed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isClosed() {
/* 104 */     return this.closed;
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
/*     */   public BsonBinary readBinaryData() {
/* 281 */     checkPreconditions("readBinaryData", BsonType.BINARY);
/* 282 */     setState(getNextState());
/* 283 */     return doReadBinaryData();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte peekBinarySubType() {
/* 288 */     checkPreconditions("readBinaryData", BsonType.BINARY);
/* 289 */     return doPeekBinarySubType();
/*     */   }
/*     */ 
/*     */   
/*     */   public int peekBinarySize() {
/* 294 */     checkPreconditions("readBinaryData", BsonType.BINARY);
/* 295 */     return doPeekBinarySize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/* 300 */     checkPreconditions("readBoolean", BsonType.BOOLEAN);
/* 301 */     setState(getNextState());
/* 302 */     return doReadBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long readDateTime() {
/* 310 */     checkPreconditions("readDateTime", BsonType.DATE_TIME);
/* 311 */     setState(getNextState());
/* 312 */     return doReadDateTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/* 317 */     checkPreconditions("readDouble", BsonType.DOUBLE);
/* 318 */     setState(getNextState());
/* 319 */     return doReadDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readEndArray() {
/* 324 */     if (isClosed()) {
/* 325 */       throw new IllegalStateException("BSONBinaryWriter");
/*     */     }
/* 327 */     if (getContext().getContextType() != BsonContextType.ARRAY) {
/* 328 */       throwInvalidContextType("readEndArray", getContext().getContextType(), new BsonContextType[] { BsonContextType.ARRAY });
/*     */     }
/* 330 */     if (getState() == State.TYPE) {
/* 331 */       readBsonType();
/*     */     }
/* 333 */     if (getState() != State.END_OF_ARRAY) {
/* 334 */       throwInvalidState("ReadEndArray", new State[] { State.END_OF_ARRAY });
/*     */     }
/*     */     
/* 337 */     doReadEndArray();
/*     */     
/* 339 */     setStateOnEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readEndDocument() {
/* 344 */     if (isClosed()) {
/* 345 */       throw new IllegalStateException("BSONBinaryWriter");
/*     */     }
/* 347 */     if (getContext().getContextType() != BsonContextType.DOCUMENT && getContext().getContextType() != BsonContextType.SCOPE_DOCUMENT) {
/* 348 */       throwInvalidContextType("readEndDocument", 
/* 349 */           getContext().getContextType(), new BsonContextType[] { BsonContextType.DOCUMENT, BsonContextType.SCOPE_DOCUMENT });
/*     */     }
/* 351 */     if (getState() == State.TYPE) {
/* 352 */       readBsonType();
/*     */     }
/* 354 */     if (getState() != State.END_OF_DOCUMENT) {
/* 355 */       throwInvalidState("readEndDocument", new State[] { State.END_OF_DOCUMENT });
/*     */     }
/*     */     
/* 358 */     doReadEndDocument();
/*     */     
/* 360 */     setStateOnEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt32() {
/* 365 */     checkPreconditions("readInt32", BsonType.INT32);
/* 366 */     setState(getNextState());
/* 367 */     return doReadInt32();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long readInt64() {
/* 373 */     checkPreconditions("readInt64", BsonType.INT64);
/* 374 */     setState(getNextState());
/* 375 */     return doReadInt64();
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 readDecimal128() {
/* 380 */     checkPreconditions("readDecimal", BsonType.DECIMAL128);
/* 381 */     setState(getNextState());
/* 382 */     return doReadDecimal128();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readJavaScript() {
/* 387 */     checkPreconditions("readJavaScript", BsonType.JAVASCRIPT);
/* 388 */     setState(getNextState());
/* 389 */     return doReadJavaScript();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readJavaScriptWithScope() {
/* 394 */     checkPreconditions("readJavaScriptWithScope", BsonType.JAVASCRIPT_WITH_SCOPE);
/* 395 */     setState(State.SCOPE_DOCUMENT);
/* 396 */     return doReadJavaScriptWithScope();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readMaxKey() {
/* 401 */     checkPreconditions("readMaxKey", BsonType.MAX_KEY);
/* 402 */     setState(getNextState());
/* 403 */     doReadMaxKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readMinKey() {
/* 408 */     checkPreconditions("readMinKey", BsonType.MIN_KEY);
/* 409 */     setState(getNextState());
/* 410 */     doReadMinKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readNull() {
/* 415 */     checkPreconditions("readNull", BsonType.NULL);
/* 416 */     setState(getNextState());
/* 417 */     doReadNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectId readObjectId() {
/* 422 */     checkPreconditions("readObjectId", BsonType.OBJECT_ID);
/* 423 */     setState(getNextState());
/* 424 */     return doReadObjectId();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonRegularExpression readRegularExpression() {
/* 429 */     checkPreconditions("readRegularExpression", BsonType.REGULAR_EXPRESSION);
/* 430 */     setState(getNextState());
/* 431 */     return doReadRegularExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDbPointer readDBPointer() {
/* 436 */     checkPreconditions("readDBPointer", BsonType.DB_POINTER);
/* 437 */     setState(getNextState());
/* 438 */     return doReadDBPointer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStartArray() {
/* 443 */     checkPreconditions("readStartArray", BsonType.ARRAY);
/* 444 */     doReadStartArray();
/* 445 */     setState(State.TYPE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStartDocument() {
/* 450 */     checkPreconditions("readStartDocument", BsonType.DOCUMENT);
/* 451 */     doReadStartDocument();
/* 452 */     setState(State.TYPE);
/*     */   }
/*     */ 
/*     */   
/*     */   public String readString() {
/* 457 */     checkPreconditions("readString", BsonType.STRING);
/* 458 */     setState(getNextState());
/* 459 */     return doReadString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readSymbol() {
/* 464 */     checkPreconditions("readSymbol", BsonType.SYMBOL);
/* 465 */     setState(getNextState());
/* 466 */     return doReadSymbol();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonTimestamp readTimestamp() {
/* 471 */     checkPreconditions("readTimestamp", BsonType.TIMESTAMP);
/* 472 */     setState(getNextState());
/* 473 */     return doReadTimestamp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readUndefined() {
/* 478 */     checkPreconditions("readUndefined", BsonType.UNDEFINED);
/* 479 */     setState(getNextState());
/* 480 */     doReadUndefined();
/*     */   }
/*     */ 
/*     */   
/*     */   public void skipName() {
/* 485 */     if (isClosed()) {
/* 486 */       throw new IllegalStateException("This instance has been closed");
/*     */     }
/* 488 */     if (getState() != State.NAME) {
/* 489 */       throwInvalidState("skipName", new State[] { State.NAME });
/*     */     }
/* 491 */     setState(State.VALUE);
/* 492 */     doSkipName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void skipValue() {
/* 497 */     if (isClosed()) {
/* 498 */       throw new IllegalStateException("BSONBinaryWriter");
/*     */     }
/* 500 */     if (getState() != State.VALUE) {
/* 501 */       throwInvalidState("skipValue", new State[] { State.VALUE });
/*     */     }
/*     */     
/* 504 */     doSkipValue();
/*     */     
/* 506 */     setState(State.TYPE);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonBinary readBinaryData(String name) {
/* 511 */     verifyName(name);
/* 512 */     return readBinaryData();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean(String name) {
/* 517 */     verifyName(name);
/* 518 */     return readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readDateTime(String name) {
/* 523 */     verifyName(name);
/* 524 */     return readDateTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble(String name) {
/* 529 */     verifyName(name);
/* 530 */     return readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt32(String name) {
/* 535 */     verifyName(name);
/* 536 */     return readInt32();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readInt64(String name) {
/* 541 */     verifyName(name);
/* 542 */     return readInt64();
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 readDecimal128(String name) {
/* 547 */     verifyName(name);
/* 548 */     return readDecimal128();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readJavaScript(String name) {
/* 553 */     verifyName(name);
/* 554 */     return readJavaScript();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readJavaScriptWithScope(String name) {
/* 559 */     verifyName(name);
/* 560 */     return readJavaScriptWithScope();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readMaxKey(String name) {
/* 565 */     verifyName(name);
/* 566 */     readMaxKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readMinKey(String name) {
/* 571 */     verifyName(name);
/* 572 */     readMinKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readName() {
/* 577 */     if (this.state == State.TYPE) {
/* 578 */       readBsonType();
/*     */     }
/* 580 */     if (this.state != State.NAME) {
/* 581 */       throwInvalidState("readName", new State[] { State.NAME });
/*     */     }
/*     */     
/* 584 */     this.state = State.VALUE;
/* 585 */     return this.currentName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readName(String name) {
/* 590 */     verifyName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readNull(String name) {
/* 595 */     verifyName(name);
/* 596 */     readNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectId readObjectId(String name) {
/* 601 */     verifyName(name);
/* 602 */     return readObjectId();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonRegularExpression readRegularExpression(String name) {
/* 607 */     verifyName(name);
/* 608 */     return readRegularExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDbPointer readDBPointer(String name) {
/* 613 */     verifyName(name);
/* 614 */     return readDBPointer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String readString(String name) {
/* 620 */     verifyName(name);
/* 621 */     return readString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readSymbol(String name) {
/* 626 */     verifyName(name);
/* 627 */     return readSymbol();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonTimestamp readTimestamp(String name) {
/* 632 */     verifyName(name);
/* 633 */     return readTimestamp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readUndefined(String name) {
/* 638 */     verifyName(name);
/* 639 */     readUndefined();
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
/*     */   protected void throwInvalidContextType(String methodName, BsonContextType actualContextType, BsonContextType... validContextTypes) {
/* 652 */     String validContextTypesString = StringUtils.join(" or ", Arrays.asList((Object[])validContextTypes));
/* 653 */     String message = String.format("%s can only be called when ContextType is %s, not when ContextType is %s.", new Object[] { methodName, validContextTypesString, actualContextType });
/*     */     
/* 655 */     throw new BsonInvalidOperationException(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void throwInvalidState(String methodName, State... validStates) {
/* 666 */     String validStatesString = StringUtils.join(" or ", Arrays.asList((Object[])validStates));
/* 667 */     String message = String.format("%s can only be called when State is %s, not when State is %s.", new Object[] { methodName, validStatesString, this.state });
/*     */     
/* 669 */     throw new BsonInvalidOperationException(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void verifyBSONType(String methodName, BsonType requiredBsonType) {
/* 679 */     if (this.state == State.INITIAL || this.state == State.SCOPE_DOCUMENT || this.state == State.TYPE) {
/* 680 */       readBsonType();
/*     */     }
/* 682 */     if (this.state == State.NAME)
/*     */     {
/* 684 */       skipName();
/*     */     }
/* 686 */     if (this.state != State.VALUE) {
/* 687 */       throwInvalidState(methodName, new State[] { State.VALUE });
/*     */     }
/* 689 */     if (this.currentBsonType != requiredBsonType) {
/* 690 */       throw new BsonInvalidOperationException(String.format("%s can only be called when CurrentBSONType is %s, not when CurrentBSONType is %s.", new Object[] { methodName, requiredBsonType, this.currentBsonType }));
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
/*     */   protected void verifyName(String expectedName) {
/* 703 */     readBsonType();
/* 704 */     String actualName = readName();
/* 705 */     if (!actualName.equals(expectedName)) {
/* 706 */       throw new BsonSerializationException(String.format("Expected element name to be '%s', not '%s'.", new Object[] { expectedName, actualName }));
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
/*     */   protected void checkPreconditions(String methodName, BsonType type) {
/* 718 */     if (isClosed()) {
/* 719 */       throw new IllegalStateException("BsonWriter is closed");
/*     */     }
/*     */     
/* 722 */     verifyBSONType(methodName, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Context getContext() {
/* 731 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setContext(Context context) {
/* 740 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected State getNextState() {
/* 749 */     switch (this.context.getContextType()) {
/*     */       case ARRAY:
/*     */       case DOCUMENT:
/*     */       case SCOPE_DOCUMENT:
/* 753 */         return State.TYPE;
/*     */       case TOP_LEVEL:
/* 755 */         return State.DONE;
/*     */     } 
/* 757 */     throw new BSONException(String.format("Unexpected ContextType %s.", new Object[] { this.context.getContextType() }));
/*     */   }
/*     */ 
/*     */   
/*     */   private void setStateOnEnd() {
/* 762 */     switch (getContext().getContextType()) {
/*     */       case ARRAY:
/*     */       case DOCUMENT:
/* 765 */         setState(State.TYPE);
/*     */         return;
/*     */       case TOP_LEVEL:
/* 768 */         setState(State.DONE);
/*     */         return;
/*     */     } 
/* 771 */     throw new BSONException(String.format("Unexpected ContextType %s.", new Object[] { getContext().getContextType() }));
/*     */   } protected abstract BsonBinary doReadBinaryData(); protected abstract byte doPeekBinarySubType(); protected abstract int doPeekBinarySize(); protected abstract boolean doReadBoolean(); protected abstract long doReadDateTime(); protected abstract double doReadDouble(); protected abstract void doReadEndArray(); protected abstract void doReadEndDocument(); protected abstract int doReadInt32(); protected abstract long doReadInt64(); protected abstract Decimal128 doReadDecimal128(); protected abstract String doReadJavaScript(); protected abstract String doReadJavaScriptWithScope(); protected abstract void doReadMaxKey(); protected abstract void doReadMinKey(); protected abstract void doReadNull(); protected abstract ObjectId doReadObjectId(); protected abstract BsonRegularExpression doReadRegularExpression(); protected abstract BsonDbPointer doReadDBPointer(); protected abstract void doReadStartArray();
/*     */   protected abstract void doReadStartDocument();
/*     */   protected abstract String doReadString();
/*     */   protected abstract String doReadSymbol();
/*     */   protected abstract BsonTimestamp doReadTimestamp();
/*     */   protected abstract void doReadUndefined();
/*     */   protected abstract void doSkipName();
/*     */   protected abstract void doSkipValue();
/*     */   public abstract BsonType readBsonType();
/*     */   protected class Mark implements BsonReaderMark { private final AbstractBsonReader.State state; private final AbstractBsonReader.Context parentContext;
/*     */     protected AbstractBsonReader.Context getParentContext() {
/* 783 */       return this.parentContext;
/*     */     }
/*     */     private final BsonContextType contextType; private final BsonType currentBsonType; private final String currentName;
/*     */     protected BsonContextType getContextType() {
/* 787 */       return this.contextType;
/*     */     }
/*     */     
/*     */     protected Mark() {
/* 791 */       this.state = AbstractBsonReader.this.state;
/* 792 */       this.parentContext = AbstractBsonReader.this.context.parentContext;
/* 793 */       this.contextType = AbstractBsonReader.this.context.contextType;
/* 794 */       this.currentBsonType = AbstractBsonReader.this.currentBsonType;
/* 795 */       this.currentName = AbstractBsonReader.this.currentName;
/*     */     }
/*     */     
/*     */     public void reset() {
/* 799 */       AbstractBsonReader.this.state = this.state;
/* 800 */       AbstractBsonReader.this.currentBsonType = this.currentBsonType;
/* 801 */       AbstractBsonReader.this.currentName = this.currentName;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract class Context
/*     */   {
/*     */     private final Context parentContext;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final BsonContextType contextType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Context(Context parentContext, BsonContextType contextType) {
/* 823 */       this.parentContext = parentContext;
/* 824 */       this.contextType = contextType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Context getParentContext() {
/* 833 */       return this.parentContext;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected BsonContextType getContextType() {
/* 842 */       return this.contextType;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum State
/*     */   {
/* 853 */     INITIAL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 858 */     TYPE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 863 */     NAME,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 868 */     VALUE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 873 */     SCOPE_DOCUMENT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 878 */     END_OF_DOCUMENT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 883 */     END_OF_ARRAY,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 888 */     DONE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 893 */     CLOSED;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\AbstractBsonReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */