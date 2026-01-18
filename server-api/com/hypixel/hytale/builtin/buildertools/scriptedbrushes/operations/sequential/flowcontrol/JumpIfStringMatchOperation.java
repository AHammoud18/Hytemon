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
/*    */ public class JumpIfStringMatchOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<JumpIfStringMatchOperation> CODEC;
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(JumpIfStringMatchOperation.class, JumpIfStringMatchOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.indexVariableNameArg = val, op -> op.indexVariableNameArg).documentation("The labeled index to jump to, previous or future").add()).append(new KeyedCodec("LeftSideOfStatement", (Codec)Codec.STRING), (op, val) -> op.sideOneArg = val, op -> op.sideOneArg).documentation("The left side of the statement for checking case-insensitive equals").add()).append(new KeyedCodec("RightSideOfStatement", (Codec)Codec.STRING), (op, val) -> op.sideTwoArg = val, op -> op.sideTwoArg).documentation("The right side of the statement for checking case-insensitive equals").add()).documentation("Jump the execution of the stack to the stored point if a string matches, useful for macro commands.")).build();
/*    */   } @Nonnull
/* 41 */   public String indexVariableNameArg = "Undefined";
/*    */   @Nonnull
/* 43 */   public String sideOneArg = "Undefined";
/*    */   @Nonnull
/* 45 */   public String sideTwoArg = "Undefined";
/*    */ 
/*    */   
/*    */   public JumpIfStringMatchOperation() {
/* 49 */     super("Jump If String Matches", "Jump the execution of the stack to the stored point if a string matches, useful for macro commands.", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 54 */     if (this.sideOneArg.equalsIgnoreCase(this.sideTwoArg))
/* 55 */       brushConfigCommandExecutor.loadOperatingIndex(this.indexVariableNameArg); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\JumpIfStringMatchOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */