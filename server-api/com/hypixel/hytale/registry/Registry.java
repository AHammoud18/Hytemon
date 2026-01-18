/*     */ package com.hypixel.hytale.registry;
/*     */ 
/*     */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Registry<T extends Registration>
/*     */ {
/*     */   @Nonnull
/*     */   private final BooleanSupplier precondition;
/*     */   private final String preconditionMessage;
/*     */   private final RegistrationWrapFunction<T> wrappingFunction;
/*     */   @Nonnull
/*     */   private final List<BooleanConsumer> registrations;
/*     */   @Nonnull
/*     */   private final List<BooleanConsumer> unmodifiableRegistrations;
/*     */   private boolean enabled = true;
/*     */   
/*     */   protected Registry(@Nonnull List<BooleanConsumer> registrations, @Nonnull BooleanSupplier precondition, String preconditionMessage, @Nonnull RegistrationWrapFunction<T> wrappingFunction) {
/*  62 */     this.registrations = registrations;
/*  63 */     this.unmodifiableRegistrations = Collections.unmodifiableList(registrations);
/*  64 */     this.precondition = precondition;
/*  65 */     this.preconditionMessage = preconditionMessage;
/*  66 */     this.wrappingFunction = wrappingFunction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkPrecondition() {
/*  73 */     if (!this.precondition.getAsBoolean()) {
/*  74 */       throw new IllegalStateException(this.preconditionMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  82 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enable() {
/*  89 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/*  96 */     this.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T register(@Nonnull T registration) {
/* 106 */     if (!this.enabled) {
/* 107 */       registration.unregister();
/* 108 */       throw new IllegalStateException("Registry is not enabled!");
/*     */     } 
/*     */     
/* 111 */     BooleanConsumer reg = v -> registration.unregister();
/* 112 */     this.registrations.add(reg);
/* 113 */     return this.wrappingFunction.wrap(registration, () -> (this.enabled || registration.isRegistered()), () -> {
/*     */           this.registrations.remove(reg);
/*     */           registration.unregister();
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<BooleanConsumer> getRegistrations() {
/* 124 */     return this.unmodifiableRegistrations;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 130 */     return "Registry{registrations.size()=" + this.registrations
/* 131 */       .size() + "}";
/*     */   }
/*     */   
/*     */   public static interface RegistrationWrapFunction<T extends Registration> {
/*     */     T wrap(T param1T, BooleanSupplier param1BooleanSupplier, Runnable param1Runnable);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\registry\Registry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */