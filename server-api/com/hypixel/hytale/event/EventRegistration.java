/*    */ package com.hypixel.hytale.event;
/*    */ 
/*    */ import com.hypixel.hytale.registry.Registration;
/*    */ import java.util.function.BooleanSupplier;
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
/*    */ public class EventRegistration<KeyType, EventType extends IBaseEvent<KeyType>>
/*    */   extends Registration
/*    */ {
/*    */   @Nonnull
/*    */   protected final Class<EventType> eventClass;
/*    */   
/*    */   public EventRegistration(@Nonnull Class<EventType> eventClass, @Nonnull BooleanSupplier isEnabled, @Nonnull Runnable unregister) {
/* 30 */     super(isEnabled, unregister);
/* 31 */     this.eventClass = eventClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventRegistration(@Nonnull EventRegistration<KeyType, EventType> registration, @Nonnull BooleanSupplier isEnabled, @Nonnull Runnable unregister) {
/* 42 */     super(isEnabled, unregister);
/* 43 */     this.eventClass = registration.eventClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Class<EventType> getEventClass() {
/* 51 */     return this.eventClass;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 57 */     return "EventRegistration{eventClass=" + String.valueOf(this.eventClass) + ", " + super
/*    */       
/* 59 */       .toString() + "}";
/*    */   }
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
/*    */   @Nonnull
/*    */   @SafeVarargs
/*    */   public static <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> combine(@Nonnull EventRegistration<KeyType, EventType> thisRegistration, @Nonnull EventRegistration<KeyType, EventType>... containerRegistrations) {
/* 76 */     return new EventRegistration<>(thisRegistration.eventClass, () -> {
/*    */           if (!thisRegistration.isEnabled.getAsBoolean())
/*    */             return false; 
/*    */           for (EventRegistration<KeyType, EventType> containerRegistration : containerRegistrations) {
/*    */             if (!containerRegistration.isEnabled.getAsBoolean())
/*    */               return false; 
/*    */           } 
/*    */           return true;
/*    */         }() -> {
/*    */           thisRegistration.unregister();
/*    */           for (EventRegistration<KeyType, EventType> containerRegistration : containerRegistrations)
/*    */             containerRegistration.unregister(); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\EventRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */