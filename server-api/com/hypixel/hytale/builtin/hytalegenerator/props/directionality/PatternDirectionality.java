/*     */ package com.hypixel.hytale.builtin.hytalegenerator.props.directionality;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.OrPattern;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class PatternDirectionality
/*     */   extends Directionality
/*     */ {
/*     */   @Nonnull
/*     */   private final List<PrefabRotation> rotations;
/*     */   @Nonnull
/*     */   private final PrefabRotation south;
/*     */   @Nonnull
/*     */   private final PrefabRotation north;
/*     */   @Nonnull
/*     */   private final PrefabRotation east;
/*     */   @Nonnull
/*     */   private final PrefabRotation west;
/*     */   @Nonnull
/*     */   private final Pattern southPattern;
/*     */   @Nonnull
/*     */   private final Pattern northPattern;
/*     */   @Nonnull
/*     */   private final Pattern eastPattern;
/*     */   @Nonnull
/*     */   private final Pattern westPattern;
/*     */   @Nonnull
/*     */   private final Pattern generalPattern;
/*     */   @Nonnull
/*     */   private final SeedGenerator seedGenerator;
/*     */   
/*     */   public PatternDirectionality(@Nonnull OrthogonalDirection startingDirection, @Nonnull Pattern southPattern, @Nonnull Pattern northPattern, @Nonnull Pattern eastPattern, @Nonnull Pattern westPattern, int seed) {
/*  50 */     this.southPattern = southPattern;
/*  51 */     this.northPattern = northPattern;
/*  52 */     this.eastPattern = eastPattern;
/*  53 */     this.westPattern = westPattern;
/*  54 */     this.generalPattern = (Pattern)new OrPattern(List.of(northPattern, southPattern, eastPattern, westPattern));
/*     */     
/*  56 */     this.seedGenerator = new SeedGenerator(seed);
/*     */     
/*  58 */     switch (startingDirection) {
/*     */       default:
/*  60 */         this.north = PrefabRotation.ROTATION_0;
/*  61 */         this.east = PrefabRotation.ROTATION_270;
/*  62 */         this.south = PrefabRotation.ROTATION_180;
/*  63 */         this.west = PrefabRotation.ROTATION_90;
/*     */         break;
/*     */       case S:
/*  66 */         this.south = PrefabRotation.ROTATION_0;
/*  67 */         this.west = PrefabRotation.ROTATION_270;
/*  68 */         this.north = PrefabRotation.ROTATION_180;
/*  69 */         this.east = PrefabRotation.ROTATION_90;
/*     */         break;
/*     */       case E:
/*  72 */         this.east = PrefabRotation.ROTATION_180;
/*  73 */         this.south = PrefabRotation.ROTATION_90;
/*  74 */         this.west = PrefabRotation.ROTATION_0;
/*  75 */         this.north = PrefabRotation.ROTATION_270;
/*     */         break;
/*     */       case W:
/*  78 */         this.west = PrefabRotation.ROTATION_180;
/*  79 */         this.north = PrefabRotation.ROTATION_90;
/*  80 */         this.east = PrefabRotation.ROTATION_0;
/*  81 */         this.south = PrefabRotation.ROTATION_270;
/*     */         break;
/*     */     } 
/*     */     
/*  85 */     this.rotations = Collections.unmodifiableList(List.of(this.north, this.south, this.east, this.west));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Pattern getGeneralPattern() {
/*  91 */     return this.generalPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getReadRangeWith(@Nonnull Scanner scanner) {
/*  98 */     return scanner.readSpaceWith(this.generalPattern).getRange();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<PrefabRotation> getPossibleRotations() {
/* 104 */     return this.rotations;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrefabRotation getRotationAt(@Nonnull Pattern.Context context) {
/* 109 */     ArrayList<PrefabRotation> successful = new ArrayList<>(4);
/* 110 */     if (this.northPattern.matches(context))
/* 111 */       successful.add(this.north); 
/* 112 */     if (this.southPattern.matches(context))
/* 113 */       successful.add(this.south); 
/* 114 */     if (this.eastPattern.matches(context))
/* 115 */       successful.add(this.east); 
/* 116 */     if (this.westPattern.matches(context)) {
/* 117 */       successful.add(this.west);
/*     */     }
/* 119 */     if (successful.isEmpty()) return null;
/*     */     
/* 121 */     FastRandom random = new FastRandom(this.seedGenerator.seedAt(context.position.x, context.position.y, context.position.z));
/* 122 */     return successful.get(random.nextInt(successful.size()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\directionality\PatternDirectionality.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */