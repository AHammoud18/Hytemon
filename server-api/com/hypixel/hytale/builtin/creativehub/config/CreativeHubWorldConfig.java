/*    */ package com.hypixel.hytale.builtin.creativehub.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
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
/*    */ public class CreativeHubWorldConfig
/*    */ {
/*    */   public static final String ID = "CreativeHub";
/*    */   @Nonnull
/*    */   public static final BuilderCodec<CreativeHubWorldConfig> CODEC;
/*    */   @Nullable
/*    */   private String startupInstance;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CreativeHubWorldConfig.class, CreativeHubWorldConfig::new).append(new KeyedCodec("StartupInstance", (Codec)Codec.STRING), (o, i) -> o.startupInstance = i, o -> o.startupInstance).documentation("The name of the instance to spawn players into when they first join this world.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static CreativeHubWorldConfig get(@Nonnull WorldConfig config) {
/* 43 */     return (CreativeHubWorldConfig)config.getPluginConfig().get(CreativeHubWorldConfig.class);
/*    */   }
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
/*    */   @Nullable
/*    */   public String getStartupInstance() {
/* 57 */     return this.startupInstance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\creativehub\config\CreativeHubWorldConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */