package listInterfaces;

public class Main {
    public static void main(String[] args) {

        LWLinkedList<Integer> linkyInt = new LWLinkedList();
        linkyInt.add(1);
        linkyInt.add(2);
        linkyInt.add(3);
        linkyInt.add(4);
        linkyInt.add(5);

        LWArrayList<Integer> aryInt = new LWArrayList();
        aryInt.add(1);
        aryInt.add(2);
        aryInt.add(3);
        aryInt.add(4);
        aryInt.add(5);

        int summyLinkyInt = 0;
        for (Integer obj: linkyInt) {

           summyLinkyInt+=obj;
        }

        int summyAryInt = 0;
        for (Integer obj:aryInt) {

            summyAryInt+=obj;
        }

        System.out.printf("\nDie Summe der mit Integer befüllten LinkedList ist: %d", summyLinkyInt);
        System.out.printf("\nDie Summe der mit Integer befüllten LinkedList ist: %d", summyAryInt);

        System.out.printf("\n//////////////////////////////////////////////////////////////////////////////////");

        LWLinkedList<Number> linkyMix = new LWLinkedList();
        linkyMix.add(1);
        linkyMix.add(2);
        linkyMix.add(3);
        linkyMix.add(4.0);
        linkyMix.add(5.0);

        LWArrayList<Number> aryMix = new LWArrayList();
        aryMix.add(1.0);
        aryMix.add(2.0);
        aryMix.add(3);
        aryMix.add(4);
        aryMix.add(5);

        double summyLinkyMix = 0d;
        for (Number obj : linkyMix) {
            summyLinkyMix += obj.doubleValue();
        }

        double summyAryMix = 0d;
        for (Number obj : aryMix) {
            summyAryMix += obj.doubleValue();
        }

        System.out.printf("\nDie Summe der mit Integer und Doubles befüllten LinkedList ist: %f", summyLinkyMix);
        System.out.printf("\nDie Summe der mit Integer und Doubles befüllten LinkedList ist: %f", summyAryMix);

        System.out.printf("\n//////////////////////////////////////////////////////////////////////////////////");

        LWLinkedList<Object> linkyMixAll = new LWLinkedList();
        linkyMixAll.add(1);
        linkyMixAll.add('2');
        linkyMixAll.add("3");
        linkyMixAll.add(4.0);
        linkyMixAll.add(5.0);

        LWLinkedList<Object> aryMixAll = new LWLinkedList();
        aryMixAll.add(1);
        aryMixAll.add('2');
        aryMixAll.add("3");
        aryMixAll.add(4.0);
        aryMixAll.add(5.0);

        double summyLinkyAll = 0d;
        for (Object obj : linkyMixAll) {
            if (obj instanceof Number num)
                summyLinkyAll += num.doubleValue();
        }

        double summyAryAll = 0d;
        for (Object obj : aryMixAll) {
            if (obj instanceof Number num)
                summyAryAll += num.doubleValue();
        }

        System.out.printf("\nDie Summe der bunt befüllten LinkedList ist: %f", summyLinkyAll);
        System.out.printf("\nDie Summe der bunt befüllten LinkedList ist: %f", summyAryAll);

        System.out.printf("\n//////////////////////////////////////////////////////////////////////////////////");

        LWList<Integer> ary = LWFactory.makeMe("Array");
        LWList<Integer> linky = LWFactory.makeMe("Linked");

        long starttimeAry = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ary.add(0, 1);
        }
        long endtimeAry = System.nanoTime();
        System.out.printf("\nTime to fill the Array list: \n%sns", (endtimeAry-starttimeAry));

        long starttimeLinky = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            linky.add(0,1);
        }
        long endtimeLinky = System.nanoTime();
        System.out.printf("\nTime to fill the Linked list: \n%sns", (endtimeLinky-starttimeLinky));
    }
}