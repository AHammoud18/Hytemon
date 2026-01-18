/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpIfCompareOperation;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BreakpointOperation extends SequenceBrushOperation {
/*  22 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<BreakpointOperation> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  61 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BreakpointOperation.class, BreakpointOperation::new).append(new KeyedCodec("Label", (Codec)Codec.STRING), (op, val) -> op.label = val, op -> op.label).documentation("Identifier for this breakpoint").add()).append(new KeyedCodec("PrintMessage", (Codec)Codec.BOOLEAN), (op, val) -> op.printMessage = val, op -> op.printMessage).documentation("Print a message when breakpoint is reached").add()).append(new KeyedCodec("PrintState", (Codec)Codec.BOOLEAN), (op, val) -> op.printState = val, op -> op.printState).documentation("Print brush state when breakpoint is reached").add()).append(new KeyedCodec("EnterStepMode", (Codec)Codec.BOOLEAN), (op, val) -> op.enterStepMode = val, op -> op.enterStepMode).documentation("Enter step-through mode (use /sb step to continue)").add()).append(new KeyedCodec("Condition", (Codec)JumpIfCompareOperation.BrushConfigIntegerComparison.CODEC), (op, val) -> op.condition = val, op -> op.condition).documentation("Optional condition - breakpoint only triggers if condition passes").add()).documentation("Debug breakpoint for scripted brushes")).build();
/*     */   } @Nonnull
/*  63 */   private String label = "";
/*     */   
/*     */   @Nonnull
/*  66 */   private Boolean printMessage = Boolean.valueOf(false);
/*     */   @Nonnull
/*  68 */   private Boolean printState = Boolean.valueOf(false);
/*     */   @Nonnull
/*  70 */   private Boolean enterStepMode = Boolean.valueOf(false); @Nullable
/*  71 */   private JumpIfCompareOperation.BrushConfigIntegerComparison condition = null;
/*     */ 
/*     */   
/*     */   public BreakpointOperation() {
/*  75 */     super("Breakpoint", "Debug breakpoint for scripted brushes", false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor executor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  80 */     if (!executor.isEnableBreakpoints()) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     if (this.condition != null && !this.condition.apply(brushConfig).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     int currentIndex = executor.getCurrentOperationIndex();
/*  89 */     BrushConfigCommandExecutor.DebugOutputTarget outputTarget = executor.getDebugOutputTarget();
/*  90 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  91 */     String labelDisplay = this.label.isEmpty() ? "unnamed" : this.label;
/*     */     
/*  93 */     boolean hasAnyOutput = (this.printMessage.booleanValue() || this.printState.booleanValue() || this.enterStepMode.booleanValue());
/*  94 */     if (hasAnyOutput) {
/*  95 */       if (shouldSendToChat(outputTarget) && playerRefComponent != null) {
/*  96 */         playerRefComponent.sendMessage(Message.translation("server.builderTools.brushConfig.debug.breakpointReached")
/*  97 */             .param("label", labelDisplay)
/*  98 */             .param("index", currentIndex));
/*     */       }
/* 100 */       if (shouldSendToConsole(outputTarget)) {
/* 101 */         LOGGER.at(Level.INFO).log("[Breakpoint] '%s' reached at operation #%d", labelDisplay, currentIndex);
/*     */       }
/*     */     } 
/*     */     
/* 105 */     if (this.printState.booleanValue()) {
/* 106 */       String stateInfo = brushConfig.getInfo();
/* 107 */       if (shouldSendToChat(outputTarget) && playerRefComponent != null) {
/* 108 */         playerRefComponent.sendMessage(Message.translation("server.builderTools.brushConfig.debug.breakpointState")
/* 109 */             .param("index", currentIndex)
/* 110 */             .param("state", stateInfo));
/*     */       }
/* 112 */       if (shouldSendToConsole(outputTarget)) {
/* 113 */         LOGGER.at(Level.INFO).log("[Breakpoint] [Operation #%d] %s", currentIndex, stateInfo);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     if (this.enterStepMode.booleanValue()) {
/* 118 */       if (shouldSendToChat(outputTarget) && playerRefComponent != null) {
/* 119 */         playerRefComponent.sendMessage(Message.translation("server.builderTools.brushConfig.debug.breakpointEnteringStepMode")
/* 120 */             .param("label", labelDisplay));
/*     */       }
/* 122 */       if (shouldSendToConsole(outputTarget)) {
/* 123 */         LOGGER.at(Level.INFO).log("[Breakpoint] '%s' - Entering step-through mode", labelDisplay);
/*     */       }
/* 125 */       executor.setInDebugSteppingMode(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldSendToChat(BrushConfigCommandExecutor.DebugOutputTarget target) {
/* 130 */     return (target == BrushConfigCommandExecutor.DebugOutputTarget.Chat || target == BrushConfigCommandExecutor.DebugOutputTarget.Both);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldSendToConsole(BrushConfigCommandExecutor.DebugOutputTarget target) {
/* 135 */     return (target == BrushConfigCommandExecutor.DebugOutputTarget.Console || target == BrushConfigCommandExecutor.DebugOutputTarget.Both);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\BreakpointOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */