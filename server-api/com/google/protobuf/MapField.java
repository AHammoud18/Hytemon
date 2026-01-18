/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class MapField<K, V>
/*     */   extends MapFieldReflectionAccessor
/*     */   implements MutabilityOracle
/*     */ {
/*     */   private volatile boolean isMutable;
/*     */   private volatile StorageMode mode;
/*     */   private MutabilityAwareMap<K, V> mapData;
/*     */   private List<Message> listData;
/*     */   private final Converter<K, V> converter;
/*     */   
/*     */   private enum StorageMode
/*     */   {
/*  58 */     MAP,
/*  59 */     LIST,
/*  60 */     BOTH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ImmutableMessageConverter<K, V>
/*     */     implements Converter<K, V>
/*     */   {
/*     */     private final MapEntry<K, V> defaultEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableMessageConverter(MapEntry<K, V> defaultEntry) {
/*  81 */       this.defaultEntry = defaultEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public Message convertKeyAndValueToMessage(K key, V value) {
/*  86 */       return this.defaultEntry.newBuilderForType().setKey(key).setValue(value).buildPartial();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void convertMessageToKeyAndValue(Message message, Map<K, V> map) {
/*  92 */       MapEntry<K, V> entry = (MapEntry<K, V>)message;
/*  93 */       map.put(entry.getKey(), entry.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public Message getMessageDefaultInstance() {
/*  98 */       return this.defaultEntry;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MapField(Converter<K, V> converter, StorageMode mode, Map<K, V> mapData) {
/* 105 */     this.converter = converter;
/* 106 */     this.isMutable = true;
/* 107 */     this.mode = mode;
/* 108 */     this.mapData = new MutabilityAwareMap<>(this, mapData);
/* 109 */     this.listData = null;
/*     */   }
/*     */   
/*     */   private MapField(MapEntry<K, V> defaultEntry, StorageMode mode, Map<K, V> mapData) {
/* 113 */     this(new ImmutableMessageConverter<>(defaultEntry), mode, mapData);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <K, V> MapField<K, V> emptyMapField(MapEntry<K, V> defaultEntry) {
/* 118 */     return new MapField<>(defaultEntry, StorageMode.MAP, Collections.emptyMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public static <K, V> MapField<K, V> newMapField(MapEntry<K, V> defaultEntry) {
/* 123 */     return new MapField<>(defaultEntry, StorageMode.MAP, new LinkedHashMap<>());
/*     */   }
/*     */   
/*     */   private Message convertKeyAndValueToMessage(K key, V value) {
/* 127 */     return this.converter.convertKeyAndValueToMessage(key, value);
/*     */   }
/*     */   
/*     */   private void convertMessageToKeyAndValue(Message message, Map<K, V> map) {
/* 131 */     this.converter.convertMessageToKeyAndValue(message, map);
/*     */   }
/*     */   
/*     */   private List<Message> convertMapToList(MutabilityAwareMap<K, V> mapData) {
/* 135 */     List<Message> listData = new ArrayList<>();
/* 136 */     for (Map.Entry<K, V> entry : mapData.entrySet()) {
/* 137 */       listData.add(convertKeyAndValueToMessage(entry.getKey(), entry.getValue()));
/*     */     }
/* 139 */     return listData;
/*     */   }
/*     */   
/*     */   private MutabilityAwareMap<K, V> convertListToMap(List<Message> listData) {
/* 143 */     Map<K, V> mapData = new LinkedHashMap<>();
/* 144 */     for (Message item : listData) {
/* 145 */       convertMessageToKeyAndValue(item, mapData);
/*     */     }
/* 147 */     return new MutabilityAwareMap<>(this, mapData);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<K, V> getMap() {
/* 152 */     if (this.mode == StorageMode.LIST) {
/* 153 */       synchronized (this) {
/* 154 */         if (this.mode == StorageMode.LIST) {
/* 155 */           this.mapData = convertListToMap(this.listData);
/* 156 */           this.mode = StorageMode.BOTH;
/*     */         } 
/*     */       } 
/*     */     }
/* 160 */     return Collections.unmodifiableMap(this.mapData);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<K, V> getMutableMap() {
/* 165 */     if (this.mode != StorageMode.MAP) {
/* 166 */       if (this.mode == StorageMode.LIST) {
/* 167 */         this.mapData = convertListToMap(this.listData);
/*     */       }
/* 169 */       this.listData = null;
/* 170 */       this.mode = StorageMode.MAP;
/*     */     } 
/* 172 */     return this.mapData;
/*     */   }
/*     */   
/*     */   public void mergeFrom(MapField<K, V> other) {
/* 176 */     getMutableMap().putAll(MapFieldLite.copy(other.getMap()));
/*     */   }
/*     */   
/*     */   public void clear() {
/* 180 */     this.mapData = new MutabilityAwareMap<>(this, new LinkedHashMap<>());
/* 181 */     this.mode = StorageMode.MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 188 */     if (!(object instanceof MapField)) {
/* 189 */       return false;
/*     */     }
/* 191 */     MapField<K, V> other = (MapField<K, V>)object;
/* 192 */     return MapFieldLite.equals(getMap(), other.getMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     return MapFieldLite.calculateHashCodeForMap(getMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public MapField<K, V> copy() {
/* 202 */     return new MapField(this.converter, StorageMode.MAP, MapFieldLite.copy(getMap()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   List<Message> getList() {
/* 208 */     if (this.mode == StorageMode.MAP) {
/* 209 */       synchronized (this) {
/* 210 */         if (this.mode == StorageMode.MAP) {
/* 211 */           this.listData = convertMapToList(this.mapData);
/* 212 */           this.mode = StorageMode.BOTH;
/*     */         } 
/*     */       } 
/*     */     }
/* 216 */     return Collections.unmodifiableList(this.listData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   List<Message> getMutableList() {
/* 222 */     if (this.mode != StorageMode.LIST) {
/* 223 */       if (this.mode == StorageMode.MAP) {
/* 224 */         this.listData = convertMapToList(this.mapData);
/*     */       }
/* 226 */       this.mapData = null;
/* 227 */       this.mode = StorageMode.LIST;
/*     */     } 
/* 229 */     return this.listData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Message getMapEntryMessageDefaultInstance() {
/* 235 */     return this.converter.getMessageDefaultInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/* 243 */     this.isMutable = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMutable() {
/* 248 */     return this.isMutable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureMutable() {
/* 256 */     if (!isMutable())
/* 257 */       throw new UnsupportedOperationException(); 
/*     */   }
/*     */   
/*     */   static class MutabilityAwareMap<K, V>
/*     */     implements Map<K, V>
/*     */   {
/*     */     private final MutabilityOracle mutabilityOracle;
/*     */     private final Map<K, V> delegate;
/*     */     
/*     */     MutabilityAwareMap(MutabilityOracle mutabilityOracle, Map<K, V> delegate) {
/* 267 */       this.mutabilityOracle = mutabilityOracle;
/* 268 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 273 */       return this.delegate.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 278 */       return this.delegate.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 283 */       return this.delegate.containsKey(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 288 */       return this.delegate.containsValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(Object key) {
/* 293 */       return this.delegate.get(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(K key, V value) {
/* 298 */       this.mutabilityOracle.ensureMutable();
/* 299 */       Internal.checkNotNull(key);
/* 300 */       Internal.checkNotNull(value);
/* 301 */       return this.delegate.put(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(Object key) {
/* 306 */       this.mutabilityOracle.ensureMutable();
/* 307 */       return this.delegate.remove(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 312 */       this.mutabilityOracle.ensureMutable();
/* 313 */       for (K key : m.keySet()) {
/* 314 */         Internal.checkNotNull(key);
/* 315 */         Internal.checkNotNull(m.get(key));
/*     */       } 
/* 317 */       this.delegate.putAll(m);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 322 */       this.mutabilityOracle.ensureMutable();
/* 323 */       this.delegate.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<K> keySet() {
/* 328 */       return new MutabilityAwareSet<>(this.mutabilityOracle, this.delegate.keySet());
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<V> values() {
/* 333 */       return new MutabilityAwareCollection<>(this.mutabilityOracle, this.delegate.values());
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<K, V>> entrySet() {
/* 338 */       return new MutabilityAwareSet<>(this.mutabilityOracle, this.delegate.entrySet());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 344 */       return this.delegate.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 349 */       return this.delegate.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 354 */       return this.delegate.toString();
/*     */     }
/*     */     
/*     */     private static class MutabilityAwareCollection<E>
/*     */       implements Collection<E> {
/*     */       private final MutabilityOracle mutabilityOracle;
/*     */       private final Collection<E> delegate;
/*     */       
/*     */       MutabilityAwareCollection(MutabilityOracle mutabilityOracle, Collection<E> delegate) {
/* 363 */         this.mutabilityOracle = mutabilityOracle;
/* 364 */         this.delegate = delegate;
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/* 369 */         return this.delegate.size();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isEmpty() {
/* 374 */         return this.delegate.isEmpty();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean contains(Object o) {
/* 379 */         return this.delegate.contains(o);
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<E> iterator() {
/* 384 */         return new MapField.MutabilityAwareMap.MutabilityAwareIterator<>(this.mutabilityOracle, this.delegate.iterator());
/*     */       }
/*     */ 
/*     */       
/*     */       public Object[] toArray() {
/* 389 */         return this.delegate.toArray();
/*     */       }
/*     */ 
/*     */       
/*     */       public <T> T[] toArray(T[] a) {
/* 394 */         return this.delegate.toArray(a);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean add(E e) {
/* 400 */         throw new UnsupportedOperationException();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean remove(Object o) {
/* 405 */         this.mutabilityOracle.ensureMutable();
/* 406 */         return this.delegate.remove(o);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean containsAll(Collection<?> c) {
/* 411 */         return this.delegate.containsAll(c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean addAll(Collection<? extends E> c) {
/* 417 */         throw new UnsupportedOperationException();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean removeAll(Collection<?> c) {
/* 422 */         this.mutabilityOracle.ensureMutable();
/* 423 */         return this.delegate.removeAll(c);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean retainAll(Collection<?> c) {
/* 428 */         this.mutabilityOracle.ensureMutable();
/* 429 */         return this.delegate.retainAll(c);
/*     */       }
/*     */ 
/*     */       
/*     */       public void clear() {
/* 434 */         this.mutabilityOracle.ensureMutable();
/* 435 */         this.delegate.clear();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean equals(Object o) {
/* 441 */         return this.delegate.equals(o);
/*     */       }
/*     */ 
/*     */       
/*     */       public int hashCode() {
/* 446 */         return this.delegate.hashCode();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/* 451 */         return this.delegate.toString();
/*     */       }
/*     */     }
/*     */     
/*     */     private static class MutabilityAwareSet<E>
/*     */       implements Set<E> {
/*     */       private final MutabilityOracle mutabilityOracle;
/*     */       private final Set<E> delegate;
/*     */       
/*     */       MutabilityAwareSet(MutabilityOracle mutabilityOracle, Set<E> delegate) {
/* 461 */         this.mutabilityOracle = mutabilityOracle;
/* 462 */         this.delegate = delegate;
/*     */       }
/*     */ 
/*     */       
/*     */       public int size() {
/* 467 */         return this.delegate.size();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isEmpty() {
/* 472 */         return this.delegate.isEmpty();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean contains(Object o) {
/* 477 */         return this.delegate.contains(o);
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<E> iterator() {
/* 482 */         return new MapField.MutabilityAwareMap.MutabilityAwareIterator<>(this.mutabilityOracle, this.delegate.iterator());
/*     */       }
/*     */ 
/*     */       
/*     */       public Object[] toArray() {
/* 487 */         return this.delegate.toArray();
/*     */       }
/*     */ 
/*     */       
/*     */       public <T> T[] toArray(T[] a) {
/* 492 */         return this.delegate.toArray(a);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean add(E e) {
/* 497 */         this.mutabilityOracle.ensureMutable();
/* 498 */         return this.delegate.add(e);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean remove(Object o) {
/* 503 */         this.mutabilityOracle.ensureMutable();
/* 504 */         return this.delegate.remove(o);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean containsAll(Collection<?> c) {
/* 509 */         return this.delegate.containsAll(c);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean addAll(Collection<? extends E> c) {
/* 514 */         this.mutabilityOracle.ensureMutable();
/* 515 */         return this.delegate.addAll(c);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean retainAll(Collection<?> c) {
/* 520 */         this.mutabilityOracle.ensureMutable();
/* 521 */         return this.delegate.retainAll(c);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean removeAll(Collection<?> c) {
/* 526 */         this.mutabilityOracle.ensureMutable();
/* 527 */         return this.delegate.removeAll(c);
/*     */       }
/*     */ 
/*     */       
/*     */       public void clear() {
/* 532 */         this.mutabilityOracle.ensureMutable();
/* 533 */         this.delegate.clear();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean equals(Object o) {
/* 539 */         return this.delegate.equals(o);
/*     */       }
/*     */ 
/*     */       
/*     */       public int hashCode() {
/* 544 */         return this.delegate.hashCode();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/* 549 */         return this.delegate.toString();
/*     */       }
/*     */     }
/*     */     
/*     */     private static class MutabilityAwareIterator<E>
/*     */       implements Iterator<E> {
/*     */       private final MutabilityOracle mutabilityOracle;
/*     */       private final Iterator<E> delegate;
/*     */       
/*     */       MutabilityAwareIterator(MutabilityOracle mutabilityOracle, Iterator<E> delegate) {
/* 559 */         this.mutabilityOracle = mutabilityOracle;
/* 560 */         this.delegate = delegate;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 565 */         return this.delegate.hasNext();
/*     */       }
/*     */ 
/*     */       
/*     */       public E next() {
/* 570 */         return this.delegate.next();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 575 */         this.mutabilityOracle.ensureMutable();
/* 576 */         this.delegate.remove();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean equals(Object obj) {
/* 582 */         return this.delegate.equals(obj);
/*     */       }
/*     */ 
/*     */       
/*     */       public int hashCode() {
/* 587 */         return this.delegate.hashCode();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/* 592 */         return this.delegate.toString();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface Converter<K, V> {
/*     */     Message convertKeyAndValueToMessage(K param1K, V param1V);
/*     */     
/*     */     void convertMessageToKeyAndValue(Message param1Message, Map<K, V> param1Map);
/*     */     
/*     */     Message getMessageDefaultInstance();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */