/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ScatterOperation
/*    */   extends ToolOperation
/*    */ {
/*    */   private final int brushDensity;
/*    */   private final BlockTypeAssetMap<String, BlockType> assetMap;
/*    */   
/*    */   public ScatterOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 21 */     super(ref, packet, componentAccessor);
/*    */     
/* 23 */     this.brushDensity = getBrushDensity();
/* 24 */     this.assetMap = BlockType.getAssetMap();
/*    */   }
/*    */ 
/*    */   
/*    */   boolean execute0(int x, int y, int z) {
/* 29 */     int currentBlock = this.edit.getBlock(x, y, z);
/*    */     
/* 31 */     if (currentBlock <= 0 && this.builderState.isAsideBlock((ChunkAccessor)this.edit.getAccessor(), x, y, z) && (((BlockType)this.assetMap.getAsset(this.edit.getBlock(x, y - 1, z))).getFlags()).isStackable && 
/* 32 */       this.random.nextInt(100) <= this.brushDensity) {
/* 33 */       this.edit.setBlock(x, y, z, this.pattern.nextBlock(this.random));
/*    */     }
/*    */ 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\ScatterOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */