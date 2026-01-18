/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.LoadClass;
/*    */ import io.sentry.util.Platform;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class SpanFactoryFactory
/*    */ {
/*    */   private static final String OTEL_SPAN_FACTORY = "io.sentry.opentelemetry.OtelSpanFactory";
/*    */   
/*    */   @NotNull
/*    */   public static ISpanFactory create(@NotNull LoadClass loadClass, @NotNull ILogger logger) {
/* 17 */     if (Platform.isJvm() && 
/* 18 */       loadClass.isClassAvailable("io.sentry.opentelemetry.OtelSpanFactory", logger)) {
/* 19 */       Class<?> otelSpanFactoryClazz = loadClass.loadClass("io.sentry.opentelemetry.OtelSpanFactory", logger);
/* 20 */       if (otelSpanFactoryClazz != null) {
/*    */         
/*    */         try {
/* 23 */           Object otelSpanFactory = otelSpanFactoryClazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 24 */           if (otelSpanFactory != null && otelSpanFactory instanceof ISpanFactory) {
/* 25 */             return (ISpanFactory)otelSpanFactory;
/*    */           }
/* 27 */         } catch (InstantiationException instantiationException) {
/*    */         
/* 29 */         } catch (IllegalAccessException illegalAccessException) {
/*    */         
/* 31 */         } catch (InvocationTargetException invocationTargetException) {
/*    */         
/* 33 */         } catch (NoSuchMethodException noSuchMethodException) {}
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     return new DefaultSpanFactory();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SpanFactoryFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */