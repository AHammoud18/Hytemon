/*     */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.DistortedShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.DistortedShapes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Constants
/*     */ {
/*     */   public static final String KEY_SHAPE = "Shape";
/*     */   public static final String KEY_WIDTH = "Width";
/*     */   public static final String KEY_HEIGHT = "Height";
/*     */   public static final String KEY_MID_WIDTH = "MiddleWidth";
/*     */   public static final String KEY_MID_HEIGHT = "MiddleHeight";
/*     */   public static final String KEY_LENGTH = "Length";
/*     */   public static final String KEY_RADIUS_X = "RadiusX";
/*     */   public static final String KEY_RADIUS_Y = "RadiusY";
/*     */   public static final String KEY_RADIUS_Z = "RadiusZ";
/*     */   public static final String KEY_INHERIT_PARENT_RADIUS = "InheritParentRadius";
/*     */   public static final String KEY_DISTORTION = "Distortion";
/*     */   public static final String KEY_INTERPOLATION = "Interpolation";
/* 143 */   public static final DistortedShape.Factory DEFAULT_SHAPE = DistortedShapes.getDefault();
/* 144 */   public static final GeneralNoise.InterpolationFunction DEFAULT_INTERPOLATION = GeneralNoise.InterpolationMode.LINEAR.function;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\DistortedCaveNodeShapeGeneratorJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */