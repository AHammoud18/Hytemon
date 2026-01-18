/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.AbstractList;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Internal
/*     */ {
/*  35 */   static final Charset UTF_8 = Charset.forName("UTF-8");
/*  36 */   static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
/*     */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*     */   
/*     */   static <T> T checkNotNull(T obj) {
/*  40 */     if (obj == null) {
/*  41 */       throw new NullPointerException();
/*     */     }
/*  43 */     return obj;
/*     */   }
/*     */ 
/*     */   
/*     */   static <T> T checkNotNull(T obj, String message) {
/*  48 */     if (obj == null) {
/*  49 */       throw new NullPointerException(message);
/*     */     }
/*  51 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stringDefaultValue(String bytes) {
/*  77 */     return new String(bytes.getBytes(ISO_8859_1), UTF_8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteString bytesDefaultValue(String bytes) {
/*  88 */     return ByteString.copyFrom(bytes.getBytes(ISO_8859_1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] byteArrayDefaultValue(String bytes) {
/*  96 */     return bytes.getBytes(ISO_8859_1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuffer byteBufferDefaultValue(String bytes) {
/* 105 */     return ByteBuffer.wrap(byteArrayDefaultValue(bytes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuffer copyByteBuffer(ByteBuffer source) {
/* 116 */     ByteBuffer temp = source.duplicate();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     temp.clear();
/* 122 */     ByteBuffer result = ByteBuffer.allocate(temp.capacity());
/* 123 */     result.put(temp);
/* 124 */     result.clear();
/* 125 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidUtf8(ByteString byteString) {
/* 155 */     return byteString.isValidUtf8();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidUtf8(byte[] byteArray) {
/* 160 */     return Utf8.isValidUtf8(byteArray);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] toByteArray(String value) {
/* 165 */     return value.getBytes(UTF_8);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringUtf8(byte[] bytes) {
/* 170 */     return new String(bytes, UTF_8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashLong(long n) {
/* 204 */     return (int)(n ^ n >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashBoolean(boolean b) {
/* 213 */     return b ? 1231 : 1237;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashEnum(EnumLite e) {
/* 224 */     return e.getNumber();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int hashEnumList(List<? extends EnumLite> list) {
/* 229 */     int hash = 1;
/* 230 */     for (EnumLite e : list) {
/* 231 */       hash = 31 * hash + hashEnum(e);
/*     */     }
/* 233 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equals(List<byte[]> a, List<byte[]> b) {
/* 238 */     if (a.size() != b.size()) {
/* 239 */       return false;
/*     */     }
/* 241 */     for (int i = 0; i < a.size(); i++) {
/* 242 */       if (!Arrays.equals(a.get(i), b.get(i))) {
/* 243 */         return false;
/*     */       }
/*     */     } 
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int hashCode(List<byte[]> list) {
/* 251 */     int hash = 1;
/* 252 */     for (byte[] bytes : list) {
/* 253 */       hash = 31 * hash + hashCode(bytes);
/*     */     }
/* 255 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashCode(byte[] bytes) {
/* 264 */     return hashCode(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int hashCode(byte[] bytes, int offset, int length) {
/* 273 */     int h = partialHash(length, bytes, offset, length);
/* 274 */     return (h == 0) ? 1 : h;
/*     */   }
/*     */ 
/*     */   
/*     */   static int partialHash(int h, byte[] bytes, int offset, int length) {
/* 279 */     for (int i = offset; i < offset + length; i++) {
/* 280 */       h = h * 31 + bytes[i];
/*     */     }
/* 282 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsByteBuffer(ByteBuffer a, ByteBuffer b) {
/* 287 */     if (a.capacity() != b.capacity()) {
/* 288 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 292 */     ByteBuffer aDuplicate = a.duplicate();
/* 293 */     Java8Compatibility.clear(aDuplicate);
/* 294 */     ByteBuffer bDuplicate = b.duplicate();
/* 295 */     Java8Compatibility.clear(bDuplicate);
/* 296 */     return aDuplicate.equals(bDuplicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsByteBuffer(List<ByteBuffer> a, List<ByteBuffer> b) {
/* 301 */     if (a.size() != b.size()) {
/* 302 */       return false;
/*     */     }
/* 304 */     for (int i = 0; i < a.size(); i++) {
/* 305 */       if (!equalsByteBuffer(a.get(i), b.get(i))) {
/* 306 */         return false;
/*     */       }
/*     */     } 
/* 309 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int hashCodeByteBuffer(List<ByteBuffer> list) {
/* 314 */     int hash = 1;
/* 315 */     for (ByteBuffer bytes : list) {
/* 316 */       hash = 31 * hash + hashCodeByteBuffer(bytes);
/*     */     }
/* 318 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashCodeByteBuffer(ByteBuffer bytes) {
/* 325 */     if (bytes.hasArray()) {
/*     */       
/* 327 */       int i = partialHash(bytes.capacity(), bytes.array(), bytes.arrayOffset(), bytes.capacity());
/* 328 */       return (i == 0) ? 1 : i;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 333 */     int bufferSize = (bytes.capacity() > 4096) ? 4096 : bytes.capacity();
/* 334 */     byte[] buffer = new byte[bufferSize];
/* 335 */     ByteBuffer duplicated = bytes.duplicate();
/* 336 */     Java8Compatibility.clear(duplicated);
/* 337 */     int h = bytes.capacity();
/* 338 */     while (duplicated.remaining() > 0) {
/*     */       
/* 340 */       int length = (duplicated.remaining() <= bufferSize) ? duplicated.remaining() : bufferSize;
/* 341 */       duplicated.get(buffer, 0, length);
/* 342 */       h = partialHash(h, buffer, 0, length);
/*     */     } 
/* 344 */     return (h == 0) ? 1 : h;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends MessageLite> T getDefaultInstance(Class<T> clazz) {
/*     */     try {
/* 351 */       Method method = clazz.getMethod("getDefaultInstance", new Class[0]);
/* 352 */       return (T)method.invoke(method, new Object[0]);
/* 353 */     } catch (Exception e) {
/* 354 */       throw new RuntimeException("Failed to get default instance for " + clazz, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 359 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*     */ 
/*     */   
/* 362 */   public static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.wrap(EMPTY_BYTE_ARRAY);
/*     */ 
/*     */ 
/*     */   
/* 366 */   public static final CodedInputStream EMPTY_CODED_INPUT_STREAM = CodedInputStream.newInstance(EMPTY_BYTE_ARRAY);
/*     */ 
/*     */   
/*     */   static Object mergeMessage(Object destination, Object source) {
/* 370 */     return ((MessageLite)destination).toBuilder().mergeFrom((MessageLite)source).buildPartial();
/*     */   }
/*     */   
/*     */   public static interface EnumLiteMap<T extends EnumLite>
/*     */   {
/*     */     T findValueByNumber(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface EnumVerifier {
/*     */     boolean isInRange(int param1Int);
/*     */   }
/*     */   
/*     */   public static class IntListAdapter<T>
/*     */     extends AbstractList<T> {
/*     */     private final Internal.IntList fromList;
/*     */     private final IntConverter<T> converter;
/*     */     
/*     */     public IntListAdapter(Internal.IntList fromList, IntConverter<T> converter) {
/* 388 */       this.fromList = fromList;
/* 389 */       this.converter = converter;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(int index) {
/* 394 */       return this.converter.convert(this.fromList.getInt(index));
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 399 */       return this.fromList.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public static interface IntConverter<T>
/*     */     {
/*     */       T convert(int param2Int);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ListAdapter<F, T>
/*     */     extends AbstractList<T>
/*     */   {
/*     */     private final List<F> fromList;
/*     */     
/*     */     private final Converter<F, T> converter;
/*     */     
/*     */     public ListAdapter(List<F> fromList, Converter<F, T> converter) {
/* 418 */       this.fromList = fromList;
/* 419 */       this.converter = converter;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(int index) {
/* 424 */       return this.converter.convert(this.fromList.get(index));
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 429 */       return this.fromList.size();
/*     */     }
/*     */     
/*     */     public static interface Converter<F, T>
/*     */     {
/*     */       T convert(F param2F);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MapAdapter<K, V, RealValue>
/*     */     extends AbstractMap<K, V> {
/*     */     private final Map<K, RealValue> realMap;
/*     */     private final Converter<RealValue, V> valueConverter;
/*     */     
/*     */     public static <T extends Internal.EnumLite> Converter<Integer, T> newEnumConverter(final Internal.EnumLiteMap<T> enumMap, final T unrecognizedValue) {
/* 444 */       return new Converter<Integer, T>()
/*     */         {
/*     */           public T doForward(Integer value) {
/* 447 */             T result = (T)enumMap.findValueByNumber(value.intValue());
/* 448 */             return (result == null) ? (T)unrecognizedValue : result;
/*     */           }
/*     */ 
/*     */           
/*     */           public Integer doBackward(T value) {
/* 453 */             return Integer.valueOf(value.getNumber());
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MapAdapter(Map<K, RealValue> realMap, Converter<RealValue, V> valueConverter) {
/* 462 */       this.realMap = realMap;
/* 463 */       this.valueConverter = valueConverter;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(Object key) {
/* 468 */       RealValue result = this.realMap.get(key);
/* 469 */       if (result == null) {
/* 470 */         return null;
/*     */       }
/* 472 */       return this.valueConverter.doForward(result);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(K key, V value) {
/* 477 */       RealValue oldValue = this.realMap.put(key, this.valueConverter.doBackward(value));
/* 478 */       if (oldValue == null) {
/* 479 */         return null;
/*     */       }
/* 481 */       return this.valueConverter.doForward(oldValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<K, V>> entrySet() {
/* 486 */       return new SetAdapter(this.realMap.entrySet());
/*     */     }
/*     */     
/*     */     private class SetAdapter extends AbstractSet<Map.Entry<K, V>> {
/*     */       private final Set<Map.Entry<K, RealValue>> realSet;
/*     */       
/*     */       public SetAdapter(Set<Map.Entry<K, RealValue>> realSet) {
/* 493 */         this.realSet = realSet;
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<Map.Entry<K, V>> iterator() {
/* 498 */         return new Internal.MapAdapter.IteratorAdapter(this.realSet.iterator());
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/* 503 */         return this.realSet.size();
/*     */       }
/*     */     }
/*     */     
/*     */     private class IteratorAdapter implements Iterator<Map.Entry<K, V>> {
/*     */       private final Iterator<Map.Entry<K, RealValue>> realIterator;
/*     */       
/*     */       public IteratorAdapter(Iterator<Map.Entry<K, RealValue>> realIterator) {
/* 511 */         this.realIterator = realIterator;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 516 */         return this.realIterator.hasNext();
/*     */       }
/*     */ 
/*     */       
/*     */       public Map.Entry<K, V> next() {
/* 521 */         return new Internal.MapAdapter.EntryAdapter(this.realIterator.next());
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 526 */         this.realIterator.remove();
/*     */       }
/*     */     }
/*     */     
/*     */     private class EntryAdapter implements Map.Entry<K, V> {
/*     */       private final Map.Entry<K, RealValue> realEntry;
/*     */       
/*     */       public EntryAdapter(Map.Entry<K, RealValue> realEntry) {
/* 534 */         this.realEntry = realEntry;
/*     */       }
/*     */ 
/*     */       
/*     */       public K getKey() {
/* 539 */         return this.realEntry.getKey();
/*     */       }
/*     */ 
/*     */       
/*     */       public V getValue() {
/* 544 */         return (V)Internal.MapAdapter.this.valueConverter.doForward(this.realEntry.getValue());
/*     */       }
/*     */ 
/*     */       
/*     */       public V setValue(V value) {
/* 549 */         RealValue oldValue = this.realEntry.setValue((RealValue)Internal.MapAdapter.this.valueConverter.doBackward(value));
/* 550 */         if (oldValue == null) {
/* 551 */           return null;
/*     */         }
/* 553 */         return (V)Internal.MapAdapter.this.valueConverter.doForward(oldValue);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean equals(Object o) {
/* 559 */         if (o == this) {
/* 560 */           return true;
/*     */         }
/* 562 */         if (!(o instanceof Map.Entry)) {
/* 563 */           return false;
/*     */         }
/* 565 */         Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
/* 566 */         return (getKey().equals(other.getKey()) && getValue().equals(getValue()));
/*     */       }
/*     */ 
/*     */       
/*     */       public int hashCode() {
/* 571 */         return this.realEntry.hashCode();
/*     */       }
/*     */     }
/*     */     
/*     */     public static interface Converter<A, B> {
/*     */       B doForward(A param2A);
/*     */       
/*     */       A doBackward(B param2B);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface ProtobufList<E> extends List<E>, RandomAccess {
/*     */     void makeImmutable();
/*     */     
/*     */     boolean isModifiable();
/*     */     
/*     */     ProtobufList<E> mutableCopyWithCapacity(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface IntList extends ProtobufList<Integer> {
/*     */     int getInt(int param1Int);
/*     */     
/*     */     void addInt(int param1Int);
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     int setInt(int param1Int1, int param1Int2);
/*     */     
/*     */     IntList mutableCopyWithCapacity(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface BooleanList extends ProtobufList<Boolean> {
/*     */     boolean getBoolean(int param1Int);
/*     */     
/*     */     void addBoolean(boolean param1Boolean);
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     boolean setBoolean(int param1Int, boolean param1Boolean);
/*     */     
/*     */     BooleanList mutableCopyWithCapacity(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface LongList extends ProtobufList<Long> {
/*     */     long getLong(int param1Int);
/*     */     
/*     */     void addLong(long param1Long);
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     long setLong(int param1Int, long param1Long);
/*     */     
/*     */     LongList mutableCopyWithCapacity(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface DoubleList extends ProtobufList<Double> {
/*     */     double getDouble(int param1Int);
/*     */     
/*     */     void addDouble(double param1Double);
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     double setDouble(int param1Int, double param1Double);
/*     */     
/*     */     DoubleList mutableCopyWithCapacity(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface FloatList extends ProtobufList<Float> {
/*     */     float getFloat(int param1Int);
/*     */     
/*     */     void addFloat(float param1Float);
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     float setFloat(int param1Int, float param1Float);
/*     */     
/*     */     FloatList mutableCopyWithCapacity(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface EnumLite {
/*     */     int getNumber();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Internal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */