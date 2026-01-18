/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseOperation
/*    */   extends ToolOperation
/*    */ {
/*    */   private final int brushDensity;
/*    */   
/*    */   public NoiseOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 19 */     super(ref, packet, componentAccessor);
/*    */     
/* 21 */     this.brushDensity = getBrushDensity();
/*    */   }
/*    */ 
/*    */   
/*    */   boolean execute0(int x, int y, int z) {
/* 26 */     int currentBlock = this.edit.getBlock(x, y, z);
/*    */     
/* 28 */     if (currentBlock <= 0 && this.builderState.isAsideBlock((ChunkAccessor)this.edit.getAccessor(), x, y, z) && 
/* 29 */       this.random.nextInt(100) <= this.brushDensity) {
/* 30 */       this.edit.setBlock(x, y, z, this.pattern.nextBlock(this.random));
/*    */     }
/*    */ 
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\NoiseOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */