/*     */ package com.hypixel.hytale.builtin.commandmacro;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandRegistration;
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
/*     */ public class MacroCommandBuilder
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, MacroCommandBuilder>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, MacroCommandBuilder> CODEC;
/*     */   private String id;
/*     */   private String name;
/*     */   private String[] aliases;
/*     */   private String description;
/*     */   private MacroCommandParameter[] parameters;
/*     */   private String[] commands;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   static {
/*  53 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(MacroCommandBuilder.class, MacroCommandBuilder::new, (Codec)Codec.STRING, (builder, id) -> builder.id = id, builder -> builder.id, (builder, data) -> builder.data = data, builder -> builder.data).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (builder, name) -> builder.name = name, builder -> builder.name).add()).append(new KeyedCodec("Aliases", (Codec)Codec.STRING_ARRAY, false), (builder, aliases) -> builder.aliases = aliases, builder -> builder.aliases).add()).append(new KeyedCodec("Description", (Codec)Codec.STRING, true), (builder, description) -> builder.description = description, builder -> builder.description).add()).append(new KeyedCodec("Parameters", (Codec)new ArrayCodec((Codec)MacroCommandParameter.CODEC, x$0 -> new MacroCommandParameter[x$0]), false), (builder, parameters) -> builder.parameters = parameters, builder -> builder.parameters).add()).append(new KeyedCodec("Commands", (Codec)Codec.STRING_ARRAY, true), (builder, commands) -> builder.commands = commands, builder -> builder.commands).add()).build();
/*     */   }
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
/*     */   @Nullable
/*     */   public static CommandRegistration createAndRegisterCommand(@Nonnull MacroCommandBuilder builder) {
/*  90 */     if (builder.name == null) {
/*  91 */       return null;
/*     */     }
/*  93 */     MacroCommandBase macroCommandBase = new MacroCommandBase(builder.name, builder.aliases, builder.description, builder.parameters, builder.commands);
/*  94 */     return MacroCommandPlugin.get().getCommandRegistry().registerCommand((AbstractCommand)macroCommandBase);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  98 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 103 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\commandmacro\MacroCommandBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */