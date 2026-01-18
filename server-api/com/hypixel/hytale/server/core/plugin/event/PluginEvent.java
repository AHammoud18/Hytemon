/*    */ package com.hypixel.hytale.server.core.plugin.event;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public abstract class PluginEvent
/*    */   implements IEvent<Class<? extends PluginBase>>
/*    */ {
/*    */   @Nonnull
/*    */   private final PluginBase plugin;
/*    */   
/*    */   public PluginEvent(@Nonnull PluginBase plugin) {
/* 25 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PluginBase getPlugin() {
/* 33 */     return this.plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 39 */     return "PluginEvent{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\event\PluginEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */