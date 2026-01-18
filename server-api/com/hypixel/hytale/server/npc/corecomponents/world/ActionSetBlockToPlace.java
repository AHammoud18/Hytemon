/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionSetBlockToPlace;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionSetBlockToPlace extends ActionBase {
/*    */   protected final String blockType;
/*    */   
/*    */   public ActionSetBlockToPlace(@Nonnull BuilderActionSetBlockToPlace builder, @Nonnull BuilderSupport support) {
/* 19 */     super((BuilderActionBase)builder);
/* 20 */     this.blockType = builder.getBlockType(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && BlockType.getAssetMap().getAsset(this.blockType) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     super.execute(ref, role, sensorInfo, dt, store);
/* 31 */     role.getWorldSupport().setBlockToPlace(this.blockType);
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionSetBlockToPlace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */