/*    */ package com.hypixel.hytale.builtin.mounts;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.protocol.BlockMountType;
/*    */ import com.hypixel.hytale.protocol.MountController;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class MountedComponent implements Component<EntityStore> {
/*    */   private Ref<EntityStore> mountedToEntity;
/*    */   
/*    */   public static ComponentType<EntityStore, MountedComponent> getComponentType() {
/* 18 */     return MountPlugin.getInstance().getMountedComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   private Ref<ChunkStore> mountedToBlock;
/*    */   
/*    */   private MountController controller;
/*    */   
/*    */   private BlockMountType blockMountType;
/* 27 */   private Vector3f attachmentOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*    */   
/*    */   private long mountStartMs;
/*    */   private boolean isNetworkOutdated = true;
/*    */   
/*    */   public MountedComponent(Ref<EntityStore> mountedToEntity, Vector3f attachmentOffset, MountController controller) {
/* 33 */     this.mountedToEntity = mountedToEntity;
/* 34 */     this.attachmentOffset = attachmentOffset;
/* 35 */     this.controller = controller;
/* 36 */     this.mountStartMs = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public MountedComponent(Ref<ChunkStore> mountedToBlock, Vector3f attachmentOffset, BlockMountType blockMountType) {
/* 40 */     this.mountedToBlock = mountedToBlock;
/* 41 */     this.attachmentOffset = attachmentOffset;
/* 42 */     this.controller = MountController.BlockMount;
/* 43 */     this.blockMountType = blockMountType;
/* 44 */     this.mountStartMs = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> getMountedToEntity() {
/* 49 */     return this.mountedToEntity;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<ChunkStore> getMountedToBlock() {
/* 54 */     return this.mountedToBlock;
/*    */   }
/*    */   
/*    */   public Vector3f getAttachmentOffset() {
/* 58 */     return this.attachmentOffset;
/*    */   }
/*    */   
/*    */   public MountController getControllerType() {
/* 62 */     return this.controller;
/*    */   }
/*    */   
/*    */   public BlockMountType getBlockMountType() {
/* 66 */     return this.blockMountType;
/*    */   }
/*    */   
/*    */   public long getMountedDurationMs() {
/* 70 */     return System.currentTimeMillis() - this.mountStartMs;
/*    */   }
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 74 */     boolean tmp = this.isNetworkOutdated;
/* 75 */     this.isNetworkOutdated = false;
/* 76 */     return tmp;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 82 */     return new MountedComponent(this.mountedToEntity, this.attachmentOffset, this.controller);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\MountedComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */