package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.controller.exception.SubServicesNotFoundException;
import com.example.HomeService_MVC.dto.user.DynamicSearchDTO;
import com.example.HomeService_MVC.dto.user.PasswordDTO;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.SubServices;
import com.example.HomeService_MVC.model.enumoration.Role;
import com.example.HomeService_MVC.repository.ExpertRepository;
import com.example.HomeService_MVC.service.interfaces.ExpertService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpertServiceImpel implements ExpertService {

    private final ExpertRepository expertRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubServicesServiceImpel subServicesServiceImpel;

    public ExpertServiceImpel(ExpertRepository expertRepository, BCryptPasswordEncoder bCryptPasswordEncoder, SubServicesServiceImpel subServicesServiceImpel) {
        this.expertRepository = expertRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.subServicesServiceImpel = subServicesServiceImpel;
    }

    @Override
    public void save(Expert expert) {
        expert.setPassword(bCryptPasswordEncoder.encode(expert.getPassword()));
        expertRepository.save(expert);
    }

    @Override
    public List<Expert> findAllByAcceptedFalse() {
        return expertRepository.findAllByAcceptedFalse();
    }

    @Override
    public Optional<Expert> findById(Integer id) {
        return expertRepository.findById(id);
    }

    @Override
    public Expert getById(Integer id) {
        return expertRepository.getById(id);
    }

    @Override
    public void ExpertAccept(Integer id) {
        Expert expert = findById(id).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        expert.setAccepted(true);
        expertRepository.save(expert);
    }

    @Override
    public void addExpertToSubService(String expertEmail, Integer subServicesId) {
        Expert expert = findByEmail(expertEmail).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new SubServicesNotFoundException("This subServices is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        subServicesSet.add(subServices);
        expert.setSubServices(subServicesSet);
        expertRepository.save(expert);
    }

    @Override
    public Optional<Expert> findByEmail(String expertEmail) {
        return expertRepository.findExpertByEmail(expertEmail);
    }

    @Override
    public void removeExpertSubServices(String expertEmail, Integer subServicesId) {
        Expert expert = findByEmail(expertEmail).orElseThrow(() -> new ExpertNotFoundException("This Expert is not found!"));
        Set<SubServices> subServicesSet = expert.getSubServices();
        SubServices subServices = subServicesServiceImpel.findById(subServicesId).orElseThrow(() -> new SubServicesNotFoundException("This subServices is not found!"));
        subServicesSet.remove(subServices);
        expert.setSubServices(subServicesSet);
        expertRepository.save(expert);
    }

    @Override
    public void updatePassword(Integer expertId, PasswordDTO passwordDTO) {
        Expert expert = getById(expertId);
        expert.setPassword(passwordDTO.getSinglePassword());
        expertRepository.save(expert);
    }

    @Override
    public void updateBalance(Expert expert) {
        expertRepository.save(expert);
    }

    @Override
    public void updateStars(Expert expert) {
        expertRepository.save(expert);
    }

    public List<Expert> filterExpert(DynamicSearchDTO dynamicSearch){
        Expert expert = new Expert(dynamicSearch.getFirstName(),
                dynamicSearch.getLastName(),
                dynamicSearch.getEmail(),null,null,
                Role.ROLE_EXPERT,null,null,dynamicSearch.getStars());

        List<Expert> experts = expertRepository.findAll(userSpecification(expert));
        SubServices subServices;
        if(dynamicSearch.getService() == null || dynamicSearch.getService().isEmpty()) {
            return experts;
        }
        else{
                subServices = subServicesServiceImpel.findBySubServicesName(dynamicSearch.getService());
                if(subServices == null) {
                    experts.clear();
                    return experts;
                }
                else
                    return subServices.getExperts().stream().filter(experts::contains).collect(Collectors.toList());
        }
    }

    private Specification<Expert> userSpecification(Expert expert){
        return (userRoot, query, criteriaBuilder)
                -> {
            CriteriaQuery<Expert> criteriaQuery = criteriaBuilder.createQuery(Expert.class);
            criteriaQuery.select(userRoot);

            List<Predicate> predicates = new ArrayList<>();
            if(expert.getRole() != null )
                predicates.add(criteriaBuilder.equal(userRoot.get("role"),expert.getRole()));
            if(expert.getFirstName() != null && !expert.getFirstName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("firstName"),expert.getFirstName()));
            if(expert.getLastName() != null && !expert.getLastName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("lastName"),expert.getLastName()));
            if(expert.getStars()!= null)
                predicates.add(criteriaBuilder.equal(userRoot.get("stars"),expert.getStars()));
            if(expert.getEmail() != null && !expert.getEmail().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("email"),expert.getEmail()));

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
