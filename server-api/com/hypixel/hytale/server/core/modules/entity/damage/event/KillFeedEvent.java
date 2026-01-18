/*     */ package com.hypixel.hytale.server.core.modules.entity.damage.event;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.List;
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
/*     */ public class KillFeedEvent
/*     */ {
/*     */   public static final class KillerMessage
/*     */     extends CancellableEcsEvent
/*     */   {
/*     */     @Nonnull
/*     */     private final Damage damage;
/*     */     @Nonnull
/*     */     private final Ref<EntityStore> targetRef;
/*     */     @Nullable
/*  42 */     private Message message = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KillerMessage(@Nonnull Damage damage, @Nonnull Ref<EntityStore> targetRef) {
/*  52 */       this.damage = damage;
/*  53 */       this.targetRef = targetRef;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Damage getDamage() {
/*  61 */       return this.damage;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Ref<EntityStore> getTargetRef() {
/*  69 */       return this.targetRef;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMessage(@Nullable Message message) {
/*  78 */       this.message = message;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Message getMessage() {
/*  86 */       return this.message;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DecedentMessage
/*     */     extends CancellableEcsEvent
/*     */   {
/*     */     @Nonnull
/*     */     private final Damage damage;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/* 104 */     private Message message = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DecedentMessage(@Nonnull Damage damage) {
/* 113 */       this.damage = damage;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Damage getDamage() {
/* 120 */       return this.damage;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMessage(@Nullable Message message) {
/* 129 */       this.message = message;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Message getMessage() {
/* 137 */       return this.message;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Display
/*     */     extends CancellableEcsEvent
/*     */   {
/*     */     @Nonnull
/*     */     private final Damage damage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private String icon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final List<PlayerRef> broadcastTargets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Display(@Nonnull Damage damage, @Nullable String icon, @Nonnull List<PlayerRef> broadcastTargets) {
/* 174 */       this.damage = damage;
/* 175 */       this.icon = icon;
/* 176 */       this.broadcastTargets = broadcastTargets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public List<PlayerRef> getBroadcastTargets() {
/* 184 */       return this.broadcastTargets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Damage getDamage() {
/* 192 */       return this.damage;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public String getIcon() {
/* 200 */       return this.icon;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setIcon(@Nullable String icon) {
/* 209 */       this.icon = icon;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\event\KillFeedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */