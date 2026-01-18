/*    */ package com.hypixel.hytale.event;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public interface IEventBus
/*    */   extends IEventRegistry
/*    */ {
/*    */   default <KeyType, EventType extends IEvent<KeyType>> EventType dispatch(@Nonnull Class<EventType> eventClass) {
/* 21 */     return (EventType)dispatchFor(eventClass, null).dispatch(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default <EventType extends IAsyncEvent<Void>> CompletableFuture<EventType> dispatchAsync(@Nonnull Class<EventType> eventClass) {
/* 32 */     return (CompletableFuture<EventType>)dispatchForAsync(eventClass).dispatch(null);
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
/*    */   default <KeyType, EventType extends IEvent<KeyType>> IEventDispatcher<EventType, EventType> dispatchFor(@Nonnull Class<? super EventType> eventClass) {
/* 44 */     return dispatchFor(eventClass, null);
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
/*    */   <KeyType, EventType extends IEvent<KeyType>> IEventDispatcher<EventType, EventType> dispatchFor(@Nonnull Class<? super EventType> paramClass, @Nullable KeyType paramKeyType);
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
/*    */   default <KeyType, EventType extends IAsyncEvent<KeyType>> IEventDispatcher<EventType, CompletableFuture<EventType>> dispatchForAsync(@Nonnull Class<? super EventType> eventClass) {
/* 68 */     return dispatchForAsync(eventClass, null);
/*    */   }
/*    */   
/*    */   <KeyType, EventType extends IAsyncEvent<KeyType>> IEventDispatcher<EventType, CompletableFuture<EventType>> dispatchForAsync(@Nonnull Class<? super EventType> paramClass, @Nullable KeyType paramKeyType);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\IEventBus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */