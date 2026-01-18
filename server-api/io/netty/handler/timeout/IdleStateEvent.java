/*    */ package io.netty.handler.timeout;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ public class IdleStateEvent
/*    */ {
/* 26 */   public static final IdleStateEvent FIRST_READER_IDLE_STATE_EVENT = new DefaultIdleStateEvent(IdleState.READER_IDLE, true);
/*    */   
/* 28 */   public static final IdleStateEvent READER_IDLE_STATE_EVENT = new DefaultIdleStateEvent(IdleState.READER_IDLE, false);
/*    */   
/* 30 */   public static final IdleStateEvent FIRST_WRITER_IDLE_STATE_EVENT = new DefaultIdleStateEvent(IdleState.WRITER_IDLE, true);
/*    */   
/* 32 */   public static final IdleStateEvent WRITER_IDLE_STATE_EVENT = new DefaultIdleStateEvent(IdleState.WRITER_IDLE, false);
/*    */   
/* 34 */   public static final IdleStateEvent FIRST_ALL_IDLE_STATE_EVENT = new DefaultIdleStateEvent(IdleState.ALL_IDLE, true);
/*    */   
/* 36 */   public static final IdleStateEvent ALL_IDLE_STATE_EVENT = new DefaultIdleStateEvent(IdleState.ALL_IDLE, false);
/*    */ 
/*    */ 
/*    */   
/*    */   private final IdleState state;
/*    */ 
/*    */ 
/*    */   
/*    */   private final boolean first;
/*    */ 
/*    */ 
/*    */   
/*    */   protected IdleStateEvent(IdleState state, boolean first) {
/* 49 */     this.state = (IdleState)ObjectUtil.checkNotNull(state, "state");
/* 50 */     this.first = first;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IdleState state() {
/* 57 */     return this.state;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFirst() {
/* 64 */     return this.first;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return StringUtil.simpleClassName(this) + '(' + this.state + (this.first ? ", first" : "") + ')';
/*    */   }
/*    */   
/*    */   private static final class DefaultIdleStateEvent extends IdleStateEvent {
/*    */     private final String representation;
/*    */     
/*    */     DefaultIdleStateEvent(IdleState state, boolean first) {
/* 76 */       super(state, first);
/* 77 */       this.representation = "IdleStateEvent(" + state + (first ? ", first" : "") + ')';
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 82 */       return this.representation;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\timeout\IdleStateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */