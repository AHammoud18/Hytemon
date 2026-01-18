/*     */ package com.hypixel.hytale.server.core.entity.entities.player.windows;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.event.IEvent;
/*     */ import com.hypixel.hytale.event.SyncEventBusRegistry;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowAction;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Consumer;
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
/*     */ public abstract class Window
/*     */ {
/*  36 */   public static final Map<WindowType, Supplier<? extends Window>> CLIENT_REQUESTABLE_WINDOW_TYPES = new ConcurrentHashMap<>();
/*     */   
/*     */   @Nonnull
/*  39 */   protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  44 */   protected final SyncEventBusRegistry<Void, WindowCloseEvent> closeEventRegistry = new SyncEventBusRegistry(LOGGER, WindowCloseEvent.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final WindowType windowType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  56 */   protected final AtomicBoolean isDirty = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  62 */   protected final AtomicBoolean needRebuild = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private WindowManager manager;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PlayerRef playerRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Window(@Nonnull WindowType windowType) {
/*  88 */     this.windowType = windowType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(@Nonnull PlayerRef playerRef, @Nonnull WindowManager manager) {
/*  98 */     this.playerRef = playerRef;
/*  99 */     this.manager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public abstract JsonObject getData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean onOpen0();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onClose0();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean onOpen() {
/* 126 */     return onOpen0();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onClose() {
/*     */     try {
/* 134 */       onClose0();
/*     */     } finally {
/* 136 */       this.closeEventRegistry.dispatchFor(null).dispatch((IBaseEvent)new WindowCloseEvent());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleAction(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull WindowAction action) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WindowType getType() {
/* 155 */     return this.windowType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 164 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 171 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PlayerRef getPlayerRef() {
/* 179 */     return this.playerRef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 186 */     assert this.manager != null;
/* 187 */     this.manager.closeWindow(this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invalidate() {
/* 194 */     this.isDirty.set(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setNeedRebuild() {
/* 201 */     this.needRebuild.set(true);
/* 202 */     getData().addProperty("needRebuild", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean consumeIsDirty() {
/* 209 */     return this.isDirty.getAndSet(false);
/*     */   }
/*     */   
/*     */   protected void consumeNeedRebuild() {
/* 213 */     if (this.needRebuild.get()) {
/* 214 */       getData().remove("needRebuild");
/* 215 */       this.needRebuild.set(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EventRegistration registerCloseEvent(@Nonnull Consumer<WindowCloseEvent> consumer) {
/* 227 */     return this.closeEventRegistry.register((short)0, null, consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EventRegistration registerCloseEvent(short priority, @Nonnull Consumer<WindowCloseEvent> consumer) {
/* 239 */     return this.closeEventRegistry.register(priority, null, consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EventRegistration registerCloseEvent(@Nonnull EventPriority priority, @Nonnull Consumer<WindowCloseEvent> consumer) {
/* 251 */     return this.closeEventRegistry.register(priority.getValue(), null, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 256 */     if (this == o) return true; 
/* 257 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 259 */     Window window = (Window)o;
/*     */     
/* 261 */     if (this.id != window.id) return false; 
/* 262 */     if (!Objects.equals(this.windowType, window.windowType)) return false; 
/* 263 */     return Objects.equals(this.playerRef, window.playerRef);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 268 */     int result = this.windowType.hashCode();
/* 269 */     result = 31 * result + this.id;
/* 270 */     result = 31 * result + ((this.playerRef != null) ? this.playerRef.hashCode() : 0);
/* 271 */     return result;
/*     */   }
/*     */   
/*     */   public static class WindowCloseEvent implements IEvent<Void> {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\windows\Window.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */