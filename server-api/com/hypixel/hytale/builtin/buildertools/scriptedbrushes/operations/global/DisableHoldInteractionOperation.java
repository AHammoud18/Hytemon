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
/*    */ public class DisableHoldInteractionOperation extends GlobalBrushOperation {
/* 14 */   public static final BuilderCodec<DisableHoldInteractionOperation> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DisableHoldInteractionOperation.class, DisableHoldInteractionOperation::new)
/* 15 */     .documentation("Disables the ability of the brush to activate multiple times on holding a button"))
/* 16 */     .build();
/*    */   
/*    */   public DisableHoldInteractionOperation() {
/* 19 */     super("Disable Activate-On-Hold", "Disables the ability of the brush to activate multiple times on holding a button");
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 24 */     if (brushConfig.isHoldDownInteraction())
/* 25 */       brushConfigCommandExecutor.exitExecution(ref, componentAccessor); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\global\DisableHoldInteractionOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */