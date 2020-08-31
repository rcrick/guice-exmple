package linkedbindings;

import com.google.inject.*;
import com.google.inject.name.Named;

class Head {
    String label;

    Head(String label) {
        this.label = label;
    }
}

class Person {
    Head head;

    Person(Head head) {
        this.head = head;
    }
}

class House {
    Person person;

    @Inject
    House(Person person) {
        this.person = person;
    }

    void test() {
        System.out.println(person.head.label);
    }
}

class HouseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(String.class).toInstance("smart");
    }

    @Provides
    Person providePerson(Head head) {
        return new Person(head);
    }

    @Provides
    Head provideHead(String label) {
        return new Head(label);
    }
}

public class test {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new HouseModule());
        House house = injector.getInstance(House.class);
        house.test();
    }
}
