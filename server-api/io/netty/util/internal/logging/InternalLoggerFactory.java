/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InternalLoggerFactory
/*     */ {
/*     */   private static volatile InternalLoggerFactory defaultFactory;
/*     */   
/*     */   private static InternalLoggerFactory newDefaultFactory(String name) {
/*  42 */     InternalLoggerFactory f = useSlf4JLoggerFactory(name);
/*  43 */     if (f != null) {
/*  44 */       return f;
/*     */     }
/*     */     
/*  47 */     f = useLog4J2LoggerFactory(name);
/*  48 */     if (f != null) {
/*  49 */       return f;
/*     */     }
/*     */     
/*  52 */     f = useLog4JLoggerFactory(name);
/*  53 */     if (f != null) {
/*  54 */       return f;
/*     */     }
/*     */     
/*  57 */     return useJdkLoggerFactory(name);
/*     */   }
/*     */   
/*     */   private static InternalLoggerFactory useSlf4JLoggerFactory(String name) {
/*     */     try {
/*  62 */       InternalLoggerFactory f = Slf4JLoggerFactory.getInstanceWithNopCheck();
/*  63 */       f.newInstance(name).debug("Using SLF4J as the default logging framework");
/*  64 */       return f;
/*  65 */     } catch (LinkageError ignore) {
/*  66 */       return null;
/*  67 */     } catch (Exception ignore) {
/*     */       
/*  69 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static InternalLoggerFactory useLog4J2LoggerFactory(String name) {
/*     */     try {
/*  75 */       InternalLoggerFactory f = Log4J2LoggerFactory.INSTANCE;
/*  76 */       f.newInstance(name).debug("Using Log4J2 as the default logging framework");
/*  77 */       return f;
/*  78 */     } catch (LinkageError ignore) {
/*  79 */       return null;
/*  80 */     } catch (Exception ignore) {
/*     */       
/*  82 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static InternalLoggerFactory useLog4JLoggerFactory(String name) {
/*     */     try {
/*  88 */       InternalLoggerFactory f = Log4JLoggerFactory.INSTANCE;
/*  89 */       f.newInstance(name).debug("Using Log4J as the default logging framework");
/*  90 */       return f;
/*  91 */     } catch (LinkageError ignore) {
/*  92 */       return null;
/*  93 */     } catch (Exception ignore) {
/*     */       
/*  95 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static InternalLoggerFactory useJdkLoggerFactory(String name) {
/* 100 */     InternalLoggerFactory f = JdkLoggerFactory.INSTANCE;
/* 101 */     f.newInstance(name).debug("Using java.util.logging as the default logging framework");
/* 102 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InternalLoggerFactory getDefaultFactory() {
/* 110 */     if (defaultFactory == null) {
/* 111 */       defaultFactory = newDefaultFactory(InternalLoggerFactory.class.getName());
/*     */     }
/* 113 */     return defaultFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultFactory(InternalLoggerFactory defaultFactory) {
/* 120 */     InternalLoggerFactory.defaultFactory = (InternalLoggerFactory)ObjectUtil.checkNotNull(defaultFactory, "defaultFactory");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InternalLogger getInstance(Class<?> clazz) {
/* 127 */     return getInstance(clazz.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InternalLogger getInstance(String name) {
/* 134 */     return getDefaultFactory().newInstance(name);
/*     */   }
/*     */   
/*     */   protected abstract InternalLogger newInstance(String paramString);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\InternalLoggerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */