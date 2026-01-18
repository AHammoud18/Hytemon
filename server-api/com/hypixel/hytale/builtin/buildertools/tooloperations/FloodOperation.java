/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloodOperation
/*    */   extends ToolOperation
/*    */ {
/*    */   public FloodOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 20 */     super(ref, packet, componentAccessor);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ComponentAccessor<EntityStore> componentAccessor) {
/* 25 */     BlockPattern targetPattern = (BlockPattern)this.args.tool().get("TargetBlock");
/* 26 */     int targetBlock = targetPattern.isEmpty() ? this.edit.getAccessor().getBlock(this.x, this.y, this.z) : targetPattern.firstBlock();
/*    */     
/* 28 */     Player playerComponent = (Player)componentAccessor.getComponent(this.playerRef, Player.getComponentType());
/* 29 */     assert playerComponent != null;
/*    */     
/* 31 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(this.playerRef, PlayerRef.getComponentType());
/* 32 */     assert playerRefComponent != null;
/*    */     
/* 34 */     BuilderToolsPlugin.getState(playerComponent, playerRefComponent)
/* 35 */       .flood(this.edit, this.x, this.y + this.originOffsetY, this.z, this.shapeRange, this.shapeHeight, this.pattern, targetBlock);
/*    */   }
/*    */ 
/*    */   
/*    */   boolean execute0(int x, int y, int z) {
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\FloodOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */