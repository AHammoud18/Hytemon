/*    */ package com.hypixel.hytale.server.core.codec;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.shape.Cylinder;
/*    */ import com.hypixel.hytale.math.shape.Ellipsoid;
/*    */ import com.hypixel.hytale.math.shape.OriginShape;
/*    */ import com.hypixel.hytale.math.shape.Shape;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class ShapeCodecs {
/* 16 */   public static final CodecMapCodec<Shape> SHAPE = new CodecMapCodec();
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<Box> BOX;
/*    */   
/*    */   public static final BuilderCodec<Ellipsoid> ELLIPSOID;
/*    */   
/*    */   public static final BuilderCodec<Cylinder> CYLINDER;
/*    */   
/*    */   public static final BuilderCodec<OriginShape<Shape>> ORIGIN_SHAPE;
/*    */ 
/*    */   
/*    */   static {
/* 29 */     BOX = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Box.class, Box::new).addField(new KeyedCodec("Min", (Codec)Vector3d.CODEC), (shape, min) -> shape.min.assign(min), shape -> shape.min)).addField(new KeyedCodec("Max", (Codec)Vector3d.CODEC), (shape, max) -> shape.max.assign(max), shape -> shape.max)).build();
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     ELLIPSOID = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Ellipsoid.class, Ellipsoid::new).addField(new KeyedCodec("RadiusX", (Codec)Codec.DOUBLE), (shape, radius) -> shape.radiusX = radius.doubleValue(), shape -> Double.valueOf(shape.radiusX))).addField(new KeyedCodec("RadiusY", (Codec)Codec.DOUBLE), (shape, radius) -> shape.radiusY = radius.doubleValue(), shape -> Double.valueOf(shape.radiusY))).addField(new KeyedCodec("RadiusZ", (Codec)Codec.DOUBLE), (shape, radius) -> shape.radiusZ = radius.doubleValue(), shape -> Double.valueOf(shape.radiusZ))).addField(new KeyedCodec("Radius", (Codec)Codec.DOUBLE), Ellipsoid::assign, shape -> null)).build();
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 73 */     CYLINDER = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Cylinder.class, Cylinder::new).addField(new KeyedCodec("Height", (Codec)Codec.DOUBLE), (shape, height) -> shape.height = height.doubleValue(), shape -> Double.valueOf(shape.height))).addField(new KeyedCodec("RadiusX", (Codec)Codec.DOUBLE), (shape, radiusX) -> shape.radiusX = radiusX.doubleValue(), shape -> Double.valueOf(shape.radiusX))).addField(new KeyedCodec("RadiusZ", (Codec)Codec.DOUBLE), (shape, radiusZ) -> shape.radiusZ = radiusZ.doubleValue(), shape -> Double.valueOf(shape.radiusZ))).addField(new KeyedCodec("Radius", (Codec)Codec.DOUBLE), Cylinder::assign, shape -> null)).build();
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
/* 87 */     ORIGIN_SHAPE = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OriginShape.class, OriginShape::new).addField(new KeyedCodec("Origin", (Codec)Vector3d.CODEC), (shape, origin) -> shape.origin.assign(origin), shape -> shape.origin)).addField(new KeyedCodec("Shape", (Codec)SHAPE), (shape, childShape) -> shape.shape = childShape, shape -> shape.shape)).build();
/*    */ 
/*    */     
/* 90 */     SHAPE.register("Box", Box.class, (Codec)BOX);
/* 91 */     SHAPE.register("Ellipsoid", Ellipsoid.class, (Codec)ELLIPSOID);
/* 92 */     SHAPE.register("Cylinder", Cylinder.class, (Codec)CYLINDER);
/* 93 */     SHAPE.register("OriginShape", OriginShape.class, (Codec)ORIGIN_SHAPE);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\codec\ShapeCodecs.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */