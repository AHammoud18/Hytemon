/*     */ package com.hypixel.hytale.component;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.function.consumer.IntObjectConsumer;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntPredicate;
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
/*     */ 
/*     */ public class ArchetypeChunk<ECS_TYPE>
/*     */ {
/*     */   @Nonnull
/*  26 */   private static final ArchetypeChunk[] EMPTY_ARRAY = new ArchetypeChunk[0];
/*     */   
/*     */   @Nonnull
/*     */   protected final Store<ECS_TYPE> store;
/*     */   
/*     */   @Nonnull
/*     */   protected final Archetype<ECS_TYPE> archetype;
/*     */   protected int entitiesSize;
/*     */   
/*     */   public static <ECS_TYPE> ArchetypeChunk<ECS_TYPE>[] emptyArray() {
/*  36 */     return (ArchetypeChunk<ECS_TYPE>[])EMPTY_ARRAY;
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
/*     */   @Nonnull
/*  59 */   protected Ref<ECS_TYPE>[] refs = (Ref<ECS_TYPE>[])new Ref[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Component<ECS_TYPE>[][] components;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArchetypeChunk(@Nonnull Store<ECS_TYPE> store, @Nonnull Archetype<ECS_TYPE> archetype) {
/*  76 */     this.store = store;
/*  77 */     this.archetype = archetype;
/*     */ 
/*     */     
/*  80 */     this.components = (Component<ECS_TYPE>[][])new Component[archetype.length()][];
/*  81 */     for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/*  82 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)archetype.get(i);
/*  83 */       if (componentType != null)
/*     */       {
/*  85 */         this.components[componentType.getIndex()] = (Component<ECS_TYPE>[])new Component[16];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Archetype<ECS_TYPE> getArchetype() {
/*  94 */     return this.archetype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 101 */     return this.entitiesSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Ref<ECS_TYPE> getReferenceTo(int index) {
/* 112 */     if (index < 0 || index >= this.entitiesSize) throw new IndexOutOfBoundsException(index); 
/* 113 */     return this.refs[index];
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
/*     */   public <T extends Component<ECS_TYPE>> void setComponent(int index, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 126 */     componentType.validateRegistry(this.store.getRegistry());
/*     */ 
/*     */     
/* 129 */     if (index < 0 || index >= this.entitiesSize) {
/* 130 */       throw new IndexOutOfBoundsException(index);
/*     */     }
/*     */ 
/*     */     
/* 134 */     if (!this.archetype.contains(componentType)) {
/* 135 */       throw new IllegalArgumentException("Entity doesn't have component type " + String.valueOf(componentType));
/*     */     }
/*     */     
/* 138 */     this.components[componentType.getIndex()][index] = (Component<ECS_TYPE>)component;
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
/*     */   @Nullable
/*     */   public <T extends Component<ECS_TYPE>> T getComponent(int index, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 155 */     componentType.validateRegistry(this.store.getRegistry());
/*     */ 
/*     */     
/* 158 */     if (index < 0 || index >= this.entitiesSize) {
/* 159 */       throw new IndexOutOfBoundsException(index);
/*     */     }
/*     */ 
/*     */     
/* 163 */     if (!this.archetype.contains(componentType)) {
/* 164 */       return null;
/*     */     }
/*     */     
/* 167 */     return (T)this.components[componentType.getIndex()][index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Holder<ECS_TYPE> holder) {
/* 178 */     if (!this.archetype.equals(holder.getArchetype())) {
/* 179 */       throw new IllegalArgumentException("EntityHolder is not for this archetype chunk!");
/*     */     }
/* 181 */     int entityIndex = this.entitiesSize++;
/*     */     
/* 183 */     int oldLength = this.refs.length;
/* 184 */     if (oldLength <= entityIndex) {
/* 185 */       int newLength = ArrayUtil.grow(entityIndex);
/* 186 */       this.refs = Arrays.<Ref<ECS_TYPE>>copyOf(this.refs, newLength);
/* 187 */       for (int j = this.archetype.getMinIndex(); j < this.archetype.length(); j++) {
/* 188 */         ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(j);
/* 189 */         if (componentType != null) {
/*     */           
/* 191 */           int componentTypeIndex = componentType.getIndex();
/* 192 */           this.components[componentTypeIndex] = Arrays.<Component<ECS_TYPE>>copyOf(this.components[componentTypeIndex], newLength);
/*     */         } 
/*     */       } 
/*     */     } 
/* 196 */     this.refs[entityIndex] = ref;
/* 197 */     for (int i = this.archetype.getMinIndex(); i < this.archetype.length(); i++) {
/* 198 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(i);
/* 199 */       if (componentType != null) {
/* 200 */         this.components[componentType.getIndex()][entityIndex] = holder.getComponent((ComponentType)componentType);
/*     */       }
/*     */     } 
/* 203 */     return entityIndex;
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
/*     */   public Holder<ECS_TYPE> copyEntity(int entityIndex, @Nonnull Holder<ECS_TYPE> target) {
/* 215 */     if (entityIndex >= this.entitiesSize) throw new IndexOutOfBoundsException(entityIndex);
/*     */     
/* 217 */     Component[] arrayOfComponent = (Component[])target.ensureComponentsSize(this.archetype.length());
/* 218 */     for (int i = this.archetype.getMinIndex(); i < this.archetype.length(); i++) {
/* 219 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(i);
/* 220 */       if (componentType != null) {
/*     */         
/* 222 */         int componentTypeIndex = componentType.getIndex();
/* 223 */         Component<ECS_TYPE> component = this.components[componentTypeIndex][entityIndex];
/* 224 */         arrayOfComponent[componentTypeIndex] = component.clone();
/*     */       } 
/*     */     } 
/* 227 */     target.init(this.archetype, (Component<ECS_TYPE>[])arrayOfComponent);
/* 228 */     return target;
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
/*     */   public Holder<ECS_TYPE> copySerializableEntity(@Nonnull ComponentRegistry.Data<ECS_TYPE> data, int entityIndex, @Nonnull Holder<ECS_TYPE> target) {
/* 243 */     if (entityIndex >= this.entitiesSize) throw new IndexOutOfBoundsException(entityIndex);
/*     */     
/* 245 */     Archetype<ECS_TYPE> serializableArchetype = this.archetype.getSerializableArchetype(data);
/*     */     
/* 247 */     Component[] arrayOfComponent = (Component[])target.ensureComponentsSize(serializableArchetype.length());
/* 248 */     for (int i = serializableArchetype.getMinIndex(); i < serializableArchetype.length(); i++) {
/* 249 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)serializableArchetype.get(i);
/* 250 */       if (componentType != null) {
/* 251 */         int componentTypeIndex = componentType.getIndex();
/* 252 */         Component<ECS_TYPE> component = this.components[componentTypeIndex][entityIndex];
/* 253 */         arrayOfComponent[componentTypeIndex] = component.cloneSerializable();
/*     */       } 
/*     */     } 
/* 256 */     target.init(serializableArchetype, (Component<ECS_TYPE>[])arrayOfComponent);
/* 257 */     return target;
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
/*     */   public Holder<ECS_TYPE> removeEntity(int entityIndex, @Nonnull Holder<ECS_TYPE> target) {
/* 269 */     if (entityIndex >= this.entitiesSize) throw new IndexOutOfBoundsException(entityIndex);
/*     */     
/* 271 */     Component[] arrayOfComponent = (Component[])target.ensureComponentsSize(this.archetype.length());
/* 272 */     for (int i = this.archetype.getMinIndex(); i < this.archetype.length(); i++) {
/* 273 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(i);
/* 274 */       if (componentType != null) {
/*     */         
/* 276 */         int componentTypeIndex = componentType.getIndex();
/* 277 */         arrayOfComponent[componentTypeIndex] = this.components[componentTypeIndex][entityIndex];
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     int lastIndex = this.entitiesSize - 1;
/* 282 */     if (entityIndex != lastIndex) fillEmptyIndex(entityIndex, lastIndex); 
/* 283 */     this.refs[lastIndex] = null;
/*     */     
/* 285 */     for (int j = this.archetype.getMinIndex(); j < this.archetype.length(); j++) {
/* 286 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(j);
/* 287 */       if (componentType != null)
/* 288 */         this.components[componentType.getIndex()][lastIndex] = null; 
/*     */     } 
/* 290 */     this.entitiesSize = lastIndex;
/*     */     
/* 292 */     target.init(this.archetype, (Component<ECS_TYPE>[])arrayOfComponent);
/* 293 */     return target;
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
/*     */   public void transferTo(@Nonnull Holder<ECS_TYPE> tempInternalEntityHolder, @Nonnull ArchetypeChunk<ECS_TYPE> chunk, @Nonnull Consumer<Holder<ECS_TYPE>> modification, @Nonnull IntObjectConsumer<Ref<ECS_TYPE>> referenceConsumer) {
/* 308 */     Component[] arrayOfComponent = new Component[this.archetype.length()];
/* 309 */     for (int entityIndex = 0; entityIndex < this.entitiesSize; entityIndex++) {
/* 310 */       Ref<ECS_TYPE> ref = this.refs[entityIndex];
/*     */       
/* 312 */       this.refs[entityIndex] = null;
/*     */       
/* 314 */       for (int i = this.archetype.getMinIndex(); i < this.archetype.length(); i++) {
/* 315 */         ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(i);
/* 316 */         if (componentType != null) {
/*     */           
/* 318 */           int componentTypeIndex = componentType.getIndex();
/* 319 */           arrayOfComponent[componentTypeIndex] = this.components[componentTypeIndex][entityIndex];
/* 320 */           this.components[componentTypeIndex][entityIndex] = null;
/*     */         } 
/*     */       } 
/* 323 */       tempInternalEntityHolder._internal_init(this.archetype, (Component<ECS_TYPE>[])arrayOfComponent, this.store.getRegistry().getUnknownComponentType());
/* 324 */       modification.accept(tempInternalEntityHolder);
/*     */       
/* 326 */       int newEntityIndex = chunk.addEntity(ref, tempInternalEntityHolder);
/* 327 */       referenceConsumer.accept(newEntityIndex, ref);
/*     */     } 
/* 329 */     this.entitiesSize = 0;
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
/*     */   public void transferSomeTo(@Nonnull Holder<ECS_TYPE> tempInternalEntityHolder, @Nonnull ArchetypeChunk<ECS_TYPE> chunk, @Nonnull IntPredicate shouldTransfer, @Nonnull Consumer<Holder<ECS_TYPE>> modification, @Nonnull IntObjectConsumer<Ref<ECS_TYPE>> referenceConsumer) {
/* 347 */     int firstTransfered = Integer.MIN_VALUE;
/* 348 */     int lastTransfered = Integer.MIN_VALUE;
/*     */ 
/*     */     
/* 351 */     Component[] arrayOfComponent = new Component[this.archetype.length()];
/* 352 */     for (int entityIndex = 0; entityIndex < this.entitiesSize; entityIndex++) {
/* 353 */       if (shouldTransfer.test(entityIndex)) {
/*     */         
/* 355 */         if (firstTransfered == Integer.MIN_VALUE) {
/* 356 */           firstTransfered = entityIndex;
/*     */         }
/* 358 */         lastTransfered = entityIndex;
/*     */         
/* 360 */         Ref<ECS_TYPE> ref = this.refs[entityIndex];
/*     */         
/* 362 */         this.refs[entityIndex] = null;
/*     */         
/* 364 */         for (int i = this.archetype.getMinIndex(); i < this.archetype.length(); i++) {
/* 365 */           ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(i);
/* 366 */           if (componentType != null) {
/*     */             
/* 368 */             int componentTypeIndex = componentType.getIndex();
/* 369 */             arrayOfComponent[componentTypeIndex] = this.components[componentTypeIndex][entityIndex];
/* 370 */             this.components[componentTypeIndex][entityIndex] = null;
/*     */           } 
/*     */         } 
/* 373 */         tempInternalEntityHolder.init(this.archetype, (Component<ECS_TYPE>[])arrayOfComponent);
/* 374 */         modification.accept(tempInternalEntityHolder);
/*     */         
/* 376 */         int newEntityIndex = chunk.addEntity(ref, tempInternalEntityHolder);
/* 377 */         referenceConsumer.accept(newEntityIndex, ref);
/*     */       } 
/*     */     } 
/* 380 */     if (firstTransfered != Integer.MIN_VALUE) {
/* 381 */       if (lastTransfered == this.entitiesSize - 1) {
/* 382 */         this.entitiesSize = firstTransfered;
/*     */         
/*     */         return;
/*     */       } 
/* 386 */       int newSize = this.entitiesSize - lastTransfered - firstTransfered + 1;
/*     */       
/* 388 */       for (int i = firstTransfered; i <= lastTransfered; i++) {
/* 389 */         if (this.refs[i] == null) {
/*     */           
/* 391 */           int lastIndex = this.entitiesSize - 1;
/*     */           
/* 393 */           if (lastIndex == lastTransfered)
/* 394 */             break;  if (i != lastIndex) fillEmptyIndex(i, lastIndex);
/*     */           
/* 396 */           this.entitiesSize--;
/*     */         } 
/*     */       } 
/* 399 */       this.entitiesSize = newSize;
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
/*     */   protected void fillEmptyIndex(int entityIndex, int lastIndex) {
/* 412 */     Ref<ECS_TYPE> ref = this.refs[lastIndex];
/*     */ 
/*     */     
/* 415 */     this.store.setEntityChunkIndex(ref, entityIndex);
/*     */ 
/*     */     
/* 418 */     for (int i = this.archetype.getMinIndex(); i < this.archetype.length(); i++) {
/* 419 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(i);
/* 420 */       if (componentType != null) {
/*     */         
/* 422 */         Component<ECS_TYPE>[] componentArr = this.components[componentType.getIndex()];
/* 423 */         componentArr[entityIndex] = componentArr[lastIndex];
/*     */       } 
/*     */     } 
/* 426 */     this.refs[entityIndex] = ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendDump(@Nonnull String prefix, @Nonnull StringBuilder sb) {
/* 436 */     sb.append(prefix).append("archetype=").append(this.archetype).append("\n");
/* 437 */     sb.append(prefix).append("entitiesSize=").append(this.entitiesSize).append("\n");
/*     */     
/* 439 */     for (int i = 0; i < this.entitiesSize; i++) {
/* 440 */       sb.append(prefix).append("\t- ").append(this.refs[i]).append("\n");
/* 441 */       sb.append(prefix).append("\t").append("components=").append("\n");
/*     */       
/* 443 */       for (int x = this.archetype.getMinIndex(); x < this.archetype.length(); x++) {
/* 444 */         ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(x);
/* 445 */         if (componentType != null) {
/* 446 */           sb.append(prefix).append("\t\t- ").append(componentType.getIndex()).append("\t").append(this.components[componentType.getIndex()][x]).append("\n");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 454 */     return "ArchetypeChunk{archetype=" + String.valueOf(this.archetype) + ", entitiesSize=" + this.entitiesSize + ", entityReferences=" + 
/*     */ 
/*     */       
/* 457 */       Arrays.toString((Object[])this.refs) + ", components=" + 
/* 458 */       Arrays.toString((Object[])this.components) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\ArchetypeChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */