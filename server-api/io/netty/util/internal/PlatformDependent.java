/*      */ package io.netty.util.internal;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import io.netty.util.internal.shaded.org.jctools.queues.MpscChunkedArrayQueue;
/*      */ import io.netty.util.internal.shaded.org.jctools.queues.SpscLinkedQueue;
/*      */ import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpmcAtomicArrayQueue;
/*      */ import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscChunkedAtomicArrayQueue;
/*      */ import io.netty.util.internal.shaded.org.jctools.queues.atomic.SpscLinkedAtomicQueue;
/*      */ import io.netty.util.internal.shaded.org.jctools.queues.atomic.unpadded.MpscAtomicUnpaddedArrayQueue;
/*      */ import io.netty.util.internal.shaded.org.jctools.queues.unpadded.MpscUnpaddedArrayQueue;
/*      */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandles;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.invoke.VarHandle;
/*      */ import java.lang.reflect.Field;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.Paths;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Deque;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.ThreadLocalRandom;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ public final class PlatformDependent {
/*      */   private static final InternalLogger logger;
/*      */   private static Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN;
/*      */   private static final boolean MAYBE_SUPER_USER;
/*      */   private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
/*      */   private static final Throwable UNSAFE_UNAVAILABILITY_CAUSE;
/*      */   private static final boolean DIRECT_BUFFER_PREFERRED;
/*      */   private static final boolean EXPLICIT_NO_PREFER_DIRECT;
/*      */   private static final long MAX_DIRECT_MEMORY;
/*      */   private static final int MPSC_CHUNK_SIZE = 1024;
/*      */   private static final int MIN_MAX_MPSC_CAPACITY = 2048;
/*      */   private static final int MAX_ALLOWED_MPSC_CAPACITY = 1073741824;
/*      */   private static final long BYTE_ARRAY_BASE_OFFSET;
/*      */   private static final File TMPDIR;
/*      */   private static final int BIT_MODE;
/*      */   private static final String NORMALIZED_ARCH;
/*      */   private static final String NORMALIZED_OS;
/*      */   private static final Set<String> LINUX_OS_CLASSIFIERS;
/*      */   private static final boolean IS_WINDOWS;
/*      */   private static final boolean IS_OSX;
/*      */   private static final boolean IS_J9_JVM;
/*      */   private static final boolean IS_IVKVM_DOT_NET;
/*      */   private static final int ADDRESS_SIZE;
/*      */   private static final boolean USE_DIRECT_BUFFER_NO_CLEANER;
/*      */   private static final AtomicLong DIRECT_MEMORY_COUNTER;
/*      */   private static final long DIRECT_MEMORY_LIMIT;
/*      */   private static final Cleaner CLEANER;
/*      */   private static final Cleaner DIRECT_CLEANER;
/*      */   private static final Cleaner LEGACY_CLEANER;
/*      */   private static final boolean HAS_ALLOCATE_UNINIT_ARRAY;
/*      */   private static final String LINUX_ID_PREFIX = "ID=";
/*      */   private static final String LINUX_ID_LIKE_PREFIX = "ID_LIKE=";
/*      */   public static final boolean BIG_ENDIAN_NATIVE_ORDER;
/*      */   private static final boolean JFR;
/*      */   private static final boolean VAR_HANDLE;
/*      */   private static final Cleaner NOOP;
/*      */   
/*      */   static {
/*      */     boolean jfrAvailable;
/*      */   }
/*      */   
/*      */   static {
/*   89 */     logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   94 */     CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !isAndroid();
/*      */     
/*   96 */     UNSAFE_UNAVAILABILITY_CAUSE = unsafeUnavailabilityCause0();
/*      */ 
/*      */     
/*   99 */     MAX_DIRECT_MEMORY = estimateMaxDirectMemory();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     BYTE_ARRAY_BASE_OFFSET = byteArrayBaseOffset0();
/*      */     
/*  107 */     TMPDIR = tmpdir0();
/*      */     
/*  109 */     BIT_MODE = bitMode0();
/*  110 */     NORMALIZED_ARCH = normalizeArch(SystemPropertyUtil.get("os.arch", ""));
/*  111 */     NORMALIZED_OS = normalizeOs(SystemPropertyUtil.get("os.name", ""));
/*      */ 
/*      */ 
/*      */     
/*  115 */     IS_WINDOWS = isWindows0();
/*  116 */     IS_OSX = isOsx0();
/*  117 */     IS_J9_JVM = isJ9Jvm0();
/*  118 */     IS_IVKVM_DOT_NET = isIkvmDotNet0();
/*      */     
/*  120 */     ADDRESS_SIZE = addressSize0();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  130 */     BIG_ENDIAN_NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  135 */     NOOP = new Cleaner()
/*      */       {
/*      */         public CleanableDirectBuffer allocate(final int capacity) {
/*  138 */           return new CleanableDirectBuffer() {
/*  139 */               private final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(capacity);
/*      */ 
/*      */               
/*      */               public ByteBuffer buffer() {
/*  143 */                 return this.byteBuffer;
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               public void clean() {}
/*      */             };
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void freeDirectBuffer(ByteBuffer buffer) {}
/*      */       };
/*  167 */     long maxDirectMemory = SystemPropertyUtil.getLong("io.netty.maxDirectMemory", -1L);
/*      */     
/*  169 */     if (maxDirectMemory == 0L || !hasUnsafe() || !PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
/*  170 */       USE_DIRECT_BUFFER_NO_CLEANER = false;
/*  171 */       DIRECT_CLEANER = NOOP;
/*  172 */       DIRECT_MEMORY_COUNTER = null;
/*      */     } else {
/*  174 */       USE_DIRECT_BUFFER_NO_CLEANER = true;
/*  175 */       DIRECT_CLEANER = new DirectCleaner();
/*  176 */       if (maxDirectMemory < 0L) {
/*  177 */         maxDirectMemory = MAX_DIRECT_MEMORY;
/*  178 */         if (maxDirectMemory <= 0L) {
/*  179 */           DIRECT_MEMORY_COUNTER = null;
/*      */         } else {
/*  181 */           DIRECT_MEMORY_COUNTER = new AtomicLong();
/*      */         } 
/*      */       } else {
/*  184 */         DIRECT_MEMORY_COUNTER = new AtomicLong();
/*      */       } 
/*      */     } 
/*  187 */     logger.debug("-Dio.netty.maxDirectMemory: {} bytes", Long.valueOf(maxDirectMemory));
/*  188 */     DIRECT_MEMORY_LIMIT = (maxDirectMemory >= 1L) ? maxDirectMemory : MAX_DIRECT_MEMORY;
/*  189 */     HAS_ALLOCATE_UNINIT_ARRAY = (javaVersion() >= 9 && PlatformDependent0.hasAllocateArrayMethod());
/*      */     
/*  191 */     MAYBE_SUPER_USER = maybeSuperUser0();
/*      */     
/*  193 */     if (!isAndroid()) {
/*      */ 
/*      */       
/*  196 */       if (javaVersion() >= 9) {
/*      */         
/*  198 */         if (CleanerJava9.isSupported()) {
/*  199 */           LEGACY_CLEANER = new CleanerJava9();
/*  200 */         } else if (CleanerJava24Linker.isSupported()) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  205 */           LEGACY_CLEANER = new CleanerJava24Linker();
/*  206 */         } else if (CleanerJava25.isSupported()) {
/*      */ 
/*      */ 
/*      */           
/*  210 */           LEGACY_CLEANER = new CleanerJava25();
/*      */         } else {
/*  212 */           LEGACY_CLEANER = NOOP;
/*      */         } 
/*      */       } else {
/*  215 */         LEGACY_CLEANER = CleanerJava6.isSupported() ? new CleanerJava6() : NOOP;
/*      */       } 
/*      */     } else {
/*  218 */       LEGACY_CLEANER = NOOP;
/*      */     } 
/*  220 */     CLEANER = USE_DIRECT_BUFFER_NO_CLEANER ? DIRECT_CLEANER : LEGACY_CLEANER;
/*      */     
/*  222 */     EXPLICIT_NO_PREFER_DIRECT = SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false);
/*      */     
/*  224 */     DIRECT_BUFFER_PREFERRED = (CLEANER != NOOP && !EXPLICIT_NO_PREFER_DIRECT);
/*      */     
/*  226 */     if (logger.isDebugEnabled()) {
/*  227 */       logger.debug("-Dio.netty.noPreferDirect: {}", Boolean.valueOf(EXPLICIT_NO_PREFER_DIRECT));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  234 */     if (CLEANER == NOOP && !PlatformDependent0.isExplicitNoUnsafe()) {
/*  235 */       logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system instability.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  241 */     Set<String> availableClassifiers = new LinkedHashSet<>();
/*      */     
/*  243 */     if (!addPropertyOsClassifiers(availableClassifiers)) {
/*  244 */       addFilesystemOsClassifiers(availableClassifiers);
/*      */     }
/*  246 */     LINUX_OS_CLASSIFIERS = Collections.unmodifiableSet(availableClassifiers);
/*      */ 
/*      */     
/*  249 */     Throwable jfrFailure = null;
/*      */     
/*      */     try {
/*  252 */       jfrAvailable = FlightRecorder.isAvailable();
/*  253 */     } catch (Throwable t) {
/*  254 */       jfrFailure = t;
/*  255 */       jfrAvailable = false;
/*      */     } 
/*  257 */     JFR = SystemPropertyUtil.getBoolean("io.netty.jfr.enabled", jfrAvailable);
/*  258 */     if (logger.isTraceEnabled() && jfrFailure != null) {
/*  259 */       logger.debug("-Dio.netty.jfr.enabled: {}", Boolean.valueOf(JFR), jfrFailure);
/*  260 */     } else if (logger.isDebugEnabled()) {
/*  261 */       logger.debug("-Dio.netty.jfr.enabled: {}", Boolean.valueOf(JFR));
/*      */     } 
/*  263 */     VAR_HANDLE = initializeVarHandle();
/*      */   }
/*      */   private static boolean initializeVarHandle() {
/*      */     Throwable varHandleFailure;
/*  267 */     if (UNSAFE_UNAVAILABILITY_CAUSE == null || javaVersion() < 9 || 
/*  268 */       PlatformDependent0.isNativeImage()) {
/*  269 */       return false;
/*      */     }
/*  271 */     boolean varHandleAvailable = false;
/*      */     
/*      */     try {
/*  274 */       VarHandle.storeStoreFence();
/*  275 */       varHandleAvailable = VarHandleFactory.isSupported();
/*  276 */       varHandleFailure = VarHandleFactory.unavailableCause();
/*  277 */     } catch (Throwable t) {
/*      */       
/*  279 */       varHandleFailure = t;
/*      */     } 
/*  281 */     if (varHandleFailure != null) {
/*  282 */       logger.debug("java.lang.invoke.VarHandle: unavailable, reason: {}", varHandleFailure.toString());
/*      */     } else {
/*  284 */       logger.debug("java.lang.invoke.VarHandle: available");
/*      */     } 
/*      */     
/*  287 */     boolean varHandleEnabled = (varHandleAvailable && SystemPropertyUtil.getBoolean("io.netty.varHandle.enabled", varHandleAvailable));
/*  288 */     if (logger.isTraceEnabled() && varHandleFailure != null) {
/*  289 */       logger.debug("-Dio.netty.varHandle.enabled: {}", Boolean.valueOf(varHandleEnabled), varHandleFailure);
/*  290 */     } else if (logger.isDebugEnabled()) {
/*  291 */       logger.debug("-Dio.netty.varHandle.enabled: {}", Boolean.valueOf(varHandleEnabled));
/*      */     } 
/*  293 */     return varHandleEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   static void addFilesystemOsClassifiers(Set<String> availableClassifiers) {
/*  298 */     if (processOsReleaseFile("/etc/os-release", availableClassifiers)) {
/*      */       return;
/*      */     }
/*  301 */     processOsReleaseFile("/usr/lib/os-release", availableClassifiers);
/*      */   }
/*      */   
/*      */   private static boolean processOsReleaseFile(String osReleaseFileName, Set<String> availableClassifiers) {
/*  305 */     Path file = Paths.get(osReleaseFileName, new String[0]);
/*  306 */     return ((Boolean)AccessController.<Boolean>doPrivileged(() -> { try { if (Files.exists(file, new java.nio.file.LinkOption[0])) { try { BufferedReader reader = new BufferedReader(new InputStreamReader(new BoundedInputStream(Files.newInputStream(file, new java.nio.file.OpenOption[0])), StandardCharsets.UTF_8)); try { String line; while ((line = reader.readLine()) != null) { if (line.startsWith("ID=")) { String id = normalizeOsReleaseVariableValue(line.substring("ID=".length())); addClassifier(availableClassifiers, new String[] { id }); continue; }  if (line.startsWith("ID_LIKE=")) { line = normalizeOsReleaseVariableValue(line.substring("ID_LIKE=".length())); addClassifier(availableClassifiers, line.split(" ")); }
/*      */                      }
/*      */                    reader.close(); }
/*  309 */                 catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1)
/*      */                   { throwable.addSuppressed(throwable1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   throw throwable; }
/*      */                  }
/*  323 */               catch (SecurityException e)
/*      */               { logger.debug("Unable to read {}", osReleaseFileName, e); }
/*  325 */               catch (IOException e)
/*      */               { logger.debug("Error while reading content of {}", osReleaseFileName, e); }
/*      */ 
/*      */               
/*      */               return Boolean.valueOf(true); }
/*      */              }
/*  331 */           catch (SecurityException e)
/*      */           { logger.debug("Unable to check if {} exists", osReleaseFileName, e); }
/*      */           
/*      */           return Boolean.valueOf(false);
/*      */         })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean addPropertyOsClassifiers(Set<String> availableClassifiers) {
/*  343 */     String osClassifiersPropertyName = "io.netty.osClassifiers";
/*  344 */     String osClassifiers = SystemPropertyUtil.get(osClassifiersPropertyName);
/*  345 */     if (osClassifiers == null) {
/*  346 */       return false;
/*      */     }
/*  348 */     if (osClassifiers.isEmpty())
/*      */     {
/*  350 */       return true;
/*      */     }
/*  352 */     String[] classifiers = osClassifiers.split(",");
/*  353 */     if (classifiers.length == 0) {
/*  354 */       throw new IllegalArgumentException(osClassifiersPropertyName + " property is not empty, but contains no classifiers: " + osClassifiers);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  359 */     if (classifiers.length > 2) {
/*  360 */       throw new IllegalArgumentException(osClassifiersPropertyName + " property contains more than 2 classifiers: " + osClassifiers);
/*      */     }
/*      */     
/*  363 */     for (String classifier : classifiers) {
/*  364 */       addClassifier(availableClassifiers, new String[] { classifier });
/*      */     } 
/*  366 */     return true;
/*      */   }
/*      */   
/*      */   public static long byteArrayBaseOffset() {
/*  370 */     return BYTE_ARRAY_BASE_OFFSET;
/*      */   }
/*      */   
/*      */   public static boolean hasDirectBufferNoCleanerConstructor() {
/*  374 */     return PlatformDependent0.hasDirectBufferNoCleanerConstructor();
/*      */   }
/*      */   
/*      */   public static byte[] allocateUninitializedArray(int size) {
/*  378 */     return HAS_ALLOCATE_UNINIT_ARRAY ? PlatformDependent0.allocateUninitializedArray(size) : new byte[size];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAndroid() {
/*  385 */     return PlatformDependent0.isAndroid();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWindows() {
/*  392 */     return IS_WINDOWS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOsx() {
/*  399 */     return IS_OSX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean maybeSuperUser() {
/*  407 */     return MAYBE_SUPER_USER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int javaVersion() {
/*  414 */     return PlatformDependent0.javaVersion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isVirtualThread(Thread thread) {
/*  422 */     return PlatformDependent0.isVirtualThread(thread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canEnableTcpNoDelayByDefault() {
/*  429 */     return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasUnsafe() {
/*  437 */     return (UNSAFE_UNAVAILABILITY_CAUSE == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Throwable getUnsafeUnavailabilityCause() {
/*  444 */     return UNSAFE_UNAVAILABILITY_CAUSE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUnaligned() {
/*  453 */     return PlatformDependent0.isUnaligned();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean directBufferPreferred() {
/*  461 */     return DIRECT_BUFFER_PREFERRED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isExplicitNoPreferDirect() {
/*  469 */     return EXPLICIT_NO_PREFER_DIRECT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canReliabilyFreeDirectBuffers() {
/*  478 */     return (CLEANER != NOOP);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long maxDirectMemory() {
/*  485 */     return DIRECT_MEMORY_LIMIT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long usedDirectMemory() {
/*  495 */     return (DIRECT_MEMORY_COUNTER != null) ? DIRECT_MEMORY_COUNTER.get() : -1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File tmpdir() {
/*  502 */     return TMPDIR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int bitMode() {
/*  509 */     return BIT_MODE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int addressSize() {
/*  517 */     return ADDRESS_SIZE;
/*      */   }
/*      */   
/*      */   public static long allocateMemory(long size) {
/*  521 */     return PlatformDependent0.allocateMemory(size);
/*      */   }
/*      */   
/*      */   public static void freeMemory(long address) {
/*  525 */     PlatformDependent0.freeMemory(address);
/*      */   }
/*      */   
/*      */   public static long reallocateMemory(long address, long newSize) {
/*  529 */     return PlatformDependent0.reallocateMemory(address, newSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void throwException(Throwable t) {
/*  536 */     PlatformDependent0.throwException(t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
/*  545 */     return new ConcurrentHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static LongCounter newLongCounter() {
/*  554 */     return new LongAdderCounter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity) {
/*  563 */     return new ConcurrentHashMap<>(initialCapacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor) {
/*  572 */     return new ConcurrentHashMap<>(initialCapacity, loadFactor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
/*  582 */     return new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
/*  591 */     return new ConcurrentHashMap<>(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CleanableDirectBuffer allocateDirect(int capacity) {
/*  600 */     return CLEANER.allocate(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void freeDirectBuffer(ByteBuffer buffer) {
/*  613 */     LEGACY_CLEANER.freeDirectBuffer(buffer);
/*      */   }
/*      */   
/*      */   public static long directBufferAddress(ByteBuffer buffer) {
/*  617 */     return PlatformDependent0.directBufferAddress(buffer);
/*      */   }
/*      */   
/*      */   public static ByteBuffer directBuffer(long memoryAddress, int size) {
/*  621 */     if (PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
/*  622 */       return PlatformDependent0.newDirectBuffer(memoryAddress, size);
/*      */     }
/*  624 */     throw new UnsupportedOperationException("sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean hasVarHandle() {
/*  629 */     return VAR_HANDLE;
/*      */   }
/*      */   
/*      */   public static VarHandle findVarHandleOfIntField(MethodHandles.Lookup lookup, Class<?> type, String fieldName) {
/*  633 */     if (VAR_HANDLE) {
/*  634 */       return VarHandleFactory.privateFindVarHandle(lookup, type, fieldName, int.class);
/*      */     }
/*  636 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle intBeArrayView() {
/*  640 */     if (VAR_HANDLE) {
/*  641 */       return VarHandleFactory.intBeArrayView();
/*      */     }
/*  643 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle intLeArrayView() {
/*  647 */     if (VAR_HANDLE) {
/*  648 */       return VarHandleFactory.intLeArrayView();
/*      */     }
/*  650 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle longBeArrayView() {
/*  654 */     if (VAR_HANDLE) {
/*  655 */       return VarHandleFactory.longBeArrayView();
/*      */     }
/*  657 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle longLeArrayView() {
/*  661 */     if (VAR_HANDLE) {
/*  662 */       return VarHandleFactory.longLeArrayView();
/*      */     }
/*  664 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle shortBeArrayView() {
/*  668 */     if (VAR_HANDLE) {
/*  669 */       return VarHandleFactory.shortBeArrayView();
/*      */     }
/*  671 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle shortLeArrayView() {
/*  675 */     if (VAR_HANDLE) {
/*  676 */       return VarHandleFactory.shortLeArrayView();
/*      */     }
/*  678 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle longBeByteBufferView() {
/*  682 */     if (VAR_HANDLE) {
/*  683 */       return VarHandleFactory.longBeByteBufferView();
/*      */     }
/*  685 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle longLeByteBufferView() {
/*  689 */     if (VAR_HANDLE) {
/*  690 */       return VarHandleFactory.longLeByteBufferView();
/*      */     }
/*  692 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle intBeByteBufferView() {
/*  696 */     if (VAR_HANDLE) {
/*  697 */       return VarHandleFactory.intBeByteBufferView();
/*      */     }
/*  699 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle intLeByteBufferView() {
/*  703 */     if (VAR_HANDLE) {
/*  704 */       return VarHandleFactory.intLeByteBufferView();
/*      */     }
/*  706 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle shortBeByteBufferView() {
/*  710 */     if (VAR_HANDLE) {
/*  711 */       return VarHandleFactory.shortBeByteBufferView();
/*      */     }
/*  713 */     return null;
/*      */   }
/*      */   
/*      */   public static VarHandle shortLeByteBufferView() {
/*  717 */     if (VAR_HANDLE) {
/*  718 */       return VarHandleFactory.shortLeByteBufferView();
/*      */     }
/*  720 */     return null;
/*      */   }
/*      */   
/*      */   public static Object getObject(Object object, long fieldOffset) {
/*  724 */     return PlatformDependent0.getObject(object, fieldOffset);
/*      */   }
/*      */   
/*      */   public static int getVolatileInt(Object object, long fieldOffset) {
/*  728 */     return PlatformDependent0.getIntVolatile(object, fieldOffset);
/*      */   }
/*      */   
/*      */   public static int getInt(Object object, long fieldOffset) {
/*  732 */     return PlatformDependent0.getInt(object, fieldOffset);
/*      */   }
/*      */   
/*      */   public static void putOrderedInt(Object object, long fieldOffset, int value) {
/*  736 */     PlatformDependent0.putOrderedInt(object, fieldOffset, value);
/*      */   }
/*      */   
/*      */   public static int getAndAddInt(Object object, long fieldOffset, int delta) {
/*  740 */     return PlatformDependent0.getAndAddInt(object, fieldOffset, delta);
/*      */   }
/*      */   
/*      */   public static boolean compareAndSwapInt(Object object, long fieldOffset, int expected, int value) {
/*  744 */     return PlatformDependent0.compareAndSwapInt(object, fieldOffset, expected, value);
/*      */   }
/*      */   
/*      */   static void safeConstructPutInt(Object object, long fieldOffset, int value) {
/*  748 */     PlatformDependent0.safeConstructPutInt(object, fieldOffset, value);
/*      */   }
/*      */   
/*      */   public static byte getByte(long address) {
/*  752 */     return PlatformDependent0.getByte(address);
/*      */   }
/*      */   
/*      */   public static short getShort(long address) {
/*  756 */     return PlatformDependent0.getShort(address);
/*      */   }
/*      */   
/*      */   public static int getInt(long address) {
/*  760 */     return PlatformDependent0.getInt(address);
/*      */   }
/*      */   
/*      */   public static long getLong(long address) {
/*  764 */     return PlatformDependent0.getLong(address);
/*      */   }
/*      */   
/*      */   public static byte getByte(byte[] data, int index) {
/*  768 */     return hasUnsafe() ? PlatformDependent0.getByte(data, index) : data[index];
/*      */   }
/*      */   
/*      */   public static byte getByte(byte[] data, long index) {
/*  772 */     return hasUnsafe() ? PlatformDependent0.getByte(data, index) : data[toIntExact(index)];
/*      */   }
/*      */   
/*      */   public static short getShort(byte[] data, int index) {
/*  776 */     return hasUnsafe() ? PlatformDependent0.getShort(data, index) : (short)data[index];
/*      */   }
/*      */   
/*      */   public static int getInt(byte[] data, int index) {
/*  780 */     return hasUnsafe() ? PlatformDependent0.getInt(data, index) : data[index];
/*      */   }
/*      */   
/*      */   public static int getInt(int[] data, long index) {
/*  784 */     return hasUnsafe() ? PlatformDependent0.getInt(data, index) : data[toIntExact(index)];
/*      */   }
/*      */   
/*      */   public static long getLong(byte[] data, int index) {
/*  788 */     return hasUnsafe() ? PlatformDependent0.getLong(data, index) : data[index];
/*      */   }
/*      */   
/*      */   public static long getLong(long[] data, long index) {
/*  792 */     return hasUnsafe() ? PlatformDependent0.getLong(data, index) : data[toIntExact(index)];
/*      */   }
/*      */   
/*      */   private static int toIntExact(long value) {
/*  796 */     return Math.toIntExact(value);
/*      */   }
/*      */   
/*      */   private static long getLongSafe(byte[] bytes, int offset) {
/*  800 */     if (BIG_ENDIAN_NATIVE_ORDER) {
/*  801 */       return bytes[offset] << 56L | (bytes[offset + 1] & 0xFFL) << 48L | (bytes[offset + 2] & 0xFFL) << 40L | (bytes[offset + 3] & 0xFFL) << 32L | (bytes[offset + 4] & 0xFFL) << 24L | (bytes[offset + 5] & 0xFFL) << 16L | (bytes[offset + 6] & 0xFFL) << 8L | bytes[offset + 7] & 0xFFL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  810 */     return bytes[offset] & 0xFFL | (bytes[offset + 1] & 0xFFL) << 8L | (bytes[offset + 2] & 0xFFL) << 16L | (bytes[offset + 3] & 0xFFL) << 24L | (bytes[offset + 4] & 0xFFL) << 32L | (bytes[offset + 5] & 0xFFL) << 40L | (bytes[offset + 6] & 0xFFL) << 48L | bytes[offset + 7] << 56L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getIntSafe(byte[] bytes, int offset) {
/*  821 */     if (BIG_ENDIAN_NATIVE_ORDER) {
/*  822 */       return bytes[offset] << 24 | (bytes[offset + 1] & 0xFF) << 16 | (bytes[offset + 2] & 0xFF) << 8 | bytes[offset + 3] & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  827 */     return bytes[offset] & 0xFF | (bytes[offset + 1] & 0xFF) << 8 | (bytes[offset + 2] & 0xFF) << 16 | bytes[offset + 3] << 24;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static short getShortSafe(byte[] bytes, int offset) {
/*  834 */     if (BIG_ENDIAN_NATIVE_ORDER) {
/*  835 */       return (short)(bytes[offset] << 8 | bytes[offset + 1] & 0xFF);
/*      */     }
/*  837 */     return (short)(bytes[offset] & 0xFF | bytes[offset + 1] << 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int hashCodeAsciiCompute(CharSequence value, int offset, int hash) {
/*  844 */     if (BIG_ENDIAN_NATIVE_ORDER) {
/*  845 */       return hash * -862048943 + 
/*      */         
/*  847 */         hashCodeAsciiSanitizeInt(value, offset + 4) * 461845907 + 
/*      */         
/*  849 */         hashCodeAsciiSanitizeInt(value, offset);
/*      */     }
/*  851 */     return hash * -862048943 + 
/*      */       
/*  853 */       hashCodeAsciiSanitizeInt(value, offset) * 461845907 + 
/*      */       
/*  855 */       hashCodeAsciiSanitizeInt(value, offset + 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int hashCodeAsciiSanitizeInt(CharSequence value, int offset) {
/*  862 */     if (BIG_ENDIAN_NATIVE_ORDER)
/*      */     {
/*  864 */       return value.charAt(offset + 3) & 0x1F | (value
/*  865 */         .charAt(offset + 2) & 0x1F) << 8 | (value
/*  866 */         .charAt(offset + 1) & 0x1F) << 16 | (value
/*  867 */         .charAt(offset) & 0x1F) << 24;
/*      */     }
/*  869 */     return (value.charAt(offset + 3) & 0x1F) << 24 | (value
/*  870 */       .charAt(offset + 2) & 0x1F) << 16 | (value
/*  871 */       .charAt(offset + 1) & 0x1F) << 8 | value
/*  872 */       .charAt(offset) & 0x1F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int hashCodeAsciiSanitizeShort(CharSequence value, int offset) {
/*  879 */     if (BIG_ENDIAN_NATIVE_ORDER)
/*      */     {
/*  881 */       return value.charAt(offset + 1) & 0x1F | (value
/*  882 */         .charAt(offset) & 0x1F) << 8;
/*      */     }
/*  884 */     return (value.charAt(offset + 1) & 0x1F) << 8 | value
/*  885 */       .charAt(offset) & 0x1F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int hashCodeAsciiSanitizeByte(char value) {
/*  892 */     return value & 0x1F;
/*      */   }
/*      */   
/*      */   public static void putByte(long address, byte value) {
/*  896 */     PlatformDependent0.putByte(address, value);
/*      */   }
/*      */   
/*      */   public static void putShort(long address, short value) {
/*  900 */     PlatformDependent0.putShort(address, value);
/*      */   }
/*      */   
/*      */   public static void putInt(long address, int value) {
/*  904 */     PlatformDependent0.putInt(address, value);
/*      */   }
/*      */   
/*      */   public static void putLong(long address, long value) {
/*  908 */     PlatformDependent0.putLong(address, value);
/*      */   }
/*      */   
/*      */   public static void putByte(byte[] data, int index, byte value) {
/*  912 */     PlatformDependent0.putByte(data, index, value);
/*      */   }
/*      */   
/*      */   public static void putByte(Object data, long offset, byte value) {
/*  916 */     PlatformDependent0.putByte(data, offset, value);
/*      */   }
/*      */   
/*      */   public static void putShort(byte[] data, int index, short value) {
/*  920 */     PlatformDependent0.putShort(data, index, value);
/*      */   }
/*      */   
/*      */   public static void putInt(byte[] data, int index, int value) {
/*  924 */     PlatformDependent0.putInt(data, index, value);
/*      */   }
/*      */   
/*      */   public static void putLong(byte[] data, int index, long value) {
/*  928 */     PlatformDependent0.putLong(data, index, value);
/*      */   }
/*      */   
/*      */   public static void putObject(Object o, long offset, Object x) {
/*  932 */     PlatformDependent0.putObject(o, offset, x);
/*      */   }
/*      */   
/*      */   public static long objectFieldOffset(Field field) {
/*  936 */     return PlatformDependent0.objectFieldOffset(field);
/*      */   }
/*      */   
/*      */   public static void copyMemory(long srcAddr, long dstAddr, long length) {
/*  940 */     PlatformDependent0.copyMemory(srcAddr, dstAddr, length);
/*      */   }
/*      */   
/*      */   public static void copyMemory(byte[] src, int srcIndex, long dstAddr, long length) {
/*  944 */     PlatformDependent0.copyMemory(src, BYTE_ARRAY_BASE_OFFSET + srcIndex, null, dstAddr, length);
/*      */   }
/*      */   
/*      */   public static void copyMemory(byte[] src, int srcIndex, byte[] dst, int dstIndex, long length) {
/*  948 */     PlatformDependent0.copyMemory(src, BYTE_ARRAY_BASE_OFFSET + srcIndex, dst, BYTE_ARRAY_BASE_OFFSET + dstIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void copyMemory(long srcAddr, byte[] dst, int dstIndex, long length) {
/*  953 */     PlatformDependent0.copyMemory(null, srcAddr, dst, BYTE_ARRAY_BASE_OFFSET + dstIndex, length);
/*      */   }
/*      */   
/*      */   public static void setMemory(byte[] dst, int dstIndex, long bytes, byte value) {
/*  957 */     PlatformDependent0.setMemory(dst, BYTE_ARRAY_BASE_OFFSET + dstIndex, bytes, value);
/*      */   }
/*      */   
/*      */   public static void setMemory(long address, long bytes, byte value) {
/*  961 */     PlatformDependent0.setMemory(address, bytes, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteBuffer allocateDirectNoCleaner(int capacity) {
/*  969 */     assert USE_DIRECT_BUFFER_NO_CLEANER;
/*      */     
/*  971 */     incrementMemoryCounter(capacity);
/*      */     try {
/*  973 */       return PlatformDependent0.allocateDirectNoCleaner(capacity);
/*  974 */     } catch (Throwable e) {
/*  975 */       decrementMemoryCounter(capacity);
/*  976 */       throwException(e);
/*  977 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CleanableDirectBuffer allocateDirectBufferNoCleaner(int capacity) {
/*  987 */     assert USE_DIRECT_BUFFER_NO_CLEANER;
/*  988 */     return DIRECT_CLEANER.allocate(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteBuffer reallocateDirectNoCleaner(ByteBuffer buffer, int capacity) {
/*  996 */     assert USE_DIRECT_BUFFER_NO_CLEANER;
/*      */     
/*  998 */     int len = capacity - buffer.capacity();
/*  999 */     incrementMemoryCounter(len);
/*      */     try {
/* 1001 */       return PlatformDependent0.reallocateDirectNoCleaner(buffer, capacity);
/* 1002 */     } catch (Throwable e) {
/* 1003 */       decrementMemoryCounter(len);
/* 1004 */       throwException(e);
/* 1005 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CleanableDirectBuffer reallocateDirectBufferNoCleaner(CleanableDirectBuffer buffer, int capacity) {
/* 1017 */     assert USE_DIRECT_BUFFER_NO_CLEANER;
/* 1018 */     return ((DirectCleaner)DIRECT_CLEANER).reallocate(buffer, capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void freeDirectNoCleaner(ByteBuffer buffer) {
/* 1026 */     assert USE_DIRECT_BUFFER_NO_CLEANER;
/*      */     
/* 1028 */     int capacity = buffer.capacity();
/* 1029 */     PlatformDependent0.freeMemory(PlatformDependent0.directBufferAddress(buffer));
/* 1030 */     decrementMemoryCounter(capacity);
/*      */   }
/*      */   
/*      */   public static boolean hasAlignDirectByteBuffer() {
/* 1034 */     return (hasUnsafe() || PlatformDependent0.hasAlignSliceMethod());
/*      */   }
/*      */   
/*      */   public static ByteBuffer alignDirectBuffer(ByteBuffer buffer, int alignment) {
/* 1038 */     if (!buffer.isDirect()) {
/* 1039 */       throw new IllegalArgumentException("Cannot get aligned slice of non-direct byte buffer.");
/*      */     }
/* 1041 */     if (PlatformDependent0.hasAlignSliceMethod()) {
/* 1042 */       return PlatformDependent0.alignSlice(buffer, alignment);
/*      */     }
/* 1044 */     if (hasUnsafe()) {
/* 1045 */       long address = directBufferAddress(buffer);
/* 1046 */       long aligned = align(address, alignment);
/* 1047 */       buffer.position((int)(aligned - address));
/* 1048 */       return buffer.slice();
/*      */     } 
/*      */     
/* 1051 */     throw new UnsupportedOperationException("Cannot align direct buffer. Needs either Unsafe or ByteBuffer.alignSlice method available.");
/*      */   }
/*      */ 
/*      */   
/*      */   public static long align(long value, int alignment) {
/* 1056 */     return Pow2.align(value, alignment);
/*      */   }
/*      */   
/*      */   public static ByteBuffer offsetSlice(ByteBuffer buffer, int index, int length) {
/* 1060 */     if (PlatformDependent0.hasOffsetSliceMethod()) {
/* 1061 */       return PlatformDependent0.offsetSlice(buffer, index, length);
/*      */     }
/* 1063 */     return ((ByteBuffer)buffer.duplicate().clear().position(index).limit(index + length)).slice();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void incrementMemoryCounter(int capacity) {
/* 1068 */     if (DIRECT_MEMORY_COUNTER != null) {
/* 1069 */       long newUsedMemory = DIRECT_MEMORY_COUNTER.addAndGet(capacity);
/* 1070 */       if (newUsedMemory > DIRECT_MEMORY_LIMIT) {
/* 1071 */         DIRECT_MEMORY_COUNTER.addAndGet(-capacity);
/* 1072 */         throw new OutOfDirectMemoryError("failed to allocate " + capacity + " byte(s) of direct memory (used: " + (newUsedMemory - capacity) + ", max: " + DIRECT_MEMORY_LIMIT + ')');
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void decrementMemoryCounter(int capacity) {
/* 1080 */     if (DIRECT_MEMORY_COUNTER != null) {
/* 1081 */       long usedMemory = DIRECT_MEMORY_COUNTER.addAndGet(-capacity);
/* 1082 */       assert usedMemory >= 0L;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean useDirectBufferNoCleaner() {
/* 1087 */     return USE_DIRECT_BUFFER_NO_CLEANER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
/* 1102 */     if (javaVersion() > 8 && (startPos2 | startPos1 | bytes1.length - length | bytes2.length - length) == 0) {
/* 1103 */       return Arrays.equals(bytes1, bytes2);
/*      */     }
/* 1105 */     return (!hasUnsafe() || !PlatformDependent0.unalignedAccess()) ? 
/* 1106 */       equalsSafe(bytes1, startPos1, bytes2, startPos2, length) : 
/* 1107 */       PlatformDependent0.equals(bytes1, startPos1, bytes2, startPos2, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(byte[] bytes, int startPos, int length) {
/* 1118 */     return (!hasUnsafe() || !PlatformDependent0.unalignedAccess()) ? 
/* 1119 */       isZeroSafe(bytes, startPos, length) : 
/* 1120 */       PlatformDependent0.isZero(bytes, startPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int equalsConstantTime(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
/* 1145 */     return (!hasUnsafe() || !PlatformDependent0.unalignedAccess()) ? 
/* 1146 */       ConstantTimeUtils.equalsConstantTime(bytes1, startPos1, bytes2, startPos2, length) : 
/* 1147 */       PlatformDependent0.equalsConstantTime(bytes1, startPos1, bytes2, startPos2, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hashCodeAscii(byte[] bytes, int startPos, int length) {
/* 1160 */     return (!hasUnsafe() || !PlatformDependent0.unalignedAccess() || BIG_ENDIAN_NATIVE_ORDER) ? 
/* 1161 */       hashCodeAsciiSafe(bytes, startPos, length) : 
/* 1162 */       PlatformDependent0.hashCodeAscii(bytes, startPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hashCodeAscii(CharSequence bytes) {
/* 1176 */     int length = bytes.length();
/* 1177 */     int remainingBytes = length & 0x7;
/* 1178 */     int hash = -1028477387;
/*      */ 
/*      */ 
/*      */     
/* 1182 */     if (length >= 32) {
/* 1183 */       for (int i = length - 8; i >= remainingBytes; i -= 8) {
/* 1184 */         hash = hashCodeAsciiCompute(bytes, i, hash);
/*      */       }
/* 1186 */     } else if (length >= 8) {
/* 1187 */       hash = hashCodeAsciiCompute(bytes, length - 8, hash);
/* 1188 */       if (length >= 16) {
/* 1189 */         hash = hashCodeAsciiCompute(bytes, length - 16, hash);
/* 1190 */         if (length >= 24) {
/* 1191 */           hash = hashCodeAsciiCompute(bytes, length - 24, hash);
/*      */         }
/*      */       } 
/*      */     } 
/* 1195 */     if (remainingBytes == 0) {
/* 1196 */       return hash;
/*      */     }
/* 1198 */     int offset = 0;
/* 1199 */     if ((((remainingBytes != 2) ? 1 : 0) & ((remainingBytes != 4) ? 1 : 0) & ((remainingBytes != 6) ? 1 : 0)) != 0) {
/* 1200 */       hash = hash * -862048943 + hashCodeAsciiSanitizeByte(bytes.charAt(0));
/* 1201 */       offset = 1;
/*      */     } 
/* 1203 */     if ((((remainingBytes != 1) ? 1 : 0) & ((remainingBytes != 4) ? 1 : 0) & ((remainingBytes != 5) ? 1 : 0)) != 0) {
/*      */       
/* 1205 */       hash = hash * ((offset == 0) ? -862048943 : 461845907) + PlatformDependent0.hashCodeAsciiSanitize(hashCodeAsciiSanitizeShort(bytes, offset));
/* 1206 */       offset += 2;
/*      */     } 
/* 1208 */     if (remainingBytes >= 4) {
/* 1209 */       return hash * (((((offset == 0) ? 1 : 0) | ((offset == 3) ? 1 : 0)) != 0) ? -862048943 : 461845907) + 
/* 1210 */         hashCodeAsciiSanitizeInt(bytes, offset);
/*      */     }
/* 1212 */     return hash;
/*      */   }
/*      */   
/*      */   private static final class Mpsc {
/*      */     private static final boolean USE_MPSC_CHUNKED_ARRAY_QUEUE;
/*      */     
/*      */     static {
/* 1219 */       Object unsafe = null;
/* 1220 */       if (PlatformDependent.hasUnsafe())
/*      */       {
/*      */ 
/*      */         
/* 1224 */         unsafe = AccessController.doPrivileged(new PrivilegedAction()
/*      */             {
/*      */               public Object run()
/*      */               {
/* 1228 */                 return UnsafeAccess.UNSAFE;
/*      */               }
/*      */             });
/*      */       }
/*      */       
/* 1233 */       if (unsafe == null) {
/* 1234 */         PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: unavailable");
/* 1235 */         USE_MPSC_CHUNKED_ARRAY_QUEUE = false;
/*      */       } else {
/* 1237 */         PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: available");
/* 1238 */         USE_MPSC_CHUNKED_ARRAY_QUEUE = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <T> Queue<T> newMpscQueue(int maxCapacity) {
/* 1246 */       int capacity = Math.max(Math.min(maxCapacity, 1073741824), 2048);
/* 1247 */       return newChunkedMpscQueue(1024, capacity);
/*      */     }
/*      */     
/*      */     static <T> Queue<T> newChunkedMpscQueue(int chunkSize, int capacity) {
/* 1251 */       return USE_MPSC_CHUNKED_ARRAY_QUEUE ? (Queue<T>)new MpscChunkedArrayQueue(chunkSize, capacity) : 
/* 1252 */         (Queue<T>)new MpscChunkedAtomicArrayQueue(chunkSize, capacity);
/*      */     }
/*      */     
/*      */     static <T> Queue<T> newMpscQueue() {
/* 1256 */       return USE_MPSC_CHUNKED_ARRAY_QUEUE ? (Queue<T>)new MpscUnboundedArrayQueue(1024) : 
/* 1257 */         (Queue<T>)new MpscUnboundedAtomicArrayQueue(1024);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Queue<T> newMpscQueue() {
/* 1267 */     return Mpsc.newMpscQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Queue<T> newMpscQueue(int maxCapacity) {
/* 1275 */     return Mpsc.newMpscQueue(maxCapacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Queue<T> newMpscQueue(int chunkSize, int maxCapacity) {
/* 1284 */     return Mpsc.newChunkedMpscQueue(chunkSize, maxCapacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Queue<T> newSpscQueue() {
/* 1292 */     return hasUnsafe() ? (Queue<T>)new SpscLinkedQueue() : (Queue<T>)new SpscLinkedAtomicQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Queue<T> newFixedMpscQueue(int capacity) {
/* 1300 */     return hasUnsafe() ? (Queue<T>)new MpscArrayQueue(capacity) : (Queue<T>)new MpscAtomicArrayQueue(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Queue<T> newFixedMpscUnpaddedQueue(int capacity) {
/* 1309 */     return hasUnsafe() ? (Queue<T>)new MpscUnpaddedArrayQueue(capacity) : (Queue<T>)new MpscAtomicUnpaddedArrayQueue(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Queue<T> newFixedMpmcQueue(int capacity) {
/* 1317 */     return hasUnsafe() ? (Queue<T>)new MpmcArrayQueue(capacity) : (Queue<T>)new MpmcAtomicArrayQueue(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassLoader getClassLoader(Class<?> clazz) {
/* 1324 */     return PlatformDependent0.getClassLoader(clazz);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassLoader getContextClassLoader() {
/* 1331 */     return PlatformDependent0.getContextClassLoader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassLoader getSystemClassLoader() {
/* 1338 */     return PlatformDependent0.getSystemClassLoader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <C> Deque<C> newConcurrentDeque() {
/* 1345 */     return new ConcurrentLinkedDeque<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static Random threadLocalRandom() {
/* 1354 */     return ThreadLocalRandom.current();
/*      */   }
/*      */   
/*      */   private static boolean isWindows0() {
/* 1358 */     boolean windows = "windows".equals(NORMALIZED_OS);
/* 1359 */     if (windows) {
/* 1360 */       logger.debug("Platform: Windows");
/*      */     }
/* 1362 */     return windows;
/*      */   }
/*      */   
/*      */   private static boolean isOsx0() {
/* 1366 */     boolean osx = "osx".equals(NORMALIZED_OS);
/* 1367 */     if (osx) {
/* 1368 */       logger.debug("Platform: MacOS");
/*      */     }
/* 1370 */     return osx;
/*      */   }
/*      */   
/*      */   private static boolean maybeSuperUser0() {
/* 1374 */     String username = SystemPropertyUtil.get("user.name");
/* 1375 */     if (isWindows()) {
/* 1376 */       return "Administrator".equals(username);
/*      */     }
/*      */     
/* 1379 */     return ("root".equals(username) || "toor".equals(username));
/*      */   }
/*      */   
/*      */   private static Throwable unsafeUnavailabilityCause0() {
/* 1383 */     if (isAndroid()) {
/* 1384 */       logger.debug("sun.misc.Unsafe: unavailable (Android)");
/* 1385 */       return new UnsupportedOperationException("sun.misc.Unsafe: unavailable (Android)");
/*      */     } 
/*      */     
/* 1388 */     if (isIkvmDotNet()) {
/* 1389 */       logger.debug("sun.misc.Unsafe: unavailable (IKVM.NET)");
/* 1390 */       return new UnsupportedOperationException("sun.misc.Unsafe: unavailable (IKVM.NET)");
/*      */     } 
/*      */     
/* 1393 */     Throwable cause = PlatformDependent0.getUnsafeUnavailabilityCause();
/* 1394 */     if (cause != null) {
/* 1395 */       return cause;
/*      */     }
/*      */     
/*      */     try {
/* 1399 */       boolean hasUnsafe = PlatformDependent0.hasUnsafe();
/* 1400 */       logger.debug("sun.misc.Unsafe: {}", hasUnsafe ? "available" : "unavailable");
/* 1401 */       return null;
/* 1402 */     } catch (Throwable t) {
/* 1403 */       logger.trace("Could not determine if Unsafe is available", t);
/*      */       
/* 1405 */       return new UnsupportedOperationException("Could not determine if Unsafe is available", t);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJ9Jvm() {
/* 1414 */     return IS_J9_JVM;
/*      */   }
/*      */   
/*      */   private static boolean isJ9Jvm0() {
/* 1418 */     String vmName = SystemPropertyUtil.get("java.vm.name", "").toLowerCase();
/* 1419 */     return (vmName.startsWith("ibm j9") || vmName.startsWith("eclipse openj9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIkvmDotNet() {
/* 1426 */     return IS_IVKVM_DOT_NET;
/*      */   }
/*      */   
/*      */   private static boolean isIkvmDotNet0() {
/* 1430 */     String vmName = SystemPropertyUtil.get("java.vm.name", "").toUpperCase(Locale.US);
/* 1431 */     return vmName.equals("IKVM.NET");
/*      */   }
/*      */ 
/*      */   
/*      */   private static Pattern getMaxDirectMemorySizeArgPattern() {
/* 1436 */     Pattern pattern = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN;
/* 1437 */     if (pattern == null) {
/* 1438 */       pattern = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
/* 1439 */       MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = pattern;
/*      */     } 
/* 1441 */     return pattern;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long estimateMaxDirectMemory() {
/* 1455 */     long maxDirectMemory = PlatformDependent0.bitsMaxDirectMemory();
/* 1456 */     if (maxDirectMemory > 0L) {
/* 1457 */       return maxDirectMemory;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1463 */       ClassLoader systemClassLoader = getSystemClassLoader();
/* 1464 */       Class<?> mgmtFactoryClass = Class.forName("java.lang.management.ManagementFactory", true, systemClassLoader);
/*      */       
/* 1466 */       Class<?> runtimeClass = Class.forName("java.lang.management.RuntimeMXBean", true, systemClassLoader);
/*      */ 
/*      */       
/* 1469 */       MethodHandles.Lookup lookup = MethodHandles.publicLookup();
/* 1470 */       MethodHandle getRuntime = lookup.findStatic(mgmtFactoryClass, "getRuntimeMXBean", 
/* 1471 */           MethodType.methodType(runtimeClass));
/* 1472 */       MethodHandle getInputArguments = lookup.findVirtual(runtimeClass, "getInputArguments", 
/* 1473 */           MethodType.methodType(List.class));
/* 1474 */       List<String> vmArgs = getInputArguments.invoke(getRuntime.invoke());
/*      */       
/* 1476 */       Pattern maxDirectMemorySizeArgPattern = getMaxDirectMemorySizeArgPattern();
/*      */       
/* 1478 */       for (int i = vmArgs.size() - 1; i >= 0; ) {
/* 1479 */         Matcher m = maxDirectMemorySizeArgPattern.matcher(vmArgs.get(i));
/* 1480 */         if (!m.matches()) {
/*      */           i--;
/*      */           continue;
/*      */         } 
/* 1484 */         maxDirectMemory = Long.parseLong(m.group(1));
/* 1485 */         switch (m.group(2).charAt(0)) { case 'K':
/*      */           case 'k':
/* 1487 */             maxDirectMemory *= 1024L; break;
/*      */           case 'M':
/*      */           case 'm':
/* 1490 */             maxDirectMemory *= 1048576L; break;
/*      */           case 'G':
/*      */           case 'g':
/* 1493 */             maxDirectMemory *= 1073741824L;
/*      */             break; }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 1500 */     } catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */     
/* 1504 */     if (maxDirectMemory <= 0L) {
/* 1505 */       maxDirectMemory = Runtime.getRuntime().maxMemory();
/* 1506 */       logger.debug("maxDirectMemory: {} bytes (maybe)", Long.valueOf(maxDirectMemory));
/*      */     } else {
/* 1508 */       logger.debug("maxDirectMemory: {} bytes", Long.valueOf(maxDirectMemory));
/*      */     } 
/*      */     
/* 1511 */     return maxDirectMemory;
/*      */   }
/*      */   
/*      */   private static File tmpdir0() {
/*      */     File f;
/*      */     try {
/* 1517 */       f = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
/* 1518 */       if (f != null) {
/* 1519 */         logger.debug("-Dio.netty.tmpdir: {}", f);
/* 1520 */         return f;
/*      */       } 
/*      */       
/* 1523 */       f = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
/* 1524 */       if (f != null) {
/* 1525 */         logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", f);
/* 1526 */         return f;
/*      */       } 
/*      */ 
/*      */       
/* 1530 */       if (isWindows()) {
/* 1531 */         f = toDirectory(System.getenv("TEMP"));
/* 1532 */         if (f != null) {
/* 1533 */           logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", f);
/* 1534 */           return f;
/*      */         } 
/*      */         
/* 1537 */         String userprofile = System.getenv("USERPROFILE");
/* 1538 */         if (userprofile != null) {
/* 1539 */           f = toDirectory(userprofile + "\\AppData\\Local\\Temp");
/* 1540 */           if (f != null) {
/* 1541 */             logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", f);
/* 1542 */             return f;
/*      */           } 
/*      */           
/* 1545 */           f = toDirectory(userprofile + "\\Local Settings\\Temp");
/* 1546 */           if (f != null) {
/* 1547 */             logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", f);
/* 1548 */             return f;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1552 */         f = toDirectory(System.getenv("TMPDIR"));
/* 1553 */         if (f != null) {
/* 1554 */           logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", f);
/* 1555 */           return f;
/*      */         } 
/*      */       } 
/* 1558 */     } catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1563 */     if (isWindows()) {
/* 1564 */       f = new File("C:\\Windows\\Temp");
/*      */     } else {
/* 1566 */       f = new File("/tmp");
/*      */     } 
/*      */     
/* 1569 */     logger.warn("Failed to get the temporary directory; falling back to: {}", f);
/* 1570 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   private static File toDirectory(String path) {
/* 1575 */     if (path == null) {
/* 1576 */       return null;
/*      */     }
/*      */     
/* 1579 */     File f = new File(path);
/* 1580 */     f.mkdirs();
/*      */     
/* 1582 */     if (!f.isDirectory()) {
/* 1583 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1587 */       return f.getAbsoluteFile();
/* 1588 */     } catch (Exception ignored) {
/* 1589 */       return f;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int bitMode0() {
/* 1595 */     int bitMode = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
/* 1596 */     if (bitMode > 0) {
/* 1597 */       logger.debug("-Dio.netty.bitMode: {}", Integer.valueOf(bitMode));
/* 1598 */       return bitMode;
/*      */     } 
/*      */ 
/*      */     
/* 1602 */     bitMode = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
/* 1603 */     if (bitMode > 0) {
/* 1604 */       logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", Integer.valueOf(bitMode));
/* 1605 */       return bitMode;
/*      */     } 
/* 1607 */     bitMode = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
/* 1608 */     if (bitMode > 0) {
/* 1609 */       logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", Integer.valueOf(bitMode));
/* 1610 */       return bitMode;
/*      */     } 
/*      */ 
/*      */     
/* 1614 */     String arch = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
/* 1615 */     if ("amd64".equals(arch) || "x86_64".equals(arch)) {
/* 1616 */       bitMode = 64;
/* 1617 */     } else if ("i386".equals(arch) || "i486".equals(arch) || "i586".equals(arch) || "i686".equals(arch)) {
/* 1618 */       bitMode = 32;
/*      */     } 
/*      */     
/* 1621 */     if (bitMode > 0) {
/* 1622 */       logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", Integer.valueOf(bitMode), arch);
/*      */     }
/*      */ 
/*      */     
/* 1626 */     String vm = SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US);
/* 1627 */     Pattern bitPattern = Pattern.compile("([1-9][0-9]+)-?bit");
/* 1628 */     Matcher m = bitPattern.matcher(vm);
/* 1629 */     if (m.find()) {
/* 1630 */       return Integer.parseInt(m.group(1));
/*      */     }
/* 1632 */     return 64;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int addressSize0() {
/* 1637 */     if (!hasUnsafe()) {
/* 1638 */       return -1;
/*      */     }
/* 1640 */     return PlatformDependent0.addressSize();
/*      */   }
/*      */   
/*      */   private static long byteArrayBaseOffset0() {
/* 1644 */     if (!hasUnsafe()) {
/* 1645 */       return -1L;
/*      */     }
/* 1647 */     return PlatformDependent0.byteArrayBaseOffset();
/*      */   }
/*      */   
/*      */   private static boolean equalsSafe(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
/* 1651 */     int end = startPos1 + length;
/* 1652 */     for (; startPos1 < end; startPos1++, startPos2++) {
/* 1653 */       if (bytes1[startPos1] != bytes2[startPos2]) {
/* 1654 */         return false;
/*      */       }
/*      */     } 
/* 1657 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean isZeroSafe(byte[] bytes, int startPos, int length) {
/* 1661 */     int end = startPos + length;
/* 1662 */     for (; startPos < end; startPos++) {
/* 1663 */       if (bytes[startPos] != 0) {
/* 1664 */         return false;
/*      */       }
/*      */     } 
/* 1667 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int hashCodeAsciiSafe(byte[] bytes, int startPos, int length) {
/* 1674 */     int hash = -1028477387;
/* 1675 */     int remainingBytes = length & 0x7;
/* 1676 */     int end = startPos + remainingBytes;
/* 1677 */     for (int i = startPos - 8 + length; i >= end; i -= 8) {
/* 1678 */       hash = PlatformDependent0.hashCodeAsciiCompute(getLongSafe(bytes, i), hash);
/*      */     }
/* 1680 */     switch (remainingBytes) {
/*      */       case 7:
/* 1682 */         return ((hash * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos])) * 461845907 + 
/* 1683 */           PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos + 1))) * -862048943 + 
/* 1684 */           PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos + 3));
/*      */       case 6:
/* 1686 */         return (hash * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos))) * 461845907 + 
/* 1687 */           PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos + 2));
/*      */       case 5:
/* 1689 */         return (hash * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos])) * 461845907 + 
/* 1690 */           PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos + 1));
/*      */       case 4:
/* 1692 */         return hash * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos));
/*      */       case 3:
/* 1694 */         return (hash * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos])) * 461845907 + 
/* 1695 */           PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos + 1));
/*      */       case 2:
/* 1697 */         return hash * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos));
/*      */       case 1:
/* 1699 */         return hash * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos]);
/*      */     } 
/* 1701 */     return hash;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String normalizedArch() {
/* 1706 */     return NORMALIZED_ARCH;
/*      */   }
/*      */   
/*      */   public static String normalizedOs() {
/* 1710 */     return NORMALIZED_OS;
/*      */   }
/*      */   
/*      */   public static Set<String> normalizedLinuxClassifiers() {
/* 1714 */     return LINUX_OS_CLASSIFIERS;
/*      */   }
/*      */   
/*      */   public static File createTempFile(String prefix, String suffix, File directory) throws IOException {
/* 1718 */     if (directory == null) {
/* 1719 */       return Files.createTempFile(prefix, suffix, (FileAttribute<?>[])new FileAttribute[0]).toFile();
/*      */     }
/* 1721 */     return Files.createTempFile(directory.toPath(), prefix, suffix, (FileAttribute<?>[])new FileAttribute[0]).toFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addClassifier(Set<String> dest, String... maybeClassifiers) {
/* 1731 */     for (String id : maybeClassifiers) {
/* 1732 */       if (isAllowedClassifier(id)) {
/* 1733 */         dest.add(id);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isAllowedClassifier(String classifier) {
/* 1739 */     switch (classifier) {
/*      */       case "fedora":
/*      */       case "suse":
/*      */       case "arch":
/* 1743 */         return true;
/*      */     } 
/* 1745 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String normalizeOsReleaseVariableValue(String value) {
/* 1751 */     String trimmed = value.trim();
/* 1752 */     StringBuilder sb = new StringBuilder(trimmed.length());
/* 1753 */     for (int i = 0; i < trimmed.length(); i++) {
/* 1754 */       char c = trimmed.charAt(i);
/* 1755 */       if (c != '"' && c != '\'') {
/* 1756 */         sb.append(c);
/*      */       }
/*      */     } 
/* 1759 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static String normalize(String value) {
/* 1764 */     StringBuilder sb = new StringBuilder(value.length());
/* 1765 */     for (int i = 0; i < value.length(); i++) {
/* 1766 */       char c = Character.toLowerCase(value.charAt(i));
/* 1767 */       if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
/* 1768 */         sb.append(c);
/*      */       }
/*      */     } 
/* 1771 */     return sb.toString();
/*      */   }
/*      */   
/*      */   private static String normalizeArch(String value) {
/* 1775 */     value = normalize(value);
/* 1776 */     switch (value) {
/*      */       case "x8664":
/*      */       case "amd64":
/*      */       case "ia32e":
/*      */       case "em64t":
/*      */       case "x64":
/* 1782 */         return "x86_64";
/*      */       
/*      */       case "x8632":
/*      */       case "x86":
/*      */       case "i386":
/*      */       case "i486":
/*      */       case "i586":
/*      */       case "i686":
/*      */       case "ia32":
/*      */       case "x32":
/* 1792 */         return "x86_32";
/*      */       
/*      */       case "ia64":
/*      */       case "itanium64":
/* 1796 */         return "itanium_64";
/*      */       
/*      */       case "sparc":
/*      */       case "sparc32":
/* 1800 */         return "sparc_32";
/*      */       
/*      */       case "sparcv9":
/*      */       case "sparc64":
/* 1804 */         return "sparc_64";
/*      */       
/*      */       case "arm":
/*      */       case "arm32":
/* 1808 */         return "arm_32";
/*      */       
/*      */       case "aarch64":
/* 1811 */         return "aarch_64";
/*      */       
/*      */       case "riscv64":
/* 1814 */         return "riscv64";
/*      */       
/*      */       case "ppc":
/*      */       case "ppc32":
/* 1818 */         return "ppc_32";
/*      */       
/*      */       case "ppc64":
/* 1821 */         return "ppc_64";
/*      */       
/*      */       case "ppc64le":
/* 1824 */         return "ppcle_64";
/*      */       
/*      */       case "s390":
/* 1827 */         return "s390_32";
/*      */       
/*      */       case "s390x":
/* 1830 */         return "s390_64";
/*      */       
/*      */       case "loongarch64":
/* 1833 */         return "loongarch_64";
/*      */     } 
/*      */     
/* 1836 */     return "unknown";
/*      */   }
/*      */ 
/*      */   
/*      */   private static String normalizeOs(String value) {
/* 1841 */     value = normalize(value);
/* 1842 */     if (value.startsWith("aix")) {
/* 1843 */       return "aix";
/*      */     }
/* 1845 */     if (value.startsWith("hpux")) {
/* 1846 */       return "hpux";
/*      */     }
/* 1848 */     if (value.startsWith("os400"))
/*      */     {
/* 1850 */       if (value.length() <= 5 || !Character.isDigit(value.charAt(5))) {
/* 1851 */         return "os400";
/*      */       }
/*      */     }
/* 1854 */     if (value.startsWith("linux")) {
/* 1855 */       return "linux";
/*      */     }
/* 1857 */     if (value.startsWith("macosx") || value.startsWith("osx") || value.startsWith("darwin")) {
/* 1858 */       return "osx";
/*      */     }
/* 1860 */     if (value.startsWith("freebsd")) {
/* 1861 */       return "freebsd";
/*      */     }
/* 1863 */     if (value.startsWith("openbsd")) {
/* 1864 */       return "openbsd";
/*      */     }
/* 1866 */     if (value.startsWith("netbsd")) {
/* 1867 */       return "netbsd";
/*      */     }
/* 1869 */     if (value.startsWith("solaris") || value.startsWith("sunos")) {
/* 1870 */       return "sunos";
/*      */     }
/* 1872 */     if (value.startsWith("windows")) {
/* 1873 */       return "windows";
/*      */     }
/*      */     
/* 1876 */     return "unknown";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJfrEnabled() {
/* 1883 */     return JFR;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\PlatformDependent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */