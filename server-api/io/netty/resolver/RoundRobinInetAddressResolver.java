/*     */ package io.netty.resolver;
/*     */ 
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
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
/*     */ public class RoundRobinInetAddressResolver
/*     */   extends InetNameResolver
/*     */ {
/*     */   private final NameResolver<InetAddress> nameResolver;
/*     */   
/*     */   public RoundRobinInetAddressResolver(EventExecutor executor, NameResolver<InetAddress> nameResolver) {
/*  46 */     super(executor);
/*  47 */     this.nameResolver = nameResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doResolve(final String inetHost, final Promise<InetAddress> promise) throws Exception {
/*  55 */     this.nameResolver.resolveAll(inetHost).addListener((GenericFutureListener)new FutureListener<List<InetAddress>>()
/*     */         {
/*     */           public void operationComplete(Future<List<InetAddress>> future) throws Exception {
/*  58 */             if (future.isSuccess()) {
/*  59 */               List<InetAddress> inetAddresses = (List<InetAddress>)future.getNow();
/*  60 */               int numAddresses = inetAddresses.size();
/*  61 */               if (numAddresses > 0) {
/*     */ 
/*     */                 
/*  64 */                 promise.setSuccess(inetAddresses.get(RoundRobinInetAddressResolver.randomIndex(numAddresses)));
/*     */               } else {
/*  66 */                 promise.setFailure(new UnknownHostException(inetHost));
/*     */               } 
/*     */             } else {
/*  69 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doResolveAll(String inetHost, final Promise<List<InetAddress>> promise) throws Exception {
/*  77 */     this.nameResolver.resolveAll(inetHost).addListener((GenericFutureListener)new FutureListener<List<InetAddress>>()
/*     */         {
/*     */           public void operationComplete(Future<List<InetAddress>> future) throws Exception {
/*  80 */             if (future.isSuccess()) {
/*  81 */               List<InetAddress> inetAddresses = (List<InetAddress>)future.getNow();
/*  82 */               if (!inetAddresses.isEmpty()) {
/*     */                 
/*  84 */                 List<InetAddress> result = new ArrayList<>(inetAddresses);
/*     */                 
/*  86 */                 Collections.rotate(result, RoundRobinInetAddressResolver.randomIndex(inetAddresses.size()));
/*  87 */                 promise.setSuccess(result);
/*     */               } else {
/*  89 */                 promise.setSuccess(inetAddresses);
/*     */               } 
/*     */             } else {
/*  92 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static int randomIndex(int numAddresses) {
/*  99 */     return (numAddresses == 1) ? 0 : ThreadLocalRandom.current().nextInt(numAddresses);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 104 */     this.nameResolver.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\RoundRobinInetAddressResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */