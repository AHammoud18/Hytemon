package com.hypixel.hytale.server.worldgen.cave.shape;

import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
import com.hypixel.hytale.server.worldgen.cave.CaveType;
import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
import java.util.Random;

public interface CaveNodeShapeGenerator {
  CaveNodeShape generateCaveNodeShape(Random paramRandom, CaveType paramCaveType, CaveNode paramCaveNode, CaveNodeType.CaveNodeChildEntry paramCaveNodeChildEntry, Vector3d paramVector3d, float paramFloat1, float paramFloat2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\CaveNodeShapeEnum$CaveNodeShapeGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */