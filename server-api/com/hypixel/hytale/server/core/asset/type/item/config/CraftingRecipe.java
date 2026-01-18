/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.BenchRequirement;
/*     */ import com.hypixel.hytale.protocol.BenchType;
/*     */ import com.hypixel.hytale.protocol.MaterialQuantity;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftingRecipe
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, CraftingRecipe>>
/*     */ {
/*     */   public static final String FIELDCRAFT_REQUIREMENT = "Fieldcraft";
/*     */   public static final AssetBuilderCodec<String, CraftingRecipe> CODEC;
/*     */   
/*     */   static {
/* 134 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(CraftingRecipe.class, CraftingRecipe::new, (Codec)Codec.STRING, (recipe, blockTypeKey) -> recipe.id = blockTypeKey, recipe -> recipe.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("Input", (Codec)new ArrayCodec((Codec)MaterialQuantity.CODEC, x$0 -> new MaterialQuantity[x$0])), (craftingRecipe, objects) -> craftingRecipe.input = objects, craftingRecipe -> craftingRecipe.input).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Output", (Codec)new ArrayCodec((Codec)MaterialQuantity.CODEC, x$0 -> new MaterialQuantity[x$0])), (craftingRecipe, objects) -> craftingRecipe.outputs = objects, craftingRecipe -> craftingRecipe.outputs).add()).append(new KeyedCodec("PrimaryOutput", (Codec)MaterialQuantity.CODEC), (craftingRecipe, objects) -> craftingRecipe.primaryOutput = objects, craftingRecipe -> craftingRecipe.primaryOutput).add()).append(new KeyedCodec("OutputQuantity", (Codec)Codec.INTEGER), (craftingRecipe, quantity) -> craftingRecipe.primaryOutputQuantity = quantity.intValue(), craftingRecipe -> Integer.valueOf(craftingRecipe.primaryOutputQuantity)).add()).append(new KeyedCodec("BenchRequirement", (Codec)new ArrayCodec((Codec)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchRequirement.class, BenchRequirement::new).append(new KeyedCodec("Type", (Codec)new EnumCodec(BenchType.class)), (benchRequirement, benchType) -> benchRequirement.type = benchType, benchRequirement -> benchRequirement.type).add()).append(new KeyedCodec("Id", (Codec)Codec.STRING), (benchRequirement, s) -> benchRequirement.id = s, benchRequirement -> benchRequirement.id).add()).append(new KeyedCodec("Categories", (Codec)Codec.STRING_ARRAY), (benchRequirement, s) -> benchRequirement.categories = s, benchRequirement -> benchRequirement.categories).add()).appendInherited(new KeyedCodec("RequiredTierLevel", (Codec)Codec.INTEGER), (benchRequirement, s) -> benchRequirement.requiredTierLevel = s.intValue(), benchRequirement -> Integer.valueOf(benchRequirement.requiredTierLevel), (benchRequirement, parent) -> benchRequirement.requiredTierLevel = parent.requiredTierLevel).add()).build(), x$0 -> new BenchRequirement[x$0])), (craftingRecipe, objects) -> craftingRecipe.benchRequirement = objects, craftingRecipe -> craftingRecipe.benchRequirement).add()).append(new KeyedCodec("TimeSeconds", (Codec)Codec.DOUBLE), (craftingRecipe, d) -> craftingRecipe.timeSeconds = d.floatValue(), craftingRecipe -> Double.valueOf(craftingRecipe.timeSeconds)).addValidator(Validators.min(Double.valueOf(0.0D))).add()).append(new KeyedCodec("KnowledgeRequired", (Codec)Codec.BOOLEAN), (craftingRecipe, b) -> craftingRecipe.knowledgeRequired = b.booleanValue(), craftingRecipe -> Boolean.valueOf(craftingRecipe.knowledgeRequired)).add()).append(new KeyedCodec("RequiredMemoriesLevel", (Codec)Codec.INTEGER), (craftingRecipe, integer) -> craftingRecipe.requiredMemoriesLevel = integer.intValue(), craftingRecipe -> Integer.valueOf(craftingRecipe.requiredMemoriesLevel)).documentation("The level of Memories starts from 1, meaning a recipe with a RequiredMemoriesLevel set at 1 will always be available to players.").addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).validator((craftingRecipe, results) -> { BenchRequirement[] benchRequirements = craftingRecipe.getBenchRequirement(); if (benchRequirements != null) for (BenchRequirement benchRequirement : benchRequirements) { if (craftingRecipe.isKnowledgeRequired() && benchRequirement.type != BenchType.Crafting && benchRequirement.type != BenchType.DiagramCrafting) results.fail("KnowledgeRequired in recipe can't be set for non crafting recipes");  if (benchRequirement.type == BenchType.DiagramCrafting && craftingRecipe.getOutputs() != null && (craftingRecipe.getOutputs()).length > 1) results.fail("DiagramCrafting in recipe can only have 1 output");  if ("Fieldcraft".equals(benchRequirement.id) && craftingRecipe.getTimeSeconds() > 0.0F) results.warn(String.format("Bench Requirement in recipe for '%s' should not have a delay (TimeSeconds) set!", new Object[] { "Fieldcraft" }));  }   })).afterDecode(CraftingRecipe::processConfig)).build();
/*     */   }
/* 136 */   private static final MaterialQuantity[] EMPTY_OUTPUT = new MaterialQuantity[0]; private static AssetStore<String, CraftingRecipe, DefaultAssetMap<String, CraftingRecipe>> ASSET_STORE; private AssetExtraInfo.Data data; protected String id;
/*     */   protected MaterialQuantity[] input;
/*     */   
/*     */   public static AssetStore<String, CraftingRecipe, DefaultAssetMap<String, CraftingRecipe>> getAssetStore() {
/* 140 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(CraftingRecipe.class); 
/* 141 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, CraftingRecipe> getAssetMap() {
/* 145 */     return (DefaultAssetMap<String, CraftingRecipe>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   protected MaterialQuantity[] outputs = EMPTY_OUTPUT;
/*     */   protected MaterialQuantity primaryOutput;
/* 153 */   protected int primaryOutputQuantity = 1;
/*     */   protected BenchRequirement[] benchRequirement;
/*     */   protected float timeSeconds;
/*     */   protected boolean knowledgeRequired;
/* 157 */   protected int requiredMemoriesLevel = 1;
/*     */   
/*     */   public CraftingRecipe(MaterialQuantity[] input, MaterialQuantity primaryOutput, MaterialQuantity[] outputs, int outputQuantity, BenchRequirement[] benchRequirement, float timeSeconds, boolean knowledgeRequired, int requiredMemoriesLevel) {
/* 160 */     this.input = input;
/* 161 */     this.primaryOutput = primaryOutput;
/* 162 */     this.outputs = outputs;
/* 163 */     this.primaryOutputQuantity = outputQuantity;
/* 164 */     this.benchRequirement = benchRequirement;
/* 165 */     this.timeSeconds = timeSeconds;
/* 166 */     this.knowledgeRequired = knowledgeRequired;
/* 167 */     this.requiredMemoriesLevel = requiredMemoriesLevel;
/*     */   }
/*     */   
/*     */   public CraftingRecipe(CraftingRecipe other) {
/* 171 */     this.input = other.input;
/* 172 */     this.primaryOutput = other.primaryOutput;
/* 173 */     this.outputs = other.outputs;
/* 174 */     this.primaryOutputQuantity = other.primaryOutputQuantity;
/* 175 */     this.benchRequirement = other.benchRequirement;
/* 176 */     this.timeSeconds = other.timeSeconds;
/* 177 */     this.knowledgeRequired = other.knowledgeRequired;
/* 178 */     this.requiredMemoriesLevel = other.requiredMemoriesLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateIdFromItemRecipe(Item item, int i) {
/* 185 */     return item.id + "_Recipe_Generated_" + item.id;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public com.hypixel.hytale.protocol.CraftingRecipe toPacket(String id) {
/* 190 */     com.hypixel.hytale.protocol.CraftingRecipe packet = new com.hypixel.hytale.protocol.CraftingRecipe();
/*     */     
/* 192 */     packet.id = id;
/* 193 */     if (this.input != null && this.input.length > 0) {
/* 194 */       packet.inputs = (MaterialQuantity[])ArrayUtil.copyAndMutate((Object[])this.input, MaterialQuantity::toPacket, x$0 -> new MaterialQuantity[x$0]);
/*     */     }
/*     */     
/* 197 */     packet.primaryOutput = this.primaryOutput.toPacket();
/* 198 */     if (this.outputs != null && this.outputs.length > 0) {
/* 199 */       packet.outputs = (MaterialQuantity[])ArrayUtil.copyAndMutate((Object[])this.outputs, MaterialQuantity::toPacket, x$0 -> new MaterialQuantity[x$0]);
/*     */     }
/*     */     
/* 202 */     if (this.benchRequirement != null && this.benchRequirement.length > 0) {
/* 203 */       packet.benchRequirement = this.benchRequirement;
/*     */     }
/*     */     
/* 206 */     packet.knowledgeRequired = this.knowledgeRequired;
/* 207 */     packet.timeSeconds = this.timeSeconds;
/* 208 */     packet.requiredMemoriesLevel = this.requiredMemoriesLevel;
/*     */     
/* 210 */     return packet;
/*     */   }
/*     */   
/*     */   private void processConfig() {
/* 214 */     if ((this.outputs == null || this.outputs.length == 0) && this.primaryOutput != null) {
/* 215 */       this.outputs = new MaterialQuantity[] { this.primaryOutput };
/*     */     }
/*     */   }
/*     */   
/*     */   public MaterialQuantity[] getInput() {
/* 220 */     return this.input;
/*     */   }
/*     */   
/*     */   public MaterialQuantity[] getOutputs() {
/* 224 */     return this.outputs;
/*     */   }
/*     */   
/*     */   public BenchRequirement[] getBenchRequirement() {
/* 228 */     return this.benchRequirement;
/*     */   }
/*     */   
/*     */   public float getTimeSeconds() {
/* 232 */     return this.timeSeconds;
/*     */   }
/*     */   
/*     */   public boolean isKnowledgeRequired() {
/* 236 */     return this.knowledgeRequired;
/*     */   }
/*     */   
/*     */   public int getRequiredMemoriesLevel() {
/* 240 */     return this.requiredMemoriesLevel;
/*     */   }
/*     */   
/*     */   public MaterialQuantity getPrimaryOutput() {
/* 244 */     return this.primaryOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRestrictedByBenchTierLevel(String benchId, int tierLevel) {
/* 255 */     if (this.benchRequirement == null) return false; 
/* 256 */     for (BenchRequirement b : this.benchRequirement) {
/* 257 */       if (benchId.equals(b.id) && tierLevel < b.requiredTierLevel) return true; 
/*     */     } 
/* 259 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 266 */     return "CraftingRecipe{input=" + Arrays.toString((Object[])this.input) + ", extraOutputs=" + 
/* 267 */       Arrays.toString((Object[])this.outputs) + ", primaryOutput=" + String.valueOf(this.primaryOutput) + ", outputQuantity=" + this.primaryOutputQuantity + ", benchRequirement=" + 
/*     */ 
/*     */       
/* 270 */       Arrays.toString((Object[])this.benchRequirement) + ", timeSeconds=" + this.timeSeconds + ", knowledgeRequired=" + this.knowledgeRequired + ", requiredMemoriesLevel=" + this.requiredMemoriesLevel + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 279 */     return this.id;
/*     */   }
/*     */   
/*     */   protected CraftingRecipe() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\CraftingRecipe.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */