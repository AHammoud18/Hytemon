/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload;
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
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
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
/*    */ public class PersistentDataOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<PersistentDataOperation> CODEC;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PersistentDataOperation.class, PersistentDataOperation::new).append(new KeyedCodec("StoredName", (Codec)Codec.STRING), (op, val) -> op.variableNameArg = val, op -> op.variableNameArg).documentation("The name of the variable to modify").add()).append(new KeyedCodec("Operation", (Codec)new EnumCodec(ArgTypes.IntegerOperation.class)), (op, val) -> op.operationArg = val, op -> op.operationArg).documentation("The operation to perform on the variable using the modifier").add()).append(new KeyedCodec("Modifier", (Codec)Codec.INTEGER), (op, val) -> op.modifierArg = val, op -> op.modifierArg).documentation("The value to modify the variable by").add()).documentation("Store and operate on data that sticks around between executions")).build();
/*    */   } @Nonnull
/* 43 */   public String variableNameArg = "Undefined";
/*    */   @Nonnull
/* 45 */   public ArgTypes.IntegerOperation operationArg = ArgTypes.IntegerOperation.SET;
/*    */   
/*    */   @Nonnull
/* 48 */   public Integer modifierArg = Integer.valueOf(0);
/*    */   
/*    */   public PersistentDataOperation() {
/* 51 */     super("Persistent Data", "Store and operate on data that sticks around between executions", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 56 */     int persistentVariable = brushConfigCommandExecutor.getPersistentVariableOrDefault(this.variableNameArg, 0);
/* 57 */     System.out.println(this.variableNameArg + ": " + this.variableNameArg);
/*    */     
/* 59 */     int newValue = this.operationArg.operate(persistentVariable, this.modifierArg.intValue());
/* 60 */     brushConfigCommandExecutor.setPersistentVariable(this.variableNameArg, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\saveandload\PersistentDataOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */