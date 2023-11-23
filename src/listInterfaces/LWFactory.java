package listInterfaces;

public class LWFactory {

    public static <T> LWList<T> makeMe(String listType) {
        switch (listType) {
            case "Array":
                return new LWArrayList();
            case "Linked":
                return new LWLinkedList() {
                };
            default:
                throw new IllegalArgumentException();
        }

    }
}
