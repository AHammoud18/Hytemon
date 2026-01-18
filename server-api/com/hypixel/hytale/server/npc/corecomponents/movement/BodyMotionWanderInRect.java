/*     */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWanderBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWanderInRect;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BodyMotionWanderInRect
/*     */   extends BodyMotionWanderBase {
/*     */   public static final int LEFT = 1;
/*     */   public static final int RIGHT = 2;
/*     */   public static final int BOTTOM = 4;
/*     */   public static final int TOP = 8;
/*     */   public static final int VERTICAL_MASK = 12;
/*     */   public static final int HORIZONTAL_MASK = 3;
/*     */   protected final double width;
/*     */   protected final double depth;
/*     */   protected final double halfWidth;
/*     */   protected final double halfDepth;
/*     */   
/*     */   public BodyMotionWanderInRect(@Nonnull BuilderBodyMotionWanderInRect builder, @Nonnull BuilderSupport builderSupport) {
/*  28 */     super((BuilderBodyMotionWanderBase)builder, builderSupport);
/*  29 */     this.width = builder.getWidth();
/*  30 */     this.halfWidth = this.width / 2.0D;
/*  31 */     this.depth = builder.getDepth();
/*  32 */     this.halfDepth = this.depth / 2.0D;
/*     */   }
/*     */   
/*     */   protected double constrainMove(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Vector3d probePosition, @Nonnull Vector3d targetPosition, double moveDist, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     double scaleX, scaleZ;
/*  37 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/*  38 */     assert npcComponent != null;
/*     */     
/*  40 */     Vector3d leash = npcComponent.getLeashPoint();
/*  41 */     double leashX = leash.getX();
/*  42 */     double leashZ = leash.getZ();
/*  43 */     double endX = targetPosition.x - leashX;
/*  44 */     double endZ = targetPosition.z - leashZ;
/*     */     
/*  46 */     int endCode = sectorCode(endX, endZ);
/*     */     
/*  48 */     if (endCode == 0)
/*     */     {
/*  50 */       return moveDist;
/*     */     }
/*     */     
/*  53 */     double startX = probePosition.x - leashX;
/*  54 */     double startZ = probePosition.z - leashZ;
/*     */     
/*  56 */     int startCode = sectorCode(startX, startZ);
/*     */     
/*  58 */     if (startCode != 0) {
/*     */       
/*  60 */       if ((startCode & endCode) != 0) {
/*     */         
/*  62 */         if (distanceSquared(endX, endZ, endCode) < distanceSquared(startX, startZ, startCode)) {
/*  63 */           return moveDist;
/*     */         }
/*  65 */         return 0.0D;
/*     */       } 
/*     */ 
/*     */       
/*  69 */       int or = startCode | endCode;
/*     */       
/*  71 */       if ((or & 0xC) == 12 || (or & 0x3) == 3)
/*     */       {
/*     */         
/*  74 */         return 0.0D;
/*     */       }
/*     */       
/*  77 */       if (distanceSquared(endX, endZ, endCode) < distanceSquared(startX, startZ, startCode)) {
/*  78 */         return moveDist;
/*     */       }
/*  80 */       return 0.0D;
/*     */     } 
/*     */ 
/*     */     
/*  84 */     double dx = endX - startX;
/*  85 */     double dz = endZ - startZ;
/*     */ 
/*     */ 
/*     */     
/*  89 */     if ((endCode & 0x1) == 1) {
/*  90 */       scaleX = (-this.halfWidth - startX) / dx;
/*  91 */     } else if ((endCode & 0x2) == 2) {
/*  92 */       scaleX = (this.halfWidth - startX) / dx;
/*     */     } else {
/*  94 */       scaleX = 1.0D;
/*     */     } 
/*     */     
/*  97 */     if ((endCode & 0x4) == 4) {
/*  98 */       scaleZ = (-this.halfDepth - startZ) / dz;
/*  99 */     } else if ((endCode & 0x8) == 8) {
/* 100 */       scaleZ = (this.halfDepth - startZ) / dz;
/*     */     } else {
/* 102 */       scaleZ = 1.0D;
/*     */     } 
/*     */     
/* 105 */     if (scaleX < 0.0D || scaleX > 1.0D) throw new IllegalArgumentException("WanderInRect: Constrained X outside of allowed range!"); 
/* 106 */     if (scaleZ < 0.0D || scaleZ > 1.0D) throw new IllegalArgumentException("WanderInRect: Constrained Z outside of allowed range!"); 
/* 107 */     return moveDist * Math.min(scaleX, scaleZ);
/*     */   }
/*     */   
/*     */   protected int sectorCode(double x, double z) {
/* 111 */     int code = 0;
/* 112 */     if (x < -this.halfWidth) {
/* 113 */       code |= 0x1;
/* 114 */     } else if (x > this.halfWidth) {
/* 115 */       code |= 0x2;
/*     */     } 
/* 117 */     if (z < -this.halfDepth) {
/* 118 */       code |= 0x4;
/* 119 */     } else if (z > this.halfDepth) {
/* 120 */       code |= 0x8;
/*     */     } 
/* 122 */     return code;
/*     */   }
/*     */   
/*     */   protected double distanceSquared(double x, double z, int sector) {
/* 126 */     switch (sector) { case 1: case 2: case 4: case 8: case 9: case 5: case 10: case 6:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       0.0D;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionWanderInRect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */