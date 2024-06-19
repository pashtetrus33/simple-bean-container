package org.example;

public class Main {
    public static void main(String[] args) {

        //Простейшая реализация контейнера бинов
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.register(Injectable.class);
        beanFactory.register(Source.class);

        Source source = beanFactory.getBean(Source.class);
        source.call();
    }
}