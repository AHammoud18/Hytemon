/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteConsumer;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleToIntFunction;
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
/*      */ public class Double2ByteOpenHashMap
/*      */   extends AbstractDouble2ByteMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient byte[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2ByteMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient ByteCollection values;
/*      */   
/*      */   public Double2ByteOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  101 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  102 */     this.f = f;
/*  103 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  104 */     this.mask = this.n - 1;
/*  105 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  106 */     this.key = new double[this.n + 1];
/*  107 */     this.value = new byte[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteOpenHashMap(int expected) {
/*  116 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteOpenHashMap() {
/*  124 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteOpenHashMap(Map<? extends Double, ? extends Byte> m, float f) {
/*  134 */     this(m.size(), f);
/*  135 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteOpenHashMap(Map<? extends Double, ? extends Byte> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteOpenHashMap(Double2ByteMap m, float f) {
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
/*      */   public Double2ByteOpenHashMap(Double2ByteMap m) {
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
/*      */   public Double2ByteOpenHashMap(double[] k, byte[] v, float f) {
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
/*      */   public Double2ByteOpenHashMap(double[] k, byte[] v) {
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
/*      */   private byte removeEntry(int pos) {
/*  215 */     byte oldValue = this.value[pos];
/*  216 */     this.size--;
/*  217 */     shiftKeys(pos);
/*  218 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  219 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte removeNullEntry() {
/*  223 */     this.containsNullKey = false;
/*  224 */     byte oldValue = this.value[this.n];
/*  225 */     this.size--;
/*  226 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Byte> m) {
/*  232 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  233 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  235 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  239 */     if (Double.doubleToLongBits(k) == 0L) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  241 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  244 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return -(pos + 1); 
/*  245 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  248 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  249 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, double k, byte v) {
/*  254 */     if (pos == this.n) this.containsNullKey = true; 
/*  255 */     this.key[pos] = k;
/*  256 */     this.value[pos] = v;
/*  257 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(double k, byte v) {
/*  263 */     int pos = find(k);
/*  264 */     if (pos < 0) {
/*  265 */       insert(-pos - 1, k, v);
/*  266 */       return this.defRetValue;
/*      */     } 
/*  268 */     byte oldValue = this.value[pos];
/*  269 */     this.value[pos] = v;
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte addToValue(int pos, byte incr) {
/*  274 */     byte oldValue = this.value[pos];
/*  275 */     this.value[pos] = (byte)(oldValue + incr);
/*  276 */     return oldValue;
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
/*      */   public byte addTo(double k, byte incr) {
/*      */     int pos;
/*  294 */     if (Double.doubleToLongBits(k) == 0L) {
/*  295 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  296 */       pos = this.n;
/*  297 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  300 */       double[] key = this.key;
/*      */       double curr;
/*  302 */       if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*  303 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return addToValue(pos, incr); 
/*  304 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) { if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  307 */     }  this.key[pos] = k;
/*  308 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  309 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  311 */     return this.defRetValue;
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
/*  324 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  326 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  328 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  329 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  332 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  333 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  334 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  336 */       key[last] = curr;
/*  337 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte remove(double k) {
/*  344 */     if (Double.doubleToLongBits(k) == 0L) {
/*  345 */       if (this.containsNullKey) return removeNullEntry(); 
/*  346 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  349 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  352 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  353 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  355 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  356 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte get(double k) {
/*  363 */     if (Double.doubleToLongBits(k) == 0L) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  365 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  368 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  369 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  372 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  373 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(double k) {
/*  380 */     if (Double.doubleToLongBits(k) == 0L) return this.containsNullKey;
/*      */     
/*  382 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  385 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return false; 
/*  386 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return true;
/*      */     
/*      */     while (true) {
/*  389 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  390 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  396 */     byte[] value = this.value;
/*  397 */     double[] key = this.key;
/*  398 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  399 */     for (int i = this.n; i-- != 0;) { if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v) return true;  }
/*  400 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(double k, byte defaultValue) {
/*  407 */     if (Double.doubleToLongBits(k) == 0L) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  409 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  412 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return defaultValue; 
/*  413 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  416 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  417 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(double k, byte v) {
/*  424 */     int pos = find(k);
/*  425 */     if (pos >= 0) return this.value[pos]; 
/*  426 */     insert(-pos - 1, k, v);
/*  427 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, byte v) {
/*  434 */     if (Double.doubleToLongBits(k) == 0L) {
/*  435 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  436 */         removeNullEntry();
/*  437 */         return true;
/*      */       } 
/*  439 */       return false;
/*      */     } 
/*      */     
/*  442 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  445 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return false; 
/*  446 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  447 */       removeEntry(pos);
/*  448 */       return true;
/*      */     } 
/*      */     while (true) {
/*  451 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  452 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  453 */         removeEntry(pos);
/*  454 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(double k, byte oldValue, byte v) {
/*  462 */     int pos = find(k);
/*  463 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  464 */     this.value[pos] = v;
/*  465 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte replace(double k, byte v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos < 0) return this.defRetValue; 
/*  473 */     byte oldValue = this.value[pos];
/*  474 */     this.value[pos] = v;
/*  475 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  481 */     Objects.requireNonNull(mappingFunction);
/*  482 */     int pos = find(k);
/*  483 */     if (pos >= 0) return this.value[pos]; 
/*  484 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  485 */     insert(-pos - 1, k, newValue);
/*  486 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(double key, Double2ByteFunction mappingFunction) {
/*  492 */     Objects.requireNonNull(mappingFunction);
/*  493 */     int pos = find(key);
/*  494 */     if (pos >= 0) return this.value[pos]; 
/*  495 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  496 */     byte newValue = mappingFunction.get(key);
/*  497 */     insert(-pos - 1, key, newValue);
/*  498 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(double k, DoubleFunction<? extends Byte> mappingFunction) {
/*  504 */     Objects.requireNonNull(mappingFunction);
/*  505 */     int pos = find(k);
/*  506 */     if (pos >= 0) return this.value[pos]; 
/*  507 */     Byte newValue = mappingFunction.apply(k);
/*  508 */     if (newValue == null) return this.defRetValue; 
/*  509 */     byte v = newValue.byteValue();
/*  510 */     insert(-pos - 1, k, v);
/*  511 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(double k, BiFunction<? super Double, ? super Byte, ? extends Byte> remappingFunction) {
/*  517 */     Objects.requireNonNull(remappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     if (pos < 0) return this.defRetValue; 
/*  520 */     Byte newValue = remappingFunction.apply(Double.valueOf(k), Byte.valueOf(this.value[pos]));
/*  521 */     if (newValue == null) {
/*  522 */       if (Double.doubleToLongBits(k) == 0L) { removeNullEntry(); }
/*  523 */       else { removeEntry(pos); }
/*  524 */        return this.defRetValue;
/*      */     } 
/*  526 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(double k, BiFunction<? super Double, ? super Byte, ? extends Byte> remappingFunction) {
/*  532 */     Objects.requireNonNull(remappingFunction);
/*  533 */     int pos = find(k);
/*  534 */     Byte newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  535 */     if (newValue == null) {
/*  536 */       if (pos >= 0)
/*  537 */         if (Double.doubleToLongBits(k) == 0L) { removeNullEntry(); }
/*  538 */         else { removeEntry(pos); }
/*      */          
/*  540 */       return this.defRetValue;
/*      */     } 
/*  542 */     byte newVal = newValue.byteValue();
/*  543 */     if (pos < 0) {
/*  544 */       insert(-pos - 1, k, newVal);
/*  545 */       return newVal;
/*      */     } 
/*  547 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(double k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  553 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  555 */     int pos = find(k);
/*  556 */     if (pos < 0) {
/*  557 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  558 */       else { this.value[pos] = v; }
/*  559 */        return v;
/*      */     } 
/*  561 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  562 */     if (newValue == null) {
/*  563 */       if (Double.doubleToLongBits(k) == 0L) { removeNullEntry(); }
/*  564 */       else { removeEntry(pos); }
/*  565 */        return this.defRetValue;
/*      */     } 
/*  567 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  578 */     if (this.size == 0)
/*  579 */       return;  this.size = 0;
/*  580 */     this.containsNullKey = false;
/*  581 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  586 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  591 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2ByteMap.Entry, Map.Entry<Double, Byte>, DoubleBytePair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  604 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public double getDoubleKey() {
/*  612 */       return Double2ByteOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double leftDouble() {
/*  617 */       return Double2ByteOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByteValue() {
/*  622 */       return Double2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte rightByte() {
/*  627 */       return Double2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte v) {
/*  632 */       byte oldValue = Double2ByteOpenHashMap.this.value[this.index];
/*  633 */       Double2ByteOpenHashMap.this.value[this.index] = v;
/*  634 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBytePair right(byte v) {
/*  639 */       Double2ByteOpenHashMap.this.value[this.index] = v;
/*  640 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  651 */       return Double.valueOf(Double2ByteOpenHashMap.this.key[this.index]);
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
/*  662 */       return Byte.valueOf(Double2ByteOpenHashMap.this.value[this.index]);
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
/*  673 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  679 */       if (!(o instanceof Map.Entry)) return false; 
/*  680 */       Map.Entry<Double, Byte> e = (Map.Entry<Double, Byte>)o;
/*  681 */       return (Double.doubleToLongBits(Double2ByteOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2ByteOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  686 */       return HashCommon.double2int(Double2ByteOpenHashMap.this.key[this.index]) ^ Double2ByteOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  691 */       return Double2ByteOpenHashMap.this.key[this.index] + "=>" + Double2ByteOpenHashMap.this.value[this.index];
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
/*  702 */     int pos = Double2ByteOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  708 */     int last = -1;
/*      */     
/*  710 */     int c = Double2ByteOpenHashMap.this.size;
/*      */     
/*  712 */     boolean mustReturnNullKey = Double2ByteOpenHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  723 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  727 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  728 */       this.c--;
/*  729 */       if (this.mustReturnNullKey) {
/*  730 */         this.mustReturnNullKey = false;
/*  731 */         return this.last = Double2ByteOpenHashMap.this.n;
/*      */       } 
/*  733 */       double[] key = Double2ByteOpenHashMap.this.key;
/*      */       while (true) {
/*  735 */         if (--this.pos < 0) {
/*      */           
/*  737 */           this.last = Integer.MIN_VALUE;
/*  738 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  739 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ByteOpenHashMap.this.mask;
/*  740 */           for (; Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]); p = p + 1 & Double2ByteOpenHashMap.this.mask);
/*  741 */           return p;
/*      */         } 
/*  743 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  748 */       if (this.mustReturnNullKey) {
/*  749 */         this.mustReturnNullKey = false;
/*  750 */         acceptOnIndex(action, this.last = Double2ByteOpenHashMap.this.n);
/*  751 */         this.c--;
/*      */       } 
/*  753 */       double[] key = Double2ByteOpenHashMap.this.key;
/*  754 */       while (this.c != 0) {
/*  755 */         if (--this.pos < 0) {
/*      */           
/*  757 */           this.last = Integer.MIN_VALUE;
/*  758 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  759 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ByteOpenHashMap.this.mask;
/*  760 */           for (; Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]); p = p + 1 & Double2ByteOpenHashMap.this.mask);
/*  761 */           acceptOnIndex(action, p);
/*  762 */           this.c--; continue;
/*  763 */         }  if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  764 */           acceptOnIndex(action, this.last = this.pos);
/*  765 */           this.c--;
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
/*  780 */       double[] key = Double2ByteOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  782 */         pos = (last = pos) + 1 & Double2ByteOpenHashMap.this.mask;
/*      */         while (true) {
/*  784 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  785 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  788 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ByteOpenHashMap.this.mask;
/*  789 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  790 */             break;  pos = pos + 1 & Double2ByteOpenHashMap.this.mask;
/*      */         } 
/*  792 */         if (pos < last) {
/*  793 */           if (this.wrapped == null) this.wrapped = new DoubleArrayList(2); 
/*  794 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  796 */         key[last] = curr;
/*  797 */         Double2ByteOpenHashMap.this.value[last] = Double2ByteOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  802 */       if (this.last == -1) throw new IllegalStateException(); 
/*  803 */       if (this.last == Double2ByteOpenHashMap.this.n)
/*  804 */       { Double2ByteOpenHashMap.this.containsNullKey = false; }
/*  805 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  808 */       { Double2ByteOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  809 */         this.last = -1;
/*      */         return; }
/*      */       
/*  812 */       Double2ByteOpenHashMap.this.size--;
/*  813 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  818 */       int i = n;
/*  819 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  820 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Double2ByteMap.Entry>> implements ObjectIterator<Double2ByteMap.Entry> { public Double2ByteOpenHashMap.MapEntry next() {
/*  829 */       return this.entry = new Double2ByteOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Double2ByteOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2ByteMap.Entry> action, int index) {
/*  835 */       action.accept(this.entry = new Double2ByteOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  840 */       super.remove();
/*  841 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Double2ByteMap.Entry>> implements ObjectIterator<Double2ByteMap.Entry> {
/*      */     private FastEntryIterator() {
/*  846 */       this.entry = new Double2ByteOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Double2ByteOpenHashMap.MapEntry entry;
/*      */     public Double2ByteOpenHashMap.MapEntry next() {
/*  850 */       this.entry.index = nextEntry();
/*  851 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2ByteMap.Entry> action, int index) {
/*  857 */       this.entry.index = index;
/*  858 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  867 */     int pos = 0;
/*      */     
/*  869 */     int max = Double2ByteOpenHashMap.this.n;
/*      */     
/*  871 */     int c = 0;
/*      */     
/*  873 */     boolean mustReturnNull = Double2ByteOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  880 */       this.pos = pos;
/*  881 */       this.max = max;
/*  882 */       this.mustReturnNull = mustReturnNull;
/*  883 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  891 */       if (this.mustReturnNull) {
/*  892 */         this.mustReturnNull = false;
/*  893 */         this.c++;
/*  894 */         acceptOnIndex(action, Double2ByteOpenHashMap.this.n);
/*  895 */         return true;
/*      */       } 
/*  897 */       double[] key = Double2ByteOpenHashMap.this.key;
/*  898 */       while (this.pos < this.max) {
/*  899 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  900 */           this.c++;
/*  901 */           acceptOnIndex(action, this.pos++);
/*  902 */           return true;
/*      */         } 
/*  904 */         this.pos++;
/*      */       } 
/*  906 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  910 */       if (this.mustReturnNull) {
/*  911 */         this.mustReturnNull = false;
/*  912 */         this.c++;
/*  913 */         acceptOnIndex(action, Double2ByteOpenHashMap.this.n);
/*      */       } 
/*  915 */       double[] key = Double2ByteOpenHashMap.this.key;
/*  916 */       while (this.pos < this.max) {
/*  917 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  918 */           acceptOnIndex(action, this.pos);
/*  919 */           this.c++;
/*      */         } 
/*  921 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  926 */       if (!this.hasSplit)
/*      */       {
/*  928 */         return (Double2ByteOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  933 */       return Math.min((Double2ByteOpenHashMap.this.size - this.c), (long)(Double2ByteOpenHashMap.this.realSize() / Double2ByteOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  938 */       if (this.pos >= this.max - 1) return null; 
/*  939 */       int retLen = this.max - this.pos >> 1;
/*  940 */       if (retLen <= 1) return null; 
/*  941 */       int myNewPos = this.pos + retLen;
/*  942 */       int retPos = this.pos;
/*  943 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  947 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  948 */       this.pos = myNewPos;
/*  949 */       this.mustReturnNull = false;
/*  950 */       this.hasSplit = true;
/*  951 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  955 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  956 */       if (n == 0L) return 0L; 
/*  957 */       long skipped = 0L;
/*  958 */       if (this.mustReturnNull) {
/*  959 */         this.mustReturnNull = false;
/*  960 */         skipped++;
/*  961 */         n--;
/*      */       } 
/*  963 */       double[] key = Double2ByteOpenHashMap.this.key;
/*  964 */       while (this.pos < this.max && n > 0L) {
/*  965 */         if (Double.doubleToLongBits(key[this.pos++]) != 0L) {
/*  966 */           skipped++;
/*  967 */           n--;
/*      */         } 
/*      */       } 
/*  970 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Double2ByteMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Double2ByteMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  981 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  986 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2ByteMap.Entry> action, int index) {
/*  991 */       action.accept(new Double2ByteOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  996 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2ByteMap.Entry> implements Double2ByteMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2ByteMap.Entry> iterator() {
/* 1003 */       return new Double2ByteOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Double2ByteMap.Entry> fastIterator() {
/* 1008 */       return new Double2ByteOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Double2ByteMap.Entry> spliterator() {
/* 1013 */       return new Double2ByteOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1020 */       if (!(o instanceof Map.Entry)) return false; 
/* 1021 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1022 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1023 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1024 */       double k = ((Double)e.getKey()).doubleValue();
/* 1025 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1026 */       if (Double.doubleToLongBits(k) == 0L) return (Double2ByteOpenHashMap.this.containsNullKey && Double2ByteOpenHashMap.this.value[Double2ByteOpenHashMap.this.n] == v);
/*      */       
/* 1028 */       double[] key = Double2ByteOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1031 */       if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ByteOpenHashMap.this.mask]) == 0L) return false; 
/* 1032 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return (Double2ByteOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1035 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ByteOpenHashMap.this.mask]) == 0L) return false; 
/* 1036 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return (Double2ByteOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1043 */       if (!(o instanceof Map.Entry)) return false; 
/* 1044 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1045 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1046 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1047 */       double k = ((Double)e.getKey()).doubleValue();
/* 1048 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1049 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1050 */         if (Double2ByteOpenHashMap.this.containsNullKey && Double2ByteOpenHashMap.this.value[Double2ByteOpenHashMap.this.n] == v) {
/* 1051 */           Double2ByteOpenHashMap.this.removeNullEntry();
/* 1052 */           return true;
/*      */         } 
/* 1054 */         return false;
/*      */       } 
/*      */       
/* 1057 */       double[] key = Double2ByteOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1060 */       if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ByteOpenHashMap.this.mask]) == 0L) return false; 
/* 1061 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1062 */         if (Double2ByteOpenHashMap.this.value[pos] == v) {
/* 1063 */           Double2ByteOpenHashMap.this.removeEntry(pos);
/* 1064 */           return true;
/*      */         } 
/* 1066 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1069 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ByteOpenHashMap.this.mask]) == 0L) return false; 
/* 1070 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1071 */           Double2ByteOpenHashMap.this.value[pos] == v) {
/* 1072 */           Double2ByteOpenHashMap.this.removeEntry(pos);
/* 1073 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1081 */       return Double2ByteOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1086 */       Double2ByteOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ByteMap.Entry> consumer) {
/* 1092 */       if (Double2ByteOpenHashMap.this.containsNullKey) consumer.accept(new Double2ByteOpenHashMap.MapEntry(Double2ByteOpenHashMap.this.n)); 
/* 1093 */       for (int pos = Double2ByteOpenHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2ByteOpenHashMap.this.key[pos]) != 0L) consumer.accept(new Double2ByteOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ByteMap.Entry> consumer) {
/* 1099 */       Double2ByteOpenHashMap.MapEntry entry = new Double2ByteOpenHashMap.MapEntry();
/* 1100 */       if (Double2ByteOpenHashMap.this.containsNullKey) {
/* 1101 */         entry.index = Double2ByteOpenHashMap.this.n;
/* 1102 */         consumer.accept(entry);
/*      */       } 
/* 1104 */       for (int pos = Double2ByteOpenHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2ByteOpenHashMap.this.key[pos]) != 0L) {
/* 1105 */           entry.index = pos;
/* 1106 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Double2ByteMap.FastEntrySet double2ByteEntrySet() {
/* 1113 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1114 */     return this.entries;
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
/*      */     extends MapIterator<DoubleConsumer>
/*      */     implements DoubleIterator
/*      */   {
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1135 */       action.accept(Double2ByteOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1140 */       return Double2ByteOpenHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<DoubleConsumer, KeySpliterator> implements DoubleSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1151 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1156 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1161 */       action.accept(Double2ByteOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1166 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1173 */       return new Double2ByteOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator spliterator() {
/* 1178 */       return new Double2ByteOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1184 */       if (Double2ByteOpenHashMap.this.containsNullKey) consumer.accept(Double2ByteOpenHashMap.this.key[Double2ByteOpenHashMap.this.n]); 
/* 1185 */       for (int pos = Double2ByteOpenHashMap.this.n; pos-- != 0; ) {
/* 1186 */         double k = Double2ByteOpenHashMap.this.key[pos];
/* 1187 */         if (Double.doubleToLongBits(k) != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1193 */       return Double2ByteOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1198 */       return Double2ByteOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1203 */       int oldSize = Double2ByteOpenHashMap.this.size;
/* 1204 */       Double2ByteOpenHashMap.this.remove(k);
/* 1205 */       return (Double2ByteOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1210 */       Double2ByteOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1216 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1217 */     return this.keys;
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
/* 1238 */       action.accept(Double2ByteOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1243 */       return Double2ByteOpenHashMap.this.value[nextEntry()];
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
/* 1254 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1259 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1264 */       action.accept(Double2ByteOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1269 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteCollection values() {
/* 1275 */     if (this.values == null) this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1278 */             return new Double2ByteOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ByteSpliterator spliterator() {
/* 1283 */             return new Double2ByteOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ByteConsumer consumer) {
/* 1289 */             if (Double2ByteOpenHashMap.this.containsNullKey) consumer.accept(Double2ByteOpenHashMap.this.value[Double2ByteOpenHashMap.this.n]); 
/* 1290 */             for (int pos = Double2ByteOpenHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2ByteOpenHashMap.this.key[pos]) != 0L) consumer.accept(Double2ByteOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1295 */             return Double2ByteOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(byte v) {
/* 1300 */             return Double2ByteOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1305 */             Double2ByteOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1308 */     return this.values;
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
/* 1325 */     return trim(this.size);
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
/* 1347 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1348 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1350 */       rehash(l);
/* 1351 */     } catch (OutOfMemoryError cantDoIt) {
/* 1352 */       return false;
/*      */     } 
/* 1354 */     return true;
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
/* 1369 */     double[] key = this.key;
/* 1370 */     byte[] value = this.value;
/* 1371 */     int mask = newN - 1;
/* 1372 */     double[] newKey = new double[newN + 1];
/* 1373 */     byte[] newValue = new byte[newN + 1];
/* 1374 */     int i = this.n;
/* 1375 */     for (int j = realSize(); j-- != 0; ) {
/* 1376 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1377 */       if (Double.doubleToLongBits(newKey[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L) while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1378 */       newKey[pos] = key[i];
/* 1379 */       newValue[pos] = value[i];
/*      */     } 
/* 1381 */     newValue[newN] = value[this.n];
/* 1382 */     this.n = newN;
/* 1383 */     this.mask = mask;
/* 1384 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1385 */     this.key = newKey;
/* 1386 */     this.value = newValue;
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
/*      */   public Double2ByteOpenHashMap clone() {
/*      */     Double2ByteOpenHashMap c;
/*      */     try {
/* 1403 */       c = (Double2ByteOpenHashMap)super.clone();
/* 1404 */     } catch (CloneNotSupportedException cantHappen) {
/* 1405 */       throw new InternalError();
/*      */     } 
/* 1407 */     c.keys = null;
/* 1408 */     c.values = null;
/* 1409 */     c.entries = null;
/* 1410 */     c.containsNullKey = this.containsNullKey;
/* 1411 */     c.key = (double[])this.key.clone();
/* 1412 */     c.value = (byte[])this.value.clone();
/* 1413 */     return c;
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
/* 1427 */     int h = 0;
/* 1428 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1429 */       for (; Double.doubleToLongBits(this.key[i]) == 0L; i++);
/* 1430 */       t = HashCommon.double2int(this.key[i]);
/* 1431 */       t ^= this.value[i];
/* 1432 */       h += t;
/* 1433 */       i++;
/*      */     } 
/*      */     
/* 1436 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1437 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1441 */     double[] key = this.key;
/* 1442 */     byte[] value = this.value;
/* 1443 */     EntryIterator i = new EntryIterator();
/* 1444 */     s.defaultWriteObject();
/* 1445 */     for (int j = this.size; j-- != 0; ) {
/* 1446 */       int e = i.nextEntry();
/* 1447 */       s.writeDouble(key[e]);
/* 1448 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1453 */     s.defaultReadObject();
/* 1454 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1455 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1456 */     this.mask = this.n - 1;
/* 1457 */     double[] key = this.key = new double[this.n + 1];
/* 1458 */     byte[] value = this.value = new byte[this.n + 1];
/*      */ 
/*      */     
/* 1461 */     for (int i = this.size; i-- != 0; ) {
/* 1462 */       int pos; double k = s.readDouble();
/* 1463 */       byte v = s.readByte();
/* 1464 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1465 */         pos = this.n;
/* 1466 */         this.containsNullKey = true;
/*      */       } else {
/* 1468 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1469 */         for (; Double.doubleToLongBits(key[pos]) != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1471 */       key[pos] = k;
/* 1472 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2ByteOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */