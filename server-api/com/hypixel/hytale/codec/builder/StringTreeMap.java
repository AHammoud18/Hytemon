/*     */ package com.hypixel.hytale.codec.builder;
/*     */ 
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class StringTreeMap<V>
/*     */ {
/*     */   public static final int STRING_PART_SIZE = 4;
/*     */   private Long2ObjectMap<StringTreeMap<V>> map;
/*     */   @Nullable
/*     */   private String key;
/*     */   @Nullable
/*     */   private V value;
/*     */   
/*     */   public StringTreeMap() {}
/*     */   
/*     */   public StringTreeMap(@Nonnull StringTreeMap<V> parent) {
/*  25 */     if (parent.map != null) {
/*  26 */       this.map = (Long2ObjectMap<StringTreeMap<V>>)new Long2ObjectOpenHashMap(parent.map.size());
/*  27 */       for (ObjectIterator<Long2ObjectMap.Entry<StringTreeMap<V>>> objectIterator = parent.map.long2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<StringTreeMap<V>> entry = objectIterator.next();
/*  28 */         this.map.put(entry.getLongKey(), new StringTreeMap((StringTreeMap<V>)entry.getValue())); }
/*     */     
/*     */     } 
/*  31 */     this.key = parent.key;
/*  32 */     this.value = parent.value;
/*     */   }
/*     */   
/*     */   public StringTreeMap(@Nonnull Map<String, V> entries) {
/*  36 */     putAll(entries);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getKey() {
/*  41 */     return this.key;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public V getValue() {
/*  46 */     return this.value;
/*     */   }
/*     */   
/*     */   public void putAll(@Nonnull Map<String, V> entries) {
/*  50 */     for (Map.Entry<String, V> entry : entries.entrySet()) {
/*  51 */       put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void put(@Nonnull String key, V values) {
/*  56 */     put0(key, values, 0, key.length());
/*     */   }
/*     */ 
/*     */   
/*     */   private void put0(@Nonnull String key, V fields, int start, int end) {
/*  61 */     if (this.map == null && (this.key == null || this.key.equals(key))) {
/*  62 */       this.key = key;
/*  63 */       this.value = fields;
/*     */       
/*     */       return;
/*     */     } 
/*  67 */     if (start >= end) {
/*     */       
/*  69 */       if (this.key != null && this.key.length() > start) {
/*  70 */         String oldKey = this.key;
/*  71 */         V oldFields = this.value;
/*     */         
/*  73 */         this.key = key;
/*  74 */         this.value = fields;
/*     */         
/*  76 */         put0(oldKey, oldFields, start, oldKey.length());
/*     */       } else {
/*  78 */         if (this.key != null && !this.key.equals(key)) throw new IllegalStateException("Keys don't match: " + this.key + " != " + key); 
/*  79 */         this.key = key;
/*  80 */         this.value = fields;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  85 */     long part = readStringPartAsLong(key, start, end);
/*     */     
/*  87 */     if (this.map == null) this.map = (Long2ObjectMap<StringTreeMap<V>>)new Long2ObjectOpenHashMap(); 
/*  88 */     ((StringTreeMap<V>)this.map.computeIfAbsent(part, k -> new StringTreeMap())).put0(key, fields, start + 4, end);
/*     */ 
/*     */     
/*  91 */     if (this.key != null && this.key.length() > start) {
/*  92 */       String oldKey = this.key;
/*  93 */       V oldFields = this.value;
/*     */       
/*  95 */       this.key = null;
/*  96 */       this.value = null;
/*     */       
/*  98 */       put0(oldKey, oldFields, start, oldKey.length());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(@Nonnull String key) {
/* 103 */     if (this.map == null) {
/* 104 */       if (this.key == null)
/* 105 */         return;  if (this.key.equals(key)) {
/* 106 */         this.key = null;
/* 107 */         this.value = null;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     remove0(key, 0, key.length());
/*     */   }
/*     */   
/*     */   protected void remove0(@Nonnull String key, int start, int end) {
/* 116 */     long part = readStringPartAsLong(key, start, end);
/*     */     
/* 118 */     StringTreeMap<V> entry = (StringTreeMap<V>)this.map.get(part);
/* 119 */     if (entry == null)
/*     */       return; 
/* 121 */     int newStart = start + 4;
/* 122 */     if (newStart >= end) {
/* 123 */       this.map.remove(part);
/*     */       
/*     */       return;
/*     */     } 
/* 127 */     if (entry.map == null) {
/* 128 */       if (entry.key == null) throw new IllegalStateException("Incorrectly built tree!"); 
/* 129 */       if (entry.key.equals(key)) {
/* 130 */         this.map.remove(part);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 135 */     entry.remove0(key, newStart, end);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public StringTreeMap<V> findEntry(@Nonnull RawJsonReader reader) throws IOException {
/* 140 */     reader.expect('"');
/* 141 */     int end = reader.findOffset('"');
/* 142 */     return findEntry0(reader, null, end);
/*     */   }
/*     */   
/*     */   public StringTreeMap<V> findEntryOrDefault(@Nonnull RawJsonReader reader, StringTreeMap<V> def) throws IOException {
/* 146 */     reader.expect('"');
/* 147 */     int end = reader.findOffset('"');
/* 148 */     return findEntry0(reader, def, end);
/*     */   }
/*     */   
/*     */   protected StringTreeMap<V> findEntry0(@Nonnull RawJsonReader reader, StringTreeMap<V> def, int end) throws IOException {
/* 152 */     if (this.map == null) {
/* 153 */       if (this.key == null) {
/* 154 */         reader.skipRemainingString();
/* 155 */         return def;
/*     */       } 
/* 157 */       return consumeEntryKey(reader, def, end, this);
/*     */     } 
/*     */     
/* 160 */     long part = reader.readStringPartAsLong(Math.min(end, 4));
/* 161 */     int newEnd = Math.max(end - 4, 0);
/*     */     
/* 163 */     StringTreeMap<V> entry = (StringTreeMap<V>)this.map.get(part);
/*     */ 
/*     */     
/* 166 */     if (entry == null) {
/* 167 */       if (newEnd != 0) {
/* 168 */         reader.skipRemainingString();
/*     */       } else {
/* 170 */         reader.expect('"');
/*     */       } 
/* 172 */       return def;
/*     */     } 
/*     */     
/* 175 */     if (newEnd == 0) {
/* 176 */       reader.expect('"');
/* 177 */       return entry;
/*     */     } 
/*     */     
/* 180 */     if (entry.map == null) {
/* 181 */       if (entry.key == null) throw new IllegalStateException("Incorrectly built tree!");
/*     */       
/* 183 */       return consumeEntryKey(reader, def, newEnd, entry);
/*     */     } 
/*     */     
/* 186 */     return entry.findEntry0(reader, def, newEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   private StringTreeMap<V> consumeEntryKey(@Nonnull RawJsonReader reader, StringTreeMap<V> def, int end, @Nonnull StringTreeMap<V> entry) throws IOException {
/* 191 */     int keyLength = entry.key.length();
/* 192 */     if (keyLength < end) {
/* 193 */       reader.skipRemainingString();
/* 194 */       return def;
/*     */     } 
/*     */     
/* 197 */     if (!reader.tryConsume(entry.key, keyLength - end)) {
/* 198 */       reader.skipRemainingString();
/* 199 */       return def;
/*     */     } 
/*     */ 
/*     */     
/* 203 */     if (!reader.tryConsume('"')) {
/* 204 */       reader.skipRemainingString();
/* 205 */       return def;
/*     */     } 
/*     */     
/* 208 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 214 */     return "StringTreeMap{map=" + String.valueOf(this.map) + ", key='" + this.key + "', value=" + String.valueOf(this.value) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long readStringPartAsLong(@Nonnull String key, int start, int end) {
/* 222 */     int length = end - start;
/*     */     
/* 224 */     char c1 = key.charAt(start);
/* 225 */     if (length == 1) return c1;
/*     */     
/* 227 */     char c2 = key.charAt(start + 1);
/* 228 */     long value = c1 | c2 << 16L;
/* 229 */     if (length == 2) return value;
/*     */     
/* 231 */     char c3 = key.charAt(start + 2);
/* 232 */     value |= c3 << 32L;
/* 233 */     if (length == 3) return value;
/*     */     
/* 235 */     char c4 = key.charAt(start + 3);
/* 236 */     return value | c4 << 48L;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\builder\StringTreeMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */