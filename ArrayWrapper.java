class ArrayWrapper {
    private final int[] array;

    public ArrayWrapper(int[] array) {
        this.array = array;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayWrapper)) {
            return false;
        }
        ArrayWrapper that = (ArrayWrapper)o;
        return Arrays.equals(array , that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
    
    @Override
    public String toString(){
        return Arrays.toString(array);
    }
}