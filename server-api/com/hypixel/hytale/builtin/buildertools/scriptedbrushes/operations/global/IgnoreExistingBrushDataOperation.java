/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.global;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.GlobalBrushOperation;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class IgnoreExistingBrushDataOperation extends GlobalBrushOperation {
/* 14 */   public static final BuilderCodec<IgnoreExistingBrushDataOperation> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(IgnoreExistingBrushDataOperation.class, IgnoreExistingBrushDataOperation::new)
/* 15 */     .documentation("Ignores any existing brush settings specified on the tool"))
/* 16 */     .build();
/*    */   
/*    */   public IgnoreExistingBrushDataOperation() {
/* 19 */     super("Ignore Existing Brush Settings", "Ignores any existing brush settings specified on the tool");
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 24 */     brushConfigCommandExecutor.setIgnoreExistingBrushData(true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\global\IgnoreExistingBrushDataOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */