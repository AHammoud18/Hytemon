/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
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
/*    */ public class LoadBrushConfigOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<LoadBrushConfigOperation> CODEC;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LoadBrushConfigOperation.class, LoadBrushConfigOperation::new).append(new KeyedCodec("StoredName", (Codec)Codec.STRING), (op, val) -> op.variableNameArg = val, op -> op.variableNameArg).documentation("The name to store the snapshot of this brush config under").add()).append(new KeyedCodec("ParametersToLoad", (Codec)new ArrayCodec((Codec)new EnumCodec(BrushConfig.DataSettingFlags.class), x$0 -> new BrushConfig.DataSettingFlags[x$0])), (op, val) -> op.dataSettingFlagArg = (val != null) ? Arrays.<BrushConfig.DataSettingFlags>asList(val) : List.<BrushConfig.DataSettingFlags>of(), op -> (BrushConfig.DataSettingFlags[])op.dataSettingFlagArg.<BrushConfig.DataSettingFlags>toArray(new BrushConfig.DataSettingFlags[0])).documentation("A list of the different parameters to load from the stored config").add()).documentation("Restore a saved brush config snapshot")).build();
/*    */   } @Nonnull
/* 38 */   public String variableNameArg = "Undefined";
/*    */   
/*    */   @Nonnull
/* 41 */   public List<BrushConfig.DataSettingFlags> dataSettingFlagArg = List.of();
/*    */   
/*    */   public LoadBrushConfigOperation() {
/* 44 */     super("Load Brush Config Snapshot", "Restore a saved brush config snapshot", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 49 */     brushConfigCommandExecutor.loadBrushConfigSnapshot(this.variableNameArg, (BrushConfig.DataSettingFlags[])this.dataSettingFlagArg.toArray(x$0 -> new BrushConfig.DataSettingFlags[x$0]));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\saveandload\LoadBrushConfigOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */