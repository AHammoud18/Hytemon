/*     */ package com.hypixel.hytale.server.core.event.events.player;
/*     */ 
/*     */ import com.hypixel.hytale.event.IAsyncEvent;
/*     */ import com.hypixel.hytale.event.ICancellable;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PlayerChatEvent
/*     */   implements IAsyncEvent<String>, ICancellable {
/*     */   @Nonnull
/*     */   public static final Formatter DEFAULT_FORMATTER;
/*     */   @Nonnull
/*     */   private PlayerRef sender;
/*     */   @Nonnull
/*     */   private List<PlayerRef> targets;
/*     */   
/*     */   static {
/*  20 */     DEFAULT_FORMATTER = ((playerRef, msg) -> Message.translation("server.chat.playerMessage").param("username", playerRef.getUsername()).param("message", msg));
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
/*     */   @Nonnull
/*     */   private String content;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Formatter formatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean cancelled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerChatEvent(@Nonnull PlayerRef sender, @Nonnull List<PlayerRef> targets, @Nonnull String content) {
/*  60 */     this.sender = sender;
/*  61 */     this.targets = targets;
/*  62 */     this.content = content;
/*  63 */     this.formatter = DEFAULT_FORMATTER;
/*  64 */     this.cancelled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PlayerRef getSender() {
/*  72 */     return this.sender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSender(@Nonnull PlayerRef sender) {
/*  81 */     this.sender = sender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<PlayerRef> getTargets() {
/*  89 */     return this.targets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargets(@Nonnull List<PlayerRef> targets) {
/*  98 */     this.targets = targets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getContent() {
/* 106 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(@Nonnull String content) {
/* 115 */     this.content = content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Formatter getFormatter() {
/* 123 */     return this.formatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormatter(@Nonnull Formatter formatter) {
/* 132 */     this.formatter = formatter;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 137 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCancelled(boolean cancelled) {
/* 142 */     this.cancelled = cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 148 */     return "PlayerChatEvent{message=" + this.content + ", targets=" + String.valueOf(this.targets) + ", formatter=" + String.valueOf(this.formatter) + ", cancelled=" + this.cancelled + "} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       .toString();
/*     */   }
/*     */   
/*     */   public static interface Formatter {
/*     */     @Nonnull
/*     */     Message format(@Nonnull PlayerRef param1PlayerRef, @Nonnull String param1String);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerChatEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */