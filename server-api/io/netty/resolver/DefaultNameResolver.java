/*    */ package io.netty.resolver;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.Promise;
/*    */ import io.netty.util.internal.SocketUtils;
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public class DefaultNameResolver
/*    */   extends InetNameResolver
/*    */ {
/*    */   public DefaultNameResolver(EventExecutor executor) {
/* 35 */     super(executor);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doResolve(String inetHost, Promise<InetAddress> promise) throws Exception {
/*    */     try {
/* 41 */       promise.setSuccess(SocketUtils.addressByName(inetHost));
/* 42 */     } catch (UnknownHostException e) {
/* 43 */       promise.setFailure(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doResolveAll(String inetHost, Promise<List<InetAddress>> promise) throws Exception {
/*    */     try {
/* 50 */       promise.setSuccess(Arrays.asList(SocketUtils.allAddressesByName(inetHost)));
/* 51 */     } catch (UnknownHostException e) {
/* 52 */       promise.setFailure(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\DefaultNameResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */