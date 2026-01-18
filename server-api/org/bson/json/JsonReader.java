/*      */ package org.bson.json;
/*      */ 
/*      */ import java.io.Reader;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParsePosition;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.time.format.DateTimeParseException;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.UUID;
/*      */ import org.bson.AbstractBsonReader;
/*      */ import org.bson.BsonBinary;
/*      */ import org.bson.BsonBinarySubType;
/*      */ import org.bson.BsonContextType;
/*      */ import org.bson.BsonDbPointer;
/*      */ import org.bson.BsonInvalidOperationException;
/*      */ import org.bson.BsonReaderMark;
/*      */ import org.bson.BsonRegularExpression;
/*      */ import org.bson.BsonTimestamp;
/*      */ import org.bson.BsonType;
/*      */ import org.bson.BsonUndefined;
/*      */ import org.bson.internal.Base64;
/*      */ import org.bson.types.Decimal128;
/*      */ import org.bson.types.MaxKey;
/*      */ import org.bson.types.MinKey;
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
/*      */ public class JsonReader
/*      */   extends AbstractBsonReader
/*      */ {
/*      */   private final JsonScanner scanner;
/*      */   private JsonToken pushedToken;
/*      */   private Object currentValue;
/*      */   
/*      */   public JsonReader(String json) {
/*   78 */     this(new JsonScanner(json));
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
/*      */   public JsonReader(Reader reader) {
/*   92 */     this(new JsonScanner(reader));
/*      */   }
/*      */ 
/*      */   
/*      */   private JsonReader(JsonScanner scanner) {
/*   97 */     this.scanner = scanner;
/*   98 */     setContext(new Context(null, BsonContextType.TOP_LEVEL));
/*      */   }
/*      */ 
/*      */   
/*      */   protected BsonBinary doReadBinaryData() {
/*  103 */     return (BsonBinary)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected byte doPeekBinarySubType() {
/*  108 */     return doReadBinaryData().getType();
/*      */   }
/*      */ 
/*      */   
/*      */   protected int doPeekBinarySize() {
/*  113 */     return (doReadBinaryData().getData()).length;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean doReadBoolean() {
/*  118 */     return ((Boolean)this.currentValue).booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public BsonType readBsonType() {
/*      */     String value;
/*  124 */     if (isClosed()) {
/*  125 */       throw new IllegalStateException("This instance has been closed");
/*      */     }
/*  127 */     if (getState() == AbstractBsonReader.State.INITIAL || getState() == AbstractBsonReader.State.DONE || getState() == AbstractBsonReader.State.SCOPE_DOCUMENT)
/*      */     {
/*  129 */       setState(AbstractBsonReader.State.TYPE);
/*      */     }
/*  131 */     if (getState() != AbstractBsonReader.State.TYPE) {
/*  132 */       throwInvalidState("readBSONType", new AbstractBsonReader.State[] { AbstractBsonReader.State.TYPE });
/*      */     }
/*      */     
/*  135 */     if (getContext().getContextType() == BsonContextType.DOCUMENT) {
/*  136 */       JsonToken nameToken = popToken();
/*  137 */       switch (nameToken.getType()) {
/*      */         case ARRAY:
/*      */         case BINARY:
/*  140 */           setCurrentName(nameToken.<String>getValue(String.class));
/*      */           break;
/*      */         case BOOLEAN:
/*  143 */           setState(AbstractBsonReader.State.END_OF_DOCUMENT);
/*  144 */           return BsonType.END_OF_DOCUMENT;
/*      */         default:
/*  146 */           throw new JsonParseException("JSON reader was expecting a name but found '%s'.", new Object[] { nameToken.getValue() });
/*      */       } 
/*      */       
/*  149 */       JsonToken colonToken = popToken();
/*  150 */       if (colonToken.getType() != JsonTokenType.COLON) {
/*  151 */         throw new JsonParseException("JSON reader was expecting ':' but found '%s'.", new Object[] { colonToken.getValue() });
/*      */       }
/*      */     } 
/*      */     
/*  155 */     JsonToken token = popToken();
/*  156 */     if (getContext().getContextType() == BsonContextType.ARRAY && token.getType() == JsonTokenType.END_ARRAY) {
/*  157 */       setState(AbstractBsonReader.State.END_OF_ARRAY);
/*  158 */       return BsonType.END_OF_DOCUMENT;
/*      */     } 
/*      */     
/*  161 */     boolean noValueFound = false;
/*  162 */     switch (token.getType()) {
/*      */       case DATE_TIME:
/*  164 */         setCurrentBsonType(BsonType.ARRAY);
/*      */         break;
/*      */       case DOCUMENT:
/*  167 */         visitExtendedJSON();
/*      */         break;
/*      */       case DOUBLE:
/*  170 */         setCurrentBsonType(BsonType.DOUBLE);
/*  171 */         this.currentValue = token.getValue();
/*      */         break;
/*      */       case INT32:
/*  174 */         setCurrentBsonType(BsonType.END_OF_DOCUMENT);
/*      */         break;
/*      */       case INT64:
/*  177 */         setCurrentBsonType(BsonType.INT32);
/*  178 */         this.currentValue = token.getValue();
/*      */         break;
/*      */       case DECIMAL128:
/*  181 */         setCurrentBsonType(BsonType.INT64);
/*  182 */         this.currentValue = token.getValue();
/*      */         break;
/*      */       case JAVASCRIPT:
/*  185 */         setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
/*  186 */         this.currentValue = token.getValue();
/*      */         break;
/*      */       case ARRAY:
/*  189 */         setCurrentBsonType(BsonType.STRING);
/*  190 */         this.currentValue = token.getValue();
/*      */         break;
/*      */       case BINARY:
/*  193 */         value = token.<String>getValue(String.class);
/*      */         
/*  195 */         if ("false".equals(value) || "true".equals(value)) {
/*  196 */           setCurrentBsonType(BsonType.BOOLEAN);
/*  197 */           this.currentValue = Boolean.valueOf(Boolean.parseBoolean(value)); break;
/*  198 */         }  if ("Infinity".equals(value)) {
/*  199 */           setCurrentBsonType(BsonType.DOUBLE);
/*  200 */           this.currentValue = Double.valueOf(Double.POSITIVE_INFINITY); break;
/*  201 */         }  if ("NaN".equals(value)) {
/*  202 */           setCurrentBsonType(BsonType.DOUBLE);
/*  203 */           this.currentValue = Double.valueOf(Double.NaN); break;
/*  204 */         }  if ("null".equals(value)) {
/*  205 */           setCurrentBsonType(BsonType.NULL); break;
/*  206 */         }  if ("undefined".equals(value)) {
/*  207 */           setCurrentBsonType(BsonType.UNDEFINED); break;
/*  208 */         }  if ("MinKey".equals(value)) {
/*  209 */           visitEmptyConstructor();
/*  210 */           setCurrentBsonType(BsonType.MIN_KEY);
/*  211 */           this.currentValue = new MinKey(); break;
/*  212 */         }  if ("MaxKey".equals(value)) {
/*  213 */           visitEmptyConstructor();
/*  214 */           setCurrentBsonType(BsonType.MAX_KEY);
/*  215 */           this.currentValue = new MaxKey(); break;
/*  216 */         }  if ("BinData".equals(value)) {
/*  217 */           setCurrentBsonType(BsonType.BINARY);
/*  218 */           this.currentValue = visitBinDataConstructor(); break;
/*  219 */         }  if ("Date".equals(value)) {
/*  220 */           this.currentValue = visitDateTimeConstructorWithOutNew();
/*  221 */           setCurrentBsonType(BsonType.STRING); break;
/*  222 */         }  if ("HexData".equals(value)) {
/*  223 */           setCurrentBsonType(BsonType.BINARY);
/*  224 */           this.currentValue = visitHexDataConstructor(); break;
/*  225 */         }  if ("ISODate".equals(value)) {
/*  226 */           setCurrentBsonType(BsonType.DATE_TIME);
/*  227 */           this.currentValue = Long.valueOf(visitISODateTimeConstructor()); break;
/*  228 */         }  if ("NumberInt".equals(value)) {
/*  229 */           setCurrentBsonType(BsonType.INT32);
/*  230 */           this.currentValue = Integer.valueOf(visitNumberIntConstructor()); break;
/*  231 */         }  if ("NumberLong".equals(value)) {
/*  232 */           setCurrentBsonType(BsonType.INT64);
/*  233 */           this.currentValue = Long.valueOf(visitNumberLongConstructor()); break;
/*  234 */         }  if ("NumberDecimal".equals(value)) {
/*  235 */           setCurrentBsonType(BsonType.DECIMAL128);
/*  236 */           this.currentValue = visitNumberDecimalConstructor(); break;
/*  237 */         }  if ("ObjectId".equals(value)) {
/*  238 */           setCurrentBsonType(BsonType.OBJECT_ID);
/*  239 */           this.currentValue = visitObjectIdConstructor(); break;
/*  240 */         }  if ("Timestamp".equals(value)) {
/*  241 */           setCurrentBsonType(BsonType.TIMESTAMP);
/*  242 */           this.currentValue = visitTimestampConstructor(); break;
/*  243 */         }  if ("RegExp".equals(value)) {
/*  244 */           setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
/*  245 */           this.currentValue = visitRegularExpressionConstructor(); break;
/*  246 */         }  if ("DBPointer".equals(value)) {
/*  247 */           setCurrentBsonType(BsonType.DB_POINTER);
/*  248 */           this.currentValue = visitDBPointerConstructor(); break;
/*  249 */         }  if ("UUID".equals(value)) {
/*  250 */           setCurrentBsonType(BsonType.BINARY);
/*  251 */           this.currentValue = visitUUIDConstructor(); break;
/*  252 */         }  if ("new".equals(value)) {
/*  253 */           visitNew(); break;
/*      */         } 
/*  255 */         noValueFound = true;
/*      */         break;
/*      */       
/*      */       default:
/*  259 */         noValueFound = true;
/*      */         break;
/*      */     } 
/*  262 */     if (noValueFound) {
/*  263 */       throw new JsonParseException("JSON reader was expecting a value but found '%s'.", new Object[] { token.getValue() });
/*      */     }
/*      */     
/*  266 */     if (getContext().getContextType() == BsonContextType.ARRAY || getContext().getContextType() == BsonContextType.DOCUMENT) {
/*  267 */       JsonToken commaToken = popToken();
/*  268 */       if (commaToken.getType() != JsonTokenType.COMMA) {
/*  269 */         pushToken(commaToken);
/*      */       }
/*      */     } 
/*      */     
/*  273 */     switch (getContext().getContextType())
/*      */     
/*      */     { 
/*      */       default:
/*  277 */         setState(AbstractBsonReader.State.NAME);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  285 */         return getCurrentBsonType();case BOOLEAN: case DATE_TIME: case DOCUMENT: break; }  setState(AbstractBsonReader.State.VALUE); return getCurrentBsonType();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Decimal128 doReadDecimal128() {
/*  291 */     return (Decimal128)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected long doReadDateTime() {
/*  296 */     return ((Long)this.currentValue).longValue();
/*      */   }
/*      */ 
/*      */   
/*      */   protected double doReadDouble() {
/*  301 */     return ((Double)this.currentValue).doubleValue();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doReadEndArray() {
/*  306 */     setContext(getContext().getParentContext());
/*      */     
/*  308 */     if (getContext().getContextType() == BsonContextType.ARRAY || getContext().getContextType() == BsonContextType.DOCUMENT) {
/*  309 */       JsonToken commaToken = popToken();
/*  310 */       if (commaToken.getType() != JsonTokenType.COMMA) {
/*  311 */         pushToken(commaToken);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doReadEndDocument() {
/*  318 */     setContext(getContext().getParentContext());
/*  319 */     if (getContext() != null && getContext().getContextType() == BsonContextType.SCOPE_DOCUMENT) {
/*  320 */       setContext(getContext().getParentContext());
/*  321 */       verifyToken(JsonTokenType.END_OBJECT);
/*      */     } 
/*      */     
/*  324 */     if (getContext() == null) {
/*  325 */       throw new JsonParseException("Unexpected end of document.");
/*      */     }
/*      */     
/*  328 */     if (getContext().getContextType() == BsonContextType.ARRAY || getContext().getContextType() == BsonContextType.DOCUMENT) {
/*  329 */       JsonToken commaToken = popToken();
/*  330 */       if (commaToken.getType() != JsonTokenType.COMMA) {
/*  331 */         pushToken(commaToken);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int doReadInt32() {
/*  338 */     return ((Integer)this.currentValue).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   protected long doReadInt64() {
/*  343 */     return ((Long)this.currentValue).longValue();
/*      */   }
/*      */ 
/*      */   
/*      */   protected String doReadJavaScript() {
/*  348 */     return (String)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String doReadJavaScriptWithScope() {
/*  353 */     return (String)this.currentValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doReadMaxKey() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doReadMinKey() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doReadNull() {}
/*      */ 
/*      */   
/*      */   protected ObjectId doReadObjectId() {
/*  370 */     return (ObjectId)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BsonRegularExpression doReadRegularExpression() {
/*  375 */     return (BsonRegularExpression)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BsonDbPointer doReadDBPointer() {
/*  380 */     return (BsonDbPointer)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doReadStartArray() {
/*  385 */     setContext(new Context(getContext(), BsonContextType.ARRAY));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doReadStartDocument() {
/*  390 */     setContext(new Context(getContext(), BsonContextType.DOCUMENT));
/*      */   }
/*      */ 
/*      */   
/*      */   protected String doReadString() {
/*  395 */     return (String)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String doReadSymbol() {
/*  400 */     return (String)this.currentValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BsonTimestamp doReadTimestamp() {
/*  405 */     return (BsonTimestamp)this.currentValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doReadUndefined() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doSkipName() {}
/*      */ 
/*      */   
/*      */   protected void doSkipValue() {
/*  418 */     switch (getCurrentBsonType()) {
/*      */       case ARRAY:
/*  420 */         readStartArray();
/*  421 */         while (readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  422 */           skipValue();
/*      */         }
/*  424 */         readEndArray();
/*      */         break;
/*      */       case BINARY:
/*  427 */         readBinaryData();
/*      */         break;
/*      */       case BOOLEAN:
/*  430 */         readBoolean();
/*      */         break;
/*      */       case DATE_TIME:
/*  433 */         readDateTime();
/*      */         break;
/*      */       case DOCUMENT:
/*  436 */         readStartDocument();
/*  437 */         while (readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  438 */           skipName();
/*  439 */           skipValue();
/*      */         } 
/*  441 */         readEndDocument();
/*      */         break;
/*      */       case DOUBLE:
/*  444 */         readDouble();
/*      */         break;
/*      */       case INT32:
/*  447 */         readInt32();
/*      */         break;
/*      */       case INT64:
/*  450 */         readInt64();
/*      */         break;
/*      */       case DECIMAL128:
/*  453 */         readDecimal128();
/*      */         break;
/*      */       case JAVASCRIPT:
/*  456 */         readJavaScript();
/*      */         break;
/*      */       case JAVASCRIPT_WITH_SCOPE:
/*  459 */         readJavaScriptWithScope();
/*  460 */         readStartDocument();
/*  461 */         while (readBsonType() != BsonType.END_OF_DOCUMENT) {
/*  462 */           skipName();
/*  463 */           skipValue();
/*      */         } 
/*  465 */         readEndDocument();
/*      */         break;
/*      */       case MAX_KEY:
/*  468 */         readMaxKey();
/*      */         break;
/*      */       case MIN_KEY:
/*  471 */         readMinKey();
/*      */         break;
/*      */       case NULL:
/*  474 */         readNull();
/*      */         break;
/*      */       case OBJECT_ID:
/*  477 */         readObjectId();
/*      */         break;
/*      */       case REGULAR_EXPRESSION:
/*  480 */         readRegularExpression();
/*      */         break;
/*      */       case STRING:
/*  483 */         readString();
/*      */         break;
/*      */       case SYMBOL:
/*  486 */         readSymbol();
/*      */         break;
/*      */       case TIMESTAMP:
/*  489 */         readTimestamp();
/*      */         break;
/*      */       case UNDEFINED:
/*  492 */         readUndefined();
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private JsonToken popToken() {
/*  499 */     if (this.pushedToken != null) {
/*  500 */       JsonToken token = this.pushedToken;
/*  501 */       this.pushedToken = null;
/*  502 */       return token;
/*      */     } 
/*  504 */     return this.scanner.nextToken();
/*      */   }
/*      */ 
/*      */   
/*      */   private void pushToken(JsonToken token) {
/*  509 */     if (this.pushedToken == null) {
/*  510 */       this.pushedToken = token;
/*      */     } else {
/*  512 */       throw new BsonInvalidOperationException("There is already a pending token.");
/*      */     } 
/*      */   }
/*      */   
/*      */   private void verifyToken(JsonTokenType expectedType) {
/*  517 */     JsonToken token = popToken();
/*  518 */     if (expectedType != token.getType()) {
/*  519 */       throw new JsonParseException("JSON reader expected token type '%s' but found '%s'.", new Object[] { expectedType, token.getValue() });
/*      */     }
/*      */   }
/*      */   
/*      */   private void verifyToken(JsonTokenType expectedType, Object expectedValue) {
/*  524 */     JsonToken token = popToken();
/*  525 */     if (expectedType != token.getType()) {
/*  526 */       throw new JsonParseException("JSON reader expected token type '%s' but found '%s'.", new Object[] { expectedType, token.getValue() });
/*      */     }
/*  528 */     if (!expectedValue.equals(token.getValue())) {
/*  529 */       throw new JsonParseException("JSON reader expected '%s' but found '%s'.", new Object[] { expectedValue, token.getValue() });
/*      */     }
/*      */   }
/*      */   
/*      */   private void verifyString(String expected) {
/*  534 */     if (expected == null) {
/*  535 */       throw new IllegalArgumentException("Can't be null");
/*      */     }
/*      */     
/*  538 */     JsonToken token = popToken();
/*  539 */     JsonTokenType type = token.getType();
/*      */     
/*  541 */     if ((type != JsonTokenType.STRING && type != JsonTokenType.UNQUOTED_STRING) || !expected.equals(token.getValue())) {
/*  542 */       throw new JsonParseException("JSON reader expected '%s' but found '%s'.", new Object[] { expected, token.getValue() });
/*      */     }
/*      */   }
/*      */   
/*      */   private void visitNew() {
/*  547 */     JsonToken typeToken = popToken();
/*  548 */     if (typeToken.getType() != JsonTokenType.UNQUOTED_STRING) {
/*  549 */       throw new JsonParseException("JSON reader expected a type name but found '%s'.", new Object[] { typeToken.getValue() });
/*      */     }
/*      */     
/*  552 */     String value = typeToken.<String>getValue(String.class);
/*      */     
/*  554 */     if ("MinKey".equals(value)) {
/*  555 */       visitEmptyConstructor();
/*  556 */       setCurrentBsonType(BsonType.MIN_KEY);
/*  557 */       this.currentValue = new MinKey();
/*  558 */     } else if ("MaxKey".equals(value)) {
/*  559 */       visitEmptyConstructor();
/*  560 */       setCurrentBsonType(BsonType.MAX_KEY);
/*  561 */       this.currentValue = new MaxKey();
/*  562 */     } else if ("BinData".equals(value)) {
/*  563 */       this.currentValue = visitBinDataConstructor();
/*  564 */       setCurrentBsonType(BsonType.BINARY);
/*  565 */     } else if ("Date".equals(value)) {
/*  566 */       this.currentValue = Long.valueOf(visitDateTimeConstructor());
/*  567 */       setCurrentBsonType(BsonType.DATE_TIME);
/*  568 */     } else if ("HexData".equals(value)) {
/*  569 */       this.currentValue = visitHexDataConstructor();
/*  570 */       setCurrentBsonType(BsonType.BINARY);
/*  571 */     } else if ("ISODate".equals(value)) {
/*  572 */       this.currentValue = Long.valueOf(visitISODateTimeConstructor());
/*  573 */       setCurrentBsonType(BsonType.DATE_TIME);
/*  574 */     } else if ("NumberInt".equals(value)) {
/*  575 */       this.currentValue = Integer.valueOf(visitNumberIntConstructor());
/*  576 */       setCurrentBsonType(BsonType.INT32);
/*  577 */     } else if ("NumberLong".equals(value)) {
/*  578 */       this.currentValue = Long.valueOf(visitNumberLongConstructor());
/*  579 */       setCurrentBsonType(BsonType.INT64);
/*  580 */     } else if ("NumberDecimal".equals(value)) {
/*  581 */       this.currentValue = visitNumberDecimalConstructor();
/*  582 */       setCurrentBsonType(BsonType.DECIMAL128);
/*  583 */     } else if ("ObjectId".equals(value)) {
/*  584 */       this.currentValue = visitObjectIdConstructor();
/*  585 */       setCurrentBsonType(BsonType.OBJECT_ID);
/*  586 */     } else if ("RegExp".equals(value)) {
/*  587 */       this.currentValue = visitRegularExpressionConstructor();
/*  588 */       setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
/*  589 */     } else if ("DBPointer".equals(value)) {
/*  590 */       this.currentValue = visitDBPointerConstructor();
/*  591 */       setCurrentBsonType(BsonType.DB_POINTER);
/*  592 */     } else if ("UUID".equals(value)) {
/*  593 */       this.currentValue = visitUUIDConstructor();
/*  594 */       setCurrentBsonType(BsonType.BINARY);
/*      */     } else {
/*  596 */       throw new JsonParseException("JSON reader expected a type name but found '%s'.", new Object[] { value });
/*      */     } 
/*      */   }
/*      */   
/*      */   private void visitExtendedJSON() {
/*  601 */     JsonToken nameToken = popToken();
/*  602 */     String value = nameToken.<String>getValue(String.class);
/*  603 */     JsonTokenType type = nameToken.getType();
/*      */     
/*  605 */     if (type == JsonTokenType.STRING || type == JsonTokenType.UNQUOTED_STRING) {
/*      */       
/*  607 */       if ("$binary".equals(value) || "$type".equals(value)) {
/*  608 */         this.currentValue = visitBinDataExtendedJson(value);
/*  609 */         if (this.currentValue != null) {
/*  610 */           setCurrentBsonType(BsonType.BINARY); return;
/*      */         } 
/*      */       } 
/*  613 */       if ("$uuid".equals(value)) {
/*  614 */         this.currentValue = visitUuidExtendedJson();
/*  615 */         setCurrentBsonType(BsonType.BINARY); return;
/*      */       } 
/*  617 */       if ("$regex".equals(value) || "$options".equals(value)) {
/*  618 */         this.currentValue = visitRegularExpressionExtendedJson(value);
/*  619 */         if (this.currentValue != null) {
/*  620 */           setCurrentBsonType(BsonType.REGULAR_EXPRESSION); return;
/*      */         } 
/*      */       } else {
/*  623 */         if ("$code".equals(value)) {
/*  624 */           visitJavaScriptExtendedJson(); return;
/*      */         } 
/*  626 */         if ("$date".equals(value)) {
/*  627 */           this.currentValue = Long.valueOf(visitDateTimeExtendedJson());
/*  628 */           setCurrentBsonType(BsonType.DATE_TIME); return;
/*      */         } 
/*  630 */         if ("$maxKey".equals(value)) {
/*  631 */           this.currentValue = visitMaxKeyExtendedJson();
/*  632 */           setCurrentBsonType(BsonType.MAX_KEY); return;
/*      */         } 
/*  634 */         if ("$minKey".equals(value)) {
/*  635 */           this.currentValue = visitMinKeyExtendedJson();
/*  636 */           setCurrentBsonType(BsonType.MIN_KEY); return;
/*      */         } 
/*  638 */         if ("$oid".equals(value)) {
/*  639 */           this.currentValue = visitObjectIdExtendedJson();
/*  640 */           setCurrentBsonType(BsonType.OBJECT_ID); return;
/*      */         } 
/*  642 */         if ("$regularExpression".equals(value)) {
/*  643 */           this.currentValue = visitNewRegularExpressionExtendedJson();
/*  644 */           setCurrentBsonType(BsonType.REGULAR_EXPRESSION); return;
/*      */         } 
/*  646 */         if ("$symbol".equals(value)) {
/*  647 */           this.currentValue = visitSymbolExtendedJson();
/*  648 */           setCurrentBsonType(BsonType.SYMBOL); return;
/*      */         } 
/*  650 */         if ("$timestamp".equals(value)) {
/*  651 */           this.currentValue = visitTimestampExtendedJson();
/*  652 */           setCurrentBsonType(BsonType.TIMESTAMP); return;
/*      */         } 
/*  654 */         if ("$undefined".equals(value)) {
/*  655 */           this.currentValue = visitUndefinedExtendedJson();
/*  656 */           setCurrentBsonType(BsonType.UNDEFINED); return;
/*      */         } 
/*  658 */         if ("$numberLong".equals(value)) {
/*  659 */           this.currentValue = visitNumberLongExtendedJson();
/*  660 */           setCurrentBsonType(BsonType.INT64); return;
/*      */         } 
/*  662 */         if ("$numberInt".equals(value)) {
/*  663 */           this.currentValue = visitNumberIntExtendedJson();
/*  664 */           setCurrentBsonType(BsonType.INT32); return;
/*      */         } 
/*  666 */         if ("$numberDouble".equals(value)) {
/*  667 */           this.currentValue = visitNumberDoubleExtendedJson();
/*  668 */           setCurrentBsonType(BsonType.DOUBLE); return;
/*      */         } 
/*  670 */         if ("$numberDecimal".equals(value)) {
/*  671 */           this.currentValue = visitNumberDecimalExtendedJson();
/*  672 */           setCurrentBsonType(BsonType.DECIMAL128); return;
/*      */         } 
/*  674 */         if ("$dbPointer".equals(value)) {
/*  675 */           this.currentValue = visitDbPointerExtendedJson();
/*  676 */           setCurrentBsonType(BsonType.DB_POINTER);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  681 */     pushToken(nameToken);
/*  682 */     setCurrentBsonType(BsonType.DOCUMENT);
/*      */   }
/*      */   
/*      */   private void visitEmptyConstructor() {
/*  686 */     JsonToken nextToken = popToken();
/*  687 */     if (nextToken.getType() == JsonTokenType.LEFT_PAREN) {
/*  688 */       verifyToken(JsonTokenType.RIGHT_PAREN);
/*      */     } else {
/*  690 */       pushToken(nextToken);
/*      */     } 
/*      */   }
/*      */   
/*      */   private BsonBinary visitBinDataConstructor() {
/*  695 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  696 */     JsonToken subTypeToken = popToken();
/*  697 */     if (subTypeToken.getType() != JsonTokenType.INT32) {
/*  698 */       throw new JsonParseException("JSON reader expected a binary subtype but found '%s'.", new Object[] { subTypeToken.getValue() });
/*      */     }
/*  700 */     verifyToken(JsonTokenType.COMMA);
/*  701 */     JsonToken bytesToken = popToken();
/*  702 */     if (bytesToken.getType() != JsonTokenType.UNQUOTED_STRING && bytesToken.getType() != JsonTokenType.STRING) {
/*  703 */       throw new JsonParseException("JSON reader expected a string but found '%s'.", new Object[] { bytesToken.getValue() });
/*      */     }
/*  705 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*      */     
/*  707 */     byte[] bytes = Base64.decode(bytesToken.<String>getValue(String.class));
/*  708 */     return new BsonBinary(((Integer)subTypeToken.<Integer>getValue(Integer.class)).byteValue(), bytes);
/*      */   }
/*      */   
/*      */   private BsonBinary visitUUIDConstructor() {
/*  712 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  713 */     String hexString = readStringFromExtendedJson().replace("-", "");
/*  714 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  715 */     return new BsonBinary(BsonBinarySubType.UUID_STANDARD, decodeHex(hexString));
/*      */   }
/*      */   
/*      */   private BsonRegularExpression visitRegularExpressionConstructor() {
/*  719 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  720 */     String pattern = readStringFromExtendedJson();
/*  721 */     String options = "";
/*  722 */     JsonToken commaToken = popToken();
/*  723 */     if (commaToken.getType() == JsonTokenType.COMMA) {
/*  724 */       options = readStringFromExtendedJson();
/*      */     } else {
/*  726 */       pushToken(commaToken);
/*      */     } 
/*  728 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  729 */     return new BsonRegularExpression(pattern, options);
/*      */   }
/*      */   
/*      */   private ObjectId visitObjectIdConstructor() {
/*  733 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  734 */     ObjectId objectId = new ObjectId(readStringFromExtendedJson());
/*  735 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  736 */     return objectId;
/*      */   }
/*      */   
/*      */   private BsonTimestamp visitTimestampConstructor() {
/*  740 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  741 */     JsonToken timeToken = popToken();
/*      */     
/*  743 */     if (timeToken.getType() != JsonTokenType.INT32) {
/*  744 */       throw new JsonParseException("JSON reader expected an integer but found '%s'.", new Object[] { timeToken.getValue() });
/*      */     }
/*  746 */     int time = ((Integer)timeToken.<Integer>getValue(Integer.class)).intValue();
/*      */     
/*  748 */     verifyToken(JsonTokenType.COMMA);
/*  749 */     JsonToken incrementToken = popToken();
/*      */     
/*  751 */     if (incrementToken.getType() != JsonTokenType.INT32) {
/*  752 */       throw new JsonParseException("JSON reader expected an integer but found '%s'.", new Object[] { timeToken.getValue() });
/*      */     }
/*  754 */     int increment = ((Integer)incrementToken.<Integer>getValue(Integer.class)).intValue();
/*      */ 
/*      */     
/*  757 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  758 */     return new BsonTimestamp(time, increment);
/*      */   }
/*      */   
/*      */   private BsonDbPointer visitDBPointerConstructor() {
/*  762 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  763 */     String namespace = readStringFromExtendedJson();
/*  764 */     verifyToken(JsonTokenType.COMMA);
/*  765 */     ObjectId id = new ObjectId(readStringFromExtendedJson());
/*  766 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  767 */     return new BsonDbPointer(namespace, id);
/*      */   }
/*      */   private int visitNumberIntConstructor() {
/*      */     int value;
/*  771 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  772 */     JsonToken valueToken = popToken();
/*      */     
/*  774 */     if (valueToken.getType() == JsonTokenType.INT32) {
/*  775 */       value = ((Integer)valueToken.<Integer>getValue(Integer.class)).intValue();
/*  776 */     } else if (valueToken.getType() == JsonTokenType.STRING) {
/*  777 */       value = Integer.parseInt(valueToken.<String>getValue(String.class));
/*      */     } else {
/*  779 */       throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", new Object[] { valueToken.getValue() });
/*      */     } 
/*  781 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  782 */     return value;
/*      */   }
/*      */   private long visitNumberLongConstructor() {
/*      */     long value;
/*  786 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  787 */     JsonToken valueToken = popToken();
/*      */     
/*  789 */     if (valueToken.getType() == JsonTokenType.INT32 || valueToken.getType() == JsonTokenType.INT64) {
/*  790 */       value = ((Long)valueToken.<Long>getValue(Long.class)).longValue();
/*  791 */     } else if (valueToken.getType() == JsonTokenType.STRING) {
/*  792 */       value = Long.parseLong(valueToken.<String>getValue(String.class));
/*      */     } else {
/*  794 */       throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", new Object[] { valueToken.getValue() });
/*      */     } 
/*  796 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  797 */     return value;
/*      */   }
/*      */   private Decimal128 visitNumberDecimalConstructor() {
/*      */     Decimal128 value;
/*  801 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  802 */     JsonToken valueToken = popToken();
/*      */     
/*  804 */     if (valueToken.getType() == JsonTokenType.INT32 || valueToken.getType() == JsonTokenType.INT64 || valueToken
/*  805 */       .getType() == JsonTokenType.DOUBLE) {
/*  806 */       value = valueToken.<Decimal128>getValue(Decimal128.class);
/*  807 */     } else if (valueToken.getType() == JsonTokenType.STRING) {
/*  808 */       value = Decimal128.parse(valueToken.<String>getValue(String.class));
/*      */     } else {
/*  810 */       throw new JsonParseException("JSON reader expected a number or a string but found '%s'.", new Object[] { valueToken.getValue() });
/*      */     } 
/*  812 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*  813 */     return value;
/*      */   }
/*      */   
/*      */   private long visitISODateTimeConstructor() {
/*  817 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*      */     
/*  819 */     JsonToken token = popToken();
/*  820 */     if (token.getType() == JsonTokenType.RIGHT_PAREN)
/*  821 */       return (new Date()).getTime(); 
/*  822 */     if (token.getType() != JsonTokenType.STRING) {
/*  823 */       throw new JsonParseException("JSON reader expected a string but found '%s'.", new Object[] { token.getValue() });
/*      */     }
/*      */     
/*  826 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*      */     
/*  828 */     String dateTimeString = token.<String>getValue(String.class);
/*      */     
/*      */     try {
/*  831 */       return DateTimeFormatter.parse(dateTimeString);
/*  832 */     } catch (DateTimeParseException e) {
/*  833 */       throw new JsonParseException("Failed to parse string as a date: " + dateTimeString, e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private BsonBinary visitHexDataConstructor() {
/*  838 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  839 */     JsonToken subTypeToken = popToken();
/*  840 */     if (subTypeToken.getType() != JsonTokenType.INT32) {
/*  841 */       throw new JsonParseException("JSON reader expected a binary subtype but found '%s'.", new Object[] { subTypeToken.getValue() });
/*      */     }
/*  843 */     verifyToken(JsonTokenType.COMMA);
/*  844 */     String hex = readStringFromExtendedJson();
/*  845 */     verifyToken(JsonTokenType.RIGHT_PAREN);
/*      */     
/*  847 */     if ((hex.length() & 0x1) != 0) {
/*  848 */       hex = "0" + hex;
/*      */     }
/*      */     
/*  851 */     for (BsonBinarySubType subType : BsonBinarySubType.values()) {
/*  852 */       if (subType.getValue() == ((Integer)subTypeToken.<Integer>getValue(Integer.class)).intValue()) {
/*  853 */         return new BsonBinary(subType, decodeHex(hex));
/*      */       }
/*      */     } 
/*  856 */     return new BsonBinary(decodeHex(hex));
/*      */   }
/*      */   
/*      */   private long visitDateTimeConstructor() {
/*  860 */     DateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
/*      */     
/*  862 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*      */     
/*  864 */     JsonToken token = popToken();
/*  865 */     if (token.getType() == JsonTokenType.RIGHT_PAREN)
/*  866 */       return (new Date()).getTime(); 
/*  867 */     if (token.getType() == JsonTokenType.STRING) {
/*  868 */       verifyToken(JsonTokenType.RIGHT_PAREN);
/*  869 */       String s = token.<String>getValue(String.class);
/*  870 */       ParsePosition pos = new ParsePosition(0);
/*  871 */       Date dateTime = format.parse(s, pos);
/*  872 */       if (dateTime != null && pos.getIndex() == s.length()) {
/*  873 */         return dateTime.getTime();
/*      */       }
/*  875 */       throw new JsonParseException("JSON reader expected a date in 'EEE MMM dd yyyy HH:mm:ss z' format but found '%s'.", new Object[] { s });
/*      */     } 
/*      */     
/*  878 */     if (token.getType() == JsonTokenType.INT32 || token.getType() == JsonTokenType.INT64) {
/*  879 */       long[] values = new long[7];
/*  880 */       int pos = 0;
/*      */       while (true) {
/*  882 */         if (pos < values.length) {
/*  883 */           values[pos++] = ((Long)token.getValue((Class)Long.class)).longValue();
/*      */         }
/*  885 */         token = popToken();
/*  886 */         if (token.getType() == JsonTokenType.RIGHT_PAREN) {
/*      */           break;
/*      */         }
/*  889 */         if (token.getType() != JsonTokenType.COMMA) {
/*  890 */           throw new JsonParseException("JSON reader expected a ',' or a ')' but found '%s'.", new Object[] { token.getValue() });
/*      */         }
/*  892 */         token = popToken();
/*  893 */         if (token.getType() != JsonTokenType.INT32 && token.getType() != JsonTokenType.INT64) {
/*  894 */           throw new JsonParseException("JSON reader expected an integer but found '%s'.", new Object[] { token.getValue() });
/*      */         }
/*      */       } 
/*  897 */       if (pos == 1)
/*  898 */         return values[0]; 
/*  899 */       if (pos < 3 || pos > 7) {
/*  900 */         throw new JsonParseException("JSON reader expected 1 or 3-7 integers but found %d.", new Object[] { Integer.valueOf(pos) });
/*      */       }
/*      */       
/*  903 */       Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/*  904 */       calendar.set(1, (int)values[0]);
/*  905 */       calendar.set(2, (int)values[1]);
/*  906 */       calendar.set(5, (int)values[2]);
/*  907 */       calendar.set(11, (int)values[3]);
/*  908 */       calendar.set(12, (int)values[4]);
/*  909 */       calendar.set(13, (int)values[5]);
/*  910 */       calendar.set(14, (int)values[6]);
/*  911 */       return calendar.getTimeInMillis();
/*      */     } 
/*  913 */     throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", new Object[] { token.getValue() });
/*      */   }
/*      */ 
/*      */   
/*      */   private String visitDateTimeConstructorWithOutNew() {
/*  918 */     verifyToken(JsonTokenType.LEFT_PAREN);
/*  919 */     JsonToken token = popToken();
/*  920 */     if (token.getType() != JsonTokenType.RIGHT_PAREN) {
/*  921 */       while (token.getType() != JsonTokenType.END_OF_FILE) {
/*  922 */         token = popToken();
/*  923 */         if (token.getType() == JsonTokenType.RIGHT_PAREN) {
/*      */           break;
/*      */         }
/*      */       } 
/*  927 */       if (token.getType() != JsonTokenType.RIGHT_PAREN) {
/*  928 */         throw new JsonParseException("JSON reader expected a ')' but found '%s'.", new Object[] { token.getValue() });
/*      */       }
/*      */     } 
/*      */     
/*  932 */     DateFormat df = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
/*  933 */     return df.format(new Date());
/*      */   }
/*      */ 
/*      */   
/*      */   private BsonBinary visitBinDataExtendedJson(String firstKey) {
/*  938 */     Mark mark = new Mark();
/*      */     
/*      */     try {
/*  941 */       verifyToken(JsonTokenType.COLON);
/*      */       
/*  943 */       if (firstKey.equals("$binary")) {
/*  944 */         JsonToken nextToken = popToken();
/*  945 */         if (nextToken.getType() == JsonTokenType.BEGIN_OBJECT) {
/*  946 */           byte data[], type; JsonToken nameToken = popToken();
/*  947 */           String firstNestedKey = nameToken.<String>getValue(String.class);
/*      */ 
/*      */           
/*  950 */           if (firstNestedKey.equals("base64")) {
/*  951 */             verifyToken(JsonTokenType.COLON);
/*  952 */             data = Base64.decode(readStringFromExtendedJson());
/*  953 */             verifyToken(JsonTokenType.COMMA);
/*  954 */             verifyString("subType");
/*  955 */             verifyToken(JsonTokenType.COLON);
/*  956 */             type = readBinarySubtypeFromExtendedJson();
/*  957 */           } else if (firstNestedKey.equals("subType")) {
/*  958 */             verifyToken(JsonTokenType.COLON);
/*  959 */             type = readBinarySubtypeFromExtendedJson();
/*  960 */             verifyToken(JsonTokenType.COMMA);
/*  961 */             verifyString("base64");
/*  962 */             verifyToken(JsonTokenType.COLON);
/*  963 */             data = Base64.decode(readStringFromExtendedJson());
/*      */           } else {
/*  965 */             throw new JsonParseException("Unexpected key for $binary: " + firstNestedKey);
/*      */           } 
/*  967 */           verifyToken(JsonTokenType.END_OBJECT);
/*  968 */           verifyToken(JsonTokenType.END_OBJECT);
/*  969 */           return new BsonBinary(type, data);
/*      */         } 
/*  971 */         mark.reset();
/*  972 */         return visitLegacyBinaryExtendedJson(firstKey);
/*      */       } 
/*      */       
/*  975 */       mark.reset();
/*  976 */       return visitLegacyBinaryExtendedJson(firstKey);
/*      */     } finally {
/*      */       
/*  979 */       mark.discard();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private BsonBinary visitLegacyBinaryExtendedJson(String firstKey) {
/*  985 */     Mark mark = new Mark();
/*      */     try {
/*      */       byte data[], type;
/*  988 */       verifyToken(JsonTokenType.COLON);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  993 */       if (firstKey.equals("$binary")) {
/*  994 */         data = Base64.decode(readStringFromExtendedJson());
/*  995 */         verifyToken(JsonTokenType.COMMA);
/*  996 */         verifyString("$type");
/*  997 */         verifyToken(JsonTokenType.COLON);
/*  998 */         type = readBinarySubtypeFromExtendedJson();
/*      */       } else {
/* 1000 */         type = readBinarySubtypeFromExtendedJson();
/* 1001 */         verifyToken(JsonTokenType.COMMA);
/* 1002 */         verifyString("$binary");
/* 1003 */         verifyToken(JsonTokenType.COLON);
/* 1004 */         data = Base64.decode(readStringFromExtendedJson());
/*      */       } 
/* 1006 */       verifyToken(JsonTokenType.END_OBJECT);
/*      */       
/* 1008 */       return new BsonBinary(type, data);
/* 1009 */     } catch (JsonParseException e) {
/* 1010 */       mark.reset();
/* 1011 */       return null;
/* 1012 */     } catch (NumberFormatException e) {
/* 1013 */       mark.reset();
/* 1014 */       return null;
/*      */     } finally {
/* 1016 */       mark.discard();
/*      */     } 
/*      */   }
/*      */   
/*      */   private byte readBinarySubtypeFromExtendedJson() {
/* 1021 */     JsonToken subTypeToken = popToken();
/* 1022 */     if (subTypeToken.getType() != JsonTokenType.STRING && subTypeToken.getType() != JsonTokenType.INT32) {
/* 1023 */       throw new JsonParseException("JSON reader expected a string or number but found '%s'.", new Object[] { subTypeToken.getValue() });
/*      */     }
/*      */     
/* 1026 */     if (subTypeToken.getType() == JsonTokenType.STRING) {
/* 1027 */       return (byte)Integer.parseInt(subTypeToken.<String>getValue(String.class), 16);
/*      */     }
/* 1029 */     return ((Integer)subTypeToken.<Integer>getValue(Integer.class)).byteValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private long visitDateTimeExtendedJson() {
/*      */     long value;
/* 1035 */     verifyToken(JsonTokenType.COLON);
/* 1036 */     JsonToken valueToken = popToken();
/* 1037 */     if (valueToken.getType() == JsonTokenType.BEGIN_OBJECT) {
/* 1038 */       JsonToken nameToken = popToken();
/* 1039 */       String name = nameToken.<String>getValue(String.class);
/* 1040 */       if (!name.equals("$numberLong")) {
/* 1041 */         throw new JsonParseException(String.format("JSON reader expected $numberLong within $date, but found %s", new Object[] { name }));
/*      */       }
/* 1043 */       value = visitNumberLongExtendedJson().longValue();
/* 1044 */       verifyToken(JsonTokenType.END_OBJECT);
/*      */     } else {
/* 1046 */       if (valueToken.getType() == JsonTokenType.INT32 || valueToken.getType() == JsonTokenType.INT64) {
/* 1047 */         value = ((Long)valueToken.<Long>getValue(Long.class)).longValue();
/* 1048 */       } else if (valueToken.getType() == JsonTokenType.STRING) {
/* 1049 */         String dateTimeString = valueToken.<String>getValue(String.class);
/*      */         try {
/* 1051 */           value = DateTimeFormatter.parse(dateTimeString);
/* 1052 */         } catch (DateTimeParseException e) {
/* 1053 */           throw new JsonParseException("Failed to parse string as a date", e);
/*      */         } 
/*      */       } else {
/* 1056 */         throw new JsonParseException("JSON reader expected an integer or string but found '%s'.", new Object[] { valueToken.getValue() });
/*      */       } 
/* 1058 */       verifyToken(JsonTokenType.END_OBJECT);
/*      */     } 
/* 1060 */     return value;
/*      */   }
/*      */   
/*      */   private MaxKey visitMaxKeyExtendedJson() {
/* 1064 */     verifyToken(JsonTokenType.COLON);
/* 1065 */     verifyToken(JsonTokenType.INT32, Integer.valueOf(1));
/* 1066 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1067 */     return new MaxKey();
/*      */   }
/*      */   
/*      */   private MinKey visitMinKeyExtendedJson() {
/* 1071 */     verifyToken(JsonTokenType.COLON);
/* 1072 */     verifyToken(JsonTokenType.INT32, Integer.valueOf(1));
/* 1073 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1074 */     return new MinKey();
/*      */   }
/*      */   
/*      */   private ObjectId visitObjectIdExtendedJson() {
/* 1078 */     verifyToken(JsonTokenType.COLON);
/* 1079 */     ObjectId objectId = new ObjectId(readStringFromExtendedJson());
/* 1080 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1081 */     return objectId;
/*      */   }
/*      */   private BsonRegularExpression visitNewRegularExpressionExtendedJson() {
/*      */     String pattern;
/* 1085 */     verifyToken(JsonTokenType.COLON);
/* 1086 */     verifyToken(JsonTokenType.BEGIN_OBJECT);
/*      */ 
/*      */     
/* 1089 */     String options = "";
/*      */     
/* 1091 */     String firstKey = readStringFromExtendedJson();
/* 1092 */     if (firstKey.equals("pattern")) {
/* 1093 */       verifyToken(JsonTokenType.COLON);
/* 1094 */       pattern = readStringFromExtendedJson();
/* 1095 */       verifyToken(JsonTokenType.COMMA);
/* 1096 */       verifyString("options");
/* 1097 */       verifyToken(JsonTokenType.COLON);
/* 1098 */       options = readStringFromExtendedJson();
/* 1099 */     } else if (firstKey.equals("options")) {
/* 1100 */       verifyToken(JsonTokenType.COLON);
/* 1101 */       options = readStringFromExtendedJson();
/* 1102 */       verifyToken(JsonTokenType.COMMA);
/* 1103 */       verifyString("pattern");
/* 1104 */       verifyToken(JsonTokenType.COLON);
/* 1105 */       pattern = readStringFromExtendedJson();
/*      */     } else {
/* 1107 */       throw new JsonParseException("Expected 't' and 'i' fields in $timestamp document but found " + firstKey);
/*      */     } 
/*      */     
/* 1110 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1111 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1112 */     return new BsonRegularExpression(pattern, options);
/*      */   }
/*      */   
/*      */   private BsonRegularExpression visitRegularExpressionExtendedJson(String firstKey) {
/* 1116 */     Mark extendedJsonMark = new Mark();
/*      */     try {
/*      */       String pattern;
/* 1119 */       verifyToken(JsonTokenType.COLON);
/*      */ 
/*      */       
/* 1122 */       String options = "";
/* 1123 */       if (firstKey.equals("$regex")) {
/* 1124 */         pattern = readStringFromExtendedJson();
/* 1125 */         verifyToken(JsonTokenType.COMMA);
/* 1126 */         verifyString("$options");
/* 1127 */         verifyToken(JsonTokenType.COLON);
/* 1128 */         options = readStringFromExtendedJson();
/*      */       } else {
/* 1130 */         options = readStringFromExtendedJson();
/* 1131 */         verifyToken(JsonTokenType.COMMA);
/* 1132 */         verifyString("$regex");
/* 1133 */         verifyToken(JsonTokenType.COLON);
/* 1134 */         pattern = readStringFromExtendedJson();
/*      */       } 
/* 1136 */       verifyToken(JsonTokenType.END_OBJECT);
/* 1137 */       return new BsonRegularExpression(pattern, options);
/* 1138 */     } catch (JsonParseException e) {
/* 1139 */       extendedJsonMark.reset();
/* 1140 */       return null;
/*      */     } finally {
/* 1142 */       extendedJsonMark.discard();
/*      */     } 
/*      */   }
/*      */   
/*      */   private String readStringFromExtendedJson() {
/* 1147 */     JsonToken patternToken = popToken();
/* 1148 */     if (patternToken.getType() != JsonTokenType.STRING) {
/* 1149 */       throw new JsonParseException("JSON reader expected a string but found '%s'.", new Object[] { patternToken.getValue() });
/*      */     }
/* 1151 */     return patternToken.<String>getValue(String.class);
/*      */   }
/*      */ 
/*      */   
/*      */   private String visitSymbolExtendedJson() {
/* 1156 */     verifyToken(JsonTokenType.COLON);
/* 1157 */     String symbol = readStringFromExtendedJson();
/* 1158 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1159 */     return symbol;
/*      */   }
/*      */   private BsonTimestamp visitTimestampExtendedJson() {
/*      */     int time, increment;
/* 1163 */     verifyToken(JsonTokenType.COLON);
/* 1164 */     verifyToken(JsonTokenType.BEGIN_OBJECT);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1169 */     String firstKey = readStringFromExtendedJson();
/* 1170 */     if (firstKey.equals("t")) {
/* 1171 */       verifyToken(JsonTokenType.COLON);
/* 1172 */       time = readIntFromExtendedJson();
/* 1173 */       verifyToken(JsonTokenType.COMMA);
/* 1174 */       verifyString("i");
/* 1175 */       verifyToken(JsonTokenType.COLON);
/* 1176 */       increment = readIntFromExtendedJson();
/* 1177 */     } else if (firstKey.equals("i")) {
/* 1178 */       verifyToken(JsonTokenType.COLON);
/* 1179 */       increment = readIntFromExtendedJson();
/* 1180 */       verifyToken(JsonTokenType.COMMA);
/* 1181 */       verifyString("t");
/* 1182 */       verifyToken(JsonTokenType.COLON);
/* 1183 */       time = readIntFromExtendedJson();
/*      */     } else {
/* 1185 */       throw new JsonParseException("Expected 't' and 'i' fields in $timestamp document but found " + firstKey);
/*      */     } 
/*      */     
/* 1188 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1189 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1190 */     return new BsonTimestamp(time, increment);
/*      */   }
/*      */   private int readIntFromExtendedJson() {
/*      */     int value;
/* 1194 */     JsonToken nextToken = popToken();
/*      */     
/* 1196 */     if (nextToken.getType() == JsonTokenType.INT32) {
/* 1197 */       value = ((Integer)nextToken.<Integer>getValue(Integer.class)).intValue();
/* 1198 */     } else if (nextToken.getType() == JsonTokenType.INT64) {
/* 1199 */       value = ((Long)nextToken.<Long>getValue(Long.class)).intValue();
/*      */     } else {
/* 1201 */       throw new JsonParseException("JSON reader expected an integer but found '%s'.", new Object[] { nextToken.getValue() });
/*      */     } 
/* 1203 */     return value;
/*      */   }
/*      */   
/*      */   private BsonBinary visitUuidExtendedJson() {
/* 1207 */     verifyToken(JsonTokenType.COLON);
/* 1208 */     String uuidString = readStringFromExtendedJson();
/* 1209 */     verifyToken(JsonTokenType.END_OBJECT);
/*      */     try {
/* 1211 */       return new BsonBinary(UUID.fromString(uuidString));
/* 1212 */     } catch (IllegalArgumentException e) {
/* 1213 */       throw new JsonParseException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void visitJavaScriptExtendedJson() {
/* 1218 */     verifyToken(JsonTokenType.COLON);
/* 1219 */     String code = readStringFromExtendedJson();
/* 1220 */     JsonToken nextToken = popToken();
/* 1221 */     switch (nextToken.getType()) {
/*      */       case JAVASCRIPT_WITH_SCOPE:
/* 1223 */         verifyString("$scope");
/* 1224 */         verifyToken(JsonTokenType.COLON);
/* 1225 */         setState(AbstractBsonReader.State.VALUE);
/* 1226 */         this.currentValue = code;
/* 1227 */         setCurrentBsonType(BsonType.JAVASCRIPT_WITH_SCOPE);
/* 1228 */         setContext(new Context(getContext(), BsonContextType.SCOPE_DOCUMENT));
/*      */         return;
/*      */       case BOOLEAN:
/* 1231 */         this.currentValue = code;
/* 1232 */         setCurrentBsonType(BsonType.JAVASCRIPT);
/*      */         return;
/*      */     } 
/* 1235 */     throw new JsonParseException("JSON reader expected ',' or '}' but found '%s'.", new Object[] { nextToken });
/*      */   }
/*      */ 
/*      */   
/*      */   private BsonUndefined visitUndefinedExtendedJson() {
/* 1240 */     verifyToken(JsonTokenType.COLON);
/* 1241 */     JsonToken valueToken = popToken();
/* 1242 */     if (!((String)valueToken.<String>getValue(String.class)).equals("true")) {
/* 1243 */       throw new JsonParseException("JSON reader requires $undefined to have the value of true but found '%s'.", new Object[] { valueToken
/* 1244 */             .getValue() });
/*      */     }
/* 1246 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1247 */     return new BsonUndefined();
/*      */   }
/*      */   private Long visitNumberLongExtendedJson() {
/*      */     Long value;
/* 1251 */     verifyToken(JsonTokenType.COLON);
/*      */     
/* 1253 */     String longAsString = readStringFromExtendedJson();
/*      */     try {
/* 1255 */       value = Long.valueOf(longAsString);
/* 1256 */     } catch (NumberFormatException e) {
/* 1257 */       throw new JsonParseException(String.format("Exception converting value '%s' to type %s", new Object[] { longAsString, Long.class.getName() }), e);
/*      */     } 
/* 1259 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1260 */     return value;
/*      */   }
/*      */   private Integer visitNumberIntExtendedJson() {
/*      */     Integer value;
/* 1264 */     verifyToken(JsonTokenType.COLON);
/*      */     
/* 1266 */     String intAsString = readStringFromExtendedJson();
/*      */     try {
/* 1268 */       value = Integer.valueOf(intAsString);
/* 1269 */     } catch (NumberFormatException e) {
/* 1270 */       throw new JsonParseException(String.format("Exception converting value '%s' to type %s", new Object[] { intAsString, Integer.class.getName() }), e);
/*      */     } 
/* 1272 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1273 */     return value;
/*      */   }
/*      */   private Double visitNumberDoubleExtendedJson() {
/*      */     Double value;
/* 1277 */     verifyToken(JsonTokenType.COLON);
/*      */     
/* 1279 */     String doubleAsString = readStringFromExtendedJson();
/*      */     try {
/* 1281 */       value = Double.valueOf(doubleAsString);
/* 1282 */     } catch (NumberFormatException e) {
/* 1283 */       throw new JsonParseException(String.format("Exception converting value '%s' to type %s", new Object[] { doubleAsString, Double.class.getName() }), e);
/*      */     } 
/* 1285 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1286 */     return value;
/*      */   }
/*      */   private Decimal128 visitNumberDecimalExtendedJson() {
/*      */     Decimal128 value;
/* 1290 */     verifyToken(JsonTokenType.COLON);
/*      */     
/* 1292 */     String decimal128AsString = readStringFromExtendedJson();
/*      */     try {
/* 1294 */       value = Decimal128.parse(decimal128AsString);
/* 1295 */     } catch (NumberFormatException e) {
/* 1296 */       throw new JsonParseException(String.format("Exception converting value '%s' to type %s", new Object[] { decimal128AsString, Decimal128.class
/* 1297 */               .getName() }), e);
/*      */     } 
/* 1299 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1300 */     return value;
/*      */   } private BsonDbPointer visitDbPointerExtendedJson() {
/*      */     String ref;
/*      */     ObjectId oid;
/* 1304 */     verifyToken(JsonTokenType.COLON);
/* 1305 */     verifyToken(JsonTokenType.BEGIN_OBJECT);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1310 */     String firstKey = readStringFromExtendedJson();
/* 1311 */     if (firstKey.equals("$ref")) {
/* 1312 */       verifyToken(JsonTokenType.COLON);
/* 1313 */       ref = readStringFromExtendedJson();
/* 1314 */       verifyToken(JsonTokenType.COMMA);
/* 1315 */       verifyString("$id");
/* 1316 */       oid = readDbPointerIdFromExtendedJson();
/* 1317 */       verifyToken(JsonTokenType.END_OBJECT);
/* 1318 */     } else if (firstKey.equals("$id")) {
/* 1319 */       oid = readDbPointerIdFromExtendedJson();
/* 1320 */       verifyToken(JsonTokenType.COMMA);
/* 1321 */       verifyString("$ref");
/* 1322 */       verifyToken(JsonTokenType.COLON);
/* 1323 */       ref = readStringFromExtendedJson();
/*      */     } else {
/*      */       
/* 1326 */       throw new JsonParseException("Expected $ref and $id fields in $dbPointer document but found " + firstKey);
/*      */     } 
/* 1328 */     verifyToken(JsonTokenType.END_OBJECT);
/* 1329 */     return new BsonDbPointer(ref, oid);
/*      */   }
/*      */ 
/*      */   
/*      */   private ObjectId readDbPointerIdFromExtendedJson() {
/* 1334 */     verifyToken(JsonTokenType.COLON);
/* 1335 */     verifyToken(JsonTokenType.BEGIN_OBJECT);
/* 1336 */     verifyToken(JsonTokenType.STRING, "$oid");
/* 1337 */     ObjectId oid = visitObjectIdExtendedJson();
/* 1338 */     return oid;
/*      */   }
/*      */ 
/*      */   
/*      */   public BsonReaderMark getMark() {
/* 1343 */     return (BsonReaderMark)new Mark();
/*      */   }
/*      */ 
/*      */   
/*      */   protected Context getContext() {
/* 1348 */     return (Context)super.getContext();
/*      */   }
/*      */   
/*      */   protected class Mark extends AbstractBsonReader.Mark {
/*      */     private final JsonToken pushedToken;
/*      */     private final Object currentValue;
/*      */     private final int markPos;
/*      */     
/*      */     protected Mark() {
/* 1357 */       super(JsonReader.this);
/* 1358 */       this.pushedToken = JsonReader.this.pushedToken;
/* 1359 */       this.currentValue = JsonReader.this.currentValue;
/* 1360 */       this.markPos = JsonReader.this.scanner.mark();
/*      */     }
/*      */     
/*      */     public void reset() {
/* 1364 */       super.reset();
/* 1365 */       JsonReader.this.pushedToken = this.pushedToken;
/* 1366 */       JsonReader.this.currentValue = this.currentValue;
/* 1367 */       JsonReader.this.scanner.reset(this.markPos);
/* 1368 */       JsonReader.this.setContext(new JsonReader.Context(getParentContext(), getContextType()));
/*      */     }
/*      */     
/*      */     public void discard() {
/* 1372 */       JsonReader.this.scanner.discard(this.markPos);
/*      */     }
/*      */   }
/*      */   
/*      */   protected class Context
/*      */     extends AbstractBsonReader.Context {
/*      */     protected Context(AbstractBsonReader.Context parentContext, BsonContextType contextType) {
/* 1379 */       super(JsonReader.this, parentContext, contextType);
/*      */     }
/*      */     
/*      */     protected Context getParentContext() {
/* 1383 */       return (Context)super.getParentContext();
/*      */     }
/*      */     
/*      */     protected BsonContextType getContextType() {
/* 1387 */       return super.getContextType();
/*      */     }
/*      */   }
/*      */   
/*      */   private static byte[] decodeHex(String hex) {
/* 1392 */     if (hex.length() % 2 != 0) {
/* 1393 */       throw new IllegalArgumentException("A hex string must contain an even number of characters: " + hex);
/*      */     }
/*      */     
/* 1396 */     byte[] out = new byte[hex.length() / 2];
/*      */     
/* 1398 */     for (int i = 0; i < hex.length(); i += 2) {
/* 1399 */       int high = Character.digit(hex.charAt(i), 16);
/* 1400 */       int low = Character.digit(hex.charAt(i + 1), 16);
/* 1401 */       if (high == -1 || low == -1) {
/* 1402 */         throw new IllegalArgumentException("A hex string can only contain the characters 0-9, A-F, a-f: " + hex);
/*      */       }
/*      */       
/* 1405 */       out[i / 2] = (byte)(high * 16 + low);
/*      */     } 
/*      */     
/* 1408 */     return out;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */