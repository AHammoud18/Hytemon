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
/*    */ import com.hypixel.hytale.server.core.codec.PairCodec;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import java.util.concurrent.ThreadLocalRandom;
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
/*    */ public class LoopRandomOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final int MAX_REPETITIONS = 100;
/*    */   public static final int IDLE_STATE = -1;
/*    */   public static final BuilderCodec<LoopRandomOperation> CODEC;
/*    */   
/*    */   static {
/* 38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LoopRandomOperation.class, LoopRandomOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.indexNameArg = val, op -> op.indexNameArg).documentation("The name of the previously stored index to begin the loop at. Note: This can only be an index previous to the current.").add()).append(new KeyedCodec("RangeOfAdditionalRepetitions", (Codec)PairCodec.IntegerPair.CODEC), (op, val) -> op.repetitionsArg = val.toPair(), op -> PairCodec.IntegerPair.fromPair(op.repetitionsArg)).documentation("The minimum and maximum of a range, randomly choosing the amount of additional times to repeat the loop after the initial, normal execution").add()).documentation("Loop the execution of instructions a random amount of times")).build();
/*    */   } @Nonnull
/* 40 */   public String indexNameArg = "Undefined";
/*    */   
/*    */   @Nonnull
/* 43 */   public Pair<Integer, Integer> repetitionsArg = Pair.of(Integer.valueOf(1), Integer.valueOf(1));
/*    */   
/* 45 */   private int repetitionsRemaining = -1;
/*    */   
/*    */   public LoopRandomOperation() {
/* 48 */     super("Loop Operations Random Amount", "Loop the execution of instructions a random amount of times", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void resetInternalState() {
/* 53 */     this.repetitionsRemaining = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 59 */     if (this.repetitionsRemaining == -1) {
/* 60 */       int repetitions = randomlyChooseRepetitionsAmount();
/* 61 */       if (repetitions > 100) {
/* 62 */         brushConfig.setErrorFlag("Cannot have more than 100 repetitions");
/*    */         return;
/*    */       } 
/* 65 */       this.repetitionsRemaining = repetitions;
/*    */     } 
/*    */ 
/*    */     
/* 69 */     if (this.repetitionsRemaining == 0) {
/* 70 */       this.repetitionsRemaining = -1;
/*    */       
/*    */       return;
/*    */     } 
/* 74 */     this.repetitionsRemaining--;
/* 75 */     brushConfigCommandExecutor.loadOperatingIndex(this.indexNameArg, false);
/*    */   }
/*    */ 
/*    */   
/*    */   private int randomlyChooseRepetitionsAmount() {
/* 80 */     return ((Integer)this.repetitionsArg.left()).equals(this.repetitionsArg.right()) ? ((Integer)this.repetitionsArg.left()).intValue() : ThreadLocalRandom.current().nextInt(((Integer)this.repetitionsArg.left()).intValue(), ((Integer)this.repetitionsArg.right()).intValue() + 1);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\loops\LoopRandomOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */