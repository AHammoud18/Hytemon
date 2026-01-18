/*     */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ public class GapPattern
/*     */   extends Pattern
/*     */ {
/*     */   private List<List<PositionedPattern>> axisPositionedPatterns;
/*     */   private List<PositionedPattern> depthPositionedPatterns;
/*     */   private double gapSize;
/*     */   private double anchorSize;
/*     */   private double anchorRoughness;
/*     */   private int depthDown;
/*     */   private int depthUp;
/*     */   private Pattern gapPattern;
/*     */   private Pattern anchorPattern;
/*     */   private SpaceSize readSpaceSize;
/*     */   
/*     */   public GapPattern(@Nonnull List<Float> angles, double gapSize, double anchorSize, double anchorRoughness, int depthDown, int depthUp, @Nonnull Pattern gapPattern, @Nonnull Pattern anchorPattern) {
/*  35 */     if (gapSize < 0.0D || anchorSize < 0.0D || anchorRoughness < 0.0D || depthDown < 0 || depthUp < 0) {
/*  36 */       throw new IllegalArgumentException("negative sizes");
/*     */     }
/*  38 */     this.gapSize = gapSize;
/*  39 */     this.anchorSize = anchorSize;
/*  40 */     this.gapPattern = gapPattern;
/*  41 */     this.anchorPattern = anchorPattern;
/*  42 */     this.anchorRoughness = anchorRoughness;
/*  43 */     this.depthDown = depthDown;
/*  44 */     this.depthUp = depthUp;
/*     */     
/*  46 */     this.depthPositionedPatterns = renderDepths();
/*     */     
/*  48 */     this.axisPositionedPatterns = new ArrayList<>(angles.size());
/*  49 */     for (Iterator<Float> iterator = angles.iterator(); iterator.hasNext(); ) { float angle = ((Float)iterator.next()).floatValue();
/*  50 */       List<PositionedPattern> positions = renderPositions(angle);
/*  51 */       this.axisPositionedPatterns.add(positions); }
/*     */ 
/*     */ 
/*     */     
/*  55 */     Vector3i min = null;
/*  56 */     Vector3i max = null;
/*  57 */     for (List<PositionedPattern> direction : this.axisPositionedPatterns) {
/*  58 */       for (PositionedPattern pos : direction) {
/*  59 */         if (min == null) {
/*  60 */           min = pos.position.clone();
/*  61 */           max = pos.position.clone();
/*     */           continue;
/*     */         } 
/*  64 */         min = Vector3i.min(min, pos.position);
/*  65 */         max = Vector3i.max(max, pos.position);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  70 */     if (max == null) {
/*  71 */       this.readSpaceSize = new SpaceSize(new Vector3i(), new Vector3i());
/*     */       
/*     */       return;
/*     */     } 
/*  75 */     max.add(1, 1, 1);
/*  76 */     this.readSpaceSize = new SpaceSize(min, max);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private List<PositionedPattern> renderDepths() {
/*  81 */     ArrayList<PositionedPattern> positions = new ArrayList<>();
/*     */     
/*  83 */     Vector3i pointer = new Vector3i();
/*  84 */     int stepsDown = this.depthDown - 1;
/*  85 */     for (int i = 0; i < this.depthDown; i++) {
/*  86 */       pointer.add(0, -1, 0);
/*  87 */       positions.add(new PositionedPattern(this.gapPattern, pointer.clone()));
/*     */     } 
/*  89 */     pointer = new Vector3i();
/*  90 */     int stepsUp = this.depthUp - 1;
/*  91 */     for (int j = 0; j < this.depthUp; j++) {
/*  92 */       pointer.add(0, 1, 0);
/*  93 */       positions.add(new PositionedPattern(this.gapPattern, pointer.clone()));
/*     */     } 
/*  95 */     return positions;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private List<PositionedPattern> renderPositions(float angle) {
/* 100 */     ArrayList<PositionedPattern> positions = new ArrayList<>();
/*     */ 
/*     */     
/* 103 */     positions.addAll(renderHalfPositions(angle));
/* 104 */     positions.addAll(renderHalfPositions(3.1415927F + angle));
/*     */ 
/*     */     
/* 107 */     ArrayList<PositionedPattern> uniquePositions = new ArrayList<>(positions.size());
/* 108 */     HashSet<Vector3i> positionsSet = new HashSet<>();
/* 109 */     for (PositionedPattern e : positions) {
/* 110 */       if (positionsSet.contains(e.position))
/* 111 */         continue;  uniquePositions.add(e);
/* 112 */       positionsSet.add(e.position);
/*     */     } 
/*     */     
/* 115 */     return uniquePositions;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private List<PositionedPattern> renderHalfPositions(float angle) {
/* 120 */     ArrayList<PositionedPattern> positions = new ArrayList<>();
/*     */     
/* 122 */     double halfGap = this.gapSize / 2.0D - 1.0D - this.anchorRoughness;
/* 123 */     halfGap = Math.max(0.0D, halfGap);
/* 124 */     double halfWall = this.anchorSize / 2.0D;
/*     */     
/* 126 */     Vector3d pointer = new Vector3d(0.5D, 0.5D, 0.5D);
/* 127 */     Vector3d mov = new Vector3d(0.0D, 0.0D, -1.0D);
/*     */ 
/*     */     
/* 130 */     mov.rotateY(angle);
/*     */ 
/*     */     
/* 133 */     double stepSize = 0.5D;
/* 134 */     mov.setLength(stepSize);
/* 135 */     int steps = (int)(halfGap / stepSize);
/* 136 */     for (int s = 0; s < steps; s++) {
/* 137 */       pointer.add(mov);
/* 138 */       positions.add(new PositionedPattern(this.gapPattern, pointer.toVector3i()));
/*     */     } 
/*     */ 
/*     */     
/* 142 */     positions.add(new PositionedPattern(this.gapPattern, new Vector3i()));
/* 143 */     pointer = mov.clone().setLength(halfGap).add(0.5D, 0.5D, 0.5D);
/* 144 */     positions.add(new PositionedPattern(this.gapPattern, pointer.toVector3i()));
/*     */ 
/*     */ 
/*     */     
/* 148 */     Vector3d anchor = mov.clone().setLength(this.gapSize / 2.0D);
/* 149 */     pointer = anchor.clone().add(0.5D, 0.5D, 0.5D);
/* 150 */     positions.add(new PositionedPattern(this.anchorPattern, anchor.toVector3i()));
/*     */ 
/*     */     
/* 153 */     mov.rotateY(1.5707964F);
/* 154 */     steps = (int)(halfWall / stepSize);
/* 155 */     for (int i = 0; i < steps; i++) {
/* 156 */       pointer.add(mov);
/* 157 */       positions.add(new PositionedPattern(this.anchorPattern, pointer.toVector3i()));
/*     */     } 
/* 159 */     Vector3d wallTip = anchor.clone().add(0.5D, 0.5D, 0.5D);
/* 160 */     wallTip.add(mov.clone().setLength(halfWall));
/* 161 */     positions.add(new PositionedPattern(this.anchorPattern, wallTip.toVector3i()));
/*     */ 
/*     */     
/* 164 */     mov.scale(-1.0D);
/* 165 */     pointer = anchor.clone().add(0.5D, 0.5D, 0.5D);
/* 166 */     for (int j = 0; j < steps; j++) {
/* 167 */       pointer.add(mov);
/* 168 */       positions.add(new PositionedPattern(this.anchorPattern, pointer.toVector3i()));
/*     */     } 
/* 170 */     wallTip = anchor.clone().add(0.5D, 0.5D, 0.5D);
/* 171 */     wallTip.add(mov.clone().setLength(halfWall));
/* 172 */     positions.add(new PositionedPattern(this.anchorPattern, wallTip.toVector3i()));
/*     */     
/* 174 */     return positions;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Pattern.Context context) {
/* 179 */     Vector3i childPosition = new Vector3i();
/* 180 */     Pattern.Context childContext = new Pattern.Context(context);
/* 181 */     childContext.position = childPosition;
/*     */     
/* 183 */     for (PositionedPattern entry : this.depthPositionedPatterns) {
/* 184 */       childPosition.assign(entry.position).add(context.position);
/* 185 */       if (!entry.pattern.matches(childContext)) {
/* 186 */         return false;
/*     */       }
/*     */     } 
/* 189 */     for (List<PositionedPattern> patternsInDirection : this.axisPositionedPatterns) {
/* 190 */       boolean matchesDirection = true;
/*     */       
/* 192 */       for (PositionedPattern entry : patternsInDirection) {
/* 193 */         childPosition.assign(entry.position).add(context.position);
/* 194 */         if (entry.pattern.matches(context)) {
/*     */           continue;
/*     */         }
/* 197 */         matchesDirection = false;
/*     */       } 
/*     */       
/* 200 */       if (matchesDirection) return true; 
/*     */     } 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SpaceSize readSpace() {
/* 208 */     return this.readSpaceSize.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PositionedPattern
/*     */   {
/*     */     private Vector3i position;
/*     */     private Pattern pattern;
/*     */     
/*     */     public PositionedPattern(@Nonnull Pattern pattern, @Nonnull Vector3i position) {
/* 218 */       this.pattern = pattern;
/* 219 */       this.position = position.clone();
/*     */     }
/*     */     
/*     */     public int getX() {
/* 223 */       return this.position.x;
/*     */     }
/*     */     
/*     */     public int getY() {
/* 227 */       return this.position.y;
/*     */     }
/*     */     
/*     */     public int getZ() {
/* 231 */       return this.position.z;
/*     */     }
/*     */     
/*     */     public Pattern getPattern() {
/* 235 */       return this.pattern;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected PositionedPattern clone() {
/* 241 */       return new PositionedPattern(this.pattern, this.position.clone());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\GapPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */