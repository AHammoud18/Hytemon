/*    */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.builtin.buildertools.utils.Material;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.block.BlockUtil;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PaintOperation
/*    */   extends ToolOperation
/*    */ {
/*    */   private final int brushDensity;
/*    */   private LongOpenHashSet packedPlacedBlockPositions;
/*    */   
/*    */   public PaintOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 27 */     super(ref, packet, componentAccessor);
/*    */     
/* 29 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 30 */     assert uuidComponent != null;
/*    */     
/* 32 */     PrototypePlayerBuilderToolSettings prototypePlayerBuilderToolSettings = ToolOperation.PROTOTYPE_TOOL_SETTINGS.get(uuidComponent.getUuid());
/* 33 */     this.packedPlacedBlockPositions = prototypePlayerBuilderToolSettings.addIgnoredPaintOperation();
/*    */     
/* 35 */     this.brushDensity = getBrushDensity();
/*    */   }
/*    */ 
/*    */   
/*    */   boolean execute0(int x, int y, int z) {
/* 40 */     if (y < 0 || y >= 320) return true;
/*    */     
/* 42 */     if (this.edit.getBlock(x, y, z) == 0) {
/* 43 */       this.packedPlacedBlockPositions.add(BlockUtil.pack(x, y, z));
/*    */     }
/*    */     
/* 46 */     if (this.random.nextInt(100) <= this.brushDensity) {
/* 47 */       this.edit.setMaterial(x, y, z, Material.fromPattern(this.pattern, this.random));
/*    */     }
/*    */     
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\PaintOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */