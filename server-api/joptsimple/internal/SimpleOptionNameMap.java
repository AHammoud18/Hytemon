/*    */ package joptsimple.internal;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleOptionNameMap<V>
/*    */   implements OptionNameMap<V>
/*    */ {
/* 35 */   private final Map<String, V> map = new HashMap<>();
/*    */ 
/*    */   
/*    */   public boolean contains(String key) {
/* 39 */     return this.map.containsKey(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public V get(String key) {
/* 44 */     return this.map.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(String key, V newValue) {
/* 49 */     this.map.put(key, newValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public void putAll(Iterable<String> keys, V newValue) {
/* 54 */     for (String each : keys) {
/* 55 */       this.map.put(each, newValue);
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove(String key) {
/* 60 */     this.map.remove(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, V> toJavaUtilMap() {
/* 65 */     return new HashMap<>(this.map);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\SimpleOptionNameMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */