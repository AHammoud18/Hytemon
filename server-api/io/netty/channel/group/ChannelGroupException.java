/*    */ package io.netty.channel.group;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelException;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
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
/*    */ public class ChannelGroupException
/*    */   extends ChannelException
/*    */   implements Iterable<Map.Entry<Channel, Throwable>>
/*    */ {
/*    */   private static final long serialVersionUID = -4093064295562629453L;
/*    */   private final Collection<Map.Entry<Channel, Throwable>> failed;
/*    */   
/*    */   public ChannelGroupException(Collection<Map.Entry<Channel, Throwable>> causes) {
/* 36 */     ObjectUtil.checkNonEmpty(causes, "causes");
/*    */     
/* 38 */     this.failed = Collections.unmodifiableCollection(causes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<Map.Entry<Channel, Throwable>> iterator() {
/* 47 */     return this.failed.iterator();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\group\ChannelGroupException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */