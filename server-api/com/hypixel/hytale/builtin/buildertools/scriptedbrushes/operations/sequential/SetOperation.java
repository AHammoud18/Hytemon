/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SetOperation extends SequenceBrushOperation {
/* 15 */   public static final BuilderCodec<SetOperation> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SetOperation.class, SetOperation::new)
/* 16 */     .documentation("Runs a 'set' operation using the parameters of the brush configuration. Supports both blocks and fluids - if the pattern contains fluid items, it sets the fluid layer instead."))
/*    */     
/* 18 */     .build();
/*    */   
/*    */   public SetOperation() {
/* 21 */     super("Set", "Runs a 'set' operation using the parameters of the brush configuration", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifyBlocks(Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/* 26 */     edit.setMaterial(x, y, z, brushConfig.getNextMaterial());
/* 27 */     return true;
/*    */   }
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\SetOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */