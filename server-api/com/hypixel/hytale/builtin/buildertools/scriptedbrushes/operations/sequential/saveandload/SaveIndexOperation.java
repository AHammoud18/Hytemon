/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload;
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
/*    */ public class SaveIndexOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<SaveIndexOperation> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SaveIndexOperation.class, SaveIndexOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.variableNameArg = val, op -> op.variableNameArg).documentation("The name to store the current execution index at").add()).documentation("Mark this spot in the stack in order to loop or jump to it")).build();
/*    */   } @Nonnull
/* 27 */   public String variableNameArg = "Undefined";
/*    */ 
/*    */   
/*    */   public SaveIndexOperation() {
/* 31 */     super("Store Current Operation Index", "Mark this spot in the stack in order to loop or jump to it", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void preExecutionModifyBrushConfig(@Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, int operationIndex) {
/* 36 */     brushConfigCommandExecutor.storeOperatingIndex(this.variableNameArg, operationIndex);
/*    */   }
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\saveandload\SaveIndexOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */