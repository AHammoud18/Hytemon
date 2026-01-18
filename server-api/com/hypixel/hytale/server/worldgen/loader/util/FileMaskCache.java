/*    */ package com.hypixel.hytale.server.worldgen.loader.util;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileMaskCache<T>
/*    */ {
/*    */   @Nonnull
/* 17 */   private final Map<String, T> fileCache = new HashMap<>(); @Nonnull
/* 18 */   private final Map<String, JsonElement> fileElements = new HashMap<>();
/*    */ 
/*    */   
/*    */   public T getIfPresentFileMask(String filename) {
/* 22 */     return this.fileCache.get(filename);
/*    */   }
/*    */   
/*    */   public void putFileMask(String filename, T value) {
/* 26 */     this.fileCache.put(filename, value);
/*    */   }
/*    */   
/*    */   public JsonElement cachedFile(String filename, @Nonnull Function<String, JsonElement> function) {
/* 30 */     return this.fileElements.computeIfAbsent(filename, function);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loade\\util\FileMaskCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */