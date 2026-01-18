/*     */ package com.hypixel.hytale.server.core.entity.entities.player.windows;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.ExtraResources;
/*     */ import com.hypixel.hytale.protocol.InventorySection;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.window.CloseWindow;
/*     */ import com.hypixel.hytale.protocol.packets.window.OpenWindow;
/*     */ import com.hypixel.hytale.protocol.packets.window.UpdateWindow;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowManager
/*     */ {
/*     */   @Nonnull
/*  34 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private final AtomicInteger windowId = new AtomicInteger(1);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  44 */   private final Int2ObjectConcurrentHashMap<Window> windows = new Int2ObjectConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  50 */   private final Int2ObjectConcurrentHashMap<EventRegistration> windowChangeEvents = new Int2ObjectConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlayerRef playerRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(@Nonnull PlayerRef playerRef) {
/*  64 */     this.playerRef = playerRef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UpdateWindow clientOpenWindow(@Nonnull Window window) {
/*  75 */     if (!Window.CLIENT_REQUESTABLE_WINDOW_TYPES.containsKey(window.getType())) {
/*  76 */       throw new IllegalArgumentException("Client opened window must be registered in Window.CLIENT_REQUESTABLE_WINDOW_TYPES but got: " + String.valueOf(window.getType()));
/*     */     }
/*     */ 
/*     */     
/*  80 */     int id = 0;
/*  81 */     Window oldWindow = (Window)this.windows.remove(0);
/*  82 */     if (oldWindow != null) {
/*  83 */       if (oldWindow instanceof ItemContainerWindow) {
/*  84 */         ((EventRegistration)this.windowChangeEvents.remove(oldWindow.getId())).unregister();
/*     */       }
/*     */       
/*  87 */       oldWindow.onClose();
/*  88 */       LOGGER.at(Level.FINE).log("%s close window %s with id %s", this.playerRef.getUuid(), oldWindow.getType(), Integer.valueOf(0));
/*     */     } 
/*     */ 
/*     */     
/*  92 */     setWindow0(0, window);
/*     */     
/*  94 */     if (!window.onOpen()) {
/*  95 */       closeWindow(0);
/*  96 */       window.setId(-1);
/*  97 */       return null;
/*     */     } 
/*     */     
/* 100 */     if (!window.consumeIsDirty()) return null;
/*     */     
/* 102 */     InventorySection section = null;
/* 103 */     if (window instanceof ItemContainerWindow) {
/* 104 */       section = ((ItemContainerWindow)window).getItemContainer().toPacket();
/*     */     }
/*     */     
/* 107 */     ExtraResources extraResources = null;
/* 108 */     if (window instanceof MaterialContainerWindow) {
/* 109 */       extraResources = ((MaterialContainerWindow)window).getExtraResourcesSection().toPacket();
/*     */     }
/*     */     
/* 112 */     return new UpdateWindow(0, window.getData().toString(), section, extraResources);
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
/*     */   @Nullable
/*     */   public OpenWindow openWindow(@Nonnull Window window) {
/* 125 */     int id = this.windowId.getAndUpdate(operand -> (++operand > 0) ? operand : 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     setWindow(id, window);
/*     */     
/* 132 */     if (!window.onOpen()) {
/* 133 */       closeWindow(id);
/* 134 */       window.setId(-1);
/* 135 */       return null;
/*     */     } 
/*     */     
/* 138 */     window.consumeIsDirty();
/*     */     
/* 140 */     LOGGER.at(Level.FINE).log("%s opened window %s with id %s and data %s", this.playerRef.getUuid(), window.getType(), Integer.valueOf(id), window.getData());
/*     */     
/* 142 */     InventorySection section = null;
/* 143 */     if (window instanceof ItemContainerWindow) {
/* 144 */       section = ((ItemContainerWindow)window).getItemContainer().toPacket();
/*     */     }
/*     */     
/* 147 */     ExtraResources extraResources = null;
/* 148 */     if (window instanceof MaterialContainerWindow) {
/* 149 */       extraResources = ((MaterialContainerWindow)window).getExtraResourcesSection().toPacket();
/*     */     }
/*     */     
/* 152 */     return new OpenWindow(id, window.getType(), window.getData().toString(), section, extraResources);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<OpenWindow> openWindows(@Nonnull Window... windows) {
/* 163 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 164 */     for (Window window : windows) {
/*     */       
/* 166 */       OpenWindow packet = openWindow(window);
/* 167 */       if (packet != null) {
/* 168 */         objectArrayList.add(packet);
/*     */       } else {
/* 170 */         for (ObjectListIterator<OpenWindow> objectListIterator = objectArrayList.iterator(); objectListIterator.hasNext(); ) { OpenWindow addedPacket = objectListIterator.next();
/* 171 */           closeWindow(addedPacket.id); }
/*     */         
/* 173 */         return null;
/*     */       } 
/*     */     } 
/* 176 */     return (List<OpenWindow>)objectArrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWindow(int id, @Nonnull Window window) {
/* 186 */     if (id >= this.windowId.get()) throw new IllegalArgumentException("id is outside of the range, use addWindow"); 
/* 187 */     if (id == 0 || id == -1) throw new IllegalArgumentException("id is invalid, can't be 0 or -1"); 
/* 188 */     setWindow0(id, window);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setWindow0(int id, @Nonnull Window window) {
/* 198 */     if (this.windows.putIfAbsent(id, window) != null) {
/* 199 */       throw new IllegalArgumentException("Window " + id + " already exists");
/*     */     }
/* 201 */     window.setId(id);
/* 202 */     window.init(this.playerRef, this);
/*     */     
/* 204 */     if (window instanceof ItemContainerWindow) {
/* 205 */       ItemContainer itemContainer = ((ItemContainerWindow)window).getItemContainer();
/* 206 */       this.windowChangeEvents.put(id, itemContainer.registerChangeEvent(EventPriority.LAST, e -> markWindowChanged(id)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Window getWindow(int id) {
/* 218 */     if (id == -1) throw new IllegalArgumentException("Window id -1 is invalid!"); 
/* 219 */     return (Window)this.windows.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Window> getWindows() {
/* 229 */     return (List<Window>)new ObjectArrayList((Collection)this.windows.values());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWindow(@Nonnull Window window) {
/* 238 */     InventorySection section = null;
/*     */ 
/*     */     
/* 241 */     if (window instanceof ItemContainerWindow) { ItemContainerWindow itemContainerWindow = (ItemContainerWindow)window;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 247 */       section = itemContainerWindow.getItemContainer().toPacket(); }
/*     */ 
/*     */     
/* 250 */     ExtraResources extraResources = null;
/* 251 */     if (window instanceof MaterialContainerWindow) { MaterialContainerWindow materialContainerWindow = (MaterialContainerWindow)window;
/* 252 */       if (!materialContainerWindow.isValid()) {
/* 253 */         extraResources = materialContainerWindow.getExtraResourcesSection().toPacket();
/*     */       } }
/*     */ 
/*     */ 
/*     */     
/* 258 */     this.playerRef.getPacketHandler().writeNoCache((Packet)new UpdateWindow(window.getId(), window.getData().toString(), section, extraResources));
/*     */ 
/*     */     
/* 261 */     window.consumeNeedRebuild();
/*     */     
/* 263 */     LOGGER.at(Level.FINER).log("%s update window %s with id %s and data %s", this.playerRef.getUuid(), window.getType(), Integer.valueOf(window.getId()), window.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Window closeWindow(int id) {
/* 274 */     if (id == -1) throw new IllegalArgumentException("Window id -1 is invalid!");
/*     */     
/* 276 */     this.playerRef.getPacketHandler().writeNoCache((Packet)new CloseWindow(id));
/*     */     
/* 278 */     Window window = (Window)this.windows.remove(id);
/* 279 */     if (window instanceof ItemContainerWindow) {
/* 280 */       ((EventRegistration)this.windowChangeEvents.remove(window.getId())).unregister();
/*     */     }
/*     */     
/* 283 */     if (window == null) {
/* 284 */       throw new IllegalStateException("Window id " + id + " is invalid!");
/*     */     }
/*     */     
/* 287 */     window.onClose();
/* 288 */     LOGGER.at(Level.FINE).log("%s close window %s with id %s", this.playerRef.getUuid(), window.getType(), Integer.valueOf(id));
/* 289 */     return window;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeAllWindows() {
/* 296 */     for (Window window : this.windows.values()) {
/* 297 */       window.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markWindowChanged(int id) {
/* 307 */     Window window = getWindow(id);
/* 308 */     if (window != null) {
/* 309 */       window.invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWindows() {
/* 317 */     this.windows.forEach((id, window, _windowManager) -> { if (window.consumeIsDirty()) _windowManager.updateWindow(window);  }this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateWindows() {
/* 326 */     for (Window value : this.windows.values()) {
/* 327 */       if (value instanceof ValidatedWindow && 
/* 328 */         !((ValidatedWindow)value).validate()) {
/* 329 */         value.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <W extends Window> void closeAndRemoveAll(@Nonnull Map<UUID, W> windows) {
/* 342 */     for (Iterator<W> iterator = windows.values().iterator(); iterator.hasNext(); ) {
/* 343 */       ((Window)iterator.next()).close();
/* 344 */       iterator.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 351 */     return "WindowManager{windowId=" + String.valueOf(this.windowId) + ", windows=" + String.valueOf(this.windows) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\windows\WindowManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */