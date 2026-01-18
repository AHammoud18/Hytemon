/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.AutoClosableReentrantLock;
/*    */ import io.sentry.util.Objects;
/*    */ import java.util.Deque;
/*    */ import java.util.Iterator;
/*    */ import java.util.concurrent.LinkedBlockingDeque;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ final class Stack
/*    */ {
/*    */   static final class StackItem
/*    */   {
/*    */     private final SentryOptions options;
/*    */     @NotNull
/*    */     private volatile ISentryClient client;
/*    */     @NotNull
/*    */     private volatile IScope scope;
/*    */     
/*    */     StackItem(@NotNull SentryOptions options, @NotNull ISentryClient client, @NotNull IScope scope) {
/* 22 */       this.client = (ISentryClient)Objects.requireNonNull(client, "ISentryClient is required.");
/* 23 */       this.scope = (IScope)Objects.requireNonNull(scope, "Scope is required.");
/* 24 */       this.options = (SentryOptions)Objects.requireNonNull(options, "Options is required");
/*    */     }
/*    */     
/*    */     StackItem(@NotNull StackItem item) {
/* 28 */       this.options = item.options;
/* 29 */       this.client = item.client;
/* 30 */       this.scope = item.scope.clone();
/*    */     }
/*    */     @NotNull
/*    */     public ISentryClient getClient() {
/* 34 */       return this.client;
/*    */     }
/*    */     
/*    */     public void setClient(@NotNull ISentryClient client) {
/* 38 */       this.client = client;
/*    */     }
/*    */     @NotNull
/*    */     public IScope getScope() {
/* 42 */       return this.scope;
/*    */     }
/*    */     @NotNull
/*    */     public SentryOptions getOptions() {
/* 46 */       return this.options;
/*    */     }
/*    */   }
/*    */   @NotNull
/* 50 */   private final Deque<StackItem> items = new LinkedBlockingDeque<>(); @NotNull
/*    */   private final ILogger logger; @NotNull
/* 52 */   private final AutoClosableReentrantLock itemsLock = new AutoClosableReentrantLock();
/*    */   
/*    */   public Stack(@NotNull ILogger logger, @NotNull StackItem rootStackItem) {
/* 55 */     this.logger = (ILogger)Objects.requireNonNull(logger, "logger is required");
/* 56 */     this.items.push((StackItem)Objects.requireNonNull(rootStackItem, "rootStackItem is required"));
/*    */   }
/*    */   
/*    */   public Stack(@NotNull Stack stack) {
/* 60 */     this(stack.logger, new StackItem(stack.items.getLast()));
/* 61 */     Iterator<StackItem> iterator = stack.items.descendingIterator();
/*    */     
/* 63 */     if (iterator.hasNext()) {
/* 64 */       iterator.next();
/*    */     }
/* 66 */     while (iterator.hasNext()) {
/* 67 */       push(new StackItem(iterator.next()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   StackItem peek() {
/* 75 */     return this.items.peek();
/*    */   }
/*    */   
/*    */   void pop() {
/* 79 */     ISentryLifecycleToken ignored = this.itemsLock.acquire(); 
/* 80 */     try { if (this.items.size() != 1) {
/* 81 */         this.items.pop();
/*    */       } else {
/* 83 */         this.logger.log(SentryLevel.WARNING, "Attempt to pop the root scope.", new Object[0]);
/*    */       } 
/* 85 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*    */         try { ignored.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 89 */      } void push(@NotNull StackItem stackItem) { this.items.push(stackItem); }
/*    */ 
/*    */   
/*    */   int size() {
/* 93 */     return this.items.size();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Stack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */