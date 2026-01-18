/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharConsumer;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
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
/*      */ import java.util.function.ToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2CharOpenHashMap<K>
/*      */   extends AbstractReference2CharMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2CharMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Reference2CharOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   97 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   98 */     this.f = f;
/*   99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  100 */     this.mask = this.n - 1;
/*  101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  102 */     this.key = (K[])new Object[this.n + 1];
/*  103 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenHashMap(int expected) {
/*  112 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenHashMap() {
/*  120 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenHashMap(Map<? extends K, ? extends Character> m, float f) {
/*  130 */     this(m.size(), f);
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenHashMap(Map<? extends K, ? extends Character> m) {
/*  140 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharOpenHashMap(Reference2CharMap<K> m, float f) {
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
/*      */   public Reference2CharOpenHashMap(Reference2CharMap<K> m) {
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
/*      */   public Reference2CharOpenHashMap(K[] k, char[] v, float f) {
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
/*      */   public Reference2CharOpenHashMap(K[] k, char[] v) {
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
/*      */   private char removeEntry(int pos) {
/*  211 */     char oldValue = this.value[pos];
/*  212 */     this.size--;
/*  213 */     shiftKeys(pos);
/*  214 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  215 */     return oldValue;
/*      */   }
/*      */   
/*      */   private char removeNullEntry() {
/*  219 */     this.containsNullKey = false;
/*  220 */     this.key[this.n] = null;
/*  221 */     char oldValue = this.value[this.n];
/*  222 */     this.size--;
/*  223 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  224 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Character> m) {
/*  229 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  230 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  232 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  237 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  239 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  242 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  243 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  246 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  247 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, char v) {
/*  252 */     if (pos == this.n) this.containsNullKey = true; 
/*  253 */     this.key[pos] = k;
/*  254 */     this.value[pos] = v;
/*  255 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(K k, char v) {
/*  261 */     int pos = find(k);
/*  262 */     if (pos < 0) {
/*  263 */       insert(-pos - 1, k, v);
/*  264 */       return this.defRetValue;
/*      */     } 
/*  266 */     char oldValue = this.value[pos];
/*  267 */     this.value[pos] = v;
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   private char addToValue(int pos, char incr) {
/*  272 */     char oldValue = this.value[pos];
/*  273 */     this.value[pos] = (char)(oldValue + incr);
/*  274 */     return oldValue;
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
/*      */   public char addTo(K k, char incr) {
/*      */     int pos;
/*  292 */     if (k == null) {
/*  293 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  294 */       pos = this.n;
/*  295 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  298 */       K[] key = this.key;
/*      */       K curr;
/*  300 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*  301 */         if (curr == k) return addToValue(pos, incr); 
/*  302 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  305 */     }  this.key[pos] = k;
/*  306 */     this.value[pos] = (char)(this.defRetValue + incr);
/*  307 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  309 */     return this.defRetValue;
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
/*  322 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  324 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  326 */         if ((curr = key[pos]) == null) {
/*  327 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  330 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  331 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  332 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  334 */       key[last] = curr;
/*  335 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeChar(Object k) {
/*  342 */     if (k == null) {
/*  343 */       if (this.containsNullKey) return removeNullEntry(); 
/*  344 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  347 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  350 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  351 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  353 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  354 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(Object k) {
/*  361 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  363 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  366 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  367 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  370 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  371 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  378 */     if (k == null) return this.containsNullKey;
/*      */     
/*  380 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  383 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  384 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  387 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  388 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  394 */     char[] value = this.value;
/*  395 */     K[] key = this.key;
/*  396 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  397 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  398 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(Object k, char defaultValue) {
/*  405 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  407 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  410 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return defaultValue; 
/*  411 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  415 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char putIfAbsent(K k, char v) {
/*  422 */     int pos = find(k);
/*  423 */     if (pos >= 0) return this.value[pos]; 
/*  424 */     insert(-pos - 1, k, v);
/*  425 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, char v) {
/*  432 */     if (k == null) {
/*  433 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  434 */         removeNullEntry();
/*  435 */         return true;
/*      */       } 
/*  437 */       return false;
/*      */     } 
/*      */     
/*  440 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  443 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  444 */     if (k == curr && v == this.value[pos]) {
/*  445 */       removeEntry(pos);
/*  446 */       return true;
/*      */     } 
/*      */     while (true) {
/*  449 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  450 */       if (k == curr && v == this.value[pos]) {
/*  451 */         removeEntry(pos);
/*  452 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, char oldValue, char v) {
/*  460 */     int pos = find(k);
/*  461 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  462 */     this.value[pos] = v;
/*  463 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char replace(K k, char v) {
/*  469 */     int pos = find(k);
/*  470 */     if (pos < 0) return this.defRetValue; 
/*  471 */     char oldValue = this.value[pos];
/*  472 */     this.value[pos] = v;
/*  473 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  479 */     Objects.requireNonNull(mappingFunction);
/*  480 */     int pos = find(k);
/*  481 */     if (pos >= 0) return this.value[pos]; 
/*  482 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  483 */     insert(-pos - 1, k, newValue);
/*  484 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(K key, Reference2CharFunction<? super K> mappingFunction) {
/*  490 */     Objects.requireNonNull(mappingFunction);
/*  491 */     int pos = find(key);
/*  492 */     if (pos >= 0) return this.value[pos]; 
/*  493 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  494 */     char newValue = mappingFunction.getChar(key);
/*  495 */     insert(-pos - 1, key, newValue);
/*  496 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  502 */     Objects.requireNonNull(remappingFunction);
/*  503 */     int pos = find(k);
/*  504 */     if (pos < 0) return this.defRetValue; 
/*  505 */     Character newValue = remappingFunction.apply(k, Character.valueOf(this.value[pos]));
/*  506 */     if (newValue == null) {
/*  507 */       if (k == null) { removeNullEntry(); }
/*  508 */       else { removeEntry(pos); }
/*  509 */        return this.defRetValue;
/*      */     } 
/*  511 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  517 */     Objects.requireNonNull(remappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     Character newValue = remappingFunction.apply(k, (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  520 */     if (newValue == null) {
/*  521 */       if (pos >= 0)
/*  522 */         if (k == null) { removeNullEntry(); }
/*  523 */         else { removeEntry(pos); }
/*      */          
/*  525 */       return this.defRetValue;
/*      */     } 
/*  527 */     char newVal = newValue.charValue();
/*  528 */     if (pos < 0) {
/*  529 */       insert(-pos - 1, k, newVal);
/*  530 */       return newVal;
/*      */     } 
/*  532 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  538 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  540 */     int pos = find(k);
/*  541 */     if (pos < 0) {
/*  542 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  543 */       else { this.value[pos] = v; }
/*  544 */        return v;
/*      */     } 
/*  546 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  547 */     if (newValue == null) {
/*  548 */       if (k == null) { removeNullEntry(); }
/*  549 */       else { removeEntry(pos); }
/*  550 */        return this.defRetValue;
/*      */     } 
/*  552 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  563 */     if (this.size == 0)
/*  564 */       return;  this.size = 0;
/*  565 */     this.containsNullKey = false;
/*  566 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  571 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  576 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2CharMap.Entry<K>, Map.Entry<K, Character>, ReferenceCharPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  589 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  597 */       return Reference2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  602 */       return Reference2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char getCharValue() {
/*  607 */       return Reference2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char rightChar() {
/*  612 */       return Reference2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public char setValue(char v) {
/*  617 */       char oldValue = Reference2CharOpenHashMap.this.value[this.index];
/*  618 */       Reference2CharOpenHashMap.this.value[this.index] = v;
/*  619 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceCharPair<K> right(char v) {
/*  624 */       Reference2CharOpenHashMap.this.value[this.index] = v;
/*  625 */       return this;
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
/*  636 */       return Character.valueOf(Reference2CharOpenHashMap.this.value[this.index]);
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
/*  647 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  653 */       if (!(o instanceof Map.Entry)) return false; 
/*  654 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  655 */       return (Reference2CharOpenHashMap.this.key[this.index] == e.getKey() && Reference2CharOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  660 */       return System.identityHashCode(Reference2CharOpenHashMap.this.key[this.index]) ^ Reference2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  665 */       return (new StringBuilder()).append(Reference2CharOpenHashMap.this.key[this.index]).append("=>").append(Reference2CharOpenHashMap.this.value[this.index]).toString();
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
/*  676 */     int pos = Reference2CharOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  682 */     int last = -1;
/*      */     
/*  684 */     int c = Reference2CharOpenHashMap.this.size;
/*      */     
/*  686 */     boolean mustReturnNullKey = Reference2CharOpenHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  697 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  701 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  702 */       this.c--;
/*  703 */       if (this.mustReturnNullKey) {
/*  704 */         this.mustReturnNullKey = false;
/*  705 */         return this.last = Reference2CharOpenHashMap.this.n;
/*      */       } 
/*  707 */       K[] key = Reference2CharOpenHashMap.this.key;
/*      */       while (true) {
/*  709 */         if (--this.pos < 0) {
/*      */           
/*  711 */           this.last = Integer.MIN_VALUE;
/*  712 */           K k = this.wrapped.get(-this.pos - 1);
/*  713 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2CharOpenHashMap.this.mask;
/*  714 */           for (; k != key[p]; p = p + 1 & Reference2CharOpenHashMap.this.mask);
/*  715 */           return p;
/*      */         } 
/*  717 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  722 */       if (this.mustReturnNullKey) {
/*  723 */         this.mustReturnNullKey = false;
/*  724 */         acceptOnIndex(action, this.last = Reference2CharOpenHashMap.this.n);
/*  725 */         this.c--;
/*      */       } 
/*  727 */       K[] key = Reference2CharOpenHashMap.this.key;
/*  728 */       while (this.c != 0) {
/*  729 */         if (--this.pos < 0) {
/*      */           
/*  731 */           this.last = Integer.MIN_VALUE;
/*  732 */           K k = this.wrapped.get(-this.pos - 1);
/*  733 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2CharOpenHashMap.this.mask;
/*  734 */           for (; k != key[p]; p = p + 1 & Reference2CharOpenHashMap.this.mask);
/*  735 */           acceptOnIndex(action, p);
/*  736 */           this.c--; continue;
/*  737 */         }  if (key[this.pos] != null) {
/*  738 */           acceptOnIndex(action, this.last = this.pos);
/*  739 */           this.c--;
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
/*  754 */       K[] key = Reference2CharOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  756 */         pos = (last = pos) + 1 & Reference2CharOpenHashMap.this.mask;
/*      */         while (true) {
/*  758 */           if ((curr = key[pos]) == null) {
/*  759 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  762 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2CharOpenHashMap.this.mask;
/*  763 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  764 */             break;  pos = pos + 1 & Reference2CharOpenHashMap.this.mask;
/*      */         } 
/*  766 */         if (pos < last) {
/*  767 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/*  768 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  770 */         key[last] = curr;
/*  771 */         Reference2CharOpenHashMap.this.value[last] = Reference2CharOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  776 */       if (this.last == -1) throw new IllegalStateException(); 
/*  777 */       if (this.last == Reference2CharOpenHashMap.this.n)
/*  778 */       { Reference2CharOpenHashMap.this.containsNullKey = false;
/*  779 */         Reference2CharOpenHashMap.this.key[Reference2CharOpenHashMap.this.n] = null; }
/*  780 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  783 */       { Reference2CharOpenHashMap.this.removeChar(this.wrapped.set(-this.pos - 1, null));
/*  784 */         this.last = -1;
/*      */         return; }
/*      */       
/*  787 */       Reference2CharOpenHashMap.this.size--;
/*  788 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  793 */       int i = n;
/*  794 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  795 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2CharMap.Entry<K>>> implements ObjectIterator<Reference2CharMap.Entry<K>> { public Reference2CharOpenHashMap<K>.MapEntry next() {
/*  804 */       return this.entry = new Reference2CharOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Reference2CharOpenHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2CharMap.Entry<K>> action, int index) {
/*  810 */       action.accept(this.entry = new Reference2CharOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  815 */       super.remove();
/*  816 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2CharMap.Entry<K>>> implements ObjectIterator<Reference2CharMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  821 */       this.entry = new Reference2CharOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Reference2CharOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2CharOpenHashMap<K>.MapEntry next() {
/*  825 */       this.entry.index = nextEntry();
/*  826 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2CharMap.Entry<K>> action, int index) {
/*  832 */       this.entry.index = index;
/*  833 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  842 */     int pos = 0;
/*      */     
/*  844 */     int max = Reference2CharOpenHashMap.this.n;
/*      */     
/*  846 */     int c = 0;
/*      */     
/*  848 */     boolean mustReturnNull = Reference2CharOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  855 */       this.pos = pos;
/*  856 */       this.max = max;
/*  857 */       this.mustReturnNull = mustReturnNull;
/*  858 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  866 */       if (this.mustReturnNull) {
/*  867 */         this.mustReturnNull = false;
/*  868 */         this.c++;
/*  869 */         acceptOnIndex(action, Reference2CharOpenHashMap.this.n);
/*  870 */         return true;
/*      */       } 
/*  872 */       K[] key = Reference2CharOpenHashMap.this.key;
/*  873 */       while (this.pos < this.max) {
/*  874 */         if (key[this.pos] != null) {
/*  875 */           this.c++;
/*  876 */           acceptOnIndex(action, this.pos++);
/*  877 */           return true;
/*      */         } 
/*  879 */         this.pos++;
/*      */       } 
/*  881 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  885 */       if (this.mustReturnNull) {
/*  886 */         this.mustReturnNull = false;
/*  887 */         this.c++;
/*  888 */         acceptOnIndex(action, Reference2CharOpenHashMap.this.n);
/*      */       } 
/*  890 */       K[] key = Reference2CharOpenHashMap.this.key;
/*  891 */       while (this.pos < this.max) {
/*  892 */         if (key[this.pos] != null) {
/*  893 */           acceptOnIndex(action, this.pos);
/*  894 */           this.c++;
/*      */         } 
/*  896 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  901 */       if (!this.hasSplit)
/*      */       {
/*  903 */         return (Reference2CharOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  908 */       return Math.min((Reference2CharOpenHashMap.this.size - this.c), (long)(Reference2CharOpenHashMap.this.realSize() / Reference2CharOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  913 */       if (this.pos >= this.max - 1) return null; 
/*  914 */       int retLen = this.max - this.pos >> 1;
/*  915 */       if (retLen <= 1) return null; 
/*  916 */       int myNewPos = this.pos + retLen;
/*  917 */       int retPos = this.pos;
/*  918 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  922 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  923 */       this.pos = myNewPos;
/*  924 */       this.mustReturnNull = false;
/*  925 */       this.hasSplit = true;
/*  926 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  930 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  931 */       if (n == 0L) return 0L; 
/*  932 */       long skipped = 0L;
/*  933 */       if (this.mustReturnNull) {
/*  934 */         this.mustReturnNull = false;
/*  935 */         skipped++;
/*  936 */         n--;
/*      */       } 
/*  938 */       K[] key = Reference2CharOpenHashMap.this.key;
/*  939 */       while (this.pos < this.max && n > 0L) {
/*  940 */         if (key[this.pos++] != null) {
/*  941 */           skipped++;
/*  942 */           n--;
/*      */         } 
/*      */       } 
/*  945 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Reference2CharMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Reference2CharMap.Entry<K>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  956 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  961 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2CharMap.Entry<K>> action, int index) {
/*  966 */       action.accept(new Reference2CharOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  971 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2CharMap.Entry<K>> implements Reference2CharMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2CharMap.Entry<K>> iterator() {
/*  978 */       return new Reference2CharOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Reference2CharMap.Entry<K>> fastIterator() {
/*  983 */       return new Reference2CharOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Reference2CharMap.Entry<K>> spliterator() {
/*  988 */       return new Reference2CharOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  995 */       if (!(o instanceof Map.Entry)) return false; 
/*  996 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  997 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/*  998 */       K k = (K)e.getKey();
/*  999 */       char v = ((Character)e.getValue()).charValue();
/* 1000 */       if (k == null) return (Reference2CharOpenHashMap.this.containsNullKey && Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n] == v);
/*      */       
/* 1002 */       K[] key = Reference2CharOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1005 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2CharOpenHashMap.this.mask]) == null) return false; 
/* 1006 */       if (k == curr) return (Reference2CharOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1009 */         if ((curr = key[pos = pos + 1 & Reference2CharOpenHashMap.this.mask]) == null) return false; 
/* 1010 */         if (k == curr) return (Reference2CharOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1017 */       if (!(o instanceof Map.Entry)) return false; 
/* 1018 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1019 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1020 */       K k = (K)e.getKey();
/* 1021 */       char v = ((Character)e.getValue()).charValue();
/* 1022 */       if (k == null) {
/* 1023 */         if (Reference2CharOpenHashMap.this.containsNullKey && Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n] == v) {
/* 1024 */           Reference2CharOpenHashMap.this.removeNullEntry();
/* 1025 */           return true;
/*      */         } 
/* 1027 */         return false;
/*      */       } 
/*      */       
/* 1030 */       K[] key = Reference2CharOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1033 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2CharOpenHashMap.this.mask]) == null) return false; 
/* 1034 */       if (curr == k) {
/* 1035 */         if (Reference2CharOpenHashMap.this.value[pos] == v) {
/* 1036 */           Reference2CharOpenHashMap.this.removeEntry(pos);
/* 1037 */           return true;
/*      */         } 
/* 1039 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1042 */         if ((curr = key[pos = pos + 1 & Reference2CharOpenHashMap.this.mask]) == null) return false; 
/* 1043 */         if (curr == k && 
/* 1044 */           Reference2CharOpenHashMap.this.value[pos] == v) {
/* 1045 */           Reference2CharOpenHashMap.this.removeEntry(pos);
/* 1046 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1054 */       return Reference2CharOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1059 */       Reference2CharOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/* 1065 */       if (Reference2CharOpenHashMap.this.containsNullKey) consumer.accept(new Reference2CharOpenHashMap.MapEntry(Reference2CharOpenHashMap.this.n)); 
/* 1066 */       for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0;) { if (Reference2CharOpenHashMap.this.key[pos] != null) consumer.accept(new Reference2CharOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/* 1072 */       Reference2CharOpenHashMap<K>.MapEntry entry = new Reference2CharOpenHashMap.MapEntry();
/* 1073 */       if (Reference2CharOpenHashMap.this.containsNullKey) {
/* 1074 */         entry.index = Reference2CharOpenHashMap.this.n;
/* 1075 */         consumer.accept(entry);
/*      */       } 
/* 1077 */       for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0;) { if (Reference2CharOpenHashMap.this.key[pos] != null) {
/* 1078 */           entry.index = pos;
/* 1079 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Reference2CharMap.FastEntrySet<K> reference2CharEntrySet() {
/* 1086 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1087 */     return this.entries;
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
/*      */     extends MapIterator<Consumer<? super K>>
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1108 */       action.accept(Reference2CharOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1113 */       return Reference2CharOpenHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<Consumer<? super K>, KeySpliterator> implements ObjectSpliterator<K> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1124 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1129 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1134 */       action.accept(Reference2CharOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1139 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1146 */       return new Reference2CharOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1151 */       return new Reference2CharOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1157 */       if (Reference2CharOpenHashMap.this.containsNullKey) consumer.accept(Reference2CharOpenHashMap.this.key[Reference2CharOpenHashMap.this.n]); 
/* 1158 */       for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0; ) {
/* 1159 */         K k = Reference2CharOpenHashMap.this.key[pos];
/* 1160 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1166 */       return Reference2CharOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1171 */       return Reference2CharOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1176 */       int oldSize = Reference2CharOpenHashMap.this.size;
/* 1177 */       Reference2CharOpenHashMap.this.removeChar(k);
/* 1178 */       return (Reference2CharOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1183 */       Reference2CharOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1189 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1190 */     return this.keys;
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
/* 1211 */       action.accept(Reference2CharOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1216 */       return Reference2CharOpenHashMap.this.value[nextEntry()];
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
/* 1227 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1232 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(CharConsumer action, int index) {
/* 1237 */       action.accept(Reference2CharOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1242 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CharCollection values() {
/* 1248 */     if (this.values == null) this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1251 */             return new Reference2CharOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public CharSpliterator spliterator() {
/* 1256 */             return new Reference2CharOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(CharConsumer consumer) {
/* 1262 */             if (Reference2CharOpenHashMap.this.containsNullKey) consumer.accept(Reference2CharOpenHashMap.this.value[Reference2CharOpenHashMap.this.n]); 
/* 1263 */             for (int pos = Reference2CharOpenHashMap.this.n; pos-- != 0;) { if (Reference2CharOpenHashMap.this.key[pos] != null) consumer.accept(Reference2CharOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1268 */             return Reference2CharOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(char v) {
/* 1273 */             return Reference2CharOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1278 */             Reference2CharOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1281 */     return this.values;
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
/* 1298 */     return trim(this.size);
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
/* 1320 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1321 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1323 */       rehash(l);
/* 1324 */     } catch (OutOfMemoryError cantDoIt) {
/* 1325 */       return false;
/*      */     } 
/* 1327 */     return true;
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
/* 1342 */     K[] key = this.key;
/* 1343 */     char[] value = this.value;
/* 1344 */     int mask = newN - 1;
/* 1345 */     K[] newKey = (K[])new Object[newN + 1];
/* 1346 */     char[] newValue = new char[newN + 1];
/* 1347 */     int i = this.n;
/* 1348 */     for (int j = realSize(); j-- != 0; ) {
/* 1349 */       while (key[--i] == null); int pos;
/* 1350 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1351 */       newKey[pos] = key[i];
/* 1352 */       newValue[pos] = value[i];
/*      */     } 
/* 1354 */     newValue[newN] = value[this.n];
/* 1355 */     this.n = newN;
/* 1356 */     this.mask = mask;
/* 1357 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1358 */     this.key = newKey;
/* 1359 */     this.value = newValue;
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
/*      */   public Reference2CharOpenHashMap<K> clone() {
/*      */     Reference2CharOpenHashMap<K> c;
/*      */     try {
/* 1376 */       c = (Reference2CharOpenHashMap<K>)super.clone();
/* 1377 */     } catch (CloneNotSupportedException cantHappen) {
/* 1378 */       throw new InternalError();
/*      */     } 
/* 1380 */     c.keys = null;
/* 1381 */     c.values = null;
/* 1382 */     c.entries = null;
/* 1383 */     c.containsNullKey = this.containsNullKey;
/* 1384 */     c.key = (K[])this.key.clone();
/* 1385 */     c.value = (char[])this.value.clone();
/* 1386 */     return c;
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
/* 1400 */     int h = 0;
/* 1401 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1402 */       for (; this.key[i] == null; i++);
/* 1403 */       if (this != this.key[i]) t = System.identityHashCode(this.key[i]); 
/* 1404 */       t ^= this.value[i];
/* 1405 */       h += t;
/* 1406 */       i++;
/*      */     } 
/*      */     
/* 1409 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1410 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1414 */     K[] key = this.key;
/* 1415 */     char[] value = this.value;
/* 1416 */     EntryIterator i = new EntryIterator();
/* 1417 */     s.defaultWriteObject();
/* 1418 */     for (int j = this.size; j-- != 0; ) {
/* 1419 */       int e = i.nextEntry();
/* 1420 */       s.writeObject(key[e]);
/* 1421 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1427 */     s.defaultReadObject();
/* 1428 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1429 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1430 */     this.mask = this.n - 1;
/* 1431 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1432 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1435 */     for (int i = this.size; i-- != 0; ) {
/* 1436 */       int pos; K k = (K)s.readObject();
/* 1437 */       char v = s.readChar();
/* 1438 */       if (k == null) {
/* 1439 */         pos = this.n;
/* 1440 */         this.containsNullKey = true;
/*      */       } else {
/* 1442 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1443 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1445 */       key[pos] = k;
/* 1446 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2CharOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */