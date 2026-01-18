/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SmoothOperation
/*    */   extends ToolOperation
/*    */ {
/*    */   private final int smoothVolume;
/*    */   
/*    */   public SmoothOperation(@Nonnull Ref<EntityStore> ref, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 17 */     super(ref, packet, componentAccessor);
/*    */     
/* 19 */     int smoothStrength = ((Integer)this.args.tool().get("SmoothStrength")).intValue();
/*    */ 
/*    */     
/* 22 */     int smoothRange = Math.min(smoothStrength, 4) * 2 + 1;
/* 23 */     this.smoothVolume = smoothRange * smoothRange * smoothRange;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean execute0(int x, int y, int z) {
/* 28 */     int currentBlock = this.edit.getBlock(x, y, z);
/*    */     
/* 30 */     BuilderToolsPlugin.BuilderState.BlocksSampleData data = BuilderToolsPlugin.getState(this.player, this.player.getPlayerRef()).getBlocksSampleData((ChunkAccessor)this.edit.getAccessor(), x, y, z, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 35 */     if (currentBlock != data.mainBlock && data.mainBlockCount > this.smoothVolume * 0.5F) {
/* 36 */       this.edit.setBlock(x, y, z, data.mainBlock);
/*    */     }
/*    */     
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\SmoothOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */