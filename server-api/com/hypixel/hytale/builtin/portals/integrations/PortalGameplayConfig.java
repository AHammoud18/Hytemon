/*    */ package com.hypixel.hytale.builtin.portals.integrations;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class PortalGameplayConfig
/*    */ {
/*    */   public static final BuilderCodec<PortalGameplayConfig> CODEC;
/*    */   private VoidEventConfig voidEvent;
/*    */   
/*    */   static {
/* 17 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PortalGameplayConfig.class, PortalGameplayConfig::new).append(new KeyedCodec("VoidEvent", (Codec)VoidEventConfig.CODEC), (config, o) -> config.voidEvent = o, config -> config.voidEvent).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public VoidEventConfig getVoidEvent() {
/* 23 */     return this.voidEvent;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\integrations\PortalGameplayConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */