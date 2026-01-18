/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2ByteOpenCustomHashMap
/*      */   extends AbstractFloat2ByteMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient byte[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected FloatHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2ByteMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient ByteCollection values;
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
/*  104 */     this.strategy = strategy;
/*  105 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  106 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  107 */     this.f = f;
/*  108 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  109 */     this.mask = this.n - 1;
/*  110 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  111 */     this.key = new float[this.n + 1];
/*  112 */     this.value = new byte[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
/*  122 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(FloatHash.Strategy strategy) {
/*  132 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(Map<? extends Float, ? extends Byte> m, float f, FloatHash.Strategy strategy) {
/*  143 */     this(m.size(), f, strategy);
/*  144 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(Map<? extends Float, ? extends Byte> m, FloatHash.Strategy strategy) {
/*  154 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(Float2ByteMap m, float f, FloatHash.Strategy strategy) {
/*  165 */     this(m.size(), f, strategy);
/*  166 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteOpenCustomHashMap(Float2ByteMap m, FloatHash.Strategy strategy) {
/*  177 */     this(m, 0.75F, strategy);
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
/*      */   public Float2ByteOpenCustomHashMap(float[] k, byte[] v, float f, FloatHash.Strategy strategy) {
/*  190 */     this(k.length, f, strategy);
/*  191 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  192 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Float2ByteOpenCustomHashMap(float[] k, byte[] v, FloatHash.Strategy strategy) {
/*  205 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatHash.Strategy strategy() {
/*  214 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  218 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  228 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  229 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  233 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  234 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private byte removeEntry(int pos) {
/*  238 */     byte oldValue = this.value[pos];
/*  239 */     this.size--;
/*  240 */     shiftKeys(pos);
/*  241 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  242 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte removeNullEntry() {
/*  246 */     this.containsNullKey = false;
/*  247 */     byte oldValue = this.value[this.n];
/*  248 */     this.size--;
/*  249 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  250 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Byte> m) {
/*  255 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  256 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  258 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  262 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  264 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  267 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return -(pos + 1); 
/*  268 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  271 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  272 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, byte v) {
/*  277 */     if (pos == this.n) this.containsNullKey = true; 
/*  278 */     this.key[pos] = k;
/*  279 */     this.value[pos] = v;
/*  280 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(float k, byte v) {
/*  286 */     int pos = find(k);
/*  287 */     if (pos < 0) {
/*  288 */       insert(-pos - 1, k, v);
/*  289 */       return this.defRetValue;
/*      */     } 
/*  291 */     byte oldValue = this.value[pos];
/*  292 */     this.value[pos] = v;
/*  293 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte addToValue(int pos, byte incr) {
/*  297 */     byte oldValue = this.value[pos];
/*  298 */     this.value[pos] = (byte)(oldValue + incr);
/*  299 */     return oldValue;
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
/*      */   public byte addTo(float k, byte incr) {
/*      */     int pos;
/*  317 */     if (this.strategy.equals(k, 0.0F)) {
/*  318 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  319 */       pos = this.n;
/*  320 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  323 */       float[] key = this.key;
/*      */       float curr;
/*  325 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  326 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  327 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  330 */     }  this.key[pos] = k;
/*  331 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  332 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  334 */     return this.defRetValue;
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
/*  347 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  349 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  351 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  352 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  355 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  356 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  357 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  359 */       key[last] = curr;
/*  360 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte remove(float k) {
/*  367 */     if (this.strategy.equals(k, 0.0F)) {
/*  368 */       if (this.containsNullKey) return removeNullEntry(); 
/*  369 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  372 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  375 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  376 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  378 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  379 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte get(float k) {
/*  386 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  388 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  391 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  392 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  395 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  396 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  403 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey;
/*      */     
/*  405 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  408 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  409 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  412 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  413 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  419 */     byte[] value = this.value;
/*  420 */     float[] key = this.key;
/*  421 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  422 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && value[i] == v) return true;  }
/*  423 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(float k, byte defaultValue) {
/*  430 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  432 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  435 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return defaultValue; 
/*  436 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  439 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  440 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(float k, byte v) {
/*  447 */     int pos = find(k);
/*  448 */     if (pos >= 0) return this.value[pos]; 
/*  449 */     insert(-pos - 1, k, v);
/*  450 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, byte v) {
/*  457 */     if (this.strategy.equals(k, 0.0F)) {
/*  458 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  459 */         removeNullEntry();
/*  460 */         return true;
/*      */       } 
/*  462 */       return false;
/*      */     } 
/*      */     
/*  465 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  468 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  469 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  470 */       removeEntry(pos);
/*  471 */       return true;
/*      */     } 
/*      */     while (true) {
/*  474 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  475 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  476 */         removeEntry(pos);
/*  477 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, byte oldValue, byte v) {
/*  485 */     int pos = find(k);
/*  486 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  487 */     this.value[pos] = v;
/*  488 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte replace(float k, byte v) {
/*  494 */     int pos = find(k);
/*  495 */     if (pos < 0) return this.defRetValue; 
/*  496 */     byte oldValue = this.value[pos];
/*  497 */     this.value[pos] = v;
/*  498 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
/*  504 */     Objects.requireNonNull(mappingFunction);
/*  505 */     int pos = find(k);
/*  506 */     if (pos >= 0) return this.value[pos]; 
/*  507 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  508 */     insert(-pos - 1, k, newValue);
/*  509 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(float key, Float2ByteFunction mappingFunction) {
/*  515 */     Objects.requireNonNull(mappingFunction);
/*  516 */     int pos = find(key);
/*  517 */     if (pos >= 0) return this.value[pos]; 
/*  518 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  519 */     byte newValue = mappingFunction.get(key);
/*  520 */     insert(-pos - 1, key, newValue);
/*  521 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(float k, DoubleFunction<? extends Byte> mappingFunction) {
/*  527 */     Objects.requireNonNull(mappingFunction);
/*  528 */     int pos = find(k);
/*  529 */     if (pos >= 0) return this.value[pos]; 
/*  530 */     Byte newValue = mappingFunction.apply(k);
/*  531 */     if (newValue == null) return this.defRetValue; 
/*  532 */     byte v = newValue.byteValue();
/*  533 */     insert(-pos - 1, k, v);
/*  534 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
/*  540 */     Objects.requireNonNull(remappingFunction);
/*  541 */     int pos = find(k);
/*  542 */     if (pos < 0) return this.defRetValue; 
/*  543 */     Byte newValue = remappingFunction.apply(Float.valueOf(k), Byte.valueOf(this.value[pos]));
/*  544 */     if (newValue == null) {
/*  545 */       if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  546 */       else { removeEntry(pos); }
/*  547 */        return this.defRetValue;
/*      */     } 
/*  549 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
/*  555 */     Objects.requireNonNull(remappingFunction);
/*  556 */     int pos = find(k);
/*  557 */     Byte newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  558 */     if (newValue == null) {
/*  559 */       if (pos >= 0)
/*  560 */         if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  561 */         else { removeEntry(pos); }
/*      */          
/*  563 */       return this.defRetValue;
/*      */     } 
/*  565 */     byte newVal = newValue.byteValue();
/*  566 */     if (pos < 0) {
/*  567 */       insert(-pos - 1, k, newVal);
/*  568 */       return newVal;
/*      */     } 
/*  570 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(float k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  578 */     int pos = find(k);
/*  579 */     if (pos < 0) {
/*  580 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  581 */       else { this.value[pos] = v; }
/*  582 */        return v;
/*      */     } 
/*  584 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  585 */     if (newValue == null) {
/*  586 */       if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  587 */       else { removeEntry(pos); }
/*  588 */        return this.defRetValue;
/*      */     } 
/*  590 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  601 */     if (this.size == 0)
/*  602 */       return;  this.size = 0;
/*  603 */     this.containsNullKey = false;
/*  604 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  609 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  614 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2ByteMap.Entry, Map.Entry<Float, Byte>, FloatBytePair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  627 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public float getFloatKey() {
/*  635 */       return Float2ByteOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  640 */       return Float2ByteOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByteValue() {
/*  645 */       return Float2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte rightByte() {
/*  650 */       return Float2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte v) {
/*  655 */       byte oldValue = Float2ByteOpenCustomHashMap.this.value[this.index];
/*  656 */       Float2ByteOpenCustomHashMap.this.value[this.index] = v;
/*  657 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBytePair right(byte v) {
/*  662 */       Float2ByteOpenCustomHashMap.this.value[this.index] = v;
/*  663 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  674 */       return Float.valueOf(Float2ByteOpenCustomHashMap.this.key[this.index]);
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
/*  685 */       return Byte.valueOf(Float2ByteOpenCustomHashMap.this.value[this.index]);
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
/*  696 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  702 */       if (!(o instanceof Map.Entry)) return false; 
/*  703 */       Map.Entry<Float, Byte> e = (Map.Entry<Float, Byte>)o;
/*  704 */       return (Float2ByteOpenCustomHashMap.this.strategy.equals(Float2ByteOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && Float2ByteOpenCustomHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  709 */       return Float2ByteOpenCustomHashMap.this.strategy.hashCode(Float2ByteOpenCustomHashMap.this.key[this.index]) ^ Float2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  714 */       return Float2ByteOpenCustomHashMap.this.key[this.index] + "=>" + Float2ByteOpenCustomHashMap.this.value[this.index];
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
/*  725 */     int pos = Float2ByteOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     int last = -1;
/*      */     
/*  733 */     int c = Float2ByteOpenCustomHashMap.this.size;
/*      */     
/*  735 */     boolean mustReturnNullKey = Float2ByteOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  746 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  750 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  751 */       this.c--;
/*  752 */       if (this.mustReturnNullKey) {
/*  753 */         this.mustReturnNullKey = false;
/*  754 */         return this.last = Float2ByteOpenCustomHashMap.this.n;
/*      */       } 
/*  756 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  758 */         if (--this.pos < 0) {
/*      */           
/*  760 */           this.last = Integer.MIN_VALUE;
/*  761 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  762 */           int p = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ByteOpenCustomHashMap.this.mask;
/*  763 */           for (; !Float2ByteOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Float2ByteOpenCustomHashMap.this.mask);
/*  764 */           return p;
/*      */         } 
/*  766 */         if (Float.floatToIntBits(key[this.pos]) != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  771 */       if (this.mustReturnNullKey) {
/*  772 */         this.mustReturnNullKey = false;
/*  773 */         acceptOnIndex(action, this.last = Float2ByteOpenCustomHashMap.this.n);
/*  774 */         this.c--;
/*      */       } 
/*  776 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*  777 */       while (this.c != 0) {
/*  778 */         if (--this.pos < 0) {
/*      */           
/*  780 */           this.last = Integer.MIN_VALUE;
/*  781 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  782 */           int p = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ByteOpenCustomHashMap.this.mask;
/*  783 */           for (; !Float2ByteOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Float2ByteOpenCustomHashMap.this.mask);
/*  784 */           acceptOnIndex(action, p);
/*  785 */           this.c--; continue;
/*  786 */         }  if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  787 */           acceptOnIndex(action, this.last = this.pos);
/*  788 */           this.c--;
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
/*  803 */       float[] key = Float2ByteOpenCustomHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  805 */         pos = (last = pos) + 1 & Float2ByteOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  807 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  808 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  811 */           int slot = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2ByteOpenCustomHashMap.this.mask;
/*  812 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  813 */             break;  pos = pos + 1 & Float2ByteOpenCustomHashMap.this.mask;
/*      */         } 
/*  815 */         if (pos < last) {
/*  816 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(2); 
/*  817 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  819 */         key[last] = curr;
/*  820 */         Float2ByteOpenCustomHashMap.this.value[last] = Float2ByteOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  825 */       if (this.last == -1) throw new IllegalStateException(); 
/*  826 */       if (this.last == Float2ByteOpenCustomHashMap.this.n)
/*  827 */       { Float2ByteOpenCustomHashMap.this.containsNullKey = false; }
/*  828 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  831 */       { Float2ByteOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  832 */         this.last = -1;
/*      */         return; }
/*      */       
/*  835 */       Float2ByteOpenCustomHashMap.this.size--;
/*  836 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  841 */       int i = n;
/*  842 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  843 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2ByteMap.Entry>> implements ObjectIterator<Float2ByteMap.Entry> { public Float2ByteOpenCustomHashMap.MapEntry next() {
/*  852 */       return this.entry = new Float2ByteOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Float2ByteOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ByteMap.Entry> action, int index) {
/*  858 */       action.accept(this.entry = new Float2ByteOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  863 */       super.remove();
/*  864 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2ByteMap.Entry>> implements ObjectIterator<Float2ByteMap.Entry> {
/*      */     private FastEntryIterator() {
/*  869 */       this.entry = new Float2ByteOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Float2ByteOpenCustomHashMap.MapEntry entry;
/*      */     public Float2ByteOpenCustomHashMap.MapEntry next() {
/*  873 */       this.entry.index = nextEntry();
/*  874 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ByteMap.Entry> action, int index) {
/*  880 */       this.entry.index = index;
/*  881 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  890 */     int pos = 0;
/*      */     
/*  892 */     int max = Float2ByteOpenCustomHashMap.this.n;
/*      */     
/*  894 */     int c = 0;
/*      */     
/*  896 */     boolean mustReturnNull = Float2ByteOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  903 */       this.pos = pos;
/*  904 */       this.max = max;
/*  905 */       this.mustReturnNull = mustReturnNull;
/*  906 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  914 */       if (this.mustReturnNull) {
/*  915 */         this.mustReturnNull = false;
/*  916 */         this.c++;
/*  917 */         acceptOnIndex(action, Float2ByteOpenCustomHashMap.this.n);
/*  918 */         return true;
/*      */       } 
/*  920 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*  921 */       while (this.pos < this.max) {
/*  922 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  923 */           this.c++;
/*  924 */           acceptOnIndex(action, this.pos++);
/*  925 */           return true;
/*      */         } 
/*  927 */         this.pos++;
/*      */       } 
/*  929 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  933 */       if (this.mustReturnNull) {
/*  934 */         this.mustReturnNull = false;
/*  935 */         this.c++;
/*  936 */         acceptOnIndex(action, Float2ByteOpenCustomHashMap.this.n);
/*      */       } 
/*  938 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*  939 */       while (this.pos < this.max) {
/*  940 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  941 */           acceptOnIndex(action, this.pos);
/*  942 */           this.c++;
/*      */         } 
/*  944 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  949 */       if (!this.hasSplit)
/*      */       {
/*  951 */         return (Float2ByteOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  956 */       return Math.min((Float2ByteOpenCustomHashMap.this.size - this.c), (long)(Float2ByteOpenCustomHashMap.this.realSize() / Float2ByteOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  961 */       if (this.pos >= this.max - 1) return null; 
/*  962 */       int retLen = this.max - this.pos >> 1;
/*  963 */       if (retLen <= 1) return null; 
/*  964 */       int myNewPos = this.pos + retLen;
/*  965 */       int retPos = this.pos;
/*  966 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  970 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  971 */       this.pos = myNewPos;
/*  972 */       this.mustReturnNull = false;
/*  973 */       this.hasSplit = true;
/*  974 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  978 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  979 */       if (n == 0L) return 0L; 
/*  980 */       long skipped = 0L;
/*  981 */       if (this.mustReturnNull) {
/*  982 */         this.mustReturnNull = false;
/*  983 */         skipped++;
/*  984 */         n--;
/*      */       } 
/*  986 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*  987 */       while (this.pos < this.max && n > 0L) {
/*  988 */         if (Float.floatToIntBits(key[this.pos++]) != 0) {
/*  989 */           skipped++;
/*  990 */           n--;
/*      */         } 
/*      */       } 
/*  993 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Float2ByteMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Float2ByteMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1004 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1009 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ByteMap.Entry> action, int index) {
/* 1014 */       action.accept(new Float2ByteOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1019 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2ByteMap.Entry> implements Float2ByteMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2ByteMap.Entry> iterator() {
/* 1026 */       return new Float2ByteOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Float2ByteMap.Entry> fastIterator() {
/* 1031 */       return new Float2ByteOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Float2ByteMap.Entry> spliterator() {
/* 1036 */       return new Float2ByteOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1043 */       if (!(o instanceof Map.Entry)) return false; 
/* 1044 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1045 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1046 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1047 */       float k = ((Float)e.getKey()).floatValue();
/* 1048 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1049 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, 0.0F)) return (Float2ByteOpenCustomHashMap.this.containsNullKey && Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n] == v);
/*      */       
/* 1051 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1054 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1055 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1058 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1059 */         if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1066 */       if (!(o instanceof Map.Entry)) return false; 
/* 1067 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1068 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1069 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1070 */       float k = ((Float)e.getKey()).floatValue();
/* 1071 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1072 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/* 1073 */         if (Float2ByteOpenCustomHashMap.this.containsNullKey && Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n] == v) {
/* 1074 */           Float2ByteOpenCustomHashMap.this.removeNullEntry();
/* 1075 */           return true;
/*      */         } 
/* 1077 */         return false;
/*      */       } 
/*      */       
/* 1080 */       float[] key = Float2ByteOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1083 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1084 */       if (Float2ByteOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1085 */         if (Float2ByteOpenCustomHashMap.this.value[pos] == v) {
/* 1086 */           Float2ByteOpenCustomHashMap.this.removeEntry(pos);
/* 1087 */           return true;
/*      */         } 
/* 1089 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1092 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1093 */         if (Float2ByteOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1094 */           Float2ByteOpenCustomHashMap.this.value[pos] == v) {
/* 1095 */           Float2ByteOpenCustomHashMap.this.removeEntry(pos);
/* 1096 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1104 */       return Float2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1109 */       Float2ByteOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ByteMap.Entry> consumer) {
/* 1115 */       if (Float2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(new Float2ByteOpenCustomHashMap.MapEntry(Float2ByteOpenCustomHashMap.this.n)); 
/* 1116 */       for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2ByteOpenCustomHashMap.this.key[pos]) != 0) consumer.accept(new Float2ByteOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ByteMap.Entry> consumer) {
/* 1122 */       Float2ByteOpenCustomHashMap.MapEntry entry = new Float2ByteOpenCustomHashMap.MapEntry();
/* 1123 */       if (Float2ByteOpenCustomHashMap.this.containsNullKey) {
/* 1124 */         entry.index = Float2ByteOpenCustomHashMap.this.n;
/* 1125 */         consumer.accept(entry);
/*      */       } 
/* 1127 */       for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2ByteOpenCustomHashMap.this.key[pos]) != 0) {
/* 1128 */           entry.index = pos;
/* 1129 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Float2ByteMap.FastEntrySet float2ByteEntrySet() {
/* 1136 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1137 */     return this.entries;
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatIterator
/*      */   {
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1158 */       action.accept(Float2ByteOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1163 */       return Float2ByteOpenCustomHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<FloatConsumer, KeySpliterator> implements FloatSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1174 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1179 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1184 */       action.accept(Float2ByteOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1189 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1196 */       return new Float2ByteOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1201 */       return new Float2ByteOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1207 */       if (Float2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(Float2ByteOpenCustomHashMap.this.key[Float2ByteOpenCustomHashMap.this.n]); 
/* 1208 */       for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1209 */         float k = Float2ByteOpenCustomHashMap.this.key[pos];
/* 1210 */         if (Float.floatToIntBits(k) != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1216 */       return Float2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1221 */       return Float2ByteOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1226 */       int oldSize = Float2ByteOpenCustomHashMap.this.size;
/* 1227 */       Float2ByteOpenCustomHashMap.this.remove(k);
/* 1228 */       return (Float2ByteOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1233 */       Float2ByteOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1239 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1240 */     return this.keys;
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
/* 1261 */       action.accept(Float2ByteOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1266 */       return Float2ByteOpenCustomHashMap.this.value[nextEntry()];
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
/* 1277 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1282 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1287 */       action.accept(Float2ByteOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1292 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteCollection values() {
/* 1298 */     if (this.values == null) this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1301 */             return new Float2ByteOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ByteSpliterator spliterator() {
/* 1306 */             return new Float2ByteOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ByteConsumer consumer) {
/* 1312 */             if (Float2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(Float2ByteOpenCustomHashMap.this.value[Float2ByteOpenCustomHashMap.this.n]); 
/* 1313 */             for (int pos = Float2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2ByteOpenCustomHashMap.this.key[pos]) != 0) consumer.accept(Float2ByteOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1318 */             return Float2ByteOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(byte v) {
/* 1323 */             return Float2ByteOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1328 */             Float2ByteOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1331 */     return this.values;
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
/* 1348 */     return trim(this.size);
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
/* 1370 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1371 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1373 */       rehash(l);
/* 1374 */     } catch (OutOfMemoryError cantDoIt) {
/* 1375 */       return false;
/*      */     } 
/* 1377 */     return true;
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
/* 1392 */     float[] key = this.key;
/* 1393 */     byte[] value = this.value;
/* 1394 */     int mask = newN - 1;
/* 1395 */     float[] newKey = new float[newN + 1];
/* 1396 */     byte[] newValue = new byte[newN + 1];
/* 1397 */     int i = this.n;
/* 1398 */     for (int j = realSize(); j-- != 0; ) {
/* 1399 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1400 */       if (Float.floatToIntBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0) while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1401 */       newKey[pos] = key[i];
/* 1402 */       newValue[pos] = value[i];
/*      */     } 
/* 1404 */     newValue[newN] = value[this.n];
/* 1405 */     this.n = newN;
/* 1406 */     this.mask = mask;
/* 1407 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1408 */     this.key = newKey;
/* 1409 */     this.value = newValue;
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
/*      */   public Float2ByteOpenCustomHashMap clone() {
/*      */     Float2ByteOpenCustomHashMap c;
/*      */     try {
/* 1426 */       c = (Float2ByteOpenCustomHashMap)super.clone();
/* 1427 */     } catch (CloneNotSupportedException cantHappen) {
/* 1428 */       throw new InternalError();
/*      */     } 
/* 1430 */     c.keys = null;
/* 1431 */     c.values = null;
/* 1432 */     c.entries = null;
/* 1433 */     c.containsNullKey = this.containsNullKey;
/* 1434 */     c.key = (float[])this.key.clone();
/* 1435 */     c.value = (byte[])this.value.clone();
/* 1436 */     c.strategy = this.strategy;
/* 1437 */     return c;
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
/* 1451 */     int h = 0;
/* 1452 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1453 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1454 */       t = this.strategy.hashCode(this.key[i]);
/* 1455 */       t ^= this.value[i];
/* 1456 */       h += t;
/* 1457 */       i++;
/*      */     } 
/*      */     
/* 1460 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1461 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1465 */     float[] key = this.key;
/* 1466 */     byte[] value = this.value;
/* 1467 */     EntryIterator i = new EntryIterator();
/* 1468 */     s.defaultWriteObject();
/* 1469 */     for (int j = this.size; j-- != 0; ) {
/* 1470 */       int e = i.nextEntry();
/* 1471 */       s.writeFloat(key[e]);
/* 1472 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1477 */     s.defaultReadObject();
/* 1478 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1479 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1480 */     this.mask = this.n - 1;
/* 1481 */     float[] key = this.key = new float[this.n + 1];
/* 1482 */     byte[] value = this.value = new byte[this.n + 1];
/*      */ 
/*      */     
/* 1485 */     for (int i = this.size; i-- != 0; ) {
/* 1486 */       int pos; float k = s.readFloat();
/* 1487 */       byte v = s.readByte();
/* 1488 */       if (this.strategy.equals(k, 0.0F)) {
/* 1489 */         pos = this.n;
/* 1490 */         this.containsNullKey = true;
/*      */       } else {
/* 1492 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1493 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1495 */       key[pos] = k;
/* 1496 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2ByteOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */