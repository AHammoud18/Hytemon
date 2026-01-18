/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.concurrent.FastThreadLocalThread;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InternalThreadLocalMap
/*     */   extends UnpaddedInternalThreadLocalMap
/*     */ {
/*  42 */   private static final ThreadLocal<InternalThreadLocalMap> slowThreadLocalMap = new ThreadLocal<>();
/*     */   
/*  44 */   private static final AtomicInteger nextIndex = new AtomicInteger();
/*     */   
/*  46 */   public static final int VARIABLES_TO_REMOVE_INDEX = nextVariableIndex();
/*     */   
/*     */   private static final int DEFAULT_ARRAY_LIST_INITIAL_CAPACITY = 8;
/*     */   
/*     */   private static final int ARRAY_LIST_CAPACITY_EXPAND_THRESHOLD = 1073741824;
/*     */   
/*     */   private static final int ARRAY_LIST_CAPACITY_MAX_SIZE = 2147483639;
/*     */   
/*     */   private static final int HANDLER_SHARABLE_CACHE_INITIAL_CAPACITY = 4;
/*     */   
/*     */   private static final int INDEXED_VARIABLE_TABLE_INITIAL_SIZE = 32;
/*     */   
/*     */   private static final int STRING_BUILDER_INITIAL_SIZE;
/*     */   private static final int STRING_BUILDER_MAX_SIZE;
/*     */   private static final InternalLogger logger;
/*  61 */   public static final Object UNSET = new Object();
/*     */   
/*     */   private Object[] indexedVariables;
/*     */   
/*     */   private int futureListenerStackDepth;
/*     */   
/*     */   private int localChannelReaderStackDepth;
/*     */   
/*     */   private Map<Class<?>, Boolean> handlerSharableCache;
/*     */   
/*     */   private Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache;
/*     */   private Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache;
/*     */   private StringBuilder stringBuilder;
/*     */   private Map<Charset, CharsetEncoder> charsetEncoderCache;
/*     */   private Map<Charset, CharsetDecoder> charsetDecoderCache;
/*     */   private ArrayList<Object> arrayList;
/*     */   private BitSet cleanerFlags;
/*     */   public long rp1;
/*     */   public long rp2;
/*     */   public long rp3;
/*     */   public long rp4;
/*     */   public long rp5;
/*     */   public long rp6;
/*     */   public long rp7;
/*     */   public long rp8;
/*     */   
/*     */   static {
/*  88 */     STRING_BUILDER_INITIAL_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalMap.stringBuilder.initialSize", 1024);
/*     */     
/*  90 */     STRING_BUILDER_MAX_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalMap.stringBuilder.maxSize", 4096);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     logger = InternalLoggerFactory.getInstance(InternalThreadLocalMap.class);
/*  98 */     logger.debug("-Dio.netty.threadLocalMap.stringBuilder.initialSize: {}", Integer.valueOf(STRING_BUILDER_INITIAL_SIZE));
/*  99 */     logger.debug("-Dio.netty.threadLocalMap.stringBuilder.maxSize: {}", Integer.valueOf(STRING_BUILDER_MAX_SIZE));
/*     */   }
/*     */   
/*     */   public static InternalThreadLocalMap getIfSet() {
/* 103 */     Thread thread = Thread.currentThread();
/* 104 */     if (thread instanceof FastThreadLocalThread) {
/* 105 */       return ((FastThreadLocalThread)thread).threadLocalMap();
/*     */     }
/* 107 */     return slowThreadLocalMap.get();
/*     */   }
/*     */   
/*     */   public static InternalThreadLocalMap get() {
/* 111 */     Thread thread = Thread.currentThread();
/* 112 */     if (thread instanceof FastThreadLocalThread) {
/* 113 */       return fastGet((FastThreadLocalThread)thread);
/*     */     }
/* 115 */     return slowGet();
/*     */   }
/*     */ 
/*     */   
/*     */   private static InternalThreadLocalMap fastGet(FastThreadLocalThread thread) {
/* 120 */     InternalThreadLocalMap threadLocalMap = thread.threadLocalMap();
/* 121 */     if (threadLocalMap == null) {
/* 122 */       thread.setThreadLocalMap(threadLocalMap = new InternalThreadLocalMap());
/*     */     }
/* 124 */     return threadLocalMap;
/*     */   }
/*     */   
/*     */   private static InternalThreadLocalMap slowGet() {
/* 128 */     InternalThreadLocalMap ret = slowThreadLocalMap.get();
/* 129 */     if (ret == null) {
/* 130 */       ret = new InternalThreadLocalMap();
/* 131 */       slowThreadLocalMap.set(ret);
/*     */     } 
/* 133 */     return ret;
/*     */   }
/*     */   
/*     */   public static void remove() {
/* 137 */     Thread thread = Thread.currentThread();
/* 138 */     if (thread instanceof FastThreadLocalThread) {
/* 139 */       ((FastThreadLocalThread)thread).setThreadLocalMap(null);
/*     */     } else {
/* 141 */       slowThreadLocalMap.remove();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void destroy() {
/* 146 */     slowThreadLocalMap.remove();
/*     */   }
/*     */   
/*     */   public static int nextVariableIndex() {
/* 150 */     int index = nextIndex.getAndIncrement();
/* 151 */     if (index >= 2147483639 || index < 0) {
/* 152 */       nextIndex.set(2147483639);
/* 153 */       throw new IllegalStateException("too many thread-local indexed variables");
/*     */     } 
/* 155 */     return index;
/*     */   }
/*     */   
/*     */   public static int lastVariableIndex() {
/* 159 */     return nextIndex.get() - 1;
/*     */   }
/*     */   
/*     */   private InternalThreadLocalMap() {
/* 163 */     this.indexedVariables = newIndexedVariableTable();
/*     */   }
/*     */   
/*     */   private static Object[] newIndexedVariableTable() {
/* 167 */     Object[] array = new Object[32];
/* 168 */     Arrays.fill(array, UNSET);
/* 169 */     return array;
/*     */   }
/*     */   
/*     */   public int size() {
/* 173 */     int count = 0;
/*     */     
/* 175 */     if (this.futureListenerStackDepth != 0) {
/* 176 */       count++;
/*     */     }
/* 178 */     if (this.localChannelReaderStackDepth != 0) {
/* 179 */       count++;
/*     */     }
/* 181 */     if (this.handlerSharableCache != null) {
/* 182 */       count++;
/*     */     }
/* 184 */     if (this.typeParameterMatcherGetCache != null) {
/* 185 */       count++;
/*     */     }
/* 187 */     if (this.typeParameterMatcherFindCache != null) {
/* 188 */       count++;
/*     */     }
/* 190 */     if (this.stringBuilder != null) {
/* 191 */       count++;
/*     */     }
/* 193 */     if (this.charsetEncoderCache != null) {
/* 194 */       count++;
/*     */     }
/* 196 */     if (this.charsetDecoderCache != null) {
/* 197 */       count++;
/*     */     }
/* 199 */     if (this.arrayList != null) {
/* 200 */       count++;
/*     */     }
/*     */     
/* 203 */     Object v = indexedVariable(VARIABLES_TO_REMOVE_INDEX);
/* 204 */     if (v != null && v != UNSET) {
/*     */       
/* 206 */       Set<FastThreadLocal<?>> variablesToRemove = (Set<FastThreadLocal<?>>)v;
/* 207 */       count += variablesToRemove.size();
/*     */     } 
/*     */     
/* 210 */     return count;
/*     */   }
/*     */   
/*     */   public StringBuilder stringBuilder() {
/* 214 */     StringBuilder sb = this.stringBuilder;
/* 215 */     if (sb == null) {
/* 216 */       return this.stringBuilder = new StringBuilder(STRING_BUILDER_INITIAL_SIZE);
/*     */     }
/* 218 */     if (sb.capacity() > STRING_BUILDER_MAX_SIZE) {
/* 219 */       sb.setLength(STRING_BUILDER_INITIAL_SIZE);
/* 220 */       sb.trimToSize();
/*     */     } 
/* 222 */     sb.setLength(0);
/* 223 */     return sb;
/*     */   }
/*     */   
/*     */   public Map<Charset, CharsetEncoder> charsetEncoderCache() {
/* 227 */     Map<Charset, CharsetEncoder> cache = this.charsetEncoderCache;
/* 228 */     if (cache == null) {
/* 229 */       this.charsetEncoderCache = cache = new IdentityHashMap<>();
/*     */     }
/* 231 */     return cache;
/*     */   }
/*     */   
/*     */   public Map<Charset, CharsetDecoder> charsetDecoderCache() {
/* 235 */     Map<Charset, CharsetDecoder> cache = this.charsetDecoderCache;
/* 236 */     if (cache == null) {
/* 237 */       this.charsetDecoderCache = cache = new IdentityHashMap<>();
/*     */     }
/* 239 */     return cache;
/*     */   }
/*     */   
/*     */   public <E> ArrayList<E> arrayList() {
/* 243 */     return arrayList(8);
/*     */   }
/*     */ 
/*     */   
/*     */   public <E> ArrayList<E> arrayList(int minCapacity) {
/* 248 */     ArrayList<Object> arrayList = this.arrayList;
/* 249 */     if (arrayList == null) {
/* 250 */       this.arrayList = new ArrayList(minCapacity);
/* 251 */       return (ArrayList)this.arrayList;
/*     */     } 
/* 253 */     arrayList.clear();
/* 254 */     arrayList.ensureCapacity(minCapacity);
/* 255 */     return (ArrayList)arrayList;
/*     */   }
/*     */   
/*     */   public int futureListenerStackDepth() {
/* 259 */     return this.futureListenerStackDepth;
/*     */   }
/*     */   
/*     */   public void setFutureListenerStackDepth(int futureListenerStackDepth) {
/* 263 */     this.futureListenerStackDepth = futureListenerStackDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ThreadLocalRandom random() {
/* 271 */     return new ThreadLocalRandom();
/*     */   }
/*     */   
/*     */   public Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache() {
/* 275 */     Map<Class<?>, TypeParameterMatcher> cache = this.typeParameterMatcherGetCache;
/* 276 */     if (cache == null) {
/* 277 */       this.typeParameterMatcherGetCache = cache = new IdentityHashMap<>();
/*     */     }
/* 279 */     return cache;
/*     */   }
/*     */   
/*     */   public Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache() {
/* 283 */     Map<Class<?>, Map<String, TypeParameterMatcher>> cache = this.typeParameterMatcherFindCache;
/* 284 */     if (cache == null) {
/* 285 */       this.typeParameterMatcherFindCache = cache = new IdentityHashMap<>();
/*     */     }
/* 287 */     return cache;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public IntegerHolder counterHashCode() {
/* 292 */     return new IntegerHolder();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setCounterHashCode(IntegerHolder counterHashCode) {}
/*     */ 
/*     */   
/*     */   public Map<Class<?>, Boolean> handlerSharableCache() {
/* 301 */     Map<Class<?>, Boolean> cache = this.handlerSharableCache;
/* 302 */     if (cache == null)
/*     */     {
/* 304 */       this.handlerSharableCache = cache = new WeakHashMap<>(4);
/*     */     }
/* 306 */     return cache;
/*     */   }
/*     */   
/*     */   public int localChannelReaderStackDepth() {
/* 310 */     return this.localChannelReaderStackDepth;
/*     */   }
/*     */   
/*     */   public void setLocalChannelReaderStackDepth(int localChannelReaderStackDepth) {
/* 314 */     this.localChannelReaderStackDepth = localChannelReaderStackDepth;
/*     */   }
/*     */   
/*     */   public Object indexedVariable(int index) {
/* 318 */     Object[] lookup = this.indexedVariables;
/* 319 */     return (index < lookup.length) ? lookup[index] : UNSET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setIndexedVariable(int index, Object value) {
/* 326 */     return (getAndSetIndexedVariable(index, value) == UNSET);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAndSetIndexedVariable(int index, Object value) {
/* 333 */     Object[] lookup = this.indexedVariables;
/* 334 */     if (index < lookup.length) {
/* 335 */       Object oldValue = lookup[index];
/* 336 */       lookup[index] = value;
/* 337 */       return oldValue;
/*     */     } 
/* 339 */     expandIndexedVariableTableAndSet(index, value);
/* 340 */     return UNSET;
/*     */   }
/*     */   private void expandIndexedVariableTableAndSet(int index, Object value) {
/*     */     int newCapacity;
/* 344 */     Object[] oldArray = this.indexedVariables;
/* 345 */     int oldCapacity = oldArray.length;
/*     */     
/* 347 */     if (index < 1073741824) {
/* 348 */       newCapacity = index;
/* 349 */       newCapacity |= newCapacity >>> 1;
/* 350 */       newCapacity |= newCapacity >>> 2;
/* 351 */       newCapacity |= newCapacity >>> 4;
/* 352 */       newCapacity |= newCapacity >>> 8;
/* 353 */       newCapacity |= newCapacity >>> 16;
/* 354 */       newCapacity++;
/*     */     } else {
/* 356 */       newCapacity = 2147483639;
/*     */     } 
/*     */     
/* 359 */     Object[] newArray = Arrays.copyOf(oldArray, newCapacity);
/* 360 */     Arrays.fill(newArray, oldCapacity, newArray.length, UNSET);
/* 361 */     newArray[index] = value;
/* 362 */     this.indexedVariables = newArray;
/*     */   }
/*     */   
/*     */   public Object removeIndexedVariable(int index) {
/* 366 */     Object[] lookup = this.indexedVariables;
/* 367 */     if (index < lookup.length) {
/* 368 */       Object v = lookup[index];
/* 369 */       lookup[index] = UNSET;
/* 370 */       return v;
/*     */     } 
/* 372 */     return UNSET;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIndexedVariableSet(int index) {
/* 377 */     Object[] lookup = this.indexedVariables;
/* 378 */     return (index < lookup.length && lookup[index] != UNSET);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean isCleanerFlagSet(int index) {
/* 383 */     return (this.cleanerFlags != null && this.cleanerFlags.get(index));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setCleanerFlag(int index) {
/* 388 */     if (this.cleanerFlags == null) {
/* 389 */       this.cleanerFlags = new BitSet();
/*     */     }
/* 391 */     this.cleanerFlags.set(index);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\InternalThreadLocalMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */