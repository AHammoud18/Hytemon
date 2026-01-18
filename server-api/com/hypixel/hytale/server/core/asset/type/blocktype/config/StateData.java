/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.codec.lookup.Priority;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StateData
/*     */ {
/*     */   public static final String NULL_STATE_ID = "default";
/*     */   public static final BuilderCodec.Builder<StateData> DEFAULT_CODEC_BUILDER;
/*     */   
/*     */   static {
/*  36 */     DEFAULT_CODEC_BUILDER = (BuilderCodec.Builder<StateData>)((BuilderCodec.Builder)BuilderCodec.builder(StateData.class, StateData::new).appendInherited(new KeyedCodec("Id", (Codec)Codec.STRING), (stateData, s) -> stateData.id = s, stateData -> stateData.id, (o, p) -> o.id = p.id).add()).afterDecode((stateData, extraInfo) -> {
/*     */           if (stateData.stateToBlock != null) {
/*     */             Object2ObjectOpenHashMap<String, String> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*     */             for (Map.Entry<String, String> entry : stateData.stateToBlock.entrySet()) {
/*     */               object2ObjectOpenHashMap.put(entry.getValue(), entry.getKey());
/*     */             }
/*     */             stateData.blockToState = Collections.unmodifiableMap((Map<? extends String, ? extends String>)object2ObjectOpenHashMap);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*  47 */   public static final BuilderCodec<StateData> DEFAULT_CODEC = DEFAULT_CODEC_BUILDER.build();
/*  48 */   public static final CodecMapCodec<StateData> CODEC = (new CodecMapCodec(true))
/*  49 */     .register(Priority.DEFAULT, "StateData", StateData.class, (Codec)DEFAULT_CODEC);
/*     */   
/*     */   private String id;
/*     */   
/*     */   private Map<String, String> stateToBlock;
/*     */   private Map<String, String> blockToState;
/*     */   
/*     */   protected StateData() {}
/*     */   
/*     */   public StateData(String id) {
/*  59 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getId() {
/*  64 */     return this.id;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getBlockForState(String state) {
/*  69 */     if (this.stateToBlock == null) return null; 
/*  70 */     return this.stateToBlock.get(state);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getStateForBlock(String blockTypeKey) {
/*  75 */     if (this.blockToState == null) return null; 
/*  76 */     return this.blockToState.get(blockTypeKey);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Integer> toPacket(@Nonnull BlockType current) {
/*  81 */     if (this.stateToBlock == null) return null; 
/*  82 */     Object2IntOpenHashMap<String, Integer> object2IntOpenHashMap = new Object2IntOpenHashMap();
/*  83 */     for (String state : this.stateToBlock.keySet()) {
/*  84 */       String key = current.getBlockKeyForState(state);
/*  85 */       int index = BlockType.getAssetMap().getIndex(key);
/*  86 */       if (index == Integer.MIN_VALUE) {
/*     */         continue;
/*     */       }
/*  89 */       object2IntOpenHashMap.put(state, Integer.valueOf(index));
/*     */     } 
/*  91 */     return (Map<String, Integer>)object2IntOpenHashMap;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  97 */     return "StateData{id='" + this.id + "', stateToBlock='" + String.valueOf(this.stateToBlock) + "'}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyFrom(@Nullable StateData state) {
/* 104 */     if (state == null || this.stateToBlock != null)
/* 105 */       return;  this.stateToBlock = state.stateToBlock;
/* 106 */     this.blockToState = state.blockToState;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void addDefinitions() {
/* 112 */     DEFAULT_CODEC_BUILDER.addField(new KeyedCodec("Definitions", (Codec)new MapCodec((Codec)new ContainedAssetCodec(BlockType.class, (AssetCodec)BlockType.CODEC, ContainedAssetCodec.Mode.INJECT_PARENT, StateData::generateBlockKey), java.util.HashMap::new)), (stateData, m) -> stateData.stateToBlock = m, stateData -> stateData.stateToBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static String generateBlockKey(@Nonnull AssetExtraInfo<String> extraInfo) {
/* 121 */     String key = (String)extraInfo.getKey();
/* 122 */     String newName = "*" + key + "_" + extraInfo.peekKey('_');
/* 123 */     return newName;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\StateData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */