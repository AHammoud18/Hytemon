/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
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
/*     */ 
/*     */ public class FastThreadLocal<V>
/*     */ {
/*     */   private final int index;
/*     */   
/*     */   public static void removeAll() {
/*  55 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
/*  56 */     if (threadLocalMap == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  61 */       Object v = threadLocalMap.indexedVariable(InternalThreadLocalMap.VARIABLES_TO_REMOVE_INDEX);
/*  62 */       if (v != null && v != InternalThreadLocalMap.UNSET) {
/*     */         
/*  64 */         Set<FastThreadLocal<?>> variablesToRemove = (Set<FastThreadLocal<?>>)v;
/*     */         
/*  66 */         FastThreadLocal[] arrayOfFastThreadLocal = variablesToRemove.<FastThreadLocal>toArray(new FastThreadLocal[0]);
/*  67 */         for (FastThreadLocal<?> tlv : arrayOfFastThreadLocal) {
/*  68 */           tlv.remove(threadLocalMap);
/*     */         }
/*     */       } 
/*     */     } finally {
/*  72 */       InternalThreadLocalMap.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/*  80 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
/*  81 */     if (threadLocalMap == null) {
/*  82 */       return 0;
/*     */     }
/*  84 */     return threadLocalMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void destroy() {
/*  95 */     InternalThreadLocalMap.destroy();
/*     */   }
/*     */   
/*     */   private static void addToVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
/*     */     Set<FastThreadLocal<?>> variablesToRemove;
/* 100 */     Object v = threadLocalMap.indexedVariable(InternalThreadLocalMap.VARIABLES_TO_REMOVE_INDEX);
/*     */     
/* 102 */     if (v == InternalThreadLocalMap.UNSET || v == null) {
/* 103 */       variablesToRemove = Collections.newSetFromMap(new IdentityHashMap<>());
/* 104 */       threadLocalMap.setIndexedVariable(InternalThreadLocalMap.VARIABLES_TO_REMOVE_INDEX, variablesToRemove);
/*     */     } else {
/* 106 */       variablesToRemove = (Set<FastThreadLocal<?>>)v;
/*     */     } 
/*     */     
/* 109 */     variablesToRemove.add(variable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeFromVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
/* 115 */     Object v = threadLocalMap.indexedVariable(InternalThreadLocalMap.VARIABLES_TO_REMOVE_INDEX);
/*     */     
/* 117 */     if (v == InternalThreadLocalMap.UNSET || v == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 122 */     Set<FastThreadLocal<?>> variablesToRemove = (Set<FastThreadLocal<?>>)v;
/* 123 */     variablesToRemove.remove(variable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FastThreadLocal() {
/* 129 */     this.index = InternalThreadLocalMap.nextVariableIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V get() {
/* 137 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
/* 138 */     Object v = threadLocalMap.indexedVariable(this.index);
/* 139 */     if (v != InternalThreadLocalMap.UNSET) {
/* 140 */       return (V)v;
/*     */     }
/*     */     
/* 143 */     return initialize(threadLocalMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V getIfExists() {
/* 151 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
/* 152 */     if (threadLocalMap != null) {
/* 153 */       Object v = threadLocalMap.indexedVariable(this.index);
/* 154 */       if (v != InternalThreadLocalMap.UNSET) {
/* 155 */         return (V)v;
/*     */       }
/*     */     } 
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V get(InternalThreadLocalMap threadLocalMap) {
/* 167 */     Object v = threadLocalMap.indexedVariable(this.index);
/* 168 */     if (v != InternalThreadLocalMap.UNSET) {
/* 169 */       return (V)v;
/*     */     }
/*     */     
/* 172 */     return initialize(threadLocalMap);
/*     */   }
/*     */   
/*     */   private V initialize(InternalThreadLocalMap threadLocalMap) {
/* 176 */     V v = null;
/*     */     try {
/* 178 */       v = initialValue();
/* 179 */       if (v == InternalThreadLocalMap.UNSET) {
/* 180 */         throw new IllegalArgumentException("InternalThreadLocalMap.UNSET can not be initial value.");
/*     */       }
/* 182 */     } catch (Exception e) {
/* 183 */       PlatformDependent.throwException(e);
/*     */     } 
/*     */     
/* 186 */     threadLocalMap.setIndexedVariable(this.index, v);
/* 187 */     addToVariablesToRemove(threadLocalMap, this);
/* 188 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(V value) {
/* 195 */     getAndSet(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(InternalThreadLocalMap threadLocalMap, V value) {
/* 202 */     getAndSet(threadLocalMap, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getAndSet(V value) {
/* 209 */     if (value != InternalThreadLocalMap.UNSET) {
/* 210 */       InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
/* 211 */       return setKnownNotUnset(threadLocalMap, value);
/*     */     } 
/* 213 */     return removeAndGet(InternalThreadLocalMap.getIfSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getAndSet(InternalThreadLocalMap threadLocalMap, V value) {
/* 220 */     if (value != InternalThreadLocalMap.UNSET) {
/* 221 */       return setKnownNotUnset(threadLocalMap, value);
/*     */     }
/* 223 */     return removeAndGet(threadLocalMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private V setKnownNotUnset(InternalThreadLocalMap threadLocalMap, V value) {
/* 231 */     V old = (V)threadLocalMap.getAndSetIndexedVariable(this.index, value);
/* 232 */     if (old == InternalThreadLocalMap.UNSET) {
/* 233 */       addToVariablesToRemove(threadLocalMap, this);
/* 234 */       return null;
/*     */     } 
/* 236 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isSet() {
/* 243 */     return isSet(InternalThreadLocalMap.getIfSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isSet(InternalThreadLocalMap threadLocalMap) {
/* 251 */     return (threadLocalMap != null && threadLocalMap.isIndexedVariableSet(this.index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void remove() {
/* 258 */     remove(InternalThreadLocalMap.getIfSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void remove(InternalThreadLocalMap threadLocalMap) {
/* 268 */     removeAndGet(threadLocalMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private V removeAndGet(InternalThreadLocalMap threadLocalMap) {
/* 278 */     if (threadLocalMap == null) {
/* 279 */       return null;
/*     */     }
/*     */     
/* 282 */     Object v = threadLocalMap.removeIndexedVariable(this.index);
/* 283 */     if (v != InternalThreadLocalMap.UNSET) {
/* 284 */       removeFromVariablesToRemove(threadLocalMap, this);
/*     */       try {
/* 286 */         onRemoval((V)v);
/* 287 */       } catch (Exception e) {
/* 288 */         PlatformDependent.throwException(e);
/*     */       } 
/* 290 */       return (V)v;
/*     */     } 
/* 292 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected V initialValue() throws Exception {
/* 299 */     return null;
/*     */   }
/*     */   
/*     */   protected void onRemoval(V value) throws Exception {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\FastThreadLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */