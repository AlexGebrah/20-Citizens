package telran.citizens.dao;

import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;

public class CitizensImpl implements Citizens {
    TreeSet<Person> idCollection;
    TreeSet<Person> lastNameCollection;
    TreeSet<Person> ageCollection;
    Comparator<Person> comparatorId = (p1, p2) -> p1.getId() - p2.getId();
    Comparator<Person> comparatorAge = (p1, p2) ->    {
       int res = Integer.compare(p1.getAge(), p2.getAge());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };
    Comparator<Person> comparatorLastName = (Person p1, Person p2) -> {
        int res = p1.getLastName().compareToIgnoreCase(p2.getLastName());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };

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
        Person res = idCollection.ceiling(searchPerson);
        return res!= null && res.getId() == id ? res : null;
    }

    // O(log(n))
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        LocalDate now = LocalDate.now();
        Person minPerson = new Person(Integer.MIN_VALUE, "", "", now.minusYears(minAge));
        Person maxPerson = new Person(Integer.MAX_VALUE, "", "", now.minusYears(maxAge));
        return ageCollection.subSet(minPerson, true, maxPerson, true);
    }

    //O(log(n))
    @Override
    public Iterable<Person> find(String lastName) {
        Person search = new Person(0, "", lastName, null);
        NavigableSet<Person> tail = lastNameCollection.tailSet(search, true);
        List<Person> result = new ArrayList<>();
        for (Person person : tail) {
            if (!person.getLastName().equalsIgnoreCase(lastName)) {
                break;
            }
            result.add(person);
        }
        return result.isEmpty() ? Collections.emptyList() : result;
    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonSortedById() {
        return idCollection;
    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonSortedByAge() {
        return ageCollection;
    }

    //O(1)
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
