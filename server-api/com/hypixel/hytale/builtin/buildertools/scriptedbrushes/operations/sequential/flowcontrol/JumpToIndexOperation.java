/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol;
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
/*    */ public class JumpToIndexOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<JumpToIndexOperation> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(JumpToIndexOperation.class, JumpToIndexOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.variableNameArg = val, op -> op.variableNameArg).documentation("The labeled index to jump to, previous or future").add()).documentation("Jump the stack execution to the point in the stack of the given saved index name")).build();
/*    */   } @Nonnull
/* 27 */   public String variableNameArg = "Undefined";
/*    */ 
/*    */   
/*    */   public JumpToIndexOperation() {
/* 31 */     super("Jump to Index", "Jump the stack execution to the point in the stack of the given saved index name", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 36 */     brushConfigCommandExecutor.loadOperatingIndex(this.variableNameArg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\JumpToIndexOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */