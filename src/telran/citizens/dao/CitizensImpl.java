package telran.citizens.dao;

import telran.citizens.model.Person;

import java.util.*;

public class CitizensImpl implements Citizens {
    Collection<Person> idCollection;
    Comparator<Person> comparatorAge = (p1, p2) ->    p2.getAge() - p1.getAge();
    Comparator<Person> comparatorLastName = (Person p1, Person p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName());

    public CitizensImpl() {
        this.idCollection = new ArrayList<>();
    }

    public CitizensImpl(List<Person> citizens) {
        this.idCollection = new ArrayList<>(citizens);
    }

    //O(n)
    @Override
    public boolean add(Person person) {
        if (find(person.getId()) != null) {
            return false;
        }
        idCollection.add(person);
        return true;
    }

    // O(n)
    @Override
    public boolean remove(int id) {
        Person person = find(id);
        if (person != null) {
            return idCollection.remove(person);
        }
        return false;
    }

    //O(n)
    @Override
    public Person find(int id) {
        for (Person p : idCollection) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // O(n)
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        List<Person> result = new ArrayList<>();
        for (Person p : idCollection) {
            if (p.getAge() >= minAge && p.getAge() <= maxAge) {
                result.add(p);
            }
        }
       return result.isEmpty() ? null : result;
    }

    //O(n)
    @Override
    public Iterable<Person> find(String lastName) {
        List<Person> result = new ArrayList<>();
        for (Person p : idCollection) {
            if (p.getLastName().equals(lastName)) {
                result.add(p);
            }
        }
        return result.isEmpty() ? null : result;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedById() {
        List<Person> personList = new ArrayList<>(idCollection);
        personList.sort((p1, p2) -> p1.compareTo(p2));
        return personList;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedByAge() {
        List<Person> personList = new ArrayList<>(idCollection);
        personList.sort(comparatorAge);
        return personList;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedByLastNAme() {
        List<Person> personList = new ArrayList<>(idCollection);
        personList.sort(comparatorLastName);
        return personList;
    }

    //О(1)
    @Override
    public int size() {
        return idCollection.size();
    }

    @Override
    public Iterator<Person> iterator() {
        return idCollection.iterator();
    }

}
