/*     */ package io.netty.util;
/*     */ 
/*     */ import com.oracle.svm.core.annotate.Alias;
/*     */ import com.oracle.svm.core.annotate.InjectAccessors;
/*     */ import com.oracle.svm.core.annotate.TargetClass;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.util.Collection;
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
/*     */ @TargetClass(NetUtil.class)
/*     */ final class NetUtilSubstitutions
/*     */ {
/*     */   @Alias
/*     */   @InjectAccessors(NetUtilLocalhost4Accessor.class)
/*     */   public static Inet4Address LOCALHOST4;
/*     */   @Alias
/*     */   @InjectAccessors(NetUtilLocalhost6Accessor.class)
/*     */   public static Inet6Address LOCALHOST6;
/*     */   @Alias
/*     */   @InjectAccessors(NetUtilLocalhostAccessor.class)
/*     */   public static InetAddress LOCALHOST;
/*     */   @Alias
/*     */   @InjectAccessors(NetUtilNetworkInterfacesAccessor.class)
/*     */   public static Collection<NetworkInterface> NETWORK_INTERFACES;
/*     */   
/*     */   private static final class NetUtilLocalhost4Accessor
/*     */   {
/*     */     static Inet4Address get() {
/*  52 */       return NetUtilSubstitutions.NetUtilLocalhost4LazyHolder.LOCALHOST4;
/*     */     }
/*     */ 
/*     */     
/*     */     static void set(Inet4Address ignored) {}
/*     */   }
/*     */   
/*     */   private static final class NetUtilLocalhost4LazyHolder
/*     */   {
/*  61 */     private static final Inet4Address LOCALHOST4 = NetUtilInitializations.createLocalhost4();
/*     */   }
/*     */   
/*     */   private static final class NetUtilLocalhost6Accessor
/*     */   {
/*     */     static Inet6Address get() {
/*  67 */       return NetUtilSubstitutions.NetUtilLocalhost6LazyHolder.LOCALHOST6;
/*     */     }
/*     */ 
/*     */     
/*     */     static void set(Inet6Address ignored) {}
/*     */   }
/*     */   
/*     */   private static final class NetUtilLocalhost6LazyHolder
/*     */   {
/*  76 */     private static final Inet6Address LOCALHOST6 = NetUtilInitializations.createLocalhost6();
/*     */   }
/*     */   
/*     */   private static final class NetUtilLocalhostAccessor
/*     */   {
/*     */     static InetAddress get() {
/*  82 */       return NetUtilSubstitutions.NetUtilLocalhostLazyHolder.LOCALHOST;
/*     */     }
/*     */ 
/*     */     
/*     */     static void set(InetAddress ignored) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class NetUtilLocalhostLazyHolder
/*     */   {
/*  92 */     private static final InetAddress LOCALHOST = NetUtilInitializations.determineLoopback(NetUtilSubstitutions.NetUtilNetworkInterfacesLazyHolder.NETWORK_INTERFACES, NetUtilSubstitutions.NetUtilLocalhost4LazyHolder
/*  93 */         .LOCALHOST4, NetUtilSubstitutions.NetUtilLocalhost6LazyHolder.LOCALHOST6)
/*  94 */       .address();
/*     */   }
/*     */   
/*     */   private static final class NetUtilNetworkInterfacesAccessor
/*     */   {
/*     */     static Collection<NetworkInterface> get() {
/* 100 */       return NetUtilSubstitutions.NetUtilNetworkInterfacesLazyHolder.NETWORK_INTERFACES;
/*     */     }
/*     */ 
/*     */     
/*     */     static void set(Collection<NetworkInterface> ignored) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class NetUtilNetworkInterfacesLazyHolder
/*     */   {
/* 110 */     private static final Collection<NetworkInterface> NETWORK_INTERFACES = NetUtilInitializations.networkInterfaces();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\NetUtilSubstitutions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */