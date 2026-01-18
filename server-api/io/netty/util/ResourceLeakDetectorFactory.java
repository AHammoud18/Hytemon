/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.reflect.Constructor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ResourceLeakDetectorFactory
/*     */ {
/*  31 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetectorFactory.class);
/*     */   
/*  33 */   private static volatile ResourceLeakDetectorFactory factoryInstance = new DefaultResourceLeakDetectorFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLeakDetectorFactory instance() {
/*  41 */     return factoryInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setResourceLeakDetectorFactory(ResourceLeakDetectorFactory factory) {
/*  52 */     factoryInstance = (ResourceLeakDetectorFactory)ObjectUtil.checkNotNull(factory, "factory");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource) {
/*  63 */     return newResourceLeakDetector(resource, ResourceLeakDetector.SAMPLING_INTERVAL);
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
/*     */   public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval) {
/*  91 */     ObjectUtil.checkPositive(samplingInterval, "samplingInterval");
/*  92 */     return newResourceLeakDetector(resource, samplingInterval, Long.MAX_VALUE);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public abstract <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> paramClass, int paramInt, long paramLong);
/*     */   
/*     */   private static final class DefaultResourceLeakDetectorFactory extends ResourceLeakDetectorFactory {
/*     */     private final Constructor<?> obsoleteCustomClassConstructor;
/*     */     private final Constructor<?> customClassConstructor;
/*     */     
/*     */     DefaultResourceLeakDetectorFactory() {
/*     */       String customLeakDetector;
/*     */       try {
/* 105 */         customLeakDetector = SystemPropertyUtil.get("io.netty.customResourceLeakDetector");
/* 106 */       } catch (Throwable cause) {
/* 107 */         ResourceLeakDetectorFactory.logger.error("Could not access System property: io.netty.customResourceLeakDetector", cause);
/* 108 */         customLeakDetector = null;
/*     */       } 
/* 110 */       if (customLeakDetector == null) {
/* 111 */         this.obsoleteCustomClassConstructor = this.customClassConstructor = null;
/*     */       } else {
/* 113 */         this.obsoleteCustomClassConstructor = obsoleteCustomClassConstructor(customLeakDetector);
/* 114 */         this.customClassConstructor = customClassConstructor(customLeakDetector);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static Constructor<?> obsoleteCustomClassConstructor(String customLeakDetector) {
/*     */       try {
/* 120 */         Class<?> detectorClass = Class.forName(customLeakDetector, true, 
/* 121 */             PlatformDependent.getSystemClassLoader());
/*     */         
/* 123 */         if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
/* 124 */           return detectorClass.getConstructor(new Class[] { Class.class, int.class, long.class });
/*     */         }
/* 126 */         ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", customLeakDetector);
/*     */       }
/* 128 */       catch (Throwable t) {
/* 129 */         ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, t);
/*     */       } 
/*     */       
/* 132 */       return null;
/*     */     }
/*     */     
/*     */     private static Constructor<?> customClassConstructor(String customLeakDetector) {
/*     */       try {
/* 137 */         Class<?> detectorClass = Class.forName(customLeakDetector, true, 
/* 138 */             PlatformDependent.getSystemClassLoader());
/*     */         
/* 140 */         if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
/* 141 */           return detectorClass.getConstructor(new Class[] { Class.class, int.class });
/*     */         }
/* 143 */         ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", customLeakDetector);
/*     */       }
/* 145 */       catch (Throwable t) {
/* 146 */         ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, t);
/*     */       } 
/*     */       
/* 149 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval, long maxActive) {
/* 156 */       if (this.obsoleteCustomClassConstructor != null) {
/*     */         
/*     */         try {
/*     */           
/* 160 */           ResourceLeakDetector<T> leakDetector = (ResourceLeakDetector<T>)this.obsoleteCustomClassConstructor.newInstance(new Object[] {
/* 161 */                 resource, Integer.valueOf(samplingInterval), Long.valueOf(maxActive) });
/* 162 */           ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", this.obsoleteCustomClassConstructor
/* 163 */               .getDeclaringClass().getName());
/* 164 */           return leakDetector;
/* 165 */         } catch (Throwable t) {
/* 166 */           ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", new Object[] { this.obsoleteCustomClassConstructor
/*     */                 
/* 168 */                 .getDeclaringClass().getName(), resource, t });
/*     */         } 
/*     */       }
/*     */       
/* 172 */       ResourceLeakDetector<T> resourceLeakDetector = new ResourceLeakDetector<>(resource, samplingInterval, maxActive);
/*     */       
/* 174 */       ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", resourceLeakDetector);
/* 175 */       return resourceLeakDetector;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval) {
/* 180 */       if (this.customClassConstructor != null) {
/*     */         
/*     */         try {
/*     */           
/* 184 */           ResourceLeakDetector<T> leakDetector = (ResourceLeakDetector<T>)this.customClassConstructor.newInstance(new Object[] { resource, Integer.valueOf(samplingInterval) });
/* 185 */           ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", this.customClassConstructor
/* 186 */               .getDeclaringClass().getName());
/* 187 */           return leakDetector;
/* 188 */         } catch (Throwable t) {
/* 189 */           ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", new Object[] { this.customClassConstructor
/*     */                 
/* 191 */                 .getDeclaringClass().getName(), resource, t });
/*     */         } 
/*     */       }
/*     */       
/* 195 */       ResourceLeakDetector<T> resourceLeakDetector = new ResourceLeakDetector<>(resource, samplingInterval);
/* 196 */       ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", resourceLeakDetector);
/* 197 */       return resourceLeakDetector;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ResourceLeakDetectorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */