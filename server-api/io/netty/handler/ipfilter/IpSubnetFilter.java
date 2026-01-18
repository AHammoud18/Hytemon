/*     */ package io.netty.handler.ipfilter;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ @Sharable
/*     */ public class IpSubnetFilter
/*     */   extends AbstractRemoteAddressFilter<InetSocketAddress>
/*     */ {
/*     */   private final boolean acceptIfNotFound;
/*     */   private final IpSubnetFilterRule[] ipv4Rules;
/*     */   private final IpSubnetFilterRule[] ipv6Rules;
/*     */   private final IpFilterRuleType ipFilterRuleTypeIPv4;
/*     */   private final IpFilterRuleType ipFilterRuleTypeIPv6;
/*     */   
/*     */   public IpSubnetFilter(IpSubnetFilterRule... rules) {
/*  70 */     this(true, Arrays.asList((IpSubnetFilterRule[])ObjectUtil.checkNotNull(rules, "rules")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IpSubnetFilter(boolean acceptIfNotFound, IpSubnetFilterRule... rules) {
/*  81 */     this(acceptIfNotFound, Arrays.asList((IpSubnetFilterRule[])ObjectUtil.checkNotNull(rules, "rules")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IpSubnetFilter(List<IpSubnetFilterRule> rules) {
/*  91 */     this(true, rules);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IpSubnetFilter(boolean acceptIfNotFound, List<IpSubnetFilterRule> rules) {
/* 102 */     ObjectUtil.checkNotNull(rules, "rules");
/* 103 */     this.acceptIfNotFound = acceptIfNotFound;
/*     */     
/* 105 */     int numAcceptIPv4 = 0;
/* 106 */     int numRejectIPv4 = 0;
/* 107 */     int numAcceptIPv6 = 0;
/* 108 */     int numRejectIPv6 = 0;
/*     */     
/* 110 */     List<IpSubnetFilterRule> unsortedIPv4Rules = new ArrayList<>();
/* 111 */     List<IpSubnetFilterRule> unsortedIPv6Rules = new ArrayList<>();
/*     */ 
/*     */     
/* 114 */     for (IpSubnetFilterRule ipSubnetFilterRule : rules) {
/* 115 */       ObjectUtil.checkNotNull(ipSubnetFilterRule, "rule");
/*     */       
/* 117 */       if (ipSubnetFilterRule.getFilterRule() instanceof IpSubnetFilterRule.Ip4SubnetFilterRule) {
/* 118 */         unsortedIPv4Rules.add(ipSubnetFilterRule);
/*     */         
/* 120 */         if (ipSubnetFilterRule.ruleType() == IpFilterRuleType.ACCEPT) {
/* 121 */           numAcceptIPv4++; continue;
/*     */         } 
/* 123 */         numRejectIPv4++;
/*     */         continue;
/*     */       } 
/* 126 */       unsortedIPv6Rules.add(ipSubnetFilterRule);
/*     */       
/* 128 */       if (ipSubnetFilterRule.ruleType() == IpFilterRuleType.ACCEPT) {
/* 129 */         numAcceptIPv6++; continue;
/*     */       } 
/* 131 */       numRejectIPv6++;
/*     */     } 
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
/* 147 */     if (numAcceptIPv4 == 0 && numRejectIPv4 > 0) {
/* 148 */       this.ipFilterRuleTypeIPv4 = IpFilterRuleType.REJECT;
/* 149 */     } else if (numAcceptIPv4 > 0 && numRejectIPv4 == 0) {
/* 150 */       this.ipFilterRuleTypeIPv4 = IpFilterRuleType.ACCEPT;
/*     */     } else {
/* 152 */       this.ipFilterRuleTypeIPv4 = null;
/*     */     } 
/*     */     
/* 155 */     if (numAcceptIPv6 == 0 && numRejectIPv6 > 0) {
/* 156 */       this.ipFilterRuleTypeIPv6 = IpFilterRuleType.REJECT;
/* 157 */     } else if (numAcceptIPv6 > 0 && numRejectIPv6 == 0) {
/* 158 */       this.ipFilterRuleTypeIPv6 = IpFilterRuleType.ACCEPT;
/*     */     } else {
/* 160 */       this.ipFilterRuleTypeIPv6 = null;
/*     */     } 
/*     */     
/* 163 */     this.ipv4Rules = unsortedIPv4Rules.isEmpty() ? null : sortAndFilter(unsortedIPv4Rules);
/* 164 */     this.ipv6Rules = unsortedIPv6Rules.isEmpty() ? null : sortAndFilter(unsortedIPv6Rules);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) {
/* 169 */     if (this.ipv4Rules != null && remoteAddress.getAddress() instanceof java.net.Inet4Address) {
/* 170 */       int indexOf = Arrays.binarySearch((Object[])this.ipv4Rules, remoteAddress, IpSubnetFilterRuleComparator.INSTANCE);
/* 171 */       if (indexOf >= 0) {
/* 172 */         if (this.ipFilterRuleTypeIPv4 == null) {
/* 173 */           return (this.ipv4Rules[indexOf].ruleType() == IpFilterRuleType.ACCEPT);
/*     */         }
/* 175 */         return (this.ipFilterRuleTypeIPv4 == IpFilterRuleType.ACCEPT);
/*     */       }
/*     */     
/* 178 */     } else if (this.ipv6Rules != null) {
/* 179 */       int indexOf = Arrays.binarySearch((Object[])this.ipv6Rules, remoteAddress, IpSubnetFilterRuleComparator.INSTANCE);
/* 180 */       if (indexOf >= 0) {
/* 181 */         if (this.ipFilterRuleTypeIPv6 == null) {
/* 182 */           return (this.ipv6Rules[indexOf].ruleType() == IpFilterRuleType.ACCEPT);
/*     */         }
/* 184 */         return (this.ipFilterRuleTypeIPv6 == IpFilterRuleType.ACCEPT);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 189 */     return this.acceptIfNotFound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IpSubnetFilterRule[] sortAndFilter(List<IpSubnetFilterRule> rules) {
/* 201 */     Collections.sort(rules);
/* 202 */     Iterator<IpSubnetFilterRule> iterator = rules.iterator();
/* 203 */     List<IpSubnetFilterRule> toKeep = new ArrayList<>();
/*     */     
/* 205 */     IpSubnetFilterRule parentRule = iterator.hasNext() ? iterator.next() : null;
/* 206 */     if (parentRule != null) {
/* 207 */       toKeep.add(parentRule);
/*     */     }
/*     */     
/* 210 */     while (iterator.hasNext()) {
/*     */ 
/*     */       
/* 213 */       IpSubnetFilterRule childRule = iterator.next();
/*     */ 
/*     */ 
/*     */       
/* 217 */       if (!parentRule.matches(new InetSocketAddress(childRule.getIpAddress(), 1))) {
/* 218 */         toKeep.add(childRule);
/*     */         
/* 220 */         parentRule = childRule;
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     return toKeep.<IpSubnetFilterRule>toArray(new IpSubnetFilterRule[0]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ipfilter\IpSubnetFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */