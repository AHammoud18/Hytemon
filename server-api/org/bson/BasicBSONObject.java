/*     */ package org.bson;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
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
/*     */ public class BasicBSONObject
/*     */   extends LinkedHashMap<String, Object>
/*     */   implements BSONObject
/*     */ {
/*     */   private static final long serialVersionUID = -4415279469780082174L;
/*     */   
/*     */   public BasicBSONObject() {}
/*     */   
/*     */   public BasicBSONObject(int size) {
/*  52 */     super(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicBSONObject(String key, Object value) {
/*  62 */     put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicBSONObject(Map<? extends String, ?> map) {
/*  72 */     super(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map toMap() {
/*  81 */     return new LinkedHashMap<>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object removeField(String key) {
/*  91 */     return remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsField(String field) {
/* 101 */     return containsKey(field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String key) {
/* 111 */     return get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInt(String key) {
/* 121 */     Object o = get(key);
/* 122 */     if (o == null) {
/* 123 */       throw new NullPointerException("no value for: " + key);
/*     */     }
/*     */     
/* 126 */     return toInt(o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInt(String key, int def) {
/* 137 */     Object foo = get(key);
/* 138 */     if (foo == null) {
/* 139 */       return def;
/*     */     }
/*     */     
/* 142 */     return toInt(foo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(String key) {
/* 152 */     Object foo = get(key);
/* 153 */     return ((Number)foo).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(String key, long def) {
/* 164 */     Object foo = get(key);
/* 165 */     if (foo == null) {
/* 166 */       return def;
/*     */     }
/*     */     
/* 169 */     return ((Number)foo).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(String key) {
/* 179 */     Object foo = get(key);
/* 180 */     return ((Number)foo).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(String key, double def) {
/* 191 */     Object foo = get(key);
/* 192 */     if (foo == null) {
/* 193 */       return def;
/*     */     }
/*     */     
/* 196 */     return ((Number)foo).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String key) {
/* 206 */     Object foo = get(key);
/* 207 */     if (foo == null) {
/* 208 */       return null;
/*     */     }
/* 210 */     return foo.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String key, String def) {
/* 221 */     Object foo = get(key);
/* 222 */     if (foo == null) {
/* 223 */       return def;
/*     */     }
/*     */     
/* 226 */     return foo.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String key) {
/* 236 */     return getBoolean(key, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String key, boolean def) {
/* 247 */     Object foo = get(key);
/* 248 */     if (foo == null) {
/* 249 */       return def;
/*     */     }
/* 251 */     if (foo instanceof Number) {
/* 252 */       return (((Number)foo).intValue() > 0);
/*     */     }
/* 254 */     if (foo instanceof Boolean) {
/* 255 */       return ((Boolean)foo).booleanValue();
/*     */     }
/* 257 */     throw new IllegalArgumentException("can't coerce to bool:" + foo.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId getObjectId(String field) {
/* 267 */     return (ObjectId)get(field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId getObjectId(String field, ObjectId def) {
/* 278 */     Object foo = get(field);
/* 279 */     return (foo != null) ? (ObjectId)foo : def;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate(String field) {
/* 289 */     return (Date)get(field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate(String field, Date def) {
/* 300 */     Object foo = get(field);
/* 301 */     return (foo != null) ? (Date)foo : def;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map m) {
/* 307 */     for (Map.Entry entry : m.entrySet()) {
/* 308 */       put(entry.getKey().toString(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(BSONObject o) {
/* 314 */     for (String k : o.keySet()) {
/* 315 */       put(k, o.get(k));
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
/*     */   public BasicBSONObject append(String key, Object val) {
/* 327 */     put(key, val);
/*     */     
/* 329 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 340 */     if (o == this) {
/* 341 */       return true;
/*     */     }
/*     */     
/* 344 */     if (!(o instanceof BSONObject)) {
/* 345 */       return false;
/*     */     }
/*     */     
/* 348 */     BSONObject other = (BSONObject)o;
/*     */     
/* 350 */     if (!keySet().equals(other.keySet())) {
/* 351 */       return false;
/*     */     }
/*     */     
/* 354 */     return Arrays.equals(getEncoder().encode(canonicalizeBSONObject(this)), getEncoder().encode(canonicalizeBSONObject(other)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 359 */     return Arrays.hashCode(canonicalizeBSONObject(this).encode());
/*     */   }
/*     */   
/*     */   private byte[] encode() {
/* 363 */     return getEncoder().encode(this);
/*     */   }
/*     */   
/*     */   private BSONEncoder getEncoder() {
/* 367 */     return new BasicBSONEncoder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object canonicalize(Object from) {
/* 373 */     if (from instanceof BSONObject && !(from instanceof org.bson.types.BasicBSONList))
/* 374 */       return canonicalizeBSONObject((BSONObject)from); 
/* 375 */     if (from instanceof List)
/* 376 */       return canonicalizeList((List<Object>)from); 
/* 377 */     if (from instanceof Map) {
/* 378 */       return canonicalizeMap((Map<String, Object>)from);
/*     */     }
/* 380 */     return from;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<String, Object> canonicalizeMap(Map<String, Object> from) {
/* 385 */     Map<String, Object> canonicalized = new LinkedHashMap<>(from.size());
/* 386 */     TreeSet<String> keysInOrder = new TreeSet<>(from.keySet());
/* 387 */     for (String key : keysInOrder) {
/* 388 */       Object val = from.get(key);
/* 389 */       canonicalized.put(key, canonicalize(val));
/*     */     } 
/* 391 */     return canonicalized;
/*     */   }
/*     */   
/*     */   private static BasicBSONObject canonicalizeBSONObject(BSONObject from) {
/* 395 */     BasicBSONObject canonicalized = new BasicBSONObject();
/* 396 */     TreeSet<String> keysInOrder = new TreeSet<>(from.keySet());
/* 397 */     for (String key : keysInOrder) {
/* 398 */       Object val = from.get(key);
/* 399 */       canonicalized.put(key, canonicalize(val));
/*     */     } 
/* 401 */     return canonicalized;
/*     */   }
/*     */   
/*     */   private static List canonicalizeList(List<Object> list) {
/* 405 */     List<Object> canonicalized = new ArrayList(list.size());
/* 406 */     for (Object cur : list) {
/* 407 */       canonicalized.add(canonicalize(cur));
/*     */     }
/* 409 */     return canonicalized;
/*     */   }
/*     */   
/*     */   private int toInt(Object o) {
/* 413 */     if (o instanceof Number) {
/* 414 */       return ((Number)o).intValue();
/*     */     }
/*     */     
/* 417 */     if (o instanceof Boolean) {
/* 418 */       return ((Boolean)o).booleanValue() ? 1 : 0;
/*     */     }
/*     */     
/* 421 */     throw new IllegalArgumentException("can't convert: " + o.getClass().getName() + " to int");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BasicBSONObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */