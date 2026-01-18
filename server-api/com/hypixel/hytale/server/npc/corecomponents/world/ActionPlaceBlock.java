/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionPlaceBlock;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.CachedPositionProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.BlockPlacementHelper;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionPlaceBlock extends ActionBase {
/* 27 */   protected static final ComponentType<EntityStore, BoundingBox> BOUNDING_BOX_COMPONENT_TYPE = BoundingBox.getComponentType();
/*    */   
/*    */   protected final double range;
/*    */   
/*    */   protected final boolean allowEmptyMaterials;
/* 32 */   protected final Vector3d target = new Vector3d();
/*    */   
/*    */   public ActionPlaceBlock(@Nonnull BuilderActionPlaceBlock builder, @Nonnull BuilderSupport support) {
/* 35 */     super((BuilderActionBase)builder);
/* 36 */     this.range = builder.getRange(support);
/* 37 */     this.allowEmptyMaterials = builder.isAllowEmptyMaterials(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 42 */     if (!super.canExecute(ref, role, sensorInfo, dt, store) || sensorInfo == null || !sensorInfo.hasPosition()) return false;
/*    */     
/* 44 */     String blockToPlace = role.getWorldSupport().getBlockToPlace();
/* 45 */     if (blockToPlace == null) return false;
/*    */     
/* 47 */     BlockType placedBlockType = (BlockType)BlockType.getAssetMap().getAsset(blockToPlace);
/* 48 */     if (placedBlockType == null) return false;
/*    */     
/* 50 */     sensorInfo.getPositionProvider().providePosition(this.target);
/*    */     
/* 52 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 54 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 55 */     assert transformComponent != null;
/*    */ 
/*    */     
/* 58 */     double maxDistance = this.range;
/* 59 */     BoundingBox hitBox = (BoundingBox)store.getComponent(ref, BOUNDING_BOX_COMPONENT_TYPE);
/* 60 */     if (hitBox != null) {
/* 61 */       maxDistance += hitBox.getBoundingBox().getMaximumExtent();
/*    */     }
/*    */     
/* 64 */     int x = MathUtil.floor(this.target.getX());
/* 65 */     int y = MathUtil.floor(this.target.getY());
/* 66 */     int z = MathUtil.floor(this.target.getZ());
/* 67 */     if (transformComponent.getPosition().distanceSquaredTo(x, y, z) > maxDistance * maxDistance) return false;
/*    */ 
/*    */     
/* 70 */     if (sensorInfo instanceof CachedPositionProvider && !((CachedPositionProvider)sensorInfo).isFromCache()) {
/* 71 */       return true;
/*    */     }
/*    */     
/* 74 */     if (!BlockPlacementHelper.canPlaceUnitBlock(world, placedBlockType, this.allowEmptyMaterials, x, y, z)) return false;
/*    */     
/* 76 */     return BlockPlacementHelper.canPlaceBlock(world, placedBlockType, 0, this.allowEmptyMaterials, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 81 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 83 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 84 */     WorldChunk chunk = world.getNonTickingChunk(ChunkUtil.indexChunkFromBlock(this.target.getX(), this.target.getZ()));
/* 85 */     chunk.setBlock(MathUtil.floor(this.target.getX()), MathUtil.floor(this.target.getY()), MathUtil.floor(this.target.getZ()), role.getWorldSupport().getBlockToPlace());
/* 86 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionPlaceBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */