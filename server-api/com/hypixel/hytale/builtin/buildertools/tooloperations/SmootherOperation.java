/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SmootherOperation
/*    */   extends ToolOperation
/*    */ {
/*    */   private final float strength;
/*    */   
/*    */   public SmootherOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 19 */     super(ref, packet, componentAccessor);
/*    */     
/* 21 */     boolean removing = (packet.type == InteractionType.Primary);
/* 22 */     int baseStrength = removing ? ((Integer)this.args.tool().get("RemoveStrength")).intValue() : ((Integer)this.args.tool().get("AddStrength")).intValue();
/*    */     
/* 24 */     this.strength = 1.0F + (removing ? baseStrength : -baseStrength) * 0.01F;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean execute0(int x, int y, int z) {
/* 29 */     int currentBlock = this.edit.getBlock(x, y, z);
/* 30 */     BuilderToolsPlugin.BuilderState.SmoothSampleData data = this.builderState.getBlocksSmoothData((ChunkAccessor)this.edit.getAccessor(), x, y, z);
/* 31 */     if (data.solidStrength > this.strength) {
/* 32 */       if (currentBlock != data.solidBlock) {
/* 33 */         this.edit.setBlock(x, y, z, data.solidBlock);
/*    */       }
/*    */     }
/* 36 */     else if (currentBlock != data.fillerBlock) {
/* 37 */       this.edit.setBlock(x, y, z, data.fillerBlock);
/*    */     } 
/*    */ 
/*    */     
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\SmootherOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */