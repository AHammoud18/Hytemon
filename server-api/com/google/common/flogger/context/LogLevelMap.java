/*     */ package com.google.common.flogger.context;
/*     */ 
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
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
/*     */ public final class LogLevelMap
/*     */ {
/*     */   private final SegmentTrie<Level> trie;
/*     */   
/*     */   public static final class Builder
/*     */   {
/*  43 */     private final Map<String, Level> map = new HashMap<String, Level>();
/*  44 */     private Level defaultLevel = Level.OFF;
/*     */ 
/*     */ 
/*     */     
/*     */     private void put(String name, Level level) {
/*  49 */       if (this.map.put(name, level) != null) {
/*  50 */         throw new IllegalArgumentException("duplicate entry for class/package: " + name);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder add(Level level, Class<?>... classes) {
/*  56 */       for (Class<?> cls : classes) {
/*  57 */         put(cls.getName(), level);
/*     */       }
/*  59 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder add(Level level, Package... packages) {
/*  64 */       for (Package pkg : packages) {
/*  65 */         put(pkg.getName(), level);
/*     */       }
/*  67 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder setDefault(Level level) {
/*  72 */       Checks.checkNotNull(this.defaultLevel, "default log level must not be null");
/*  73 */       this.defaultLevel = level;
/*  74 */       return this;
/*     */     }
/*     */     
/*     */     public LogLevelMap build() {
/*  78 */       return LogLevelMap.create(this.map, this.defaultLevel);
/*     */     }
/*     */     
/*     */     private Builder() {} }
/*     */   
/*     */   public static Builder builder() {
/*  84 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LogLevelMap create(Level level) {
/*  92 */     return create(Collections.emptyMap(), level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LogLevelMap create(Map<String, ? extends Level> map) {
/* 101 */     return create(map, Level.OFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LogLevelMap create(Map<String, ? extends Level> map, Level defaultLevel) {
/* 110 */     Checks.checkNotNull(defaultLevel, "default log level must not be null");
/* 111 */     for (Map.Entry<String, ? extends Level> e : map.entrySet()) {
/* 112 */       String name = e.getKey();
/* 113 */       if (name.startsWith(".") || name.endsWith(".") || name.contains("..")) {
/* 114 */         throw new IllegalArgumentException("invalid logger name: " + name);
/*     */       }
/* 116 */       if (e.getValue() == null) {
/* 117 */         throw new IllegalArgumentException("log levels must not be null; logger=" + name);
/*     */       }
/*     */     } 
/* 120 */     return new LogLevelMap(map, defaultLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LogLevelMap(Map<String, ? extends Level> map, Level defaultLevel) {
/* 126 */     this.trie = SegmentTrie.create(map, '.', defaultLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getLevel(String loggerName) {
/* 135 */     return this.trie.find(loggerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogLevelMap merge(LogLevelMap other) {
/* 143 */     Map<String, Level> thisMap = this.trie.getEntryMap();
/* 144 */     Map<String, Level> otherMap = other.trie.getEntryMap();
/*     */ 
/*     */     
/* 147 */     Map<String, Level> mergedMap = new HashMap<String, Level>();
/* 148 */     Set<String> allKeys = new HashSet<String>(thisMap.keySet());
/* 149 */     allKeys.addAll(otherMap.keySet());
/* 150 */     for (String key : allKeys) {
/* 151 */       if (!otherMap.containsKey(key)) {
/* 152 */         mergedMap.put(key, thisMap.get(key)); continue;
/* 153 */       }  if (!thisMap.containsKey(key)) {
/* 154 */         mergedMap.put(key, otherMap.get(key)); continue;
/*     */       } 
/* 156 */       mergedMap.put(key, min(thisMap.get(key), otherMap.get(key)));
/*     */     } 
/*     */ 
/*     */     
/* 160 */     Level defaultLevel = min(this.trie.getDefaultValue(), other.trie.getDefaultValue());
/* 161 */     return create(mergedMap, defaultLevel);
/*     */   }
/*     */   
/*     */   private static Level min(Level a, Level b) {
/* 165 */     return (a.intValue() <= b.intValue()) ? a : b;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\context\LogLevelMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */