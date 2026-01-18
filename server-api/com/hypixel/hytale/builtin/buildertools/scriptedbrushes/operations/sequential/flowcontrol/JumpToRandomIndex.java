/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.codec.PairCodec;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ public class JumpToRandomIndex
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<JumpToRandomIndex> CODEC;
/*    */   
/*    */   static {
/* 48 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(JumpToRandomIndex.class, JumpToRandomIndex::new).append(new KeyedCodec("WeightedListOfIndexNames", (Codec)new ArrayCodec((Codec)PairCodec.IntegerStringPair.CODEC, x$0 -> new PairCodec.IntegerStringPair[x$0])), (op, val) -> { if (val == null || val.length == 0) { op.variableNameArg = null; } else { WeightedMap.Builder<String> builder = WeightedMap.builder((Object[])new String[0]); for (PairCodec.IntegerStringPair pair : val) builder.put(pair.getRight(), pair.getLeft().doubleValue());  op.variableNameArg = builder.build(); }  }op -> { if (op.variableNameArg == null) return (Function)new PairCodec.IntegerStringPair[0];  ArrayList<PairCodec.IntegerStringPair> pairs = new ArrayList<>(); op.variableNameArg.forEachEntry(()); return (Function)pairs.<PairCodec.IntegerStringPair>toArray(new PairCodec.IntegerStringPair[0]); }).documentation("A weighted list of weights and their corresponding index names").add()).documentation("Jump the stack execution to a random location in the stack using the specified weights and saved index names")).build();
/*    */   } @Nullable
/* 50 */   public IWeightedMap<String> variableNameArg = null;
/*    */ 
/*    */   
/*    */   public JumpToRandomIndex() {
/* 54 */     super("Jump to Random Stored Index", "Jump the stack execution to a random location in the stack using the specified weights and saved index names", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 59 */     if (this.variableNameArg == null)
/* 60 */       return;  String indexName = (String)this.variableNameArg.get(brushConfig.getRandom());
/* 61 */     brushConfigCommandExecutor.loadOperatingIndex(indexName);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\JumpToRandomIndex.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */