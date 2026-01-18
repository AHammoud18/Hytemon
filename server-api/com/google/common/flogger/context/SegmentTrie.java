/*     */ package com.google.common.flogger.context;
/*     */ 
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
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
/*     */ abstract class SegmentTrie<T>
/*     */ {
/*     */   private final T defaultValue;
/*     */   
/*     */   public static <T> SegmentTrie<T> create(Map<String, ? extends T> map, char separator, T defaultValue) {
/*     */     Map.Entry<String, ? extends T> e;
/*  70 */     switch (map.size()) {
/*     */       case 0:
/*  72 */         return new EmptyTrie<T>(defaultValue);
/*     */       case 1:
/*  74 */         e = map.entrySet().iterator().next();
/*  75 */         return new SingletonTrie<T>(e.getKey(), e.getValue(), separator, defaultValue);
/*     */     } 
/*  77 */     return new SortedTrie<T>(map, separator, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SegmentTrie(T defaultValue) {
/*  84 */     this.defaultValue = defaultValue;
/*     */   }
/*     */   
/*     */   public final T getDefaultValue() {
/*  88 */     return this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract T find(String paramString);
/*     */   
/*     */   public abstract Map<String, T> getEntryMap();
/*     */   
/*     */   private static final class EmptyTrie<T>
/*     */     extends SegmentTrie<T>
/*     */   {
/*     */     EmptyTrie(T defaultValue) {
/* 100 */       super(defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public T find(String k) {
/* 105 */       return getDefaultValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, T> getEntryMap() {
/* 110 */       return Collections.emptyMap();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SingletonTrie<T>
/*     */     extends SegmentTrie<T> {
/*     */     private final String key;
/*     */     private final T value;
/*     */     private final char separator;
/*     */     
/*     */     SingletonTrie(String key, T value, char separator, T defaultValue) {
/* 121 */       super(defaultValue);
/* 122 */       this.key = (String)Checks.checkNotNull(key, "key");
/* 123 */       this.value = value;
/* 124 */       this.separator = separator;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public T find(String k) {
/* 130 */       return 
/* 131 */         (k.regionMatches(0, this.key, 0, this.key.length()) && (k.length() == this.key.length() || k.charAt(this.key.length()) == this.separator)) ? 
/* 132 */         this.value : 
/* 133 */         getDefaultValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, T> getEntryMap() {
/* 138 */       Map<String, T> map = new HashMap<String, T>();
/* 139 */       map.put(this.key, this.value);
/* 140 */       return Collections.unmodifiableMap(map);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SortedTrie<T>
/*     */     extends SegmentTrie<T>
/*     */   {
/*     */     private final String[] keys;
/*     */     private final List<T> values;
/*     */     private final int[] parent;
/*     */     private final char separator;
/*     */     
/*     */     SortedTrie(Map<String, ? extends T> entries, char separator, T defaultValue) {
/* 153 */       super(defaultValue);
/* 154 */       TreeMap<String, T> sorted = new TreeMap<String, T>(entries);
/* 155 */       this.keys = (String[])sorted.keySet().toArray((Object[])new String[0]);
/* 156 */       this.values = new ArrayList<T>(sorted.values());
/* 157 */       this.parent = buildParentMap(this.keys, separator);
/* 158 */       this.separator = separator;
/*     */     }
/*     */ 
/*     */     
/*     */     public T find(String key) {
/* 163 */       int keyLen = key.length();
/*     */ 
/*     */       
/* 166 */       int lhsIdx = 0;
/* 167 */       int lhsPrefix = prefixCompare(key, this.keys[lhsIdx], 0);
/* 168 */       if (lhsPrefix == keyLen)
/*     */       {
/* 170 */         return this.values.get(lhsIdx);
/*     */       }
/* 172 */       if (lhsPrefix < 0)
/*     */       {
/* 174 */         return getDefaultValue();
/*     */       }
/*     */ 
/*     */       
/* 178 */       int rhsIdx = this.keys.length - 1;
/* 179 */       int rhsPrefix = prefixCompare(key, this.keys[rhsIdx], 0);
/* 180 */       if (rhsPrefix == keyLen)
/*     */       {
/* 182 */         return this.values.get(rhsIdx);
/*     */       }
/* 184 */       if (rhsPrefix >= 0)
/*     */       {
/* 186 */         return findParent(key, rhsIdx, rhsPrefix);
/*     */       }
/*     */       
/* 189 */       rhsPrefix ^= 0xFFFFFFFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 196 */         int midIdx = lhsIdx + rhsIdx >>> 1;
/* 197 */         if (midIdx == lhsIdx)
/*     */         {
/*     */ 
/*     */           
/* 201 */           return findParent(key, lhsIdx, lhsPrefix);
/*     */         }
/*     */ 
/*     */         
/* 205 */         int midPrefix = prefixCompare(key, this.keys[midIdx], Math.min(lhsPrefix, rhsPrefix));
/* 206 */         if (keyLen == midPrefix)
/*     */         {
/* 208 */           return this.values.get(midIdx);
/*     */         }
/* 210 */         if (midPrefix >= 0) {
/*     */           
/* 212 */           lhsIdx = midIdx;
/* 213 */           lhsPrefix = midPrefix;
/*     */           continue;
/*     */         } 
/* 216 */         rhsIdx = midIdx;
/* 217 */         rhsPrefix = midPrefix ^ 0xFFFFFFFF;
/*     */       } 
/*     */     }
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
/*     */     private T findParent(String k, int idx, int len) {
/* 232 */       while (!isParent(this.keys[idx], k, len)) {
/* 233 */         idx = this.parent[idx];
/* 234 */         if (idx == -1) {
/* 235 */           return getDefaultValue();
/*     */         }
/*     */       } 
/* 238 */       return this.values.get(idx);
/*     */     }
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
/*     */     private boolean isParent(String p, String k, int len) {
/* 262 */       return (p.length() <= len && k.charAt(p.length()) == this.separator);
/*     */     }
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
/*     */     private static int prefixCompare(String lhs, String rhs, int start) {
/* 291 */       if (start < 0) {
/* 292 */         throw new IllegalStateException("lhs=" + lhs + ", rhs=" + rhs + ", start=" + start);
/*     */       }
/* 294 */       int len = Math.min(lhs.length(), rhs.length());
/* 295 */       for (int n = start; n < len; n++) {
/* 296 */         int diff = lhs.charAt(n) - rhs.charAt(n);
/* 297 */         if (diff != 0) {
/* 298 */           return (diff < 0) ? (n ^ 0xFFFFFFFF) : n;
/*     */         }
/*     */       } 
/* 301 */       return (len < rhs.length()) ? (len ^ 0xFFFFFFFF) : len;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static int[] buildParentMap(String[] keys, char separator) {
/* 309 */       int[] pmap = new int[keys.length];
/*     */       
/* 311 */       pmap[0] = -1;
/* 312 */       for (int n = 1; n < keys.length; n++) {
/*     */         
/* 314 */         pmap[n] = -1;
/*     */         
/* 316 */         String key = keys[n]; int sidx;
/* 317 */         for (sidx = key.lastIndexOf(separator); sidx >= 0; sidx = key.lastIndexOf(separator)) {
/* 318 */           key = key.substring(0, sidx);
/* 319 */           int i = Arrays.binarySearch((Object[])keys, 0, n, key);
/* 320 */           if (i >= 0) {
/*     */             
/* 322 */             pmap[n] = i;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 327 */       return pmap;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, T> getEntryMap() {
/* 332 */       Map<String, T> map = new LinkedHashMap<String, T>();
/* 333 */       for (int n = 0; n < this.keys.length; n++) {
/* 334 */         map.put(this.keys[n], this.values.get(n));
/*     */       }
/* 336 */       return Collections.unmodifiableMap(map);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\context\SegmentTrie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */