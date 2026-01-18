/*    */ package com.hypixel.hytale.server.npc.components.messaging;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BeaconSupport
/*    */   extends MessageSupport implements Component<EntityStore> {
/*    */   private NPCMessage[] messageSlots;
/*    */   private Object2IntMap<String> messageIndices;
/*    */   private Int2ObjectMap<String> indicesToMessages;
/*    */   
/*    */   public static ComponentType<EntityStore, BeaconSupport> getComponentType() {
/* 21 */     return NPCPlugin.get().getBeaconSupportComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postMessage(String message, Ref<EntityStore> target, double age) {
/* 29 */     if (this.messageSlots == null)
/* 30 */       return;  if (age < 0.0D && age != -1.0D)
/*    */       return; 
/* 32 */     int slot = this.messageIndices.getInt(message);
/* 33 */     if (slot != Integer.MIN_VALUE && this.messageSlots[slot].isEnabled()) {
/* 34 */       this.messageSlots[slot].activate(target, age);
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> pollMessage(int messageIndex) {
/* 40 */     NPCMessage beacon = this.messageSlots[messageIndex];
/* 41 */     beacon.deactivate();
/* 42 */     return beacon.getTarget();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> peekMessage(int messageIndex) {
/* 47 */     return this.messageSlots[messageIndex].getTarget();
/*    */   }
/*    */   
/*    */   public void initialise(@Nonnull Object2IntMap<String> messageIndices) {
/* 51 */     this.messageIndices = messageIndices;
/* 52 */     this.indicesToMessages = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/* 53 */     messageIndices.forEach((key, value) -> this.indicesToMessages.put(value, key));
/*    */     
/* 55 */     NPCMessage[] messages = new NPCMessage[messageIndices.size()];
/* 56 */     for (int i = 0; i < messages.length; i++) {
/* 57 */       messages[i] = new NPCMessage();
/*    */     }
/* 59 */     this.messageSlots = messages;
/*    */   }
/*    */   
/*    */   public String getMessageTextForIndex(int messageIndex) {
/* 63 */     return (String)this.indicesToMessages.get(messageIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public NPCMessage[] getMessageSlots() {
/* 68 */     return this.messageSlots;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 74 */     BeaconSupport beaconSupport = new BeaconSupport();
/* 75 */     beaconSupport.messageSlots = new NPCMessage[this.messageSlots.length];
/* 76 */     for (int i = 0; i < beaconSupport.messageSlots.length; i++) {
/* 77 */       beaconSupport.messageSlots[i] = this.messageSlots[i].clone();
/*    */     }
/* 79 */     beaconSupport.messageIndices = this.messageIndices;
/* 80 */     return beaconSupport;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\BeaconSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */