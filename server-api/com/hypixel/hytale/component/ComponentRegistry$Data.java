/*      */ package com.hypixel.hytale.component;
/*      */ 
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.lookup.MapProvidedMapCodec;
/*      */ import com.hypixel.hytale.common.util.ArrayUtil;
/*      */ import com.hypixel.hytale.component.data.change.DataChange;
/*      */ import com.hypixel.hytale.component.data.unknown.TempUnknownComponent;
/*      */ import com.hypixel.hytale.component.system.ISystem;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Collections;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Data<ECS_TYPE>
/*      */ {
/*      */   private final int version;
/*      */   @Nonnull
/*      */   private final ComponentRegistry<ECS_TYPE> registry;
/*      */   private final Object2IntMap<String> componentIdToIndex;
/*      */   private final int componentSize;
/*      */   @Nonnull
/*      */   private final String[] componentIds;
/*      */   @Nonnull
/*      */   private final BuilderCodec<? extends Component<ECS_TYPE>>[] componentCodecs;
/*      */   @Nonnull
/*      */   private final Supplier<? extends Component<ECS_TYPE>>[] componentSuppliers;
/*      */   @Nonnull
/*      */   private final ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>[] componentTypes;
/*      */   private final Object2IntMap<String> resourceIdToIndex;
/*      */   private final int resourceSize;
/*      */   @Nonnull
/*      */   private final String[] resourceIds;
/*      */   @Nonnull
/*      */   private final BuilderCodec<? extends Resource<ECS_TYPE>>[] resourceCodecs;
/*      */   @Nonnull
/*      */   private final Supplier<? extends Resource<ECS_TYPE>>[] resourceSuppliers;
/*      */   @Nonnull
/*      */   private final ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>[] resourceTypes;
/*      */   private final Object2IntMap<Class<? extends ISystem<ECS_TYPE>>> systemTypeClassToIndex;
/*      */   private final int systemTypeSize;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>[] systemTypes;
/*      */   @Nonnull
/*      */   private final BitSet[] systemTypeToSystemIndex;
/*      */   private final int systemSize;
/*      */   @Nonnull
/*      */   private final ISystem<ECS_TYPE>[] sortedSystems;
/*      */   @Nonnull
/*      */   private final Map<String, Codec<Component<ECS_TYPE>>> codecMap;
/*      */   @Nonnull
/*      */   private final BuilderCodec<Holder<ECS_TYPE>> entityCodec;
/*      */   @Nullable
/*      */   private final DataChange[] dataChanges;
/*      */   
/*      */   private Data(@Nonnull ComponentRegistry<ECS_TYPE> registry) {
/* 1462 */     this.version = 0;
/* 1463 */     this.registry = registry;
/*      */     
/* 1465 */     this.componentIdToIndex = Object2IntMaps.emptyMap();
/* 1466 */     this.componentSize = 0;
/* 1467 */     this.componentIds = ArrayUtil.EMPTY_STRING_ARRAY;
/* 1468 */     this.componentCodecs = (BuilderCodec<? extends Component<ECS_TYPE>>[])BuilderCodec.EMPTY_ARRAY;
/* 1469 */     this.componentSuppliers = (Supplier<? extends Component<ECS_TYPE>>[])ArrayUtil.emptySupplierArray();
/* 1470 */     this.componentTypes = (ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>[])ComponentType.EMPTY_ARRAY;
/*      */     
/* 1472 */     this.resourceIdToIndex = Object2IntMaps.emptyMap();
/* 1473 */     this.resourceSize = 0;
/* 1474 */     this.resourceIds = ArrayUtil.EMPTY_STRING_ARRAY;
/* 1475 */     this.resourceCodecs = (BuilderCodec<? extends Resource<ECS_TYPE>>[])BuilderCodec.EMPTY_ARRAY;
/* 1476 */     this.resourceSuppliers = (Supplier<? extends Resource<ECS_TYPE>>[])ArrayUtil.emptySupplierArray();
/* 1477 */     this.resourceTypes = (ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>[])ResourceType.EMPTY_ARRAY;
/*      */     
/* 1479 */     this.systemTypeClassToIndex = Object2IntMaps.emptyMap();
/* 1480 */     this.systemTypeSize = 0;
/* 1481 */     this.systemTypes = (SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>[])SystemType.EMPTY_ARRAY;
/* 1482 */     this.systemTypeToSystemIndex = ArrayUtil.EMPTY_BITSET_ARRAY;
/*      */     
/* 1484 */     this.systemSize = 0;
/* 1485 */     this.sortedSystems = (ISystem<ECS_TYPE>[])ISystem.EMPTY_ARRAY;
/*      */     
/* 1487 */     this.codecMap = Collections.emptyMap();
/* 1488 */     this.entityCodec = createCodec();
/*      */     
/* 1490 */     this.dataChanges = null;
/*      */   }
/*      */   
/*      */   private Data(int version, @Nonnull ComponentRegistry<ECS_TYPE> registry, DataChange... dataChanges) {
/* 1494 */     this.version = version;
/* 1495 */     this.registry = registry;
/*      */     
/* 1497 */     this.componentIdToIndex = (Object2IntMap<String>)new Object2IntOpenHashMap(registry.componentIdToIndex);
/* 1498 */     this.componentIdToIndex.defaultReturnValue(-2147483648);
/* 1499 */     this.componentSize = registry.componentSize;
/* 1500 */     this.componentIds = Arrays.<String>copyOf(registry.componentIds, this.componentSize);
/* 1501 */     this.componentCodecs = Arrays.<BuilderCodec<? extends Component<ECS_TYPE>>>copyOf(registry.componentCodecs, this.componentSize);
/* 1502 */     this.componentSuppliers = Arrays.<Supplier<? extends Component<ECS_TYPE>>>copyOf(registry.componentSuppliers, this.componentSize);
/* 1503 */     this.componentTypes = Arrays.<ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>>copyOf(registry.componentTypes, this.componentSize);
/*      */     
/* 1505 */     this.resourceIdToIndex = (Object2IntMap<String>)new Object2IntOpenHashMap(registry.resourceIdToIndex);
/* 1506 */     this.resourceIdToIndex.defaultReturnValue(-2147483648);
/* 1507 */     this.resourceSize = registry.resourceSize;
/* 1508 */     this.resourceIds = Arrays.<String>copyOf(registry.resourceIds, this.resourceSize);
/* 1509 */     this.resourceCodecs = Arrays.<BuilderCodec<? extends Resource<ECS_TYPE>>>copyOf(registry.resourceCodecs, this.resourceSize);
/* 1510 */     this.resourceSuppliers = Arrays.<Supplier<? extends Resource<ECS_TYPE>>>copyOf(registry.resourceSuppliers, this.resourceSize);
/* 1511 */     this.resourceTypes = Arrays.<ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>>copyOf(registry.resourceTypes, this.resourceSize);
/*      */     
/* 1513 */     this.systemTypeClassToIndex = (Object2IntMap<Class<? extends ISystem<ECS_TYPE>>>)new Object2IntOpenHashMap(registry.systemTypeClassToIndex);
/* 1514 */     this.systemTypeClassToIndex.defaultReturnValue(-2147483648);
/* 1515 */     this.systemTypeSize = registry.systemTypeSize;
/* 1516 */     this.systemTypes = Arrays.<SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>>copyOf(registry.systemTypes, this.systemTypeSize);
/* 1517 */     this.systemTypeToSystemIndex = Arrays.<BitSet>copyOf(registry.systemTypeToSystemIndex, this.systemTypeSize);
/*      */     
/* 1519 */     this.systemSize = registry.systemSize;
/* 1520 */     this.sortedSystems = Arrays.<ISystem<ECS_TYPE>>copyOf(registry.sortedSystems, this.systemSize);
/*      */     
/* 1522 */     Object2ObjectOpenHashMap<String, Codec<Component<ECS_TYPE>>> codecMap = new Object2ObjectOpenHashMap(this.componentSize);
/* 1523 */     for (int i = 0; i < this.componentSize; i++) {
/* 1524 */       if (this.componentCodecs[i] != null)
/*      */       {
/* 1526 */         codecMap.put(this.componentIds[i], this.componentCodecs[i]); } 
/*      */     } 
/* 1528 */     this.codecMap = (Map<String, Codec<Component<ECS_TYPE>>>)codecMap;
/* 1529 */     this.entityCodec = createCodec();
/*      */     
/* 1531 */     this.dataChanges = dataChanges;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private BuilderCodec<Holder<ECS_TYPE>> createCodec() {
/* 1539 */     Function<Codec<Component<ECS_TYPE>>, Codec<Component<ECS_TYPE>>> function = componentCodec -> (componentCodec != null) ? componentCodec : TempUnknownComponent.COMPONENT_CODEC;
/* 1540 */     Objects.requireNonNull(this.registry); return ((BuilderCodec.Builder)BuilderCodec.builder(Holder.class, this.registry::newHolder)
/* 1541 */       .addField(new KeyedCodec("Components", (Codec)new MapProvidedMapCodec(this.codecMap, function, java.util.LinkedHashMap::new, false)), (holder, map) -> holder.loadComponentsMap(this, map), holder -> holder.createComponentsMap(this)))
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1546 */       .build();
/*      */   }
/*      */   
/*      */   public int getVersion() {
/* 1550 */     return this.version;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ComponentRegistry<ECS_TYPE> getRegistry() {
/* 1555 */     return this.registry;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ComponentType<ECS_TYPE, ?> getComponentType(String id) {
/* 1560 */     int index = this.componentIdToIndex.getInt(id);
/* 1561 */     if (index == Integer.MIN_VALUE) return null; 
/* 1562 */     return this.componentTypes[index];
/*      */   }
/*      */   
/*      */   public int getComponentSize() {
/* 1566 */     return this.componentSize;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getComponentId(@Nonnull ComponentType<ECS_TYPE, ?> componentType) {
/* 1571 */     return this.componentIds[componentType.getIndex()];
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public <T extends Component<ECS_TYPE>> Codec<T> getComponentCodec(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1577 */     return (Codec)this.componentCodecs[componentType.getIndex()];
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> T createComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1582 */     componentType.validateRegistry(this.registry);
/* 1583 */     componentType.validate();
/* 1584 */     return (T)this.componentSuppliers[componentType.getIndex()].get();
/*      */   }
/*      */   
/*      */   public ResourceType<ECS_TYPE, ?> getResourceType(int index) {
/* 1588 */     return this.resourceTypes[index];
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ResourceType<ECS_TYPE, ?> getResourceType(String id) {
/* 1593 */     int index = this.resourceIdToIndex.getInt(id);
/* 1594 */     if (index == Integer.MIN_VALUE) return null; 
/* 1595 */     return this.resourceTypes[index];
/*      */   }
/*      */   
/*      */   public int getResourceSize() {
/* 1599 */     return this.resourceSize;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getResourceId(@Nonnull ResourceType<ECS_TYPE, ?> resourceType) {
/* 1604 */     return this.resourceIds[resourceType.getIndex()];
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public <T extends Resource<ECS_TYPE>> BuilderCodec<T> getResourceCodec(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 1610 */     return (BuilderCodec)this.resourceCodecs[resourceType.getIndex()];
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Resource<ECS_TYPE>> T createResource(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 1615 */     resourceType.validateRegistry(this.registry);
/* 1616 */     resourceType.validate();
/* 1617 */     return (T)this.resourceSuppliers[resourceType.getIndex()].get();
/*      */   }
/*      */   
/*      */   public int getSystemTypeSize() {
/* 1621 */     return this.systemTypeSize;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T extends ISystem<ECS_TYPE>> SystemType<ECS_TYPE, T> getSystemType(Class<? super T> systemTypeClass) {
/* 1626 */     int systemTypeClassToIndexInt = this.systemTypeClassToIndex.getInt(systemTypeClass);
/* 1627 */     if (systemTypeClassToIndexInt == Integer.MIN_VALUE) return null;
/*      */     
/* 1629 */     return (SystemType)this.systemTypes[systemTypeClassToIndexInt];
/*      */   }
/*      */   
/*      */   public SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>> getSystemType(int systemTypeIndex) {
/* 1633 */     return this.systemTypes[systemTypeIndex];
/*      */   }
/*      */   
/*      */   public <T extends ISystem<ECS_TYPE>> BitSet getSystemIndexesForType(@Nonnull SystemType<ECS_TYPE, T> systemType) {
/* 1637 */     return this.systemTypeToSystemIndex[systemType.getIndex()];
/*      */   }
/*      */   
/*      */   public int getSystemSize() {
/* 1641 */     return this.systemSize;
/*      */   }
/*      */   
/*      */   public ISystem<ECS_TYPE> getSystem(int systemIndex) {
/* 1645 */     return this.sortedSystems[systemIndex];
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends ISystem<ECS_TYPE>> T getSystem(int systemIndex, SystemType<ECS_TYPE, T> systemType) {
/* 1650 */     return (T)this.sortedSystems[systemIndex];
/*      */   }
/*      */   
/*      */   public int indexOf(ISystem<ECS_TYPE> system) {
/* 1654 */     int systemIndex = -1;
/* 1655 */     for (int i = 0; i < this.sortedSystems.length; i++) {
/* 1656 */       if (this.sortedSystems[i] == system) {
/* 1657 */         systemIndex = i;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1661 */     return systemIndex;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public BuilderCodec<Holder<ECS_TYPE>> getEntityCodec() {
/* 1666 */     return this.entityCodec;
/*      */   }
/*      */   
/*      */   public int getDataChangeCount() {
/* 1670 */     return this.dataChanges.length;
/*      */   }
/*      */   
/*      */   public DataChange getDataChange(int index) {
/* 1674 */     return this.dataChanges[index];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Object o) {
/* 1679 */     if (this == o) return true; 
/* 1680 */     if (o == null || getClass() != o.getClass()) return false;
/*      */     
/* 1682 */     Data<?> data = (Data)o;
/*      */     
/* 1684 */     return (this.version == data.version);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1689 */     return this.version;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String toString() {
/* 1695 */     return "Data{version=" + this.version + ", componentSize=" + this.componentSize + ", componentSuppliers=" + 
/*      */ 
/*      */       
/* 1698 */       Arrays.toString((Object[])this.componentSuppliers) + ", resourceSize=" + this.resourceSize + ", resourceSuppliers=" + 
/*      */       
/* 1700 */       Arrays.toString((Object[])this.resourceSuppliers) + ", systemSize=" + this.systemSize + ", sortedSystems=" + 
/*      */       
/* 1702 */       Arrays.toString((Object[])this.sortedSystems) + ", dataChanges=" + 
/* 1703 */       Arrays.toString((Object[])this.dataChanges) + "}";
/*      */   }
/*      */ 
/*      */   
/*      */   public void appendDump(@Nonnull String prefix, @Nonnull StringBuilder sb) {
/* 1708 */     sb.append(prefix).append("version=").append(this.version).append("\n");
/* 1709 */     sb.append(prefix).append("componentSize=").append(this.componentSize).append("\n");
/* 1710 */     sb.append(prefix).append("componentSuppliers=").append("\n"); int i;
/* 1711 */     for (i = 0; i < this.componentSize; i++) {
/* 1712 */       sb.append(prefix).append("\t- ").append(i).append("\t").append(this.componentSuppliers[i]).append("\n");
/*      */     }
/* 1714 */     sb.append(prefix).append("resourceSuppliers=").append("\n");
/* 1715 */     for (i = 0; i < this.resourceSize; i++) {
/* 1716 */       sb.append(prefix).append("\t- ").append(i).append("\t").append(this.resourceSuppliers[i]).append("\n");
/*      */     }
/* 1718 */     sb.append(prefix).append("systemSize=").append(this.systemSize).append("\n");
/* 1719 */     sb.append(prefix).append("sortedSystems=").append("\n");
/* 1720 */     for (i = 0; i < this.systemSize; i++) {
/* 1721 */       sb.append(prefix).append("\t- ").append(i).append("\t").append(this.sortedSystems[i]).append("\n");
/*      */     }
/* 1723 */     sb.append(prefix).append("dataChanges=").append("\n");
/* 1724 */     for (i = 0; i < this.dataChanges.length; i++)
/* 1725 */       sb.append(prefix).append("\t- ").append(i).append("\t").append(this.dataChanges[i]).append("\n"); 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\ComponentRegistry$Data.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */