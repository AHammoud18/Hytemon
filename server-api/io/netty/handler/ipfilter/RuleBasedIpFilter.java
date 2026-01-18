/*    */ package io.netty.handler.ipfilter;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.ArrayList;
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
/*    */ @Sharable
/*    */ public class RuleBasedIpFilter
/*    */   extends AbstractRemoteAddressFilter<InetSocketAddress>
/*    */ {
/*    */   private final boolean acceptIfNotFound;
/*    */   private final List<IpFilterRule> rules;
/*    */   
/*    */   public RuleBasedIpFilter(IpFilterRule... rules) {
/* 58 */     this(true, rules);
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
/*    */   public RuleBasedIpFilter(boolean acceptIfNotFound, IpFilterRule... rules) {
/* 70 */     ObjectUtil.checkNotNull(rules, "rules");
/*    */     
/* 72 */     this.acceptIfNotFound = acceptIfNotFound;
/* 73 */     this.rules = new ArrayList<>(rules.length);
/*    */     
/* 75 */     for (IpFilterRule rule : rules) {
/* 76 */       if (rule != null) {
/* 77 */         this.rules.add(rule);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
/* 84 */     for (IpFilterRule rule : this.rules) {
/* 85 */       if (rule.matches(remoteAddress)) {
/* 86 */         return (rule.ruleType() == IpFilterRuleType.ACCEPT);
/*    */       }
/*    */     } 
/*    */     
/* 90 */     return this.acceptIfNotFound;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ipfilter\RuleBasedIpFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */