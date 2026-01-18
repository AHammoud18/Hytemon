/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.Codec;
/*     */ import org.bson.codecs.configuration.CodecProvider;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ import org.bson.diagnostics.Logger;
/*     */ import org.bson.diagnostics.Loggers;
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
/*     */ public final class PojoCodecProvider
/*     */   implements CodecProvider
/*     */ {
/*  42 */   static final Logger LOGGER = Loggers.getLogger("codecs.pojo");
/*     */   
/*     */   private final boolean automatic;
/*     */   private final Map<Class<?>, ClassModel<?>> classModels;
/*     */   private final Set<String> packages;
/*     */   private final List<Convention> conventions;
/*     */   private final DiscriminatorLookup discriminatorLookup;
/*     */   private final List<PropertyCodecProvider> propertyCodecProviders;
/*     */   
/*     */   private PojoCodecProvider(boolean automatic, Map<Class<?>, ClassModel<?>> classModels, Set<String> packages, List<Convention> conventions, List<PropertyCodecProvider> propertyCodecProviders) {
/*  52 */     this.automatic = automatic;
/*  53 */     this.classModels = classModels;
/*  54 */     this.packages = packages;
/*  55 */     this.conventions = conventions;
/*  56 */     this.discriminatorLookup = new DiscriminatorLookup(classModels, packages);
/*  57 */     this.propertyCodecProviders = propertyCodecProviders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/*  67 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/*  72 */     return getPojoCodec(clazz, registry);
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> PojoCodec<T> getPojoCodec(Class<T> clazz, CodecRegistry registry) {
/*  77 */     ClassModel<T> classModel = (ClassModel<T>)this.classModels.get(clazz);
/*  78 */     if (classModel != null)
/*  79 */       return new PojoCodecImpl<>(classModel, registry, this.propertyCodecProviders, this.discriminatorLookup); 
/*  80 */     if (this.automatic || (clazz.getPackage() != null && this.packages.contains(clazz.getPackage().getName()))) {
/*     */       try {
/*  82 */         classModel = createClassModel(clazz, this.conventions);
/*  83 */         if (clazz.isInterface() || !classModel.getPropertyModels().isEmpty()) {
/*  84 */           this.discriminatorLookup.addClassModel(classModel);
/*  85 */           return new AutomaticPojoCodec<>(new PojoCodecImpl<>(classModel, registry, this.propertyCodecProviders, this.discriminatorLookup));
/*     */         }
/*     */       
/*  88 */       } catch (Exception e) {
/*  89 */         LOGGER.warn(String.format("Cannot use '%s' with the PojoCodec.", new Object[] { clazz.getSimpleName() }), e);
/*  90 */         return null;
/*     */       } 
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 100 */     private final Set<String> packages = new HashSet<>();
/* 101 */     private final Map<Class<?>, ClassModel<?>> classModels = new HashMap<>();
/* 102 */     private final List<Class<?>> clazzes = new ArrayList<>();
/* 103 */     private List<Convention> conventions = null;
/* 104 */     private final List<PropertyCodecProvider> propertyCodecProviders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean automatic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PojoCodecProvider build() {
/* 116 */       List<Convention> immutableConventions = (this.conventions != null) ? Collections.<Convention>unmodifiableList(new ArrayList<>(this.conventions)) : null;
/* 117 */       for (Class<?> clazz : this.clazzes) {
/* 118 */         if (!this.classModels.containsKey(clazz)) {
/* 119 */           register((ClassModel<?>[])new ClassModel[] { PojoCodecProvider.access$100(clazz, immutableConventions) });
/*     */         }
/*     */       } 
/* 122 */       return new PojoCodecProvider(this.automatic, this.classModels, this.packages, immutableConventions, this.propertyCodecProviders);
/*     */     }
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
/*     */     public Builder automatic(boolean automatic) {
/* 135 */       this.automatic = automatic;
/* 136 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder conventions(List<Convention> conventions) {
/* 146 */       this.conventions = (List<Convention>)Assertions.notNull("conventions", conventions);
/* 147 */       return this;
/*     */     }
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
/*     */     public Builder register(Class<?>... classes) {
/* 160 */       this.clazzes.addAll(Arrays.asList(classes));
/* 161 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder register(ClassModel<?>... classModels) {
/* 171 */       Assertions.notNull("classModels", classModels);
/* 172 */       for (ClassModel<?> classModel : classModels) {
/* 173 */         this.classModels.put(classModel.getType(), classModel);
/*     */       }
/* 175 */       return this;
/*     */     }
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
/*     */     public Builder register(String... packageNames) {
/* 189 */       this.packages.addAll(Arrays.asList((String[])Assertions.notNull("packageNames", packageNames)));
/* 190 */       return this;
/*     */     }
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
/*     */     public Builder register(PropertyCodecProvider... providers) {
/* 206 */       this.propertyCodecProviders.addAll(Arrays.asList((PropertyCodecProvider[])Assertions.notNull("providers", providers)));
/* 207 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   private static <T> ClassModel<T> createClassModel(Class<T> clazz, List<Convention> conventions) {
/* 215 */     ClassModelBuilder<T> builder = ClassModel.builder(clazz);
/* 216 */     if (conventions != null) {
/* 217 */       builder.conventions(conventions);
/*     */     }
/* 219 */     return builder.build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PojoCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */