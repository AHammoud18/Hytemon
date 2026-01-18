/*     */ package org.jline.style;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Objects;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
/*     */ import org.jline.utils.AttributedString;
/*     */ import org.jline.utils.AttributedStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StyleBundleInvocationHandler
/*     */   implements InvocationHandler
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(StyleBundleInvocationHandler.class.getName());
/*     */   
/*     */   private final Class<? extends StyleBundle> type;
/*     */   
/*     */   private final StyleResolver resolver;
/*     */   
/*     */   public StyleBundleInvocationHandler(Class<? extends StyleBundle> type, StyleResolver resolver) {
/*  40 */     this.type = Objects.<Class<? extends StyleBundle>>requireNonNull(type);
/*  41 */     this.resolver = Objects.<StyleResolver>requireNonNull(resolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validate(Method method) {
/*  49 */     if (method.getParameterCount() != 1) {
/*  50 */       throw new InvalidStyleBundleMethodException(method, "Invalid parameters");
/*     */     }
/*     */ 
/*     */     
/*  54 */     if (method.getReturnType() != AttributedString.class) {
/*  55 */       throw new InvalidStyleBundleMethodException(method, "Invalid return-type");
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String emptyToNull(@Nullable String value) {
/*  61 */     if (value == null || value.isEmpty()) {
/*  62 */       return null;
/*     */     }
/*  64 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String getStyleGroup(Class<?> type) {
/*  72 */     StyleBundle.StyleGroup styleGroup = type.<StyleBundle.StyleGroup>getAnnotation(StyleBundle.StyleGroup.class);
/*  73 */     return (styleGroup != null) ? emptyToNull(styleGroup.value().trim()) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getStyleName(Method method) {
/*  84 */     StyleBundle.StyleName styleName = method.<StyleBundle.StyleName>getAnnotation(StyleBundle.StyleName.class);
/*  85 */     return (styleName != null) ? emptyToNull(styleName.value().trim()) : method.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String getDefaultStyle(Method method) {
/*  93 */     StyleBundle.DefaultStyle defaultStyle = method.<StyleBundle.DefaultStyle>getAnnotation(StyleBundle.DefaultStyle.class);
/*     */     
/*  95 */     return (defaultStyle != null) ? emptyToNull(defaultStyle.value()) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends StyleBundle> T create(StyleResolver resolver, Class<T> type) {
/* 105 */     Objects.requireNonNull(resolver);
/* 106 */     Objects.requireNonNull(type);
/*     */     
/* 108 */     if (log.isLoggable(Level.FINEST)) {
/* 109 */       log.finest(String.format("Using style-group: %s for type: %s", new Object[] { resolver.getGroup(), type.getName() }));
/*     */     }
/*     */     
/* 112 */     StyleBundleInvocationHandler handler = new StyleBundleInvocationHandler(type, resolver);
/* 113 */     return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class[] { type }, handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends StyleBundle> T create(StyleSource source, Class<T> type) {
/* 122 */     Objects.requireNonNull(type);
/*     */     
/* 124 */     String group = getStyleGroup(type);
/* 125 */     if (group == null) {
/* 126 */       throw new InvalidStyleGroupException(type);
/*     */     }
/*     */     
/* 129 */     return create(new StyleResolver(source, group), type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 139 */     if (method.getDeclaringClass() == Object.class) {
/* 140 */       return method.invoke(this, args);
/*     */     }
/*     */ 
/*     */     
/* 144 */     validate(method);
/*     */ 
/*     */     
/* 147 */     String styleName = getStyleName(method);
/*     */ 
/*     */     
/* 150 */     String style = this.resolver.getSource().get(this.resolver.getGroup(), styleName);
/* 151 */     if (log.isLoggable(Level.FINEST)) {
/* 152 */       log.finest(String.format("Sourced-style: %s -> %s", new Object[] { styleName, style }));
/*     */     }
/*     */     
/* 155 */     if (style == null) {
/* 156 */       style = getDefaultStyle(method);
/*     */ 
/*     */       
/* 159 */       if (style == null) {
/* 160 */         throw new StyleBundleMethodMissingDefaultStyleException(method);
/*     */       }
/*     */     } 
/*     */     
/* 164 */     String value = String.valueOf(args[0]);
/* 165 */     if (log.isLoggable(Level.FINEST)) {
/* 166 */       log.finest(String.format("Applying style: %s -> %s to: %s", new Object[] { styleName, style, value }));
/*     */     }
/*     */     
/* 169 */     AttributedStyle astyle = this.resolver.resolve(style);
/* 170 */     return new AttributedString(value, astyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 178 */     return this.type.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class StyleBundleMethodMissingDefaultStyleException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StyleBundleMethodMissingDefaultStyleException(Method method) {
/* 194 */       super(String.format("%s method missing @%s: %s", new Object[] { StyleBundle.class
/*     */               
/* 196 */               .getSimpleName(), StyleBundle.DefaultStyle.class.getSimpleName(), method }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class InvalidStyleBundleMethodException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     public InvalidStyleBundleMethodException(Method method, String message) {
/* 209 */       super(message + ": " + method);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class InvalidStyleGroupException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     public InvalidStyleGroupException(Class<?> type) {
/* 222 */       super(String.format("%s missing or invalid @%s: %s", new Object[] { StyleBundle.class
/*     */               
/* 224 */               .getSimpleName(), StyleBundle.StyleGroup.class.getSimpleName(), type.getName() }));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\StyleBundleInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */