/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2ObjectOpenHashMap<V>
/*      */   extends AbstractChar2ObjectMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2ObjectMap.FastEntrySet<V> entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Char2ObjectOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   97 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   98 */     this.f = f;
/*   99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  100 */     this.mask = this.n - 1;
/*  101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  102 */     this.key = new char[this.n + 1];
/*  103 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ObjectOpenHashMap(int expected) {
/*  112 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ObjectOpenHashMap() {
/*  120 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ObjectOpenHashMap(Map<? extends Character, ? extends V> m, float f) {
/*  130 */     this(m.size(), f);
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ObjectOpenHashMap(Map<? extends Character, ? extends V> m) {
/*  140 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ObjectOpenHashMap(Char2ObjectMap<V> m, float f) {
/*  150 */     this(m.size(), f);
/*  151 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ObjectOpenHashMap(Char2ObjectMap<V> m) {
/*  161 */     this(m, 0.75F);
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
/*      */   public Char2ObjectOpenHashMap(char[] k, V[] v, float f) {
/*  173 */     this(k.length, f);
/*  174 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  175 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Char2ObjectOpenHashMap(char[] k, V[] v) {
/*  187 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  191 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  201 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  202 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  206 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  207 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private V removeEntry(int pos) {
/*  211 */     V oldValue = this.value[pos];
/*  212 */     this.value[pos] = null;
/*  213 */     this.size--;
/*  214 */     shiftKeys(pos);
/*  215 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  216 */     return oldValue;
/*      */   }
/*      */   
/*      */   private V removeNullEntry() {
/*  220 */     this.containsNullKey = false;
/*  221 */     V oldValue = this.value[this.n];
/*  222 */     this.value[this.n] = null;
/*  223 */     this.size--;
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  225 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends V> m) {
/*  230 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  231 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  233 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  237 */     if (k == '\000') return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  239 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  242 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000') return -(pos + 1); 
/*  243 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  246 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return -(pos + 1); 
/*  247 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, char k, V v) {
/*  252 */     if (pos == this.n) this.containsNullKey = true; 
/*  253 */     this.key[pos] = k;
/*  254 */     this.value[pos] = v;
/*  255 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(char k, V v) {
/*  261 */     int pos = find(k);
/*  262 */     if (pos < 0) {
/*  263 */       insert(-pos - 1, k, v);
/*  264 */       return this.defRetValue;
/*      */     } 
/*  266 */     V oldValue = this.value[pos];
/*  267 */     this.value[pos] = v;
/*  268 */     return oldValue;
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
/*  281 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  283 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  285 */         if ((curr = key[pos]) == '\000') {
/*  286 */           key[last] = Character.MIN_VALUE;
/*  287 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  290 */         int slot = HashCommon.mix(curr) & this.mask;
/*  291 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  292 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  294 */       key[last] = curr;
/*  295 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(char k) {
/*  302 */     if (k == '\000') {
/*  303 */       if (this.containsNullKey) return removeNullEntry(); 
/*  304 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  307 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  310 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000') return this.defRetValue; 
/*  311 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  313 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return this.defRetValue; 
/*  314 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(char k) {
/*  321 */     if (k == '\000') return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  323 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  326 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000') return this.defRetValue; 
/*  327 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  330 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return this.defRetValue; 
/*  331 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(char k) {
/*  338 */     if (k == '\000') return this.containsNullKey;
/*      */     
/*  340 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  343 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000') return false; 
/*  344 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  347 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return false; 
/*  348 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  354 */     V[] value = this.value;
/*  355 */     char[] key = this.key;
/*  356 */     if (this.containsNullKey && Objects.equals(value[this.n], v)) return true; 
/*  357 */     for (int i = this.n; i-- != 0;) { if (key[i] != '\000' && Objects.equals(value[i], v)) return true;  }
/*  358 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(char k, V defaultValue) {
/*  365 */     if (k == '\000') return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  367 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  370 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000') return defaultValue; 
/*  371 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  374 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return defaultValue; 
/*  375 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(char k, V v) {
/*  382 */     int pos = find(k);
/*  383 */     if (pos >= 0) return this.value[pos]; 
/*  384 */     insert(-pos - 1, k, v);
/*  385 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, Object v) {
/*  392 */     if (k == '\000') {
/*  393 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
/*  394 */         removeNullEntry();
/*  395 */         return true;
/*      */       } 
/*  397 */       return false;
/*      */     } 
/*      */     
/*  400 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  403 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000') return false; 
/*  404 */     if (k == curr && Objects.equals(v, this.value[pos])) {
/*  405 */       removeEntry(pos);
/*  406 */       return true;
/*      */     } 
/*      */     while (true) {
/*  409 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000') return false; 
/*  410 */       if (k == curr && Objects.equals(v, this.value[pos])) {
/*  411 */         removeEntry(pos);
/*  412 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(char k, V oldValue, V v) {
/*  420 */     int pos = find(k);
/*  421 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos])) return false; 
/*  422 */     this.value[pos] = v;
/*  423 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(char k, V v) {
/*  429 */     int pos = find(k);
/*  430 */     if (pos < 0) return this.defRetValue; 
/*  431 */     V oldValue = this.value[pos];
/*  432 */     this.value[pos] = v;
/*  433 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(char k, IntFunction<? extends V> mappingFunction) {
/*  439 */     Objects.requireNonNull(mappingFunction);
/*  440 */     int pos = find(k);
/*  441 */     if (pos >= 0) return this.value[pos]; 
/*  442 */     V newValue = mappingFunction.apply(k);
/*  443 */     insert(-pos - 1, k, newValue);
/*  444 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(char key, Char2ObjectFunction<? extends V> mappingFunction) {
/*  450 */     Objects.requireNonNull(mappingFunction);
/*  451 */     int pos = find(key);
/*  452 */     if (pos >= 0) return this.value[pos]; 
/*  453 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  454 */     V newValue = mappingFunction.get(key);
/*  455 */     insert(-pos - 1, key, newValue);
/*  456 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(char k, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/*  462 */     Objects.requireNonNull(remappingFunction);
/*  463 */     int pos = find(k);
/*  464 */     if (pos < 0) return this.defRetValue; 
/*  465 */     if (this.value[pos] == null) return this.defRetValue; 
/*  466 */     V newValue = remappingFunction.apply(Character.valueOf(k), this.value[pos]);
/*  467 */     if (newValue == null) {
/*  468 */       if (k == '\000') { removeNullEntry(); }
/*  469 */       else { removeEntry(pos); }
/*  470 */        return this.defRetValue;
/*      */     } 
/*  472 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(char k, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/*  478 */     Objects.requireNonNull(remappingFunction);
/*  479 */     int pos = find(k);
/*  480 */     V newValue = remappingFunction.apply(Character.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  481 */     if (newValue == null) {
/*  482 */       if (pos >= 0)
/*  483 */         if (k == '\000') { removeNullEntry(); }
/*  484 */         else { removeEntry(pos); }
/*      */          
/*  486 */       return this.defRetValue;
/*      */     } 
/*  488 */     V newVal = newValue;
/*  489 */     if (pos < 0) {
/*  490 */       insert(-pos - 1, k, newVal);
/*  491 */       return newVal;
/*      */     } 
/*  493 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(char k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  499 */     Objects.requireNonNull(remappingFunction);
/*  500 */     Objects.requireNonNull(v);
/*  501 */     int pos = find(k);
/*  502 */     if (pos < 0 || this.value[pos] == null) {
/*  503 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  504 */       else { this.value[pos] = v; }
/*  505 */        return v;
/*      */     } 
/*  507 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  508 */     if (newValue == null) {
/*  509 */       if (k == '\000') { removeNullEntry(); }
/*  510 */       else { removeEntry(pos); }
/*  511 */        return this.defRetValue;
/*      */     } 
/*  513 */     this.value[pos] = newValue; return newValue;
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
/*  524 */     if (this.size == 0)
/*  525 */       return;  this.size = 0;
/*  526 */     this.containsNullKey = false;
/*  527 */     Arrays.fill(this.key, false);
/*  528 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  533 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  538 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Char2ObjectMap.Entry<V>, Map.Entry<Character, V>, CharObjectPair<V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  551 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public char getCharKey() {
/*  559 */       return Char2ObjectOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char leftChar() {
/*  564 */       return Char2ObjectOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/*  569 */       return Char2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V right() {
/*  574 */       return Char2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V v) {
/*  579 */       V oldValue = Char2ObjectOpenHashMap.this.value[this.index];
/*  580 */       Char2ObjectOpenHashMap.this.value[this.index] = v;
/*  581 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharObjectPair<V> right(V v) {
/*  586 */       Char2ObjectOpenHashMap.this.value[this.index] = v;
/*  587 */       return this;
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
/*  598 */       return Character.valueOf(Char2ObjectOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  604 */       if (!(o instanceof Map.Entry)) return false; 
/*  605 */       Map.Entry<Character, V> e = (Map.Entry<Character, V>)o;
/*  606 */       return (Char2ObjectOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && Objects.equals(Char2ObjectOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  611 */       return Char2ObjectOpenHashMap.this.key[this.index] ^ ((Char2ObjectOpenHashMap.this.value[this.index] == null) ? 0 : Char2ObjectOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  616 */       return Char2ObjectOpenHashMap.this.key[this.index] + "=>" + Char2ObjectOpenHashMap.this.value[this.index];
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
/*  627 */     int pos = Char2ObjectOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  633 */     int last = -1;
/*      */     
/*  635 */     int c = Char2ObjectOpenHashMap.this.size;
/*      */     
/*  637 */     boolean mustReturnNullKey = Char2ObjectOpenHashMap.this.containsNullKey;
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
/*  648 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  652 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  653 */       this.c--;
/*  654 */       if (this.mustReturnNullKey) {
/*  655 */         this.mustReturnNullKey = false;
/*  656 */         return this.last = Char2ObjectOpenHashMap.this.n;
/*      */       } 
/*  658 */       char[] key = Char2ObjectOpenHashMap.this.key;
/*      */       while (true) {
/*  660 */         if (--this.pos < 0) {
/*      */           
/*  662 */           this.last = Integer.MIN_VALUE;
/*  663 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  664 */           int p = HashCommon.mix(k) & Char2ObjectOpenHashMap.this.mask;
/*  665 */           for (; k != key[p]; p = p + 1 & Char2ObjectOpenHashMap.this.mask);
/*  666 */           return p;
/*      */         } 
/*  668 */         if (key[this.pos] != '\000') return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  673 */       if (this.mustReturnNullKey) {
/*  674 */         this.mustReturnNullKey = false;
/*  675 */         acceptOnIndex(action, this.last = Char2ObjectOpenHashMap.this.n);
/*  676 */         this.c--;
/*      */       } 
/*  678 */       char[] key = Char2ObjectOpenHashMap.this.key;
/*  679 */       while (this.c != 0) {
/*  680 */         if (--this.pos < 0) {
/*      */           
/*  682 */           this.last = Integer.MIN_VALUE;
/*  683 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  684 */           int p = HashCommon.mix(k) & Char2ObjectOpenHashMap.this.mask;
/*  685 */           for (; k != key[p]; p = p + 1 & Char2ObjectOpenHashMap.this.mask);
/*  686 */           acceptOnIndex(action, p);
/*  687 */           this.c--; continue;
/*  688 */         }  if (key[this.pos] != '\000') {
/*  689 */           acceptOnIndex(action, this.last = this.pos);
/*  690 */           this.c--;
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
/*  705 */       char[] key = Char2ObjectOpenHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  707 */         pos = (last = pos) + 1 & Char2ObjectOpenHashMap.this.mask;
/*      */         while (true) {
/*  709 */           if ((curr = key[pos]) == '\000') {
/*  710 */             key[last] = Character.MIN_VALUE;
/*  711 */             Char2ObjectOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  714 */           int slot = HashCommon.mix(curr) & Char2ObjectOpenHashMap.this.mask;
/*  715 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  716 */             break;  pos = pos + 1 & Char2ObjectOpenHashMap.this.mask;
/*      */         } 
/*  718 */         if (pos < last) {
/*  719 */           if (this.wrapped == null) this.wrapped = new CharArrayList(2); 
/*  720 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  722 */         key[last] = curr;
/*  723 */         Char2ObjectOpenHashMap.this.value[last] = Char2ObjectOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  728 */       if (this.last == -1) throw new IllegalStateException(); 
/*  729 */       if (this.last == Char2ObjectOpenHashMap.this.n)
/*  730 */       { Char2ObjectOpenHashMap.this.containsNullKey = false;
/*  731 */         Char2ObjectOpenHashMap.this.value[Char2ObjectOpenHashMap.this.n] = null; }
/*  732 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  735 */       { Char2ObjectOpenHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  736 */         this.last = -1;
/*      */         return; }
/*      */       
/*  739 */       Char2ObjectOpenHashMap.this.size--;
/*  740 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  745 */       int i = n;
/*  746 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  747 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Char2ObjectMap.Entry<V>>> implements ObjectIterator<Char2ObjectMap.Entry<V>> { public Char2ObjectOpenHashMap<V>.MapEntry next() {
/*  756 */       return this.entry = new Char2ObjectOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Char2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Char2ObjectMap.Entry<V>> action, int index) {
/*  762 */       action.accept(this.entry = new Char2ObjectOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  767 */       super.remove();
/*  768 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Char2ObjectMap.Entry<V>>> implements ObjectIterator<Char2ObjectMap.Entry<V>> {
/*      */     private FastEntryIterator() {
/*  773 */       this.entry = new Char2ObjectOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Char2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     public Char2ObjectOpenHashMap<V>.MapEntry next() {
/*  777 */       this.entry.index = nextEntry();
/*  778 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Char2ObjectMap.Entry<V>> action, int index) {
/*  784 */       this.entry.index = index;
/*  785 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  794 */     int pos = 0;
/*      */     
/*  796 */     int max = Char2ObjectOpenHashMap.this.n;
/*      */     
/*  798 */     int c = 0;
/*      */     
/*  800 */     boolean mustReturnNull = Char2ObjectOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  807 */       this.pos = pos;
/*  808 */       this.max = max;
/*  809 */       this.mustReturnNull = mustReturnNull;
/*  810 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  818 */       if (this.mustReturnNull) {
/*  819 */         this.mustReturnNull = false;
/*  820 */         this.c++;
/*  821 */         acceptOnIndex(action, Char2ObjectOpenHashMap.this.n);
/*  822 */         return true;
/*      */       } 
/*  824 */       char[] key = Char2ObjectOpenHashMap.this.key;
/*  825 */       while (this.pos < this.max) {
/*  826 */         if (key[this.pos] != '\000') {
/*  827 */           this.c++;
/*  828 */           acceptOnIndex(action, this.pos++);
/*  829 */           return true;
/*      */         } 
/*  831 */         this.pos++;
/*      */       } 
/*  833 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  837 */       if (this.mustReturnNull) {
/*  838 */         this.mustReturnNull = false;
/*  839 */         this.c++;
/*  840 */         acceptOnIndex(action, Char2ObjectOpenHashMap.this.n);
/*      */       } 
/*  842 */       char[] key = Char2ObjectOpenHashMap.this.key;
/*  843 */       while (this.pos < this.max) {
/*  844 */         if (key[this.pos] != '\000') {
/*  845 */           acceptOnIndex(action, this.pos);
/*  846 */           this.c++;
/*      */         } 
/*  848 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  853 */       if (!this.hasSplit)
/*      */       {
/*  855 */         return (Char2ObjectOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  860 */       return Math.min((Char2ObjectOpenHashMap.this.size - this.c), (long)(Char2ObjectOpenHashMap.this.realSize() / Char2ObjectOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  865 */       if (this.pos >= this.max - 1) return null; 
/*  866 */       int retLen = this.max - this.pos >> 1;
/*  867 */       if (retLen <= 1) return null; 
/*  868 */       int myNewPos = this.pos + retLen;
/*  869 */       int retPos = this.pos;
/*  870 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  874 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  875 */       this.pos = myNewPos;
/*  876 */       this.mustReturnNull = false;
/*  877 */       this.hasSplit = true;
/*  878 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  882 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  883 */       if (n == 0L) return 0L; 
/*  884 */       long skipped = 0L;
/*  885 */       if (this.mustReturnNull) {
/*  886 */         this.mustReturnNull = false;
/*  887 */         skipped++;
/*  888 */         n--;
/*      */       } 
/*  890 */       char[] key = Char2ObjectOpenHashMap.this.key;
/*  891 */       while (this.pos < this.max && n > 0L) {
/*  892 */         if (key[this.pos++] != '\000') {
/*  893 */           skipped++;
/*  894 */           n--;
/*      */         } 
/*      */       } 
/*  897 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Char2ObjectMap.Entry<V>>, EntrySpliterator> implements ObjectSpliterator<Char2ObjectMap.Entry<V>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  908 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  913 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Char2ObjectMap.Entry<V>> action, int index) {
/*  918 */       action.accept(new Char2ObjectOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  923 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2ObjectMap.Entry<V>> implements Char2ObjectMap.FastEntrySet<V> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2ObjectMap.Entry<V>> iterator() {
/*  930 */       return new Char2ObjectOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Char2ObjectMap.Entry<V>> fastIterator() {
/*  935 */       return new Char2ObjectOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Char2ObjectMap.Entry<V>> spliterator() {
/*  940 */       return new Char2ObjectOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  947 */       if (!(o instanceof Map.Entry)) return false; 
/*  948 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  949 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/*  950 */       char k = ((Character)e.getKey()).charValue();
/*  951 */       V v = (V)e.getValue();
/*  952 */       if (k == '\000') return (Char2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Char2ObjectOpenHashMap.this.value[Char2ObjectOpenHashMap.this.n], v));
/*      */       
/*  954 */       char[] key = Char2ObjectOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  957 */       if ((curr = key[pos = HashCommon.mix(k) & Char2ObjectOpenHashMap.this.mask]) == '\000') return false; 
/*  958 */       if (k == curr) return Objects.equals(Char2ObjectOpenHashMap.this.value[pos], v);
/*      */       
/*      */       while (true) {
/*  961 */         if ((curr = key[pos = pos + 1 & Char2ObjectOpenHashMap.this.mask]) == '\000') return false; 
/*  962 */         if (k == curr) return Objects.equals(Char2ObjectOpenHashMap.this.value[pos], v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  969 */       if (!(o instanceof Map.Entry)) return false; 
/*  970 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  971 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/*  972 */       char k = ((Character)e.getKey()).charValue();
/*  973 */       V v = (V)e.getValue();
/*  974 */       if (k == '\000') {
/*  975 */         if (Char2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Char2ObjectOpenHashMap.this.value[Char2ObjectOpenHashMap.this.n], v)) {
/*  976 */           Char2ObjectOpenHashMap.this.removeNullEntry();
/*  977 */           return true;
/*      */         } 
/*  979 */         return false;
/*      */       } 
/*      */       
/*  982 */       char[] key = Char2ObjectOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  985 */       if ((curr = key[pos = HashCommon.mix(k) & Char2ObjectOpenHashMap.this.mask]) == '\000') return false; 
/*  986 */       if (curr == k) {
/*  987 */         if (Objects.equals(Char2ObjectOpenHashMap.this.value[pos], v)) {
/*  988 */           Char2ObjectOpenHashMap.this.removeEntry(pos);
/*  989 */           return true;
/*      */         } 
/*  991 */         return false;
/*      */       } 
/*      */       while (true) {
/*  994 */         if ((curr = key[pos = pos + 1 & Char2ObjectOpenHashMap.this.mask]) == '\000') return false; 
/*  995 */         if (curr == k && 
/*  996 */           Objects.equals(Char2ObjectOpenHashMap.this.value[pos], v)) {
/*  997 */           Char2ObjectOpenHashMap.this.removeEntry(pos);
/*  998 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1006 */       return Char2ObjectOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1011 */       Char2ObjectOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2ObjectMap.Entry<V>> consumer) {
/* 1017 */       if (Char2ObjectOpenHashMap.this.containsNullKey) consumer.accept(new Char2ObjectOpenHashMap.MapEntry(Char2ObjectOpenHashMap.this.n)); 
/* 1018 */       for (int pos = Char2ObjectOpenHashMap.this.n; pos-- != 0;) { if (Char2ObjectOpenHashMap.this.key[pos] != '\000') consumer.accept(new Char2ObjectOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2ObjectMap.Entry<V>> consumer) {
/* 1024 */       Char2ObjectOpenHashMap<V>.MapEntry entry = new Char2ObjectOpenHashMap.MapEntry();
/* 1025 */       if (Char2ObjectOpenHashMap.this.containsNullKey) {
/* 1026 */         entry.index = Char2ObjectOpenHashMap.this.n;
/* 1027 */         consumer.accept(entry);
/*      */       } 
/* 1029 */       for (int pos = Char2ObjectOpenHashMap.this.n; pos-- != 0;) { if (Char2ObjectOpenHashMap.this.key[pos] != '\000') {
/* 1030 */           entry.index = pos;
/* 1031 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Char2ObjectMap.FastEntrySet<V> char2ObjectEntrySet() {
/* 1038 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1039 */     return this.entries;
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
/* 1060 */       action.accept(Char2ObjectOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1065 */       return Char2ObjectOpenHashMap.this.key[nextEntry()];
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
/* 1076 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1081 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1086 */       action.accept(Char2ObjectOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1091 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/* 1098 */       return new Char2ObjectOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator spliterator() {
/* 1103 */       return new Char2ObjectOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(CharConsumer consumer) {
/* 1109 */       if (Char2ObjectOpenHashMap.this.containsNullKey) consumer.accept(Char2ObjectOpenHashMap.this.key[Char2ObjectOpenHashMap.this.n]); 
/* 1110 */       for (int pos = Char2ObjectOpenHashMap.this.n; pos-- != 0; ) {
/* 1111 */         char k = Char2ObjectOpenHashMap.this.key[pos];
/* 1112 */         if (k != '\000') consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1118 */       return Char2ObjectOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(char k) {
/* 1123 */       return Char2ObjectOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(char k) {
/* 1128 */       int oldSize = Char2ObjectOpenHashMap.this.size;
/* 1129 */       Char2ObjectOpenHashMap.this.remove(k);
/* 1130 */       return (Char2ObjectOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1135 */       Char2ObjectOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
/* 1141 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1142 */     return this.keys;
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
/*      */     extends MapIterator<Consumer<? super V>>
/*      */     implements ObjectIterator<V>
/*      */   {
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1163 */       action.accept(Char2ObjectOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1168 */       return Char2ObjectOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<Consumer<? super V>, ValueSpliterator> implements ObjectSpliterator<V> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 0;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1179 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1184 */       return this.hasSplit ? 0 : 64;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1189 */       action.accept(Char2ObjectOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1194 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1200 */     if (this.values == null) this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1203 */             return new Char2ObjectOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSpliterator<V> spliterator() {
/* 1208 */             return new Char2ObjectOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1214 */             if (Char2ObjectOpenHashMap.this.containsNullKey) consumer.accept(Char2ObjectOpenHashMap.this.value[Char2ObjectOpenHashMap.this.n]); 
/* 1215 */             for (int pos = Char2ObjectOpenHashMap.this.n; pos-- != 0;) { if (Char2ObjectOpenHashMap.this.key[pos] != '\000') consumer.accept(Char2ObjectOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1220 */             return Char2ObjectOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object v) {
/* 1225 */             return Char2ObjectOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1230 */             Char2ObjectOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1233 */     return this.values;
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
/* 1250 */     return trim(this.size);
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
/* 1272 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1273 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1275 */       rehash(l);
/* 1276 */     } catch (OutOfMemoryError cantDoIt) {
/* 1277 */       return false;
/*      */     } 
/* 1279 */     return true;
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
/* 1294 */     char[] key = this.key;
/* 1295 */     V[] value = this.value;
/* 1296 */     int mask = newN - 1;
/* 1297 */     char[] newKey = new char[newN + 1];
/* 1298 */     V[] newValue = (V[])new Object[newN + 1];
/* 1299 */     int i = this.n;
/* 1300 */     for (int j = realSize(); j-- != 0; ) {
/* 1301 */       while (key[--i] == '\000'); int pos;
/* 1302 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != '\000') while (newKey[pos = pos + 1 & mask] != '\000'); 
/* 1303 */       newKey[pos] = key[i];
/* 1304 */       newValue[pos] = value[i];
/*      */     } 
/* 1306 */     newValue[newN] = value[this.n];
/* 1307 */     this.n = newN;
/* 1308 */     this.mask = mask;
/* 1309 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1310 */     this.key = newKey;
/* 1311 */     this.value = newValue;
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
/*      */   public Char2ObjectOpenHashMap<V> clone() {
/*      */     Char2ObjectOpenHashMap<V> c;
/*      */     try {
/* 1328 */       c = (Char2ObjectOpenHashMap<V>)super.clone();
/* 1329 */     } catch (CloneNotSupportedException cantHappen) {
/* 1330 */       throw new InternalError();
/*      */     } 
/* 1332 */     c.keys = null;
/* 1333 */     c.values = null;
/* 1334 */     c.entries = null;
/* 1335 */     c.containsNullKey = this.containsNullKey;
/* 1336 */     c.key = (char[])this.key.clone();
/* 1337 */     c.value = (V[])this.value.clone();
/* 1338 */     return c;
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
/* 1352 */     int h = 0;
/* 1353 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1354 */       for (; this.key[i] == '\000'; i++);
/* 1355 */       t = this.key[i];
/* 1356 */       if (this != this.value[i]) t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1357 */       h += t;
/* 1358 */       i++;
/*      */     } 
/*      */     
/* 1361 */     if (this.containsNullKey) h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1362 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1366 */     char[] key = this.key;
/* 1367 */     V[] value = this.value;
/* 1368 */     EntryIterator i = new EntryIterator();
/* 1369 */     s.defaultWriteObject();
/* 1370 */     for (int j = this.size; j-- != 0; ) {
/* 1371 */       int e = i.nextEntry();
/* 1372 */       s.writeChar(key[e]);
/* 1373 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1379 */     s.defaultReadObject();
/* 1380 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1381 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1382 */     this.mask = this.n - 1;
/* 1383 */     char[] key = this.key = new char[this.n + 1];
/* 1384 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1387 */     for (int i = this.size; i-- != 0; ) {
/* 1388 */       int pos; char k = s.readChar();
/* 1389 */       V v = (V)s.readObject();
/* 1390 */       if (k == '\000') {
/* 1391 */         pos = this.n;
/* 1392 */         this.containsNullKey = true;
/*      */       } else {
/* 1394 */         pos = HashCommon.mix(k) & this.mask;
/* 1395 */         for (; key[pos] != '\000'; pos = pos + 1 & this.mask);
/*      */       } 
/* 1397 */       key[pos] = k;
/* 1398 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ObjectOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */