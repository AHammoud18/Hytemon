/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntPredicate;
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
/*      */ public class Byte2BooleanOpenHashMap
/*      */   extends AbstractByte2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Byte2BooleanMap.FastEntrySet entries;
/*      */   protected transient ByteSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Byte2BooleanOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  101 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  102 */     this.f = f;
/*  103 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  104 */     this.mask = this.n - 1;
/*  105 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  106 */     this.key = new byte[this.n + 1];
/*  107 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanOpenHashMap(int expected) {
/*  116 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanOpenHashMap() {
/*  124 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanOpenHashMap(Map<? extends Byte, ? extends Boolean> m, float f) {
/*  134 */     this(m.size(), f);
/*  135 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanOpenHashMap(Map<? extends Byte, ? extends Boolean> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanOpenHashMap(Byte2BooleanMap m, float f) {
/*  154 */     this(m.size(), f);
/*  155 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanOpenHashMap(Byte2BooleanMap m) {
/*  165 */     this(m, 0.75F);
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
/*      */   public Byte2BooleanOpenHashMap(byte[] k, boolean[] v, float f) {
/*  177 */     this(k.length, f);
/*  178 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  179 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanOpenHashMap(byte[] k, boolean[] v) {
/*  191 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  195 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  205 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  206 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  210 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  211 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  215 */     boolean oldValue = this.value[pos];
/*  216 */     this.size--;
/*  217 */     shiftKeys(pos);
/*  218 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  219 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  223 */     this.containsNullKey = false;
/*  224 */     boolean oldValue = this.value[this.n];
/*  225 */     this.size--;
/*  226 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Boolean> m) {
/*  232 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  233 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  235 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  239 */     if (k == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  241 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  244 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return -(pos + 1); 
/*  245 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  248 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  249 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, byte k, boolean v) {
/*  254 */     if (pos == this.n) this.containsNullKey = true; 
/*  255 */     this.key[pos] = k;
/*  256 */     this.value[pos] = v;
/*  257 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(byte k, boolean v) {
/*  263 */     int pos = find(k);
/*  264 */     if (pos < 0) {
/*  265 */       insert(-pos - 1, k, v);
/*  266 */       return this.defRetValue;
/*      */     } 
/*  268 */     boolean oldValue = this.value[pos];
/*  269 */     this.value[pos] = v;
/*  270 */     return oldValue;
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
/*      */   protected final void shiftKeys(int pos) {
/*  283 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  285 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  287 */         if ((curr = key[pos]) == 0) {
/*  288 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  291 */         int slot = HashCommon.mix(curr) & this.mask;
/*  292 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  293 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  295 */       key[last] = curr;
/*  296 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k) {
/*  303 */     if (k == 0) {
/*  304 */       if (this.containsNullKey) return removeNullEntry(); 
/*  305 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  308 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  311 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  312 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  314 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  315 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(byte k) {
/*  322 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  324 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  327 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  328 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  331 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  332 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  339 */     if (k == 0) return this.containsNullKey;
/*      */     
/*  341 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  344 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  345 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  348 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  349 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  355 */     boolean[] value = this.value;
/*  356 */     byte[] key = this.key;
/*  357 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  358 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  359 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(byte k, boolean defaultValue) {
/*  366 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  368 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  371 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return defaultValue; 
/*  372 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  376 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(byte k, boolean v) {
/*  383 */     int pos = find(k);
/*  384 */     if (pos >= 0) return this.value[pos]; 
/*  385 */     insert(-pos - 1, k, v);
/*  386 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, boolean v) {
/*  393 */     if (k == 0) {
/*  394 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  395 */         removeNullEntry();
/*  396 */         return true;
/*      */       } 
/*  398 */       return false;
/*      */     } 
/*      */     
/*  401 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  404 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  405 */     if (k == curr && v == this.value[pos]) {
/*  406 */       removeEntry(pos);
/*  407 */       return true;
/*      */     } 
/*      */     while (true) {
/*  410 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  411 */       if (k == curr && v == this.value[pos]) {
/*  412 */         removeEntry(pos);
/*  413 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, boolean oldValue, boolean v) {
/*  421 */     int pos = find(k);
/*  422 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  423 */     this.value[pos] = v;
/*  424 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, boolean v) {
/*  430 */     int pos = find(k);
/*  431 */     if (pos < 0) return this.defRetValue; 
/*  432 */     boolean oldValue = this.value[pos];
/*  433 */     this.value[pos] = v;
/*  434 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(byte k, IntPredicate mappingFunction) {
/*  440 */     Objects.requireNonNull(mappingFunction);
/*  441 */     int pos = find(k);
/*  442 */     if (pos >= 0) return this.value[pos]; 
/*  443 */     boolean newValue = mappingFunction.test(k);
/*  444 */     insert(-pos - 1, k, newValue);
/*  445 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(byte key, Byte2BooleanFunction mappingFunction) {
/*  451 */     Objects.requireNonNull(mappingFunction);
/*  452 */     int pos = find(key);
/*  453 */     if (pos >= 0) return this.value[pos]; 
/*  454 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  455 */     boolean newValue = mappingFunction.get(key);
/*  456 */     insert(-pos - 1, key, newValue);
/*  457 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(byte k, IntFunction<? extends Boolean> mappingFunction) {
/*  463 */     Objects.requireNonNull(mappingFunction);
/*  464 */     int pos = find(k);
/*  465 */     if (pos >= 0) return this.value[pos]; 
/*  466 */     Boolean newValue = mappingFunction.apply(k);
/*  467 */     if (newValue == null) return this.defRetValue; 
/*  468 */     boolean v = newValue.booleanValue();
/*  469 */     insert(-pos - 1, k, v);
/*  470 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(byte k, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  476 */     Objects.requireNonNull(remappingFunction);
/*  477 */     int pos = find(k);
/*  478 */     if (pos < 0) return this.defRetValue; 
/*  479 */     Boolean newValue = remappingFunction.apply(Byte.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  480 */     if (newValue == null) {
/*  481 */       if (k == 0) { removeNullEntry(); }
/*  482 */       else { removeEntry(pos); }
/*  483 */        return this.defRetValue;
/*      */     } 
/*  485 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(byte k, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  491 */     Objects.requireNonNull(remappingFunction);
/*  492 */     int pos = find(k);
/*  493 */     Boolean newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  494 */     if (newValue == null) {
/*  495 */       if (pos >= 0)
/*  496 */         if (k == 0) { removeNullEntry(); }
/*  497 */         else { removeEntry(pos); }
/*      */          
/*  499 */       return this.defRetValue;
/*      */     } 
/*  501 */     boolean newVal = newValue.booleanValue();
/*  502 */     if (pos < 0) {
/*  503 */       insert(-pos - 1, k, newVal);
/*  504 */       return newVal;
/*      */     } 
/*  506 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(byte k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  512 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  514 */     int pos = find(k);
/*  515 */     if (pos < 0) {
/*  516 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  517 */       else { this.value[pos] = v; }
/*  518 */        return v;
/*      */     } 
/*  520 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  521 */     if (newValue == null) {
/*  522 */       if (k == 0) { removeNullEntry(); }
/*  523 */       else { removeEntry(pos); }
/*  524 */        return this.defRetValue;
/*      */     } 
/*  526 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  537 */     if (this.size == 0)
/*  538 */       return;  this.size = 0;
/*  539 */     this.containsNullKey = false;
/*  540 */     Arrays.fill(this.key, (byte)0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  545 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  550 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Byte2BooleanMap.Entry, Map.Entry<Byte, Boolean>, ByteBooleanPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  563 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public byte getByteKey() {
/*  571 */       return Byte2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte leftByte() {
/*  576 */       return Byte2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  581 */       return Byte2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  586 */       return Byte2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  591 */       boolean oldValue = Byte2BooleanOpenHashMap.this.value[this.index];
/*  592 */       Byte2BooleanOpenHashMap.this.value[this.index] = v;
/*  593 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBooleanPair right(boolean v) {
/*  598 */       Byte2BooleanOpenHashMap.this.value[this.index] = v;
/*  599 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getKey() {
/*  610 */       return Byte.valueOf(Byte2BooleanOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  621 */       return Boolean.valueOf(Byte2BooleanOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  632 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  638 */       if (!(o instanceof Map.Entry)) return false; 
/*  639 */       Map.Entry<Byte, Boolean> e = (Map.Entry<Byte, Boolean>)o;
/*  640 */       return (Byte2BooleanOpenHashMap.this.key[this.index] == ((Byte)e.getKey()).byteValue() && Byte2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  645 */       return Byte2BooleanOpenHashMap.this.key[this.index] ^ (Byte2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  650 */       return Byte2BooleanOpenHashMap.this.key[this.index] + "=>" + Byte2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapIterator<ConsumerType>
/*      */   {
/*  661 */     int pos = Byte2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  667 */     int last = -1;
/*      */     
/*  669 */     int c = Byte2BooleanOpenHashMap.this.size;
/*      */     
/*  671 */     boolean mustReturnNullKey = Byte2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ByteArrayList wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  682 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  686 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  687 */       this.c--;
/*  688 */       if (this.mustReturnNullKey) {
/*  689 */         this.mustReturnNullKey = false;
/*  690 */         return this.last = Byte2BooleanOpenHashMap.this.n;
/*      */       } 
/*  692 */       byte[] key = Byte2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  694 */         if (--this.pos < 0) {
/*      */           
/*  696 */           this.last = Integer.MIN_VALUE;
/*  697 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  698 */           int p = HashCommon.mix(k) & Byte2BooleanOpenHashMap.this.mask;
/*  699 */           for (; k != key[p]; p = p + 1 & Byte2BooleanOpenHashMap.this.mask);
/*  700 */           return p;
/*      */         } 
/*  702 */         if (key[this.pos] != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  707 */       if (this.mustReturnNullKey) {
/*  708 */         this.mustReturnNullKey = false;
/*  709 */         acceptOnIndex(action, this.last = Byte2BooleanOpenHashMap.this.n);
/*  710 */         this.c--;
/*      */       } 
/*  712 */       byte[] key = Byte2BooleanOpenHashMap.this.key;
/*  713 */       while (this.c != 0) {
/*  714 */         if (--this.pos < 0) {
/*      */           
/*  716 */           this.last = Integer.MIN_VALUE;
/*  717 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  718 */           int p = HashCommon.mix(k) & Byte2BooleanOpenHashMap.this.mask;
/*  719 */           for (; k != key[p]; p = p + 1 & Byte2BooleanOpenHashMap.this.mask);
/*  720 */           acceptOnIndex(action, p);
/*  721 */           this.c--; continue;
/*  722 */         }  if (key[this.pos] != 0) {
/*  723 */           acceptOnIndex(action, this.last = this.pos);
/*  724 */           this.c--;
/*      */         } 
/*      */       } 
/*      */     }
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
/*      */     private void shiftKeys(int pos) {
/*  739 */       byte[] key = Byte2BooleanOpenHashMap.this.key; while (true) {
/*      */         byte curr; int last;
/*  741 */         pos = (last = pos) + 1 & Byte2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  743 */           if ((curr = key[pos]) == 0) {
/*  744 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  747 */           int slot = HashCommon.mix(curr) & Byte2BooleanOpenHashMap.this.mask;
/*  748 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  749 */             break;  pos = pos + 1 & Byte2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  751 */         if (pos < last) {
/*  752 */           if (this.wrapped == null) this.wrapped = new ByteArrayList(2); 
/*  753 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  755 */         key[last] = curr;
/*  756 */         Byte2BooleanOpenHashMap.this.value[last] = Byte2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  761 */       if (this.last == -1) throw new IllegalStateException(); 
/*  762 */       if (this.last == Byte2BooleanOpenHashMap.this.n)
/*  763 */       { Byte2BooleanOpenHashMap.this.containsNullKey = false; }
/*  764 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  767 */       { Byte2BooleanOpenHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
/*  768 */         this.last = -1;
/*      */         return; }
/*      */       
/*  771 */       Byte2BooleanOpenHashMap.this.size--;
/*  772 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  777 */       int i = n;
/*  778 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  779 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Byte2BooleanMap.Entry>> implements ObjectIterator<Byte2BooleanMap.Entry> { public Byte2BooleanOpenHashMap.MapEntry next() {
/*  788 */       return this.entry = new Byte2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Byte2BooleanOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2BooleanMap.Entry> action, int index) {
/*  794 */       action.accept(this.entry = new Byte2BooleanOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  799 */       super.remove();
/*  800 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Byte2BooleanMap.Entry>> implements ObjectIterator<Byte2BooleanMap.Entry> {
/*      */     private FastEntryIterator() {
/*  805 */       this.entry = new Byte2BooleanOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Byte2BooleanOpenHashMap.MapEntry entry;
/*      */     public Byte2BooleanOpenHashMap.MapEntry next() {
/*  809 */       this.entry.index = nextEntry();
/*  810 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2BooleanMap.Entry> action, int index) {
/*  816 */       this.entry.index = index;
/*  817 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  826 */     int pos = 0;
/*      */     
/*  828 */     int max = Byte2BooleanOpenHashMap.this.n;
/*      */     
/*  830 */     int c = 0;
/*      */     
/*  832 */     boolean mustReturnNull = Byte2BooleanOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  839 */       this.pos = pos;
/*  840 */       this.max = max;
/*  841 */       this.mustReturnNull = mustReturnNull;
/*  842 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  850 */       if (this.mustReturnNull) {
/*  851 */         this.mustReturnNull = false;
/*  852 */         this.c++;
/*  853 */         acceptOnIndex(action, Byte2BooleanOpenHashMap.this.n);
/*  854 */         return true;
/*      */       } 
/*  856 */       byte[] key = Byte2BooleanOpenHashMap.this.key;
/*  857 */       while (this.pos < this.max) {
/*  858 */         if (key[this.pos] != 0) {
/*  859 */           this.c++;
/*  860 */           acceptOnIndex(action, this.pos++);
/*  861 */           return true;
/*      */         } 
/*  863 */         this.pos++;
/*      */       } 
/*  865 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  869 */       if (this.mustReturnNull) {
/*  870 */         this.mustReturnNull = false;
/*  871 */         this.c++;
/*  872 */         acceptOnIndex(action, Byte2BooleanOpenHashMap.this.n);
/*      */       } 
/*  874 */       byte[] key = Byte2BooleanOpenHashMap.this.key;
/*  875 */       while (this.pos < this.max) {
/*  876 */         if (key[this.pos] != 0) {
/*  877 */           acceptOnIndex(action, this.pos);
/*  878 */           this.c++;
/*      */         } 
/*  880 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  885 */       if (!this.hasSplit)
/*      */       {
/*  887 */         return (Byte2BooleanOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  892 */       return Math.min((Byte2BooleanOpenHashMap.this.size - this.c), (long)(Byte2BooleanOpenHashMap.this.realSize() / Byte2BooleanOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  897 */       if (this.pos >= this.max - 1) return null; 
/*  898 */       int retLen = this.max - this.pos >> 1;
/*  899 */       if (retLen <= 1) return null; 
/*  900 */       int myNewPos = this.pos + retLen;
/*  901 */       int retPos = this.pos;
/*  902 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  906 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  907 */       this.pos = myNewPos;
/*  908 */       this.mustReturnNull = false;
/*  909 */       this.hasSplit = true;
/*  910 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  914 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  915 */       if (n == 0L) return 0L; 
/*  916 */       long skipped = 0L;
/*  917 */       if (this.mustReturnNull) {
/*  918 */         this.mustReturnNull = false;
/*  919 */         skipped++;
/*  920 */         n--;
/*      */       } 
/*  922 */       byte[] key = Byte2BooleanOpenHashMap.this.key;
/*  923 */       while (this.pos < this.max && n > 0L) {
/*  924 */         if (key[this.pos++] != 0) {
/*  925 */           skipped++;
/*  926 */           n--;
/*      */         } 
/*      */       } 
/*  929 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Byte2BooleanMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Byte2BooleanMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  940 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  945 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2BooleanMap.Entry> action, int index) {
/*  950 */       action.accept(new Byte2BooleanOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  955 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Byte2BooleanMap.Entry> implements Byte2BooleanMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Byte2BooleanMap.Entry> iterator() {
/*  962 */       return new Byte2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Byte2BooleanMap.Entry> fastIterator() {
/*  967 */       return new Byte2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Byte2BooleanMap.Entry> spliterator() {
/*  972 */       return new Byte2BooleanOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  979 */       if (!(o instanceof Map.Entry)) return false; 
/*  980 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  981 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/*  982 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/*  983 */       byte k = ((Byte)e.getKey()).byteValue();
/*  984 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  985 */       if (k == 0) return (Byte2BooleanOpenHashMap.this.containsNullKey && Byte2BooleanOpenHashMap.this.value[Byte2BooleanOpenHashMap.this.n] == v);
/*      */       
/*  987 */       byte[] key = Byte2BooleanOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  990 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2BooleanOpenHashMap.this.mask]) == 0) return false; 
/*  991 */       if (k == curr) return (Byte2BooleanOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/*  994 */         if ((curr = key[pos = pos + 1 & Byte2BooleanOpenHashMap.this.mask]) == 0) return false; 
/*  995 */         if (k == curr) return (Byte2BooleanOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1002 */       if (!(o instanceof Map.Entry)) return false; 
/* 1003 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1004 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1005 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1006 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1007 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1008 */       if (k == 0) {
/* 1009 */         if (Byte2BooleanOpenHashMap.this.containsNullKey && Byte2BooleanOpenHashMap.this.value[Byte2BooleanOpenHashMap.this.n] == v) {
/* 1010 */           Byte2BooleanOpenHashMap.this.removeNullEntry();
/* 1011 */           return true;
/*      */         } 
/* 1013 */         return false;
/*      */       } 
/*      */       
/* 1016 */       byte[] key = Byte2BooleanOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1019 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2BooleanOpenHashMap.this.mask]) == 0) return false; 
/* 1020 */       if (curr == k) {
/* 1021 */         if (Byte2BooleanOpenHashMap.this.value[pos] == v) {
/* 1022 */           Byte2BooleanOpenHashMap.this.removeEntry(pos);
/* 1023 */           return true;
/*      */         } 
/* 1025 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1028 */         if ((curr = key[pos = pos + 1 & Byte2BooleanOpenHashMap.this.mask]) == 0) return false; 
/* 1029 */         if (curr == k && 
/* 1030 */           Byte2BooleanOpenHashMap.this.value[pos] == v) {
/* 1031 */           Byte2BooleanOpenHashMap.this.removeEntry(pos);
/* 1032 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1040 */       return Byte2BooleanOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1045 */       Byte2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2BooleanMap.Entry> consumer) {
/* 1051 */       if (Byte2BooleanOpenHashMap.this.containsNullKey) consumer.accept(new Byte2BooleanOpenHashMap.MapEntry(Byte2BooleanOpenHashMap.this.n)); 
/* 1052 */       for (int pos = Byte2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Byte2BooleanOpenHashMap.this.key[pos] != 0) consumer.accept(new Byte2BooleanOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2BooleanMap.Entry> consumer) {
/* 1058 */       Byte2BooleanOpenHashMap.MapEntry entry = new Byte2BooleanOpenHashMap.MapEntry();
/* 1059 */       if (Byte2BooleanOpenHashMap.this.containsNullKey) {
/* 1060 */         entry.index = Byte2BooleanOpenHashMap.this.n;
/* 1061 */         consumer.accept(entry);
/*      */       } 
/* 1063 */       for (int pos = Byte2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Byte2BooleanOpenHashMap.this.key[pos] != 0) {
/* 1064 */           entry.index = pos;
/* 1065 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Byte2BooleanMap.FastEntrySet byte2BooleanEntrySet() {
/* 1072 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1073 */     return this.entries;
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
/*      */   private final class KeyIterator
/*      */     extends MapIterator<ByteConsumer>
/*      */     implements ByteIterator
/*      */   {
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1094 */       action.accept(Byte2BooleanOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1099 */       return Byte2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<ByteConsumer, KeySpliterator> implements ByteSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1110 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1115 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1120 */       action.accept(Byte2BooleanOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1125 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteIterator iterator() {
/* 1132 */       return new Byte2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator spliterator() {
/* 1137 */       return new Byte2BooleanOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(ByteConsumer consumer) {
/* 1143 */       if (Byte2BooleanOpenHashMap.this.containsNullKey) consumer.accept(Byte2BooleanOpenHashMap.this.key[Byte2BooleanOpenHashMap.this.n]); 
/* 1144 */       for (int pos = Byte2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/* 1145 */         byte k = Byte2BooleanOpenHashMap.this.key[pos];
/* 1146 */         if (k != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1152 */       return Byte2BooleanOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(byte k) {
/* 1157 */       return Byte2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(byte k) {
/* 1162 */       int oldSize = Byte2BooleanOpenHashMap.this.size;
/* 1163 */       Byte2BooleanOpenHashMap.this.remove(k);
/* 1164 */       return (Byte2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1169 */       Byte2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteSet keySet() {
/* 1175 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1176 */     return this.keys;
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
/*      */   private final class ValueIterator
/*      */     extends MapIterator<BooleanConsumer>
/*      */     implements BooleanIterator
/*      */   {
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1197 */       action.accept(Byte2BooleanOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1202 */       return Byte2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<BooleanConsumer, ValueSpliterator> implements BooleanSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1213 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1218 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1223 */       action.accept(Byte2BooleanOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1228 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1234 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1237 */             return new Byte2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1242 */             return new Byte2BooleanOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1248 */             if (Byte2BooleanOpenHashMap.this.containsNullKey) consumer.accept(Byte2BooleanOpenHashMap.this.value[Byte2BooleanOpenHashMap.this.n]); 
/* 1249 */             for (int pos = Byte2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Byte2BooleanOpenHashMap.this.key[pos] != 0) consumer.accept(Byte2BooleanOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1254 */             return Byte2BooleanOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1259 */             return Byte2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1264 */             Byte2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1267 */     return this.values;
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
/*      */   public boolean trim() {
/* 1284 */     return trim(this.size);
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
/*      */   public boolean trim(int n) {
/* 1306 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1307 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1309 */       rehash(l);
/* 1310 */     } catch (OutOfMemoryError cantDoIt) {
/* 1311 */       return false;
/*      */     } 
/* 1313 */     return true;
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
/*      */   protected void rehash(int newN) {
/* 1328 */     byte[] key = this.key;
/* 1329 */     boolean[] value = this.value;
/* 1330 */     int mask = newN - 1;
/* 1331 */     byte[] newKey = new byte[newN + 1];
/* 1332 */     boolean[] newValue = new boolean[newN + 1];
/* 1333 */     int i = this.n;
/* 1334 */     for (int j = realSize(); j-- != 0; ) {
/* 1335 */       while (key[--i] == 0); int pos;
/* 1336 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
/* 1337 */       newKey[pos] = key[i];
/* 1338 */       newValue[pos] = value[i];
/*      */     } 
/* 1340 */     newValue[newN] = value[this.n];
/* 1341 */     this.n = newN;
/* 1342 */     this.mask = mask;
/* 1343 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1344 */     this.key = newKey;
/* 1345 */     this.value = newValue;
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
/*      */   public Byte2BooleanOpenHashMap clone() {
/*      */     Byte2BooleanOpenHashMap c;
/*      */     try {
/* 1362 */       c = (Byte2BooleanOpenHashMap)super.clone();
/* 1363 */     } catch (CloneNotSupportedException cantHappen) {
/* 1364 */       throw new InternalError();
/*      */     } 
/* 1366 */     c.keys = null;
/* 1367 */     c.values = null;
/* 1368 */     c.entries = null;
/* 1369 */     c.containsNullKey = this.containsNullKey;
/* 1370 */     c.key = (byte[])this.key.clone();
/* 1371 */     c.value = (boolean[])this.value.clone();
/* 1372 */     return c;
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
/*      */   public int hashCode() {
/* 1386 */     int h = 0;
/* 1387 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1388 */       for (; this.key[i] == 0; i++);
/* 1389 */       t = this.key[i];
/* 1390 */       t ^= this.value[i] ? 1231 : 1237;
/* 1391 */       h += t;
/* 1392 */       i++;
/*      */     } 
/*      */     
/* 1395 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1396 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1400 */     byte[] key = this.key;
/* 1401 */     boolean[] value = this.value;
/* 1402 */     EntryIterator i = new EntryIterator();
/* 1403 */     s.defaultWriteObject();
/* 1404 */     for (int j = this.size; j-- != 0; ) {
/* 1405 */       int e = i.nextEntry();
/* 1406 */       s.writeByte(key[e]);
/* 1407 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1412 */     s.defaultReadObject();
/* 1413 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1414 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1415 */     this.mask = this.n - 1;
/* 1416 */     byte[] key = this.key = new byte[this.n + 1];
/* 1417 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1420 */     for (int i = this.size; i-- != 0; ) {
/* 1421 */       int pos; byte k = s.readByte();
/* 1422 */       boolean v = s.readBoolean();
/* 1423 */       if (k == 0) {
/* 1424 */         pos = this.n;
/* 1425 */         this.containsNullKey = true;
/*      */       } else {
/* 1427 */         pos = HashCommon.mix(k) & this.mask;
/* 1428 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1430 */       key[pos] = k;
/* 1431 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */