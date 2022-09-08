import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) throws Exception {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        Map<String, Long> persons = Arrays.stream(RAW_DATA)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparing(Person::getId))
                .collect(groupingBy(Person::getName, Collectors.counting()));

        persons.forEach((key, value) -> System.out.println("Key: " + key + "\nValue:" + value));

        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */

        int[] input = {3, 4, 2, 7};
        int number = 10;

        System.out.println("Print pairs with sum = " + number);

        findPair(input, number);

        /*
        Task3
            Реализовать функцию нечеткого поиска
         */

        System.out.println("Find function:");
        fuzzySearch("car", "ca6$$#_rtwheel"); // true
        fuzzySearch("cwhl", "cartwheel"); // true
        fuzzySearch("cwhee", "cartwheel"); // true
        fuzzySearch("cartwheel", "cartwheel"); // true
        fuzzySearch("cwheeel", "cartwheel"); // false
        fuzzySearch("lw", "cartwheel"); // false

    }

    /**
     * Поиск первых 2 элементов, дающих в сумме требуемое значение
     * @param arr массив чисел
     * @param sum требуемое значение суммы 2 чисел
     */
    public static void findPair(int[] arr, int sum) throws Exception {
        if (arr == null) throw new Exception("Array is empty");

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if ((arr[i] + arr[j]) == sum) {
                    System.out.printf("[%d, %d]\n", arr[i], arr[j]);
                    return;
                }
            }
        }

        System.out.println("Pair not found");
    }

    /**
     * Функция нечеткого поиска
     * @param whatToLook что искать
     * @param whereToLook где искать
     */
    public static void fuzzySearch(String whatToLook, String whereToLook) throws Exception {
        if (whatToLook == null || whereToLook == null) throw new Exception("Argument is null");

        int count = 0;
        int wordIndex = 0;
        int phraseIndex = 0;

        char[] wordArray = whatToLook.toCharArray();
        char[] phraseArray = whereToLook.toCharArray();

        while (wordIndex < wordArray.length && phraseIndex < phraseArray.length){
            if (wordArray[wordIndex] == phraseArray[phraseIndex]){
                count++;
                wordIndex++;
            }
            phraseIndex++;
        }

        System.out.println(count == wordArray.length);
    }
}
