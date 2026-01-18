/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.builtin.buildertools.utils.Material;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.block.BlockUtil;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SculptOperation
/*    */   extends ToolOperation
/*    */ {
/*    */   private final int smoothVolume;
/*    */   private final int smoothRadius;
/*    */   private final boolean isAltPlaySculptBrushModDown;
/*    */   private final int brushDensity;
/*    */   private LongOpenHashSet packedPlacedBlockPositions;
/*    */   
/*    */   public SculptOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 30 */     super(ref, packet, componentAccessor);
/*    */     
/* 32 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 33 */     assert uuidComponent != null;
/*    */     
/* 35 */     this.isAltPlaySculptBrushModDown = packet.isAltPlaySculptBrushModDown;
/*    */     
/* 37 */     PrototypePlayerBuilderToolSettings prototypePlayerBuilderToolSettings = ToolOperation.PROTOTYPE_TOOL_SETTINGS.get(uuidComponent.getUuid());
/* 38 */     this.packedPlacedBlockPositions = prototypePlayerBuilderToolSettings.addIgnoredPaintOperation();
/*    */ 
/*    */     
/* 41 */     int smoothStrength = ((Integer)this.args.tool().get("SmoothStrength")).intValue();
/* 42 */     this.smoothRadius = Math.min(smoothStrength, 4);
/* 43 */     int smoothRange = this.smoothRadius * 2 + 1;
/* 44 */     this.smoothVolume = smoothRange * smoothRange * smoothRange;
/*    */     
/* 46 */     this.brushDensity = getBrushDensity();
/*    */   }
/*    */ 
/*    */   
/*    */   boolean execute0(int x, int y, int z) {
/* 51 */     int currentBlock = this.edit.getBlock(x, y, z);
/*    */ 
/*    */     
/* 54 */     if (this.isAltPlaySculptBrushModDown) {
/*    */ 
/*    */ 
/*    */       
/* 58 */       BuilderToolsPlugin.BuilderState.BlocksSampleData data = BuilderToolsPlugin.getState(this.player, this.player.getPlayerRef()).getBlocksSampleData((ChunkAccessor)this.edit.getAccessor(), x, y, z, 2);
/* 59 */       if (currentBlock != data.mainBlock && data.mainBlockCount > this.smoothVolume * 0.5F) {
/* 60 */         this.edit.setMaterial(x, y, z, Material.block(data.mainBlock));
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 65 */     else if (this.interactionType == InteractionType.Primary) {
/*    */       
/* 67 */       if (currentBlock > 0 && this.builderState.isAsideAir((ChunkAccessor)this.edit.getAccessor(), x, y, z) && 
/* 68 */         this.random.nextInt(100) <= this.brushDensity) {
/* 69 */         this.edit.setMaterial(x, y, z, Material.EMPTY);
/*    */       }
/*    */     }
/* 72 */     else if (this.interactionType == InteractionType.Secondary) {
/*    */       
/* 74 */       if (currentBlock <= 0 && this.builderState.isAsideBlock((ChunkAccessor)this.edit.getAccessor(), x, y, z)) {
/* 75 */         if (this.edit.getBlock(x, y, z) == 0) {
/* 76 */           this.packedPlacedBlockPositions.add(BlockUtil.pack(x, y, z));
/*    */         }
/*    */         
/* 79 */         if (this.random.nextInt(100) <= this.brushDensity) {
/* 80 */           Material material = Material.fromPattern(this.pattern, this.random);
/*    */           
/* 82 */           if (material.isEmpty()) {
/* 83 */             BuilderToolsPlugin.BuilderState.BlocksSampleData data = this.builderState.getBlocksSampleData((ChunkAccessor)this.edit.getAccessor(), x, y, z, 1);
/* 84 */             material = Material.block(data.mainBlockNotAir);
/*    */           } 
/* 86 */           this.edit.setMaterial(x, y, z, material);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\SculptOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */