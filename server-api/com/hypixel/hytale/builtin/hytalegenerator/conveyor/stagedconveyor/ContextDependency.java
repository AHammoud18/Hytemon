/*     */ package com.hypixel.hytale.builtin.hytalegenerator.conveyor.stagedconveyor;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextDependency
/*     */ {
/*     */   @Nonnull
/*  20 */   public static ContextDependency EMPTY = new ContextDependency(new Vector3i(), new Vector3i());
/*     */   
/*     */   private final Vector3i readRange;
/*     */   private final Vector3i writeRange;
/*     */   private Vector3i trashRange;
/*     */   private Vector3i externalDependencyRange;
/*     */   private Vector3i positioningRange;
/*     */   
/*     */   public ContextDependency(@Nonnull Vector3i readRange, @Nonnull Vector3i writeRange) {
/*  29 */     this.readRange = readRange.clone();
/*  30 */     this.writeRange = writeRange.clone();
/*  31 */     update();
/*     */   }
/*     */   
/*     */   public ContextDependency() {
/*  35 */     this(new Vector3i(), new Vector3i());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i getTotalPropBounds_voxelGrid() {
/*  40 */     Vector3i readMin_voxelGrid = getReadRange().scale(-1);
/*  41 */     Vector3i readMax_voxelGrid = getReadRange().add(Vector3i.ALL_ONES);
/*     */     
/*  43 */     Vector3i writeMin_voxelGrid = getWriteRange().scale(-1);
/*  44 */     Vector3i writeMax_voxelGrid = getWriteRange().add(Vector3i.ALL_ONES);
/*     */     
/*  46 */     Bounds3i readBounds_voxelGrid = new Bounds3i(readMin_voxelGrid, readMax_voxelGrid);
/*  47 */     Bounds3i writeBounds_voxelGrid = new Bounds3i(writeMin_voxelGrid, writeMax_voxelGrid);
/*     */     
/*  49 */     writeBounds_voxelGrid.stack(readBounds_voxelGrid);
/*  50 */     return writeBounds_voxelGrid;
/*     */   }
/*     */   
/*     */   private void update() {
/*  54 */     this.trashRange = VectorUtil.fromOperation(this.readRange, this.writeRange, (r, w, retriever) -> 
/*  55 */         (r < 0 || w < 0) ? 0 : (r + w));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     this.externalDependencyRange = VectorUtil.fromOperation(this.readRange, this.writeRange, (r, w, retriever) -> (r < 0) ? -w : Math.max(r, retriever.from(this.trashRange)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.positioningRange = VectorUtil.fromOperation(this.readRange, this.writeRange, (r, w, retriever) -> (r < 0) ? -w : r);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     this.trashRange.y = 0;
/*  80 */     this.externalDependencyRange.y = 0;
/*  81 */     this.positioningRange.y = 0;
/*  82 */     this.readRange.y = 0;
/*  83 */     this.writeRange.y = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ContextDependency stackOver(@Nonnull ContextDependency other) {
/*  90 */     Vector3i totalRead = new Vector3i();
/*  91 */     Vector3i totalWrite = new Vector3i();
/*     */     
/*  93 */     Vector3i r1 = getReadRange();
/*  94 */     Vector3i w1 = getWriteRange();
/*  95 */     Vector3i r2 = other.getReadRange();
/*  96 */     Vector3i w2 = other.getWriteRange();
/*     */     
/*  98 */     totalRead = VectorUtil.fromOperation(value -> 
/*  99 */         (value.from(r1) < 0 && value.from(r2) < 0) ? -1 : ((value.from(r1) < 0) ? value.from(r2) : ((value.from(r2) < 0) ? value.from(r1) : (value.from(r1) + value.from(w1) + value.from(r2)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     totalWrite = VectorUtil.fromOperation(value -> 
/* 113 */         (value.from(r1) < 0 && value.from(r2) < 0) ? -Math.min(value.from(w1), value.from(w2)) : ((value.from(r1) < 0) ? value.from(w2) : ((value.from(r2) < 0) ? value.from(w1) : value.from(w2))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return new ContextDependency(totalRead, totalWrite);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getReadRange() {
/* 132 */     return this.readRange.clone();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getWriteRange() {
/* 137 */     return this.writeRange.clone();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getTrashRange() {
/* 142 */     return this.trashRange.clone();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getExternalDependencyRange() {
/* 147 */     return this.externalDependencyRange.clone();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getPositioningRange() {
/* 152 */     return this.positioningRange.clone();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3i getRequiredPadOf(@Nonnull List<ContextDependency> dependencies) {
/* 157 */     Vector3i pad = new Vector3i();
/* 158 */     for (ContextDependency dependency : dependencies) {
/* 159 */       pad.add(dependency.getExternalDependencyRange());
/*     */     }
/* 161 */     return pad;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Integer, ContextDependency> cloneMap(@Nonnull Map<Integer, ContextDependency> map) {
/* 166 */     HashMap<Integer, ContextDependency> out = new HashMap<>(map.size());
/* 167 */     map.forEach((k, v) -> out.put(k, v.clone()));
/* 168 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Integer, ContextDependency> stackMaps(@Nonnull Map<Integer, ContextDependency> under, @Nonnull Map<Integer, ContextDependency> over) {
/* 174 */     Map<Integer, ContextDependency> out = new HashMap<>();
/* 175 */     for (Map.Entry<Integer, ContextDependency> entry : over.entrySet()) {
/* 176 */       if (!under.containsKey(entry.getKey())) {
/*     */         
/* 178 */         out.put(entry.getKey(), entry.getValue());
/*     */         
/*     */         continue;
/*     */       } 
/* 182 */       out.put(entry.getKey(), ((ContextDependency)entry.getValue()).stackOver(under.get(entry.getKey())));
/*     */     } 
/*     */ 
/*     */     
/* 186 */     for (Map.Entry<Integer, ContextDependency> entry : under.entrySet()) {
/* 187 */       if (!over.containsKey(entry.getKey())) {
/* 188 */         out.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/* 191 */     return out;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ContextDependency mostOf(@Nonnull List<ContextDependency> dependencies) {
/* 196 */     ContextDependency out = EMPTY;
/* 197 */     for (ContextDependency d : dependencies) {
/* 198 */       out = mostOf(out, d);
/*     */     }
/* 200 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ContextDependency mostOf(@Nonnull ContextDependency a, @Nonnull ContextDependency b) {
/* 206 */     Vector3i read = Vector3i.max(a.readRange, b.readRange);
/* 207 */     Vector3i write = Vector3i.max(a.writeRange, b.writeRange);
/*     */     
/* 209 */     return new ContextDependency(read, write);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ContextDependency clone() {
/* 215 */     return new ContextDependency(this.readRange, this.writeRange);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\conveyor\stagedconveyor\ContextDependency.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */