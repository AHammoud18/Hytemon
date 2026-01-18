/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.views;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NEntityBuffer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.entity.EntityPlacementData;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NEntityBufferView
/*    */   implements EntityContainer {
/*    */   private final NBufferBundle.Access.View access;
/*    */   private final Bounds3i bounds_voxelGrid;
/*    */   private final Bounds3i bounds_bufferGrid;
/*    */   
/*    */   public NEntityBufferView(@Nonnull NBufferBundle.Access.View bufferAccess) {
/* 21 */     this.access = bufferAccess;
/* 22 */     this.bounds_bufferGrid = bufferAccess.getBounds_bufferGrid();
/* 23 */     this.bounds_voxelGrid = bufferAccess.getBounds_bufferGrid();
/* 24 */     GridUtils.toVoxelGrid_fromBufferGrid(this.bounds_voxelGrid);
/*    */   }
/*    */   
/*    */   public void forEach(@Nonnull Consumer<EntityPlacementData> consumer) {
/* 28 */     Vector3i position_bufferGrid = this.bounds_voxelGrid.min.clone();
/*    */     
/* 30 */     position_bufferGrid.setX(this.bounds_bufferGrid.min.x); for (; position_bufferGrid.x < this.bounds_bufferGrid.max.x; position_bufferGrid.setX(position_bufferGrid.x + 1)) {
/* 31 */       position_bufferGrid.setZ(this.bounds_bufferGrid.min.z); for (; position_bufferGrid.z < this.bounds_bufferGrid.max.z; position_bufferGrid.setZ(position_bufferGrid.z + 1)) {
/* 32 */         position_bufferGrid.setY(this.bounds_bufferGrid.min.y); for (; position_bufferGrid.y < this.bounds_bufferGrid.max.y; position_bufferGrid.setY(position_bufferGrid.y + 1)) {
/*    */           
/* 34 */           NEntityBuffer buffer = getBuffer_fromBufferGrid(position_bufferGrid);
/* 35 */           buffer.forEach(consumer);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private NEntityBuffer getBuffer_fromBufferGrid(@Nonnull Vector3i position_bufferGrid) {
/* 42 */     return (NEntityBuffer)this.access.getBuffer(position_bufferGrid).buffer();
/*    */   }
/*    */   
/*    */   public void copyFrom(@Nonnull NEntityBufferView source) {
/* 46 */     assert source.bounds_voxelGrid.contains(this.bounds_voxelGrid);
/*    */     
/* 48 */     Bounds3i thisBounds_bufferGrid = this.access.getBounds_bufferGrid();
/* 49 */     Vector3i pos_bufferGrid = new Vector3i();
/*    */     
/* 51 */     pos_bufferGrid.setX(thisBounds_bufferGrid.min.x); for (; pos_bufferGrid.x < thisBounds_bufferGrid.max.x; pos_bufferGrid.setX(pos_bufferGrid.x + 1)) {
/* 52 */       pos_bufferGrid.setY(thisBounds_bufferGrid.min.y); for (; pos_bufferGrid.y < thisBounds_bufferGrid.max.y; pos_bufferGrid.setY(pos_bufferGrid.y + 1)) {
/* 53 */         pos_bufferGrid.setZ(thisBounds_bufferGrid.min.z); for (; pos_bufferGrid.z < thisBounds_bufferGrid.max.z; pos_bufferGrid.setZ(pos_bufferGrid.z + 1)) {
/*    */           
/* 55 */           NEntityBuffer sourceBuffer = source.getBuffer_fromBufferGrid(pos_bufferGrid);
/* 56 */           NEntityBuffer destinationBuffer = getBuffer_fromBufferGrid(pos_bufferGrid);
/* 57 */           destinationBuffer.copyFrom(sourceBuffer);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void addEntity(@Nonnull EntityPlacementData entityPlacementData) {
/* 65 */     Vector3d entityPosition_voxelGrid = entityPlacementData.getOffset().toVector3d();
/* 66 */     TransformComponent transform = (TransformComponent)entityPlacementData.getEntityHolder().getComponent(TransformComponent.getComponentType());
/* 67 */     assert transform != null;
/*    */     
/* 69 */     Vector3d holderPosition_voxelGrid = transform.getPosition();
/* 70 */     entityPosition_voxelGrid.add(holderPosition_voxelGrid);
/*    */     
/* 72 */     Vector3i position_bufferGrid = GridUtils.toIntegerGrid_fromDecimalGrid(entityPosition_voxelGrid);
/* 73 */     assert this.bounds_voxelGrid.contains(position_bufferGrid);
/* 74 */     GridUtils.toBufferGrid_fromVoxelGrid(position_bufferGrid);
/* 75 */     NEntityBuffer buffer = (NEntityBuffer)this.access.getBuffer(position_bufferGrid).buffer();
/*    */     
/* 77 */     buffer.addEntity(entityPlacementData);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInsideBuffer(int x, int y, int z) {
/* 82 */     return this.bounds_voxelGrid.contains(new Vector3i(x, y, z));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\views\NEntityBufferView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */