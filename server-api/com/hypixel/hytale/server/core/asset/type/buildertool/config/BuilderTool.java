/*     */ package com.hypixel.hytale.server.core.asset.type.buildertool.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.lookup.MapProvidedMapCodec;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolArg;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolArgGroup;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolState;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.ToolArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.ToolArgException;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class BuilderTool
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BuilderTool>>, NetworkSerializable<BuilderToolState> {
/*     */   public static final String TOOL_DATA_KEY = "ToolData";
/*  34 */   public static final KeyedCodec<BrushData.Values> BRUSH_DATA_KEY_CODEC = new KeyedCodec("BrushData", BrushData.Values.CODEC);
/*     */   
/*  36 */   public static final BuilderTool DEFAULT = new BuilderTool();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final AssetBuilderCodec<String, BuilderTool> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DefaultAssetMap<String, BuilderTool> ASSET_MAP;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isBrush;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String brushConfigurationCommand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  78 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BuilderTool.class, BuilderTool::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (builderTool, o) -> builderTool.id = o, builderTool -> builderTool.id)).addField(new KeyedCodec("IsBrush", (Codec)Codec.BOOLEAN), (builderTool, o) -> builderTool.isBrush = o.booleanValue(), builderTool -> Boolean.valueOf(builderTool.isBrush))).addField(new KeyedCodec("BrushConfigurationCommand", (Codec)Codec.STRING), (builderTool, o) -> builderTool.brushConfigurationCommand = o, builderTool -> builderTool.brushConfigurationCommand)).addField(new KeyedCodec("Args", (Codec)new MapCodec((Codec)ToolArg.CODEC, java.util.HashMap::new)), (builderTool, s) -> builderTool.args = s, builderTool -> builderTool.args)).addField(new KeyedCodec("BrushData", (Codec)BrushData.CODEC), (builderTool, o) -> builderTool.brushData = o, builderTool -> builderTool.brushData)).afterDecode(builderTool -> { if (!builderTool.args.isEmpty()) builderTool.argsCodec = new MapProvidedMapCodec(builderTool.args, ToolArg::getCodec, java.util.HashMap::new);  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DefaultAssetMap<String, BuilderTool> getAssetMap() {
/*  83 */     if (ASSET_MAP == null) ASSET_MAP = (DefaultAssetMap<String, BuilderTool>)AssetRegistry.getAssetStore(BuilderTool.class).getAssetMap(); 
/*  84 */     return ASSET_MAP;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BuilderTool getActiveBuilderTool(@Nonnull Player player) {
/*  89 */     ItemStack activeItemStack = player.getInventory().getItemInHand();
/*  90 */     if (activeItemStack == null) return null;
/*     */     
/*  92 */     Item item = activeItemStack.getItem();
/*  93 */     BuilderToolData builderToolData = item.getBuilderToolData();
/*  94 */     if (builderToolData == null) return null;
/*     */     
/*  96 */     return builderToolData.getTools()[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   protected BrushData brushData = BrushData.DEFAULT;
/* 105 */   protected Map<String, ToolArg> args = Collections.emptyMap();
/*     */   
/*     */   protected Map<String, Object> defaultToolArgs;
/*     */   
/*     */   private MapProvidedMapCodec<Object, ToolArg> argsCodec;
/*     */   
/*     */   private SoftReference<BuilderToolState> cachedPacket;
/*     */ 
/*     */   
/*     */   public String getId() {
/* 115 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getBrushConfigurationCommand() {
/* 119 */     return this.brushConfigurationCommand;
/*     */   }
/*     */   
/*     */   public boolean isBrush() {
/* 123 */     return this.isBrush;
/*     */   }
/*     */   
/*     */   public BrushData getBrushData() {
/* 127 */     return this.brushData;
/*     */   }
/*     */   
/*     */   public Map<String, ToolArg> getArgs() {
/* 131 */     return this.args;
/*     */   }
/*     */   
/*     */   public MapProvidedMapCodec<Object, ToolArg> getArgsCodec() {
/* 135 */     return this.argsCodec;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private Map<String, Object> getDefaultToolArgs(@Nonnull ItemStack itemStack) {
/* 141 */     BuilderTool builderToolAsset = itemStack.getItem().getBuilderToolData().getTools()[0];
/* 142 */     Object2ObjectOpenHashMap<String, Object> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap(builderToolAsset.args.size());
/* 143 */     for (Map.Entry<String, ToolArg> entry : builderToolAsset.args.entrySet()) {
/* 144 */       object2ObjectOpenHashMap.put(entry.getKey(), ((ToolArg)entry.getValue()).getValue());
/*     */     }
/* 146 */     return (Map<String, Object>)object2ObjectOpenHashMap;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private BrushData.Values getDefaultBrushArgs(@Nonnull ItemStack itemStack) {
/* 152 */     BuilderTool builderToolAsset = itemStack.getItem().getBuilderToolData().getTools()[0];
/* 153 */     return new BrushData.Values(builderToolAsset.brushData);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ArgData getItemArgData(@Nonnull ItemStack itemStack) {
/* 158 */     Map<String, Object> toolArgs = null;
/* 159 */     if (!this.args.isEmpty()) {
/* 160 */       Map<String, Object> toolData = (Map<String, Object>)itemStack.getFromMetadataOrNull("ToolData", (Codec)this.argsCodec);
/* 161 */       toolArgs = (toolData == null) ? getDefaultToolArgs(itemStack) : toolData;
/*     */     } 
/*     */     
/* 164 */     BrushData.Values brushArgs = null;
/* 165 */     if (this.isBrush) {
/* 166 */       BrushData.Values brushData = (BrushData.Values)itemStack.getFromMetadataOrNull(BRUSH_DATA_KEY_CODEC);
/* 167 */       brushArgs = (brushData == null) ? getDefaultBrushArgs(itemStack) : brushData;
/*     */     } 
/*     */     
/* 170 */     return new ArgData(toolArgs, brushArgs);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack createItemStack(@Nonnull String itemId, int quantity, @Nonnull ArgData argData) {
/* 175 */     BsonDocument meta = new BsonDocument();
/* 176 */     if (argData.tool() != null) meta.put("ToolData", this.argsCodec.encode(argData.tool())); 
/* 177 */     if (this.isBrush) BRUSH_DATA_KEY_CODEC.put(meta, argData.brush); 
/* 178 */     return new ItemStack(itemId, quantity, meta);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack updateArgMetadata(@Nonnull ItemStack itemStack, BuilderToolArgGroup group, @Nonnull String id, @Nullable String value) throws ToolArgException {
/* 183 */     ArgData argData = getItemArgData(itemStack);
/* 184 */     if (group == BuilderToolArgGroup.Brush) {
/* 185 */       this.brushData.updateArgValue(argData.brush, id, value);
/*     */     } else {
/* 187 */       ToolArg arg = this.args.get(id);
/* 188 */       if (arg == null) throw new ToolArgException(Message.translation("server.builderTools.toolUnknownArg").param("arg", id));
/*     */       
/* 190 */       if (value == null) {
/* 191 */         if (arg.isRequired()) throw new ToolArgException(Message.translation("server.builderTools.toolArgMissing").param("arg", id)); 
/* 192 */         argData = ArgData.removeToolArg(argData, id);
/*     */       } else {
/* 194 */         Object newValue = arg.fromString(value);
/* 195 */         argData = ArgData.setToolArg(argData, id, newValue);
/*     */       } 
/*     */     } 
/* 198 */     return createItemStack(itemStack.getItemId(), itemStack.getQuantity(), argData);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BuilderToolState toPacket() {
/* 204 */     BuilderToolState cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 205 */     if (cached != null) return cached;
/*     */     
/* 207 */     BuilderToolState packet = new BuilderToolState();
/* 208 */     packet.id = this.id;
/* 209 */     packet.isBrush = this.isBrush;
/* 210 */     if (this.brushData != null) packet.brushData = this.brushData.toPacket(); 
/* 211 */     Object2ObjectOpenHashMap<String, BuilderToolArg> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap(this.args.size());
/* 212 */     for (Map.Entry<String, ToolArg> entry : this.args.entrySet()) {
/* 213 */       object2ObjectOpenHashMap.put(entry.getKey(), ((ToolArg)entry.getValue()).toPacket());
/*     */     }
/* 215 */     packet.args = (Map)object2ObjectOpenHashMap;
/*     */     
/* 217 */     this.cachedPacket = new SoftReference<>(packet);
/* 218 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 224 */     return "BuilderTool{id='" + this.id + "', isBrush=" + this.isBrush + ", brushData=" + String.valueOf(this.brushData) + ", args=" + String.valueOf(this.args) + "}";
/*     */   }
/*     */   
/*     */   public static final class ArgData extends Record {
/*     */     private final Map<String, Object> tool;
/*     */     private final BrushData.Values brush;
/*     */     
/*     */     public ArgData(Map<String, Object> tool, BrushData.Values brush) {
/* 232 */       this.tool = tool; this.brush = brush;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #232	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;
/*     */     } @Nullable
/*     */     public Map<String, Object> tool() {
/* 237 */       return this.tool;
/*     */     } public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #232	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     } @Nonnull
/*     */     public static ArgData setToolArg(@Nonnull ArgData argData, String argId, Object value) {
/* 242 */       Map<String, Object> tool = argData.tool();
/* 243 */       if (tool == null) return argData;
/*     */       
/* 245 */       Object2ObjectOpenHashMap<String, Object> newToolArgs = new Object2ObjectOpenHashMap(tool);
/* 246 */       newToolArgs.put(argId, value);
/*     */       
/* 248 */       return new ArgData((Map<String, Object>)newToolArgs, argData.brush());
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public static ArgData removeToolArg(@Nonnull ArgData argData, String argId) {
/* 253 */       Map<String, Object> tool = argData.tool();
/* 254 */       if (tool == null) return argData;
/*     */       
/* 256 */       Object2ObjectOpenHashMap<String, Object> newToolArgs = new Object2ObjectOpenHashMap(tool);
/* 257 */       newToolArgs.remove(argId);
/*     */       
/* 259 */       return new ArgData((Map<String, Object>)newToolArgs, argData.brush());
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public BrushData.Values brush() {
/* 265 */       return this.brush;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 271 */       return "ArgData{tool=" + String.valueOf(this.tool) + ", brush=" + String.valueOf(this.brush) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\BuilderTool.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */