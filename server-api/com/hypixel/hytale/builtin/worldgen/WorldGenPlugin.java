/*    */ package com.hypixel.hytale.builtin.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.Priority;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*    */ import com.hypixel.hytale.server.worldgen.BiomeDataSystem;
/*    */ import com.hypixel.hytale.server.worldgen.HytaleWorldGenProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   private static WorldGenPlugin instance;
/*    */   
/*    */   public static WorldGenPlugin get() {
/* 21 */     return instance;
/*    */   }
/*    */   
/*    */   public WorldGenPlugin(@Nonnull JavaPluginInit init) {
/* 25 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 30 */     instance = this;
/*    */     
/* 32 */     getEntityStoreRegistry().registerSystem((ISystem)new BiomeDataSystem());
/*    */     
/* 34 */     IWorldGenProvider.CODEC.register(Priority.DEFAULT
/*    */         
/* 36 */         .before(1), "Hytale", HytaleWorldGenProvider.class, (Codec)HytaleWorldGenProvider.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\worldgen\WorldGenPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */