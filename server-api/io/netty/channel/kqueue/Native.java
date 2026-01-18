/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.channel.unix.Errors;
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.channel.unix.PeerCredentials;
/*     */ import io.netty.channel.unix.Unix;
/*     */ import io.netty.util.internal.ClassInitializerUtil;
/*     */ import io.netty.util.internal.NativeLibraryLoader;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.ThrowableUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Native
/*     */ {
/*  57 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Native.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  65 */     ClassInitializerUtil.tryLoadClasses(Native.class, new Class[] { PeerCredentials.class, DefaultFileRegion.class, FileChannel.class, FileDescriptor.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  73 */       sizeofKEvent();
/*  74 */     } catch (UnsatisfiedLinkError ignore) {
/*     */       
/*  76 */       loadNativeLibrary();
/*     */     } 
/*  78 */     Unix.registerInternal(new Runnable()
/*     */         {
/*     */           public void run() {
/*  81 */             Native.registerUnix();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  88 */   static final short EV_ADD = KQueueStaticallyReferencedJniMethods.evAdd();
/*  89 */   static final short EV_ENABLE = KQueueStaticallyReferencedJniMethods.evEnable();
/*  90 */   static final short EV_DISABLE = KQueueStaticallyReferencedJniMethods.evDisable();
/*  91 */   static final short EV_DELETE = KQueueStaticallyReferencedJniMethods.evDelete();
/*  92 */   static final short EV_CLEAR = KQueueStaticallyReferencedJniMethods.evClear();
/*  93 */   static final short EV_ERROR = KQueueStaticallyReferencedJniMethods.evError();
/*  94 */   static final short EV_EOF = KQueueStaticallyReferencedJniMethods.evEOF();
/*     */   
/*  96 */   static final int NOTE_READCLOSED = KQueueStaticallyReferencedJniMethods.noteReadClosed();
/*  97 */   static final int NOTE_CONNRESET = KQueueStaticallyReferencedJniMethods.noteConnReset();
/*  98 */   static final int NOTE_DISCONNECTED = KQueueStaticallyReferencedJniMethods.noteDisconnected();
/*     */   
/* 100 */   static final int NOTE_RDHUP = NOTE_READCLOSED | NOTE_CONNRESET | NOTE_DISCONNECTED;
/*     */ 
/*     */   
/* 103 */   static final short EV_ADD_ENABLE = (short)(EV_ADD | EV_ENABLE);
/* 104 */   static final short EV_DELETE_DISABLE = (short)(EV_DELETE | EV_DISABLE);
/*     */   
/* 106 */   static final short EVFILT_READ = KQueueStaticallyReferencedJniMethods.evfiltRead();
/* 107 */   static final short EVFILT_WRITE = KQueueStaticallyReferencedJniMethods.evfiltWrite();
/* 108 */   static final short EVFILT_USER = KQueueStaticallyReferencedJniMethods.evfiltUser();
/* 109 */   static final short EVFILT_SOCK = KQueueStaticallyReferencedJniMethods.evfiltSock();
/*     */ 
/*     */   
/* 112 */   private static final int CONNECT_RESUME_ON_READ_WRITE = KQueueStaticallyReferencedJniMethods.connectResumeOnReadWrite();
/* 113 */   private static final int CONNECT_DATA_IDEMPOTENT = KQueueStaticallyReferencedJniMethods.connectDataIdempotent();
/* 114 */   static final int CONNECT_TCP_FASTOPEN = CONNECT_RESUME_ON_READ_WRITE | CONNECT_DATA_IDEMPOTENT;
/* 115 */   static final boolean IS_SUPPORTING_TCP_FASTOPEN_CLIENT = isSupportingFastOpenClient();
/* 116 */   static final boolean IS_SUPPORTING_TCP_FASTOPEN_SERVER = isSupportingFastOpenServer();
/*     */ 
/*     */   
/* 119 */   static final KQueueIoOps READ_ENABLED_OPS = KQueueIoOps.newOps(EVFILT_READ, EV_ADD_ENABLE, 0);
/*     */   
/* 121 */   static final KQueueIoOps WRITE_ENABLED_OPS = KQueueIoOps.newOps(EVFILT_WRITE, EV_ADD_ENABLE, 0);
/*     */   
/* 123 */   static final KQueueIoOps READ_DISABLED_OPS = KQueueIoOps.newOps(EVFILT_READ, EV_DELETE_DISABLE, 0);
/*     */   
/* 125 */   static final KQueueIoOps WRITE_DISABLED_OPS = KQueueIoOps.newOps(EVFILT_WRITE, EV_DELETE_DISABLE, 0);
/*     */   
/*     */   static FileDescriptor newKQueue() {
/* 128 */     return new FileDescriptor(kqueueCreate());
/*     */   }
/*     */ 
/*     */   
/*     */   static int keventWait(int kqueueFd, KQueueEventArray changeList, KQueueEventArray eventList, int tvSec, int tvNsec) throws IOException {
/* 133 */     int ready = keventWait(kqueueFd, changeList.memoryAddress(), changeList.size(), eventList
/* 134 */         .memoryAddress(), eventList.capacity(), tvSec, tvNsec);
/* 135 */     if (ready < 0) {
/* 136 */       throw Errors.newIOException("kevent", ready);
/*     */     }
/* 138 */     return ready;
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
/*     */   private static void loadNativeLibrary() {
/* 157 */     String name = PlatformDependent.normalizedOs();
/* 158 */     if (!"osx".equals(name) && !name.contains("bsd")) {
/* 159 */       throw new IllegalStateException("Only supported on OSX/BSD");
/*     */     }
/* 161 */     String staticLibName = "netty_transport_native_kqueue";
/* 162 */     String sharedLibName = staticLibName + '_' + PlatformDependent.normalizedArch();
/* 163 */     ClassLoader cl = PlatformDependent.getClassLoader(Native.class);
/*     */     try {
/* 165 */       NativeLibraryLoader.load(sharedLibName, cl);
/* 166 */     } catch (UnsatisfiedLinkError e1) {
/*     */       try {
/* 168 */         NativeLibraryLoader.load(staticLibName, cl);
/* 169 */         logger.debug("Failed to load {}", sharedLibName, e1);
/* 170 */       } catch (UnsatisfiedLinkError e2) {
/* 171 */         ThrowableUtil.addSuppressed(e1, e2);
/* 172 */         throw e1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isSupportingFastOpenClient() {
/*     */     try {
/* 179 */       return (KQueueStaticallyReferencedJniMethods.fastOpenClient() == 1);
/* 180 */     } catch (Exception e) {
/* 181 */       logger.debug("Failed to probe fastOpenClient sysctl, assuming client-side TCP FastOpen cannot be used.", e);
/*     */       
/* 183 */       return false;
/*     */     } 
/*     */   }
/*     */   private static boolean isSupportingFastOpenServer() {
/*     */     try {
/* 188 */       return (KQueueStaticallyReferencedJniMethods.fastOpenServer() == 1);
/* 189 */     } catch (Exception e) {
/* 190 */       logger.debug("Failed to probe fastOpenServer sysctl, assuming server-side TCP FastOpen cannot be used.", e);
/*     */       
/* 192 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native int registerUnix();
/*     */   
/*     */   private static native int kqueueCreate();
/*     */   
/*     */   private static native int keventWait(int paramInt1, long paramLong1, int paramInt2, long paramLong2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   static native int keventTriggerUserEvent(int paramInt1, int paramInt2);
/*     */   
/*     */   static native int keventAddUserEvent(int paramInt1, int paramInt2);
/*     */   
/*     */   static native int sizeofKEvent();
/*     */   
/*     */   static native int offsetofKEventIdent();
/*     */   
/*     */   static native int offsetofKEventFlags();
/*     */   
/*     */   static native int offsetofKEventFFlags();
/*     */   
/*     */   static native int offsetofKEventFilter();
/*     */   
/*     */   static native int offsetofKeventData();
/*     */   
/*     */   static native int offsetofKeventUdata();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\Native.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */