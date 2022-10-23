package fakeprinciples;

public class AntiL {
    public class Person {
        String name;
        String secondName;
        public String getSecondName() {
            return secondName;
        }
    }

    public class PersonWithoutSecondName extends Person {
        @Override
        public String getSecondName() {
            throw new RuntimeException("Фамилии то нет");
        }
    }
}
