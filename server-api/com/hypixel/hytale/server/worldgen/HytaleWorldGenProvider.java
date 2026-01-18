/*    */ package com.hypixel.hytale.server.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.common.util.PathUtil;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*    */ import com.hypixel.hytale.server.worldgen.loader.ChunkGeneratorJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.prefab.PrefabStoreRoot;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.function.Supplier;
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
/*    */ public class HytaleWorldGenProvider
/*    */   implements IWorldGenProvider
/*    */ {
/*    */   public static final String ID = "Hytale";
/*    */   public static final BuilderCodec<HytaleWorldGenProvider> CODEC;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HytaleWorldGenProvider.class, HytaleWorldGenProvider::new).documentation("The standard generator for Hytale.")).append(new KeyedCodec("Name", (Codec)Codec.STRING), (config, s) -> config.name = s, config -> config.name).documentation("The name of the generator to use. \"*Default*\" if not provided.").add()).append(new KeyedCodec("Path", (Codec)Codec.STRING), (config, s) -> config.path = s, config -> config.path).documentation("The path to the world generation configuration. \n\nDefaults to the server provided world generation folder if not set.").add()).build();
/*    */   }
/* 42 */   private String name = "Default";
/*    */   
/*    */   private String path;
/*    */   
/*    */   @Nonnull
/*    */   public IWorldGen getGenerator() throws WorldGenLoadException {
/*    */     Path worldGenPath;
/* 49 */     if (this.path != null) {
/* 50 */       worldGenPath = PathUtil.get(this.path);
/*    */     } else {
/* 52 */       worldGenPath = Universe.getWorldGenPath();
/*    */     } 
/*    */ 
/*    */     
/* 56 */     if (!"Default".equals(this.name) || !Files.exists(worldGenPath.resolve("World.json"), new java.nio.file.LinkOption[0])) {
/* 57 */       worldGenPath = worldGenPath.resolve(this.name);
/*    */     }
/*    */     
/*    */     try {
/* 61 */       return (IWorldGen)(new ChunkGeneratorJsonLoader(new SeedString("ChunkGenerator", new SeedStringResource(PrefabStoreRoot.DEFAULT, worldGenPath)), worldGenPath))
/*    */         
/* 63 */         .load();
/* 64 */     } catch (Error e) {
/* 65 */       throw new WorldGenLoadException("Failed to load world gen!", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "HytaleWorldGenProvider{name='" + this.name + "', path='" + this.path + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\HytaleWorldGenProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */