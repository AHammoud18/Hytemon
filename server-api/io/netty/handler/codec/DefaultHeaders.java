/*      */ package io.netty.handler.codec;
/*      */ 
/*      */ import io.netty.util.HashingStrategy;
/*      */ import io.netty.util.internal.MathUtil;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultHeaders<K, V, T extends Headers<K, V, T>>
/*      */   implements Headers<K, V, T>
/*      */ {
/*      */   static final int HASH_CODE_SEED = -1028477387;
/*      */   private final HeaderEntry<K, V>[] entries;
/*      */   protected final HeaderEntry<K, V> head;
/*      */   private final byte hashMask;
/*      */   private final ValueConverter<V> valueConverter;
/*      */   private final NameValidator<K> nameValidator;
/*      */   private final ValueValidator<V> valueValidator;
/*      */   private final HashingStrategy<K> hashingStrategy;
/*      */   int size;
/*      */   
/*      */   public static interface NameValidator<K>
/*      */   {
/*   68 */     public static final NameValidator NOT_NULL = new NameValidator()
/*      */       {
/*      */         public void validateName(Object name) {
/*   71 */           ObjectUtil.checkNotNull(name, "name");
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void validateName(K param1K);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface ValueValidator<V>
/*      */   {
/*   85 */     public static final ValueValidator<?> NO_VALIDATION = new ValueValidator<Object>()
/*      */       {
/*      */         public void validate(Object value) {}
/*      */       };
/*      */     
/*      */     void validate(V param1V);
/*      */   }
/*      */   
/*      */   public DefaultHeaders(ValueConverter<V> valueConverter) {
/*   94 */     this(HashingStrategy.JAVA_HASHER, valueConverter);
/*      */   }
/*      */ 
/*      */   
/*      */   public DefaultHeaders(ValueConverter<V> valueConverter, NameValidator<K> nameValidator) {
/*   99 */     this(HashingStrategy.JAVA_HASHER, valueConverter, nameValidator);
/*      */   }
/*      */ 
/*      */   
/*      */   public DefaultHeaders(HashingStrategy<K> nameHashingStrategy, ValueConverter<V> valueConverter) {
/*  104 */     this(nameHashingStrategy, valueConverter, NameValidator.NOT_NULL);
/*      */   }
/*      */ 
/*      */   
/*      */   public DefaultHeaders(HashingStrategy<K> nameHashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator) {
/*  109 */     this(nameHashingStrategy, valueConverter, nameValidator, 16);
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
/*      */   public DefaultHeaders(HashingStrategy<K> nameHashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator, int arraySizeHint) {
/*  123 */     this(nameHashingStrategy, valueConverter, nameValidator, arraySizeHint, (ValueValidator)ValueValidator.NO_VALIDATION);
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
/*      */   public DefaultHeaders(HashingStrategy<K> nameHashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator, int arraySizeHint, ValueValidator<V> valueValidator) {
/*  139 */     this.valueConverter = (ValueConverter<V>)ObjectUtil.checkNotNull(valueConverter, "valueConverter");
/*  140 */     this.nameValidator = (NameValidator<K>)ObjectUtil.checkNotNull(nameValidator, "nameValidator");
/*  141 */     this.hashingStrategy = (HashingStrategy<K>)ObjectUtil.checkNotNull(nameHashingStrategy, "nameHashingStrategy");
/*  142 */     this.valueValidator = (ValueValidator<V>)ObjectUtil.checkNotNull(valueValidator, "valueValidator");
/*      */ 
/*      */     
/*  145 */     this.entries = (HeaderEntry<K, V>[])new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.max(2, Math.min(arraySizeHint, 128)))];
/*  146 */     this.hashMask = (byte)(this.entries.length - 1);
/*  147 */     this.head = new HeaderEntry<>();
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(K name) {
/*  152 */     ObjectUtil.checkNotNull(name, "name");
/*      */     
/*  154 */     int h = this.hashingStrategy.hashCode(name);
/*  155 */     int i = index(h);
/*  156 */     HeaderEntry<K, V> e = this.entries[i];
/*  157 */     V value = null;
/*      */     
/*  159 */     while (e != null) {
/*  160 */       if (e.hash == h && this.hashingStrategy.equals(name, e.key)) {
/*  161 */         value = e.value;
/*      */       }
/*      */       
/*  164 */       e = e.next;
/*      */     } 
/*  166 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(K name, V defaultValue) {
/*  171 */     V value = get(name);
/*  172 */     if (value == null) {
/*  173 */       return defaultValue;
/*      */     }
/*  175 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public V getAndRemove(K name) {
/*  180 */     int h = this.hashingStrategy.hashCode(name);
/*  181 */     return remove0(h, index(h), (K)ObjectUtil.checkNotNull(name, "name"));
/*      */   }
/*      */ 
/*      */   
/*      */   public V getAndRemove(K name, V defaultValue) {
/*  186 */     V value = getAndRemove(name);
/*  187 */     if (value == null) {
/*  188 */       return defaultValue;
/*      */     }
/*  190 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<V> getAll(K name) {
/*  195 */     ObjectUtil.checkNotNull(name, "name");
/*      */     
/*  197 */     LinkedList<V> values = new LinkedList<>();
/*      */     
/*  199 */     int h = this.hashingStrategy.hashCode(name);
/*  200 */     int i = index(h);
/*  201 */     HeaderEntry<K, V> e = this.entries[i];
/*  202 */     while (e != null) {
/*  203 */       if (e.hash == h && this.hashingStrategy.equals(name, e.key)) {
/*  204 */         values.addFirst(e.getValue());
/*      */       }
/*  206 */       e = e.next;
/*      */     } 
/*  208 */     return values;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<V> valueIterator(K name) {
/*  217 */     return new ValueIterator(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<V> getAllAndRemove(K name) {
/*  222 */     List<V> all = getAll(name);
/*  223 */     remove(name);
/*  224 */     return all;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(K name) {
/*  229 */     return (get(name) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsObject(K name, Object value) {
/*  234 */     return contains(name, fromObject(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsBoolean(K name, boolean value) {
/*  239 */     return contains(name, fromBoolean(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsByte(K name, byte value) {
/*  244 */     return contains(name, fromByte(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsChar(K name, char value) {
/*  249 */     return contains(name, fromChar(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsShort(K name, short value) {
/*  254 */     return contains(name, fromShort(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsInt(K name, int value) {
/*  259 */     return contains(name, fromInt(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsLong(K name, long value) {
/*  264 */     return contains(name, fromLong(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsFloat(K name, float value) {
/*  269 */     return contains(name, fromFloat(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsDouble(K name, double value) {
/*  274 */     return contains(name, fromDouble(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsTimeMillis(K name, long value) {
/*  279 */     return contains(name, fromTimeMillis(name, value));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(K name, V value) {
/*  285 */     return contains(name, value, HashingStrategy.JAVA_HASHER);
/*      */   }
/*      */   
/*      */   public final boolean contains(K name, V value, HashingStrategy<? super V> valueHashingStrategy) {
/*  289 */     ObjectUtil.checkNotNull(name, "name");
/*      */     
/*  291 */     int h = this.hashingStrategy.hashCode(name);
/*  292 */     int i = index(h);
/*  293 */     HeaderEntry<K, V> e = this.entries[i];
/*  294 */     while (e != null) {
/*  295 */       if (e.hash == h && this.hashingStrategy.equals(name, e.key) && valueHashingStrategy.equals(value, e.value)) {
/*  296 */         return true;
/*      */       }
/*  298 */       e = e.next;
/*      */     } 
/*  300 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  305 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  310 */     return (this.head == this.head.after);
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<K> names() {
/*  315 */     if (isEmpty()) {
/*  316 */       return Collections.emptySet();
/*      */     }
/*  318 */     Set<K> names = new LinkedHashSet<>(size());
/*  319 */     HeaderEntry<K, V> e = this.head.after;
/*  320 */     while (e != this.head) {
/*  321 */       names.add(e.getKey());
/*  322 */       e = e.after;
/*      */     } 
/*  324 */     return names;
/*      */   }
/*      */ 
/*      */   
/*      */   public T add(K name, V value) {
/*  329 */     validateName(this.nameValidator, true, name);
/*  330 */     validateValue(this.valueValidator, name, value);
/*  331 */     ObjectUtil.checkNotNull(value, "value");
/*  332 */     int h = this.hashingStrategy.hashCode(name);
/*  333 */     int i = index(h);
/*  334 */     add0(h, i, name, value);
/*  335 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T add(K name, Iterable<? extends V> values) {
/*  340 */     validateName(this.nameValidator, true, name);
/*  341 */     int h = this.hashingStrategy.hashCode(name);
/*  342 */     int i = index(h);
/*  343 */     for (V v : values) {
/*  344 */       validateValue(this.valueValidator, name, v);
/*  345 */       add0(h, i, name, v);
/*      */     } 
/*  347 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T add(K name, V... values) {
/*  352 */     validateName(this.nameValidator, true, name);
/*  353 */     int h = this.hashingStrategy.hashCode(name);
/*  354 */     int i = index(h);
/*  355 */     for (V v : values) {
/*  356 */       validateValue(this.valueValidator, name, v);
/*  357 */       add0(h, i, name, v);
/*      */     } 
/*  359 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T addObject(K name, Object value) {
/*  364 */     return add(name, fromObject(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addObject(K name, Iterable<?> values) {
/*  369 */     for (Object value : values) {
/*  370 */       addObject(name, value);
/*      */     }
/*  372 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T addObject(K name, Object... values) {
/*  377 */     for (Object value : values) {
/*  378 */       addObject(name, value);
/*      */     }
/*  380 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T addInt(K name, int value) {
/*  385 */     return add(name, fromInt(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addLong(K name, long value) {
/*  390 */     return add(name, fromLong(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addDouble(K name, double value) {
/*  395 */     return add(name, fromDouble(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addTimeMillis(K name, long value) {
/*  400 */     return add(name, fromTimeMillis(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addChar(K name, char value) {
/*  405 */     return add(name, fromChar(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addBoolean(K name, boolean value) {
/*  410 */     return add(name, fromBoolean(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addFloat(K name, float value) {
/*  415 */     return add(name, fromFloat(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addByte(K name, byte value) {
/*  420 */     return add(name, fromByte(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T addShort(K name, short value) {
/*  425 */     return add(name, fromShort(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T add(Headers<? extends K, ? extends V, ?> headers) {
/*  430 */     if (headers == this) {
/*  431 */       throw new IllegalArgumentException("can't add to itself.");
/*      */     }
/*  433 */     addImpl(headers);
/*  434 */     return thisT();
/*      */   }
/*      */   
/*      */   protected void addImpl(Headers<? extends K, ? extends V, ?> headers) {
/*  438 */     if (headers instanceof DefaultHeaders) {
/*      */       
/*  440 */       DefaultHeaders<? extends K, ? extends V, T> defaultHeaders = (DefaultHeaders)headers;
/*      */       
/*  442 */       HeaderEntry<? extends K, ? extends V> e = defaultHeaders.head.after;
/*  443 */       if (defaultHeaders.hashingStrategy == this.hashingStrategy && defaultHeaders.nameValidator == this.nameValidator) {
/*      */ 
/*      */         
/*  446 */         while (e != defaultHeaders.head) {
/*  447 */           add0(e.hash, index(e.hash), e.key, e.value);
/*  448 */           e = e.after;
/*      */         } 
/*      */       } else {
/*      */         
/*  452 */         while (e != defaultHeaders.head) {
/*  453 */           add(e.key, e.value);
/*  454 */           e = e.after;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  459 */       for (Map.Entry<? extends K, ? extends V> header : headers) {
/*  460 */         add(header.getKey(), header.getValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public T set(K name, V value) {
/*  467 */     validateName(this.nameValidator, false, name);
/*  468 */     validateValue(this.valueValidator, name, value);
/*  469 */     ObjectUtil.checkNotNull(value, "value");
/*  470 */     int h = this.hashingStrategy.hashCode(name);
/*  471 */     int i = index(h);
/*  472 */     remove0(h, i, name);
/*  473 */     add0(h, i, name, value);
/*  474 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T set(K name, Iterable<? extends V> values) {
/*  479 */     validateName(this.nameValidator, false, name);
/*  480 */     ObjectUtil.checkNotNull(values, "values");
/*      */     
/*  482 */     int h = this.hashingStrategy.hashCode(name);
/*  483 */     int i = index(h);
/*      */     
/*  485 */     remove0(h, i, name);
/*  486 */     for (V v : values) {
/*  487 */       if (v == null) {
/*      */         break;
/*      */       }
/*  490 */       validateValue(this.valueValidator, name, v);
/*  491 */       add0(h, i, name, v);
/*      */     } 
/*      */     
/*  494 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T set(K name, V... values) {
/*  499 */     validateName(this.nameValidator, false, name);
/*  500 */     ObjectUtil.checkNotNull(values, "values");
/*      */     
/*  502 */     int h = this.hashingStrategy.hashCode(name);
/*  503 */     int i = index(h);
/*      */     
/*  505 */     remove0(h, i, name);
/*  506 */     for (V v : values) {
/*  507 */       if (v == null) {
/*      */         break;
/*      */       }
/*  510 */       validateValue(this.valueValidator, name, v);
/*  511 */       add0(h, i, name, v);
/*      */     } 
/*      */     
/*  514 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T setObject(K name, Object value) {
/*  519 */     V convertedValue = (V)ObjectUtil.checkNotNull(fromObject(name, value), "convertedValue");
/*  520 */     return set(name, convertedValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public T setObject(K name, Iterable<?> values) {
/*  525 */     validateName(this.nameValidator, false, name);
/*      */     
/*  527 */     int h = this.hashingStrategy.hashCode(name);
/*  528 */     int i = index(h);
/*      */     
/*  530 */     remove0(h, i, name);
/*  531 */     for (Object v : values) {
/*  532 */       if (v == null) {
/*      */         break;
/*      */       }
/*  535 */       V converted = fromObject(name, v);
/*  536 */       validateValue(this.valueValidator, name, converted);
/*  537 */       add0(h, i, name, converted);
/*      */     } 
/*      */     
/*  540 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T setObject(K name, Object... values) {
/*  545 */     validateName(this.nameValidator, false, name);
/*      */     
/*  547 */     int h = this.hashingStrategy.hashCode(name);
/*  548 */     int i = index(h);
/*      */     
/*  550 */     remove0(h, i, name);
/*  551 */     for (Object v : values) {
/*  552 */       if (v == null) {
/*      */         break;
/*      */       }
/*  555 */       V converted = fromObject(name, v);
/*  556 */       validateValue(this.valueValidator, name, converted);
/*  557 */       add0(h, i, name, converted);
/*      */     } 
/*      */     
/*  560 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T setInt(K name, int value) {
/*  565 */     return set(name, fromInt(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setLong(K name, long value) {
/*  570 */     return set(name, fromLong(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setDouble(K name, double value) {
/*  575 */     return set(name, fromDouble(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setTimeMillis(K name, long value) {
/*  580 */     return set(name, fromTimeMillis(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setFloat(K name, float value) {
/*  585 */     return set(name, fromFloat(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setChar(K name, char value) {
/*  590 */     return set(name, fromChar(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setBoolean(K name, boolean value) {
/*  595 */     return set(name, fromBoolean(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setByte(K name, byte value) {
/*  600 */     return set(name, fromByte(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T setShort(K name, short value) {
/*  605 */     return set(name, fromShort(name, value));
/*      */   }
/*      */ 
/*      */   
/*      */   public T set(Headers<? extends K, ? extends V, ?> headers) {
/*  610 */     if (headers != this) {
/*  611 */       clear();
/*  612 */       addImpl(headers);
/*      */     } 
/*  614 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public T setAll(Headers<? extends K, ? extends V, ?> headers) {
/*  619 */     if (headers != this) {
/*  620 */       for (K key : headers.names()) {
/*  621 */         remove(key);
/*      */       }
/*  623 */       addImpl(headers);
/*      */     } 
/*  625 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(K name) {
/*  630 */     return (getAndRemove(name) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public T clear() {
/*  635 */     Arrays.fill((Object[])this.entries, (Object)null);
/*  636 */     this.head.before = this.head.after = this.head;
/*  637 */     this.size = 0;
/*  638 */     return thisT();
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<Map.Entry<K, V>> iterator() {
/*  643 */     return new HeaderIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean getBoolean(K name) {
/*  648 */     V v = get(name);
/*      */     try {
/*  650 */       return (v != null) ? Boolean.valueOf(toBoolean(name, v)) : null;
/*  651 */     } catch (RuntimeException ignore) {
/*  652 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(K name, boolean defaultValue) {
/*  658 */     Boolean v = getBoolean(name);
/*  659 */     return (v != null) ? v.booleanValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte getByte(K name) {
/*  664 */     V v = get(name);
/*      */     try {
/*  666 */       return (v != null) ? Byte.valueOf(toByte(name, v)) : null;
/*  667 */     } catch (RuntimeException ignore) {
/*  668 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(K name, byte defaultValue) {
/*  674 */     Byte v = getByte(name);
/*  675 */     return (v != null) ? v.byteValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Character getChar(K name) {
/*  680 */     V v = get(name);
/*      */     try {
/*  682 */       return (v != null) ? Character.valueOf(toChar(name, v)) : null;
/*  683 */     } catch (RuntimeException ignore) {
/*  684 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(K name, char defaultValue) {
/*  690 */     Character v = getChar(name);
/*  691 */     return (v != null) ? v.charValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Short getShort(K name) {
/*  696 */     V v = get(name);
/*      */     try {
/*  698 */       return (v != null) ? Short.valueOf(toShort(name, v)) : null;
/*  699 */     } catch (RuntimeException ignore) {
/*  700 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(K name, short defaultValue) {
/*  706 */     Short v = getShort(name);
/*  707 */     return (v != null) ? v.shortValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getInt(K name) {
/*  712 */     V v = get(name);
/*      */     try {
/*  714 */       return (v != null) ? Integer.valueOf(toInt(name, v)) : null;
/*  715 */     } catch (RuntimeException ignore) {
/*  716 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(K name, int defaultValue) {
/*  722 */     Integer v = getInt(name);
/*  723 */     return (v != null) ? v.intValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Long getLong(K name) {
/*  728 */     V v = get(name);
/*      */     try {
/*  730 */       return (v != null) ? Long.valueOf(toLong(name, v)) : null;
/*  731 */     } catch (RuntimeException ignore) {
/*  732 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(K name, long defaultValue) {
/*  738 */     Long v = getLong(name);
/*  739 */     return (v != null) ? v.longValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Float getFloat(K name) {
/*  744 */     V v = get(name);
/*      */     try {
/*  746 */       return (v != null) ? Float.valueOf(toFloat(name, v)) : null;
/*  747 */     } catch (RuntimeException ignore) {
/*  748 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(K name, float defaultValue) {
/*  754 */     Float v = getFloat(name);
/*  755 */     return (v != null) ? v.floatValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Double getDouble(K name) {
/*  760 */     V v = get(name);
/*      */     try {
/*  762 */       return (v != null) ? Double.valueOf(toDouble(name, v)) : null;
/*  763 */     } catch (RuntimeException ignore) {
/*  764 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(K name, double defaultValue) {
/*  770 */     Double v = getDouble(name);
/*  771 */     return (v != null) ? v.doubleValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Long getTimeMillis(K name) {
/*  776 */     V v = get(name);
/*      */     try {
/*  778 */       return (v != null) ? Long.valueOf(toTimeMillis(name, v)) : null;
/*  779 */     } catch (RuntimeException ignore) {
/*  780 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getTimeMillis(K name, long defaultValue) {
/*  786 */     Long v = getTimeMillis(name);
/*  787 */     return (v != null) ? v.longValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean getBooleanAndRemove(K name) {
/*  792 */     V v = getAndRemove(name);
/*      */     try {
/*  794 */       return (v != null) ? Boolean.valueOf(toBoolean(name, v)) : null;
/*  795 */     } catch (RuntimeException ignore) {
/*  796 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBooleanAndRemove(K name, boolean defaultValue) {
/*  802 */     Boolean v = getBooleanAndRemove(name);
/*  803 */     return (v != null) ? v.booleanValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte getByteAndRemove(K name) {
/*  808 */     V v = getAndRemove(name);
/*      */     try {
/*  810 */       return (v != null) ? Byte.valueOf(toByte(name, v)) : null;
/*  811 */     } catch (RuntimeException ignore) {
/*  812 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByteAndRemove(K name, byte defaultValue) {
/*  818 */     Byte v = getByteAndRemove(name);
/*  819 */     return (v != null) ? v.byteValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Character getCharAndRemove(K name) {
/*  824 */     V v = getAndRemove(name);
/*      */     try {
/*  826 */       return (v != null) ? Character.valueOf(toChar(name, v)) : null;
/*  827 */     } catch (RuntimeException ignore) {
/*  828 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char getCharAndRemove(K name, char defaultValue) {
/*  834 */     Character v = getCharAndRemove(name);
/*  835 */     return (v != null) ? v.charValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Short getShortAndRemove(K name) {
/*  840 */     V v = getAndRemove(name);
/*      */     try {
/*  842 */       return (v != null) ? Short.valueOf(toShort(name, v)) : null;
/*  843 */     } catch (RuntimeException ignore) {
/*  844 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShortAndRemove(K name, short defaultValue) {
/*  850 */     Short v = getShortAndRemove(name);
/*  851 */     return (v != null) ? v.shortValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getIntAndRemove(K name) {
/*  856 */     V v = getAndRemove(name);
/*      */     try {
/*  858 */       return (v != null) ? Integer.valueOf(toInt(name, v)) : null;
/*  859 */     } catch (RuntimeException ignore) {
/*  860 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getIntAndRemove(K name, int defaultValue) {
/*  866 */     Integer v = getIntAndRemove(name);
/*  867 */     return (v != null) ? v.intValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Long getLongAndRemove(K name) {
/*  872 */     V v = getAndRemove(name);
/*      */     try {
/*  874 */       return (v != null) ? Long.valueOf(toLong(name, v)) : null;
/*  875 */     } catch (RuntimeException ignore) {
/*  876 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLongAndRemove(K name, long defaultValue) {
/*  882 */     Long v = getLongAndRemove(name);
/*  883 */     return (v != null) ? v.longValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Float getFloatAndRemove(K name) {
/*  888 */     V v = getAndRemove(name);
/*      */     try {
/*  890 */       return (v != null) ? Float.valueOf(toFloat(name, v)) : null;
/*  891 */     } catch (RuntimeException ignore) {
/*  892 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloatAndRemove(K name, float defaultValue) {
/*  898 */     Float v = getFloatAndRemove(name);
/*  899 */     return (v != null) ? v.floatValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Double getDoubleAndRemove(K name) {
/*  904 */     V v = getAndRemove(name);
/*      */     try {
/*  906 */       return (v != null) ? Double.valueOf(toDouble(name, v)) : null;
/*  907 */     } catch (RuntimeException ignore) {
/*  908 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDoubleAndRemove(K name, double defaultValue) {
/*  914 */     Double v = getDoubleAndRemove(name);
/*  915 */     return (v != null) ? v.doubleValue() : defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Long getTimeMillisAndRemove(K name) {
/*  920 */     V v = getAndRemove(name);
/*      */     try {
/*  922 */       return (v != null) ? Long.valueOf(toTimeMillis(name, v)) : null;
/*  923 */     } catch (RuntimeException ignore) {
/*  924 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getTimeMillisAndRemove(K name, long defaultValue) {
/*  930 */     Long v = getTimeMillisAndRemove(name);
/*  931 */     return (v != null) ? v.longValue() : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  937 */     if (!(o instanceof Headers)) {
/*  938 */       return false;
/*      */     }
/*      */     
/*  941 */     return equals((Headers<K, V, ?>)o, HashingStrategy.JAVA_HASHER);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  947 */     return hashCode(HashingStrategy.JAVA_HASHER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean equals(Headers<K, V, ?> h2, HashingStrategy<V> valueHashingStrategy) {
/*  958 */     if (h2.size() != size()) {
/*  959 */       return false;
/*      */     }
/*      */     
/*  962 */     if (this == h2) {
/*  963 */       return true;
/*      */     }
/*      */     
/*  966 */     for (K name : names()) {
/*  967 */       List<V> otherValues = h2.getAll(name);
/*  968 */       List<V> values = getAll(name);
/*  969 */       if (otherValues.size() != values.size()) {
/*  970 */         return false;
/*      */       }
/*  972 */       for (int i = 0; i < otherValues.size(); i++) {
/*  973 */         if (!valueHashingStrategy.equals(otherValues.get(i), values.get(i))) {
/*  974 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*  978 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int hashCode(HashingStrategy<V> valueHashingStrategy) {
/*  987 */     int result = -1028477387;
/*  988 */     for (K name : names()) {
/*  989 */       result = 31 * result + this.hashingStrategy.hashCode(name);
/*  990 */       List<V> values = getAll(name);
/*  991 */       for (int i = 0; i < values.size(); i++) {
/*  992 */         result = 31 * result + valueHashingStrategy.hashCode(values.get(i));
/*      */       }
/*      */     } 
/*  995 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1000 */     return HeadersUtils.toString(getClass(), iterator(), size());
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
/*      */   protected void validateName(NameValidator<K> validator, boolean forAdd, K name) {
/* 1012 */     validator.validateName(name);
/*      */   }
/*      */   
/*      */   protected void validateValue(ValueValidator<V> validator, K name, V value) {
/*      */     try {
/* 1017 */       validator.validate(value);
/* 1018 */     } catch (IllegalArgumentException e) {
/* 1019 */       throw new IllegalArgumentException("Validation failed for header '" + name + "'", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected HeaderEntry<K, V> newHeaderEntry(int h, K name, V value, HeaderEntry<K, V> next) {
/* 1024 */     return new HeaderEntry<>(h, name, value, next, this.head);
/*      */   }
/*      */   
/*      */   protected ValueConverter<V> valueConverter() {
/* 1028 */     return this.valueConverter;
/*      */   }
/*      */   
/*      */   protected NameValidator<K> nameValidator() {
/* 1032 */     return this.nameValidator;
/*      */   }
/*      */   
/*      */   protected ValueValidator<V> valueValidator() {
/* 1036 */     return this.valueValidator;
/*      */   }
/*      */   
/*      */   private int index(int hash) {
/* 1040 */     return hash & this.hashMask;
/*      */   }
/*      */ 
/*      */   
/*      */   private void add0(int h, int i, K name, V value) {
/* 1045 */     this.entries[i] = newHeaderEntry(h, name, value, this.entries[i]);
/* 1046 */     this.size++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private V remove0(int h, int i, K name) {
/* 1053 */     HeaderEntry<K, V> e = this.entries[i];
/* 1054 */     if (e == null) {
/* 1055 */       return null;
/*      */     }
/*      */     
/* 1058 */     V value = null;
/* 1059 */     HeaderEntry<K, V> next = e.next;
/* 1060 */     while (next != null) {
/* 1061 */       if (next.hash == h && this.hashingStrategy.equals(name, next.key)) {
/* 1062 */         value = next.value;
/* 1063 */         e.next = next.next;
/* 1064 */         next.remove();
/* 1065 */         this.size--;
/*      */       } else {
/* 1067 */         e = next;
/*      */       } 
/*      */       
/* 1070 */       next = e.next;
/*      */     } 
/*      */     
/* 1073 */     e = this.entries[i];
/* 1074 */     if (e.hash == h && this.hashingStrategy.equals(name, e.key)) {
/* 1075 */       if (value == null) {
/* 1076 */         value = e.value;
/*      */       }
/* 1078 */       this.entries[i] = e.next;
/* 1079 */       e.remove();
/* 1080 */       this.size--;
/*      */     } 
/*      */     
/* 1083 */     return value;
/*      */   }
/*      */   
/*      */   HeaderEntry<K, V> remove0(HeaderEntry<K, V> entry, HeaderEntry<K, V> previous) {
/* 1087 */     int i = index(entry.hash);
/* 1088 */     HeaderEntry<K, V> firstEntry = this.entries[i];
/* 1089 */     if (firstEntry == entry) {
/* 1090 */       this.entries[i] = entry.next;
/* 1091 */       previous = this.entries[i];
/* 1092 */     } else if (previous == null) {
/*      */       
/* 1094 */       previous = firstEntry;
/* 1095 */       HeaderEntry<K, V> next = firstEntry.next;
/* 1096 */       while (next != null && next != entry) {
/* 1097 */         previous = next;
/* 1098 */         next = next.next;
/*      */       } 
/* 1100 */       assert next != null : "Entry not found in its hash bucket: " + entry;
/* 1101 */       previous.next = entry.next;
/*      */     } else {
/* 1103 */       previous.next = entry.next;
/*      */     } 
/* 1105 */     entry.remove();
/* 1106 */     this.size--;
/* 1107 */     return previous;
/*      */   }
/*      */ 
/*      */   
/*      */   private T thisT() {
/* 1112 */     return (T)this;
/*      */   }
/*      */   
/*      */   private V fromObject(K name, Object value) {
/*      */     try {
/* 1117 */       return this.valueConverter.convertObject(ObjectUtil.checkNotNull(value, "value"));
/* 1118 */     } catch (IllegalArgumentException e) {
/* 1119 */       throw new IllegalArgumentException("Failed to convert object value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromBoolean(K name, boolean value) {
/*      */     try {
/* 1125 */       return this.valueConverter.convertBoolean(value);
/* 1126 */     } catch (IllegalArgumentException e) {
/* 1127 */       throw new IllegalArgumentException("Failed to convert boolean value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromByte(K name, byte value) {
/*      */     try {
/* 1133 */       return this.valueConverter.convertByte(value);
/* 1134 */     } catch (IllegalArgumentException e) {
/* 1135 */       throw new IllegalArgumentException("Failed to convert byte value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromChar(K name, char value) {
/*      */     try {
/* 1141 */       return this.valueConverter.convertChar(value);
/* 1142 */     } catch (IllegalArgumentException e) {
/* 1143 */       throw new IllegalArgumentException("Failed to convert char value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromShort(K name, short value) {
/*      */     try {
/* 1149 */       return this.valueConverter.convertShort(value);
/* 1150 */     } catch (IllegalArgumentException e) {
/* 1151 */       throw new IllegalArgumentException("Failed to convert short value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromInt(K name, int value) {
/*      */     try {
/* 1157 */       return this.valueConverter.convertInt(value);
/* 1158 */     } catch (IllegalArgumentException e) {
/* 1159 */       throw new IllegalArgumentException("Failed to convert int value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromLong(K name, long value) {
/*      */     try {
/* 1165 */       return this.valueConverter.convertLong(value);
/* 1166 */     } catch (IllegalArgumentException e) {
/* 1167 */       throw new IllegalArgumentException("Failed to convert long value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromFloat(K name, float value) {
/*      */     try {
/* 1173 */       return this.valueConverter.convertFloat(value);
/* 1174 */     } catch (IllegalArgumentException e) {
/* 1175 */       throw new IllegalArgumentException("Failed to convert float value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromDouble(K name, double value) {
/*      */     try {
/* 1181 */       return this.valueConverter.convertDouble(value);
/* 1182 */     } catch (IllegalArgumentException e) {
/* 1183 */       throw new IllegalArgumentException("Failed to convert double value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private V fromTimeMillis(K name, long value) {
/*      */     try {
/* 1189 */       return this.valueConverter.convertTimeMillis(value);
/* 1190 */     } catch (IllegalArgumentException e) {
/* 1191 */       throw new IllegalArgumentException("Failed to convert millsecond value for header '" + name + '\'', e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean toBoolean(K name, V value) {
/*      */     try {
/* 1197 */       return this.valueConverter.convertToBoolean(value);
/* 1198 */     } catch (IllegalArgumentException e) {
/* 1199 */       throw new IllegalArgumentException("Failed to convert header value to boolean for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private byte toByte(K name, V value) {
/*      */     try {
/* 1205 */       return this.valueConverter.convertToByte(value);
/* 1206 */     } catch (IllegalArgumentException e) {
/* 1207 */       throw new IllegalArgumentException("Failed to convert header value to byte for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private char toChar(K name, V value) {
/*      */     try {
/* 1213 */       return this.valueConverter.convertToChar(value);
/* 1214 */     } catch (IllegalArgumentException e) {
/* 1215 */       throw new IllegalArgumentException("Failed to convert header value to char for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private short toShort(K name, V value) {
/*      */     try {
/* 1221 */       return this.valueConverter.convertToShort(value);
/* 1222 */     } catch (IllegalArgumentException e) {
/* 1223 */       throw new IllegalArgumentException("Failed to convert header value to short for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private int toInt(K name, V value) {
/*      */     try {
/* 1229 */       return this.valueConverter.convertToInt(value);
/* 1230 */     } catch (IllegalArgumentException e) {
/* 1231 */       throw new IllegalArgumentException("Failed to convert header value to int for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private long toLong(K name, V value) {
/*      */     try {
/* 1237 */       return this.valueConverter.convertToLong(value);
/* 1238 */     } catch (IllegalArgumentException e) {
/* 1239 */       throw new IllegalArgumentException("Failed to convert header value to long for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private float toFloat(K name, V value) {
/*      */     try {
/* 1245 */       return this.valueConverter.convertToFloat(value);
/* 1246 */     } catch (IllegalArgumentException e) {
/* 1247 */       throw new IllegalArgumentException("Failed to convert header value to float for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private double toDouble(K name, V value) {
/*      */     try {
/* 1253 */       return this.valueConverter.convertToDouble(value);
/* 1254 */     } catch (IllegalArgumentException e) {
/* 1255 */       throw new IllegalArgumentException("Failed to convert header value to double for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */   
/*      */   private long toTimeMillis(K name, V value) {
/*      */     try {
/* 1261 */       return this.valueConverter.convertToTimeMillis(value);
/* 1262 */     } catch (IllegalArgumentException e) {
/* 1263 */       throw new IllegalArgumentException("Failed to convert header value to millsecond for header '" + name + '\'');
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultHeaders<K, V, T> copy() {
/* 1272 */     DefaultHeaders<K, V, T> copy = new DefaultHeaders(this.hashingStrategy, this.valueConverter, this.nameValidator, this.entries.length);
/*      */     
/* 1274 */     copy.addImpl(this);
/* 1275 */     return copy;
/*      */   }
/*      */   
/*      */   private final class HeaderIterator implements Iterator<Map.Entry<K, V>> {
/* 1279 */     private DefaultHeaders.HeaderEntry<K, V> current = DefaultHeaders.this.head;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1283 */       return (this.current.after != DefaultHeaders.this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> next() {
/* 1288 */       this.current = this.current.after;
/*      */       
/* 1290 */       if (this.current == DefaultHeaders.this.head) {
/* 1291 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/* 1294 */       return this.current;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1299 */       throw new UnsupportedOperationException("read only");
/*      */     }
/*      */     
/*      */     private HeaderIterator() {}
/*      */   }
/*      */   
/*      */   private final class ValueIterator
/*      */     implements Iterator<V> {
/*      */     private final K name;
/*      */     private final int hash;
/*      */     
/*      */     ValueIterator(K name) {
/* 1311 */       this.name = (K)ObjectUtil.checkNotNull(name, "name");
/* 1312 */       this.hash = DefaultHeaders.this.hashingStrategy.hashCode(name);
/* 1313 */       calculateNext(DefaultHeaders.this.entries[DefaultHeaders.this.index(this.hash)]);
/*      */     }
/*      */     private DefaultHeaders.HeaderEntry<K, V> removalPrevious; private DefaultHeaders.HeaderEntry<K, V> previous; private DefaultHeaders.HeaderEntry<K, V> next;
/*      */     
/*      */     public boolean hasNext() {
/* 1318 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1323 */       if (!hasNext()) {
/* 1324 */         throw new NoSuchElementException();
/*      */       }
/* 1326 */       if (this.previous != null) {
/* 1327 */         this.removalPrevious = this.previous;
/*      */       }
/* 1329 */       this.previous = this.next;
/* 1330 */       calculateNext(this.next.next);
/* 1331 */       return this.previous.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1336 */       if (this.previous == null) {
/* 1337 */         throw new IllegalStateException();
/*      */       }
/* 1339 */       this.removalPrevious = DefaultHeaders.this.remove0(this.previous, this.removalPrevious);
/* 1340 */       this.previous = null;
/*      */     }
/*      */     
/*      */     private void calculateNext(DefaultHeaders.HeaderEntry<K, V> entry) {
/* 1344 */       while (entry != null) {
/* 1345 */         if (entry.hash == this.hash && DefaultHeaders.this.hashingStrategy.equals(this.name, entry.key)) {
/* 1346 */           this.next = entry;
/*      */           return;
/*      */         } 
/* 1349 */         entry = entry.next;
/*      */       } 
/* 1351 */       this.next = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected static class HeaderEntry<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     protected final int hash;
/*      */     
/*      */     protected final K key;
/*      */     
/*      */     protected V value;
/*      */     protected HeaderEntry<K, V> next;
/*      */     protected HeaderEntry<K, V> before;
/*      */     protected HeaderEntry<K, V> after;
/*      */     
/*      */     protected HeaderEntry(int hash, K key) {
/* 1369 */       this.hash = hash;
/* 1370 */       this.key = key;
/*      */     }
/*      */     
/*      */     HeaderEntry(int hash, K key, V value, HeaderEntry<K, V> next, HeaderEntry<K, V> head) {
/* 1374 */       this.hash = hash;
/* 1375 */       this.key = key;
/* 1376 */       this.value = value;
/* 1377 */       this.next = next;
/*      */       
/* 1379 */       this.after = head;
/* 1380 */       this.before = head.before;
/* 1381 */       pointNeighborsToThis();
/*      */     }
/*      */     
/*      */     HeaderEntry() {
/* 1385 */       this.hash = -1;
/* 1386 */       this.key = null;
/* 1387 */       this.before = this.after = this;
/*      */     }
/*      */     
/*      */     protected final void pointNeighborsToThis() {
/* 1391 */       this.before.after = this;
/* 1392 */       this.after.before = this;
/*      */     }
/*      */     
/*      */     public final HeaderEntry<K, V> before() {
/* 1396 */       return this.before;
/*      */     }
/*      */     
/*      */     public final HeaderEntry<K, V> after() {
/* 1400 */       return this.after;
/*      */     }
/*      */     
/*      */     protected void remove() {
/* 1404 */       this.before.after = this.after;
/* 1405 */       this.after.before = this.before;
/*      */     }
/*      */ 
/*      */     
/*      */     public final K getKey() {
/* 1410 */       return this.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public final V getValue() {
/* 1415 */       return this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public final V setValue(V value) {
/* 1420 */       ObjectUtil.checkNotNull(value, "value");
/* 1421 */       V oldValue = this.value;
/* 1422 */       this.value = value;
/* 1423 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public final String toString() {
/* 1428 */       return this.key.toString() + '=' + this.value.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1433 */       if (!(o instanceof Map.Entry)) {
/* 1434 */         return false;
/*      */       }
/* 1436 */       Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
/* 1437 */       return (((getKey() == null) ? (other.getKey() == null) : getKey().equals(other.getKey())) && (
/* 1438 */         (getValue() == null) ? (other.getValue() == null) : getValue().equals(other.getValue())));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1443 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\DefaultHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */