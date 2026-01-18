/*     */ package com.hypixel.hytale.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*     */ import com.hypixel.hytale.component.query.ExactArchetypeQuery;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Archetype<ECS_TYPE>
/*     */   implements Query<ECS_TYPE>
/*     */ {
/*     */   @Nonnull
/*  25 */   private static final Archetype EMPTY = new Archetype(0, 0, (ComponentType<ECS_TYPE, ?>[])ComponentType.EMPTY_ARRAY);
/*     */   
/*     */   private final int minIndex;
/*     */   private final int count;
/*     */   @Nonnull
/*     */   private final ComponentType<ECS_TYPE, ?>[] componentTypes;
/*     */   
/*     */   public static <ECS_TYPE> Archetype<ECS_TYPE> empty() {
/*  33 */     return EMPTY;
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
/*     */   @Nonnull
/*  55 */   private final ExactArchetypeQuery<ECS_TYPE> exactQuery = new ExactArchetypeQuery(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Archetype(int minIndex, int count, @Nonnull ComponentType<ECS_TYPE, ?>[] componentTypes) {
/*  66 */     this.minIndex = minIndex;
/*  67 */     this.count = count;
/*  68 */     this.componentTypes = componentTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinIndex() {
/*  76 */     return this.minIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int count() {
/*  83 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/*  91 */     return this.componentTypes.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ComponentType<ECS_TYPE, ?> get(int index) {
/* 101 */     return this.componentTypes[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 108 */     return (this.componentTypes.length == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(@Nonnull ComponentType<ECS_TYPE, ?> componentType) {
/* 116 */     int index = componentType.getIndex();
/* 117 */     return (index < this.componentTypes.length && this.componentTypes[index] == componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(@Nonnull Archetype<ECS_TYPE> archetype) {
/* 125 */     if (this == archetype || archetype.isEmpty()) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     for (int i = archetype.minIndex; i < archetype.componentTypes.length; i++) {
/* 130 */       ComponentType<ECS_TYPE, ?> componentType = archetype.componentTypes[i];
/* 131 */       if (componentType != null && 
/* 132 */         !contains(componentType)) return false; 
/*     */     } 
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateComponentType(@Nonnull ComponentType<ECS_TYPE, ?> componentType) {
/* 145 */     if (!contains(componentType)) {
/* 146 */       throw new IllegalArgumentException("ComponentType is not in archetype: " + String.valueOf(componentType) + ", " + String.valueOf(this));
/*     */     }
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
/*     */   public void validateComponents(@Nonnull Component<ECS_TYPE>[] components, @Nullable ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> ignore) {
/* 160 */     int len = Math.max(this.componentTypes.length, components.length);
/* 161 */     for (int index = 0; index < len; index++) {
/* 162 */       ComponentType<ECS_TYPE, ?> componentType = (index >= this.componentTypes.length) ? null : this.componentTypes[index];
/* 163 */       Component<ECS_TYPE> component = (index >= components.length) ? null : components[index];
/*     */       
/* 165 */       if (componentType == null) {
/* 166 */         if (component != null && (ignore == null || index != ignore.getIndex()))
/*     */         {
/*     */           
/* 169 */           throw new IllegalStateException("Invalid component at index " + index + " expected null but found " + String.valueOf(component.getClass()));
/*     */         }
/*     */       } else {
/* 172 */         Class<?> typeClass = componentType.getTypeClass();
/* 173 */         if (component == null) throw new IllegalStateException("Invalid component at index " + index + " expected " + String.valueOf(typeClass) + " but found null");
/*     */         
/* 175 */         Class<? extends Component> aClass = (Class)component.getClass();
/* 176 */         if (!aClass.equals(typeClass)) {
/* 177 */           throw new IllegalStateException("Invalid component at index " + index + " expected " + String.valueOf(typeClass) + " but found " + String.valueOf(aClass));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSerializableComponents(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/* 189 */     if (isEmpty()) {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     if (contains(data.getRegistry().getNonSerializedComponentType())) {
/* 194 */       return false;
/*     */     }
/*     */     
/* 197 */     for (int index = this.minIndex; index < this.componentTypes.length; index++) {
/* 198 */       ComponentType<ECS_TYPE, ?> componentType = this.componentTypes[index];
/* 199 */       if (componentType != null && data.getComponentCodec(componentType) != null) return true;
/*     */     
/*     */     } 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Archetype<ECS_TYPE> getSerializableArchetype(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/* 212 */     if (isEmpty()) {
/* 213 */       return EMPTY;
/*     */     }
/*     */     
/* 216 */     if (contains(data.getRegistry().getNonSerializedComponentType())) {
/* 217 */       return EMPTY;
/*     */     }
/*     */     
/* 220 */     int lastSerializableIndex = this.componentTypes.length - 1;
/* 221 */     for (int index = this.componentTypes.length - 1; index >= this.minIndex; index--) {
/* 222 */       ComponentType<ECS_TYPE, ?> componentType = this.componentTypes[index];
/* 223 */       if (componentType != null && data.getComponentCodec(componentType) != null) {
/* 224 */         lastSerializableIndex = index;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 229 */     if (lastSerializableIndex < this.minIndex) {
/* 230 */       return EMPTY;
/*     */     }
/*     */ 
/*     */     
/* 234 */     ComponentType[] arrayOfComponentType = new ComponentType[lastSerializableIndex + 1];
/*     */     
/* 236 */     int serializableMinIndex = this.minIndex;
/* 237 */     for (int i = serializableMinIndex; i < arrayOfComponentType.length; i++) {
/*     */       
/* 239 */       ComponentType<ECS_TYPE, ?> componentType = this.componentTypes[i];
/* 240 */       if (componentType != null && data.getComponentCodec(componentType) != null) {
/* 241 */         serializableMinIndex = Math.min(serializableMinIndex, i);
/* 242 */         arrayOfComponentType[i] = componentType;
/*     */       } 
/*     */     } 
/* 245 */     return new Archetype(this.minIndex, arrayOfComponentType.length, (ComponentType<ECS_TYPE, ?>[])arrayOfComponentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ExactArchetypeQuery<ECS_TYPE> asExactQuery() {
/* 253 */     return this.exactQuery;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <ECS_TYPE> Archetype<ECS_TYPE> of(@Nonnull ComponentType<ECS_TYPE, ?> componentTypes) {
/* 265 */     int index = componentTypes.getIndex();
/*     */ 
/*     */     
/* 268 */     ComponentType[] arrayOfComponentType = new ComponentType[index + 1];
/* 269 */     arrayOfComponentType[index] = componentTypes;
/* 270 */     return new Archetype<>(index, 1, (ComponentType<ECS_TYPE, ?>[])arrayOfComponentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   public static <ECS_TYPE> Archetype<ECS_TYPE> of(@Nonnull ComponentType<ECS_TYPE, ?>... componentTypes) {
/* 282 */     if (componentTypes.length == 0)
/*     */     {
/* 284 */       return EMPTY;
/*     */     }
/*     */     
/* 287 */     ComponentRegistry<ECS_TYPE> registry = componentTypes[0].getRegistry();
/*     */     
/* 289 */     int minIndex = Integer.MAX_VALUE;
/* 290 */     int maxIndex = Integer.MIN_VALUE;
/* 291 */     for (int i = 0; i < componentTypes.length; i++) {
/*     */       
/* 293 */       componentTypes[i].validateRegistry(registry);
/*     */ 
/*     */       
/* 296 */       int index = componentTypes[i].getIndex();
/* 297 */       if (index < minIndex) minIndex = index; 
/* 298 */       if (index > maxIndex) maxIndex = index;
/*     */ 
/*     */       
/* 301 */       for (int n = i + 1; n < componentTypes.length; n++) {
/* 302 */         if (componentTypes[i] == componentTypes[n]) {
/* 303 */           throw new IllegalArgumentException("ComponentType provided multiple times! " + Arrays.toString(componentTypes));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 309 */     ComponentType[] arrayOfComponentType = new ComponentType[maxIndex + 1];
/* 310 */     for (ComponentType<ECS_TYPE, ?> componentType : componentTypes) {
/* 311 */       arrayOfComponentType[componentType.getIndex()] = componentType;
/*     */     }
/* 313 */     return new Archetype<>(minIndex, componentTypes.length, (ComponentType<ECS_TYPE, ?>[])arrayOfComponentType);
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
/*     */   @Nonnull
/*     */   public static <ECS_TYPE, T extends Component<ECS_TYPE>> Archetype<ECS_TYPE> add(@Nonnull Archetype<ECS_TYPE> archetype, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 328 */     if (archetype.isEmpty()) return of(componentType); 
/* 329 */     if (archetype.contains(componentType)) throw new IllegalArgumentException("ComponentType is already in Archetype! " + String.valueOf(archetype) + ", " + String.valueOf(componentType));
/*     */     
/* 331 */     archetype.validateRegistry(componentType.getRegistry());
/*     */     
/* 333 */     int index = componentType.getIndex();
/* 334 */     int minIndex = Math.min(index, archetype.minIndex);
/* 335 */     int newLength = Math.max(index + 1, archetype.componentTypes.length);
/* 336 */     ComponentType[] arrayOfComponentType = Arrays.<ComponentType>copyOf((ComponentType[])archetype.componentTypes, newLength);
/* 337 */     arrayOfComponentType[index] = componentType;
/* 338 */     return new Archetype<>(minIndex, archetype.count + 1, (ComponentType<ECS_TYPE, ?>[])arrayOfComponentType);
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
/*     */   public static <ECS_TYPE, T extends Component<ECS_TYPE>> Archetype<ECS_TYPE> remove(@Nonnull Archetype<ECS_TYPE> archetype, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 352 */     if (archetype.isEmpty()) throw new IllegalArgumentException("Archetype is already empty!"); 
/* 353 */     if (!archetype.contains(componentType)) {
/* 354 */       throw new IllegalArgumentException("Archetype doesn't contain ComponentType! " + String.valueOf(archetype) + ", " + String.valueOf(componentType));
/*     */     }
/*     */     
/* 357 */     int oldLength = archetype.componentTypes.length;
/* 358 */     int oldMinIndex = archetype.minIndex;
/* 359 */     int oldMaxIndex = oldLength - 1;
/* 360 */     if (oldMinIndex == oldMaxIndex)
/*     */     {
/* 362 */       return EMPTY;
/*     */     }
/*     */     
/* 365 */     int newCount = archetype.count - 1;
/* 366 */     int index = componentType.getIndex();
/*     */ 
/*     */     
/* 369 */     if (index == oldMaxIndex) {
/*     */ 
/*     */       
/* 372 */       int maxIndex = index - 1;
/* 373 */       for (; maxIndex > oldMinIndex && 
/* 374 */         archetype.componentTypes[maxIndex] == null; maxIndex--);
/*     */ 
/*     */       
/* 377 */       return new Archetype<>(oldMinIndex, newCount, Arrays.<ComponentType<ECS_TYPE, ?>>copyOf(archetype.componentTypes, maxIndex + 1));
/*     */     } 
/*     */     
/* 380 */     ComponentType[] arrayOfComponentType = Arrays.<ComponentType>copyOf((ComponentType[])archetype.componentTypes, oldLength);
/* 381 */     arrayOfComponentType[index] = null;
/*     */ 
/*     */     
/* 384 */     if (index == oldMinIndex) {
/*     */       
/* 386 */       int minIndex = index + 1;
/* 387 */       for (; minIndex < oldLength && 
/* 388 */         arrayOfComponentType[minIndex] == null; minIndex++);
/*     */ 
/*     */       
/* 391 */       return new Archetype<>(minIndex, newCount, (ComponentType<ECS_TYPE, ?>[])arrayOfComponentType);
/*     */     } 
/*     */ 
/*     */     
/* 395 */     return new Archetype<>(oldMinIndex, newCount, (ComponentType<ECS_TYPE, ?>[])arrayOfComponentType);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(@Nonnull Archetype<ECS_TYPE> archetype) {
/* 400 */     return archetype.contains(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresComponentType(@Nonnull ComponentType<ECS_TYPE, ?> componentType) {
/* 405 */     return contains(componentType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateRegistry(ComponentRegistry<ECS_TYPE> registry) {
/* 410 */     if (isEmpty())
/* 411 */       return;  this.componentTypes[this.minIndex].validateRegistry(registry);
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate() {
/* 416 */     for (int i = this.minIndex; i < this.componentTypes.length; i++) {
/* 417 */       ComponentType<ECS_TYPE, ?> componentType = this.componentTypes[i];
/* 418 */       if (componentType != null) componentType.validate();
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 424 */     if (this == o) return true; 
/* 425 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 427 */     Archetype<?> archetype = (Archetype)o;
/*     */     
/* 429 */     return Arrays.equals((Object[])this.componentTypes, (Object[])archetype.componentTypes);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 434 */     return Arrays.hashCode((Object[])this.componentTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 441 */     return "Archetype{componentTypes=" + Arrays.toString((Object[])this.componentTypes) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\Archetype.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */