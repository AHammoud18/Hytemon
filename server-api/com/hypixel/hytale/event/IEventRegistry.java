package com.hypixel.hytale.event;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IEventRegistry {
  @Nullable
  <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(@Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(@Nonnull EventPriority paramEventPriority, @Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(short paramShort, @Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(@Nonnull Class<? super EventType> paramClass, @Nonnull KeyType paramKeyType, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(@Nonnull EventPriority paramEventPriority, @Nonnull Class<? super EventType> paramClass, @Nonnull KeyType paramKeyType, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(short paramShort, @Nonnull Class<? super EventType> paramClass, @Nonnull KeyType paramKeyType, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(@Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(@Nonnull EventPriority paramEventPriority, @Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(short paramShort, Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(@Nonnull Class<? super EventType> paramClass, @Nonnull KeyType paramKeyType, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(@Nonnull EventPriority paramEventPriority, Class<? super EventType> paramClass, @Nonnull KeyType paramKeyType, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(short paramShort, @Nonnull Class<? super EventType> paramClass, @Nonnull KeyType paramKeyType, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(@Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(@Nonnull EventPriority paramEventPriority, @Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(short paramShort, @Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(@Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(@Nonnull EventPriority paramEventPriority, @Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(short paramShort, @Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(@Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(@Nonnull EventPriority paramEventPriority, @Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(short paramShort, @Nonnull Class<? super EventType> paramClass, @Nonnull Consumer<EventType> paramConsumer);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(@Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(@Nonnull EventPriority paramEventPriority, @Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
  
  @Nullable
  <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(short paramShort, @Nonnull Class<? super EventType> paramClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> paramFunction);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\IEventRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */