/*     */ package com.hypixel.hytale.server.npc.blackboard.view.event;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.ListUtil;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.function.consumer.IntObjectConsumer;
/*     */ import com.hypixel.hytale.function.predicate.BiIntPredicate;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class EventTypeRegistration<EventType extends Enum<EventType>, NotificationType extends EventNotification> {
/*     */   @Nullable
/*  24 */   private static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
/*     */   
/*     */   private final EventType type;
/*  27 */   private final BitSet eventSets = new BitSet();
/*  28 */   private final Int2ObjectMap<List<Ref<EntityStore>>> entitiesBySet = (Int2ObjectMap<List<Ref<EntityStore>>>)new Int2ObjectOpenHashMap();
/*     */   private final BiIntPredicate setTester;
/*     */   private final IEventCallback<EventType, NotificationType> eventCallback;
/*     */   
/*     */   public EventTypeRegistration(EventType type, BiIntPredicate setTester, IEventCallback<EventType, NotificationType> eventCallback) {
/*  33 */     this.type = type;
/*  34 */     this.setTester = setTester;
/*  35 */     this.eventCallback = eventCallback;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialiseEntity(Ref<EntityStore> ref, @Nonnull IntSet changeSets) {
/*  40 */     IntIterator it = changeSets.iterator();
/*  41 */     while (it.hasNext()) {
/*  42 */       int set = it.nextInt();
/*  43 */       this.eventSets.set(set);
/*  44 */       ((List<Ref<EntityStore>>)this.entitiesBySet.computeIfAbsent(set, k -> new ObjectArrayList())).add(ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void relayEvent(int senderTypeId, @Nonnull NotificationType reusableEventNotification, Ref<EntityStore> skipEntityReference, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  50 */     Ref<EntityStore> initiator = reusableEventNotification.getInitiator(); int set;
/*  51 */     for (set = this.eventSets.nextSetBit(0); set >= 0; set = this.eventSets.nextSetBit(set + 1)) {
/*  52 */       if (this.setTester.test(set, senderTypeId)) {
/*  53 */         List<Ref<EntityStore>> entities = (List<Ref<EntityStore>>)this.entitiesBySet.get(set);
/*  54 */         if (entities != null) {
/*  55 */           reusableEventNotification.setSet(set);
/*  56 */           for (int j = 0; j < entities.size(); j++) {
/*  57 */             Ref<EntityStore> entity = entities.get(j);
/*  58 */             if (entity.isValid())
/*     */             {
/*     */ 
/*     */               
/*  62 */               if (!entity.equals(initiator) && !entity.equals(skipEntityReference)) {
/*  63 */                 NPCEntity npc = (NPCEntity)componentAccessor.getComponent(entity, NPC_COMPONENT_TYPE);
/*  64 */                 this.eventCallback.notify(npc, this.type, reusableEventNotification);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*  70 */       if (set == Integer.MAX_VALUE) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSetCount() {
/*  77 */     return this.eventSets.cardinality();
/*     */   }
/*     */   
/*     */   public void forEach(@Nonnull IntObjectConsumer<EventType> setConsumer, @Nonnull Consumer<Ref<EntityStore>> npcConsumer) {
/*  81 */     for (int set = this.eventSets.nextSetBit(0); set >= 0; set = this.eventSets.nextSetBit(set + 1)) {
/*  82 */       setConsumer.accept(set, this.type);
/*  83 */       List<Ref<EntityStore>> entities = (List<Ref<EntityStore>>)this.entitiesBySet.get(set);
/*  84 */       if (entities != null) {
/*     */ 
/*     */ 
/*     */         
/*  88 */         for (int i = 0; i < entities.size(); i++) {
/*  89 */           Ref<EntityStore> entity = entities.get(i);
/*  90 */           if (entity.isValid()) npcConsumer.accept(entity);
/*     */         
/*     */         } 
/*  93 */         if (set == Integer.MAX_VALUE)
/*     */           break; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cleanup() {
/* 100 */     for (int set = this.eventSets.nextSetBit(0); set >= 0; set = this.eventSets.nextSetBit(set + 1)) {
/* 101 */       List<Ref<EntityStore>> entities = (List<Ref<EntityStore>>)this.entitiesBySet.getOrDefault(set, null);
/* 102 */       if (entities != null) {
/* 103 */         ListUtil.removeIf(entities, r -> !r.isValid());
/* 104 */         if (entities.isEmpty())
/*     */         {
/* 106 */           this.eventSets.clear(set);
/*     */         }
/*     */       } 
/*     */       
/* 110 */       if (set == Integer.MAX_VALUE)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\event\EventTypeRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */