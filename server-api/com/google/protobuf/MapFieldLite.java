/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public final class MapFieldLite<K, V>
/*     */   extends LinkedHashMap<K, V>
/*     */ {
/*     */   private boolean isMutable;
/*     */   
/*     */   private MapFieldLite() {
/*  29 */     this.isMutable = true;
/*     */   }
/*     */   
/*     */   private MapFieldLite(Map<K, V> mapData) {
/*  33 */     super(mapData);
/*  34 */     this.isMutable = true;
/*     */   }
/*     */   
/*  37 */   private static final MapFieldLite<?, ?> EMPTY_MAP_FIELD = new MapFieldLite();
/*     */   
/*     */   static {
/*  40 */     EMPTY_MAP_FIELD.makeImmutable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> MapFieldLite<K, V> emptyMapField() {
/*  46 */     return (MapFieldLite)EMPTY_MAP_FIELD;
/*     */   }
/*     */   
/*     */   public void mergeFrom(MapFieldLite<K, V> other) {
/*  50 */     ensureMutable();
/*  51 */     if (!other.isEmpty()) {
/*  52 */       putAll(other);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/*  58 */     return isEmpty() ? Collections.<Map.Entry<K, V>>emptySet() : super.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  63 */     ensureMutable();
/*  64 */     super.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/*  69 */     ensureMutable();
/*  70 */     Internal.checkNotNull(key);
/*     */     
/*  72 */     Internal.checkNotNull(value);
/*  73 */     return super.put(key, value);
/*     */   }
/*     */   
/*     */   public V put(Map.Entry<K, V> entry) {
/*  77 */     return put(entry.getKey(), entry.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> m) {
/*  82 */     ensureMutable();
/*  83 */     checkForNullKeysAndValues(m);
/*  84 */     super.putAll(m);
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object key) {
/*  89 */     ensureMutable();
/*  90 */     return super.remove(key);
/*     */   }
/*     */   
/*     */   private static void checkForNullKeysAndValues(Map<?, ?> m) {
/*  94 */     for (Object key : m.keySet()) {
/*  95 */       Internal.checkNotNull(key);
/*  96 */       Internal.checkNotNull(m.get(key));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(Object a, Object b) {
/* 103 */     if (a instanceof byte[] && b instanceof byte[]) {
/* 104 */       return Arrays.equals((byte[])a, (byte[])b);
/*     */     }
/* 106 */     return a.equals(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K, V> boolean equals(Map<K, V> a, Map<K, V> b) {
/* 114 */     if (a == b) {
/* 115 */       return true;
/*     */     }
/* 117 */     if (a.size() != b.size()) {
/* 118 */       return false;
/*     */     }
/* 120 */     for (Map.Entry<K, V> entry : a.entrySet()) {
/* 121 */       if (!b.containsKey(entry.getKey())) {
/* 122 */         return false;
/*     */       }
/* 124 */       if (!equals(entry.getValue(), b.get(entry.getKey()))) {
/* 125 */         return false;
/*     */       }
/*     */     } 
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 136 */     return (object instanceof Map && equals(this, (Map<K, V>)object));
/*     */   }
/*     */   
/*     */   private static int calculateHashCodeForObject(Object a) {
/* 140 */     if (a instanceof byte[]) {
/* 141 */       return Internal.hashCode((byte[])a);
/*     */     }
/*     */     
/* 144 */     if (a instanceof Internal.EnumLite) {
/* 145 */       throw new UnsupportedOperationException();
/*     */     }
/* 147 */     return a.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K, V> int calculateHashCodeForMap(Map<K, V> a) {
/* 155 */     int result = 0;
/* 156 */     for (Map.Entry<K, V> entry : a.entrySet()) {
/* 157 */       result += 
/* 158 */         calculateHashCodeForObject(entry.getKey()) ^ calculateHashCodeForObject(entry.getValue());
/*     */     }
/* 160 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 165 */     return calculateHashCodeForMap(this);
/*     */   }
/*     */   
/*     */   private static Object copy(Object object) {
/* 169 */     if (object instanceof byte[]) {
/* 170 */       byte[] data = (byte[])object;
/* 171 */       return Arrays.copyOf(data, data.length);
/*     */     } 
/* 173 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K, V> Map<K, V> copy(Map<K, V> map) {
/* 183 */     Map<K, V> result = new LinkedHashMap<>(map.size() * 4 / 3 + 1);
/* 184 */     for (Map.Entry<K, V> entry : map.entrySet()) {
/* 185 */       result.put(entry.getKey(), (V)copy(entry.getValue()));
/*     */     }
/* 187 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapFieldLite<K, V> mutableCopy() {
/* 192 */     return isEmpty() ? new MapFieldLite() : new MapFieldLite(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/* 200 */     this.isMutable = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMutable() {
/* 205 */     return this.isMutable;
/*     */   }
/*     */   
/*     */   private void ensureMutable() {
/* 209 */     if (!isMutable())
/* 210 */       throw new UnsupportedOperationException(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapFieldLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */