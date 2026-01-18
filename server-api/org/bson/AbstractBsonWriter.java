/*      */ package org.bson;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ import org.bson.assertions.Assertions;
/*      */ import org.bson.types.Decimal128;
/*      */ import org.bson.types.ObjectId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractBsonWriter
/*      */   implements BsonWriter, Closeable
/*      */ {
/*      */   private final BsonWriterSettings settings;
/*   38 */   private final Stack<FieldNameValidator> fieldNameValidatorStack = new Stack<>();
/*      */   
/*      */   private State state;
/*      */   
/*      */   private Context context;
/*      */   
/*      */   private int serializationDepth;
/*      */   
/*      */   private boolean closed;
/*      */ 
/*      */   
/*      */   protected AbstractBsonWriter(BsonWriterSettings settings) {
/*   50 */     this(settings, new NoOpFieldNameValidator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractBsonWriter(BsonWriterSettings settings, FieldNameValidator validator) {
/*   60 */     if (validator == null) {
/*   61 */       throw new IllegalArgumentException("Validator can not be null");
/*      */     }
/*   63 */     this.settings = settings;
/*   64 */     this.fieldNameValidatorStack.push(validator);
/*   65 */     this.state = State.INITIAL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getName() {
/*   74 */     return this.context.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isClosed() {
/*   83 */     return this.closed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setState(State state) {
/*   92 */     this.state = state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected State getState() {
/*  101 */     return this.state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Context getContext() {
/*  110 */     return this.context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setContext(Context context) {
/*  119 */     this.context = context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartDocument(String name) {
/*  271 */     writeName(name);
/*  272 */     writeStartDocument();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeStartDocument() {
/*  277 */     checkPreconditions("writeStartDocument", new State[] { State.INITIAL, State.VALUE, State.SCOPE_DOCUMENT, State.DONE });
/*  278 */     if (this.context != null && this.context.name != null) {
/*  279 */       FieldNameValidator validator = ((FieldNameValidator)this.fieldNameValidatorStack.peek()).getValidatorForField(getName());
/*  280 */       this.fieldNameValidatorStack.push(validator);
/*  281 */       validator.start();
/*      */     } 
/*  283 */     this.serializationDepth++;
/*  284 */     if (this.serializationDepth > this.settings.getMaxSerializationDepth()) {
/*  285 */       throw new BsonSerializationException("Maximum serialization depth exceeded (does the object being serialized have a circular reference?).");
/*      */     }
/*      */ 
/*      */     
/*  289 */     doWriteStartDocument();
/*  290 */     setState(State.NAME);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeEndDocument() {
/*  295 */     checkPreconditions("writeEndDocument", new State[] { State.NAME });
/*      */     
/*  297 */     BsonContextType contextType = getContext().getContextType();
/*  298 */     if (contextType != BsonContextType.DOCUMENT && contextType != BsonContextType.SCOPE_DOCUMENT) {
/*  299 */       throwInvalidContextType("WriteEndDocument", contextType, new BsonContextType[] { BsonContextType.DOCUMENT, BsonContextType.SCOPE_DOCUMENT });
/*      */     }
/*      */     
/*  302 */     if (this.context.getParentContext() != null && (this.context.getParentContext()).name != null) {
/*  303 */       ((FieldNameValidator)this.fieldNameValidatorStack.pop()).end();
/*      */     }
/*  305 */     this.serializationDepth--;
/*      */     
/*  307 */     doWriteEndDocument();
/*      */     
/*  309 */     if (getContext() == null || getContext().getContextType() == BsonContextType.TOP_LEVEL) {
/*  310 */       setState(State.DONE);
/*      */     } else {
/*  312 */       setState(getNextState());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeStartArray(String name) {
/*  318 */     writeName(name);
/*  319 */     writeStartArray();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeStartArray() {
/*  324 */     checkPreconditions("writeStartArray", new State[] { State.VALUE });
/*      */     
/*  326 */     if (this.context != null && this.context.name != null) {
/*  327 */       this.fieldNameValidatorStack.push(((FieldNameValidator)this.fieldNameValidatorStack.peek()).getValidatorForField(getName()));
/*      */     }
/*  329 */     this.serializationDepth++;
/*  330 */     if (this.serializationDepth > this.settings.getMaxSerializationDepth()) {
/*  331 */       throw new BsonSerializationException("Maximum serialization depth exceeded (does the object being serialized have a circular reference?).");
/*      */     }
/*      */ 
/*      */     
/*  335 */     doWriteStartArray();
/*  336 */     setState(State.VALUE);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeEndArray() {
/*  341 */     checkPreconditions("writeEndArray", new State[] { State.VALUE });
/*      */     
/*  343 */     if (getContext().getContextType() != BsonContextType.ARRAY) {
/*  344 */       throwInvalidContextType("WriteEndArray", getContext().getContextType(), new BsonContextType[] { BsonContextType.ARRAY });
/*      */     }
/*      */     
/*  347 */     if (this.context.getParentContext() != null && (this.context.getParentContext()).name != null) {
/*  348 */       this.fieldNameValidatorStack.pop();
/*      */     }
/*  350 */     this.serializationDepth--;
/*      */     
/*  352 */     doWriteEndArray();
/*  353 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBinaryData(String name, BsonBinary binary) {
/*  358 */     Assertions.notNull("name", name);
/*  359 */     Assertions.notNull("value", binary);
/*  360 */     writeName(name);
/*  361 */     writeBinaryData(binary);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBinaryData(BsonBinary binary) {
/*  366 */     Assertions.notNull("value", binary);
/*  367 */     checkPreconditions("writeBinaryData", new State[] { State.VALUE, State.INITIAL });
/*  368 */     doWriteBinaryData(binary);
/*  369 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBoolean(String name, boolean value) {
/*  374 */     writeName(name);
/*  375 */     writeBoolean(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBoolean(boolean value) {
/*  380 */     checkPreconditions("writeBoolean", new State[] { State.VALUE, State.INITIAL });
/*  381 */     doWriteBoolean(value);
/*  382 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDateTime(String name, long value) {
/*  387 */     writeName(name);
/*  388 */     writeDateTime(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDateTime(long value) {
/*  393 */     checkPreconditions("writeDateTime", new State[] { State.VALUE, State.INITIAL });
/*  394 */     doWriteDateTime(value);
/*  395 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDBPointer(String name, BsonDbPointer value) {
/*  400 */     Assertions.notNull("name", name);
/*  401 */     Assertions.notNull("value", value);
/*  402 */     writeName(name);
/*  403 */     writeDBPointer(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDBPointer(BsonDbPointer value) {
/*  408 */     Assertions.notNull("value", value);
/*  409 */     checkPreconditions("writeDBPointer", new State[] { State.VALUE, State.INITIAL });
/*  410 */     doWriteDBPointer(value);
/*  411 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDouble(String name, double value) {
/*  416 */     writeName(name);
/*  417 */     writeDouble(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDouble(double value) {
/*  422 */     checkPreconditions("writeDBPointer", new State[] { State.VALUE, State.INITIAL });
/*  423 */     doWriteDouble(value);
/*  424 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeInt32(String name, int value) {
/*  429 */     writeName(name);
/*  430 */     writeInt32(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeInt32(int value) {
/*  435 */     checkPreconditions("writeInt32", new State[] { State.VALUE });
/*  436 */     doWriteInt32(value);
/*  437 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeInt64(String name, long value) {
/*  442 */     writeName(name);
/*  443 */     writeInt64(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeInt64(long value) {
/*  448 */     checkPreconditions("writeInt64", new State[] { State.VALUE });
/*  449 */     doWriteInt64(value);
/*  450 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDecimal128(Decimal128 value) {
/*  455 */     Assertions.notNull("value", value);
/*  456 */     checkPreconditions("writeInt64", new State[] { State.VALUE });
/*  457 */     doWriteDecimal128(value);
/*  458 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDecimal128(String name, Decimal128 value) {
/*  463 */     Assertions.notNull("name", name);
/*  464 */     Assertions.notNull("value", value);
/*  465 */     writeName(name);
/*  466 */     writeDecimal128(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeJavaScript(String name, String code) {
/*  471 */     Assertions.notNull("name", name);
/*  472 */     Assertions.notNull("value", code);
/*  473 */     writeName(name);
/*  474 */     writeJavaScript(code);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeJavaScript(String code) {
/*  479 */     Assertions.notNull("value", code);
/*  480 */     checkPreconditions("writeJavaScript", new State[] { State.VALUE });
/*  481 */     doWriteJavaScript(code);
/*  482 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeJavaScriptWithScope(String name, String code) {
/*  487 */     Assertions.notNull("name", name);
/*  488 */     Assertions.notNull("value", code);
/*  489 */     writeName(name);
/*  490 */     writeJavaScriptWithScope(code);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeJavaScriptWithScope(String code) {
/*  495 */     Assertions.notNull("value", code);
/*  496 */     checkPreconditions("writeJavaScriptWithScope", new State[] { State.VALUE });
/*  497 */     doWriteJavaScriptWithScope(code);
/*  498 */     setState(State.SCOPE_DOCUMENT);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMaxKey(String name) {
/*  503 */     writeName(name);
/*  504 */     writeMaxKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMaxKey() {
/*  509 */     checkPreconditions("writeMaxKey", new State[] { State.VALUE });
/*  510 */     doWriteMaxKey();
/*  511 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMinKey(String name) {
/*  516 */     writeName(name);
/*  517 */     writeMinKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMinKey() {
/*  522 */     checkPreconditions("writeMinKey", new State[] { State.VALUE });
/*  523 */     doWriteMinKey();
/*  524 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeName(String name) {
/*  529 */     Assertions.notNull("name", name);
/*  530 */     if (this.state != State.NAME) {
/*  531 */       throwInvalidState("WriteName", new State[] { State.NAME });
/*      */     }
/*  533 */     if (!((FieldNameValidator)this.fieldNameValidatorStack.peek()).validate(name)) {
/*  534 */       throw new IllegalArgumentException(String.format("Invalid BSON field name %s", new Object[] { name }));
/*      */     }
/*  536 */     doWriteName(name);
/*  537 */     this.context.name = name;
/*  538 */     this.state = State.VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doWriteName(String name) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNull(String name) {
/*  552 */     writeName(name);
/*  553 */     writeNull();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeNull() {
/*  558 */     checkPreconditions("writeNull", new State[] { State.VALUE });
/*  559 */     doWriteNull();
/*  560 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeObjectId(String name, ObjectId objectId) {
/*  565 */     Assertions.notNull("name", name);
/*  566 */     Assertions.notNull("value", objectId);
/*  567 */     writeName(name);
/*  568 */     writeObjectId(objectId);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeObjectId(ObjectId objectId) {
/*  573 */     Assertions.notNull("value", objectId);
/*  574 */     checkPreconditions("writeObjectId", new State[] { State.VALUE });
/*  575 */     doWriteObjectId(objectId);
/*  576 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRegularExpression(String name, BsonRegularExpression regularExpression) {
/*  581 */     Assertions.notNull("name", name);
/*  582 */     Assertions.notNull("value", regularExpression);
/*  583 */     writeName(name);
/*  584 */     writeRegularExpression(regularExpression);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRegularExpression(BsonRegularExpression regularExpression) {
/*  589 */     Assertions.notNull("value", regularExpression);
/*  590 */     checkPreconditions("writeRegularExpression", new State[] { State.VALUE });
/*  591 */     doWriteRegularExpression(regularExpression);
/*  592 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeString(String name, String value) {
/*  597 */     Assertions.notNull("name", name);
/*  598 */     Assertions.notNull("value", value);
/*  599 */     writeName(name);
/*  600 */     writeString(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeString(String value) {
/*  605 */     Assertions.notNull("value", value);
/*  606 */     checkPreconditions("writeString", new State[] { State.VALUE });
/*  607 */     doWriteString(value);
/*  608 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeSymbol(String name, String value) {
/*  614 */     Assertions.notNull("name", name);
/*  615 */     Assertions.notNull("value", value);
/*  616 */     writeName(name);
/*  617 */     writeSymbol(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeSymbol(String value) {
/*  622 */     Assertions.notNull("value", value);
/*  623 */     checkPreconditions("writeSymbol", new State[] { State.VALUE });
/*  624 */     doWriteSymbol(value);
/*  625 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeTimestamp(String name, BsonTimestamp value) {
/*  630 */     Assertions.notNull("name", name);
/*  631 */     Assertions.notNull("value", value);
/*  632 */     writeName(name);
/*  633 */     writeTimestamp(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeTimestamp(BsonTimestamp value) {
/*  638 */     Assertions.notNull("value", value);
/*  639 */     checkPreconditions("writeTimestamp", new State[] { State.VALUE });
/*  640 */     doWriteTimestamp(value);
/*  641 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeUndefined(String name) {
/*  646 */     writeName(name);
/*  647 */     writeUndefined();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeUndefined() {
/*  652 */     checkPreconditions("writeUndefined", new State[] { State.VALUE });
/*  653 */     doWriteUndefined();
/*  654 */     setState(getNextState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected State getNextState() {
/*  664 */     if (getContext().getContextType() == BsonContextType.ARRAY) {
/*  665 */       return State.VALUE;
/*      */     }
/*  667 */     return State.NAME;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean checkState(State[] validStates) {
/*  678 */     for (State cur : validStates) {
/*  679 */       if (cur == getState()) {
/*  680 */         return true;
/*      */       }
/*      */     } 
/*  683 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkPreconditions(String methodName, State... validStates) {
/*  696 */     if (isClosed()) {
/*  697 */       throw new IllegalStateException("BsonWriter is closed");
/*      */     }
/*      */     
/*  700 */     if (!checkState(validStates)) {
/*  701 */       throwInvalidState(methodName, validStates);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void throwInvalidContextType(String methodName, BsonContextType actualContextType, BsonContextType... validContextTypes) {
/*  715 */     String validContextTypesString = StringUtils.join(" or ", Arrays.asList((Object[])validContextTypes));
/*  716 */     throw new BsonInvalidOperationException(String.format("%s can only be called when ContextType is %s, not when ContextType is %s.", new Object[] { methodName, validContextTypesString, actualContextType }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void throwInvalidState(String methodName, State... validStates) {
/*  729 */     if ((this.state == State.INITIAL || this.state == State.SCOPE_DOCUMENT || this.state == State.DONE) && 
/*  730 */       !methodName.startsWith("end") && !methodName.equals("writeName")) {
/*      */       
/*  732 */       String typeName = methodName.substring(5);
/*  733 */       if (typeName.startsWith("start")) {
/*  734 */         typeName = typeName.substring(5);
/*      */       }
/*  736 */       String article = "A";
/*  737 */       if (Arrays.<Character>asList(new Character[] { Character.valueOf('A'), Character.valueOf('E'), Character.valueOf('I'), Character.valueOf('O'), Character.valueOf('U') }).contains(Character.valueOf(typeName.charAt(0)))) {
/*  738 */         article = "An";
/*      */       }
/*  740 */       throw new BsonInvalidOperationException(String.format("%s %s value cannot be written to the root level of a BSON document.", new Object[] { article, typeName }));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  745 */     String validStatesString = StringUtils.join(" or ", Arrays.asList((Object[])validStates));
/*  746 */     throw new BsonInvalidOperationException(String.format("%s can only be called when State is %s, not when State is %s", new Object[] { methodName, validStatesString, this.state }));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/*  752 */     this.closed = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void pipe(BsonReader reader) {
/*  757 */     Assertions.notNull("reader", reader);
/*  758 */     pipeDocument(reader, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pipe(BsonReader reader, List<BsonElement> extraElements) {
/*  769 */     Assertions.notNull("reader", reader);
/*  770 */     Assertions.notNull("extraElements", extraElements);
/*  771 */     pipeDocument(reader, extraElements);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pipeExtraElements(List<BsonElement> extraElements) {
/*  780 */     Assertions.notNull("extraElements", extraElements);
/*  781 */     for (BsonElement cur : extraElements) {
/*  782 */       writeName(cur.getName());
/*  783 */       pipeValue(cur.getValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean abortPipe() {
/*  795 */     return false;
/*      */   }
/*      */   
/*      */   private void pipeDocument(BsonReader reader, List<BsonElement> extraElements) {
/*  799 */     reader.readStartDocument();
/*  800 */     writeStartDocument();
/*  801 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  802 */       writeName(reader.readName());
/*  803 */       pipeValue(reader);
/*  804 */       if (abortPipe()) {
/*      */         return;
/*      */       }
/*      */     } 
/*  808 */     reader.readEndDocument();
/*  809 */     if (extraElements != null) {
/*  810 */       pipeExtraElements(extraElements);
/*      */     }
/*  812 */     writeEndDocument();
/*      */   }
/*      */   
/*      */   private void pipeJavascriptWithScope(BsonReader reader) {
/*  816 */     writeJavaScriptWithScope(reader.readJavaScriptWithScope());
/*  817 */     pipeDocument(reader, null);
/*      */   }
/*      */   
/*      */   private void pipeValue(BsonReader reader) {
/*  821 */     switch (reader.getCurrentBsonType()) {
/*      */       case DOCUMENT:
/*  823 */         pipeDocument(reader, null);
/*      */         return;
/*      */       case ARRAY:
/*  826 */         pipeArray(reader);
/*      */         return;
/*      */       case DOUBLE:
/*  829 */         writeDouble(reader.readDouble());
/*      */         return;
/*      */       case STRING:
/*  832 */         writeString(reader.readString());
/*      */         return;
/*      */       case BINARY:
/*  835 */         writeBinaryData(reader.readBinaryData());
/*      */         return;
/*      */       case UNDEFINED:
/*  838 */         reader.readUndefined();
/*  839 */         writeUndefined();
/*      */         return;
/*      */       case OBJECT_ID:
/*  842 */         writeObjectId(reader.readObjectId());
/*      */         return;
/*      */       case BOOLEAN:
/*  845 */         writeBoolean(reader.readBoolean());
/*      */         return;
/*      */       case DATE_TIME:
/*  848 */         writeDateTime(reader.readDateTime());
/*      */         return;
/*      */       case NULL:
/*  851 */         reader.readNull();
/*  852 */         writeNull();
/*      */         return;
/*      */       case REGULAR_EXPRESSION:
/*  855 */         writeRegularExpression(reader.readRegularExpression());
/*      */         return;
/*      */       case JAVASCRIPT:
/*  858 */         writeJavaScript(reader.readJavaScript());
/*      */         return;
/*      */       case SYMBOL:
/*  861 */         writeSymbol(reader.readSymbol());
/*      */         return;
/*      */       case JAVASCRIPT_WITH_SCOPE:
/*  864 */         pipeJavascriptWithScope(reader);
/*      */         return;
/*      */       case INT32:
/*  867 */         writeInt32(reader.readInt32());
/*      */         return;
/*      */       case TIMESTAMP:
/*  870 */         writeTimestamp(reader.readTimestamp());
/*      */         return;
/*      */       case INT64:
/*  873 */         writeInt64(reader.readInt64());
/*      */         return;
/*      */       case DECIMAL128:
/*  876 */         writeDecimal128(reader.readDecimal128());
/*      */         return;
/*      */       case MIN_KEY:
/*  879 */         reader.readMinKey();
/*  880 */         writeMinKey();
/*      */         return;
/*      */       case DB_POINTER:
/*  883 */         writeDBPointer(reader.readDBPointer());
/*      */         return;
/*      */       case MAX_KEY:
/*  886 */         reader.readMaxKey();
/*  887 */         writeMaxKey();
/*      */         return;
/*      */     } 
/*  890 */     throw new IllegalArgumentException("unhandled BSON type: " + reader.getCurrentBsonType());
/*      */   }
/*      */ 
/*      */   
/*      */   private void pipeDocument(BsonDocument value) {
/*  895 */     writeStartDocument();
/*  896 */     for (Map.Entry<String, BsonValue> cur : value.entrySet()) {
/*  897 */       writeName(cur.getKey());
/*  898 */       pipeValue(cur.getValue());
/*      */     } 
/*  900 */     writeEndDocument();
/*      */   }
/*      */   
/*      */   private void pipeArray(BsonReader reader) {
/*  904 */     reader.readStartArray();
/*  905 */     writeStartArray();
/*  906 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  907 */       pipeValue(reader);
/*  908 */       if (abortPipe()) {
/*      */         return;
/*      */       }
/*      */     } 
/*  912 */     reader.readEndArray();
/*  913 */     writeEndArray();
/*      */   }
/*      */   
/*      */   private void pipeArray(BsonArray array) {
/*  917 */     writeStartArray();
/*  918 */     for (BsonValue cur : array) {
/*  919 */       pipeValue(cur);
/*      */     }
/*  921 */     writeEndArray();
/*      */   }
/*      */   
/*      */   private void pipeJavascriptWithScope(BsonJavaScriptWithScope javaScriptWithScope) {
/*  925 */     writeJavaScriptWithScope(javaScriptWithScope.getCode());
/*  926 */     pipeDocument(javaScriptWithScope.getScope());
/*      */   } protected abstract void doWriteStartDocument(); protected abstract void doWriteEndDocument(); protected abstract void doWriteStartArray(); protected abstract void doWriteEndArray(); protected abstract void doWriteBinaryData(BsonBinary paramBsonBinary); protected abstract void doWriteBoolean(boolean paramBoolean); protected abstract void doWriteDateTime(long paramLong); protected abstract void doWriteDBPointer(BsonDbPointer paramBsonDbPointer); protected abstract void doWriteDouble(double paramDouble); protected abstract void doWriteInt32(int paramInt); protected abstract void doWriteInt64(long paramLong);
/*      */   protected abstract void doWriteDecimal128(Decimal128 paramDecimal128);
/*      */   private void pipeValue(BsonValue value) {
/*  930 */     switch (value.getBsonType()) {
/*      */       case DOCUMENT:
/*  932 */         pipeDocument(value.asDocument());
/*      */         return;
/*      */       case ARRAY:
/*  935 */         pipeArray(value.asArray());
/*      */         return;
/*      */       case DOUBLE:
/*  938 */         writeDouble(value.asDouble().getValue());
/*      */         return;
/*      */       case STRING:
/*  941 */         writeString(value.asString().getValue());
/*      */         return;
/*      */       case BINARY:
/*  944 */         writeBinaryData(value.asBinary());
/*      */         return;
/*      */       case UNDEFINED:
/*  947 */         writeUndefined();
/*      */         return;
/*      */       case OBJECT_ID:
/*  950 */         writeObjectId(value.asObjectId().getValue());
/*      */         return;
/*      */       case BOOLEAN:
/*  953 */         writeBoolean(value.asBoolean().getValue());
/*      */         return;
/*      */       case DATE_TIME:
/*  956 */         writeDateTime(value.asDateTime().getValue());
/*      */         return;
/*      */       case NULL:
/*  959 */         writeNull();
/*      */         return;
/*      */       case REGULAR_EXPRESSION:
/*  962 */         writeRegularExpression(value.asRegularExpression());
/*      */         return;
/*      */       case JAVASCRIPT:
/*  965 */         writeJavaScript(value.asJavaScript().getCode());
/*      */         return;
/*      */       case SYMBOL:
/*  968 */         writeSymbol(value.asSymbol().getSymbol());
/*      */         return;
/*      */       case JAVASCRIPT_WITH_SCOPE:
/*  971 */         pipeJavascriptWithScope(value.asJavaScriptWithScope());
/*      */         return;
/*      */       case INT32:
/*  974 */         writeInt32(value.asInt32().getValue());
/*      */         return;
/*      */       case TIMESTAMP:
/*  977 */         writeTimestamp(value.asTimestamp());
/*      */         return;
/*      */       case INT64:
/*  980 */         writeInt64(value.asInt64().getValue());
/*      */         return;
/*      */       case DECIMAL128:
/*  983 */         writeDecimal128(value.asDecimal128().getValue());
/*      */         return;
/*      */       case MIN_KEY:
/*  986 */         writeMinKey();
/*      */         return;
/*      */       case DB_POINTER:
/*  989 */         writeDBPointer(value.asDBPointer());
/*      */         return;
/*      */       case MAX_KEY:
/*  992 */         writeMaxKey();
/*      */         return;
/*      */     } 
/*  995 */     throw new IllegalArgumentException("unhandled BSON type: " + value.getBsonType());
/*      */   } protected abstract void doWriteJavaScript(String paramString); protected abstract void doWriteJavaScriptWithScope(String paramString);
/*      */   protected abstract void doWriteMaxKey();
/*      */   protected abstract void doWriteMinKey();
/*      */   protected abstract void doWriteNull();
/*      */   protected abstract void doWriteObjectId(ObjectId paramObjectId);
/*      */   protected abstract void doWriteRegularExpression(BsonRegularExpression paramBsonRegularExpression);
/*      */   protected abstract void doWriteString(String paramString);
/*      */   protected abstract void doWriteSymbol(String paramString);
/*      */   protected abstract void doWriteTimestamp(BsonTimestamp paramBsonTimestamp);
/*      */   protected abstract void doWriteUndefined();
/* 1006 */   public enum State { INITIAL,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1011 */     NAME,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1016 */     VALUE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     SCOPE_DOCUMENT,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1026 */     DONE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1031 */     CLOSED; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class Context
/*      */   {
/*      */     private final Context parentContext;
/*      */ 
/*      */     
/*      */     private final BsonContextType contextType;
/*      */ 
/*      */     
/*      */     private String name;
/*      */ 
/*      */ 
/*      */     
/*      */     public Context(Context from) {
/* 1050 */       this.parentContext = from.parentContext;
/* 1051 */       this.contextType = from.contextType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Context(Context parentContext, BsonContextType contextType) {
/* 1061 */       this.parentContext = parentContext;
/* 1062 */       this.contextType = contextType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Context getParentContext() {
/* 1071 */       return this.parentContext;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BsonContextType getContextType() {
/* 1080 */       return this.contextType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Context copy() {
/* 1089 */       return new Context(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class Mark
/*      */   {
/* 1107 */     private final AbstractBsonWriter.Context markedContext = AbstractBsonWriter.this.context.copy();
/* 1108 */     private final AbstractBsonWriter.State markedState = AbstractBsonWriter.this.state;
/* 1109 */     private final String currentName = AbstractBsonWriter.this.context.name;
/* 1110 */     private final int serializationDepth = AbstractBsonWriter.this.serializationDepth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void reset() {
/* 1118 */       AbstractBsonWriter.this.setContext(this.markedContext);
/* 1119 */       AbstractBsonWriter.this.setState(this.markedState);
/* 1120 */       AbstractBsonWriter.this.context.name = this.currentName;
/* 1121 */       AbstractBsonWriter.this.serializationDepth = this.serializationDepth;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\AbstractBsonWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */