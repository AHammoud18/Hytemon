/*    */ package com.hypixel.hytale.server.npc.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.resource.ResourceView;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class BlockTarget
/*    */ {
/* 14 */   private final Vector3d position = new Vector3d(Vector3d.MIN);
/* 15 */   private int chunkChangeRevision = -1;
/* 16 */   private int foundBlockType = Integer.MIN_VALUE;
/*    */   @Nullable
/*    */   private ResourceView reservationHolder;
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPosition() {
/* 22 */     return this.position;
/*    */   }
/*    */   
/*    */   public int getChunkChangeRevision() {
/* 26 */     return this.chunkChangeRevision;
/*    */   }
/*    */   
/*    */   public int getFoundBlockType() {
/* 30 */     return this.foundBlockType;
/*    */   }
/*    */   
/*    */   public void setChunkChangeRevision(int chunkChangeRevision) {
/* 34 */     this.chunkChangeRevision = chunkChangeRevision;
/*    */   }
/*    */   
/*    */   public void setFoundBlockType(int foundBlockType) {
/* 38 */     this.foundBlockType = foundBlockType;
/*    */   }
/*    */   
/*    */   public void setReservationHolder(ResourceView resourceView) {
/* 42 */     this.reservationHolder = resourceView;
/*    */   }
/*    */   
/*    */   public void reset(@Nonnull NPCEntity parent) {
/* 46 */     if (this.reservationHolder != null) {
/* 47 */       this.reservationHolder.clearReservation(parent.getReference());
/* 48 */       Blackboard.LOGGER.at(Level.FINE).log("Entity %s cleared reservation at %s", parent.getRoleName(), this.position);
/*    */     } 
/* 50 */     this.reservationHolder = null;
/* 51 */     this.position.assign(Vector3d.MIN);
/* 52 */     this.chunkChangeRevision = -1;
/* 53 */     this.foundBlockType = Integer.MIN_VALUE;
/*    */   }
/*    */   
/*    */   public boolean isActive() {
/* 57 */     return (this.foundBlockType >= 0);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\BlockTarget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */