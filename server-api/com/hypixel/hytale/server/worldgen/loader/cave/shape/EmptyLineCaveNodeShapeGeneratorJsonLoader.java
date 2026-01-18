/*    */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.shape.EmptyLineCaveNodeShape;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmptyLineCaveNodeShapeGeneratorJsonLoader
/*    */   extends CaveNodeShapeGeneratorJsonLoader
/*    */ {
/*    */   public EmptyLineCaveNodeShapeGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 22 */     super(seed.append(".EmptyLineCaveNodeShapeGenerator"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EmptyLineCaveNodeShape.EmptyLineCaveNodeShapeGenerator load() {
/* 28 */     return new EmptyLineCaveNodeShape.EmptyLineCaveNodeShapeGenerator(
/* 29 */         loadLength());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleRange loadLength() {
/* 35 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Length"), 0.0D))
/* 36 */       .load();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_LENGTH = "Length";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\EmptyLineCaveNodeShapeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */