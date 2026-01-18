/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.MetadataKey;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MetadataProcessor
/*     */ {
/*  56 */   private static final MetadataProcessor EMPTY_PROCESSOR = new MetadataProcessor()
/*     */     {
/*     */       public <C> void process(MetadataHandler<C> handler, C context) {}
/*     */ 
/*     */       
/*     */       public <C> void handle(MetadataKey<?> key, MetadataHandler<C> handler, C context) {}
/*     */ 
/*     */       
/*     */       public <T> T getSingleValue(MetadataKey<T> key) {
/*  65 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public int keyCount() {
/*  70 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public Set<MetadataKey<?>> keySet() {
/*  75 */         return Collections.emptySet();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MetadataProcessor forScopeAndLogSite(Metadata scopeMetadata, Metadata logMetadata) {
/*  90 */     int totalSize = scopeMetadata.size() + logMetadata.size();
/*  91 */     if (totalSize == 0)
/*  92 */       return EMPTY_PROCESSOR; 
/*  93 */     if (totalSize <= 28) {
/*  94 */       return getLightweightProcessor(scopeMetadata, logMetadata);
/*     */     }
/*  96 */     return getSimpleProcessor(scopeMetadata, logMetadata);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static MetadataProcessor getLightweightProcessor(Metadata scope, Metadata logged) {
/* 102 */     return new LightweightProcessor(scope, logged);
/*     */   }
/*     */ 
/*     */   
/*     */   static MetadataProcessor getSimpleProcessor(Metadata scope, Metadata logged) {
/* 107 */     return new SimpleProcessor(scope, logged);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MetadataProcessor() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <C> void process(MetadataHandler<C> paramMetadataHandler, C paramC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <C> void handle(MetadataKey<?> paramMetadataKey, MetadataHandler<C> paramMetadataHandler, C paramC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> T getSingleValue(MetadataKey<T> paramMetadataKey);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int keyCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Set<MetadataKey<?>> keySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class LightweightProcessor
/*     */     extends MetadataProcessor
/*     */   {
/*     */     private static final int MAX_LIGHTWEIGHT_ELEMENTS = 28;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Metadata scope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Metadata logged;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] keyMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int keyCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private LightweightProcessor(Metadata scope, Metadata logged) {
/* 193 */       this.scope = (Metadata)Checks.checkNotNull(scope, "scope metadata");
/* 194 */       this.logged = (Metadata)Checks.checkNotNull(logged, "logged metadata");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       int maxKeyCount = scope.size() + logged.size();
/*     */       
/* 201 */       Checks.checkArgument((maxKeyCount <= 28), "metadata size too large");
/* 202 */       this.keyMap = new int[maxKeyCount];
/* 203 */       this.keyCount = prepareKeyMap(this.keyMap);
/*     */     }
/*     */ 
/*     */     
/*     */     public <C> void process(MetadataHandler<C> handler, C context) {
/* 208 */       for (int i = 0; i < this.keyCount; i++) {
/* 209 */         int n = this.keyMap[i];
/* 210 */         dispatch(getKey(n & 0x1F), n, handler, context);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <C> void handle(MetadataKey<?> key, MetadataHandler<C> handler, C context) {
/* 216 */       int index = indexOf(key, this.keyMap, this.keyCount);
/* 217 */       if (index >= 0) {
/* 218 */         dispatch(key, this.keyMap[index], handler, context);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T getSingleValue(MetadataKey<T> key) {
/* 224 */       Checks.checkArgument(!key.canRepeat(), "key must be single valued");
/* 225 */       int index = indexOf(key, this.keyMap, this.keyCount);
/*     */       
/* 227 */       return (index >= 0) ? (T)key.cast(getValue(this.keyMap[index])) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public int keyCount() {
/* 232 */       return this.keyCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<MetadataKey<?>> keySet() {
/* 239 */       return new AbstractSet<MetadataKey<?>>()
/*     */         {
/*     */           public int size() {
/* 242 */             return MetadataProcessor.LightweightProcessor.this.keyCount;
/*     */           }
/*     */ 
/*     */           
/*     */           public Iterator<MetadataKey<?>> iterator() {
/* 247 */             return new Iterator<MetadataKey<?>>() {
/* 248 */                 private int i = 0;
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 252 */                   return (this.i < MetadataProcessor.LightweightProcessor.this.keyCount);
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public MetadataKey<?> next() {
/* 257 */                   return MetadataProcessor.LightweightProcessor.this.getKey(MetadataProcessor.LightweightProcessor.this.keyMap[this.i++] & 0x1F);
/*     */                 }
/*     */               };
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     private <T, C> void dispatch(MetadataKey<T> key, int n, MetadataHandler<C> handler, C context) {
/* 266 */       if (!key.canRepeat()) {
/*     */         
/* 268 */         handler.handle(key, (T)key.cast(getValue(n)), context);
/*     */       } else {
/* 270 */         handler.handleRepeated(key, new ValueIterator<T>(key, n), context);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private final class ValueIterator<T>
/*     */       implements Iterator<T>
/*     */     {
/*     */       private final MetadataKey<T> key;
/*     */       
/*     */       private int nextIndex;
/*     */       
/*     */       private int mask;
/*     */       
/*     */       private ValueIterator(MetadataKey<T> key, int valueIndices) {
/* 285 */         this.key = key;
/*     */         
/* 287 */         this.nextIndex = valueIndices & 0x1F;
/*     */ 
/*     */ 
/*     */         
/* 291 */         this.mask = valueIndices >>> 5 + this.nextIndex;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 296 */         return (this.nextIndex >= 0);
/*     */       }
/*     */ 
/*     */       
/*     */       public T next() {
/* 301 */         T next = (T)this.key.cast(MetadataProcessor.LightweightProcessor.this.getValue(this.nextIndex));
/* 302 */         if (this.mask != 0) {
/*     */           
/* 304 */           int skip = 1 + Integer.numberOfTrailingZeros(this.mask);
/* 305 */           this.mask >>>= skip;
/* 306 */           this.nextIndex += skip;
/*     */         } else {
/*     */           
/* 309 */           this.nextIndex = -1;
/*     */         } 
/* 311 */         return next;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private int prepareKeyMap(int[] keyMap) {
/* 317 */       long bloomFilterMask = 0L;
/* 318 */       int count = 0;
/* 319 */       for (int n = 0; n < keyMap.length; n++) {
/* 320 */         MetadataKey<?> key = getKey(n);
/*     */ 
/*     */ 
/*     */         
/* 324 */         long oldMask = bloomFilterMask;
/* 325 */         bloomFilterMask |= key.getBloomFilterMask();
/* 326 */         if (bloomFilterMask == oldMask) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 335 */           int i = indexOf(key, keyMap, count);
/*     */           
/* 337 */           if (i != -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 345 */             keyMap[i] = key.canRepeat() ? (keyMap[i] | 1 << n + 4) : n;
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/* 350 */         keyMap[count++] = n; continue;
/*     */       } 
/* 352 */       return count;
/*     */     }
/*     */ 
/*     */     
/*     */     private int indexOf(MetadataKey<?> key, int[] keyMap, int count) {
/* 357 */       for (int i = 0; i < count; i++) {
/*     */         
/* 359 */         if (key.equals(getKey(keyMap[i] & 0x1F))) {
/* 360 */           return i;
/*     */         }
/*     */       } 
/* 363 */       return -1;
/*     */     }
/*     */     
/*     */     private MetadataKey<?> getKey(int n) {
/* 367 */       int scopeSize = this.scope.size();
/* 368 */       return (n >= scopeSize) ? this.logged.getKey(n - scopeSize) : this.scope.getKey(n);
/*     */     }
/*     */     
/*     */     private Object getValue(int n) {
/* 372 */       int scopeSize = this.scope.size();
/* 373 */       return (n >= scopeSize) ? this.logged.getValue(n - scopeSize) : this.scope.getValue(n);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class SimpleProcessor
/*     */     extends MetadataProcessor
/*     */   {
/*     */     private final Map<MetadataKey<?>, Object> map;
/*     */ 
/*     */ 
/*     */     
/*     */     private SimpleProcessor(Metadata scope, Metadata logged) {
/* 387 */       LinkedHashMap<MetadataKey<?>, Object> map = new LinkedHashMap<MetadataKey<?>, Object>();
/* 388 */       addTo(map, scope);
/* 389 */       addTo(map, logged);
/*     */       
/* 391 */       for (Map.Entry<MetadataKey<?>, Object> e : map.entrySet()) {
/* 392 */         if (((MetadataKey)e.getKey()).canRepeat()) {
/* 393 */           e.setValue(Collections.unmodifiableList((List)e.getValue()));
/*     */         }
/*     */       } 
/* 396 */       this.map = Collections.unmodifiableMap(map);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static void addTo(Map<MetadataKey<?>, Object> map, Metadata metadata) {
/* 402 */       for (int i = 0; i < metadata.size(); i++) {
/* 403 */         MetadataKey<?> key = metadata.getKey(i);
/* 404 */         Object value = map.get(key);
/* 405 */         if (key.canRepeat()) {
/*     */           
/* 407 */           List<Object> list = (List<Object>)value;
/* 408 */           if (list == null) {
/* 409 */             list = new ArrayList();
/* 410 */             map.put(key, list);
/*     */           } 
/*     */           
/* 413 */           list.add(key.cast(metadata.getValue(i)));
/*     */         } else {
/*     */           
/* 416 */           map.put(key, key.cast(metadata.getValue(i)));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <C> void process(MetadataHandler<C> handler, C context) {
/* 423 */       for (Map.Entry<MetadataKey<?>, Object> e : this.map.entrySet()) {
/* 424 */         dispatch(e.getKey(), e.getValue(), handler, context);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public <C> void handle(MetadataKey<?> key, MetadataHandler<C> handler, C context) {
/* 430 */       Object value = this.map.get(key);
/* 431 */       if (value != null) {
/* 432 */         dispatch(key, value, handler, context);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T getSingleValue(MetadataKey<T> key) {
/* 440 */       Checks.checkArgument(!key.canRepeat(), "key must be single valued");
/* 441 */       Object value = this.map.get(key);
/* 442 */       return (value != null) ? (T)value : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public int keyCount() {
/* 447 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<MetadataKey<?>> keySet() {
/* 452 */       return this.map.keySet();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static <T, C> void dispatch(MetadataKey<T> key, Object value, MetadataHandler<C> handler, C context) {
/* 460 */       if (key.canRepeat()) {
/* 461 */         handler.handleRepeated(key, ((List<T>)value).iterator(), context);
/*     */       } else {
/* 463 */         handler.handle(key, (T)value, context);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\MetadataProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */