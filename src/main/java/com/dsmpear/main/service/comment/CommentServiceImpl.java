package com.dsmpear.main.service.comment;

import com.dsmpear.main.entity.comment.Comment;
import com.dsmpear.main.entity.comment.CommentRepository;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.CommentNotFoundException;
import com.dsmpear.main.exceptions.PermissionDeniedException;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.request.CommentRequest;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ReportRepository reportRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void createComment(CommentRequest commentRequest) {
        String email = authenticationFacade.getUserEmail();
        if(email == null) {
            throw new PermissionDeniedException();
        }


        reportRepository.findById(commentRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);
        commentRepository.save(
                Comment.builder()
                    .content(commentRequest.getContent())
                    .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .reportId(commentRequest.getReportId())
                    .userEmail(email)
                    .build()
        );

    }

    @Override
    public Integer updateComment(Integer commentId, String content) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        System.out.println("여기까진 살아있나?");

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if(!user.getEmail().equals(comment.getUserEmail())) {
            throw new PermissionDeniedException();
        }

        commentRepository.save(comment.updateContent(content));

        return commentId;
    }
    @Override
    public void deleteComment(Integer commentId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);

        if(!(comment.getUserEmail().equals(user.getEmail()))){
            throw new PermissionDeniedException();
        }

        commentRepository.delete(comment);
    }

}
