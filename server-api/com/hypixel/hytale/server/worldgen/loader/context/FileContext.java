/*    */ package com.hypixel.hytale.server.worldgen.loader.context;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FileContext<T> {
/*    */   private final int id;
/*    */   private final String name;
/*    */   private final Path filepath;
/*    */   private final T parentContext;
/*    */   
/*    */   public FileContext(int id, String name, Path filepath, T parentContext) {
/* 17 */     this.id = id;
/* 18 */     this.name = name;
/* 19 */     this.filepath = filepath;
/* 20 */     this.parentContext = parentContext;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 24 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 28 */     return this.name;
/*    */   }
/*    */   
/*    */   public Path getPath() {
/* 32 */     return this.filepath;
/*    */   }
/*    */   
/*    */   public T getParentContext() {
/* 36 */     return this.parentContext;
/*    */   }
/*    */   public static interface Constants {
/*    */     public static final String ERROR_MISSING_ENTRY = "Missing %s entry for key %s";
/*    */     public static final String ERROR_DUPLICATE_ENTRY = "Duplicate %s entry registered for key %s"; }
/*    */   
/*    */   public static class Registry<T> implements Iterable<Map.Entry<String, T>> { private final String registryName;
/*    */     
/*    */     public Registry(String name) {
/* 45 */       this.registryName = name;
/* 46 */       this.backing = (Object2ObjectMap<String, T>)new Object2ObjectLinkedOpenHashMap();
/*    */     } @Nonnull
/*    */     private final Object2ObjectMap<String, T> backing;
/*    */     public int size() {
/* 50 */       return this.backing.size();
/*    */     }
/*    */     
/*    */     public String getName() {
/* 54 */       return this.registryName;
/*    */     }
/*    */     
/*    */     public boolean contains(String name) {
/* 58 */       return this.backing.containsKey(name);
/*    */     }
/*    */     
/*    */     @Nonnull
/*    */     public T get(String name) {
/* 63 */       T value = (T)this.backing.get(name);
/* 64 */       if (value == null) throw new Error(String.format("Missing %s entry for key %s", new Object[] { this.registryName, name })); 
/* 65 */       return value;
/*    */     }
/*    */     
/*    */     public void register(String name, T biome) {
/* 69 */       if (this.backing.containsKey(name)) throw new Error(String.format("Duplicate %s entry registered for key %s", new Object[] { this.registryName, name })); 
/* 70 */       this.backing.put(name, biome);
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Iterator<Map.Entry<String, T>> iterator() {
/* 76 */       return (Iterator<Map.Entry<String, T>>)this.backing.entrySet().iterator();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\context\FileContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */