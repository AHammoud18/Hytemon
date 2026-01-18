/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaterialOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<MaterialOperation> CODEC;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MaterialOperation.class, MaterialOperation::new).append(new KeyedCodec("BlockType", (Codec)Codec.STRING), (op, val) -> op.blockTypeArg = val, op -> op.blockTypeArg).documentation("A single material to set the block type to. You can also use Block Pattern operation to set a pattern of blocks").add()).documentation("Change the brush's material")).build();
/*    */   } @Nonnull
/* 30 */   public String blockTypeArg = "Rock_Stone";
/*    */ 
/*    */   
/*    */   public MaterialOperation() {
/* 34 */     super("Material", "Change the brush's material", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 39 */     WeightedMap.Builder<String> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_STRING_ARRAY);
/* 40 */     builder.put(this.blockTypeArg, 1.0D);
/* 41 */     brushConfig.setPattern(new BlockPattern(builder.build()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\MaterialOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */