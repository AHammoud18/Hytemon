/*    */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.shape.CylinderCaveNodeShape;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CylinderCaveNodeShapeGeneratorJsonLoader
/*    */   extends CaveNodeShapeGeneratorJsonLoader
/*    */ {
/*    */   public CylinderCaveNodeShapeGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 23 */     super(seed.append(".CylinderCaveNodeShapeGenerator"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CylinderCaveNodeShape.CylinderCaveNodeShapeGenerator load() {
/* 29 */     return new CylinderCaveNodeShape.CylinderCaveNodeShapeGenerator(
/* 30 */         loadRadius(), 
/* 31 */         loadMiddleRadius(), 
/* 32 */         loadLength(), 
/* 33 */         loadInheritParentRadius());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleRange loadRadius() {
/* 39 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Radius"), 3.0D))
/* 40 */       .load();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleRange loadMiddleRadius() {
/* 45 */     IDoubleRange middleRadius = null;
/* 46 */     if (has("MiddleRadius"))
/*    */     {
/* 48 */       middleRadius = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("MiddleRadius"), 0.0D)).load();
/*    */     }
/* 50 */     return middleRadius;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleRange loadLength() {
/* 55 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Length"), 5.0D, 15.0D))
/* 56 */       .load();
/*    */   }
/*    */   
/*    */   protected boolean loadInheritParentRadius() {
/* 60 */     boolean inherit = true;
/* 61 */     if (has("InheritParentRadius")) {
/* 62 */       inherit = get("InheritParentRadius").getAsBoolean();
/*    */     }
/* 64 */     return inherit;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_RADIUS = "Radius";
/*    */     public static final String KEY_MIDDLE_RADIUS = "MiddleRadius";
/*    */     public static final String KEY_LENGTH = "Length";
/*    */     public static final String KEY_INHERIT_PARENT_RADIUS = "InheritParentRadius";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\CylinderCaveNodeShapeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */