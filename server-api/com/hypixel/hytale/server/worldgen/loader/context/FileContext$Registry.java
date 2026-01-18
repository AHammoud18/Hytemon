/*    */ package com.hypixel.hytale.server.worldgen.loader.context;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ public class Registry<T>
/*    */   implements Iterable<Map.Entry<String, T>>
/*    */ {
/*    */   private final String registryName;
/*    */   @Nonnull
/*    */   private final Object2ObjectMap<String, T> backing;
/*    */   
/*    */   public Registry(String name) {
/* 45 */     this.registryName = name;
/* 46 */     this.backing = (Object2ObjectMap<String, T>)new Object2ObjectLinkedOpenHashMap();
/*    */   }
/*    */   
/*    */   public int size() {
/* 50 */     return this.backing.size();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 54 */     return this.registryName;
/*    */   }
/*    */   
/*    */   public boolean contains(String name) {
/* 58 */     return this.backing.containsKey(name);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public T get(String name) {
/* 63 */     T value = (T)this.backing.get(name);
/* 64 */     if (value == null) throw new Error(String.format("Missing %s entry for key %s", new Object[] { this.registryName, name })); 
/* 65 */     return value;
/*    */   }
/*    */   
/*    */   public void register(String name, T biome) {
/* 69 */     if (this.backing.containsKey(name)) throw new Error(String.format("Duplicate %s entry registered for key %s", new Object[] { this.registryName, name })); 
/* 70 */     this.backing.put(name, biome);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Iterator<Map.Entry<String, T>> iterator() {
/* 76 */     return (Iterator<Map.Entry<String, T>>)this.backing.entrySet().iterator();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\context\FileContext$Registry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */