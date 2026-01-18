/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Signals
/*     */ {
/*     */   public static Object register(String name, Runnable handler) {
/*  83 */     Objects.requireNonNull(handler);
/*  84 */     return register(name, handler, handler.getClass().getClassLoader());
/*     */   }
/*     */   
/*     */   public static Object register(String name, Runnable handler, ClassLoader loader) {
/*     */     try {
/*  89 */       Class<?> signalHandlerClass = Class.forName("sun.misc.SignalHandler");
/*     */ 
/*     */       
/*  92 */       Object signalHandler = Proxy.newProxyInstance(loader, new Class[] { signalHandlerClass }, (proxy, method, args) -> {
/*     */             if (method.getDeclaringClass() == Object.class) {
/*     */               if ("toString".equals(method.getName())) {
/*     */                 return handler.toString();
/*     */               }
/*     */             } else if (method.getDeclaringClass() == signalHandlerClass) {
/*     */               Log.trace(());
/*     */               
/*     */               handler.run();
/*     */             } 
/*     */             return null;
/*     */           });
/* 104 */       return doRegister(name, signalHandler);
/* 105 */     } catch (Exception e) {
/*     */       
/* 107 */       Log.debug(new Object[] { "Error registering handler for signal ", name, e });
/* 108 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object registerDefault(String name) {
/*     */     try {
/* 114 */       Class<?> signalHandlerClass = Class.forName("sun.misc.SignalHandler");
/* 115 */       return doRegister(name, signalHandlerClass.getField("SIG_DFL").get(null));
/* 116 */     } catch (Exception e) {
/*     */       
/* 118 */       Log.debug(new Object[] { "Error registering default handler for signal ", name, e });
/* 119 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unregister(String name, Object previous) {
/*     */     try {
/* 126 */       if (previous != null) {
/* 127 */         doRegister(name, previous);
/*     */       }
/* 129 */     } catch (Exception e) {
/*     */       
/* 131 */       Log.debug(new Object[] { "Error unregistering handler for signal ", name, e });
/*     */     } 
/*     */   }
/*     */   private static Object doRegister(String name, Object handler) throws Exception {
/*     */     Object signal;
/* 136 */     Log.trace(() -> "Registering signal " + name + " with handler " + toString(handler));
/* 137 */     Class<?> signalClass = Class.forName("sun.misc.Signal");
/* 138 */     Constructor<?> constructor = signalClass.getConstructor(new Class[] { String.class });
/*     */     
/*     */     try {
/* 141 */       signal = constructor.newInstance(new Object[] { name });
/* 142 */     } catch (InvocationTargetException e) {
/* 143 */       if (e.getCause() instanceof IllegalArgumentException) {
/* 144 */         Log.trace(() -> "Ignoring unsupported signal " + name);
/*     */       } else {
/* 146 */         Log.debug(new Object[] { "Error registering handler for signal ", name, e });
/*     */       } 
/* 148 */       return null;
/*     */     } 
/* 150 */     Class<?> signalHandlerClass = Class.forName("sun.misc.SignalHandler");
/* 151 */     return signalClass.getMethod("handle", new Class[] { signalClass, signalHandlerClass }).invoke(null, new Object[] { signal, handler });
/*     */   }
/*     */ 
/*     */   
/*     */   private static String toString(Object handler) {
/*     */     try {
/* 157 */       Class<?> signalHandlerClass = Class.forName("sun.misc.SignalHandler");
/* 158 */       if (handler == signalHandlerClass.getField("SIG_DFL").get(null)) {
/* 159 */         return "SIG_DFL";
/*     */       }
/* 161 */       if (handler == signalHandlerClass.getField("SIG_IGN").get(null)) {
/* 162 */         return "SIG_IGN";
/*     */       }
/* 164 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 167 */     return (handler != null) ? handler.toString() : "null";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Signals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */