import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentHomeSurnameStorage {
    private TreeMap<String, Set<Long>> surnamesTreeMapHome = new TreeMap<>();

    public void studentCreateHome(Long id, String surname) {
        Set<Long> existingId = surnamesTreeMapHome.getOrDefault(surname, new HashSet<>());
        existingId.add(id);
        surnamesTreeMapHome.put(surname, existingId);

    }

    public void studentDeleteHome(Long id, String surname) {
        surnamesTreeMapHome.get(surname).remove(id);
    }

    public void studentUpdateHome(Long id, String oldsSurname, String newsSurname) {
        studentDeleteHome(id, oldsSurname);
        studentCreateHome(id, newsSurname);
    }

    public Set<Long> getStudentBySurnamesLessOrEqualThanHome(String surnameStart, String surnameEnd) {

        Set<Long> result;
        result = surnamesTreeMapHome
                .subMap(surnameStart, true, surnameEnd, true)
                .values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return result;

    }


}


