/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZlibCodecFactory
/*     */ {
/*  27 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
/*     */   
/*     */   private static final int DEFAULT_JDK_WINDOW_SIZE = 15;
/*     */   
/*     */   private static final int DEFAULT_JDK_MEM_LEVEL = 8;
/*     */ 
/*     */   
/*     */   static {
/*     */     boolean jzlibAvailable;
/*     */   }
/*     */   
/*  38 */   private static final boolean noJdkZlibDecoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibDecoder", false); static {
/*  39 */     logger.debug("-Dio.netty.noJdkZlibDecoder: {}", Boolean.valueOf(noJdkZlibDecoder));
/*     */   }
/*  41 */   private static final boolean noJdkZlibEncoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibEncoder", false); static {
/*  42 */     logger.debug("-Dio.netty.noJdkZlibEncoder: {}", Boolean.valueOf(noJdkZlibEncoder));
/*     */ 
/*     */     
/*     */     try {
/*  46 */       Class.forName("com.jcraft.jzlib.JZlib", false, 
/*  47 */           PlatformDependent.getClassLoader(ZlibCodecFactory.class));
/*  48 */       jzlibAvailable = true;
/*  49 */     } catch (ClassNotFoundException t) {
/*  50 */       jzlibAvailable = false;
/*  51 */       logger.debug("JZlib not in the classpath; the only window bits supported value will be 15");
/*     */     } 
/*     */ 
/*     */     
/*  55 */     JZLIB_AVAILABLE = jzlibAvailable;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final boolean JZLIB_AVAILABLE;
/*     */   
/*     */   public static boolean isSupportingWindowSizeAndMemLevel() {
/*  62 */     return JZLIB_AVAILABLE;
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel) {
/*  66 */     if (noJdkZlibEncoder) {
/*  67 */       return new JZlibEncoder(compressionLevel);
/*     */     }
/*  69 */     return new JdkZlibEncoder(compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper) {
/*  74 */     if (noJdkZlibEncoder) {
/*  75 */       return new JZlibEncoder(wrapper);
/*     */     }
/*  77 */     return new JdkZlibEncoder(wrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  82 */     if (noJdkZlibEncoder) {
/*  83 */       return new JZlibEncoder(wrapper, compressionLevel);
/*     */     }
/*  85 */     return new JdkZlibEncoder(wrapper, compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
/*  90 */     if (noJdkZlibEncoder || windowBits != 15 || memLevel != 8)
/*     */     {
/*  92 */       return new JZlibEncoder(wrapper, compressionLevel, windowBits, memLevel);
/*     */     }
/*  94 */     return new JdkZlibEncoder(wrapper, compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(byte[] dictionary) {
/*  99 */     if (noJdkZlibEncoder) {
/* 100 */       return new JZlibEncoder(dictionary);
/*     */     }
/* 102 */     return new JdkZlibEncoder(dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel, byte[] dictionary) {
/* 107 */     if (noJdkZlibEncoder) {
/* 108 */       return new JZlibEncoder(compressionLevel, dictionary);
/*     */     }
/* 110 */     return new JdkZlibEncoder(compressionLevel, dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
/* 115 */     if (noJdkZlibEncoder || windowBits != 15 || memLevel != 8)
/*     */     {
/* 117 */       return new JZlibEncoder(compressionLevel, windowBits, memLevel, dictionary);
/*     */     }
/* 119 */     return new JdkZlibEncoder(compressionLevel, dictionary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static ZlibDecoder newZlibDecoder() {
/* 130 */     return newZlibDecoder(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(int maxAllocation) {
/* 141 */     if (noJdkZlibDecoder) {
/* 142 */       return new JZlibDecoder(maxAllocation);
/*     */     }
/* 144 */     return new JdkZlibDecoder(true, maxAllocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static ZlibDecoder newZlibDecoder(ZlibWrapper wrapper) {
/* 155 */     return newZlibDecoder(wrapper, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(ZlibWrapper wrapper, int maxAllocation) {
/* 166 */     if (noJdkZlibDecoder) {
/* 167 */       return new JZlibDecoder(wrapper, maxAllocation);
/*     */     }
/* 169 */     return new JdkZlibDecoder(wrapper, true, maxAllocation);
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
/*     */   @Deprecated
/*     */   public static ZlibDecoder newZlibDecoder(byte[] dictionary) {
/* 182 */     return newZlibDecoder(dictionary, 0);
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
/*     */   public static ZlibDecoder newZlibDecoder(byte[] dictionary, int maxAllocation) {
/* 195 */     if (noJdkZlibDecoder) {
/* 196 */       return new JZlibDecoder(dictionary, maxAllocation);
/*     */     }
/* 198 */     return new JdkZlibDecoder(dictionary, maxAllocation);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZlibCodecFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */