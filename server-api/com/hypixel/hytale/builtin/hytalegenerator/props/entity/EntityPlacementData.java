/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.entity;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityPlacementData
/*    */   implements MemInstrument {
/*    */   private final Vector3i offset;
/*    */   private final PrefabRotation rotation;
/*    */   private final Holder<EntityStore> entityHolder;
/*    */   private final int objectId;
/*    */   
/*    */   public EntityPlacementData(Vector3i offset, PrefabRotation rotation, Holder<EntityStore> entityHolder, int objectId) {
/* 18 */     this.offset = offset;
/* 19 */     this.rotation = rotation;
/*    */     
/* 21 */     this.entityHolder = entityHolder;
/* 22 */     this.objectId = objectId;
/*    */   }
/*    */   
/*    */   public Vector3i getOffset() {
/* 26 */     return this.offset;
/*    */   }
/*    */   
/*    */   public PrefabRotation getRotation() {
/* 30 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public Holder<EntityStore> getEntityHolder() {
/* 34 */     return this.entityHolder;
/*    */   }
/*    */   
/*    */   public int getObjectId() {
/* 38 */     return this.objectId;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MemInstrument.Report getMemoryUsage() {
/* 44 */     long size_bytes = 48L;
/*    */ 
/*    */ 
/*    */     
/* 48 */     return new MemInstrument.Report(48L);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\entity\EntityPlacementData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */