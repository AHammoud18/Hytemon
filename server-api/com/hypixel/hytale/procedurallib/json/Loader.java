/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Loader<K extends SeedResource, T>
/*    */ {
/*    */   protected SeedString<K> seed;
/*    */   protected final Path dataFolder;
/*    */   
/*    */   public Loader(SeedString<K> seed, Path dataFolder) {
/* 15 */     this.seed = seed;
/* 16 */     this.dataFolder = dataFolder;
/*    */   }
/*    */   
/*    */   public SeedString<K> getSeed() {
/* 20 */     return this.seed;
/*    */   }
/*    */   
/*    */   public Path getDataFolder() {
/* 24 */     return this.dataFolder;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public abstract T load();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\Loader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */