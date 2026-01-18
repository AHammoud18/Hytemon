/*     */ package org.bson;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bson.types.BSONTimestamp;
/*     */ import org.bson.types.BasicBSONList;
/*     */ import org.bson.types.Binary;
/*     */ import org.bson.types.Code;
/*     */ import org.bson.types.CodeWScope;
/*     */ import org.bson.types.Decimal128;
/*     */ import org.bson.types.MaxKey;
/*     */ import org.bson.types.MinKey;
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
/*     */ public class BasicBSONCallback
/*     */   implements BSONCallback
/*     */ {
/*     */   private Object root;
/*     */   private final LinkedList<BSONObject> stack;
/*     */   private final LinkedList<String> nameStack;
/*     */   
/*     */   public BasicBSONCallback() {
/*  50 */     this.stack = new LinkedList<>();
/*  51 */     this.nameStack = new LinkedList<>();
/*  52 */     reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get() {
/*  57 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSONObject create() {
/*  66 */     return new BasicBSONObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BSONObject createList() {
/*  75 */     return (BSONObject)new BasicBSONList();
/*     */   }
/*     */ 
/*     */   
/*     */   public BSONCallback createBSONCallback() {
/*  80 */     return new BasicBSONCallback();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSONObject create(boolean array, List<String> path) {
/*  91 */     return array ? createList() : create();
/*     */   }
/*     */ 
/*     */   
/*     */   public void objectStart() {
/*  96 */     if (this.stack.size() > 0) {
/*  97 */       throw new IllegalStateException("Illegal object beginning in current context.");
/*     */     }
/*  99 */     this.root = create(false, null);
/* 100 */     this.stack.add((BSONObject)this.root);
/*     */   }
/*     */ 
/*     */   
/*     */   public void objectStart(String name) {
/* 105 */     this.nameStack.addLast(name);
/* 106 */     BSONObject o = create(false, this.nameStack);
/* 107 */     ((BSONObject)this.stack.getLast()).put(name, o);
/* 108 */     this.stack.addLast(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object objectDone() {
/* 113 */     BSONObject o = this.stack.removeLast();
/* 114 */     if (this.nameStack.size() > 0) {
/* 115 */       this.nameStack.removeLast();
/* 116 */     } else if (this.stack.size() > 0) {
/* 117 */       throw new IllegalStateException("Illegal object end in current context.");
/*     */     } 
/*     */     
/* 120 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayStart() {
/* 125 */     this.root = create(true, null);
/* 126 */     this.stack.add((BSONObject)this.root);
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayStart(String name) {
/* 131 */     this.nameStack.addLast(name);
/* 132 */     BSONObject o = create(true, this.nameStack);
/* 133 */     ((BSONObject)this.stack.getLast()).put(name, o);
/* 134 */     this.stack.addLast(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object arrayDone() {
/* 139 */     return objectDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotNull(String name) {
/* 144 */     cur().put(name, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void gotUndefined(String name) {}
/*     */ 
/*     */   
/*     */   public void gotMinKey(String name) {
/* 153 */     cur().put(name, new MinKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotMaxKey(String name) {
/* 158 */     cur().put(name, new MaxKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotBoolean(String name, boolean value) {
/* 163 */     _put(name, Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDouble(String name, double value) {
/* 168 */     _put(name, Double.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotInt(String name, int value) {
/* 173 */     _put(name, Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotLong(String name, long value) {
/* 178 */     _put(name, Long.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDecimal128(String name, Decimal128 value) {
/* 183 */     _put(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDate(String name, long millis) {
/* 188 */     _put(name, new Date(millis));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotRegex(String name, String pattern, String flags) {
/* 193 */     _put(name, Pattern.compile(pattern, BSON.regexFlags(flags)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotString(String name, String value) {
/* 198 */     _put(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotSymbol(String name, String value) {
/* 203 */     _put(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotTimestamp(String name, int time, int increment) {
/* 208 */     _put(name, new BSONTimestamp(time, increment));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotObjectId(String name, ObjectId id) {
/* 213 */     _put(name, id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDBRef(String name, String namespace, ObjectId id) {
/* 218 */     _put(name, (new BasicBSONObject("$ns", namespace)).append("$id", id));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotBinary(String name, byte type, byte[] data) {
/* 223 */     if (type == 0 || type == 2) {
/* 224 */       _put(name, data);
/*     */     } else {
/* 226 */       _put(name, new Binary(type, data));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotUUID(String name, long part1, long part2) {
/* 232 */     _put(name, new UUID(part1, part2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotCode(String name, String code) {
/* 237 */     _put(name, new Code(code));
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotCodeWScope(String name, String code, Object scope) {
/* 242 */     _put(name, new CodeWScope(code, (BSONObject)scope));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _put(String name, Object value) {
/* 252 */     cur().put(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BSONObject cur() {
/* 261 */     return this.stack.getLast();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String curName() {
/* 270 */     return this.nameStack.peekLast();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setRoot(Object root) {
/* 279 */     this.root = root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isStackEmpty() {
/* 288 */     return (this.stack.size() < 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 293 */     this.root = null;
/* 294 */     this.stack.clear();
/* 295 */     this.nameStack.clear();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BasicBSONCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */