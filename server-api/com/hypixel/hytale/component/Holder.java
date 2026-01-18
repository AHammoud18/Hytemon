/*     */ package com.hypixel.hytale.component;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.component.data.change.ChangeType;
/*     */ import com.hypixel.hytale.component.data.change.ComponentChange;
/*     */ import com.hypixel.hytale.component.data.change.DataChange;
/*     */ import com.hypixel.hytale.component.data.unknown.TempUnknownComponent;
/*     */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public class Holder<ECS_TYPE> {
/*  21 */   private static final Holder<?>[] EMPTY_ARRAY = (Holder<?>[])new Holder[0]; @Nullable
/*     */   private final ComponentRegistry<ECS_TYPE> registry;
/*     */   
/*     */   public static <T> Holder<T>[] emptyArray() {
/*  25 */     return (Holder[])EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private final StampedLock lock = new StampedLock();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Archetype<ECS_TYPE> archetype;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Component<ECS_TYPE>[] components;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ensureValidComponents = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Holder() {
/*  56 */     this.registry = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Holder(@Nonnull ComponentRegistry<ECS_TYPE> registry) {
/*  66 */     this.registry = registry;
/*  67 */     this.archetype = Archetype.empty();
/*  68 */     this.components = (Component<ECS_TYPE>[])Component.EMPTY_ARRAY;
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
/*     */   Holder(@Nonnull ComponentRegistry<ECS_TYPE> registry, @Nonnull Archetype<ECS_TYPE> archetype, @Nonnull Component<ECS_TYPE>[] components) {
/*  81 */     this.registry = registry;
/*  82 */     init(archetype, components);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ECS_TYPE>[] ensureComponentsSize(int size) {
/*  93 */     long stamp = this.lock.writeLock();
/*     */     try {
/*  95 */       if (this.components == null) {
/*     */         
/*  97 */         this.components = (Component<ECS_TYPE>[])new Component[size];
/*  98 */         return this.components;
/*     */       } 
/* 100 */       if (this.components.length < size) this.components = Arrays.<Component<ECS_TYPE>>copyOf(this.components, size); 
/* 101 */       return this.components;
/*     */     } finally {
/* 103 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(@Nonnull Archetype<ECS_TYPE> archetype, @Nonnull Component<ECS_TYPE>[] components) {
/* 114 */     archetype.validate();
/* 115 */     archetype.validateComponents(components, null);
/* 116 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 118 */       this.archetype = archetype;
/* 119 */       this.components = components;
/* 120 */       this.ensureValidComponents = true;
/*     */     } finally {
/* 122 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _internal_init(@Nonnull Archetype<ECS_TYPE> archetype, @Nonnull Component<ECS_TYPE>[] components, @Nonnull ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> unknownComponentType) {
/* 130 */     archetype.validateComponents(components, unknownComponentType);
/* 131 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 133 */       this.archetype = archetype;
/* 134 */       this.components = components;
/* 135 */       this.ensureValidComponents = false;
/*     */     } finally {
/* 137 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Archetype<ECS_TYPE> getArchetype() {
/* 146 */     return this.archetype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void ensureComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 156 */     assert this.archetype != null;
/* 157 */     assert this.registry != null;
/*     */     
/* 159 */     if (this.ensureValidComponents) componentType.validate(); 
/* 160 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 162 */       if (this.archetype.contains(componentType))
/* 163 */         return;  T component = this.registry.createComponent(componentType);
/* 164 */       addComponent0(componentType, component);
/*     */     } finally {
/* 166 */       this.lock.unlockWrite(stamp);
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
/*     */   @Nonnull
/*     */   public <T extends Component<ECS_TYPE>> T ensureAndGetComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 180 */     ensureComponent(componentType);
/*     */     
/* 182 */     return getComponent(componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void addComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 193 */     assert this.archetype != null;
/*     */     
/* 195 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 197 */       if (this.ensureValidComponents) componentType.validate(); 
/* 198 */       if (this.archetype.contains(componentType)) throw new IllegalArgumentException("Entity contains component type: " + String.valueOf(componentType));
/*     */       
/* 200 */       addComponent0(componentType, component);
/*     */     } finally {
/* 202 */       this.lock.unlockWrite(stamp);
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
/*     */   private <T extends Component<ECS_TYPE>> void addComponent0(@Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 214 */     assert this.archetype != null;
/* 215 */     assert this.components != null;
/*     */     
/* 217 */     this.archetype = Archetype.add(this.archetype, componentType);
/*     */ 
/*     */     
/* 220 */     int newLength = this.archetype.length();
/* 221 */     if (this.components.length < newLength) {
/* 222 */       this.components = Arrays.<Component<ECS_TYPE>>copyOf(this.components, newLength);
/*     */     }
/*     */     
/* 225 */     this.components[componentType.getIndex()] = (Component<ECS_TYPE>)component;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void replaceComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 236 */     assert this.archetype != null;
/* 237 */     assert this.components != null;
/*     */     
/* 239 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 241 */       if (this.ensureValidComponents) componentType.validate(); 
/* 242 */       this.archetype.validateComponentType(componentType);
/*     */       
/* 244 */       this.components[componentType.getIndex()] = (Component<ECS_TYPE>)component;
/*     */     } finally {
/* 246 */       this.lock.unlockWrite(stamp);
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
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void putComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 261 */     if (getComponent(componentType) != null) {
/* 262 */       replaceComponent(componentType, component);
/*     */     } else {
/* 264 */       addComponent(componentType, component);
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
/*     */   @Nullable
/*     */   public <T extends Component<ECS_TYPE>> T getComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 278 */     assert this.archetype != null;
/* 279 */     assert this.components != null;
/*     */     
/* 281 */     long stamp = this.lock.readLock();
/*     */     try {
/* 283 */       if (this.ensureValidComponents) componentType.validate(); 
/* 284 */       if (!this.archetype.contains(componentType)) return null; 
/* 285 */       return (T)this.components[componentType.getIndex()];
/*     */     } finally {
/* 287 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void removeComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 298 */     assert this.archetype != null;
/* 299 */     assert this.components != null;
/*     */     
/* 301 */     long stamp = this.lock.writeLock();
/*     */     try {
/* 303 */       if (this.ensureValidComponents) componentType.validate(); 
/* 304 */       this.archetype.validateComponentType(componentType);
/*     */       
/* 306 */       this.archetype = Archetype.remove(this.archetype, componentType);
/* 307 */       this.components[componentType.getIndex()] = null;
/*     */     } finally {
/* 309 */       this.lock.unlockWrite(stamp);
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
/*     */   public <T extends Component<ECS_TYPE>> boolean tryRemoveComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 322 */     if (getComponent(componentType) == null) return false; 
/* 323 */     removeComponent(componentType);
/* 324 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSerializableComponents(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/* 334 */     assert this.archetype != null;
/* 335 */     return this.archetype.hasSerializableComponents(data);
/*     */   }
/*     */   
/*     */   public void updateData(@Nonnull ComponentRegistry.Data<ECS_TYPE> oldData, @Nonnull ComponentRegistry.Data<ECS_TYPE> newData) {
/* 339 */     assert this.archetype != null;
/* 340 */     assert this.components != null;
/* 341 */     assert this.registry != null;
/*     */     
/* 343 */     long stamp = this.lock.writeLock();
/*     */     
/*     */     try {
/* 346 */       if (this.archetype.isEmpty())
/*     */         return; 
/* 348 */       ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> unknownComponentType = this.registry.getUnknownComponentType();
/* 349 */       for (int i = 0; i < newData.getDataChangeCount(); i++) {
/*     */         
/* 351 */         DataChange dataChange = newData.getDataChange(i);
/* 352 */         if (dataChange instanceof ComponentChange) {
/*     */           String componentId;
/*     */           Codec<Component<ECS_TYPE>> componentCodec;
/* 355 */           ComponentChange<ECS_TYPE, ? extends Component<ECS_TYPE>> componentChange = (ComponentChange<ECS_TYPE, ? extends Component<ECS_TYPE>>)dataChange;
/* 356 */           ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = componentChange.getComponentType();
/*     */           
/* 358 */           switch (componentChange.getType()) {
/*     */             case REGISTERED:
/* 360 */               assert this.archetype != null;
/*     */ 
/*     */               
/* 363 */               if (this.archetype.contains(componentType)) {
/*     */                 break;
/*     */               }
/* 366 */               if (!this.archetype.contains(unknownComponentType))
/*     */                 break; 
/* 368 */               componentId = newData.getComponentId(componentType);
/*     */               
/* 370 */               componentCodec = newData.getComponentCodec((ComponentType)componentType);
/*     */ 
/*     */               
/* 373 */               if (componentCodec != null) {
/* 374 */                 UnknownComponents<ECS_TYPE> unknownComponents = (UnknownComponents<ECS_TYPE>)this.components[unknownComponentType.getIndex()];
/* 375 */                 assert unknownComponents != null;
/* 376 */                 Component<ECS_TYPE> component = unknownComponents.removeComponent(componentId, componentCodec);
/* 377 */                 if (component != null)
/*     */                 {
/* 379 */                   addComponent0(componentType, component);
/*     */                 }
/*     */               } 
/*     */               break;
/*     */             case UNREGISTERED:
/* 384 */               assert this.archetype != null;
/*     */ 
/*     */               
/* 387 */               if (!this.archetype.contains(componentType))
/*     */                 break; 
/* 389 */               componentId = oldData.getComponentId(componentType);
/*     */               
/* 391 */               componentCodec = oldData.getComponentCodec((ComponentType)componentType);
/*     */ 
/*     */               
/* 394 */               if (componentCodec != null) {
/*     */                 UnknownComponents<ECS_TYPE> unknownComponents;
/*     */                 
/* 397 */                 if (this.archetype.contains(unknownComponentType)) {
/* 398 */                   unknownComponents = (UnknownComponents<ECS_TYPE>)this.components[unknownComponentType.getIndex()];
/* 399 */                   assert unknownComponents != null;
/*     */                 } else {
/* 401 */                   unknownComponents = new UnknownComponents();
/* 402 */                   addComponent0(unknownComponentType, unknownComponents);
/*     */                 } 
/*     */ 
/*     */                 
/* 406 */                 Component<ECS_TYPE> component = this.components[componentType.getIndex()];
/* 407 */                 unknownComponents.addComponent(componentId, component, componentCodec);
/*     */               } 
/*     */               
/* 410 */               this.archetype = Archetype.remove(this.archetype, componentType);
/* 411 */               this.components[componentType.getIndex()] = null;
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 417 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Holder<ECS_TYPE> clone() {
/* 424 */     assert this.archetype != null;
/* 425 */     assert this.components != null;
/* 426 */     assert this.registry != null;
/*     */     
/* 428 */     long stamp = this.lock.readLock();
/*     */     
/*     */     try {
/* 431 */       Component[] arrayOfComponent = new Component[this.components.length];
/* 432 */       for (int i = 0; i < this.components.length; i++) {
/* 433 */         Component<ECS_TYPE> component = this.components[i];
/* 434 */         if (component != null) arrayOfComponent[i] = component.clone(); 
/*     */       } 
/* 436 */       return this.registry.newHolder(this.archetype, (Component<ECS_TYPE>[])arrayOfComponent);
/*     */     } finally {
/* 438 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   void loadComponentsMap(@Nonnull ComponentRegistry.Data<ECS_TYPE> data, @Nonnull Map<String, Component<ECS_TYPE>> map) {
/* 443 */     assert this.components != null;
/*     */     
/* 445 */     long stamp = this.lock.writeLock();
/*     */     
/*     */     try {
/* 448 */       ComponentType[] arrayOfComponentType = new ComponentType[map.size()];
/* 449 */       int i = 0;
/*     */       
/* 451 */       ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> unknownComponentType = data.getRegistry().getUnknownComponentType();
/* 452 */       UnknownComponents<ECS_TYPE> unknownComponents = (UnknownComponents<ECS_TYPE>)map.remove("Unknown");
/* 453 */       if (unknownComponents != null) {
/*     */         
/* 455 */         for (Map.Entry<String, BsonDocument> e : (Iterable<Map.Entry<String, BsonDocument>>)unknownComponents.getUnknownComponents().entrySet()) {
/* 456 */           ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> type = (ComponentType)data.getComponentType(e.getKey());
/* 457 */           if (type == null) {
/*     */             continue;
/*     */           }
/* 460 */           if (map.containsKey(e.getKey())) {
/*     */             continue;
/*     */           }
/* 463 */           Codec<Component<ECS_TYPE>> codec = data.getComponentCodec((ComponentType)type);
/*     */           
/* 465 */           ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 466 */           Component<ECS_TYPE> decodedComponent = (Component<ECS_TYPE>)codec.decode((BsonValue)e.getValue(), extraInfo);
/* 467 */           extraInfo.getValidationResults().logOrThrowValidatorExceptions(UnknownComponents.LOGGER);
/*     */           
/* 469 */           if (arrayOfComponentType.length <= i) {
/* 470 */             arrayOfComponentType = Arrays.<ComponentType>copyOf(arrayOfComponentType, i + 1);
/*     */           }
/*     */           
/* 473 */           arrayOfComponentType[i++] = type;
/* 474 */           int j = type.getIndex();
/* 475 */           if (this.components.length <= j) {
/* 476 */             this.components = Arrays.<Component<ECS_TYPE>>copyOf(this.components, j + 1);
/*     */           }
/* 478 */           this.components[j] = decodedComponent;
/*     */         } 
/*     */         
/* 481 */         if (arrayOfComponentType.length <= i) {
/* 482 */           arrayOfComponentType = Arrays.<ComponentType>copyOf(arrayOfComponentType, i + 1);
/*     */         }
/*     */         
/* 485 */         arrayOfComponentType[i++] = unknownComponentType;
/* 486 */         int index = unknownComponentType.getIndex();
/* 487 */         if (this.components.length <= index) {
/* 488 */           this.components = Arrays.<Component<ECS_TYPE>>copyOf(this.components, index + 1);
/*     */         }
/* 490 */         this.components[index] = (Component<ECS_TYPE>)unknownComponents;
/*     */       } 
/*     */       
/* 493 */       for (Map.Entry<String, Component<ECS_TYPE>> entry : map.entrySet()) {
/* 494 */         Component<ECS_TYPE> component = entry.getValue();
/*     */ 
/*     */         
/* 497 */         if (component instanceof TempUnknownComponent) { TempUnknownComponent tempUnknownComponent = (TempUnknownComponent)component;
/* 498 */           if (unknownComponents == null) {
/* 499 */             unknownComponents = new UnknownComponents();
/* 500 */             if (arrayOfComponentType.length <= i) {
/* 501 */               arrayOfComponentType = Arrays.<ComponentType>copyOf(arrayOfComponentType, i + 1);
/*     */             }
/* 503 */             arrayOfComponentType[i++] = unknownComponentType;
/* 504 */             int j = unknownComponentType.getIndex();
/* 505 */             if (this.components.length <= j) {
/* 506 */               this.components = Arrays.<Component<ECS_TYPE>>copyOf(this.components, j + 1);
/*     */             }
/* 508 */             this.components[j] = (Component<ECS_TYPE>)unknownComponents;
/*     */           } 
/*     */           
/* 511 */           unknownComponents.addComponent(entry.getKey(), tempUnknownComponent);
/*     */           
/*     */           continue; }
/*     */         
/* 515 */         ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)data.getComponentType(entry.getKey());
/* 516 */         if (arrayOfComponentType.length <= i) {
/* 517 */           arrayOfComponentType = Arrays.<ComponentType>copyOf(arrayOfComponentType, i + 1);
/*     */         }
/* 519 */         arrayOfComponentType[i++] = componentType;
/* 520 */         int index = componentType.getIndex();
/* 521 */         if (this.components.length <= index) {
/* 522 */           this.components = Arrays.<Component<ECS_TYPE>>copyOf(this.components, index + 1);
/*     */         }
/* 524 */         this.components[index] = component;
/*     */       } 
/* 526 */       this.archetype = Archetype.of((arrayOfComponentType.length == i) ? (ComponentType<ECS_TYPE, ?>[])arrayOfComponentType : Arrays.<ComponentType<ECS_TYPE, ?>>copyOf((ComponentType<ECS_TYPE, ?>[])arrayOfComponentType, i));
/*     */     } finally {
/* 528 */       this.lock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   Map<String, Component<ECS_TYPE>> createComponentsMap(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/* 534 */     assert this.archetype != null;
/* 535 */     assert this.components != null;
/*     */     
/* 537 */     long stamp = this.lock.readLock();
/*     */     try {
/* 539 */       if (this.archetype.isEmpty()) return (Map)Collections.emptyMap();
/*     */       
/* 541 */       ComponentRegistry<ECS_TYPE> registry = data.getRegistry();
/* 542 */       ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> unknownComponentType = registry.getUnknownComponentType();
/*     */       
/* 544 */       Object2ObjectOpenHashMap<String, TempUnknownComponent> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap(this.archetype.length());
/* 545 */       for (int i = this.archetype.getMinIndex(); i < this.archetype.length(); i++) {
/* 546 */         ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)this.archetype.get(i);
/* 547 */         if (componentType != null && 
/* 548 */           data.getComponentCodec(componentType) != null)
/*     */         {
/*     */           
/* 551 */           if (componentType == unknownComponentType) {
/* 552 */             UnknownComponents<ECS_TYPE> unknownComponents = (UnknownComponents<ECS_TYPE>)this.components[componentType.getIndex()];
/* 553 */             for (Map.Entry<String, BsonDocument> entry : (Iterable<Map.Entry<String, BsonDocument>>)unknownComponents.getUnknownComponents().entrySet()) {
/* 554 */               object2ObjectOpenHashMap.putIfAbsent(entry.getKey(), new TempUnknownComponent(entry.getValue()));
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 559 */             object2ObjectOpenHashMap.put(data.getComponentId(componentType), this.components[componentType.getIndex()]);
/*     */           }  } 
/* 561 */       }  return (Map)object2ObjectOpenHashMap;
/*     */     } finally {
/* 563 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 569 */     if (this == o) return true; 
/* 570 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 572 */     Holder<?> that = (Holder)o;
/*     */     
/* 574 */     long stamp = this.lock.readLock();
/* 575 */     long thatStamp = that.lock.readLock();
/*     */     try {
/* 577 */       if (!Objects.equals(this.archetype, that.archetype)) return false;
/*     */       
/* 579 */       return Arrays.equals((Object[])this.components, (Object[])that.components);
/*     */     } finally {
/* 581 */       that.lock.unlockRead(thatStamp);
/* 582 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 588 */     long stamp = this.lock.readLock();
/*     */     try {
/* 590 */       int result = (this.archetype != null) ? this.archetype.hashCode() : 0;
/* 591 */       result = 31 * result + Arrays.hashCode((Object[])this.components);
/* 592 */       return result;
/*     */     } finally {
/* 594 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 601 */     long stamp = this.lock.readLock();
/*     */     try {
/* 603 */       return "EntityHolder{archetype=" + String.valueOf(this.archetype) + ", components=" + 
/*     */         
/* 605 */         Arrays.toString((Object[])this.components) + "}";
/*     */     } finally {
/*     */       
/* 608 */       this.lock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\Holder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */