/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorEntityBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorPlayer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class SensorPlayer
/*    */   extends SensorEntityBase
/*    */ {
/*    */   public SensorPlayer(@Nonnull BuilderSensorPlayer builder, @Nonnull BuilderSupport builderSupport) {
/* 13 */     super((BuilderSensorEntityBase)builder, builder.getPrioritiser(builderSupport), builderSupport);
/* 14 */     initialisePrioritiser();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGetPlayers() {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGetNPCs() {
/* 24 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorPlayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */