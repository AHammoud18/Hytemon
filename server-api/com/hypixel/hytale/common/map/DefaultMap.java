/*     */ package com.hypixel.hytale.common.map;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   private final Map<K, V> delegate;
/*     */   private final boolean allowReplacing;
/*     */   private final boolean replaceNullWithDefault;
/*     */   private V defaultValue;
/*     */   
/*     */   public DefaultMap(V defaultValue) {
/*  26 */     this(defaultValue, new HashMap<>());
/*     */   }
/*     */   
/*     */   public DefaultMap(V defaultValue, Map<K, V> delegate) {
/*  30 */     this(defaultValue, delegate, true);
/*     */   }
/*     */   
/*     */   public DefaultMap(V defaultValue, Map<K, V> delegate, boolean allowReplacing) {
/*  34 */     this(defaultValue, delegate, allowReplacing, true);
/*     */   }
/*     */   
/*     */   public DefaultMap(V defaultValue, Map<K, V> delegate, boolean allowReplacing, boolean replaceNullWithDefault) {
/*  38 */     this.defaultValue = defaultValue;
/*  39 */     this.delegate = delegate;
/*  40 */     this.allowReplacing = allowReplacing;
/*  41 */     this.replaceNullWithDefault = replaceNullWithDefault;
/*     */   }
/*     */   
/*     */   public V getDefaultValue() {
/*  45 */     return this.defaultValue;
/*     */   }
/*     */   
/*     */   public void setDefaultValue(V defaultValue) {
/*  49 */     this.defaultValue = defaultValue;
/*     */   }
/*     */   
/*     */   public Map<K, V> getDelegate() {
/*  53 */     return this.delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  58 */     return this.delegate.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  63 */     return this.delegate.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  68 */     return this.delegate.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  73 */     return this.delegate.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(@Nullable Object key) {
/*  78 */     if (this.replaceNullWithDefault && key == null) return this.defaultValue; 
/*  79 */     V value = this.delegate.get(key);
/*  80 */     return (value != null) ? value : this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/*  85 */     if (this.allowReplacing) return this.delegate.put(key, value);
/*     */     
/*  87 */     V oldValue = this.delegate.putIfAbsent(key, value);
/*  88 */     if (oldValue == null) return null; 
/*  89 */     throw new IllegalArgumentException("Attachment (" + String.valueOf(key) + ") is already registered!");
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object key) {
/*  94 */     return this.delegate.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(@Nonnull Map<? extends K, ? extends V> m) {
/*  99 */     this.delegate.putAll(m);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 104 */     this.delegate.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<K> keySet() {
/* 110 */     return this.delegate.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Collection<V> values() {
/* 116 */     return this.delegate.values();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 122 */     return this.delegate.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 127 */     if (this == o) return true; 
/* 128 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 130 */     DefaultMap<?, ?> that = (DefaultMap<?, ?>)o;
/*     */     
/* 132 */     if (this.allowReplacing != that.allowReplacing) return false; 
/* 133 */     if (this.replaceNullWithDefault != that.replaceNullWithDefault) return false; 
/* 134 */     if ((this.delegate != null) ? !this.delegate.equals(that.delegate) : (that.delegate != null)) return false; 
/* 135 */     return (this.defaultValue != null) ? this.defaultValue.equals(that.defaultValue) : ((that.defaultValue == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     int result = (this.delegate != null) ? this.delegate.hashCode() : 0;
/* 141 */     result = 31 * result + (this.allowReplacing ? 1 : 0);
/* 142 */     result = 31 * result + (this.replaceNullWithDefault ? 1 : 0);
/* 143 */     result = 31 * result + ((this.defaultValue != null) ? this.defaultValue.hashCode() : 0);
/* 144 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public V getOrDefault(Object key, V defaultValue) {
/* 149 */     return this.delegate.getOrDefault(key, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(BiConsumer<? super K, ? super V> action) {
/* 154 */     this.delegate.forEach(action);
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 159 */     this.delegate.replaceAll(function);
/*     */   }
/*     */ 
/*     */   
/*     */   public V putIfAbsent(K key, V value) {
/* 164 */     return this.delegate.putIfAbsent(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object key, Object value) {
/* 169 */     return this.delegate.remove(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replace(K key, V oldValue, V newValue) {
/* 174 */     return this.delegate.replace(key, oldValue, newValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public V replace(K key, V value) {
/* 179 */     return this.delegate.replace(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public V computeIfAbsent(K key, @Nonnull Function<? super K, ? extends V> mappingFunction) {
/* 184 */     return this.delegate.computeIfAbsent(key, mappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public V computeIfPresent(K key, @Nonnull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 190 */     return this.delegate.computeIfPresent(key, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   public V compute(K key, @Nonnull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 195 */     return this.delegate.compute(key, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   public V merge(K key, @Nonnull V value, @Nonnull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 200 */     return this.delegate.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 206 */     return "DefaultMap{defaultValue=" + String.valueOf(this.defaultValue) + ", delegate=" + String.valueOf(this.delegate) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\map\DefaultMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */