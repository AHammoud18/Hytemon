/*     */ package io.netty.channel.group;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
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
/*     */ final class VoidChannelGroupFuture
/*     */   implements ChannelGroupFuture
/*     */ {
/*  30 */   private static final Iterator<ChannelFuture> EMPTY = Collections.<ChannelFuture>emptyList().iterator();
/*     */   private final ChannelGroup group;
/*     */   
/*     */   VoidChannelGroupFuture(ChannelGroup group) {
/*  34 */     this.group = group;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroup group() {
/*  39 */     return this.group;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture find(Channel channel) {
/*  44 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuccess() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupException cause() {
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPartialSuccess() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPartialFailure() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
/*  69 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
/*  74 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
/*  79 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
/*  84 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture await() {
/*  89 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture awaitUninterruptibly() {
/*  94 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture syncUninterruptibly() {
/*  99 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture sync() {
/* 104 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<ChannelFuture> iterator() {
/* 109 */     return EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancellable() {
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeout, TimeUnit unit) {
/* 119 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeoutMillis) {
/* 124 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
/* 129 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeoutMillis) {
/* 134 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public Void getNow() {
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Void get() {
/* 164 */     throw reject();
/*     */   }
/*     */ 
/*     */   
/*     */   public Void get(long timeout, TimeUnit unit) {
/* 169 */     throw reject();
/*     */   }
/*     */   
/*     */   private static RuntimeException reject() {
/* 173 */     return new IllegalStateException("void future");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\group\VoidChannelGroupFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */