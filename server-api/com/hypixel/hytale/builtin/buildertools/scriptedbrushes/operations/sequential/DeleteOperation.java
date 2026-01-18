/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.builtin.buildertools.utils.Material;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DeleteOperation extends SequenceBrushOperation {
/* 16 */   public static final BuilderCodec<DeleteOperation> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DeleteOperation.class, DeleteOperation::new)
/* 17 */     .documentation("Remove all blocks and fluids in the area"))
/* 18 */     .build();
/*    */   
/*    */   public DeleteOperation() {
/* 21 */     super("Delete", "Remove all blocks and fluids in the area", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifyBlocks(Ref<EntityStore> ref, BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/* 26 */     edit.setMaterial(x, y, z, Material.EMPTY);
/* 27 */     return true;
/*    */   }
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\DeleteOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */