/*     */ package org.bson;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class BsonDocumentReader
/*     */   extends AbstractBsonReader
/*     */ {
/*     */   private BsonValue currentValue;
/*     */   
/*     */   public BsonDocumentReader(BsonDocument document) {
/*  46 */     setContext(new Context(null, BsonContextType.TOP_LEVEL, document));
/*  47 */     this.currentValue = document;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BsonBinary doReadBinaryData() {
/*  52 */     return this.currentValue.asBinary();
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte doPeekBinarySubType() {
/*  57 */     return this.currentValue.asBinary().getType();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doPeekBinarySize() {
/*  62 */     return (this.currentValue.asBinary().getData()).length;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doReadBoolean() {
/*  67 */     return this.currentValue.asBoolean().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doReadDateTime() {
/*  72 */     return this.currentValue.asDateTime().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected double doReadDouble() {
/*  77 */     return this.currentValue.asDouble().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doReadEndArray() {
/*  82 */     setContext(getContext().getParentContext());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doReadEndDocument() {
/*  87 */     setContext(getContext().getParentContext());
/*  88 */     switch (getContext().getContextType()) {
/*     */       case ARRAY:
/*     */       case DOCUMENT:
/*  91 */         setState(AbstractBsonReader.State.TYPE);
/*     */         return;
/*     */       case TOP_LEVEL:
/*  94 */         setState(AbstractBsonReader.State.DONE);
/*     */         return;
/*     */     } 
/*  97 */     throw new BSONException("Unexpected ContextType.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int doReadInt32() {
/* 103 */     return this.currentValue.asInt32().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doReadInt64() {
/* 108 */     return this.currentValue.asInt64().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 doReadDecimal128() {
/* 113 */     return this.currentValue.asDecimal128().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadJavaScript() {
/* 118 */     return this.currentValue.asJavaScript().getCode();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadJavaScriptWithScope() {
/* 123 */     return this.currentValue.asJavaScriptWithScope().getCode();
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
/* 140 */     return this.currentValue.asObjectId().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BsonRegularExpression doReadRegularExpression() {
/* 145 */     return this.currentValue.asRegularExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BsonDbPointer doReadDBPointer() {
/* 150 */     return this.currentValue.asDBPointer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doReadStartArray() {
/* 155 */     BsonArray array = this.currentValue.asArray();
/* 156 */     setContext(new Context(getContext(), BsonContextType.ARRAY, array));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doReadStartDocument() {
/*     */     BsonDocument document;
/* 162 */     if (this.currentValue.getBsonType() == BsonType.JAVASCRIPT_WITH_SCOPE) {
/* 163 */       document = this.currentValue.asJavaScriptWithScope().getScope();
/*     */     } else {
/* 165 */       document = this.currentValue.asDocument();
/*     */     } 
/* 167 */     setContext(new Context(getContext(), BsonContextType.DOCUMENT, document));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadString() {
/* 172 */     return this.currentValue.asString().getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String doReadSymbol() {
/* 177 */     return this.currentValue.asSymbol().getSymbol();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BsonTimestamp doReadTimestamp() {
/* 182 */     return this.currentValue.asTimestamp();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doReadUndefined() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSkipName() {}
/*     */ 
/*     */   
/*     */   protected void doSkipValue() {}
/*     */ 
/*     */   
/*     */   public BsonType readBsonType() {
/*     */     Map.Entry<String, BsonValue> currentElement;
/* 199 */     if (getState() == AbstractBsonReader.State.INITIAL || getState() == AbstractBsonReader.State.SCOPE_DOCUMENT) {
/*     */       
/* 201 */       setCurrentBsonType(BsonType.DOCUMENT);
/* 202 */       setState(AbstractBsonReader.State.VALUE);
/* 203 */       return getCurrentBsonType();
/*     */     } 
/*     */     
/* 206 */     if (getState() != AbstractBsonReader.State.TYPE) {
/* 207 */       throwInvalidState("ReadBSONType", new AbstractBsonReader.State[] { AbstractBsonReader.State.TYPE });
/*     */     }
/*     */     
/* 210 */     switch (getContext().getContextType()) {
/*     */       case ARRAY:
/* 212 */         this.currentValue = getContext().getNextValue();
/* 213 */         if (this.currentValue == null) {
/* 214 */           setState(AbstractBsonReader.State.END_OF_ARRAY);
/* 215 */           return BsonType.END_OF_DOCUMENT;
/*     */         } 
/* 217 */         setState(AbstractBsonReader.State.VALUE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 233 */         setCurrentBsonType(this.currentValue.getBsonType());
/* 234 */         return getCurrentBsonType();case DOCUMENT: currentElement = getContext().getNextElement(); if (currentElement == null) { setState(AbstractBsonReader.State.END_OF_DOCUMENT); return BsonType.END_OF_DOCUMENT; }  setCurrentName(currentElement.getKey()); this.currentValue = currentElement.getValue(); setState(AbstractBsonReader.State.NAME); setCurrentBsonType(this.currentValue.getBsonType()); return getCurrentBsonType();
/*     */     } 
/*     */     throw new BSONException("Invalid ContextType.");
/*     */   }
/*     */   public BsonReaderMark getMark() {
/* 239 */     return new Mark();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Context getContext() {
/* 244 */     return (Context)super.getContext();
/*     */   }
/*     */   
/*     */   protected class Mark extends AbstractBsonReader.Mark {
/*     */     private final BsonValue currentValue;
/*     */     private final BsonDocumentReader.Context context;
/*     */     
/*     */     protected Mark() {
/* 252 */       this.currentValue = BsonDocumentReader.this.currentValue;
/* 253 */       this.context = BsonDocumentReader.this.getContext();
/* 254 */       this.context.mark();
/*     */     }
/*     */     
/*     */     public void reset() {
/* 258 */       super.reset();
/* 259 */       BsonDocumentReader.this.currentValue = this.currentValue;
/* 260 */       BsonDocumentReader.this.setContext(this.context);
/* 261 */       this.context.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BsonDocumentMarkableIterator<T>
/*     */     implements Iterator<T> {
/*     */     private Iterator<T> baseIterator;
/* 268 */     private List<T> markIterator = new ArrayList<>();
/*     */     private int curIndex;
/*     */     private boolean marking;
/*     */     
/*     */     protected BsonDocumentMarkableIterator(Iterator<T> baseIterator) {
/* 273 */       this.baseIterator = baseIterator;
/* 274 */       this.curIndex = 0;
/* 275 */       this.marking = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void mark() {
/* 282 */       this.marking = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void reset() {
/* 289 */       this.curIndex = 0;
/* 290 */       this.marking = false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 296 */       return (this.baseIterator.hasNext() || this.curIndex < this.markIterator.size());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public T next() {
/*     */       T value;
/* 303 */       if (this.curIndex < this.markIterator.size()) {
/* 304 */         value = this.markIterator.get(this.curIndex);
/* 305 */         if (this.marking) {
/* 306 */           this.curIndex++;
/*     */         } else {
/* 308 */           this.markIterator.remove(0);
/*     */         } 
/*     */       } else {
/* 311 */         value = this.baseIterator.next();
/* 312 */         if (this.marking) {
/* 313 */           this.markIterator.add(value);
/* 314 */           this.curIndex++;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 319 */       return value;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected class Context
/*     */     extends AbstractBsonReader.Context
/*     */   {
/*     */     private BsonDocumentReader.BsonDocumentMarkableIterator<Map.Entry<String, BsonValue>> documentIterator;
/*     */     private BsonDocumentReader.BsonDocumentMarkableIterator<BsonValue> arrayIterator;
/*     */     
/*     */     protected Context(Context parentContext, BsonContextType contextType, BsonArray array) {
/* 334 */       super(parentContext, contextType);
/* 335 */       this.arrayIterator = new BsonDocumentReader.BsonDocumentMarkableIterator<>(array.iterator());
/*     */     }
/*     */     
/*     */     protected Context(Context parentContext, BsonContextType contextType, BsonDocument document) {
/* 339 */       super(parentContext, contextType);
/* 340 */       this.documentIterator = new BsonDocumentReader.BsonDocumentMarkableIterator<>(document.entrySet().iterator());
/*     */     }
/*     */     
/*     */     public Map.Entry<String, BsonValue> getNextElement() {
/* 344 */       if (this.documentIterator.hasNext()) {
/* 345 */         return this.documentIterator.next();
/*     */       }
/* 347 */       return null;
/*     */     }
/*     */     
/*     */     protected void mark() {
/* 351 */       if (this.documentIterator != null) {
/* 352 */         this.documentIterator.mark();
/*     */       } else {
/* 354 */         this.arrayIterator.mark();
/*     */       } 
/*     */       
/* 357 */       if (getParentContext() != null) {
/* 358 */         ((Context)getParentContext()).mark();
/*     */       }
/*     */     }
/*     */     
/*     */     protected void reset() {
/* 363 */       if (this.documentIterator != null) {
/* 364 */         this.documentIterator.reset();
/*     */       } else {
/* 366 */         this.arrayIterator.reset();
/*     */       } 
/*     */       
/* 369 */       if (getParentContext() != null) {
/* 370 */         ((Context)getParentContext()).reset();
/*     */       }
/*     */     }
/*     */     
/*     */     public BsonValue getNextValue() {
/* 375 */       if (this.arrayIterator.hasNext()) {
/* 376 */         return this.arrayIterator.next();
/*     */       }
/* 378 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDocumentReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */