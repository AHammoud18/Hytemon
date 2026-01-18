/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.collection.IntObjectHashMap;
/*     */ import io.netty.util.internal.ClassInitializerUtil;
/*     */ import io.netty.util.internal.NativeLibraryLoader;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLHandshakeException;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ final class Quiche
/*     */ {
/*  36 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Quiche.class);
/*  37 */   private static final boolean TRACE_LOGGING_ENABLED = logger.isTraceEnabled();
/*  38 */   private static final IntObjectHashMap<QuicTransportErrorHolder> ERROR_MAPPINGS = new IntObjectHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  46 */     ClassInitializerUtil.tryLoadClasses(Quiche.class, new Class[] { byte[].class, String.class, BoringSSLCertificateCallback.class, BoringSSLCertificateVerifyCallback.class, BoringSSLHandshakeCompleteCallback.class, QuicheLogger.class });
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
/*     */     try {
/*  58 */       quiche_version();
/*  59 */     } catch (UnsatisfiedLinkError ignore) {
/*     */       
/*  61 */       loadNativeLibrary();
/*     */     } 
/*     */ 
/*     */     
/*  65 */     if (TRACE_LOGGING_ENABLED) {
/*  66 */       quiche_enable_debug_logging(new QuicheLogger(logger));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadNativeLibrary() {
/*  73 */     String libName = "netty_quiche42";
/*  74 */     ClassLoader cl = PlatformDependent.getClassLoader(Quiche.class);
/*     */     
/*  76 */     if (!PlatformDependent.isAndroid())
/*     */     {
/*  78 */       libName = libName + '_' + PlatformDependent.normalizedOs() + '_' + PlatformDependent.normalizedArch();
/*     */     }
/*     */     
/*     */     try {
/*  82 */       NativeLibraryLoader.load(libName, cl);
/*  83 */     } catch (UnsatisfiedLinkError e) {
/*  84 */       logger.debug("Failed to load {}", libName, e);
/*  85 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*  89 */   static final short AF_INET = (short)QuicheNativeStaticallyReferencedJniMethods.afInet();
/*  90 */   static final short AF_INET6 = (short)QuicheNativeStaticallyReferencedJniMethods.afInet6();
/*  91 */   static final int SIZEOF_SOCKADDR_STORAGE = QuicheNativeStaticallyReferencedJniMethods.sizeofSockaddrStorage();
/*  92 */   static final int SIZEOF_SOCKADDR_IN = QuicheNativeStaticallyReferencedJniMethods.sizeofSockaddrIn();
/*  93 */   static final int SIZEOF_SOCKADDR_IN6 = QuicheNativeStaticallyReferencedJniMethods.sizeofSockaddrIn6();
/*     */   
/*  95 */   static final int SOCKADDR_IN_OFFSETOF_SIN_FAMILY = QuicheNativeStaticallyReferencedJniMethods.sockaddrInOffsetofSinFamily();
/*     */   
/*  97 */   static final int SOCKADDR_IN_OFFSETOF_SIN_PORT = QuicheNativeStaticallyReferencedJniMethods.sockaddrInOffsetofSinPort();
/*     */   
/*  99 */   static final int SOCKADDR_IN_OFFSETOF_SIN_ADDR = QuicheNativeStaticallyReferencedJniMethods.sockaddrInOffsetofSinAddr();
/* 100 */   static final int IN_ADDRESS_OFFSETOF_S_ADDR = QuicheNativeStaticallyReferencedJniMethods.inAddressOffsetofSAddr();
/*     */   
/* 102 */   static final int SOCKADDR_IN6_OFFSETOF_SIN6_FAMILY = QuicheNativeStaticallyReferencedJniMethods.sockaddrIn6OffsetofSin6Family();
/*     */   
/* 104 */   static final int SOCKADDR_IN6_OFFSETOF_SIN6_PORT = QuicheNativeStaticallyReferencedJniMethods.sockaddrIn6OffsetofSin6Port();
/*     */   
/* 106 */   static final int SOCKADDR_IN6_OFFSETOF_SIN6_FLOWINFO = QuicheNativeStaticallyReferencedJniMethods.sockaddrIn6OffsetofSin6Flowinfo();
/*     */   
/* 108 */   static final int SOCKADDR_IN6_OFFSETOF_SIN6_ADDR = QuicheNativeStaticallyReferencedJniMethods.sockaddrIn6OffsetofSin6Addr();
/*     */   
/* 110 */   static final int SOCKADDR_IN6_OFFSETOF_SIN6_SCOPE_ID = QuicheNativeStaticallyReferencedJniMethods.sockaddrIn6OffsetofSin6ScopeId();
/*     */   
/* 112 */   static final int IN6_ADDRESS_OFFSETOF_S6_ADDR = QuicheNativeStaticallyReferencedJniMethods.in6AddressOffsetofS6Addr();
/* 113 */   static final int SIZEOF_SOCKLEN_T = QuicheNativeStaticallyReferencedJniMethods.sizeofSocklenT();
/* 114 */   static final int SIZEOF_SIZE_T = QuicheNativeStaticallyReferencedJniMethods.sizeofSizeT();
/*     */   
/* 116 */   static final int SIZEOF_TIMESPEC = QuicheNativeStaticallyReferencedJniMethods.sizeofTimespec();
/*     */   
/* 118 */   static final int SIZEOF_TIME_T = QuicheNativeStaticallyReferencedJniMethods.sizeofTimeT();
/* 119 */   static final int SIZEOF_LONG = QuicheNativeStaticallyReferencedJniMethods.sizeofLong();
/*     */ 
/*     */   
/* 122 */   static final int TIMESPEC_OFFSETOF_TV_SEC = QuicheNativeStaticallyReferencedJniMethods.timespecOffsetofTvSec();
/*     */ 
/*     */   
/* 125 */   static final int TIMESPEC_OFFSETOF_TV_NSEC = QuicheNativeStaticallyReferencedJniMethods.timespecOffsetofTvNsec();
/*     */ 
/*     */   
/* 128 */   static final int QUICHE_RECV_INFO_OFFSETOF_FROM = QuicheNativeStaticallyReferencedJniMethods.quicheRecvInfoOffsetofFrom();
/*     */   
/* 130 */   static final int QUICHE_RECV_INFO_OFFSETOF_FROM_LEN = QuicheNativeStaticallyReferencedJniMethods.quicheRecvInfoOffsetofFromLen();
/*     */ 
/*     */   
/* 133 */   static final int QUICHE_RECV_INFO_OFFSETOF_TO = QuicheNativeStaticallyReferencedJniMethods.quicheRecvInfoOffsetofTo();
/*     */   
/* 135 */   static final int QUICHE_RECV_INFO_OFFSETOF_TO_LEN = QuicheNativeStaticallyReferencedJniMethods.quicheRecvInfoOffsetofToLen();
/*     */   
/* 137 */   static final int SIZEOF_QUICHE_RECV_INFO = QuicheNativeStaticallyReferencedJniMethods.sizeofQuicheRecvInfo();
/*     */   
/* 139 */   static final int QUICHE_SEND_INFO_OFFSETOF_TO = QuicheNativeStaticallyReferencedJniMethods.quicheSendInfoOffsetofTo();
/*     */   
/* 141 */   static final int QUICHE_SEND_INFO_OFFSETOF_TO_LEN = QuicheNativeStaticallyReferencedJniMethods.quicheSendInfoOffsetofToLen();
/*     */ 
/*     */   
/* 144 */   static final int QUICHE_SEND_INFO_OFFSETOF_FROM = QuicheNativeStaticallyReferencedJniMethods.quicheSendInfoOffsetofFrom();
/*     */   
/* 146 */   static final int QUICHE_SEND_INFO_OFFSETOF_FROM_LEN = QuicheNativeStaticallyReferencedJniMethods.quicheSendInfoOffsetofFromLen();
/*     */ 
/*     */   
/* 149 */   static final int QUICHE_SEND_INFO_OFFSETOF_AT = QuicheNativeStaticallyReferencedJniMethods.quicheSendInfoOffsetofAt();
/* 150 */   static final int SIZEOF_QUICHE_SEND_INFO = QuicheNativeStaticallyReferencedJniMethods.sizeofQuicheSendInfo();
/*     */   
/* 152 */   static final int QUICHE_PROTOCOL_VERSION = QuicheNativeStaticallyReferencedJniMethods.quiche_protocol_version();
/* 153 */   static final int QUICHE_MAX_CONN_ID_LEN = QuicheNativeStaticallyReferencedJniMethods.quiche_max_conn_id_len();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   static final int QUICHE_SHUTDOWN_READ = QuicheNativeStaticallyReferencedJniMethods.quiche_shutdown_read();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   static final int QUICHE_SHUTDOWN_WRITE = QuicheNativeStaticallyReferencedJniMethods.quiche_shutdown_write();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   static final int QUICHE_ERR_DONE = QuicheNativeStaticallyReferencedJniMethods.quiche_err_done();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   static final int QUICHE_ERR_BUFFER_TOO_SHORT = QuicheNativeStaticallyReferencedJniMethods.quiche_err_buffer_too_short();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   static final int QUICHE_ERR_UNKNOWN_VERSION = QuicheNativeStaticallyReferencedJniMethods.quiche_err_unknown_version();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   static final int QUICHE_ERR_INVALID_FRAME = QuicheNativeStaticallyReferencedJniMethods.quiche_err_invalid_frame();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   static final int QUICHE_ERR_INVALID_PACKET = QuicheNativeStaticallyReferencedJniMethods.quiche_err_invalid_packet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   static final int QUICHE_ERR_INVALID_STATE = QuicheNativeStaticallyReferencedJniMethods.quiche_err_invalid_state();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   static final int QUICHE_ERR_INVALID_STREAM_STATE = QuicheNativeStaticallyReferencedJniMethods.quiche_err_invalid_stream_state();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   static final int QUICHE_ERR_INVALID_TRANSPORT_PARAM = QuicheNativeStaticallyReferencedJniMethods.quiche_err_invalid_transport_param();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   static final int QUICHE_ERR_CRYPTO_FAIL = QuicheNativeStaticallyReferencedJniMethods.quiche_err_crypto_fail();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   static final int QUICHE_ERR_TLS_FAIL = QuicheNativeStaticallyReferencedJniMethods.quiche_err_tls_fail();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   static final int QUICHE_ERR_FLOW_CONTROL = QuicheNativeStaticallyReferencedJniMethods.quiche_err_flow_control();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   static final int QUICHE_ERR_STREAM_LIMIT = QuicheNativeStaticallyReferencedJniMethods.quiche_err_stream_limit();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   static final int QUICHE_ERR_FINAL_SIZE = QuicheNativeStaticallyReferencedJniMethods.quiche_err_final_size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   static final int QUICHE_ERR_CONGESTION_CONTROL = QuicheNativeStaticallyReferencedJniMethods.quiche_err_congestion_control();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   static final int QUICHE_ERR_STREAM_RESET = QuicheNativeStaticallyReferencedJniMethods.quiche_err_stream_reset();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   static final int QUICHE_ERR_STREAM_STOPPED = QuicheNativeStaticallyReferencedJniMethods.quiche_err_stream_stopped();
/*     */ 
/*     */ 
/*     */   
/* 269 */   static final int QUICHE_ERR_ID_LIMIT = QuicheNativeStaticallyReferencedJniMethods.quiche_err_id_limit();
/*     */ 
/*     */ 
/*     */   
/* 273 */   static final int QUICHE_ERR_OUT_OF_IDENTIFIERS = QuicheNativeStaticallyReferencedJniMethods.quiche_err_out_of_identifiers();
/*     */ 
/*     */ 
/*     */   
/* 277 */   static final int QUICHE_ERR_KEY_UPDATE = QuicheNativeStaticallyReferencedJniMethods.quiche_err_key_update();
/*     */ 
/*     */   
/* 280 */   static final int QUICHE_ERR_CRYPTO_BUFFER_EXCEEDED = QuicheNativeStaticallyReferencedJniMethods.quiche_err_crypto_buffer_exceeded();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   static final int QUICHE_CC_RENO = QuicheNativeStaticallyReferencedJniMethods.quiche_cc_reno();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   static final int QUICHE_CC_CUBIC = QuicheNativeStaticallyReferencedJniMethods.quiche_cc_cubic();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 298 */   static final int QUICHE_CC_BBR = QuicheNativeStaticallyReferencedJniMethods.quiche_cc_bbr();
/*     */   
/* 300 */   static final int QUICHE_PATH_EVENT_NEW = QuicheNativeStaticallyReferencedJniMethods.quiche_path_event_new();
/*     */   
/* 302 */   static final int QUICHE_PATH_EVENT_VALIDATED = QuicheNativeStaticallyReferencedJniMethods.quiche_path_event_validated();
/*     */   
/* 304 */   static final int QUICHE_PATH_EVENT_FAILED_VALIDATION = QuicheNativeStaticallyReferencedJniMethods.quiche_path_event_failed_validation();
/* 305 */   static final int QUICHE_PATH_EVENT_CLOSED = QuicheNativeStaticallyReferencedJniMethods.quiche_path_event_closed();
/*     */   
/* 307 */   static final int QUICHE_PATH_EVENT_REUSED_SOURCE_CONNECTION_ID = QuicheNativeStaticallyReferencedJniMethods.quiche_path_event_reused_source_connection_id();
/*     */   
/* 309 */   static final int QUICHE_PATH_EVENT_PEER_MIGRATED = QuicheNativeStaticallyReferencedJniMethods.quiche_path_event_peer_migrated();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static QuicConnectionCloseEvent quiche_conn_peer_error(long connAddr) {
/* 366 */     Object[] error = quiche_conn_peer_error0(connAddr);
/* 367 */     if (error == null) {
/* 368 */       return null;
/*     */     }
/* 370 */     return new QuicConnectionCloseEvent(((Boolean)error[0]).booleanValue(), ((Integer)error[1]).intValue(), (byte[])error[2]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long readerMemoryAddress(ByteBuf buf) {
/* 755 */     return memoryAddress(buf, buf.readerIndex(), buf.readableBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long writerMemoryAddress(ByteBuf buf) {
/* 766 */     return memoryAddress(buf, buf.writerIndex(), buf.writableBytes());
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
/*     */   
/*     */   static long memoryAddress(ByteBuf buf, int offset, int len) {
/* 779 */     assert buf.isDirect();
/* 780 */     if (buf.hasMemoryAddress()) {
/* 781 */       return buf.memoryAddress() + offset;
/*     */     }
/* 783 */     return memoryAddressWithPosition(buf.internalNioBuffer(offset, len));
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
/*     */   static long memoryAddressWithPosition(ByteBuffer buf) {
/* 795 */     assert buf.isDirect();
/* 796 */     return buffer_memory_address(buf) + buf.position();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuf allocateNativeOrder(int capacity) {
/* 802 */     ByteBuf buffer = Unpooled.directBuffer(capacity);
/*     */ 
/*     */ 
/*     */     
/* 806 */     return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? buffer : buffer.order(ByteOrder.LITTLE_ENDIAN);
/*     */   }
/*     */   
/*     */   static boolean shouldClose(int res) {
/* 810 */     return (res == QUICHE_ERR_CRYPTO_FAIL || res == QUICHE_ERR_TLS_FAIL);
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
/*     */   static boolean isSameAddress(ByteBuffer memory, ByteBuffer memory2, int addressOffset) {
/* 822 */     long address1 = memoryAddressWithPosition(memory) + addressOffset;
/* 823 */     long address2 = memoryAddressWithPosition(memory2) + addressOffset;
/* 824 */     return (SockaddrIn.cmp(address1, address2) == 0);
/*     */   }
/*     */   
/*     */   static void setPrimitiveValue(ByteBuffer memory, int offset, int valueType, long value) {
/* 828 */     switch (valueType) {
/*     */       case 1:
/* 830 */         memory.put(offset, (byte)(int)value);
/*     */         return;
/*     */       case 2:
/* 833 */         memory.putShort(offset, (short)(int)value);
/*     */         return;
/*     */       case 4:
/* 836 */         memory.putInt(offset, (int)value);
/*     */         return;
/*     */       case 8:
/* 839 */         memory.putLong(offset, value);
/*     */         return;
/*     */     } 
/* 842 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   static long getPrimitiveValue(ByteBuffer memory, int offset, int valueType) {
/* 847 */     switch (valueType) {
/*     */       case 1:
/* 849 */         return memory.get(offset);
/*     */       case 2:
/* 851 */         return memory.getShort(offset);
/*     */       case 4:
/* 853 */         return memory.getInt(offset);
/*     */       case 8:
/* 855 */         return memory.getLong(offset);
/*     */     } 
/* 857 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 863 */     ERROR_MAPPINGS.put(QUICHE_ERR_DONE, new QuicTransportErrorHolder(QuicTransportError.NO_ERROR, "QUICHE_ERR_DONE"));
/*     */     
/* 865 */     ERROR_MAPPINGS.put(QUICHE_ERR_INVALID_FRAME, new QuicTransportErrorHolder(QuicTransportError.FRAME_ENCODING_ERROR, "QUICHE_ERR_INVALID_FRAME"));
/*     */     
/* 867 */     ERROR_MAPPINGS.put(QUICHE_ERR_INVALID_STREAM_STATE, new QuicTransportErrorHolder(QuicTransportError.STREAM_STATE_ERROR, "QUICHE_ERR_INVALID_STREAM_STATE"));
/*     */     
/* 869 */     ERROR_MAPPINGS.put(QUICHE_ERR_INVALID_TRANSPORT_PARAM, new QuicTransportErrorHolder(QuicTransportError.TRANSPORT_PARAMETER_ERROR, "QUICHE_ERR_INVALID_TRANSPORT_PARAM"));
/*     */ 
/*     */     
/* 872 */     ERROR_MAPPINGS.put(QUICHE_ERR_FLOW_CONTROL, new QuicTransportErrorHolder(QuicTransportError.FLOW_CONTROL_ERROR, "QUICHE_ERR_FLOW_CONTROL"));
/*     */     
/* 874 */     ERROR_MAPPINGS.put(QUICHE_ERR_STREAM_LIMIT, new QuicTransportErrorHolder(QuicTransportError.STREAM_LIMIT_ERROR, "QUICHE_ERR_STREAM_LIMIT"));
/*     */     
/* 876 */     ERROR_MAPPINGS.put(QUICHE_ERR_ID_LIMIT, new QuicTransportErrorHolder(QuicTransportError.CONNECTION_ID_LIMIT_ERROR, "QUICHE_ERR_ID_LIMIT"));
/*     */     
/* 878 */     ERROR_MAPPINGS.put(QUICHE_ERR_FINAL_SIZE, new QuicTransportErrorHolder(QuicTransportError.FINAL_SIZE_ERROR, "QUICHE_ERR_FINAL_SIZE"));
/*     */     
/* 880 */     ERROR_MAPPINGS.put(QUICHE_ERR_CRYPTO_BUFFER_EXCEEDED, new QuicTransportErrorHolder(QuicTransportError.CRYPTO_BUFFER_EXCEEDED, "QUICHE_ERR_CRYPTO_BUFFER_EXCEEDED"));
/*     */ 
/*     */     
/* 883 */     ERROR_MAPPINGS.put(QUICHE_ERR_KEY_UPDATE, new QuicTransportErrorHolder(QuicTransportError.KEY_UPDATE_ERROR, "QUICHE_ERR_KEY_UPDATE"));
/*     */ 
/*     */ 
/*     */     
/* 887 */     ERROR_MAPPINGS.put(QUICHE_ERR_TLS_FAIL, new QuicTransportErrorHolder(
/* 888 */           QuicTransportError.valueOf(256L), "QUICHE_ERR_TLS_FAIL"));
/* 889 */     ERROR_MAPPINGS.put(QUICHE_ERR_CRYPTO_FAIL, new QuicTransportErrorHolder(
/* 890 */           QuicTransportError.valueOf(256L), "QUICHE_ERR_CRYPTO_FAIL"));
/*     */     
/* 892 */     ERROR_MAPPINGS.put(QUICHE_ERR_BUFFER_TOO_SHORT, new QuicTransportErrorHolder(QuicTransportError.PROTOCOL_VIOLATION, "QUICHE_ERR_BUFFER_TOO_SHORT"));
/*     */     
/* 894 */     ERROR_MAPPINGS.put(QUICHE_ERR_UNKNOWN_VERSION, new QuicTransportErrorHolder(QuicTransportError.PROTOCOL_VIOLATION, "QUICHE_ERR_UNKNOWN_VERSION"));
/*     */     
/* 896 */     ERROR_MAPPINGS.put(QUICHE_ERR_INVALID_PACKET, new QuicTransportErrorHolder(QuicTransportError.PROTOCOL_VIOLATION, "QUICHE_ERR_INVALID_PACKET"));
/*     */     
/* 898 */     ERROR_MAPPINGS.put(QUICHE_ERR_INVALID_STATE, new QuicTransportErrorHolder(QuicTransportError.PROTOCOL_VIOLATION, "QUICHE_ERR_INVALID_STATE"));
/*     */     
/* 900 */     ERROR_MAPPINGS.put(QUICHE_ERR_CONGESTION_CONTROL, new QuicTransportErrorHolder(QuicTransportError.PROTOCOL_VIOLATION, "QUICHE_ERR_CONGESTION_CONTROL"));
/*     */     
/* 902 */     ERROR_MAPPINGS.put(QUICHE_ERR_STREAM_STOPPED, new QuicTransportErrorHolder(QuicTransportError.PROTOCOL_VIOLATION, "QUICHE_ERR_STREAM_STOPPED"));
/*     */     
/* 904 */     ERROR_MAPPINGS.put(QUICHE_ERR_OUT_OF_IDENTIFIERS, new QuicTransportErrorHolder(QuicTransportError.PROTOCOL_VIOLATION, "QUICHE_ERR_OUT_OF_IDENTIFIERS"));
/*     */   }
/*     */ 
/*     */   
/*     */   static Exception convertToException(int result) {
/* 909 */     return convertToException(result, -1L);
/*     */   } @Nullable
/*     */   static native String quiche_version(); static native boolean quiche_version_is_supported(int paramInt); static native int quiche_negotiate_version(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, int paramInt3); static native int quiche_retry(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, int paramInt3, long paramLong4, int paramInt4, int paramInt5, long paramLong5, int paramInt6); static native long quiche_conn_new_with_tls(long paramLong1, int paramInt1, long paramLong2, int paramInt2, long paramLong3, int paramInt3, long paramLong4, int paramInt4, long paramLong5, long paramLong6, boolean paramBoolean); static native boolean quiche_conn_set_qlog_path(long paramLong, String paramString1, String paramString2, String paramString3); static native int quiche_conn_recv(long paramLong1, long paramLong2, int paramInt, long paramLong3); static native int quiche_conn_send(long paramLong1, long paramLong2, int paramInt, long paramLong3); static native void quiche_conn_free(long paramLong); private static native Object[] quiche_conn_peer_error0(long paramLong); static native long quiche_conn_peer_streams_left_bidi(long paramLong); static native long quiche_conn_peer_streams_left_uni(long paramLong); static native int quiche_conn_stream_priority(long paramLong1, long paramLong2, byte paramByte, boolean paramBoolean); static native int quiche_conn_send_quantum(long paramLong); static native byte[] quiche_conn_trace_id(long paramLong); static native byte[] quiche_conn_source_id(long paramLong); static native byte[] quiche_conn_destination_id(long paramLong); static native int quiche_conn_stream_recv(long paramLong1, long paramLong2, long paramLong3, int paramInt, long paramLong4, long paramLong5); static native int quiche_conn_stream_send(long paramLong1, long paramLong2, long paramLong3, int paramInt, boolean paramBoolean); static native int quiche_conn_stream_shutdown(long paramLong1, long paramLong2, int paramInt, long paramLong3); static native long quiche_conn_stream_capacity(long paramLong1, long paramLong2); static native boolean quiche_conn_stream_finished(long paramLong1, long paramLong2); static native int quiche_conn_close(long paramLong1, boolean paramBoolean, long paramLong2, long paramLong3, int paramInt); static native boolean quiche_conn_is_established(long paramLong); static native boolean quiche_conn_is_in_early_data(long paramLong); static native boolean quiche_conn_is_closed(long paramLong); static native boolean quiche_conn_is_timed_out(long paramLong); static native long[] quiche_conn_stats(long paramLong); static native long[] quiche_conn_peer_transport_params(long paramLong); static native long quiche_conn_timeout_as_nanos(long paramLong); static native void quiche_conn_on_timeout(long paramLong); static native long quiche_conn_readable(long paramLong); static native long quiche_conn_writable(long paramLong); static native int quiche_stream_iter_next(long paramLong, long[] paramArrayOflong); static native void quiche_stream_iter_free(long paramLong); static native Object[] quiche_conn_path_stats(long paramLong1, long paramLong2); static native int quiche_conn_dgram_max_writable_len(long paramLong); static native int quiche_conn_dgram_recv_front_len(long paramLong); static native int quiche_conn_dgram_recv(long paramLong1, long paramLong2, int paramInt); static native int quiche_conn_dgram_send(long paramLong1, long paramLong2, int paramInt);
/*     */   static Exception convertToException(int result, long code) {
/* 913 */     QuicTransportErrorHolder holder = (QuicTransportErrorHolder)ERROR_MAPPINGS.get(result);
/* 914 */     if (holder == null) {
/*     */       
/* 916 */       QuicheError error = QuicheError.valueOf(result);
/* 917 */       if (error == QuicheError.STREAM_RESET) {
/* 918 */         return new QuicStreamResetException(error.message(), code);
/*     */       }
/* 920 */       return new QuicException(error.message());
/*     */     } 
/*     */     
/* 923 */     Exception exception = new QuicException(holder.error + ": " + holder.quicheErrorName, holder.error);
/* 924 */     if (result == QUICHE_ERR_TLS_FAIL) {
/* 925 */       String lastSslError = BoringSSL.ERR_last_error();
/* 926 */       SSLHandshakeException sslExc = new SSLHandshakeException(lastSslError);
/* 927 */       sslExc.initCause(exception);
/* 928 */       return sslExc;
/*     */     } 
/* 930 */     if (result == QUICHE_ERR_CRYPTO_FAIL) {
/* 931 */       return new SSLException(exception);
/*     */     }
/* 933 */     return exception;
/*     */   } static native int quiche_conn_set_session(long paramLong, byte[] paramArrayOfbyte); static native int quiche_conn_max_send_udp_payload_size(long paramLong); static native int quiche_conn_scids_left(long paramLong); static native long quiche_conn_new_scid(long paramLong1, long paramLong2, int paramInt, byte[] paramArrayOfbyte, boolean paramBoolean, long paramLong3); static native byte[] quiche_conn_retired_scid_next(long paramLong); static native long quiche_conn_path_event_next(long paramLong); static native int quiche_path_event_type(long paramLong); static native void quiche_path_event_free(long paramLong); static native Object[] quiche_path_event_new(long paramLong); static native Object[] quiche_path_event_validated(long paramLong); static native Object[] quiche_path_event_failed_validation(long paramLong); static native Object[] quiche_path_event_closed(long paramLong); static native Object[] quiche_path_event_reused_source_connection_id(long paramLong); static native Object[] quiche_path_event_peer_migrated(long paramLong); static native long quiche_config_new(int paramInt); static native void quiche_config_grease(long paramLong, boolean paramBoolean); static native void quiche_config_set_max_idle_timeout(long paramLong1, long paramLong2); static native void quiche_config_set_max_recv_udp_payload_size(long paramLong1, long paramLong2); static native void quiche_config_set_max_send_udp_payload_size(long paramLong1, long paramLong2); static native void quiche_config_set_initial_max_data(long paramLong1, long paramLong2); static native void quiche_config_set_initial_max_stream_data_bidi_local(long paramLong1, long paramLong2); static native void quiche_config_set_initial_max_stream_data_bidi_remote(long paramLong1, long paramLong2); static native void quiche_config_set_initial_max_stream_data_uni(long paramLong1, long paramLong2); static native void quiche_config_set_initial_max_streams_bidi(long paramLong1, long paramLong2); static native void quiche_config_set_initial_max_streams_uni(long paramLong1, long paramLong2); static native void quiche_config_set_ack_delay_exponent(long paramLong1, long paramLong2); static native void quiche_config_set_max_ack_delay(long paramLong1, long paramLong2); static native void quiche_config_set_disable_active_migration(long paramLong, boolean paramBoolean); static native void quiche_config_set_cc_algorithm(long paramLong, int paramInt); static native void quiche_config_set_initial_congestion_window_packets(long paramLong, int paramInt); static native void quiche_config_enable_hystart(long paramLong, boolean paramBoolean); static native void quiche_config_discover_pmtu(long paramLong, boolean paramBoolean); static native void quiche_config_enable_dgram(long paramLong, boolean paramBoolean, int paramInt1, int paramInt2); static native void quiche_config_set_active_connection_id_limit(long paramLong1, long paramLong2);
/*     */   static native void quiche_config_set_stateless_reset_token(long paramLong, byte[] paramArrayOfbyte);
/*     */   static native void quiche_config_free(long paramLong);
/*     */   private static native void quiche_enable_debug_logging(QuicheLogger paramQuicheLogger);
/*     */   private static native long buffer_memory_address(ByteBuffer paramByteBuffer);
/*     */   static native int sockaddr_cmp(long paramLong1, long paramLong2);
/*     */   private static final class QuicTransportErrorHolder { QuicTransportErrorHolder(QuicTransportError error, String quicheErrorName) {
/* 941 */       this.error = error;
/* 942 */       this.quicheErrorName = quicheErrorName;
/*     */     }
/*     */     
/*     */     private final QuicTransportError error;
/*     */     private final String quicheErrorName; }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\Quiche.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */