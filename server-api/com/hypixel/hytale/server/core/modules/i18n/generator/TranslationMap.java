/*    */ package com.hypixel.hytale.server.core.modules.i18n.generator;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TranslationMap {
/*    */   @Nonnull
/* 10 */   private LinkedHashMap<String, String> map = new LinkedHashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TranslationMap(Map<String, String> initial) {
/* 18 */     this.map.putAll(initial);
/*    */   }
/*    */   
/*    */   public TranslationMap(@Nonnull Properties initial) {
/* 22 */     for (String key : initial.stringPropertyNames()) {
/* 23 */       this.map.put(key, initial.getProperty(key));
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String get(String key) {
/* 29 */     return this.map.get(key);
/*    */   }
/*    */   
/*    */   public void put(String key, String value) {
/* 33 */     this.map.put(key, value);
/*    */   }
/*    */   
/*    */   public void removeKeys(@Nonnull Collection<? extends String> keys) {
/* 37 */     this.map.keySet().removeAll(keys);
/*    */   }
/*    */   
/*    */   public int size() {
/* 41 */     return this.map.size();
/*    */   }
/*    */   
/*    */   public void putAbsentKeys(@Nonnull TranslationMap other) {
/* 45 */     for (Map.Entry<String, String> e : other.map.entrySet()) {
/* 46 */       String key = e.getKey();
/* 47 */       String otherValue = e.getValue();
/* 48 */       this.map.putIfAbsent(key, otherValue);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void sortByKeyBeforeFirstDot() {
/* 53 */     ObjectArrayList<String> objectArrayList = new ObjectArrayList(this.map.keySet());
/*    */ 
/*    */ 
/*    */     
/* 57 */     Comparator<String> comparator = Comparator.comparing(fullKey -> { int firstDotIndex = fullKey.indexOf('.'); return (firstDotIndex == -1) ? fullKey : fullKey.substring(0, firstDotIndex); }).thenComparing(fullKey -> {
/*    */           int firstDotIndex = fullKey.indexOf('.');
/*    */           
/*    */           return (firstDotIndex == -1) ? "" : fullKey.substring(firstDotIndex + 1);
/*    */         });
/* 62 */     objectArrayList.sort(comparator);
/*    */     
/* 64 */     LinkedHashMap<String, String> sorted = new LinkedHashMap<>();
/* 65 */     for (String key : objectArrayList) {
/* 66 */       sorted.put(key, this.map.get(key));
/*    */     }
/* 68 */     this.map = sorted;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Map<String, String> asMap() {
/* 73 */     return Collections.unmodifiableMap(this.map);
/*    */   }
/*    */   
/*    */   public TranslationMap() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\generator\TranslationMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */