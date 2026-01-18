/*    */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.shape.CaveNodeShapeEnum;
/*    */ import java.nio.file.Path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CaveNodeShapeGeneratorJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CaveNodeShapeEnum.CaveNodeShapeGenerator>
/*    */ {
/*    */   public CaveNodeShapeGeneratorJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 17 */     super(seed, dataFolder, json);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\CaveNodeShapeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */