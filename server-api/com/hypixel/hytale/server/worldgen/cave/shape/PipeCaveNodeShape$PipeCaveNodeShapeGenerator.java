/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class PipeCaveNodeShapeGenerator
/*     */   implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */ {
/*     */   private final IDoubleRange radius;
/*     */   private final IDoubleRange middleRadius;
/*     */   private final IDoubleRange length;
/*     */   private final boolean inheritParentRadius;
/*     */   
/*     */   public PipeCaveNodeShapeGenerator(IDoubleRange radius, IDoubleRange middleRadius, IDoubleRange length, boolean inheritParentRadius) {
/* 213 */     this.radius = radius;
/* 214 */     this.middleRadius = middleRadius;
/* 215 */     this.length = length;
/* 216 */     this.inheritParentRadius = inheritParentRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CaveNodeShape generateCaveNodeShape(@Nonnull Random random, CaveType caveType, CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d origin, float yaw, float pitch) {
/* 226 */     double radius1, l = this.length.getValue(random.nextDouble());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     Vector3d direction = (new Vector3d((TrigMathUtil.sin(pitch) * TrigMathUtil.cos(yaw)), TrigMathUtil.cos(pitch), (TrigMathUtil.sin(pitch) * TrigMathUtil.sin(yaw)))).scale(l);
/*     */     
/* 234 */     if (this.inheritParentRadius) {
/* 235 */       radius1 = CaveNodeShapeUtils.getEndRadius(parentNode, this.radius, random);
/*     */     } else {
/* 237 */       radius1 = this.radius.getValue(random.nextDouble());
/*     */     } 
/* 239 */     double radius2 = this.radius.getValue(random.nextDouble());
/*     */     
/* 241 */     double radius3 = (radius2 - radius1) * 0.5D + radius1;
/* 242 */     if (this.middleRadius != null) {
/* 243 */       radius3 = this.middleRadius.getValue(random.nextDouble());
/*     */     }
/*     */     
/* 246 */     Vector3d offset = CaveNodeShapeUtils.getOffset(parentNode, childEntry);
/* 247 */     origin.add(offset);
/*     */     
/* 249 */     return new PipeCaveNodeShape(caveType, origin, direction, radius1, radius2, radius3);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\PipeCaveNodeShape$PipeCaveNodeShapeGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */