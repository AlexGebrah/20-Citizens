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

        List<Person> lastNameList = new ArrayList<>(lastNameCollection);
        List<Person> ageList = new ArrayList<>(ageCollection);

        int indexLastName = Collections.binarySearch(lastNameList, person, comparatorLastName);
        indexLastName = indexLastName >= 0 ? indexLastName : -indexLastName - 1;
        lastNameList.add(indexLastName, person);

        int indexAge = Collections.binarySearch(ageList, person, comparatorAge);
        indexAge = indexAge >= 0 ? indexAge : -indexAge - 1;
        ageList.add(indexAge, person);

        lastNameCollection = lastNameList;
        ageCollection = ageList;

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
//        for (Person p : lastNameCollection) {
//            if (p.getLastName().equals(lastName)) {
//                result.add(p);
//            }
//        }
        List<Person> lastNameList = new ArrayList<>(lastNameCollection);
        int index = Collections.binarySearch(lastNameList, new Person(0, "", lastName, null), comparatorLastName);
        if (index < 0) {
            return new ArrayList<>();
        }
        result.add(lastNameList.get(index));

        int left = index - 1;
        while (left >= 0 && lastNameList.get(left).getLastName().equalsIgnoreCase(lastName)) {
            result.add(0, lastNameList.get(left));
            left--;
        }

        int right = index + 1;
        while (right < lastNameList.size() && lastNameList.get(right).getLastName().equalsIgnoreCase(lastName)) {
            result.add(lastNameList.get(right));
            right++;
        }

        return result;
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
