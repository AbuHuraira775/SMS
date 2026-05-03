package com.ah.studentmanagement.service.imp;

import com.ah.studentmanagement.dto.CourseDTO;
import com.ah.studentmanagement.dto.StudentDTO;
import com.ah.studentmanagement.entity.Courses;
import com.ah.studentmanagement.entity.Students;
import com.ah.studentmanagement.repository.CourseRepository;
import com.ah.studentmanagement.repository.StudentRepository;
import com.ah.studentmanagement.service.CourseService;
import com.ah.studentmanagement.service.StudentService;
import jakarta.transaction.Transactional;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author "Abu Huraira"
 **/

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper mapper){
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Students students = mapper.map(studentDTO, Students.class);
        Students saved = studentRepository.save(students);
        return mapper.map(saved, StudentDTO.class);
    }

    @Override
    public Page<StudentDTO> getStudentsByActiveTrue(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return studentRepository.findByActiveTrue(pageRequest)
                .map(student-> mapper.map(student, StudentDTO.class) );
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Students students = studentRepository.findById(id).orElseThrow(()-> new RuntimeException("No course found"));
        return mapper.map(students, StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Students student = studentRepository.findById(id).orElseThrow(()-> new RuntimeException("No student found"));
        mapper.map(studentDTO, student);
        studentRepository.save(student);
        return mapper.map(student, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findByActiveTrue().stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id) {
        return studentRepository.existsByEmailIgnoreCaseAndIdNot(email, id);
    }
}
