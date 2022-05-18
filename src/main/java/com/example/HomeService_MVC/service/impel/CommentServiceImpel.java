package com.example.HomeService_MVC.service.impel;

import com.example.HomeService_MVC.dto.comment.CommentDto;
import com.example.HomeService_MVC.model.Comment;
import com.example.HomeService_MVC.model.Customer;
import com.example.HomeService_MVC.model.Expert;
import com.example.HomeService_MVC.model.Offer;
import com.example.HomeService_MVC.repository.CommentRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import com.example.HomeService_MVC.service.interfaces.CommentService;

@Service
public class CommentServiceImpel implements CommentService {

    private final OfferServiceImpel offerServiceImpel;
    private final DozerBeanMapper mapper;
    private final CommentRepository commentRepository;
    private final ExpertServiceImpel expertServiceImpel;

    public CommentServiceImpel(OfferServiceImpel offerServiceImpel, DozerBeanMapper mapper, CommentRepository commentRepository, ExpertServiceImpel expertServiceImpel) {
        this.offerServiceImpel = offerServiceImpel;
        this.mapper = mapper;
        this.commentRepository = commentRepository;
        this.expertServiceImpel = expertServiceImpel;
    }

    @Override
    public void placeAComment(Customer customer,CommentDto commentDto, Integer orderId) {
        Offer offer = offerServiceImpel.findByOrderIdAndAcceptedTrue(orderId);
        Comment comment = mapper.map(commentDto,Comment.class);
        Expert expert = offer.getExpert();
        comment.setCustomer(customer);
        comment.setExpert(expert);
        expert.setStars(expert.getStars() + comment.getStars());
        commentRepository.save(comment);
        expertServiceImpel.updateStars(expert);
    }
}
