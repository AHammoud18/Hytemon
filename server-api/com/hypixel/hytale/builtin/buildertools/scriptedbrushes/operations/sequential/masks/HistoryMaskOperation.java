/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
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
/*    */ public class HistoryMaskOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<HistoryMaskOperation> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HistoryMaskOperation.class, HistoryMaskOperation::new).append(new KeyedCodec("HistoryMask", (Codec)new EnumCodec(BrushConfig.HistoryMask.class)), (op, val) -> op.historyMaskArg = val, op -> op.historyMaskArg).documentation("Changes the mask to block history, enable only history, or ignore history").add()).documentation("Sets the history mask, allowing you to mask to previously edited or non-edited blocks")).build();
/*    */   } @Nonnull
/* 27 */   public BrushConfig.HistoryMask historyMaskArg = BrushConfig.HistoryMask.None;
/*    */ 
/*    */   
/*    */   public HistoryMaskOperation() {
/* 31 */     super("History Mask", "Sets the history mask, allowing you to mask to previously edited or non-edited blocks", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 36 */     brushConfig.setHistoryMask(this.historyMaskArg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\masks\HistoryMaskOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */