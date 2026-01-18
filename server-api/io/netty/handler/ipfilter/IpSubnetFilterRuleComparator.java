/*    */ package io.netty.handler.ipfilter;
/*    */ 
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.Comparator;
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
/*    */ final class IpSubnetFilterRuleComparator
/*    */   implements Comparator<Object>
/*    */ {
/* 26 */   static final IpSubnetFilterRuleComparator INSTANCE = new IpSubnetFilterRuleComparator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 34 */     return ((IpSubnetFilterRule)o1).compareTo((InetSocketAddress)o2);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ipfilter\IpSubnetFilterRuleComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */