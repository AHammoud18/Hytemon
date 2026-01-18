/*      */ package io.netty.util.internal;
/*      */ 
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandles;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class PlatformDependent0
/*      */ {
/*      */   private static final InternalLogger logger;
/*      */   private static final long ADDRESS_FIELD_OFFSET;
/*      */   private static final long BYTE_ARRAY_BASE_OFFSET;
/*      */   private static final long INT_ARRAY_BASE_OFFSET;
/*      */   private static final long INT_ARRAY_INDEX_SCALE;
/*      */   private static final long LONG_ARRAY_BASE_OFFSET;
/*      */   private static final long LONG_ARRAY_INDEX_SCALE;
/*      */   private static final MethodHandle DIRECT_BUFFER_CONSTRUCTOR;
/*      */   private static final MethodHandle ALLOCATE_ARRAY_METHOD;
/*      */   private static final MethodHandle ALIGN_SLICE;
/*      */   private static final MethodHandle OFFSET_SLICE;
/*      */   private static final boolean IS_ANDROID;
/*      */   private static final int JAVA_VERSION;
/*      */   
/*      */   static {
/*   41 */     logger = InternalLoggerFactory.getInstance(PlatformDependent0.class);
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
/*   52 */     IS_ANDROID = isAndroid0();
/*   53 */     JAVA_VERSION = javaVersion0();
/*   54 */     EXPLICIT_NO_UNSAFE_CAUSE = explicitNoUnsafeCause0();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   60 */     RUNNING_IN_NATIVE_IMAGE = SystemPropertyUtil.contains("org.graalvm.nativeimage.imagecode");
/*      */ 
/*      */     
/*   63 */     IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE = explicitTryReflectionSetAccessible0();
/*      */ 
/*      */     
/*   66 */     IS_VIRTUAL_THREAD_METHOD_HANDLE = getIsVirtualThreadMethodHandle();
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
/*   86 */     final MethodHandles.Lookup lookup = MethodHandles.lookup();
/*      */     
/*   88 */     Field addressField = null;
/*   89 */     MethodHandle allocateArrayMethod = null;
/*      */     
/*      */     Throwable unsafeUnavailabilityCause;
/*   92 */     if ((unsafeUnavailabilityCause = EXPLICIT_NO_UNSAFE_CAUSE) != null) {
/*   93 */       direct = null;
/*   94 */       addressField = null;
/*   95 */       unsafe = null;
/*      */     } else {
/*   97 */       direct = ByteBuffer.allocateDirect(1);
/*      */ 
/*      */       
/*  100 */       Object maybeUnsafe = AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/*      */               try {
/*  104 */                 Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
/*      */ 
/*      */                 
/*  107 */                 Throwable cause = ReflectionUtil.trySetAccessible(unsafeField, false);
/*  108 */                 if (cause != null) {
/*  109 */                   return cause;
/*      */                 }
/*      */                 
/*  112 */                 return unsafeField.get(null);
/*  113 */               } catch (NoSuchFieldException|IllegalAccessException|SecurityException e) {
/*  114 */                 return e;
/*  115 */               } catch (NoClassDefFoundError e) {
/*      */ 
/*      */                 
/*  118 */                 return e;
/*      */               } 
/*      */             }
/*      */           });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  127 */       if (maybeUnsafe instanceof Throwable) {
/*  128 */         unsafe = null;
/*  129 */         unsafeUnavailabilityCause = (Throwable)maybeUnsafe;
/*  130 */         if (logger.isTraceEnabled()) {
/*  131 */           logger.debug("sun.misc.Unsafe.theUnsafe: unavailable", unsafeUnavailabilityCause);
/*      */         } else {
/*  133 */           logger.debug("sun.misc.Unsafe.theUnsafe: unavailable: {}", unsafeUnavailabilityCause.getMessage());
/*      */         } 
/*      */       } else {
/*  136 */         unsafe = (Unsafe)maybeUnsafe;
/*  137 */         logger.debug("sun.misc.Unsafe.theUnsafe: available");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  145 */       if (unsafe != null) {
/*  146 */         final Unsafe finalUnsafe = unsafe;
/*  147 */         Object maybeException = AccessController.doPrivileged(new PrivilegedAction()
/*      */             {
/*      */               public Object run()
/*      */               {
/*      */                 try {
/*  152 */                   Class<? extends Unsafe> cls = (Class)finalUnsafe.getClass();
/*  153 */                   cls.getDeclaredMethod("copyMemory", new Class[] { Object.class, long.class, Object.class, long.class, long.class });
/*      */                   
/*  155 */                   if (PlatformDependent0.javaVersion() > 23) {
/*  156 */                     cls.getDeclaredMethod("objectFieldOffset", new Class[] { Field.class });
/*  157 */                     cls.getDeclaredMethod("staticFieldOffset", new Class[] { Field.class });
/*  158 */                     cls.getDeclaredMethod("staticFieldBase", new Class[] { Field.class });
/*  159 */                     cls.getDeclaredMethod("arrayBaseOffset", new Class[] { Class.class });
/*  160 */                     cls.getDeclaredMethod("arrayIndexScale", new Class[] { Class.class });
/*  161 */                     cls.getDeclaredMethod("allocateMemory", new Class[] { long.class });
/*  162 */                     cls.getDeclaredMethod("reallocateMemory", new Class[] { long.class, long.class });
/*  163 */                     cls.getDeclaredMethod("freeMemory", new Class[] { long.class });
/*  164 */                     cls.getDeclaredMethod("setMemory", new Class[] { long.class, long.class, byte.class });
/*  165 */                     cls.getDeclaredMethod("setMemory", new Class[] { Object.class, long.class, long.class, byte.class });
/*  166 */                     cls.getDeclaredMethod("getBoolean", new Class[] { Object.class, long.class });
/*  167 */                     cls.getDeclaredMethod("getByte", new Class[] { long.class });
/*  168 */                     cls.getDeclaredMethod("getByte", new Class[] { Object.class, long.class });
/*  169 */                     cls.getDeclaredMethod("getInt", new Class[] { long.class });
/*  170 */                     cls.getDeclaredMethod("getInt", new Class[] { Object.class, long.class });
/*  171 */                     cls.getDeclaredMethod("getLong", new Class[] { long.class });
/*  172 */                     cls.getDeclaredMethod("getLong", new Class[] { Object.class, long.class });
/*  173 */                     cls.getDeclaredMethod("putByte", new Class[] { long.class, byte.class });
/*  174 */                     cls.getDeclaredMethod("putByte", new Class[] { Object.class, long.class, byte.class });
/*  175 */                     cls.getDeclaredMethod("putInt", new Class[] { long.class, int.class });
/*  176 */                     cls.getDeclaredMethod("putInt", new Class[] { Object.class, long.class, int.class });
/*  177 */                     cls.getDeclaredMethod("putLong", new Class[] { long.class, long.class });
/*  178 */                     cls.getDeclaredMethod("putLong", new Class[] { Object.class, long.class, long.class });
/*  179 */                     cls.getDeclaredMethod("addressSize", new Class[0]);
/*      */                   } 
/*  181 */                   if (PlatformDependent0.javaVersion() >= 23) {
/*      */ 
/*      */                     
/*  184 */                     long address = finalUnsafe.allocateMemory(8L);
/*  185 */                     finalUnsafe.putLong(address, 42L);
/*  186 */                     finalUnsafe.freeMemory(address);
/*      */                   } 
/*  188 */                   return null;
/*  189 */                 } catch (UnsupportedOperationException|SecurityException|NoSuchMethodException e) {
/*  190 */                   return e;
/*      */                 } 
/*      */               }
/*      */             });
/*      */         
/*  195 */         if (maybeException == null) {
/*  196 */           logger.debug("sun.misc.Unsafe base methods: all available");
/*      */         } else {
/*      */           
/*  199 */           unsafe = null;
/*  200 */           unsafeUnavailabilityCause = (Throwable)maybeException;
/*  201 */           if (logger.isTraceEnabled()) {
/*  202 */             logger.debug("sun.misc.Unsafe method unavailable:", unsafeUnavailabilityCause);
/*      */           } else {
/*  204 */             logger.debug("sun.misc.Unsafe method unavailable: {}", unsafeUnavailabilityCause.getMessage());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  209 */       if (unsafe != null) {
/*  210 */         final Unsafe finalUnsafe = unsafe;
/*      */ 
/*      */         
/*  213 */         Object maybeAddressField = AccessController.doPrivileged(new PrivilegedAction()
/*      */             {
/*      */               public Object run() {
/*      */                 try {
/*  217 */                   Field field = Buffer.class.getDeclaredField("address");
/*      */ 
/*      */                   
/*  220 */                   long offset = finalUnsafe.objectFieldOffset(field);
/*  221 */                   long address = finalUnsafe.getLong(direct, offset);
/*      */ 
/*      */                   
/*  224 */                   if (address == 0L) {
/*  225 */                     return null;
/*      */                   }
/*  227 */                   return field;
/*  228 */                 } catch (NoSuchFieldException|SecurityException e) {
/*  229 */                   return e;
/*      */                 } 
/*      */               }
/*      */             });
/*      */         
/*  234 */         if (maybeAddressField instanceof Field) {
/*  235 */           addressField = (Field)maybeAddressField;
/*  236 */           logger.debug("java.nio.Buffer.address: available");
/*      */         } else {
/*  238 */           unsafeUnavailabilityCause = (Throwable)maybeAddressField;
/*  239 */           if (logger.isTraceEnabled()) {
/*  240 */             logger.debug("java.nio.Buffer.address: unavailable", (Throwable)maybeAddressField);
/*      */           } else {
/*  242 */             logger.debug("java.nio.Buffer.address: unavailable: {}", ((Throwable)maybeAddressField)
/*  243 */                 .getMessage());
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  248 */           unsafe = null;
/*      */         } 
/*      */       } 
/*      */       
/*  252 */       if (unsafe != null) {
/*      */ 
/*      */         
/*  255 */         long byteArrayIndexScale = unsafe.arrayIndexScale(byte[].class);
/*  256 */         if (byteArrayIndexScale != 1L) {
/*  257 */           logger.debug("unsafe.arrayIndexScale is {} (expected: 1). Not using unsafe.", Long.valueOf(byteArrayIndexScale));
/*  258 */           unsafeUnavailabilityCause = new UnsupportedOperationException("Unexpected unsafe.arrayIndexScale");
/*  259 */           unsafe = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*  263 */     UNSAFE_UNAVAILABILITY_CAUSE = unsafeUnavailabilityCause;
/*  264 */     UNSAFE = unsafe;
/*      */     
/*  266 */     if (unsafe == null) {
/*  267 */       ADDRESS_FIELD_OFFSET = -1L;
/*  268 */       BYTE_ARRAY_BASE_OFFSET = -1L;
/*  269 */       LONG_ARRAY_BASE_OFFSET = -1L;
/*  270 */       LONG_ARRAY_INDEX_SCALE = -1L;
/*  271 */       INT_ARRAY_BASE_OFFSET = -1L;
/*  272 */       INT_ARRAY_INDEX_SCALE = -1L;
/*  273 */       UNALIGNED = false;
/*  274 */       BITS_MAX_DIRECT_MEMORY = -1L;
/*  275 */       DIRECT_BUFFER_CONSTRUCTOR = null;
/*  276 */       ALLOCATE_ARRAY_METHOD = null;
/*      */     } else {
/*      */       MethodHandle directBufferConstructor; boolean unaligned;
/*  279 */       long address = -1L;
/*      */       
/*      */       try {
/*  282 */         Object maybeDirectBufferConstructor = AccessController.doPrivileged(new PrivilegedAction()
/*      */             {
/*      */               public Object run() {
/*      */                 try {
/*  286 */                   Class<? extends ByteBuffer> directClass = (Class)direct.getClass();
/*      */ 
/*      */                   
/*  289 */                   Constructor<?> constructor = (PlatformDependent0.javaVersion() >= 21) ? directClass.getDeclaredConstructor(new Class[] { long.class, long.class }) : directClass.getDeclaredConstructor(new Class[] { long.class, int.class });
/*  290 */                   Throwable cause = ReflectionUtil.trySetAccessible(constructor, true);
/*  291 */                   if (cause != null) {
/*  292 */                     return cause;
/*      */                   }
/*  294 */                   return lookup.unreflectConstructor(constructor)
/*  295 */                     .asType(MethodType.methodType(ByteBuffer.class, long.class, new Class[] { int.class }));
/*  296 */                 } catch (Throwable e) {
/*  297 */                   return e;
/*      */                 } 
/*      */               }
/*      */             });
/*      */         
/*  302 */         if (maybeDirectBufferConstructor instanceof MethodHandle) {
/*  303 */           address = UNSAFE.allocateMemory(1L);
/*      */           
/*      */           try {
/*  306 */             MethodHandle constructor = (MethodHandle)maybeDirectBufferConstructor;
/*  307 */             ByteBuffer ignore = constructor.invokeExact(address, 1);
/*  308 */             directBufferConstructor = constructor;
/*  309 */             logger.debug("direct buffer constructor: available");
/*  310 */           } catch (Throwable e) {
/*  311 */             directBufferConstructor = null;
/*      */           } 
/*      */         } else {
/*  314 */           if (logger.isTraceEnabled()) {
/*  315 */             logger.debug("direct buffer constructor: unavailable", (Throwable)maybeDirectBufferConstructor);
/*      */           } else {
/*      */             
/*  318 */             logger.debug("direct buffer constructor: unavailable: {}", ((Throwable)maybeDirectBufferConstructor)
/*  319 */                 .getMessage());
/*      */           } 
/*  321 */           directBufferConstructor = null;
/*      */         } 
/*      */       } finally {
/*  324 */         if (address != -1L) {
/*  325 */           UNSAFE.freeMemory(address);
/*      */         }
/*      */       } 
/*  328 */       DIRECT_BUFFER_CONSTRUCTOR = directBufferConstructor;
/*  329 */       ADDRESS_FIELD_OFFSET = objectFieldOffset(addressField);
/*  330 */       BYTE_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
/*  331 */       INT_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(int[].class);
/*  332 */       INT_ARRAY_INDEX_SCALE = UNSAFE.arrayIndexScale(int[].class);
/*  333 */       LONG_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(long[].class);
/*  334 */       LONG_ARRAY_INDEX_SCALE = UNSAFE.arrayIndexScale(long[].class);
/*      */ 
/*      */       
/*  337 */       final AtomicLong maybeMaxMemory = new AtomicLong(-1L);
/*  338 */       Object maybeUnaligned = AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run()
/*      */             {
/*      */               try {
/*  343 */                 Class<?> bitsClass = Class.forName("java.nio.Bits", false, PlatformDependent0.getSystemClassLoader());
/*  344 */                 int version = PlatformDependent0.javaVersion();
/*  345 */                 if (version >= 9) {
/*      */                   
/*  347 */                   String fieldName = (version >= 11) ? "MAX_MEMORY" : "maxMemory";
/*      */ 
/*      */                   
/*      */                   try {
/*  351 */                     Field maxMemoryField = bitsClass.getDeclaredField(fieldName);
/*  352 */                     if (maxMemoryField.getType() == long.class) {
/*  353 */                       long offset = PlatformDependent0.UNSAFE.staticFieldOffset(maxMemoryField);
/*  354 */                       Object object = PlatformDependent0.UNSAFE.staticFieldBase(maxMemoryField);
/*  355 */                       maybeMaxMemory.lazySet(PlatformDependent0.UNSAFE.getLong(object, offset));
/*      */                     } 
/*  357 */                   } catch (Throwable throwable) {}
/*      */ 
/*      */                   
/*  360 */                   fieldName = (version >= 11) ? "UNALIGNED" : "unaligned";
/*      */                   try {
/*  362 */                     Field unalignedField = bitsClass.getDeclaredField(fieldName);
/*  363 */                     if (unalignedField.getType() == boolean.class) {
/*  364 */                       long offset = PlatformDependent0.UNSAFE.staticFieldOffset(unalignedField);
/*  365 */                       Object object = PlatformDependent0.UNSAFE.staticFieldBase(unalignedField);
/*  366 */                       return Boolean.valueOf(PlatformDependent0.UNSAFE.getBoolean(object, offset));
/*      */                     }
/*      */                   
/*      */                   }
/*  370 */                   catch (NoSuchFieldException noSuchFieldException) {}
/*      */                 } 
/*      */ 
/*      */                 
/*  374 */                 Method unalignedMethod = bitsClass.getDeclaredMethod("unaligned", new Class[0]);
/*  375 */                 Throwable cause = ReflectionUtil.trySetAccessible(unalignedMethod, true);
/*  376 */                 if (cause != null) {
/*  377 */                   return cause;
/*      */                 }
/*  379 */                 return unalignedMethod.invoke(null, new Object[0]);
/*  380 */               } catch (NoSuchMethodException|SecurityException|IllegalAccessException|java.lang.reflect.InvocationTargetException|ClassNotFoundException e) {
/*      */                 
/*  382 */                 return e;
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/*  387 */       if (maybeUnaligned instanceof Boolean) {
/*  388 */         unaligned = ((Boolean)maybeUnaligned).booleanValue();
/*  389 */         logger.debug("java.nio.Bits.unaligned: available, {}", Boolean.valueOf(unaligned));
/*      */       } else {
/*  391 */         String arch = SystemPropertyUtil.get("os.arch", "");
/*      */         
/*  393 */         unaligned = arch.matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
/*  394 */         Throwable t = (Throwable)maybeUnaligned;
/*  395 */         if (logger.isTraceEnabled()) {
/*  396 */           logger.debug("java.nio.Bits.unaligned: unavailable, {}", Boolean.valueOf(unaligned), t);
/*      */         } else {
/*  398 */           logger.debug("java.nio.Bits.unaligned: unavailable, {}, {}", Boolean.valueOf(unaligned), t.getMessage());
/*      */         } 
/*      */       } 
/*      */       
/*  402 */       UNALIGNED = unaligned;
/*  403 */       BITS_MAX_DIRECT_MEMORY = (maybeMaxMemory.get() >= 0L) ? maybeMaxMemory.get() : -1L;
/*      */       
/*  405 */       if (javaVersion() >= 9) {
/*  406 */         Object maybeException = AccessController.doPrivileged(new PrivilegedAction()
/*      */             {
/*      */ 
/*      */               
/*      */               public Object run()
/*      */               {
/*      */                 try {
/*  413 */                   Class<?> cls = PlatformDependent0.getClassLoader(PlatformDependent0.class).loadClass("jdk.internal.misc.Unsafe");
/*  414 */                   return lookup.findStatic(cls, "getUnsafe", MethodType.methodType(cls)).invoke();
/*  415 */                 } catch (Throwable e) {
/*  416 */                   return e;
/*      */                 } 
/*      */               }
/*      */             });
/*  420 */         if (!(maybeException instanceof Throwable)) {
/*  421 */           final Object finalInternalUnsafe = maybeException;
/*  422 */           maybeException = AccessController.doPrivileged(new PrivilegedAction()
/*      */               {
/*      */                 public Object run() {
/*      */                   try {
/*  426 */                     Class<?> finalInternalUnsafeClass = finalInternalUnsafe.getClass();
/*  427 */                     return lookup.findVirtual(finalInternalUnsafeClass, "allocateUninitializedArray", 
/*      */ 
/*      */                         
/*  430 */                         MethodType.methodType(Object.class, Class.class, new Class[] { int.class }));
/*  431 */                   } catch (Throwable e) {
/*  432 */                     return e;
/*      */                   } 
/*      */                 }
/*      */               });
/*      */           
/*  437 */           if (maybeException instanceof MethodHandle) {
/*      */             try {
/*  439 */               MethodHandle m = (MethodHandle)maybeException;
/*  440 */               m = m.bindTo(finalInternalUnsafe);
/*  441 */               byte[] bytes = (byte[])m.invokeExact(byte.class, 8);
/*  442 */               assert bytes.length == 8;
/*  443 */               allocateArrayMethod = m;
/*  444 */             } catch (Throwable e) {
/*  445 */               maybeException = e;
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/*  450 */         if (maybeException instanceof Throwable) {
/*  451 */           if (logger.isTraceEnabled()) {
/*  452 */             logger.debug("jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable", (Throwable)maybeException);
/*      */           } else {
/*      */             
/*  455 */             logger.debug("jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable: {}", ((Throwable)maybeException)
/*  456 */                 .getMessage());
/*      */           } 
/*      */         } else {
/*  459 */           logger.debug("jdk.internal.misc.Unsafe.allocateUninitializedArray(int): available");
/*      */         } 
/*      */       } else {
/*  462 */         logger.debug("jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable prior to Java9");
/*      */       } 
/*  464 */       ALLOCATE_ARRAY_METHOD = allocateArrayMethod;
/*      */     } 
/*      */     
/*  467 */     if (javaVersion() > 9) {
/*  468 */       ALIGN_SLICE = AccessController.<MethodHandle>doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/*      */               try {
/*  472 */                 return MethodHandles.publicLookup().findVirtual(ByteBuffer.class, "alignedSlice", 
/*  473 */                     MethodType.methodType(ByteBuffer.class, int.class));
/*  474 */               } catch (Throwable e) {
/*  475 */                 return null;
/*      */               } 
/*      */             }
/*      */           });
/*      */     } else {
/*  480 */       ALIGN_SLICE = null;
/*      */     } 
/*      */     
/*  483 */     if (javaVersion() >= 13) {
/*  484 */       OFFSET_SLICE = AccessController.<MethodHandle>doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/*      */               try {
/*  488 */                 return MethodHandles.publicLookup().findVirtual(ByteBuffer.class, "slice", 
/*  489 */                     MethodType.methodType(ByteBuffer.class, int.class, new Class[] { int.class }));
/*  490 */               } catch (Throwable e) {
/*  491 */                 return null;
/*      */               } 
/*      */             }
/*      */           });
/*      */     } else {
/*  496 */       OFFSET_SLICE = null;
/*      */     } 
/*      */     
/*  499 */     logger.debug("java.nio.DirectByteBuffer.<init>(long, {int,long}): {}", 
/*  500 */         (DIRECT_BUFFER_CONSTRUCTOR != null) ? "available" : "unavailable"); } private static final Throwable EXPLICIT_NO_UNSAFE_CAUSE; private static final Throwable UNSAFE_UNAVAILABILITY_CAUSE; private static final boolean RUNNING_IN_NATIVE_IMAGE; private static final boolean IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE; static final MethodHandle IS_VIRTUAL_THREAD_METHOD_HANDLE; static final Unsafe UNSAFE; static final int HASH_CODE_ASCII_SEED = -1028477387; static final int HASH_CODE_C1 = -862048943; static final int HASH_CODE_C2 = 461845907; private static final long UNSAFE_COPY_THRESHOLD = 1048576L; private static final boolean UNALIGNED; private static final long BITS_MAX_DIRECT_MEMORY; static {
/*      */     final ByteBuffer direct;
/*      */     Unsafe unsafe;
/*      */   } private static MethodHandle getIsVirtualThreadMethodHandle() {
/*      */     try {
/*  505 */       MethodHandle methodHandle = MethodHandles.publicLookup().findVirtual(Thread.class, "isVirtual", 
/*  506 */           MethodType.methodType(boolean.class));
/*      */       
/*  508 */       boolean isVirtual = methodHandle.invokeExact(Thread.currentThread());
/*  509 */       return methodHandle;
/*  510 */     } catch (Throwable e) {
/*  511 */       if (logger.isTraceEnabled()) {
/*  512 */         logger.debug("Thread.isVirtual() is not available: ", e);
/*      */       } else {
/*  514 */         logger.debug("Thread.isVirtual() is not available: ", e.getMessage());
/*      */       } 
/*  516 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isVirtualThread(Thread thread) {
/*  525 */     if (thread == null || IS_VIRTUAL_THREAD_METHOD_HANDLE == null) {
/*  526 */       return false;
/*      */     }
/*      */     try {
/*  529 */       return IS_VIRTUAL_THREAD_METHOD_HANDLE.invokeExact(thread);
/*  530 */     } catch (Throwable t) {
/*      */       
/*  532 */       if (t instanceof Error) {
/*  533 */         throw (Error)t;
/*      */       }
/*  535 */       throw new Error(t);
/*      */     } 
/*      */   }
/*      */   
/*      */   static boolean isNativeImage() {
/*  540 */     return RUNNING_IN_NATIVE_IMAGE;
/*      */   }
/*      */   
/*      */   static boolean isExplicitNoUnsafe() {
/*  544 */     return (EXPLICIT_NO_UNSAFE_CAUSE != null);
/*      */   }
/*      */   private static Throwable explicitNoUnsafeCause0() {
/*      */     String unsafePropName;
/*  548 */     boolean explicitProperty = SystemPropertyUtil.contains("io.netty.noUnsafe");
/*  549 */     boolean noUnsafe = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
/*  550 */     logger.debug("-Dio.netty.noUnsafe: {}", Boolean.valueOf(noUnsafe));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  556 */     String reason = "io.netty.noUnsafe";
/*  557 */     String unspecified = "<unspecified>";
/*  558 */     String unsafeMemoryAccess = SystemPropertyUtil.get("sun.misc.unsafe.memory.access", unspecified);
/*  559 */     if (!explicitProperty && unspecified.equals(unsafeMemoryAccess) && javaVersion() >= 25) {
/*  560 */       reason = "io.netty.noUnsafe=true by default on Java 25+";
/*  561 */       noUnsafe = true;
/*  562 */     } else if (!"allow".equals(unsafeMemoryAccess) && !unspecified.equals(unsafeMemoryAccess)) {
/*  563 */       reason = "--sun-misc-unsafe-memory-access=" + unsafeMemoryAccess;
/*  564 */       noUnsafe = true;
/*      */     } 
/*      */     
/*  567 */     if (noUnsafe) {
/*  568 */       String msg = "sun.misc.Unsafe: unavailable (" + reason + ')';
/*  569 */       logger.debug(msg);
/*  570 */       return new UnsupportedOperationException(msg);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  575 */     if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
/*  576 */       unsafePropName = "io.netty.tryUnsafe";
/*      */     } else {
/*  578 */       unsafePropName = "org.jboss.netty.tryUnsafe";
/*      */     } 
/*      */     
/*  581 */     if (!SystemPropertyUtil.getBoolean(unsafePropName, true)) {
/*  582 */       String msg = "sun.misc.Unsafe: unavailable (" + unsafePropName + ')';
/*  583 */       logger.debug(msg);
/*  584 */       return new UnsupportedOperationException(msg);
/*      */     } 
/*      */     
/*  587 */     return null;
/*      */   }
/*      */   
/*      */   static boolean isUnaligned() {
/*  591 */     return UNALIGNED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long bitsMaxDirectMemory() {
/*  598 */     return BITS_MAX_DIRECT_MEMORY;
/*      */   }
/*      */   
/*      */   static boolean hasUnsafe() {
/*  602 */     return (UNSAFE != null);
/*      */   }
/*      */   
/*      */   static Throwable getUnsafeUnavailabilityCause() {
/*  606 */     return UNSAFE_UNAVAILABILITY_CAUSE;
/*      */   }
/*      */   
/*      */   static boolean unalignedAccess() {
/*  610 */     return UNALIGNED;
/*      */   }
/*      */   
/*      */   static void throwException(Throwable cause) {
/*  614 */     throwException0(cause);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <E extends Throwable> void throwException0(Throwable t) throws E {
/*  619 */     throw (E)t;
/*      */   }
/*      */   
/*      */   static boolean hasDirectBufferNoCleanerConstructor() {
/*  623 */     return (DIRECT_BUFFER_CONSTRUCTOR != null);
/*      */   }
/*      */   
/*      */   static ByteBuffer reallocateDirectNoCleaner(ByteBuffer buffer, int capacity) {
/*  627 */     return newDirectBuffer(UNSAFE.reallocateMemory(directBufferAddress(buffer), capacity), capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ByteBuffer allocateDirectNoCleaner(int capacity) {
/*  634 */     return newDirectBuffer(UNSAFE.allocateMemory(Math.max(1, capacity)), capacity);
/*      */   }
/*      */   
/*      */   static boolean hasAlignSliceMethod() {
/*  638 */     return (ALIGN_SLICE != null);
/*      */   }
/*      */   
/*      */   static ByteBuffer alignSlice(ByteBuffer buffer, int alignment) {
/*      */     try {
/*  643 */       return ALIGN_SLICE.invokeExact(buffer, alignment);
/*  644 */     } catch (Throwable e) {
/*  645 */       rethrowIfPossible(e);
/*  646 */       throw new LinkageError("ByteBuffer.alignedSlice not available", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static boolean hasOffsetSliceMethod() {
/*  651 */     return (OFFSET_SLICE != null);
/*      */   }
/*      */   
/*      */   static ByteBuffer offsetSlice(ByteBuffer buffer, int index, int length) {
/*      */     try {
/*  656 */       return OFFSET_SLICE.invokeExact(buffer, index, length);
/*  657 */     } catch (Throwable e) {
/*  658 */       rethrowIfPossible(e);
/*  659 */       throw new LinkageError("ByteBuffer.slice(int, int) not available", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static boolean hasAllocateArrayMethod() {
/*  664 */     return (ALLOCATE_ARRAY_METHOD != null);
/*      */   }
/*      */   
/*      */   static byte[] allocateUninitializedArray(int size) {
/*      */     try {
/*  669 */       return (byte[])ALLOCATE_ARRAY_METHOD.invokeExact(byte.class, size);
/*  670 */     } catch (Throwable e) {
/*  671 */       rethrowIfPossible(e);
/*  672 */       throw new LinkageError("Unsafe.allocateUninitializedArray not available", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static ByteBuffer newDirectBuffer(long address, int capacity) {
/*  677 */     ObjectUtil.checkPositiveOrZero(capacity, "capacity");
/*      */     
/*      */     try {
/*  680 */       return DIRECT_BUFFER_CONSTRUCTOR.invokeExact(address, capacity);
/*  681 */     } catch (Throwable cause) {
/*  682 */       rethrowIfPossible(cause);
/*  683 */       throw new LinkageError("DirectByteBuffer constructor not available", cause);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void rethrowIfPossible(Throwable cause) {
/*  688 */     if (cause instanceof Error) {
/*  689 */       throw (Error)cause;
/*      */     }
/*  691 */     if (cause instanceof RuntimeException) {
/*  692 */       throw (RuntimeException)cause;
/*      */     }
/*      */   }
/*      */   
/*      */   static long directBufferAddress(ByteBuffer buffer) {
/*  697 */     return getLong(buffer, ADDRESS_FIELD_OFFSET);
/*      */   }
/*      */   
/*      */   static long byteArrayBaseOffset() {
/*  701 */     return BYTE_ARRAY_BASE_OFFSET;
/*      */   }
/*      */   
/*      */   static Object getObject(Object object, long fieldOffset) {
/*  705 */     return UNSAFE.getObject(object, fieldOffset);
/*      */   }
/*      */   
/*      */   static int getInt(Object object, long fieldOffset) {
/*  709 */     return UNSAFE.getInt(object, fieldOffset);
/*      */   }
/*      */   
/*      */   static int getIntVolatile(Object object, long fieldOffset) {
/*  713 */     return UNSAFE.getIntVolatile(object, fieldOffset);
/*      */   }
/*      */   
/*      */   static void putOrderedInt(Object object, long fieldOffset, int value) {
/*  717 */     UNSAFE.putOrderedInt(object, fieldOffset, value);
/*      */   }
/*      */   
/*      */   static int getAndAddInt(Object object, long fieldOffset, int value) {
/*  721 */     return UNSAFE.getAndAddInt(object, fieldOffset, value);
/*      */   }
/*      */   
/*      */   static boolean compareAndSwapInt(Object object, long fieldOffset, int expected, int value) {
/*  725 */     return UNSAFE.compareAndSwapInt(object, fieldOffset, expected, value);
/*      */   }
/*      */   
/*      */   static void safeConstructPutInt(Object object, long fieldOffset, int value) {
/*  729 */     UNSAFE.putInt(object, fieldOffset, value);
/*  730 */     UNSAFE.storeFence();
/*      */   }
/*      */   
/*      */   private static long getLong(Object object, long fieldOffset) {
/*  734 */     return UNSAFE.getLong(object, fieldOffset);
/*      */   }
/*      */   
/*      */   static long objectFieldOffset(Field field) {
/*  738 */     return UNSAFE.objectFieldOffset(field);
/*      */   }
/*      */   
/*      */   static byte getByte(long address) {
/*  742 */     return UNSAFE.getByte(address);
/*      */   }
/*      */   
/*      */   static short getShort(long address) {
/*  746 */     return UNSAFE.getShort(address);
/*      */   }
/*      */   
/*      */   static int getInt(long address) {
/*  750 */     return UNSAFE.getInt(address);
/*      */   }
/*      */   
/*      */   static long getLong(long address) {
/*  754 */     return UNSAFE.getLong(address);
/*      */   }
/*      */   
/*      */   static byte getByte(byte[] data, int index) {
/*  758 */     return UNSAFE.getByte(data, BYTE_ARRAY_BASE_OFFSET + index);
/*      */   }
/*      */   
/*      */   static byte getByte(byte[] data, long index) {
/*  762 */     return UNSAFE.getByte(data, BYTE_ARRAY_BASE_OFFSET + index);
/*      */   }
/*      */   
/*      */   static short getShort(byte[] data, int index) {
/*  766 */     return UNSAFE.getShort(data, BYTE_ARRAY_BASE_OFFSET + index);
/*      */   }
/*      */   
/*      */   static int getInt(byte[] data, int index) {
/*  770 */     return UNSAFE.getInt(data, BYTE_ARRAY_BASE_OFFSET + index);
/*      */   }
/*      */   
/*      */   static int getInt(int[] data, long index) {
/*  774 */     return UNSAFE.getInt(data, INT_ARRAY_BASE_OFFSET + INT_ARRAY_INDEX_SCALE * index);
/*      */   }
/*      */   
/*      */   static long getLong(byte[] data, int index) {
/*  778 */     return UNSAFE.getLong(data, BYTE_ARRAY_BASE_OFFSET + index);
/*      */   }
/*      */   
/*      */   static long getLong(long[] data, long index) {
/*  782 */     return UNSAFE.getLong(data, LONG_ARRAY_BASE_OFFSET + LONG_ARRAY_INDEX_SCALE * index);
/*      */   }
/*      */   
/*      */   static void putByte(long address, byte value) {
/*  786 */     UNSAFE.putByte(address, value);
/*      */   }
/*      */   
/*      */   static void putShort(long address, short value) {
/*  790 */     UNSAFE.putShort(address, value);
/*      */   }
/*      */   
/*      */   static void putShortOrdered(long address, short newValue) {
/*  794 */     UNSAFE.storeFence();
/*  795 */     UNSAFE.putShort(null, address, newValue);
/*      */   }
/*      */   
/*      */   static void putInt(long address, int value) {
/*  799 */     UNSAFE.putInt(address, value);
/*      */   }
/*      */   
/*      */   static void putLong(long address, long value) {
/*  803 */     UNSAFE.putLong(address, value);
/*      */   }
/*      */   
/*      */   static void putByte(byte[] data, int index, byte value) {
/*  807 */     UNSAFE.putByte(data, BYTE_ARRAY_BASE_OFFSET + index, value);
/*      */   }
/*      */   
/*      */   static void putByte(Object data, long offset, byte value) {
/*  811 */     UNSAFE.putByte(data, offset, value);
/*      */   }
/*      */   
/*      */   static void putShort(byte[] data, int index, short value) {
/*  815 */     UNSAFE.putShort(data, BYTE_ARRAY_BASE_OFFSET + index, value);
/*      */   }
/*      */   
/*      */   static void putInt(byte[] data, int index, int value) {
/*  819 */     UNSAFE.putInt(data, BYTE_ARRAY_BASE_OFFSET + index, value);
/*      */   }
/*      */   
/*      */   static void putLong(byte[] data, int index, long value) {
/*  823 */     UNSAFE.putLong(data, BYTE_ARRAY_BASE_OFFSET + index, value);
/*      */   }
/*      */   
/*      */   static void putObject(Object o, long offset, Object x) {
/*  827 */     UNSAFE.putObject(o, offset, x);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyMemory(long srcAddr, long dstAddr, long length) {
/*  833 */     if (javaVersion() <= 8) {
/*  834 */       copyMemoryWithSafePointPolling(srcAddr, dstAddr, length);
/*      */     } else {
/*  836 */       UNSAFE.copyMemory(srcAddr, dstAddr, length);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void copyMemoryWithSafePointPolling(long srcAddr, long dstAddr, long length) {
/*  841 */     while (length > 0L) {
/*  842 */       long size = Math.min(length, 1048576L);
/*  843 */       UNSAFE.copyMemory(srcAddr, dstAddr, size);
/*  844 */       length -= size;
/*  845 */       srcAddr += size;
/*  846 */       dstAddr += size;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyMemory(Object src, long srcOffset, Object dst, long dstOffset, long length) {
/*  853 */     if (javaVersion() <= 8) {
/*  854 */       copyMemoryWithSafePointPolling(src, srcOffset, dst, dstOffset, length);
/*      */     } else {
/*  856 */       UNSAFE.copyMemory(src, srcOffset, dst, dstOffset, length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void copyMemoryWithSafePointPolling(Object src, long srcOffset, Object dst, long dstOffset, long length) {
/*  862 */     while (length > 0L) {
/*  863 */       long size = Math.min(length, 1048576L);
/*  864 */       UNSAFE.copyMemory(src, srcOffset, dst, dstOffset, size);
/*  865 */       length -= size;
/*  866 */       srcOffset += size;
/*  867 */       dstOffset += size;
/*      */     } 
/*      */   }
/*      */   
/*      */   static void setMemory(long address, long bytes, byte value) {
/*  872 */     UNSAFE.setMemory(address, bytes, value);
/*      */   }
/*      */   
/*      */   static void setMemory(Object o, long offset, long bytes, byte value) {
/*  876 */     UNSAFE.setMemory(o, offset, bytes, value);
/*      */   }
/*      */   
/*      */   static boolean equals(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
/*  880 */     int remainingBytes = length & 0x7;
/*  881 */     long baseOffset1 = BYTE_ARRAY_BASE_OFFSET + startPos1;
/*  882 */     long diff = (startPos2 - startPos1);
/*  883 */     if (length >= 8) {
/*  884 */       long end = baseOffset1 + remainingBytes; long i;
/*  885 */       for (i = baseOffset1 - 8L + length; i >= end; i -= 8L) {
/*  886 */         if (UNSAFE.getLong(bytes1, i) != UNSAFE.getLong(bytes2, i + diff)) {
/*  887 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*  891 */     if (remainingBytes >= 4) {
/*  892 */       remainingBytes -= 4;
/*  893 */       long pos = baseOffset1 + remainingBytes;
/*  894 */       if (UNSAFE.getInt(bytes1, pos) != UNSAFE.getInt(bytes2, pos + diff)) {
/*  895 */         return false;
/*      */       }
/*      */     } 
/*  898 */     long baseOffset2 = baseOffset1 + diff;
/*  899 */     if (remainingBytes >= 2) {
/*  900 */       return (UNSAFE.getChar(bytes1, baseOffset1) == UNSAFE.getChar(bytes2, baseOffset2) && (remainingBytes == 2 || UNSAFE
/*      */         
/*  902 */         .getByte(bytes1, baseOffset1 + 2L) == UNSAFE.getByte(bytes2, baseOffset2 + 2L)));
/*      */     }
/*  904 */     return (remainingBytes == 0 || UNSAFE
/*  905 */       .getByte(bytes1, baseOffset1) == UNSAFE.getByte(bytes2, baseOffset2));
/*      */   }
/*      */   
/*      */   static int equalsConstantTime(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
/*  909 */     long result = 0L;
/*  910 */     long remainingBytes = (length & 0x7);
/*  911 */     long baseOffset1 = BYTE_ARRAY_BASE_OFFSET + startPos1;
/*  912 */     long end = baseOffset1 + remainingBytes;
/*  913 */     long diff = (startPos2 - startPos1); long i;
/*  914 */     for (i = baseOffset1 - 8L + length; i >= end; i -= 8L) {
/*  915 */       result |= UNSAFE.getLong(bytes1, i) ^ UNSAFE.getLong(bytes2, i + diff);
/*      */     }
/*  917 */     if (remainingBytes >= 4L) {
/*  918 */       result |= (UNSAFE.getInt(bytes1, baseOffset1) ^ UNSAFE.getInt(bytes2, baseOffset1 + diff));
/*  919 */       remainingBytes -= 4L;
/*      */     } 
/*  921 */     if (remainingBytes >= 2L) {
/*  922 */       long pos = end - remainingBytes;
/*  923 */       result |= (UNSAFE.getChar(bytes1, pos) ^ UNSAFE.getChar(bytes2, pos + diff));
/*  924 */       remainingBytes -= 2L;
/*      */     } 
/*  926 */     if (remainingBytes == 1L) {
/*  927 */       long pos = end - 1L;
/*  928 */       result |= (UNSAFE.getByte(bytes1, pos) ^ UNSAFE.getByte(bytes2, pos + diff));
/*      */     } 
/*  930 */     return ConstantTimeUtils.equalsConstantTime(result, 0L);
/*      */   }
/*      */   
/*      */   static boolean isZero(byte[] bytes, int startPos, int length) {
/*  934 */     if (length <= 0) {
/*  935 */       return true;
/*      */     }
/*  937 */     long baseOffset = BYTE_ARRAY_BASE_OFFSET + startPos;
/*  938 */     int remainingBytes = length & 0x7;
/*  939 */     long end = baseOffset + remainingBytes; long i;
/*  940 */     for (i = baseOffset - 8L + length; i >= end; i -= 8L) {
/*  941 */       if (UNSAFE.getLong(bytes, i) != 0L) {
/*  942 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  946 */     if (remainingBytes >= 4) {
/*  947 */       remainingBytes -= 4;
/*  948 */       if (UNSAFE.getInt(bytes, baseOffset + remainingBytes) != 0) {
/*  949 */         return false;
/*      */       }
/*      */     } 
/*  952 */     if (remainingBytes >= 2) {
/*  953 */       return (UNSAFE.getChar(bytes, baseOffset) == '\000' && (remainingBytes == 2 || bytes[startPos + 2] == 0));
/*      */     }
/*      */     
/*  956 */     return (bytes[startPos] == 0);
/*      */   }
/*      */   
/*      */   static int hashCodeAscii(byte[] bytes, int startPos, int length) {
/*  960 */     int hash = -1028477387;
/*  961 */     long baseOffset = BYTE_ARRAY_BASE_OFFSET + startPos;
/*  962 */     int remainingBytes = length & 0x7;
/*  963 */     long end = baseOffset + remainingBytes; long i;
/*  964 */     for (i = baseOffset - 8L + length; i >= end; i -= 8L) {
/*  965 */       hash = hashCodeAsciiCompute(UNSAFE.getLong(bytes, i), hash);
/*      */     }
/*  967 */     if (remainingBytes == 0) {
/*  968 */       return hash;
/*      */     }
/*  970 */     int hcConst = -862048943;
/*  971 */     if ((((remainingBytes != 2) ? 1 : 0) & ((remainingBytes != 4) ? 1 : 0) & ((remainingBytes != 6) ? 1 : 0)) != 0) {
/*  972 */       hash = hash * -862048943 + hashCodeAsciiSanitize(UNSAFE.getByte(bytes, baseOffset));
/*  973 */       hcConst = 461845907;
/*  974 */       baseOffset++;
/*      */     } 
/*  976 */     if ((((remainingBytes != 1) ? 1 : 0) & ((remainingBytes != 4) ? 1 : 0) & ((remainingBytes != 5) ? 1 : 0)) != 0) {
/*  977 */       hash = hash * hcConst + hashCodeAsciiSanitize(UNSAFE.getShort(bytes, baseOffset));
/*  978 */       hcConst = (hcConst == -862048943) ? 461845907 : -862048943;
/*  979 */       baseOffset += 2L;
/*      */     } 
/*  981 */     if (remainingBytes >= 4) {
/*  982 */       return hash * hcConst + hashCodeAsciiSanitize(UNSAFE.getInt(bytes, baseOffset));
/*      */     }
/*  984 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int hashCodeAsciiCompute(long value, int hash) {
/*  990 */     return hash * -862048943 + 
/*      */       
/*  992 */       hashCodeAsciiSanitize((int)value) * 461845907 + (int)((value & 0x1F1F1F1F00000000L) >>> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int hashCodeAsciiSanitize(int value) {
/*  998 */     return value & 0x1F1F1F1F;
/*      */   }
/*      */   
/*      */   static int hashCodeAsciiSanitize(short value) {
/* 1002 */     return value & 0x1F1F;
/*      */   }
/*      */   
/*      */   static int hashCodeAsciiSanitize(byte value) {
/* 1006 */     return value & 0x1F;
/*      */   }
/*      */   
/*      */   static ClassLoader getClassLoader(final Class<?> clazz) {
/* 1010 */     if (System.getSecurityManager() == null) {
/* 1011 */       return clazz.getClassLoader();
/*      */     }
/* 1013 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*      */         {
/*      */           public ClassLoader run() {
/* 1016 */             return clazz.getClassLoader();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   static ClassLoader getContextClassLoader() {
/* 1023 */     if (System.getSecurityManager() == null) {
/* 1024 */       return Thread.currentThread().getContextClassLoader();
/*      */     }
/* 1026 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*      */         {
/*      */           public ClassLoader run() {
/* 1029 */             return Thread.currentThread().getContextClassLoader();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   static ClassLoader getSystemClassLoader() {
/* 1036 */     if (System.getSecurityManager() == null) {
/* 1037 */       return ClassLoader.getSystemClassLoader();
/*      */     }
/* 1039 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*      */         {
/*      */           public ClassLoader run() {
/* 1042 */             return ClassLoader.getSystemClassLoader();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   static int addressSize() {
/* 1049 */     return UNSAFE.addressSize();
/*      */   }
/*      */   
/*      */   static long allocateMemory(long size) {
/* 1053 */     return UNSAFE.allocateMemory(size);
/*      */   }
/*      */   
/*      */   static void freeMemory(long address) {
/* 1057 */     UNSAFE.freeMemory(address);
/*      */   }
/*      */   
/*      */   static long reallocateMemory(long address, long newSize) {
/* 1061 */     return UNSAFE.reallocateMemory(address, newSize);
/*      */   }
/*      */   
/*      */   static boolean isAndroid() {
/* 1065 */     return IS_ANDROID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAndroid0() {
/* 1076 */     String vmName = SystemPropertyUtil.get("java.vm.name");
/* 1077 */     boolean isAndroid = "Dalvik".equals(vmName);
/* 1078 */     if (isAndroid) {
/* 1079 */       logger.debug("Platform: Android");
/*      */     }
/* 1081 */     return isAndroid;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean explicitTryReflectionSetAccessible0() {
/* 1086 */     return SystemPropertyUtil.getBoolean("io.netty.tryReflectionSetAccessible", (
/* 1087 */         javaVersion() < 9 || RUNNING_IN_NATIVE_IMAGE));
/*      */   }
/*      */   
/*      */   static boolean isExplicitTryReflectionSetAccessible() {
/* 1091 */     return IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE;
/*      */   }
/*      */   
/*      */   static int javaVersion() {
/* 1095 */     return JAVA_VERSION;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int javaVersion0() {
/*      */     int majorVersion;
/* 1101 */     if (isAndroid()) {
/* 1102 */       majorVersion = 6;
/*      */     } else {
/* 1104 */       majorVersion = majorVersionFromJavaSpecificationVersion();
/*      */     } 
/*      */     
/* 1107 */     logger.debug("Java version: {}", Integer.valueOf(majorVersion));
/*      */     
/* 1109 */     return majorVersion;
/*      */   }
/*      */ 
/*      */   
/*      */   static int majorVersionFromJavaSpecificationVersion() {
/* 1114 */     return majorVersion(SystemPropertyUtil.get("java.specification.version", "1.6"));
/*      */   }
/*      */ 
/*      */   
/*      */   static int majorVersion(String javaSpecVersion) {
/* 1119 */     String[] components = javaSpecVersion.split("\\.");
/* 1120 */     int[] version = new int[components.length];
/* 1121 */     for (int i = 0; i < components.length; i++) {
/* 1122 */       version[i] = Integer.parseInt(components[i]);
/*      */     }
/*      */     
/* 1125 */     if (version[0] == 1) {
/* 1126 */       assert version[1] >= 6;
/* 1127 */       return version[1];
/*      */     } 
/* 1129 */     return version[0];
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\PlatformDependent0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */