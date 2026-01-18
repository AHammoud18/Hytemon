/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeVector3i;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockMask;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public class JumpIfBlockTypeOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<JumpIfBlockTypeOperation> CODEC;
/*    */   
/*    */   static {
/* 44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(JumpIfBlockTypeOperation.class, JumpIfBlockTypeOperation::new).append(new KeyedCodec("Offset", (Codec)new ArrayCodec((Codec)RelativeVector3i.CODEC, x$0 -> new RelativeVector3i[x$0])), (op, val) -> op.offsetListArg = (val != null) ? Arrays.<RelativeVector3i>asList(val) : List.<RelativeVector3i>of(), op -> (RelativeVector3i[])op.offsetListArg.<RelativeVector3i>toArray(new RelativeVector3i[0])).documentation("The offset(s) to compare from. In 3 dimensions. Each value is optionally relative by prefixing it with a tilde.").add()).append(new KeyedCodec("Mask", BlockMask.CODEC), (op, val) -> op.blockMaskArg = val, op -> op.blockMaskArg).documentation("The block mask for the comparison.").add()).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.indexVariableNameArg = val, op -> op.indexVariableNameArg).documentation("The labeled index to jump to, previous or future").add()).documentation("Jump the execution of the stack based on a block type comparison")).build();
/*    */   }
/*    */   @Nonnull
/* 47 */   public List<RelativeVector3i> offsetListArg = List.of(); @Nonnull
/* 48 */   public BlockMask blockMaskArg = BlockMask.EMPTY;
/*    */   @Nonnull
/* 50 */   public String indexVariableNameArg = "Undefined";
/*    */ 
/*    */   
/*    */   public JumpIfBlockTypeOperation() {
/* 54 */     super("Jump If Block Type Comparison", "Jump the execution of the stack based on a block type comparison", false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 60 */     Vector3i currentBrushOrigin = brushConfig.getOriginAfterOffset();
/* 61 */     if (currentBrushOrigin == null) {
/* 62 */       brushConfig.setErrorFlag("Could not find the origin for the operation.");
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 67 */     for (RelativeVector3i offset : this.offsetListArg) {
/* 68 */       Vector3i brushOriginAfterOffset = offset.resolve(currentBrushOrigin);
/* 69 */       int targetBlockId = brushConfigCommandExecutor.getEdit().getBlock(brushOriginAfterOffset.x, brushOriginAfterOffset.y, brushOriginAfterOffset.z);
/* 70 */       int targetFluidId = brushConfigCommandExecutor.getEdit().getFluid(brushOriginAfterOffset.x, brushOriginAfterOffset.y, brushOriginAfterOffset.z);
/*    */       
/* 72 */       if (this.blockMaskArg.isExcluded((ChunkAccessor)brushConfigCommandExecutor.getEdit().getAccessor(), brushOriginAfterOffset.x, brushOriginAfterOffset.y, brushOriginAfterOffset.z, null, null, targetBlockId, targetFluidId)) {
/*    */         continue;
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 78 */       brushConfigCommandExecutor.loadOperatingIndex(this.indexVariableNameArg);
/*    */       return;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\JumpIfBlockTypeOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */