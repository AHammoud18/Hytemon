/*    */ package com.hypixel.hytale.builtin.commandmacro;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandRegistration;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MacroCommandPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   private static MacroCommandPlugin instance;
/*    */   
/*    */   public static MacroCommandPlugin get() {
/* 24 */     return instance;
/*    */   }
/*    */   
/* 27 */   private final Map<String, CommandRegistration> macroCommandRegistrations = (Map<String, CommandRegistration>)new Object2ObjectOpenHashMap();
/*    */   
/*    */   public MacroCommandPlugin(@Nonnull JavaPluginInit init) {
/* 30 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 35 */     instance = this;
/*    */     
/* 37 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(MacroCommandBuilder.class, (AssetMap)new DefaultAssetMap())
/* 38 */         .setPath("MacroCommands"))
/* 39 */         .setKeyFunction(MacroCommandBuilder::getId))
/* 40 */         .setCodec((AssetCodec)MacroCommandBuilder.CODEC))
/* 41 */         .build());
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
/* 52 */     getEventRegistry().register(LoadedAssetsEvent.class, MacroCommandBuilder.class, this::loadCommandMacroAsset);
/*    */     
/* 54 */     getCommandRegistry().registerCommand((AbstractCommand)new WaitCommand());
/* 55 */     getCommandRegistry().registerCommand((AbstractCommand)new EchoCommand());
/*    */   }
/*    */   
/*    */   public void loadCommandMacroAsset(@Nonnull LoadedAssetsEvent<String, MacroCommandBuilder, DefaultAssetMap<String, MacroCommandBuilder>> event) {
/* 59 */     for (MacroCommandBuilder value : event.getLoadedAssets().values()) {
/* 60 */       if (this.macroCommandRegistrations.containsKey(value.getName())) {
/* 61 */         ((CommandRegistration)this.macroCommandRegistrations.get(value.getName())).unregister();
/*    */       }
/*    */       
/* 64 */       CommandRegistration commandRegistration = MacroCommandBuilder.createAndRegisterCommand(value);
/* 65 */       if (commandRegistration != null)
/* 66 */         this.macroCommandRegistrations.put(value.getName(), commandRegistration); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\commandmacro\MacroCommandPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */