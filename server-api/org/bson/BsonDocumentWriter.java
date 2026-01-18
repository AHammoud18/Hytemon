/*     */ package org.bson;
/*     */ 
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
/*     */ 
/*     */ public class BsonDocumentWriter
/*     */   extends AbstractBsonWriter
/*     */ {
/*     */   private final BsonDocument document;
/*     */   
/*     */   public BsonDocumentWriter(BsonDocument document) {
/*  44 */     super(new BsonWriterSettings());
/*  45 */     this.document = document;
/*  46 */     setContext(new Context());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocument getDocument() {
/*  55 */     return this.document;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteStartDocument() {
/*  60 */     switch (getState()) {
/*     */       case INITIAL:
/*  62 */         setContext(new Context(this.document, BsonContextType.DOCUMENT, getContext()));
/*     */         return;
/*     */       case VALUE:
/*  65 */         setContext(new Context(new BsonDocument(), BsonContextType.DOCUMENT, getContext()));
/*     */         return;
/*     */       case SCOPE_DOCUMENT:
/*  68 */         setContext(new Context(new BsonDocument(), BsonContextType.SCOPE_DOCUMENT, getContext()));
/*     */         return;
/*     */     } 
/*  71 */     throw new BsonInvalidOperationException("Unexpected state " + getState());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doWriteEndDocument() {
/*  77 */     BsonValue value = (getContext()).container;
/*  78 */     setContext(getContext().getParentContext());
/*     */     
/*  80 */     if (getContext().getContextType() == BsonContextType.JAVASCRIPT_WITH_SCOPE) {
/*  81 */       BsonDocument scope = (BsonDocument)value;
/*  82 */       BsonString code = (BsonString)(getContext()).container;
/*  83 */       setContext(getContext().getParentContext());
/*  84 */       write(new BsonJavaScriptWithScope(code.getValue(), scope));
/*  85 */     } else if (getContext().getContextType() != BsonContextType.TOP_LEVEL) {
/*  86 */       write(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteStartArray() {
/*  92 */     setContext(new Context(new BsonArray(), BsonContextType.ARRAY, getContext()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteEndArray() {
/*  97 */     BsonValue array = (getContext()).container;
/*  98 */     setContext(getContext().getParentContext());
/*  99 */     write(array);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteBinaryData(BsonBinary value) {
/* 104 */     write(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteBoolean(boolean value) {
/* 109 */     write(BsonBoolean.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDateTime(long value) {
/* 114 */     write(new BsonDateTime(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDBPointer(BsonDbPointer value) {
/* 119 */     write(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDouble(double value) {
/* 124 */     write(new BsonDouble(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt32(int value) {
/* 129 */     write(new BsonInt32(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt64(long value) {
/* 134 */     write(new BsonInt64(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDecimal128(Decimal128 value) {
/* 139 */     write(new BsonDecimal128(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScript(String value) {
/* 144 */     write(new BsonJavaScript(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScriptWithScope(String value) {
/* 149 */     setContext(new Context(new BsonString(value), BsonContextType.JAVASCRIPT_WITH_SCOPE, getContext()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMaxKey() {
/* 154 */     write(new BsonMaxKey());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMinKey() {
/* 159 */     write(new BsonMinKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteNull() {
/* 164 */     write(BsonNull.VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteObjectId(ObjectId value) {
/* 169 */     write(new BsonObjectId(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteRegularExpression(BsonRegularExpression value) {
/* 174 */     write(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteString(String value) {
/* 179 */     write(new BsonString(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteSymbol(String value) {
/* 184 */     write(new BsonSymbol(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteTimestamp(BsonTimestamp value) {
/* 189 */     write(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteUndefined() {
/* 194 */     write(new BsonUndefined());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */   
/*     */   protected Context getContext() {
/* 203 */     return (Context)super.getContext();
/*     */   }
/*     */   
/*     */   private void write(BsonValue value) {
/* 207 */     getContext().add(value);
/*     */   }
/*     */   
/*     */   private class Context extends AbstractBsonWriter.Context {
/*     */     private BsonValue container;
/*     */     
/*     */     Context(BsonValue container, BsonContextType contextType, Context parent) {
/* 214 */       super(parent, contextType);
/* 215 */       this.container = container;
/*     */     }
/*     */     
/*     */     Context() {
/* 219 */       super(null, BsonContextType.TOP_LEVEL);
/*     */     }
/*     */     
/*     */     void add(BsonValue value) {
/* 223 */       if (this.container instanceof BsonArray) {
/* 224 */         ((BsonArray)this.container).add(value);
/*     */       } else {
/* 226 */         ((BsonDocument)this.container).put(BsonDocumentWriter.this.getName(), value);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDocumentWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */