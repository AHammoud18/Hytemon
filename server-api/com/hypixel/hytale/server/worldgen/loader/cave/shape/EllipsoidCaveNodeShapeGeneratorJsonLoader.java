/*    */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.shape.EllipsoidCaveNodeShape;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EllipsoidCaveNodeShapeGeneratorJsonLoader
/*    */   extends CaveNodeShapeGeneratorJsonLoader
/*    */ {
/*    */   public EllipsoidCaveNodeShapeGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 22 */     super(seed.append(".EllipsoidCaveNodeShapeGenerator"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EllipsoidCaveNodeShape.EllipsoidCaveNodeShapeGenerator load() {
/* 28 */     IDoubleRange radiusX = null, radiusY = null, radiusZ = null;
/* 29 */     if (has("Radius"))
/*    */     {
/* 31 */       radiusX = radiusY = radiusZ = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Radius"), 5.0D)).load();
/*    */     }
/* 33 */     if (has("RadiusX"))
/*    */     {
/* 35 */       radiusX = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("RadiusX"), 5.0D)).load();
/*    */     }
/* 37 */     if (has("RadiusY"))
/*    */     {
/* 39 */       radiusY = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("RadiusY"), 5.0D)).load();
/*    */     }
/* 41 */     if (has("RadiusZ"))
/*    */     {
/* 43 */       radiusZ = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("RadiusZ"), 5.0D)).load();
/*    */     }
/* 45 */     Objects.requireNonNull(radiusX, "RadiusX");
/* 46 */     Objects.requireNonNull(radiusY, "RadiusY");
/* 47 */     Objects.requireNonNull(radiusZ, "RadiusZ");
/* 48 */     return new EllipsoidCaveNodeShape.EllipsoidCaveNodeShapeGenerator(radiusX, radiusY, radiusZ);
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_RADIUS = "Radius";
/*    */     public static final String KEY_RADIUS_X = "RadiusX";
/*    */     public static final String KEY_RADIUS_Y = "RadiusY";
/*    */     public static final String KEY_RADIUS_Z = "RadiusZ";
/*    */     public static final String ERROR_RADIUS_NOT_SET = "%s was not set for Ellipsoid!";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\EllipsoidCaveNodeShapeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */