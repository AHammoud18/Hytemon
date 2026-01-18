/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorEntity;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorEntityBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class SensorEntity
/*    */   extends SensorEntityBase
/*    */ {
/*    */   protected final boolean getPlayers;
/*    */   protected final boolean getNPCs;
/*    */   protected final boolean excludeOwnType;
/*    */   
/*    */   public SensorEntity(@Nonnull BuilderSensorEntity builder, @Nonnull BuilderSupport builderSupport) {
/* 17 */     super((BuilderSensorEntityBase)builder, builder.getPrioritiser(builderSupport), builderSupport);
/* 18 */     this.getPlayers = builder.isGetPlayers(builderSupport);
/* 19 */     this.getNPCs = builder.isGetNPCs(builderSupport);
/* 20 */     this.excludeOwnType = builder.isExcludeOwnType(builderSupport);
/* 21 */     initialisePrioritiser();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGetPlayers() {
/* 26 */     return this.getPlayers;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGetNPCs() {
/* 31 */     return this.getNPCs;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExcludingOwnType() {
/* 36 */     return this.excludeOwnType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */