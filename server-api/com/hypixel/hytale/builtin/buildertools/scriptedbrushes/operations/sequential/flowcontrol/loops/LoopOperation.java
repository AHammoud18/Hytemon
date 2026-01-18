/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ public class LoopOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final int MAX_REPETITIONS = 100;
/*    */   public static final int IDLE_STATE = -1;
/*    */   public static final BuilderCodec<LoopOperation> CODEC;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LoopOperation.class, LoopOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.indexNameArg = val, op -> op.indexNameArg).documentation("The name of the previously stored index to begin the loop at. Note: This can only be an index previous to the current.").add()).append(new KeyedCodec("AdditionalRepetitions", (Codec)Codec.INTEGER), (op, val) -> op.repetitionsArg = val, op -> op.repetitionsArg).documentation("The amount of additional times to repeat the loop after the initial, normal execution").add()).documentation("Loop the execution of instructions a set amount of times")).build();
/*    */   } @Nonnull
/* 37 */   public String indexNameArg = "Undefined";
/*    */   
/*    */   @Nonnull
/* 40 */   public Integer repetitionsArg = Integer.valueOf(0);
/*    */   
/* 42 */   private int repetitionsRemaining = -1;
/*    */   
/*    */   public LoopOperation() {
/* 45 */     super("Loop Operations", "Loop the execution of instructions a set amount of times", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void resetInternalState() {
/* 50 */     this.repetitionsRemaining = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 56 */     if (this.repetitionsRemaining == -1) {
/* 57 */       if (this.repetitionsArg.intValue() > 100 || this.repetitionsArg.intValue() < 0) {
/* 58 */         brushConfig.setErrorFlag("Cannot have more than 100 repetitions, or negative repetitions");
/*    */         return;
/*    */       } 
/* 61 */       this.repetitionsRemaining = this.repetitionsArg.intValue();
/*    */     } 
/*    */ 
/*    */     
/* 65 */     if (this.repetitionsRemaining == 0) {
/* 66 */       this.repetitionsRemaining = -1;
/*    */       
/*    */       return;
/*    */     } 
/* 70 */     this.repetitionsRemaining--;
/* 71 */     brushConfigCommandExecutor.loadOperatingIndex(this.indexNameArg, false);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\loops\LoopOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */