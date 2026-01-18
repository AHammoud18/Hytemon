/*     */ package com.hypixel.hytale.server.npc.blackboard;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.DamageBlockEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.IBlackboardView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.IBlackboardViewManager;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.SingletonBlackboardViewManager;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.attitude.AttitudeView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeViewManager;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.interaction.InteractionView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.resource.ResourceView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.resource.ResourceViewManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class Blackboard
/*     */   implements Resource<EntityStore>
/*     */ {
/*  34 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   public static ResourceType<EntityStore, Blackboard> getResourceType() {
/*  37 */     return NPCPlugin.get().getBlackboardResourceType();
/*     */   }
/*     */ 
/*     */   
/*  41 */   private final ConcurrentHashMap<Class<? extends IBlackboardView<?>>, IBlackboardViewManager<?>> views = new ConcurrentHashMap<>();
/*     */   
/*     */   public void init(@Nonnull World world) {
/*  44 */     registerViewType(BlockTypeView.class, (IBlackboardViewManager<BlockTypeView>)new BlockTypeViewManager());
/*  45 */     registerViewType(BlockEventView.class, (IBlackboardViewManager<BlockEventView>)new SingletonBlackboardViewManager((IBlackboardView)new BlockEventView(world)));
/*  46 */     registerViewType(EntityEventView.class, (IBlackboardViewManager<EntityEventView>)new SingletonBlackboardViewManager((IBlackboardView)new EntityEventView(world)));
/*  47 */     registerViewType(ResourceView.class, (IBlackboardViewManager<ResourceView>)new ResourceViewManager());
/*  48 */     registerViewType(AttitudeView.class, (IBlackboardViewManager<AttitudeView>)new SingletonBlackboardViewManager((IBlackboardView)new AttitudeView(world)));
/*  49 */     registerViewType(InteractionView.class, (IBlackboardViewManager<InteractionView>)new SingletonBlackboardViewManager((IBlackboardView)new InteractionView(world)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityDamageBlock(@Nonnull Ref<EntityStore> ref, @Nonnull DamageBlockEvent event) {
/*  59 */     for (Iterator<IBlackboardViewManager> iterator = this.views.values().iterator(); iterator.hasNext(); ) { IBlackboardViewManager<?> manager = iterator.next();
/*  60 */       manager.forEachView(view -> {
/*     */             if (view instanceof BlockEventView) {
/*     */               BlockEventView blockEventView = (BlockEventView)view;
/*     */               blockEventView.onEntityDamageBlock(ref, event);
/*     */             } 
/*     */           }); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityBreakBlock(@Nonnull Ref<EntityStore> ref, @Nonnull BreakBlockEvent event) {
/*  75 */     for (Iterator<IBlackboardViewManager> iterator = this.views.values().iterator(); iterator.hasNext(); ) { IBlackboardViewManager<?> manager = iterator.next();
/*  76 */       manager.forEachView(view -> {
/*     */             if (view instanceof BlockEventView) {
/*     */               BlockEventView blockEventView = (BlockEventView)view;
/*     */               blockEventView.onEntityBreakBlock(ref, event);
/*     */             } 
/*     */           }); }
/*     */   
/*     */   }
/*     */   private <View extends IBlackboardView<View>> void registerViewType(@Nonnull Class<View> clazz, @Nonnull IBlackboardViewManager<View> holder) {
/*  85 */     this.views.put(clazz, holder);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanupViews() {
/*  90 */     this.views.forEach((clazz, manager) -> manager.cleanup());
/*     */   }
/*     */   
/*     */   public void clear() {
/*  94 */     this.views.forEach((clazz, manager) -> manager.clear());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onWorldRemoved() {
/*  99 */     this.views.forEach((clazz, manager) -> manager.onWorldRemoved());
/*     */   }
/*     */   
/*     */   public <View extends IBlackboardView<View>> void forEachView(Class<View> viewTypeClass, Consumer<View> consumer) {
/* 103 */     getViewManager(viewTypeClass).forEachView(consumer);
/*     */   }
/*     */   
/*     */   public <View extends IBlackboardView<View>> View getView(Class<View> viewTypeClass, Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {
/* 107 */     return (View)getViewManager(viewTypeClass).get(ref, this, componentAccessor);
/*     */   }
/*     */   
/*     */   public <View extends IBlackboardView<View>> View getView(Class<View> viewTypeClass, int chunkX, int chunkZ) {
/* 111 */     return (View)getViewManager(viewTypeClass).get(chunkX, chunkZ, this);
/*     */   }
/*     */   
/*     */   public <View extends IBlackboardView<View>> View getView(Class<View> viewTypeClass, long index) {
/* 115 */     return (View)getViewManager(viewTypeClass).get(index, this);
/*     */   }
/*     */   
/*     */   public <View extends IBlackboardView<View>> View getIfExists(Class<View> viewTypeClass, long index) {
/* 119 */     return (View)getViewManager(viewTypeClass).getIfExists(index);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private <View extends IBlackboardView<View>> IBlackboardViewManager<View> getViewManager(Class<View> viewTypeClass) {
/* 125 */     return Objects.<IBlackboardViewManager<View>>requireNonNull((IBlackboardViewManager<View>)this.views.get(viewTypeClass), "View type manager not registered!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Resource<EntityStore> clone() {
/* 132 */     Blackboard blackboard = new Blackboard();
/*     */     
/* 134 */     blackboard.views.putAll(this.views);
/* 135 */     return blackboard;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\Blackboard.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */