package telran.citizens.dao;

import telran.citizens.model.Person;

public interface Citizens  {
    boolean add (Person person);

    boolean remove (int id);

    Person find (int id);

    Iterable<Person> find (int minAge, int maxAge);

    Iterable<Person> find (String lastName);

    Iterable<Person> getAllPersonSortedById();

    Iterable<Person> getAllPersonSortedByAge();

    Iterable<Person> getAllPersonSortedByLastNAme();

    int size();
}
