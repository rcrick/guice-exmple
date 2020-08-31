package BindingAnnotations;

import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

abstract class Fruit {
    String price;

    void sell() {
        System.out.println(price);
    }
}

class Apple extends Fruit {
    @Inject
    Apple(@Named("applePrice") String price) {
        this.price = price;
    }
//    @Override
//    public void sell() {
//        System.out.println("Apple");
//    }
}

class Pear extends Fruit {
    @Inject
    Pear(@Named("pearPrice") String price) {
        this.price = price;
    }

//    @Override
//    public void sell() {
//        System.out.println("Pear");
//    }
}

class Shop {
    @Inject
    Shop(@Named("apple") Fruit fruit) {
        fruit.sell();
    }
}

class ShopMudule extends AbstractModule {
    @Override
    protected void configure() {
        // Avoid using .toInstance with objects that are complicated to create, since it can slow down application startup. You can use an @Provides method instead.
        bind(String.class).annotatedWith(Names.named("applePrice")).toInstance("applePrice: 10");
        bind(String.class).annotatedWith(Names.named("pearPrice")).toInstance("pearPrice: 5");
        bind(Fruit.class).annotatedWith(Names.named("apple")).to(Apple.class);
        bind(Fruit.class).annotatedWith(Names.named("pear")).to(Pear.class);
    }
}

class ShopModule2 extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Named("applePrice")
    String providerApplePrice() {
        return "applePrice: 10";
    }

    @Provides
    @Named("pearPrice")
    String providerPearPrice() {
        return "pearPrice: 5";
    }

    @Provides
    @Named("apple")
    Fruit provideApple(@Named("applePrice") String price) {
        return new Apple(price);
    }

    @Provides
    @Named("pear")
    Fruit providePear(@Named("pearPrice") String price) {
        return new Apple(price);
    }

}

public class test {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ShopModule2());
        Shop shop = injector.getInstance(Shop.class);
    }
}
