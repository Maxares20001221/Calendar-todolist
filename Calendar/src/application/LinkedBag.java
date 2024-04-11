package application;

public class LinkedBag<T> implements BagInterface<T> {
	
	private Node firstNode; // Reference to first node
    private int numberOfEntries;

    public LinkedBag() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public int getCurrentSize() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean add(T newEntry) {
        // Add to beginning of chain:
        Node newNode = new Node(newEntry, firstNode);
        firstNode = newNode;
        numberOfEntries++;
        return true;
    }

    @Override
    public T remove() {
        T result = null;
        if (firstNode != null) {
            result = (T) firstNode.getData();
            firstNode = firstNode.getNextNode();
            numberOfEntries--;
        }
        return result;
    }

    @Override
    public boolean remove(T anEntry) {
        Node nodeN = getReferenceTo(anEntry);
        if (nodeN != null) {
            nodeN.setData(firstNode.getData()); // Replace located entry with entry in first node
            firstNode = firstNode.getNextNode(); // Remove first node
            numberOfEntries--;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            remove();
        }
    }

    @Override
    public int getFrequencyOf(T anEntry) {
        int frequency = 0;
        Node currentNode = firstNode;
        while (currentNode != null) {
            if (anEntry.equals(currentNode.getData())) {
                frequency++;
            }
            currentNode = currentNode.getNextNode();
        }
        return frequency;
    }

    @Override
    public boolean contains(T anEntry) {
        return getReferenceTo(anEntry) != null;
    }

    @Override
    public T[] toArray() {
        // The cast is safe because the new array contains null entries.
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast
        int index = 0;
        Node currentNode = firstNode;
        while ((index < numberOfEntries) && (currentNode != null)) {
            result[index] = (T) currentNode.getData();
            index++;
            currentNode = currentNode.getNextNode();
        }
        return result;
    }

    // Returns a reference to the node containing the entry, if located,
    // or null otherwise. This is a helper method for methods that require this functionality.
    private Node getReferenceTo(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.getData())) {
                found = true;
            } else {
                currentNode = currentNode.getNextNode();
            }
        }
        return found ? currentNode : null;
    }
    
    
	
	
	

}
