/*    */ package io.sentry;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class OptionsContainer<T> {
/*    */   @NotNull
/*    */   public static <T> OptionsContainer<T> create(@NotNull Class<T> clazz) {
/* 11 */     return new OptionsContainer<>(clazz);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   private final Class<T> clazz;
/*    */   
/*    */   private OptionsContainer(@NotNull Class<T> clazz) {
/* 18 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public T createInstance() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
/* 26 */     return this.clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\OptionsContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */