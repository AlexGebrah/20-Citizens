package telran.citizens.dao;

import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;

import static java.util.Collections.addAll;

public class CitizensImpl implements Citizens {
    TreeSet<Person> idCollection;
    TreeSet<Person> lastNameCollection;
    TreeSet<Person> ageCollection;
    Comparator<Person> comparatorId = (p1, p2) -> p2.getId() - p1.getId();
    Comparator<Person> comparatorAge = (p1, p2) ->    p2.getAge() - p1.getAge();
    Comparator<Person> comparatorLastName = (Person p1, Person p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName());

    public CitizensImpl() {
        this.idCollection = new TreeSet<>(comparatorId);
        this.lastNameCollection = new TreeSet<>(comparatorLastName);
        this.ageCollection = new TreeSet<>(comparatorAge);
    }


    public CitizensImpl(List<Person> citizens) {
        this();
        idCollection.addAll(citizens);
        lastNameCollection.addAll(citizens);
        ageCollection.addAll(citizens);
    }

    //O(log(n))
    @Override
    public boolean add(Person person) {
        if (!idCollection.add(person)) {
            return false;
        }
        idCollection.add(person);
        lastNameCollection.add(person);
        ageCollection.add(person);
        return true;
    }

    // O(log(n))
    @Override
    public boolean remove(int id) {
        Person person = find(id);
        if (person != null) {
            lastNameCollection.remove(person);
            ageCollection.remove(person);
            return idCollection.remove(person);
        }
        return false;
    }


    //O(log(n))
    @Override
    public Person find(int id) {
        Person searchPerson = new Person(id, "", "", null);
        TreeSet<Person> res = (TreeSet<Person>) idCollection.subSet(searchPerson, true, searchPerson, true);
        return res.isEmpty() ? null : res.first();
    }

    // O(log(n))
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        Person minPerson = new Person(0, "", "", LocalDate.now().minusYears(maxAge));
        Person maxPerson = new Person(0, "", "", LocalDate.now().minusYears(minAge));

        TreeSet<Person> res = (TreeSet<Person>) ageCollection.subSet(minPerson, true, maxPerson, true);
        return res.isEmpty() ? Collections.emptyList() : res;
    }

    //O(log(n))
    @Override
    public Iterable<Person> find(String lastName) {
        Person searchPerson = new Person(0, "", lastName, null);
        TreeSet<Person> res = (TreeSet<Person>) lastNameCollection.subSet(searchPerson, true, searchPerson, true);
        return res.isEmpty() ? Collections.emptyList() : res;
    }


    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedById() {
        return idCollection;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedByAge() {
        return ageCollection;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedByLastNAme() {

        return lastNameCollection;
    }

    //О(1)
    @Override
    public int size() {
        return idCollection.size();
    }

    }
