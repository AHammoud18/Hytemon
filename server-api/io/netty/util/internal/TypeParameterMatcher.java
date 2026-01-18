/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TypeParameterMatcher
/*    */ {
/* 24 */   private static final TypeParameterMatcher NOOP = new TypeParameterMatcher()
/*    */     {
/*    */       public boolean match(Object msg) {
/* 27 */         return true;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public static TypeParameterMatcher get(Class<?> parameterType) {
/* 33 */     Map<Class<?>, TypeParameterMatcher> getCache = InternalThreadLocalMap.get().typeParameterMatcherGetCache();
/*    */     
/* 35 */     TypeParameterMatcher matcher = getCache.get(parameterType);
/* 36 */     if (matcher == null) {
/* 37 */       if (parameterType == Object.class) {
/* 38 */         matcher = NOOP;
/*    */       } else {
/* 40 */         matcher = new ReflectiveMatcher(parameterType);
/*    */       } 
/* 42 */       getCache.put(parameterType, matcher);
/*    */     } 
/*    */     
/* 45 */     return matcher;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static TypeParameterMatcher find(Object object, Class<?> parametrizedSuperclass, String typeParamName) {
/* 52 */     Map<Class<?>, Map<String, TypeParameterMatcher>> findCache = InternalThreadLocalMap.get().typeParameterMatcherFindCache();
/* 53 */     Class<?> thisClass = object.getClass();
/*    */     
/* 55 */     Map<String, TypeParameterMatcher> map = findCache.get(thisClass);
/* 56 */     if (map == null) {
/* 57 */       map = new HashMap<>();
/* 58 */       findCache.put(thisClass, map);
/*    */     } 
/*    */     
/* 61 */     TypeParameterMatcher matcher = map.get(typeParamName);
/* 62 */     if (matcher == null) {
/* 63 */       matcher = get(ReflectionUtil.resolveTypeParameter(object, parametrizedSuperclass, typeParamName));
/* 64 */       map.put(typeParamName, matcher);
/*    */     } 
/*    */     
/* 67 */     return matcher;
/*    */   }
/*    */   
/*    */   public abstract boolean match(Object paramObject);
/*    */   
/*    */   private static final class ReflectiveMatcher extends TypeParameterMatcher {
/*    */     private final Class<?> type;
/*    */     
/*    */     ReflectiveMatcher(Class<?> type) {
/* 76 */       this.type = type;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean match(Object msg) {
/* 81 */       return this.type.isInstance(msg);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\TypeParameterMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */