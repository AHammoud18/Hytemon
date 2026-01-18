/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ 
/*      */ public class Char2ByteOpenCustomHashMap
/*      */   extends AbstractChar2ByteMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient byte[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected CharHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2ByteMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient ByteCollection values;
/*      */   
/*      */   public Char2ByteOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
/*  104 */     this.strategy = strategy;
/*  105 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  106 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  107 */     this.f = f;
/*  108 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  109 */     this.mask = this.n - 1;
/*  110 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  111 */     this.key = new char[this.n + 1];
/*  112 */     this.value = new byte[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ByteOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
/*  122 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ByteOpenCustomHashMap(CharHash.Strategy strategy) {
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
/*      */   public Char2ByteOpenCustomHashMap(Map<? extends Character, ? extends Byte> m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2ByteOpenCustomHashMap(Map<? extends Character, ? extends Byte> m, CharHash.Strategy strategy) {
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
/*      */   public Char2ByteOpenCustomHashMap(Char2ByteMap m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2ByteOpenCustomHashMap(Char2ByteMap m, CharHash.Strategy strategy) {
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
/*      */   public Char2ByteOpenCustomHashMap(char[] k, byte[] v, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2ByteOpenCustomHashMap(char[] k, byte[] v, CharHash.Strategy strategy) {
/*  205 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharHash.Strategy strategy() {
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
/*      */   public void putAll(Map<? extends Character, ? extends Byte> m) {
/*  255 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  256 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  258 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  262 */     if (this.strategy.equals(k, false)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  264 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  267 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return -(pos + 1); 
/*  268 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  271 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return -(pos + 1); 
/*  272 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, char k, byte v) {
/*  277 */     if (pos == this.n) this.containsNullKey = true; 
/*  278 */     this.key[pos] = k;
/*  279 */     this.value[pos] = v;
/*  280 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(char k, byte v) {
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
/*      */   public byte addTo(char k, byte incr) {
/*      */     int pos;
/*  317 */     if (this.strategy.equals(k, false)) {
/*  318 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  319 */       pos = this.n;
/*  320 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  323 */       char[] key = this.key;
/*      */       char curr;
/*  325 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*  326 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  327 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
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
/*  347 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  349 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  351 */         if ((curr = key[pos]) == '\000') {
/*  352 */           key[last] = Character.MIN_VALUE;
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
/*      */   public byte remove(char k) {
/*  367 */     if (this.strategy.equals(k, false)) {
/*  368 */       if (this.containsNullKey) return removeNullEntry(); 
/*  369 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  372 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  375 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return this.defRetValue; 
/*  376 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  378 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return this.defRetValue; 
/*  379 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte get(char k) {
/*  386 */     if (this.strategy.equals(k, false)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  388 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  391 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return this.defRetValue; 
/*  392 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  395 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return this.defRetValue; 
/*  396 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(char k) {
/*  403 */     if (this.strategy.equals(k, false)) return this.containsNullKey;
/*      */     
/*  405 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  408 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return false; 
/*  409 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  412 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return false; 
/*  413 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  419 */     byte[] value = this.value;
/*  420 */     char[] key = this.key;
/*  421 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  422 */     for (int i = this.n; i-- != 0;) { if (key[i] != '\000' && value[i] == v) return true;  }
/*  423 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(char k, byte defaultValue) {
/*  430 */     if (this.strategy.equals(k, false)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  432 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  435 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return defaultValue; 
/*  436 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  439 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return defaultValue; 
/*  440 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(char k, byte v) {
/*  447 */     int pos = find(k);
/*  448 */     if (pos >= 0) return this.value[pos]; 
/*  449 */     insert(-pos - 1, k, v);
/*  450 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, byte v) {
/*  457 */     if (this.strategy.equals(k, false)) {
/*  458 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  459 */         removeNullEntry();
/*  460 */         return true;
/*      */       } 
/*  462 */       return false;
/*      */     } 
/*      */     
/*  465 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  468 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return false; 
/*  469 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  470 */       removeEntry(pos);
/*  471 */       return true;
/*      */     } 
/*      */     while (true) {
/*  474 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return false; 
/*  475 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  476 */         removeEntry(pos);
/*  477 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(char k, byte oldValue, byte v) {
/*  485 */     int pos = find(k);
/*  486 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  487 */     this.value[pos] = v;
/*  488 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte replace(char k, byte v) {
/*  494 */     int pos = find(k);
/*  495 */     if (pos < 0) return this.defRetValue; 
/*  496 */     byte oldValue = this.value[pos];
/*  497 */     this.value[pos] = v;
/*  498 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(char k, IntUnaryOperator mappingFunction) {
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
/*      */   public byte computeIfAbsent(char key, Char2ByteFunction mappingFunction) {
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
/*      */   public byte computeIfAbsentNullable(char k, IntFunction<? extends Byte> mappingFunction) {
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
/*      */   public byte computeIfPresent(char k, BiFunction<? super Character, ? super Byte, ? extends Byte> remappingFunction) {
/*  540 */     Objects.requireNonNull(remappingFunction);
/*  541 */     int pos = find(k);
/*  542 */     if (pos < 0) return this.defRetValue; 
/*  543 */     Byte newValue = remappingFunction.apply(Character.valueOf(k), Byte.valueOf(this.value[pos]));
/*  544 */     if (newValue == null) {
/*  545 */       if (this.strategy.equals(k, false)) { removeNullEntry(); }
/*  546 */       else { removeEntry(pos); }
/*  547 */        return this.defRetValue;
/*      */     } 
/*  549 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(char k, BiFunction<? super Character, ? super Byte, ? extends Byte> remappingFunction) {
/*  555 */     Objects.requireNonNull(remappingFunction);
/*  556 */     int pos = find(k);
/*  557 */     Byte newValue = remappingFunction.apply(Character.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  558 */     if (newValue == null) {
/*  559 */       if (pos >= 0)
/*  560 */         if (this.strategy.equals(k, false)) { removeNullEntry(); }
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
/*      */   public byte merge(char k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*  586 */       if (this.strategy.equals(k, false)) { removeNullEntry(); }
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
/*  604 */     Arrays.fill(this.key, false);
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
/*      */     implements Char2ByteMap.Entry, Map.Entry<Character, Byte>, CharBytePair
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
/*      */     public char getCharKey() {
/*  635 */       return Char2ByteOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char leftChar() {
/*  640 */       return Char2ByteOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByteValue() {
/*  645 */       return Char2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte rightByte() {
/*  650 */       return Char2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte v) {
/*  655 */       byte oldValue = Char2ByteOpenCustomHashMap.this.value[this.index];
/*  656 */       Char2ByteOpenCustomHashMap.this.value[this.index] = v;
/*  657 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharBytePair right(byte v) {
/*  662 */       Char2ByteOpenCustomHashMap.this.value[this.index] = v;
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
/*      */     public Character getKey() {
/*  674 */       return Character.valueOf(Char2ByteOpenCustomHashMap.this.key[this.index]);
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
/*  685 */       return Byte.valueOf(Char2ByteOpenCustomHashMap.this.value[this.index]);
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
/*  703 */       Map.Entry<Character, Byte> e = (Map.Entry<Character, Byte>)o;
/*  704 */       return (Char2ByteOpenCustomHashMap.this.strategy.equals(Char2ByteOpenCustomHashMap.this.key[this.index], ((Character)e.getKey()).charValue()) && Char2ByteOpenCustomHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  709 */       return Char2ByteOpenCustomHashMap.this.strategy.hashCode(Char2ByteOpenCustomHashMap.this.key[this.index]) ^ Char2ByteOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  714 */       return Char2ByteOpenCustomHashMap.this.key[this.index] + "=>" + Char2ByteOpenCustomHashMap.this.value[this.index];
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
/*  725 */     int pos = Char2ByteOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     int last = -1;
/*      */     
/*  733 */     int c = Char2ByteOpenCustomHashMap.this.size;
/*      */     
/*  735 */     boolean mustReturnNullKey = Char2ByteOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CharArrayList wrapped;
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
/*  754 */         return this.last = Char2ByteOpenCustomHashMap.this.n;
/*      */       } 
/*  756 */       char[] key = Char2ByteOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  758 */         if (--this.pos < 0) {
/*      */           
/*  760 */           this.last = Integer.MIN_VALUE;
/*  761 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  762 */           int p = HashCommon.mix(Char2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ByteOpenCustomHashMap.this.mask;
/*  763 */           for (; !Char2ByteOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Char2ByteOpenCustomHashMap.this.mask);
/*  764 */           return p;
/*      */         } 
/*  766 */         if (key[this.pos] != '\000') return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  771 */       if (this.mustReturnNullKey) {
/*  772 */         this.mustReturnNullKey = false;
/*  773 */         acceptOnIndex(action, this.last = Char2ByteOpenCustomHashMap.this.n);
/*  774 */         this.c--;
/*      */       } 
/*  776 */       char[] key = Char2ByteOpenCustomHashMap.this.key;
/*  777 */       while (this.c != 0) {
/*  778 */         if (--this.pos < 0) {
/*      */           
/*  780 */           this.last = Integer.MIN_VALUE;
/*  781 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  782 */           int p = HashCommon.mix(Char2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ByteOpenCustomHashMap.this.mask;
/*  783 */           for (; !Char2ByteOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Char2ByteOpenCustomHashMap.this.mask);
/*  784 */           acceptOnIndex(action, p);
/*  785 */           this.c--; continue;
/*  786 */         }  if (key[this.pos] != '\000') {
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
/*  803 */       char[] key = Char2ByteOpenCustomHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  805 */         pos = (last = pos) + 1 & Char2ByteOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  807 */           if ((curr = key[pos]) == '\000') {
/*  808 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  811 */           int slot = HashCommon.mix(Char2ByteOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2ByteOpenCustomHashMap.this.mask;
/*  812 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  813 */             break;  pos = pos + 1 & Char2ByteOpenCustomHashMap.this.mask;
/*      */         } 
/*  815 */         if (pos < last) {
/*  816 */           if (this.wrapped == null) this.wrapped = new CharArrayList(2); 
/*  817 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  819 */         key[last] = curr;
/*  820 */         Char2ByteOpenCustomHashMap.this.value[last] = Char2ByteOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  825 */       if (this.last == -1) throw new IllegalStateException(); 
/*  826 */       if (this.last == Char2ByteOpenCustomHashMap.this.n)
/*  827 */       { Char2ByteOpenCustomHashMap.this.containsNullKey = false; }
/*  828 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  831 */       { Char2ByteOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  832 */         this.last = -1;
/*      */         return; }
/*      */       
/*  835 */       Char2ByteOpenCustomHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Char2ByteMap.Entry>> implements ObjectIterator<Char2ByteMap.Entry> { public Char2ByteOpenCustomHashMap.MapEntry next() {
/*  852 */       return this.entry = new Char2ByteOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Char2ByteOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Char2ByteMap.Entry> action, int index) {
/*  858 */       action.accept(this.entry = new Char2ByteOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  863 */       super.remove();
/*  864 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Char2ByteMap.Entry>> implements ObjectIterator<Char2ByteMap.Entry> {
/*      */     private FastEntryIterator() {
/*  869 */       this.entry = new Char2ByteOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Char2ByteOpenCustomHashMap.MapEntry entry;
/*      */     public Char2ByteOpenCustomHashMap.MapEntry next() {
/*  873 */       this.entry.index = nextEntry();
/*  874 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Char2ByteMap.Entry> action, int index) {
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
/*  892 */     int max = Char2ByteOpenCustomHashMap.this.n;
/*      */     
/*  894 */     int c = 0;
/*      */     
/*  896 */     boolean mustReturnNull = Char2ByteOpenCustomHashMap.this.containsNullKey;
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
/*  917 */         acceptOnIndex(action, Char2ByteOpenCustomHashMap.this.n);
/*  918 */         return true;
/*      */       } 
/*  920 */       char[] key = Char2ByteOpenCustomHashMap.this.key;
/*  921 */       while (this.pos < this.max) {
/*  922 */         if (key[this.pos] != '\000') {
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
/*  936 */         acceptOnIndex(action, Char2ByteOpenCustomHashMap.this.n);
/*      */       } 
/*  938 */       char[] key = Char2ByteOpenCustomHashMap.this.key;
/*  939 */       while (this.pos < this.max) {
/*  940 */         if (key[this.pos] != '\000') {
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
/*  951 */         return (Char2ByteOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  956 */       return Math.min((Char2ByteOpenCustomHashMap.this.size - this.c), (long)(Char2ByteOpenCustomHashMap.this.realSize() / Char2ByteOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  986 */       char[] key = Char2ByteOpenCustomHashMap.this.key;
/*  987 */       while (this.pos < this.max && n > 0L) {
/*  988 */         if (key[this.pos++] != '\000') {
/*  989 */           skipped++;
/*  990 */           n--;
/*      */         } 
/*      */       } 
/*  993 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Char2ByteMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Char2ByteMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Char2ByteMap.Entry> action, int index) {
/* 1014 */       action.accept(new Char2ByteOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1019 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2ByteMap.Entry> implements Char2ByteMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2ByteMap.Entry> iterator() {
/* 1026 */       return new Char2ByteOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Char2ByteMap.Entry> fastIterator() {
/* 1031 */       return new Char2ByteOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Char2ByteMap.Entry> spliterator() {
/* 1036 */       return new Char2ByteOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1043 */       if (!(o instanceof Map.Entry)) return false; 
/* 1044 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1045 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 1046 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1047 */       char k = ((Character)e.getKey()).charValue();
/* 1048 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1049 */       if (Char2ByteOpenCustomHashMap.this.strategy.equals(k, false)) return (Char2ByteOpenCustomHashMap.this.containsNullKey && Char2ByteOpenCustomHashMap.this.value[Char2ByteOpenCustomHashMap.this.n] == v);
/*      */       
/* 1051 */       char[] key = Char2ByteOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1054 */       if ((curr = key[pos = HashCommon.mix(Char2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ByteOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1055 */       if (Char2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) return (Char2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1058 */         if ((curr = key[pos = pos + 1 & Char2ByteOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1059 */         if (Char2ByteOpenCustomHashMap.this.strategy.equals(k, curr)) return (Char2ByteOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1066 */       if (!(o instanceof Map.Entry)) return false; 
/* 1067 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1068 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 1069 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1070 */       char k = ((Character)e.getKey()).charValue();
/* 1071 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1072 */       if (Char2ByteOpenCustomHashMap.this.strategy.equals(k, false)) {
/* 1073 */         if (Char2ByteOpenCustomHashMap.this.containsNullKey && Char2ByteOpenCustomHashMap.this.value[Char2ByteOpenCustomHashMap.this.n] == v) {
/* 1074 */           Char2ByteOpenCustomHashMap.this.removeNullEntry();
/* 1075 */           return true;
/*      */         } 
/* 1077 */         return false;
/*      */       } 
/*      */       
/* 1080 */       char[] key = Char2ByteOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1083 */       if ((curr = key[pos = HashCommon.mix(Char2ByteOpenCustomHashMap.this.strategy.hashCode(k)) & Char2ByteOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1084 */       if (Char2ByteOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1085 */         if (Char2ByteOpenCustomHashMap.this.value[pos] == v) {
/* 1086 */           Char2ByteOpenCustomHashMap.this.removeEntry(pos);
/* 1087 */           return true;
/*      */         } 
/* 1089 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1092 */         if ((curr = key[pos = pos + 1 & Char2ByteOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1093 */         if (Char2ByteOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1094 */           Char2ByteOpenCustomHashMap.this.value[pos] == v) {
/* 1095 */           Char2ByteOpenCustomHashMap.this.removeEntry(pos);
/* 1096 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1104 */       return Char2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1109 */       Char2ByteOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2ByteMap.Entry> consumer) {
/* 1115 */       if (Char2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(new Char2ByteOpenCustomHashMap.MapEntry(Char2ByteOpenCustomHashMap.this.n)); 
/* 1116 */       for (int pos = Char2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Char2ByteOpenCustomHashMap.this.key[pos] != '\000') consumer.accept(new Char2ByteOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2ByteMap.Entry> consumer) {
/* 1122 */       Char2ByteOpenCustomHashMap.MapEntry entry = new Char2ByteOpenCustomHashMap.MapEntry();
/* 1123 */       if (Char2ByteOpenCustomHashMap.this.containsNullKey) {
/* 1124 */         entry.index = Char2ByteOpenCustomHashMap.this.n;
/* 1125 */         consumer.accept(entry);
/*      */       } 
/* 1127 */       for (int pos = Char2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Char2ByteOpenCustomHashMap.this.key[pos] != '\000') {
/* 1128 */           entry.index = pos;
/* 1129 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Char2ByteMap.FastEntrySet char2ByteEntrySet() {
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
/*      */     extends MapIterator<CharConsumer>
/*      */     implements CharIterator
/*      */   {
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1158 */       action.accept(Char2ByteOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1163 */       return Char2ByteOpenCustomHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<CharConsumer, KeySpliterator> implements CharSpliterator {
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
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1184 */       action.accept(Char2ByteOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1189 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/* 1196 */       return new Char2ByteOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator spliterator() {
/* 1201 */       return new Char2ByteOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(CharConsumer consumer) {
/* 1207 */       if (Char2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(Char2ByteOpenCustomHashMap.this.key[Char2ByteOpenCustomHashMap.this.n]); 
/* 1208 */       for (int pos = Char2ByteOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1209 */         char k = Char2ByteOpenCustomHashMap.this.key[pos];
/* 1210 */         if (k != '\000') consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1216 */       return Char2ByteOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(char k) {
/* 1221 */       return Char2ByteOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(char k) {
/* 1226 */       int oldSize = Char2ByteOpenCustomHashMap.this.size;
/* 1227 */       Char2ByteOpenCustomHashMap.this.remove(k);
/* 1228 */       return (Char2ByteOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1233 */       Char2ByteOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
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
/* 1261 */       action.accept(Char2ByteOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1266 */       return Char2ByteOpenCustomHashMap.this.value[nextEntry()];
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
/* 1287 */       action.accept(Char2ByteOpenCustomHashMap.this.value[index]);
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
/* 1301 */             return new Char2ByteOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ByteSpliterator spliterator() {
/* 1306 */             return new Char2ByteOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ByteConsumer consumer) {
/* 1312 */             if (Char2ByteOpenCustomHashMap.this.containsNullKey) consumer.accept(Char2ByteOpenCustomHashMap.this.value[Char2ByteOpenCustomHashMap.this.n]); 
/* 1313 */             for (int pos = Char2ByteOpenCustomHashMap.this.n; pos-- != 0;) { if (Char2ByteOpenCustomHashMap.this.key[pos] != '\000') consumer.accept(Char2ByteOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1318 */             return Char2ByteOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(byte v) {
/* 1323 */             return Char2ByteOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1328 */             Char2ByteOpenCustomHashMap.this.clear();
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
/* 1392 */     char[] key = this.key;
/* 1393 */     byte[] value = this.value;
/* 1394 */     int mask = newN - 1;
/* 1395 */     char[] newKey = new char[newN + 1];
/* 1396 */     byte[] newValue = new byte[newN + 1];
/* 1397 */     int i = this.n;
/* 1398 */     for (int j = realSize(); j-- != 0; ) {
/* 1399 */       while (key[--i] == '\000'); int pos;
/* 1400 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != '\000') while (newKey[pos = pos + 1 & mask] != '\000'); 
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
/*      */   public Char2ByteOpenCustomHashMap clone() {
/*      */     Char2ByteOpenCustomHashMap c;
/*      */     try {
/* 1426 */       c = (Char2ByteOpenCustomHashMap)super.clone();
/* 1427 */     } catch (CloneNotSupportedException cantHappen) {
/* 1428 */       throw new InternalError();
/*      */     } 
/* 1430 */     c.keys = null;
/* 1431 */     c.values = null;
/* 1432 */     c.entries = null;
/* 1433 */     c.containsNullKey = this.containsNullKey;
/* 1434 */     c.key = (char[])this.key.clone();
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
/* 1453 */       for (; this.key[i] == '\000'; i++);
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
/* 1465 */     char[] key = this.key;
/* 1466 */     byte[] value = this.value;
/* 1467 */     EntryIterator i = new EntryIterator();
/* 1468 */     s.defaultWriteObject();
/* 1469 */     for (int j = this.size; j-- != 0; ) {
/* 1470 */       int e = i.nextEntry();
/* 1471 */       s.writeChar(key[e]);
/* 1472 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1477 */     s.defaultReadObject();
/* 1478 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1479 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1480 */     this.mask = this.n - 1;
/* 1481 */     char[] key = this.key = new char[this.n + 1];
/* 1482 */     byte[] value = this.value = new byte[this.n + 1];
/*      */ 
/*      */     
/* 1485 */     for (int i = this.size; i-- != 0; ) {
/* 1486 */       int pos; char k = s.readChar();
/* 1487 */       byte v = s.readByte();
/* 1488 */       if (this.strategy.equals(k, false)) {
/* 1489 */         pos = this.n;
/* 1490 */         this.containsNullKey = true;
/*      */       } else {
/* 1492 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1493 */         for (; key[pos] != '\000'; pos = pos + 1 & this.mask);
/*      */       } 
/* 1495 */       key[pos] = k;
/* 1496 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ByteOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */