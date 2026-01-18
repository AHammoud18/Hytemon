/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.biomes.BiomeAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockmask.BlockMaskAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.InverterCurveAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.MinCurveAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.MultiplierCurveAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.AmplitudeDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.AxisDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.CacheDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.CellWallDistanceDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.MaxDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.MinDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.PositionsTwistDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.RotatorDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.SliderDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.YOverrideDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes.DensityReturnTypeAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes.ReturnTypeAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.environmentproviders.DensityDelimitedEnvironmentProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.environmentproviders.EnvironmentProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets.ConditionAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets.SmallerThanConditionAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.layerassets.ConstantThicknessLayerAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.layerassets.LayerAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.noisegenerators.CellNoiseAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ImportedPatternAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.pointgenerators.MeshPointGeneratorAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.ImportedPositionProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.PositionProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.UnionPositionProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments.AssignmentsAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.ClusterPropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.ColumnPropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality.DirectionalityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.ScannerAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders.ExportedVectorProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders.ImportedVectorProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders.VectorProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.WorldStructureAsset;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AssetManager {
/*     */   static {
/*  60 */     AssetRegistry.register(
/*  61 */         (AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(BiomeAsset.class, (AssetMap)new DefaultAssetMap())
/*  62 */         .setPath("HytaleGenerator/Biomes"))
/*  63 */         .setKeyFunction(BiomeAsset::getId))
/*  64 */         .setCodec((AssetCodec)BiomeAsset.CODEC))
/*  65 */         .build());
/*     */     
/*  67 */     AssetRegistry.register(
/*  68 */         (AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(WorldStructureAsset.class, (AssetMap)new DefaultAssetMap())
/*  69 */         .setPath("HytaleGenerator/WorldStructures"))
/*  70 */         .setKeyFunction(WorldStructureAsset::getId))
/*  71 */         .setCodec((AssetCodec)WorldStructureAsset.CODEC))
/*  72 */         .build());
/*     */     
/*  74 */     AssetRegistry.register(
/*  75 */         (AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(DensityAsset.class, (AssetMap)new DefaultAssetMap())
/*  76 */         .setPath("HytaleGenerator/Density"))
/*  77 */         .setKeyFunction(DensityAsset::getId))
/*  78 */         .setCodec((AssetCodec)DensityAsset.CODEC))
/*  79 */         .build());
/*     */     
/*  81 */     AssetRegistry.register(
/*  82 */         (AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(BlockMaskAsset.class, (AssetMap)new DefaultAssetMap())
/*  83 */         .setPath("HytaleGenerator/MaterialMasks"))
/*  84 */         .setKeyFunction(BlockMaskAsset::getId))
/*  85 */         .setCodec((AssetCodec)BlockMaskAsset.CODEC))
/*  86 */         .build());
/*     */     
/*  88 */     AssetRegistry.register(
/*  89 */         (AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(AssignmentsAsset.class, (AssetMap)new DefaultAssetMap())
/*  90 */         .setPath("HytaleGenerator/Assignments"))
/*  91 */         .setKeyFunction(AssignmentsAsset::getId))
/*  92 */         .setCodec((AssetCodec)AssignmentsAsset.CODEC))
/*  93 */         .build());
/*     */     
/*  95 */     AssetRegistry.register(
/*  96 */         (AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(SettingsAsset.class, (AssetMap)new DefaultAssetMap())
/*  97 */         .setPath("HytaleGenerator/Settings"))
/*  98 */         .setKeyFunction(SettingsAsset::getId))
/*  99 */         .setCodec((AssetCodec)SettingsAsset.CODEC))
/* 100 */         .build());
/*     */     
/* 102 */     DensityAsset.CODEC.register("SimplexNoise2D", SimplexNoise2dDensityAsset.class, SimplexNoise2dDensityAsset.CODEC);
/* 103 */     DensityAsset.CODEC.register("SimplexNoise3D", SimplexNoise3DDensityAsset.class, SimplexNoise3DDensityAsset.CODEC);
/* 104 */     DensityAsset.CODEC.register("Offset", OffsetDensityAsset.class, OffsetDensityAsset.CODEC);
/* 105 */     DensityAsset.CODEC.register("Sum", SumDensityAsset.class, SumDensityAsset.CODEC);
/* 106 */     DensityAsset.CODEC.register("Sqrt", SqrtDensityAsset.class, SqrtDensityAsset.CODEC);
/* 107 */     DensityAsset.CODEC.register("Pow", PowDensityAsset.class, PowDensityAsset.CODEC);
/* 108 */     DensityAsset.CODEC.register("Multiplier", MultiplierDensityAsset.class, MultiplierDensityAsset.CODEC);
/* 109 */     DensityAsset.CODEC.register("Amplitude", AmplitudeDensityAsset.class, AmplitudeDensityAsset.CODEC);
/* 110 */     DensityAsset.CODEC.register("Clamp", ClampDensityAsset.class, ClampDensityAsset.CODEC);
/* 111 */     DensityAsset.CODEC.register("SmoothClamp", SmoothClampDensityAsset.class, SmoothClampDensityAsset.CODEC);
/* 112 */     DensityAsset.CODEC.register("Max", MaxDensityAsset.class, MaxDensityAsset.CODEC);
/* 113 */     DensityAsset.CODEC.register("Min", MinDensityAsset.class, MinDensityAsset.CODEC);
/* 114 */     DensityAsset.CODEC.register("Floor", FloorDensityAsset.class, FloorDensityAsset.CODEC);
/* 115 */     DensityAsset.CODEC.register("Ceiling", CeilingDensityAsset.class, CeilingDensityAsset.CODEC);
/* 116 */     DensityAsset.CODEC.register("SmoothMax", SmoothMaxDensityAsset.class, SmoothMaxDensityAsset.CODEC);
/* 117 */     DensityAsset.CODEC.register("SmoothMin", SmoothMinDensityAsset.class, SmoothMinDensityAsset.CODEC);
/* 118 */     DensityAsset.CODEC.register("SmoothFloor", SmoothFloorDensityAsset.class, SmoothFloorDensityAsset.CODEC);
/* 119 */     DensityAsset.CODEC.register("SmoothCeiling", SmoothCeilingDensityAsset.class, SmoothCeilingDensityAsset.CODEC);
/* 120 */     DensityAsset.CODEC.register("Constant", ConstantDensityAsset.class, ConstantDensityAsset.CODEC);
/* 121 */     DensityAsset.CODEC.register("Abs", AbsDensityAsset.class, AbsDensityAsset.CODEC);
/* 122 */     DensityAsset.CODEC.register("Inverter", InverterDensityAsset.class, InverterDensityAsset.CODEC);
/* 123 */     DensityAsset.CODEC.register("AmplitudeConstant", AmplitudeConstantAsset.class, AmplitudeConstantAsset.CODEC);
/* 124 */     DensityAsset.CODEC.register("OffsetConstant", OffsetConstantAsset.class, OffsetConstantAsset.CODEC);
/* 125 */     DensityAsset.CODEC.register("Pipeline", PipelineDensityAsset.class, PipelineDensityAsset.CODEC);
/* 126 */     DensityAsset.CODEC.register("Normalizer", NormalizerDensityAsset.class, NormalizerDensityAsset.CODEC);
/* 127 */     DensityAsset.CODEC.register("Imported", ImportedDensityAsset.class, ImportedDensityAsset.CODEC);
/* 128 */     DensityAsset.CODEC.register("PositionsCellNoise", PositionsCellNoiseDensityAsset.class, PositionsCellNoiseDensityAsset.CODEC);
/* 129 */     DensityAsset.CODEC.register("Positions3D", Positions3DDensityAsset.class, Positions3DDensityAsset.CODEC);
/* 130 */     DensityAsset.CODEC.register("CellNoise2D", CellNoise2DDensityAsset.class, CellNoise2DDensityAsset.CODEC);
/* 131 */     DensityAsset.CODEC.register("CellNoise3D", CellNoise3DDensityAsset.class, CellNoise3DDensityAsset.CODEC);
/* 132 */     DensityAsset.CODEC.register("Gradient", GradientDensityAsset.class, GradientDensityAsset.CODEC);
/* 133 */     DensityAsset.CODEC.register("Scale", ScaleDensityAsset.class, ScaleDensityAsset.CODEC);
/* 134 */     DensityAsset.CODEC.register("Slider", SliderDensityAsset.class, SliderDensityAsset.CODEC);
/* 135 */     DensityAsset.CODEC.register("GradientWarp", GradientWarpDensityAsset.class, GradientWarpDensityAsset.CODEC);
/* 136 */     DensityAsset.CODEC.register("VectorWarp", VectorWarpDensityAsset.class, VectorWarpDensityAsset.CODEC);
/* 137 */     DensityAsset.CODEC.register("Cache2D", Cache2dDensityAsset_Deprecated.class, Cache2dDensityAsset_Deprecated.CODEC);
/* 138 */     DensityAsset.CODEC.register("Rotator", RotatorDensityAsset.class, RotatorDensityAsset.CODEC);
/* 139 */     DensityAsset.CODEC.register("PositionsPinch", PositionsPinchDensityAsset.class, PositionsPinchDensityAsset.CODEC);
/* 140 */     DensityAsset.CODEC.register("PositionsTwist", PositionsTwistDensityAsset.class, PositionsTwistDensityAsset.CODEC);
/* 141 */     DensityAsset.CODEC.register("BaseHeight", BaseHeightDensityAsset.class, BaseHeightDensityAsset.CODEC);
/* 142 */     DensityAsset.CODEC.register("CurveMapper", CurveMapperDensityAsset.class, CurveMapperDensityAsset.CODEC);
/* 143 */     DensityAsset.CODEC.register("Anchor", AnchorDensityAsset.class, AnchorDensityAsset.CODEC);
/* 144 */     DensityAsset.CODEC.register("Distance", DistanceDensityAsset.class, DistanceDensityAsset.CODEC);
/* 145 */     DensityAsset.CODEC.register("Shell", ShellDensityAsset.class, ShellDensityAsset.CODEC);
/* 146 */     DensityAsset.CODEC.register("Axis", AxisDensityAsset.class, AxisDensityAsset.CODEC);
/* 147 */     DensityAsset.CODEC.register("Plane", PlaneDensityAsset.class, PlaneDensityAsset.CODEC);
/* 148 */     DensityAsset.CODEC.register("Switch", SwitchDensityAsset.class, SwitchDensityAsset.CODEC);
/* 149 */     DensityAsset.CODEC.register("SwitchState", SwitchStateDensityAsset.class, SwitchStateDensityAsset.CODEC);
/* 150 */     DensityAsset.CODEC.register("Ellipsoid", EllipsoidDensityAsset.class, EllipsoidDensityAsset.CODEC);
/* 151 */     DensityAsset.CODEC.register("Cube", CubeDensityAsset.class, CubeDensityAsset.CODEC);
/* 152 */     DensityAsset.CODEC.register("Cuboid", CuboidDensityAsset.class, CuboidDensityAsset.CODEC);
/* 153 */     DensityAsset.CODEC.register("Cylinder", CylinderDensityAsset.class, CylinderDensityAsset.CODEC);
/* 154 */     DensityAsset.CODEC.register("CellWallDistance", CellWallDistanceDensityAsset.class, CellWallDistanceDensityAsset.CODEC);
/* 155 */     DensityAsset.CODEC.register("FastGradientWarp", FastGradientWarpDensityAsset.class, FastGradientWarpDensityAsset.CODEC);
/* 156 */     DensityAsset.CODEC.register("Mix", MixDensityAsset.class, MixDensityAsset.CODEC);
/* 157 */     DensityAsset.CODEC.register("MultiMix", MultiMixDensityAsset.class, MultiMixDensityAsset.CODEC);
/* 158 */     DensityAsset.CODEC.register("XValue", XValueDensityAsset.class, XValueDensityAsset.CODEC);
/* 159 */     DensityAsset.CODEC.register("YValue", YValueDensityAsset.class, YValueDensityAsset.CODEC);
/* 160 */     DensityAsset.CODEC.register("ZValue", ZValueDensityAsset.class, ZValueDensityAsset.CODEC);
/* 161 */     DensityAsset.CODEC.register("XOverride", XOverrideDensityAsset.class, XOverrideDensityAsset.CODEC);
/* 162 */     DensityAsset.CODEC.register("YOverride", YOverrideDensityAsset.class, YOverrideDensityAsset.CODEC);
/* 163 */     DensityAsset.CODEC.register("ZOverride", ZOverrideDensityAsset.class, ZOverrideDensityAsset.CODEC);
/* 164 */     DensityAsset.CODEC.register("Cache", CacheDensityAsset.class, CacheDensityAsset.CODEC);
/* 165 */     DensityAsset.CODEC.register("Angle", AngleDensityAsset.class, AngleDensityAsset.CODEC);
/* 166 */     DensityAsset.CODEC.register("Exported", ExportedDensityAsset.class, ExportedDensityAsset.CODEC);
/* 167 */     DensityAsset.CODEC.register("Terrain", TerrainDensityAsset.class, TerrainDensityAsset.CODEC);
/* 168 */     DensityAsset.CODEC.register("DistanceToBiomeEdge", DistanceToBiomeEdgeDensityAsset.class, DistanceToBiomeEdgeDensityAsset.CODEC);
/*     */     
/* 170 */     ContentFieldAsset.CODEC.register("BaseHeight", BaseHeightContentFieldAsset.class, BaseHeightContentFieldAsset.CODEC);
/*     */     
/* 172 */     TerrainAsset.CODEC.register("DAOTerrain", DensityTerrainAsset.class, DensityTerrainAsset.CODEC);
/*     */     
/* 174 */     NoiseAsset.CODEC.register("Simplex", SimplexNoiseAsset.class, SimplexNoiseAsset.CODEC);
/* 175 */     NoiseAsset.CODEC.register("Cell", CellNoiseAsset.class, CellNoiseAsset.CODEC);
/*     */     
/* 177 */     WorldStructureAsset.CODEC.register("NoiseRange", BasicWorldStructureAsset.class, BasicWorldStructureAsset.CODEC);
/*     */     
/* 179 */     MaterialProviderAsset.CODEC.register("Constant", ConstantMaterialProviderAsset.class, ConstantMaterialProviderAsset.CODEC);
/* 180 */     MaterialProviderAsset.CODEC.register("Solidity", SolidityMaterialProviderAsset.class, SolidityMaterialProviderAsset.CODEC);
/* 181 */     MaterialProviderAsset.CODEC.register("DownwardDepth", DownwardDepthMaterialProviderAsset.class, DownwardDepthMaterialProviderAsset.CODEC);
/* 182 */     MaterialProviderAsset.CODEC.register("DownwardSpace", DownwardSpaceMaterialProviderAsset.class, DownwardSpaceMaterialProviderAsset.CODEC);
/* 183 */     MaterialProviderAsset.CODEC.register("UpwardDepth", UpwardDepthMaterialProviderAsset.class, UpwardDepthMaterialProviderAsset.CODEC);
/* 184 */     MaterialProviderAsset.CODEC.register("UpwardSpace", UpwardSpaceMaterialProviderAsset.class, UpwardSpaceMaterialProviderAsset.CODEC);
/* 185 */     MaterialProviderAsset.CODEC.register("Queue", QueueMaterialProviderAsset.class, QueueMaterialProviderAsset.CODEC);
/* 186 */     MaterialProviderAsset.CODEC.register("SimpleHorizontal", SimpleHorizontalMaterialProviderAsset.class, SimpleHorizontalMaterialProviderAsset.CODEC);
/* 187 */     MaterialProviderAsset.CODEC.register("Striped", StripedMaterialProviderAsset.class, StripedMaterialProviderAsset.CODEC);
/* 188 */     MaterialProviderAsset.CODEC.register("FieldFunction", FieldFunctionMaterialProviderAsset.class, FieldFunctionMaterialProviderAsset.CODEC);
/* 189 */     MaterialProviderAsset.CODEC.register("TerrainDensity", TerrainDensityMaterialProviderAsset.class, TerrainDensityMaterialProviderAsset.CODEC);
/* 190 */     MaterialProviderAsset.CODEC.register("Weighted", WeightedMaterialProviderAsset.class, WeightedMaterialProviderAsset.CODEC);
/* 191 */     MaterialProviderAsset.CODEC.register("SpaceAndDepth", SpaceAndDepthMaterialProviderAsset.class, SpaceAndDepthMaterialProviderAsset.CODEC);
/* 192 */     MaterialProviderAsset.CODEC.register("Imported", ImportedMaterialProviderAsset.class, ImportedMaterialProviderAsset.CODEC);
/*     */     
/* 194 */     LayerAsset.CODEC.register("ConstantThickness", ConstantThicknessLayerAsset.class, ConstantThicknessLayerAsset.CODEC);
/* 195 */     LayerAsset.CODEC.register("NoiseThickness", NoiseThicknessAsset.class, NoiseThicknessAsset.CODEC);
/* 196 */     LayerAsset.CODEC.register("RangeThickness", RangeThicknessAsset.class, RangeThicknessAsset.CODEC);
/* 197 */     LayerAsset.CODEC.register("WeightedThickness", WeightedThicknessLayerAsset.class, WeightedThicknessLayerAsset.CODEC);
/*     */     
/* 199 */     ConditionAsset.CODEC.register("AndCondition", AndConditionAsset.class, AndConditionAsset.CODEC);
/* 200 */     ConditionAsset.CODEC.register("EqualsCondition", EqualsConditionAsset.class, EqualsConditionAsset.CODEC);
/* 201 */     ConditionAsset.CODEC.register("GreaterThanCondition", GreaterThanConditionAsset.class, GreaterThanConditionAsset.CODEC);
/* 202 */     ConditionAsset.CODEC.register("NotCondition", NotConditionAsset.class, NotConditionAsset.CODEC);
/* 203 */     ConditionAsset.CODEC.register("OrCondition", OrConditionAsset.class, OrConditionAsset.CODEC);
/* 204 */     ConditionAsset.CODEC.register("SmallerThanCondition", SmallerThanConditionAsset.class, SmallerThanConditionAsset.CODEC);
/* 205 */     ConditionAsset.CODEC.register("AlwaysTrueCondition", AlwaysTrueConditionAsset.class, AlwaysTrueConditionAsset.CODEC);
/*     */     
/* 207 */     PositionProviderAsset.CODEC.register("List", ListPositionProviderAsset.class, ListPositionProviderAsset.CODEC);
/* 208 */     PositionProviderAsset.CODEC.register("Mesh2D", Mesh2DPositionProviderAsset.class, Mesh2DPositionProviderAsset.CODEC);
/* 209 */     PositionProviderAsset.CODEC.register("Mesh3D", Mesh3DPositionProviderAsset.class, Mesh3DPositionProviderAsset.CODEC);
/* 210 */     PositionProviderAsset.CODEC.register("FieldFunction", FieldFunctionPositionProviderAsset.class, FieldFunctionPositionProviderAsset.CODEC);
/* 211 */     PositionProviderAsset.CODEC.register("Occurrence", FieldFunctionOccurrencePositionProviderAsset.class, FieldFunctionOccurrencePositionProviderAsset.CODEC);
/* 212 */     PositionProviderAsset.CODEC.register("Offset", OffsetPositionProviderAsset.class, OffsetPositionProviderAsset.CODEC);
/* 213 */     PositionProviderAsset.CODEC.register("Union", UnionPositionProviderAsset.class, UnionPositionProviderAsset.CODEC);
/* 214 */     PositionProviderAsset.CODEC.register("VerticalEliminator", VerticalEliminatorPositionProviderAsset.class, VerticalEliminatorPositionProviderAsset.CODEC);
/* 215 */     PositionProviderAsset.CODEC.register("Cache", CachedPositionProviderAsset.class, CachedPositionProviderAsset.CODEC);
/* 216 */     PositionProviderAsset.CODEC.register("BaseHeight", BaseHeightPositionProviderAsset.class, BaseHeightPositionProviderAsset.CODEC);
/* 217 */     PositionProviderAsset.CODEC.register("Imported", ImportedPositionProviderAsset.class, ImportedPositionProviderAsset.CODEC);
/* 218 */     PositionProviderAsset.CODEC.register("Anchor", AnchorPositionProviderAsset.class, AnchorPositionProviderAsset.CODEC);
/* 219 */     PositionProviderAsset.CODEC.register("Sphere", SpherePositionProviderAsset.class, SpherePositionProviderAsset.CODEC);
/*     */     
/* 221 */     PointGeneratorAsset.CODEC.register("Mesh", MeshPointGeneratorAsset.class, MeshPointGeneratorAsset.CODEC);
/*     */     
/* 223 */     AssignmentsAsset.CODEC.register("FieldFunction", FieldFunctionAssignmentsAsset.class, FieldFunctionAssignmentsAsset.CODEC);
/* 224 */     AssignmentsAsset.CODEC.register("Sandwich", SandwichAssignmentsAsset.class, SandwichAssignmentsAsset.CODEC);
/* 225 */     AssignmentsAsset.CODEC.register("Weighted", WeightedAssignmentsAsset.class, WeightedAssignmentsAsset.CODEC);
/* 226 */     AssignmentsAsset.CODEC.register("Constant", ConstantAssignmentsAsset.class, ConstantAssignmentsAsset.CODEC);
/* 227 */     AssignmentsAsset.CODEC.register("Imported", ImportedAssignmentsAsset.class, ImportedAssignmentsAsset.CODEC);
/*     */     
/* 229 */     PropAsset.CODEC.register("Box", BoxPropAsset.class, BoxPropAsset.CODEC);
/* 230 */     PropAsset.CODEC.register("Imported", ImportedPropAsset.class, ImportedPropAsset.CODEC);
/* 231 */     PropAsset.CODEC.register("Union", UnionPropAsset.class, UnionPropAsset.CODEC);
/* 232 */     PropAsset.CODEC.register("Column", ColumnPropAsset.class, ColumnPropAsset.CODEC);
/* 233 */     PropAsset.CODEC.register("Cluster", ClusterPropAsset.class, ClusterPropAsset.CODEC);
/* 234 */     PropAsset.CODEC.register("Queue", QueuePropAsset.class, QueuePropAsset.CODEC);
/* 235 */     PropAsset.CODEC.register("Prefab", PrefabPropAsset.class, PrefabPropAsset.CODEC);
/* 236 */     PropAsset.CODEC.register("PondFiller", PondFillerPropAsset.class, PondFillerPropAsset.CODEC);
/* 237 */     PropAsset.CODEC.register("Density", DensityPropAsset.class, DensityPropAsset.CODEC);
/*     */     
/* 239 */     DirectionalityAsset.CODEC.register("Imported", ImportedDirectionalityAsset.class, ImportedDirectionalityAsset.CODEC);
/* 240 */     DirectionalityAsset.CODEC.register("Static", StaticDirectionalityAsset.class, StaticDirectionalityAsset.CODEC);
/* 241 */     DirectionalityAsset.CODEC.register("Random", RandomDirectionalityAsset.class, RandomDirectionalityAsset.CODEC);
/* 242 */     DirectionalityAsset.CODEC.register("Pattern", PatternDirectionalityAsset.class, PatternDirectionalityAsset.CODEC);
/*     */     
/* 244 */     PatternAsset.CODEC.register("BlockType", MaterialPatternAsset.class, MaterialPatternAsset.CODEC);
/* 245 */     PatternAsset.CODEC.register("BlockSet", BlockSetPatternAsset.class, BlockSetPatternAsset.CODEC);
/* 246 */     PatternAsset.CODEC.register("Offset", OffsetPatternAsset.class, OffsetPatternAsset.CODEC);
/* 247 */     PatternAsset.CODEC.register("Floor", FloorPatternAsset.class, FloorPatternAsset.CODEC);
/* 248 */     PatternAsset.CODEC.register("Ceiling", CeilingPatternAsset.class, CeilingPatternAsset.CODEC);
/* 249 */     PatternAsset.CODEC.register("Wall", WallPatternAsset.class, WallPatternAsset.CODEC);
/* 250 */     PatternAsset.CODEC.register("Cuboid", CuboidPatternAsset.class, CuboidPatternAsset.CODEC);
/* 251 */     PatternAsset.CODEC.register("And", AndPatternAsset.class, AndPatternAsset.CODEC);
/* 252 */     PatternAsset.CODEC.register("Or", OrPatternAsset.class, OrPatternAsset.CODEC);
/* 253 */     PatternAsset.CODEC.register("Not", NotPatternAsset.class, NotPatternAsset.CODEC);
/* 254 */     PatternAsset.CODEC.register("Surface", SurfacePatternAsset.class, SurfacePatternAsset.CODEC);
/* 255 */     PatternAsset.CODEC.register("Gap", GapPatternAsset.class, GapPatternAsset.CODEC);
/* 256 */     PatternAsset.CODEC.register("FieldFunction", DensityPatternAsset.class, DensityPatternAsset.CODEC);
/* 257 */     PatternAsset.CODEC.register("Imported", ImportedPatternAsset.class, ImportedPatternAsset.CODEC);
/* 258 */     PatternAsset.CODEC.register("Constant", ConstantPatternAsset.class, ConstantPatternAsset.CODEC);
/*     */     
/* 260 */     ScannerAsset.CODEC.register("ColumnLinear", ColumnLinearScannerAsset.class, ColumnLinearScannerAsset.CODEC);
/* 261 */     ScannerAsset.CODEC.register("ColumnRandom", ColumnRandomScannerAsset.class, ColumnRandomScannerAsset.CODEC);
/* 262 */     ScannerAsset.CODEC.register("Origin", OriginScannerAsset.class, OriginScannerAsset.CODEC);
/* 263 */     ScannerAsset.CODEC.register("Area", AreaScannerAsset.class, AreaScannerAsset.CODEC);
/* 264 */     ScannerAsset.CODEC.register("Imported", ImportedScannerAsset.class, ImportedScannerAsset.CODEC);
/*     */     
/* 266 */     CurveAsset.CODEC.register("Imported", ImportedCurveAsset.class, ImportedCurveAsset.CODEC);
/* 267 */     CurveAsset.CODEC.register("Manual", ManualCurveAsset.class, ManualCurveAsset.CODEC);
/* 268 */     CurveAsset.CODEC.register("DistanceExponential", DistanceExponentialCurveAsset.class, DistanceExponentialCurveAsset.CODEC);
/* 269 */     CurveAsset.CODEC.register("DistanceS", DistanceSCurveAsset.class, DistanceSCurveAsset.CODEC);
/* 270 */     CurveAsset.CODEC.register("Not", NotCurveAsset.class, NotCurveAsset.CODEC);
/* 271 */     CurveAsset.CODEC.register("Multiplier", MultiplierCurveAsset.class, MultiplierCurveAsset.CODEC);
/* 272 */     CurveAsset.CODEC.register("Sum", SumCurveAsset.class, SumCurveAsset.CODEC);
/* 273 */     CurveAsset.CODEC.register("Inverter", InverterCurveAsset.class, InverterCurveAsset.CODEC);
/* 274 */     CurveAsset.CODEC.register("Clamp", ClampCurveAsset.class, ClampCurveAsset.CODEC);
/* 275 */     CurveAsset.CODEC.register("SmoothClamp", SmoothClampCurveAsset.class, SmoothClampCurveAsset.CODEC);
/* 276 */     CurveAsset.CODEC.register("Min", MinCurveAsset.class, MinCurveAsset.CODEC);
/* 277 */     CurveAsset.CODEC.register("Max", MinCurveAsset.class, MinCurveAsset.CODEC);
/* 278 */     CurveAsset.CODEC.register("SmoothMin", SmoothMinCurveAsset.class, SmoothMinCurveAsset.CODEC);
/* 279 */     CurveAsset.CODEC.register("SmoothMax", SmoothMaxCurveAsset.class, SmoothMaxCurveAsset.CODEC);
/* 280 */     CurveAsset.CODEC.register("SmoothFloor", SmoothFloorCurveAsset.class, SmoothFloorCurveAsset.CODEC);
/* 281 */     CurveAsset.CODEC.register("SmoothCeiling", SmoothCeilingCurveAsset.class, SmoothCeilingCurveAsset.CODEC);
/* 282 */     CurveAsset.CODEC.register("Floor", FloorCurveAsset.class, FloorCurveAsset.CODEC);
/* 283 */     CurveAsset.CODEC.register("Ceiling", CeilingCurveAsset.class, CeilingCurveAsset.CODEC);
/* 284 */     CurveAsset.CODEC.register("Constant", ConstantCurveAsset.class, ConstantCurveAsset.CODEC);
/*     */     
/* 286 */     ReturnTypeAsset.CODEC.register("CellValue", CellValueReturnTypeAsset.class, CellValueReturnTypeAsset.CODEC);
/* 287 */     ReturnTypeAsset.CODEC.register("Curve", CurveReturnTypeAsset.class, CurveReturnTypeAsset.CODEC);
/* 288 */     ReturnTypeAsset.CODEC.register("Distance", DistanceReturnTypeAsset.class, DistanceReturnTypeAsset.CODEC);
/* 289 */     ReturnTypeAsset.CODEC.register("Distance2", Distance2ReturnTypeAsset.class, Distance2ReturnTypeAsset.CODEC);
/* 290 */     ReturnTypeAsset.CODEC.register("Distance2Add", Distance2AddReturnTypeAsset.class, Distance2AddReturnTypeAsset.CODEC);
/* 291 */     ReturnTypeAsset.CODEC.register("Distance2Sub", Distance2SubReturnTypeAsset.class, Distance2SubReturnTypeAsset.CODEC);
/* 292 */     ReturnTypeAsset.CODEC.register("Distance2Mul", Distance2MulReturnTypeAsset.class, Distance2MulReturnTypeAsset.CODEC);
/* 293 */     ReturnTypeAsset.CODEC.register("Distance2Div", Distance2DivReturnTypeAsset.class, Distance2DivReturnTypeAsset.CODEC);
/* 294 */     ReturnTypeAsset.CODEC.register("Imported", ImportedReturnTypeAsset.class, ImportedReturnTypeAsset.CODEC);
/* 295 */     ReturnTypeAsset.CODEC.register("Density", DensityReturnTypeAsset.class, DensityReturnTypeAsset.CODEC);
/*     */     
/* 297 */     DistanceFunctionAsset.CODEC.register("Euclidean", EuclideanDistanceFunctionAsset.class, EuclideanDistanceFunctionAsset.CODEC);
/* 298 */     DistanceFunctionAsset.CODEC.register("Manhattan", ManhattanDistanceFunctionAsset.class, ManhattanDistanceFunctionAsset.CODEC);
/*     */     
/* 300 */     EnvironmentProviderAsset.CODEC.register("Constant", ConstantEnvironmentProviderAsset.class, ConstantEnvironmentProviderAsset.CODEC);
/* 301 */     EnvironmentProviderAsset.CODEC.register("DensityDelimited", DensityDelimitedEnvironmentProviderAsset.class, DensityDelimitedEnvironmentProviderAsset.CODEC);
/*     */     
/* 303 */     TintProviderAsset.CODEC.register("Constant", ConstantTintProviderAsset.class, ConstantTintProviderAsset.CODEC);
/* 304 */     TintProviderAsset.CODEC.register("DensityDelimited", DensityDelimitedTintProviderAsset.class, DensityDelimitedTintProviderAsset.CODEC);
/*     */     
/* 306 */     VectorProviderAsset.CODEC.register("Constant", ConstantVectorProviderAsset.class, ConstantVectorProviderAsset.CODEC);
/* 307 */     VectorProviderAsset.CODEC.register("DensityGradient", DensityGradientVectorProviderAsset.class, DensityGradientVectorProviderAsset.CODEC);
/* 308 */     VectorProviderAsset.CODEC.register("Cache", CacheVectorProviderAsset.class, CacheVectorProviderAsset.CODEC);
/* 309 */     VectorProviderAsset.CODEC.register("Exported", ExportedVectorProviderAsset.class, ExportedVectorProviderAsset.CODEC);
/* 310 */     VectorProviderAsset.CODEC.register("Imported", ImportedVectorProviderAsset.class, ImportedVectorProviderAsset.CODEC);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final HashMap<String, DensityAsset> densityAssets;
/*     */   @Nonnull
/*     */   private final HashMap<String, AssignmentsAsset> assigmentAssets;
/*     */   @Nonnull
/*     */   private final HashMap<String, BiomeAsset> biomeAssets;
/*     */   @Nonnull
/*     */   private final HashMap<String, WorldStructureAsset> worldStructureAssets;
/*     */   @Nonnull
/*     */   private final HashMap<String, BlockMaskAsset> blockMaskAssets;
/*     */   private SettingsAsset settingsAsset;
/*     */   @Nonnull
/*     */   private final HytaleLogger logger;
/*     */   private List<Runnable> reloadListeners;
/*     */   
/*     */   public AssetManager(@Nonnull EventRegistry eventRegistry, @Nonnull HytaleLogger logger) {
/* 330 */     this.logger = logger;
/*     */     
/* 332 */     this.reloadListeners = new ArrayList<>(1);
/*     */     
/* 334 */     this.densityAssets = new HashMap<>(1);
/* 335 */     this.assigmentAssets = new HashMap<>(1);
/* 336 */     this.biomeAssets = new HashMap<>(1);
/* 337 */     this.worldStructureAssets = new HashMap<>(1);
/* 338 */     this.blockMaskAssets = new HashMap<>(1);
/*     */     
/* 340 */     eventRegistry.register(LoadedAssetsEvent.class, DensityAsset.class, this::loadDensityAssets);
/* 341 */     eventRegistry.register(LoadedAssetsEvent.class, AssignmentsAsset.class, this::loadAssignmentsAssets);
/* 342 */     eventRegistry.register(LoadedAssetsEvent.class, BiomeAsset.class, this::loadBiomeAssets);
/* 343 */     eventRegistry.register(LoadedAssetsEvent.class, WorldStructureAsset.class, this::loadWorldStructureAssets);
/* 344 */     eventRegistry.register(LoadedAssetsEvent.class, SettingsAsset.class, this::loadSettingsAssets);
/* 345 */     eventRegistry.register(LoadedAssetsEvent.class, BlockMaskAsset.class, this::loadBlockMaskAssets);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadBlockMaskAssets(@Nonnull LoadedAssetsEvent<String, BlockMaskAsset, DefaultAssetMap<String, BlockMaskAsset>> event) {
/* 351 */     this.blockMaskAssets.clear();
/* 352 */     for (BlockMaskAsset value : event.getLoadedAssets().values()) {
/* 353 */       this.blockMaskAssets.put(value.getId(), value);
/* 354 */       this.logger.at(Level.FINE).log("Loaded BlockMask asset " + value.toString());
/*     */     } 
/* 356 */     triggerReloadListeners();
/*     */   }
/*     */   
/*     */   private void loadDensityAssets(@Nonnull LoadedAssetsEvent<String, DensityAsset, DefaultAssetMap<String, DensityAsset>> event) {
/* 360 */     this.densityAssets.clear();
/* 361 */     for (DensityAsset value : event.getLoadedAssets().values()) {
/* 362 */       this.densityAssets.put(value.getId(), value);
/* 363 */       this.logger.at(Level.FINE).log("Loaded Density asset " + value.toString());
/*     */     } 
/* 365 */     triggerReloadListeners();
/*     */   }
/*     */   
/*     */   private void loadAssignmentsAssets(@Nonnull LoadedAssetsEvent<String, AssignmentsAsset, DefaultAssetMap<String, AssignmentsAsset>> event) {
/* 369 */     this.assigmentAssets.clear();
/* 370 */     for (AssignmentsAsset value : event.getLoadedAssets().values()) {
/* 371 */       this.assigmentAssets.put(value.getId(), value);
/*     */     }
/* 373 */     triggerReloadListeners();
/*     */   }
/*     */   
/*     */   private void loadBiomeAssets(@Nonnull LoadedAssetsEvent<String, BiomeAsset, DefaultAssetMap<String, BiomeAsset>> event) {
/* 377 */     this.biomeAssets.clear();
/* 378 */     for (BiomeAsset value : event.getLoadedAssets().values()) {
/* 379 */       this.biomeAssets.put(value.getId(), value);
/*     */     }
/* 381 */     triggerReloadListeners();
/*     */   }
/*     */   
/*     */   private void loadWorldStructureAssets(@Nonnull LoadedAssetsEvent<String, WorldStructureAsset, DefaultAssetMap<String, WorldStructureAsset>> event) {
/* 385 */     this.biomeAssets.clear();
/* 386 */     for (WorldStructureAsset value : event.getLoadedAssets().values()) {
/* 387 */       this.worldStructureAssets.put(value.getId(), value);
/*     */     }
/* 389 */     triggerReloadListeners();
/*     */   }
/*     */   
/*     */   private void loadSettingsAssets(@Nonnull LoadedAssetsEvent<String, SettingsAsset, DefaultAssetMap<String, SettingsAsset>> event) {
/* 393 */     SettingsAsset asset = (SettingsAsset)event.getLoadedAssets().get("Settings");
/* 394 */     if (asset == null)
/* 395 */       return;  this.settingsAsset = asset;
/* 396 */     this.logger.at(Level.INFO).log("Loaded Settings asset.");
/* 397 */     triggerReloadListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SettingsAsset getSettingsAsset() {
/* 403 */     return this.settingsAsset;
/*     */   }
/*     */   
/*     */   public WorldStructureAsset getWorldStructureAsset(@Nonnull String id) {
/* 407 */     return this.worldStructureAssets.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerReloadListener(@Nonnull Runnable l) {
/* 413 */     this.reloadListeners.add(l);
/*     */   }
/*     */   
/*     */   public void unregisterReloadListener(@Nonnull Runnable l) {
/* 417 */     this.reloadListeners.remove(l);
/*     */   }
/*     */   
/*     */   private void triggerReloadListeners() {
/* 421 */     for (Runnable l : this.reloadListeners) {
/*     */       try {
/* 423 */         l.run();
/* 424 */       } catch (Exception e) {
/* 425 */         String msg = "Exception thrown by HytaleGenerator while executing a reload listener:\n";
/* 426 */         msg = msg + msg;
/* 427 */         LoggerUtil.getLogger().severe(msg);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\AssetManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */