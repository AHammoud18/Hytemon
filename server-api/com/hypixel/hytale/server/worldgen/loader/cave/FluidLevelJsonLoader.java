/*    */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*    */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*    */ import java.nio.file.Path;
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
/*    */ public class FluidLevelJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CaveType.FluidLevel>
/*    */ {
/*    */   public FluidLevelJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 27 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CaveType.FluidLevel load() {
/* 33 */     int blockId = 0;
/* 34 */     int rotation = 0;
/* 35 */     int fluidId = 0;
/* 36 */     if (has("Block")) {
/* 37 */       String blockString = get("Block").getAsString();
/* 38 */       BlockPattern.BlockEntry blockTypeKey = BlockPattern.BlockEntry.decode(blockString);
/* 39 */       blockId = BlockType.getAssetMap().getIndex(blockTypeKey.blockTypeKey());
/* 40 */       if (blockId == Integer.MIN_VALUE) throw new Error(String.format("Could not resolve block \"%s\" from BlockTypes.", new Object[] { blockString })); 
/* 41 */       rotation = blockTypeKey.rotation();
/*    */     } 
/*    */     
/* 44 */     if (has("Fluid")) {
/* 45 */       String fluidKey = get("Fluid").getAsString();
/* 46 */       fluidId = Fluid.getAssetMap().getIndex(fluidKey);
/* 47 */       if (fluidId == Integer.MIN_VALUE) throw new Error(String.format("Could not resolve fluid \"%s\" from Fluids.", new Object[] { fluidKey }));
/*    */     
/*    */     } 
/* 50 */     if (!has("Block") && !has("Fluid")) throw new IllegalArgumentException("Could not find block to use in FluidLevel container. Keyword: Block");
/*    */     
/* 52 */     return new CaveType.FluidLevel(new BlockFluidEntry(blockId, rotation, fluidId), 
/*    */         
/* 54 */         loadHeight());
/*    */   }
/*    */ 
/*    */   
/*    */   protected int loadHeight() {
/* 59 */     return get("Height").getAsInt();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_HEIGHT = "Height";
/*    */     public static final String KEY_BLOCK = "Block";
/*    */     public static final String KEY_FLUID = "Fluid";
/*    */     public static final String ERROR_NO_BLOCK = "Could not find block to use in FluidLevel container. Keyword: Block";
/*    */     public static final String ERROR_UNKOWN_BLOCK = "Could not resolve block \"%s\" from BlockTypes.";
/*    */     public static final String ERROR_UNKOWN_FLUID = "Could not resolve fluid \"%s\" from Fluids.";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\FluidLevelJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */