/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
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
/*      */ import java.util.function.IntUnaryOperator;
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
/*      */ public class Byte2ByteOpenHashMap
/*      */   extends AbstractByte2ByteMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient byte[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Byte2ByteMap.FastEntrySet entries;
/*      */   protected transient ByteSet keys;
/*      */   protected transient ByteCollection values;
/*      */   
/*      */   public Byte2ByteOpenHashMap(int expected, float f) {
/*   94 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   95 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   96 */     this.f = f;
/*   97 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*   98 */     this.mask = this.n - 1;
/*   99 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  100 */     this.key = new byte[this.n + 1];
/*  101 */     this.value = new byte[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenHashMap(int expected) {
/*  110 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenHashMap() {
/*  118 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenHashMap(Map<? extends Byte, ? extends Byte> m, float f) {
/*  128 */     this(m.size(), f);
/*  129 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenHashMap(Map<? extends Byte, ? extends Byte> m) {
/*  138 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenHashMap(Byte2ByteMap m, float f) {
/*  148 */     this(m.size(), f);
/*  149 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenHashMap(Byte2ByteMap m) {
/*  159 */     this(m, 0.75F);
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
/*      */   public Byte2ByteOpenHashMap(byte[] k, byte[] v, float f) {
/*  171 */     this(k.length, f);
/*  172 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  173 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Byte2ByteOpenHashMap(byte[] k, byte[] v) {
/*  185 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  189 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  199 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  200 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  204 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  205 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private byte removeEntry(int pos) {
/*  209 */     byte oldValue = this.value[pos];
/*  210 */     this.size--;
/*  211 */     shiftKeys(pos);
/*  212 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  213 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte removeNullEntry() {
/*  217 */     this.containsNullKey = false;
/*  218 */     byte oldValue = this.value[this.n];
/*  219 */     this.size--;
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  221 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Byte> m) {
/*  226 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  227 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  229 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  233 */     if (k == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  235 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  238 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return -(pos + 1); 
/*  239 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  242 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  243 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, byte k, byte v) {
/*  248 */     if (pos == this.n) this.containsNullKey = true; 
/*  249 */     this.key[pos] = k;
/*  250 */     this.value[pos] = v;
/*  251 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(byte k, byte v) {
/*  257 */     int pos = find(k);
/*  258 */     if (pos < 0) {
/*  259 */       insert(-pos - 1, k, v);
/*  260 */       return this.defRetValue;
/*      */     } 
/*  262 */     byte oldValue = this.value[pos];
/*  263 */     this.value[pos] = v;
/*  264 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte addToValue(int pos, byte incr) {
/*  268 */     byte oldValue = this.value[pos];
/*  269 */     this.value[pos] = (byte)(oldValue + incr);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte addTo(byte k, byte incr) {
/*      */     int pos;
/*  288 */     if (k == 0) {
/*  289 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  290 */       pos = this.n;
/*  291 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  294 */       byte[] key = this.key;
/*      */       byte curr;
/*  296 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  297 */         if (curr == k) return addToValue(pos, incr); 
/*  298 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  301 */     }  this.key[pos] = k;
/*  302 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  303 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  305 */     return this.defRetValue;
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
/*  318 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  320 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  322 */         if ((curr = key[pos]) == 0) {
/*  323 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  326 */         int slot = HashCommon.mix(curr) & this.mask;
/*  327 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  328 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  330 */       key[last] = curr;
/*  331 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte remove(byte k) {
/*  338 */     if (k == 0) {
/*  339 */       if (this.containsNullKey) return removeNullEntry(); 
/*  340 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  343 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  346 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  347 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  349 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  350 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte get(byte k) {
/*  357 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  359 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  362 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  363 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  366 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  367 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  374 */     if (k == 0) return this.containsNullKey;
/*      */     
/*  376 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  379 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  380 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  383 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  384 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  390 */     byte[] value = this.value;
/*  391 */     byte[] key = this.key;
/*  392 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  393 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  394 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(byte k, byte defaultValue) {
/*  401 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  403 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  406 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return defaultValue; 
/*  407 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  410 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  411 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(byte k, byte v) {
/*  418 */     int pos = find(k);
/*  419 */     if (pos >= 0) return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, byte v) {
/*  428 */     if (k == 0) {
/*  429 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  430 */         removeNullEntry();
/*  431 */         return true;
/*      */       } 
/*  433 */       return false;
/*      */     } 
/*      */     
/*  436 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  439 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  440 */     if (k == curr && v == this.value[pos]) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  446 */       if (k == curr && v == this.value[pos]) {
/*  447 */         removeEntry(pos);
/*  448 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, byte oldValue, byte v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  458 */     this.value[pos] = v;
/*  459 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte replace(byte k, byte v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0) return this.defRetValue; 
/*  467 */     byte oldValue = this.value[pos];
/*  468 */     this.value[pos] = v;
/*  469 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(byte k, IntUnaryOperator mappingFunction) {
/*  475 */     Objects.requireNonNull(mappingFunction);
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0) return this.value[pos]; 
/*  478 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  479 */     insert(-pos - 1, k, newValue);
/*  480 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(byte key, Byte2ByteFunction mappingFunction) {
/*  486 */     Objects.requireNonNull(mappingFunction);
/*  487 */     int pos = find(key);
/*  488 */     if (pos >= 0) return this.value[pos]; 
/*  489 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  490 */     byte newValue = mappingFunction.get(key);
/*  491 */     insert(-pos - 1, key, newValue);
/*  492 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(byte k, IntFunction<? extends Byte> mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     Byte newValue = mappingFunction.apply(k);
/*  502 */     if (newValue == null) return this.defRetValue; 
/*  503 */     byte v = newValue.byteValue();
/*  504 */     insert(-pos - 1, k, v);
/*  505 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(byte k, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  511 */     Objects.requireNonNull(remappingFunction);
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0) return this.defRetValue; 
/*  514 */     Byte newValue = remappingFunction.apply(Byte.valueOf(k), Byte.valueOf(this.value[pos]));
/*  515 */     if (newValue == null) {
/*  516 */       if (k == 0) { removeNullEntry(); }
/*  517 */       else { removeEntry(pos); }
/*  518 */        return this.defRetValue;
/*      */     } 
/*  520 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(byte k, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  526 */     Objects.requireNonNull(remappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     Byte newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  529 */     if (newValue == null) {
/*  530 */       if (pos >= 0)
/*  531 */         if (k == 0) { removeNullEntry(); }
/*  532 */         else { removeEntry(pos); }
/*      */          
/*  534 */       return this.defRetValue;
/*      */     } 
/*  536 */     byte newVal = newValue.byteValue();
/*  537 */     if (pos < 0) {
/*  538 */       insert(-pos - 1, k, newVal);
/*  539 */       return newVal;
/*      */     } 
/*  541 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(byte k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  547 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  549 */     int pos = find(k);
/*  550 */     if (pos < 0) {
/*  551 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  552 */       else { this.value[pos] = v; }
/*  553 */        return v;
/*      */     } 
/*  555 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  556 */     if (newValue == null) {
/*  557 */       if (k == 0) { removeNullEntry(); }
/*  558 */       else { removeEntry(pos); }
/*  559 */        return this.defRetValue;
/*      */     } 
/*  561 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  572 */     if (this.size == 0)
/*  573 */       return;  this.size = 0;
/*  574 */     this.containsNullKey = false;
/*  575 */     Arrays.fill(this.key, (byte)0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  580 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  585 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Byte2ByteMap.Entry, Map.Entry<Byte, Byte>, ByteBytePair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  598 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public byte getByteKey() {
/*  606 */       return Byte2ByteOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte leftByte() {
/*  611 */       return Byte2ByteOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByteValue() {
/*  616 */       return Byte2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte rightByte() {
/*  621 */       return Byte2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte v) {
/*  626 */       byte oldValue = Byte2ByteOpenHashMap.this.value[this.index];
/*  627 */       Byte2ByteOpenHashMap.this.value[this.index] = v;
/*  628 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBytePair right(byte v) {
/*  633 */       Byte2ByteOpenHashMap.this.value[this.index] = v;
/*  634 */       return this;
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
/*  645 */       return Byte.valueOf(Byte2ByteOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/*  656 */       return Byte.valueOf(Byte2ByteOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte setValue(Byte v) {
/*  667 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  673 */       if (!(o instanceof Map.Entry)) return false; 
/*  674 */       Map.Entry<Byte, Byte> e = (Map.Entry<Byte, Byte>)o;
/*  675 */       return (Byte2ByteOpenHashMap.this.key[this.index] == ((Byte)e.getKey()).byteValue() && Byte2ByteOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  680 */       return Byte2ByteOpenHashMap.this.key[this.index] ^ Byte2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  685 */       return Byte2ByteOpenHashMap.this.key[this.index] + "=>" + Byte2ByteOpenHashMap.this.value[this.index];
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
/*  696 */     int pos = Byte2ByteOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  702 */     int last = -1;
/*      */     
/*  704 */     int c = Byte2ByteOpenHashMap.this.size;
/*      */     
/*  706 */     boolean mustReturnNullKey = Byte2ByteOpenHashMap.this.containsNullKey;
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
/*  717 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  721 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  722 */       this.c--;
/*  723 */       if (this.mustReturnNullKey) {
/*  724 */         this.mustReturnNullKey = false;
/*  725 */         return this.last = Byte2ByteOpenHashMap.this.n;
/*      */       } 
/*  727 */       byte[] key = Byte2ByteOpenHashMap.this.key;
/*      */       while (true) {
/*  729 */         if (--this.pos < 0) {
/*      */           
/*  731 */           this.last = Integer.MIN_VALUE;
/*  732 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  733 */           int p = HashCommon.mix(k) & Byte2ByteOpenHashMap.this.mask;
/*  734 */           for (; k != key[p]; p = p + 1 & Byte2ByteOpenHashMap.this.mask);
/*  735 */           return p;
/*      */         } 
/*  737 */         if (key[this.pos] != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  742 */       if (this.mustReturnNullKey) {
/*  743 */         this.mustReturnNullKey = false;
/*  744 */         acceptOnIndex(action, this.last = Byte2ByteOpenHashMap.this.n);
/*  745 */         this.c--;
/*      */       } 
/*  747 */       byte[] key = Byte2ByteOpenHashMap.this.key;
/*  748 */       while (this.c != 0) {
/*  749 */         if (--this.pos < 0) {
/*      */           
/*  751 */           this.last = Integer.MIN_VALUE;
/*  752 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  753 */           int p = HashCommon.mix(k) & Byte2ByteOpenHashMap.this.mask;
/*  754 */           for (; k != key[p]; p = p + 1 & Byte2ByteOpenHashMap.this.mask);
/*  755 */           acceptOnIndex(action, p);
/*  756 */           this.c--; continue;
/*  757 */         }  if (key[this.pos] != 0) {
/*  758 */           acceptOnIndex(action, this.last = this.pos);
/*  759 */           this.c--;
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
/*  774 */       byte[] key = Byte2ByteOpenHashMap.this.key; while (true) {
/*      */         byte curr; int last;
/*  776 */         pos = (last = pos) + 1 & Byte2ByteOpenHashMap.this.mask;
/*      */         while (true) {
/*  778 */           if ((curr = key[pos]) == 0) {
/*  779 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  782 */           int slot = HashCommon.mix(curr) & Byte2ByteOpenHashMap.this.mask;
/*  783 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  784 */             break;  pos = pos + 1 & Byte2ByteOpenHashMap.this.mask;
/*      */         } 
/*  786 */         if (pos < last) {
/*  787 */           if (this.wrapped == null) this.wrapped = new ByteArrayList(2); 
/*  788 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  790 */         key[last] = curr;
/*  791 */         Byte2ByteOpenHashMap.this.value[last] = Byte2ByteOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  796 */       if (this.last == -1) throw new IllegalStateException(); 
/*  797 */       if (this.last == Byte2ByteOpenHashMap.this.n)
/*  798 */       { Byte2ByteOpenHashMap.this.containsNullKey = false; }
/*  799 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  802 */       { Byte2ByteOpenHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
/*  803 */         this.last = -1;
/*      */         return; }
/*      */       
/*  806 */       Byte2ByteOpenHashMap.this.size--;
/*  807 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  812 */       int i = n;
/*  813 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  814 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Byte2ByteMap.Entry>> implements ObjectIterator<Byte2ByteMap.Entry> { public Byte2ByteOpenHashMap.MapEntry next() {
/*  823 */       return this.entry = new Byte2ByteOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Byte2ByteOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/*  829 */       action.accept(this.entry = new Byte2ByteOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  834 */       super.remove();
/*  835 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Byte2ByteMap.Entry>> implements ObjectIterator<Byte2ByteMap.Entry> {
/*      */     private FastEntryIterator() {
/*  840 */       this.entry = new Byte2ByteOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Byte2ByteOpenHashMap.MapEntry entry;
/*      */     public Byte2ByteOpenHashMap.MapEntry next() {
/*  844 */       this.entry.index = nextEntry();
/*  845 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/*  851 */       this.entry.index = index;
/*  852 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  861 */     int pos = 0;
/*      */     
/*  863 */     int max = Byte2ByteOpenHashMap.this.n;
/*      */     
/*  865 */     int c = 0;
/*      */     
/*  867 */     boolean mustReturnNull = Byte2ByteOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  874 */       this.pos = pos;
/*  875 */       this.max = max;
/*  876 */       this.mustReturnNull = mustReturnNull;
/*  877 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  885 */       if (this.mustReturnNull) {
/*  886 */         this.mustReturnNull = false;
/*  887 */         this.c++;
/*  888 */         acceptOnIndex(action, Byte2ByteOpenHashMap.this.n);
/*  889 */         return true;
/*      */       } 
/*  891 */       byte[] key = Byte2ByteOpenHashMap.this.key;
/*  892 */       while (this.pos < this.max) {
/*  893 */         if (key[this.pos] != 0) {
/*  894 */           this.c++;
/*  895 */           acceptOnIndex(action, this.pos++);
/*  896 */           return true;
/*      */         } 
/*  898 */         this.pos++;
/*      */       } 
/*  900 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  904 */       if (this.mustReturnNull) {
/*  905 */         this.mustReturnNull = false;
/*  906 */         this.c++;
/*  907 */         acceptOnIndex(action, Byte2ByteOpenHashMap.this.n);
/*      */       } 
/*  909 */       byte[] key = Byte2ByteOpenHashMap.this.key;
/*  910 */       while (this.pos < this.max) {
/*  911 */         if (key[this.pos] != 0) {
/*  912 */           acceptOnIndex(action, this.pos);
/*  913 */           this.c++;
/*      */         } 
/*  915 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  920 */       if (!this.hasSplit)
/*      */       {
/*  922 */         return (Byte2ByteOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  927 */       return Math.min((Byte2ByteOpenHashMap.this.size - this.c), (long)(Byte2ByteOpenHashMap.this.realSize() / Byte2ByteOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  932 */       if (this.pos >= this.max - 1) return null; 
/*  933 */       int retLen = this.max - this.pos >> 1;
/*  934 */       if (retLen <= 1) return null; 
/*  935 */       int myNewPos = this.pos + retLen;
/*  936 */       int retPos = this.pos;
/*  937 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  941 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  942 */       this.pos = myNewPos;
/*  943 */       this.mustReturnNull = false;
/*  944 */       this.hasSplit = true;
/*  945 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  949 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  950 */       if (n == 0L) return 0L; 
/*  951 */       long skipped = 0L;
/*  952 */       if (this.mustReturnNull) {
/*  953 */         this.mustReturnNull = false;
/*  954 */         skipped++;
/*  955 */         n--;
/*      */       } 
/*  957 */       byte[] key = Byte2ByteOpenHashMap.this.key;
/*  958 */       while (this.pos < this.max && n > 0L) {
/*  959 */         if (key[this.pos++] != 0) {
/*  960 */           skipped++;
/*  961 */           n--;
/*      */         } 
/*      */       } 
/*  964 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Byte2ByteMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Byte2ByteMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  975 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  980 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/*  985 */       action.accept(new Byte2ByteOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  990 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Byte2ByteMap.Entry> implements Byte2ByteMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Byte2ByteMap.Entry> iterator() {
/*  997 */       return new Byte2ByteOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Byte2ByteMap.Entry> fastIterator() {
/* 1002 */       return new Byte2ByteOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Byte2ByteMap.Entry> spliterator() {
/* 1007 */       return new Byte2ByteOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1014 */       if (!(o instanceof Map.Entry)) return false; 
/* 1015 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1016 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1017 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1018 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1019 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1020 */       if (k == 0) return (Byte2ByteOpenHashMap.this.containsNullKey && Byte2ByteOpenHashMap.this.value[Byte2ByteOpenHashMap.this.n] == v);
/*      */       
/* 1022 */       byte[] key = Byte2ByteOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1025 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2ByteOpenHashMap.this.mask]) == 0) return false; 
/* 1026 */       if (k == curr) return (Byte2ByteOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1029 */         if ((curr = key[pos = pos + 1 & Byte2ByteOpenHashMap.this.mask]) == 0) return false; 
/* 1030 */         if (k == curr) return (Byte2ByteOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1041 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1042 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1043 */       if (k == 0) {
/* 1044 */         if (Byte2ByteOpenHashMap.this.containsNullKey && Byte2ByteOpenHashMap.this.value[Byte2ByteOpenHashMap.this.n] == v) {
/* 1045 */           Byte2ByteOpenHashMap.this.removeNullEntry();
/* 1046 */           return true;
/*      */         } 
/* 1048 */         return false;
/*      */       } 
/*      */       
/* 1051 */       byte[] key = Byte2ByteOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1054 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2ByteOpenHashMap.this.mask]) == 0) return false; 
/* 1055 */       if (curr == k) {
/* 1056 */         if (Byte2ByteOpenHashMap.this.value[pos] == v) {
/* 1057 */           Byte2ByteOpenHashMap.this.removeEntry(pos);
/* 1058 */           return true;
/*      */         } 
/* 1060 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1063 */         if ((curr = key[pos = pos + 1 & Byte2ByteOpenHashMap.this.mask]) == 0) return false; 
/* 1064 */         if (curr == k && 
/* 1065 */           Byte2ByteOpenHashMap.this.value[pos] == v) {
/* 1066 */           Byte2ByteOpenHashMap.this.removeEntry(pos);
/* 1067 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1075 */       return Byte2ByteOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1080 */       Byte2ByteOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2ByteMap.Entry> consumer) {
/* 1086 */       if (Byte2ByteOpenHashMap.this.containsNullKey) consumer.accept(new Byte2ByteOpenHashMap.MapEntry(Byte2ByteOpenHashMap.this.n)); 
/* 1087 */       for (int pos = Byte2ByteOpenHashMap.this.n; pos-- != 0;) { if (Byte2ByteOpenHashMap.this.key[pos] != 0) consumer.accept(new Byte2ByteOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2ByteMap.Entry> consumer) {
/* 1093 */       Byte2ByteOpenHashMap.MapEntry entry = new Byte2ByteOpenHashMap.MapEntry();
/* 1094 */       if (Byte2ByteOpenHashMap.this.containsNullKey) {
/* 1095 */         entry.index = Byte2ByteOpenHashMap.this.n;
/* 1096 */         consumer.accept(entry);
/*      */       } 
/* 1098 */       for (int pos = Byte2ByteOpenHashMap.this.n; pos-- != 0;) { if (Byte2ByteOpenHashMap.this.key[pos] != 0) {
/* 1099 */           entry.index = pos;
/* 1100 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Byte2ByteMap.FastEntrySet byte2ByteEntrySet() {
/* 1107 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1108 */     return this.entries;
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
/* 1129 */       action.accept(Byte2ByteOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1134 */       return Byte2ByteOpenHashMap.this.key[nextEntry()];
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
/* 1145 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1150 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1155 */       action.accept(Byte2ByteOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1160 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteIterator iterator() {
/* 1167 */       return new Byte2ByteOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator spliterator() {
/* 1172 */       return new Byte2ByteOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(ByteConsumer consumer) {
/* 1178 */       if (Byte2ByteOpenHashMap.this.containsNullKey) consumer.accept(Byte2ByteOpenHashMap.this.key[Byte2ByteOpenHashMap.this.n]); 
/* 1179 */       for (int pos = Byte2ByteOpenHashMap.this.n; pos-- != 0; ) {
/* 1180 */         byte k = Byte2ByteOpenHashMap.this.key[pos];
/* 1181 */         if (k != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1187 */       return Byte2ByteOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(byte k) {
/* 1192 */       return Byte2ByteOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(byte k) {
/* 1197 */       int oldSize = Byte2ByteOpenHashMap.this.size;
/* 1198 */       Byte2ByteOpenHashMap.this.remove(k);
/* 1199 */       return (Byte2ByteOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1204 */       Byte2ByteOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteSet keySet() {
/* 1210 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1211 */     return this.keys;
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
/*      */     extends MapIterator<ByteConsumer>
/*      */     implements ByteIterator
/*      */   {
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1232 */       action.accept(Byte2ByteOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1237 */       return Byte2ByteOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<ByteConsumer, ValueSpliterator> implements ByteSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1248 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1253 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1258 */       action.accept(Byte2ByteOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1263 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteCollection values() {
/* 1269 */     if (this.values == null) this.values = new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1272 */             return new Byte2ByteOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ByteSpliterator spliterator() {
/* 1277 */             return new Byte2ByteOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ByteConsumer consumer) {
/* 1283 */             if (Byte2ByteOpenHashMap.this.containsNullKey) consumer.accept(Byte2ByteOpenHashMap.this.value[Byte2ByteOpenHashMap.this.n]); 
/* 1284 */             for (int pos = Byte2ByteOpenHashMap.this.n; pos-- != 0;) { if (Byte2ByteOpenHashMap.this.key[pos] != 0) consumer.accept(Byte2ByteOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1289 */             return Byte2ByteOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(byte v) {
/* 1294 */             return Byte2ByteOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1299 */             Byte2ByteOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1302 */     return this.values;
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
/* 1319 */     return trim(this.size);
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
/* 1341 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1342 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1344 */       rehash(l);
/* 1345 */     } catch (OutOfMemoryError cantDoIt) {
/* 1346 */       return false;
/*      */     } 
/* 1348 */     return true;
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
/* 1363 */     byte[] key = this.key;
/* 1364 */     byte[] value = this.value;
/* 1365 */     int mask = newN - 1;
/* 1366 */     byte[] newKey = new byte[newN + 1];
/* 1367 */     byte[] newValue = new byte[newN + 1];
/* 1368 */     int i = this.n;
/* 1369 */     for (int j = realSize(); j-- != 0; ) {
/* 1370 */       while (key[--i] == 0); int pos;
/* 1371 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
/* 1372 */       newKey[pos] = key[i];
/* 1373 */       newValue[pos] = value[i];
/*      */     } 
/* 1375 */     newValue[newN] = value[this.n];
/* 1376 */     this.n = newN;
/* 1377 */     this.mask = mask;
/* 1378 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1379 */     this.key = newKey;
/* 1380 */     this.value = newValue;
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
/*      */   public Byte2ByteOpenHashMap clone() {
/*      */     Byte2ByteOpenHashMap c;
/*      */     try {
/* 1397 */       c = (Byte2ByteOpenHashMap)super.clone();
/* 1398 */     } catch (CloneNotSupportedException cantHappen) {
/* 1399 */       throw new InternalError();
/*      */     } 
/* 1401 */     c.keys = null;
/* 1402 */     c.values = null;
/* 1403 */     c.entries = null;
/* 1404 */     c.containsNullKey = this.containsNullKey;
/* 1405 */     c.key = (byte[])this.key.clone();
/* 1406 */     c.value = (byte[])this.value.clone();
/* 1407 */     return c;
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
/* 1421 */     int h = 0;
/* 1422 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1423 */       for (; this.key[i] == 0; i++);
/* 1424 */       t = this.key[i];
/* 1425 */       t ^= this.value[i];
/* 1426 */       h += t;
/* 1427 */       i++;
/*      */     } 
/*      */     
/* 1430 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1431 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1435 */     byte[] key = this.key;
/* 1436 */     byte[] value = this.value;
/* 1437 */     EntryIterator i = new EntryIterator();
/* 1438 */     s.defaultWriteObject();
/* 1439 */     for (int j = this.size; j-- != 0; ) {
/* 1440 */       int e = i.nextEntry();
/* 1441 */       s.writeByte(key[e]);
/* 1442 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1447 */     s.defaultReadObject();
/* 1448 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1449 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1450 */     this.mask = this.n - 1;
/* 1451 */     byte[] key = this.key = new byte[this.n + 1];
/* 1452 */     byte[] value = this.value = new byte[this.n + 1];
/*      */ 
/*      */     
/* 1455 */     for (int i = this.size; i-- != 0; ) {
/* 1456 */       int pos; byte k = s.readByte();
/* 1457 */       byte v = s.readByte();
/* 1458 */       if (k == 0) {
/* 1459 */         pos = this.n;
/* 1460 */         this.containsNullKey = true;
/*      */       } else {
/* 1462 */         pos = HashCommon.mix(k) & this.mask;
/* 1463 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1465 */       key[pos] = k;
/* 1466 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2ByteOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */