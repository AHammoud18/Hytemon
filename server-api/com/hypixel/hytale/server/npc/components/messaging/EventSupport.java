/*    */ package com.hypixel.hytale.server.npc.components.messaging;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.event.EventNotification;
/*    */ import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public abstract class EventSupport<EventType extends Enum<EventType>, NotificationType extends EventNotification>
/*    */   extends MessageSupport
/*    */ {
/*    */   protected static final double EVENT_AGE = 2.0D;
/*    */   protected EventMessage[] messageSlots;
/*    */   protected Map<EventType, Int2IntMap> messageIndices;
/*    */   
/*    */   public void postMessage(EventType type, @Nonnull NotificationType notification, @Nonnull Ref<EntityStore> parent, @Nonnull Store<EntityStore> store) {
/* 24 */     EventMessage slot = getMessageSlot(type, notification);
/* 25 */     if (slot == null || !slot.isEnabled())
/*    */       return; 
/* 27 */     Vector3d parentEntityPosition = ((TransformComponent)store.getComponent(parent, TransformComponent.getComponentType())).getPosition();
/* 28 */     Vector3d pos = notification.getPosition();
/* 29 */     double x = pos.getX();
/* 30 */     double y = pos.getY();
/* 31 */     double z = pos.getZ();
/* 32 */     double distanceSquared = parentEntityPosition.distanceSquaredTo(x, y, z);
/* 33 */     if (distanceSquared <= slot.getMaxRangeSquared() && (!slot.isActivated() || distanceSquared < slot.getPosition().distanceSquaredTo(parentEntityPosition))) {
/* 34 */       slot.activate(x, y, z, notification.getInitiator(), 2.0D);
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public EventMessage getMessageSlot(EventType type, @Nonnull NotificationType notification) {
/* 40 */     if (this.messageSlots == null) return null;
/*    */     
/* 42 */     Int2IntMap typeSlots = this.messageIndices.get(type);
/* 43 */     if (typeSlots == null) return null;
/*    */     
/* 45 */     int slotIdx = typeSlots.get(notification.getSet());
/* 46 */     if (slotIdx == Integer.MIN_VALUE) return null;
/*    */     
/* 48 */     return this.messageSlots[slotIdx];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasMatchingMessage(int messageIndex, @Nonnull Vector3d parentPosition, double range) {
/* 53 */     if (!isMessageQueued(messageIndex)) return false;
/*    */     
/* 55 */     EventMessage event = this.messageSlots[messageIndex];
/* 56 */     return (event.getPosition().distanceSquaredTo(parentPosition) < range * range);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> pollMessage(int messageIndex) {
/* 61 */     EventMessage event = this.messageSlots[messageIndex];
/* 62 */     event.deactivate();
/* 63 */     return event.getTarget();
/*    */   }
/*    */   
/*    */   public void initialise(Map<EventType, Int2IntMap> setIndices, @Nonnull Int2DoubleMap messageRanges, int count) {
/* 67 */     this.messageIndices = setIndices;
/* 68 */     EventMessage[] messages = new EventMessage[count];
/* 69 */     for (int i = 0; i < messages.length; i++) {
/* 70 */       messages[i] = new EventMessage(messageRanges.get(i));
/*    */     }
/* 72 */     this.messageSlots = messages;
/*    */   }
/*    */   
/*    */   public void cloneTo(@Nonnull EventSupport<EventType, NotificationType> other) {
/* 76 */     other.messageSlots = new EventMessage[this.messageSlots.length];
/* 77 */     for (int i = 0; i < other.messageSlots.length; i++) {
/* 78 */       other.messageSlots[i] = this.messageSlots[i].clone();
/*    */     }
/* 80 */     other.messageIndices = this.messageIndices;
/*    */   }
/*    */ 
/*    */   
/*    */   public NPCMessage[] getMessageSlots() {
/* 85 */     return (NPCMessage[])this.messageSlots;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\EventSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */