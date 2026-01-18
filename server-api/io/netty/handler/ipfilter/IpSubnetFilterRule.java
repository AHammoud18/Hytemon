/*     */ package io.netty.handler.ipfilter;
/*     */ 
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import java.math.BigInteger;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
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
/*     */ public final class IpSubnetFilterRule
/*     */   implements IpFilterRule, Comparable<IpSubnetFilterRule>
/*     */ {
/*     */   private final IpFilterRule filterRule;
/*     */   private final String ipAddress;
/*     */   
/*     */   public IpSubnetFilterRule(String ipAddressWithCidr, IpFilterRuleType ruleType) {
/*     */     try {
/*  46 */       String[] ipAndCidr = ipAddressWithCidr.split("/");
/*  47 */       if (ipAndCidr.length != 2) {
/*  48 */         throw new IllegalArgumentException("ipAddressWithCidr: " + ipAddressWithCidr + " (expected: \"<ip-address>/<mask-size>\")");
/*     */       }
/*     */ 
/*     */       
/*  52 */       this.ipAddress = ipAndCidr[0];
/*  53 */       int cidrPrefix = Integer.parseInt(ipAndCidr[1]);
/*  54 */       this.filterRule = selectFilterRule(SocketUtils.addressByName(this.ipAddress), cidrPrefix, ruleType);
/*  55 */     } catch (UnknownHostException e) {
/*  56 */       throw new IllegalArgumentException("ipAddressWithCidr", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IpSubnetFilterRule(String ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
/*     */     try {
/*  69 */       this.ipAddress = ipAddress;
/*  70 */       this.filterRule = selectFilterRule(SocketUtils.addressByName(ipAddress), cidrPrefix, ruleType);
/*  71 */     } catch (UnknownHostException e) {
/*  72 */       throw new IllegalArgumentException("ipAddress", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IpSubnetFilterRule(InetAddress ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
/*  84 */     this.ipAddress = ipAddress.getHostAddress();
/*  85 */     this.filterRule = selectFilterRule(ipAddress, cidrPrefix, ruleType);
/*     */   }
/*     */   
/*     */   private static IpFilterRule selectFilterRule(InetAddress ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
/*  89 */     ObjectUtil.checkNotNull(ipAddress, "ipAddress");
/*  90 */     ObjectUtil.checkNotNull(ruleType, "ruleType");
/*     */     
/*  92 */     if (ipAddress instanceof Inet4Address)
/*  93 */       return new Ip4SubnetFilterRule((Inet4Address)ipAddress, cidrPrefix, ruleType); 
/*  94 */     if (ipAddress instanceof Inet6Address) {
/*  95 */       return new Ip6SubnetFilterRule((Inet6Address)ipAddress, cidrPrefix, ruleType);
/*     */     }
/*  97 */     throw new IllegalArgumentException("Only IPv4 and IPv6 addresses are supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(InetSocketAddress remoteAddress) {
/* 103 */     return this.filterRule.matches(remoteAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public IpFilterRuleType ruleType() {
/* 108 */     return this.filterRule.ruleType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getIpAddress() {
/* 115 */     return this.ipAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IpFilterRule getFilterRule() {
/* 122 */     return this.filterRule;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(IpSubnetFilterRule ipSubnetFilterRule) {
/* 127 */     if (this.filterRule instanceof Ip4SubnetFilterRule) {
/* 128 */       return compareInt(((Ip4SubnetFilterRule)this.filterRule).networkAddress, ((Ip4SubnetFilterRule)ipSubnetFilterRule.filterRule)
/* 129 */           .networkAddress);
/*     */     }
/* 131 */     return ((Ip6SubnetFilterRule)this.filterRule).networkAddress
/* 132 */       .compareTo(((Ip6SubnetFilterRule)ipSubnetFilterRule.filterRule).networkAddress);
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
/*     */   int compareTo(InetSocketAddress inetSocketAddress) {
/* 144 */     if (this.filterRule instanceof Ip4SubnetFilterRule) {
/* 145 */       Ip4SubnetFilterRule ip4SubnetFilterRule = (Ip4SubnetFilterRule)this.filterRule;
/* 146 */       return compareInt(ip4SubnetFilterRule.networkAddress, NetUtil.ipv4AddressToInt((Inet4Address)inetSocketAddress
/* 147 */             .getAddress()) & ip4SubnetFilterRule.subnetMask);
/*     */     } 
/* 149 */     Ip6SubnetFilterRule ip6SubnetFilterRule = (Ip6SubnetFilterRule)this.filterRule;
/* 150 */     return ip6SubnetFilterRule.networkAddress
/* 151 */       .compareTo(Ip6SubnetFilterRule.ipToInt((Inet6Address)inetSocketAddress.getAddress())
/* 152 */         .and(ip6SubnetFilterRule.networkAddress));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int compareInt(int x, int y) {
/* 160 */     return (x < y) ? -1 : ((x == y) ? 0 : 1);
/*     */   }
/*     */   
/*     */   static final class Ip4SubnetFilterRule
/*     */     implements IpFilterRule {
/*     */     private final int networkAddress;
/*     */     private final int subnetMask;
/*     */     private final IpFilterRuleType ruleType;
/*     */     
/*     */     private Ip4SubnetFilterRule(Inet4Address ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
/* 170 */       if (cidrPrefix < 0 || cidrPrefix > 32) {
/* 171 */         throw new IllegalArgumentException(String.format("IPv4 requires the subnet prefix to be in range of [0,32]. The prefix was: %d", new Object[] {
/* 172 */                 Integer.valueOf(cidrPrefix)
/*     */               }));
/*     */       }
/* 175 */       this.subnetMask = prefixToSubnetMask(cidrPrefix);
/* 176 */       this.networkAddress = NetUtil.ipv4AddressToInt(ipAddress) & this.subnetMask;
/* 177 */       this.ruleType = ruleType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(InetSocketAddress remoteAddress) {
/* 182 */       InetAddress inetAddress = remoteAddress.getAddress();
/* 183 */       if (inetAddress instanceof Inet4Address) {
/* 184 */         int ipAddress = NetUtil.ipv4AddressToInt((Inet4Address)inetAddress);
/* 185 */         return ((ipAddress & this.subnetMask) == this.networkAddress);
/*     */       } 
/* 187 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public IpFilterRuleType ruleType() {
/* 192 */       return this.ruleType;
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
/*     */     private static int prefixToSubnetMask(int cidrPrefix) {
/* 206 */       return (int)(-1L << 32 - cidrPrefix);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Ip6SubnetFilterRule
/*     */     implements IpFilterRule {
/* 212 */     private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1L);
/*     */     
/*     */     private final BigInteger networkAddress;
/*     */     private final BigInteger subnetMask;
/*     */     private final IpFilterRuleType ruleType;
/*     */     
/*     */     private Ip6SubnetFilterRule(Inet6Address ipAddress, int cidrPrefix, IpFilterRuleType ruleType) {
/* 219 */       if (cidrPrefix < 0 || cidrPrefix > 128) {
/* 220 */         throw new IllegalArgumentException(String.format("IPv6 requires the subnet prefix to be in range of [0,128]. The prefix was: %d", new Object[] {
/* 221 */                 Integer.valueOf(cidrPrefix)
/*     */               }));
/*     */       }
/* 224 */       this.subnetMask = prefixToSubnetMask(cidrPrefix);
/* 225 */       this.networkAddress = ipToInt(ipAddress).and(this.subnetMask);
/* 226 */       this.ruleType = ruleType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(InetSocketAddress remoteAddress) {
/* 231 */       InetAddress inetAddress = remoteAddress.getAddress();
/* 232 */       if (inetAddress instanceof Inet6Address) {
/* 233 */         BigInteger ipAddress = ipToInt((Inet6Address)inetAddress);
/* 234 */         return (ipAddress.and(this.subnetMask).equals(this.subnetMask) || ipAddress.and(this.subnetMask).equals(this.networkAddress));
/*     */       } 
/* 236 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public IpFilterRuleType ruleType() {
/* 241 */       return this.ruleType;
/*     */     }
/*     */     
/*     */     private static BigInteger ipToInt(Inet6Address ipAddress) {
/* 245 */       byte[] octets = ipAddress.getAddress();
/* 246 */       assert octets.length == 16;
/*     */       
/* 248 */       return new BigInteger(octets);
/*     */     }
/*     */     
/*     */     private static BigInteger prefixToSubnetMask(int cidrPrefix) {
/* 252 */       return MINUS_ONE.shiftLeft(128 - cidrPrefix);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ipfilter\IpSubnetFilterRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */