/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.nio.channels.ClosedChannelException;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ public final class QuicClosedChannelException
/*    */   extends ClosedChannelException
/*    */ {
/*    */   private final QuicConnectionCloseEvent event;
/*    */   
/*    */   QuicClosedChannelException(@Nullable QuicConnectionCloseEvent event) {
/* 31 */     this.event = event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public QuicConnectionCloseEvent event() {
/* 41 */     return this.event;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicClosedChannelException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */