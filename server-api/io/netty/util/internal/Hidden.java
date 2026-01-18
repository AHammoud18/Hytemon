/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocalThread;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import reactor.blockhound.BlockHound;
/*     */ import reactor.blockhound.integration.BlockHoundIntegration;
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
/*     */ class Hidden
/*     */ {
/*     */   public static final class NettyBlockHoundIntegration
/*     */     implements BlockHoundIntegration
/*     */   {
/*     */     public void applyTo(BlockHound.Builder builder) {
/*  42 */       builder.allowBlockingCallsInside("io.netty.channel.nio.NioEventLoop", "handleLoopException");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  47 */       builder.allowBlockingCallsInside("io.netty.channel.kqueue.KQueueEventLoop", "handleLoopException");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  52 */       builder.allowBlockingCallsInside("io.netty.channel.epoll.EpollEventLoop", "handleLoopException");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  57 */       builder.allowBlockingCallsInside("io.netty.util.HashedWheelTimer", "start");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  62 */       builder.allowBlockingCallsInside("io.netty.util.HashedWheelTimer", "stop");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  67 */       builder.allowBlockingCallsInside("io.netty.util.HashedWheelTimer$Worker", "waitForNextTick");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  72 */       builder.allowBlockingCallsInside("io.netty.util.concurrent.SingleThreadEventExecutor", "confirmShutdown");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       builder.allowBlockingCallsInside("io.netty.buffer.PoolArena", "lock");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       builder.allowBlockingCallsInside("io.netty.buffer.PoolSubpage", "lock");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       builder.allowBlockingCallsInside("io.netty.buffer.PoolChunk", "allocateRun");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       builder.allowBlockingCallsInside("io.netty.buffer.PoolChunk", "free");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       builder.allowBlockingCallsInside("io.netty.buffer.AdaptivePoolingAllocator$1", "initialValue");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       builder.allowBlockingCallsInside("io.netty.buffer.AdaptivePoolingAllocator$1", "onRemoval");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       builder.allowBlockingCallsInside("io.netty.handler.ssl.SslHandler", "handshake");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       builder.allowBlockingCallsInside("io.netty.handler.ssl.SslHandler", "runAllDelegatedTasks");
/*     */ 
/*     */ 
/*     */       
/* 116 */       builder.allowBlockingCallsInside("io.netty.handler.ssl.SslHandler", "runDelegatedTasks");
/*     */ 
/*     */ 
/*     */       
/* 120 */       builder.allowBlockingCallsInside("io.netty.util.concurrent.GlobalEventExecutor", "takeTask");
/*     */ 
/*     */ 
/*     */       
/* 124 */       builder.allowBlockingCallsInside("io.netty.util.concurrent.GlobalEventExecutor", "addTask");
/*     */ 
/*     */ 
/*     */       
/* 128 */       builder.allowBlockingCallsInside("io.netty.util.concurrent.SingleThreadEventExecutor", "pollTaskFrom");
/*     */ 
/*     */ 
/*     */       
/* 132 */       builder.allowBlockingCallsInside("io.netty.util.concurrent.SingleThreadEventExecutor", "takeTask");
/*     */ 
/*     */ 
/*     */       
/* 136 */       builder.allowBlockingCallsInside("io.netty.util.concurrent.SingleThreadEventExecutor", "addTask");
/*     */ 
/*     */ 
/*     */       
/* 140 */       builder.allowBlockingCallsInside("io.netty.handler.ssl.ReferenceCountedOpenSslClientContext$ExtendedTrustManagerVerifyCallback", "verify");
/*     */ 
/*     */ 
/*     */       
/* 144 */       builder.allowBlockingCallsInside("io.netty.handler.ssl.JdkSslContext$Defaults", "init");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       builder.allowBlockingCallsInside("sun.security.ssl.SSLEngineImpl", "unwrap");
/*     */ 
/*     */ 
/*     */       
/* 154 */       builder.allowBlockingCallsInside("sun.security.ssl.SSLEngineImpl", "wrap");
/*     */ 
/*     */ 
/*     */       
/* 158 */       builder.allowBlockingCallsInside("io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider", "parse");
/*     */ 
/*     */ 
/*     */       
/* 162 */       builder.allowBlockingCallsInside("io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider", "parseEtcResolverSearchDomains");
/*     */ 
/*     */ 
/*     */       
/* 166 */       builder.allowBlockingCallsInside("io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider", "parseEtcResolverOptions");
/*     */ 
/*     */ 
/*     */       
/* 170 */       builder.allowBlockingCallsInside("io.netty.resolver.HostsFileEntriesProvider$ParserImpl", "parse");
/*     */ 
/*     */ 
/*     */       
/* 174 */       builder.allowBlockingCallsInside("io.netty.util.NetUtil$SoMaxConnAction", "run");
/*     */ 
/*     */ 
/*     */       
/* 178 */       builder.allowBlockingCallsInside("io.netty.util.internal.ReferenceCountUpdater", "retryRelease0");
/*     */ 
/*     */       
/* 181 */       builder.allowBlockingCallsInside("io.netty.util.internal.PlatformDependent", "createTempFile");
/* 182 */       builder.nonBlockingThreadPredicate(new Function<Predicate<Thread>, Predicate<Thread>>()
/*     */           {
/*     */             public Predicate<Thread> apply(final Predicate<Thread> p) {
/* 185 */               return new Predicate<Thread>()
/*     */                 {
/*     */                   public boolean test(Thread thread) {
/* 188 */                     return (p.test(thread) || (thread instanceof FastThreadLocalThread && 
/*     */                       
/* 190 */                       !((FastThreadLocalThread)thread).permitBlockingCalls()));
/*     */                   }
/*     */                 };
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(BlockHoundIntegration o) {
/* 199 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\Hidden.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */