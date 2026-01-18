/*    */ package com.hypixel.hytale.component.event;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.SystemType;
/*    */ import com.hypixel.hytale.component.system.EcsEvent;
/*    */ import com.hypixel.hytale.component.system.EventSystem;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EventSystemType<ECS_TYPE, Event extends EcsEvent, SYSTEM_TYPE extends EventSystem<Event> & ISystem<ECS_TYPE>>
/*    */   extends SystemType<ECS_TYPE, SYSTEM_TYPE>
/*    */ {
/*    */   @Nonnull
/*    */   private final Class<Event> eClass;
/*    */   
/*    */   protected EventSystemType(@Nonnull ComponentRegistry<ECS_TYPE> registry, @Nonnull Class<? super SYSTEM_TYPE> tClass, @Nonnull Class<Event> eClass, int index) {
/* 39 */     super(registry, tClass, index);
/* 40 */     this.eClass = eClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Class<Event> getEventClass() {
/* 48 */     return this.eClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isType(@Nonnull ISystem<ECS_TYPE> system) {
/* 53 */     if (!super.isType(system)) return false; 
/* 54 */     if (system instanceof EventSystem) { EventSystem<?> eventSystem = (EventSystem)system;
/* 55 */       return this.eClass.equals(eventSystem.getEventType()); }
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\event\EventSystemType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */