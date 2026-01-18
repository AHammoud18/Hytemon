/*     */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.CaveNodeShapeEnum;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.DistortedCaveNodeShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.DistortedShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.DistortedShapes;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.ShapeDistortion;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class DistortedCaveNodeShapeGeneratorJsonLoader extends CaveNodeShapeGeneratorJsonLoader {
/*     */   public DistortedCaveNodeShapeGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  21 */     super(seed.append(".DistortedCaveNodeShape"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CaveNodeShapeEnum.CaveNodeShapeGenerator load() {
/*  27 */     return (CaveNodeShapeEnum.CaveNodeShapeGenerator)new DistortedCaveNodeShape.DistortedCaveNodeShapeGenerator(
/*  28 */         loadShape(), 
/*  29 */         loadWidth(), 
/*  30 */         loadHeight(), 
/*  31 */         loadMidWidth(), 
/*  32 */         loadMidHeight(), 
/*  33 */         loadLength(), 
/*  34 */         loadInheritParentRadius(), 
/*  35 */         loadShapeDistortion(), 
/*  36 */         loadInterpolation());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private DistortedShape.Factory loadShape() {
/*  42 */     if (has("Shape")) {
/*  43 */       DistortedShape.Factory shape = DistortedShapes.getByName(get("Shape").getAsString());
/*  44 */       if (shape != null) {
/*  45 */         return shape;
/*     */       }
/*     */     } 
/*  48 */     return Constants.DEFAULT_SHAPE;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private IDoubleRange loadWidth() {
/*  53 */     if (has("RadiusX")) {
/*  54 */       return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("RadiusX"), 3.0D))
/*  55 */         .load();
/*     */     }
/*  57 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Width"), 3.0D))
/*  58 */       .load();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private IDoubleRange loadHeight() {
/*  63 */     if (has("RadiusY")) {
/*  64 */       return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("RadiusY"), 3.0D))
/*  65 */         .load();
/*     */     }
/*  67 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Height"), 3.0D))
/*  68 */       .load();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private IDoubleRange loadMidWidth() {
/*  73 */     IDoubleRange midWidth = null;
/*  74 */     if (has("MiddleWidth"))
/*     */     {
/*  76 */       midWidth = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("MiddleWidth"), 0.0D)).load();
/*     */     }
/*  78 */     return midWidth;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private IDoubleRange loadMidHeight() {
/*  83 */     IDoubleRange midHeight = null;
/*  84 */     if (has("MiddleHeight"))
/*     */     {
/*  86 */       midHeight = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("MiddleHeight"), 0.0D)).load();
/*     */     }
/*  88 */     return midHeight;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private IDoubleRange loadLength() {
/*  93 */     if (has("RadiusZ")) {
/*  94 */       return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("RadiusZ"), 3.0D))
/*  95 */         .load();
/*     */     }
/*  97 */     IDoubleRange length = null;
/*  98 */     if (has("Length"))
/*     */     {
/* 100 */       length = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Length"), 5.0D, 15.0D)).load();
/*     */     }
/* 102 */     return length;
/*     */   }
/*     */   
/*     */   private boolean loadInheritParentRadius() {
/* 106 */     boolean inherit = true;
/* 107 */     if (has("InheritParentRadius")) {
/* 108 */       inherit = get("InheritParentRadius").getAsBoolean();
/*     */     }
/* 110 */     return inherit;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ShapeDistortion loadShapeDistortion() {
/* 115 */     ShapeDistortion distortion = ShapeDistortion.DEFAULT;
/* 116 */     if (has("Distortion")) {
/* 117 */       distortion = (new ShapeDistortionJsonLoader<>(this.seed, this.dataFolder, get("Distortion"))).load();
/*     */     }
/* 119 */     return distortion;
/*     */   }
/*     */   
/*     */   private GeneralNoise.InterpolationFunction loadInterpolation() {
/* 123 */     GeneralNoise.InterpolationFunction interpolation = Constants.DEFAULT_INTERPOLATION;
/* 124 */     if (has("Interpolation")) {
/* 125 */       interpolation = (GeneralNoise.InterpolationMode.valueOf(get("Interpolation").getAsString())).function;
/*     */     }
/* 127 */     return interpolation;
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_SHAPE = "Shape";
/*     */     public static final String KEY_WIDTH = "Width";
/*     */     public static final String KEY_HEIGHT = "Height";
/*     */     public static final String KEY_MID_WIDTH = "MiddleWidth";
/*     */     public static final String KEY_MID_HEIGHT = "MiddleHeight";
/*     */     public static final String KEY_LENGTH = "Length";
/*     */     public static final String KEY_RADIUS_X = "RadiusX";
/*     */     public static final String KEY_RADIUS_Y = "RadiusY";
/*     */     public static final String KEY_RADIUS_Z = "RadiusZ";
/*     */     public static final String KEY_INHERIT_PARENT_RADIUS = "InheritParentRadius";
/*     */     public static final String KEY_DISTORTION = "Distortion";
/*     */     public static final String KEY_INTERPOLATION = "Interpolation";
/* 143 */     public static final DistortedShape.Factory DEFAULT_SHAPE = DistortedShapes.getDefault();
/* 144 */     public static final GeneralNoise.InterpolationFunction DEFAULT_INTERPOLATION = GeneralNoise.InterpolationMode.LINEAR.function;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\DistortedCaveNodeShapeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */