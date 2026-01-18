/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ public class Char2CharOpenCustomHashMap
/*      */   extends AbstractChar2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected CharHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2CharMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Char2CharOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
/*   98 */     this.strategy = strategy;
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new char[this.n + 1];
/*  106 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
/*  116 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharOpenCustomHashMap(CharHash.Strategy strategy) {
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
/*      */   public Char2CharOpenCustomHashMap(Map<? extends Character, ? extends Character> m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2CharOpenCustomHashMap(Map<? extends Character, ? extends Character> m, CharHash.Strategy strategy) {
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
/*      */   public Char2CharOpenCustomHashMap(Char2CharMap m, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2CharOpenCustomHashMap(Char2CharMap m, CharHash.Strategy strategy) {
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
/*      */   public Char2CharOpenCustomHashMap(char[] k, char[] v, float f, CharHash.Strategy strategy) {
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
/*      */   public Char2CharOpenCustomHashMap(char[] k, char[] v, CharHash.Strategy strategy) {
/*  199 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharHash.Strategy strategy() {
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
/*      */   private char removeEntry(int pos) {
/*  232 */     char oldValue = this.value[pos];
/*  233 */     this.size--;
/*  234 */     shiftKeys(pos);
/*  235 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  236 */     return oldValue;
/*      */   }
/*      */   
/*      */   private char removeNullEntry() {
/*  240 */     this.containsNullKey = false;
/*  241 */     char oldValue = this.value[this.n];
/*  242 */     this.size--;
/*  243 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  244 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends Character> m) {
/*  249 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  250 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  252 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  256 */     if (this.strategy.equals(k, false)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  258 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  261 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return -(pos + 1); 
/*  262 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  265 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return -(pos + 1); 
/*  266 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, char k, char v) {
/*  271 */     if (pos == this.n) this.containsNullKey = true; 
/*  272 */     this.key[pos] = k;
/*  273 */     this.value[pos] = v;
/*  274 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(char k, char v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     char oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
/*      */   }
/*      */   
/*      */   private char addToValue(int pos, char incr) {
/*  291 */     char oldValue = this.value[pos];
/*  292 */     this.value[pos] = (char)(oldValue + incr);
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
/*      */   public char addTo(char k, char incr) {
/*      */     int pos;
/*  311 */     if (this.strategy.equals(k, false)) {
/*  312 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  313 */       pos = this.n;
/*  314 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  317 */       char[] key = this.key;
/*      */       char curr;
/*  319 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*  320 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  321 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  324 */     }  this.key[pos] = k;
/*  325 */     this.value[pos] = (char)(this.defRetValue + incr);
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
/*  341 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == '\000') {
/*  346 */           key[last] = Character.MIN_VALUE;
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
/*      */   public char remove(char k) {
/*  361 */     if (this.strategy.equals(k, false)) {
/*  362 */       if (this.containsNullKey) return removeNullEntry(); 
/*  363 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  366 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return this.defRetValue; 
/*  370 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return this.defRetValue; 
/*  373 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char get(char k) {
/*  380 */     if (this.strategy.equals(k, false)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  382 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  385 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return this.defRetValue; 
/*  386 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(char k) {
/*  397 */     if (this.strategy.equals(k, false)) return this.containsNullKey;
/*      */     
/*  399 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return false; 
/*  403 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  406 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return false; 
/*  407 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  413 */     char[] value = this.value;
/*  414 */     char[] key = this.key;
/*  415 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  416 */     for (int i = this.n; i-- != 0;) { if (key[i] != '\000' && value[i] == v) return true;  }
/*  417 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(char k, char defaultValue) {
/*  424 */     if (this.strategy.equals(k, false)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  426 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return defaultValue; 
/*  430 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  433 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return defaultValue; 
/*  434 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char putIfAbsent(char k, char v) {
/*  441 */     int pos = find(k);
/*  442 */     if (pos >= 0) return this.value[pos]; 
/*  443 */     insert(-pos - 1, k, v);
/*  444 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, char v) {
/*  451 */     if (this.strategy.equals(k, false)) {
/*  452 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  453 */         removeNullEntry();
/*  454 */         return true;
/*      */       } 
/*  456 */       return false;
/*      */     } 
/*      */     
/*  459 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  462 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000') return false; 
/*  463 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  464 */       removeEntry(pos);
/*  465 */       return true;
/*      */     } 
/*      */     while (true) {
/*  468 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return false; 
/*  469 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  470 */         removeEntry(pos);
/*  471 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(char k, char oldValue, char v) {
/*  479 */     int pos = find(k);
/*  480 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  481 */     this.value[pos] = v;
/*  482 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char replace(char k, char v) {
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0) return this.defRetValue; 
/*  490 */     char oldValue = this.value[pos];
/*  491 */     this.value[pos] = v;
/*  492 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(char k, IntUnaryOperator mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  502 */     insert(-pos - 1, k, newValue);
/*  503 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(char key, Char2CharFunction mappingFunction) {
/*  509 */     Objects.requireNonNull(mappingFunction);
/*  510 */     int pos = find(key);
/*  511 */     if (pos >= 0) return this.value[pos]; 
/*  512 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  513 */     char newValue = mappingFunction.get(key);
/*  514 */     insert(-pos - 1, key, newValue);
/*  515 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(char k, IntFunction<? extends Character> mappingFunction) {
/*  521 */     Objects.requireNonNull(mappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos >= 0) return this.value[pos]; 
/*  524 */     Character newValue = mappingFunction.apply(k);
/*  525 */     if (newValue == null) return this.defRetValue; 
/*  526 */     char v = newValue.charValue();
/*  527 */     insert(-pos - 1, k, v);
/*  528 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(char k, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0) return this.defRetValue; 
/*  537 */     Character newValue = remappingFunction.apply(Character.valueOf(k), Character.valueOf(this.value[pos]));
/*  538 */     if (newValue == null) {
/*  539 */       if (this.strategy.equals(k, false)) { removeNullEntry(); }
/*  540 */       else { removeEntry(pos); }
/*  541 */        return this.defRetValue;
/*      */     } 
/*  543 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(char k, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  549 */     Objects.requireNonNull(remappingFunction);
/*  550 */     int pos = find(k);
/*  551 */     Character newValue = remappingFunction.apply(Character.valueOf(k), (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  552 */     if (newValue == null) {
/*  553 */       if (pos >= 0)
/*  554 */         if (this.strategy.equals(k, false)) { removeNullEntry(); }
/*  555 */         else { removeEntry(pos); }
/*      */          
/*  557 */       return this.defRetValue;
/*      */     } 
/*  559 */     char newVal = newValue.charValue();
/*  560 */     if (pos < 0) {
/*  561 */       insert(-pos - 1, k, newVal);
/*  562 */       return newVal;
/*      */     } 
/*  564 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(char k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  570 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  572 */     int pos = find(k);
/*  573 */     if (pos < 0) {
/*  574 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  575 */       else { this.value[pos] = v; }
/*  576 */        return v;
/*      */     } 
/*  578 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  579 */     if (newValue == null) {
/*  580 */       if (this.strategy.equals(k, false)) { removeNullEntry(); }
/*  581 */       else { removeEntry(pos); }
/*  582 */        return this.defRetValue;
/*      */     } 
/*  584 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  598 */     Arrays.fill(this.key, false);
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
/*      */     implements Char2CharMap.Entry, Map.Entry<Character, Character>, CharCharPair
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
/*      */     public char getCharKey() {
/*  629 */       return Char2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char leftChar() {
/*  634 */       return Char2CharOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char getCharValue() {
/*  639 */       return Char2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char rightChar() {
/*  644 */       return Char2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char setValue(char v) {
/*  649 */       char oldValue = Char2CharOpenCustomHashMap.this.value[this.index];
/*  650 */       Char2CharOpenCustomHashMap.this.value[this.index] = v;
/*  651 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharCharPair right(char v) {
/*  656 */       Char2CharOpenCustomHashMap.this.value[this.index] = v;
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
/*      */     public Character getKey() {
/*  668 */       return Character.valueOf(Char2CharOpenCustomHashMap.this.key[this.index]);
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
/*  679 */       return Character.valueOf(Char2CharOpenCustomHashMap.this.value[this.index]);
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
/*  690 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  696 */       if (!(o instanceof Map.Entry)) return false; 
/*  697 */       Map.Entry<Character, Character> e = (Map.Entry<Character, Character>)o;
/*  698 */       return (Char2CharOpenCustomHashMap.this.strategy.equals(Char2CharOpenCustomHashMap.this.key[this.index], ((Character)e.getKey()).charValue()) && Char2CharOpenCustomHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  703 */       return Char2CharOpenCustomHashMap.this.strategy.hashCode(Char2CharOpenCustomHashMap.this.key[this.index]) ^ Char2CharOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  708 */       return Char2CharOpenCustomHashMap.this.key[this.index] + "=>" + Char2CharOpenCustomHashMap.this.value[this.index];
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
/*  719 */     int pos = Char2CharOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     int last = -1;
/*      */     
/*  727 */     int c = Char2CharOpenCustomHashMap.this.size;
/*      */     
/*  729 */     boolean mustReturnNullKey = Char2CharOpenCustomHashMap.this.containsNullKey;
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
/*  740 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  744 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  745 */       this.c--;
/*  746 */       if (this.mustReturnNullKey) {
/*  747 */         this.mustReturnNullKey = false;
/*  748 */         return this.last = Char2CharOpenCustomHashMap.this.n;
/*      */       } 
/*  750 */       char[] key = Char2CharOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  752 */         if (--this.pos < 0) {
/*      */           
/*  754 */           this.last = Integer.MIN_VALUE;
/*  755 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  756 */           int p = HashCommon.mix(Char2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Char2CharOpenCustomHashMap.this.mask;
/*  757 */           for (; !Char2CharOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Char2CharOpenCustomHashMap.this.mask);
/*  758 */           return p;
/*      */         } 
/*  760 */         if (key[this.pos] != '\000') return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  765 */       if (this.mustReturnNullKey) {
/*  766 */         this.mustReturnNullKey = false;
/*  767 */         acceptOnIndex(action, this.last = Char2CharOpenCustomHashMap.this.n);
/*  768 */         this.c--;
/*      */       } 
/*  770 */       char[] key = Char2CharOpenCustomHashMap.this.key;
/*  771 */       while (this.c != 0) {
/*  772 */         if (--this.pos < 0) {
/*      */           
/*  774 */           this.last = Integer.MIN_VALUE;
/*  775 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  776 */           int p = HashCommon.mix(Char2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Char2CharOpenCustomHashMap.this.mask;
/*  777 */           for (; !Char2CharOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Char2CharOpenCustomHashMap.this.mask);
/*  778 */           acceptOnIndex(action, p);
/*  779 */           this.c--; continue;
/*  780 */         }  if (key[this.pos] != '\000') {
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
/*  797 */       char[] key = Char2CharOpenCustomHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  799 */         pos = (last = pos) + 1 & Char2CharOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  801 */           if ((curr = key[pos]) == '\000') {
/*  802 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  805 */           int slot = HashCommon.mix(Char2CharOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2CharOpenCustomHashMap.this.mask;
/*  806 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  807 */             break;  pos = pos + 1 & Char2CharOpenCustomHashMap.this.mask;
/*      */         } 
/*  809 */         if (pos < last) {
/*  810 */           if (this.wrapped == null) this.wrapped = new CharArrayList(2); 
/*  811 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  813 */         key[last] = curr;
/*  814 */         Char2CharOpenCustomHashMap.this.value[last] = Char2CharOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  819 */       if (this.last == -1) throw new IllegalStateException(); 
/*  820 */       if (this.last == Char2CharOpenCustomHashMap.this.n)
/*  821 */       { Char2CharOpenCustomHashMap.this.containsNullKey = false; }
/*  822 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  825 */       { Char2CharOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  826 */         this.last = -1;
/*      */         return; }
/*      */       
/*  829 */       Char2CharOpenCustomHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Char2CharMap.Entry>> implements ObjectIterator<Char2CharMap.Entry> { public Char2CharOpenCustomHashMap.MapEntry next() {
/*  846 */       return this.entry = new Char2CharOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Char2CharOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Char2CharMap.Entry> action, int index) {
/*  852 */       action.accept(this.entry = new Char2CharOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Char2CharMap.Entry>> implements ObjectIterator<Char2CharMap.Entry> {
/*      */     private FastEntryIterator() {
/*  863 */       this.entry = new Char2CharOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Char2CharOpenCustomHashMap.MapEntry entry;
/*      */     public Char2CharOpenCustomHashMap.MapEntry next() {
/*  867 */       this.entry.index = nextEntry();
/*  868 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Char2CharMap.Entry> action, int index) {
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
/*  886 */     int max = Char2CharOpenCustomHashMap.this.n;
/*      */     
/*  888 */     int c = 0;
/*      */     
/*  890 */     boolean mustReturnNull = Char2CharOpenCustomHashMap.this.containsNullKey;
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
/*  911 */         acceptOnIndex(action, Char2CharOpenCustomHashMap.this.n);
/*  912 */         return true;
/*      */       } 
/*  914 */       char[] key = Char2CharOpenCustomHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (key[this.pos] != '\000') {
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
/*  930 */         acceptOnIndex(action, Char2CharOpenCustomHashMap.this.n);
/*      */       } 
/*  932 */       char[] key = Char2CharOpenCustomHashMap.this.key;
/*  933 */       while (this.pos < this.max) {
/*  934 */         if (key[this.pos] != '\000') {
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
/*  945 */         return (Char2CharOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  950 */       return Math.min((Char2CharOpenCustomHashMap.this.size - this.c), (long)(Char2CharOpenCustomHashMap.this.realSize() / Char2CharOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  980 */       char[] key = Char2CharOpenCustomHashMap.this.key;
/*  981 */       while (this.pos < this.max && n > 0L) {
/*  982 */         if (key[this.pos++] != '\000') {
/*  983 */           skipped++;
/*  984 */           n--;
/*      */         } 
/*      */       } 
/*  987 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Char2CharMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Char2CharMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Char2CharMap.Entry> action, int index) {
/* 1008 */       action.accept(new Char2CharOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1013 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2CharMap.Entry> implements Char2CharMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2CharMap.Entry> iterator() {
/* 1020 */       return new Char2CharOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Char2CharMap.Entry> fastIterator() {
/* 1025 */       return new Char2CharOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Char2CharMap.Entry> spliterator() {
/* 1030 */       return new Char2CharOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1041 */       char k = ((Character)e.getKey()).charValue();
/* 1042 */       char v = ((Character)e.getValue()).charValue();
/* 1043 */       if (Char2CharOpenCustomHashMap.this.strategy.equals(k, false)) return (Char2CharOpenCustomHashMap.this.containsNullKey && Char2CharOpenCustomHashMap.this.value[Char2CharOpenCustomHashMap.this.n] == v);
/*      */       
/* 1045 */       char[] key = Char2CharOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1048 */       if ((curr = key[pos = HashCommon.mix(Char2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Char2CharOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1049 */       if (Char2CharOpenCustomHashMap.this.strategy.equals(k, curr)) return (Char2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1052 */         if ((curr = key[pos = pos + 1 & Char2CharOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1053 */         if (Char2CharOpenCustomHashMap.this.strategy.equals(k, curr)) return (Char2CharOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1060 */       if (!(o instanceof Map.Entry)) return false; 
/* 1061 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1062 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 1063 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1064 */       char k = ((Character)e.getKey()).charValue();
/* 1065 */       char v = ((Character)e.getValue()).charValue();
/* 1066 */       if (Char2CharOpenCustomHashMap.this.strategy.equals(k, false)) {
/* 1067 */         if (Char2CharOpenCustomHashMap.this.containsNullKey && Char2CharOpenCustomHashMap.this.value[Char2CharOpenCustomHashMap.this.n] == v) {
/* 1068 */           Char2CharOpenCustomHashMap.this.removeNullEntry();
/* 1069 */           return true;
/*      */         } 
/* 1071 */         return false;
/*      */       } 
/*      */       
/* 1074 */       char[] key = Char2CharOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1077 */       if ((curr = key[pos = HashCommon.mix(Char2CharOpenCustomHashMap.this.strategy.hashCode(k)) & Char2CharOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1078 */       if (Char2CharOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1079 */         if (Char2CharOpenCustomHashMap.this.value[pos] == v) {
/* 1080 */           Char2CharOpenCustomHashMap.this.removeEntry(pos);
/* 1081 */           return true;
/*      */         } 
/* 1083 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1086 */         if ((curr = key[pos = pos + 1 & Char2CharOpenCustomHashMap.this.mask]) == '\000') return false; 
/* 1087 */         if (Char2CharOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1088 */           Char2CharOpenCustomHashMap.this.value[pos] == v) {
/* 1089 */           Char2CharOpenCustomHashMap.this.removeEntry(pos);
/* 1090 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1098 */       return Char2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1103 */       Char2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2CharMap.Entry> consumer) {
/* 1109 */       if (Char2CharOpenCustomHashMap.this.containsNullKey) consumer.accept(new Char2CharOpenCustomHashMap.MapEntry(Char2CharOpenCustomHashMap.this.n)); 
/* 1110 */       for (int pos = Char2CharOpenCustomHashMap.this.n; pos-- != 0;) { if (Char2CharOpenCustomHashMap.this.key[pos] != '\000') consumer.accept(new Char2CharOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2CharMap.Entry> consumer) {
/* 1116 */       Char2CharOpenCustomHashMap.MapEntry entry = new Char2CharOpenCustomHashMap.MapEntry();
/* 1117 */       if (Char2CharOpenCustomHashMap.this.containsNullKey) {
/* 1118 */         entry.index = Char2CharOpenCustomHashMap.this.n;
/* 1119 */         consumer.accept(entry);
/*      */       } 
/* 1121 */       for (int pos = Char2CharOpenCustomHashMap.this.n; pos-- != 0;) { if (Char2CharOpenCustomHashMap.this.key[pos] != '\000') {
/* 1122 */           entry.index = pos;
/* 1123 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Char2CharMap.FastEntrySet char2CharEntrySet() {
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
/*      */     extends MapIterator<CharConsumer>
/*      */     implements CharIterator
/*      */   {
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1152 */       action.accept(Char2CharOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1157 */       return Char2CharOpenCustomHashMap.this.key[nextEntry()];
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
/* 1168 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1173 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1178 */       action.accept(Char2CharOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1183 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/* 1190 */       return new Char2CharOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator spliterator() {
/* 1195 */       return new Char2CharOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(CharConsumer consumer) {
/* 1201 */       if (Char2CharOpenCustomHashMap.this.containsNullKey) consumer.accept(Char2CharOpenCustomHashMap.this.key[Char2CharOpenCustomHashMap.this.n]); 
/* 1202 */       for (int pos = Char2CharOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1203 */         char k = Char2CharOpenCustomHashMap.this.key[pos];
/* 1204 */         if (k != '\000') consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1210 */       return Char2CharOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(char k) {
/* 1215 */       return Char2CharOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(char k) {
/* 1220 */       int oldSize = Char2CharOpenCustomHashMap.this.size;
/* 1221 */       Char2CharOpenCustomHashMap.this.remove(k);
/* 1222 */       return (Char2CharOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1227 */       Char2CharOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
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
/*      */     extends MapIterator<CharConsumer>
/*      */     implements CharIterator
/*      */   {
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1255 */       action.accept(Char2CharOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1260 */       return Char2CharOpenCustomHashMap.this.value[nextEntry()];
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
/* 1271 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1276 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1281 */       action.accept(Char2CharOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1286 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CharCollection values() {
/* 1292 */     if (this.values == null) this.values = new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1295 */             return new Char2CharOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public CharSpliterator spliterator() {
/* 1300 */             return new Char2CharOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(CharConsumer consumer) {
/* 1306 */             if (Char2CharOpenCustomHashMap.this.containsNullKey) consumer.accept(Char2CharOpenCustomHashMap.this.value[Char2CharOpenCustomHashMap.this.n]); 
/* 1307 */             for (int pos = Char2CharOpenCustomHashMap.this.n; pos-- != 0;) { if (Char2CharOpenCustomHashMap.this.key[pos] != '\000') consumer.accept(Char2CharOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1312 */             return Char2CharOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(char v) {
/* 1317 */             return Char2CharOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1322 */             Char2CharOpenCustomHashMap.this.clear();
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
/* 1386 */     char[] key = this.key;
/* 1387 */     char[] value = this.value;
/* 1388 */     int mask = newN - 1;
/* 1389 */     char[] newKey = new char[newN + 1];
/* 1390 */     char[] newValue = new char[newN + 1];
/* 1391 */     int i = this.n;
/* 1392 */     for (int j = realSize(); j-- != 0; ) {
/* 1393 */       while (key[--i] == '\000'); int pos;
/* 1394 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != '\000') while (newKey[pos = pos + 1 & mask] != '\000'); 
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
/*      */   public Char2CharOpenCustomHashMap clone() {
/*      */     Char2CharOpenCustomHashMap c;
/*      */     try {
/* 1420 */       c = (Char2CharOpenCustomHashMap)super.clone();
/* 1421 */     } catch (CloneNotSupportedException cantHappen) {
/* 1422 */       throw new InternalError();
/*      */     } 
/* 1424 */     c.keys = null;
/* 1425 */     c.values = null;
/* 1426 */     c.entries = null;
/* 1427 */     c.containsNullKey = this.containsNullKey;
/* 1428 */     c.key = (char[])this.key.clone();
/* 1429 */     c.value = (char[])this.value.clone();
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
/* 1447 */       for (; this.key[i] == '\000'; i++);
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
/* 1459 */     char[] key = this.key;
/* 1460 */     char[] value = this.value;
/* 1461 */     EntryIterator i = new EntryIterator();
/* 1462 */     s.defaultWriteObject();
/* 1463 */     for (int j = this.size; j-- != 0; ) {
/* 1464 */       int e = i.nextEntry();
/* 1465 */       s.writeChar(key[e]);
/* 1466 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1471 */     s.defaultReadObject();
/* 1472 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1473 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1474 */     this.mask = this.n - 1;
/* 1475 */     char[] key = this.key = new char[this.n + 1];
/* 1476 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1479 */     for (int i = this.size; i-- != 0; ) {
/* 1480 */       int pos; char k = s.readChar();
/* 1481 */       char v = s.readChar();
/* 1482 */       if (this.strategy.equals(k, false)) {
/* 1483 */         pos = this.n;
/* 1484 */         this.containsNullKey = true;
/*      */       } else {
/* 1486 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1487 */         for (; key[pos] != '\000'; pos = pos + 1 & this.mask);
/*      */       } 
/* 1489 */       key[pos] = k;
/* 1490 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2CharOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */