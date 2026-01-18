/*     */ package io.netty.resolver;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetAddress;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ public final class DefaultHostsFileEntriesResolver
/*     */   implements HostsFileEntriesResolver
/*     */ {
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultHostsFileEntriesResolver.class);
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final AtomicLong lastRefresh = new AtomicLong(System.nanoTime());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private static final long DEFAULT_REFRESH_INTERVAL = SystemPropertyUtil.getLong("io.netty.hostsFileRefreshInterval", 0L); private final long refreshInterval; private final HostsFileEntriesProvider.Parser hostsFileParser;
/*     */   
/*     */   static {
/*  52 */     if (logger.isDebugEnabled())
/*  53 */       logger.debug("-Dio.netty.hostsFileRefreshInterval: {}", Long.valueOf(DEFAULT_REFRESH_INTERVAL)); 
/*     */   }
/*     */   private volatile Map<String, List<InetAddress>> inet4Entries; private volatile Map<String, List<InetAddress>> inet6Entries;
/*     */   
/*     */   public DefaultHostsFileEntriesResolver() {
/*  58 */     this(HostsFileEntriesProvider.parser(), DEFAULT_REFRESH_INTERVAL);
/*     */   }
/*     */ 
/*     */   
/*     */   DefaultHostsFileEntriesResolver(HostsFileEntriesProvider.Parser hostsFileParser, long refreshInterval) {
/*  63 */     this.hostsFileParser = hostsFileParser;
/*  64 */     this.refreshInterval = ObjectUtil.checkPositiveOrZero(refreshInterval, "refreshInterval");
/*  65 */     HostsFileEntriesProvider entries = parseEntries(hostsFileParser);
/*  66 */     this.inet4Entries = entries.ipv4Entries();
/*  67 */     this.inet6Entries = entries.ipv6Entries();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress address(String inetHost, ResolvedAddressTypes resolvedAddressTypes) {
/*  72 */     return firstAddress(addresses(inetHost, resolvedAddressTypes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<InetAddress> addresses(String inetHost, ResolvedAddressTypes resolvedAddressTypes) {
/*     */     List<InetAddress> allInet4Addresses, allInet6Addresses;
/*  84 */     String normalized = normalize(inetHost);
/*  85 */     ensureHostsFileEntriesAreFresh();
/*     */     
/*  87 */     switch (resolvedAddressTypes) {
/*     */       case IPV4_ONLY:
/*  89 */         return this.inet4Entries.get(normalized);
/*     */       case IPV6_ONLY:
/*  91 */         return this.inet6Entries.get(normalized);
/*     */       case IPV4_PREFERRED:
/*  93 */         allInet4Addresses = this.inet4Entries.get(normalized);
/*  94 */         return (allInet4Addresses != null) ? allAddresses(allInet4Addresses, this.inet6Entries.get(normalized)) : 
/*  95 */           this.inet6Entries.get(normalized);
/*     */       case IPV6_PREFERRED:
/*  97 */         allInet6Addresses = this.inet6Entries.get(normalized);
/*  98 */         return (allInet6Addresses != null) ? allAddresses(allInet6Addresses, this.inet4Entries.get(normalized)) : 
/*  99 */           this.inet4Entries.get(normalized);
/*     */     } 
/* 101 */     throw new IllegalArgumentException("Unknown ResolvedAddressTypes " + resolvedAddressTypes);
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureHostsFileEntriesAreFresh() {
/* 106 */     long interval = this.refreshInterval;
/* 107 */     if (interval == 0L) {
/*     */       return;
/*     */     }
/* 110 */     long last = this.lastRefresh.get();
/* 111 */     long currentTime = System.nanoTime();
/* 112 */     if (currentTime - last > interval && 
/* 113 */       this.lastRefresh.compareAndSet(last, currentTime)) {
/* 114 */       HostsFileEntriesProvider entries = parseEntries(this.hostsFileParser);
/* 115 */       this.inet4Entries = entries.ipv4Entries();
/* 116 */       this.inet6Entries = entries.ipv6Entries();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   String normalize(String inetHost) {
/* 123 */     return inetHost.toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   private static List<InetAddress> allAddresses(List<InetAddress> a, List<InetAddress> b) {
/* 127 */     List<InetAddress> result = new ArrayList<>(a.size() + ((b == null) ? 0 : b.size()));
/* 128 */     result.addAll(a);
/* 129 */     if (b != null) {
/* 130 */       result.addAll(b);
/*     */     }
/* 132 */     return result;
/*     */   }
/*     */   
/*     */   private static InetAddress firstAddress(List<InetAddress> addresses) {
/* 136 */     return (addresses != null && !addresses.isEmpty()) ? addresses.get(0) : null;
/*     */   }
/*     */   
/*     */   private static HostsFileEntriesProvider parseEntries(HostsFileEntriesProvider.Parser parser) {
/* 140 */     if (PlatformDependent.isWindows())
/*     */     {
/*     */ 
/*     */       
/* 144 */       return parser.parseSilently(new Charset[] { Charset.defaultCharset(), CharsetUtil.UTF_16, CharsetUtil.UTF_8 });
/*     */     }
/* 146 */     return parser.parseSilently();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\DefaultHostsFileEntriesResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */