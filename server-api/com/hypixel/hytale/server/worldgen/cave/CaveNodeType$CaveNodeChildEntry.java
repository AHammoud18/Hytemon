/*     */ package com.hypixel.hytale.server.worldgen.cave;
/*     */ 
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CaveNodeChildEntry
/*     */ {
/* 127 */   public static final CaveNodeChildEntry[] EMPTY_ARRAY = new CaveNodeChildEntry[0];
/*     */   
/*     */   @Nonnull
/*     */   private final IWeightedMap<CaveNodeType> types;
/*     */   
/*     */   @Nonnull
/*     */   private final Vector3d anchor;
/*     */   
/*     */   @Nonnull
/*     */   private final Vector3d offset;
/*     */   
/*     */   @Nonnull
/*     */   private final PrefabRotation[] rotation;
/*     */   
/*     */   @Nullable
/*     */   private final IDoubleRange childrenLimit;
/*     */   
/*     */   @Nonnull
/*     */   private final IDoubleRange repeat;
/*     */   
/*     */   @Nonnull
/*     */   private final OrientationModifier pitchModifier;
/*     */   
/*     */   @Nonnull
/*     */   private final OrientationModifier yawModifier;
/*     */   
/*     */   private final double chance;
/*     */   
/*     */   @Nonnull
/*     */   private final CaveYawMode yawMode;
/*     */   
/*     */   public CaveNodeChildEntry(@Nonnull IWeightedMap<CaveNodeType> types, @Nonnull Vector3d anchor, @Nonnull Vector3d offset, @Nonnull PrefabRotation[] rotation, @Nullable IDoubleRange childrenLimit, @Nonnull IDoubleRange repeat, @Nonnull OrientationModifier pitchModifier, @Nonnull OrientationModifier yawModifier, double chance, @Nonnull CaveYawMode yawMode) {
/* 159 */     this.types = types;
/* 160 */     this.anchor = anchor;
/* 161 */     this.offset = offset;
/* 162 */     this.rotation = rotation;
/* 163 */     this.childrenLimit = childrenLimit;
/* 164 */     this.repeat = repeat;
/* 165 */     this.pitchModifier = pitchModifier;
/* 166 */     this.yawModifier = yawModifier;
/* 167 */     this.chance = chance;
/* 168 */     this.yawMode = yawMode;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IWeightedMap<CaveNodeType> getTypes() {
/* 173 */     return this.types;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor() {
/* 178 */     return this.anchor;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getOffset() {
/* 183 */     return this.offset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabRotation getRotation(@Nonnull Random random) {
/* 188 */     return (this.rotation.length == 1) ? this.rotation[0] : this.rotation[random.nextInt(this.rotation.length)];
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IDoubleRange getChildrenLimit() {
/* 193 */     return this.childrenLimit;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IDoubleRange getRepeat() {
/* 198 */     return this.repeat;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrientationModifier getPitchModifier() {
/* 203 */     return this.pitchModifier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OrientationModifier getYawModifier() {
/* 208 */     return this.yawModifier;
/*     */   }
/*     */   
/*     */   public double getChance() {
/* 212 */     return this.chance;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CaveYawMode getYawMode() {
/* 217 */     return this.yawMode;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface OrientationModifier {
/*     */     float calc(float param2Float, Random param2Random);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\CaveNodeType$CaveNodeChildEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */