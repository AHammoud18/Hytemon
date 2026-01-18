/*     */ package com.nimbusds.jose.shaded.gson.internal;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.InstanceCreator;
/*     */ import com.nimbusds.jose.shaded.gson.JsonIOException;
/*     */ import com.nimbusds.jose.shaded.gson.ReflectionAccessFilter;
/*     */ import com.nimbusds.jose.shaded.gson.internal.reflect.ReflectionHelper;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentSkipListMap;
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
/*     */ public final class ConstructorConstructor
/*     */ {
/*     */   private final Map<Type, InstanceCreator<?>> instanceCreators;
/*     */   private final boolean useJdkUnsafe;
/*     */   private final List<ReflectionAccessFilter> reflectionFilters;
/*     */   
/*     */   public ConstructorConstructor(Map<Type, InstanceCreator<?>> instanceCreators, boolean useJdkUnsafe, List<ReflectionAccessFilter> reflectionFilters) {
/*  54 */     this.instanceCreators = instanceCreators;
/*  55 */     this.useJdkUnsafe = useJdkUnsafe;
/*  56 */     this.reflectionFilters = reflectionFilters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String checkInstantiable(Class<?> c) {
/*  67 */     int modifiers = c.getModifiers();
/*  68 */     if (Modifier.isInterface(modifiers)) {
/*  69 */       return "Interfaces can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Interface name: " + c
/*     */         
/*  71 */         .getName();
/*     */     }
/*  73 */     if (Modifier.isAbstract(modifiers))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       return "Abstract classes can't be instantiated! Adjust the R8 configuration or register an InstanceCreator or a TypeAdapter for this type. Class name: " + c
/*     */         
/*  84 */         .getName() + "\nSee " + 
/*     */         
/*  86 */         TroubleshootingGuide.createUrl("r8-abstract-class");
/*     */     }
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) {
/*  93 */     return get(typeToken, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObjectConstructor<T> get(TypeToken<T> typeToken, boolean allowUnsafe) {
/* 104 */     Type type = typeToken.getType();
/* 105 */     Class<? super T> rawType = typeToken.getRawType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     InstanceCreator<T> typeCreator = (InstanceCreator<T>)this.instanceCreators.get(type);
/* 111 */     if (typeCreator != null) {
/* 112 */       return () -> typeCreator.createInstance(type);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 117 */     InstanceCreator<T> rawTypeCreator = (InstanceCreator<T>)this.instanceCreators.get(rawType);
/* 118 */     if (rawTypeCreator != null) {
/* 119 */       return () -> rawTypeCreator.createInstance(type);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     ObjectConstructor<T> specialConstructor = newSpecialCollectionConstructor(type, rawType);
/* 126 */     if (specialConstructor != null) {
/* 127 */       return specialConstructor;
/*     */     }
/*     */ 
/*     */     
/* 131 */     ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, rawType);
/* 132 */     ObjectConstructor<T> defaultConstructor = newDefaultConstructor(rawType, filterResult);
/* 133 */     if (defaultConstructor != null) {
/* 134 */       return defaultConstructor;
/*     */     }
/*     */     
/* 137 */     ObjectConstructor<T> defaultImplementation = newDefaultImplementationConstructor(type, rawType);
/* 138 */     if (defaultImplementation != null) {
/* 139 */       return defaultImplementation;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 144 */     String exceptionMessage = checkInstantiable(rawType);
/* 145 */     if (exceptionMessage != null) {
/* 146 */       return () -> {
/*     */           throw new JsonIOException(exceptionMessage);
/*     */         };
/*     */     }
/*     */     
/* 151 */     if (!allowUnsafe) {
/* 152 */       String message = "Unable to create instance of " + rawType + "; Register an InstanceCreator or a TypeAdapter for this type.";
/*     */ 
/*     */ 
/*     */       
/* 156 */       return () -> {
/*     */           throw new JsonIOException(message);
/*     */         };
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (filterResult != ReflectionAccessFilter.FilterResult.ALLOW) {
/* 164 */       String message = "Unable to create instance of " + rawType + "; ReflectionAccessFilter does not permit using reflection or Unsafe. Register an InstanceCreator or a TypeAdapter for this type or adjust the access filter to allow using reflection.";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       return () -> {
/*     */           throw new JsonIOException(message);
/*     */         };
/*     */     } 
/*     */ 
/*     */     
/* 176 */     return newUnsafeAllocator(rawType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> ObjectConstructor<T> newSpecialCollectionConstructor(Type type, Class<? super T> rawType) {
/* 185 */     if (EnumSet.class.isAssignableFrom(rawType)) {
/* 186 */       return () -> {
/*     */           if (type instanceof ParameterizedType) {
/*     */             Type elementType = ((ParameterizedType)type).getActualTypeArguments()[0];
/*     */ 
/*     */             
/*     */             if (elementType instanceof Class) {
/*     */               return EnumSet.noneOf((Class<Enum>)elementType);
/*     */             }
/*     */ 
/*     */             
/*     */             throw new JsonIOException("Invalid EnumSet type: " + type.toString());
/*     */           } 
/*     */           
/*     */           throw new JsonIOException("Invalid EnumSet type: " + type.toString());
/*     */         };
/*     */     }
/*     */     
/* 203 */     if (rawType == EnumMap.class) {
/* 204 */       return () -> {
/*     */           if (type instanceof ParameterizedType) {
/*     */             Type elementType = ((ParameterizedType)type).getActualTypeArguments()[0];
/*     */ 
/*     */             
/*     */             if (elementType instanceof Class) {
/*     */               return new EnumMap<>((Class<Enum>)elementType);
/*     */             }
/*     */             
/*     */             throw new JsonIOException("Invalid EnumMap type: " + type.toString());
/*     */           } 
/*     */           
/*     */           throw new JsonIOException("Invalid EnumMap type: " + type.toString());
/*     */         };
/*     */     }
/*     */     
/* 220 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> rawType, ReflectionAccessFilter.FilterResult filterResult) {
/*     */     Constructor<? super T> constructor;
/* 226 */     if (Modifier.isAbstract(rawType.getModifiers())) {
/* 227 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 232 */       constructor = rawType.getDeclaredConstructor(new Class[0]);
/* 233 */     } catch (NoSuchMethodException e) {
/* 234 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     boolean canAccess = (filterResult == ReflectionAccessFilter.FilterResult.ALLOW || (ReflectionAccessFilterHelper.canAccess(constructor, null) && (filterResult != ReflectionAccessFilter.FilterResult.BLOCK_ALL || Modifier.isPublic(constructor.getModifiers()))));
/*     */     
/* 245 */     if (!canAccess) {
/* 246 */       String message = "Unable to invoke no-args constructor of " + rawType + "; constructor is not accessible and ReflectionAccessFilter does not permit making it accessible. Register an InstanceCreator or a TypeAdapter for this type, change the visibility of the constructor or adjust the access filter.";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 253 */       return () -> {
/*     */           throw new JsonIOException(message);
/*     */         };
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 260 */     if (filterResult == ReflectionAccessFilter.FilterResult.ALLOW) {
/* 261 */       String exceptionMessage = ReflectionHelper.tryMakeAccessible(constructor);
/* 262 */       if (exceptionMessage != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 271 */         return () -> {
/*     */             throw new JsonIOException(exceptionMessage);
/*     */           };
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     return () -> {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*     */           
/*     */           return (ObjectConstructor)constructor.newInstance(new Object[0]);
/* 288 */         } catch (InstantiationException e) {
/*     */ 
/*     */           
/*     */           throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(constructor) + "' with no args", e);
/*     */         
/*     */         }
/* 294 */         catch (InvocationTargetException e) {
/*     */ 
/*     */ 
/*     */           
/*     */           throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(constructor) + "' with no args", e.getCause());
/*     */ 
/*     */         
/*     */         }
/* 302 */         catch (IllegalAccessException e) {
/*     */           throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
/*     */         } 
/*     */       };
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
/*     */   private static <T> ObjectConstructor<T> newDefaultImplementationConstructor(Type type, Class<? super T> rawType) {
/* 320 */     if (Collection.class.isAssignableFrom(rawType)) {
/*     */       
/* 322 */       ObjectConstructor<T> constructor = (ObjectConstructor)newCollectionConstructor(rawType);
/* 323 */       return constructor;
/*     */     } 
/*     */     
/* 326 */     if (Map.class.isAssignableFrom(rawType)) {
/*     */       
/* 328 */       ObjectConstructor<T> constructor = (ObjectConstructor)newMapConstructor(type, rawType);
/* 329 */       return constructor;
/*     */     } 
/*     */ 
/*     */     
/* 333 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectConstructor<? extends Collection<? extends Object>> newCollectionConstructor(Class<?> rawType) {
/* 340 */     if (rawType.isAssignableFrom(ArrayList.class)) {
/* 341 */       return () -> new ArrayList();
/*     */     }
/*     */     
/* 344 */     if (rawType.isAssignableFrom(LinkedHashSet.class)) {
/* 345 */       return () -> new LinkedHashSet();
/*     */     }
/*     */     
/* 348 */     if (rawType.isAssignableFrom(TreeSet.class)) {
/* 349 */       return () -> new TreeSet();
/*     */     }
/*     */     
/* 352 */     if (rawType.isAssignableFrom(ArrayDeque.class)) {
/* 353 */       return () -> new ArrayDeque();
/*     */     }
/*     */ 
/*     */     
/* 357 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasStringKeyType(Type mapType) {
/* 362 */     if (!(mapType instanceof ParameterizedType)) {
/* 363 */       return true;
/*     */     }
/*     */     
/* 366 */     Type[] typeArguments = ((ParameterizedType)mapType).getActualTypeArguments();
/* 367 */     if (typeArguments.length == 0) {
/* 368 */       return false;
/*     */     }
/* 370 */     return (GsonTypes.getRawType(typeArguments[0]) == String.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectConstructor<? extends Map<? extends Object, Object>> newMapConstructor(Type type, Class<?> rawType) {
/* 380 */     if (rawType.isAssignableFrom(LinkedTreeMap.class) && hasStringKeyType(type))
/* 381 */       return () -> new LinkedTreeMap<>(); 
/* 382 */     if (rawType.isAssignableFrom(LinkedHashMap.class)) {
/* 383 */       return () -> new LinkedHashMap<>();
/*     */     }
/*     */     
/* 386 */     if (rawType.isAssignableFrom(TreeMap.class)) {
/* 387 */       return () -> new TreeMap<>();
/*     */     }
/*     */     
/* 390 */     if (rawType.isAssignableFrom(ConcurrentHashMap.class)) {
/* 391 */       return () -> new ConcurrentHashMap<>();
/*     */     }
/*     */     
/* 394 */     if (rawType.isAssignableFrom(ConcurrentSkipListMap.class)) {
/* 395 */       return () -> new ConcurrentSkipListMap<>();
/*     */     }
/*     */ 
/*     */     
/* 399 */     return null;
/*     */   }
/*     */   
/*     */   private <T> ObjectConstructor<T> newUnsafeAllocator(Class<? super T> rawType) {
/* 403 */     if (this.useJdkUnsafe) {
/* 404 */       return () -> {
/*     */ 
/*     */           
/*     */           try {
/*     */             return (ObjectConstructor)UnsafeAllocator.INSTANCE.newInstance(rawType);
/* 409 */           } catch (Exception e) {
/*     */             throw new RuntimeException("Unable to create instance of " + rawType + ". Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.", e);
/*     */           } 
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     String exceptionMessage = "Unable to create instance of " + rawType + "; usage of JDK Unsafe is disabled. Registering an InstanceCreator or a TypeAdapter for this type, adding a no-args constructor, or enabling usage of JDK Unsafe may fix this problem.";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 427 */     if ((rawType.getDeclaredConstructors()).length == 0)
/*     */     {
/*     */       
/* 430 */       exceptionMessage = exceptionMessage + " Or adjust your R8 configuration to keep the no-args constructor of the class.";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 435 */     String exceptionMessageF = exceptionMessage;
/*     */     
/* 437 */     return () -> {
/*     */         throw new JsonIOException(exceptionMessageF);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 445 */     return this.instanceCreators.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\ConstructorConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */