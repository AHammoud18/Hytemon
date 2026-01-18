/*    */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.shape.PipeCaveNodeShape;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PipeCaveNodeShapeGeneratorJsonLoader
/*    */   extends CaveNodeShapeGeneratorJsonLoader
/*    */ {
/*    */   public PipeCaveNodeShapeGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 22 */     super(seed.append(".PipeCaveNodeShapeGenerator"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PipeCaveNodeShape.PipeCaveNodeShapeGenerator load() {
/* 28 */     return new PipeCaveNodeShape.PipeCaveNodeShapeGenerator(
/* 29 */         loadRadius(), 
/* 30 */         loadMiddleRadius(), 
/* 31 */         loadLength(), 
/* 32 */         loadInheritParentRadius());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleRange loadRadius() {
/* 38 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Radius"), 3.0D))
/* 39 */       .load();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleRange loadMiddleRadius() {
/* 44 */     IDoubleRange middleRadius = null;
/* 45 */     if (has("MiddleRadius"))
/*    */     {
/* 47 */       middleRadius = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("MiddleRadius"), 0.0D)).load();
/*    */     }
/* 49 */     return middleRadius;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleRange loadLength() {
/* 54 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Length"), 5.0D, 15.0D))
/* 55 */       .load();
/*    */   }
/*    */   
/*    */   protected boolean loadInheritParentRadius() {
/* 59 */     boolean inherit = true;
/* 60 */     if (has("InheritParentRadius")) {
/* 61 */       inherit = get("InheritParentRadius").getAsBoolean();
/*    */     }
/* 63 */     return inherit;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_RADIUS = "Radius";
/*    */     public static final String KEY_MIDDLE_RADIUS = "MiddleRadius";
/*    */     public static final String KEY_LENGTH = "Length";
/*    */     public static final String KEY_INHERIT_PARENT_RADIUS = "InheritParentRadius";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\PipeCaveNodeShapeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */