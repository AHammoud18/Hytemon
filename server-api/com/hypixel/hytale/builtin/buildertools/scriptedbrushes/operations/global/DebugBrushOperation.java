/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.global;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.GlobalBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugBrushOperation
/*    */   extends GlobalBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<DebugBrushOperation> CODEC;
/*    */   
/*    */   static {
/* 54 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DebugBrushOperation.class, DebugBrushOperation::new).append(new KeyedCodec("PrintOperations", (Codec)Codec.BOOLEAN), (op, val) -> op.printOperations = val, op -> op.printOperations).documentation("Prints the index and name of each operation as it executes").add()).append(new KeyedCodec("StepThrough", (Codec)Codec.BOOLEAN), (op, val) -> op.stepThrough = val, op -> op.stepThrough).documentation("Enables manual step-through mode (pause after each operation)").add()).append(new KeyedCodec("EnableBreakpoints", (Codec)Codec.BOOLEAN), (op, val) -> op.enableBreakpoints = val, op -> op.enableBreakpoints).documentation("Master toggle for breakpoint operations").add()).append(new KeyedCodec("OutputTarget", (Codec)new EnumCodec(BrushConfigCommandExecutor.DebugOutputTarget.class)), (op, val) -> op.outputTarget = val, op -> op.outputTarget).documentation("Where debug messages are sent (Chat, Console, or Both)").add()).append(new KeyedCodec("BreakOnError", (Codec)Codec.BOOLEAN), (op, val) -> op.breakOnError = val, op -> op.breakOnError).documentation("Pause on error instead of terminating execution").add()).documentation("Debug options for scripted brushes")).build();
/*    */   }
/*    */   @Nonnull
/* 57 */   private Boolean printOperations = Boolean.valueOf(false);
/*    */   @Nonnull
/* 59 */   private Boolean stepThrough = Boolean.valueOf(false);
/*    */   @Nonnull
/* 61 */   private Boolean enableBreakpoints = Boolean.valueOf(false); @Nonnull
/* 62 */   private BrushConfigCommandExecutor.DebugOutputTarget outputTarget = BrushConfigCommandExecutor.DebugOutputTarget.Chat;
/*    */   
/*    */   @Nonnull
/* 65 */   private Boolean breakOnError = Boolean.valueOf(false);
/*    */   
/*    */   public DebugBrushOperation() {
/* 68 */     super("Debug Step-Through", "Debug options for scripted brushes");
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 73 */     brushConfigCommandExecutor.setInDebugSteppingMode(this.stepThrough.booleanValue());
/* 74 */     brushConfigCommandExecutor.setPrintOperations(this.printOperations.booleanValue());
/* 75 */     brushConfigCommandExecutor.setEnableBreakpoints(this.enableBreakpoints.booleanValue());
/* 76 */     brushConfigCommandExecutor.setDebugOutputTarget(this.outputTarget);
/* 77 */     brushConfigCommandExecutor.setBreakOnError(this.breakOnError.booleanValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\global\DebugBrushOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */