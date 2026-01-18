/*     */ package com.hypixel.hytale.server.core.command.system;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class AbbreviationMap<Value>
/*     */ {
/*     */   private final Map<String, Value> abbreviationMap;
/*     */   
/*     */   public AbbreviationMap(@Nonnull Map<String, Value> abbreviationMap) {
/*  28 */     this.abbreviationMap = abbreviationMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Value get(@Nonnull String abbreviation) {
/*  38 */     return this.abbreviationMap.get(abbreviation.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <V> AbbreviationMapBuilder<V> create() {
/*  49 */     return new AbbreviationMapBuilder<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AbbreviationMapBuilder<Value>
/*     */   {
/*  58 */     private final Map<String, Value> keys = (Map<String, Value>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public AbbreviationMapBuilder<Value> put(@Nonnull String key, @Nonnull Value value) {
/*  70 */       if (this.keys.putIfAbsent(key.toLowerCase(), value) != null) {
/*  71 */         throw new IllegalArgumentException("Cannot have values with the same key in AbbreviationMap: " + key);
/*     */       }
/*  73 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public AbbreviationMap<Value> build() {
/*  83 */       Object2ObjectOpenHashMap<String, Value> abbreviationMap = new Object2ObjectOpenHashMap();
/*     */       
/*  85 */       for (Map.Entry<String, Value> entry : this.keys.entrySet()) {
/*  86 */         appendAbbreviation(entry.getKey(), entry.getValue(), (Map<String, Value>)abbreviationMap);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  91 */       abbreviationMap.values().removeIf(Objects::isNull);
/*  92 */       abbreviationMap.trim();
/*     */       
/*  94 */       return new AbbreviationMap<>(Collections.unmodifiableMap((Map<? extends String, ? extends Value>)abbreviationMap));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void appendAbbreviation(@Nonnull String key, @Nonnull Value value, @Nonnull Map<String, Value> map) {
/* 105 */       map.put(key, value);
/*     */       
/* 107 */       for (int i = 1; i < key.length(); i++) {
/* 108 */         String substring = key.substring(0, key.length() - i);
/* 109 */         Value existingAbbreviationValue = map.get(substring);
/*     */ 
/*     */         
/* 112 */         if (existingAbbreviationValue == null) {
/* 113 */           map.put(substring, value);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 118 */         else if (!this.keys.containsKey(substring)) {
/*     */ 
/*     */ 
/*     */           
/* 122 */           if (!existingAbbreviationValue.equals(value)) map.put(substring, null); 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\AbbreviationMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */