/*     */ package com.hypixel.hytale.builtin.instances.event;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.instances.config.InstanceDiscoveryConfig;
/*     */ import com.hypixel.hytale.component.system.ICancellableEcsEvent;
/*     */ import java.util.UUID;
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
/*     */ public class Display
/*     */   extends DiscoverInstanceEvent
/*     */   implements ICancellableEcsEvent
/*     */ {
/*     */   private boolean cancelled = false;
/*     */   private boolean display;
/*     */   
/*     */   public Display(@Nonnull UUID instanceWorldUuid, @Nonnull InstanceDiscoveryConfig discoveryConfig) {
/*  81 */     super(instanceWorldUuid, discoveryConfig);
/*  82 */     this.display = discoveryConfig.isDisplay();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/*  87 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCancelled(boolean cancelled) {
/*  92 */     this.cancelled = cancelled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldDisplay() {
/*  99 */     return this.display;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplay(boolean display) {
/* 108 */     this.display = display;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\event\DiscoverInstanceEvent$Display.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */