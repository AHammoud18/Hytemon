/*    */ package com.hypixel.hytale.server.npc.blackboard.view.event;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.event.EventRegistry;
/*    */ import com.hypixel.hytale.event.IEventRegistry;
/*    */ import com.hypixel.hytale.function.consumer.IntObjectConsumer;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.IBlackboardView;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EventView<ViewType extends IBlackboardView<ViewType>, EventType extends Enum<EventType>, NotificationType extends EventNotification>
/*    */   implements IBlackboardView<ViewType>
/*    */ {
/*    */   @Nonnull
/*    */   protected final Map<EventType, EventTypeRegistration<EventType, NotificationType>> entityMapsByEventType;
/*    */   @Nonnull
/*    */   protected final World world;
/*    */   protected final EventType[] eventTypes;
/*    */   @Nullable
/*    */   protected EventRegistry eventRegistry;
/*    */   @Nullable
/*    */   protected ComponentRegistryProxy<EntityStore> entityStoreRegistry;
/*    */   protected boolean shutdown;
/*    */   protected final NotificationType reusableEventNotification;
/*    */   
/*    */   protected EventView(Class<EventType> type, EventType[] eventTypes, NotificationType reusableEventNotification, @Nonnull World world) {
/* 43 */     this.entityMapsByEventType = new EnumMap<>(type);
/* 44 */     this.eventTypes = eventTypes;
/* 45 */     this.reusableEventNotification = reusableEventNotification;
/* 46 */     this.world = world;
/*    */     
/* 48 */     this.entityStoreRegistry = NPCPlugin.get().getEntityStoreRegistry();
/* 49 */     this.eventRegistry = new EventRegistry(new CopyOnWriteArrayList(), () -> !this.shutdown, String.format("EventView for world %s is not enabled!", new Object[] { world.getName() }), (IEventRegistry)NPCPlugin.get().getEventRegistry());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOutdated(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onWorldRemoved() {
/* 59 */     this.shutdown = true;
/* 60 */     if (this.eventRegistry != null) {
/* 61 */       this.eventRegistry.shutdown();
/* 62 */       this.eventRegistry = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 68 */     for (EventType eventType : this.eventTypes) {
/* 69 */       ((EventTypeRegistration)this.entityMapsByEventType.get(eventType)).cleanup();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getSetCount() {
/* 74 */     int count = 0;
/* 75 */     for (EventType type : this.eventTypes) {
/* 76 */       count += ((EventTypeRegistration)this.entityMapsByEventType.get(type)).getSetCount();
/*    */     }
/* 78 */     return count;
/*    */   }
/*    */   
/*    */   public void forEach(@Nonnull IntObjectConsumer<EventType> setConsumer, @Nonnull Consumer<Ref<EntityStore>> npcConsumer) {
/* 82 */     for (EventType eventType : this.eventTypes) {
/* 83 */       ((EventTypeRegistration)this.entityMapsByEventType.get(eventType)).forEach(setConsumer, npcConsumer);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onEvent(int senderTypeId, double x, double y, double z, Ref<EntityStore> initiator, Ref<EntityStore> skip, @Nonnull ComponentAccessor<EntityStore> componentAccessor, EventType type) {
/* 89 */     this.reusableEventNotification.setPosition(x, y, z);
/* 90 */     this.reusableEventNotification.setInitiator(initiator);
/* 91 */     ((EventTypeRegistration)this.entityMapsByEventType.get(type)).relayEvent(senderTypeId, this.reusableEventNotification, skip, componentAccessor);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\event\EventView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */