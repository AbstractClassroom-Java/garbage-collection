Java garbage collection (GC) is an automatic process that manages memory by identifying and disposing of objects that are no longer in use, freeing up resources and preventing memory leaks. Here’s a breakdown of how it works:

---

### **1. Managed Memory & Heap Space**
- **Heap**: Java programs allocate memory for objects in the heap.
- **GC Role**: The garbage collector's job is to find unused objects in the heap and reclaim their memory.

---

### **2. Object Reachability**
Garbage collection depends on the concept of *reachability*:
- **Reachable object**: Can be accessed directly or indirectly from a chain of references starting from *GC roots*.
- **Unreachable object**: Has no path from the GC roots and is considered garbage.

---

###  **3. GC Roots**
Common GC roots include:
- Local variables in active methods
- Static fields
- Active threads
- JNI references

---

###  **4. Mark-and-Sweep Algorithm (Simplified View)**
Most collectors follow some variation of this:
1. **Mark**: Traverse from GC roots and mark all reachable objects.
2. **Sweep**: Delete all unmarked objects (unreachable ones).
3. **Compact (optional)**: Rearranges memory to eliminate fragmentation.

---

###  **5. Generational Garbage Collection**
Java heap is divided into **generations** to optimize performance:
- **Young Generation**: Newly created objects (mostly short-lived).
  - Uses *minor GC*.
  - Divided into *Eden* and *Survivor spaces*.
- **Old (Tenured) Generation**: Objects that have survived multiple minor GCs.
  - Uses *major/full GC*.
- **Metaspace**: Stores class metadata (replaces PermGen from Java 8 onward).

---

###  **6. GC Algorithms in the JVM**
JVM offers different collectors for different use cases:
- **Serial GC**: Single-threaded, good for small applications.
- **Parallel GC (Throughput Collector)**: Multi-threaded, focuses on throughput.
- **G1 GC (Garbage First)**: Splits heap into regions, minimizes pause times.
- **ZGC / Shenandoah**: Low-latency collectors for large heaps.

---

### **7. You Can Influence GC**
You can't control GC directly, but you can:
- Suggest it with `System.gc()` (not guaranteed).
- Tune it with JVM options (e.g., `-Xmx`, `-XX:+UseG1GC`).
- Avoid unnecessary object creation to reduce GC load.

---

###  Summary
Garbage collection in Java:
- Automatically frees memory
- Tracks object reachability
- Uses generational strategies for efficiency
- Offers different collectors for various needs

Let me know if you want diagrams or help comparing the different garbage collectors!
