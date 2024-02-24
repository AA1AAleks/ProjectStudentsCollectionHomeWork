import java.util.*;

import java.util.stream.Collectors;

public class StudentStorage {
    private Map<Long,  Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();
    private StudentHomeSurnameStorage studentHomeSurnameStorage = new StudentHomeSurnameStorage();

    private Long currentId = 0L;

    /**
     * Создание данных о студенте
     * @param student данные о студенте
     * @return сгенерированный уникальный идентификатор студента
     */
    public Long createStudent(Student student){
        Long nextId = getNextId();
        studentStorageMap.put(nextId, student);
        studentSurnameStorage.studentCreate(nextId, student.getSurname());
        studentHomeSurnameStorage.studentCreateHome(nextId,student.getSurname());
        return nextId;
    }

    /**
     * Обновления данных о студенте
     * @param id идентификатор студента
     * @param student данные студента
     * @return true если данные были обновлены, если студент не был найден
     */
    public boolean updateStudent(Long id, Student student){
        if(!studentStorageMap.containsKey(id)){
            return false;
        }else {
            String newStudent = student.getSurname();
            String oldStudent = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdated(id, oldStudent, newStudent);
            studentHomeSurnameStorage.studentUpdateHome(id,oldStudent, newStudent);
            studentStorageMap.put(id,student);
            return true;
        }
    }

    /**
     * Данные о студенте
     * @param id идентификатор студента
     * @return true если студент был удален
     * false если студент не был найден по идентификатору
     */
    public boolean deleteStudent(Long id){
       Student removed = studentStorageMap.remove(id);
       if(removed != null){
           String surname = removed.getSurname();
           studentSurnameStorage.studentDelete(id,surname);
           studentHomeSurnameStorage.studentDeleteHome(id,surname);
       }
        return removed != null;
    }
    public void search(String surname){
        Set<Long> students = studentSurnameStorage.getStudentBySurnamesLessOrEqualThan(surname);
        for (Long studentId : students){
            Student student = studentStorageMap.get(studentId);
            System.out.println(student);
        }
    }
    public List<Student> searchHome(String surname) {

        List<Student> resList = new ArrayList<>();
        Set<Long> students = studentHomeSurnameStorage
                .getStudentBySurnamesLessOrEqualThanHome(surname);
        for (Long studentId : students) {
            Student studentH = studentStorageMap.get(studentId);

            resList.add(studentH);
        }
        return resList;
    }
     // Set<Long> studentHome = studentStorageMap.keySet();
     // for (Long studId : studentHome) {
     //     Student studentH = studentStorageMap.get(studId);
     //     if (surname.isEmpty()) {
     //         System.out.println(studentH);

     //     }
     // }
  //  }

       /* Set<Long> studentHome = studentStorageMap.keySet();
        for (Long studId : studentHome){
              Student studentH = studentStorageMap.get(studId);
            if(surname.isEmpty()) {
                System.out.println(studentH);
            }

        }*/

    public Long getNextId(){
        currentId = currentId + 1;
        return currentId;
    }
    public void printAll(){
        System.out.println(studentStorageMap);
    }
    public void printMap(Map<String, Long> data){
        data.entrySet().stream().forEach(e->{
            System.out.println(e.getKey() + "-" + e.getValue());
        });
    }
    public Map<String,Long> getCountByCourse(){
        Map<String,Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        s-> s.getCourse(),
                        student -> 1L,
                        (count1,count2)->count1+count2
                ));
                return res;
      /*  Map<String,Long> res = new HashMap<>();
        for (Student student:studentStorageMap.values()){
            String key = student.getCourse();
            Long count = res.getOrDefault(key, 0L);
            count++;
            res.put(key, count);
        }
        return res;*/
    }
    public Map<String,Long> getCountByCity(){
        Map<String, Long> result;
        result = studentStorageMap.values().stream()
                .collect(Collectors.toMap(Student::getCity,
                        student -> 1L,
                        Long::sum));
        return result;
    }

}
