package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import java.util.Comparator;

public interface ByteIndirectPriorityQueue extends IndirectPriorityQueue<Byte> {
  ByteComparator comparator();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */