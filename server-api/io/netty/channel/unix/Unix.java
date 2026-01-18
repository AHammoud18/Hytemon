/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.util.internal.ClassInitializerUtil;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.PortUnreachableException;
/*    */ import java.nio.channels.ClosedChannelException;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*    */ public final class Unix
/*    */ {
/* 32 */   private static final AtomicBoolean registered = new AtomicBoolean();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 40 */     ClassInitializerUtil.tryLoadClasses(Unix.class, new Class[] { OutOfMemoryError.class, RuntimeException.class, ClosedChannelException.class, IOException.class, PortUnreachableException.class, DatagramSocketAddress.class, DomainDatagramSocketAddress.class, InetSocketAddress.class });
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
/*    */ 
/*    */   
/*    */   public static synchronized void registerInternal(Runnable registerTask) {
/* 57 */     registerTask.run();
/* 58 */     Socket.initialize();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static boolean isAvailable() {
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static void ensureAvailability() {
/* 78 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static Throwable unavailabilityCause() {
/* 89 */     return new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\Unix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */