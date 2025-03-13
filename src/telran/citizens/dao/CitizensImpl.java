package telran.citizens.dao;

import telran.citizens.model.Person;

import java.util.*;

public class CitizensImpl implements Citizens {
    Collection<Person> idCollection;
    Collection<Person> lastNameCollection;
    Collection<Person> ageCollection;
    Comparator<Person> comparatorAge = (p1, p2) ->    p2.getAge() - p1.getAge();
    Comparator<Person> comparatorLastName = (Person p1, Person p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName());

    public CitizensImpl() {
        this.idCollection = new ArrayList<>();
        this.lastNameCollection = new ArrayList<>();
        this.ageCollection = new ArrayList<>();
    }


    public CitizensImpl(List<Person> citizens) {
        this.idCollection = new ArrayList<>(citizens);
        this.lastNameCollection = new ArrayList<>(citizens);
        this.ageCollection = new ArrayList<>(citizens);
    }

    //O(n)
    @Override
    public boolean add(Person person) {
        if (find(person.getId()) != null) {
            return false;
        }
        idCollection.add(person);

        List<Person> lastNameList = (List<Person>) lastNameCollection;
        List<Person> ageList = (List<Person>) ageCollection;

        int indexLastName = Math.abs(Collections.binarySearch(lastNameList, person, comparatorLastName) + 1);
        lastNameList.add(indexLastName, person);

        int indexAge = Math.abs(Collections.binarySearch(ageList, person, comparatorAge) + 1);
        ageList.add(indexAge, person);
        return true;
    }

    // O(n)
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
        for (Person p : ageCollection) {
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
        for (Person p : lastNameCollection) {
            if (p.getLastName().equals(lastName)) {
                result.add(p);
            }
        }
        return result.isEmpty() ? null : result;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedById() {
        return new ArrayList<>(idCollection);
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedByAge() {
        return new ArrayList<>(ageCollection);
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonSortedByLastNAme() {

        return new ArrayList<>(lastNameCollection);
    }

    //О(1)
    @Override
    public int size() {
        return idCollection.size();
    }

    }
