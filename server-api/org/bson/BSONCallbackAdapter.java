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
/*     */ class BSONCallbackAdapter
/*     */   extends AbstractBsonWriter
/*     */ {
/*     */   private BSONCallback bsonCallback;
/*     */   
/*     */   protected BSONCallbackAdapter(BsonWriterSettings settings, BSONCallback bsonCallback) {
/*  35 */     super(settings);
/*  36 */     this.bsonCallback = bsonCallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doWriteStartDocument() {
/*  48 */     BsonContextType contextType = (getState() == AbstractBsonWriter.State.SCOPE_DOCUMENT) ? BsonContextType.SCOPE_DOCUMENT : BsonContextType.DOCUMENT;
/*     */     
/*  50 */     if (getContext() == null || contextType == BsonContextType.SCOPE_DOCUMENT) {
/*  51 */       this.bsonCallback.objectStart();
/*     */     } else {
/*  53 */       this.bsonCallback.objectStart(getName());
/*     */     } 
/*  55 */     setContext(new Context(getContext(), contextType));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteEndDocument() {
/*  60 */     BsonContextType contextType = getContext().getContextType();
/*     */     
/*  62 */     setContext(getContext().getParentContext());
/*  63 */     this.bsonCallback.objectDone();
/*     */     
/*  65 */     if (contextType == BsonContextType.SCOPE_DOCUMENT) {
/*  66 */       Object scope = this.bsonCallback.get();
/*  67 */       this.bsonCallback = (getContext()).callback;
/*  68 */       this.bsonCallback.gotCodeWScope((getContext()).name, (getContext()).code, scope);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteStartArray() {
/*  74 */     this.bsonCallback.arrayStart(getName());
/*  75 */     setContext(new Context(getContext(), BsonContextType.ARRAY));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteEndArray() {
/*  80 */     setContext(getContext().getParentContext());
/*  81 */     this.bsonCallback.arrayDone();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteBinaryData(BsonBinary value) {
/*  86 */     if (value.getType() == BsonBinarySubType.UUID_LEGACY.getValue()) {
/*  87 */       this.bsonCallback.gotUUID(getName(), 
/*  88 */           Bits.readLong(value.getData(), 0), 
/*  89 */           Bits.readLong(value.getData(), 8));
/*     */     } else {
/*  91 */       this.bsonCallback.gotBinary(getName(), value.getType(), value.getData());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteBoolean(boolean value) {
/*  97 */     this.bsonCallback.gotBoolean(getName(), value);
/*  98 */     setState(getNextState());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDateTime(long value) {
/* 103 */     this.bsonCallback.gotDate(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDBPointer(BsonDbPointer value) {
/* 108 */     this.bsonCallback.gotDBRef(getName(), value.getNamespace(), value.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDouble(double value) {
/* 113 */     this.bsonCallback.gotDouble(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt32(int value) {
/* 118 */     this.bsonCallback.gotInt(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteInt64(long value) {
/* 123 */     this.bsonCallback.gotLong(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteDecimal128(Decimal128 value) {
/* 128 */     this.bsonCallback.gotDecimal128(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScript(String value) {
/* 133 */     this.bsonCallback.gotCode(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteJavaScriptWithScope(String value) {
/* 138 */     (getContext()).callback = this.bsonCallback;
/* 139 */     (getContext()).code = value;
/* 140 */     (getContext()).name = getName();
/* 141 */     this.bsonCallback = this.bsonCallback.createBSONCallback();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMaxKey() {
/* 146 */     this.bsonCallback.gotMaxKey(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteMinKey() {
/* 151 */     this.bsonCallback.gotMinKey(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteNull() {
/* 156 */     this.bsonCallback.gotNull(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteObjectId(ObjectId value) {
/* 161 */     this.bsonCallback.gotObjectId(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteRegularExpression(BsonRegularExpression value) {
/* 166 */     this.bsonCallback.gotRegex(getName(), value.getPattern(), value.getOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteString(String value) {
/* 171 */     this.bsonCallback.gotString(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteSymbol(String value) {
/* 176 */     this.bsonCallback.gotSymbol(getName(), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteTimestamp(BsonTimestamp value) {
/* 181 */     this.bsonCallback.gotTimestamp(getName(), value.getTime(), value.getInc());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doWriteUndefined() {
/* 186 */     this.bsonCallback.gotUndefined(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Context getContext() {
/* 191 */     return (Context)super.getContext();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getName() {
/* 196 */     if (getContext().getContextType() == BsonContextType.ARRAY) {
/* 197 */       return Integer.toString((getContext()).index++);
/*     */     }
/* 199 */     return super.getName();
/*     */   }
/*     */   
/*     */   public class Context
/*     */     extends AbstractBsonWriter.Context {
/*     */     private int index;
/*     */     private BSONCallback callback;
/*     */     private String code;
/*     */     private String name;
/*     */     
/*     */     Context(Context parentContext, BsonContextType contextType) {
/* 210 */       super(parentContext, contextType);
/*     */     }
/*     */ 
/*     */     
/*     */     public Context getParentContext() {
/* 215 */       return (Context)super.getParentContext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BSONCallbackAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */