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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Byte2ByteOpenCustomHashMap
/*      */   extends AbstractByte2ByteMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient byte[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ByteHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Byte2ByteMap.FastEntrySet entries;
/*      */   protected transient ByteSet keys;
/*      */   protected transient ByteCollection values;
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(int expected, float f, ByteHash.Strategy strategy) {
/*   98 */     this.strategy = strategy;
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new byte[this.n + 1];
/*  106 */     this.value = new byte[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(int expected, ByteHash.Strategy strategy) {
/*  116 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(ByteHash.Strategy strategy) {
/*  126 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(Map<? extends Byte, ? extends Byte> m, float f, ByteHash.Strategy strategy) {
/*  137 */     this(m.size(), f, strategy);
/*  138 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(Map<? extends Byte, ? extends Byte> m, ByteHash.Strategy strategy) {
/*  148 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(Byte2ByteMap m, float f, ByteHash.Strategy strategy) {
/*  159 */     this(m.size(), f, strategy);
/*  160 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(Byte2ByteMap m, ByteHash.Strategy strategy) {
/*  171 */     this(m, 0.75F, strategy);
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
/*      */   public Byte2ByteOpenCustomHashMap(byte[] k, byte[] v, float f, ByteHash.Strategy strategy) {
/*  184 */     this(k.length, f, strategy);
/*  185 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  186 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   
/*      */   public Byte2ByteOpenCustomHashMap(byte[] k, byte[] v, ByteHash.Strategy strategy) {
/*  199 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteHash.Strategy strategy() {
/*  208 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  212 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  222 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  223 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  227 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  228 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private byte removeEntry(int pos) {
/*  232 */     byte oldValue = this.value[pos];
/*  233 */     this.size--;
/*  234 */     shiftKeys(pos);
/*  235 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  236 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte removeNullEntry() {
/*  240 */     this.containsNullKey = false;
/*  241 */     byte oldValue = this.value[this.n];
/*  242 */     this.size--;
/*  243 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  244 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Byte> m) {
/*  249 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  250 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  252 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  256 */     if (this.strategy.equals(k, (byte)0)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  258 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  261 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return -(pos + 1); 
/*  262 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  265 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  266 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, byte k, byte v) {
/*  271 */     if (pos == this.n) this.containsNullKey = true; 
/*  272 */     this.key[pos] = k;
/*  273 */     this.value[pos] = v;
/*  274 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(byte k, byte v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     byte oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte addToValue(int pos, byte incr) {
/*  291 */     byte oldValue = this.value[pos];
/*  292 */     this.value[pos] = (byte)(oldValue + incr);
/*  293 */     return oldValue;
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
/*  311 */     if (this.strategy.equals(k, (byte)0)) {
/*  312 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  313 */       pos = this.n;
/*  314 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  317 */       byte[] key = this.key;
/*      */       byte curr;
/*  319 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  320 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  321 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  324 */     }  this.key[pos] = k;
/*  325 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  326 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  328 */     return this.defRetValue;
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
/*  341 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == 0) {
/*  346 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  349 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  350 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  351 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  353 */       key[last] = curr;
/*  354 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte remove(byte k) {
/*  361 */     if (this.strategy.equals(k, (byte)0)) {
/*  362 */       if (this.containsNullKey) return removeNullEntry(); 
/*  363 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  366 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  370 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  373 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte get(byte k) {
/*  380 */     if (this.strategy.equals(k, (byte)0)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  382 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  385 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  386 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  397 */     if (this.strategy.equals(k, (byte)0)) return this.containsNullKey;
/*      */     
/*  399 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  403 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  406 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  407 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  413 */     byte[] value = this.value;
/*  414 */     byte[] key = this.key;
/*  415 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  416 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  417 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(byte k, byte defaultValue) {
/*  424 */     if (this.strategy.equals(k, (byte)0)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  426 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return defaultValue; 
/*  430 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  433 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  434 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(byte k, byte v) {
/*  441 */     int pos = find(k);
/*  442 */     if (pos >= 0) return this.value[pos]; 
/*  443 */     insert(-pos - 1, k, v);
/*  444 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, byte v) {
/*  451 */     if (this.strategy.equals(k, (byte)0)) {
/*  452 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  453 */         removeNullEntry();
/*  454 */         return true;
/*      */       } 
/*  456 */       return false;
/*      */     } 
/*      */     
/*  459 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  462 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  463 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  464 */       removeEntry(pos);
/*  465 */       return true;
/*      */     } 
/*      */     while (true) {
/*  468 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  469 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  470 */         removeEntry(pos);
/*  471 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, byte oldValue, byte v) {
/*  479 */     int pos = find(k);
/*  480 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  481 */     this.value[pos] = v;
/*  482 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte replace(byte k, byte v) {
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0) return this.defRetValue; 
/*  490 */     byte oldValue = this.value[pos];
/*  491 */     this.value[pos] = v;
/*  492 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(byte k, IntUnaryOperator mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  502 */     insert(-pos - 1, k, newValue);
/*  503 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(byte key, Byte2ByteFunction mappingFunction) {
/*  509 */     Objects.requireNonNull(mappingFunction);
/*  510 */     int pos = find(key);
/*  511 */     if (pos >= 0) return this.value[pos]; 
/*  512 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  513 */     byte newValue = mappingFunction.get(key);
/*  514 */     insert(-pos - 1, key, newValue);
/*  515 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(byte k, IntFunction<? extends Byte> mappingFunction) {
/*  521 */     Objects.requireNonNull(mappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos >= 0) return this.value[pos]; 
/*  524 */     Byte newValue = mappingFunction.apply(k);
/*  525 */     if (newValue == null) return this.defRetValue; 
/*  526 */     byte v = newValue.byteValue();
/*  527 */     insert(-pos - 1, k, v);
/*  528 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(byte k, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0) return this.defRetValue; 
/*  537 */     Byte newValue = remappingFunction.apply(Byte.valueOf(k), Byte.valueOf(this.value[pos]));
/*  538 */     if (newValue == null) {
/*  539 */       if (this.strategy.equals(k, (byte)0)) { removeNullEntry(); }
/*  540 */       else { removeEntry(pos); }
/*  541 */        return this.defRetValue;
/*      */     } 
/*  543 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(byte k, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  549 */     Objects.requireNonNull(remappingFunction);
/*  550 */     int pos = find(k);
/*  551 */     Byte newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  552 */     if (newValue == null) {
/*  553 */       if (pos >= 0)
/*  554 */         if (this.strategy.equals(k, (byte)0)) { removeNullEntry(); }
/*  555 */         else { removeEntry(pos); }
/*      */          
/*  557 */       return this.defRetValue;
/*      */     } 
/*  559 */     byte newVal = newValue.byteValue();
/*  560 */     if (pos < 0) {
/*  561 */       insert(-pos - 1, k, newVal);
/*  562 */       return newVal;
/*      */     } 
/*  564 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(byte k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  570 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  572 */     int pos = find(k);
/*  573 */     if (pos < 0) {
/*  574 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  575 */       else { this.value[pos] = v; }
/*  576 */        return v;
/*      */     } 
/*  578 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  579 */     if (newValue == null) {
/*  580 */       if (this.strategy.equals(k, (byte)0)) { removeNullEntry(); }
/*  581 */       else { removeEntry(pos); }
/*  582 */        return this.defRetValue;
/*      */     } 
/*  584 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  595 */     if (this.size == 0)
/*  596 */       return;  this.size = 0;
/*  597 */     this.containsNullKey = false;
/*  598 */     Arrays.fill(this.key, (byte)0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  603 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  608 */     return (this.size == 0);
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
/*  621 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public byte getByteKey() {
/*  629 */       return Byte2ByteOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte leftByte() {
/*  634 */       return Byte2ByteOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByteValue() {
/*  639 */       return Byte2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte rightByte() {
/*  644 */       return Byte2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte v) {
/*  649 */       byte oldValue = Byte2ByteOpenCustomHashMap.this.value[this.index];
/*  650 */       Byte2ByteOpenCustomHashMap.this.value[this.index] = v;
/*  651 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBytePair right(byte v) {
/*  656 */       Byte2ByteOpenCustomHashMap.this.value[this.index] = v;
/*  657 */       return this;
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
/*  668 */       return Byte.valueOf(Byte2ByteOpenCustomHashMap.this.key[this.index]);
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
/*  679 */       return Byte.valueOf(Byte2ByteOpenCustomHashMap.this.value[this.index]);
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
/*  690 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  696 */       if (!(o instanceof Map.Entry)) return false; 
/*  697 */       Map.Entry<Byte, Byte> e = (Map.Entry<Byte, Byte>)o;
/*  698 */       return (Byte2ByteOpenCustomHashMap.this.strategy.equals(Byte2ByteOpenCustomHashMap.this.key[this.index], ((Byte)e.getKey()).byteValue()) && Byte2ByteOpenCustomHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  703 */       return Byte2ByteOpenCustomHashMap.this.strategy.hashCode(Byte2ByteOpenCustomHashMap.this.key[this.index]) ^ Byte2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  708 */       return Byte2ByteOpenCustomHashMap.this.key[this.index] + "=>" + Byte2ByteOpenCustomHashMap.this.value[this.index];
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
/*  719 */     int pos = Byte2ByteOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     int last = -1;
/*      */     
/*  727 */     int c = Byte2ByteOpenCustomHashMap.this.size;
/*      */     
/*  729 */     boolean mustReturnNullKey = Byte2ByteOpenCustomHashMap.this.containsNullKey;
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
/*  740 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  744 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  745 */       this.c--;
/*  746 */       if (this.mustReturnNullKey) {
/*  747 */         this.mustReturnNullKey = false;
/*  748 */         return this.last = Byte2ByteOpenCustomHashMap.this.n;
/*      */       } 
/*  750 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  752 */         if (--this.pos < 0) {
/*      */           
/*  754 */           this.last = Integer.MIN_VALUE;
/*  755 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  756 */           int p = HashCommon.mix(Byte2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ByteOpenCustomHashMap.this.mask;
/*  757 */           for (; !Byte2ByteOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Byte2ByteOpenCustomHashMap.this.mask);
/*  758 */           return p;
/*      */         } 
/*  760 */         if (key[this.pos] != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  765 */       if (this.mustReturnNullKey) {
/*  766 */         this.mustReturnNullKey = false;
/*  767 */         acceptOnIndex(action, this.last = Byte2ByteOpenCustomHashMap.this.n);
/*  768 */         this.c--;
/*      */       } 
/*  770 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key;
/*  771 */       while (this.c != 0) {
/*  772 */         if (--this.pos < 0) {
/*      */           
/*  774 */           this.last = Integer.MIN_VALUE;
/*  775 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  776 */           int p = HashCommon.mix(Byte2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ByteOpenCustomHashMap.this.mask;
/*  777 */           for (; !Byte2ByteOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Byte2ByteOpenCustomHashMap.this.mask);
/*  778 */           acceptOnIndex(action, p);
/*  779 */           this.c--; continue;
/*  780 */         }  if (key[this.pos] != 0) {
/*  781 */           acceptOnIndex(action, this.last = this.pos);
/*  782 */           this.c--;
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
/*  797 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key; while (true) {
/*      */         byte curr; int last;
/*  799 */         pos = (last = pos) + 1 & Byte2ByteOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  801 */           if ((curr = key[pos]) == 0) {
/*  802 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  805 */           int slot = HashCommon.mix(Byte2ByteOpenCustomHashMap.this.strategy.hashCode(curr)) & Byte2ByteOpenCustomHashMap.this.mask;
/*  806 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  807 */             break;  pos = pos + 1 & Byte2ByteOpenCustomHashMap.this.mask;
/*      */         } 
/*  809 */         if (pos < last) {
/*  810 */           if (this.wrapped == null) this.wrapped = new ByteArrayList(2); 
/*  811 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  813 */         key[last] = curr;
/*  814 */         Byte2ByteOpenCustomHashMap.this.value[last] = Byte2ByteOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  819 */       if (this.last == -1) throw new IllegalStateException(); 
/*  820 */       if (this.last == Byte2ByteOpenCustomHashMap.this.n)
/*  821 */       { Byte2ByteOpenCustomHashMap.this.containsNullKey = false; }
/*  822 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  825 */       { Byte2ByteOpenCustomHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
/*  826 */         this.last = -1;
/*      */         return; }
/*      */       
/*  829 */       Byte2ByteOpenCustomHashMap.this.size--;
/*  830 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  835 */       int i = n;
/*  836 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  837 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Byte2ByteMap.Entry>> implements ObjectIterator<Byte2ByteMap.Entry> { public Byte2ByteOpenCustomHashMap.MapEntry next() {
/*  846 */       return this.entry = new Byte2ByteOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Byte2ByteOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/*  852 */       action.accept(this.entry = new Byte2ByteOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Byte2ByteMap.Entry>> implements ObjectIterator<Byte2ByteMap.Entry> {
/*      */     private FastEntryIterator() {
/*  863 */       this.entry = new Byte2ByteOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Byte2ByteOpenCustomHashMap.MapEntry entry;
/*      */     public Byte2ByteOpenCustomHashMap.MapEntry next() {
/*  867 */       this.entry.index = nextEntry();
/*  868 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/*  874 */       this.entry.index = index;
/*  875 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  884 */     int pos = 0;
/*      */     
/*  886 */     int max = Byte2ByteOpenCustomHashMap.this.n;
/*      */     
/*  888 */     int c = 0;
/*      */     
/*  890 */     boolean mustReturnNull = Byte2ByteOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  897 */       this.pos = pos;
/*  898 */       this.max = max;
/*  899 */       this.mustReturnNull = mustReturnNull;
/*  900 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  908 */       if (this.mustReturnNull) {
/*  909 */         this.mustReturnNull = false;
/*  910 */         this.c++;
/*  911 */         acceptOnIndex(action, Byte2ByteOpenCustomHashMap.this.n);
/*  912 */         return true;
/*      */       } 
/*  914 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (key[this.pos] != 0) {
/*  917 */           this.c++;
/*  918 */           acceptOnIndex(action, this.pos++);
/*  919 */           return true;
/*      */         } 
/*  921 */         this.pos++;
/*      */       } 
/*  923 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  927 */       if (this.mustReturnNull) {
/*  928 */         this.mustReturnNull = false;
/*  929 */         this.c++;
/*  930 */         acceptOnIndex(action, Byte2ByteOpenCustomHashMap.this.n);
/*      */       } 
/*  932 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key;
/*  933 */       while (this.pos < this.max) {
/*  934 */         if (key[this.pos] != 0) {
/*  935 */           acceptOnIndex(action, this.pos);
/*  936 */           this.c++;
/*      */         } 
/*  938 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  943 */       if (!this.hasSplit)
/*      */       {
/*  945 */         return (Byte2ByteOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  950 */       return Math.min((Byte2ByteOpenCustomHashMap.this.size - this.c), (long)(Byte2ByteOpenCustomHashMap.this.realSize() / Byte2ByteOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  955 */       if (this.pos >= this.max - 1) return null; 
/*  956 */       int retLen = this.max - this.pos >> 1;
/*  957 */       if (retLen <= 1) return null; 
/*  958 */       int myNewPos = this.pos + retLen;
/*  959 */       int retPos = this.pos;
/*  960 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  964 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  965 */       this.pos = myNewPos;
/*  966 */       this.mustReturnNull = false;
/*  967 */       this.hasSplit = true;
/*  968 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  972 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  973 */       if (n == 0L) return 0L; 
/*  974 */       long skipped = 0L;
/*  975 */       if (this.mustReturnNull) {
/*  976 */         this.mustReturnNull = false;
/*  977 */         skipped++;
/*  978 */         n--;
/*      */       } 
/*  980 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key;
/*  981 */       while (this.pos < this.max && n > 0L) {
/*  982 */         if (key[this.pos++] != 0) {
/*  983 */           skipped++;
/*  984 */           n--;
/*      */         } 
/*      */       } 
/*  987 */       return skipped;
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
/*  998 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1003 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/* 1008 */       action.accept(new Byte2ByteOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1013 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Byte2ByteMap.Entry> implements Byte2ByteMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Byte2ByteMap.Entry> iterator() {
/* 1020 */       return new Byte2ByteOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Byte2ByteMap.Entry> fastIterator() {
/* 1025 */       return new Byte2ByteOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Byte2ByteMap.Entry> spliterator() {
/* 1030 */       return new Byte2ByteOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1041 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1042 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1043 */       if (Byte2ByteOpenCustomHashMap.this.strategy.equals(k, (byte)0)) return (Byte2ByteOpenCustomHashMap.this.containsNullKey && Byte2ByteOpenCustomHashMap.this.value[Byte2ByteOpenCustomHashMap.this.n] == v);
/*      */       
/* 1045 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1048 */       if ((curr = key[pos = HashCommon.mix(Byte2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1049 */       if (Byte2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) return (Byte2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1052 */         if ((curr = key[pos = pos + 1 & Byte2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1053 */         if (Byte2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) return (Byte2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1060 */       if (!(o instanceof Map.Entry)) return false; 
/* 1061 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1062 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1063 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1064 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1065 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1066 */       if (Byte2ByteOpenCustomHashMap.this.strategy.equals(k, (byte)0)) {
/* 1067 */         if (Byte2ByteOpenCustomHashMap.this.containsNullKey && Byte2ByteOpenCustomHashMap.this.value[Byte2ByteOpenCustomHashMap.this.n] == v) {
/* 1068 */           Byte2ByteOpenCustomHashMap.this.removeNullEntry();
/* 1069 */           return true;
/*      */         } 
/* 1071 */         return false;
/*      */       } 
/*      */       
/* 1074 */       byte[] key = Byte2ByteOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1077 */       if ((curr = key[pos = HashCommon.mix(Byte2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1078 */       if (Byte2ByteOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1079 */         if (Byte2ByteOpenCustomHashMap.this.value[pos] == v) {
/* 1080 */           Byte2ByteOpenCustomHashMap.this.removeEntry(pos);
/* 1081 */           return true;
/*      */         } 
/* 1083 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1086 */         if ((curr = key[pos = pos + 1 & Byte2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1087 */         if (Byte2ByteOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1088 */           Byte2ByteOpenCustomHashMap.this.value[pos] == v) {
/* 1089 */           Byte2ByteOpenCustomHashMap.this.removeEntry(pos);
/* 1090 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1098 */       return Byte2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1103 */       Byte2ByteOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2ByteMap.Entry> consumer) {
/* 1109 */       if (Byte2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(new Byte2ByteOpenCustomHashMap.MapEntry(Byte2ByteOpenCustomHashMap.this.n)); 
/* 1110 */       for (int pos = Byte2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Byte2ByteOpenCustomHashMap.this.key[pos] != 0) consumer.accept(new Byte2ByteOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2ByteMap.Entry> consumer) {
/* 1116 */       Byte2ByteOpenCustomHashMap.MapEntry entry = new Byte2ByteOpenCustomHashMap.MapEntry();
/* 1117 */       if (Byte2ByteOpenCustomHashMap.this.containsNullKey) {
/* 1118 */         entry.index = Byte2ByteOpenCustomHashMap.this.n;
/* 1119 */         consumer.accept(entry);
/*      */       } 
/* 1121 */       for (int pos = Byte2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Byte2ByteOpenCustomHashMap.this.key[pos] != 0) {
/* 1122 */           entry.index = pos;
/* 1123 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Byte2ByteMap.FastEntrySet byte2ByteEntrySet() {
/* 1130 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1131 */     return this.entries;
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
/* 1152 */       action.accept(Byte2ByteOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1157 */       return Byte2ByteOpenCustomHashMap.this.key[nextEntry()];
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
/* 1168 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1173 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1178 */       action.accept(Byte2ByteOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1183 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteIterator iterator() {
/* 1190 */       return new Byte2ByteOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator spliterator() {
/* 1195 */       return new Byte2ByteOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(ByteConsumer consumer) {
/* 1201 */       if (Byte2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(Byte2ByteOpenCustomHashMap.this.key[Byte2ByteOpenCustomHashMap.this.n]); 
/* 1202 */       for (int pos = Byte2ByteOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1203 */         byte k = Byte2ByteOpenCustomHashMap.this.key[pos];
/* 1204 */         if (k != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1210 */       return Byte2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(byte k) {
/* 1215 */       return Byte2ByteOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(byte k) {
/* 1220 */       int oldSize = Byte2ByteOpenCustomHashMap.this.size;
/* 1221 */       Byte2ByteOpenCustomHashMap.this.remove(k);
/* 1222 */       return (Byte2ByteOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1227 */       Byte2ByteOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteSet keySet() {
/* 1233 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1234 */     return this.keys;
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
/* 1255 */       action.accept(Byte2ByteOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1260 */       return Byte2ByteOpenCustomHashMap.this.value[nextEntry()];
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
/* 1271 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1276 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1281 */       action.accept(Byte2ByteOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1286 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteCollection values() {
/* 1292 */     if (this.values == null) this.values = new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1295 */             return new Byte2ByteOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ByteSpliterator spliterator() {
/* 1300 */             return new Byte2ByteOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ByteConsumer consumer) {
/* 1306 */             if (Byte2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(Byte2ByteOpenCustomHashMap.this.value[Byte2ByteOpenCustomHashMap.this.n]); 
/* 1307 */             for (int pos = Byte2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Byte2ByteOpenCustomHashMap.this.key[pos] != 0) consumer.accept(Byte2ByteOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1312 */             return Byte2ByteOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(byte v) {
/* 1317 */             return Byte2ByteOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1322 */             Byte2ByteOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1325 */     return this.values;
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
/* 1342 */     return trim(this.size);
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
/* 1364 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1365 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1367 */       rehash(l);
/* 1368 */     } catch (OutOfMemoryError cantDoIt) {
/* 1369 */       return false;
/*      */     } 
/* 1371 */     return true;
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
/* 1386 */     byte[] key = this.key;
/* 1387 */     byte[] value = this.value;
/* 1388 */     int mask = newN - 1;
/* 1389 */     byte[] newKey = new byte[newN + 1];
/* 1390 */     byte[] newValue = new byte[newN + 1];
/* 1391 */     int i = this.n;
/* 1392 */     for (int j = realSize(); j-- != 0; ) {
/* 1393 */       while (key[--i] == 0); int pos;
/* 1394 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
/* 1395 */       newKey[pos] = key[i];
/* 1396 */       newValue[pos] = value[i];
/*      */     } 
/* 1398 */     newValue[newN] = value[this.n];
/* 1399 */     this.n = newN;
/* 1400 */     this.mask = mask;
/* 1401 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1402 */     this.key = newKey;
/* 1403 */     this.value = newValue;
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
/*      */   public Byte2ByteOpenCustomHashMap clone() {
/*      */     Byte2ByteOpenCustomHashMap c;
/*      */     try {
/* 1420 */       c = (Byte2ByteOpenCustomHashMap)super.clone();
/* 1421 */     } catch (CloneNotSupportedException cantHappen) {
/* 1422 */       throw new InternalError();
/*      */     } 
/* 1424 */     c.keys = null;
/* 1425 */     c.values = null;
/* 1426 */     c.entries = null;
/* 1427 */     c.containsNullKey = this.containsNullKey;
/* 1428 */     c.key = (byte[])this.key.clone();
/* 1429 */     c.value = (byte[])this.value.clone();
/* 1430 */     c.strategy = this.strategy;
/* 1431 */     return c;
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
/* 1445 */     int h = 0;
/* 1446 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1447 */       for (; this.key[i] == 0; i++);
/* 1448 */       t = this.strategy.hashCode(this.key[i]);
/* 1449 */       t ^= this.value[i];
/* 1450 */       h += t;
/* 1451 */       i++;
/*      */     } 
/*      */     
/* 1454 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1455 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1459 */     byte[] key = this.key;
/* 1460 */     byte[] value = this.value;
/* 1461 */     EntryIterator i = new EntryIterator();
/* 1462 */     s.defaultWriteObject();
/* 1463 */     for (int j = this.size; j-- != 0; ) {
/* 1464 */       int e = i.nextEntry();
/* 1465 */       s.writeByte(key[e]);
/* 1466 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1471 */     s.defaultReadObject();
/* 1472 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1473 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1474 */     this.mask = this.n - 1;
/* 1475 */     byte[] key = this.key = new byte[this.n + 1];
/* 1476 */     byte[] value = this.value = new byte[this.n + 1];
/*      */ 
/*      */     
/* 1479 */     for (int i = this.size; i-- != 0; ) {
/* 1480 */       int pos; byte k = s.readByte();
/* 1481 */       byte v = s.readByte();
/* 1482 */       if (this.strategy.equals(k, (byte)0)) {
/* 1483 */         pos = this.n;
/* 1484 */         this.containsNullKey = true;
/*      */       } else {
/* 1486 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1487 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1489 */       key[pos] = k;
/* 1490 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2ByteOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */