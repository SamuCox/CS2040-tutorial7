import java.util.*;

class Patient {
   private String _name; private int _level;
   public Patient(String name, int lvl) { _name = name; _level = lvl; }
   
   public String toString() { return _level + ":" + _name; }
   public String getName() { return _name; }
   public int getLevel() { return _level; }
   public void addLevel(int toAdd) {_level += toAdd; }
}

class PatientQueue { // You proudly created this max-heap =P
   private ArrayList<Patient> _maxHeap;
   private HashMap<String, Integer> _nameToHeapIndex;
   public PatientQueue() {
      _maxHeap = new ArrayList<Patient>();
      _nameToHeapIndex = new HashMap<String, Integer>();
   }
   public String toString() { return "" + _maxHeap; }

   public void offerPatient(Patient newPatient) {
      _maxHeap.add(newPatient); // Add to bottom of heap and HT
      _nameToHeapIndex.put(newPatient.getName(), _maxHeap.size() - 1);
      bubbleUp(_maxHeap.size() - 1); // Bubble up newly added Patient
   }

   public Patient pollPatient() {
      if (_maxHeap.isEmpty()) return null;
      Patient removed = _maxHeap.get(0);
      _maxHeap.set(0, _maxHeap.get(_maxHeap.size() - 1));
      _maxHeap.remove(_maxHeap.size() - 1); // Replace root w/ last elm.
      _nameToHeapIndex.remove(removed.getName()); // Cleanup HT
      if (_maxHeap.isEmpty()) return removed;
      _nameToHeapIndex.put(_maxHeap.get(0).getName(), 0);
      bubbleDown(0); // Bubble down new root
      return removed;
   }

   private void bubbleUp(int targetIdx) {
      while (targetIdx > 0) { // While target not root
         int parentIdx = (targetIdx - 1) / 2;
         Patient target = _maxHeap.get(targetIdx),
                 parent = _maxHeap.get(parentIdx);
         if (target.getLevel() <= parent.getLevel()) // No violation
            return;
         swap(targetIdx, parentIdx); // Violation, swap
         targetIdx = parentIdx; // Move up, check for further violations
      }
   }
   private void bubbleDown(int targetIdx) { // aka heapRebuild()
      while (targetIdx < _maxHeap.size() / 2) { // While target not leaf
         int leftChildIdx = targetIdx * 2 + 1;
         Patient target = _maxHeap.get(targetIdx),
                 leftChild = _maxHeap.get(leftChildIdx),
                 rightChild = (leftChildIdx + 1 == _maxHeap.size() ?
                                null : _maxHeap.get(leftChildIdx + 1));
         int largerChildIdx = leftChildIdx;
         if (rightChild != null && // Has right child, check if bigger
                 leftChild.getLevel() < rightChild.getLevel())
            largerChildIdx++;
         Patient largerChild = _maxHeap.get(largerChildIdx);
         if (target.getLevel() < largerChild.getLevel())
            swap(targetIdx, largerChildIdx); // Violation, swap
         targetIdx = largerChildIdx; // Move down, check violations
      }
   }

   private void swap(int leftIdx, int rightIdx) {
      Patient newLeft = _maxHeap.get(rightIdx),
              newRight = _maxHeap.get(leftIdx);
      _maxHeap.set(rightIdx, newRight); // Swap heap elments
      _maxHeap.set(leftIdx, newLeft);
      _nameToHeapIndex.put(newLeft.getName(), leftIdx); // Swap HT idxs.
      _nameToHeapIndex.put(newRight.getName(), rightIdx);
   }

   public void updatePatient(String name, int toAdd) {
      Integer heapIdx = _nameToHeapIndex.get(name);
      if (toAdd == 0 || heapIdx == null) return; // No change / not found
      _maxHeap.get(heapIdx).addLevel(toAdd);
      if (toAdd > 0) bubbleUp(heapIdx); // Incr - may have violations above
      else bubbleDown(heapIdx); // Decr - May have violations below
   }

   public Patient removePatient(String name) {
      Integer match = _nameToHeapIndex.remove(name); // Cleanup HT
      if (match == null) return null; // Not found
      int heapIdx = match;

      Patient removed = _maxHeap.get(heapIdx),
              newElm = _maxHeap.get(_maxHeap.size() - 1);
      _maxHeap.set(heapIdx, newElm); // Replace removed w/ last elm.
      _maxHeap.remove(_maxHeap.size() - 1);
      if (heapIdx == _maxHeap.size()) return removed; // Removed last elm
      _nameToHeapIndex.put(newElm.getName(), heapIdx); // Cleanup HT again

      if (newElm.getLevel() > removed.getLevel())
         bubbleUp(heapIdx); // May have violations above
      else if (newElm.getLevel() < removed.getLevel())
         bubbleDown(heapIdx); // May have violations below
      return removed;
   }
}

class tut8q2 {
   public static void main(String[] args) {
      System.out.println("¯\\_(ツ)_/¯");
   }
}














