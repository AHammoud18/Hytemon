/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharConsumer;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
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
/*      */ import java.util.function.IntConsumer;
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
/*      */ public class Int2CharOpenHashMap
/*      */   extends AbstractInt2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2CharMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Int2CharOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  101 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  102 */     this.f = f;
/*  103 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  104 */     this.mask = this.n - 1;
/*  105 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  106 */     this.key = new int[this.n + 1];
/*  107 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharOpenHashMap(int expected) {
/*  116 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharOpenHashMap() {
/*  124 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharOpenHashMap(Map<? extends Integer, ? extends Character> m, float f) {
/*  134 */     this(m.size(), f);
/*  135 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharOpenHashMap(Map<? extends Integer, ? extends Character> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharOpenHashMap(Int2CharMap m, float f) {
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
/*      */   public Int2CharOpenHashMap(Int2CharMap m) {
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
/*      */   public Int2CharOpenHashMap(int[] k, char[] v, float f) {
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
/*      */   public Int2CharOpenHashMap(int[] k, char[] v) {
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
/*      */   private char removeEntry(int pos) {
/*  215 */     char oldValue = this.value[pos];
/*  216 */     this.size--;
/*  217 */     shiftKeys(pos);
/*  218 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  219 */     return oldValue;
/*      */   }
/*      */   
/*      */   private char removeNullEntry() {
/*  223 */     this.containsNullKey = false;
/*  224 */     char oldValue = this.value[this.n];
/*  225 */     this.size--;
/*  226 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Character> m) {
/*  232 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  233 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  235 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  239 */     if (k == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  241 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  244 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return -(pos + 1); 
/*  245 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  248 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  249 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, int k, char v) {
/*  254 */     if (pos == this.n) this.containsNullKey = true; 
/*  255 */     this.key[pos] = k;
/*  256 */     this.value[pos] = v;
/*  257 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(int k, char v) {
/*  263 */     int pos = find(k);
/*  264 */     if (pos < 0) {
/*  265 */       insert(-pos - 1, k, v);
/*  266 */       return this.defRetValue;
/*      */     } 
/*  268 */     char oldValue = this.value[pos];
/*  269 */     this.value[pos] = v;
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   private char addToValue(int pos, char incr) {
/*  274 */     char oldValue = this.value[pos];
/*  275 */     this.value[pos] = (char)(oldValue + incr);
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
/*      */   public char addTo(int k, char incr) {
/*      */     int pos;
/*  294 */     if (k == 0) {
/*  295 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  296 */       pos = this.n;
/*  297 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  300 */       int[] key = this.key;
/*      */       int curr;
/*  302 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  303 */         if (curr == k) return addToValue(pos, incr); 
/*  304 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  307 */     }  this.key[pos] = k;
/*  308 */     this.value[pos] = (char)(this.defRetValue + incr);
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
/*  324 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  326 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  328 */         if ((curr = key[pos]) == 0) {
/*  329 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  332 */         int slot = HashCommon.mix(curr) & this.mask;
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
/*      */   public char remove(int k) {
/*  344 */     if (k == 0) {
/*  345 */       if (this.containsNullKey) return removeNullEntry(); 
/*  346 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  349 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  352 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  353 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  355 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  356 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char get(int k) {
/*  363 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  365 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  368 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  369 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  373 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(int k) {
/*  380 */     if (k == 0) return this.containsNullKey;
/*      */     
/*  382 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  385 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  386 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  390 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  396 */     char[] value = this.value;
/*  397 */     int[] key = this.key;
/*  398 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  399 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  400 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(int k, char defaultValue) {
/*  407 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  409 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  412 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return defaultValue; 
/*  413 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  416 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  417 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char putIfAbsent(int k, char v) {
/*  424 */     int pos = find(k);
/*  425 */     if (pos >= 0) return this.value[pos]; 
/*  426 */     insert(-pos - 1, k, v);
/*  427 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, char v) {
/*  434 */     if (k == 0) {
/*  435 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  436 */         removeNullEntry();
/*  437 */         return true;
/*      */       } 
/*  439 */       return false;
/*      */     } 
/*      */     
/*  442 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  445 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  446 */     if (k == curr && v == this.value[pos]) {
/*  447 */       removeEntry(pos);
/*  448 */       return true;
/*      */     } 
/*      */     while (true) {
/*  451 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  452 */       if (k == curr && v == this.value[pos]) {
/*  453 */         removeEntry(pos);
/*  454 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(int k, char oldValue, char v) {
/*  462 */     int pos = find(k);
/*  463 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  464 */     this.value[pos] = v;
/*  465 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char replace(int k, char v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos < 0) return this.defRetValue; 
/*  473 */     char oldValue = this.value[pos];
/*  474 */     this.value[pos] = v;
/*  475 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
/*  481 */     Objects.requireNonNull(mappingFunction);
/*  482 */     int pos = find(k);
/*  483 */     if (pos >= 0) return this.value[pos]; 
/*  484 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  485 */     insert(-pos - 1, k, newValue);
/*  486 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(int key, Int2CharFunction mappingFunction) {
/*  492 */     Objects.requireNonNull(mappingFunction);
/*  493 */     int pos = find(key);
/*  494 */     if (pos >= 0) return this.value[pos]; 
/*  495 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  496 */     char newValue = mappingFunction.get(key);
/*  497 */     insert(-pos - 1, key, newValue);
/*  498 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(int k, IntFunction<? extends Character> mappingFunction) {
/*  504 */     Objects.requireNonNull(mappingFunction);
/*  505 */     int pos = find(k);
/*  506 */     if (pos >= 0) return this.value[pos]; 
/*  507 */     Character newValue = mappingFunction.apply(k);
/*  508 */     if (newValue == null) return this.defRetValue; 
/*  509 */     char v = newValue.charValue();
/*  510 */     insert(-pos - 1, k, v);
/*  511 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(int k, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
/*  517 */     Objects.requireNonNull(remappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     if (pos < 0) return this.defRetValue; 
/*  520 */     Character newValue = remappingFunction.apply(Integer.valueOf(k), Character.valueOf(this.value[pos]));
/*  521 */     if (newValue == null) {
/*  522 */       if (k == 0) { removeNullEntry(); }
/*  523 */       else { removeEntry(pos); }
/*  524 */        return this.defRetValue;
/*      */     } 
/*  526 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(int k, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
/*  532 */     Objects.requireNonNull(remappingFunction);
/*  533 */     int pos = find(k);
/*  534 */     Character newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  535 */     if (newValue == null) {
/*  536 */       if (pos >= 0)
/*  537 */         if (k == 0) { removeNullEntry(); }
/*  538 */         else { removeEntry(pos); }
/*      */          
/*  540 */       return this.defRetValue;
/*      */     } 
/*  542 */     char newVal = newValue.charValue();
/*  543 */     if (pos < 0) {
/*  544 */       insert(-pos - 1, k, newVal);
/*  545 */       return newVal;
/*      */     } 
/*  547 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(int k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  553 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  555 */     int pos = find(k);
/*  556 */     if (pos < 0) {
/*  557 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  558 */       else { this.value[pos] = v; }
/*  559 */        return v;
/*      */     } 
/*  561 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  562 */     if (newValue == null) {
/*  563 */       if (k == 0) { removeNullEntry(); }
/*  564 */       else { removeEntry(pos); }
/*  565 */        return this.defRetValue;
/*      */     } 
/*  567 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  581 */     Arrays.fill(this.key, 0);
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
/*      */     implements Int2CharMap.Entry, Map.Entry<Integer, Character>, IntCharPair
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
/*      */     public int getIntKey() {
/*  612 */       return Int2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int leftInt() {
/*  617 */       return Int2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char getCharValue() {
/*  622 */       return Int2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char rightChar() {
/*  627 */       return Int2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char setValue(char v) {
/*  632 */       char oldValue = Int2CharOpenHashMap.this.value[this.index];
/*  633 */       Int2CharOpenHashMap.this.value[this.index] = v;
/*  634 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntCharPair right(char v) {
/*  639 */       Int2CharOpenHashMap.this.value[this.index] = v;
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
/*      */     public Integer getKey() {
/*  651 */       return Integer.valueOf(Int2CharOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  662 */       return Character.valueOf(Int2CharOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  673 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  679 */       if (!(o instanceof Map.Entry)) return false; 
/*  680 */       Map.Entry<Integer, Character> e = (Map.Entry<Integer, Character>)o;
/*  681 */       return (Int2CharOpenHashMap.this.key[this.index] == ((Integer)e.getKey()).intValue() && Int2CharOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  686 */       return Int2CharOpenHashMap.this.key[this.index] ^ Int2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  691 */       return Int2CharOpenHashMap.this.key[this.index] + "=>" + Int2CharOpenHashMap.this.value[this.index];
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
/*  702 */     int pos = Int2CharOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  708 */     int last = -1;
/*      */     
/*  710 */     int c = Int2CharOpenHashMap.this.size;
/*      */     
/*  712 */     boolean mustReturnNullKey = Int2CharOpenHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
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
/*  731 */         return this.last = Int2CharOpenHashMap.this.n;
/*      */       } 
/*  733 */       int[] key = Int2CharOpenHashMap.this.key;
/*      */       while (true) {
/*  735 */         if (--this.pos < 0) {
/*      */           
/*  737 */           this.last = Integer.MIN_VALUE;
/*  738 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  739 */           int p = HashCommon.mix(k) & Int2CharOpenHashMap.this.mask;
/*  740 */           for (; k != key[p]; p = p + 1 & Int2CharOpenHashMap.this.mask);
/*  741 */           return p;
/*      */         } 
/*  743 */         if (key[this.pos] != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  748 */       if (this.mustReturnNullKey) {
/*  749 */         this.mustReturnNullKey = false;
/*  750 */         acceptOnIndex(action, this.last = Int2CharOpenHashMap.this.n);
/*  751 */         this.c--;
/*      */       } 
/*  753 */       int[] key = Int2CharOpenHashMap.this.key;
/*  754 */       while (this.c != 0) {
/*  755 */         if (--this.pos < 0) {
/*      */           
/*  757 */           this.last = Integer.MIN_VALUE;
/*  758 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  759 */           int p = HashCommon.mix(k) & Int2CharOpenHashMap.this.mask;
/*  760 */           for (; k != key[p]; p = p + 1 & Int2CharOpenHashMap.this.mask);
/*  761 */           acceptOnIndex(action, p);
/*  762 */           this.c--; continue;
/*  763 */         }  if (key[this.pos] != 0) {
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
/*  780 */       int[] key = Int2CharOpenHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  782 */         pos = (last = pos) + 1 & Int2CharOpenHashMap.this.mask;
/*      */         while (true) {
/*  784 */           if ((curr = key[pos]) == 0) {
/*  785 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  788 */           int slot = HashCommon.mix(curr) & Int2CharOpenHashMap.this.mask;
/*  789 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  790 */             break;  pos = pos + 1 & Int2CharOpenHashMap.this.mask;
/*      */         } 
/*  792 */         if (pos < last) {
/*  793 */           if (this.wrapped == null) this.wrapped = new IntArrayList(2); 
/*  794 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  796 */         key[last] = curr;
/*  797 */         Int2CharOpenHashMap.this.value[last] = Int2CharOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  802 */       if (this.last == -1) throw new IllegalStateException(); 
/*  803 */       if (this.last == Int2CharOpenHashMap.this.n)
/*  804 */       { Int2CharOpenHashMap.this.containsNullKey = false; }
/*  805 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  808 */       { Int2CharOpenHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  809 */         this.last = -1;
/*      */         return; }
/*      */       
/*  812 */       Int2CharOpenHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Int2CharMap.Entry>> implements ObjectIterator<Int2CharMap.Entry> { public Int2CharOpenHashMap.MapEntry next() {
/*  829 */       return this.entry = new Int2CharOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Int2CharOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Int2CharMap.Entry> action, int index) {
/*  835 */       action.accept(this.entry = new Int2CharOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  840 */       super.remove();
/*  841 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Int2CharMap.Entry>> implements ObjectIterator<Int2CharMap.Entry> {
/*      */     private FastEntryIterator() {
/*  846 */       this.entry = new Int2CharOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Int2CharOpenHashMap.MapEntry entry;
/*      */     public Int2CharOpenHashMap.MapEntry next() {
/*  850 */       this.entry.index = nextEntry();
/*  851 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Int2CharMap.Entry> action, int index) {
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
/*  869 */     int max = Int2CharOpenHashMap.this.n;
/*      */     
/*  871 */     int c = 0;
/*      */     
/*  873 */     boolean mustReturnNull = Int2CharOpenHashMap.this.containsNullKey;
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
/*  894 */         acceptOnIndex(action, Int2CharOpenHashMap.this.n);
/*  895 */         return true;
/*      */       } 
/*  897 */       int[] key = Int2CharOpenHashMap.this.key;
/*  898 */       while (this.pos < this.max) {
/*  899 */         if (key[this.pos] != 0) {
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
/*  913 */         acceptOnIndex(action, Int2CharOpenHashMap.this.n);
/*      */       } 
/*  915 */       int[] key = Int2CharOpenHashMap.this.key;
/*  916 */       while (this.pos < this.max) {
/*  917 */         if (key[this.pos] != 0) {
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
/*  928 */         return (Int2CharOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  933 */       return Math.min((Int2CharOpenHashMap.this.size - this.c), (long)(Int2CharOpenHashMap.this.realSize() / Int2CharOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  963 */       int[] key = Int2CharOpenHashMap.this.key;
/*  964 */       while (this.pos < this.max && n > 0L) {
/*  965 */         if (key[this.pos++] != 0) {
/*  966 */           skipped++;
/*  967 */           n--;
/*      */         } 
/*      */       } 
/*  970 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Int2CharMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Int2CharMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Int2CharMap.Entry> action, int index) {
/*  991 */       action.accept(new Int2CharOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  996 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2CharMap.Entry> implements Int2CharMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2CharMap.Entry> iterator() {
/* 1003 */       return new Int2CharOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Int2CharMap.Entry> fastIterator() {
/* 1008 */       return new Int2CharOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Int2CharMap.Entry> spliterator() {
/* 1013 */       return new Int2CharOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1020 */       if (!(o instanceof Map.Entry)) return false; 
/* 1021 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1022 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 1023 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1024 */       int k = ((Integer)e.getKey()).intValue();
/* 1025 */       char v = ((Character)e.getValue()).charValue();
/* 1026 */       if (k == 0) return (Int2CharOpenHashMap.this.containsNullKey && Int2CharOpenHashMap.this.value[Int2CharOpenHashMap.this.n] == v);
/*      */       
/* 1028 */       int[] key = Int2CharOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1031 */       if ((curr = key[pos = HashCommon.mix(k) & Int2CharOpenHashMap.this.mask]) == 0) return false; 
/* 1032 */       if (k == curr) return (Int2CharOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1035 */         if ((curr = key[pos = pos + 1 & Int2CharOpenHashMap.this.mask]) == 0) return false; 
/* 1036 */         if (k == curr) return (Int2CharOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1043 */       if (!(o instanceof Map.Entry)) return false; 
/* 1044 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1045 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 1046 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1047 */       int k = ((Integer)e.getKey()).intValue();
/* 1048 */       char v = ((Character)e.getValue()).charValue();
/* 1049 */       if (k == 0) {
/* 1050 */         if (Int2CharOpenHashMap.this.containsNullKey && Int2CharOpenHashMap.this.value[Int2CharOpenHashMap.this.n] == v) {
/* 1051 */           Int2CharOpenHashMap.this.removeNullEntry();
/* 1052 */           return true;
/*      */         } 
/* 1054 */         return false;
/*      */       } 
/*      */       
/* 1057 */       int[] key = Int2CharOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1060 */       if ((curr = key[pos = HashCommon.mix(k) & Int2CharOpenHashMap.this.mask]) == 0) return false; 
/* 1061 */       if (curr == k) {
/* 1062 */         if (Int2CharOpenHashMap.this.value[pos] == v) {
/* 1063 */           Int2CharOpenHashMap.this.removeEntry(pos);
/* 1064 */           return true;
/*      */         } 
/* 1066 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1069 */         if ((curr = key[pos = pos + 1 & Int2CharOpenHashMap.this.mask]) == 0) return false; 
/* 1070 */         if (curr == k && 
/* 1071 */           Int2CharOpenHashMap.this.value[pos] == v) {
/* 1072 */           Int2CharOpenHashMap.this.removeEntry(pos);
/* 1073 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1081 */       return Int2CharOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1086 */       Int2CharOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2CharMap.Entry> consumer) {
/* 1092 */       if (Int2CharOpenHashMap.this.containsNullKey) consumer.accept(new Int2CharOpenHashMap.MapEntry(Int2CharOpenHashMap.this.n)); 
/* 1093 */       for (int pos = Int2CharOpenHashMap.this.n; pos-- != 0;) { if (Int2CharOpenHashMap.this.key[pos] != 0) consumer.accept(new Int2CharOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2CharMap.Entry> consumer) {
/* 1099 */       Int2CharOpenHashMap.MapEntry entry = new Int2CharOpenHashMap.MapEntry();
/* 1100 */       if (Int2CharOpenHashMap.this.containsNullKey) {
/* 1101 */         entry.index = Int2CharOpenHashMap.this.n;
/* 1102 */         consumer.accept(entry);
/*      */       } 
/* 1104 */       for (int pos = Int2CharOpenHashMap.this.n; pos-- != 0;) { if (Int2CharOpenHashMap.this.key[pos] != 0) {
/* 1105 */           entry.index = pos;
/* 1106 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Int2CharMap.FastEntrySet int2CharEntrySet() {
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
/*      */     extends MapIterator<IntConsumer>
/*      */     implements IntIterator
/*      */   {
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1135 */       action.accept(Int2CharOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1140 */       return Int2CharOpenHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<IntConsumer, KeySpliterator> implements IntSpliterator {
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
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1161 */       action.accept(Int2CharOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1166 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/* 1173 */       return new Int2CharOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator spliterator() {
/* 1178 */       return new Int2CharOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1184 */       if (Int2CharOpenHashMap.this.containsNullKey) consumer.accept(Int2CharOpenHashMap.this.key[Int2CharOpenHashMap.this.n]); 
/* 1185 */       for (int pos = Int2CharOpenHashMap.this.n; pos-- != 0; ) {
/* 1186 */         int k = Int2CharOpenHashMap.this.key[pos];
/* 1187 */         if (k != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1193 */       return Int2CharOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int k) {
/* 1198 */       return Int2CharOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int k) {
/* 1203 */       int oldSize = Int2CharOpenHashMap.this.size;
/* 1204 */       Int2CharOpenHashMap.this.remove(k);
/* 1205 */       return (Int2CharOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1210 */       Int2CharOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
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
/*      */     extends MapIterator<CharConsumer>
/*      */     implements CharIterator
/*      */   {
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1238 */       action.accept(Int2CharOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1243 */       return Int2CharOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<CharConsumer, ValueSpliterator> implements CharSpliterator {
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
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1264 */       action.accept(Int2CharOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1269 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CharCollection values() {
/* 1275 */     if (this.values == null) this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1278 */             return new Int2CharOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public CharSpliterator spliterator() {
/* 1283 */             return new Int2CharOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(CharConsumer consumer) {
/* 1289 */             if (Int2CharOpenHashMap.this.containsNullKey) consumer.accept(Int2CharOpenHashMap.this.value[Int2CharOpenHashMap.this.n]); 
/* 1290 */             for (int pos = Int2CharOpenHashMap.this.n; pos-- != 0;) { if (Int2CharOpenHashMap.this.key[pos] != 0) consumer.accept(Int2CharOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1295 */             return Int2CharOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(char v) {
/* 1300 */             return Int2CharOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1305 */             Int2CharOpenHashMap.this.clear();
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
/* 1369 */     int[] key = this.key;
/* 1370 */     char[] value = this.value;
/* 1371 */     int mask = newN - 1;
/* 1372 */     int[] newKey = new int[newN + 1];
/* 1373 */     char[] newValue = new char[newN + 1];
/* 1374 */     int i = this.n;
/* 1375 */     for (int j = realSize(); j-- != 0; ) {
/* 1376 */       while (key[--i] == 0); int pos;
/* 1377 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
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
/*      */   public Int2CharOpenHashMap clone() {
/*      */     Int2CharOpenHashMap c;
/*      */     try {
/* 1403 */       c = (Int2CharOpenHashMap)super.clone();
/* 1404 */     } catch (CloneNotSupportedException cantHappen) {
/* 1405 */       throw new InternalError();
/*      */     } 
/* 1407 */     c.keys = null;
/* 1408 */     c.values = null;
/* 1409 */     c.entries = null;
/* 1410 */     c.containsNullKey = this.containsNullKey;
/* 1411 */     c.key = (int[])this.key.clone();
/* 1412 */     c.value = (char[])this.value.clone();
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
/* 1429 */       for (; this.key[i] == 0; i++);
/* 1430 */       t = this.key[i];
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
/* 1441 */     int[] key = this.key;
/* 1442 */     char[] value = this.value;
/* 1443 */     EntryIterator i = new EntryIterator();
/* 1444 */     s.defaultWriteObject();
/* 1445 */     for (int j = this.size; j-- != 0; ) {
/* 1446 */       int e = i.nextEntry();
/* 1447 */       s.writeInt(key[e]);
/* 1448 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1453 */     s.defaultReadObject();
/* 1454 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1455 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1456 */     this.mask = this.n - 1;
/* 1457 */     int[] key = this.key = new int[this.n + 1];
/* 1458 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1461 */     for (int i = this.size; i-- != 0; ) {
/* 1462 */       int pos, k = s.readInt();
/* 1463 */       char v = s.readChar();
/* 1464 */       if (k == 0) {
/* 1465 */         pos = this.n;
/* 1466 */         this.containsNullKey = true;
/*      */       } else {
/* 1468 */         pos = HashCommon.mix(k) & this.mask;
/* 1469 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1471 */       key[pos] = k;
/* 1472 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2CharOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */